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
import com.jaw.common.exceptions.util.ErrorDescriptionNotFoundException;
import com.jaw.common.exceptions.util.TableNotSpecifiedForAuditException;
import com.jaw.common.util.ErrorCodeUtil;
import com.jaw.core.dao.CourseSubLink;
import com.jaw.core.service.IStudentGroupMasterService;
import com.jaw.core.service.ITeacherSubjectLinkService;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.sessCache.SessionCache;
import com.jaw.mark.controller.MarkMasterSearchVO;

@Controller
public class TeacherSubjectLinkController {
	// Logging
	Logger logger = Logger.getLogger(StudentGroupMasterController.class);
	@Autowired
	IDropDownListService dropDownListService;
	@Autowired
	ITeacherSubjectLinkService teacherSubLinkService;
	@Autowired
	ErrorCodeUtil errorCodeUtil;
	// Academic Calendar List Get method

		@RequestMapping(value = "/teacherSubjectLinkList", method = RequestMethod.GET)
		public ModelAndView teacherSubLink(
				@ModelAttribute("trSubLinkMtrr") TeacherSubjectLinkMasterVO teacherSubjectLinkMasterVO,
				ModelMap model, HttpServletRequest httpRequest,
				HttpSession httpSession) {

			logger.info("Rendering Teacher Subject Link List page");
			// Null the session display tag values
			ModelAndView mav = new ModelAndView(
					ModelAndViewConstant.TEACHER_SUB_LINK_LIST, "trSubLinkMtrr",
					teacherSubjectLinkMasterVO);
			WebUtils.setSessionAttribute(httpRequest, "display_tbl", null);
			WebUtils.setSessionAttribute(httpRequest, "success", null);
			WebUtils.setSessionAttribute(httpRequest, "keepStatus",
					null);
			WebUtils.setSessionAttribute(httpRequest, "searchVo",
					null);
			mav.getModelMap().addAttribute("status", "true");
			return mav;
			

		}
	
	@RequestMapping(value = "/teacherSubjectLinkList", method = RequestMethod.GET, params = { "Get" })
	public String viewTeacherSubjectLink(
			@ModelAttribute("trSubLinkMtrr") TeacherSubjectLinkMasterVO teacherSubjectLinkMasterVO,
			HttpServletRequest httpRequest,HttpSession session,RedirectAttributes redirectAttributes) throws NoDataFoundException {

		// Getting the display tag parameter
		Map stockParamMap = WebUtils.getParametersStartingWith(httpRequest,
				"d-");
		// Setting model and view for exception handler
		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		ModelAndView modelAndView = new ModelAndView(
				ModelAndViewConstant.TEACHER_SUB_LINK_LIST, "trSubLinkMtrr",
				teacherSubjectLinkMasterVO);
		httpRequest.setAttribute("page", modelAndView);
		httpRequest.setAttribute("errors", "error");
		httpRequest.setAttribute("trSubLinkPojo", teacherSubjectLinkMasterVO);
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

			teacherSubLinkService.selectTeacherSubjectLinkList(teacherSubjectLinkMasterVO,sessionCache.getUserSessionDetails());
			// Set the list to session to access in jsp

			WebUtils.setSessionAttribute(httpRequest, "display_tbl",
					teacherSubjectLinkMasterVO.getTeaSubLinkVOList());
			WebUtils.setSessionAttribute(httpRequest, "searchVo",
					teacherSubjectLinkMasterVO.getTeacherSubjectLinkSearchVO());
			teacherSubjectLinkMasterVO.setPageSize(teacherSubjectLinkMasterVO.getPageSize());

		}
		redirectAttributes.addFlashAttribute("trSubLinkMtrr",
				teacherSubjectLinkMasterVO);
		return "redirect:/teacherSubjectLinkList.htm?data";

	}

	@RequestMapping(value = "/teacherSubjectLinkList", method = RequestMethod.GET, params = "data")
	public ModelAndView staffListBack(
			@ModelAttribute("trSubLinkMtrr") TeacherSubjectLinkMasterVO teacherSubjectLinkMasterVO,
			HttpServletRequest httpServletRequest) {
		TeacherSubjectLinkSearchVO teacherSubjectLinkSearchVO= (TeacherSubjectLinkSearchVO) WebUtils
				.getSessionAttribute(httpServletRequest, "searchVo");		
		teacherSubjectLinkMasterVO.setTeacherSubjectLinkSearchVO(teacherSubjectLinkSearchVO);
		ModelAndView modelAndView = new ModelAndView(
				ModelAndViewConstant.TEACHER_SUB_LINK_LIST, "trSubLinkMtrr",
				teacherSubjectLinkMasterVO);
		return modelAndView;
	}
	
	@RequestMapping(value = "/teacherSubjectLinkList", method = RequestMethod.GET, params = { "actionGet" })
	public String getTeacherSubjectLink(
			@ModelAttribute("trSubLinkMtrr") TeacherSubjectLinkMasterVO teacherSubjectLinkMasterVO,
			ModelMap model, HttpServletRequest httpServletRequest,
			HttpSession session, RedirectAttributes redirectAttribute) {
		logger.debug("inside Get Teacher Subject Link method");
		TeacherSubjectLinkSearchVO teacherSubjectLinkSearchVO= (TeacherSubjectLinkSearchVO) WebUtils
				.getSessionAttribute(httpServletRequest, "searchVo");		
		teacherSubjectLinkMasterVO.setTeacherSubjectLinkSearchVO(teacherSubjectLinkSearchVO);
		redirectAttribute.addFlashAttribute("trSubLinkMtrr", teacherSubjectLinkMasterVO);
		List<TeacherSubjectLinkVO> teacherSubjectLinkVOs = (List<TeacherSubjectLinkVO>) WebUtils
				.getSessionAttribute(httpServletRequest, "display_tbl");

		String id = httpServletRequest.getParameter("rowId");
		teacherSubjectLinkMasterVO.setTeacSubLinkVO(teacherSubjectLinkVOs.get(Integer
				.parseInt(id)));
		model.addAttribute("popup", "update");
		String keepstat = (String) WebUtils.getSessionAttribute(
				httpServletRequest, "keepStatus");
		System.out.println("Keep status  :" + keepstat);
		ModelAndView modelAndView = new ModelAndView(
				ModelAndViewConstant.TEACHER_SUB_LINK_LIST);
		if (keepstat != null) {
			modelAndView.getModelMap().addAttribute("status", "false");
		} else {
			modelAndView.getModelMap().addAttribute("status", "true");
		}
		redirectAttribute.addFlashAttribute("popup", "update");
		redirectAttribute.addFlashAttribute("trSubLinkMtrr", teacherSubjectLinkMasterVO);
		return "redirect:/teacherSubjectLinkList.htm?data";			
	}

	@RequestMapping(value = "/teacherSubjectLinkList", method = RequestMethod.POST, params = { "actionSave" })
	public String saveTeacherSubjectLink(
			@ModelAttribute("trSubLinkMtrr") TeacherSubjectLinkMasterVO teacherSubjectLinkMasterVO,
			ModelMap model, HttpServletRequest httpServletRequest,
			HttpSession session, RedirectAttributes redirectAttribute)
			throws DuplicateEntryException, DatabaseException, DeleteFailedException, NoDataFoundException, TableNotSpecifiedForAuditException {
		TeacherSubjectLinkSearchVO teacherSubjectLinkSearchVO= (TeacherSubjectLinkSearchVO) WebUtils
				.getSessionAttribute(httpServletRequest, "searchVo");		
		teacherSubjectLinkMasterVO.setTeacherSubjectLinkSearchVO(teacherSubjectLinkSearchVO);
		redirectAttribute.addFlashAttribute("trSubLinkMtrr", teacherSubjectLinkMasterVO);
		logger.debug("inside save Teacher Subject Link method");
		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		ApplicationCache applicationCache = (ApplicationCache) session
				.getServletContext().getAttribute(
						ApplicationConstant.APPLICATION_CACHE);
		ModelAndView modelAndView = new ModelAndView(
				ModelAndViewConstant.TEACHER_SUB_LINK_LIST, "trSubLinkMtrr",
				teacherSubjectLinkMasterVO);
		httpServletRequest.setAttribute("page", modelAndView);
		httpServletRequest.setAttribute("errors", "erroradd");

		teacherSubLinkService.insertTeacherSubLinkRec(
				teacherSubjectLinkMasterVO.getTeacSubLinkVO(),
				sessionCache.getUserSessionDetails(),applicationCache);
		WebUtils.setSessionAttribute(httpServletRequest, "success",
				ErrorCodeConstant.ADD_SUCCESS_MESS);

		logger.debug("Data's inserted successfully!");
		WebUtils.setSessionAttribute(httpServletRequest, "keepStatus", "status");
		return "redirect:/teacherSubjectLinkList.htm?Get";
	}

	@RequestMapping(value = "/teacherSubjectLinkList", method = RequestMethod.POST, params = { "actionUpdate" })
	public String updateTeacherSubjectLink(
			@ModelAttribute("trSubLinkMtrr") TeacherSubjectLinkMasterVO teacherSubjectLinkMasterVO,
			ModelMap model, HttpServletRequest httpServletRequest,
			HttpSession session, RedirectAttributes redirectAttribute)
			throws UpdateFailedException, NoDataFoundException, DuplicateEntryException, DatabaseException, TableNotSpecifiedForAuditException {
		TeacherSubjectLinkSearchVO teacherSubjectLinkSearchVO= (TeacherSubjectLinkSearchVO) WebUtils
				.getSessionAttribute(httpServletRequest, "searchVo");		
		teacherSubjectLinkMasterVO.setTeacherSubjectLinkSearchVO(teacherSubjectLinkSearchVO);
		redirectAttribute.addFlashAttribute("trSubLinkMtrr", teacherSubjectLinkMasterVO);
		logger.debug("inside Updated Teacher Subject Link method");
		ApplicationCache applicationCache = (ApplicationCache) session
				.getServletContext().getAttribute(
						ApplicationConstant.APPLICATION_CACHE);
		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		ModelAndView modelAndView = new ModelAndView(
				ModelAndViewConstant.TEACHER_SUB_LINK_LIST, "trSubLinkMtrr",
				teacherSubjectLinkMasterVO);
		httpServletRequest.setAttribute("page", modelAndView);
		httpServletRequest.setAttribute("errors", "erroradd");
		teacherSubLinkService.updateTeacherSubLinkRec(
				teacherSubjectLinkMasterVO.getTeacSubLinkVO(),
				sessionCache.getUserSessionDetails(),applicationCache);
		WebUtils.setSessionAttribute(httpServletRequest, "success",
				ErrorCodeConstant.UPDATE_SUCCESS_MESS);

		logger.debug("Data's updated successfully!");
		WebUtils.setSessionAttribute(httpServletRequest, "keepStatus", "status");
		return "redirect:/teacherSubjectLinkList.htm?Get";
	}

	// Delete Method
	@RequestMapping(value = "/teacherSubjectLinkList", method = RequestMethod.GET, params = { "actionDelete" })
	public String deleteTeacherSubjectLink(
			@ModelAttribute("trSubLinkMtrr") TeacherSubjectLinkMasterVO teacherSubjectLinkMasterVO,
			ModelMap model, HttpServletRequest httpServletRequest,
			HttpSession session, RedirectAttributes redirectAttribute)
			throws DeleteFailedException, NoDataFoundException, DuplicateEntryException, DatabaseException, TableNotSpecifiedForAuditException {
		TeacherSubjectLinkSearchVO teacherSubjectLinkSearchVO= (TeacherSubjectLinkSearchVO) WebUtils
				.getSessionAttribute(httpServletRequest, "searchVo");		
		teacherSubjectLinkMasterVO.setTeacherSubjectLinkSearchVO(teacherSubjectLinkSearchVO);
		redirectAttribute.addFlashAttribute("trSubLinkMtrr", teacherSubjectLinkMasterVO);
		logger.debug("inside Deleted Teacher Subject Link method");
		ApplicationCache applicationCache = (ApplicationCache) session
				.getServletContext().getAttribute(
						ApplicationConstant.APPLICATION_CACHE);
		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		ModelAndView modelAndView = new ModelAndView(
				ModelAndViewConstant.TEACHER_SUB_LINK_LIST, "trSubLinkMtrr",
				teacherSubjectLinkMasterVO);
		httpServletRequest.setAttribute("page", modelAndView);
		httpServletRequest.setAttribute("errors", "erroradd");

		List<TeacherSubjectLinkVO> teacherSubjectLinkVOs = (List<TeacherSubjectLinkVO>) WebUtils
				.getSessionAttribute(httpServletRequest, "display_tbl");

		String id = httpServletRequest.getParameter("rowId");
		teacherSubjectLinkMasterVO.setTeacSubLinkVO(teacherSubjectLinkVOs.get(Integer
				.parseInt(id)));

		teacherSubLinkService.deleteTeacherSubLinkRec(teacherSubjectLinkMasterVO.getTeacSubLinkVO(),
				sessionCache.getUserSessionDetails(),applicationCache);
		WebUtils.setSessionAttribute(httpServletRequest, "success",
				ErrorCodeConstant.DELETE_SUCCESS_MESS);

		logger.debug("Data's deleted successfully!");
		WebUtils.setSessionAttribute(httpServletRequest, "keepStatus", "status");
		return "redirect:/teacherSubjectLinkList.htm?Get";
	}


	@ModelAttribute("teachingStaffList")
	public Map<String, String> gerTeachingStaffNameList(HttpSession session,
			HttpServletRequest httpSevletRequest, HttpServletResponse response,
			ModelMap model) throws IOException {
		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		Map<String, String> map = null;
		try {
			map = dropDownListService.selectTeachingStaffList(sessionCache.getUserSessionDetails());
		} catch (NoDataFoundException e) {
			logger.error("Error occured in Staff Name list Dropdown :" + e.getMessage());	
		}
		httpSevletRequest.setAttribute("teachingStaffList", map);
		return map;

	}
	
	@ModelAttribute("allSubjectList")
	public Map<String, String> gerAllSubjectList(HttpSession session,
			HttpServletRequest httpSevletRequest, HttpServletResponse response,
			ModelMap model) throws IOException {
		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		Map<String, String> map = null;
		try {
			map = dropDownListService.selectAllSubjectList(sessionCache.getUserSessionDetails());
		} catch (NoDataFoundException e) {
			logger.error("Error occured in Staff Name list Dropdown :" + e.getMessage());	
		}
		httpSevletRequest.setAttribute("allSubjectList", map);
		return map;

	}
	// Ajax For getting Subject List
		@RequestMapping(value = "/teacherSubjectLinkList", method = RequestMethod.GET, params = { "subjectList" })
		public @ResponseBody
		Map<String, String>  retriveSubjectList(HttpSession session, HttpServletRequest request, HttpServletResponse response) throws ErrorDescriptionNotFoundException {
			logger.debug("inside ajax get subject list method");
			String res = null;
			ApplicationCache applicationCache = (ApplicationCache) session
					.getServletContext().getAttribute(
							ApplicationConstant.APPLICATION_CACHE);
			SessionCache sessionCache = (SessionCache) session
					.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		
			List<CourseSubLink> courseSubLinks = null;
			Map<String, String> list = null;
			try {
				list =dropDownListService.selectAllSubjectList(sessionCache.getUserSessionDetails());
			}
				catch (NoDataFoundException e) {
				
				e.printStackTrace();
				
				try {
					response.getWriter().write(
							e.getMessage()
									+ ":"
									+ errorCodeUtil.getErrorDescription(applicationCache,
											e.getMessage()));
				}
				catch (IOException e1) {
					
					e1.printStackTrace();
				}
			}
			
			return list;
		}
	@ExceptionHandler({ DuplicateEntryException.class, DatabaseException.class,
			 DeleteFailedException.class,UpdateFailedException.class ,TableNotSpecifiedForAuditException.class,ErrorDescriptionNotFoundException.class})
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
		} else {
			System.out.println("inside non  delete message");
			WebUtils.setSessionAttribute(request, "success", null);
			mav.getModelMap().addAttribute("error", ex.getMessage());
		}
		

	WebUtils.setSessionAttribute(request, "display_tbl", null);
	return mav;
}
}
