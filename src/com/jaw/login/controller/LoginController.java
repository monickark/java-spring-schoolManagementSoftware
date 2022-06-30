package com.jaw.login.controller;

import java.io.FileNotFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.web.util.WebUtils;

import com.jaw.common.business.CommonBusiness;
import com.jaw.common.constants.ApplicationConstant;
import com.jaw.common.constants.ErrorCodeConstant;
import com.jaw.common.exceptions.DatabaseException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.DuplicatePasswordException;
import com.jaw.common.exceptions.FileNotFoundInDatabase;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.PasswordMismatchException;
import com.jaw.common.exceptions.PasswordrequestedLoginFailException;
import com.jaw.common.exceptions.UpdateFailedException;
import com.jaw.common.exceptions.login.InvalidAttemptException;
import com.jaw.common.exceptions.login.InvalidUserIdException;
import com.jaw.common.exceptions.login.PasswordExpiredException;
import com.jaw.common.exceptions.login.PasswordNotResetException;
import com.jaw.common.exceptions.login.SessionCacheNotLoadedException;
import com.jaw.common.exceptions.login.UserDisabledException;
import com.jaw.common.exceptions.login.WrongPasswordException;
import com.jaw.common.exceptions.util.PropertyNotFoundException;
import com.jaw.common.util.CommonCodeUtil;
import com.jaw.common.util.ErrorCodeUtil;
import com.jaw.common.util.PropertyManagementUtil;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.sessCache.SessionCache;
import com.jaw.login.service.ILoginService;
import com.jaw.user.controller.UserVO;

@Controller
public class LoginController {
	
	Logger logger = Logger.getLogger(LoginController.class);
	@Autowired
	private ILoginService loginService;
	@Autowired
	CommonBusiness commonBusiness;
	@Autowired
	ErrorCodeUtil errorCodeUtil;
	@Autowired
	CommonCodeUtil commonCodeUtil;
	@Autowired
	PropertyManagementUtil propertyManagementUtil;
	
	// To show login page
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView student(@ModelAttribute("login") UserVO login,
			HttpServletRequest ht) {
		logger.debug("Opening Home page");
		return new ModelAndView("login/login");
	}
	
	// To check user login
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(@Valid @ModelAttribute("login") UserVO user,
			BindingResult result, HttpSession session, ModelMap model,
			HttpServletRequest request) throws FileNotFoundException,
			FileNotFoundInDatabase, InvalidUserIdException, DatabaseException, NoDataFoundException {
		String returnPage = null;
		logger.debug("Performs Login validation.");
		System.out.println("inside login");
		ApplicationCache applicationCache = (ApplicationCache) session
				.getServletContext().getAttribute(
						ApplicationConstant.APPLICATION_CACHE);
		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		user.setIpAddress(request.getRemoteAddr());
		if (sessionCache == null) {
			sessionCache = new SessionCache();
			session.setAttribute(ApplicationConstant.SESSION_CACHE_KEY,
					sessionCache);
		}
		else {
			sessionCache = (SessionCache) session
					.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		}
		
		logger.debug("Getting session object and application object");
		try {
			// set Session id
			logger.debug("Setting session Id for User object");
			user.setSessionId(session.getId());
			// check user is valid or not
			logger.debug("check user is valid or not");
			
			loginService.loginUser(user, applicationCache, sessionCache);
			
			logger.debug("Login success, Redirect to dashboard page");
			logger.debug("Logged user is [Inst Id :"
					+ sessionCache.getUserSessionDetails().getInstId()
					+ " | Branch Id :"
					+ sessionCache.getUserSessionDetails().getBranchId()
					+ " | User Id :"
					+ sessionCache.getUserSessionDetails().getUserId()
					+ " | Session Id :"
					+ sessionCache.getUserSessionDetails().getSessionId());
			returnPage = "redirect:/welcome.htm";
			
		}
		catch (InvalidUserIdException e) {
			
			logger.error("Username and password provided are incorrect ,Redirecting to login page");
			model.addAttribute("error",
					ErrorCodeConstant.INVALID_USERNAME_PASSWORD);
			user.setPassword("");
			returnPage = "login/login";
			
		}
		catch (PasswordExpiredException e) {
			
			logger.error("Provided Password expired ,Redirecting to change password page");
			model.addAttribute("error", ErrorCodeConstant.PASSWORD_EXPIRED);
			user.setPassword("");
			returnPage = "login/force_change_password";
			
		}
		catch (PasswordNotResetException e) {
			
			logger.error("Password need to reset ,Redirecting to change password page");
			user.setPassword("");
			returnPage = "login/force_change_password";
			
		}
		catch (InvalidAttemptException e) {
			
			logger.error("User exceed maximum number of invalid attempts , Password  disabled ,Redirecting to login page");
			model.addAttribute("error",
					ErrorCodeConstant.EXCEEDED_MAXIMUM_NUMBER_OF_ATTEMPTS);
			user.setPassword("");
			returnPage = "login/login";
			
		}
		catch (PasswordMismatchException e) {
			logger.error(" password provided is incorrect ,Redirecting to login page");
			model.addAttribute("error",
					ErrorCodeConstant.INVALID_USERNAME_PASSWORD);
			user.setPassword("");
			returnPage = "login/login";
			
		}
		catch (DuplicateEntryException e) {
			logger.error("User has been already Logged in,Redirecting to login page");
			model.addAttribute("error",
					ErrorCodeConstant.SESSION_NOT_INVALIDATE);
			user.setPassword("");
			returnPage = "login/login";
			
		}
		catch (UserDisabledException e) {
			logger.error(" password disabled,Redirecting to login page");
			model.addAttribute("error", ErrorCodeConstant.USER_DISABLED);
			user.setPassword("");
			returnPage = "login/login";
		}
		catch (SessionCacheNotLoadedException e) {
			logger.error(" Errro in session cache loading");
			model.addAttribute("error", ErrorCodeConstant.SESSION_CACHE_FAILED);
			user.setPassword("");
			returnPage = "login/login";
		}
		catch (PasswordrequestedLoginFailException e) {
			logger.error(" Errro in session cache loading");
			model.addAttribute("error",
					ErrorCodeConstant.PASSWORD_REQUESTED_LOGIN_FAILS);
			user.setPassword("");
			returnPage = "login/login";
		}
		catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (PropertyNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (UpdateFailedException e) {
			model.addAttribute("error", ErrorCodeConstant.UPDATE_FAILED);
			user.setPassword("");
			returnPage = "login/login";
		}
		catch (NoDataFoundException e) {
			model.addAttribute("error", ErrorCodeConstant.SESSION_CACHE_FAILED);
			user.setPassword("");
			returnPage = "login/login";
		}
		catch (Exception e) {
			logger.error(" Errro in session cache loading");
			model.addAttribute("error", ErrorCodeConstant.SESSION_CACHE_FAILED);
			sessionCache.setUserSessionDetails(null);
			sessionCache.setStudentSession(null);
			sessionCache.setStaffSession(null);
			sessionCache.setParentSession(null);
			sessionCache.setNonStaffSession(null);
			sessionCache.setManagementSession(null);
			user.setPassword("");
			returnPage = "login/login";
		}
		return returnPage;
	}
	
	@RequestMapping(value = "/changePassword", method = RequestMethod.GET)
	public ModelAndView changePassword(@ModelAttribute("login") UserVO user) {
		logger.info("Showing Change Password page");
		return new ModelAndView(".jaw.changePassword");
	}
	
	@RequestMapping(value = "/forceChangePassword", method = RequestMethod.GET)
	public ModelAndView forceChangePassword(@ModelAttribute("login") UserVO user) {
		logger.info("Showing Change Password page");
		return new ModelAndView("login/force_change_password");
	}
	
	@RequestMapping(value = "/forceChangePassword", method = RequestMethod.POST)
	public String forcechangePasswordPost(@ModelAttribute("login") UserVO user,
			HttpSession session, ModelMap model, HttpServletRequest request)
			throws InvalidUserIdException, DatabaseException, PropertyNotFoundException {
		String returnPage = null;
		ApplicationCache applicationCache = (ApplicationCache) session
				.getServletContext().getAttribute(
						ApplicationConstant.APPLICATION_CACHE);
		user.setIpAddress(request.getRemoteAddr());
		try {
			
			loginService.updatePassword(user, applicationCache);
			user.setPassword(user.getChangePassword());
			model.addAttribute("success",
					ErrorCodeConstant.PASSWORD_CHANGE_SUCCESS);
			returnPage = "login/force_change_password";
			
		}
		catch (PasswordMismatchException e) {
			logger.error("New password and retype password are not matched");
			model.addAttribute("error",
					ErrorCodeConstant.RETYPE_PASSWORD_NOT_MATCH);
			returnPage = "login/force_change_password";
		}
		catch (WrongPasswordException e) {
			logger.error("Old password given for the username is not matching with db already exist");
			model.addAttribute("error", ErrorCodeConstant.PASSWORD_NOT_MATCH);
			returnPage = "login/force_change_password";
		}
		catch (DuplicatePasswordException e) {
			logger.error("Old password given for the username is not matching with db already exist");
			model.addAttribute("error", ErrorCodeConstant.DUPLICATE_PASSWORD);
			returnPage = "login/force_change_password";
		}
		catch (DuplicateEntryException e) {
			model.addAttribute("error", ErrorCodeConstant.DUPLICATE_ENTRY);
			logger.error("No data found");
			returnPage = "login/force_change_password";
			
		}
		catch (UpdateFailedException e) {
			model.addAttribute("error", ErrorCodeConstant.UPDATE_FAILED);
			user.setPassword("");
			returnPage = "login/force_change_password";
		}
		return returnPage;
	}
	
	@RequestMapping(value = "/changePassword", method = RequestMethod.POST)
	public String changePasswordPost(@ModelAttribute("login") UserVO user,
			HttpSession session, ModelMap model,
			final RedirectAttributes redirectAttributes)
			throws InvalidUserIdException, DatabaseException, PropertyNotFoundException {
		String returnPage = null;
		ApplicationCache applicationCache = (ApplicationCache) session
				.getServletContext().getAttribute(
						ApplicationConstant.APPLICATION_CACHE);
		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		try {
			logger.debug("Getting session object and application object");
			logger.debug("Setting session cache username with browser input");
			user.setUserId(sessionCache.getUserSessionDetails().getUserId());
			user.setBranchId(sessionCache.getUserSessionDetails().getBranchId());
			user.setInstId(sessionCache.getUserSessionDetails().getInstId());
			logger.debug("Checking old password exist in db for the same user");
			loginService.updatePassword(user, applicationCache);
			user.setPassword(user.getChangePassword());
			redirectAttributes.addFlashAttribute("success",
					ErrorCodeConstant.PASSWORD_CHANGE_SUCCESS);
			returnPage = "redirect:/changePassword.htm";
		}
		catch (PasswordMismatchException e) {
			logger.error("New password and retype password are not matched");
			model.addAttribute("error",
					ErrorCodeConstant.RETYPE_PASSWORD_NOT_MATCH);
			returnPage = ".jaw.changePassword";
			
		}
		catch (WrongPasswordException e) {
			logger.error("Old password given for the username is not matching with db already exist");
			model.addAttribute("error", ErrorCodeConstant.PASSWORD_NOT_MATCH);
			returnPage = ".jaw.changePassword";
		}
		catch (DuplicatePasswordException e) {
			logger.error("Old password given for the username is not matching with db already exist");
			model.addAttribute("error", ErrorCodeConstant.DUPLICATE_PASSWORD);
			returnPage = ".jaw.changePassword";
		}
		catch (DuplicateEntryException e) {
			model.addAttribute("error", ErrorCodeConstant.DUPLICATE_ENTRY);
			logger.error("No data found");
			returnPage = "login/forceChangePassword";
			
		}
		catch (UpdateFailedException e) {
			model.addAttribute("error", ErrorCodeConstant.UPDATE_FAILED);
			user.setPassword("");
			returnPage = "login/forceChangePassword";
		}
		return returnPage;
	}
	
	// Logout function
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public ModelAndView logout(@ModelAttribute("login") UserVO uservo,
			HttpSession httpSession, HttpServletRequest httpServletRequest)
			throws InvalidUserIdException, DatabaseException {
		SessionCache sessionScope = (SessionCache) httpSession
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		
		ModelAndView mav = new ModelAndView(".jaw.dashboard");
		RedirectView view = new RedirectView();
		view.setUrl(".jaw.dashboard");
		
		try {
			loginService.logout(sessionScope.getUserSessionDetails());
			httpServletRequest.setAttribute("branch", sessionScope
					.getUserSessionDetails().getBranchId());
			httpServletRequest.setAttribute("inst", sessionScope
					.getUserSessionDetails().getInstId());
			sessionScope.setUserSessionDetails(null);
			sessionScope.setStudentSession(null);
			sessionScope.setStaffSession(null);
			sessionScope.setParentSession(null);
			sessionScope.setNonStaffSession(null);
			sessionScope.setManagementSession(null);
			WebUtils.setSessionAttribute(httpServletRequest, "display_tbl", null);
			WebUtils.setSessionAttribute(httpServletRequest, "success", null);
			WebUtils.setSessionAttribute(httpServletRequest, "keepStatus", null);
			WebUtils.setSessionAttribute(httpServletRequest, "searchVo", null);
			httpSession.setAttribute(ApplicationConstant.SESSION_CACHE_KEY,
					null);
		}
		catch (DuplicateEntryException e) {
			mav.getModelMap().addAttribute("error",
					ErrorCodeConstant.DUPLICATE_ENTRY);
			logger.error("Failed in Auditing :No data found");
			return mav;
			
		}
		catch (UpdateFailedException e) {
			mav.getModelMap().addAttribute("error",
					ErrorCodeConstant.UPDATE_FAILED);
		}
		
		logger.info("Logout completed.");
		return new ModelAndView("login/logout");
	}
	
}
