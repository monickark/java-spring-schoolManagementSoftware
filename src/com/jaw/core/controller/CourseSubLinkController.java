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
import org.springframework.web.util.WebUtils;

import com.jaw.common.constants.ApplicationConstant;
import com.jaw.common.constants.ErrorCodeConstant;
import com.jaw.common.constants.ModelAndViewConstant;
import com.jaw.common.dropdown.service.IDropDownListService;
import com.jaw.common.exceptions.DatabaseException;
import com.jaw.common.exceptions.DeleteFailedException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.SubjectOrderAlreadyExistExecption;
import com.jaw.common.exceptions.UpdateFailedException;
import com.jaw.common.exceptions.util.CommonCodeNotFoundException;
import com.jaw.common.exceptions.util.ErrorDescriptionNotFoundException;
import com.jaw.common.exceptions.util.PropertyNotFoundException;
import com.jaw.common.exceptions.util.TableNotSpecifiedForAuditException;
import com.jaw.common.util.ErrorCodeUtil;
import com.jaw.core.service.ICourseSubLinkService;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.sessCache.SessionCache;

//Academic Term Details Controller Class
@Controller
public class CourseSubLinkController {
	// Logging
	Logger logger = Logger.getLogger(CourseSubLinkController.class);
	@Autowired
	ICourseSubLinkService courseSubLinkService;
	@Autowired
	IDropDownListService dropDownListService;
	@Autowired
	ErrorCodeUtil errorCodeUtil;

	@RequestMapping(value = "/courseSubLink", method = RequestMethod.GET)
	public ModelAndView getCourseSubjectLink(
			@ModelAttribute("courseSubLink") CourseSubLinkMasterVO courseSubLinkMasterVO,
			ModelMap modelMap, HttpServletRequest httpServletRequest) {

		ModelAndView mav = new ModelAndView(
				ModelAndViewConstant.COURSE_SUB_LINK, "courseSubLink",
				courseSubLinkMasterVO);
		WebUtils.setSessionAttribute(httpServletRequest, "display_tbl", null);
		WebUtils.setSessionAttribute(httpServletRequest, "success", null);
		WebUtils.setSessionAttribute(httpServletRequest, "keepStatus", null);
		mav.getModelMap().addAttribute("status", "true");
		return mav;
	}

	@RequestMapping(value = "/courseSubLink", method = RequestMethod.GET, params = "Get")
	public String viewCourseSubmDetailsList(
			@ModelAttribute("courseSubLink") CourseSubLinkMasterVO courseSubLinkMasterVO,
			ModelMap model, HttpServletRequest httpServletRequest,
			HttpSession session, RedirectAttributes redirectAttribute)
			throws DuplicateEntryException, DatabaseException,
			NoDataFoundException {

		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		// Getting the display tag parameter
		WebUtils.setSessionAttribute(httpServletRequest, "searchVo",
				courseSubLinkMasterVO.getCourseSubLinkSearchVO());
		Map stockParamMap = WebUtils.getParametersStartingWith(
				httpServletRequest, "d-");

		// Setting model and view for exception handler

		httpServletRequest.setAttribute("courseSubLink", courseSubLinkMasterVO);
		httpServletRequest.setAttribute("errors", "error"); // Check whether the
															// list already get
															// or have to fetch
															// from data base
		// using the list size

		if (stockParamMap.size() == 0) {

			logger.info("Table operation has been triggered");

			// Get list from database

			try {
				courseSubLinkService.selectCourseSubjectLinkingData(
						courseSubLinkMasterVO,
						sessionCache.getUserSessionDetails());
			} catch (NoDataFoundException e) {
				WebUtils.setSessionAttribute(httpServletRequest, "display_tbl",
						null);
				throw new NoDataFoundException();
			}

			// Set the list to session to access in jsp

			WebUtils.setSessionAttribute(httpServletRequest, "display_tbl",
					courseSubLinkMasterVO.getCourseSubLinkVOs());
			/*
			 * courseSubLinkMasterVO.getCourseSubLinkSearchVO().setPageSize(
			 * courseSubLinkMasterVO.getCourseSubLinkSearchVO() .getPageSize());
			 */

		}
		redirectAttribute.addFlashAttribute("courseSubLink",
				courseSubLinkMasterVO);
		if (httpServletRequest.getParameter("Get").equals("Get")) {
			WebUtils.setSessionAttribute(httpServletRequest, "keepStatus", null);
		} else {
			WebUtils.setSessionAttribute(httpServletRequest, "keepStatus",
					"status");
		}

		return "redirect:/courseSubLink.htm?data";
	}

	@RequestMapping(value = "/courseSubLink", method = RequestMethod.GET, params = {
			"data", "!actionGet" })
	public ModelAndView getCourseSubmDetailsList(
			@ModelAttribute("courseSubLink") CourseSubLinkMasterVO courseSubLinkMasterVO,
			ModelMap model, HttpServletRequest httpServletRequest,
			HttpSession session, RedirectAttributes redirectAttribute)
			throws DuplicateEntryException, DatabaseException,
			NoDataFoundException {
		ModelAndView modelAndView = new ModelAndView(
				ModelAndViewConstant.COURSE_SUB_LINK);
		CourseSubLinkSearchVO courseSubLinkSearchVO = (CourseSubLinkSearchVO) WebUtils
				.getSessionAttribute(httpServletRequest, "searchVo");
		courseSubLinkMasterVO.setCourseSubLinkSearchVO(courseSubLinkSearchVO);
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

	@RequestMapping(value = "/courseSubLink", method = RequestMethod.POST, params = { "actionSave" })
	public String addCourseSubjectLink(
			@ModelAttribute("courseSubLink") CourseSubLinkMasterVO courseSubLinkMasterVO,
			ModelMap model, HttpServletRequest httpServletRequest,
			HttpSession session, RedirectAttributes redirectAttribute)
			throws DuplicateEntryException, DatabaseException, SubjectOrderAlreadyExistExecption {

		logger.debug("inside save Course Subject Linking method");
		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		httpServletRequest.setAttribute("courseSubLink", courseSubLinkMasterVO);
		httpServletRequest.setAttribute("errors", "erroradd");
		CourseSubLinkSearchVO courseSubLinkSearchVO = (CourseSubLinkSearchVO) WebUtils
				.getSessionAttribute(httpServletRequest, "searchVo");
		courseSubLinkMasterVO.setCourseSubLinkSearchVO(courseSubLinkSearchVO);
		logger.debug("course Id :"
				+ courseSubLinkMasterVO.getCourseSubLinkVO()
						.getCourseMasterId());
		logger.debug("term Id :"
				+ courseSubLinkMasterVO.getCourseSubLinkVO().getTermId());
		courseSubLinkService.insertCouseSubjectLinkRec(
				courseSubLinkMasterVO.getCourseSubLinkVO(),
				sessionCache.getUserSessionDetails());
		courseSubLinkMasterVO.setCourseSubLinkVO(null);
		redirectAttribute.addFlashAttribute("courseSubLink",
				courseSubLinkMasterVO);

		WebUtils.setSessionAttribute(httpServletRequest, "success",
				ErrorCodeConstant.ADD_SUCCESS_MESS);
		logger.debug("Data's inserted successfully!");
		WebUtils.setSessionAttribute(httpServletRequest, "keepStatus", "status");
		return "redirect:/courseSubLink.htm?Get";
	}

	@RequestMapping(value = "/courseSubLink", method = RequestMethod.GET, params = { "actionDelete" })
	public String deleteCourseSubDetails(
			@ModelAttribute("courseSubLink") CourseSubLinkMasterVO courseSubLinkMasterVO,
			ModelMap model, HttpServletRequest httpServletRequest,
			HttpSession session, RedirectAttributes redirectAttribute)
			throws UpdateFailedException, DeleteFailedException,
			NoDataFoundException, DuplicateEntryException, DatabaseException,
			TableNotSpecifiedForAuditException {

		logger.debug("inside Delete Course subject liking method");
		ApplicationCache applicationCache = (ApplicationCache) session
				.getServletContext().getAttribute(
						ApplicationConstant.APPLICATION_CACHE);
		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		List<CourseSubLinkListVO> courseSubLinkVOs = (List<CourseSubLinkListVO>) WebUtils
				.getSessionAttribute(httpServletRequest, "display_tbl");
		CourseSubLinkSearchVO courseSubLinkSearchVO = (CourseSubLinkSearchVO) WebUtils
				.getSessionAttribute(httpServletRequest, "searchVo");
		courseSubLinkMasterVO.setCourseSubLinkSearchVO(courseSubLinkSearchVO);
		String id = httpServletRequest.getParameter("rowId");
		courseSubLinkMasterVO.setCourseSubLinkListVO(courseSubLinkVOs
				.get(Integer.parseInt(id)));

		httpServletRequest.setAttribute("courseSubLink", courseSubLinkMasterVO);

		courseSubLinkService.deleteCourseSubLinkDAORec(
				courseSubLinkMasterVO.getCourseSubLinkListVO(),
				sessionCache.getUserSessionDetails(), applicationCache);

		redirectAttribute.addFlashAttribute("courseSubLink",
				courseSubLinkMasterVO);

		WebUtils.setSessionAttribute(httpServletRequest, "success",
				ErrorCodeConstant.DELETE_SUCCESS_MESS);
		logger.debug("Data's deleted successfully!");
		WebUtils.setSessionAttribute(httpServletRequest, "keepStatus", "status");
		return "redirect:/courseSubLink.htm?Get";
	}

	

	@RequestMapping(value = "/courseSubLink", method = RequestMethod.POST, params = { "actionUpdate" })
	public String updateCourseSubDetails(
			@ModelAttribute("courseSubLink") CourseSubLinkMasterVO courseSubLinkMasterVO,
			ModelMap model, HttpServletRequest httpServletRequest,
			HttpSession session, RedirectAttributes redirectAttribute)
			throws UpdateFailedException, DeleteFailedException,
			NoDataFoundException, DuplicateEntryException, DatabaseException,
			TableNotSpecifiedForAuditException {

		logger.debug("inside update Course subject liking method");

		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		ApplicationCache applicationCache = (ApplicationCache) session
				.getServletContext().getAttribute(
						ApplicationConstant.APPLICATION_CACHE);
		httpServletRequest.setAttribute("courseSubLink", courseSubLinkMasterVO);
		CourseSubLinkSearchVO courseSubLinkSearchVO = (CourseSubLinkSearchVO) WebUtils
				.getSessionAttribute(httpServletRequest, "searchVo");
		courseSubLinkMasterVO.setCourseSubLinkSearchVO(courseSubLinkSearchVO);

		courseSubLinkService.updateCourseSubjectLinking(
				courseSubLinkMasterVO.getCourseSubLinkVO(),
				sessionCache.getUserSessionDetails(), applicationCache);
		courseSubLinkMasterVO.setCourseSubLinkVO(null);
		redirectAttribute.addFlashAttribute("courseSubLink",
				courseSubLinkMasterVO);
		WebUtils.setSessionAttribute(httpServletRequest, "success",
				ErrorCodeConstant.UPDATE_SUCCESS_MESS);
		logger.debug("Data's updated successfully!");
		WebUtils.setSessionAttribute(httpServletRequest, "keepStatus", "status");
		return "redirect:/courseSubLink.htm?Get";
	}

	@RequestMapping(value = "/courseSubLink", method = RequestMethod.GET, params = { "actionGet" })
	public String getCourseSubmDetails(
			@ModelAttribute("courseSubLink") CourseSubLinkMasterVO courseSubLinkMasterVO,
			ModelMap model, HttpServletRequest httpServletRequest,
			HttpSession session, RedirectAttributes redirectAttribute) {

		logger.debug("inside Update Course Subject Linking method");

		List<CourseSubLinkListVO> courseSubLinkVOs = (List<CourseSubLinkListVO>) WebUtils
				.getSessionAttribute(httpServletRequest, "display_tbl");
		CourseSubLinkSearchVO courseSubLinkSearchVO = (CourseSubLinkSearchVO) WebUtils
				.getSessionAttribute(httpServletRequest, "searchVo");
		courseSubLinkMasterVO.setCourseSubLinkSearchVO(courseSubLinkSearchVO);
		String id = httpServletRequest.getParameter("rowId");
		logger.debug("Id received from jsp :" + id);
		courseSubLinkMasterVO.setCourseSubLinkListVO(courseSubLinkVOs
				.get(Integer.parseInt(id)));
		System.out.println("courseSubLinkMasterVO.getCourseSubLinkListVO()1 :"
				+ courseSubLinkMasterVO.getCourseSubLinkListVO().toString());
		System.out.println("courseSubLinkMasterVO.getCourseSubLinkVO()1 :"
				+ courseSubLinkMasterVO.getCourseSubLinkVO().toString());
		courseSubLinkService.copyListToVO(courseSubLinkMasterVO);
		redirectAttribute.addFlashAttribute("popup", "update");
		redirectAttribute.addFlashAttribute("courseSubLink",
				courseSubLinkMasterVO);
		return "redirect:/courseSubLink.htm?data";
	}

	@ModelAttribute("courseList")
	public Map<String, String> gerCourseNameList(HttpSession session,
			HttpServletRequest httpSevletRequest, HttpServletResponse response,
			ModelMap model) throws IOException {
		Map<String, String> map = null;
		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		try {
			map = dropDownListService.getCourseNameListTag(sessionCache
					.getUserSessionDetails());
		} catch (NoDataFoundException e) {

		}
		httpSevletRequest.setAttribute("courseList", map);
		return map;

	}

	@RequestMapping(value = "/courseSubLink", method = RequestMethod.GET, params = { "term" })
	public @ResponseBody
	Map<String, String> getTermModelBasedOnCourseId(
			HttpServletRequest httpServletRequest, HttpSession session,
			HttpServletResponse response) throws PropertyNotFoundException,
			ErrorDescriptionNotFoundException

	{
		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		String courseId = httpServletRequest.getParameter("courseValue");
		logger.debug("Inside ajax method to get subject link based on course Id");
		Map<String, String> map = null;
		try {
			map = courseSubLinkService.getTermDetailsBasedOnCourseId(courseId,
					sessionCache.getUserSessionDetails());
		} catch (NoDataFoundException exception) {
			response.setStatus(400);
			/*
			 * try { response.getWriter() .write(
			 * errorCodeUtil.getErrorDescription(applicationCache,
			 * exception.getMessage())); } catch (IOException e) {
			 * logger.error("Error occured :" + e.getMessage()); }
			 */
		}
		return map;

	}

	// Ajax method to get subjects based on subject type
	@RequestMapping(value = "/courseSubLink", method = RequestMethod.GET, params = { "subject","courseId" })
	public @ResponseBody
	Map<String, String> getSubjectList(HttpServletRequest httpServletRequest,
			HttpSession session)

	{
		ApplicationCache applicationCache = (ApplicationCache) session
				.getServletContext().getAttribute(
						ApplicationConstant.APPLICATION_CACHE);
		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		String subjectType = httpServletRequest.getParameter("subjectType");
		String courseId = httpServletRequest.getParameter("courseId");
		logger.debug("Inside ajax method to get subject link based on course Id");
		logger.debug("Subject Type :" + subjectType);
		Map<String, String> map = null;

		try {
			map = courseSubLinkService.getSubjectList(applicationCache,
					sessionCache.getUserSessionDetails(), subjectType,courseId);
		} catch (NoDataFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return map;
	
	}

	@ExceptionHandler({ DuplicateEntryException.class,
			DeleteFailedException.class, UpdateFailedException.class,
			CommonCodeNotFoundException.class, DatabaseException.class,
			TableNotSpecifiedForAuditException.class,SubjectOrderAlreadyExistExecption.class })
	public ModelAndView handleException(Exception ex, HttpSession session,
			HttpServletRequest request) {

		CourseSubLinkMasterVO courseSubLinkMasterVO = (CourseSubLinkMasterVO) request
				.getAttribute("courseSubLink");
		ModelAndView modelAndView = new ModelAndView(
				ModelAndViewConstant.COURSE_SUB_LINK, "courseSubLink",
				courseSubLinkMasterVO);
		String error = (String) request.getAttribute("errors");
		modelAndView.getModelMap().addAttribute(error, ex.getMessage());

		return modelAndView;

	}

	@ExceptionHandler({ NoDataFoundException.class })
	public ModelAndView handleException1(Exception ex, HttpSession session,
			HttpServletRequest request) {

		CourseSubLinkMasterVO courseSubLinkMasterVO = (CourseSubLinkMasterVO) request
				.getAttribute("courseSubLink");
		ModelAndView modelAndView = new ModelAndView(
				ModelAndViewConstant.COURSE_SUB_LINK, "courseSubLink",
				courseSubLinkMasterVO);
		String error = (String) request.getAttribute("errors");
		String message = (String) WebUtils.getSessionAttribute(request,
				"success");
		if ((message != null)
				&& (!message.equals(ErrorCodeConstant.DELETE_SUCCESS_MESS))) {
			System.out.println("inside delete message");
			WebUtils.setSessionAttribute(request, "success", null);
		} else {
			System.out.println("inside non  delete message");
			modelAndView.getModelMap().addAttribute(error, ex.getMessage());
		}
		WebUtils.setSessionAttribute(request, "display_tbl", null);

		return modelAndView;

	}

}
