package com.jaw.login.controller;

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

import com.jaw.common.constants.ApplicationConstant;
import com.jaw.common.constants.ErrorCodeConstant;
import com.jaw.common.exceptions.DatabaseException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.RequestNotAcceptedException;
import com.jaw.common.exceptions.UpdateFailedException;
import com.jaw.common.exceptions.login.InvalidUserIdException;
import com.jaw.common.exceptions.login.UserDisabledException;
import com.jaw.common.exceptions.util.ErrorDescriptionNotFoundException;
import com.jaw.common.util.ErrorCodeUtil;
import com.jaw.common.util.MenuProfileUtil;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.login.service.IRequestService;
import com.jaw.user.controller.UserVO;
import com.jaw.user.service.IUserManagementService;

@Controller
public class RequestController {
	Logger logger = Logger.getLogger(RequestController.class);

	@Autowired
	private IRequestService requestService;
	@Autowired
	private ErrorCodeUtil errorCodeUtil;
	@Autowired
	MenuProfileUtil menuProfileUtil;
	@Autowired
	IUserManagementService userManagementService;

	// Request List Get method

	// Page render method to show requesting new password page

	@RequestMapping(value = "/requestPassword", method = RequestMethod.GET)
	public ModelAndView request(@ModelAttribute("login") UserVO userVO,
			HttpServletRequest ht) {

		logger.info("Rendering page for request new password");

		return new ModelAndView("login/request_password");

	}

	// Add the new password request

	@RequestMapping(value = "/requestPassword", method = RequestMethod.POST)
	public ModelAndView requestPassword(@ModelAttribute("login") UserVO userVO,
			HttpServletRequest ht, HttpSession session, ModelMap model)
			throws InvalidUserIdException, DuplicateEntryException,
			DatabaseException, RequestNotAcceptedException,
			ErrorDescriptionNotFoundException, UpdateFailedException,
			UserDisabledException {

		logger.info("Processing the  request for password");

		ApplicationCache applicationCache = (ApplicationCache) session
				.getServletContext().getAttribute(
						ApplicationConstant.APPLICATION_CACHE);

		// Setting model and view for exception handler

		ModelAndView modelAndView = new ModelAndView("login/request_password",
				"login", userVO);
		ht.setAttribute("page", modelAndView);

		// Process the request submitted

		requestService.insertRequest(userVO);

		// Send the success message

		model.addAttribute("success", errorCodeUtil.getErrorDescription(
				applicationCache, ErrorCodeConstant.REQUEST_SENT));

		modelAndView = new ModelAndView("login/request_submitted", "login",
				userVO);
		return modelAndView;
	}

	// Exception handler for request new password

	@ExceptionHandler({ RequestNotAcceptedException.class,
			ErrorDescriptionNotFoundException.class,
			UserDisabledException.class, UpdateFailedException.class,
			DuplicateEntryException.class })
	public ModelAndView handleException1(Exception ex, HttpSession session,
			HttpServletRequest request) {
		logger.error("Exception has occured,Inside the method to handle the exception :"
				+ ex);

		ModelAndView page = (ModelAndView) request.getAttribute("page");
		page.getModelMap().addAttribute("error", ex.getMessage());
		return page;

	}

}