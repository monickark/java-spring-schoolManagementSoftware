package com.jaw.fee.controller;

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
import com.jaw.core.controller.AcademicCalendarController;
import com.jaw.core.controller.CourseTermLinkingMasterVO;
import com.jaw.core.controller.CourseTermLinkingVO;
import com.jaw.fee.service.IFeePaymentDetailService;
import com.jaw.fee.service.IFeesTermService;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.sessCache.SessionCache;

@Controller
public class FeePaymentDetailsController {
	// Logging
	Logger logger = Logger.getLogger(FeePaymentDetailsController.class);

	@Autowired
	IFeePaymentDetailService feePaymentDetailService;

	// Academic Calendar List Get method

	@RequestMapping(value = "/feePaymentDetails", method = RequestMethod.GET)
	public ModelAndView feePaymentDetails(
			@ModelAttribute("feePayDetail") FeePaymentDetailMasterVO feePaymentDetailMasterVO,
			ModelMap model, HttpServletRequest httpRequest,
			HttpSession httpSession) {

		logger.info("Rendering Fee Payment Details List page");
		// Null the session display tag values
		ModelAndView mav = new ModelAndView(
				ModelAndViewConstant.FEE_PAYMENT_DETAILS_LIST, "feePayDetail",
				feePaymentDetailMasterVO);
		WebUtils.setSessionAttribute(httpRequest, "display_tbl", null);
		WebUtils.setSessionAttribute(httpRequest, "success", null);
		WebUtils.setSessionAttribute(httpRequest, "keepStatus", null);
		
		  WebUtils.setSessionAttribute(httpRequest, "searchVo", null);
		 
		mav.getModelMap().addAttribute("status", "true");
		WebUtils.setSessionAttribute(httpRequest, "addAction", null);
		return mav;

	}

	@RequestMapping(value = "/feePaymentDetails", method = RequestMethod.GET, params = { "Get" })
	public String viewFeePaymentDetailsList(
			@ModelAttribute("feePayDetail") FeePaymentDetailMasterVO feePaymentDetailMasterVO,
			HttpSession session, HttpServletRequest httpRequest,
			ModelMap model, RedirectAttributes redirectAttributes)
			throws NoDataFoundException {

		System.out.println("search vo values :"
				+ feePaymentDetailMasterVO.getFeePaymentDetailSearchVO());
		FeePaymentDetailSearchVO feePaymentDetSearchVos = (FeePaymentDetailSearchVO) WebUtils
				.getSessionAttribute(httpRequest, "searchVo");
		System.out.println("session values : " + feePaymentDetSearchVos);
		// Getting the display tag parameter

		Map stockParamMap = WebUtils.getParametersStartingWith(httpRequest,
				"d-");
		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		// Setting model and view for exception handler
		ModelAndView modelAndView = new ModelAndView(
				ModelAndViewConstant.FEE_PAYMENT_DETAILS_LIST, "feePayDetail",
				feePaymentDetailMasterVO);

		System.out.println("action request attribute"
				+ httpRequest.getParameter("action"));

		if (httpRequest.getParameter("action") == null) {
			WebUtils.setSessionAttribute(httpRequest, "searchVo",
					feePaymentDetailMasterVO.getFeePaymentDetailSearchVO());
			WebUtils.setSessionAttribute(httpRequest, "addAction", null);
		} else {
			WebUtils.setSessionAttribute(httpRequest, "addAction", "AddAction");
			FeePaymentDetailSearchVO feePaymentDetSearchVo = (FeePaymentDetailSearchVO) WebUtils
					.getSessionAttribute(httpRequest, "searchVo");
			feePaymentDetailMasterVO
					.setFeePaymentDetailSearchVO(feePaymentDetSearchVo);
		}

		httpRequest.setAttribute("page", modelAndView);
		httpRequest.setAttribute("errors", "error");

		// Check whether the list already get or have to fetch from data base
		// using the list size
		if (httpRequest.getParameter("Get").equals("Get")) {
			WebUtils.setSessionAttribute(httpRequest, "keepStatus", null);
			WebUtils.setSessionAttribute(httpRequest, "success", null);
		} else {
			WebUtils.setSessionAttribute(httpRequest, "keepStatus", "status");
		}
		if (stockParamMap.size() == 0) {

			logger.info("Table operation has been triggered");
			System.out.println("before service : "
					+ feePaymentDetailMasterVO.getFeePaymentDetailSearchVO());
			// Get list from database
			feePaymentDetailService.selectFeePaymentDetailsList(
					feePaymentDetailMasterVO,
					sessionCache.getUserSessionDetails());
			// Set the list to session to access in jsp

			WebUtils.setSessionAttribute(httpRequest, "display_tbl",
					feePaymentDetailMasterVO.getFeePaymentDetailsVOs());

			feePaymentDetailMasterVO.setPageSize(feePaymentDetailMasterVO
					.getPageSize());
		}

		FeePaymentDetailSearchVO feePaymentDetSearchVo = (FeePaymentDetailSearchVO) WebUtils
				.getSessionAttribute(httpRequest, "searchVo");
		System.out.println("get values : " + feePaymentDetSearchVo);
		redirectAttributes.addFlashAttribute("feePayDetail",
				feePaymentDetailMasterVO);

		return "redirect:/feePaymentDetails.htm?data";

	}

	@RequestMapping(value = "/feePaymentDetails", method = RequestMethod.GET, params = "data")
	public ModelAndView feePaymentDetailListBack(
			@ModelAttribute("feePayDetail") FeePaymentDetailMasterVO feePaymentDetailMasterVO,
			ModelMap model, HttpServletRequest httpServletRequest) {

		FeePaymentDetailSearchVO feePaymentDetSearchVo = (FeePaymentDetailSearchVO) WebUtils
				.getSessionAttribute(httpServletRequest, "searchVo");

		System.out.println("data values : " + feePaymentDetSearchVo);
		if (feePaymentDetSearchVo != null) {
			feePaymentDetailMasterVO
					.setFeePaymentDetailSearchVO(feePaymentDetSearchVo);
		}

		ModelAndView modelAndView = new ModelAndView(
				ModelAndViewConstant.FEE_PAYMENT_DETAILS_LIST, "feePayDetail",
				feePaymentDetailMasterVO);

		return modelAndView;
	}

	@RequestMapping(value = "/feePaymentDetails", method = RequestMethod.GET, params = { "actionSave" })
	public String saveFeePaymentDetail(
			@ModelAttribute("feePayDetail") FeePaymentDetailMasterVO feePaymentDetailMasterVO,
			ModelMap model, HttpServletRequest httpServletRequest,
			HttpSession session, RedirectAttributes redirectAttribute)
			throws DuplicateEntryException, DatabaseException,
			UpdateFailedException {

		logger.debug("inside save Fee Payment detail method");
		FeePaymentDetailSearchVO feePaymentDetSearchVo = (FeePaymentDetailSearchVO) WebUtils
				.getSessionAttribute(httpServletRequest, "searchVo");
		System.out.println("save Value : " + feePaymentDetSearchVo);
		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);

		if (feePaymentDetSearchVo != null) {
			feePaymentDetailMasterVO
					.setFeePaymentDetailSearchVO(feePaymentDetSearchVo);
		}
		redirectAttribute.addFlashAttribute("feePayDetail",
				feePaymentDetailMasterVO);
		ModelAndView modelAndView = new ModelAndView(
				ModelAndViewConstant.FEE_PAYMENT_DETAILS_LIST, "feePayDetail",
				feePaymentDetailMasterVO);
		httpServletRequest.setAttribute("page", modelAndView);
		httpServletRequest.setAttribute("errors", "error");
		FeePaymentDetailVO feePaymentDetailVO = new FeePaymentDetailVO();
		feePaymentDetailVO.setFeeCategory(httpServletRequest
				.getParameter("feeCategory"));
		feePaymentDetailVO.setFeePaymentTerm(httpServletRequest
				.getParameter("feePaymentTrm"));
		feePaymentDetailVO.setDueDate(httpServletRequest
				.getParameter("dueDateValue"));
		feePaymentDetailMasterVO.setFeePaymentDetailVO(feePaymentDetailVO);

		feePaymentDetailService.insertFeePaymentDetailRec(
				feePaymentDetailMasterVO, sessionCache.getUserSessionDetails());
		WebUtils.setSessionAttribute(httpServletRequest, "success",
				ErrorCodeConstant.ADD_SUCCESS_MESS);
		logger.debug("Data's inserted successfully!");
		WebUtils.setSessionAttribute(httpServletRequest, "keepStatus", "status");
		return "redirect:/feePaymentDetails.htm?Get";
	}

	// Delete Method
	@RequestMapping(value = "/feePaymentDetails", method = RequestMethod.GET, params = { "actionDelete" })
	public String deleteFeePaymentDetail(
			@ModelAttribute("feePayDetail") FeePaymentDetailMasterVO feePaymentDetailMasterVO,
			ModelMap model, HttpServletRequest httpServletRequest,
			HttpSession session, RedirectAttributes redirectAttribute)
			throws DeleteFailedException, NoDataFoundException,
			DuplicateEntryException, DatabaseException,
			TableNotSpecifiedForAuditException {

		logger.debug("inside Deleted Fee Payment Details method");
		ApplicationCache applicationCache = (ApplicationCache) session
				.getServletContext().getAttribute(
						ApplicationConstant.APPLICATION_CACHE);
		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		FeePaymentDetailSearchVO feePaymentDetSearchVo = (FeePaymentDetailSearchVO) WebUtils
				.getSessionAttribute(httpServletRequest, "searchVo");
		System.out.println("delete values : " + feePaymentDetSearchVo);
		if (feePaymentDetSearchVo != null) {
			feePaymentDetailMasterVO
					.setFeePaymentDetailSearchVO(feePaymentDetSearchVo);
		}
		redirectAttribute.addFlashAttribute("feePayDetail",
				feePaymentDetailMasterVO);
		ModelAndView modelAndView = new ModelAndView(
				ModelAndViewConstant.FEE_PAYMENT_DETAILS_LIST, "feePayDetail",
				feePaymentDetailMasterVO);
		httpServletRequest.setAttribute("page", modelAndView);
		httpServletRequest.setAttribute("errors", "error");

		List<FeePaymentDetailVO> feePaymentDetailVOs = (List<FeePaymentDetailVO>) WebUtils
				.getSessionAttribute(httpServletRequest, "display_tbl");

		String id = httpServletRequest.getParameter("rowId");
		System.out.println("fee payment fetail vo : "
				+ feePaymentDetailVOs.get(Integer.parseInt(id)));
		feePaymentDetailMasterVO.setFeePaymentDetailVO(feePaymentDetailVOs
				.get(Integer.parseInt(id)));
		feePaymentDetailService.deleteFeePaymentDetailRec(
				feePaymentDetailMasterVO.getFeePaymentDetailVO(),
				sessionCache.getUserSessionDetails(), applicationCache);
		WebUtils.setSessionAttribute(httpServletRequest, "success",
				ErrorCodeConstant.DELETE_SUCCESS_MESS);
		logger.debug("Data's deleted successfully!");
		WebUtils.setSessionAttribute(httpServletRequest, "keepStatus", "status");
		return "redirect:/feePaymentDetails.htm?Get";
	}

	@ExceptionHandler({ DuplicateEntryException.class, DatabaseException.class,
			DeleteFailedException.class, UpdateFailedException.class,
			TableNotSpecifiedForAuditException.class })
	public ModelAndView handleException(Exception ex, HttpSession session,
			HttpServletRequest request) {
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
			mav.getModelMap().addAttribute("error",
					ErrorCodeConstant.DELETE_SUCCESS_MESS);

		} else {
			mav.getModelMap().addAttribute("error", ex.getMessage());
		}
		WebUtils.setSessionAttribute(request, "success", null);

		WebUtils.setSessionAttribute(request, "display_tbl", null);

		return mav;
	}

}
