package com.jaw.core.controller;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jaw.common.constants.ApplicationConstant;
import com.jaw.common.constants.ErrorCodeConstant;
import com.jaw.common.constants.ModelAndViewConstant;
import com.jaw.common.dropdown.service.IDropDownListService;
import com.jaw.common.exceptions.DatabaseException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.batch.DuplicateEntryForHolGenException;
import com.jaw.core.service.IHolidayMaintenanceService;
import com.jaw.core.service.IStudentGroupMasterService;
import com.jaw.framework.sessCache.SessionCache;

@Controller
public class HolidayGenerationController {
	// Logging
	Logger logger = Logger.getLogger(HolidayGenerationController.class);
	@Autowired
	IDropDownListService dropDownListService;
	@Autowired
	IHolidayMaintenanceService holidayMaintenanceService;

	@RequestMapping(value = "/holidayGeneration", method = RequestMethod.GET)
	public ModelAndView viewHolidayGeneration(
			@ModelAttribute("holidayM") HolidayGenerationVO holidayGenerationVO) {
		ModelAndView mav = new ModelAndView(
				ModelAndViewConstant.HOLIDAY_GENERATION);
		return mav;
	}

	

	@RequestMapping(value = "/holidayGeneration", method = RequestMethod.POST)
	public String generateHoliday(
			@ModelAttribute("holidayM") HolidayGenerationVO holidayGenerationVO,
			ModelMap model, HttpServletRequest httpServletRequest,
			HttpSession session, RedirectAttributes redirectAttribute)
			throws DuplicateEntryForHolGenException, DuplicateEntryException, DatabaseException {

		logger.debug("inside holiday generation method");
		
		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		ModelAndView modelAndView = new ModelAndView(
				ModelAndViewConstant.HOLIDAY_GENERATION, "holidayM", holidayGenerationVO);
		httpServletRequest.setAttribute("page", modelAndView);
		holidayMaintenanceService.insertHolidayMaintenanceBatchRec(holidayGenerationVO,
				sessionCache.getUserSessionDetails());
		redirectAttribute.addFlashAttribute("success",
				ErrorCodeConstant.HOLIDAY_GEN_SUCCESS);
		
		logger.debug("Data's inserted successfully!");
		return "redirect:/holidayGeneration.htm";
	}

	// Ajax For getting AC Term
		@RequestMapping(value = "/holidayGeneration", method = RequestMethod.GET, params = { "branchAcTrm" })
		public @ResponseBody
		List<String> retriveList(HttpSession session, HttpServletRequest request) {	
			SessionCache sessionCache = (SessionCache) session
					.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
			List<String> lts = null;
				lts = holidayMaintenanceService.selectAcTrmForHlyGen(sessionCache.getUserSessionDetails(), request.getParameter("branch"));
			
			return lts;
		}
	
	@ModelAttribute("branchList")
	public Map<String, String> gerCourseNameList(HttpSession session,
			HttpServletRequest httpSevletRequest, HttpServletResponse response,
			ModelMap model) throws IOException, NoDataFoundException {
		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		Map<String, String> map = dropDownListService.getBranchListTag(sessionCache.getUserSessionDetails());
		httpSevletRequest.setAttribute("branchList", map);
		return map;

	}
	
	@ExceptionHandler({ DuplicateEntryForHolGenException.class,DuplicateEntryException.class, DatabaseException.class })
	public ModelAndView handleException(Exception ex, HttpSession session,
			HttpServletRequest request) {

		ModelAndView mav = (ModelAndView) request.getAttribute("page");
		mav.getModelMap().addAttribute("error", ex.getMessage());
		return mav;

	}
}
