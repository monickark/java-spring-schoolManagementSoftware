package com.jaw.user.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import com.jaw.common.business.CommonBusiness;
import com.jaw.common.constants.ApplicationConstant;
import com.jaw.common.constants.ErrorCodeConstant;
import com.jaw.common.exceptions.BatchUpdateFailedException;
import com.jaw.common.exceptions.DatabaseException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.UpdateFailedException;
import com.jaw.common.exceptions.login.InvalidUserIdException;
import com.jaw.common.exceptions.util.PropertyNotFoundException;
import com.jaw.common.util.ErrorCodeUtil;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.sessCache.SessionCache;
import com.jaw.prodAdm.service.ICommonCodeService;
import com.jaw.user.service.IUserManagementService;

@Controller
public class UserManagementController {
	Logger logger = Logger.getLogger(UserManagementController.class);
	@Autowired
	ICommonCodeService commonCodeService;
	@Autowired
	CommonBusiness commonBusiness;
	@Autowired
	ErrorCodeUtil errorCodeUtil;
	@Autowired
	IUserManagementService userManagementService;
	String error = "";
	
	// this method id used to display the userManagement.jsp page
	
	@RequestMapping(value = "/userManagement", method = RequestMethod.GET)
	public ModelAndView cocdGet(
			@ModelAttribute("userManagement") UserManagementVO userManagement,
			HttpServletRequest httpServletRequest, HttpSession session,
			ModelMap modelMap) throws NoDataFoundException {
		WebUtils.setSessionAttribute(httpServletRequest, "display_tbl", null);
		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		List<String> menuProfileList = null;
		menuProfileList = userManagementService.getMenuProfileList(sessionCache
				.getUserSessionDetails());
		modelMap.addAttribute("menu", menuProfileList);
		logger.debug("Menu profile list size :" + menuProfileList.size());
		session.setAttribute("userManagement", userManagement);
		return new ModelAndView(".jaw.userManagement");
	}
	
	// This method will call when a submit action is triggered in
	// userManagement.jsp page
	
	@RequestMapping(value = "/userManagement", method = RequestMethod.GET, params = {
			"Search=Get", "!Details"
	})
	public ModelAndView cocdPost(
			@Valid @ModelAttribute("userManagement") UserManagementVO userManagement,
			HttpServletRequest httpServletRequest, HttpSession session,
			ModelMap map) throws NoDataFoundException {
		Map stockParamMap = WebUtils.getParametersStartingWith(
				httpServletRequest, "d-");
		ApplicationCache applicationCache = (ApplicationCache) session
				.getServletContext().getAttribute(
						ApplicationConstant.APPLICATION_CACHE);
		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		ModelAndView modelAndView = new ModelAndView(".jaw.userManagement",
				"userManagement", userManagement);
		
		httpServletRequest.setAttribute("page", modelAndView);
		if (stockParamMap.size() == 0) {
			List<UserManagementVO> userManagementVOs = null;
			
			userManagementVOs = userManagementService
					.getUserDetailsForUserManagement(applicationCache,
							sessionCache, sessionCache.getUserSessionDetails()
									.getBranchId(), sessionCache
									.getUserSessionDetails().getInstId(),
							userManagement, "userManagement.htm");
			WebUtils.setSessionAttribute(httpServletRequest, "display_tbl",
					userManagementVOs);
			WebUtils.setSessionAttribute(httpServletRequest, "searchvo",
					userManagement);
			//userManagement.setPageNo("P1");
			//userManagement.setPageNo(userManagement.getPageNo());
		}
		
		ModelAndView ma = new ModelAndView(".jaw.userManagement",
				"userManagement", userManagement);
		httpServletRequest.setAttribute("page", ma);
		List<String> menuProfileList = null;
		menuProfileList = userManagementService.getMenuProfileList(sessionCache
				.getUserSessionDetails());
		map.addAttribute("menu", menuProfileList);
		return modelAndView;
		
	}
	
	@RequestMapping(value = "/userManagement", method = RequestMethod.GET, params = "Update=Update")
	public ModelAndView userManagementUpdate(
			HttpServletRequest httpServletRequest, HttpSession session,
			ModelMap map,
			@ModelAttribute("userManagement") UserManagementVO userManagement)
			throws DuplicateEntryException, InvalidUserIdException,
			UpdateFailedException, DatabaseException, NumberFormatException,
			PropertyNotFoundException {
		ApplicationCache applicationCache = (ApplicationCache) session
				.getServletContext().getAttribute(
						ApplicationConstant.APPLICATION_CACHE);
		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		String rowId = (httpServletRequest.getParameter("rowId"));
		System.out.println("Row id :"+rowId);
		String remarks = (httpServletRequest.getParameter("remark"));
		List<UserManagementVO> userManagementVOs = (List<UserManagementVO>) WebUtils
				.getSessionAttribute(httpServletRequest, "display_tbl");
		System.out.println("User management :"+userManagementVOs);
		UserManagementVO managementVO = userManagementVOs.get(Integer
				.parseInt(rowId));
		managementVO.setRemarks(remarks);
		userManagementService.userEnableOrDisable(managementVO,
				sessionCache.getUserSessionDetails(), applicationCache);
		
		map.addAttribute("success", ErrorCodeConstant.UPDATE_SUCCESS_MESS);
		return new ModelAndView(".jaw.userManagement");
		
	}
	
	@ExceptionHandler({
			DuplicateEntryException.class,
			BatchUpdateFailedException.class, NoDataFoundException.class,
			PropertyNotFoundException.class
	})
	public ModelAndView handleException(Exception ex, HttpSession session,
			HttpServletRequest request) {
		WebUtils.setSessionAttribute(request, "request", null);
		ModelAndView page = (ModelAndView) request.getAttribute("page");
		logger.debug("Page is :" + page.getViewName());
		String type = "error";
		String message = ex.getMessage();
		page.getModelMap().addAttribute("message", message)
				.addAttribute("type", type);
		return page;
		
	}
}
