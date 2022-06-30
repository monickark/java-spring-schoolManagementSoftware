package com.jaw.fee.controller;

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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.WebUtils;

import com.jaw.common.constants.ApplicationConstant;
import com.jaw.common.constants.ErrorCodeConstant;
import com.jaw.common.dropdown.service.IDropDownListService;
import com.jaw.common.exceptions.BatchUpdateFailedException;
import com.jaw.common.exceptions.DatabaseException;
import com.jaw.common.exceptions.DeleteFailedException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.UpdateFailedException;
import com.jaw.common.exceptions.util.ErrorDescriptionNotFoundException;
import com.jaw.common.exceptions.util.TableNotSpecifiedForAuditException;
import com.jaw.common.util.ErrorCodeUtil;
import com.jaw.fee.service.IFeeMasterService;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.sessCache.SessionCache;

@Controller
public class FeeMasterController {

	Logger logger = Logger.getLogger(FeeMasterController.class);
	@Autowired
	IFeeMasterService feeMasterService;
	@Autowired
	IDropDownListService dropDownListService;
	@Autowired
	ErrorCodeUtil errorCodeUtil;

	@RequestMapping(value = "/feeMaster", method = RequestMethod.GET)
	public ModelAndView stdlistGet(
			@ModelAttribute("feeMaster") FeeMasterSearchVO feeMasterSearchVO,
			HttpServletRequest HttpServletRequest, HttpSession session,
			ModelMap model) throws NoDataFoundException,
			ErrorDescriptionNotFoundException {

		logger.info("Fee Master page submitted");
		ModelAndView mav = new ModelAndView(".jaw.feeMaster", "feeMaster",
				feeMasterSearchVO);
		WebUtils.setSessionAttribute(HttpServletRequest, "display_tbl", null);
		WebUtils.setSessionAttribute(HttpServletRequest, "display_tbl1", null);
		WebUtils.setSessionAttribute(HttpServletRequest, "success", null);
		HttpServletRequest.setAttribute("page", mav);

		return mav;

	}

	@RequestMapping(value = "/feeMaster", method = RequestMethod.GET, params = { "Get" })
	public String feeMasterSearch(
			@ModelAttribute("feeMaster") FeeMasterSearchVO feeMasterSearchVO,
			ModelMap model, HttpServletRequest httpRequest,
			HttpSession httpSession,RedirectAttributes redirectAttributes) throws NoDataFoundException {

		logger.info("Search Initiated,Get has been clicked");
		SessionCache sessionCache = (SessionCache) httpSession
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);

		// Getting the display tag parameter

		Map stockParamMap = WebUtils.getParametersStartingWith(httpRequest,
				"d-");

		List<FeeMasterVO> feeMasterList = null;
		List<FeeCategoryLinkingListVO> feeCategoryLinkingListVOs = null;

		// Setting model and view for exception handler

		ModelAndView modelAndView = new ModelAndView(".jaw.feeMaster",
				"feeMaster", feeMasterSearchVO);
		httpRequest.setAttribute("page", modelAndView);

		// Check whether the list already get or have to fetch from data base
		// using the list size
		WebUtils.setSessionAttribute(httpRequest, "searchVo", feeMasterSearchVO);
		
			
			logger.info("Table operation has been triggered");

			// Get list from database

			try {
				feeMasterList = feeMasterService
						.selectfeeMasterList(feeMasterSearchVO,
								sessionCache.getUserSessionDetails());
				WebUtils.setSessionAttribute(httpRequest, "display_tbl",
						feeMasterList);
				WebUtils.setSessionAttribute(httpRequest, "display_tbl1",
						null);
			} catch (NoDataFoundException e) {
				feeCategoryLinkingListVOs = feeMasterService
						.selectFeeCategoryList(feeMasterSearchVO,
								sessionCache.getUserSessionDetails());
				
				WebUtils.setSessionAttribute(httpRequest, "display_tbl1",
						feeCategoryLinkingListVOs);
				WebUtils.setSessionAttribute(httpRequest, "display_tbl",
						null);
			}

			// Set the list to session to access in jsp
			feeMasterSearchVO.setPageSize(feeMasterSearchVO.getPageSize());

		
		String getAttr = httpRequest.getParameter("Get");
		if (getAttr.equals("Get")) {
			WebUtils.setSessionAttribute(httpRequest, "success", null);
		}
		redirectAttributes.addFlashAttribute("feeMaster", feeMasterSearchVO);
		return "redirect:/feeMaster.htm?data";

	}

	@RequestMapping(value = "/feeMaster", method = RequestMethod.GET, params = {
			"data", "!Get", "!Save", "!Add", "!update", "!delete" })
	public ModelAndView stdlistData(
			@ModelAttribute("feeMaster") FeeMasterSearchVO feeMasterSearchVO,
			HttpServletRequest HttpServletRequest, HttpSession session,
			ModelMap model) throws NoDataFoundException,
			ErrorDescriptionNotFoundException {

		logger.info("Fee Master page submitted");
		feeMasterSearchVO = (FeeMasterSearchVO) WebUtils.getSessionAttribute(
				HttpServletRequest, "searchVo");
		System.out.println("Fee master searchvo added");
		ModelAndView mav = new ModelAndView(".jaw.feeMaster", "feeMaster",
				feeMasterSearchVO);
		HttpServletRequest.setAttribute("page", mav);

		return mav;

	}

	@RequestMapping(value = "/feeMaster", method = RequestMethod.GET, params = { "Save" })
	public String feeMasterSave(
			@ModelAttribute("feeMaster") FeeMasterSearchVO feeMasterSearchVO,
			ModelMap model, HttpServletRequest httpServletRequest,
			HttpSession httpSession,RedirectAttributes redirectAttributes) throws NoDataFoundException,
			DuplicateEntryException, BatchUpdateFailedException,
			DatabaseException {
		SessionCache sessionCache = (SessionCache) httpSession
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		feeMasterSearchVO = (FeeMasterSearchVO) WebUtils.getSessionAttribute(
				httpServletRequest, "searchVo");
		ApplicationCache applicationCache = (ApplicationCache) httpSession
				.getServletContext().getAttribute(
						ApplicationConstant.APPLICATION_CACHE);
		List<FeeCategoryLinkingListVO> feeCategoryLinkingListVOs = (List<FeeCategoryLinkingListVO>) WebUtils
				.getSessionAttribute(httpServletRequest, "display_tbl1");
		System.out.println("FeeCategoryLinkingListVO :"+feeCategoryLinkingListVOs.toString());
		String[] feeAmounts = httpServletRequest.getParameterValues("amount");
		String[] courseVariant = httpServletRequest.getParameterValues("courseVariant");
		feeMasterService.insertFeeMasters(sessionCache.getUserSessionDetails(),
				feeAmounts,courseVariant, applicationCache, feeCategoryLinkingListVOs,
				feeMasterSearchVO);
		redirectAttributes.addFlashAttribute("feeMaster", feeMasterSearchVO);
		return "redirect:/feeMaster.htm?Get";

	}
	
	@RequestMapping(value = "/feeMaster", method = RequestMethod.GET, params = { "SaveNew" })
	public String feeMasterSaveNew(
			@ModelAttribute("feeMaster") FeeMasterSearchVO feeMasterSearchVO,
			ModelMap model, HttpServletRequest httpServletRequest,
			HttpSession httpSession,RedirectAttributes redirectAttributes) throws NoDataFoundException,
			DuplicateEntryException, BatchUpdateFailedException,
			DatabaseException {
	
		SessionCache sessionCache = (SessionCache) httpSession
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		feeMasterSearchVO = (FeeMasterSearchVO) WebUtils.getSessionAttribute(
				httpServletRequest, "searchVo");
		ApplicationCache applicationCache = (ApplicationCache) httpSession
				.getServletContext().getAttribute(
						ApplicationConstant.APPLICATION_CACHE);
		List<FeeMasterVO> feeMasterVOs = (List<FeeMasterVO>) WebUtils
				.getSessionAttribute(httpServletRequest, "display_tbl");

		String feeAmounts = httpServletRequest.getParameter("amount");
		String courseVariant = httpServletRequest.getParameter("courseVariant");
		String rowId = httpServletRequest.getParameter("rowId");
		FeeMasterVO feeMasterVO = feeMasterVOs.get(Integer.parseInt(rowId));
		feeMasterVO.setFeeAmt(Integer.parseInt(feeAmounts));
		feeMasterVO.setCourseVariant(courseVariant);
		feeMasterService.insertFeeMaster(sessionCache.getUserSessionDetails(),applicationCache,
				feeMasterSearchVO,feeMasterVO);
		redirectAttributes.addFlashAttribute("feeMaster", feeMasterSearchVO);
		return "redirect:/feeMaster.htm?Get";

	}

	@RequestMapping(value = "/feeMaster", method = RequestMethod.GET, params = { "Add" })
	public String feeMasterAdd(
			@ModelAttribute("feeMaster") FeeMasterSearchVO feeMasterSearchVO,
			ModelMap model, HttpServletRequest httpRequest,
			HttpSession httpSession,RedirectAttributes redirectAttributes) throws NoDataFoundException {

		logger.info("Search Initiated,Get has been clicked");
		SessionCache sessionCache = (SessionCache) httpSession
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);

		List<FeeCategoryLinkingListVO> feeCategoryLinkingListVOs = null;

		// Setting model and view for exception handler

		ModelAndView modelAndView = new ModelAndView(".jaw.feeMaster",
				"feeMaster", feeMasterSearchVO);
		httpRequest.setAttribute("page", modelAndView);

		// Check whether the list already get or have to fetch from data base
		// using the list size

		feeCategoryLinkingListVOs = feeMasterService.selectUnAllottedList(
				feeMasterSearchVO, sessionCache.getUserSessionDetails());
		System.out.println("Un allotted record size :"+feeCategoryLinkingListVOs.size());
		WebUtils.setSessionAttribute(httpRequest, "display_tbl1",
				feeCategoryLinkingListVOs);
		redirectAttributes.addFlashAttribute("feeMaster", feeMasterSearchVO);
		return "redirect:/feeMaster.htm?data";

	}

	@RequestMapping(value = "/feeMaster", method = RequestMethod.GET, params = { "update" })
	public String getFeeMasterLinking(
			@ModelAttribute("feeMaster") FeeMasterSearchVO feeMasterSearchVO,
			ModelMap model, HttpServletRequest httpServletRequest,
			HttpSession session, RedirectAttributes redirectAttribute)
			throws UpdateFailedException, NumberFormatException,
			NoDataFoundException, DuplicateEntryException, DatabaseException,
			TableNotSpecifiedForAuditException {

		logger.debug("inside Update course classes linking method");

		List<FeeMasterVO> FeeMasterVOs = (List<FeeMasterVO>) WebUtils
				.getSessionAttribute(httpServletRequest, "display_tbl");

		String rowId = httpServletRequest.getParameter("rowId");
		String amount = httpServletRequest.getParameter("amount");
		System.out.println("amount:" + amount);
		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		feeMasterSearchVO = (FeeMasterSearchVO) WebUtils.getSessionAttribute(
				httpServletRequest, "searchVo");
		ApplicationCache applicationCache = (ApplicationCache) session
				.getServletContext().getAttribute(
						ApplicationConstant.APPLICATION_CACHE);
		FeeMasterVO FeeMasterVO = FeeMasterVOs.get(Integer.parseInt(rowId));
		FeeMasterVO.setFeeAmt(Integer.parseInt(amount));
		feeMasterService.updateFeeMaster(FeeMasterVO, feeMasterSearchVO,
				sessionCache.getUserSessionDetails(), applicationCache);

		redirectAttribute.addFlashAttribute("feeMaster", feeMasterSearchVO);
		WebUtils.setSessionAttribute(httpServletRequest, "success",
				ErrorCodeConstant.UPDATE_SUCCESS_MESS);
	
		return "redirect:/feeMaster.htm?Get";
	}

	@RequestMapping(value = "/feeMaster", method = RequestMethod.GET, params = { "delete" })
	public String deleteFeeMasterLinking(
			@ModelAttribute("feeMaster") FeeMasterSearchVO feeMasterSearchVO,
			ModelMap model, HttpServletRequest httpServletRequest,
			HttpSession session, RedirectAttributes redirectAttribute)
			throws UpdateFailedException, NumberFormatException,
			NoDataFoundException, DeleteFailedException,
			DuplicateEntryException, DatabaseException,
			TableNotSpecifiedForAuditException {

		logger.debug("inside delete course classes linking method");
		System.out.println("inside ajax delete");
		List<FeeMasterVO> feeMasterVOs = (List<FeeMasterVO>) WebUtils
				.getSessionAttribute(httpServletRequest, "display_tbl");

		String id = httpServletRequest.getParameter("rowId");

		ApplicationCache applicationCache = (ApplicationCache) session
				.getServletContext().getAttribute(
						ApplicationConstant.APPLICATION_CACHE);
		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);

		System.out.println("Id received from jsp :" + id);

		FeeMasterVO FeeMasterVO = feeMasterVOs.get(Integer.parseInt(id));
		feeMasterSearchVO = (FeeMasterSearchVO) WebUtils.getSessionAttribute(
				httpServletRequest, "searchVo");
		feeMasterService.deleteFeeMasterDAORec(FeeMasterVO, feeMasterSearchVO,
				sessionCache.getUserSessionDetails(), applicationCache);

		redirectAttribute.addFlashAttribute("feeMaster", feeMasterSearchVO);
		WebUtils.setSessionAttribute(httpServletRequest, "display_tbl",
				feeMasterVOs);
		WebUtils.setSessionAttribute(httpServletRequest, "success",
				ErrorCodeConstant.DELETE_SUCCESS_MESS);
		
		return "redirect:/feeMaster.htm?Get";
	}

	@ModelAttribute("courseList")
	public Map<String, String> gerCourseNameList(HttpSession session,
			HttpServletRequest httpSevletfeeMaster,
			HttpServletResponse response, ModelMap model) throws IOException {
		Map<String, String> map = null;
		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		try {
			map = dropDownListService.getCourseNameListTag(sessionCache
					.getUserSessionDetails());
		} catch (NoDataFoundException e) {

		}
		httpSevletfeeMaster.setAttribute("courseList", map);
		return map;

	}

	@ExceptionHandler({ NoDataFoundException.class })
	public ModelAndView handleException1(Exception ex, HttpSession session,
			HttpServletRequest httpServletRequest) {
		ModelAndView modelAndView = (ModelAndView) httpServletRequest
				.getAttribute("page");
		modelAndView.getModelMap().addAttribute("error", ex.getMessage());
		WebUtils.setSessionAttribute(httpServletRequest, "display_tbl", null);
		WebUtils.setSessionAttribute(httpServletRequest, "display_tbl1", null);
		return modelAndView;
	}
}
