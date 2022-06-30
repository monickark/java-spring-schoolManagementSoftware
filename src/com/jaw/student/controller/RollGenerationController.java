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
import com.jaw.common.exceptions.BatchUpdateFailedException;
import com.jaw.common.exceptions.DatabaseException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.SectionNotAllocatedException;
import com.jaw.common.exceptions.util.ErrorDescriptionNotFoundException;
import com.jaw.common.util.ErrorCodeUtil;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.sessCache.SessionCache;
import com.jaw.student.admission.dao.StudentMaster;
import com.jaw.student.service.IStudentRollGenService;

@Controller
public class RollGenerationController {
	Logger logger = Logger.getLogger(RollGenerationController.class);

	@Autowired
	IStudentRollGenService studentRollGenService;
	@Autowired
	ErrorCodeUtil errorCodeUtil;
	@Autowired
	IDropDownListService dropDownListService;

	//Method to render the Roll No Generation Page
	@RequestMapping(value = "/rollnogeneration", method = RequestMethod.GET)
	public ModelAndView rollNoSearch(
			@ModelAttribute("sturoll") StudentRollSearchVO studentSearchVO,
			HttpServletRequest request,HttpSession session) throws DatabaseException, DuplicateEntryException, NoDataFoundException, BatchUpdateFailedException, SectionNotAllocatedException {	
		ModelAndView mav = new ModelAndView(".jaw.rollgeneration");
		WebUtils.setSessionAttribute(request, "display_tbl", null);
		WebUtils.setSessionAttribute(request, "success", null);
		WebUtils.setSessionAttribute(request, "keepStatus",
				null);
		WebUtils.setSessionAttribute(request, "search",
				null);
		mav.getModelMap().addAttribute("status", "true");
		WebUtils.setSessionAttribute(request, "display_tbl",
				null);	
		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		studentSearchVO.setAcademicYear(sessionCache.getUserSessionDetails().getCurrAcTerm());
		return mav;

	}
	
	//Method to redirect after post method
	@RequestMapping(value = "/rollnogeneration", method = RequestMethod.GET, params = "data")
	public ModelAndView rollNoRender(
			@ModelAttribute("sturoll") StudentRollSearchVO studentSearchVO,
			HttpServletRequest request,HttpSession session) throws DatabaseException, DuplicateEntryException, NoDataFoundException, BatchUpdateFailedException, SectionNotAllocatedException {
		
		StudentRollSearchVO	studentRoll= (StudentRollSearchVO) WebUtils
				.getSessionAttribute(request, "search");				
			ModelAndView mav = new ModelAndView(".jaw.rollgeneration");				
				
			studentRoll.setPageSize(studentSearchVO.getPageSize());		
		
			mav.getModelMap().addAttribute("sturoll", studentRoll);
				return mav;
		}
	
		
	
	//Method to check the roll no has been already allocated or not
	@RequestMapping(value = "/rollnogeneration", method = RequestMethod.GET, params = "rollNo")
	public @ResponseBody String rollNoCheck(
			@ModelAttribute("sturoll") StudentRollSearchVO studentSearchVO,
			HttpServletRequest request,HttpSession session) throws DatabaseException, DuplicateEntryException, NoDataFoundException, BatchUpdateFailedException, SectionNotAllocatedException {
		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		String acTerm = request.getParameter("aTrm");
		String stuGrpId = request.getParameter("sGrpId");	
	Integer count = studentRollGenService.checkForRollNoGeneration(sessionCache.getUserSessionDetails(), acTerm, stuGrpId);			
	if(count!=0){
		return "Don't Proceed";
		
	}else{
		return "Proceed";
	}
				
		}

//Method to Allocate Roll No & Fetch the list-Ajax call
	@RequestMapping(value = "/rollnogeneration", method = RequestMethod.POST)
	public String rollNoGeneration(
			@ModelAttribute("sturoll") StudentRollSearchVO studentSearchVO,
			HttpSession session, HttpServletRequest request)
			throws ErrorDescriptionNotFoundException, NoDataFoundException, BatchUpdateFailedException, DatabaseException, DuplicateEntryException, SectionNotAllocatedException {	
		request.setAttribute("sturoll", studentSearchVO);	
		ApplicationCache applicationCache = (ApplicationCache) session
				.getServletContext().getAttribute(
						ApplicationConstant.APPLICATION_CACHE);
		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
			
		if ((request.getParameter("Get")!=null)&&(request.getParameter("Get").equals("Generate RollNo")) ){			
			WebUtils.setSessionAttribute(request, "display_tbl", null);
			WebUtils.setSessionAttribute(request, "success", null);
		}
		else {			
			WebUtils.setSessionAttribute(request, "keepStatus",
					"status");
		}
		
		List<StudentMaster> stuMasterList = null;	
		stuMasterList = studentRollGenService.getStudentList(
				applicationCache, studentSearchVO,
				sessionCache.getUserSessionDetails());	
	
	WebUtils.setSessionAttribute(request, "display_tbl",
			stuMasterList);		
	WebUtils.setSessionAttribute(request, "search",
			studentSearchVO);
		
		logger.info("roll number generated");
		WebUtils.setSessionAttribute(request, "success",
				ErrorCodeConstant.ROLL_NUM_SUCCESS);		
		
		return "redirect:/rollnogeneration.htm?data";	
	}
	
	@ModelAttribute("acTermList")
	public Map<String, String> gerAcaTrmList(HttpSession session,
			HttpServletRequest httpSevletRequest, HttpServletResponse response,
			ModelMap model) throws IOException {
		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		Map<String, String> map = null;

		try {
			map = dropDownListService.getAcademicTermListForPresentAndFuture(sessionCache
					.getUserSessionDetails());
		} catch (NoDataFoundException e) {
			logger.error("Error occured in Academic Term Dropdown :" + e.getMessage());			
		}
		httpSevletRequest.setAttribute("acTermList", map);
		return map;

	}
	
	
/*	@ModelAttribute("studentGrpList")
	public Map<String, String> gerCourseNameList(HttpSession session,
			HttpServletRequest httpSevletRequest, HttpServletResponse response,
				ModelMap model) throws IOException {
			Map<String, String> map = null;
			SessionCache sessionCache = (SessionCache) session
					.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
			try {
				map = dropDownListService.getStudentGroupListTag(sessionCache.getUserSessionDetails());
			} catch (NoDataFoundException e) {
				logger.error("Error occured in Student Group Dropdown :" + e.getMessage());			
			}
			httpSevletRequest.setAttribute("studentGrpList", map);
			return map;
	
		}
		*/
	
	@ExceptionHandler({ ErrorDescriptionNotFoundException.class,NoDataFoundException.class,
		BatchUpdateFailedException.class,DatabaseException.class,DuplicateEntryException.class,SectionNotAllocatedException.class})
public ModelAndView handleException(Exception ex, HttpSession session,
		HttpServletRequest request) {

		StudentRollSearchVO studentRollSearchVO = (StudentRollSearchVO) request
			.getAttribute("sturoll");
		WebUtils.setSessionAttribute(request, "display_tbl",
				null);
		WebUtils.setSessionAttribute(request, "success",
				null);
	ModelAndView mav = new ModelAndView(".jaw.rollgeneration",
			"sturoll", studentRollSearchVO);
	mav.getModelMap().addAttribute("error", ex.getMessage());
	return mav;

}

}
