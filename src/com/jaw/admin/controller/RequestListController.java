package com.jaw.admin.controller;

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

import com.jaw.admin.service.IRequestListService;
import com.jaw.common.constants.ApplicationConstant;
import com.jaw.common.constants.ErrorCodeConstant;
import com.jaw.common.exceptions.BatchUpdateFailedException;
import com.jaw.common.exceptions.DatabaseException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.NoRecordSelectedException;
import com.jaw.common.exceptions.login.InvalidUserIdException;
import com.jaw.common.util.ErrorCodeUtil;
import com.jaw.common.util.MenuProfileUtil;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.sessCache.SessionCache;
import com.jaw.user.service.IUserManagementService;

@Controller
public class RequestListController {
	Logger logger = Logger.getLogger(DataLogController.class);

	@Autowired
	private IRequestListService requestService;
	@Autowired
	private ErrorCodeUtil errorCodeUtil;
	@Autowired
	MenuProfileUtil menuProfileUtil;
	@Autowired
	IUserManagementService userManagementService;

	// Request List Get method

	@RequestMapping(value = "/requestList", method = RequestMethod.GET)
	public ModelAndView request(@ModelAttribute("request") RequestListVo requestVo,
			ModelMap model, HttpServletRequest httpRequest,
			HttpSession httpSession) {

		logger.info("Rendering Request List page");

		// Null the session display tag values

		WebUtils.setSessionAttribute(httpRequest, "display_tbl", null);
		return new ModelAndView(".jaw.request");

	}

	// Request List Search method

	@RequestMapping(value = "/requestList", method = RequestMethod.GET, params = { "Get" })
	public ModelAndView requestSearch(
			@ModelAttribute("request") RequestListVo requestVo, ModelMap model,
			HttpServletRequest httpRequest, HttpSession httpSession)
			throws NoDataFoundException {

		logger.info("Search Initiated,Get has been clicked");

		ApplicationCache applicationCache = (ApplicationCache) httpSession
				.getServletContext().getAttribute(
						ApplicationConstant.APPLICATION_CACHE);
		SessionCache sessionCache = (SessionCache) httpSession
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);

		// Getting the display tag parameter

		Map stockParamMap = WebUtils.getParametersStartingWith(httpRequest,
				"d-");
		//from Dashboard request list
		//String page=httpRequest.getParameter("page");
		List<RequestListVo> passwordRequestList;

		// Setting model and view for exception handler

		ModelAndView modelAndView = new ModelAndView(".jaw.request", "request",
				requestVo);
		httpRequest.setAttribute("page", modelAndView);

		// Check whether the list already get or have to fetch from data base
		// using the list size

		if (stockParamMap.size() == 0) {

			logger.info("Table operation has been triggered");

			// Get list from database

			passwordRequestList = requestService.selectAllRequestList(
					applicationCache, sessionCache, requestVo,
					sessionCache.getUserSessionDetails(), "requestList.htm");

			// Set the list to session to access in jsp

			WebUtils.setSessionAttribute(httpRequest, "display_tbl",
					passwordRequestList);
			requestVo.setPageNo(requestVo.getPageNo());

		}
		return new ModelAndView(".jaw.request");

	}

	// Back to the request list page from user details page

	@RequestMapping(value = "/requestSingleUserDetails", method = RequestMethod.GET, params = { "RequestBack" })
	public ModelAndView userManagementBack(
			@ModelAttribute("request") RequestListVo requestVo,
			HttpServletRequest httpServletRequest) {
		ModelAndView modelAndView = new ModelAndView(".jaw.request",
				"singleUser", requestVo);
		httpServletRequest.setAttribute("page", modelAndView);
		return modelAndView;

	}

	// Processing the pending request get method

	@RequestMapping(value = "/pendingRequestList", method = RequestMethod.GET)
	public ModelAndView requestGet(
			@ModelAttribute("request") RequestListVo requestVo, ModelMap model,
			HttpServletRequest httpRequest, HttpSession httpSession) {

		logger.info("Process request page rendering");

		// Null the session display tag values

		WebUtils.setSessionAttribute(httpRequest, "display_tbl", null);

		return new ModelAndView(".jaw.pendingRequest");
	}

	// Search method to get pending records

	@RequestMapping(value = "/pendingRequestList", method = RequestMethod.GET, params = { "Get" })
	public ModelAndView pendingRequestGet(
			@ModelAttribute("request") RequestListVo requestVo, ModelMap model,
			HttpServletRequest httpRequest, HttpSession httpSession)
			throws NoDataFoundException {

		logger.info("Process request search rendering to get pending records");
		ApplicationCache applicationCache = (ApplicationCache) httpSession
				.getServletContext().getAttribute(
						ApplicationConstant.APPLICATION_CACHE);
		SessionCache sessionCache = (SessionCache) httpSession
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);

		// Check whether the list already get or have to fetch from data base
		// using the list size

		List<RequestListVo> requestList;
		Map stockParamMap = WebUtils.getParametersStartingWith(httpRequest,
				"d-");

		// Setting model and view for exception handler

		ModelAndView modelAndView = new ModelAndView(".jaw.pendingRequest",
				"request", requestVo);
		httpRequest.setAttribute("page", modelAndView);

		// Check whether the list already get or have to fetch from data base
		// using the list size

		if (stockParamMap.size() == 0) {

			// Get pending request list from database

			requestList = requestService.selectPendingRequestRecords(requestVo,
					sessionCache.getUserSessionDetails(), applicationCache,
					"pendingRequestList.htm");

			// Set the list to session to access in jsp

			WebUtils.setSessionAttribute(httpRequest, "display_tbl",
					requestList);
			requestVo.setPageNo(requestVo.getPageNo());
		}

		return new ModelAndView(".jaw.pendingRequest");
	}

	// Method to process the pending request

	@RequestMapping(value = "/pendingRequestList", method = RequestMethod.GET, params = {
			"Get", "Process Request" })
	public ModelAndView pendingRequestaSearchPost(ModelMap model,
			@ModelAttribute("request") RequestListVo requestVo,
			HttpServletRequest httpRequest, HttpSession httpSession,
			RedirectAttributes redirectAttributes) throws NoDataFoundException,
			DuplicateEntryException, BatchUpdateFailedException,
			DatabaseException, NoRecordSelectedException {

		logger.info("Processing the  pending records");

		SessionCache sessionCache = (SessionCache) httpSession
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);

		List<RequestListVo> passwordRequestList = (List<RequestListVo>) WebUtils
				.getSessionAttribute(httpRequest, "display_tbl");

		// Get the selected row id to process the request from jsp using
		// variable '_chk' in array of variable selectedRowIds

		String[] selectedRowIds = httpRequest.getParameterValues("_chk");

		// Setting model and view for exception handler

		ModelAndView ma = new ModelAndView(".jaw.pendingRequest", "request",
				requestVo);
		httpRequest.setAttribute("page", ma);

		if (selectedRowIds != null) {

			// Process the selected request list row Id

			requestService.processRequest(requestVo,
					sessionCache.getUserSessionDetails(), passwordRequestList,
					selectedRowIds);

		} else {
			logger.error("No record selected to process");
			throw new NoRecordSelectedException();

		}

		// Redirecting to search method to get fresh data from database with
		// success message after completed successfully

		model.addAttribute("success",
				ErrorCodeConstant.REQUEST_PROCESSED_SUCCESS);
		WebUtils.setSessionAttribute(httpRequest, "display_tbl", null);

		return new ModelAndView(".jaw.pendingRequest");
	}

	// Exception handler for request list and process list

	@ExceptionHandler({ DuplicateEntryException.class,
			BatchUpdateFailedException.class, InvalidUserIdException.class,
			NoDataFoundException.class })
	public ModelAndView handleException(Exception ex, HttpSession session,
			HttpServletRequest request) {
		logger.error("Exception has occured,Inside the method to handle the exception :"
				+ ex);
		WebUtils.setSessionAttribute(request, "display_tbl", null);
		ModelAndView page = (ModelAndView) request.getAttribute("page");

		page.getModelMap().addAttribute("error", ex.getMessage());
		return page;

	}

}