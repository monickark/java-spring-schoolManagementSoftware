package com.jaw.student.admission.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import com.jaw.common.constants.ApplicationConstant;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.util.PropertyNotFoundException;
import com.jaw.common.util.ErrorCodeUtil;
import com.jaw.common.util.PropertyManagementUtil;
import com.jaw.framework.sessCache.SessionCache;
import com.jaw.student.admission.service.IAdmissionListService;
import com.jaw.student.admission.service.IAdmissionService;

@Controller
public class NewAdmissionController {
	Logger logger = Logger.getLogger(NewAdmissionController.class);

	@Autowired
	private IAdmissionService admissionService;
	@Autowired
	IAdmissionListService admissionListService;
	@Autowired
	PropertyManagementUtil propertyManagementUtil;
	@Autowired
	ErrorCodeUtil errorCodeUtil;

	@RequestMapping(value = "/admissionDetails", method = RequestMethod.GET)
	public ModelAndView selectNewAdmissionList(
			@ModelAttribute("newAdmission") NewAdmissionDetailsVO newAdmissionDetailsVO,
			HttpSession session, HttpServletRequest request)
			throws PropertyNotFoundException {
		ModelAndView modelAndView = new ModelAndView(".jaw.newAdmissionList");
		WebUtils.setSessionAttribute(request, "success", null);
		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		AdmissionSearchVO admissionSearchVO = new AdmissionSearchVO();
		admissionSearchVO.setAcademicStatus("P");
		newAdmissionDetailsVO.setAdmissionSearchVO(admissionSearchVO);
		try {
			newAdmissionDetailsVO = admissionListService
					.selectAdmissionList(newAdmissionDetailsVO,
							sessionCache.getUserSessionDetails());
			System.out.println("Admission List from Serivce"
					+ newAdmissionDetailsVO.getAdmissionList());
			WebUtils.setSessionAttribute(request, "display_tbl",
					newAdmissionDetailsVO.getAdmissionList());

		} catch (NoDataFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return modelAndView;
	}

	@RequestMapping(value = "/admissionDetails", method = RequestMethod.GET, params = { "newList" })
	public @ResponseBody List<AdmissionCountListVO> selectNewAdmissionCountList(
			HttpSession session, HttpServletRequest request)
			throws PropertyNotFoundException {
		System.out.println("Inside controller");
		WebUtils.setSessionAttribute(request, "success", null);
		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		AdmissionSearchVO admissionSearchVO = new AdmissionSearchVO();
		admissionSearchVO.setAcademicStatus("P");
		// Map<String,Integer> admissionCountMap=null;
		// AdmissionCountListVO[] admissionCountListArray=null;
		List<AdmissionCountListVO> admissionCountList = null;
		try {
			admissionCountList = admissionListService.selectAdmissionCount(
					admissionSearchVO, sessionCache.getUserSessionDetails(),
					session);
			System.out.println("Admission Count List from Serivce"
					+ admissionCountList);
			WebUtils.setSessionAttribute(request, "display_tbl1",
					admissionCountList);

			// admissionCountListArray= (AdmissionCountListVO[])
			// admissionCountListsVO.toArray();

		} catch (NoDataFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Count" + admissionCountList);
		return admissionCountList;
	}

	@RequestMapping(value = "/admissionDetails", method = RequestMethod.GET, params = { "print" })
	public void printAdmissionList(
			@ModelAttribute("newAdmission") NewAdmissionDetailsVO newAdmissionDetailsVO,
			HttpSession session, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		logger.debug("Received request to download PDF report");
		String input = request.getSession().getServletContext()
				.getRealPath("//reports//admission//NewAdmissionList.jrxml");

		JasperReport jasperReport1 = null;
		try {
			System.out.println("In jasper datasource :");
			List<AdmissionListVO> admissionList =(List<AdmissionListVO>) WebUtils
					.getSessionAttribute(request, "display_tbl");
			JRDataSource datasource = new JRBeanCollectionDataSource(
					admissionList);
			Map<String, Object> parameterMap = new HashMap<String, Object>();
			parameterMap.put("datasource", datasource);
			System.out.println("Generating Admission List PDF...");
			System.out.println("Admission List :"+admissionList);
			SessionCache sessionCache = (SessionCache) request.getSession()
					.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
			String path = request
					.getSession()
					.getServletContext()
					.getRealPath(
							"//images//"
									+ sessionCache.getUserSessionDetails()
											.getBranchId() + "_LOGO.png");

			parameterMap.put("logo", path);
			JRExporter exporter = new JRPdfExporter();
			jasperReport1 = JasperCompileManager.compileReport(input);
			JasperPrint jasperPrint = JasperFillManager.fillReport(
					jasperReport1, parameterMap, datasource);
			exporter.setParameter(JRPdfExporterParameter.JASPER_PRINT,
					jasperPrint);
			String outputPdf = request.getSession().getServletContext()
					.getRealPath("//reports//admission//NewAdmissionList.pdf");
			OutputStream output = new FileOutputStream(new File(outputPdf));
			exporter.setParameter(JRPdfExporterParameter.OUTPUT_STREAM, output);

			exporter.exportReport();
			System.out.println("going to export in path :" + outputPdf);

			InputStream file1 = new FileInputStream(new File(outputPdf));

			response.setContentType("application/pdf");
			String name = "NewAdmissionList.pdf";
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

			System.out.println("gng to close strem...");

			out.close();
			out.flush();
			output.close();
			output.flush();
			file1.close();
		} catch (JRException e) {
			e.printStackTrace();
		}
	}
}
