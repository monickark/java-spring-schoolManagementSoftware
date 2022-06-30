package com.jaw.prodAdm.controller;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.WebUtils;

import com.jaw.common.business.CommonBusiness;
import com.jaw.common.constants.ApplicationConstant;
import com.jaw.common.constants.ErrorCodeConstant;
import com.jaw.common.exceptions.DeleteFailedException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.FileNotFoundInDatabase;
import com.jaw.common.exceptions.InsertFailedException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.UpdateFailedException;
import com.jaw.common.util.ErrorCodeUtil;
import com.jaw.framework.sessCache.SessionCache;
import com.jaw.prodAdm.service.ICommonCodeService;

/**
 * CommonCodeController is used for commoncode table actions and its binding
 * with cocd.jsp
 */

@Controller
public class CommonCodeController {

	Logger logger = Logger.getLogger(CommonCodeController.class);
	@Autowired
	ICommonCodeService commonCodeService;
	@Autowired
	CommonBusiness commonBusiness;
	@Autowired
	ErrorCodeUtil errorCodeUtil;
	String error = "";

	// this method id used to display the cocd.jsp page

	@RequestMapping(value = "/cocd", method = RequestMethod.GET)
	public ModelAndView cocdGet(@ModelAttribute("cocd") CommonCodeVO adminVO) {
		logger.info("Opening cocd page");
		return new ModelAndView(".jaw.cocd");
	}

	// This method will call when a submit action is triggered in cocd.jsp page

	@RequestMapping(value = "/cocd", method = RequestMethod.POST, params = { "Save" })
	public String cocdPost(@Valid @ModelAttribute("cocd") CommonCodeVO cocd,
			HttpServletRequest httpServletRequest, HttpSession session,
			ModelMap map, final RedirectAttributes redirectAttributes)
			throws NoDataFoundException, InsertFailedException,
			DuplicateEntryException {
		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		cocd.setCommonCode(cocd.getNewCommonCode());
		cocd.setCodeType(cocd.getNewCodeType());
		cocd.setCodeDescription(cocd.getNewCodeDescription());
		commonCodeService
				.insertCocd(cocd, sessionCache.getUserSessionDetails());
		cocd.setSearchCodeType(cocd.getNewCodeType());

		redirectAttributes.addFlashAttribute("cocd", cocd);
		redirectAttributes.addFlashAttribute("success",
				ErrorCodeConstant.ADD_SUCCESS_MESS);
		httpServletRequest.setAttribute("cocd", cocd);
		return "redirect:cocd.htm?Search=Search";
	}

	@RequestMapping(value = "/cocd", method = RequestMethod.GET, params = { "Search" })
	public ModelAndView cocdPostSearch(
			@Valid @ModelAttribute("cocd") CommonCodeVO cocd,
			HttpServletRequest httpRequest, HttpSession session, ModelMap map)
			throws NoDataFoundException {
		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		Map stockParamMap = WebUtils.getParametersStartingWith(httpRequest,
				"d-");
		List<CommonCodeListVO> commonCodeList;
		if (stockParamMap.size() == 0) {
			cocd.setCodeType(cocd.getSearchCodeType());
			commonCodeList = commonCodeService.selectCocdList(cocd,
					sessionCache.getUserSessionDetails());
			WebUtils.setSessionAttribute(httpRequest, "commoncode",
					commonCodeList);
			cocd.setPageNo("P1");
		}
		httpRequest.setAttribute("cocd", cocd);
		return new ModelAndView(".jaw.cocd");
	}

	@RequestMapping(value = "/cocd", method = RequestMethod.POST, params = { "Update" })
	public String cocdPostUpdate(
			@Valid @ModelAttribute("cocd") CommonCodeVO cocd,
			HttpServletRequest httpServletRequest, HttpSession session,
			ModelMap map, final RedirectAttributes redirectAttributes)
			throws NoDataFoundException, UpdateFailedException {

		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		List<CommonCodeListVO> codeListVOs = (List<CommonCodeListVO>) WebUtils
				.getSessionAttribute(httpServletRequest, "commoncode");
		String id = httpServletRequest.getParameter("rowId");
		String description = httpServletRequest.getParameter("textbox");
		CommonCodeListVO cocdlist = codeListVOs.get(Integer.parseInt(id));
		cocdlist.setCodeDescription(description);
		commonCodeService.updateCocdRecord(cocdlist,
				sessionCache.getUserSessionDetails());
		cocd.setCodeDescription(cocdlist.getCodeDescription());
		cocd.setCommonCode(cocdlist.getCommonCode());
		cocd.setSearchCodeType(cocdlist.getCodeType());
		redirectAttributes.addFlashAttribute("success",
				ErrorCodeConstant.UPDATE_SUCCESS_MESS);
		redirectAttributes.addFlashAttribute("cocd", cocd);
		return "redirect:cocd.htm?Search=Search";

	}

	@RequestMapping(value = "/cocd", method = RequestMethod.GET, params = { "Delete" })
	public String cocdPostDelete(
			@Valid @ModelAttribute("cocd") CommonCodeVO cocd,
			HttpServletRequest httpServletRequest, HttpSession session,
			ModelMap map, final RedirectAttributes redirectAttributes)
			throws NoDataFoundException, DeleteFailedException {
		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		List<CommonCodeListVO> codeListVOs = (List<CommonCodeListVO>) WebUtils
				.getSessionAttribute(httpServletRequest, "commoncode");
		String id = httpServletRequest.getParameter("rowId");
		CommonCodeListVO cocdlist = codeListVOs.get(Integer.parseInt(id));
		commonCodeService.deleteCocdRecord(cocdlist,
				sessionCache.getUserSessionDetails());
		cocd.setCodeDescription(cocdlist.getCodeDescription());
		cocd.setCommonCode(cocdlist.getCommonCode());
		cocd.setSearchCodeType(cocdlist.getCodeType());
		redirectAttributes.addFlashAttribute("success",
				ErrorCodeConstant.DELETE_SUCCESS_MESS);
		redirectAttributes.addFlashAttribute("cocd", cocd);
		return "redirect:cocd.htm?Search=Search";

	}

	// Get all code type from application cache
	@RequestMapping(value = "/getListByType", method = RequestMethod.POST)
	public @ResponseBody
	String[] getCocodListByType(HttpSession session, ModelMap model)
			throws NoDataFoundException {

		logger.info("Ajax get type list called for autocomplete");
		return commonCodeService.retriveType();
	}

	@ExceptionHandler({ FileNotFoundInDatabase.class,
			DuplicateEntryException.class, NoDataFoundException.class,
			DeleteFailedException.class, InsertFailedException.class })
	public ModelAndView handleException(Exception ex, HttpSession session,
			HttpServletRequest request,
			@ModelAttribute("cocd") CommonCodeVO cocd) {
		logger.debug("inside exception handler");
		ModelAndView mav = new ModelAndView(".jaw.cocd");
		mav.getModelMap().addAttribute("error", ex.getMessage());
		return mav;

	}

}