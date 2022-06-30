package com.jaw.nonStaff.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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

import com.jaw.common.business.CommonBusiness;
import com.jaw.common.constants.ApplicationConstant;
import com.jaw.common.constants.ErrorCodeConstant;
import com.jaw.common.exceptions.BatchUpdateFailedException;
import com.jaw.common.exceptions.DatabaseException;
import com.jaw.common.exceptions.DeleteFailedException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.FileNotFoundInDatabase;
import com.jaw.common.exceptions.FileNotSaveException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.UpdateFailedException;
import com.jaw.common.exceptions.login.InvalidUserIdException;
import com.jaw.common.exceptions.util.TableNotSpecifiedForAuditException;
import com.jaw.common.util.ErrorCodeUtil;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.sessCache.SessionCache;
import com.jaw.nonStaff.service.INonStaffModifyService;
import com.jaw.prodAdm.service.ICommonCodeService;
import com.jaw.user.controller.BranchAdminVO;
import com.jaw.user.controller.UserManagementVO;

@Controller
public class NonStaffModifyController {

	@Autowired
	ICommonCodeService commonCodeService;
	@Autowired
	CommonBusiness commonBusiness;
	@Autowired
	ErrorCodeUtil errorCodeUtil;
	@Autowired
	INonStaffModifyService nonStaffDetailsService;
	String error = "";

	@RequestMapping(value = "/modifyUser", method = RequestMethod.POST, params = { "updateNonStaff" })
	public String userNonStaffUpdate(HttpServletRequest httpServletRequest,
			HttpSession session, ModelMap map,
			@ModelAttribute("singleUser") BranchAdminVO userBranchAdminVO,
			RedirectAttributes redirectAttributes)
			throws InvalidUserIdException, UpdateFailedException,
			NoDataFoundException, DuplicateEntryException,
			FileNotFoundInDatabase, DatabaseException,
			TableNotSpecifiedForAuditException, IllegalStateException, IOException, DeleteFailedException, FileNotSaveException {

		ApplicationCache applicationCache = (ApplicationCache) session
				.getServletContext().getAttribute(
						ApplicationConstant.APPLICATION_CACHE);
		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		UserManagementVO managementVO = (UserManagementVO) WebUtils
				.getSessionAttribute(httpServletRequest, "searchvo");
		ModelAndView modelAndView = new ModelAndView(".jaw.userManagement",
				"userManagement", managementVO);
		httpServletRequest.setAttribute("page", modelAndView);
		nonStaffDetailsService.updateNonStaffDetails(applicationCache,
				userBranchAdminVO, sessionCache.getUserSessionDetails(),session.getServletContext());
		map.addAttribute("success", ErrorCodeConstant.UPDATE_SUCCESS_MESS);
		redirectAttributes.addFlashAttribute("success",
				ErrorCodeConstant.UPDATE_SUCCESS_MESS);
		return "redirect:userManagement.htm?Search=Get";

	}

	@RequestMapping(value = "/modifyUser", method = RequestMethod.POST, params = { "resetNonStaff" })
	public ModelAndView userNonStaffReset(
			HttpServletRequest httpServletRequest, HttpSession session,
			ModelMap map,
			@ModelAttribute("singleUser") BranchAdminVO branchAdminVO)
			throws DuplicateEntryException, InvalidUserIdException,
			NoDataFoundException, FileNotFoundInDatabase {
		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		nonStaffDetailsService.selectNonStaff(branchAdminVO,
				sessionCache.getUserSessionDetails());
		return new ModelAndView(".jaw.NonStaffModify");

	}

	@RequestMapping(value = "/edituser", method = RequestMethod.POST, params = { "updateNonStaffEP" })
	public ModelAndView userNonStaffUpdateEP(
			HttpServletRequest httpServletRequest, HttpSession session,
			ModelMap map,
			@ModelAttribute("singleUser") BranchAdminVO userBranchAdminVO,
			RedirectAttributes redirectAttributes)
			throws InvalidUserIdException, UpdateFailedException,
			NoDataFoundException, DuplicateEntryException,
			FileNotFoundInDatabase, DatabaseException,
			TableNotSpecifiedForAuditException, IllegalStateException, IOException, DeleteFailedException, FileNotSaveException {

		ApplicationCache applicationCache = (ApplicationCache) session
				.getServletContext().getAttribute(
						ApplicationConstant.APPLICATION_CACHE);
		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		ModelAndView modelAndView = new ModelAndView(".jaw.nonStaffEdit",
				"singleUser", userBranchAdminVO);
		httpServletRequest.setAttribute("page", modelAndView);
		nonStaffDetailsService.updateNonStaffDetails(applicationCache,
				userBranchAdminVO, sessionCache.getUserSessionDetails(),session.getServletContext());
		map.addAttribute("success", ErrorCodeConstant.UPDATE_SUCCESS_MESS);
		redirectAttributes.addFlashAttribute("success",
				ErrorCodeConstant.UPDATE_SUCCESS_MESS);
		return modelAndView;

	}

	@RequestMapping(value = "/edituser", method = RequestMethod.POST, params = { "resetNonStaffEP" })
	public ModelAndView resetNonStaffEP(HttpServletRequest httpServletRequest,
			HttpSession session, ModelMap map,
			@ModelAttribute("singleUser") BranchAdminVO userBranchAdminVO,
			RedirectAttributes redirectAttributes)
			throws InvalidUserIdException, UpdateFailedException,
			NoDataFoundException, DuplicateEntryException,
			FileNotFoundInDatabase, DatabaseException {
		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		ModelAndView modelAndView = new ModelAndView(".jaw.nonStaffEdit",
				"singleUser", userBranchAdminVO);
		nonStaffDetailsService.selectNonStaff(userBranchAdminVO,
				sessionCache.getUserSessionDetails());
		return modelAndView;

	}

	@ExceptionHandler({ DuplicateEntryException.class,
			UpdateFailedException.class, BatchUpdateFailedException.class,
			NoDataFoundException.class, InvalidUserIdException.class,
			FileNotFoundInDatabase.class, DeleteFailedException.class,
			TableNotSpecifiedForAuditException.class,DeleteFailedException.class,FileNotSaveException.class })
	public ModelAndView handleException(Exception ex, HttpSession session,
			HttpServletRequest request) {
		ModelAndView modelAndView = (ModelAndView) request.getAttribute("page");
		modelAndView.getModelMap().addAttribute("error", ex.getMessage());
		return modelAndView;

	}
}
