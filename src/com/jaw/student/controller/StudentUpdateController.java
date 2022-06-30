package com.jaw.student.controller;

import java.io.IOException;
import java.util.ArrayList;
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
import com.jaw.common.exceptions.BatchUpdateFailedException;
import com.jaw.common.exceptions.DatabaseException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.InvalidCategoryForUpdateException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.util.ErrorCodeUtil;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.sessCache.SessionCache;
import com.jaw.student.service.IStudentUpdatesService;

@Controller
public class StudentUpdateController {
	// Logging
	Logger logger = Logger.getLogger(StudentUpdateController.class);
	@Autowired
	IStudentUpdatesService stuUpdatesService;
	@Autowired
	IDropDownListService dropDownListService;
	@Autowired
	ErrorCodeUtil errorCodeUtil;

	
	//Method to render the Student update Page
	@RequestMapping(value = "/stuUpdate", method = RequestMethod.GET)
	public ModelAndView getStudentUpdatePage(
			@ModelAttribute("stuUpdate") StudentUpdateMasterVO studentUpdatesSearch,
			ModelMap modelMap, HttpServletRequest httpServletRequest) {

		ModelAndView mav = new ModelAndView(ModelAndViewConstant.STU_UPDATE);
		//Session attributes to be null
		WebUtils.setSessionAttribute(httpServletRequest, "display_tbl", null);
		WebUtils.setSessionAttribute(httpServletRequest, "success", null);
		WebUtils.setSessionAttribute(httpServletRequest, "keepStatus", null);
		WebUtils.setSessionAttribute(httpServletRequest, "searchVo", null);
		mav.getModelMap().addAttribute("status", "true");
		
		return mav;
	}

	//Data method,in which every action will be ended(Session attributes won't be null)
	@RequestMapping(value = "/stuUpdate", method = RequestMethod.GET, params = "data")
	public ModelAndView getStudentUpdatePageData(
			@ModelAttribute("stuUpdate") StudentUpdateMasterVO studentUpdatesSearch,
			ModelMap modelMap, HttpServletRequest httpServletRequest,
			RedirectAttributes redirectAttributes) {

		ModelAndView mav = new ModelAndView(ModelAndViewConstant.STU_UPDATE);
		StudentUpdateMasterVO studentUpdateMasterVO = (StudentUpdateMasterVO) WebUtils
				.getSessionAttribute(httpServletRequest, "searchVo");
		studentUpdateMasterVO.setPageSize(studentUpdatesSearch.getPageSize());
		mav.getModelMap().addAttribute("stuUpdate", studentUpdateMasterVO);

		//To keep the Edited List values persistent
		String[] selectedValues = httpServletRequest
				.getParameterValues("selectedOpt");
		String[] rowIds = httpServletRequest.getParameterValues("rowids");			
		List<StudentUpdateListVO> stuList = (List<StudentUpdateListVO>) WebUtils
				.getSessionAttribute(httpServletRequest, "display_tbl");
		if (stuList != null) {
			if ((selectedValues != null)) {
				for (int j = 0; j < selectedValues.length; j++) {
					int index = Integer.parseInt(rowIds[j]);
					stuList.get(index - 1).setColValue(selectedValues[j]);
				}

			}
		}

		return mav;
	}

	//Get the List from stum,for the update operation
	@RequestMapping(value = "/stuUpdate", method = RequestMethod.GET, params = "Get")
	public String getStudentListForUpdate(
			@ModelAttribute("stuUpdate") StudentUpdateMasterVO studentUpdatesSearch,
			ModelMap modelMap, HttpServletRequest httpServletRequest,
			HttpSession session, RedirectAttributes attributes)
			throws NoDataFoundException, InvalidCategoryForUpdateException {
		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		
		// Getting the display tag parameter
		Map stockParamMap = WebUtils.getParametersStartingWith(
				httpServletRequest, "d-");
		WebUtils.setSessionAttribute(httpServletRequest,"keepStatus", true);		
		if ((httpServletRequest != null)
				&& (httpServletRequest.getParameter("Get").equals("Get"))) {
			WebUtils.setSessionAttribute(httpServletRequest,"keepStatus", null);
			WebUtils.setSessionAttribute(httpServletRequest, "success", null);
			
		} else {
			if ((StudentUpdateMasterVO) WebUtils.getSessionAttribute(
					httpServletRequest, "searchVo") != null) {
				studentUpdatesSearch = (StudentUpdateMasterVO) WebUtils
						.getSessionAttribute(httpServletRequest, "searchVo");			

			}			
		}

		// Setting model and view for exception handler
		httpServletRequest.setAttribute("stuUpdate", studentUpdatesSearch);
		httpServletRequest.setAttribute("errors", "error"); // Check whether the
															// list already get
															// or have to fetch
															// from data base
		// using the list size
		if (stockParamMap.size() == 0) {

			logger.info("Table operation has been triggered");
			
			try {
				// Get list from database
				stuUpdatesService.getStuListForUpdates(
						sessionCache.getUserSessionDetails(),
						studentUpdatesSearch);
			} catch (NoDataFoundException e) {
				WebUtils.setSessionAttribute(httpServletRequest, "display_tbl",
						null);
				throw new NoDataFoundException();
			}
			
			// Set the list & searchVo to session to access in jsp
			WebUtils.setSessionAttribute(httpServletRequest, "display_tbl",
					studentUpdatesSearch.getStuList());
			WebUtils.setSessionAttribute(httpServletRequest, "searchVo",
					studentUpdatesSearch);
		}

		return "redirect:/stuUpdate.htm?data";
	}

	// Method to update the modified values
	@RequestMapping(value = "/stuUpdate", method = RequestMethod.GET, params = {
			"data", "actionSave" })
	public String saveAttendance(
			@ModelAttribute("stuUpdate") StudentUpdateMasterVO studentUpdatesSearch,
			ModelMap modelMap, HttpServletRequest httpServletRequest,
			HttpSession session, RedirectAttributes attributes)
			throws DatabaseException, DuplicateEntryException,
			BatchUpdateFailedException {
		StudentUpdateMasterVO studentUpdateMasterVO = (StudentUpdateMasterVO) WebUtils
				.getSessionAttribute(httpServletRequest, "searchVo");

		studentUpdateMasterVO.setPageSize(studentUpdatesSearch.getPageSize());

		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);

		//To keep the Edited List values persistent
		String[] selectedValues = httpServletRequest
				.getParameterValues("selectedOpt");
		String[] rowIds = httpServletRequest.getParameterValues("rowids");
		List<StudentUpdateListVO> stuList = (List<StudentUpdateListVO>) WebUtils
				.getSessionAttribute(httpServletRequest, "display_tbl");
		if (stuList != null) {
			if ((selectedValues != null)) {
				for (int j = 0; j < selectedValues.length; j++) {
					int index = Integer.parseInt(rowIds[j]);
					stuList.get(index - 1).setColValue(selectedValues[j]);
				}

			}
		}		
		WebUtils.setSessionAttribute(httpServletRequest, "keepStatus", "status");
		httpServletRequest.setAttribute("stuUpdate", studentUpdateMasterVO);
		//Save the selected Objects only-from check box
		String[] checkBoxes = httpServletRequest.getParameterValues("_chk");	
		if(checkBoxes!=null){	
			List<StudentUpdateListVO> studentListForSave = new ArrayList<StudentUpdateListVO>();
			for(String checkBox:checkBoxes){				
				studentListForSave.add(stuList.get((Integer.valueOf(checkBox))-1));
			}			
			studentUpdateMasterVO.setStuList(studentListForSave);
			stuUpdatesService.updatedStuList(sessionCache.getUserSessionDetails(),
					studentUpdateMasterVO);
			studentUpdatesSearch = (StudentUpdateMasterVO) WebUtils
					.getSessionAttribute(httpServletRequest, "searchVo");
			WebUtils.setSessionAttribute(httpServletRequest, "success",
					ErrorCodeConstant.STUU_SUCCESS);

			logger.debug("Student Update has been successfully done!");
			
			
		}				
		

		return "redirect:/stuUpdate.htm?Get";
	}

/*	@ModelAttribute("studentGrpList")
	public Map<String, String> gerCourseNameList(HttpSession session,
			HttpServletRequest httpSevletRequest, HttpServletResponse response,
			ModelMap model) throws IOException {
		Map<String, String> map = null;
		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		try {
			map = dropDownListService.getStudentGroupListTag(sessionCache
					.getUserSessionDetails());
		} catch (NoDataFoundException e) {
			logger.error("Error occured in Student Group Dropdown :"
					+ e.getMessage());
		}
		httpSevletRequest.setAttribute("studentGrpList", map);
		return map;

	}
*/
	@ModelAttribute("acTermList")
	public Map<String, String> gerAcaTrmList(HttpSession session,
			HttpServletRequest httpSevletRequest, HttpServletResponse response,
			ModelMap model) throws IOException {
		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		Map<String, String> map = null;

		try {
			map = dropDownListService
					.getAcademicTermListForPresentAndFuture(sessionCache
							.getUserSessionDetails());
		} catch (NoDataFoundException e) {
			logger.error("Error occured in Academic Term Dropdown :"
					+ e.getMessage());
		}
		httpSevletRequest.setAttribute("acTermList", map);
		return map;

	}

	@ExceptionHandler({ NoDataFoundException.class,
			BatchUpdateFailedException.class,
			InvalidCategoryForUpdateException.class })
	public ModelAndView handleException(Exception ex, HttpSession session,
			HttpServletRequest request) {
		StudentUpdateMasterVO stuMasterVO = (StudentUpdateMasterVO) request
				.getAttribute("stuUpdate");
		ModelAndView modelAndView = new ModelAndView(
				ModelAndViewConstant.STU_UPDATE);
		modelAndView.getModelMap().addAttribute("stuUpdate", stuMasterVO);		
	//	String error = (String) request.getAttribute("errors");
		WebUtils.setSessionAttribute(request, "success", null);
		WebUtils.setSessionAttribute(request, "display_tbl", null);
		modelAndView.getModelMap().addAttribute("error", ex.getMessage());

		return modelAndView;

	}

}
