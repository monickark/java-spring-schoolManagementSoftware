package com.jaw.batch.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.ParserConfigurationException;

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
import org.xml.sax.SAXException;

import com.jaw.batch.service.IBatchStatusService;
import com.jaw.common.business.CommonBusiness;
import com.jaw.common.constants.ApplicationConstant;
import com.jaw.common.dropdown.service.IDropDownListService;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.batch.NoRecordFoundException;
import com.jaw.common.util.DateUtil;
import com.jaw.framework.sessCache.SessionCache;

@Controller
// Controller class for BatchStatus
public class BatchStatusController {
	Logger logger = Logger.getLogger(BatchStatusController.class);
	@Autowired
	IBatchStatusService batchService;
	@Autowired
	CommonBusiness commonBusiness;
	@Autowired
	IDropDownListService dropDownListService;


	// Method to render the BatchStatus page
	@RequestMapping(value = "/batchStatus", method = RequestMethod.GET)
	public ModelAndView loadBatchStatusPage(
			@ModelAttribute("batchStatus") BatchStatusSearchVO batchStatusVO,
			HttpServletRequest request, ModelMap map, HttpSession session,
			RedirectAttributes redirect) throws ParserConfigurationException,
			SAXException, IOException, NoRecordFoundException,
			NoDataFoundException {
		logger.info("Batch Status page is opening");
		SessionCache sessionCache = null;
		ModelAndView mav = new ModelAndView(".jaw.batchStatus");
		if ((request.getParameter("Search2") == null)
				&& (request.getParameter("BackSearch1") == null)) {
			WebUtils.setSessionAttribute(request, "display_tbl", null);
		}
		sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		if ((request.getParameter("Search2") != null)
				&& (request.getParameter("Search2").equals("Search"))) {
			logger.info("Started Search,by Advanced Search");
			List<BatchStatusVO> batchStatusList = null;
			request.setAttribute("batchStatusRec", null);
			request.setAttribute("batchStatusVO", batchStatusVO);
			WebUtils.setSessionAttribute(request, "batchStatus", null);
			WebUtils.setSessionAttribute(request, "display_tbl", null);
			batchStatusList = batchService.getBatchStatusList(batchStatusVO,
					sessionCache.getUserSessionDetails());
			WebUtils.setSessionAttribute(request, "display_tbl",
					batchStatusList);
			WebUtils.setSessionAttribute(request, "batchStatus", batchStatusVO);
			mav.getModelMap().addAttribute("status", "true");
		} else if ((request.getParameter("BackSearch1") != null)) {
			if (((BatchStatusSearchVO) WebUtils.getSessionAttribute(request,
					"batchStatus")) != null) {
				String pgNo = request.getParameter("pageNo");
				commonBusiness.changeObject(batchStatusVO,
						((BatchStatusSearchVO) WebUtils.getSessionAttribute(
								request, "batchStatus")));
				if (pgNo != null) {
					batchStatusVO.setPageNo(pgNo);
					WebUtils.setSessionAttribute(request, "batchStatus",
							batchStatusVO);
				}
			}
			return mav;

		}
		return mav;
	}

	@RequestMapping(value = "/batchStatusDetails", method = RequestMethod.GET)
	public ModelAndView loadBatchStatusMessage(
			@ModelAttribute("batchStatus") BatchStatusSearchVO batchStatusVO,
			HttpServletRequest request, ModelMap map, HttpSession session,
			RedirectAttributes redirect) throws NoRecordFoundException {
		logger.info("Batch Status Details page is opening");
		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		if ((request.getParameter("BackSearch") != null)
				&& (request.getParameter("BackSearch").equals("Back"))) {
			batchStatusVO = ((BatchStatusSearchVO) WebUtils
					.getSessionAttribute(request, "batchStatus"));
			ModelAndView mav = new ModelAndView("redirect:/batchStatus.htm");
			redirect.addFlashAttribute("batchStatus", batchStatusVO);
			mav.getModelMap().addAttribute("status", "false");
			mav.getModelMap().addAttribute("BackSearch1", "Back");
			return mav;

		} else {
			String rowid = request.getParameter("rowid");
			List<BatchStatusVO> batchStatusList = null;
			batchStatusList = extracted(request);
			BatchStatusVO batchStatusRec = null;
			if (batchStatusList != null) {
				batchStatusRec = batchStatusList.get(Integer.valueOf(rowid));
				BatchStatusSearchVO serachVO = new BatchStatusSearchVO();
				batchStatusRec = batchService.getBatchStatusRecord(serachVO,
						sessionCache.getUserSessionDetails(),
						batchStatusRec.getBatchSrlNo());
			}
			batchStatusVO = ((BatchStatusSearchVO) WebUtils
					.getSessionAttribute(request, "batchStatus"));
			map.addAttribute("batchStatusRec", batchStatusRec);
			java.util.List<String> listOfReasonForFailure = batchStatusRecOperations(batchStatusRec);
			map.addAttribute("listOfReasonForFailure", listOfReasonForFailure);
		}

		return new ModelAndView(".jaw.batchStatusDetails");

	}

	@RequestMapping(value = "/batchStatusMessage", method = RequestMethod.GET)
	public ModelAndView renderBatchStatusMessage(
			@ModelAttribute("batchStatus") BatchStatusSearchVO batchStatusVO,
			HttpServletRequest request, ModelMap map, HttpSession session) {
		return new ModelAndView(".jaw.batchStatusDetails");
	}

	// method to extract the list which is already in session
	private List<BatchStatusVO> extracted(HttpServletRequest request) {
		List<BatchStatusVO> sessionAttribute = (List<BatchStatusVO>) WebUtils
				.getSessionAttribute(request, "display_tbl");
		return sessionAttribute;
	}

	// method to load the batch details from batch id search
	@RequestMapping(value = "/batchStatus", method = RequestMethod.GET, params = { "Search1" })
	public ModelAndView loadResultTableForSearch(
			@ModelAttribute("batchStatus") BatchStatusSearchVO batchStatusVO,
			ModelMap map, HttpServletRequest request, HttpSession session)
			throws ParserConfigurationException, SAXException, IOException,
			NoRecordFoundException {
		logger.info("Started Search,by Batch Id alone");
		request.setAttribute("batchStatusVO", batchStatusVO);
		BatchStatusVO batchStatusRec = batchService
				.getBatchStatusRecord(batchStatusVO);
		WebUtils.setSessionAttribute(request, "display_tbl", null);
		java.util.List<String> listOfReasonForFailure = batchStatusRecOperations(batchStatusRec);
		map.addAttribute("listOfReasonForFailure", listOfReasonForFailure);
		map.addAttribute("batchStatusRec", batchStatusRec);
		return new ModelAndView(".jaw.batchStatus");
	}

	public List<String> batchStatusRecOperations(BatchStatusVO batchStatusRec) {
		String reasonForFailure = batchStatusRec.getReasonForFailure();
		if (batchStatusRec.getExecEndDate() != null) {
			batchStatusRec
					.setExecEndDate(DateUtil
							.getFormattedDateTimeFromDB(batchStatusRec
									.getExecEndDate()));
		} else {
			batchStatusRec.setExecEndDate("");
		}
		batchStatusRec.setExecStartDate(DateUtil
				.getFormattedDateTimeFromDB(batchStatusRec.getExecStartDate()));
		reasonForFailure = reasonForFailure.replace("[", "");
		reasonForFailure = reasonForFailure.replace("]", "");
		String[] arrayOfReasonForFailure = reasonForFailure.split(";,");
		java.util.List<String> listOfReasonForFailure = Arrays
				.asList(arrayOfReasonForFailure);

		return listOfReasonForFailure;
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

	// Method to handle the user-defined exception
	@ExceptionHandler({ NoRecordFoundException.class,
			NoDataFoundException.class })
	public ModelAndView handleException(Exception ex, HttpSession session,
			HttpServletRequest request) {
		WebUtils.setSessionAttribute(request, "display_tbl", null);
		BatchStatusSearchVO batchStatusVO = (BatchStatusSearchVO) request
				.getAttribute("batchStatusVO");
		ModelAndView mav = new ModelAndView(".jaw.batchStatus", "batchStatus",
				batchStatusVO);
		mav.getModelMap().addAttribute("error", ex.getMessage());
		return mav;
	}

}
