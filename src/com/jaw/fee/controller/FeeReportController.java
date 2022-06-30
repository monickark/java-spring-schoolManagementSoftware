package com.jaw.fee.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.jasperreports.engine.JRDataSource;
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
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.WebUtils;

import com.jaw.common.constants.ApplicationConstant;
import com.jaw.common.constants.CommonCodeConstant;
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
import com.jaw.common.util.CommonCodeUtil;
import com.jaw.fee.service.IFeeReportService;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.sessCache.SessionCache;

@Controller
public class FeeReportController {
	Logger logger = Logger.getLogger(FeeReportController.class);
	@Autowired
	IDropDownListService dropDownListService;
	@Autowired
	IFeeReportService feeReportService;
	@Autowired
	CommonCodeUtil commonCodeUtil;

	@RequestMapping(value = "/feeReport", method = RequestMethod.GET)
	public ModelAndView stdlistGet(
			@ModelAttribute("feeReportMaster") FeeReportMasterVO FeeReportMasterVO,
			HttpServletRequest httpServletRequest, HttpSession session,
			ModelMap model) throws NoDataFoundException,
			ErrorDescriptionNotFoundException {
		ModelAndView mav = null;

		
			logger.info("Fee Details render page");
			// Null the session display tag values

			mav = new ModelAndView(ModelAndViewConstant.FEE_REPORT_LIST,
					"feeReportMaster", FeeReportMasterVO);
			httpServletRequest.setAttribute("page", mav);
			WebUtils.setSessionAttribute(httpServletRequest, "display_tbl",
					null);
			WebUtils.setSessionAttribute(httpServletRequest, "success", null);
			WebUtils.setSessionAttribute(httpServletRequest, "searchVo1", null);
			WebUtils.setSessionAttribute(httpServletRequest, "searchVo", null);

	
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
	@ModelAttribute("userlist")
	public Map<String, String> gerCourseNameList(HttpSession session,
			HttpServletRequest httpSevletRequest, HttpServletResponse response,
			ModelMap model) throws IOException {
		Map<String, String> map = null;
		
		try {
			map = dropDownListService.selectAdminUserList();
		} catch (NoDataFoundException e) {

		}
		httpSevletRequest.setAttribute("menuprofile", map);
		return map;

	}
	@RequestMapping(value = "/feeReport", method = RequestMethod.GET, params = { "Get" })
	public String viewMarkMasterList(
			@ModelAttribute("feeReportMaster") FeeReportMasterVO FeeReportMasterVO,
			HttpSession session, HttpServletRequest httpRequest,
			RedirectAttributes redirectAttributes) throws NoDataFoundException {
		// Getting the display tag parameter
		String redirect = "";

		Map stockParamMap = WebUtils.getParametersStartingWith(httpRequest,
				"d-");
		// Setting model and view for exception handler
			//from Dashboard fee collections
			String page=httpRequest.getParameter("page");
			if((page!=null)&&(page.equals("dash"))){
			String stDate= "2016-02-03";
			String endDate= "2016-03-03";
			System.out.println("From Dashboard:"+stDate);
			System.out.println("From Dashboard:"+endDate);
			FeeReportSearchVO feeReportSearchVO=new FeeReportSearchVO();
			feeReportSearchVO.setFromDate(stDate);
			feeReportSearchVO.setToDate(endDate);
			feeReportSearchVO.setAcademicTerm("AT3");
			feeReportSearchVO.setReportType(CommonCodeConstant.FEE_COLLECTION_DETAILS);
			System.out.println("get Date "+stDate +" to Date"+endDate);
			FeeReportMasterVO.setFeeReportSearchVO(feeReportSearchVO);
			}
			ModelAndView modelAndView = new ModelAndView(
					ModelAndViewConstant.FEE_REPORT_LIST, "feeReportMaster",
					FeeReportMasterVO);
			httpRequest.setAttribute("page", modelAndView);
			httpRequest.setAttribute("errors", "error");
	
			SessionCache sessionCache = (SessionCache) session
					.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
			WebUtils.setSessionAttribute(httpRequest, "searchVo1",
					FeeReportMasterVO.getFeeReportSearchVO());
			if (stockParamMap.size() == 0) {

				logger.info("Table operation has been triggered");

				if(FeeReportMasterVO.getFeeReportSearchVO().getReportType().equals(CommonCodeConstant.FEE_COLLECTION_DETAILS)){
				feeReportService.selectFeePaymentListReport(sessionCache, FeeReportMasterVO);
				System.out.println("display_tbl list:"+FeeReportMasterVO.getFeeReportList());
				WebUtils.setSessionAttribute(httpRequest, "display_tbl",
						FeeReportMasterVO.getFeeReportList());
				WebUtils.setSessionAttribute(httpRequest, "display_tbl1",
						null);
					
				
				} else {
					System.out.println("display_tbl11111111111111 list:"+FeeReportMasterVO.getFeeReportList());
					feeReportService.selectFeeStatusReport(sessionCache, FeeReportMasterVO);
					WebUtils.setSessionAttribute(httpRequest, "display_tbl1",
							FeeReportMasterVO.getFeeReportList());
					WebUtils.setSessionAttribute(httpRequest, "display_tbl",
							null);
				}
				
			}
			redirectAttributes.addFlashAttribute("feeReportMaster",
					FeeReportMasterVO);
			redirect = "redirect:/feeReport.htm?data";
		
		return redirect;

	}
	@RequestMapping(value = "/feeReport", method = RequestMethod.GET, params = { "feePaymentListPrint","data" })
	
		public void printdata(
				@ModelAttribute("feeReportMaster") FeeReportMasterVO FeeReportMasterVO,
				HttpSession session, HttpServletRequest request, HttpServletResponse response, 
				RedirectAttributes redirectAttributes) throws  IOException {
				logger.debug("Received request to download PDF report");
				String input = request.getSession().getServletContext()
						.getRealPath("//reports//fee//FeePaymentList.jrxml");

				JasperReport jreport1 = null;

				try {

					System.out.println("In jasper datasource :");
					List<FeeReportListVO> feeReportList = (List<FeeReportListVO>) WebUtils
							.getSessionAttribute(request, "display_tbl");
					
					FeeReportSearchVO FeeReportSearchVO = (FeeReportSearchVO) WebUtils
							.getSessionAttribute(request, "searchVo1");
					System.out.println("Datasource size:"+ feeReportList.size());
					ApplicationCache applicationCache = (ApplicationCache) session
							.getServletContext().getAttribute(
									ApplicationConstant.APPLICATION_CACHE);
					
					JRDataSource datasource = new JRBeanCollectionDataSource(
							feeReportList);
					Map<String, Object> parameterMap = new HashMap<String, Object>();
					parameterMap.put("datasource", datasource);

					System.out.println("Parameters output:" + FeeReportSearchVO.toString());
					String stgroup=commonCodeUtil.getDescriptionByCode(applicationCache, FeeReportSearchVO.getStudentGroupId());
					String term=commonCodeUtil.getDescriptionByCode(applicationCache, FeeReportSearchVO.getAcademicTerm());
					
					parameterMap.put("acTerm", term);
					parameterMap.put("stGroup", stgroup);
					parameterMap.put("fromDate", FeeReportSearchVO.getFromDate());
					parameterMap.put("toDate", FeeReportSearchVO.getToDate());
					SessionCache sessionCache = (SessionCache) request
							.getSession().getAttribute(
									ApplicationConstant.SESSION_CACHE_KEY);
					String path = request
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

					String outputPdf = request
							.getSession()
							.getServletContext()
							.getRealPath(
									"//reports//fee//FeePaymentList.pdf");

					OutputStream output = new FileOutputStream(new File(outputPdf));
					exporter.setParameter(JRPdfExporterParameter.OUTPUT_STREAM, output);

					exporter.exportReport();
					System.out.println("going to export in path :" + outputPdf);
					//////////////////////
					
					InputStream file1 = new FileInputStream(new File(outputPdf));

					response.setContentType("application/pdf");
					String name = "FeePaymentList.pdf";
					response.setHeader("Content-disposition", "attachment; filename="
							+ name);
					
					ServletOutputStream out = response.getOutputStream();
					byte[] buffer = new byte[1024]; /* or whatever size */

					int read = file1.read(buffer);
					while (read >= 0) {

						if (read > 0)
							out.write(buffer, 0, read);
						read = file1.read(buffer);
					}

					// baos.writeTo(out);
					System.out.println("gng to close strem...");
					////////////////////
					
					out.close();
					out.flush();
					output.close();
					output.flush();
					file1.close();
					
				} catch (JRException e) {

					StringWriter stringWriter = new StringWriter();
					PrintWriter printWriter = new PrintWriter(stringWriter);
					e.printStackTrace(printWriter);
					response.setContentType("text/plain");
					response.getOutputStream().print(stringWriter.toString());
				}
			
			
			}

	@RequestMapping(value = "/feeReport", method = RequestMethod.GET, params = "data")
	public ModelAndView staffListBack(
			@ModelAttribute("feeReportMaster") FeeReportMasterVO FeeReportMasterVO,
			HttpServletRequest httpServletRequest) {
		FeeReportSearchVO FeeReportSearchVO = (FeeReportSearchVO) WebUtils
				.getSessionAttribute(httpServletRequest, "searchVo1");
		FeeReportMasterVO.setFeeReportSearchVO(FeeReportSearchVO);
		ModelAndView modelAndView = new ModelAndView(
				ModelAndViewConstant.FEE_REPORT_LIST, "feeReportMaster",
				FeeReportMasterVO);

		return modelAndView;
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
