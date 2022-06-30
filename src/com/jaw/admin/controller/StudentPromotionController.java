package com.jaw.admin.controller;

import java.io.IOException;
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
import org.springframework.web.util.WebUtils;

import com.jaw.admin.service.ISMSConfigurationService;
import com.jaw.admin.service.IStudentPromotionService;
import com.jaw.common.constants.ApplicationConstant;
import com.jaw.common.constants.ErrorCodeConstant;
import com.jaw.common.constants.ModelAndViewConstant;
import com.jaw.common.dropdown.service.IDropDownListService;
import com.jaw.common.exceptions.DatabaseException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.StudentPromoteException;
import com.jaw.common.exceptions.UpdateFailedException;
import com.jaw.common.exceptions.batch.DuplicateEntryForHolGenException;
import com.jaw.common.exceptions.util.CommonCodeNotFoundException;
import com.jaw.core.controller.AcademicCalendarVO;
import com.jaw.core.controller.HolidayGenerationVO;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.sessCache.SessionCache;

@Controller
public class StudentPromotionController {
	// Logging
	Logger logger = Logger.getLogger(StudentPromotionController.class);

	@Autowired
	IDropDownListService dropDownListService;
	@Autowired
	IStudentPromotionService studentPromotionService;

	@RequestMapping(value = "/studentPromotion", method = RequestMethod.GET)
	public ModelAndView getStudentPromotionList(
			@ModelAttribute("studentProVo") StudentPromotionVO studentPromotionVO,
			ModelMap modelMap, HttpServletRequest httpServletRequest,
			HttpSession session) {

		ModelAndView mav = new ModelAndView(
				ModelAndViewConstant.STUDENT_PROMOTION, "studentProVo",
				studentPromotionVO);
		WebUtils.setSessionAttribute(httpServletRequest, "display_tbl", null);
		WebUtils.setSessionAttribute(httpServletRequest, "success", null);
		WebUtils.setSessionAttribute(httpServletRequest, "searchVo", null);
		return mav;
	}

	@RequestMapping(value = "/studentPromotion", method = RequestMethod.POST)
	public ModelAndView studentPromotionPost(
			@ModelAttribute("studentProVo") StudentPromotionVO studentPromotionVO,
			ModelMap model, HttpServletRequest httpServletRequest,
			HttpSession session, RedirectAttributes redirectAttribute)
			throws DuplicateEntryException, NoDataFoundException,
			CommonCodeNotFoundException, DatabaseException,
			UpdateFailedException, StudentPromoteException {

		logger.debug("inside student promoted method");

		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		ApplicationCache applicationCache = (ApplicationCache) session
				.getServletContext().getAttribute(
						ApplicationConstant.APPLICATION_CACHE);
		ModelAndView mav = new ModelAndView(
				ModelAndViewConstant.STUDENT_PROMOTION, "studentProVo",
				studentPromotionVO);
		httpServletRequest.setAttribute("page", mav);
		String threadResult = studentPromotionService.studentPromoted(
				studentPromotionVO, sessionCache, applicationCache);
		
		httpServletRequest.setAttribute("threadID", threadResult);
		logger.debug("Data's inserted successfully!");
		return mav;
	}

	// Ajax For getting Ac Term Date for Validation
	@RequestMapping(value = "/studentPromotion", method = RequestMethod.GET, params = { "acadTermStsValid" })
	public @ResponseBody
	int validateAcademicTermStatus(HttpSession session,
			HttpServletRequest request) {
		logger.debug("inside Student promotion Validation method");
		StudentPromotionVO studentPromotion = new StudentPromotionVO();
		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		studentPromotion.setFromAcademicTerm(request
				.getParameter("academicTerm"));
		studentPromotion.setBranchId(request.getParameter("branch"));

		int result = studentPromotionService.checkAcademicTermStatus(
				studentPromotion, sessionCache.getUserSessionDetails(),
				request.getParameter("FromOrTo"));
		System.out.println("controller resulttttttttt   : " + result);
		return result;
	}

	/*// Ajax For getting Ac Term Date for Validation
	@RequestMapping(value = "/studentPromotion", method = RequestMethod.GET, params = { "checkThreadID" })
	public @ResponseBody
	String retriveList(HttpSession session, HttpServletRequest request) {
		System.out.println("check Thread Id method");
		// threadIdValue;
		System.out.println("thread idddddddd "
				+ request.getParameter("threadIdValue"));
		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		String result = "";
		try {
			result = studentPromotionService.checkAcademicTermPromotiontatus(
					request.getParameter("threadIdValue"),
					request.getParameter("branchIdValue"), sessionCache);
		} catch (NoDataFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;
	}*/

	@ModelAttribute("branchList")
	public Map<String, String> gerCourseNameList(HttpSession session,
			HttpServletRequest httpSevletRequest, HttpServletResponse response,
			ModelMap model) throws IOException, NoDataFoundException {
		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		Map<String, String> map = dropDownListService
				.getBranchListTag(sessionCache.getUserSessionDetails());
		httpSevletRequest.setAttribute("branchList", map);
		return map;

	}

	@ModelAttribute("acTermList")
	public Map<String, String> gerAcaTrmList(HttpSession session,
			HttpServletRequest httpSevletRequest, HttpServletResponse response,
			ModelMap model) throws IOException {
		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		Map<String, String> map = null;

		try {
			map = dropDownListService.getAcademicTermListTag(sessionCache
					.getUserSessionDetails());
		} catch (NoDataFoundException e) {
			logger.error("Error occured in Academic Term Dropdown :"
					+ e.getMessage());
		}
		httpSevletRequest.setAttribute("acTermList", map);
		return map;

	}

	@ExceptionHandler({ DuplicateEntryException.class,
			NoDataFoundException.class, CommonCodeNotFoundException.class,
			DatabaseException.class, StudentPromoteException.class })
	public ModelAndView handleException(Exception ex, HttpSession session,
			HttpServletRequest request) {

		ModelAndView mav = (ModelAndView) request.getAttribute("page");
		mav.getModelMap().addAttribute("error", ex.getMessage());
		return mav;

	}
}
