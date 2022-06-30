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

import com.jaw.attendance.service.IAttendanceService;
import com.jaw.common.constants.ApplicationConstant;
import com.jaw.common.constants.CommonCodeConstant;
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
import com.jaw.common.exceptions.batch.NoRecordFoundException;
import com.jaw.common.exceptions.util.CommonCodeNotFoundException;
import com.jaw.common.util.ErrorCodeUtil;
import com.jaw.framework.sessCache.SessionCache;

//Academic Term Details Controller Classes
@Controller
public class AttendanceController {
	// Logging
	Logger logger = Logger.getLogger(AttendanceController.class);
	@Autowired
	IAttendanceService attendanceService;
	@Autowired
	IDropDownListService dropDownListService;
	@Autowired
	ErrorCodeUtil errorCodeUtil;

	@RequestMapping(value = "/attendanceView", method = RequestMethod.GET)
	public ModelAndView getAttendanceList(
			@ModelAttribute("attendance") AttendanceMasterVO attendanceMasterVO,
			ModelMap modelMap, HttpServletRequest httpServletRequest,HttpSession httpSession) {

		ModelAndView mav = new ModelAndView(
				ModelAndViewConstant.ATTENDANCE_UPDATE, "attendance",
				attendanceMasterVO);
		WebUtils.setSessionAttribute(httpServletRequest, "display_tbl", null);
		WebUtils.setSessionAttribute(httpServletRequest, "display_tbl1", null);
		WebUtils.setSessionAttribute(httpServletRequest, "success", null);
		WebUtils.setSessionAttribute(httpServletRequest, "searchVo", null);
		httpSession.setAttribute("rowIdpers", null);
		return mav;
	}

	@RequestMapping(value = "/attendanceView", method = RequestMethod.GET, params = "Get")
	public String viewAttendanceLinkingListGet(

	@ModelAttribute("attendance") AttendanceMasterVO attendanceMasterVO,
			ModelMap model, HttpServletRequest httpServletRequest,
			HttpSession session, RedirectAttributes redirectAttribute,HttpSession httpSession)
			throws DuplicateEntryException, DatabaseException,
			NoRecordFoundException, NoDataFoundException {
		WebUtils.setSessionAttribute(httpServletRequest, "success", null);
		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);

		Map stockParamMap = WebUtils.getParametersStartingWith(
				httpServletRequest, "d-");
		WebUtils.setSessionAttribute(httpServletRequest, "searchVo",
				attendanceMasterVO.getAttendanceSearchVO());
		ModelAndView mav = new ModelAndView(
				ModelAndViewConstant.ATTENDANCE_UPDATE, "attendance",
				attendanceMasterVO);
		httpServletRequest.setAttribute("page", mav);

		if (stockParamMap.size() == 0) {

			logger.info("Table operation has been triggered");

			// Get list from database

			attendanceService.selectAttendanceData(attendanceMasterVO,
					sessionCache.getUserSessionDetails());

			// Set the list to session to access in jsp
			if (sessionCache.getUserSessionDetails().getInbrCategory()
					.equals(CommonCodeConstant.IBCAT_PRE_UNIVERSITY_COLLEGE)) {
				WebUtils.setSessionAttribute(httpServletRequest, "display_tbl",
						attendanceMasterVO.getAttendanceDetailsListVOs());
			} else {
				System.out.println("In controller dis table1:"
						+ attendanceMasterVO.getAttendanceDetailsListVOs().size());
				System.out.println("Inside controller else");
				WebUtils.setSessionAttribute(httpServletRequest,
						"display_tbl1",
						attendanceMasterVO.getAttendanceListVOs());
			}
		}
		System.out.println("In controller dis table1:"
				+ attendanceMasterVO.getAttendanceDetailsListVOs().size());
		System.out.println("In controller dis table2:"
				+ attendanceMasterVO.getAttendanceListVOs().size());
		WebUtils.setSessionAttribute(httpServletRequest, "pageSize2",
				attendanceMasterVO.getPageSize2());
		System.out.println("institution category :"
				+ sessionCache.getUserSessionDetails().getInbrCategory());
		/*if(httpServletRequest.getParameter("Get").equals("Get")) {
			httpSession.setAttribute("rowIdpers", null);
			WebUtils.setSessionAttribute(httpServletRequest, "display_tbl1", null);
			WebUtils.setSessionAttribute(httpServletRequest, "success", null);
		}*/
		return "redirect:/attendanceView.htm?data";
	}

	@RequestMapping(value = "/attendanceView", method = RequestMethod.GET, params = "GetDetails")
	public ModelAndView viewAttendanceLinkingList(

	@ModelAttribute("attendance") AttendanceMasterVO attendanceMasterVO,
			ModelMap model, HttpServletRequest httpServletRequest,
			HttpSession session, RedirectAttributes redirectAttribute)
			throws DuplicateEntryException, DatabaseException,
			NoDataFoundException {
		WebUtils.setSessionAttribute(httpServletRequest, "success", null);
		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);

		Map stockParamMap = WebUtils.getParametersStartingWith(
				httpServletRequest, "d-");

		// Setting model and view for exception handler
		AttendanceSearchVO attendanceSearchVO = (AttendanceSearchVO) WebUtils
				.getSessionAttribute(httpServletRequest, "searchVo");
		ModelAndView mav = new ModelAndView(
				ModelAndViewConstant.ATTENDANCE_UPDATE, "attendance",
				attendanceMasterVO);
		httpServletRequest.setAttribute("page", mav);

		if (stockParamMap.size() == 0) {

			logger.info("Table operation has been triggered");

			// Get list from database
			List<AttendanceDetailsListVO> attendanceDetailsListVOs = (List<AttendanceDetailsListVO>) WebUtils
					.getSessionAttribute(httpServletRequest, "display_tbl");

			String id = httpServletRequest.getParameter("rowId1");

			System.out.println("Id received from jsp :" + id);
			AttendanceDetailsListVO attendanceDetailsListVO = attendanceDetailsListVOs
					.get(Integer.parseInt(id));

			attendanceService.selectAttendanceDetailsData(attendanceMasterVO,
					attendanceDetailsListVO,
					sessionCache.getUserSessionDetails());

			// Set the list to session to access in jsp
			System.out.println("In controller list size :"
					+ attendanceMasterVO.getAttendanceListVOs().size());
			WebUtils.setSessionAttribute(httpServletRequest, "display_tbl1",
					attendanceMasterVO.getAttendanceListVOs());

		}
		System.out.println("In controller :"
				+ attendanceMasterVO.getAttendanceSearchVO());
		attendanceMasterVO.setAttendanceSearchVO(attendanceSearchVO);
		redirectAttribute.addFlashAttribute("attendance", attendanceMasterVO);
		
		return mav;
	}

	@RequestMapping(value = "/attendanceView", method = RequestMethod.GET, params = "data")
	public ModelAndView getAttendanceLinkingList(

	@ModelAttribute("attendance") AttendanceMasterVO attendanceMasterVO,
			ModelMap model, HttpServletRequest httpServletRequest,
			HttpSession session, RedirectAttributes redirectAttribute) {
		AttendanceSearchVO attendanceSearchVO = (AttendanceSearchVO) WebUtils
				.getSessionAttribute(httpServletRequest, "searchVo");
		attendanceMasterVO.setAttendanceSearchVO(attendanceSearchVO);
		ModelAndView modelAndView = new ModelAndView(
				ModelAndViewConstant.ATTENDANCE_UPDATE);

		return modelAndView;
	}

	@RequestMapping(value = "/attendanceView", method = RequestMethod.GET, params = { "update" })
	public String getAttendanceLinking(
			@ModelAttribute("attendance") AttendanceMasterVO attendanceMasterVO,
			ModelMap model, HttpServletRequest httpServletRequest,
			HttpSession session, RedirectAttributes redirectAttribute)
			throws UpdateFailedException, NumberFormatException,
			NoDataFoundException, DuplicateEntryException, DatabaseException,
			DeleteFailedException {

		logger.debug("inside Update course classes linking method");

		List<AttendanceListVO> AttendanceVOs = (List<AttendanceListVO>) WebUtils
				.getSessionAttribute(httpServletRequest, "display_tbl1");
		AttendanceSearchVO attendanceSearchVO = (AttendanceSearchVO) WebUtils
				.getSessionAttribute(httpServletRequest, "searchVo");

		String id = httpServletRequest.getParameter("id");
		String absent = httpServletRequest.getParameter("abs");
		String remark = httpServletRequest.getParameter("rem");
		String pageSize = httpServletRequest.getParameter("pageSize");
		String remarkForChange = httpServletRequest
				.getParameter("remarkForChange");
		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		System.out.println("Id received from jsp :" + id);
		System.out.println("Absent received from jsp :" + absent);
		System.out.println("Remarks received from jsp :" + remark);
		System.out.println("Remarks reason received from jsp :"
				+ remarkForChange);
		AttendanceListVO attendanceListVO = AttendanceVOs.get(Integer
				.parseInt(id));
		attendanceListVO.setIsAbsent(absent);
		attendanceListVO.setRemark(remark);
		attendanceListVO.setRemarkForChange(remarkForChange);
		System.out.println("Admission Number :"
				+ attendanceListVO.getAdmissionNumber());
		attendanceMasterVO.setAttendanceSearchVO(attendanceSearchVO);
		attendanceMasterVO.setPageSize2(pageSize);
		attendanceService.updateAttendanceRec(attendanceMasterVO,
				attendanceListVO, sessionCache.getUserSessionDetails());

		WebUtils.setSessionAttribute(httpServletRequest, "success",
				ErrorCodeConstant.UPDATE_SUCCESS_MESS);
		redirectAttribute.addFlashAttribute("attendance", attendanceMasterVO);
		return "redirect:/attendanceView.htm?Get";
	}

	// Ajax For getting Sub List
	@RequestMapping(value = "/attendanceView", method = RequestMethod.GET, params = { "subList" })
	public @ResponseBody
	Map<String, String> retriveList(HttpSession session,
			HttpServletRequest request, HttpServletResponse response) {
		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		String stGroup = request.getParameter("studentGrpId");
		System.out.println("student group id :" + stGroup);
		Map<String, String> map = null;

		try {
			map = dropDownListService.selectSubjectList(stGroup,
					sessionCache.getUserSessionDetails());
		} catch (NoDataFoundException e) {
			logger.error("No subject found for student group :" + stGroup);
			response.setStatus(420);
		}

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
			DeleteFailedException.class, UpdateFailedException.class,
			CommonCodeNotFoundException.class, DatabaseException.class,
			BatchUpdateFailedException.class, NoRecordSelectedException.class })
	public ModelAndView handleException(Exception ex, HttpSession session,
			HttpServletRequest request) {
		WebUtils.setSessionAttribute(request, "success", null);
		ModelAndView modelAndView = (ModelAndView) request.getAttribute("page");
		modelAndView.getModelMap().addAttribute("error", ex.getMessage());
		return modelAndView;
	}

	@ExceptionHandler({ NoRecordFoundException.class })
	public ModelAndView handleException1(Exception ex, HttpSession session,
			HttpServletRequest request) {
		WebUtils.setSessionAttribute(request, "success", null);
		ModelAndView modelAndView = (ModelAndView) request.getAttribute("page");

		WebUtils.setSessionAttribute(request, "display_tbl", null);
		WebUtils.setSessionAttribute(request, "display_tbl1", null);
		modelAndView.getModelMap().addAttribute("error", ex.getMessage());
		return modelAndView;
	}

	@ExceptionHandler({ NoDataFoundException.class })
	public ModelAndView handleException2(Exception ex, HttpSession session,
			HttpServletRequest request) {
		WebUtils.setSessionAttribute(request, "success", null);
		ModelAndView modelAndView = (ModelAndView) request.getAttribute("page");
		WebUtils.setSessionAttribute(request, "display_tbl1", null);
		modelAndView.getModelMap().addAttribute("error", ex.getMessage());
		return modelAndView;
	}

}
