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
import com.jaw.common.exceptions.BatchUpdateFailedException;
import com.jaw.common.exceptions.CustomDuplicateEntryException;
import com.jaw.common.exceptions.DatabaseException;
import com.jaw.common.exceptions.DeleteFailedException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.NoRecordSelectedException;
import com.jaw.common.exceptions.UpdateFailedException;
import com.jaw.common.exceptions.util.CommonCodeNotFoundException;
import com.jaw.common.exceptions.util.ErrorDescriptionNotFoundException;
import com.jaw.common.exceptions.util.TableNotSpecifiedForAuditException;
import com.jaw.common.util.ErrorCodeUtil;
import com.jaw.core.service.ICourseClassesService;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.sessCache.SessionCache;
import com.jaw.mark.controller.MarkSubjectLinkMasterVO;

//Academic Term Details Controller Classes
@Controller
public class CourseClassesController {
	// Logging
	Logger logger = Logger.getLogger(CourseClassesController.class);
	@Autowired
	ICourseClassesService courseClassesService;
	@Autowired
	IDropDownListService dropDownListService;
	@Autowired
	ErrorCodeUtil errorCodeUtil;
	
	@RequestMapping(value = "/courseClasses", method = RequestMethod.GET)
	public ModelAndView getCourseClasses(
			@ModelAttribute("courseClasses") CourseClassesMasterVO courseClassesMasterVO,
			ModelMap modelMap, HttpServletRequest httpServletRequest) {
		
		ModelAndView mav = new ModelAndView(ModelAndViewConstant.COURSE_CLASS_LINK,
				"courseClasses", courseClassesMasterVO);
		WebUtils.setSessionAttribute(httpServletRequest, "display_tbl",
				null);
		
		WebUtils.setSessionAttribute(httpServletRequest, "searchVo",
				null);
		WebUtils.setSessionAttribute(httpServletRequest, "keepStatus",
				null);
		return mav;
	}
	
	@RequestMapping(value = "/courseClasses", method = RequestMethod.GET, params = "Get")
	public String viewCourseClassesLinkingList(
			@ModelAttribute("courseClasses") CourseClassesMasterVO courseClassesMasterVO,
			ModelMap model, HttpServletRequest httpServletRequest,
			HttpSession session, RedirectAttributes redirectAttribute)
			throws DuplicateEntryException, DatabaseException, NoDataFoundException {
		
		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		// Getting the display tag parameter
		
		Map stockParamMap = WebUtils.getParametersStartingWith(httpServletRequest,
				"d-");
		
		// Setting model and view for exception handler
		
		ModelAndView mav = new ModelAndView(ModelAndViewConstant.COURSE_CLASS_LINK,
				"courseClasses", courseClassesMasterVO);
		httpServletRequest.setAttribute("page", mav);
		// Check whether the list already get or have to fetch from data base
		// using the list size
		
		if (stockParamMap.size() == 0) {
			
			logger.info("Table operation has been triggered");
			
			// Get list from database
			
			courseClassesService.selectCourseClassesData(courseClassesMasterVO,
					sessionCache.getUserSessionDetails());
			
			// Set the list to session to access in jsp
			
			WebUtils.setSessionAttribute(httpServletRequest, "display_tbl",
					courseClassesMasterVO.getCourseClassesVOs());
			
		}
		System.out.println("In controller :" + courseClassesMasterVO.getCourseClassesSearchVO());
		WebUtils.setSessionAttribute(httpServletRequest, "searchVo",
				courseClassesMasterVO.getCourseClassesSearchVO());
		if (httpServletRequest.getParameter("Get").equals("Get")) {
			WebUtils.setSessionAttribute(httpServletRequest, "keepStatus",
					null);
		}
		else {
			WebUtils.setSessionAttribute(httpServletRequest, "keepStatus",
					"status");
		}
		
		return "redirect:/courseClasses.htm?data";
	}
	
	@RequestMapping(value = "/courseClasses", method = RequestMethod.GET, params = {
			"data", "!actionSave", "!save", "!update", "!delete"
	})
	public ModelAndView getCourseClassesLinkingList(
			@ModelAttribute("courseClasses") CourseClassesMasterVO courseClassesMasterVO,
			ModelMap model, HttpServletRequest httpServletRequest,
			HttpSession session, RedirectAttributes redirectAttribute) {
		
		ModelAndView modelAndView = new ModelAndView(ModelAndViewConstant.COURSE_CLASS_LINK);
		CourseClassesSearchVO courseClassesSearchVO = (CourseClassesSearchVO) WebUtils
				.getSessionAttribute(httpServletRequest, "searchVo");
		courseClassesMasterVO.setCourseClassesSearchVO(courseClassesSearchVO);
		return modelAndView;
	}
	
	@RequestMapping(value = "/courseClasses", method = RequestMethod.GET, params = {
			"actionSave"
	})
	public String addCourseClassesLinking(
			@ModelAttribute("courseClasses") CourseClassesMasterVO courseClassesMasterVO,
			ModelMap model, HttpServletRequest httpServletRequest,
			HttpSession session, RedirectAttributes redirectAttribute) throws NoDataFoundException,
			 BatchUpdateFailedException, DatabaseException,
			NoRecordSelectedException, CustomDuplicateEntryException, DuplicateEntryException {
		
		logger.debug("inside Update course classes linking method");
		ApplicationCache applicationCache = (ApplicationCache) session
				.getServletContext().getAttribute(
						ApplicationConstant.APPLICATION_CACHE);
		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		
		CourseClassesSearchVO courseClassesSearchVO = (CourseClassesSearchVO) WebUtils
				.getSessionAttribute(httpServletRequest, "searchVo");
		courseClassesMasterVO.setCourseClassesSearchVO(courseClassesSearchVO);
		List<CourseClassesVO> courseClassesVOs =
				(List<CourseClassesVO>) WebUtils
						.getSessionAttribute(httpServletRequest, "display_tbl");
		
		ModelAndView mav = new ModelAndView(ModelAndViewConstant.COURSE_CLASS_LINK,
				"courseClasses", courseClassesMasterVO);
		httpServletRequest.setAttribute("page", mav);
		String[] StaffIds = httpServletRequest.getParameterValues("textboxview");
		String[] clsType = httpServletRequest.getParameterValues("clsType");
		String[] labSessNo = httpServletRequest.getParameterValues("labSessNo");
		String[] classNo = httpServletRequest.getParameterValues("classNo");
		String[] labBatch = httpServletRequest.getParameterValues("labBatch");
		String[] duration = httpServletRequest.getParameterValues("duration");
		String[] hiddenRowId = httpServletRequest.getParameterValues("hiddenRowId");
		for (String staff : StaffIds) {
			System.out.println("Staff id seleced :" + staff);
		}
		String[] selectedRowIds = httpServletRequest.getParameterValues("_chk");
		if (selectedRowIds != null) {
			
			// the selected request list row Id
			
		
				courseClassesService.insertCourseClassList(
						sessionCache.getUserSessionDetails(),
						courseClassesSearchVO, courseClassesVOs,
						selectedRowIds, StaffIds, clsType, labSessNo, classNo, labBatch,duration,
						applicationCache,hiddenRowId);
			
		}
		else {
			logger.error("No record selected to process");
			throw new NoRecordSelectedException();
			
		}
		redirectAttribute.addFlashAttribute("courseClasses", courseClassesMasterVO);
		WebUtils.setSessionAttribute(httpServletRequest, "success",
				ErrorCodeConstant.ADD_SUCCESS_MESS);
		return "redirect:/courseClasses.htm?Get";
	}
	
	@RequestMapping(value = "/courseClasses", method = RequestMethod.GET, params = {
			"save"
	})
	public String addSingkeCourseClassesLinking(
			@ModelAttribute("courseClasses") CourseClassesMasterVO courseClassesMasterVO,
			ModelMap model, HttpServletRequest httpServletRequest,
			HttpSession session, RedirectAttributes redirectAttribute) throws NoDataFoundException,
			 BatchUpdateFailedException, DatabaseException,
			NoRecordSelectedException, TableNotSpecifiedForAuditException,  CustomDuplicateEntryException {
		CourseClassesSearchVO courseClassesSearchVO = (CourseClassesSearchVO) WebUtils
				.getSessionAttribute(httpServletRequest, "searchVo");
		courseClassesMasterVO.setCourseClassesSearchVO(courseClassesSearchVO);
		System.out.println("search :"
				+ courseClassesMasterVO.getCourseClassesSearchVO().getStudentGrpId());
		System.out.println("search :"
				+ courseClassesMasterVO.getCourseClassesSearchVO().getAcTerm());
		logger.debug("inside Update course classes linking method");
		
		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		
		List<CourseClassesVO> courseClassesVOs =
				(List<CourseClassesVO>) WebUtils
						.getSessionAttribute(httpServletRequest, "display_tbl");
		
		ModelAndView mav = new ModelAndView(ModelAndViewConstant.COURSE_CLASS_LINK,
				"courseClasses", courseClassesMasterVO);
		httpServletRequest.setAttribute("page", mav);
		String rowId = httpServletRequest.getParameter("rowId");
		String staffId = httpServletRequest.getParameter("sstaff");
		String clsType = httpServletRequest.getParameter("clsType");
		String labSessNo = httpServletRequest.getParameter("labSessNo");
		String classNo = httpServletRequest.getParameter("classNo");
		String duration = httpServletRequest.getParameter("duration");
		String labBatch = httpServletRequest.getParameter("labBatch");
		System.out.println("rowId:" + rowId + " staffId:" + staffId + " clsType:" + clsType
				+ " labSessNo:" + labSessNo + " classNo:" + classNo + " labBatch:" + labBatch+ " duration:" + duration);
		CourseClassesVO courseClassesVO = courseClassesVOs.get(Integer.parseInt(rowId));
		courseClassesVO.setStaffId(staffId);
		courseClassesVO.setClsType(clsType);
		courseClassesVO.setLabBatch(labBatch);
		courseClassesVO.setNoOfClassesPerWeek(classNo);
		courseClassesVO.setNoOfLabClassesPerWeek(labSessNo);
		courseClassesVO.setDuration(duration);
		try {
			courseClassesService
					.insertCourseClassList(
							sessionCache.getUserSessionDetails(),
							courseClassesMasterVO.getCourseClassesSearchVO(), courseClassesVO
					);
		} catch (DuplicateEntryException e) {
			courseClassesVO.setCcId(null);
			courseClassesVOs.add(Integer.parseInt(rowId)+1, courseClassesVO);
			httpServletRequest.setAttribute("parameter",
					courseClassesSearchVO.getAcTerm() + ","
							+ courseClassesSearchVO.getStudentGrpId()
							+ "," + courseClassesVO.getCrslId() + ","
							+ courseClassesVO.getStaffId() + ","
							+ courseClassesVO.getLabBatch());
			throw new CustomDuplicateEntryException();
			
		}
		
		redirectAttribute.addFlashAttribute("courseClasses", courseClassesMasterVO);
		WebUtils.setSessionAttribute(httpServletRequest, "success",
				ErrorCodeConstant.ADD_SUCCESS_MESS);
		return "redirect:/courseClasses.htm?Get";
	}
	
	@RequestMapping(value = "/courseClasses", method = RequestMethod.GET, params = {
			"update"
	})
	public String getCourseClassesLinking(
			@ModelAttribute("courseClasses") CourseClassesMasterVO courseClassesMasterVO,
			ModelMap model, HttpServletRequest httpServletRequest,
			HttpSession session, RedirectAttributes redirectAttribute)
			throws UpdateFailedException, NumberFormatException, NoDataFoundException,
			DuplicateEntryException, DatabaseException, TableNotSpecifiedForAuditException {
		
		logger.debug("inside Update course classes linking method");
		
		List<CourseClassesVO> courseClassesVOs =
				(List<CourseClassesVO>) WebUtils
						.getSessionAttribute(httpServletRequest, "display_tbl");
		CourseClassesSearchVO courseClassesSearchVO = (CourseClassesSearchVO) WebUtils
				.getSessionAttribute(httpServletRequest, "searchVo");
		courseClassesMasterVO.setCourseClassesSearchVO(courseClassesSearchVO);
		System.out.println("search :"
				+ courseClassesMasterVO.getCourseClassesSearchVO().getStudentGrpId());
		System.out.println("search :"
				+ courseClassesMasterVO.getCourseClassesSearchVO().getAcTerm());
		String rowId = httpServletRequest.getParameter("rowId");
		String staffId = httpServletRequest.getParameter("staffId");
		String clsType = httpServletRequest.getParameter("clsType");
		String labSessNo = httpServletRequest.getParameter("labSessNo");
		String classNo = httpServletRequest.getParameter("classNo");
		String labBatch = httpServletRequest.getParameter("labBatch");
		String duration = httpServletRequest.getParameter("duration");
		System.out.println("rowId:" + rowId + " staffId:" + staffId + " clsType:" + clsType
				+ " labSessNo:" + labSessNo + " classNo:" + classNo + " labBatch:" + labBatch+ " duration:" + duration);
		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		ApplicationCache applicationCache = (ApplicationCache) session
				.getServletContext().getAttribute(
						ApplicationConstant.APPLICATION_CACHE);
		System.out.println("Id received from jsp :" + rowId);
		System.out.println("Staff received from jsp :" + staffId);
		CourseClassesVO courseClassesVO = courseClassesVOs.get(Integer.parseInt(rowId));
		courseClassesVO.setStudentGrpId(courseClassesMasterVO.getCourseClassesSearchVO()
				.getStudentGrpId());
		courseClassesVO.setAcTerm(courseClassesMasterVO.getCourseClassesSearchVO().getAcTerm());
		courseClassesVO.setStaffId(staffId);
		courseClassesVO.setClsType(clsType);
		courseClassesVO.setLabBatch(labBatch);
		courseClassesVO.setNoOfClassesPerWeek(classNo);
		courseClassesVO.setNoOfLabClassesPerWeek(labSessNo);
		courseClassesVO.setDuration(duration);
		courseClassesService.updateCourseClasses(courseClassesVO,
				sessionCache.getUserSessionDetails(), applicationCache);
		
		redirectAttribute.addFlashAttribute("courseClasses", courseClassesMasterVO);
		WebUtils.setSessionAttribute(httpServletRequest, "success",
				ErrorCodeConstant.UPDATE_SUCCESS_MESS);
		
		return "redirect:/courseClasses.htm?Get";
	}
	
	@RequestMapping(value = "/courseClasses", method = RequestMethod.GET, params = {
			"delete"
	})
	public String deleteCourseClassesLinking(
			@ModelAttribute("courseClasses") CourseClassesMasterVO courseClassesMasterVO,
			ModelMap model, HttpServletRequest httpServletRequest,
			HttpSession session, RedirectAttributes redirectAttribute)
			throws UpdateFailedException, NumberFormatException, NoDataFoundException,
			DeleteFailedException, DuplicateEntryException, DatabaseException,
			TableNotSpecifiedForAuditException {
		
		logger.debug("inside delete course classes linking method");
		System.out.println("inside ajax delete");
		List<CourseClassesVO> courseClassesVOs =
				(List<CourseClassesVO>) WebUtils
						.getSessionAttribute(httpServletRequest, "display_tbl");
		
		String id = httpServletRequest.getParameter("rowId");
		
		ApplicationCache applicationCache = (ApplicationCache) session
				.getServletContext().getAttribute(
						ApplicationConstant.APPLICATION_CACHE);
		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		
		System.out.println("Id received from jsp :" + id);
		CourseClassesSearchVO courseClassesSearchVO = (CourseClassesSearchVO) WebUtils
				.getSessionAttribute(httpServletRequest, "searchVo");
		courseClassesMasterVO.setCourseClassesSearchVO(courseClassesSearchVO);
		System.out.println("search :"
				+ courseClassesMasterVO.getCourseClassesSearchVO().getStudentGrpId());
		System.out.println("search :"
				+ courseClassesMasterVO.getCourseClassesSearchVO().getAcTerm());
		CourseClassesVO courseClassesVO = courseClassesVOs.get(Integer.parseInt(id));
		courseClassesService.deleteCourseClassesDAORec(courseClassesVO,
				sessionCache.getUserSessionDetails(), applicationCache);
		
		redirectAttribute.addFlashAttribute("courseClasses", courseClassesMasterVO);
		WebUtils.setSessionAttribute(httpServletRequest, "display_tbl", courseClassesVOs);
		WebUtils.setSessionAttribute(httpServletRequest, "success",
				ErrorCodeConstant.DELETE_SUCCESS_MESS);
		return "redirect:/courseClasses.htm?Get";
	}
	
	@RequestMapping(value = "/courseClasses", method = RequestMethod.GET, params = {
			"staff"
	})
	public @ResponseBody
	Map<String, String> getStaffList(
			ModelMap model, HttpServletRequest httpServletRequest, HttpServletResponse response,
			HttpSession session) throws ErrorDescriptionNotFoundException {
		
		logger.debug("inside Update course classes linking method");
		
		ApplicationCache applicationCache = (ApplicationCache) session
				.getServletContext().getAttribute(
						ApplicationConstant.APPLICATION_CACHE);
		
		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		
		String subId = httpServletRequest.getParameter("subId");
		System.out.println("Staff Id in ctrl :"+subId);
		Map<String, String> list = null;
		try {
			list = courseClassesService.selectStaffList(subId,
					sessionCache.getUserSessionDetails());
		}
		
		catch (NoDataFoundException e) {
						
			try {
				response.setStatus(500);
				response.getWriter().write(
						e.getMessage()
								+ ":"
								+ errorCodeUtil.getErrorDescription(applicationCache,
										e.getMessage()));
			}
			catch (IOException e1) {
				
				e1.printStackTrace();
			}
		}
		
		return list;
	}
	
	@ModelAttribute("acTermList")
	public Map<String, String> gerAcademicTermList(HttpSession session,
			@ModelAttribute("courseClasses") CourseClassesMasterVO courseClassesMasterVO,
			HttpServletRequest httpSevletRequest, HttpServletResponse response,
			ModelMap model) throws IOException, NoDataFoundException {
		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		ModelAndView mav = new ModelAndView(ModelAndViewConstant.COURSE_CLASS_LINK,
				"courseClasses", courseClassesMasterVO);
		httpSevletRequest.setAttribute("page", mav);
		Map<String, String> map = dropDownListService.getAcademicTermListTag(sessionCache
				.getUserSessionDetails());
		httpSevletRequest.setAttribute("acTermList", map);
		return map;
		
	}
	
	@ModelAttribute("courseList")
	public Map<String, String> gerCourseNameList(HttpSession session,
			@ModelAttribute("courseClasses") CourseClassesMasterVO courseClassesMasterVO,
			HttpServletRequest httpSevletRequest, HttpServletResponse response,
			ModelMap model) throws IOException, NoDataFoundException {
		ModelAndView mav = new ModelAndView(ModelAndViewConstant.COURSE_CLASS_LINK,
				"courseClasses", courseClassesMasterVO);
		httpSevletRequest.setAttribute("page", mav);
		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		Map<String, String> map = dropDownListService.getCourseNameListTag(sessionCache
				.getUserSessionDetails());
		httpSevletRequest.setAttribute("courseList", map);
		return map;
		
	}
	
	/*@ModelAttribute("studentGrpList")
	public Map<String, String> getStudentGroupList(HttpSession session,
			@ModelAttribute("courseClasses") CourseClassesMasterVO courseClassesMasterVO,
			HttpServletRequest httpSevletRequest, HttpServletResponse response,
			ModelMap model) throws IOException, NoDataFoundException {
		ModelAndView mav = new ModelAndView(ModelAndViewConstant.COURSE_CLASS_LINK,
				"courseClasses", courseClassesMasterVO);
		httpSevletRequest.setAttribute("page", mav);
		Map<String, String> map = null;
		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		map = dropDownListService.getStudentGroupListTag(sessionCache.getUserSessionDetails());
		
		httpSevletRequest.setAttribute("studentGrpList", map);
		return map;
		
	}*/
	
	@ExceptionHandler({
			DuplicateEntryException.class,
			DeleteFailedException.class, UpdateFailedException.class,
			CommonCodeNotFoundException.class, DatabaseException.class,
			BatchUpdateFailedException.class, NoRecordSelectedException.class
	})
	public ModelAndView handleException(Exception ex, HttpSession session,
			HttpServletRequest request) {
		ModelAndView modelAndView = (ModelAndView) request.getAttribute("page");
		modelAndView.getModelMap().addAttribute("error", ex.getMessage());
		WebUtils.setSessionAttribute(request, "success",
				null);
		return modelAndView;
	}
	
	@ExceptionHandler({
			NoDataFoundException.class
	})
	public ModelAndView handleException1(Exception ex, HttpSession session,
			HttpServletRequest request) {
		ModelAndView modelAndView = (ModelAndView) request.getAttribute("page");
		modelAndView.getModelMap().addAttribute("error", ex.getMessage());
		WebUtils.setSessionAttribute(request, "display_tbl",
				null);
		WebUtils.setSessionAttribute(request, "success",
				null);
		return modelAndView;
	}
	@ExceptionHandler({ CustomDuplicateEntryException.class })
	public ModelAndView handleException2(Exception ex, HttpSession session,
			HttpServletRequest request) {
		WebUtils.setSessionAttribute(request, "success", null);
	
		ModelAndView modelAndView = (ModelAndView) request.getAttribute("page");
		String errorParam = (String) request.getAttribute("parameter");
		modelAndView.getModelMap().addAttribute("errorParam", errorParam);
		modelAndView.getModelMap().addAttribute("error", ex.getMessage());
		return modelAndView;

	}
}
