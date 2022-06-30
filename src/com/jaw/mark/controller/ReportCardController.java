package com.jaw.mark.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Date;
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
import com.jaw.common.constants.ErrorCodeConstant;
import com.jaw.common.constants.ModelAndViewConstant;
import com.jaw.common.dropdown.service.IDropDownListService;
import com.jaw.common.exceptions.DatabaseException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.FileNotFoundInDatabase;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.UpdateFailedException;
import com.jaw.common.exceptions.util.TableNotSpecifiedForAuditException;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.sessCache.SessionCache;
import com.jaw.mark.service.IMarkMasterService;
import com.jaw.mark.service.IReportCardRemarksService;
import com.jaw.mark.service.IViewReportCardService;
import com.jaw.student.admission.controller.StudentMasterVO;

//Mark Master Controller Class
@Controller
public class ReportCardController {
	// Logging
	Logger logger = Logger.getLogger(ReportCardController.class);
	@Autowired
	IMarkMasterService markMasterService;
	@Autowired
	IDropDownListService dropDownListService;
	@Autowired
	IReportCardRemarksService reportCardRemarksService;
	@Autowired
	IViewReportCardService viewReportCardService;

	// Academic Calendar List Get method

	@RequestMapping(value = "/reportCardList", method = RequestMethod.GET)
	public ModelAndView markMaster(
			@ModelAttribute("markMaster") ReportCardMasterVo ReportCardMasterVo,
			ModelMap model, HttpServletRequest httpRequest,
			HttpSession httpSession, ModelAndView modelAndView) {

		logger.info("Rendering Mark Master List page");
		// Null the session display tag values
		WebUtils.setSessionAttribute(httpRequest, "admisno", null);
		WebUtils.setSessionAttribute(httpRequest, "display_tbl", null);
		WebUtils.setSessionAttribute(httpRequest, "display_tbl1", null);
		WebUtils.setSessionAttribute(httpRequest, "success", null);
		return new ModelAndView(ModelAndViewConstant.REPORT_CARD_REMARK);

	}

	@RequestMapping(value = "/reportCardList", method = RequestMethod.GET, params = { "Get" })
	public String viewMarkMasterList(
			@ModelAttribute("markMaster") ReportCardMasterVo ReportCardMasterVo,
			HttpServletRequest httpRequest, HttpSession session,
			RedirectAttributes redirectAttributes) throws NoDataFoundException {
		// Getting the display tag parameter

		Map stockParamMap = WebUtils.getParametersStartingWith(httpRequest,
				"d-");
		WebUtils.setSessionAttribute(httpRequest, "display_tbl",
				ReportCardMasterVo.getMarkMasterVOs());
		WebUtils.setSessionAttribute(httpRequest, "searchVo",
				ReportCardMasterVo.getMarkMasterSearchVo());
		// Setting model and view for exception handler
		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		ModelAndView modelAndView = new ModelAndView(
				ModelAndViewConstant.REPORT_CARD_REMARK, "markMaster",
				ReportCardMasterVo);
		httpRequest.setAttribute("page", modelAndView);

		if (stockParamMap.size() == 0) {

			logger.info("Table operation has been triggered");
			MarkMtrMasterVO markMasterVO = new MarkMtrMasterVO();

			// Get list from database
			markMasterService.selectmarkMasterList(markMasterVO,
					ReportCardMasterVo, sessionCache.getUserSessionDetails());

			// Set the list to session to access in jsp
			ReportCardMasterVo
					.setMarkMasterVOs(markMasterVO.getMarkMasterVOs());
			WebUtils.setSessionAttribute(httpRequest, "display_tbl",
					ReportCardMasterVo.getMarkMasterVOs());

			ReportCardMasterVo.setPageSize(ReportCardMasterVo.getPageSize());

		}
		WebUtils.getSessionAttribute(httpRequest, "searchVo");
		
		redirectAttributes.addFlashAttribute("markMaster", ReportCardMasterVo);
		return "redirect:/reportCardList.htm?data";

	}

	
	@RequestMapping(value = "/reportCardList", method = RequestMethod.GET, params = {
			"data", "!GetRemarks", "!addRemarks", "!Get" })
	public ModelAndView markMasterBack(
			@ModelAttribute("markMaster") ReportCardMasterVo ReportCardMasterVo,
			HttpServletRequest httpServletRequest) {
		ModelAndView modelAndView = new ModelAndView(
				ModelAndViewConstant.REPORT_CARD_REMARK, "markMaster",
				ReportCardMasterVo);
		return modelAndView;
	}

	@RequestMapping(value = "/rcMaintenance", method = RequestMethod.GET, params = { "process=Generate" })
	public String reportCardGenerate(
			@ModelAttribute("markMaster") ReportCardMasterVo ReportCardMasterVo,
			ModelMap model, HttpServletRequest httpServletRequest,
			HttpSession session, RedirectAttributes redirectAttribute,
			HttpServletResponse response) throws NoDataFoundException,
			UpdateFailedException, DuplicateEntryException, DatabaseException,
			TableNotSpecifiedForAuditException, IOException,
			FileNotFoundInDatabase

	{
		MarkMasterSearchVO markMasterSearchVO = (MarkMasterSearchVO) WebUtils
				.getSessionAttribute(httpServletRequest, "searchVo");
		List<MarkMasterVO> markMasterVOs = (List<MarkMasterVO>) WebUtils
				.getSessionAttribute(httpServletRequest, "display_tbl");
		ReportCardMasterVo.setMarkMasterSearchVo(markMasterSearchVO);
		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		ApplicationCache applicationCache = (ApplicationCache) session
				.getServletContext().getAttribute(
						ApplicationConstant.APPLICATION_CACHE);
		String id = httpServletRequest.getParameter("rowId");

		ModelAndView modelAndView = new ModelAndView(
				ModelAndViewConstant.REPORT_CARD_REMARK, "markMaster",
				ReportCardMasterVo);
		httpServletRequest.setAttribute("page", modelAndView);
		System.out.println("Row In from jsp:" + id);
		ReportCardMasterVo.setMarkMasterVO(markMasterVOs.get(Integer
				.parseInt(id)));
		System.out.println("mark master vo :"
				+ ReportCardMasterVo.getMarkMasterVO().toString());

		reportCardRemarksService.reportCardGeneration(ReportCardMasterVo,
				sessionCache.getUserSessionDetails(), applicationCache);

		redirectAttribute.addFlashAttribute("markMaster", ReportCardMasterVo);
		redirectAttribute.addFlashAttribute("success",
				ErrorCodeConstant.REPORT_CARD_GENERATED);
		doSalesMultiReport(ReportCardMasterVo, modelAndView,
				modelAndView.getModelMap(), response, httpServletRequest);
	redirectAttribute.addFlashAttribute("success",
				ErrorCodeConstant.REPORT_CARD_GENERATED);
		return "redirect:/reportCardList.htm?Get";
	}

	public void doSalesMultiReport(ReportCardMasterVo ReportCardMasterVo,
			ModelAndView modelAndView, ModelMap model,
			HttpServletResponse response, HttpServletRequest httpServletRequest)
			throws IOException {
		logger.debug("Received request to download PDF report");
		String input = httpServletRequest.getSession().getServletContext()
				.getRealPath("//reports//ReportCardGenerate.jrxml");

		System.out.println("for size :"
				+ ReportCardMasterVo.getStudentReportCardVOsList().size());
		List<JasperPrint> jprintlist = new ArrayList<JasperPrint>();
		JasperReport jreport1 = null;

		try {

			for (int i = 0; i < ReportCardMasterVo
					.getStudentReportCardVOsList().size(); i++) {
				JRDataSource datasource = new JRBeanCollectionDataSource(
						ReportCardMasterVo.getStudentReportCardVOsList().get(i));
				Map<String, Object> parameterMap = new HashMap<String, Object>();
				parameterMap.put("datasource", datasource);
				parameterMap.put("table",
						ReportCardMasterVo.getStudentReportCardVOs());
				StudentMasterVO studentMasterVO = ReportCardMasterVo
						.getStudentMasterVOs().get(i);
				parameterMap
						.put("admisNo", studentMasterVO.getStudentAdmisNo());
				parameterMap.put("name", studentMasterVO.getStudentName());
				parameterMap.put("stGroup", studentMasterVO.getStuGrpId());

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

				JasperPrint jprint1 = JasperFillManager.fillReport(jreport1,
						parameterMap, datasource);
				jprintlist.add(jprint1);
			}
			JRExporter exporter = new JRPdfExporter();
			exporter.setParameter(JRPdfExporterParameter.JASPER_PRINT_LIST,
					jprintlist);
			
			String outputPdf = httpServletRequest.getSession()
					.getServletContext()
					.getRealPath("//genReports//RC.pdf");
			
			OutputStream output = new FileOutputStream(new File(outputPdf));
			exporter.setParameter(JRPdfExporterParameter.OUTPUT_STREAM,
					output);
		
			exporter.exportReport();
			System.out.println("going to export in path :"+outputPdf);


			InputStream file = new FileInputStream(new File(outputPdf));

			response.setContentType("application/pdf");
			String name = "RC - "
					+ ReportCardMasterVo.getStudentMasterVOs().get(0)
							.getStuGrpId()
							+" - "+ new Date()+".pdf";
			response.setHeader("Content-disposition", "attachment; filename="
					+ name);
			
			ServletOutputStream out = response.getOutputStream();
			byte[] buffer = new byte[1024]; /* or whatever size */

			int read = file.read(buffer);
			while (read >= 0) {

				if (read > 0)
					out.write(buffer, 0, read);
				read = file.read(buffer);
			}

			// baos.writeTo(out);
			System.out.println("gng to close strem...");
			out.flush();
			out.close();
			output.flush();
			output.close();
			file.close();

		} catch (JRException e) {

			StringWriter stringWriter = new StringWriter();
			PrintWriter printWriter = new PrintWriter(stringWriter);
			e.printStackTrace(printWriter);
			response.setContentType("text/plain");
			response.getOutputStream().print(stringWriter.toString());
		}

		

	}

	@RequestMapping(value = "/rcMaintenance", method = RequestMethod.GET, params = { "process=Publish" })
	public String reportCardPublish(
			@ModelAttribute("markMaster") ReportCardMasterVo ReportCardMasterVo,
			ModelMap model, HttpServletRequest httpServletRequest,
			HttpSession session, RedirectAttributes redirectAttribute)
			throws NoDataFoundException, UpdateFailedException,
			DuplicateEntryException, DatabaseException,
			TableNotSpecifiedForAuditException

	{
		MarkMasterSearchVO markMasterSearchVO = (MarkMasterSearchVO) WebUtils
				.getSessionAttribute(httpServletRequest, "searchVo");
		List<MarkMasterVO> markMasterVOs = (List<MarkMasterVO>) WebUtils
				.getSessionAttribute(httpServletRequest, "display_tbl");
		ReportCardMasterVo.setMarkMasterSearchVo(markMasterSearchVO);
		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		ApplicationCache applicationCache = (ApplicationCache) session
				.getServletContext().getAttribute(
						ApplicationConstant.APPLICATION_CACHE);
		String id = httpServletRequest.getParameter("rowId");

		ModelAndView modelAndView = new ModelAndView(
				ModelAndViewConstant.REPORT_CARD_REMARK, "markMaster",
				ReportCardMasterVo);
		httpServletRequest.setAttribute("page", modelAndView);
		System.out.println("Row In from jsp:" + id);
		ReportCardMasterVo.setMarkMasterVO(markMasterVOs.get(Integer
				.parseInt(id)));
		System.out.println("mark master vo :"
				+ ReportCardMasterVo.getMarkMasterVO().toString());
		reportCardRemarksService.reportCardPublish(ReportCardMasterVo,
				sessionCache.getUserSessionDetails(), applicationCache);

		redirectAttribute.addFlashAttribute("markMaster", ReportCardMasterVo);
		redirectAttribute.addFlashAttribute("success",
				ErrorCodeConstant.REPORT_CARD_PUBLISHED);
		return "redirect:/reportCardList.htm?Get";

	}

	@RequestMapping(value = "/rcMaintenance", method = RequestMethod.GET, params = { "action" })
	public String lockMarks(
			@ModelAttribute("markMaster") ReportCardMasterVo ReportCardMasterVo,
			ModelMap model, HttpServletRequest httpServletRequest,
			HttpSession session, RedirectAttributes redirectAttribute)
			throws NoDataFoundException, UpdateFailedException,
			DuplicateEntryException, DatabaseException,
			TableNotSpecifiedForAuditException

	{
		MarkMasterSearchVO markMasterSearchVO = (MarkMasterSearchVO) WebUtils
				.getSessionAttribute(httpServletRequest, "searchVo");

		ReportCardMasterVo.setMarkMasterSearchVo(markMasterSearchVO);
		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		ApplicationCache applicationCache = (ApplicationCache) session
				.getServletContext().getAttribute(
						ApplicationConstant.APPLICATION_CACHE);

		String action = httpServletRequest.getParameter("action");
		List<MarkMasterVO> markMasterVOs = (List<MarkMasterVO>) WebUtils
				.getSessionAttribute(httpServletRequest, "display_tbl");
		String id = httpServletRequest.getParameter("rowId");
		ReportCardMasterVo.setMarkMasterVO(markMasterVOs.get(Integer
				.parseInt(id)));
		reportCardRemarksService.marksLockandClose(ReportCardMasterVo,
				sessionCache.getUserSessionDetails(), action, applicationCache);

		redirectAttribute.addFlashAttribute("markMaster", ReportCardMasterVo);
		if (action.equals(ApplicationConstant.MARKS_LOCKED)) {
			redirectAttribute.addFlashAttribute("success",
					ErrorCodeConstant.MARKS_LOCKED);

		} else if (action.equals(ApplicationConstant.MARKS_CLOSED)) {
			redirectAttribute.addFlashAttribute("success",
					ErrorCodeConstant.MARKS_CLOSED);
		}
		return "redirect:/reportCardList.htm?Get";

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