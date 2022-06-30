package com.jaw.staff.controller;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
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
import com.jaw.common.exceptions.DatabaseException;
import com.jaw.common.exceptions.DeleteFailedException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.FileNotFoundInDatabase;
import com.jaw.common.exceptions.FileNotSaveException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.util.PropertyNotFoundException;
import com.jaw.common.util.DateUtil;
import com.jaw.common.util.ErrorCodeUtil;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.sessCache.SessionCache;
import com.jaw.staff.service.IStaffInsertionService;

//Controller class for Admission
@Controller
public class StaffAdditionController {
	
	Logger logger = Logger.getLogger(StaffAdditionController.class);
	@Autowired
	IDropDownListService dropDownListService;
	@Autowired
	private IStaffInsertionService staffInsertionService;
	@Autowired
	ErrorCodeUtil errorCodeUtil;
	@Autowired
	DateUtil dateUtil;
	
	@RequestMapping(value = "/staffAddition", method = RequestMethod.GET)
	public ModelAndView insertMemDtls(
			@ModelAttribute("staff") StaffVo staffAdmissionVo,HttpServletRequest httpServletRequest) {
		ModelAndView modelAndView = new ModelAndView(".jaw.StaffAddition");
		WebUtils.setSessionAttribute(httpServletRequest, "display_tbl",
				null);
		WebUtils.setSessionAttribute(httpServletRequest, "display_tbl1",
				null);
		WebUtils.setSessionAttribute(httpServletRequest, "success",
				null);
		WebUtils.setSessionAttribute(httpServletRequest, "keepStatus",
				null);
		return modelAndView;
	}
	
	@RequestMapping(value = "/staffAddition", method = RequestMethod.POST)
	public String insertContact(
			@ModelAttribute("staff") StaffVo staffAdmissionVo,
			BindingResult result, HttpSession session,
			HttpServletRequest httpServletRequest, ModelMap model,
			RedirectAttributes redirectAttributes) throws IOException,
			FileNotFoundInDatabase, DuplicateEntryException, DatabaseException,
			NumberFormatException, PropertyNotFoundException, IllegalStateException, DeleteFailedException, FileNotSaveException {
		ApplicationCache applicationCache = (ApplicationCache) session
				.getServletContext().getAttribute(
						ApplicationConstant.APPLICATION_CACHE);
		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		httpServletRequest.setAttribute("admissionDetailsVO", staffAdmissionVo);
		System.out.println("staff admission user :"
				+ staffAdmissionVo.getIsUser());
		staffInsertionService.insertStaff(staffAdmissionVo,
				sessionCache.getUserSessionDetails(), applicationCache,session.getServletContext());
		
		redirectAttributes.addFlashAttribute("success",
				ErrorCodeConstant.ADD_SUCCESS_MESS);
		return "redirect:/staffAddition.htm";
		
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
	@ModelAttribute("menuprofile")
	public Map<String, String> gerCourseNameList(HttpSession session,
			HttpServletRequest httpSevletRequest, HttpServletResponse response,
			ModelMap model) throws IOException {
		Map<String, String> map = null;
		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		try {
			map = dropDownListService.selectMenuProfileList(ApplicationConstant.PG_STAFF, sessionCache.getUserSessionDetails());
		} catch (NoDataFoundException e) {

		}
		httpSevletRequest.setAttribute("menuprofile", map);
		return map;

	}
	@ExceptionHandler({
			DuplicateEntryException.class,
			PropertyNotFoundException.class,
			DeleteFailedException.class,
			FileNotSaveException.class
	})
	public ModelAndView handleException(Exception ex, HttpSession session,
			HttpServletRequest request) {
		
		StaffVo staffAdmissionVo = (StaffVo) request
				.getAttribute("admissionDetailsVO");
		
		ModelAndView mav = new ModelAndView(".jaw.StaffAddition", "staff",
				staffAdmissionVo);
		mav.addObject("staff", staffAdmissionVo);
		mav.getModelMap().addAttribute("error", ex.getMessage());
		
		return mav;
		
	}
}
