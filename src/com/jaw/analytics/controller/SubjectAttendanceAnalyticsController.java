package com.jaw.analytics.controller;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import com.jaw.analytics.service.IAnalyticAttendanceService;
import com.jaw.attendance.controller.ViewAttendance;
import com.jaw.attendance.controller.ViewAttendanceMasterVO;
import com.jaw.attendance.dao.IViewAttendanceDAO;
import com.jaw.attendance.dao.StudentAttendance;
import com.jaw.attendance.dao.ViewAttendanceKey;
import com.jaw.attendance.service.IViewAttendanceService;
import com.jaw.common.constants.ApplicationConstant;
import com.jaw.common.constants.ModelAndViewConstant;
import com.jaw.common.dropdown.service.IDropDownListService;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.util.PropertyNotFoundException;
import com.jaw.core.dao.CourseSubLink;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.sessCache.SessionCache;
import com.jaw.framework.sessCache.StudentSession;

@Controller
public class SubjectAttendanceAnalyticsController {
	// Logging
			Logger logger = Logger.getLogger(SubjectAttendanceAnalyticsController.class);
			@Autowired
			IAnalyticAttendanceService analyticAttendanceService;
			@Autowired
			IDropDownListService dropDownListService;
			

			@RequestMapping(value = "/subjectAttendance", method = RequestMethod.GET)
			public ModelAndView getAttendanceList(
					@ModelAttribute("attendanceViewSingStu") ViewAttendanceMasterVO viewAttendanceMasterVO,
					ModelMap modelMap, HttpServletRequest httpServletRequest,
					HttpSession session) throws NoDataFoundException {
				SessionCache sessionCache = (SessionCache) session
						.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);

				ModelAndView mav = new ModelAndView(
						ModelAndViewConstant.SUBJECT_VIEW_ATTENDANCE, "attendanceViewSingStu",
						viewAttendanceMasterVO);
				
				WebUtils.setSessionAttribute(httpServletRequest, "display_tbl", null);
				
				return mav;
			}
			/*
			@ModelAttribute("stGroupList")
			public Map<String, String> gerCourseNameList(HttpSession session,
					HttpServletRequest httpSevletRequest,ModelMap model)  {
				SessionCache sessionCache = (SessionCache) session
						.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
				Map<String, String> map = null;
				try {
					map = dropDownListService.getStGroupModelAttr(sessionCache
							.getUserSessionDetails());
				} catch (NoDataFoundException e) {
					logger.error("Error occured in Student group list Dropdown :" + e.getMessage());
				}
				httpSevletRequest.setAttribute("stGroupList", map);
				return map;

			}*/
			// Ajax For getting StudentGroups
			@RequestMapping(value = "/subjectAttendance", method = RequestMethod.GET, params = { "subject" })
			public @ResponseBody
			Set<ViewAttendance> getSubjectWiseAttendance(HttpSession session,
					HttpServletRequest request,HttpServletResponse response) throws PropertyNotFoundException {
				System.out.println("ajax controller");
				SessionCache sessionCache = (SessionCache) session
						.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
				
				String stGroup=request.getParameter("studentGroup");
				String stAdmisNo=request.getParameter("studentAdmisNo");		
				String subCrslId=request.getParameter("crslId");
				if ((sessionCache.getUserSessionDetails().getProfileGroup()
						.equals(ApplicationConstant.PG_STUDENT))) {
					stGroup=sessionCache.getStudentSession().getStuGrpId();
					stAdmisNo=sessionCache.getStudentSession().getStudentAdmisNo();
				} else if ((sessionCache.getUserSessionDetails().getProfileGroup()
						.equals(ApplicationConstant.PG_PARENT))) {
					StudentSession studentSession2=sessionCache.getParentSession().getStudentSession().get(
							Integer.parseInt(sessionCache.getParentSession().getSelectedStuIndex()));
					stGroup=studentSession2.getStuGrpId();
					stAdmisNo=studentSession2.getStudentAdmisNo();
				}
				
				
				System.out.println("ajax controller student group 1"+stGroup);
				System.out.println("ajax controller student admis no 1"+stAdmisNo);
				ViewAttendanceKey viewAttendanceKey=new ViewAttendanceKey();
				viewAttendanceKey.setAcTerm(sessionCache.getUserSessionDetails().getCurrAcTerm());
				viewAttendanceKey.setInstId(sessionCache.getUserSessionDetails().getInstId());
				viewAttendanceKey.setBranchId(sessionCache.getUserSessionDetails().getBranchId());
				viewAttendanceKey.setStudentAdmisNo(stAdmisNo);
				viewAttendanceKey.setStudentGrpId(stGroup);
				viewAttendanceKey.setCrslId(subCrslId.split("-")[0]);
				ApplicationCache applicationCache = (ApplicationCache) session
						.getServletContext().getAttribute(
								ApplicationConstant.APPLICATION_CACHE);
				
				Set<ViewAttendance> viewAttendanceList = null;
				try {
					viewAttendanceList = analyticAttendanceService.selectSubjecteAttendance(viewAttendanceKey,sessionCache.getUserSessionDetails(),applicationCache);
				} catch (NoDataFoundException e) {
					response.setStatus(400);
				}
				System.out.println("view attendance size  :"+viewAttendanceList.size());
				
				 return viewAttendanceList;
			}
			
			// Ajax For getting Subject List Staff
						@RequestMapping(value = "/subjectAttendance", method = RequestMethod.GET, params = { "subjectList" })
						public @ResponseBody
						List<CourseSubLink> getSubjectListForStudentGrpStaff(HttpSession session,
								HttpServletRequest request,HttpServletResponse response) throws PropertyNotFoundException {
							System.out.println("ajax controller");
							SessionCache sessionCache = (SessionCache) session
									.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
							ApplicationCache applicationCache = (ApplicationCache) session
									.getServletContext().getAttribute(
											ApplicationConstant.APPLICATION_CACHE);
							String stGroup=request.getParameter("studentGroup");
							List<CourseSubLink> subjectList=null;
							try {
								subjectList=dropDownListService.selectSubListForStudentGrpAnalytics(applicationCache,stGroup, sessionCache.getUserSessionDetails());
							} catch (NoDataFoundException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
							return subjectList;
						}
						// Ajax For getting Student List For staff
						@RequestMapping(value = "/subjectAttendance", method = RequestMethod.GET, params = { "studentList" })
						public @ResponseBody
						Map<String, String> getStudentListForStudentGrpStaff(HttpSession session,
								HttpServletRequest request,HttpServletResponse response) throws PropertyNotFoundException {
							System.out.println("ajax controller");
							SessionCache sessionCache = (SessionCache) session
									.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
							String stGroup=request.getParameter("studentGroup");
							String crslId=request.getParameter("crsl");
							String studentBatch=request.getParameter("studentBatch");
							
							Map<String, String> studentList=null;
							try {
								studentList=analyticAttendanceService.selectStudentListStaffAnalytic(sessionCache.getUserSessionDetails(), stGroup, crslId,studentBatch);
							} catch (NoDataFoundException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
							return studentList;
						}
}
