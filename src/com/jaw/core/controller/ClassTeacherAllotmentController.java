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
import org.springframework.web.util.WebUtils;

import com.jaw.common.constants.ApplicationConstant;
import com.jaw.common.constants.ErrorCodeConstant;
import com.jaw.common.constants.PropertyManagementConstant;
import com.jaw.common.dropdown.service.IDropDownListService;
import com.jaw.common.exceptions.DatabaseException;
import com.jaw.common.exceptions.DeleteFailedException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.util.ErrorDescriptionNotFoundException;
import com.jaw.common.exceptions.util.PropertyNotFoundException;
import com.jaw.common.exceptions.util.TableNotSpecifiedForAuditException;
import com.jaw.common.util.ErrorCodeUtil;
import com.jaw.common.util.PropertyManagementUtil;
import com.jaw.core.service.IClassTeacherAllotmentService;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.sessCache.SessionCache;
import com.jaw.staff.controller.StaffMasterVo;

@Controller
public class ClassTeacherAllotmentController {

	Logger logger = Logger.getLogger(ClassTeacherAllotmentController.class);

	@Autowired
	IClassTeacherAllotmentService classTeacherAllotmentService;
	@Autowired
	IDropDownListService dropDownListService;
	@Autowired
	ErrorCodeUtil errorCodeUtil;
	@Autowired
	PropertyManagementUtil propertyManagementUtil;

	@RequestMapping(value = "/classTeacherAllotment", method = RequestMethod.GET)
	// To open class teacher allotment page
	public ModelAndView stdlist(
			@ModelAttribute("sub") ClassTeacherAllotmentVO classTeacher,
			HttpServletRequest httpServletRequest, HttpSession session,
			ModelMap model) throws PropertyNotFoundException {
		logger.info("Opening class teacher allotment ");
		ApplicationCache applicationCache = (ApplicationCache) session
				.getServletContext().getAttribute(
						ApplicationConstant.APPLICATION_CACHE);
		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		String isMentorApplicable = propertyManagementUtil.getPropertyValue(
				applicationCache, sessionCache.getUserSessionDetails()
						.getInstId(), sessionCache.getUserSessionDetails()
						.getBranchId(),
				PropertyManagementConstant.MENTOR_APPLICABLE);
		System.out.println("Is mentor applicable :" + isMentorApplicable);
		classTeacher.setIsBatchInclude(isMentorApplicable);
		model.addAttribute("status", "true");
		return new ModelAndView(".jaw.classteacher");
	}

	// Class teacher allotment post method

	@RequestMapping(value = "/classTeacherAllotment", method = RequestMethod.GET, params = { "Get" })
	public ModelAndView stdlistpost(
			@ModelAttribute("sub") ClassTeacherAllotmentVO classTeacher,
			HttpServletRequest httpServletRequest, HttpSession session,
			ModelMap model) throws NoDataFoundException,
			ErrorDescriptionNotFoundException {

		logger.info("Class teacher allotment page submitted");

		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		ModelAndView mav = new ModelAndView(".jaw.classteacher", "sub",
				classTeacher);
		httpServletRequest.setAttribute("page", mav);
		WebUtils.setSessionAttribute(httpServletRequest, "searchVo",
				classTeacher);
		List<AllottedClassTeachersVO> allottedClassTeachersVOs;
		classTeacherAllotmentService.getAcTemStatus(classTeacher,
				sessionCache.getUserSessionDetails());
		Map stockParamMap = WebUtils.getParametersStartingWith(
				httpServletRequest, "d-");
		System.out.println("academic term status :"
				+ classTeacher.getAcTermSts());
		if (classTeacher.getAcTermSts().equals(ApplicationConstant.CLOSED)) {
			if (stockParamMap.size() == 0) {

				logger.info("Table operation has been triggered");

				// Get list from database

				allottedClassTeachersVOs = classTeacherAllotmentService
						.getOldClassTeacherList(classTeacher,
								sessionCache.getUserSessionDetails());

				// Set the list to session to access in jsp
				System.out.println("allotted teachers list :"
						+ allottedClassTeachersVOs.size());
				WebUtils.setSessionAttribute(httpServletRequest, "display_tbl",
						allottedClassTeachersVOs);

			}
		} else {

			Map<String, String> list = classTeacherAllotmentService
					.getStudentGroupList(classTeacher.getAcTerm(),
							classTeacher.getCourseId(),
							sessionCache.getUserSessionDetails());
			model.addAttribute("standardList", list);

			List<StaffMasterVo> staff = classTeacherAllotmentService
					.selectStaff(classTeacher.getAcTerm(),
							sessionCache.getUserSessionDetails());
			model.addAttribute("staff", staff);

			Map<String, String> classteacher = classTeacherAllotmentService
					.getClassTeachersList(classTeacher,
							sessionCache.getUserSessionDetails());
			model.addAttribute("classteacher", classteacher);

		}

		logger.info("Data fetched successfully,Redirecting to classteacher teacher allotment page");
		model.addAttribute("status", "true");
		return mav;

	}

	// Ajax method to get subjects based on subject type
	@RequestMapping(value = "/classTeacherAllotment", method = RequestMethod.GET, params = { "right" })
	public @ResponseBody
	String insertClassTeacher(HttpServletRequest httpServletRequest,
			HttpSession session) throws DatabaseException,
			DuplicateEntryException, ErrorDescriptionNotFoundException

	{
		ApplicationCache applicationCache = (ApplicationCache) session
				.getServletContext().getAttribute(
						ApplicationConstant.APPLICATION_CACHE);
		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);

		ClassTeacherAllotmentVO classTeacher = new ClassTeacherAllotmentVO();

		String staffId = httpServletRequest.getParameter("staffId");
		String stGroup = httpServletRequest.getParameter("stGroup");
		String batch = httpServletRequest.getParameter("batch");
		String isAuditRequired = httpServletRequest
				.getParameter("isAuditRequired");
		ClassTeacherAllotmentVO classTeacherAllotmentVO = (ClassTeacherAllotmentVO) WebUtils
				.getSessionAttribute(httpServletRequest, "searchVo");
		classTeacher.setStaffId(staffId);
		classTeacher.setStGroup(stGroup);
		classTeacher.setStBatchId(batch);
		classTeacher.setIsAuditRequired(isAuditRequired);
		classTeacher.setAcTerm(classTeacherAllotmentVO.getAcTerm());
		classTeacherAllotmentService.insertClassTeacher(classTeacher,
				sessionCache.getUserSessionDetails());

		logger.info("Data saved successfully,Redirecting to classteacher teacher allotment page,with success message");
		String returnString = (String) errorCodeUtil.getErrorDescription(
				applicationCache, ErrorCodeConstant.ADD_SUCCESS_MESS)
				+ "/"
				+ classTeacher.getIsAuditRequired();
		System.out.println("Return string :" + returnString);
		return returnString;
	}

	@RequestMapping(value = "/classTeacherAllotment", method = RequestMethod.GET, params = { "left" })
	public @ResponseBody
	String deleteClassTeacher(HttpServletRequest httpServletRequest,
			HttpSession session) throws DeleteFailedException,
			NoDataFoundException, ErrorDescriptionNotFoundException,
			DuplicateEntryException, DatabaseException,
			TableNotSpecifiedForAuditException

	{
		ApplicationCache applicationCache = (ApplicationCache) session
				.getServletContext().getAttribute(
						ApplicationConstant.APPLICATION_CACHE);
		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		String staffId = httpServletRequest.getParameter("staffId");
		String stGroup = httpServletRequest.getParameter("stGroup");
		String batch = httpServletRequest.getParameter("batch");

		ClassTeacherAllotmentVO classTeacherAllotmentVO = (ClassTeacherAllotmentVO) WebUtils
				.getSessionAttribute(httpServletRequest, "searchVo");
		classTeacherAllotmentVO.setStaffId(staffId);
		classTeacherAllotmentVO.setStGroup(stGroup);
		classTeacherAllotmentVO.setStBatchId(batch);
		classTeacherAllotmentVO.setAcTerm(classTeacherAllotmentVO.getAcTerm());

		classTeacherAllotmentService.deleteClassTeacher(
				classTeacherAllotmentVO, sessionCache.getUserSessionDetails(),
				applicationCache);

		logger.info("Data saved successfully,Redirecting to classteacher teacher allotment page,with success message");
		return (String) errorCodeUtil.getErrorDescription(applicationCache,
				ErrorCodeConstant.DELETE_SUCCESS_MESS);
	}

	@RequestMapping(value = "/classTeacherAllotment", method = RequestMethod.GET, params = { "getBatch" })
	public @ResponseBody
	Map<String, String> getStudentBatch(HttpServletRequest httpServletRequest,
			HttpSession session, HttpServletResponse response)

	{
		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		String stGroup = httpServletRequest.getParameter("stGroup");
		String acTerm = httpServletRequest.getParameter("acTerm");

		logger.info("Data saved successfully,Redirecting to classteacher teacher allotment page,with success message");
		try {
			return classTeacherAllotmentService.getStudentBatchList(stGroup,
					sessionCache.getUserSessionDetails(), acTerm);
		} catch (NoDataFoundException e) {
			response.setStatus(400);
		}
		return null;

	}

	@RequestMapping(value = "/ajaxCall", method = RequestMethod.GET, params = { "courseList" })
	public @ResponseBody
	Map<String, String> gerCourseNameList(HttpSession session,
			HttpServletRequest httpSevletRequest, HttpServletResponse response,
			ModelMap model) throws IOException {
		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		Map<String, String> map = null;
		try {
			map = dropDownListService.getCourseNameListTag(sessionCache
					.getUserSessionDetails());
		} catch (NoDataFoundException e) {
			logger.error("Error occured in Course Name list Dropdown :"
					+ e.getMessage());
		}
		httpSevletRequest.setAttribute("courseList", map);
		return map;

	}

	@ExceptionHandler({ NoDataFoundException.class })
	public ModelAndView handleException1(Exception ex, HttpSession session,
			HttpServletRequest request) {
		ModelAndView modelAndView = (ModelAndView) request.getAttribute("page");
		modelAndView.getModelMap().addAttribute("error", ex.getMessage());
		WebUtils.setSessionAttribute(request, "display_tbl", null);
		return modelAndView;
	}
}
