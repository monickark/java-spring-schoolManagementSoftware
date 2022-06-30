package com.jaw.admin.controller;

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


import com.jaw.admin.service.ISMSConfigurationService;
import com.jaw.common.constants.ApplicationConstant;
import com.jaw.common.constants.ErrorCodeConstant;
import com.jaw.common.constants.ModelAndViewConstant;
import com.jaw.common.dropdown.service.IDropDownListService;
import com.jaw.common.exceptions.AcadCalFutureDateDeleteFailedException;
import com.jaw.common.exceptions.AcadCalendarDeleteFailedException;
import com.jaw.common.exceptions.BatchUpdateFailedException;
import com.jaw.common.exceptions.DatabaseException;
import com.jaw.common.exceptions.DeleteFailedException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.NoRecordSelectedException;
import com.jaw.common.exceptions.UpdateFailedException;
import com.jaw.common.exceptions.batch.DuplicateEntryForAcaTermHolGenException;
import com.jaw.common.exceptions.batch.DuplicateEntryForHolGenException;
import com.jaw.common.exceptions.util.TableNotSpecifiedForAuditException;
import com.jaw.core.controller.AcademicCalendarMasterVO;
import com.jaw.core.controller.AcademicCalendarSearchVO;
import com.jaw.core.controller.CourseClassesMasterVO;
import com.jaw.core.controller.CourseClassesSearchVO;
import com.jaw.core.controller.CourseClassesVO;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.sessCache.SessionCache;
@Controller
public class SMSConfigurationController {
	// Logging
		Logger logger = Logger.getLogger(SMSConfigurationController.class);
	
		@Autowired
		IDropDownListService dropDownListService;
		@Autowired
		ISMSConfigurationService smsConfigurationService;
	

		@RequestMapping(value = "/smsConfiguration", method = RequestMethod.GET)
		public ModelAndView getAttendanceList(
				@ModelAttribute("smsConfig") SMSConfigurationMasterVO smsConfigMaster,
				ModelMap modelMap, HttpServletRequest httpServletRequest,HttpSession session) {
			ApplicationCache applicationCache = (ApplicationCache) session
					.getServletContext().getAttribute(
							ApplicationConstant.APPLICATION_CACHE);
			
			ModelAndView mav = new ModelAndView(
					ModelAndViewConstant.SMS_CONFIGURATION, "smsConfig",
					smsConfigMaster);
			WebUtils.setSessionAttribute(httpServletRequest, "display_tbl", null);
			WebUtils.setSessionAttribute(httpServletRequest, "display_tbl1", null);
			WebUtils.setSessionAttribute(httpServletRequest, "success", null);
			WebUtils.setSessionAttribute(httpServletRequest, "searchVo", null);
			return mav;
		}
		
		@RequestMapping(value = "/smsConfiguration", method = RequestMethod.GET, params = { "Get" })
		public String viewAcademicCalendarList(
				@ModelAttribute("smsConfig") SMSConfigurationMasterVO smsConfigMaster,
				HttpServletRequest httpRequest,HttpSession session,
				RedirectAttributes redirectAttributes) throws NoDataFoundException {
			System.out.println("sms configuration detailsssssssssssssss");
			System.out.println("sms configuration detailsssssssssssssss"+smsConfigMaster.getSmsConfigVO().toString());
			
			// Getting the display tag parameter
					WebUtils.setSessionAttribute(httpRequest, "searchVo",
							smsConfigMaster.getSmsConfigVO());
			Map stockParamMap = WebUtils.getParametersStartingWith(httpRequest,
					"d-");
			SessionCache sessionCache = (SessionCache) session
					.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
			// Setting model and view for exception handler
		
			ModelAndView modelAndView = new ModelAndView(
					ModelAndViewConstant.SMS_CONFIGURATION, "smsConfig",
					smsConfigMaster);
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
				smsConfigurationService.selectStudentGroupList(smsConfigMaster, sessionCache.getUserSessionDetails());
				System.out.println("lengthhhhhhhhhhhhhh"+smsConfigMaster.getSmsConfigListVO().size());
				for(int i=0;i<smsConfigMaster.getSmsConfigListVO().size();i++){
					System.out.println("sms config detailssssssssssssssss"+smsConfigMaster.getSmsConfigListVO().get(i).toString());
				}
				WebUtils.setSessionAttribute(httpRequest, "display_tbl",
						smsConfigMaster.getSmsConfigListVO());
				
				smsConfigMaster.setPageSize(smsConfigMaster.getPageSize());

			}
			redirectAttributes.addFlashAttribute("smsConfig",
					smsConfigMaster);
			return "redirect:/smsConfiguration.htm?data";

		}
		
		
		@RequestMapping(value = "/smsConfiguration", method = RequestMethod.GET,params = {
				"data", "!actionSave",  "!update"
		})
				
				
				
		public ModelAndView staffListBack(
				@ModelAttribute("smsConfig") SMSConfigurationMasterVO smsConfigMaster,
				HttpServletRequest httpServletRequest) {
			SMSConfigurationVO smsConfigurationVO= (SMSConfigurationVO) WebUtils
					.getSessionAttribute(httpServletRequest, "searchVo");		
			smsConfigMaster.setSmsConfigVO(smsConfigurationVO)	;	
			ModelAndView modelAndView = new ModelAndView(
					ModelAndViewConstant.SMS_CONFIGURATION, "smsConfig",
					smsConfigMaster);
			
			return modelAndView;
		}
		
		
		
		
		@RequestMapping(value = "/smsConfiguration", method = RequestMethod.GET, params = {
				"actionSave"
		})
		public String addCourseClassesLinking(
				@ModelAttribute("smsConfig") SMSConfigurationMasterVO smsConfigMaster,
				ModelMap model, HttpServletRequest httpServletRequest,
				HttpSession session, RedirectAttributes redirectAttribute) throws NoDataFoundException,
				DuplicateEntryException, BatchUpdateFailedException, DatabaseException,
				NoRecordSelectedException, UpdateFailedException {
			
			logger.debug("inside Update course classes linking method");
			ApplicationCache applicationCache = (ApplicationCache) session
					.getServletContext().getAttribute(
							ApplicationConstant.APPLICATION_CACHE);
			SessionCache sessionCache = (SessionCache) session
					.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
			List<SMSConfigurationVO> smsConfigurationVOs =
					(List<SMSConfigurationVO>) WebUtils
							.getSessionAttribute(httpServletRequest, "display_tbl");
			SMSConfigurationVO smsConfigurationVO = (SMSConfigurationVO) WebUtils
					.getSessionAttribute(httpServletRequest, "searchVo");
			smsConfigMaster.setSmsConfigListVO(smsConfigurationVOs);
			smsConfigMaster.setSmsConfigVO(smsConfigurationVO);
			ModelAndView modelAndView = new ModelAndView(
					ModelAndViewConstant.SMS_CONFIGURATION, "smsConfig",
					smsConfigMaster);
			httpServletRequest.setAttribute("page", modelAndView);
			httpServletRequest.setAttribute("errors", "error");
			String[] propertyValues = httpServletRequest.getParameterValues("propertyValuetext");
			String[] propertyNames = httpServletRequest.getParameterValues("propertyNametext");
			String[] selectedRowIds = httpServletRequest.getParameterValues("_chk");
			String[] selectedRows = httpServletRequest.getParameterValues("propertyValueRows");
			if((propertyValues!=null)&&(propertyNames!=null)&&(selectedRowIds!=null)){
			System.out.println("lengthhhhhhhhhhhhhhh"+propertyValues.length);
			System.out.println("lengthhhhhhhhhhhhhhh"+propertyNames.length);
			System.out.println("lengthhhhhhhhhhhhhhh"+selectedRowIds.length);
			System.out.println("lengthhhhhhhhhhhhhhh"+selectedRows.length);
			
			for(int i=0;i<selectedRows.length;i++) {
				System.out.println("Property Values id seleced :" + selectedRows[i]);
			}
			for(int i=0;i<propertyValues.length;i++) {
				System.out.println("Property Values id seleced :" + propertyValues[i]);
			}
			for(int i=0;i<propertyNames.length;i++) {
				System.out.println("Property name id seleced :" + propertyNames[i]);
			}
			for(int i=0;i<selectedRowIds.length;i++) {
				System.out.println("Property row id id seleced :" + selectedRowIds[i]);
			}
			smsConfigurationService.saveSMSConfigurationDetails(smsConfigMaster, sessionCache.getUserSessionDetails(),
					selectedRowIds, propertyValues, propertyNames);
			}
			redirectAttribute.addFlashAttribute("smsConfig", smsConfigMaster);
			WebUtils.setSessionAttribute(httpServletRequest, "success",
					ErrorCodeConstant.ADD_SUCCESS_MESS);
			return "redirect:/smsConfiguration.htm?Get";
		}
		
		
		@RequestMapping(value = "/smsConfiguration", method = RequestMethod.GET, params = {
				"update"
		})
		public String getCourseClassesLinking(
				@ModelAttribute("smsConfig") SMSConfigurationMasterVO smsConfigMaster,
				ModelMap model, HttpServletRequest httpServletRequest,
				HttpSession session, RedirectAttributes redirectAttribute)
				throws UpdateFailedException, NumberFormatException, NoDataFoundException,
				DuplicateEntryException, DatabaseException, TableNotSpecifiedForAuditException {
			
			logger.debug("inside Update SMS Configuration linking method");
		
			SMSConfigurationVO smsConfigurationVO= (SMSConfigurationVO) WebUtils
					.getSessionAttribute(httpServletRequest, "searchVo");
			smsConfigMaster.setSmsConfigVO(smsConfigurationVO);
			ModelAndView modelAndView = new ModelAndView(
					ModelAndViewConstant.SMS_CONFIGURATION, "smsConfig",
					smsConfigMaster);
			httpServletRequest.setAttribute("page", modelAndView);
			httpServletRequest.setAttribute("errors", "error");
			String propertyValue = httpServletRequest.getParameter("propertyVal");
			String branch = httpServletRequest.getParameter("branch");
			String propertyName = httpServletRequest.getParameter("propertyName");
			String propertyType = httpServletRequest.getParameter("propertyType");
			System.out.println("propertyValue:" + propertyValue + " branch:" + branch + " propertyName:" + propertyName
					+ " propertyType:" + propertyType );
			SessionCache sessionCache = (SessionCache) session
					.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
			ApplicationCache applicationCache = (ApplicationCache) session
					.getServletContext().getAttribute(
							ApplicationConstant.APPLICATION_CACHE);
			
			SMSConfigurationVO smsConfigVo=new SMSConfigurationVO();
			smsConfigVo.setBranchId(branch);
			smsConfigVo.setPropertyName(propertyName);
			smsConfigVo.setPropertyValue(propertyValue);
			smsConfigVo.setPropertyType(propertyType);
			smsConfigurationService.updateSMSConfigurationRec(smsConfigVo, sessionCache.getUserSessionDetails(), applicationCache);
			
			redirectAttribute.addFlashAttribute("smsConfig", smsConfigMaster);
			WebUtils.setSessionAttribute(httpServletRequest, "success",
					ErrorCodeConstant.UPDATE_SUCCESS_MESS);
			
			return "redirect:/smsConfiguration.htm?Get";
		}
		
		@ModelAttribute("branchList")
		public Map<String, String> gerCourseNameList(HttpSession session,
				HttpServletRequest httpSevletRequest, HttpServletResponse response,
				ModelMap model) throws IOException, NoDataFoundException {
			SessionCache sessionCache = (SessionCache) session
					.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
			Map<String, String> map = dropDownListService.getBranchListTag(sessionCache.getUserSessionDetails());
			httpSevletRequest.setAttribute("branchList", map);
			return map;

		}
		@ExceptionHandler({ DuplicateEntryException.class, DatabaseException.class,
			DuplicateEntryForAcaTermHolGenException.class,
				UpdateFailedException.class, DeleteFailedException.class,
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
