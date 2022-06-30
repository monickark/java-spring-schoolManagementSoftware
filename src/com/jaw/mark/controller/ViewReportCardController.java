package com.jaw.mark.controller;

import java.io.IOException;
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
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

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
import com.jaw.common.constants.ModelAndViewConstant;
import com.jaw.common.constants.PropertyManagementConstant;
import com.jaw.common.dropdown.service.IDropDownListService;
import com.jaw.common.exceptions.AllMarksNotEnteredException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.util.PropertyNotFoundException;
import com.jaw.common.util.CommonCodeUtil;
import com.jaw.common.util.PropertyManagementUtil;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.sessCache.SessionCache;
import com.jaw.framework.sessCache.UserSessionDetails;
import com.jaw.mark.service.IMarkMasterService;
import com.jaw.mark.service.IReportCardRemarksService;
import com.jaw.mark.service.IViewReportCardService;

//Mark Master Controller Class
@Controller
public class ViewReportCardController {
	// Logging
	Logger logger = Logger.getLogger(ViewReportCardController.class);
	@Autowired
	IMarkMasterService markMasterService;
	@Autowired
	IDropDownListService dropDownListService;
	@Autowired
	IReportCardRemarksService reportCardRemarksService;
	@Autowired
	PropertyManagementUtil propertyManagementUtil;
	@Autowired
	IViewReportCardService viewReportCardService;
	@Autowired
	CommonCodeUtil commonCodeUtil;

	@RequestMapping(value = "/viewReportCard", method = RequestMethod.GET, params = { "rcard=Get" })
	public String reportCardGet(
			@ModelAttribute("markMaster") ReportCardMasterVo ReportCardMasterVo,
			ModelMap model, HttpServletRequest httpServletRequest,
			HttpSession session, RedirectAttributes redirectAttribute)
			throws NoDataFoundException, AllMarksNotEnteredException,
			DuplicateEntryException {
		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		MarkMasterSearchVO markMasterSearchVO = (MarkMasterSearchVO) WebUtils
				.getSessionAttribute(httpServletRequest, "searchVo");
		List<MarkMasterVO> markMasterVOs = (List<MarkMasterVO>) WebUtils
				.getSessionAttribute(httpServletRequest, "display_tbl");
		String id = httpServletRequest.getParameter("rowId");
		System.out.println("Row In from jsp:" + id);
		ReportCardMasterVo.setMarkMasterVO(markMasterVOs.get(Integer
				.parseInt(id)));
		WebUtils.setSessionAttribute(httpServletRequest, "sessObj",
				markMasterVOs.get(Integer.parseInt(id)));
		ReportCardMasterVo.setMarkMasterSearchVo(markMasterSearchVO);
		System.out.println("search vo in controller :"
				+ markMasterSearchVO.toString());
		viewReportCardService.getStudentAdmisNo(ReportCardMasterVo,
				sessionCache.getUserSessionDetails(), "common");
		WebUtils.setSessionAttribute(httpServletRequest, "studentAdmisNoList",
				ReportCardMasterVo.getStudentAdmisNoMap());

		WebUtils.setSessionAttribute(httpServletRequest, "display_tbl3", null);
		WebUtils.setSessionAttribute(httpServletRequest, "display_tbl2", null);
		redirectAttribute.addFlashAttribute("markMaster", ReportCardMasterVo);
		return "redirect:/viewReportCard.htm?data";

	}

	@RequestMapping(value = "/viewReportCard", method = RequestMethod.GET)
	public ModelAndView reportCardView(
			@ModelAttribute("markMaster") ReportCardMasterVo ReportCardMasterVo,
			ModelMap model, HttpServletRequest httpServletRequest,
			HttpSession session, RedirectAttributes redirectAttribute,
			HttpServletResponse response) throws NoDataFoundException,
			AllMarksNotEnteredException, DuplicateEntryException, IOException,
			PropertyNotFoundException {

		logger.debug("inside Get Mark Master Details from List method");
		ApplicationCache applicationCache = (ApplicationCache) session
				.getServletContext().getAttribute(
						ApplicationConstant.APPLICATION_CACHE);
		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		Map stockParamMap = WebUtils.getParametersStartingWith(
				httpServletRequest, "d-");
		MarkMasterSearchVO markMasterSearchVO = (MarkMasterSearchVO) WebUtils
				.getSessionAttribute(httpServletRequest, "searchVo");
		Map<String, String> stuAdmisNoList = (Map<String, String>) WebUtils
				.getSessionAttribute(httpServletRequest, "studentAdmisNoList");
		MarkMasterVO markMasterVO = (MarkMasterVO) WebUtils
				.getSessionAttribute(httpServletRequest, "sessObj");
		ReportCardMasterVo.setMarkMasterVO(markMasterVO);
		if (sessionCache.getUserSessionDetails().getProfileGroup()
				.equals(ApplicationConstant.PG_STUDENT)) {
			markMasterSearchVO = new MarkMasterSearchVO();
			markMasterSearchVO.setStudentAdmisNo(sessionCache
					.getUserSessionDetails().getLinkId());
		} else if (sessionCache.getUserSessionDetails().getProfileGroup()
				.equals(ApplicationConstant.PG_PARENT)) {
			markMasterSearchVO = new MarkMasterSearchVO();
			markMasterSearchVO.setStudentAdmisNo(sessionCache
					.getParentSession()
					.getStudentSession()
					.get(Integer.parseInt(sessionCache.getParentSession()
							.getSelectedStuIndex())).getStudentAdmisNo());

		} else {
			System.out.println("else : "
					+ ReportCardMasterVo.getMarkMasterSearchVo()
							.getStudentAdmisNo());

			markMasterSearchVO.setStudentAdmisNo(ReportCardMasterVo
					.getMarkMasterSearchVo().getStudentAdmisNo());
		}

		ModelAndView modelAndView = new ModelAndView(
				ModelAndViewConstant.REPORT_CARD, "markMaster",
				ReportCardMasterVo);
		httpServletRequest.setAttribute("page", modelAndView);
		ReportCardMasterVo.setMarkMasterSearchVo(markMasterSearchVO);

		if (stockParamMap.size() == 0) {

			logger.info("Table operation has been triggered");

			// Get list from database
			viewReportCardService.viewRepordCard(ReportCardMasterVo,
					sessionCache.getUserSessionDetails(),
					sessionCache.getStudentSession(),
					sessionCache.getParentSession());
			// Set the list to session to access in jsp

			System.out.println("list added :"
					+ ReportCardMasterVo.getStudentReportCardVOs().size());
			WebUtils.setSessionAttribute(httpServletRequest, "display_tbl3",
					ReportCardMasterVo.getStudentReportCardVOs());
			WebUtils.setSessionAttribute(httpServletRequest, "display_tbl2",
					ReportCardMasterVo.getConsolidatedreportCard());

			ReportCardMasterVo.setPageSize(ReportCardMasterVo.getPageSize());

		}
		ReportCardMasterVo.setStudentAdmisNoMap(stuAdmisNoList);
		redirectAttribute.addFlashAttribute("markMaster", ReportCardMasterVo);
		
		
		System.out.println("consolidated report list size :"
				+ ReportCardMasterVo.getConsolidatedreportCard().size());
		System.out.println("Student report list size :"
				+ ReportCardMasterVo.getStudentReportCardVOs().size());
		
		String value = propertyManagementUtil.getPropertyValue(
				applicationCache, sessionCache.getUserSessionDetails()
						.getInstId(), sessionCache.getUserSessionDetails()
						.getInstId(), PropertyManagementConstant.PRINT_ALLOWED);

		modelAndView.getModelMap().addAttribute("print", value);
		WebUtils.setSessionAttribute(httpServletRequest, "markMaster",
				ReportCardMasterVo);
		System.out.println("for size inside get:"
				+ ReportCardMasterVo.getStudentReportCardVOsList().size());
		
		return modelAndView;

	}

	@RequestMapping(value = "/viewReportCard", method = RequestMethod.GET, params = "Print")
	public void reportCardPrint(
			@ModelAttribute("markMaster") ReportCardMasterVo reportCardMasterVo,
			ModelMap model, HttpServletRequest httpServletRequest,
			HttpSession session, RedirectAttributes redirectAttribute,
			HttpServletResponse response) throws NoDataFoundException,
			AllMarksNotEnteredException, DuplicateEntryException, IOException {

		logger.debug("inside Get Mark Master Details from List method");

		reportCardMasterVo = (ReportCardMasterVo) WebUtils.getSessionAttribute(
				httpServletRequest, "markMaster");
		System.out.println("for size inside print:"
				+ reportCardMasterVo.getStudentReportCardVOsList().size());
		ApplicationCache applicationCache = (ApplicationCache) session
				.getServletContext().getAttribute(
						ApplicationConstant.APPLICATION_CACHE);
		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		doSalesMultiReport(reportCardMasterVo, applicationCache,
				sessionCache.getUserSessionDetails(), response,
				httpServletRequest);

	}

	public void doSalesMultiReport(ReportCardMasterVo reportCardMasterVo,
			ApplicationCache applicationCache,
			UserSessionDetails userSessionDetails,
			HttpServletResponse response, HttpServletRequest httpServletRequest)
			throws IOException {
		logger.debug("Received request to download PDF report");

		String input = httpServletRequest.getSession().getServletContext()
				.getRealPath("//reports//ReportCardGenerate.jrxml");
		System.out.println("for size :"
				+ reportCardMasterVo.getStudentReportCardVOs().size());

		JasperReport jreport1 = null;
		try {
			ServletOutputStream servletOutputStream = response
					.getOutputStream();
			byte[] bytes = null;

			JRDataSource datasource = new JRBeanCollectionDataSource(
					reportCardMasterVo.getStudentReportCardVOs());
			Map<String, Object> parameterMap = new HashMap<String, Object>();
			System.out.println("Data source :"+datasource);
			System.out.println("Data source :"+reportCardMasterVo.getStudentReportCardVOs().size());
			parameterMap.put("datasource", datasource);
			parameterMap.put("table",
					reportCardMasterVo.getStudentReportCardVOs());
			parameterMap.put("admisNo", reportCardMasterVo.getStudentMasterVO()
					.getStudentAdmisNo());
			parameterMap.put("name", reportCardMasterVo.getStudentMasterVO()
					.getStudentName());
			parameterMap.put("stGroup", reportCardMasterVo.getStudentMasterVO()
					.getStuGrpId());
			System.out.println("exam id :"
					+ reportCardMasterVo.getMarkMasterVO().getExamId());
			String examName = CommonCodeUtil.getDescriptionByTypeAndCode(
					applicationCache, CommonCodeConstant.EXAMID,
					reportCardMasterVo.getMarkMasterVO().getExamId(),
					userSessionDetails.getInstId(),
					userSessionDetails.getBranchId());
			System.out.println("exam Name :" + examName);
			parameterMap.put("exam", examName);
			System.out.println("servlet context path :"
					+ httpServletRequest.getSession().getServletContext());
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
	}

	@RequestMapping(value = "/viewReportCard", method = RequestMethod.GET, params = {
			"data", "!rcard", "!Get" })
	public ModelAndView reportCardData(
			@ModelAttribute("markMaster") ReportCardMasterVo ReportCardMasterVo,
			HttpServletRequest httpServletRequest) {
		ModelAndView modelAndView = new ModelAndView(
				ModelAndViewConstant.REPORT_CARD, "markMaster",
				ReportCardMasterVo);
		httpServletRequest.setAttribute("page", modelAndView);
		Map<String, String> stuAdmisNoList = (Map<String, String>) WebUtils
				.getSessionAttribute(httpServletRequest, "studentAdmisNoList");
		List<StudentReportCardVO> studentReportCardVOs = (List<StudentReportCardVO>) WebUtils
				.getSessionAttribute(httpServletRequest, "display_tbl3");
		List<StudentReportCardVO> studentReportCardVOs2 = (List<StudentReportCardVO>) WebUtils
				.getSessionAttribute(httpServletRequest, "display_tbl2");
		ReportCardMasterVo.setStudentReportCardVOs(studentReportCardVOs);
		ReportCardMasterVo.setConsolidatedreportCard(studentReportCardVOs2);
		ReportCardMasterVo.setStudentAdmisNoMap(stuAdmisNoList);
		return modelAndView;
	}

	// Get Values from list for popup
	@RequestMapping(value = "/reportCardList", method = RequestMethod.POST, params = { "Back" })
	public ModelAndView backReportCardRemarks(
			@ModelAttribute("markMaster") ReportCardMasterVo ReportCardMasterVo,
			ModelMap model, HttpServletRequest httpServletRequest,
			HttpSession session, RedirectAttributes redirectAttribute)
			throws NoDataFoundException, AllMarksNotEnteredException {

		logger.debug("inside Get Mark Master Details from List method");

		MarkMasterSearchVO markMasterSearchVO = (MarkMasterSearchVO) WebUtils
				.getSessionAttribute(httpServletRequest, "searchVo");
		ReportCardMasterVo.setMarkMasterSearchVo(markMasterSearchVO);
		ModelAndView modelAndView = new ModelAndView(
				ModelAndViewConstant.REPORT_CARD_REMARK, "markMaster",
				ReportCardMasterVo);
		httpServletRequest.setAttribute("page", modelAndView);
		return modelAndView;
	}

	@ExceptionHandler({ NoDataFoundException.class })
	public ModelAndView handleException1(Exception ex, HttpSession session,
			HttpServletRequest request) {
		ModelAndView modelAndView = (ModelAndView) request.getAttribute("page");
		modelAndView.getModelMap().addAttribute("error", ex.getMessage());
		WebUtils.setSessionAttribute(request, "display_tbl", null);
		WebUtils.setSessionAttribute(request, "success", null);
		return modelAndView;
	}
}
