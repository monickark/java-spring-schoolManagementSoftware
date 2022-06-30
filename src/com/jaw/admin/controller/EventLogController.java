package com.jaw.admin.controller;

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
import org.springframework.web.util.WebUtils;

import com.jaw.admin.service.IEventLogService;
import com.jaw.common.constants.ApplicationConstant;
import com.jaw.common.dropdown.service.IDropDownListService;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.batch.NoRecordFoundException;
import com.jaw.framework.sessCache.SessionCache;

@Controller
public class EventLogController {
	Logger logger = Logger.getLogger(EventLogController.class);
	@Autowired
	IEventLogService eventLogService;
	@Autowired
	IDropDownListService dropDownListService;

	@RequestMapping(value = "/eventLog", method = RequestMethod.GET)
	public ModelAndView getEventLogPage(
			@ModelAttribute("eventLog") EventLogSearchVO eventLogSearchVO,
			HttpSession session, HttpServletRequest request)
			throws NoDataFoundException {
		logger.info("Rendering Event Log List page");
		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		List<EventLogListVO> eventLogList = null;
		request.setAttribute("eventLog", eventLogSearchVO);
		if ((request.getParameter("Search") != null)
				&& request.getParameter("Search").equals("Get")) {
			logger.info("Search Initiated,Get has been clicked");
			WebUtils.setSessionAttribute(request, "display_tbl", null);
			eventLogList = eventLogService.selectListOfAuditEventLogRecords(
					eventLogSearchVO, sessionCache.getUserSessionDetails());
			WebUtils.setSessionAttribute(request, "display_tbl", eventLogList);
			Map stockParamMap = WebUtils.getParametersStartingWith(request,
					"d-");
			if (stockParamMap.size() == 0) {
				logger.info("Table operation has been triggered");
				eventLogList = eventLogService
						.selectListOfAuditEventLogRecords(eventLogSearchVO,
								sessionCache.getUserSessionDetails());
				WebUtils.setSessionAttribute(request, "display_tbl",
						eventLogList);

			}
		} else {
			WebUtils.setSessionAttribute(request, "display_tbl", null);
		}
		ModelAndView mav = new ModelAndView(".jaw.eventLog");
		return mav;

	}

	// method to extract the list which is already in session
	public List<EventLogListVO> extracted(HttpServletRequest request) {
		logger.info("Going to get the Searched List from session");
		List<EventLogListVO> sessionAttribute = (List<EventLogListVO>) WebUtils
				.getSessionAttribute(request, "display_tbl");
		return sessionAttribute;
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

	@ExceptionHandler({ NoRecordFoundException.class,
			NoDataFoundException.class })
	public ModelAndView handleException(Exception ex, HttpSession session,
			HttpServletRequest request) {
		logger.info("Exception has occured,Inside the method to handle the exception");
		EventLogSearchVO eventLogSearchVO = new EventLogSearchVO();
		eventLogSearchVO = (EventLogSearchVO) request.getAttribute("eventLog");
		ModelAndView mav = new ModelAndView(".jaw.eventLog", "eventLog",
				eventLogSearchVO);
		mav.getModelMap().addAttribute("error", ex.getMessage());
		return mav;

	}

}
