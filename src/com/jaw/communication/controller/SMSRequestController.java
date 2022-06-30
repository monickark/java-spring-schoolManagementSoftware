package com.jaw.communication.controller;

import java.util.HashMap;
import java.util.List;

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
import com.jaw.common.exceptions.DatabaseException;
import com.jaw.common.exceptions.DeleteFailedException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.UpdateFailedException;
import com.jaw.common.exceptions.util.TableNotSpecifiedForAuditException;
import com.jaw.communication.dao.SMSMemberList;
import com.jaw.communication.service.ISMSRequestService;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.sessCache.SessionCache;

@Controller
public class SMSRequestController {
	// Logging
	Logger logger = Logger.getLogger(SMSRequestController.class);
	@Autowired
	ISMSRequestService smsRequestService;

	@RequestMapping(value = "/smsRequestList", method = RequestMethod.GET)
	public ModelAndView smsRequest(
			@ModelAttribute("smsRequest") SMSRequestMasterVO smsRequestMasterVO,
			ModelMap model, HttpServletRequest httpRequest,
			HttpSession httpSession) {

		logger.info("Rendering sms Request List page");
		// Null the session display tag values
		ModelAndView mav = new ModelAndView(ModelAndViewConstant.SMS_REQUEST,
				"smsRequest", smsRequestMasterVO);
		WebUtils.setSessionAttribute(httpRequest, "success", null);
		WebUtils.setSessionAttribute(httpRequest, "keepStatus", null);
		WebUtils.setSessionAttribute(httpRequest, "searchVo", null);
		SessionCache sessionCache = (SessionCache) httpSession
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		HashMap<String, String> lisy = new HashMap<String, String>();
		lisy.put("PAR", "Parent");
		lisy.put("STU", "Student");
		lisy.put("STA", "Staff");
		lisy.put("MGT", "Management");

		WebUtils.setSessionAttribute(httpRequest, "StringCheckBoxArray", lisy);
		WebUtils.setSessionAttribute(httpRequest, "StudentGrpListArray",
				smsRequestService.getStudentGroupList(sessionCache));
		WebUtils.setSessionAttribute(httpRequest, "DepartementListArray",
				smsRequestService.getDepartementList(sessionCache
						.getUserSessionDetails()));

		mav.getModelMap().addAttribute("status", "true");
		return mav;

	}

	@RequestMapping(value = "/smsRequestList", method = RequestMethod.GET, params = { "Save" })
	public ModelAndView viewAlertList(
			@ModelAttribute("smsRequest") SMSRequestMasterVO smsRequestMasterVO,
			HttpServletRequest httpRequest, HttpSession session,
			RedirectAttributes redirectAttributes) throws NoDataFoundException,
			DatabaseException, DuplicateEntryException, UpdateFailedException,
			TableNotSpecifiedForAuditException {
		System.out.println("list valuessssssssssssss"
				+ smsRequestMasterVO.getSmsRestVo().toString());
		String[] speMemListArray = httpRequest
				.getParameterValues("specificMemberMobileList");
		if (httpRequest.getParameterValues("specificMemberMobileList") != null) {
			System.out
					.println("mobile listtttttttttttt"
							+ httpRequest
									.getParameterValues("specificMemberMobileList").length);
			for (int i = 0; i < httpRequest
					.getParameterValues("specificMemberMobileList").length; i++) {
				System.out
						.println("mobile listttttttttttt"
								+ httpRequest
										.getParameterValues("specificMemberMobileList")[i]);
			}

		}
		ApplicationCache applicationCache = (ApplicationCache) session
				.getServletContext().getAttribute(
						ApplicationConstant.APPLICATION_CACHE);
		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		// Setting model and view for exception handler

		ModelAndView modelAndView = new ModelAndView(
				ModelAndViewConstant.SMS_REQUEST, "smsRequest",
				smsRequestMasterVO);
		httpRequest.setAttribute("page", modelAndView);
		httpRequest.setAttribute("errors", "error");
		smsRequestService.saveSMSRequest(sessionCache.getUserSessionDetails(),
				smsRequestMasterVO.getSmsRestVo(), speMemListArray,
				applicationCache);
		WebUtils.setSessionAttribute(httpRequest, "success",
				ErrorCodeConstant.ADD_SUCCESS_MESS);
		redirectAttributes.addFlashAttribute("smsRequest", smsRequestMasterVO);
		return modelAndView;

	}

	// Ajax For getting StudentGroups
	@RequestMapping(value = "/smsRequestList", method = RequestMethod.GET, params = { "specicficMember" })
	public @ResponseBody
	List<SMSMemberList> retriveSpecificMemberList(HttpSession session,
			HttpServletRequest request, HttpServletResponse response) {
		String res = "result";
		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		List<SMSMemberList> lts = null;
		try {
			lts = smsRequestService.getSpecificMemberList(
					sessionCache.getUserSessionDetails(),
					(String) request.getParameter("GenericGroup"),
					(String) request.getParameter("SpecificGroup"));
		} catch (NoDataFoundException e) {
			response.setStatus(400);
			logger.error("Error occured in Specific Member controller :"
					+ e.getMessage());
		}
		if (lts.isEmpty()) {
			lts.add(null);
		}
		return lts;
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

	@ExceptionHandler({ NoDataFoundException.class })
	public ModelAndView handleNoDataException(Exception ex,
			HttpSession session, HttpServletRequest request) {
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
