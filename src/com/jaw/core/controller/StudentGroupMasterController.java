package com.jaw.core.controller;

import java.io.IOException;
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
import com.jaw.common.exceptions.DatabaseException;
import com.jaw.common.exceptions.DeleteFailedException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.UpdateFailedException;
import com.jaw.common.exceptions.util.TableNotSpecifiedForAuditException;
import com.jaw.core.dao.StudentGroupMaster;
import com.jaw.core.service.IStudentGroupMasterService;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.sessCache.SessionCache;

@Controller
public class StudentGroupMasterController {
	// Logging
	Logger logger = Logger.getLogger(StudentGroupMasterController.class);
	@Autowired
	IDropDownListService dropDownListService;
	@Autowired
	IStudentGroupMasterService studentGroupMasterService;

	// Academic Calendar List Get method

		@RequestMapping(value = "/studentGroupList", method = RequestMethod.GET)
		public ModelAndView studentGrpList(
				@ModelAttribute("stdgrpM") StudentGroupMasterVO studentGroupMasterVO,
				ModelMap model, HttpServletRequest httpRequest,
				HttpSession httpSession) {

			logger.info("Rendering Student Group List page");
			// Null the session display tag values
			ModelAndView mav = new ModelAndView(
					ModelAndViewConstant.STUDENT_GRP_LIST, "stdgrpM",
					studentGroupMasterVO);
			WebUtils.setSessionAttribute(httpRequest, "display_tbl", null);
			WebUtils.setSessionAttribute(httpRequest, "success", null);
			WebUtils.setSessionAttribute(httpRequest, "searchVo",
					null);
			WebUtils.setSessionAttribute(httpRequest, "keepStatus",
					null);
			mav.getModelMap().addAttribute("status", "true");
			return mav;

		}

	@RequestMapping(value = "/studentGroupList", method = RequestMethod.GET, params = { "Get" })
	public String viewStudentGroupList(
			@ModelAttribute("stdgrpM") StudentGroupMasterVO studentGroupMasterVO,
			HttpSession session, HttpServletRequest httpRequest,ModelMap model,RedirectAttributes redirectAttributes) throws NoDataFoundException {

		// Getting the display tag parameter

		Map stockParamMap = WebUtils.getParametersStartingWith(httpRequest,
				"d-");
		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		// Setting model and view for exception handler

		ModelAndView modelAndView = new ModelAndView(
				ModelAndViewConstant.STUDENT_GRP_LIST, "stdgrpM", studentGroupMasterVO);
		httpRequest.setAttribute("page", modelAndView);
		httpRequest.setAttribute("errors", "error");
	
		
		// Check whether the list already get or have to fetch from data base
		// using the list size
		if (httpRequest.getParameter("Get").equals("Get")) {
			WebUtils.setSessionAttribute(httpRequest, "keepStatus",
					null);
			WebUtils.setSessionAttribute(httpRequest, "success", null);
		}
		else {
			WebUtils.setSessionAttribute(httpRequest, "keepStatus",
					"status");
		}
		if (stockParamMap.size() == 0) {

			logger.info("Table operation has been triggered");

			// Get list from database

			studentGroupMasterService.selectStudentGroupList(studentGroupMasterVO,sessionCache.getUserSessionDetails());

			// Set the list to session to access in jsp

			WebUtils.setSessionAttribute(httpRequest, "display_tbl",
					studentGroupMasterVO.getStudentMasterVOList());
			WebUtils.setSessionAttribute(httpRequest, "searchVo",
					studentGroupMasterVO.getCourseMasterId());
			studentGroupMasterVO.setPageSize(studentGroupMasterVO.getPageSize());
		}
		redirectAttributes.addFlashAttribute("stdgrpM", studentGroupMasterVO);
		
		return "redirect:/studentGroupList.htm?data";

		
	}
	@RequestMapping(value = "/studentGroupList", method = RequestMethod.GET, params = "data")
	public ModelAndView staffListBack(
			@ModelAttribute("stdgrpM") StudentGroupMasterVO studentGroupMasterVO,ModelMap model,
			HttpServletRequest httpServletRequest) {
	
		String searchString= (String) WebUtils
				.getSessionAttribute(httpServletRequest, "searchVo");		
		if(searchString!=null){
			studentGroupMasterVO.setCourseMasterId(searchString);
		}
		
		ModelAndView modelAndView = new ModelAndView(
				ModelAndViewConstant.STUDENT_GRP_LIST, "stdgrpM",
				studentGroupMasterVO);	
	
		return modelAndView;
	}


	@RequestMapping(value = "/studentGroupList", method = RequestMethod.GET, params = {
			"actionGet"
	})
	public String getStudentGroupMaster(
			@ModelAttribute("stdgrpM") StudentGroupMasterVO studentGroupMasterVO,
			ModelMap model, HttpServletRequest httpServletRequest,
			HttpSession session, RedirectAttributes redirectAttribute)
	{
		
		logger.debug("inside Update Student Group Master method");
		
		List<StudentGroupVO> studentGroupVOs = (List<StudentGroupVO>) WebUtils
				.getSessionAttribute(httpServletRequest, "display_tbl");
		
		String id = httpServletRequest.getParameter("rowId");
		studentGroupMasterVO.setStudentMtrVo(studentGroupVOs.get(Integer.parseInt(id)));	
		model.addAttribute("popup", "update");
		String keepstat = (String) WebUtils.getSessionAttribute(
				httpServletRequest, "keepStatus");	
		ModelAndView modelAndView = new ModelAndView(
				ModelAndViewConstant.STUDENT_GRP_LIST);
		if (keepstat != null) {
			modelAndView.getModelMap().addAttribute("status", "false");
		} else {
			modelAndView.getModelMap().addAttribute("status", "true");
		}
		redirectAttribute.addFlashAttribute("popup", "update");
		redirectAttribute.addFlashAttribute("stdgrpM", studentGroupMasterVO);
		return "redirect:/studentGroupList.htm?data";	
		
		
	}
	
	@RequestMapping(value = "/studentGroupList", method = RequestMethod.POST, params = { "actionSave" })
	public String saveStudentGroup(
			@ModelAttribute("stdgrpM") StudentGroupMasterVO studentGroupMasterVO,
			ModelMap model, HttpServletRequest httpServletRequest,
			HttpSession session, RedirectAttributes redirectAttribute)
			throws DuplicateEntryException, DatabaseException {

		logger.debug("inside save Student Group Master method");
		
		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		String searchString= (String) WebUtils
				.getSessionAttribute(httpServletRequest, "searchVo");		
		if(searchString!=null){
			studentGroupMasterVO.setCourseMasterId(searchString);
		}
		redirectAttribute.addFlashAttribute("stdgrpM", studentGroupMasterVO);
		ModelAndView modelAndView = new ModelAndView(
				ModelAndViewConstant.STUDENT_GRP_LIST, "stdgrpM", studentGroupMasterVO);	
		httpServletRequest.setAttribute("page", modelAndView);
		httpServletRequest.setAttribute("errors", "erroradd");
		if(studentGroupMasterVO.getStudentMtrVo().getCourseMasterId().contains (","))
	    {
	        String parts[] = studentGroupMasterVO.getStudentMtrVo().getCourseMasterId().split(",");
	        studentGroupMasterVO.getStudentMtrVo().setCourseMasterId(parts[0])  ; // i want to strip part after  +
	    }
		studentGroupMasterService.insertStudentGroupMasterRec(
				studentGroupMasterVO.getStudentMtrVo(),
				sessionCache.getUserSessionDetails());	
		
		
		WebUtils.setSessionAttribute(httpServletRequest, "success",
				ErrorCodeConstant.ADD_SUCCESS_MESS);
		logger.debug("Data's inserted successfully!");
		WebUtils.setSessionAttribute(httpServletRequest, "keepStatus", "status");
		return "redirect:/studentGroupList.htm?Get";
	}

	@RequestMapping(value = "/studentGroupList", method = RequestMethod.POST, params = { "actionUpdate" })
	public String updateStudentGroup(
			@ModelAttribute("stdgrpM") StudentGroupMasterVO studentGroupMasterVO,
			ModelMap model, HttpServletRequest httpServletRequest,
			HttpSession session, RedirectAttributes redirectAttribute)
			throws UpdateFailedException, NoDataFoundException, DuplicateEntryException, DatabaseException, TableNotSpecifiedForAuditException {

		logger.debug("inside Updated Student Group Master method");
		ApplicationCache applicationCache = (ApplicationCache) session
				.getServletContext().getAttribute(
						ApplicationConstant.APPLICATION_CACHE);
		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		String searchString= (String) WebUtils
				.getSessionAttribute(httpServletRequest, "searchVo");		
		if(searchString!=null){
			studentGroupMasterVO.setCourseMasterId(searchString);
		}
		redirectAttribute.addFlashAttribute("stdgrpM", studentGroupMasterVO);
		ModelAndView modelAndView = new ModelAndView(
				ModelAndViewConstant.STUDENT_GRP_LIST, "stdgrpM", studentGroupMasterVO);
		httpServletRequest.setAttribute("page", modelAndView);
		httpServletRequest.setAttribute("errors", "erroradd");
		
		studentGroupMasterService.updateStudentGroupMasterRec(
				studentGroupMasterVO.getStudentMtrVo(),
				sessionCache.getUserSessionDetails(),applicationCache);		
		
		WebUtils.setSessionAttribute(httpServletRequest, "success",
				ErrorCodeConstant.UPDATE_SUCCESS_MESS);
		logger.debug("Data's updated successfully!");
		WebUtils.setSessionAttribute(httpServletRequest, "keepStatus", "status");
		return "redirect:/studentGroupList.htm?Get";
	}
	//Delete Method
	@RequestMapping(value = "/studentGroupList", method = RequestMethod.GET, params = { "actionDelete" })
	public String deleteStudentGroup(
			@ModelAttribute("stdgrpM") StudentGroupMasterVO studentGroupMasterVO,
			ModelMap model, HttpServletRequest httpServletRequest,
			HttpSession session, RedirectAttributes redirectAttribute)
			throws DeleteFailedException, NoDataFoundException, DuplicateEntryException, DatabaseException, TableNotSpecifiedForAuditException {

		logger.debug("inside Deleted Student Group Master method");
		ApplicationCache applicationCache = (ApplicationCache) session
				.getServletContext().getAttribute(
						ApplicationConstant.APPLICATION_CACHE);
		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		String searchString= (String) WebUtils
				.getSessionAttribute(httpServletRequest, "searchVo");		
		if(searchString!=null){
			studentGroupMasterVO.setCourseMasterId(searchString);
		}
		redirectAttribute.addFlashAttribute("stdgrpM", studentGroupMasterVO);
		ModelAndView modelAndView = new ModelAndView(
				ModelAndViewConstant.STUDENT_GRP_LIST, "stdgrpM", studentGroupMasterVO);
		httpServletRequest.setAttribute("page", modelAndView);
		httpServletRequest.setAttribute("errors", "erroradd");	
		
		List<StudentGroupVO> studentGroupVOs = (List<StudentGroupVO>) WebUtils
				.getSessionAttribute(httpServletRequest, "display_tbl");
		
		String id = httpServletRequest.getParameter("rowId");
		studentGroupMasterVO.setStudentMtrVo(studentGroupVOs.get(Integer.parseInt(id)));
		
		studentGroupMasterService.deleteStudentGrpMasterRec(
				studentGroupMasterVO.getStudentMtrVo(),
				sessionCache.getUserSessionDetails(),applicationCache);
		WebUtils.setSessionAttribute(httpServletRequest, "success",
				ErrorCodeConstant.DELETE_SUCCESS_MESS);
		logger.debug("Data's deleted successfully!");
		WebUtils.setSessionAttribute(httpServletRequest, "keepStatus", "status");
		return "redirect:/studentGroupList.htm?Get";
	}

	
	// Ajax For getting StudentGroups
			@RequestMapping(value = "/studentGrpListTag", method = RequestMethod.GET, params = { "studentGroups" })
			public @ResponseBody
			List<StudentGroupMaster> retriveStudentGroupList(HttpSession session, HttpServletRequest request) throws NoDataFoundException {
				String res = "result";			
				SessionCache sessionCache = (SessionCache) session
						.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);				
				List<StudentGroupMaster> lts = null;			
					lts = dropDownListService.getStudentGroupListTag(sessionCache, request.getParameter("academicTerm"));				
				if(lts.isEmpty()){
					lts.add(null);
				}
				return lts;
			}
		// Ajax For getting StudentGroups
			@RequestMapping(value = "/studentGrpListTag", method = RequestMethod.GET, params = { "studentGroupsForDashBoard" })
			public @ResponseBody
			List<StudentGroupMaster> retriveStudentGroupListForDashBoard(HttpSession session, HttpServletRequest request) throws NoDataFoundException {
						String res = "result";			
						SessionCache sessionCache = (SessionCache) session
									.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);				
							List<StudentGroupMaster> lts = null;			
								lts = dropDownListService.getStudentGroupListTagForDashBoard(sessionCache, request.getParameter("academicTerm"));				
							if(lts.isEmpty()){
								lts.add(null);
							}
							return lts;
						}
	
	@ModelAttribute("courseList")
	public Map<String, String> gerCourseNameList(HttpSession session,
			HttpServletRequest httpSevletRequest, HttpServletResponse response,ModelMap model) throws IOException {
		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		Map<String, String> map = null;
		try {
			map = dropDownListService.getCourseNameListTag(sessionCache.getUserSessionDetails());
		} catch (NoDataFoundException e) {
			logger.error("Error occured in Course Name list Dropdown :" + e.getMessage());
		}
		httpSevletRequest.setAttribute("courseList", map);
		return map;

	}
	
	@ExceptionHandler({ DuplicateEntryException.class, DatabaseException.class,
			DeleteFailedException.class,UpdateFailedException.class ,  TableNotSpecifiedForAuditException.class})
	public ModelAndView handleException(Exception ex, HttpSession session,
			HttpServletRequest request) {
		ModelAndView mav = (ModelAndView) request.getAttribute("page");
		String error = (String) request.getAttribute("errors");
		mav.getModelMap().addAttribute(error, ex.getMessage());
		WebUtils.setSessionAttribute(request, "success", null);
		return mav;

	}
	@ExceptionHandler({ 
		NoDataFoundException.class  })
public ModelAndView handleNoDataException(Exception ex, HttpSession session,
		HttpServletRequest request) {
		logger.error("Exception Handledddd :" + ex.getMessage());	
		ModelAndView mav = (ModelAndView) request.getAttribute("page");
		String message = (String) WebUtils.getSessionAttribute(request,
				"success");
		if ((message != null)
				&& (message.equals(ErrorCodeConstant.DELETE_SUCCESS_MESS))) {	
			mav.getModelMap().addAttribute("error",ErrorCodeConstant.DELETE_SUCCESS_MESS);
			
		} else {		
			mav.getModelMap().addAttribute("error", ex.getMessage());
		}
		WebUtils.setSessionAttribute(request, "success", null);

	WebUtils.setSessionAttribute(request, "display_tbl", null);
	return mav;
}
}
