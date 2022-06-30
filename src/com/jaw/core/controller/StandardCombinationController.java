package com.jaw.core.controller;

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

import com.jaw.common.constants.ApplicationConstant;
import com.jaw.common.constants.ErrorCodeConstant;
import com.jaw.common.exceptions.BatchUpdateFailedException;
import com.jaw.common.exceptions.DeleteFailedException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.InsertFailedException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.UpdateFailedException;
import com.jaw.common.util.ErrorCodeUtil;
import com.jaw.core.service.IStandardCombinationService;
import com.jaw.framework.sessCache.SessionCache;

@Controller
public class StandardCombinationController {
	Logger logger = Logger.getLogger(StandardCombinationController.class);
	@Autowired
	IStandardCombinationService standardService;
	@Autowired
	ErrorCodeUtil errorCodeUtil;

	@RequestMapping(value = "/stdcomb", method = RequestMethod.GET)
	public String standardCombinationGet(
			@ModelAttribute("stdcomb") StandardCombinationVO standardCombination,
			HttpServletRequest httpServletRequest, HttpSession session,
			ModelMap model) throws UpdateFailedException, NoDataFoundException,
			DuplicateEntryException, DeleteFailedException {
		logger.info("Opening standard list");

		return "redirect:stdcomb.htm?Select=Select";
	}

	@RequestMapping(value = "/stdcomb", method = RequestMethod.POST, params = { "Save" })
	public String standardCombinationSave(
			@ModelAttribute("stdcomb") StandardCombinationVO standardCombination,
			HttpServletRequest httpServletRequest, HttpSession session,
			ModelMap map, final RedirectAttributes redirectAttributes)
			throws NoDataFoundException, DuplicateEntryException,
			InsertFailedException {
		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		standardService.insertStandard(standardCombination,
				sessionCache.getUserSessionDetails());
		httpServletRequest.setAttribute("stdcomb", standardCombination);
		redirectAttributes.addFlashAttribute("success",
				ErrorCodeConstant.ADD_SUCCESS_MESS);
		return "redirect:stdcomb.htm?Select=Select";

	}

	@RequestMapping(value = "/stdcomb", method = RequestMethod.GET, params = { "Delete" })
	public String standardCombinationDelete(
			@ModelAttribute("stdcomb") StandardCombinationVO standardCombination,
			HttpServletRequest httpServletRequest, HttpSession session,
			ModelMap map, final RedirectAttributes redirectAttributes)
			throws NoDataFoundException, DeleteFailedException {
		String id = httpServletRequest.getParameter("rowId");
		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		List<StandardCombinationListVO> standardCombinationVOs = (List<StandardCombinationListVO>) WebUtils
				.getSessionAttribute(httpServletRequest, "stdlist");
		StandardCombinationListVO standardCombinationList = standardCombinationVOs
				.get(Integer.parseInt(id));
		standardService.deleteStandard(standardCombinationList,
				sessionCache.getUserSessionDetails());
		httpServletRequest.setAttribute("stdcomb", standardCombination);
		redirectAttributes.addFlashAttribute("success",
				ErrorCodeConstant.DELETE_SUCCESS_MESS);
		return "redirect:stdcomb.htm?Select=Select";

	}

	@RequestMapping(value = "/stdcomb", method = RequestMethod.GET, params = { "Select" })
	public ModelAndView standardCombinationSelect(
			@ModelAttribute("stdcomb") StandardCombinationVO standardCombination,
			HttpServletRequest httpServletRequest, HttpSession session,
			ModelMap map) throws NoDataFoundException, DuplicateEntryException {
		Map stockParamMap = WebUtils.getParametersStartingWith(
				httpServletRequest, "d-");
		if (stockParamMap.size() == 0) {
			SessionCache sessionCache = (SessionCache) session
					.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
			standardService.selectAllStandard(standardCombination, sessionCache
					.getUserSessionDetails().getBranchId(), sessionCache
					.getUserSessionDetails().getInstId());
			WebUtils.setSessionAttribute(httpServletRequest, "stdlist",
					standardCombination.getStandardCombinationList());
			standardCombination.setPageNo("P1");
		}

		return new ModelAndView(".jaw.stdcomb");

	}

	@ExceptionHandler({ DuplicateEntryException.class,
			DuplicateEntryException.class, BatchUpdateFailedException.class,
			DeleteFailedException.class, InsertFailedException.class,
			NoDataFoundException.class })
	public ModelAndView handleException(Exception ex, HttpSession session,
			HttpServletRequest request) {
		StandardCombinationVO standardCombinationVO = new StandardCombinationVO();
		ModelAndView mav = new ModelAndView(".jaw.stdcomb", "stdcomb",
				standardCombinationVO);
		mav.getModelMap().addAttribute("error", ex.getMessage());
		return mav;

	}
}
