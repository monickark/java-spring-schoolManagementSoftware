package com.jaw.core.controller;

import java.io.IOException;
import java.text.ParseException;
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
import com.jaw.common.exceptions.AcadCalFutureDateDeleteFailedException;
import com.jaw.common.exceptions.AcadCalendarDeleteFailedException;
import com.jaw.common.exceptions.DatabaseException;
import com.jaw.common.exceptions.DeleteFailedException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.UpdateFailedException;
import com.jaw.common.exceptions.batch.DuplicateEntryForAcaTermHolGenException;
import com.jaw.common.exceptions.batch.DuplicateEntryForHolGenException;
import com.jaw.common.exceptions.util.TableNotSpecifiedForAuditException;
import com.jaw.core.dao.AcademicCalendar;
import com.jaw.core.dao.AcademicCalendarListKey;
import com.jaw.core.service.IAcademicCalendarService;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.sessCache.SessionCache;

//Academic Calendar Controller Class
@Controller
public class AcademicCalendarController {
	// Logging
	Logger logger = Logger.getLogger(AcademicCalendarController.class);
	@Autowired
	IAcademicCalendarService academicCalendarService;
	@Autowired
	IDropDownListService dropDownListService;

	// Academic Calendar List Get method

	@RequestMapping(value = "/academicCalendarList", method = RequestMethod.GET)
	public ModelAndView academicCal(
			@ModelAttribute("acadcal") AcademicCalendarMasterVO academicCalendarMasterVO,
			ModelMap model, HttpServletRequest httpRequest,
			HttpSession httpSession) {

		logger.info("Rendering Academic Calendar List page");
		// Null the session display tag values
		ModelAndView mav = new ModelAndView(
				ModelAndViewConstant.ACAD_CAL_LIST, "acadcal",
				academicCalendarMasterVO);
		WebUtils.setSessionAttribute(httpRequest, "display_tbl", null);
		WebUtils.setSessionAttribute(httpRequest, "success", null);
		WebUtils.setSessionAttribute(httpRequest, "keepStatus",
				null);
		WebUtils.setSessionAttribute(httpRequest, "searchVo",
				null);
		mav.getModelMap().addAttribute("status", "true");
		return mav;

	}

	@RequestMapping(value = "/academicCalendarList", method = RequestMethod.GET, params = { "Get" })
	public String viewAcademicCalendarList(
			@ModelAttribute("acadcal") AcademicCalendarMasterVO academicCalendarMasterVO,
			HttpServletRequest httpRequest,HttpSession session,
			RedirectAttributes redirectAttributes) throws NoDataFoundException {
		// Getting the display tag parameter
				WebUtils.setSessionAttribute(httpRequest, "searchVo",
						academicCalendarMasterVO.getAcadClSeaVo());
		Map stockParamMap = WebUtils.getParametersStartingWith(httpRequest,
				"d-");
		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		// Setting model and view for exception handler
		WebUtils.setSessionAttribute(httpRequest, "searchVo",
				academicCalendarMasterVO.getAcadClSeaVo());
		ModelAndView modelAndView = new ModelAndView(
				ModelAndViewConstant.ACAD_CAL_LIST, "acadcal",
				academicCalendarMasterVO);
		httpRequest.setAttribute("page", modelAndView);
		httpRequest.setAttribute("errors", "error");
		if (httpRequest.getParameter("Get").equals("Get")) {
			WebUtils.setSessionAttribute(httpRequest, "keepStatus",
					null);
			WebUtils.setSessionAttribute(httpRequest, "success", null);
		}
		else {
			WebUtils.setSessionAttribute(httpRequest, "keepStatus",
					"status");
		}
		// Check whether the list already get or have to fetch from data base
		// using the list size
		if (stockParamMap.size() == 0) {

			logger.info("Table operation has been triggered");

			// Get list from database

			academicCalendarService
					.selectStudentGroupList(academicCalendarMasterVO,sessionCache.getUserSessionDetails());

			// Set the list to session to access in jsp

			WebUtils.setSessionAttribute(httpRequest, "display_tbl",
					academicCalendarMasterVO.getAcadCalVOList());
			
			academicCalendarMasterVO.setPageSize(academicCalendarMasterVO.getPageSize());

		}
		redirectAttributes.addFlashAttribute("acadcal",
				academicCalendarMasterVO);
		return "redirect:/academicCalendarList.htm?data";

	}

	@RequestMapping(value = "/academicCalendarList", method = RequestMethod.GET, params = "data")
	public ModelAndView staffListBack(
			@ModelAttribute("acadcal") AcademicCalendarMasterVO academicCalendarMasterVO,
			HttpServletRequest httpServletRequest) {
		AcademicCalendarSearchVO academicSearchVo= (AcademicCalendarSearchVO) WebUtils
				.getSessionAttribute(httpServletRequest, "searchVo");		
		academicCalendarMasterVO.setAcadClSeaVo(academicSearchVo);			
		ModelAndView modelAndView = new ModelAndView(
				ModelAndViewConstant.ACAD_CAL_LIST, "acadcal",
				academicCalendarMasterVO);
		
		return modelAndView;
	}

	// Save Method
	@RequestMapping(value = "/academicCalendarList", method = RequestMethod.POST, params = { "actionSave" })
	public String saveAcademicCalendar(
			@ModelAttribute("acadcal") AcademicCalendarMasterVO academicCalendarMasterVO,
			ModelMap model, HttpServletRequest httpServletRequest,
			HttpSession session, RedirectAttributes redirectAttribute)
			throws DuplicateEntryException, DatabaseException,
			 DuplicateEntryForAcaTermHolGenException, NoDataFoundException, UpdateFailedException, DuplicateEntryForHolGenException  {
		AcademicCalendarSearchVO academicSearchVo= (AcademicCalendarSearchVO) WebUtils
				.getSessionAttribute(httpServletRequest, "searchVo");		
		academicCalendarMasterVO.setAcadClSeaVo(academicSearchVo);
httpServletRequest.setAttribute("errors", "erroradd");		
		redirectAttribute.addFlashAttribute("acadcal", academicCalendarMasterVO);
		logger.debug("inside save AcademicCalendar method");
		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		ModelAndView modelAndView = new ModelAndView(
				ModelAndViewConstant.ACAD_CAL_LIST, "acadcal",
				academicCalendarMasterVO);
		httpServletRequest.setAttribute("page", modelAndView);
		if (academicCalendarMasterVO.getAcadClVO().getAcTerm().contains(",")) {
			String parts[] = academicCalendarMasterVO.getAcadClVO().getAcTerm()
					.split(",");
			academicCalendarMasterVO.getAcadClVO().setAcTerm(parts[0]); 
		}
		academicCalendarService.insertAcademicCalRec(
				academicCalendarMasterVO.getAcadClVO(),
				sessionCache.getUserSessionDetails());		
		WebUtils.setSessionAttribute(httpServletRequest, "success",
				ErrorCodeConstant.ADD_SUCCESS_MESS);
		WebUtils.setSessionAttribute(httpServletRequest, "keepStatus", "status");
		logger.debug("Data's inserted successfully!");
		return "redirect:/academicCalendarList.htm?Get";
	}

	// Get Values from list for popup
	@RequestMapping(value = "/academicCalendarList", method = RequestMethod.GET, params = { "actionGet" })
	public String getAcademicCalendar(
			@ModelAttribute("acadcal") AcademicCalendarMasterVO aCalMtrVO,
			ModelMap model, HttpServletRequest httpServletRequest,
			HttpSession session, RedirectAttributes redirectAttribute) {
		logger.debug("inside Get Academic Details from List method");
		List<AcademicCalendarVO> academicCalendarVOs = (List<AcademicCalendarVO>) WebUtils
				.getSessionAttribute(httpServletRequest, "display_tbl");
		aCalMtrVO.setAcadClSeaVo((AcademicCalendarSearchVO) WebUtils
				.getSessionAttribute(httpServletRequest, "searchVo"));
		
		String id = httpServletRequest.getParameter("rowId");
		aCalMtrVO.setAcadClVO(academicCalendarVOs.get(Integer.parseInt(id)));
		model.addAttribute("popup", "update");
		String keepstat = (String) WebUtils.getSessionAttribute(
				httpServletRequest, "keepStatus");
		System.out.println("Keep status  :" + keepstat);
		ModelAndView modelAndView = new ModelAndView(
				ModelAndViewConstant.ACAD_CAL_LIST);
		if (keepstat != null) {
			modelAndView.getModelMap().addAttribute("status", "false");
		} else {
			modelAndView.getModelMap().addAttribute("status", "true");
		}
		redirectAttribute.addFlashAttribute("popup", "update");
		redirectAttribute.addFlashAttribute("acadcal", aCalMtrVO);
		return "redirect:/academicCalendarList.htm?data";
	}

	// Update method
	@RequestMapping(value = "/academicCalendarList", method = RequestMethod.POST, params = { "actionUpdate" })
	public String updateAcademicCalendar(
			@ModelAttribute("acadcal") AcademicCalendarMasterVO academicCalendarMasterVO,
			ModelMap model, HttpServletRequest httpServletRequest,
			HttpSession session, RedirectAttributes redirectAttribute)
			throws UpdateFailedException, NoDataFoundException, DuplicateEntryException, DatabaseException, TableNotSpecifiedForAuditException {
		AcademicCalendarSearchVO academicSearchVo= (AcademicCalendarSearchVO) WebUtils
				.getSessionAttribute(httpServletRequest, "searchVo");		
		academicCalendarMasterVO.setAcadClSeaVo(academicSearchVo);	
httpServletRequest.setAttribute("errors", "erroradd");		
		redirectAttribute.addFlashAttribute("acadcal", academicCalendarMasterVO);
		logger.debug("inside update AcademicCalendar method");
		ApplicationCache applicationCache = (ApplicationCache) session
				.getServletContext().getAttribute(
						ApplicationConstant.APPLICATION_CACHE);
		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		ModelAndView modelAndView = new ModelAndView(
				ModelAndViewConstant.ACAD_CAL_LIST, "acadcal",
				academicCalendarMasterVO);
		httpServletRequest.setAttribute("page", modelAndView);
		academicCalendarService.updateAcademicCalRec(
				academicCalendarMasterVO.getAcadClVO(),
				sessionCache.getUserSessionDetails(),applicationCache);
		academicCalendarMasterVO.setAcadClSeaVo((AcademicCalendarSearchVO) WebUtils
				.getSessionAttribute(httpServletRequest, "searchVo"));
		WebUtils.setSessionAttribute(httpServletRequest, "success",
				ErrorCodeConstant.UPDATE_SUCCESS_MESS);
		logger.debug("Data's updated successfully!");
		WebUtils.setSessionAttribute(httpServletRequest, "keepStatus", "status");
		return "redirect:/academicCalendarList.htm?Get";
	}

	// Delete Method
	@RequestMapping(value = "/academicCalendarList", method = RequestMethod.GET, params = { "actionDelete" })
	public String deleteAcademicCalendar(
			@ModelAttribute("acadcal") AcademicCalendarMasterVO academicCalendarMasterVO,
			ModelMap model, HttpServletRequest httpServletRequest,
			HttpSession session, RedirectAttributes redirectAttribute)
			throws DeleteFailedException, NoDataFoundException, AcadCalendarDeleteFailedException, DuplicateEntryException, DatabaseException, TableNotSpecifiedForAuditException, AcadCalFutureDateDeleteFailedException, ParseException, DuplicateEntryForHolGenException, UpdateFailedException {
		AcademicCalendarSearchVO academicSearchVo= (AcademicCalendarSearchVO) WebUtils
				.getSessionAttribute(httpServletRequest, "searchVo");		
		academicCalendarMasterVO.setAcadClSeaVo(academicSearchVo);	
		redirectAttribute.addFlashAttribute("acadcal", academicCalendarMasterVO);
		logger.debug("inside Deleted Academic Calendae method");
		ApplicationCache applicationCache = (ApplicationCache) session
				.getServletContext().getAttribute(
						ApplicationConstant.APPLICATION_CACHE);
		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		ModelAndView modelAndView = new ModelAndView(
				ModelAndViewConstant.ACAD_CAL_LIST, "acadcal",
				academicCalendarMasterVO);
		httpServletRequest.setAttribute("page", modelAndView);
		httpServletRequest.setAttribute("errors", "erroradd");
		List<AcademicCalendarVO> academicCalendarVOs = (List<AcademicCalendarVO>) WebUtils
				.getSessionAttribute(httpServletRequest, "display_tbl");
		String id = httpServletRequest.getParameter("rowId");
		academicCalendarMasterVO.setAcadClVO(academicCalendarVOs.get(Integer
				.parseInt(id)));
		academicCalendarService.deleteAcademicCalendarRec(
				academicCalendarMasterVO.getAcadClVO(),
				sessionCache.getUserSessionDetails(),applicationCache);
		academicCalendarMasterVO.setAcadClSeaVo((AcademicCalendarSearchVO) WebUtils
				.getSessionAttribute(httpServletRequest, "searchVo"));
		WebUtils.setSessionAttribute(httpServletRequest, "success",
				ErrorCodeConstant.DELETE_SUCCESS_MESS);
		logger.debug("Data's deleted successfully!");
		WebUtils.setSessionAttribute(httpServletRequest, "keepStatus", "status");
		return "redirect:/academicCalendarList.htm?Get";
	}

	// Ajax For getting Ac Term Date for Validation
	@RequestMapping(value = "/academicCalendarAjax", method = RequestMethod.GET, params = { "acadTermName" })
	public @ResponseBody
	String retriveList(HttpSession session, HttpServletRequest request) {
		logger.debug("inside Academic Term Date Validation method");
		String res = null;
		AcademicCalendarVO acadCalVOff = new AcademicCalendarVO();
		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		acadCalVOff.setAcTerm(request.getParameter("academicTermV"));
		AcademicCalendarVO acv;
		try {
			acv = academicCalendarService.getAcademicTermDateRec(acadCalVOff,
					sessionCache.getUserSessionDetails());
			res = acv.getItemStartDate() + "," + acv.getItemEndDate();
			logger.debug("from and To date value for ACademic Term "
					+ acv.getItemStartDate() + "," + acv.getItemEndDate());
		} catch (NoDataFoundException e) {
			logger.error("NoDataFoundException occured in AJax AC Term date get validation");
			res = "error";
		}
		return res;
	}

	// Ajax For  Ac Term Status Validation
	@RequestMapping(value = "/academicCalendarAjax", method = RequestMethod.GET, params = { "acadTermValid" })
	public @ResponseBody
	String retriveAcTermValid(
			@ModelAttribute("acadcal") AcademicCalendarMasterVO academicCalendarMasterVO,
			HttpSession session, HttpServletRequest request,
			HttpServletResponse response) {
		logger.debug("inside Academic Term Status Validation method");
		String res = null;
		
		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);

		boolean f = academicCalendarService.checkAcTermValidation(
				(String) request.getParameter("academicTerm"),
				sessionCache.getUserSessionDetails());
		res = String.valueOf(f);

		return res;
	}

	// Ajax For  Ac Term Date Validation in searchVo
		@RequestMapping(value = "/academicCalendarList", method = RequestMethod.GET, params = { "acadTermSearch" })
		public @ResponseBody
		String retriveAcTermDateForSearch(
				@ModelAttribute("acadcal") AcademicCalendarMasterVO academicCalendarMasterVO,
				HttpSession session, HttpServletRequest request,
				HttpServletResponse response) {
			logger.debug("inside Academic Term Date Validation fro searchVo method");
			String res = "ssssss";
			
			SessionCache sessionCache = (SessionCache) session
					.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
			AcademicCalendarVO ac=academicCalendarService.getAcTermDateForValidation((String) request.getParameter("academicTermSearch"),
					sessionCache.getUserSessionDetails());
						if( ac.getItemStartDate()==null){
				res="error";
			}else{
			res = ac.getItemStartDate()+"_"+ac.getItemEndDate();			
		}

			return res;
		}
		
		// Ajax For  hol Exist for studentgrp validation
				@RequestMapping(value = "/academicCalendarList", method = RequestMethod.GET, params = { "holidayExist" })
				public @ResponseBody
				int checkHolidayExist(
						/*@ModelAttribute("acadcal") AcademicCalendarMasterVO academicCalendarMasterVO,*/
						HttpSession session, HttpServletRequest request,
						HttpServletResponse response) {
					logger.debug("inside holiday exist check method");
					
					SessionCache sessionCache = (SessionCache) session
							.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
					AcademicCalendar academicCalendar=new AcademicCalendar();
					academicCalendar.setAcTerm((String) request.getParameter("acTermHol"));
					academicCalendar.setItemStartDate((String) request.getParameter("startDateHol"));
					academicCalendar.setItemEndDate((String) request.getParameter("endDateHol"));
					academicCalendar.setInstId(sessionCache.getUserSessionDetails().getInstId());
					academicCalendar.setBranchId(sessionCache.getUserSessionDetails().getBranchId());
					int result=academicCalendarService.checkHolidayExistForStudentGrp(academicCalendar);
					System.out.println("result in hol acd controllerr"+result);
				/*	String res = null;
					res="success";*/
					return result;
				}
				
				// Ajax For  Ac Term Date Validation in searchVo
				@RequestMapping(value = "/academicCalendarList", method = RequestMethod.GET, params = { "deleteholiday" })
				public @ResponseBody
				String deleteAddlHolidayExistForStudentGrp(
						@ModelAttribute("acadcal") AcademicCalendarMasterVO academicCalendarMasterVO,
						HttpSession session, HttpServletRequest request,
						HttpServletResponse response) {
					logger.debug("inside holiday exist check method");
					
					SessionCache sessionCache = (SessionCache) session
							.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
					AcademicCalendar academicCalendar=new AcademicCalendar();
					academicCalendar.setAcTerm((String) request.getParameter("acTermHol"));
					academicCalendar.setItemStartDate((String) request.getParameter("startDateHol"));
					academicCalendar.setItemEndDate((String) request.getParameter("endDateHol"));
					academicCalendar.setInstId(sessionCache.getUserSessionDetails().getInstId());
					academicCalendar.setBranchId(sessionCache.getUserSessionDetails().getBranchId());
					academicCalendarService.removeAddlHolForStudentGrp(academicCalendar);
					

					return "success";
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
			logger.error("Error occured in Academic Term Dropdown :" + e.getMessage());			
		}
		httpSevletRequest.setAttribute("acTermList", map);
		return map;

	}

	@ExceptionHandler({ DuplicateEntryException.class, DatabaseException.class,
		DuplicateEntryForAcaTermHolGenException.class,
			UpdateFailedException.class, DeleteFailedException.class,AcadCalendarDeleteFailedException.class,
			AcadCalFutureDateDeleteFailedException.class,
			TableNotSpecifiedForAuditException.class })
	public ModelAndView handleException(Exception ex, HttpSession session,
			HttpServletRequest request) {
		logger.error("Exception Handledddd :" + ex.getMessage());	
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
		} else {
			System.out.println("inside non  delete message");
			WebUtils.setSessionAttribute(request, "success", null);
			mav.getModelMap().addAttribute("error", ex.getMessage());
		}
		

	WebUtils.setSessionAttribute(request, "display_tbl", null);
	return mav;
}
	
	
	
}
