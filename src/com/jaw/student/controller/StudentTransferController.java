package com.jaw.student.controller;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.WebUtils;

import com.jaw.common.constants.ApplicationConstant;
import com.jaw.common.constants.ErrorCodeConstant;
import com.jaw.common.constants.ModelAndViewConstant;
import com.jaw.common.dropdown.service.IDropDownListService;
import com.jaw.common.exceptions.BatchUpdateFailedException;
import com.jaw.common.exceptions.DatabaseException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.FeeDueFoundException;
import com.jaw.common.exceptions.InvalidCategoryForUpdateException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.UpdateFailedException;
import com.jaw.common.exceptions.util.TableNotSpecifiedForAuditException;
import com.jaw.core.dao.StudentGroupMaster;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.sessCache.SessionCache;
import com.jaw.student.service.IStuTranService;
@Controller
public class StudentTransferController {
	@Autowired
	IStuTranService stuTranService;
	@Autowired
	IDropDownListService dropDownListService;
	
	// Logging
		Logger logger = Logger.getLogger(StudentTransferController.class);
		
		//Method to render the Student update Page
		@RequestMapping(value = "/stuTran", method = RequestMethod.GET)
		public ModelAndView getStudentUpdatePage(
				@ModelAttribute("stuTran") StuTranMasterVO stuTranMasterVO,
				ModelMap modelMap, HttpServletRequest httpServletRequest) {

			ModelAndView mav = new ModelAndView(".jaw.stuTran");
			//Session attributes to be null
			WebUtils.setSessionAttribute(httpServletRequest, "display_tbl", null);
			WebUtils.setSessionAttribute(httpServletRequest, "display_tbl1", null);
			WebUtils.setSessionAttribute(httpServletRequest, "success", null);
			WebUtils.setSessionAttribute(httpServletRequest, "keepStatus", null);
			WebUtils.setSessionAttribute(httpServletRequest, "searchVo", null);
			mav.getModelMap().addAttribute("status", "true");
			
			return mav;
		}
		
		
		@RequestMapping(value = "/stuTran", method = RequestMethod.GET, params = "StuDetails")
		public ModelAndView getStudentFeeDue(
				@ModelAttribute("stuTran") StuTranMasterVO stuTranMasterVO,
				ModelMap modelMap, HttpServletRequest httpServletRequest,
				RedirectAttributes redirectAttributes,HttpSession session) {
			SessionCache sessionCache = (SessionCache) session
					.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
			ModelAndView mav = new ModelAndView(ModelAndViewConstant.STUDENT_TRANSFER);
			String feeDue = stuTranService.getStuForTransfer(sessionCache.getUserSessionDetails(),stuTranMasterVO.getStuSearch());		
			stuTranMasterVO.getStuTranVO().setFeeDue(feeDue);
			WebUtils.setSessionAttribute(httpServletRequest, "searchVo", stuTranMasterVO.getStuSearch());
			return mav;
		}
		
		@RequestMapping(value = "/stuTran", method = RequestMethod.POST, params = "Transfer")
		public ModelAndView transferStu(
				@ModelAttribute("stuTran") StuTranMasterVO stuTranMasterVO,
				ModelMap modelMap, HttpServletRequest httpServletRequest,
				RedirectAttributes redirectAttributes,HttpSession session) throws FeeDueFoundException, NoDataFoundException, UpdateFailedException, DuplicateEntryException, DatabaseException, TableNotSpecifiedForAuditException {
			httpServletRequest.setAttribute("stuTran", stuTranMasterVO.getStuSearch());
			StuTranSearchVO  stuSearch = (StuTranSearchVO) WebUtils.getSessionAttribute(httpServletRequest, "searchVo");		
			ModelAndView mav = new ModelAndView(ModelAndViewConstant.STUDENT_TRANSFER);
			SessionCache sessionCache = (SessionCache) session
					.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
			ApplicationCache applicationCache = (ApplicationCache) session
					.getServletContext().getAttribute(
							ApplicationConstant.APPLICATION_CACHE);		
			if((stuTranMasterVO.getStuTranVO().getFeeDue()==null)||(stuTranMasterVO.getStuTranVO().getFeeDue().equals(""))){
				stuTranService.studentTransfer(stuSearch, stuTranMasterVO.getStuTranVO(), sessionCache.getUserSessionDetails(), applicationCache);
				WebUtils.setSessionAttribute(httpServletRequest, "success",
						ErrorCodeConstant.STU_TRAN_SUCCESS);
				WebUtils.setSessionAttribute(httpServletRequest, "searchVo", null);
			}else{
				httpServletRequest.setAttribute("enteredDetails", stuTranMasterVO.getStuTranVO());
				throw new FeeDueFoundException();
			}
		//	WebUtils.setSessionAttribute(httpServletRequest, "searchVo", stuTranMasterVO.getStuSearch());
			return mav;
		}


		@ModelAttribute("studentGrpList")
		public Map<String, String> gerStuGrpList(HttpSession session,
				HttpServletRequest httpSevletRequest, HttpServletResponse response,
				ModelMap model) throws IOException {
			Map<String, String> map = new LinkedHashMap<String,String>();
			SessionCache sessionCache = (SessionCache) session
					.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
			try {
				
				List<StudentGroupMaster> listOfStuGrpMas = dropDownListService.getStudentGroupListTag(sessionCache);
				for(StudentGroupMaster  stuGrpMas:listOfStuGrpMas){
					map.put(stuGrpMas.getStudentGrpId(), stuGrpMas.getStudentGrp());
				
				}			
					
			} catch (NoDataFoundException e) {
				logger.error("Error occured in Student Group Dropdown :" + e.getMessage());			
			}
			httpSevletRequest.setAttribute("studentGrpList", map);
			return map;

		}
		
		
		@RequestMapping(value = "/stuTran", method = RequestMethod.GET, params = { "StuNameMap" })
		public @ResponseBody  Map<String, String> getStudentName(HttpSession session,
				HttpServletRequest request, HttpServletResponse response,
				ModelMap model) throws IOException {		
			Map<String, String> mapOfStuNames = null;
			SessionCache sessionCache = (SessionCache) session
					.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
			String acy = request.getParameter("acTrm");
			String stuGrpId = request.getParameter("stuGrpId");			
			try {							
				mapOfStuNames = stuTranService.getStudentNameMap(sessionCache.getUserSessionDetails(), acy, stuGrpId);		
			
			} catch (NoDataFoundException e) {
				logger.error("Error occured in Student Name :" + e.getMessage());
			}				
			return mapOfStuNames;

		}
		
		@ExceptionHandler({ NoDataFoundException.class,
			FeeDueFoundException.class,
			IOException.class })
	public ModelAndView handleException(Exception ex, HttpSession session,
			HttpServletRequest request) {
			StuTranSearchVO stuMasterVO = (StuTranSearchVO) request
				.getAttribute("stuTran");
			StuTranMasterVO stuTran = new StuTranMasterVO();
			stuTran.setStuSearch(stuMasterVO);
		ModelAndView modelAndView = new ModelAndView(
				ModelAndViewConstant.STUDENT_TRANSFER);
		modelAndView.getModelMap().addAttribute("stuTran", stuTran);	
		StuTranVO stuTranVO =(StuTranVO)request.getAttribute("enteredDetails");
		stuTran.setStuTranVO(stuTranVO);
	//	String error = (String) request.getAttribute("errors");
		WebUtils.setSessionAttribute(request, "success", null);
		WebUtils.setSessionAttribute(request, "display_tbl", null);
		modelAndView.getModelMap().addAttribute("error", ex.getMessage());

		return modelAndView;

	}



}
