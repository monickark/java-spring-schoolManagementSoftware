package com.jaw.communication.controller;

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
import com.jaw.common.exceptions.DatabaseException;
import com.jaw.common.exceptions.DeleteFailedException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.UpdateFailedException;
import com.jaw.common.exceptions.util.TableNotSpecifiedForAuditException;
import com.jaw.communication.service.ISMSRequestService;
import com.jaw.core.controller.AcademicCalendarMasterVO;
import com.jaw.core.controller.AcademicCalendarSearchVO;
import com.jaw.core.controller.AcademicCalendarVO;
import com.jaw.framework.sessCache.SessionCache;

@Controller
public class SMSHistoryController {
	// Logging
		Logger logger = Logger.getLogger(SMSRequestController.class);
	@Autowired
	ISMSRequestService smsRqstService;
	@RequestMapping(value="/smsHistoryList",method = RequestMethod.GET)
	public ModelAndView smsHistory(
			@ModelAttribute("smsHistory") SMSHistoryMasterVO smsHistoryMasterVO,
			ModelMap model, HttpServletRequest httpRequest,
			HttpSession httpSession) {
		ModelAndView mav = null ;
		if ((httpRequest.getParameter("action") != null)
				&& (httpRequest.getParameter("action").equals("Back"))) {
			logger.info("In SMS Details page,Back Button has been clicked");
			
			SMSHistorySearchVO smsHistorySearchVO= (SMSHistorySearchVO) WebUtils
					.getSessionAttribute(httpRequest, "searchVo");		
		
			if (smsHistorySearchVO != null) {
				smsHistoryMasterVO.setSmsHistorySearchVO(smsHistorySearchVO);
				 mav = new ModelAndView(
						ModelAndViewConstant.SMS_HISTORY, "smsHistory",smsHistoryMasterVO);
			}
			
		} else {
		logger.info("Rendering sms Request List page");
		// Null the session display tag values
	mav= new ModelAndView(
				ModelAndViewConstant.SMS_HISTORY, "smsHistory",
				smsHistoryMasterVO);	
		WebUtils.setSessionAttribute(httpRequest, "success", null);
		WebUtils.setSessionAttribute(httpRequest, "keepStatus",
				null);
		WebUtils.setSessionAttribute(httpRequest, "searchVo",
				null);
		WebUtils.setSessionAttribute(httpRequest, "display_tbl",
				null);
		WebUtils.setSessionAttribute(httpRequest, "display_tbl1",
				null);
		mav.getModelMap().addAttribute("status", "true");
		
		}
	        
		
		return mav;

	}
	@RequestMapping(value = "/smsHistoryList", method = RequestMethod.GET, params = { "Get" })
	public String viewSMSHistoryList(
			@ModelAttribute("smsHistory") SMSHistoryMasterVO smsHistoryMasterVO,
			HttpServletRequest httpRequest,HttpSession session,
			RedirectAttributes redirectAttributes) throws NoDataFoundException {
		System.out.println("search listtttttttttt "+smsHistoryMasterVO.getSmsHistorySearchVO().toString());
		String redirect="";
		WebUtils.setSessionAttribute(httpRequest, "display_tbl1",
				null);
	
		if ((httpRequest.getParameter("action") != null)
				&& (httpRequest.getParameter("action").equals("Back"))) {
			logger.info("In SMS Details page,Back Button has been clicked");
			
		//dataLogSearchVO.setKeepstat("clear");
			redirectAttributes.addFlashAttribute("smsHistory",
					smsHistoryMasterVO);
			redirect="redirect:/smsHistoryList.htm?data";
			
			//modelAndView.getModelMap().addAttribute("action", "Back");
		} else {
			
		// Getting the display tag parameter
				WebUtils.setSessionAttribute(httpRequest, "searchVo",
						smsHistoryMasterVO.getSmsHistorySearchVO());
		Map stockParamMap = WebUtils.getParametersStartingWith(httpRequest,
				"d-");
		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		// Setting model and view for exception handler
	
		ModelAndView modelAndView = new ModelAndView(
				ModelAndViewConstant.SMS_HISTORY, "smsHistory",
				smsHistoryMasterVO);
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

			smsRqstService.selectSMSRequestList(smsHistoryMasterVO, sessionCache.getUserSessionDetails());
			// Set the list to session to access in jsp

			WebUtils.setSessionAttribute(httpRequest, "display_tbl",
					smsHistoryMasterVO.getSmsRequestListVOs());
			
			smsHistoryMasterVO.setPageSize(smsHistoryMasterVO.getPageSize());

		}
		redirectAttributes.addFlashAttribute("smsHistory",
				smsHistoryMasterVO);
		redirect="redirect:/smsHistoryList.htm?data";
		}
		return redirect;

	}
	@RequestMapping(value="/smsHistoryList",method = RequestMethod.GET,params={"smsDetails"})
	public ModelAndView getSMSDetails(
			@ModelAttribute("smsHistory") SMSHistoryMasterVO smsHistoryMasterVO,
			ModelMap model, HttpServletRequest httpRequest,
			HttpSession httpSession) throws NoDataFoundException {
		System.out.println("sms details list");
		// Getting the display tag parameter
		SMSHistorySearchVO smsHistorySearchVO= (SMSHistorySearchVO) WebUtils
				.getSessionAttribute(httpRequest, "searchVo");		
		smsHistoryMasterVO.setSmsHistorySearchVO(smsHistorySearchVO);	
		Map stockParamMap = WebUtils.getParametersStartingWith(httpRequest,
				"d-");
		SessionCache sessionCache = (SessionCache) httpSession
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		// Setting model and view for exception handler
	
		ModelAndView modelAndView = new ModelAndView(
				ModelAndViewConstant.SMS_HISTORY, "smsHistory",
				smsHistoryMasterVO);
		httpRequest.setAttribute("page", modelAndView);
		httpRequest.setAttribute("errors", "error");
		
		ModelAndView modelAndVie = new ModelAndView(
				ModelAndViewConstant.SMS_DETAILS, "smsHistory",
				smsHistoryMasterVO);
		
	
		List<SMSRequestListVO> smsRqstListVos = (List<SMSRequestListVO>) WebUtils
				.getSessionAttribute(httpRequest, "display_tbl");
		String id = httpRequest.getParameter("rowId");
		System.out.println("sms rqst to string "+smsRqstListVos.get(Integer.parseInt(id)));
		smsHistoryMasterVO.setSmsReqstListVO(smsRqstListVos.get(Integer.parseInt(id)));
		smsRqstService.selectSMSDetailsList(smsHistoryMasterVO, sessionCache.getUserSessionDetails());
		System.out.println("list size    : "+smsHistoryMasterVO.getSmsDetailsListVOs().size());
		for(int i=0;i<smsHistoryMasterVO.getSmsDetailsListVOs().size();i++){
			System.out.println("sms details values : "+smsHistoryMasterVO.getSmsDetailsListVOs().get(i).toString());
		}
		WebUtils.setSessionAttribute(httpRequest, "display_tbl1",
				smsHistoryMasterVO.getSmsDetailsListVOs());
		
		return modelAndVie;

	}
	@RequestMapping(value = "/smsHistoryList", method = RequestMethod.GET, params = "data")
	public ModelAndView staffSMSHistoryListBack(
			@ModelAttribute("smsHistory") SMSHistoryMasterVO smsHistoryMasterVO,
			HttpServletRequest httpServletRequest) {
		SMSHistorySearchVO smsHistorySearchVO= (SMSHistorySearchVO) WebUtils
				.getSessionAttribute(httpServletRequest, "searchVo");		
		smsHistoryMasterVO.setSmsHistorySearchVO(smsHistorySearchVO);		
		ModelAndView modelAndView = new ModelAndView(
				ModelAndViewConstant.SMS_HISTORY, "smsHistory",
				smsHistoryMasterVO);
		
		return modelAndView;
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
