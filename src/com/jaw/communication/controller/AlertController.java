package com.jaw.communication.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
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
import com.jaw.communication.service.IAlertService;
import com.jaw.communication.service.INoticeBoardService;
import com.jaw.communication.service.ISMSRequestService;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.sessCache.SessionCache;

@Controller
public class AlertController {
	// Logging
			Logger logger = Logger.getLogger(AlertController.class);
			@Autowired 
			IAlertService alertService;
			@Autowired 
			ISMSRequestService smsRequestService;
		@RequestMapping(value="/alertList",method = RequestMethod.GET)
		public ModelAndView alert(
				@ModelAttribute("alert") AlertMasterVO alertMasterVO,
				ModelMap model, HttpServletRequest httpRequest,
				HttpSession httpSession) {

			logger.info("Rendering Alert List page");
			// Null the session display tag values
			ModelAndView mav = new ModelAndView(
					ModelAndViewConstant.ALERT_LIST, "alert",
					alertMasterVO);
			WebUtils.setSessionAttribute(httpRequest, "display_tbl", null);
			WebUtils.setSessionAttribute(httpRequest, "success", null);
			WebUtils.setSessionAttribute(httpRequest, "keepStatus",
					null);
			WebUtils.setSessionAttribute(httpRequest, "searchVo",
					null);
			SessionCache sessionCache = (SessionCache) httpSession
					.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
			  HashMap<String,String> lisy=new HashMap<String,String>();
			  lisy.put("PAR", "Parent");
			  lisy.put("STU", "Student");
			  lisy.put("STA", "Staff");
			  lisy.put("MGT", "Management");
		        
		        WebUtils.setSessionAttribute(httpRequest, "StringCheckBoxArray",
		        		lisy);
		        WebUtils.setSessionAttribute(httpRequest, "StudentGrpListArray",
		        		smsRequestService.getStudentGroupList(sessionCache));
		        WebUtils.setSessionAttribute(httpRequest, "DepartementListArray",
		        		smsRequestService.getDepartementList(sessionCache.getUserSessionDetails()));
		        
			mav.getModelMap().addAttribute("status", "true");
			return mav;

		}
		
		@RequestMapping(value = "/alertList", method = RequestMethod.GET, params = { "Get" })
		public String viewAlertList(
				@ModelAttribute("alert") AlertMasterVO alertMasterVO,
				HttpServletRequest httpRequest,HttpSession session,
				RedirectAttributes redirectAttributes) throws NoDataFoundException {
			System.out.println("Inside get method");
			
			System.out.println("alert Search vo values  :  "+alertMasterVO.getAlertSearchVo().toString());
			// Getting the display tag parameter
			
					WebUtils.setSessionAttribute(httpRequest, "searchVo",
							alertMasterVO.getAlertSearchVo());
			Map stockParamMap = WebUtils.getParametersStartingWith(httpRequest,
					"d-");
			SessionCache sessionCache = (SessionCache) session
					.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
			// Setting model and view for exception handler
			
			ModelAndView modelAndView = new ModelAndView(
					ModelAndViewConstant.ALERT_LIST, "alert",
					alertMasterVO);
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
			
				alertService.selectAlertList(alertMasterVO, sessionCache.getUserSessionDetails());

				// Set the list to session to access in jsp

				WebUtils.setSessionAttribute(httpRequest, "display_tbl",
						alertMasterVO.getAlertVOList());
				alertMasterVO.setPageSize(alertMasterVO.getPageSize());
				

			}
			redirectAttributes.addFlashAttribute("alert",
					alertMasterVO);
			return "redirect:/alertList.htm?data";

		}

		@RequestMapping(value = "/alertList", method = RequestMethod.GET, params = "data")
		public ModelAndView AlertListBack(
				@ModelAttribute("alert") AlertMasterVO alertMasterVO,
				HttpServletRequest httpServletRequest) {
			/*AlertSearchVo alertSearchVo= (AlertSearchVo) WebUtils
					.getSessionAttribute(httpServletRequest, "searchVo");	
			alertMasterVO.setAlertSearchVo(alertSearchVo);*/	
			System.out.println("alert Search vo values  in data:  "+alertMasterVO.getAlertSearchVo().toString());
			ModelAndView modelAndView = new ModelAndView(
					ModelAndViewConstant.ALERT_LIST, "alert",
					alertMasterVO);
			
			return modelAndView;
		}
		// Save Method
				@RequestMapping(value = "/alertList", method = RequestMethod.POST, params = { "actionSave" })
				public String saveAlert(
						@ModelAttribute("alert") AlertMasterVO alertMasterVO,
						ModelMap model, HttpServletRequest httpServletRequest,
						HttpSession session, RedirectAttributes redirectAttribute)
						throws DuplicateEntryException, DatabaseException,
						 DuplicateEntryForAcaTermHolGenException, NoDataFoundException, UpdateFailedException, DuplicateEntryForHolGenException  {
					AlertSearchVo alertSearchVo= (AlertSearchVo) WebUtils
							.getSessionAttribute(httpServletRequest, "searchVo");	
					alertMasterVO.setAlertSearchVo(alertSearchVo);
			httpServletRequest.setAttribute("errors", "erroradd");		
			System.out.println("Alert Vosssssssss : "+alertMasterVO.getAlertVO().toString());
			
			
			
					redirectAttribute.addFlashAttribute("alert", alertMasterVO);
					logger.debug("inside save Notice Board method");
					SessionCache sessionCache = (SessionCache) session
							.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
					ModelAndView modelAndView = new ModelAndView(
							ModelAndViewConstant.ALERT_LIST, "alert",
							alertMasterVO);
					httpServletRequest.setAttribute("page", modelAndView);
					System.out.println("alert vo valuesssssssssssssssss"+alertMasterVO.getAlertVO().toString());
					if (alertMasterVO.getAlertVO().getAcTerm().contains(",")) {
						String parts[] = alertMasterVO.getAlertVO().getAcTerm()
								.split(",");
						alertMasterVO.getAlertVO().setAcTerm(parts[0]); 
					}
					alertService.insertAlertDetailsRec(
							alertMasterVO,
							sessionCache.getUserSessionDetails());		
					WebUtils.setSessionAttribute(httpServletRequest, "success",
							ErrorCodeConstant.ADD_SUCCESS_MESS);
					WebUtils.setSessionAttribute(httpServletRequest, "keepStatus", "status");
					logger.debug("Data's inserted successfully!");
					return "redirect:/alertList.htm?Get";
				}
				
				// Get Values from list for popup
				@RequestMapping(value = "/alertList", method = RequestMethod.GET, params = { "actionGet" })
				public String getAlert(
						@ModelAttribute("alert") AlertMasterVO alertMasterVO,
						ModelMap model, HttpServletRequest httpServletRequest,
						HttpSession session, RedirectAttributes redirectAttribute) {
					logger.debug("inside Get Alert from List method");
					AlertSearchVo alertSearchVo= (AlertSearchVo) WebUtils
							.getSessionAttribute(httpServletRequest, "searchVo");	
					alertMasterVO.setAlertSearchVo(alertSearchVo);
					List<AlertVO> alertVOVOs = (List<AlertVO>) WebUtils
							.getSessionAttribute(httpServletRequest, "display_tbl");			
					
					String id = httpServletRequest.getParameter("rowId");
					alertMasterVO.setAlertVO(alertVOVOs.get(Integer.parseInt(id)));
					model.addAttribute("popup", "update");
					String keepstat = (String) WebUtils.getSessionAttribute(
							httpServletRequest, "keepStatus");
					System.out.println("Keep status  :" + keepstat);
					ModelAndView modelAndView = new ModelAndView(
							ModelAndViewConstant.ALERT_LIST, "alert",
							alertMasterVO);
					if (keepstat != null) {
						modelAndView.getModelMap().addAttribute("status", "false");
					} else {
						modelAndView.getModelMap().addAttribute("status", "true");
					}
					System.out.println("edit alert vo function        :  "+alertMasterVO.getAlertVO().toString());
					//redirectAttribute.addFlashAttribute("generalGrpListArray",alertMasterVO.getAlertVO().getGeneralGrpListArray());
					redirectAttribute.addFlashAttribute("popup", "update");
					redirectAttribute.addFlashAttribute("alert", alertMasterVO);
					return "redirect:/alertList.htm?data";
				}

				// Update method
				@RequestMapping(value = "/alertList", method = RequestMethod.POST, params = { "actionUpdate" })
				public String updateAlert(
						@ModelAttribute("alert") AlertMasterVO alertMasterVO,
						ModelMap model, HttpServletRequest httpServletRequest,
						HttpSession session, RedirectAttributes redirectAttribute)
						throws UpdateFailedException, NoDataFoundException, DuplicateEntryException, DatabaseException, TableNotSpecifiedForAuditException {
					AlertSearchVo alertSearchVo= (AlertSearchVo) WebUtils
							.getSessionAttribute(httpServletRequest, "searchVo");	
					alertMasterVO.setAlertSearchVo(alertSearchVo);	
			         httpServletRequest.setAttribute("errors", "erroradd");		
					redirectAttribute.addFlashAttribute("alert", alertMasterVO);
					logger.debug("inside update Notice Board method");
					ApplicationCache applicationCache = (ApplicationCache) session
							.getServletContext().getAttribute(
									ApplicationConstant.APPLICATION_CACHE);
					SessionCache sessionCache = (SessionCache) session
							.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
					ModelAndView modelAndView = new ModelAndView(
							ModelAndViewConstant.ALERT_LIST, "alert",
							alertMasterVO);
					httpServletRequest.setAttribute("page", modelAndView);
					String generalGrpArray="";
					System.out.println("targ lengthhhhhhhhhh"+alertMasterVO.getAlertVO().getGeneralGrpListArray());
					if(alertMasterVO.getAlertVO().getGeneralGrpListArray()!=null){
						System.out.println("targ lengthhhhhhhhhh"+alertMasterVO.getAlertVO().getGeneralGrpListArray().length);
						for(int i=0;i<alertMasterVO.getAlertVO().getGeneralGrpListArray().length;i++){
							System.out.println("targ valueeeee"+alertMasterVO.getAlertVO().getGeneralGrpListArray()[i]);
							if(i==0){
								generalGrpArray=alertMasterVO.getAlertVO().getGeneralGrpListArray()[i];
							}else{
								generalGrpArray=generalGrpArray+","+alertMasterVO.getAlertVO().getGeneralGrpListArray()[i];
							}
						}
					}
					alertMasterVO.getAlertVO().setGeneralGrpList(generalGrpArray);
					alertService.updateAlertDetailsRec(alertMasterVO.getAlertVO(), sessionCache.getUserSessionDetails(), applicationCache);
					WebUtils.setSessionAttribute(httpServletRequest, "success",
							ErrorCodeConstant.UPDATE_SUCCESS_MESS);
					logger.debug("Data's updated successfully!");
					WebUtils.setSessionAttribute(httpServletRequest, "keepStatus", "status");
					return "redirect:/alertList.htm?Get";
				}

				// Delete Method
				@RequestMapping(value = "/alertList", method = RequestMethod.GET, params = { "actionDelete" })
				public String deleteAlert(
						@ModelAttribute("alert") AlertMasterVO alertMasterVO,
						ModelMap model, HttpServletRequest httpServletRequest,
						HttpSession session, RedirectAttributes redirectAttribute)
						throws DeleteFailedException, NoDataFoundException, AcadCalendarDeleteFailedException, DuplicateEntryException, DatabaseException, TableNotSpecifiedForAuditException, AcadCalFutureDateDeleteFailedException, ParseException, DuplicateEntryForHolGenException, UpdateFailedException {
					AlertSearchVo alertSearchVo= (AlertSearchVo) WebUtils
							.getSessionAttribute(httpServletRequest, "searchVo");	
					alertMasterVO.setAlertSearchVo(alertSearchVo);	
			         httpServletRequest.setAttribute("errors", "erroradd");		
					redirectAttribute.addFlashAttribute("alert", alertMasterVO);
					logger.debug("inside Deleted Notice Board method");
					ApplicationCache applicationCache = (ApplicationCache) session
							.getServletContext().getAttribute(
									ApplicationConstant.APPLICATION_CACHE);
					SessionCache sessionCache = (SessionCache) session
							.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
					ModelAndView modelAndView = new ModelAndView(
							ModelAndViewConstant.ALERT_LIST, "alert",
							alertMasterVO);
					httpServletRequest.setAttribute("page", modelAndView);

					
					List<AlertVO> alertVOVOs = (List<AlertVO>) WebUtils
							.getSessionAttribute(httpServletRequest, "display_tbl");		
					
					String id = httpServletRequest.getParameter("rowId");
					alertMasterVO.setAlertVO(alertVOVOs.get(Integer.parseInt(id)));
					alertService.deleteAlertDetailsRec(alertMasterVO.getAlertVO(), 
							sessionCache.getUserSessionDetails(),applicationCache);			
					WebUtils.setSessionAttribute(httpServletRequest, "success",
							ErrorCodeConstant.DELETE_SUCCESS_MESS);
					logger.debug("Data's deleted successfully!");
					WebUtils.setSessionAttribute(httpServletRequest, "keepStatus", "status");
					return "redirect:/alertList.htm?Get";
				}
				
				
				// ALert Stop Method
				@RequestMapping(value = "/alertList", method = RequestMethod.GET, params = { "actionStop" })
				public String stopAlert(
						@ModelAttribute("alert") AlertMasterVO alertMasterVO,
						ModelMap model, HttpServletRequest httpServletRequest,
						HttpSession session, RedirectAttributes redirectAttribute)
						throws DeleteFailedException, NoDataFoundException, AcadCalendarDeleteFailedException, DuplicateEntryException, DatabaseException, TableNotSpecifiedForAuditException, AcadCalFutureDateDeleteFailedException, ParseException, DuplicateEntryForHolGenException, UpdateFailedException {
					AlertSearchVo alertSearchVo= (AlertSearchVo) WebUtils
							.getSessionAttribute(httpServletRequest, "searchVo");	
					alertMasterVO.setAlertSearchVo(alertSearchVo);	
			         httpServletRequest.setAttribute("errors", "erroradd");		
					redirectAttribute.addFlashAttribute("alert", alertMasterVO);
					logger.debug("inside Deleted Notice Board method");
					ApplicationCache applicationCache = (ApplicationCache) session
							.getServletContext().getAttribute(
									ApplicationConstant.APPLICATION_CACHE);
					SessionCache sessionCache = (SessionCache) session
							.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
					ModelAndView modelAndView = new ModelAndView(
							ModelAndViewConstant.ALERT_LIST, "alert",
							alertMasterVO);
					httpServletRequest.setAttribute("page", modelAndView);

					
					List<AlertVO> alertVOVOs = (List<AlertVO>) WebUtils
							.getSessionAttribute(httpServletRequest, "display_tbl");		
					
					String id = httpServletRequest.getParameter("rowId");
					alertMasterVO.setAlertVO(alertVOVOs.get(Integer.parseInt(id)));
					System.out.println("stop alert vo valuessssssssssssssss"+alertMasterVO.getAlertVO().toString());
					alertService.stopAlertDetailsRec(alertMasterVO.getAlertVO(), 
							sessionCache.getUserSessionDetails(),applicationCache);			
					WebUtils.setSessionAttribute(httpServletRequest, "success",
							ErrorCodeConstant.ALERT_STOP_SUCCESS);
					logger.debug("Data's deleted successfully!");
					WebUtils.setSessionAttribute(httpServletRequest, "keepStatus", "status");
					return "redirect:/alertList.htm?Get";
				}
				
				
				@ExceptionHandler({ DuplicateEntryException.class, DatabaseException.class,			
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
