package com.jaw.login.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.jaw.analytics.service.IAnalyticAttendanceService;
import com.jaw.attendance.controller.ViewAttendance;
import com.jaw.attendance.dao.ViewAttendanceKey;
import com.jaw.common.constants.ApplicationConstant;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.communication.controller.AlertVO;
import com.jaw.communication.controller.NoticeBoardVO;
import com.jaw.communication.dao.AlertListKey;
import com.jaw.communication.dao.NoticeBoard;
import com.jaw.communication.dao.NoticeBoardListKey;
import com.jaw.communication.service.IAlertService;
import com.jaw.communication.service.INoticeBoardService;
import com.jaw.core.controller.AcademicCalendarController;
import com.jaw.core.controller.AcademicCalendarMasterVO;
import com.jaw.core.controller.AcademicCalendarVO;
import com.jaw.core.dao.AcademicCalendarListKey;
import com.jaw.core.service.IAcademicCalendarService;
import com.jaw.core.service.IHolidayMaintenanceService;
import com.jaw.framework.sessCache.SessionCache;
import com.jaw.framework.sessCache.StudentSession;
import com.jaw.login.service.IDashboardService;

@Controller
public class DashboardController {
	// Logging
	Logger logger = Logger.getLogger(DashboardController.class);
	@Autowired
	IAcademicCalendarService academicCalendarService;
	@Autowired
	IHolidayMaintenanceService holidayMaintenanceService;
	@Autowired
	INoticeBoardService noticeBoardService;
	@Autowired
	IDashboardService dashboardService;
	@Autowired
	IAnalyticAttendanceService analyticAttendanceService;
	@Autowired
	IAlertService alertService;

	@RequestMapping(value = "/dashboard", method = RequestMethod.GET)
	public ModelAndView renderDashboard(HttpServletRequest request,
			HttpSession session) {

		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		ModelAndView mav = new ModelAndView(".jaw.dashboard");

		if (sessionCache.getUserSessionDetails().getProfileGroup()
				.equals(ApplicationConstant.PG_PARENT)) {
			List<StudentSession> stuSession = sessionCache.getParentSession()
					.getStudentSession();
			mav.getModelMap().addAttribute("listOfChildren", stuSession);
			// Swap between children's
			String admisNo = request.getParameter("studentAdmisNo");
			if (request.getParameter("studentAdmisNo") != null) {
				for (Integer index = 0; index < stuSession.size(); index++) {
					StudentSession stSession = stuSession.get(index);
					if ((stSession.getStudentAdmisNo()).equals(admisNo)) {
						sessionCache.getParentSession().setSelectedStuIndex(
								index.toString());
					}
				}
			}
		}

		return mav;

	}

	
	
	
	// Ajax For Ac Term Details for dashboard
		@RequestMapping(value = "/dashboard", method = RequestMethod.GET, params = { "feeCollection" })
		public @ResponseBody DashboardConsolidatedVO getFeeCollectionList(
				HttpSession session, HttpServletRequest request,
				HttpServletResponse response) {
			logger.debug("inside holiday exist check method");

			SessionCache sessionCache = (SessionCache) session
					.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);

			String fromDate = request.getParameter("fromDate");
			String toDate = request.getParameter("toDate");
			System.out.println("fromdate:"+fromDate+" Todate:"+toDate);
			DashboardConsolidatedVO dashboardConsolidated = null;
			try {
				dashboardConsolidated = dashboardService.selectDashboardRecord(fromDate, toDate, sessionCache.getUserSessionDetails());
			} catch (NoDataFoundException e) {
				response.setStatus(400);
				logger.error("Error occured in Academic Calendar details controller :"
						+ e.getMessage());
			}
			logger.debug("Return value:"+ dashboardConsolidated.toString());
			return dashboardConsolidated;
		}

	
	// Ajax For Ac Term Details for dashboard
	@RequestMapping(value = "/dashboard", method = RequestMethod.GET, params = { "dashBoardAcal" })
	public @ResponseBody List<AcademicCalendarVO> getAcademicCalendarList(
			HttpSession session, HttpServletRequest request,
			HttpServletResponse response) {
		logger.debug("inside holiday exist check method");

		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);

		AcademicCalendarListKey academicCalendarListKey = new AcademicCalendarListKey();
		academicCalendarListKey.setAcTerm(sessionCache.getUserSessionDetails()
				.getCurrAcTerm());
		academicCalendarListKey.setInstId(sessionCache.getUserSessionDetails()
				.getInstId());
		academicCalendarListKey.setBranchId(sessionCache
				.getUserSessionDetails().getBranchId());
		List<AcademicCalendarVO> acals = null;
		try {
			acals = academicCalendarService
					.getAcademicCalendarDetailsForDashBoard(
							academicCalendarListKey,
							(String) request.getParameter("monthAndYear"));
		} catch (NoDataFoundException e) {
			response.setStatus(400);
			logger.error("Error occured in Academic Calendar details controller :"
					+ e.getMessage());
		}

		return acals;
	}

	// Ajax For holiday Details for dashboard
	@RequestMapping(value = "/dashboard", method = RequestMethod.GET, params = { "dashBoardHol" })
	public @ResponseBody List<AcademicCalendarVO> getHolidayList(
			HttpSession session, HttpServletRequest request,
			HttpServletResponse response) {
		logger.debug("inside holiday exist check method");

		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);

		AcademicCalendarListKey academicCalendarListKey = new AcademicCalendarListKey();
		academicCalendarListKey.setAcTerm(sessionCache.getUserSessionDetails()
				.getCurrAcTerm());
		academicCalendarListKey.setInstId(sessionCache.getUserSessionDetails()
				.getInstId());
		academicCalendarListKey.setBranchId(sessionCache
				.getUserSessionDetails().getBranchId());
		List<AcademicCalendarVO> acals = null;
		try {
			acals = holidayMaintenanceService
					.getHolidayDetailsForDashBoard(academicCalendarListKey,
							(String) request.getParameter("monthAndYear"),
							sessionCache);
		} catch (NoDataFoundException e) {
			response.setStatus(400);
			logger.error("Error occured in Holiday details controller :"
					+ e.getMessage());
		}

		return acals;
	}

	// Ajax For get list of months for AcTerm
	@RequestMapping(value = "/dashboard", method = RequestMethod.GET, params = { "monthsAcal" })
	public @ResponseBody List<String> getMonthList(
			@ModelAttribute("acadcal") AcademicCalendarMasterVO academicCalendarMasterVO,
			HttpSession session, HttpServletRequest request,
			HttpServletResponse response) {
		logger.debug("inside holiday exist check method");

		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);

		List<String> acals = null;
		try {
			acals = academicCalendarService
					.getListOfMonthsForAcademicTerm(sessionCache
							.getUserSessionDetails());
		} catch (NoDataFoundException e) {
			response.setStatus(400);
			logger.error("Error occured in Academic Calendar details controller :"
					+ e.getMessage());
		}

		return acals;
	}

	// Ajax For Notice Board Details for dashboard
	@RequestMapping(value = "/dashboard", method = RequestMethod.GET, params = { "dashBoardNotice" })
	public @ResponseBody List<NoticeBoardVO> getNoticeBoardList(
			HttpSession session, HttpServletRequest request,
			HttpServletResponse response) {
		logger.debug("inside notice board method");

		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);

		NoticeBoardListKey noticeBoardListKey = new NoticeBoardListKey();
		noticeBoardListKey.setAcTerm(sessionCache.getUserSessionDetails()
				.getCurrAcTerm());
		noticeBoardListKey.setInstId(sessionCache.getUserSessionDetails()
				.getInstId());
		noticeBoardListKey.setBranchId(sessionCache.getUserSessionDetails()
				.getBranchId());
		noticeBoardListKey.setNoticeType((String) request
				.getParameter("noticetype"));
		List<NoticeBoardVO> noticeBoards = null;
		try {
			noticeBoards = noticeBoardService
					.selectNoticeBoardListForDashBoard(noticeBoardListKey,
							sessionCache.getUserSessionDetails());

		} catch (NoDataFoundException e) {
			response.setStatus(400);
			logger.error("Error occured in Notice Board details controller :"
					+ e.getMessage());
		}
		System.out.println("list size in controleler" + noticeBoards.size());

		return noticeBoards;
	}

	// Ajax For Alert Details for dashboard
	@RequestMapping(value = "/dashboard", method = RequestMethod.GET, params = { "dashBoardAlert" })
	public @ResponseBody List<AlertVO> getAlertList(HttpSession session,
			HttpServletRequest request, HttpServletResponse response) {
		logger.debug("inside alert method");

		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);

		AlertListKey alertListKey = new AlertListKey();
		alertListKey.setAcTerm(sessionCache.getUserSessionDetails()
				.getCurrAcTerm());
		alertListKey
				.setInstId(sessionCache.getUserSessionDetails().getInstId());
		alertListKey.setBranchId(sessionCache.getUserSessionDetails()
				.getBranchId());
		List<AlertVO> alertVOs = null;
		try {
			alertVOs = alertService.selectAlertListForDashBoard(alertListKey,
					sessionCache.getUserSessionDetails());

		} catch (NoDataFoundException e) {
			response.setStatus(400);
			logger.error("Error occured in Alert details controller :"
					+ e.getMessage());
		}
		System.out.println("list size in controleler" + alertVOs.size());

		return alertVOs;
	}

	// Ajax For Consolidate Student attendance Details for dashboard
	@RequestMapping(value = "/dashboard", method = RequestMethod.GET, params = { "dashBoardStuAttendance" })
	public @ResponseBody List<ViewAttendance> getStudentAttendanceList(
			HttpSession session, HttpServletRequest request,
			HttpServletResponse response) {
		System.out.println("ajax controller");
		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);

		String stGroup = "";
		String stAdmisNo = "";

		if ((sessionCache.getUserSessionDetails().getProfileGroup()
				.equals(ApplicationConstant.PG_STUDENT))) {
			stGroup = sessionCache.getStudentSession().getStuGrpId();
			stAdmisNo = sessionCache.getStudentSession().getStudentAdmisNo();
		} else if ((sessionCache.getUserSessionDetails().getProfileGroup()
				.equals(ApplicationConstant.PG_PARENT))) {
			StudentSession studentSession2 = sessionCache
					.getParentSession()
					.getStudentSession()
					.get(Integer.parseInt(sessionCache.getParentSession()
							.getSelectedStuIndex()));
			stGroup = studentSession2.getStuGrpId();
			stAdmisNo = studentSession2.getStudentAdmisNo();
		}

		System.out.println("ajax controller student group 1" + stGroup);
		System.out.println("ajax controller student admis no 1" + stAdmisNo);
		ViewAttendanceKey viewAttendanceKey = new ViewAttendanceKey();
		viewAttendanceKey.setAcTerm(sessionCache.getUserSessionDetails()
				.getCurrAcTerm());
		viewAttendanceKey.setInstId(sessionCache.getUserSessionDetails()
				.getInstId());
		viewAttendanceKey.setBranchId(sessionCache.getUserSessionDetails()
				.getBranchId());
		viewAttendanceKey.setStudentAdmisNo(stAdmisNo);
		viewAttendanceKey.setStudentGrpId(stGroup);

		List<ViewAttendance> viewAttendanceList = null;
		try {
			viewAttendanceList = analyticAttendanceService
					.selectConsolidateAttendance(viewAttendanceKey);
		} catch (NoDataFoundException e) {
			response.setStatus(400);
		}
		System.out.println("view attendance size  :"
				+ viewAttendanceList.size());

		return viewAttendanceList;
	}
}
