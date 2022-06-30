package com.jaw.attendance.controller;



import java.util.ArrayList;
import java.util.List;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import com.jaw.attendance.service.IAttendanceService;
import com.jaw.common.constants.ApplicationConstant;
import com.jaw.common.constants.ModelAndViewConstant;
import com.jaw.common.dropdown.service.IDropDownListService;
import com.jaw.common.exceptions.DatabaseException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.util.PropertyNotFoundException;
import com.jaw.common.util.ErrorCodeUtil;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.sessCache.SessionCache;

//Academic Term Details Controller Classes
@Controller
public class ViewAttendanceReportController {
	// Logging
	Logger logger = Logger.getLogger(AttendanceMarkController.class);
	@Autowired
	IAttendanceService attendanceService;
	@Autowired
	IDropDownListService dropDownListService;
	@Autowired
	ErrorCodeUtil errorCodeUtil;

	@RequestMapping(value = "/subjectWiseAttendance", method = RequestMethod.GET)
	public ModelAndView getSubjectWiseAttendance(
			@ModelAttribute("attendance") AttendanceMasterVO attendanceMasterVO,
			ModelMap modelMap, HttpServletRequest httpServletRequest,HttpSession session) throws NoDataFoundException, PropertyNotFoundException {
		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		ApplicationCache applicationCache = (ApplicationCache) session
				.getAttribute(ApplicationConstant.APPLICATION_CACHE);
		ModelAndView mav = new ModelAndView(
				ModelAndViewConstant.SUBJECT_VIEW_ATTENDANCE, "attendance",
				attendanceMasterVO);
		logger.info("Rendering Attendance List page");
		httpServletRequest.setAttribute("page", mav);
		attendanceService.getSubjectWiseAttendance(applicationCache, sessionCache.getUserSessionDetails(), sessionCache.getStudentSession(),attendanceMasterVO);
		return mav;
	}

	
	
	/*@RequestMapping(value = "/consolidatedAttendance", method = RequestMethod.GET)
	public ModelAndView getConsolidatedAttendance(
			@ModelAttribute("attendance") AttendanceMasterVO attendanceMasterVO,
			ModelMap modelMap, HttpServletRequest httpServletRequest) {
		ModelAndView mav = new ModelAndView(
				ModelAndViewConstant.CONSOLIDATED_VIEW_ATTENDANCE, "attendance",
				attendanceMasterVO);
		logger.info("Rendering Attendance List page");
		// Null the session display tag values
		httpServletRequest.setAttribute("page", mav);
		return mav;
	}*/

	// Ajax 
		@RequestMapping(value = "/subjectWiseAttendance", method = RequestMethod.GET, params = { "subjectWithoutLab" })
		public @ResponseBody
		List<ViewAttendance> retriveSubjectAttendanceList(HttpSession session, HttpServletRequest request,@ModelAttribute("attendance") AttendanceMasterVO attendanceMasterVO) {
			String res = "result";

			
			List<ViewAttendance> lts = null;
			
			SessionCache sessionCache = (SessionCache) session
					.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
			ApplicationCache applicationCache = (ApplicationCache) session
					.getAttribute(ApplicationConstant.APPLICATION_CACHE);
		
		
			try {
				attendanceService.getSubjectWiseAttendance(applicationCache, sessionCache.getUserSessionDetails(), sessionCache.getStudentSession(),attendanceMasterVO);
			} catch (NoDataFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (PropertyNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("list sizeeee"+attendanceMasterVO.getStudentAttenList().size());
			lts=new ArrayList<ViewAttendance>();
			if(attendanceMasterVO.getViewAttenList().isEmpty()){
				
			}else{
				lts=attendanceMasterVO.getViewAttenList();
				/*for(StudentAttendance st:attendanceMasterVO.getStudentAttenList()){
					lts.add(st.getCrslId());
				}*/
			}
			return lts;
		}

		
	/*	// Ajax 
		@RequestMapping(value = "/consolidatedAttendance", method = RequestMethod.GET, params = { "consolidated" })
		public @ResponseBody
		List<ViewAttendance> retriveConsolidatedAttendanceList(HttpSession session, HttpServletRequest request,@ModelAttribute("attendance") AttendanceMasterVO attendanceMasterVO) {
			String res = "result";

			
			List<ViewAttendance> lts = null;
			
			SessionCache sessionCache = (SessionCache) session
					.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
			ApplicationCache applicationCache = (ApplicationCache) session
					.getAttribute(ApplicationConstant.APPLICATION_CACHE);
		
		
			try {
				attendanceService.getConsolidatedAttendance( sessionCache.getUserSessionDetails(), sessionCache.getStudentSession(),attendanceMasterVO);
			} catch (NoDataFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("list sizeeee"+attendanceMasterVO.getStudentAttenList().size());
			lts=new ArrayList<ViewAttendance>();
			if(attendanceMasterVO.getViewAttenList().isEmpty()){
				
			}else{
				lts=attendanceMasterVO.getViewAttenList();
				for(StudentAttendance st:attendanceMasterVO.getStudentAttenList()){
					lts.add(st.getCrslId());
				}
			}
			return lts;
		}*/
	@ExceptionHandler({
			NoDataFoundException.class, 	DatabaseException.class ,PropertyNotFoundException.class})
	public ModelAndView handleException(Exception ex, HttpSession session,
			HttpServletRequest request) {
		ModelAndView modelAndView = (ModelAndView) request.getAttribute("page");
		modelAndView.getModelMap().addAttribute("error", ex.getMessage());
		WebUtils.setSessionAttribute(request, "success", null);
		return modelAndView;
	}

}
