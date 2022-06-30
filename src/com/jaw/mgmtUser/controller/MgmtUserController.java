package com.jaw.mgmtUser.controller;

import java.io.IOException;

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
import org.springframework.web.servlet.view.RedirectView;

import com.jaw.common.business.CommonBusiness;
import com.jaw.common.constants.ApplicationConstant;
import com.jaw.common.constants.ErrorCodeConstant;
import com.jaw.common.exceptions.DatabaseException;
import com.jaw.common.exceptions.DeleteFailedException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.FileNotFoundInDatabase;
import com.jaw.common.exceptions.FileNotSaveException;
import com.jaw.common.exceptions.util.PropertyNotFoundException;
import com.jaw.common.util.ErrorCodeUtil;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.sessCache.SessionCache;
import com.jaw.mgmtUser.service.IMgmtUserDetailsService;

//Management Controller Class
@Controller
public class MgmtUserController {

	// Logging
	Logger logger = Logger.getLogger(MgmtUserController.class);

	@Autowired
	IMgmtUserDetailsService managementDetailsService;
	@Autowired
	ErrorCodeUtil errorCodeUtil;
	@Autowired
	CommonBusiness commonBusiness;

	// method to view management.jsp
	@RequestMapping(value = "/management", method = RequestMethod.GET)
	public ModelAndView managementCreate(
			@ModelAttribute("management") MgmtUserVO managementVO,
			HttpSession session, HttpServletRequest httpServletRequest,
			RedirectView redirect, ModelMap model) {

		logger.info("Opening Management Page");

		ModelAndView mav = new ModelAndView(".jaw.management");

		return mav;
	}

	// method to Update Management
	@RequestMapping(value = "/management", method = RequestMethod.POST)
	public String insertManagement(
			@ModelAttribute("management") MgmtUserVO managementVO,
			RedirectAttributes redirectAttributes, HttpSession session,
			HttpServletRequest httpServletRequest)
			throws FileNotFoundInDatabase, DuplicateEntryException,
			DatabaseException, NumberFormatException, PropertyNotFoundException, IllegalStateException, IOException, DeleteFailedException, FileNotSaveException {

		ApplicationCache applicationCache = (ApplicationCache) session
				.getServletContext().getAttribute(
						ApplicationConstant.APPLICATION_CACHE);
		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		ModelAndView mav = new ModelAndView(".jaw.management", "management",
				managementVO);
		httpServletRequest.setAttribute("page", mav);
		managementDetailsService.insertManagementDetails(managementVO,
				sessionCache.getUserSessionDetails(), applicationCache,session.getServletContext());
		redirectAttributes.addFlashAttribute("success",
				ErrorCodeConstant.ADD_SUCCESS_MESS);
		return "redirect:/management.htm";

	}

	@ExceptionHandler({ FileNotFoundInDatabase.class,
			DuplicateEntryException.class, PropertyNotFoundException.class,DeleteFailedException.class,FileNotSaveException.class })
	public ModelAndView handleException(Exception ex, HttpSession session,
			HttpServletRequest request,
			@ModelAttribute("management") MgmtUserVO managementVO) {
		ModelAndView mav = (ModelAndView) request.getAttribute("page");
		mav.getModelMap().addAttribute("error", ex.getMessage());
		return mav;

	}
}
