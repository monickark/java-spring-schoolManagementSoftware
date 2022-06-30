package com.jaw.core.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
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

import com.jaw.common.constants.ApplicationConstant;
import com.jaw.common.constants.ErrorCodeConstant;
import com.jaw.common.constants.ModelAndViewConstant;
import com.jaw.common.exceptions.DatabaseException;
import com.jaw.common.exceptions.DeleteFailedException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.UpdateFailedException;
import com.jaw.common.exceptions.util.TableNotSpecifiedForAuditException;
import com.jaw.core.service.ICourseMasterService;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.sessCache.SessionCache;

@Controller
public class CourseMasterController {
	// Logging
	Logger logger = Logger.getLogger(CourseMasterController.class);
	@Autowired
	ICourseMasterService courseMasterService;

	@RequestMapping(value = "/courseMasterList", method = RequestMethod.GET)
	public ModelAndView viewCourseMasterList(
			@ModelAttribute("courseMastr_Mtr") CourseMaster_MasterVO courseMaster_MasterVO,
			HttpServletRequest httpRequest,HttpSession session,
			RedirectAttributes redirectAttributes) throws NoDataFoundException {
		//WebUtils.setSessionAttribute(httpRequest, "success", null);
		// Getting the display tag parameter
		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		Map stockParamMap = WebUtils.getParametersStartingWith(httpRequest,
				"d-");
		
		// Setting model and view for exception handler
		
		ModelAndView modelAndView = new ModelAndView(
				ModelAndViewConstant.CRSE_MASTER_LIST, "courseMastr_Mtr",
				courseMaster_MasterVO);
		httpRequest.setAttribute("page", modelAndView);
		httpRequest.setAttribute("errors", "error");
		// Check whether the list already get or have to fetch from data base
		// using the list size
      
		if (stockParamMap.size() == 0) {

			logger.info("Table operation has been triggered");

			// Get list from database

			try {
				courseMasterService
						.selectCourseMasterList(courseMaster_MasterVO,sessionCache.getUserSessionDetails());
			} catch (NoDataFoundException e) {
				logger.error("Course Master list No Data found Exception found");
				WebUtils.setSessionAttribute(httpRequest, "display_tbl", null);
				throw new NoDataFoundException();
			}

			// Set the list to session to access in jsp

			WebUtils.setSessionAttribute(httpRequest, "display_tbl",
					courseMaster_MasterVO.getCrseMtrVOList());
			courseMaster_MasterVO.setPageSize(courseMaster_MasterVO
					.getPageSize());

		}
		redirectAttributes.addFlashAttribute("courseMastr_Mtr",
				courseMaster_MasterVO);
		return modelAndView;
	}

	@RequestMapping(value = "/courseMasterList", method = RequestMethod.POST, params = { "actionSave" })
	public String saveCourseMaster(
			@ModelAttribute("courseMastr_Mtr") CourseMaster_MasterVO courseMaster_MasterVO,
			ModelMap model, HttpServletRequest httpServletRequest,
			HttpSession session, RedirectAttributes redirectAttribute)
			throws DuplicateEntryException, DatabaseException {

		logger.debug("inside save Course Master method");
		
		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		httpServletRequest.setAttribute("errors", "erroradd");
		
		ModelAndView modelAndView = new ModelAndView(
				ModelAndViewConstant.CRSE_MASTER_LIST, "courseMastr_Mtr",
				courseMaster_MasterVO);
		httpServletRequest.setAttribute("page", modelAndView);
		courseMasterService.insertCourseMasterRec(
				courseMaster_MasterVO.getCrsMtrO(),
				sessionCache.getUserSessionDetails());
		WebUtils.setSessionAttribute(httpServletRequest, "success",
				ErrorCodeConstant.ADD_SUCCESS_MESS);
		logger.debug("Data's inserted successfully!");
		return "redirect:/courseMasterList.htm";
	}

	// Get Values from list for popup
	@RequestMapping(value = "/courseMasterList", method = RequestMethod.GET, params = { "actionGet" })
	public String getAcademicCalendar(
			@ModelAttribute("courseMastr_Mtr") CourseMaster_MasterVO courseMaster_MasterVO,
			ModelMap model, HttpServletRequest httpServletRequest,
			HttpSession session, RedirectAttributes redirectAttribute) {
		logger.debug("inside Get CourseMaster Details from list method");

		List<CourseMasterVO> courseMasterVOs = (List<CourseMasterVO>) WebUtils
				.getSessionAttribute(httpServletRequest, "display_tbl");

		String id = httpServletRequest.getParameter("rowId");
		courseMaster_MasterVO.setCrsMtrO(courseMasterVOs.get(Integer
				.parseInt(id)));
		/*model.addAttribute("popup", "update");
		return new ModelAndView(ModelAndViewConstant.CRSE_MASTER_LIST);*/
		redirectAttribute.addFlashAttribute("popup", "update");
		redirectAttribute.addFlashAttribute("courseMastr_Mtr", courseMaster_MasterVO);
		return "redirect:/courseMasterList.htm";
	}

	// Update method
	@RequestMapping(value = "/courseMasterList", method = RequestMethod.POST, params = { "actionUpdate" })
	public String updateCourseMaster(
			@ModelAttribute("courseMastr_Mtr") CourseMaster_MasterVO courseMaster_MasterVO,
			ModelMap model, HttpServletRequest httpServletRequest,
			HttpSession session, RedirectAttributes redirectAttribute)
			throws UpdateFailedException, NoDataFoundException, DuplicateEntryException, DatabaseException, TableNotSpecifiedForAuditException {

		logger.debug("inside update CourseMaster method");
		ApplicationCache applicationCache = (ApplicationCache) session
				.getServletContext().getAttribute(
						ApplicationConstant.APPLICATION_CACHE);
		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		ModelAndView modelAndView = new ModelAndView(
				ModelAndViewConstant.CRSE_MASTER_LIST, "courseMastr_Mtr",
				courseMaster_MasterVO);
		httpServletRequest.setAttribute("page", modelAndView);
		httpServletRequest.setAttribute("errors", "erroradd");
		courseMasterService.updateCourseMasterRec(
				courseMaster_MasterVO.getCrsMtrO(),
				sessionCache.getUserSessionDetails(),applicationCache);
		WebUtils.setSessionAttribute(httpServletRequest, "success",
				ErrorCodeConstant.UPDATE_SUCCESS_MESS);
		logger.debug("Data's updated successfully!");
		return "redirect:/courseMasterList.htm";
	}

	// Delete Method
	@RequestMapping(value = "/courseMasterList", method = RequestMethod.GET, params = { "actionDelete" })
	public String deleteCourseMaster(
			@ModelAttribute("courseMastr_Mtr") CourseMaster_MasterVO courseMaster_MasterVO,
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
				ModelAndViewConstant.CRSE_MASTER_LIST, "courseMastr_Mtr",
				courseMaster_MasterVO);
		httpServletRequest.setAttribute("page", modelAndView);
		httpServletRequest.setAttribute("errors", "erroradd");
		List<CourseMasterVO> courseMasterVOs = (List<CourseMasterVO>) WebUtils
				.getSessionAttribute(httpServletRequest, "display_tbl");

		String id = httpServletRequest.getParameter("rowId");
		courseMaster_MasterVO.setCrsMtrO(courseMasterVOs.get(Integer
				.parseInt(id)));
		courseMasterService.deleteCourseMasterRec(
				courseMaster_MasterVO.getCrsMtrO(),
				sessionCache.getUserSessionDetails(),applicationCache);
		WebUtils.setSessionAttribute(httpServletRequest, "success",
				ErrorCodeConstant.DELETE_SUCCESS_MESS);
		logger.debug("Data's deleted successfully!");
		return "redirect:/courseMasterList.htm";
	}

	@ExceptionHandler({ DuplicateEntryException.class, DatabaseException.class,
		 UpdateFailedException.class,
			DeleteFailedException.class, TableNotSpecifiedForAuditException.class })
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
