package com.jaw.attendance.controller;

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
import org.springframework.web.util.WebUtils;

import com.jaw.attendance.dao.StudentAttendanceList;
import com.jaw.attendance.service.IAttendanceService;
import com.jaw.common.constants.ApplicationConstant;
import com.jaw.common.constants.ErrorCodeConstant;
import com.jaw.common.constants.ModelAndViewConstant;
import com.jaw.common.dropdown.service.IDropDownListService;
import com.jaw.common.exceptions.BatchUpdateFailedException;
import com.jaw.common.exceptions.DatabaseException;
import com.jaw.common.exceptions.DeleteFailedException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.NoRecordSelectedException;
import com.jaw.common.exceptions.UpdateFailedException;
import com.jaw.common.exceptions.batch.AttendanceExistException;
import com.jaw.common.exceptions.batch.DateIsHolidayException;
import com.jaw.common.exceptions.batch.DuplicateEntryForHolGenException;
import com.jaw.common.exceptions.batch.FutureDateException;
import com.jaw.common.exceptions.util.CommonCodeNotFoundException;
import com.jaw.common.exceptions.util.PropertyNotFoundException;
import com.jaw.common.util.ErrorCodeUtil;
import com.jaw.core.controller.AcademicCalendarMasterVO;
import com.jaw.core.controller.AcademicCalendarVO;
import com.jaw.core.controller.SpecialClassMasterVO;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.sessCache.SessionCache;

//Academic Term Details Controller Classes
@Controller
public class AttendanceMarkController {
	// Logging
	Logger logger = Logger.getLogger(AttendanceMarkController.class);
	@Autowired
	IAttendanceService attendanceService;
	@Autowired
	IDropDownListService dropDownListService;
	@Autowired
	ErrorCodeUtil errorCodeUtil;

	@RequestMapping(value = "/attendanceAdd", method = RequestMethod.GET)
	public ModelAndView getAttendance(
			@ModelAttribute("attendance") AttendanceMasterVO attendanceMasterVO,
			ModelMap modelMap, HttpServletRequest httpServletRequest) {
		ModelAndView mav = new ModelAndView(
				ModelAndViewConstant.MARK_ATTENDANCE, "attendance",
				attendanceMasterVO);
		logger.info("Rendering Attendance List page");
		// Null the session display tag values

		WebUtils.setSessionAttribute(httpServletRequest, "display_tbl", null);
		WebUtils.setSessionAttribute(httpServletRequest, "success", null);
		WebUtils.setSessionAttribute(httpServletRequest, "keepStatus",
				null);
		WebUtils.setSessionAttribute(httpServletRequest, "searchVo",
				null);
		mav.getModelMap().addAttribute("status", "true");
		return mav;
	}

	// Get list of STudentce
	@RequestMapping(value = "/attendanceAdd", method = RequestMethod.GET, params = { "Get" })
	public String viewStudentsList(
			@ModelAttribute("attendance") AttendanceMasterVO attendanceMasterVO,
			HttpServletRequest httpRequest,
			RedirectAttributes redirectAttributes, HttpSession session)
			throws NoDataFoundException, AttendanceExistException, FutureDateException, DateIsHolidayException {		
		WebUtils.setSessionAttribute(httpRequest, "success", null);
		// Getting the display tag parameter
		Map stockParamMap = WebUtils.getParametersStartingWith(httpRequest,
				"d-");
		if ((attendanceMasterVO.getAttendanceSearchVO().getCrslId() != "")
				&& (attendanceMasterVO.getAttendanceSearchVO().getCrslId() != null)) {
			if (attendanceMasterVO.getAttendanceSearchVO().getCrslId()
					.contains(",")) {
				String parts[] = attendanceMasterVO.getAttendanceSearchVO()
						.getCrslId().split(",");
				attendanceMasterVO.getAttendanceSearchVO().setCrslId(parts[0]);
			}
		}

		AttendanceSearchVO attendanceSearchVO = attendanceMasterVO
				.getAttendanceSearchVO();
		WebUtils.setSessionAttribute(httpRequest, "searchVo",
				attendanceSearchVO);
		if (httpRequest.getParameter("Get").equals("Get")) {
			WebUtils.setSessionAttribute(httpRequest, "keepStatus",
					null);
			WebUtils.setSessionAttribute(httpRequest, "success", null);
		}
		else {
			WebUtils.setSessionAttribute(httpRequest, "keepStatus",
					"status");
		}
		// Setting model and view for exception handler
		ModelAndView modelAndView = new ModelAndView(
				ModelAndViewConstant.MARK_ATTENDANCE, "attendance",
				attendanceMasterVO);
		httpRequest.setAttribute("page", modelAndView);
		httpRequest.setAttribute("errors", "error");
		// Check whether the list already get or have to fetch from data base
		// using the list size
		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);

		if (stockParamMap.size() == 0) {

			logger.info("Table operation has been triggered");

			AttendanceSearchVO atMrVo = (AttendanceSearchVO) WebUtils
					.getSessionAttribute(httpRequest, "searchVo");
			System.out.println("searchVo vo  brfore service:" + atMrVo);
			// Get list from database
			List<StudentAttendanceList> list = attendanceService
					.getStudentListForAttendance(attendanceMasterVO,
							sessionCache.getUserSessionDetails());
			// Set the list to session to access in jsp
			WebUtils.setSessionAttribute(httpRequest, "display_tbl", list);
			attendanceMasterVO.setPageSize(
					attendanceMasterVO.getPageSize());

		}

	
		return "redirect:/attendanceAdd.htm?data";

	}



	@RequestMapping(value = "/attendanceAdd", method = RequestMethod.GET, params = { "data" })
	public ModelAndView staffListBack(
			@ModelAttribute("attendance") AttendanceMasterVO attendanceMasterVO,
			HttpServletRequest httpServletRequest) {
	
		AttendanceSearchVO atMrVo = (AttendanceSearchVO) WebUtils
				.getSessionAttribute(httpServletRequest, "searchVo");		
		attendanceMasterVO.setAttendanceSearchVO(atMrVo);

		List<StudentAttendanceList> stuAttList = (List<StudentAttendanceList>) WebUtils
				.getSessionAttribute(httpServletRequest, "display_tbl");
		String[] remarksValue = httpServletRequest
				.getParameterValues("remarks");
		String[] attendanceValue = httpServletRequest
				.getParameterValues("attendance");
		String[] rowidsValue = httpServletRequest
				.getParameterValues("rowids");		
		
		if (stuAttList != null) {
			if ((attendanceValue != null)) {				
				for (int j = 0; j < attendanceValue.length; j++) {					
					int absentValue = Integer.parseInt(attendanceValue[j]);
					stuAttList.get(absentValue).setAttendanceAbsent("A");				
				}

			}
			if ((remarksValue != null)) {				
				for (int j = 0; j < remarksValue.length; j++) {					
					int absentValue = Integer.parseInt(rowidsValue[j]);
					stuAttList.get(absentValue).setRemarks(remarksValue[j]);				
				}

			}
		}

		ModelAndView modelAndView = new ModelAndView(
				ModelAndViewConstant.MARK_ATTENDANCE, "attendance",
				attendanceMasterVO);
		String keepstat = (String) WebUtils.getSessionAttribute(
				httpServletRequest, "keepStatus");
		System.out.println("Keep status  :" + keepstat);
		if (keepstat != null) {
			modelAndView.getModelMap().addAttribute("status", "false");
		} else {
			modelAndView.getModelMap().addAttribute("status", "true");
		}
		return modelAndView;
	}

	// Save Method
	@RequestMapping(value = "/attendanceAdd", method = RequestMethod.GET, params = {
			"data", "actionSave" })
	public String saveAttendance(
			@ModelAttribute("attendance") AttendanceMasterVO attendanceMasterVO,
			ModelMap model, HttpServletRequest httpServletRequest,
			HttpSession session) throws DatabaseException,
			DuplicateEntryException, PropertyNotFoundException, NoDataFoundException, UpdateFailedException {
		ApplicationCache applicationCache = (ApplicationCache) session
				.getServletContext().getAttribute(
						ApplicationConstant.APPLICATION_CACHE);
		
		AttendanceSearchVO atMrVo = (AttendanceSearchVO) WebUtils
				.getSessionAttribute(httpServletRequest, "searchVo");
		attendanceMasterVO.setAttendanceSearchVO(atMrVo);
		ModelAndView modelAndView = new ModelAndView(
				ModelAndViewConstant.MARK_ATTENDANCE, "attendance",
				attendanceMasterVO);
		httpServletRequest.setAttribute("page", modelAndView);
		// System.out.println("valueeeeee>>>>>>>>>>>>>>>>>>>"+atMrVo.toString());
		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		
		String[] remarksValue = httpServletRequest
				.getParameterValues("remarks");
		String[] attendanceValue = httpServletRequest
				.getParameterValues("attendance");
		String[] rowidsValue = httpServletRequest
				.getParameterValues("rowids");		
		List<StudentAttendanceList> stuAttList = (List<StudentAttendanceList>) WebUtils
				.getSessionAttribute(httpServletRequest, "display_tbl");
		if (stuAttList != null) {
			if ((attendanceValue != null)) {				
				for (int j = 0; j < attendanceValue.length; j++) {					
					int absentValue = Integer.parseInt(attendanceValue[j]);
					stuAttList.get(absentValue).setAttendanceAbsent("A");				
				}

			}
			if ((remarksValue != null)) {				
				for (int j = 0; j < remarksValue.length; j++) {					
					int absentValue = Integer.parseInt(rowidsValue[j]);
					stuAttList.get(absentValue).setRemarks(remarksValue[j]);				
				}

			}
		}
		
		
		attendanceService.markAttendance(stuAttList, sessionCache.getUserSessionDetails(),
				attendanceMasterVO,applicationCache);		
		WebUtils.setSessionAttribute(httpServletRequest, "success",
				ErrorCodeConstant.ATTENDANCE_MARK_SUCCESS);
		logger.debug("Attendance has been marked successfully!");
		WebUtils.setSessionAttribute(httpServletRequest, "keepStatus", "status");
		return "redirect:/attendanceAdd.htm?clear";
	}

	@RequestMapping(value = "/attendanceAdd", method = RequestMethod.GET, params = { "clear" })
	public ModelAndView getAttendanceClear(
			@ModelAttribute("attendance") AttendanceMasterVO attendanceMasterVO,
			ModelMap modelMap, HttpServletRequest httpServletRequest) {

		AttendanceSearchVO atMrVo = (AttendanceSearchVO) WebUtils
				.getSessionAttribute(httpServletRequest, "searchVo");
		attendanceMasterVO.setAttendanceSearchVO(atMrVo);
		ModelAndView mav = new ModelAndView(
				ModelAndViewConstant.MARK_ATTENDANCE, "attendance",
				attendanceMasterVO);

		
		WebUtils.setSessionAttribute(httpServletRequest, "display_tbl", null);
		return mav;
	}

	// Ajax For getting Sub Id,Opt Sub Id
	@RequestMapping(value = "/attendanceAdd", method = RequestMethod.GET, params = { "stdGrpID" })
	public @ResponseBody
	List<String> retriveList(HttpSession session, HttpServletRequest request) {
		String res = "result";

		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		List<String> lts = null;

		lts = attendanceService.selectSubjectListForAtt(
				request.getParameter("studentGrpId"),
				sessionCache.getUserSessionDetails());
		System.out.println("list size in controller" + lts.size());

		return lts;
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

	

	// Ajax For Ac Term Status Validation
	@RequestMapping(value = "/attendanceAdd", method = RequestMethod.GET, params = { "holDateValidForAtt" })
	public @ResponseBody
	int CheckDateIsHoliday(HttpSession session, HttpServletRequest request) {
		System.out.println("holiday dateeeeeeeeee");
		logger.debug("inside Holiday Check Validation method");
		String res = null;

		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		int f = attendanceService.checkDateIsHoliday(
				(String) request.getParameter("academicTerm"),
				(String) request.getParameter("holDate"),
				(String) request.getParameter("studentGrpId"),
				sessionCache.getUserSessionDetails(),
				(String) request.getParameter("crslId"));
System.out.println("fffffffffffff"+f);
		return f;
	}

	@ExceptionHandler({ DuplicateEntryException.class,
			DeleteFailedException.class, UpdateFailedException.class,
			 CommonCodeNotFoundException.class,
			DatabaseException.class, BatchUpdateFailedException.class,PropertyNotFoundException.class
			})
	public ModelAndView handleException(Exception ex, HttpSession session,
			HttpServletRequest request) {
		ModelAndView modelAndView = (ModelAndView) request.getAttribute("page");
		modelAndView.getModelMap().addAttribute("error", ex.getMessage());
		WebUtils.setSessionAttribute(request, "success", null);
		return modelAndView;
	}
	@ExceptionHandler({ 
		NoDataFoundException.class, 
		AttendanceExistException.class,FutureDateException.class, DateIsHolidayException.class  })
public ModelAndView handleNoDataAndExistException(Exception ex, HttpSession session,
		HttpServletRequest request) {
	ModelAndView modelAndView = (ModelAndView) request.getAttribute("page");
	modelAndView.getModelMap().addAttribute("error", ex.getMessage());
	WebUtils.setSessionAttribute(request, "success", null);
	WebUtils.setSessionAttribute(request, "display_tbl", null);
	return modelAndView;
}

}
