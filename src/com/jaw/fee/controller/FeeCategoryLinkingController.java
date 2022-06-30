package com.jaw.fee.controller;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import com.jaw.common.constants.ApplicationConstant;
import com.jaw.common.constants.ErrorCodeConstant;
import com.jaw.common.exceptions.BatchUpdateFailedException;
import com.jaw.common.exceptions.DatabaseException;
import com.jaw.common.exceptions.DeleteFailedException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.util.ErrorDescriptionNotFoundException;
import com.jaw.common.exceptions.util.TableNotSpecifiedForAuditException;
import com.jaw.common.util.ErrorCodeUtil;
import com.jaw.fee.service.IFeeCategoryLinkingService;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.sessCache.SessionCache;

@Controller
public class FeeCategoryLinkingController {

	Logger logger = Logger.getLogger(FeeCategoryLinkingController.class);
	@Autowired
	IFeeCategoryLinkingService feeCategoryLinkingService;
	@Autowired
	ErrorCodeUtil errorCodeUtil;

	@RequestMapping(value = "/feeCatLinking", method = RequestMethod.GET)
	public ModelAndView stdlistGet(
			@ModelAttribute("feeCatLink") FeeCategoryLinkingVO feeCategoryLinkingVO,
			HttpServletRequest httpServletRequest, HttpSession session,
			ModelMap model) throws NoDataFoundException,
			ErrorDescriptionNotFoundException {

		logger.info("Class teacher allotment page submitted");

		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		ModelAndView mav = new ModelAndView(".jaw.feeCatLinking", "feeCatLink",
				feeCategoryLinkingVO);
		httpServletRequest.setAttribute("page", mav);

		return mav;

	}

	@RequestMapping(value = "/feeCatLinking", method = RequestMethod.GET, params = { "Get" })
	public ModelAndView stdlistpost(
			@ModelAttribute("feeCatLink") FeeCategoryLinkingVO feeCategoryLinkingVO,
			HttpServletRequest httpServletRequest, HttpSession session,
			ModelMap model) {

		logger.info("Class teacher allotment page submitted");

		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		ModelAndView mav = new ModelAndView(".jaw.feeCatLinking", "feeCatLink",
				feeCategoryLinkingVO);
		httpServletRequest.setAttribute("page", mav);
		WebUtils.setSessionAttribute(httpServletRequest, "searchVo",
				feeCategoryLinkingVO);

		Map<String, String> allotted = feeCategoryLinkingService
				.getAllotedFeetype(sessionCache.getUserSessionDetails(),
						feeCategoryLinkingVO.getFeeCategory());
		model.addAttribute("allotted", allotted);
		Map<String, String> electiveSubjects = feeCategoryLinkingService
				.getElectiveSubjects(sessionCache.getUserSessionDetails());
		model.addAttribute("electiveSubjects", electiveSubjects);
		Map<String, String> unAllotted = feeCategoryLinkingService
				.getUnallotedFeetype(sessionCache.getUserSessionDetails(),
						feeCategoryLinkingVO.getFeeCategory());
		model.addAttribute("unAllotted", unAllotted);
		return mav;

	}

	// Ajax method to get subjects based on subject type
	@RequestMapping(value = "/feeCatLinking", method = RequestMethod.GET, params = { "insert" })
	public @ResponseBody
	String insertFeeCategory(
			@RequestParam(value = "selectedfeeType[]", required = false) String[] selectedfeeType,
			HttpServletRequest httpServletRequest, HttpSession session)
			throws DatabaseException, DuplicateEntryException,
			ErrorDescriptionNotFoundException, NoDataFoundException,
			BatchUpdateFailedException

	{
		ApplicationCache applicationCache = (ApplicationCache) session
				.getServletContext().getAttribute(
						ApplicationConstant.APPLICATION_CACHE);
		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);

		String fee = httpServletRequest.getParameter("feeCategory");
		System.out.println("Selected fee type :" + selectedfeeType.toString());
		System.out.println("Selected fee type :" + fee);
		feeCategoryLinkingService.insertFeeCategoryLinking(
				sessionCache.getUserSessionDetails(), selectedfeeType,
				applicationCache, fee);
		String returnString = (String) errorCodeUtil.getErrorDescription(
				applicationCache, ErrorCodeConstant.ADD_SUCCESS_MESS);
		System.out.println("Return string :" + returnString);
		return returnString;
	}
	
	
	@RequestMapping(value = "/feeCatLinking", method = RequestMethod.GET, params = { "delete" })
	public @ResponseBody
	String deleteFeeCategory(
			HttpServletRequest httpServletRequest, HttpSession session)
			throws DatabaseException, DuplicateEntryException,
			ErrorDescriptionNotFoundException, NoDataFoundException,
			BatchUpdateFailedException, DeleteFailedException, TableNotSpecifiedForAuditException

	{
		ApplicationCache applicationCache = (ApplicationCache) session
				.getServletContext().getAttribute(
						ApplicationConstant.APPLICATION_CACHE);
		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);

		String feeType = httpServletRequest.getParameter("feeType");
		String feeCategory = httpServletRequest.getParameter("feeCategory");
		System.out.println("Selected fee type :" + feeCategory.toString());
		System.out.println("Selected fee type :" + feeType);
		feeCategoryLinkingService.deleteFeeCategory(feeCategory, feeType,
				sessionCache.getUserSessionDetails(), 
				applicationCache);
		String returnString = (String) errorCodeUtil.getErrorDescription(
				applicationCache, ErrorCodeConstant.DELETE_SUCCESS_MESS);
		System.out.println("Return string :" + returnString);
		return returnString;
	}



	@ExceptionHandler({ NoDataFoundException.class })
	public ModelAndView handleException1(Exception ex, HttpSession session,
			HttpServletRequest request) {
		ModelAndView modelAndView = (ModelAndView) request.getAttribute("page");
		modelAndView.getModelMap().addAttribute("error", ex.getMessage());
		WebUtils.setSessionAttribute(request, "display_tbl", null);
		return modelAndView;
	}
}
