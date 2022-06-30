package com.jaw.core.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jaw.common.constants.ApplicationConstant;
import com.jaw.common.constants.PropertyManagementConstant;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.UpdateFailedException;
import com.jaw.common.exceptions.util.PropertyNotFoundException;
import com.jaw.common.util.ErrorCodeUtil;
import com.jaw.common.util.PropertyManagementUtil;
import com.jaw.core.service.IStandardCombinationService;
import com.jaw.core.service.IStandardSectionService;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.sessCache.SessionCache;

@Controller
public class StandardSectionController {

	Logger logger = Logger.getLogger(StandardSectionController.class);

	@Autowired
	IStandardCombinationService standardListService;
	@Autowired
	IStandardSectionService standardSectionService;
	@Autowired
	ErrorCodeUtil errorCodeUtil;
	@Autowired
	PropertyManagementUtil propertyManagementUtil;

	StandardCombinationVO standardCombinationVO = new StandardCombinationVO();
	StandardSectionVO standardSectionVO = new StandardSectionVO();

	@RequestMapping(value = "/stdsec", method = RequestMethod.GET)
	public String stdsec(
			@ModelAttribute("standardSection") StandardSectionVO standardSection,
			HttpServletRequest httpServletRequest, HttpSession session,
			ModelMap model) {

		logger.info("Opening standard list");

		return "redirect:stdsec.htm?Get=Get";

	}

	@RequestMapping(value = "/stdsec", method = RequestMethod.GET, params = { "Get" })
	public ModelAndView stdsecSearch(
			@ModelAttribute("standardSection") StandardSectionVO standardSection,
			HttpServletRequest httpServletRequest, HttpSession session,
			ModelMap model) throws NoDataFoundException,
			PropertyNotFoundException {
		logger.info("Opening standard list");

		ApplicationCache applicationCache = (ApplicationCache) session
				.getServletContext().getAttribute(
						ApplicationConstant.APPLICATION_CACHE);
		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);

		standardSection.setIsSingleMedium(propertyManagementUtil
				.getPropertyValue(applicationCache, sessionCache
						.getUserSessionDetails().getInstId(), sessionCache
						.getUserSessionDetails().getBranchId(),
						PropertyManagementConstant.SINGLE_MEDIUM));

		standardListService.selectAllStandard(standardCombinationVO,
				sessionCache.getUserSessionDetails().getBranchId(),
				sessionCache.getUserSessionDetails().getInstId());

		standardSectionService.selectAllSection(standardSectionVO,
				sessionCache.getUserSessionDetails());
		model.addAttribute("list",
				standardCombinationVO.getStandardCombinationList());
		model.addAttribute("stdsec",
				standardSectionVO.getStandardSectionVOList());
		return new ModelAndView(".jaw.stdsec");

	}

	@RequestMapping(value = "/stdsec", method = RequestMethod.POST, params = { "Save" })
	public String stdsecpost(
			@ModelAttribute("standardSection") StandardSectionVO standardSection,
			HttpServletRequest httpServletRequest, HttpSession session,
			ModelMap model, final RedirectAttributes redirectAttributes)
			throws NoDataFoundException, UpdateFailedException {

		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);

		ArrayList<String> stdlist = new ArrayList<String>();
		ArrayList<String> comblist = new ArrayList<String>();
		List<List<String>> seclist = new ArrayList<List<String>>();

		if (standardSection.getSeclist() != null) {
			for (String string : standardSection.getSeclist()) {
				if (string != null) {
					List<String> split = (List<String>) Arrays.asList(string
							.split(","));
					seclist.add(split);
				}
			}
		}

		logger.debug("inside save method");
		for (StandardCombinationListVO standardCombination : standardCombinationVO
				.getStandardCombinationList()) {
			stdlist.add(standardCombination.getStandardId());
			comblist.add(standardCombination.getCombinationId());
		}
		standardSection.setStdlist(stdlist);
		standardSection.setComblist(comblist);
		standardSection.setStdseclist(seclist);

		standardSectionService.insertStandard(standardSection,
				sessionCache.getUserSessionDetails());

		redirectAttributes
				.addFlashAttribute("standardSection", standardSection);
		return "redirect:stdsec.htm";
	}
}
