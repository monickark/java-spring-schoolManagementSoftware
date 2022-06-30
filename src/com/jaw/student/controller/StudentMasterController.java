package com.jaw.student.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
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
import com.jaw.common.constants.CommonCodeConstant;
import com.jaw.common.constants.ErrorCodeConstant;
import com.jaw.common.constants.PropertyManagementConstant;
import com.jaw.common.dropdown.service.IDropDownListService;
import com.jaw.common.exceptions.DatabaseException;
import com.jaw.common.exceptions.DeleteFailedException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.FileNotFoundInDatabase;
import com.jaw.common.exceptions.FileNotSaveException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.util.PropertyNotFoundException;
import com.jaw.common.files.service.IFileMasterService;
import com.jaw.common.util.CommonCodeUtil;
import com.jaw.common.util.PropertyManagementUtil;
import com.jaw.common.util.dao.ICodeAndDescriptionDao;
import com.jaw.fee.controller.FeePaymentMasterVO;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.sessCache.SessionCache;
import com.jaw.framework.sessCache.UserSessionDetails;
import com.jaw.student.admission.controller.AdmissionDetailsVO;
import com.jaw.student.admission.controller.FileTypeVO;
import com.jaw.student.admission.controller.AdmissionController.INBR;
import com.jaw.student.admission.service.IViewAndEditFileService;
import com.jaw.student.service.IStudentMasterService;
import com.jaw.student.service.IViewProfileService;

@Controller
public class StudentMasterController {
	Logger logger = Logger.getLogger(StudentMasterController.class);

	@Autowired
	private IStudentMasterService studentMasterService;
	@Autowired
	PropertyManagementUtil propertyManagementUtil;
	@Autowired
	private IViewProfileService viewProfileService;
	@Autowired
	private IFileMasterService fileMasterService;
	@Autowired
	IDropDownListService dropDownListService;
	@Autowired
	IViewAndEditFileService viewAndEditFilesService;
	@Autowired
	CommonCodeUtil commonCodeUtil;
	@Autowired
	ICodeAndDescriptionDao codeAndDescriptionDao;
	UserSessionDetails userSessionDetails;

	public UserSessionDetails getUserSessionDetails() {
		return userSessionDetails;
	}

	public void setUserSessionDetails(UserSessionDetails userSessionDetails) {
		this.userSessionDetails = userSessionDetails;
	}

	@RequestMapping(value = "/studentMasterSearch", method = RequestMethod.GET)
	public ModelAndView search(
			@ModelAttribute("stuMaster") StudentSearchVO studentSearchVO,
			HttpServletRequest request, HttpSession session)
			throws PropertyNotFoundException {
		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		
		INBR inbr = INBR.valueOf(sessionCache.getUserSessionDetails()
				.getInbrCategory());
		
		ModelAndView modelAndView = null;
		switch (inbr) {
		case DC:
		case PUC: {
			modelAndView = new ModelAndView(".jaw.clgeStuSearch");
			break;
		}
		case SSLC:
		case ICSE: {
			modelAndView = new ModelAndView(".jaw.sklStuSearch");
			break;
		}

		default: {
			break;
		}

		}

		request.setAttribute("studentSearchVO", studentSearchVO);
		WebUtils.setSessionAttribute(request, "display_tbl", null);
		ApplicationCache applicationCache = (ApplicationCache) session
				.getServletContext().getAttribute(
						ApplicationConstant.APPLICATION_CACHE);

		setUserSessionDetails(sessionCache.getUserSessionDetails());
		studentSearchVO.setAcademicYear(userSessionDetails.getCurrAcTerm());
		modelAndView.getModelMap().addAttribute("searchView", "searchView");
		WebUtils.setSessionAttribute(request, "success", null);
		WebUtils.setSessionAttribute(request, "keepStatus", null);
		WebUtils.setSessionAttribute(request, "search", null);
		modelAndView.getModelMap().addAttribute("keepStatus", "true");
		return modelAndView;
	}
	@RequestMapping(value = "/studentMasterSearch", method = RequestMethod.GET, params = "data")
	public ModelAndView searchdata(
			@ModelAttribute("stuMaster") StudentSearchVO studentSearchVO,
			HttpServletRequest request, HttpSession session)
			throws PropertyNotFoundException {
		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		INBR inbr = INBR.valueOf(sessionCache.getUserSessionDetails()
				.getInbrCategory());
		//System.out.println("Search Stud"+inbr);
		ModelAndView modelAndView = null;
		
		switch (inbr) {
		case DC:
		case PUC: {
			modelAndView = new ModelAndView(".jaw.clgeStuSearch");
			break;
		}
		case SSLC:
		case ICSE: {
			modelAndView = new ModelAndView(".jaw.sklStuSearch");
			break;
		}

		default:
			break;
		}

		String pageSize = request.getParameter("pageNo");
		StudentSearchVO studentSearch = (StudentSearchVO) WebUtils
				.getSessionAttribute(request, "search");
		studentSearch.setPageNo(studentSearchVO.getPageNo());
		WebUtils.setSessionAttribute(request, "search", studentSearch);
		modelAndView.getModelMap().addAttribute("stuMaster", studentSearch);
		return modelAndView;
	}

	@RequestMapping(value = "/studentMaster", method = RequestMethod.GET)
	public String searchStud(
			@ModelAttribute("stuMaster") StudentSearchVO studentSearchVO,
			ModelMap model, HttpServletRequest request,
			final RedirectAttributes redirectAttrs, HttpSession session)
			throws PropertyNotFoundException, NoDataFoundException {
		WebUtils.setSessionAttribute(request, "success", null);
		request.setAttribute("studentSearchVO", studentSearchVO);
		ApplicationCache applicationCache = (ApplicationCache) session
				.getServletContext().getAttribute(
						ApplicationConstant.APPLICATION_CACHE);
		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		INBR inbr = INBR.valueOf(sessionCache.getUserSessionDetails()
				.getInbrCategory());
		System.out.println("Search Stud"+inbr);
		request.setAttribute("inb", inbr);
		ModelAndView modelAndView = null;
		List<StudentMasterListVO> searchStudent = null;
		
		switch (inbr) {
		case PUC:
		case DC: {
			modelAndView = new ModelAndView(".jaw.clgeStuSearch");
			break;
		}

		case SSLC:
		case ICSE: {
			modelAndView = new ModelAndView(".jaw.sklStuSearch");
			break;
		}

		default:
			break;
		}

		if ((request.getParameter("Get") != null)
				&& ((request.getParameter("Get").equals("Get")) || ((request
						.getParameter("Get").equals("Search"))))) {
			WebUtils.setSessionAttribute(request, "keepStatus", "true");
			WebUtils.setSessionAttribute(request, "success", null);
			String button = request.getParameter("Get");

			Map stockParamMap = WebUtils.getParametersStartingWith(request,
					"d-");
			if (stockParamMap.size() == 0) {
				studentSearchVO.setButton(button);

				WebUtils.setSessionAttribute(request, "search", studentSearchVO);
				WebUtils.setSessionAttribute(request, "display_tbl", null);
				if (button.equals("Search")) {
					searchStudent = studentMasterService.retrieveStudent(
							studentSearchVO.getStudentAdmisNo(),
							userSessionDetails, applicationCache);
				} else if (button.equals("Get")) {
					searchStudent = studentMasterService.findStudent(
							studentSearchVO, userSessionDetails);
					
				}
				WebUtils.setSessionAttribute(request, "display_tbl",
						searchStudent);
			}

		} else {
			WebUtils.setSessionAttribute(request, "keepStatus", "false");
		}
		String status = (String) WebUtils.getSessionAttribute(request,
				"keepStatus");

		return "redirect:/studentMasterSearch.htm?data";
	}

	@RequestMapping(value = "/studentMaster", method = RequestMethod.GET, params = "print")
	public void printdata(
			@ModelAttribute("stuMaster") StudentSearchVO studentSearchVO,
			HttpServletRequest request, HttpServletResponse response,
			HttpSession session) throws IOException {
		logger.debug("Received request to download PDF report");
		String input = request.getSession().getServletContext()
				.getRealPath("//reports//StudentList.jrxml");

		JasperReport jreport1 = null;

		try {

			System.out.println("In jasper datasource :");
			List<StudentMasterListVO> searchStudent = (List<StudentMasterListVO>) WebUtils
					.getSessionAttribute(request, "display_tbl");

			studentSearchVO = (StudentSearchVO) WebUtils.getSessionAttribute(
					request, "search");
			System.out.println("Datasource size:" + searchStudent.size());
			ApplicationCache applicationCache = (ApplicationCache) session
					.getServletContext().getAttribute(
							ApplicationConstant.APPLICATION_CACHE);
			List<StudentMasterListVO> searchStudent1 = new ArrayList<StudentMasterListVO>();
			searchStudent1.add(null);
			for (StudentMasterListVO studentMasterListVO : searchStudent) {
				String acTerm = commonCodeUtil
						.getDescriptionByCode(applicationCache,
								studentMasterListVO.getAcademicYear());
				String course = commonCodeUtil.getDescriptionByCode(
						applicationCache, studentMasterListVO.getCourse());
				String term = commonCodeUtil.getDescriptionByCode(
						applicationCache, studentMasterListVO.getStandard());
				studentMasterListVO.setAcademicYear(acTerm);
				studentMasterListVO.setCourse(course);
				studentMasterListVO.setStandard(term);
				searchStudent1.add(studentMasterListVO);
				System.out.println("Datasource data :"
						+ studentMasterListVO.toString());
			}
			JRDataSource datasource = new JRBeanCollectionDataSource(
					searchStudent1);
			Map<String, Object> parameterMap = new HashMap<String, Object>();
			parameterMap.put("datasource", datasource);

			System.out.println("Parameters output:"
					+ studentSearchVO.toString());
			SessionCache sessionCache = (SessionCache) request.getSession()
					.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
			String stgroup = codeAndDescriptionDao.getStudentGrpName(
					sessionCache.getUserSessionDetails().getInstId(),
					sessionCache.getUserSessionDetails().getBranchId(),
					studentSearchVO.getStuGrpId());
			String term = commonCodeUtil.getDescriptionByCode(applicationCache,
					studentSearchVO.getAcademicYear());
			
			parameterMap.put("acTerm", term);
			parameterMap.put("stGroup", stgroup);
			
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

			String outputPdf = request.getSession().getServletContext()
					.getRealPath("//reports//StudentList.pdf");

			OutputStream output = new FileOutputStream(new File(outputPdf));
			exporter.setParameter(JRPdfExporterParameter.OUTPUT_STREAM, output);

			exporter.exportReport();
			System.out.println("going to export in path :" + outputPdf);
			// ////////////////////

			InputStream file1 = new FileInputStream(new File(outputPdf));

			response.setContentType("application/pdf");
			String name = "StudentList.pdf";
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
			// //////////////////

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

	@RequestMapping(value = "/addAndViewFiles")
	public ModelAndView viewFiles(
			@ModelAttribute("viewfiles") AdmissionDetailsVO admissionDetailsVO,
			HttpServletRequest request, HttpSession session,
			final RedirectAttributes redirectAttributes)
			throws PropertyNotFoundException, NoDataFoundException {

		ModelAndView modelAndView = null;
		ApplicationCache applicationCache = (ApplicationCache) session
				.getServletContext().getAttribute(
						ApplicationConstant.APPLICATION_CACHE);
		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);

		INBR inbr = INBR.valueOf(sessionCache.getUserSessionDetails()
				.getInbrCategory());
		switch (inbr) {
		case PUC:
		case DC: {
			modelAndView = new ModelAndView(".jaw.addAndViewFiles");
			break;
		}

		case SSLC:
		case ICSE: {
			modelAndView = new ModelAndView(".jaw.skladdAndViewFiles");
			break;
		}

		default:
			break;
		}

		if (request.getParameter("actt") != null) {
			StudentSearchVO studentSearchVO = (StudentSearchVO) WebUtils
					.getSessionAttribute(request, "search");
			redirectAttributes.addFlashAttribute("stuMaster", studentSearchVO);
			if ((studentSearchVO.getButton() != null)
					&& (studentSearchVO.getButton().equals("Search"))) {
				modelAndView = new ModelAndView(
						"redirect:/studentMaster.htm?Get=Search");
			} else if (studentSearchVO.getButton().equals("Get")) {
				modelAndView = new ModelAndView(
						"redirect:/studentMaster.htm?Get=Get");
			}
		} else {

			String rowId = request.getParameter("rowid");
			List<StudentMasterListVO> searchStudent = (List<StudentMasterListVO>) WebUtils
					.getSessionAttribute(request, "display_tbl");
			StudentMasterListVO stuMas = searchStudent.get(Integer
					.valueOf(Integer.valueOf(rowId) - 1));

			admissionDetailsVO.setStudentAdmisNo(stuMas.getStudentAdmisNo());

			admissionDetailsVO.setAcademicYear(stuMas.getAcademicYear());
			FileTypeVO fileType = viewProfileService.getFileType(
					sessionCache.getUserSessionDetails(),
					stuMas.getStudentAdmisNo());
			if (fileType != null) {
				modelAndView.getModelMap().addAttribute("fileType", fileType);
			}
		}
		return modelAndView;
	}

	@RequestMapping(value = "/addAndViewFiles", method = RequestMethod.POST)
	public ModelAndView addFiles(
			@ModelAttribute("viewfiles") AdmissionDetailsVO admissionDetailsVO,
			HttpServletRequest request, HttpSession session)
			throws PropertyNotFoundException, NoDataFoundException,
			IOException, IllegalAccessException, InvocationTargetException,
			DatabaseException, DuplicateEntryException, FileNotFoundInDatabase,
			DeleteFailedException, FileNotSaveException {
		ModelAndView modelAndView = null;
		ApplicationCache applicationCache = (ApplicationCache) session
				.getServletContext().getAttribute(
						ApplicationConstant.APPLICATION_CACHE);
		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		INBR inbr = INBR.valueOf(sessionCache.getUserSessionDetails()
				.getInbrCategory());
		
		switch (inbr) {
		case PUC:
		case DC: {
			modelAndView = new ModelAndView(".jaw.addAndViewFiles");
			break;
		}

		case SSLC:
		case ICSE: {
			modelAndView = new ModelAndView(".jaw.skladdAndViewFiles");
			break;
		}

		default:
			break;
		}

		String rowId = request.getParameter("rowid");
		viewAndEditFilesService.saveFile(admissionDetailsVO,
				sessionCache.getUserSessionDetails(), applicationCache,
				session.getServletContext());

		FileTypeVO fileType = viewProfileService.getFileType(
				sessionCache.getUserSessionDetails(),
				admissionDetailsVO.getStudentAdmisNo());
		if (fileType != null) {
			modelAndView.getModelMap().addAttribute("fileType", fileType);
		}
		WebUtils.setSessionAttribute(request, "success",
				ErrorCodeConstant.UPDATE_SUCCESS_MESS);
		return modelAndView;
	}

	@ModelAttribute("allSubjectList")
	public Map<String, String> gerAllSubjectList(HttpSession session,
			HttpServletRequest httpSevletRequest, HttpServletResponse response,
			ModelMap model) throws IOException {
		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		Map<String, String> map = null;
		try {
			map = dropDownListService.selectAllSubjectList(sessionCache
					.getUserSessionDetails());
		} catch (NoDataFoundException e) {
			logger.error("Error occured in Subject list Dropdown :"
					+ e.getMessage());
		}
		httpSevletRequest.setAttribute("allSubjectList", map);
		return map;

	}

	@ExceptionHandler({ NoDataFoundException.class, FileNotSaveException.class })
	public ModelAndView handleNoDataException(Exception ex,
			HttpSession session, HttpServletRequest request) {
		StudentSearchVO admissionDetailsVO = (StudentSearchVO) request
				.getAttribute("studentSearchVO");
		INBR inbr =(INBR) request.getAttribute("inb");
		System.out.println("INBR"+inbr);
		ModelAndView mav = null;
		switch (inbr) {
		case DC:
		case PUC: {
			mav = new ModelAndView(".jaw.clgeStuSearch", "stuMaster",
					admissionDetailsVO);
			break;
		}
		case SSLC:
		case ICSE: {
			mav = new ModelAndView(".jaw.sklStuSearch", "stuMaster",
					admissionDetailsVO);
			break;
		}

		default:
			break;
		}
		//System.out.println("Page "+mav);
		System.out.println("Error Message"+ex.getMessage());
		mav.getModelMap().addAttribute("error", ex.getMessage());
		String message = (String) WebUtils.getSessionAttribute(request,
				"success");
		if ((message != null)
				&& (message.equals(ErrorCodeConstant.DELETE_SUCCESS_MESS))) {
		} else {
			WebUtils.setSessionAttribute(request, "success", null);
			mav.getModelMap().addAttribute("error", ex.getMessage());
		}

		WebUtils.setSessionAttribute(request, "display_tbl", null);
		return mav;
	}

}
