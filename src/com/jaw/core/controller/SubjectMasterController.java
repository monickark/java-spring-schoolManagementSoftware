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
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.WebUtils;
import com.jaw.common.constants.PropertyManagementConstant;

import com.jaw.common.constants.ApplicationConstant;
import com.jaw.common.constants.ErrorCodeConstant;
import com.jaw.common.constants.ModelAndViewConstant;
import com.jaw.common.dropdown.service.IDropDownListService;
import com.jaw.common.exceptions.CustomAndSubjectCodeExistException;
import com.jaw.common.exceptions.DatabaseException;
import com.jaw.common.exceptions.DeleteFailedException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.UpdateFailedException;
import com.jaw.common.exceptions.util.PropertyNotFoundException;
import com.jaw.common.exceptions.util.TableNotSpecifiedForAuditException;
import com.jaw.common.util.PropertyManagementUtil;
import com.jaw.core.service.ICourseMasterService;
import com.jaw.core.service.ISubjectMasterService;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.sessCache.SessionCache;
@Controller
public class SubjectMasterController {
	// Logging
	Logger logger = Logger.getLogger(SubjectMasterController.class);
	@Autowired
	IDropDownListService dropDownListService;
	@Autowired
	ISubjectMasterService subjectMasterService;
	@Autowired
	PropertyManagementUtil propertyManagementUtil;
	@RequestMapping(value = "/subjectMasterList", method = RequestMethod.GET)
	public ModelAndView viewSubjectMasterList(
			@ModelAttribute("subjectMastr_Mtr") SubjectMaster_MasterVO subjectMaster_MasterVO,
			HttpServletRequest httpRequest,HttpSession session,
			RedirectAttributes redirectAttributes) throws NoDataFoundException, PropertyNotFoundException {
		//WebUtils.setSessionAttribute(httpRequest, "success", null);
		// Getting the display tag parameter
		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		ApplicationCache applicationCache = (ApplicationCache) session
				.getServletContext().getAttribute(
						ApplicationConstant.APPLICATION_CACHE);
		
		
		Map stockParamMap = WebUtils.getParametersStartingWith(httpRequest,
				"d-");
		
		// Setting model and view for exception handler
		
		ModelAndView modelAndView = new ModelAndView(
				ModelAndViewConstant.SUB_MASTER_LIST, "subjectMastr_Mtr",
				subjectMaster_MasterVO);
		httpRequest.setAttribute("page", modelAndView);
		httpRequest.setAttribute("errors", "error");
		// Check whether the list already get or have to fetch from data base
		// using the list size
		
		if (stockParamMap.size() == 0) {

			logger.info("Table operation has been triggered");

			// Get list from database

			try {
				subjectMasterService.selectSubjectMasterList(subjectMaster_MasterVO, sessionCache.getUserSessionDetails());
			} catch (NoDataFoundException e) {
				logger.error("Course Master list No Data found Exception found");
				WebUtils.setSessionAttribute(httpRequest, "display_tbl", null);
				throw new NoDataFoundException();
			}

			// Set the list to session to access in jsp

			WebUtils.setSessionAttribute(httpRequest, "display_tbl",
					subjectMaster_MasterVO.getSubMtrVOList());
			subjectMaster_MasterVO.setPageSize(subjectMaster_MasterVO
					.getPageSize());

		}
		
		return modelAndView;
	}

	@RequestMapping(value = "/subjectMasterList", method = RequestMethod.POST, params = { "actionSave" })
	public String saveSubjectMaster(
			@ModelAttribute("subjectMastr_Mtr") SubjectMaster_MasterVO subjectMaster_MasterVO,
			ModelMap model, HttpServletRequest httpServletRequest,
			HttpSession session, RedirectAttributes redirectAttribute)
			throws DuplicateEntryException, DatabaseException, NoDataFoundException, UpdateFailedException, TableNotSpecifiedForAuditException, CustomAndSubjectCodeExistException {

		logger.debug("inside save Subject Master method");
		ApplicationCache applicationCache = (ApplicationCache) session
				.getServletContext().getAttribute(
						ApplicationConstant.APPLICATION_CACHE);
		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		httpServletRequest.setAttribute("errors", "erroradd");
		System.out.println("subject Id"+subjectMaster_MasterVO.getSubjectMasterVO().getSub_Id());
		
		ModelAndView modelAndView = new ModelAndView(
				ModelAndViewConstant.SUB_MASTER_LIST, "subjectMastr_Mtr",
				subjectMaster_MasterVO);
		httpServletRequest.setAttribute("page", modelAndView);
		System.out.println("subject master list            "+subjectMaster_MasterVO.getSubjectMasterVO().toString());
		subjectMasterService.insertSubjectMasterRec(subjectMaster_MasterVO.getSubjectMasterVO(), sessionCache.getUserSessionDetails(),applicationCache);
		WebUtils.setSessionAttribute(httpServletRequest, "success",
				ErrorCodeConstant.ADD_SUCCESS_MESS);
		logger.debug("Data's inserted successfully!");
		return "redirect:/subjectMasterList.htm";
	}

	// Get Values from list for popup
	@RequestMapping(value = "/subjectMasterList", method = RequestMethod.GET, params = { "actionGet" })
	public String getSubjectMaster(
			@ModelAttribute("subjectMastr_Mtr") SubjectMaster_MasterVO subjectMaster_MasterVO,
			ModelMap model, HttpServletRequest httpServletRequest,
			HttpSession session, RedirectAttributes redirectAttribute) {
		logger.debug("inside Get SubjectMaster Details from list method");

		List<SubjectMasterVO> subjectMasterVOs = (List<SubjectMasterVO>) WebUtils
				.getSessionAttribute(httpServletRequest, "display_tbl");

		String id = httpServletRequest.getParameter("rowId");
		System.out.println("action get eeeeeeee"+subjectMasterVOs.get(Integer
				.parseInt(id)).toString());
		subjectMaster_MasterVO.setSubjectMasterVO(subjectMasterVOs.get(Integer
				.parseInt(id)));
		model.addAttribute("popup", "update");		
		redirectAttribute.addFlashAttribute("popup", "update");
		redirectAttribute.addFlashAttribute("subjectMastr_Mtr", subjectMaster_MasterVO);
		return "redirect:/subjectMasterList.htm";
	}

	// Update method
	@RequestMapping(value = "/subjectMasterList", method = RequestMethod.POST, params = { "actionUpdate" })
	public String updateCourseMaster(
			@ModelAttribute("subjectMastr_Mtr") SubjectMaster_MasterVO subjectMaster_MasterVO,
			ModelMap model, HttpServletRequest httpServletRequest,
			HttpSession session, RedirectAttributes redirectAttribute)
			throws UpdateFailedException, NoDataFoundException, DuplicateEntryException, DatabaseException, TableNotSpecifiedForAuditException, CustomAndSubjectCodeExistException {

		logger.debug("inside update SubjectMaster method");
		ApplicationCache applicationCache = (ApplicationCache) session
				.getServletContext().getAttribute(
						ApplicationConstant.APPLICATION_CACHE);
		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		ModelAndView modelAndView = new ModelAndView(
				ModelAndViewConstant.SUB_MASTER_LIST, "subjectMastr_Mtr",
				subjectMaster_MasterVO);
		httpServletRequest.setAttribute("page", modelAndView);
		httpServletRequest.setAttribute("errors", "erroradd");
		redirectAttribute.addFlashAttribute("popup", "update");
		subjectMasterService.updateSubjectMasterRec(
				subjectMaster_MasterVO.getSubjectMasterVO(),
				sessionCache.getUserSessionDetails(),applicationCache);
		redirectAttribute.addFlashAttribute("popup", null);
		WebUtils.setSessionAttribute(httpServletRequest, "success",
				ErrorCodeConstant.UPDATE_SUCCESS_MESS);
		logger.debug("Data's updated successfully!");
		return "redirect:/subjectMasterList.htm?Get";
	}

	// Delete Method
	@RequestMapping(value = "/subjectMasterList", method = RequestMethod.GET, params = { "actionDelete" })
	public String deleteCourseMaster(
			@ModelAttribute("subjectMastr_Mtr") SubjectMaster_MasterVO subjectMaster_MasterVO,
			ModelMap model, HttpServletRequest httpServletRequest,
			HttpSession session, RedirectAttributes redirectAttribute)
			throws DeleteFailedException, NoDataFoundException, DuplicateEntryException, DatabaseException, TableNotSpecifiedForAuditException {

		logger.debug("inside Deleted Course Master method");
		ApplicationCache applicationCache = (ApplicationCache) session
				.getServletContext().getAttribute(
						ApplicationConstant.APPLICATION_CACHE);
		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		ModelAndView modelAndView = new ModelAndView(
				ModelAndViewConstant.SUB_MASTER_LIST, "subjectMastr_Mtr",
				subjectMaster_MasterVO);
		httpServletRequest.setAttribute("page", modelAndView);
		httpServletRequest.setAttribute("errors", "erroradd");
		List<SubjectMasterVO> subjectMasterVOs = (List<SubjectMasterVO>) WebUtils
				.getSessionAttribute(httpServletRequest, "display_tbl");

		String id = httpServletRequest.getParameter("rowId");
		subjectMaster_MasterVO.setSubjectMasterVO(subjectMasterVOs.get(Integer
				.parseInt(id)));
		subjectMasterService.deleteSubjectMasterRec(
				subjectMaster_MasterVO.getSubjectMasterVO(),
				sessionCache.getUserSessionDetails(),applicationCache);
		WebUtils.setSessionAttribute(httpServletRequest, "success",
				ErrorCodeConstant.DELETE_SUCCESS_MESS);
		logger.debug("Data's deleted successfully!");
		return "redirect:/subjectMasterList.htm?Get";
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
		 UpdateFailedException.class,
			DeleteFailedException.class, TableNotSpecifiedForAuditException.class, CustomAndSubjectCodeExistException.class })
	public ModelAndView handleException(Exception ex, HttpSession session,
			HttpServletRequest request) {
		logger.error("inside Exception Handler method :"+ex.getMessage());
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
