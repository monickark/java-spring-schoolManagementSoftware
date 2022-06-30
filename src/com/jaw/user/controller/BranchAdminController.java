package com.jaw.user.controller;

import java.io.IOException;
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
import org.springframework.web.servlet.view.RedirectView;

import com.jaw.common.business.CommonBusiness;
import com.jaw.common.constants.ApplicationConstant;
import com.jaw.common.constants.ErrorCodeConstant;
import com.jaw.common.constants.PropertyManagementConstant;
import com.jaw.common.dropdown.service.IDropDownListService;
import com.jaw.common.exceptions.DatabaseException;
import com.jaw.common.exceptions.DeleteFailedException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.FileNotFoundInDatabase;
import com.jaw.common.exceptions.FileNotSaveException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.UpdateFailedException;
import com.jaw.common.exceptions.UserNotCreatedException;
import com.jaw.common.exceptions.login.InvalidUserIdException;
import com.jaw.common.exceptions.util.PropertyNotFoundException;
import com.jaw.common.util.ErrorCodeUtil;
import com.jaw.common.util.PropertyManagementUtil;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.sessCache.SessionCache;
import com.jaw.mgmtUser.service.IMgmtUserDetailsService;
import com.jaw.user.service.IBranchAdminService;

//Institute Master Controller Class
@Controller
public class BranchAdminController {

	// Logging
	Logger logger = Logger.getLogger(BranchAdminController.class);

	@Autowired
	IDropDownListService dropDownListService;
	@Autowired
	IMgmtUserDetailsService managementDetailsService;
	@Autowired
	ErrorCodeUtil errorCodeUtil;
	@Autowired
	CommonBusiness commonBusiness;
	@Autowired
	IBranchAdminService branchAdminService;
	@Autowired
	PropertyManagementUtil propertyManagementUtil;

	// method to view management.jsp
	@RequestMapping(value = "/branchadmin", method = RequestMethod.GET)
	public ModelAndView branchAdminGet(
			@ModelAttribute("branchadmin") BranchAdminVO branchAdminVO,
			HttpSession session, HttpServletRequest httpServletRequest,
			RedirectView redirect, ModelMap model)
			throws PropertyNotFoundException {

		ModelAndView mav = new ModelAndView(".jaw.branchadmin", "branchadmin",
				branchAdminVO);
		httpServletRequest.setAttribute("page", mav);
		logger.info("Opening Branch Admin Page");

		ApplicationCache applicationCache = (ApplicationCache) session
				.getServletContext().getAttribute(
						ApplicationConstant.APPLICATION_CACHE);
		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		branchAdminVO.setIsSingleBranch(propertyManagementUtil
				.getPropertyValue(applicationCache, sessionCache
						.getUserSessionDetails().getInstId(), sessionCache
						.getUserSessionDetails().getBranchId(),
						PropertyManagementConstant.INST_SINGLE_BRANCH));
		System.out.println("Is single branch get:"
				+ branchAdminVO.getIsSingleBranch());
		return mav;
	}

	@ModelAttribute("branchList")
	public Map<String, String> gerBranchList(HttpSession session,
			HttpServletRequest httpSevletRequest, HttpServletResponse response,
			ModelMap model) throws IOException, NoDataFoundException {
		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		Map<String, String> map = dropDownListService.getBranchListTag(sessionCache.getUserSessionDetails());
		httpSevletRequest.setAttribute("branchList", map);
		return map;

	}

	// method to Update Management
	@RequestMapping(value = "/branchadmin", method = RequestMethod.GET, params = { "Get" })
	public ModelAndView branchAdminGetDet(
			@ModelAttribute("branchadmin") BranchAdminVO branchAdminVO,
			HttpSession session, HttpServletRequest httpServletRequest)
			throws NoDataFoundException, UserNotCreatedException {
		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		ModelAndView mav = new ModelAndView(".jaw.branchadmin", "branchadmin",
				branchAdminVO);
		httpServletRequest.setAttribute("page", mav);

		branchAdminService.selectStaffDetails(branchAdminVO,
				sessionCache.getUserSessionDetails());
		System.out.println("Is single branch param:"
				+ branchAdminVO.getIsSingleBranch());
		return new ModelAndView(".jaw.branchadmin");

	}

	// method to Update Management
	@RequestMapping(value = "/branchadmin", method = RequestMethod.POST)
	public String branchAdminPost(
			@ModelAttribute("branchadmin") BranchAdminVO branchAdminVO,
			HttpSession session, HttpServletRequest httpServletRequest,
			ModelMap map, RedirectAttributes redirectAttributes)
			throws FileNotFoundInDatabase, DuplicateEntryException,
			InvalidUserIdException, UpdateFailedException, DatabaseException,
			NumberFormatException, PropertyNotFoundException, IllegalStateException, IOException, DeleteFailedException, FileNotSaveException {
		ApplicationCache applicationCache = (ApplicationCache) session
				.getServletContext().getAttribute(
						ApplicationConstant.APPLICATION_CACHE);
		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		ModelAndView mav = new ModelAndView(".jaw.branchadmin", "branchadmin",
				branchAdminVO);
		httpServletRequest.setAttribute("page", mav);
		branchAdminService.insertBranchAdmin(branchAdminVO,
				sessionCache.getUserSessionDetails(), applicationCache,session.getServletContext());
		redirectAttributes.addFlashAttribute("success",
				ErrorCodeConstant.ADD_SUCCESS_MESS);
		System.out.println("Is single branch redirect:"
				+ branchAdminVO.getIsSingleBranch());
		return "redirect:/branchadmin.htm";

	}

	@ExceptionHandler({ DuplicateEntryException.class,
			UpdateFailedException.class, NoDataFoundException.class,
			UserNotCreatedException.class, PropertyNotFoundException.class,DeleteFailedException.class,FileNotSaveException.class })
	public ModelAndView handleException(Exception ex, HttpSession session,
			HttpServletRequest request) {
		ModelAndView modelAndView = (ModelAndView) request.getAttribute("page");
		modelAndView.getModelMap().addAttribute("error", ex.getMessage());
		Map<String, String> map = (Map<String, String>) request
				.getAttribute("branchList");
		logger.debug("Request object : Model attribute :" + map);
		modelAndView.getModelMap().addAttribute("branchList", map);
		return modelAndView;

	}
}
