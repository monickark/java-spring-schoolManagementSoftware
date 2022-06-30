package com.jaw.fee.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRPdfExporterParameter;

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
import com.jaw.common.exceptions.AcadCalFutureDateDeleteFailedException;
import com.jaw.common.exceptions.AcadCalendarDeleteFailedException;
import com.jaw.common.exceptions.DatabaseException;
import com.jaw.common.exceptions.DeleteFailedException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.UpdateFailedException;
import com.jaw.common.exceptions.batch.DuplicateEntryForAcaTermHolGenException;
import com.jaw.common.exceptions.util.ErrorDescriptionNotFoundException;
import com.jaw.common.exceptions.util.PropertyNotFoundException;
import com.jaw.common.exceptions.util.TableNotSpecifiedForAuditException;
import com.jaw.fee.dao.FeeDueDetailsList;
import com.jaw.fee.service.IFeeDetailsService;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.sessCache.SessionCache;

@Controller
public class FeeDetailsController {
	Logger logger = Logger.getLogger(FeeDetailsController.class);
	@Autowired
	IDropDownListService dropDownListService;
	@Autowired
	IFeeDetailsService feeDetailsService;
	
	@RequestMapping(value = "/feeDetails", method = RequestMethod.GET)
	public ModelAndView stdlistGet(
			@ModelAttribute("feeDetMaster") FeeDetailsMasterVO feeDetailsMasterVO,
			HttpServletRequest httpServletRequest, HttpSession session,
			ModelMap model) throws NoDataFoundException,
			ErrorDescriptionNotFoundException {
		ModelAndView mav = null;

		if ((httpServletRequest.getParameter("action") != null)
				&& (httpServletRequest.getParameter("action").equals("Back"))) {
			logger.info("In Fee Details page,Back Button has been clicked");

			FeeDetailsSearchVO feeDetailsSearchVO = (FeeDetailsSearchVO) WebUtils
					.getSessionAttribute(httpServletRequest, "searchVo");
			feeDetailsMasterVO.setFeeDetailsSearchVO(feeDetailsSearchVO);
			if (feeDetailsSearchVO != null) {
				feeDetailsMasterVO.setFeeDetailsSearchVO(feeDetailsSearchVO);
				mav = new ModelAndView(ModelAndViewConstant.FEE_DETAILS_LIST,
						"feeDetMaster", feeDetailsMasterVO);
			}

		} else {
			logger.info("Fee Details render page");
			// Null the session display tag values

			mav = new ModelAndView(ModelAndViewConstant.FEE_DETAILS_LIST,
					"feeDetMaster", feeDetailsMasterVO);
			httpServletRequest.setAttribute("page", mav);
			WebUtils.setSessionAttribute(httpServletRequest, "display_tbl",
					null);
			WebUtils.setSessionAttribute(httpServletRequest, "success", null);
			WebUtils.setSessionAttribute(httpServletRequest, "display_tblPaid",
					null);
			WebUtils.setSessionAttribute(httpServletRequest, "display_tbl1",
					null);
			WebUtils.setSessionAttribute(httpServletRequest, "searchVo", null);

		}

		return mav;

	}

	@ModelAttribute("stGroupList")
	public Map<String, String> gerStudentGroupList(HttpSession session,
			HttpServletRequest httpSevletRequest, ModelMap model) {
		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		Map<String, String> map = null;
		try {
			map = dropDownListService.getStGroupModelAttr(sessionCache
					.getUserSessionDetails());
		} catch (NoDataFoundException e) {
			logger.error("Error occured in Student group list Dropdown :"
					+ e.getMessage());
		}
		httpSevletRequest.setAttribute("stGroupList", map);
		return map;

	}

	@RequestMapping(value = "/feeDetails", method = RequestMethod.GET, params = { "Get" })
	public String viewMarkMasterList(
			@ModelAttribute("feeDetMaster") FeeDetailsMasterVO feeDetailsMasterVO,
			HttpSession session, HttpServletRequest httpRequest,
			RedirectAttributes redirectAttributes) throws NoDataFoundException {
		// Getting the display tag parameter
		String redirect = "";
		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		Map stockParamMap = WebUtils.getParametersStartingWith(httpRequest,
				"d-");
		// Setting model and view for exception handler
		if ((httpRequest.getParameter("action") != null)
				&& (httpRequest.getParameter("action").equals("Back"))) {
			logger.info("In Fee Details page,Back Button has been clicked");

			System.out
					.println("In Fee Details page,Back Button has been clicked");
			redirectAttributes.addFlashAttribute("feeDetMaster",
					feeDetailsMasterVO);
			redirect = "redirect:/feeDetails.htm?data";

			// modelAndView.getModelMap().addAttribute("action", "Back");
		} else {

			if ((httpRequest.getParameter("feeReport") != null)) {
				String feeReport = httpRequest.getParameter("feeReport");
				System.out.println("Fee  Report:" + feeReport);
				FeeDetailsSearchVO feeDetailsSearchVO = new FeeDetailsSearchVO();
				String admisNo = httpRequest.getParameter("admisNo");
				feeDetailsSearchVO.setAdmissionNumber(admisNo);
				String stGroup = httpRequest.getParameter("stGroup");
				feeDetailsSearchVO.setStudentGroupId(stGroup);
				feeDetailsSearchVO.setAcademicTerm(sessionCache
						.getUserSessionDetails().getCurrAcTerm());
				feeDetailsMasterVO.setFeeDetailsSearchVO(feeDetailsSearchVO);
			}
			ModelAndView modelAndView = new ModelAndView(
					ModelAndViewConstant.FEE_DETAILS_LIST, "feeDetMaster",
					feeDetailsMasterVO);
			httpRequest.setAttribute("page", modelAndView);
			httpRequest.setAttribute("errors", "error");
			// Check whether the list already get or have to fetch from data
			// base
			// using the list size
			System.out.println("b4 set Searchvo :"
					+ feeDetailsMasterVO.getFeeDetailsSearchVO().toString());
			WebUtils.setSessionAttribute(httpRequest, "searchVo",
					feeDetailsMasterVO.getFeeDetailsSearchVO());
			if (stockParamMap.size() == 0) {

				logger.info("Table operation has been triggered");

				// Get list from database
				feeDetailsService.selectFeeDueAndPaidListDetails(sessionCache,
						feeDetailsMasterVO);
				// Set the list to session to access in jsp

				WebUtils.setSessionAttribute(httpRequest, "display_tbl",
						feeDetailsMasterVO.getFeeDueList());
				// feeDetailsService.selectFeePaidListDetails(sessionCache,
				// feeDetailsMasterVO);
				WebUtils.setSessionAttribute(httpRequest, "display_tblPaid",
						feeDetailsMasterVO.getFeePaidList());
			}
			redirectAttributes.addFlashAttribute("feeDetMaster",
					feeDetailsMasterVO);
			redirect = "redirect:/feeDetails.htm?data";
		}
		return redirect;

	}

	@RequestMapping(value = "/feeDetails", method = RequestMethod.GET, params = "data")
	public ModelAndView staffListBack(
			@ModelAttribute("feeDetMaster") FeeDetailsMasterVO feeDetailsMasterVO,
			HttpServletRequest httpServletRequest) {
		FeeDetailsSearchVO feeDetailsSearchVO = (FeeDetailsSearchVO) WebUtils
				.getSessionAttribute(httpServletRequest, "searchVo");
		System.out.println("Searchvo :" + feeDetailsSearchVO.toString());
		feeDetailsMasterVO.setFeeDetailsSearchVO(feeDetailsSearchVO);
		ModelAndView modelAndView = new ModelAndView(
				ModelAndViewConstant.FEE_DETAILS_LIST, "feeDetMaster",
				feeDetailsMasterVO);

		return modelAndView;
	}

	// For Fee Due list details
	@RequestMapping(value = "/feeDetails", method = RequestMethod.GET, params = { "feeDueDetails" })
	public ModelAndView getFeeDueDetails(
			@ModelAttribute("feeDetMaster") FeeDetailsMasterVO feeDetailsMasterVO,
			ModelMap model, HttpServletRequest httpRequest,
			HttpSession httpSession) throws NoDataFoundException {
		System.out.println("Fee due details list");
		// Getting the display tag parameter
		FeeDetailsSearchVO feeDetailsSearchVO = (FeeDetailsSearchVO) WebUtils
				.getSessionAttribute(httpRequest, "searchVo");
		feeDetailsMasterVO.setFeeDetailsSearchVO(feeDetailsSearchVO);
		Map stockParamMap = WebUtils.getParametersStartingWith(httpRequest,
				"d-");
		SessionCache sessionCache = (SessionCache) httpSession
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		// Setting model and view for exception handler

		ModelAndView modelAndView = new ModelAndView(
				ModelAndViewConstant.FEE_DETAILS_LIST, "feeDetMaster",
				feeDetailsMasterVO);
		httpRequest.setAttribute("page", modelAndView);
		httpRequest.setAttribute("errors", "error");

		List<FeeDueListVO> feeDueLists = (List<FeeDueListVO>) WebUtils
				.getSessionAttribute(httpRequest, "display_tbl");
		String id = httpRequest.getParameter("rowId");

		FeeDueListVO feeDue = feeDueLists.get(Integer.parseInt(id));
		List<FeeDueDetailsList> feeDueDetList = feeDetailsService
				.getFeeDueDetailsList(feeDue,
						sessionCache.getUserSessionDetails(),
						feeDetailsMasterVO);
		System.out.println("list size    : " + feeDueDetList.size());
		if ((feeDueDetList != null) && (feeDueDetList.size() != 0)) {
			FeeDueDetailVO feeDueDetailVO = new FeeDueDetailVO();
			feeDueDetailVO.setConcessionAmt(feeDueDetList.get(0)
					.getConcessionAmt());
			feeDueDetailVO.setFeeDueAmt(feeDueDetList.get(0).getFeeDueAmt());
			feeDueDetailVO
					.setTotalFeeAmt(feeDueDetList.get(0).getTotalFeeAmt());
			feeDueDetailVO.setFeePaidAmt(feeDueDetList.get(0).getFeePaidAmt());
			feeDueDetailVO.setLastYearPayment(feeDueDetList.get(0)
					.getLastYearPayment());
			feeDetailsMasterVO.setFeeDueDetailVO(feeDueDetailVO);
		}
		httpSession.setAttribute("feeDueDet",
				feeDetailsMasterVO.getFeeDueDetailVO());
		ModelAndView modelAndVie = new ModelAndView(
				ModelAndViewConstant.FEE_DUE_DETAILS_LIST, "feeDetMaster",
				feeDetailsMasterVO);
		WebUtils.setSessionAttribute(httpRequest, "display_tbl1", feeDueDetList);
		return modelAndVie;
	}

	// For Fee Payment Receipt list details
	@RequestMapping(value = "/feeDetails", method = RequestMethod.GET, params = { "feePaidReceiptDetails" })
	public ModelAndView getFeePaidReceiptDetails(
			@ModelAttribute("feeDetMaster") FeeDetailsMasterVO feeDetailsMasterVO,
			ModelMap model, HttpServletRequest httpRequest,
			HttpSession httpSession) throws NoDataFoundException,
			PropertyNotFoundException {
		System.out.println("Fee paid Receipt details list");
		// Getting the display tag parameter
		FeeDetailsSearchVO feeDetailsSearchVO = (FeeDetailsSearchVO) WebUtils
				.getSessionAttribute(httpRequest, "searchVo");
		feeDetailsMasterVO.setFeeDetailsSearchVO(feeDetailsSearchVO);
		Map stockParamMap = WebUtils.getParametersStartingWith(httpRequest,
				"d-");
		SessionCache sessionCache = (SessionCache) httpSession
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		ApplicationCache applicationCache = (ApplicationCache) httpSession
				.getServletContext().getAttribute(
						ApplicationConstant.APPLICATION_CACHE);
		// Setting model and view for exception handler

		ModelAndView modelAndView = new ModelAndView(
				ModelAndViewConstant.FEE_DETAILS_LIST, "feeDetMaster",
				feeDetailsMasterVO);
		httpRequest.setAttribute("page", modelAndView);
		httpRequest.setAttribute("errors", "error");

		List<FeePaidListVO> feePaidLists = (List<FeePaidListVO>) WebUtils
				.getSessionAttribute(httpRequest, "display_tblPaid");
		String id = httpRequest.getParameter("rowId");

		FeePaidListVO feePaid = feePaidLists.get(Integer.parseInt(id));
		List<FeePaymentList> feePaymnetDetList = feeDetailsService
				.selectFeePaidPaymentListDetails(feePaid, sessionCache,
						feeDetailsMasterVO, applicationCache);
		System.out.println("list size    : " + feePaymnetDetList.size());

		WebUtils.setSessionAttribute(httpRequest, "display_tblPaidReceipt",
				feePaymnetDetList);
		int totalAmount = 0;
		for (FeePaymentList feePaymentList : feePaymnetDetList) {
			totalAmount = totalAmount + feePaymentList.getReceiptFeeAmt();

		}
		httpRequest.setAttribute("totalPaid", totalAmount);
		ModelAndView modelAndVie = new ModelAndView(
				ModelAndViewConstant.FEE_RECEIPT_DETAILS_LIST, "feeDetMaster",
				feeDetailsMasterVO);

		return modelAndVie;
	}

	// For Fee Payment Receipt list details
	@RequestMapping(value = "/feeDetails", method = RequestMethod.GET, params = { "viewReceipt" })
	public ModelAndView viewReceiptDetails(
			@ModelAttribute("feeDetMaster") FeeDetailsMasterVO feeDetailsMasterVO,
			ModelMap model, HttpServletRequest httpRequest,
			HttpSession httpSession, HttpServletResponse httpServletResponse)
			throws NoDataFoundException, PropertyNotFoundException,
			IOException, DatabaseException, UpdateFailedException {
		System.out.println("Fee paid Receipt details list");
		// Getting the display tag parameter
		FeeDetailsSearchVO feeDetailsSearchVO = (FeeDetailsSearchVO) WebUtils
				.getSessionAttribute(httpRequest, "searchVo");
		feeDetailsMasterVO.setFeeDetailsSearchVO(feeDetailsSearchVO);
		Map stockParamMap = WebUtils.getParametersStartingWith(httpRequest,
				"d-");
		SessionCache sessionCache = (SessionCache) httpSession
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		ApplicationCache applicationCache = (ApplicationCache) httpSession
				.getServletContext().getAttribute(
						ApplicationConstant.APPLICATION_CACHE);
		// Setting model and view for exception handler

		ModelAndView modelAndView = new ModelAndView(
				ModelAndViewConstant.FEE_DETAILS_LIST, "feeDetMaster",
				feeDetailsMasterVO);
		httpRequest.setAttribute("page", modelAndView);
		httpRequest.setAttribute("errors", "error");

		List<FeePaidListVO> feePaidLists = (List<FeePaidListVO>) WebUtils
				.getSessionAttribute(httpRequest, "display_tblPaid");
		String id = httpRequest.getParameter("rowId");
		String print = httpRequest.getParameter("print");
		System.out.println("Print :" + print);
		FeePaidListVO feePaid = feePaidLists.get(Integer.parseInt(id));
		if (!print.equals("")) {
			feeDetailsService.printReceipt(feePaid,
					sessionCache.getUserSessionDetails(), applicationCache);
		}

		List<FeePaymentList> feePaymnetDetList = feeDetailsService
				.selectFeePaidPaymentListDetails(feePaid, sessionCache,
						feeDetailsMasterVO, applicationCache);
		System.out.println("list size    : " + feePaymnetDetList.size());
		WebUtils.setSessionAttribute(httpRequest, "display_tblPaidReceipt",
				feePaymnetDetList);

		getBranchReceipt(feePaymnetDetList, feeDetailsMasterVO,
				feeDetailsSearchVO, feePaid, httpServletResponse, httpRequest);

		ModelAndView modelAndVie = new ModelAndView(
				ModelAndViewConstant.FEE_RECEIPT_DETAILS_LIST, "feeDetMaster",
				feeDetailsMasterVO);

		return modelAndVie;
	}

	public String getBranchReceipt(List<FeePaymentList> feePaymnetDetList,
			FeeDetailsMasterVO feeDetailsMasterVO,
			FeeDetailsSearchVO feeDetailsSearchVO, FeePaidListVO paidListVO,

			HttpServletResponse response, HttpServletRequest httpServletRequest)
			throws IOException {
		logger.debug("Received request to download PDF report");
		String input = httpServletRequest.getSession().getServletContext()
				.getRealPath("//reports//ReceiptGenerate.jrxml");

		JasperReport jreport1 = null;

		try {
			ServletOutputStream servletOutputStream = response
					.getOutputStream();
			byte[] bytes = null;

			System.out.println("In jasper datasource :"
					+ feePaymnetDetList.size());

			JRDataSource datasource = new JRBeanCollectionDataSource(
					feePaymnetDetList);
			Map<String, Object> parameterMap = new HashMap<String, Object>();
			parameterMap.put("datasource", datasource);

			parameterMap.put("admisNo", feeDetailsMasterVO
					.getFeeDetailsSearchVO().getAdmissionNumber());
			parameterMap.put("name", feeDetailsMasterVO.getFeeReceiptVO()
					.getStudentName());
			parameterMap.put("stGroup", feeDetailsMasterVO.getFeeReceiptVO()
					.getStudentGroup());

			parameterMap.put("instrumentNo", "12358643");
			parameterMap.put("feePaid", feeDetailsMasterVO.getFeeReceiptVO()
					.getFeePaidAmt());
			parameterMap.put("date", feeDetailsMasterVO.getFeeReceiptVO()
					.getFeeReceiptDate());
			parameterMap.put("srlNo", feeDetailsMasterVO.getFeeReceiptVO()
					.getFeeReceiptNo());

			SessionCache sessionCache = (SessionCache) httpServletRequest
					.getSession().getAttribute(
							ApplicationConstant.SESSION_CACHE_KEY);
			String path = httpServletRequest
					.getSession()
					.getServletContext()
					.getRealPath(
							"//images//"
									+ sessionCache.getUserSessionDetails()
											.getBranchId() + "_LOGO.png");

			parameterMap.put("logo", path);

			jreport1 = JasperCompileManager.compileReport(input);
			bytes = JasperRunManager.runReportToPdf(jreport1, parameterMap,
					datasource);
			response.setContentType("application/pdf");
			response.setContentLength(bytes.length);

			servletOutputStream.write(bytes, 0, bytes.length);

		} catch (JRException e) {

			StringWriter stringWriter = new StringWriter();
			PrintWriter printWriter = new PrintWriter(stringWriter);
			e.printStackTrace(printWriter);
			response.setContentType("text/plain");
			response.getOutputStream().print(stringWriter.toString());
		}
		return null;

	}

	public String getTrustReceipt(FeeDetailsMasterVO feeDetailsMasterVO,

	HttpServletResponse response, HttpServletRequest httpServletRequest)
			throws IOException {
		logger.debug("Received request to download PDF report");
		String input = httpServletRequest.getSession().getServletContext()
				.getRealPath("//reports//TrustReceiptGenerate.jrxml");

		JasperReport jreport1 = null;

		try {
			ServletOutputStream servletOutputStream = response
					.getOutputStream();
			byte[] bytes = null;

			Map<String, Object> parameterMap = new HashMap<String, Object>();

			parameterMap.put("admisNo", feeDetailsMasterVO
					.getFeeDetailsSearchVO().getAdmissionNumber());
			parameterMap.put("name", feeDetailsMasterVO.getFeeReceiptVO()
					.getStudentName());
			parameterMap.put("stGroup", feeDetailsMasterVO.getFeeReceiptVO()
					.getStudentGroup());

			parameterMap.put("instrumentNo", "12358643");
			parameterMap.put("feePaid", feeDetailsMasterVO.getFeeReceiptVO()
					.getFeePaidAmt());
			parameterMap.put("date", feeDetailsMasterVO.getFeeReceiptVO()
					.getFeeReceiptDate());
			parameterMap.put("srlNo", feeDetailsMasterVO.getFeeReceiptVO()
					.getFeeReceiptNo());

			SessionCache sessionCache = (SessionCache) httpServletRequest
					.getSession().getAttribute(
							ApplicationConstant.SESSION_CACHE_KEY);
			String path = httpServletRequest
					.getSession()
					.getServletContext()
					.getRealPath(
							"//images//"
									+ sessionCache.getUserSessionDetails()
											.getInstId() + "_LOGO.png");

			jreport1 = JasperCompileManager.compileReport(input);
			bytes = JasperRunManager.runReportToPdf(jreport1, parameterMap,
					new JREmptyDataSource());
			response.setContentType("application/pdf");
			response.setContentLength(bytes.length);

			servletOutputStream.write(bytes, 0, bytes.length);

		} catch (JRException e) {

			StringWriter stringWriter = new StringWriter();
			PrintWriter printWriter = new PrintWriter(stringWriter);
			e.printStackTrace(printWriter);
			response.setContentType("text/plain");
			response.getOutputStream().print(stringWriter.toString());
		}
		return null;

	}

	@ExceptionHandler({ DuplicateEntryException.class, DatabaseException.class,
			DuplicateEntryForAcaTermHolGenException.class,
			UpdateFailedException.class, DeleteFailedException.class,
			AcadCalendarDeleteFailedException.class,
			AcadCalFutureDateDeleteFailedException.class,
			PropertyNotFoundException.class,
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
		WebUtils.setSessionAttribute(request, "display_tblPaid", null);
		WebUtils.setSessionAttribute(request, "display_tbl1", null);
		return mav;
	}
}
