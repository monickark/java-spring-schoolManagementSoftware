package com.jaw.mgmtUser.controller;

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
import com.jaw.mgmtUser.service.IMgmtUserModifyService;
import com.jaw.prodAdm.service.ICommonCodeService;
import com.jaw.user.controller.UserManagementVO;

@Controller
public class MgmtUserModifyController {

	@Autowired
	ICommonCodeService commonCodeService;
	@Autowired
	CommonBusiness commonBusiness;
	@Autowired
	ErrorCodeUtil errorCodeUtil;
	@Autowired
	IMgmtUserModifyService managementDetailsService;
	String error = "";

	@RequestMapping(value = "/modifyUser", method = RequestMethod.POST, params = { "update" })
	public String userManagementUpdate(HttpServletRequest httpServletRequest,
			HttpSession session, ModelMap map,
			@ModelAttribute("singleUser") MgmtUserVO userManagementVO,
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
		managementDetailsService.updateManagementDetails(applicationCache,
				userManagementVO, sessionCache.getUserSessionDetails(),session.getServletContext());
		map.addAttribute("success", ErrorCodeConstant.UPDATE_SUCCESS_MESS);

		redirectAttributes.addFlashAttribute("success",
				ErrorCodeConstant.UPDATE_SUCCESS_MESS);
		return "redirect:userManagement.htm?Search=Get";

	}

	@RequestMapping(value = "/modifyUser", method = RequestMethod.POST, params = { "delete" })
	public String userManagementDelete(HttpServletRequest httpServletRequest,
			HttpSession session, ModelMap map,
			@ModelAttribute("singleUser") MgmtUserVO managementVO,
			RedirectAttributes redirectAttributes)
			throws InvalidUserIdException, DeleteFailedException,
			NoDataFoundException {
		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		UserManagementVO management = (UserManagementVO) WebUtils
				.getSessionAttribute(httpServletRequest, "searchvo");
		ModelAndView modelAndView = new ModelAndView(".jaw.userManagement",
				"userManagement", management);
		httpServletRequest.setAttribute("page", modelAndView);
		managementDetailsService.deleteManagementDetails(managementVO,
				sessionCache.getUserSessionDetails());

		redirectAttributes.addFlashAttribute("success",
				ErrorCodeConstant.DELETE_SUCCESS_MESS);
		return "redirect:userManagement.htm?Search=Get";

	}

	@RequestMapping(value = "/modifyUser", method = RequestMethod.POST, params = { "reset" })
	public ModelAndView userManagementReset(
			HttpServletRequest httpServletRequest, HttpSession session,
			ModelMap map,
			@ModelAttribute("singleUser") MgmtUserVO managementVO)
			throws DuplicateEntryException, InvalidUserIdException,
			NoDataFoundException, FileNotFoundInDatabase {
		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		UserManagementVO management = (UserManagementVO) WebUtils
				.getSessionAttribute(httpServletRequest, "searchvo");
		ModelAndView modelAndView = new ModelAndView(".jaw.userManagement",
				"userManagement", management);
		httpServletRequest.setAttribute("page", modelAndView);
		managementDetailsService.selectManagement(managementVO,
				sessionCache.getUserSessionDetails());
		return new ModelAndView(".jaw.managementModify");

	}

	@RequestMapping(value = "/edituser", method = RequestMethod.POST, params = { "updateEP" })
	public ModelAndView editUser(HttpServletRequest httpServletRequest,
			HttpSession session, ModelMap map,
			@ModelAttribute("singleUser") MgmtUserVO userManagementVO,
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
		ModelAndView modelAndView = new ModelAndView(".jaw.managementEdit",
				"singleUser", userManagementVO);
		httpServletRequest.setAttribute("page", modelAndView);
		managementDetailsService.updateManagementDetails(applicationCache,
				userManagementVO, sessionCache.getUserSessionDetails(),session.getServletContext());
		map.addAttribute("success", ErrorCodeConstant.UPDATE_SUCCESS_MESS);

		redirectAttributes.addFlashAttribute("success",
				ErrorCodeConstant.UPDATE_SUCCESS_MESS);
		return modelAndView;

	}

	@RequestMapping(value = "/edituser", method = RequestMethod.POST, params = { "resetEP" })
	public ModelAndView modifyUserReset(HttpServletRequest httpServletRequest,
			HttpSession session, ModelMap map,
			@ModelAttribute("singleUser") MgmtUserVO managementVO)
			throws DuplicateEntryException, InvalidUserIdException,
			NoDataFoundException, FileNotFoundInDatabase {
		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		ModelAndView modelAndView = new ModelAndView(".jaw.managementEdit",
				"singleUser", managementVO);
		httpServletRequest.setAttribute("page", modelAndView);
		managementDetailsService.selectManagement(managementVO,
				sessionCache.getUserSessionDetails());
		return modelAndView;

	}

	@ExceptionHandler({ DuplicateEntryException.class,
			UpdateFailedException.class, BatchUpdateFailedException.class,
			NoDataFoundException.class, InvalidUserIdException.class,
			FileNotFoundInDatabase.class, DeleteFailedException.class,
			TableNotSpecifiedForAuditException.class,DeleteFailedException.class,FileNotSaveException.class })
	public ModelAndView handleException(Exception ex, HttpSession session,
			HttpServletRequest request, ModelMap modelMap) {
		ModelAndView modelAndView = (ModelAndView) request.getAttribute("page");
		modelAndView.getModelMap().addAttribute("error", ex.getMessage());
		return modelAndView;

	}
}
