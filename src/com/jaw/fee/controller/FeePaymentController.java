package com.jaw.fee.controller;

import java.io.File;
import java.io.FileInputStream;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.WebUtils;

import com.jaw.common.constants.ApplicationConstant;
import com.jaw.common.constants.CommonCodeConstant;
import com.jaw.common.constants.ErrorCodeConstant;
import com.jaw.common.constants.PropertyManagementConstant;
import com.jaw.common.dropdown.service.IDropDownListService;
import com.jaw.common.exceptions.DatabaseException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.FeeMasterExistException;
import com.jaw.common.exceptions.FeeMasterNotFoundException;
import com.jaw.common.exceptions.FeeMasterNotFoundForIntegratedCourseException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.StudentNotFoundException;
import com.jaw.common.exceptions.UpdateFailedException;
import com.jaw.common.exceptions.util.ErrorDescriptionNotFoundException;
import com.jaw.common.exceptions.util.PropertyNotFoundException;
import com.jaw.common.util.ErrorCodeUtil;
import com.jaw.common.util.PropertyManagementUtil;
import com.jaw.fee.dao.FeeDueDetailsList;
import com.jaw.fee.service.IFeeDetailsService;
import com.jaw.fee.service.IFeePaymentService;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.sessCache.SessionCache;

@Controller
public class FeePaymentController {

	Logger logger = Logger.getLogger(FeePaymentController.class);

	@Autowired
	IDropDownListService dropDownListService;
	@Autowired
	ErrorCodeUtil errorCodeUtil;
	@Autowired
	IFeePaymentService feePaymentService;
	@Autowired
	PropertyManagementUtil propertyManagementUtil;
	@Autowired
	IFeeDetailsService  feeDetailsService;
	@RequestMapping(value = "/feePayment", method = RequestMethod.GET)
	public ModelAndView stdlistGet(
			@ModelAttribute("feePayment") FeePaymentMasterVO FeePaymentMasterVO,
			HttpServletRequest HttpServletRequest, HttpSession session,
			ModelMap model) throws NoDataFoundException,
			ErrorDescriptionNotFoundException {

		logger.info("Fee Master page submitted");
		ModelAndView mav = new ModelAndView(".jaw.feePayment", "feePayment",
				FeePaymentMasterVO);
		WebUtils.setSessionAttribute(HttpServletRequest, "display_tbl", null);
		WebUtils.setSessionAttribute(HttpServletRequest, "display_tbl1", null);
		WebUtils.setSessionAttribute(HttpServletRequest, "success", null);
		HttpServletRequest.setAttribute("page", mav);

		return mav;

	}

	@RequestMapping(value = "/feePayment", method = RequestMethod.GET, params = { "Get" })
	public String FeePaymentSearch(
			@ModelAttribute("feePayment") FeePaymentMasterVO feePaymentMasterVO,
			ModelMap model, HttpServletRequest httpRequest,
			HttpSession httpSession, RedirectAttributes redirectAttributes)
			throws NoDataFoundException, FeeMasterNotFoundException,
			StudentNotFoundException, DuplicateEntryException,
			DatabaseException, PropertyNotFoundException,
			UpdateFailedException, FeeMasterExistException,
			FeeMasterNotFoundForIntegratedCourseException {

		logger.info("Search Initiated,Get has been clicked");
		SessionCache sessionCache = (SessionCache) httpSession
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		ApplicationCache applicationCache = (ApplicationCache) httpSession
				.getServletContext().getAttribute(
						ApplicationConstant.APPLICATION_CACHE);
		
		// Getting the display tag parameter
		ModelAndView mav = new ModelAndView(".jaw.feePayment", "feePayment",
				feePaymentMasterVO);
		httpRequest.setAttribute("page", mav);
		Map stockParamMap = WebUtils.getParametersStartingWith(httpRequest,
				"d-");
		
		if ((httpRequest.getParameter("feeDetail") != null)) {
			String feeDetail = httpRequest.getParameter("feeDetail");
			System.out.println("Fee  Detail:" + feeDetail);
			FeePaymentSearchVO feePaymentSearchVO = new FeePaymentSearchVO();
			String admisNo = httpRequest.getParameter("admisNo");
			feePaymentSearchVO.setStudentAdmisNo(admisNo);
			String stGroup = httpRequest.getParameter("stGroup");
			feePaymentSearchVO.setStGroup(stGroup);
			feePaymentSearchVO.setAcTerm(sessionCache
					.getUserSessionDetails().getCurrAcTerm());
			
			feePaymentMasterVO.setFeePaymentSearchVO(feePaymentSearchVO);
		}
		
		WebUtils.setSessionAttribute(httpRequest, "searchVo1",
				feePaymentMasterVO.getFeePaymentSearchVO());
		
		feePaymentService.selectFeePaymentRec(feePaymentMasterVO,
				sessionCache.getUserSessionDetails(), sessionCache,
				applicationCache);
		FeeDueListVO feeDue=new FeeDueListVO();
		List<FeeDueDetailsList> feeDueDetList = feeDetailsService
				.getFeeConsolidation(feeDue,
						sessionCache.getUserSessionDetails(),
						feePaymentMasterVO.getFeePaymentSearchVO());
		System.out.println("list size    : " + feeDueDetList.size());
		if ((feeDueDetList != null) && (feeDueDetList.size() != 0)) {
			FeeDueDetailVO feeDueDetailVO = new FeeDueDetailVO();
			feeDueDetailVO.setConcessionAmt(feeDueDetList.get(0)
					.getConcessionAmt());
			feeDueDetailVO.setFeeDueAmt(feeDueDetList.get(0).getFeeDueAmt());
			feeDueDetailVO
					.setTotalFeeAmt(feeDueDetList.get(0).getTotalFeeAmt());
			httpSession.setAttribute("feeDueDet",
					feeDueDetailVO);
			feeDueDetailVO
			.setFeePaidAmt(feeDueDetList.get(0).getFeePaidAmt());
			feeDueDetailVO.setLastYearPayment(feeDueDetList.get(0).getLastYearPayment());
			feeDueDetailVO.setMonthlyFeeDueAmt(feeDueDetList.get(0).getMonthlyFeeDueAmt());
			System.out.println("session scope "+feeDueDetailVO.toString());
		}
		
		
		
		String isPartialPaymentAllowed = propertyManagementUtil
				.getPropertyValue(applicationCache, sessionCache
						.getUserSessionDetails().getInstId(), sessionCache
						.getUserSessionDetails().getBranchId(),
						PropertyManagementConstant.PARTIAL_PAYMENT_ALLOWED);
		
		feePaymentMasterVO.getFeePaymentVO().setIsPartialPaymentAllowed(
				isPartialPaymentAllowed);
		System.out.println("Fee payment master vo :"
				+ feePaymentMasterVO.getFeePaymentVO().toString());
		String getAttr = httpRequest.getParameter("Get");
		if (getAttr.equals("Get")) {
			WebUtils.setSessionAttribute(httpRequest, "success", null);
			httpSession.setAttribute("path", null);
		}
		redirectAttributes.addFlashAttribute("feePayment", feePaymentMasterVO);
		return "redirect:/feePayment.htm?data";

	}

	@RequestMapping(value = "/feePayment", method = RequestMethod.GET, params = {
			"data", "!Get", "!Save", "!Add", "!update", "!delete" })
	public ModelAndView stdlistData(
			@ModelAttribute("feePayment") FeePaymentMasterVO FeePaymentMasterVO,
			HttpServletRequest HttpServletRequest, HttpSession session,
			ModelMap model) throws NoDataFoundException,
			ErrorDescriptionNotFoundException {

		logger.info("Fee Master page submitted");
		System.out.println("Fee master searchvo added");
		ModelAndView mav = new ModelAndView(".jaw.feePayment", "feePayment",
				FeePaymentMasterVO);
		System.out.println("Before Data :"
				+ FeePaymentMasterVO.getFeePaymentVO().toString());
		HttpServletRequest.setAttribute("page", mav);

		return mav;

	}

	@RequestMapping(value = "/feePayment", method = RequestMethod.GET, params = { "Save" })
	public String FeePaymentSave(
			@ModelAttribute("feePayment") FeePaymentMasterVO FeePaymentMasterVO,
			ModelMap model, HttpServletRequest httpServletRequest,
			HttpSession httpSession, RedirectAttributes redirectAttributes,
			HttpServletResponse httpServletResponse)
			throws NoDataFoundException, DuplicateEntryException,
			DatabaseException, UpdateFailedException, IOException,
			FeeMasterNotFoundException, StudentNotFoundException,
			FeeMasterExistException, PropertyNotFoundException {
		SessionCache sessionCache = (SessionCache) httpSession
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		ApplicationCache applicationCache = (ApplicationCache) httpSession
				.getServletContext().getAttribute(
						ApplicationConstant.APPLICATION_CACHE);
		FeePaymentSearchVO feePaymentSearchVO = (FeePaymentSearchVO) WebUtils
				.getSessionAttribute(httpServletRequest, "searchVo1");
		System.out.println("FeePaymentSearchVO "+feePaymentSearchVO);
		FeePaymentMasterVO.setFeePaymentSearchVO(feePaymentSearchVO);
		String feeSelect=httpServletRequest.getParameter("fee");
		System.out.println("Testttttttttttt"+feeSelect);
		FeePaymentMasterVO.getFeePaymentVO().setSelectFee(feeSelect);
		feePaymentService.insertFeePaymentRec(FeePaymentMasterVO,
				sessionCache.getUserSessionDetails(), sessionCache,
				applicationCache);
		redirectAttributes.addFlashAttribute("feePayment", FeePaymentMasterVO);
		WebUtils.setSessionAttribute(httpServletRequest, "success",
				ErrorCodeConstant.FEE_ACCEPTED);
		System.out.println("Is receipt required :"
				+ FeePaymentMasterVO.getFeePaymentVO().getIsReceiptRequired());
		ModelAndView mav = new ModelAndView(".jaw.feePayment", "feePayment",
				FeePaymentMasterVO);
		System.out.println("Before Data :"
				+ FeePaymentMasterVO.getFeePaymentVO().toString());
		
		if (FeePaymentMasterVO.getFeePaymentVO().getIsReceiptRequired() != null) {
			String path = "";
			if (FeePaymentMasterVO.getFeePaymentVO().getReceiptCategory()
					.equals(CommonCodeConstant.RECEIPT_CATGRY_BRANCH)) {
				path = getBranchReceipt(FeePaymentMasterVO,
						httpServletResponse, httpServletRequest);
			} else if (FeePaymentMasterVO.getFeePaymentVO()
					.getReceiptCategory()
					.equals(CommonCodeConstant.RECEIPT_CATGRY_TRUST)) {
				path = getTrustReceipt(FeePaymentMasterVO, httpServletResponse,
						httpServletRequest);
			}

			System.out.println("Path is :" + path);

			httpSession.setAttribute("path", path);
			FeePaymentMasterVO.getFeePaymentSearchVO().setPath(path);
		}
		redirectAttributes.addFlashAttribute("feePayment", FeePaymentMasterVO);
		return "redirect:/feePayment.htm?Get";

	}

	public String getBranchReceipt(FeePaymentMasterVO feePaymentMasterVO,

	HttpServletResponse response, HttpServletRequest httpServletRequest)
			throws IOException {
		logger.debug("Received request to download PDF report");
		String input = httpServletRequest.getSession().getServletContext()
				.getRealPath("//reports//ReceiptGenerate.jrxml");

		JasperReport jreport1 = null;

		try {

			System.out.println("In jasper datasource :"
					+ feePaymentMasterVO.getFeePaymentLists().size());
			System.out.println("Fee receipt vo :"
					+ feePaymentMasterVO.getFeeReceiptVO());
			JRDataSource datasource = new JRBeanCollectionDataSource(
					feePaymentMasterVO.getFeePaymentLists());
			Map<String, Object> parameterMap = new HashMap<String, Object>();
			parameterMap.put("datasource", datasource);

			parameterMap.put("admisNo", feePaymentMasterVO
					.getFeePaymentSearchVO().getStudentAdmisNo());
			parameterMap.put("name", feePaymentMasterVO.getStudentMaster().getStudentName());
			parameterMap.put("stGroup", feePaymentMasterVO
					.getStudentMaster().getStuGrpId());

			parameterMap.put("instrumentNo", feePaymentMasterVO
					.getFeeReceiptVO().getInstrumentNo());
			parameterMap.put("feePaid", feePaymentMasterVO.getFeeReceiptVO()
					.getFeePaidAmt());
			parameterMap.put("date", feePaymentMasterVO.getFeeReceiptVO()
					.getFeeReceiptDate());
			parameterMap.put("srlNo", feePaymentMasterVO.getFeeReceiptVO()
					.getFeeReceiptNo());
			parameterMap.put("total", feePaymentMasterVO.getFeeReceiptVO()
					.getTotalPaid());
			parameterMap.put("fineAmount", feePaymentMasterVO.getFeeReceiptVO()
					.getFineAmt());

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
			JRExporter exporter = new JRPdfExporter();
			jreport1 = JasperCompileManager.compileReport(input);
			System.out.println("Datasource :" + datasource);
			JasperPrint jprint1 = JasperFillManager.fillReport(jreport1,
					parameterMap, datasource);
			exporter.setParameter(JRPdfExporterParameter.JASPER_PRINT, jprint1);

			String outputPdf = httpServletRequest
					.getSession()
					.getServletContext()
					.getRealPath(
							"//genReports//Receipt-"
									+ sessionCache.getUserSessionDetails()
											.getUserId() + ".pdf");

			OutputStream output = new FileOutputStream(new File(outputPdf));
			exporter.setParameter(JRPdfExporterParameter.OUTPUT_STREAM, output);

			exporter.exportReport();
			System.out.println("going to export in path :" + outputPdf);
			output.close();
			output.flush();
			return outputPdf;
		} catch (JRException e) {

			StringWriter stringWriter = new StringWriter();
			PrintWriter printWriter = new PrintWriter(stringWriter);
			e.printStackTrace(printWriter);
			response.setContentType("text/plain");
			response.getOutputStream().print(stringWriter.toString());
		}
		return null;

	}

	public String getTrustReceipt(FeePaymentMasterVO feePaymentMasterVO,

	HttpServletResponse response, HttpServletRequest httpServletRequest)
			throws IOException {
		logger.debug("Received request to download PDF report");
		String input = httpServletRequest.getSession().getServletContext()
				.getRealPath("//reports//TrustReceiptGenerate.jrxml");

		JasperReport jreport1 = null;

		try {

			Map<String, Object> parameterMap = new HashMap<String, Object>();

			parameterMap.put("admisNo", feePaymentMasterVO
					.getFeePaymentSearchVO().getStudentAdmisNo());
			parameterMap.put("name", "Akshaya");
			parameterMap.put("stGroup", feePaymentMasterVO
					.getFeePaymentSearchVO().getStGroup());

			parameterMap.put("instrumentNo", feePaymentMasterVO
					.getFeePaymentVO().getInstrumentNo());
			parameterMap.put("feePaid", feePaymentMasterVO.getFeePaymentVO()
					.getFeePaidAmt());
			parameterMap.put("date", feePaymentMasterVO.getFeePaymentVO()
					.getFeeReceiptDate());
			parameterMap.put("srlNo", feePaymentMasterVO.getFeePaymentVO()
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

			parameterMap.put("logo", path);
			JRExporter exporter = new JRPdfExporter();
			jreport1 = JasperCompileManager.compileReport(input);

			JasperPrint jprint1 = JasperFillManager.fillReport(jreport1,
					parameterMap, new JREmptyDataSource());
			exporter.setParameter(JRPdfExporterParameter.JASPER_PRINT, jprint1);

			String outputPdf = httpServletRequest
					.getSession()
					.getServletContext()
					.getRealPath(
							"//genReports//TrustReceipt-"
									+ sessionCache.getUserSessionDetails()
											.getUserId() + ".pdf");

			OutputStream output = new FileOutputStream(new File(outputPdf));
			exporter.setParameter(JRPdfExporterParameter.OUTPUT_STREAM, output);

			exporter.exportReport();
			System.out.println("going to export in path :" + outputPdf);
			output.close();
			output.flush();
			return outputPdf;
		} catch (JRException e) {

			StringWriter stringWriter = new StringWriter();
			PrintWriter printWriter = new PrintWriter(stringWriter);
			e.printStackTrace(printWriter);
			response.setContentType("text/plain");
			response.getOutputStream().print(stringWriter.toString());
		}
		return null;

	}

	@RequestMapping(value = "/feePayment", method = RequestMethod.GET, params = { "View" })
	public void doSalesMultiReport(HttpSession httpSession,
			HttpServletResponse response) throws IOException {
		logger.debug("Received request to download PDF report");

		ServletOutputStream servletOutputStream = response.getOutputStream();
		FileInputStream fileInputStream = null;
		String path = (String) httpSession.getAttribute("path");

		File file = new File(path);
		byte[] bFile = new byte[(int) file.length()];

		try {
			// convert file into array of bytes
			fileInputStream = new FileInputStream(file);
			fileInputStream.read(bFile);
			fileInputStream.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		response.setContentType("application/pdf");
		response.setContentLength(bFile.length);

		servletOutputStream.write(bFile, 0, bFile.length);
		

	}

	@ModelAttribute("feeCategory")
	public Map<String, String> gerCourseNameList(HttpSession session,
			HttpServletRequest httpSevletFeePayment,
			HttpServletResponse response, ModelMap model) throws IOException {
		Map<String, String> map = null;
		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		try {
			map = dropDownListService.selectFeeCategoryList(sessionCache
					.getUserSessionDetails());
		} catch (NoDataFoundException e) {

		}
		httpSevletFeePayment.setAttribute("feeCategory", map);
		return map;

	}

	@RequestMapping(value = "/feePayment", method = RequestMethod.GET, params = { "student" })
	public @ResponseBody
	Map<String, String> getStaffList(ModelMap model,
			HttpServletRequest httpServletRequest,
			HttpServletResponse response, HttpSession session)
			throws ErrorDescriptionNotFoundException, PropertyNotFoundException {

		logger.debug("inside Update course classes linking method");

		ApplicationCache applicationCache = (ApplicationCache) session
				.getServletContext().getAttribute(
						ApplicationConstant.APPLICATION_CACHE);

		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);

		String stGroup = httpServletRequest.getParameter("stGroup");
		String acTerm = httpServletRequest.getParameter("acTerm");
		String feeCategory = httpServletRequest.getParameter("feeCategory");
		Map<String, String> list = null;
		try {
			list = feePaymentService.getStudentAdmisNo(applicationCache,acTerm, stGroup,
					feeCategory, sessionCache.getUserSessionDetails());
		}

		catch (NoDataFoundException e) {

			try {
				response.setStatus(500);
				response.getWriter().write(
						e.getMessage()
								+ ":"
								+ errorCodeUtil.getErrorDescription(
										applicationCache, e.getMessage()));
			} catch (IOException e1) {

				e1.printStackTrace();
			}
		}

		return list;
	}

	@ExceptionHandler({ NoDataFoundException.class })
	public ModelAndView handleException1(Exception ex, HttpSession session,
			HttpServletRequest FeePayment) {
		ModelAndView modelAndView = (ModelAndView) FeePayment
				.getAttribute("page");
		modelAndView.getModelMap().addAttribute("error", ex.getMessage());
		WebUtils.setSessionAttribute(FeePayment, "display_tbl", null);
		return modelAndView;
	}

	@ExceptionHandler({ FeeMasterNotFoundException.class,
			StudentNotFoundException.class, 
			DuplicateEntryException.class, DatabaseException.class,
			UpdateFailedException.class, FeeMasterExistException.class,
			FeeMasterNotFoundForIntegratedCourseException.class })
	public ModelAndView handleException(Exception ex, HttpSession session,
			HttpServletRequest request) {
		ModelAndView mav = (ModelAndView) request.getAttribute("page");
		mav.getModelMap().addAttribute("error", ex.getMessage());
		WebUtils.setSessionAttribute(request, "success", null);
		return mav;

	}

}