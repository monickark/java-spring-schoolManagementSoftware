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
import com.jaw.common.exceptions.batch.DataIntegrityException;
import com.jaw.common.exceptions.batch.RuntimeExceptionForBatch;
import com.jaw.common.util.ErrorCodeUtil;
import com.jaw.core.controller.CourseClassesMasterVO;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.sessCache.SessionCache;
import com.jaw.student.service.IStudentTransportService;

@Controller
public class StudentTransportController {
	// Logging
	Logger logger = Logger.getLogger(StudentTransportController.class);
	@Autowired
	IStudentTransportService studentTransportService;
	@Autowired
	IDropDownListService dropDownListService;
	@Autowired
	ErrorCodeUtil errorCodeUtil;

	
	//Method to render the Student update Page
	@RequestMapping(value = "/studentTransport", method = RequestMethod.GET)
	public ModelAndView getStudentTransportPage(
			@ModelAttribute("studentTransport") StudentTransportMasterVO StudentTransportsSearch,
			ModelMap modelMap, HttpServletRequest httpServletRequest) {

		ModelAndView mav = new ModelAndView(ModelAndViewConstant.STU_TRANSPORT);
		//Session attributes to be null
		WebUtils.setSessionAttribute(httpServletRequest, "display_tbl", null);
		WebUtils.setSessionAttribute(httpServletRequest, "success", null);
		WebUtils.setSessionAttribute(httpServletRequest, "keepStatus", null);
		WebUtils.setSessionAttribute(httpServletRequest, "searchVo", null);
		mav.getModelMap().addAttribute("status", "true");
		
		return mav;
	}

	//Data method,in which every action will be ended(Session attributes won't be null)
	@RequestMapping(value = "/studentTransport", method = RequestMethod.GET, params = "data")
	public ModelAndView getStudentTransportPageData(
			@ModelAttribute("studentTransport") StudentTransportMasterVO StudentTransportsSearch,
			ModelMap modelMap, HttpServletRequest httpServletRequest,
			RedirectAttributes redirectAttributes) {

		ModelAndView mav = new ModelAndView(ModelAndViewConstant.STU_TRANSPORT);
		StudentTransportMasterVO StudentTransportMasterVO = (StudentTransportMasterVO) WebUtils
				.getSessionAttribute(httpServletRequest, "searchVo");
		StudentTransportMasterVO.setPageSize(StudentTransportsSearch.getPageSize());
		mav.getModelMap().addAttribute("studentTransport", StudentTransportMasterVO);

		//To keep the Edited List values persistent
		String[] selectedValues = httpServletRequest
				.getParameterValues("selectedOpt");
		String[] rowIds = httpServletRequest.getParameterValues("rowids");			
		List<StudentTransportListVO> stuList = (List<StudentTransportListVO>) WebUtils
				.getSessionAttribute(httpServletRequest, "display_tbl");
		if (stuList != null) {
			if ((selectedValues != null)) {
				for (int j = 0; j < selectedValues.length; j++) {
					int index = Integer.parseInt(rowIds[j]);
					stuList.get(index).setPickupPoint(selectedValues[j]);
				}

			}
		}

		return mav;
	}

	//Get the List from stum,for the update operation
	@RequestMapping(value = "/studentTransport", method = RequestMethod.GET, params = "Get")
	public String getStudentListForUpdate(
			@ModelAttribute("studentTransport") StudentTransportMasterVO StudentTransportsSearch,
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
			if ((StudentTransportMasterVO) WebUtils.getSessionAttribute(
					httpServletRequest, "searchVo") != null) {
				StudentTransportsSearch = (StudentTransportMasterVO) WebUtils
						.getSessionAttribute(httpServletRequest, "searchVo");			

			}			
		}

		// Setting model and view for exception handler
		httpServletRequest.setAttribute("studentTransport", StudentTransportsSearch);
		httpServletRequest.setAttribute("errors", "error"); // Check whether the
															// list already get
															// or have to fetch
															// from data base
		// using the list size
		if (stockParamMap.size() == 0) {

			logger.info("Table operation has been triggered");
			
			try {
				// Get list from database
				studentTransportService.getStuListForTransport(
						sessionCache.getUserSessionDetails(),
						StudentTransportsSearch);
			} catch (NoDataFoundException e) {
				WebUtils.setSessionAttribute(httpServletRequest, "display_tbl",
						null);
				throw new NoDataFoundException();
			}
			
			// Set the list & searchVo to session to access in jsp
			WebUtils.setSessionAttribute(httpServletRequest, "display_tbl",
					StudentTransportsSearch.getStuList());
			WebUtils.setSessionAttribute(httpServletRequest, "searchVo",
					StudentTransportsSearch);
		}

		return "redirect:/studentTransport.htm?data";
	}

	// Method to update the modified values
	@RequestMapping(value = "/studentTransport", method = RequestMethod.GET, params = {
			"data", "actionSave" })
	public String saveTransportDetails(
			@ModelAttribute("studentTransport") StudentTransportMasterVO StudentTransportsSearch,
			ModelMap modelMap, HttpServletRequest httpServletRequest,
			HttpSession session, RedirectAttributes attributes)
			throws DatabaseException, DuplicateEntryException,
			BatchUpdateFailedException, DataIntegrityException, RuntimeExceptionForBatch {
		StudentTransportMasterVO StudentTransportMasterVO = (StudentTransportMasterVO) WebUtils
				.getSessionAttribute(httpServletRequest, "searchVo");

		StudentTransportMasterVO.setPageSize(StudentTransportsSearch.getPageSize());

		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);

		//To keep the Edited List values persistent
		String[] selectedValues = httpServletRequest
				.getParameterValues("selectedOpt");
		System.out.println("Selected values:"+selectedValues.toString());
		String[] chk = httpServletRequest.getParameterValues("_chk");
		String[] rowids = httpServletRequest.getParameterValues("rowids");
		System.out.println("Row Ids"+rowids.length);
		System.out.println("chk Ids"+chk.length);
		List<StudentTransportListVO> stuList = (List<StudentTransportListVO>) WebUtils
				.getSessionAttribute(httpServletRequest, "display_tbl");
		System.out.println("Student List size:"+stuList.size());
		if (stuList != null) {
			if ((selectedValues != null)) {
				for (int j = 0; j < selectedValues.length; j++) {
					System.out.println("row id:"+rowids[j]);
					
					int index = Integer.parseInt(rowids[j]);
					System.out.println("Selected values:"+selectedValues[j]);
					System.out.println("Index:"+index);
					stuList.get(index).setPickupPoint(selectedValues[j]);
				}

			}
		}		
		WebUtils.setSessionAttribute(httpServletRequest, "keepStatus", "status");
		httpServletRequest.setAttribute("studentTransport", StudentTransportsSearch);
		//Save the selected Objects only-from check box
		String[] checkBoxes = httpServletRequest.getParameterValues("_chk");	
		if(checkBoxes!=null){	
			List<StudentTransportListVO> studentListForSave = new ArrayList<StudentTransportListVO>();
			for(String checkBox:checkBoxes){				
				studentListForSave.add(stuList.get((Integer.valueOf(checkBox))));
			}			
			StudentTransportMasterVO.setStuList(studentListForSave);
			studentTransportService.insertStuList(sessionCache.getUserSessionDetails(),
					StudentTransportMasterVO);
			StudentTransportsSearch = (StudentTransportMasterVO) WebUtils
					.getSessionAttribute(httpServletRequest, "searchVo");
			WebUtils.setSessionAttribute(httpServletRequest, "success",
					ErrorCodeConstant.ADD_SUCCESS_MESS);

			logger.debug("Student Update has been successfully done!");
			
			
		}				
		

		return "redirect:/studentTransport.htm?Get";
	}


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
	@ModelAttribute("pickuppoint")
	public Map<String, String> gerCourseNameList(HttpSession session,
			@ModelAttribute("courseClasses") CourseClassesMasterVO courseClassesMasterVO,
			HttpServletRequest httpSevletRequest, HttpServletResponse response,
			ModelMap model) throws IOException, NoDataFoundException {
		
		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		Map<String, String> map = dropDownListService.getPickupPoints(sessionCache);
		httpSevletRequest.setAttribute("pickuppoint", map);
		return map;
		
	}

	@ExceptionHandler({ NoDataFoundException.class,
			BatchUpdateFailedException.class,
			InvalidCategoryForUpdateException.class })
	public ModelAndView handleException(Exception ex, HttpSession session,
			HttpServletRequest request) {
		StudentTransportMasterVO stuMasterVO = (StudentTransportMasterVO) request
				.getAttribute("studentTransport");
		ModelAndView modelAndView = new ModelAndView(
				ModelAndViewConstant.STU_TRANSPORT);
		modelAndView.getModelMap().addAttribute("studentTransport", stuMasterVO);		
	//	String error = (String) request.getAttribute("errors");
		WebUtils.setSessionAttribute(request, "success", null);
		WebUtils.setSessionAttribute(request, "display_tbl", null);
		modelAndView.getModelMap().addAttribute("error", ex.getMessage());

		return modelAndView;

	}

}
