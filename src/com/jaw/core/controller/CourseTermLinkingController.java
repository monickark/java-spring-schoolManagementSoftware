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
import com.jaw.common.exceptions.UpdateFailedException;
import com.jaw.common.exceptions.util.TableNotSpecifiedForAuditException;
import com.jaw.core.service.IAcademicCalendarService;
import com.jaw.core.service.ICourseTermLinkListService;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.sessCache.SessionCache;
@Controller
public class CourseTermLinkingController {
	// Logging
		Logger logger = Logger.getLogger(CourseTermLinkingController.class);
		@Autowired
		ICourseTermLinkListService courseTermLinkListService;
		@Autowired
		IDropDownListService dropDownListService;
		 List<CourseTermLinkingVO> courseTrmList=null;
		// Academic Calendar List Get method

		@RequestMapping(value = "/courseTrmLinkList", method = RequestMethod.GET)
		public ModelAndView courseTermLinking(
				@ModelAttribute("courseTermLink") CourseTermLinkingMasterVO courseTermLinkingMasterVO,
				ModelMap model, HttpServletRequest httpRequest,
				HttpSession httpSession) {

			logger.info("Rendering Course term linking List page");
			// Null the session display tag values
			ModelAndView mav = new ModelAndView(
					ModelAndViewConstant.COURSE_TERM_LINK_LIST, "courseTermLink",
					courseTermLinkingMasterVO);
			WebUtils.setSessionAttribute(httpRequest, "display_tbl", null);
			WebUtils.setSessionAttribute(httpRequest, "success", null);
			WebUtils.setSessionAttribute(httpRequest, "keepStatus",
					null);
			WebUtils.setSessionAttribute(httpRequest, "searchVo",
					null);
			WebUtils.setSessionAttribute(httpRequest, "addAction",
					null);
			WebUtils.setSessionAttribute(httpRequest, "errorobject",
					null);
			mav.getModelMap().addAttribute("status", "true");
			return mav;

		}
		
		@RequestMapping(value = "/courseTrmLinkList", method = RequestMethod.GET, params = { "Get" })
		public String viewCourseTermLinkList(
				@ModelAttribute("courseTermLink") CourseTermLinkingMasterVO courseTermLinkingMasterVO,
				HttpSession session, HttpServletRequest httpRequest,ModelMap model,RedirectAttributes redirectAttributes) throws NoDataFoundException {

			// Getting the display tag parameter
			WebUtils.setSessionAttribute(httpRequest, "errorobject",
					null);
			Map stockParamMap = WebUtils.getParametersStartingWith(httpRequest,
					"d-");
			SessionCache sessionCache = (SessionCache) session
					.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
			// Setting model and view for exception handler
			ModelAndView modelAndView = new ModelAndView(
					ModelAndViewConstant.COURSE_TERM_LINK_LIST, "courseTermLink",
					courseTermLinkingMasterVO);
			System.out.println("action request attributttttttttttt"+httpRequest.getParameter("action"));
		
			if(httpRequest.getParameter("action")==null){				
				WebUtils.setSessionAttribute(httpRequest, "addAction",
						null);
			}else{	
				String searchString= (String) WebUtils
						.getSessionAttribute(httpRequest, "searchVo");		
				if(searchString!=null){
					courseTermLinkingMasterVO.setCourseMasterId(searchString);
				}
				WebUtils.setSessionAttribute(httpRequest, "addAction",
						"AddAction");
			}
		
			httpRequest.setAttribute("page", modelAndView);
			httpRequest.setAttribute("errors", "error");
		
			
			// Check whether the list already get or have to fetch from data base
			// using the list size
			if (httpRequest.getParameter("Get").equals("Get")) {
				WebUtils.setSessionAttribute(httpRequest, "keepStatus",
						null);
				WebUtils.setSessionAttribute(httpRequest, "success", null);
			}
			else {
				WebUtils.setSessionAttribute(httpRequest, "keepStatus",
						"status");
			}
			if (stockParamMap.size() == 0) {

				logger.info("Table operation has been triggered");

				// Get list from database

				courseTermLinkListService.selectCourseTermLinkList(courseTermLinkingMasterVO,sessionCache.getUserSessionDetails());
				courseTrmList=courseTermLinkingMasterVO.getCourseTermLinkingVOs();
				// Set the list to session to access in jsp
				
				WebUtils.setSessionAttribute(httpRequest, "display_tbl",
						courseTrmList);
				WebUtils.setSessionAttribute(httpRequest, "searchVo",
						courseTermLinkingMasterVO.getCourseMasterId());
				courseTermLinkingMasterVO.setPageSize(courseTermLinkingMasterVO.getPageSize());
			}
			redirectAttributes.addFlashAttribute("courseTermLink", courseTermLinkingMasterVO);
			
			return "redirect:/courseTrmLinkList.htm?data";

			
		}
		@RequestMapping(value = "/courseTrmLinkList", method = RequestMethod.GET, params = "data")
		public ModelAndView courseTermLinkListBack(
				@ModelAttribute("courseTermLink") CourseTermLinkingMasterVO courseTermLinkingMasterVO,ModelMap model,
				HttpServletRequest httpServletRequest) {
		
			String searchString= (String) WebUtils
					.getSessionAttribute(httpServletRequest, "searchVo");		
			if(searchString!=null){
				courseTermLinkingMasterVO.setCourseMasterId(searchString);
			}
			
			ModelAndView modelAndView = new ModelAndView(
					ModelAndViewConstant.COURSE_TERM_LINK_LIST, "courseTermLink",
					courseTermLinkingMasterVO);
		
			return modelAndView;
		}
		
		@RequestMapping(value = "/courseTrmLinkList", method = RequestMethod.GET, params = { "actionSave" })
		public String saveCourseTermLink(
				@ModelAttribute("courseTermLink") CourseTermLinkingMasterVO courseTermLinkingMasterVO,
				ModelMap model, HttpServletRequest httpServletRequest,
				HttpSession session, RedirectAttributes redirectAttribute)
				throws  DatabaseException, UpdateFailedException, DuplicateEntryException {

			logger.debug("inside save Course Term linking method");
			
			SessionCache sessionCache = (SessionCache) session
					.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
			String searchString= (String) WebUtils
					.getSessionAttribute(httpServletRequest, "searchVo");		
			if(searchString!=null){
				courseTermLinkingMasterVO.setCourseMasterId(searchString);
			}
			redirectAttribute.addFlashAttribute("courseTermLink", courseTermLinkingMasterVO);
			List<CourseTermLinkingVO> courseTermLinkingVOs = (List<CourseTermLinkingVO>) WebUtils
					.getSessionAttribute(httpServletRequest, "display_tbl");
			ModelAndView modelAndView = new ModelAndView(
					ModelAndViewConstant.COURSE_TERM_LINK_LIST, "courseTermLink",
					courseTermLinkingMasterVO);	
			httpServletRequest.setAttribute("page", modelAndView);
			httpServletRequest.setAttribute("errors", "error");
			CourseTermLinkingVO courseTermLinkingVO=new CourseTermLinkingVO();
			courseTermLinkingVO.setCourseMasterId(httpServletRequest.getParameter("courseID"));
			courseTermLinkingVO.setTermId(httpServletRequest.getParameter("termId"));
			courseTermLinkingVO.setTermSerialOrder(Integer.parseInt(httpServletRequest.getParameter("termOrderNum").trim()));
			courseTermLinkingMasterVO.setCourseTermLinkingVO(courseTermLinkingVO);
			String rowId=httpServletRequest.getParameter("RowId") ; 
			
			try {
				courseTermLinkListService.insertCourseTermLinkRec(courseTermLinkingMasterVO, sessionCache.getUserSessionDetails());
			} catch (DuplicateEntryException e) {
				System.out.println("course term link vosssssss  : "+courseTermLinkingVO.toString());				
				
				WebUtils.setSessionAttribute(httpServletRequest, "errorobject",
						courseTermLinkingVO);
				throw new DuplicateEntryException();
			}
			WebUtils.setSessionAttribute(httpServletRequest, "success",
					ErrorCodeConstant.ADD_SUCCESS_MESS);
			logger.debug("Data's inserted successfully!");
			WebUtils.setSessionAttribute(httpServletRequest, "keepStatus", "status");
			return "redirect:/courseTrmLinkList.htm?Get";
		}

		
		//Delete Method
		@RequestMapping(value = "/courseTrmLinkList", method = RequestMethod.GET, params = { "actionDelete" })
		public String deleteCourseTermLink(
				@ModelAttribute("courseTermLink") CourseTermLinkingMasterVO courseTermLinkingMasterVO,
				ModelMap model, HttpServletRequest httpServletRequest,
				HttpSession session, RedirectAttributes redirectAttribute)
				throws DeleteFailedException, NoDataFoundException, DuplicateEntryException, DatabaseException, TableNotSpecifiedForAuditException {

			logger.debug("inside Deleted Student Group Master method");
			ApplicationCache applicationCache = (ApplicationCache) session
					.getServletContext().getAttribute(
							ApplicationConstant.APPLICATION_CACHE);
			SessionCache sessionCache = (SessionCache) session
					.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
			String searchString= (String) WebUtils
					.getSessionAttribute(httpServletRequest, "searchVo");		
			if(searchString!=null){
				courseTermLinkingMasterVO.setCourseMasterId(searchString);
			}
			redirectAttribute.addFlashAttribute("courseTermLink", courseTermLinkingMasterVO);
			ModelAndView modelAndView = new ModelAndView(
					ModelAndViewConstant.COURSE_TERM_LINK_LIST, "courseTermLink",
					courseTermLinkingMasterVO);	
			httpServletRequest.setAttribute("page", modelAndView);
			httpServletRequest.setAttribute("errors", "error");
			
			List<CourseTermLinkingVO> courseTermLinkingVOs = (List<CourseTermLinkingVO>) WebUtils
					.getSessionAttribute(httpServletRequest, "display_tbl");
			
			String id = httpServletRequest.getParameter("rowId");
			courseTermLinkingMasterVO.setCourseTermLinkingVO(courseTermLinkingVOs.get(Integer.parseInt(id)));
			courseTermLinkListService.deleteCourseTermLinkingRec(courseTermLinkingMasterVO.getCourseTermLinkingVO(),
					sessionCache.getUserSessionDetails(),applicationCache);
			WebUtils.setSessionAttribute(httpServletRequest, "success",
					ErrorCodeConstant.DELETE_SUCCESS_MESS);
			logger.debug("Data's deleted successfully!");
			WebUtils.setSessionAttribute(httpServletRequest, "keepStatus", "status");
			return "redirect:/courseTrmLinkList.htm?Get";
		}
		@ModelAttribute("courseList")
		public Map<String, String> gerCourseNameList(HttpSession session,
				HttpServletRequest httpSevletRequest, HttpServletResponse response,ModelMap model) throws IOException {
			SessionCache sessionCache = (SessionCache) session
					.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
			Map<String, String> map = null;
			try {
				map = dropDownListService.getCourseNameListTag(sessionCache.getUserSessionDetails());
			} catch (NoDataFoundException e) {
				logger.error("Error occured in Course Name list Dropdown :" + e.getMessage());
			}
			httpSevletRequest.setAttribute("courseList", map);
			return map;

		}
		
		@ExceptionHandler({ DuplicateEntryException.class, DatabaseException.class,
			DeleteFailedException.class,UpdateFailedException.class ,  TableNotSpecifiedForAuditException.class})
	public ModelAndView handleException(Exception ex, HttpSession session,
			HttpServletRequest request) {
		ModelAndView mav = (ModelAndView) request.getAttribute("page");
		String error = (String) request.getAttribute("errors");
		mav.getModelMap().addAttribute(error, ex.getMessage());
		WebUtils.setSessionAttribute(request, "success", null);
		return mav;

	}
	@ExceptionHandler({ 
		NoDataFoundException.class  })
public ModelAndView handleNoDataException(Exception ex, HttpSession session,
		HttpServletRequest request) {
		logger.error("Exception Handledddd :" + ex.getMessage());	
		ModelAndView mav = (ModelAndView) request.getAttribute("page");
		String message = (String) WebUtils.getSessionAttribute(request,
				"success");
		if ((message != null)
				&& (message.equals(ErrorCodeConstant.DELETE_SUCCESS_MESS))) {	
			mav.getModelMap().addAttribute("error",ErrorCodeConstant.DELETE_SUCCESS_MESS);
			
		} else {		
			mav.getModelMap().addAttribute("error", ex.getMessage());
		}
		WebUtils.setSessionAttribute(request, "success", null);

		WebUtils.setSessionAttribute(request, "display_tbl", null);		
		
	return mav;
}
}
