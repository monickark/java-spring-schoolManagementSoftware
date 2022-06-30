package com.jaw.student.controller;

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
import com.jaw.common.dropdown.service.IDropDownListService;
import com.jaw.common.exceptions.DatabaseException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.SectionNotFoundException;
import com.jaw.framework.sessCache.SessionCache;
import com.jaw.student.admission.controller.AdmissionController;
import com.jaw.student.admission.dao.StudentMaster;
import com.jaw.student.service.IStudentAllocationService;

@Controller
public class StudentAllocationController {
	Logger logger = Logger.getLogger(AdmissionController.class);
	@Autowired
	private IStudentAllocationService studentAllocationService;
	@Autowired
	IDropDownListService dropDownListService;	


	@RequestMapping(value = "/studentallocation", method = RequestMethod.GET)
	public ModelAndView loadUploadPage(
			@ModelAttribute("stuAlloc") StudentAllocationVO studentAllocationVO,HttpServletRequest httpRequest) {
		ModelAndView mav = new ModelAndView(".jaw.studentallocation");
		WebUtils.setSessionAttribute(httpRequest, "searchVo",
				null);
		logger.info("studentAlloca");
		return mav;
	}

	@RequestMapping(value = "/studentallocation", method = RequestMethod.GET,params = { "getList" })
	public ModelAndView retriveList(
			@ModelAttribute("stuAlloc") StudentAllocationVO studentAllocationVO,HttpSession session,HttpServletRequest request) throws NoDataFoundException, SectionNotFoundException {	
		Map<String, String> map = null;
		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		request.setAttribute("stuAlloc", studentAllocationVO);
		ModelAndView mav = new ModelAndView(".jaw.studentallocation");
		WebUtils.setSessionAttribute(request, "searchVo",
				studentAllocationVO);
		List<StudentMaster> studentMaster = studentAllocationService
				.studentAllocList(studentAllocationVO,sessionCache.getUserSessionDetails());		
		//Get section List
		map = dropDownListService.getSectionList(sessionCache.getUserSessionDetails(),studentAllocationVO.getCourse(),
				studentAllocationVO.getStandard(),null);
		if(map.size()==0){
			throw new SectionNotFoundException();
		}
		
		map.remove(studentAllocationVO.getSection());
		
		mav.getModelMap().addAttribute("studentList", studentMaster);
		mav.getModelMap().addAttribute("stuAlloc", studentAllocationVO);
		System.out.println("Student allocation vo:"+studentAllocationVO);
		mav.getModelMap().addAttribute("secMap", map);		
		return mav;
	}

	@RequestMapping(value = "/studentallocation", method = RequestMethod.POST,params = { "secAlloc" })
	public ModelAndView loadUpload(
			@ModelAttribute("stuAlloc") StudentAllocationVO studentAllocationVO,
			HttpServletRequest request,HttpSession session) throws DuplicateEntryException, DatabaseException {	
		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		ModelAndView mav = new ModelAndView(".jaw.studentallocation");
		String[] updatingRecords = request.getParameterValues("stuAdmisNoInSAUpdate");
		String sec = request.getParameter("yourFieldName");
		String[] dbtsValues = request.getParameterValues("dbtsUpdate");
		String academicYear = request.getParameter("hiddenAcy");
		String courseId = request.getParameter("hiddenCourse");
		String termId = request.getParameter("hiddenStd");
		String[] sectionValues = request.getParameterValues("sectionInSecAllocUpdate");
		
		studentAllocationVO.setAcademicYear(academicYear);				
		
		  studentAllocationService.sectionAllocation(updatingRecords, sec,sessionCache.getUserSessionDetails(),
				  studentAllocationVO.getAcademicYear(),dbtsValues,sectionValues,courseId,termId);
		 
		  mav.getModelMap().addAttribute("success", ErrorCodeConstant.SECTION_ALLOCATED_SUCCESSFULLY);
		return mav;
	}
	

	@ModelAttribute("courseList")
	public Map<String, String> gerCourseNameList(HttpSession session,
			HttpServletRequest httpSevletRequest, HttpServletResponse response,
			ModelMap model) throws IOException {
		Map<String, String> map = null;
		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		try {
			map = dropDownListService.getCourseNameListTag(sessionCache.getUserSessionDetails());
		} catch (NoDataFoundException e) {
			logger.error("Error occured in Course Name list Dropdown :" + e.getMessage());
		}
		httpSevletRequest.setAttribute("courseList", map);
		return map;

	}
	
	@RequestMapping(value = "/studentallocation", method = RequestMethod.GET, params = { "trmList" })
	public @ResponseBody Map<String,String> getTermList(HttpSession session,
			HttpServletRequest request, HttpServletResponse response,
			ModelMap model){
		
		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		String courseId = request.getParameter("courseId");		
		
		Map<String,String> listOfTrm = studentAllocationService.termList(sessionCache.getUserSessionDetails().getInstId(), 
				sessionCache.getUserSessionDetails().getBranchId(), courseId);
		
		return listOfTrm;
		
	}
	
	
	@ExceptionHandler({ 
		NoDataFoundException.class,DuplicateEntryException.class, DatabaseException.class,SectionNotFoundException.class  })
public ModelAndView handleNoDataException(Exception ex, HttpSession session,
		HttpServletRequest request) {

		StudentAllocationVO studentAllocationVO = (StudentAllocationVO) request
				.getAttribute("stuAlloc");

		ModelAndView mav = new ModelAndView(".jaw.studentallocation",
				"stuAlloc", studentAllocationVO);
		mav.getModelMap().addAttribute("error", ex.getMessage());
		return mav;

	}
		
	
}
