package com.jaw.attendance.controller;

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
import org.springframework.web.util.WebUtils;

import com.jaw.attendance.service.IViewAttendanceService;
import com.jaw.common.constants.ApplicationConstant;
import com.jaw.common.constants.ModelAndViewConstant;
import com.jaw.common.dropdown.service.IDropDownListService;
import com.jaw.common.exceptions.BatchUpdateFailedException;
import com.jaw.common.exceptions.DatabaseException;
import com.jaw.common.exceptions.DeleteFailedException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.NoRecordSelectedException;
import com.jaw.common.exceptions.UpdateFailedException;
import com.jaw.common.exceptions.util.CommonCodeNotFoundException;
import com.jaw.common.exceptions.util.PropertyNotFoundException;
import com.jaw.common.util.ErrorCodeUtil;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.sessCache.SessionCache;

//Academic Term Details Controller Classes
@Controller
public class ViewAttendanceController {
	// Logging
	Logger logger = Logger.getLogger(ViewAttendanceController.class);
	@Autowired
	IViewAttendanceService viewAttendanceService;
	@Autowired
	IDropDownListService dropDownListService;
	@Autowired
	ErrorCodeUtil errorCodeUtil;

	@RequestMapping(value = "/attendanceViewSingStu", method = RequestMethod.GET)
	public String getAttendanceList(
			@ModelAttribute("attendanceViewSingStu") ViewAttendanceMasterVO viewAttendanceMasterVO,
			ModelMap modelMap, HttpServletRequest httpServletRequest,
			HttpSession session) throws NoDataFoundException {
		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);

		System.out.println("admis no:" + sessionCache.getStudentSession());
		System.out.println("student session :"+sessionCache.getStudentSession());
		System.out.println("parent session :"+sessionCache.getParentSession());
		String returnValue = "";
		if ((sessionCache.getStudentSession() == null)
				&& (sessionCache.getParentSession() == null)) {
			returnValue = "redirect:/attendanceViewSingStu.htm?data";

		} else {
			returnValue = "redirect:/attendanceViewSingStu.htm?Get";
		}

		viewAttendanceService.getMonth(viewAttendanceMasterVO,
				sessionCache.getUserSessionDetails());
		WebUtils.setSessionAttribute(httpServletRequest, "monthList",
				viewAttendanceMasterVO.getMonthList());
		WebUtils.setSessionAttribute(httpServletRequest, "display_tbl", null);

		return returnValue;
	}

	@RequestMapping(value = "/attendanceViewSingStu", method = RequestMethod.GET, params = "Get")
	public String viewAttendanceLinkingList(

			@ModelAttribute("attendanceViewSingStu") ViewAttendanceMasterVO viewAttendanceMasterVO,
			ModelMap model, HttpServletRequest httpServletRequest,
			HttpSession session, RedirectAttributes redirectAttribute)
			throws DuplicateEntryException, DatabaseException,
			NoDataFoundException, PropertyNotFoundException {
		WebUtils.setSessionAttribute(httpServletRequest, "message", null);
		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		ApplicationCache applicationCache = (ApplicationCache) session
				.getServletContext().getAttribute(
						ApplicationConstant.APPLICATION_CACHE);
		Map stockParamMap = WebUtils.getParametersStartingWith(
				httpServletRequest, "d-");

		// Setting model and view for exception handler
		WebUtils.setSessionAttribute(httpServletRequest, "searchVo",
				viewAttendanceMasterVO.getViewAttendanceSearchVO());
		System.out.println("student session 1:"+sessionCache.getStudentSession());
		System.out.println("parent session 1:"+sessionCache.getParentSession());
		ModelAndView mav = null;
		if ((sessionCache.getStudentSession() == null)
				&& (sessionCache.getParentSession() == null)) {
			mav = new ModelAndView(
					ModelAndViewConstant.ATTENDANCE_VIEW_SING_STU,
					"attendanceViewSingStu", viewAttendanceMasterVO);
		} else {
			mav = new ModelAndView(
					ModelAndViewConstant.DASHBOARD_VIEW_ATTENDANCE,
					"attendanceViewSingStu", viewAttendanceMasterVO);
		}

		httpServletRequest.setAttribute("page", mav);
		httpServletRequest.setAttribute("errors", "error");

		if (stockParamMap.size() == 0) {

			logger.info("Table operation has been triggered");

			// Get list from database

			viewAttendanceService.selectViewAttendanceData(
					viewAttendanceMasterVO,
					sessionCache.getUserSessionDetails(), applicationCache,
					sessionCache.getStudentSession(),sessionCache.getParentSession());

			// Set the list to session to access in jsp

			WebUtils.setSessionAttribute(httpServletRequest, "display_tbl",
					viewAttendanceMasterVO.getViewAttendanceListVOs());

		}
		System.out.println("In controller :"
				+ viewAttendanceMasterVO.getViewAttendanceListVOs().size());

		WebUtils.setSessionAttribute(httpServletRequest, "stgroup",
				viewAttendanceMasterVO.getViewAttendanceSearchVO()
						.getStudentGroupId());
		WebUtils.setSessionAttribute(httpServletRequest, "acTerm",
				viewAttendanceMasterVO.getViewAttendanceSearchVO()
						.getAcademicTerm());
		redirectAttribute.addFlashAttribute("attendanceViewSingStu",
				viewAttendanceMasterVO);
		return "redirect:/attendanceViewSingStu.htm?data";
	}

	@RequestMapping(value = "/attendanceViewSingStu", method = RequestMethod.GET, params = "subject")
	public ModelAndView viewSubjectAttendanceList(
			@ModelAttribute("attendanceViewSingStu") ViewAttendanceMasterVO viewAttendanceMasterVO,
			ModelMap model, HttpServletRequest httpServletRequest,
			HttpSession session, RedirectAttributes redirectAttribute)
			throws DuplicateEntryException, DatabaseException,
			NoDataFoundException {
		ApplicationCache applicationCache = (ApplicationCache) session
				.getServletContext().getAttribute(
						ApplicationConstant.APPLICATION_CACHE);
		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		String admisNo = httpServletRequest.getParameter("admisNo");
		String date = httpServletRequest.getParameter("date");
		viewAttendanceMasterVO.getViewAttendanceSearchVO().setAdmissionNumber(
				admisNo);
		viewAttendanceMasterVO.getViewAttendanceSearchVO().setDate(date);
		ModelAndView mav = null;
		if ((sessionCache.getStudentSession() == null)
				&& (sessionCache.getParentSession() == null)) {
			mav = new ModelAndView(
					ModelAndViewConstant.ATTENDANCE_VIEW_SING_STU,
					"attendanceViewSingStu", viewAttendanceMasterVO);
		} else {
			mav = new ModelAndView(
					ModelAndViewConstant.DASHBOARD_VIEW_ATTENDANCE,
					"attendanceViewSingStu", viewAttendanceMasterVO);
			viewAttendanceMasterVO.getViewAttendanceSearchVO()
					.setAdmissionNumber(
							sessionCache.getStudentSession()
									.getStudentAdmisNo());
		}
		viewAttendanceService.selectSubjectAttendance(applicationCache,
				viewAttendanceMasterVO, sessionCache.getUserSessionDetails(),
				sessionCache.getStudentSession(),sessionCache.getParentSession());

		return mav;
	}

	@RequestMapping(value = "/attendanceViewSingStu", method = RequestMethod.GET, params = "ajax")
	public @ResponseBody
	List<ViewAttendanceListVO> viewSubjectAttendanceListAjax(
			@ModelAttribute("attendanceViewSingStu") ViewAttendanceMasterVO viewAttendanceMasterVO,
			ModelMap model, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, HttpSession session,
			RedirectAttributes redirectAttribute) {
		ApplicationCache applicationCache = (ApplicationCache) session
				.getServletContext().getAttribute(
						ApplicationConstant.APPLICATION_CACHE);
		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		String admisNo = httpServletRequest.getParameter("admisNo");
		String date = httpServletRequest.getParameter("date");

		String stGroup = httpServletRequest.getParameter("stGroup");
		viewAttendanceMasterVO.getViewAttendanceSearchVO().setAdmissionNumber(
				admisNo);
		viewAttendanceMasterVO.getViewAttendanceSearchVO().setDate(date);
		viewAttendanceMasterVO.getViewAttendanceSearchVO().setStudentGroupId(
				stGroup);
		try {
			viewAttendanceService.selectSubjectAttendance(applicationCache,
					viewAttendanceMasterVO,
					sessionCache.getUserSessionDetails(),
					sessionCache.getStudentSession(),sessionCache.getParentSession());
			System.out.println("ajax return :"
					+ viewAttendanceMasterVO.getSubjectAttendanceList());
			return viewAttendanceMasterVO.getSubjectAttendanceList();
		} catch (NoDataFoundException e) {
			httpServletResponse.setStatus(400);
		}
		return null;

	}

	@RequestMapping(value = "/attendanceViewSingStu", method = RequestMethod.GET, params = "data")
	public ModelAndView getAttendanceListData(
			@ModelAttribute("attendanceViewSingStu") ViewAttendanceMasterVO viewAttendanceMasterVO,
			ModelMap modelMap, HttpServletRequest httpServletRequest,
			HttpSession session) {
		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		ModelAndView mav = null;
		if ((sessionCache.getStudentSession() == null)
				&& (sessionCache.getParentSession() == null)) {
			mav = new ModelAndView(
					ModelAndViewConstant.ATTENDANCE_VIEW_SING_STU,
					"attendanceViewSingStu", viewAttendanceMasterVO);
		} else {
			mav = new ModelAndView(
					ModelAndViewConstant.DASHBOARD_VIEW_ATTENDANCE,
					"attendanceViewSingStu", viewAttendanceMasterVO);
		}
		return mav;
	}

	@ModelAttribute("stGroupList")
	public Map<String, String> gerCourseNameList(HttpSession session,
			HttpServletRequest httpSevletRequest, ModelMap model) {
		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		Map<String, String> map = null;
		try {
			map = dropDownListService.getStGroupModelAttr(sessionCache
					.getUserSessionDetails());
		} catch (NoDataFoundException e) {
			logger.error("Error occured in Student group list Dropdown :"
					+ e.getMessage());
		}
		httpSevletRequest.setAttribute("stGroupList", map);
		return map;

	}

	// Ajax For getting StudentGroups
	@RequestMapping(value = "/ajaxCall", method = RequestMethod.GET, params = { "studAdmisNo" })
	public @ResponseBody
	Map<String, String> retriveStudentGroupList(HttpSession session,
			HttpServletRequest request, HttpServletResponse response) {
		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);

		String stGroup = request.getParameter("stGroup");
		Map<String, String> map = null;

		try {
			map = dropDownListService.getStudentAdmisNo(
					sessionCache.getUserSessionDetails(), stGroup);
			System.out.println("Map list :" + map.size());
		} catch (NoDataFoundException e) {
			response.setStatus(400);
		}
		return map;
	}

	@ExceptionHandler({ DuplicateEntryException.class,
			DeleteFailedException.class, UpdateFailedException.class,
			NoDataFoundException.class, CommonCodeNotFoundException.class,
			DatabaseException.class, BatchUpdateFailedException.class,
			NoRecordSelectedException.class })
	public ModelAndView handleException(Exception ex, HttpSession session,
			HttpServletRequest request) {
		ModelAndView modelAndView = (ModelAndView) request.getAttribute("page");
		modelAndView.getModelMap().addAttribute("error", ex.getMessage());
		return modelAndView;
	}

}
