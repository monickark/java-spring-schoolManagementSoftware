package com.jaw.fee.controller;

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
import com.jaw.fee.service.IStudentFeeDiscountService;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.sessCache.SessionCache;

@Controller
public class StudentFeeDiscountController {
	// Logging
	Logger logger = Logger.getLogger(StudentFeeDiscountController.class);
	@Autowired
	IStudentFeeDiscountService studentFeeDiscountService;
	@Autowired
	IDropDownListService dropDownListService;
	@Autowired
	ErrorCodeUtil errorCodeUtil;

	
	//Method to render the Student update Page
	@RequestMapping(value = "/feeDiscount", method = RequestMethod.GET)
	public ModelAndView getstudentFeeDiscountPage(
			@ModelAttribute("studentFeeDiscount") StudentFeeDiscountMasterVO studentFeeDiscountsSearch,
			ModelMap modelMap, HttpServletRequest httpServletRequest) {

		ModelAndView mav = new ModelAndView(ModelAndViewConstant.STUDENT_FEE_DISCOUNT);
		//Session attributes to be null
		WebUtils.setSessionAttribute(httpServletRequest, "display_tbl", null);
		WebUtils.setSessionAttribute(httpServletRequest, "success", null);
		WebUtils.setSessionAttribute(httpServletRequest, "keepStatus", null);
		WebUtils.setSessionAttribute(httpServletRequest, "searchVo", null);
		mav.getModelMap().addAttribute("status", "true");
		
		return mav;
	}

	//Data method,in which every action will be ended(Session attributes won't be null)
	@RequestMapping(value = "/feeDiscount", method = RequestMethod.GET, params = "data")
	public ModelAndView getstudentFeeDiscountPageData(
			@ModelAttribute("studentFeeDiscount") StudentFeeDiscountMasterVO studentFeeDiscountsSearch,
			ModelMap modelMap, HttpServletRequest httpServletRequest,
			RedirectAttributes redirectAttributes) {

		ModelAndView mav = new ModelAndView(ModelAndViewConstant.STUDENT_FEE_DISCOUNT);
		StudentFeeDiscountMasterVO studentFeeDiscountMasterVO = (StudentFeeDiscountMasterVO) WebUtils
				.getSessionAttribute(httpServletRequest, "searchVo");
		studentFeeDiscountMasterVO.setPageSize(studentFeeDiscountsSearch.getPageSize());
		mav.getModelMap().addAttribute("studentFeeDiscount", studentFeeDiscountMasterVO);

		//To keep the Edited List values persistent
		String[] selectedValues = httpServletRequest
				.getParameterValues("selectedOpt");
		String[] rowIds = httpServletRequest.getParameterValues("rowids");			
		List<StudentFeeDiscountListVO> stuList = (List<StudentFeeDiscountListVO>) WebUtils
				.getSessionAttribute(httpServletRequest, "display_tbl");
	/*	if (stuList != null) {
			if ((selectedValues != null)) {
				for (int j = 0; j < selectedValues.length; j++) {
					int index = Integer.parseInt(rowIds[j]);
					stuList.get(index).setPickupPoint(selectedValues[j]);
				}

			}
		}*/

		return mav;
	}

	//Get the List from stum,for the update operation
	@RequestMapping(value = "/feeDiscount", method = RequestMethod.GET, params = "Get")
	public String getStudentListForUpdate(
			@ModelAttribute("studentFeeDiscount") StudentFeeDiscountMasterVO studentFeeDiscountsSearch,
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
			if ((StudentFeeDiscountMasterVO) WebUtils.getSessionAttribute(
					httpServletRequest, "searchVo") != null) {
				studentFeeDiscountsSearch = (StudentFeeDiscountMasterVO) WebUtils
						.getSessionAttribute(httpServletRequest, "searchVo");			

			}			
		}

		// Setting model and view for exception handler
		httpServletRequest.setAttribute("studentFeeDiscount", studentFeeDiscountsSearch);
		httpServletRequest.setAttribute("errors", "error"); // Check whether the
															// list already get
															// or have to fetch
															// from data base
		// using the list size
		if (stockParamMap.size() == 0) {

			logger.info("Table operation has been triggered");
			
			try {
				// Get list from database
				studentFeeDiscountService.getStuListForFeeDiscount(
						sessionCache.getUserSessionDetails(),
						studentFeeDiscountsSearch);
			} catch (NoDataFoundException e) {
				WebUtils.setSessionAttribute(httpServletRequest, "display_tbl",
						null);
				throw new NoDataFoundException();
			}
			
			// Set the list & searchVo to session to access in jsp
			WebUtils.setSessionAttribute(httpServletRequest, "display_tbl",
					studentFeeDiscountsSearch.getStuList());
			WebUtils.setSessionAttribute(httpServletRequest, "searchVo",
					studentFeeDiscountsSearch);
		}

		return "redirect:/feeDiscount.htm?data";
	}

	// Method to update the modified values
	@RequestMapping(value = "/feeDiscount", method = RequestMethod.GET, params = {
			"data", "actionSave" })
	public String saveTransportDetails(
			@ModelAttribute("studentFeeDiscount") StudentFeeDiscountMasterVO studentFeeDiscountsSearch,
			ModelMap modelMap, HttpServletRequest httpServletRequest,
			HttpSession session, RedirectAttributes attributes)
			throws DatabaseException, DuplicateEntryException,
			BatchUpdateFailedException, DataIntegrityException, RuntimeExceptionForBatch {
		StudentFeeDiscountMasterVO studentFeeDiscountMasterVO = (StudentFeeDiscountMasterVO) WebUtils
				.getSessionAttribute(httpServletRequest, "searchVo");

		studentFeeDiscountMasterVO.setPageSize(studentFeeDiscountsSearch.getPageSize());

		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);

		//To keep the Edited List values persistent
		String[] connValues = httpServletRequest
				.getParameterValues("textbox");
		String[] remarks = httpServletRequest
				.getParameterValues("remarks");
		System.out.println("Selected values:"+connValues.toString());
		String[] chk = httpServletRequest.getParameterValues("_chk");
		String[] rowids = httpServletRequest.getParameterValues("rowids");
		System.out.println("Row Ids"+rowids.length);
		System.out.println("chk Ids"+chk.length);
		List<StudentFeeDiscountListVO> stuList = (List<StudentFeeDiscountListVO>) WebUtils
				.getSessionAttribute(httpServletRequest, "display_tbl");
		System.out.println("Student List size:"+stuList.size());
		if (stuList != null) {
			if ((connValues != null)) {
				for (int j = 0; j < connValues.length; j++) {
					System.out.println("row id:"+rowids[j]);
					
					int index = Integer.parseInt(rowids[j]);
					System.out.println("Selected values:"+connValues[j]);
					System.out.println("Index:"+index);
					stuList.get(index).setConcessionAmt(connValues[j]);
				}

			}
			if ((remarks != null)) {
				for (int j = 0; j < remarks.length; j++) {
					System.out.println("row id:"+rowids[j]);
					
					int index = Integer.parseInt(rowids[j]);
					System.out.println("Selected values:"+remarks[j]);
					System.out.println("Index:"+index);
					stuList.get(index).setFeeDmdremarks(remarks[j]);
				}

			}
		}		
		System.out.println("After Chenges done:"+stuList.toString());
		WebUtils.setSessionAttribute(httpServletRequest, "keepStatus", "status");
		httpServletRequest.setAttribute("studentFeeDiscount", studentFeeDiscountsSearch);
		//Save the selected Objects only-from check box
		String[] checkBoxes = httpServletRequest.getParameterValues("_chk");	
		if(checkBoxes!=null){	
			List<StudentFeeDiscountListVO> studentListForSave = new ArrayList<StudentFeeDiscountListVO>();
			for(String checkBox:checkBoxes){				
				studentListForSave.add(stuList.get((Integer.valueOf(checkBox))));
			}			
			System.out.println("List size to save:"+studentListForSave);
			studentFeeDiscountMasterVO.setStuList(studentListForSave);
			studentFeeDiscountService.insertStuList(sessionCache.getUserSessionDetails(),
					studentFeeDiscountMasterVO);
			studentFeeDiscountsSearch = (StudentFeeDiscountMasterVO) WebUtils
					.getSessionAttribute(httpServletRequest, "searchVo");
			WebUtils.setSessionAttribute(httpServletRequest, "success",
					ErrorCodeConstant.ADD_SUCCESS_MESS);

			logger.debug("Student Update has been successfully done!");
			
			
		}				
		

		return "redirect:/feeDiscount.htm?Get";
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
		StudentFeeDiscountMasterVO stuMasterVO = (StudentFeeDiscountMasterVO) request
				.getAttribute("studentFeeDiscount");
		ModelAndView modelAndView = new ModelAndView(
				ModelAndViewConstant.STUDENT_FEE_DISCOUNT);
		modelAndView.getModelMap().addAttribute("studentFeeDiscount", stuMasterVO);		
	//	String error = (String) request.getAttribute("errors");
		WebUtils.setSessionAttribute(request, "success", null);
		WebUtils.setSessionAttribute(request, "display_tbl", null);
		modelAndView.getModelMap().addAttribute("error", ex.getMessage());

		return modelAndView;

	}

}
