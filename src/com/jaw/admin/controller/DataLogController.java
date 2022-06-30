package com.jaw.admin.controller;

import java.io.IOException;
import java.util.ArrayList;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.WebUtils;

import com.jaw.admin.service.IDataLogService;
import com.jaw.common.constants.ApplicationConstant;
import com.jaw.common.dropdown.service.IDropDownListService;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.sessCache.SessionCache;

@Controller
public class DataLogController {
	Logger logger = Logger.getLogger(DataLogController.class);
	@Autowired
	IDataLogService dataLogService;
	@Autowired
	IDropDownListService dropDownListService;


	@RequestMapping(value = "/dataLog", method = RequestMethod.GET)
	public ModelAndView getDataLogPage(
			@ModelAttribute("dataLog") DataLogSearchVO dataLogSearchVO,
			HttpServletRequest request, HttpSession session)
			throws NoDataFoundException {
		logger.info("Rendering Data Log List page");
		ModelAndView modelAndView = null;
		List<DataLogListVO> dataLogList = null;
		modelAndView = new ModelAndView(".jaw.dataLog");
		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		request.setAttribute("dataLogReq", dataLogSearchVO);
		if (dataLogSearchVO.getKeepstat() != "") {
			modelAndView.getModelMap().addAttribute("status", "false");
		} else {
			modelAndView.getModelMap().addAttribute("status", "true");
		}
		if ((request.getParameter("Search") != null)
				&& request.getParameter("Search").equals("Get")) {
			logger.info("Search Initiated,Get has been clicked");
			Map stockParamMap = WebUtils.getParametersStartingWith(request,
					"d-");
			if (stockParamMap.size() == 0) {

				logger.info("Table operation has been triggered");
				WebUtils.setSessionAttribute(request, "searchObj", null);
				WebUtils.setSessionAttribute(request, "searchObj",
						dataLogSearchVO);

				WebUtils.setSessionAttribute(request, "display_tbl", null);
				dataLogList = dataLogService.selectListOfAuditRecords(
						dataLogSearchVO, sessionCache.getUserSessionDetails());
				WebUtils.setSessionAttribute(request, "display_tbl",
						dataLogList);
				return modelAndView;
			}
			String pageNo = dataLogSearchVO.getPageNo();
			dataLogSearchVO = (DataLogSearchVO) WebUtils.getSessionAttribute(
					request, "searchObj");
			dataLogSearchVO.setPageNo(pageNo);

			WebUtils.setSessionAttribute(request, "searchObj", dataLogSearchVO);

			modelAndView.addObject("dataLog", dataLogSearchVO);
			dataLogList = (List<DataLogListVO>) WebUtils.getSessionAttribute(
					request, "display_tbl");
		} else if ((request.getParameter("action") != null)
				&& request.getParameter("action").equals("Back")) {
			DataLogSearchVO dataLog = (DataLogSearchVO) WebUtils
					.getSessionAttribute(request, "searchObj");
			if (dataLog != null) {
				dataLog.setPageNo(dataLogSearchVO.getPageNo());
				modelAndView.addObject("dataLog", dataLog);
			}
			return modelAndView;

		} else {
			WebUtils.setSessionAttribute(request, "display_tbl", null);

		}

		return modelAndView;

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

	// method to extract the list which is already in session
	private List<DataLogListVO> extracted(HttpServletRequest request) {
		logger.info("Going to get the Searched List from session");
		List<DataLogListVO> sessionAttribute = (List<DataLogListVO>) WebUtils
				.getSessionAttribute(request, "display_tbl");
		return sessionAttribute;
	}

	@RequestMapping(value = "/dataLogDetails", method = RequestMethod.GET)
	public ModelAndView getDataLogPage(
			@ModelAttribute("dataLogVO") DataLogDetailsVO dataLogVO,
			HttpServletRequest request, HttpSession session,
			final RedirectAttributes redirectAttributes)
			throws NoDataFoundException {
		ModelAndView modelAndView = null;
		String rowid = request.getParameter("rowid");
		DataLogSearchVO dataLogSearchVO = (DataLogSearchVO) WebUtils
				.getSessionAttribute(request, "searchObj");
		if ((request.getParameter("action") != null)
				&& (request.getParameter("action").equals("Back"))) {
			logger.info("In Data Log Details page,Back Button has been clicked");
			dataLogSearchVO.setKeepstat("clear");
			redirectAttributes.addFlashAttribute("dataLog", dataLogSearchVO);

			modelAndView = new ModelAndView("redirect:dataLog.htm");
			modelAndView.getModelMap().addAttribute("action", "Back");
		} else {
			logger.info("Rendering Data Log Details page");
			SessionCache sessionCache = (SessionCache) session
					.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
			ApplicationCache applicationCache = (ApplicationCache) session
					.getServletContext().getAttribute(
							ApplicationConstant.APPLICATION_CACHE);

			List<DataLogListVO> listOfDataLog = extracted(request);
			DataLogListVO dataLogListVO = listOfDataLog.get(Integer
					.valueOf(rowid));
			Map<String, ArrayList<String>> columnAndRecords = dataLogService
					.getDataLogRecFromListRec(dataLogListVO, dataLogVO,
							sessionCache.getUserSessionDetails(),
							applicationCache);
			dataLogVO.setRowid(rowid);
			String rCreTime = dataLogVO.getrCreTime().substring(0, 19);
			dataLogVO.setrCreTime(rCreTime);
			Map<String, ArrayList<String>> tableKeyValues = dataLogService
					.getTableKey(dataLogVO,applicationCache,
							sessionCache.getUserSessionDetails());			
			request.setAttribute("tableKeyValues", tableKeyValues);
			request.setAttribute("columnAndRecords", columnAndRecords);
			modelAndView = new ModelAndView(".jaw.dataLogDetails");
		}

		return modelAndView;

	}

	@ExceptionHandler({ NoDataFoundException.class })
	public ModelAndView handleException(Exception ex, HttpSession session,
			HttpServletRequest request) {
		logger.info("Exception has occured,Inside the method to handle the exception");
		DataLogSearchVO dataLogSearchVO = new DataLogSearchVO();
		ModelAndView mav = null;
		dataLogSearchVO = (DataLogSearchVO) request.getAttribute("dataLogReq");
		if (dataLogSearchVO != null) {
			mav = new ModelAndView(".jaw.dataLog", "dataLog", dataLogSearchVO);
		}
		mav.getModelMap().addAttribute("error", ex.getMessage());
		return mav;

	}
}
