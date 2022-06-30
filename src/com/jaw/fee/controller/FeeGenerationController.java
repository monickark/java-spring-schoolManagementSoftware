package com.jaw.fee.controller;

import java.io.IOException;
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

import com.jaw.attendance.controller.AttendanceMasterVO;
import com.jaw.attendance.controller.AttendanceSearchVO;
import com.jaw.common.constants.ApplicationConstant;
import com.jaw.common.constants.ErrorCodeConstant;
import com.jaw.common.constants.ModelAndViewConstant;
import com.jaw.common.dropdown.service.IDropDownListService;
import com.jaw.common.exceptions.DatabaseException;
import com.jaw.common.exceptions.DeleteFailedException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.FeeMasterExistException;
import com.jaw.common.exceptions.FeeMasterNotFoundException;
import com.jaw.common.exceptions.FeeMasterNotFoundForIntegratedCourseException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.StudentNotFoundException;
import com.jaw.common.exceptions.UpdateFailedException;
import com.jaw.common.exceptions.util.TableNotSpecifiedForAuditException;
import com.jaw.core.controller.AcademicCalendarVO;
import com.jaw.fee.service.IFeeGenerationService;
import com.jaw.fee.service.IFeePaymentDetailService;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.sessCache.SessionCache;

@Controller
public class FeeGenerationController {
	// Logging
	Logger logger = Logger.getLogger(FeeGenerationController.class);

	@Autowired
	IDropDownListService dropDownListService;
	@Autowired
	IFeeGenerationService feeGenerationService;

	// Academic Calendar List Get method

	@RequestMapping(value = "/feeGeneration", method = RequestMethod.GET)
	public ModelAndView feePaymentDetails(
			@ModelAttribute("feeGen") FeeGenerationMasterVO feeGenerationMasterVO,
			ModelMap model, HttpServletRequest httpRequest,
			HttpSession httpSession) {

		logger.info("Rendering Fee Generation page");
		// Null the session display tag values
		ModelAndView mav = new ModelAndView(
				ModelAndViewConstant.FEE_GENERATION, "feeGen",
				feeGenerationMasterVO);
		WebUtils.setSessionAttribute(httpRequest, "display_tbl", null);
		WebUtils.setSessionAttribute(httpRequest, "success", null);
		WebUtils.setSessionAttribute(httpRequest, "keepStatus", null);
		/*
		 * WebUtils.setSessionAttribute(httpRequest, "searchVo", null);
		 */
		mav.getModelMap().addAttribute("status", "true");
		WebUtils.setSessionAttribute(httpRequest, "addAction", null);
		return mav;

	}

	@RequestMapping(value = "/feeGeneration", method = RequestMethod.POST, params = { "Get" })
	public ModelAndView viewFeePaymentDetailsList(
			@ModelAttribute("feeGen") FeeGenerationMasterVO feeGenerationMasterVO,
			HttpSession session, HttpServletRequest httpRequest,
			ModelMap model, RedirectAttributes redirectAttributes)
			throws FeeMasterNotFoundException, StudentNotFoundException,
			NoDataFoundException, DuplicateEntryException, DatabaseException,
			UpdateFailedException, FeeMasterExistException,
			FeeMasterNotFoundForIntegratedCourseException {
		System.out.println("fee generation get");
		ModelAndView mav = new ModelAndView(
				ModelAndViewConstant.FEE_GENERATION, "feeGen",
				feeGenerationMasterVO);
		httpRequest.setAttribute("page", mav);
		httpRequest.setAttribute("errors", "error");
		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		ApplicationCache applicationCache = (ApplicationCache) session
				.getServletContext().getAttribute(
						ApplicationConstant.APPLICATION_CACHE);
		String threadId = feeGenerationService.feeGenerate(applicationCache,
				feeGenerationMasterVO, sessionCache);
		httpRequest.setAttribute("threadID", threadId);

		System.out.println("values : fee generation"
				+ feeGenerationMasterVO.toString());
		return mav;
	}

	// Ajax For getting Ac Term Date for Validation
	@RequestMapping(value = "/feeGeneration", method = RequestMethod.GET, params = { "checkThreadID" })
	public @ResponseBody String retriveList(HttpSession session,
			HttpServletRequest request) {
		System.out.println("check Thread Id method");
		// threadIdValue;
		System.out.println("thread idddddddd "
				+ request.getParameter("threadIdValue"));
		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		String result = "";
		try {
			result = feeGenerationService.checkFeeStatus(
					request.getParameter("threadIdValue"), sessionCache);
		} catch (NoDataFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;
	}

	@ModelAttribute("courseList")
	public Map<String, String> gerCourseNameList(HttpSession session,
			HttpServletRequest httpSevletRequest, HttpServletResponse response,
			ModelMap model) throws IOException {
		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		Map<String, String> map = null;
		try {
			map = dropDownListService.getCourseNameListTag(sessionCache
					.getUserSessionDetails());
		} catch (NoDataFoundException e) {
			logger.error("Error occured in Course Name list Dropdown :"
					+ e.getMessage());
		}
		httpSevletRequest.setAttribute("courseList", map);
		return map;

	}

	@ExceptionHandler({ FeeMasterNotFoundException.class,
			StudentNotFoundException.class, NoDataFoundException.class,
			DuplicateEntryException.class, DatabaseException.class,
			UpdateFailedException.class, FeeMasterExistException.class,
			FeeMasterNotFoundForIntegratedCourseException.class })
	public ModelAndView handleException(Exception ex, HttpSession session,
			HttpServletRequest request) {
		ModelAndView mav = (ModelAndView) request.getAttribute("page");
		String error = (String) request.getAttribute("errors");
		mav.getModelMap().addAttribute(error, ex.getMessage());
		WebUtils.setSessionAttribute(request, "success", null);
		return mav;

	}
}
