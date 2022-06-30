package com.jaw.student.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.WebUtils;

import com.jaw.common.constants.ApplicationConstant;
import com.jaw.common.constants.ErrorCodeConstant;
import com.jaw.common.dropdown.service.IDropDownListService;
import com.jaw.common.exceptions.DatabaseException;
import com.jaw.common.exceptions.DeleteFailedException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.FileNotFoundInDatabase;
import com.jaw.common.exceptions.FileNotSaveException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.UpdateFailedException;
import com.jaw.common.exceptions.util.PropertyNotFoundException;
import com.jaw.common.exceptions.util.TableNotSpecifiedForAuditException;
import com.jaw.common.files.service.IFileMasterService;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.sessCache.SessionCache;
import com.jaw.student.admission.controller.AdmissionDetailsVO;
import com.jaw.student.admission.controller.FileTypeVO;
import com.jaw.student.admission.controller.PreSportParticipationDetailsVO;
import com.jaw.student.admission.controller.SiblingDetailsVO;
import com.jaw.student.admission.controller.AdmissionController.INBR;
import com.jaw.student.service.IViewProfileService;

@Controller
public class ViewProfileController {

	Logger logger = Logger.getLogger(ViewProfileController.class);

	@Autowired
	private IViewProfileService viewProfileService;
	@Autowired
	private IFileMasterService fileMasterService;
	@Autowired
	IDropDownListService dropDownListService;

	private final List<PreSportParticipationDetailsVO> preSportParticipationDetailsVO = new ArrayList<PreSportParticipationDetailsVO>();
	//private final List<SiblingDetailsVO> siblingDetailsVO = new ArrayList<SiblingDetailsVO>();	

	@RequestMapping(value = "/qviewstuMaster", method = RequestMethod.GET)
	public ModelAndView redirectView(
			@ModelAttribute("stuMaster") AdmissionDetailsVO admissionDetailsVO,
			HttpServletRequest request, HttpSession session,
			final RedirectAttributes redirectAttributes)
			throws PropertyNotFoundException, NoDataFoundException {
		ApplicationCache applicationCache = (ApplicationCache) session
				.getServletContext().getAttribute(
						ApplicationConstant.APPLICATION_CACHE);
		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		WebUtils.setSessionAttribute(request, "success", null);
		ModelAndView modelAndView = null;

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
			INBR inbr = INBR.valueOf(sessionCache.getUserSessionDetails()
					.getInbrCategory());
			switch (inbr) {			 
			case PUC:case DC: {
				modelAndView = new ModelAndView(".jaw.clgeStuMasterview");
				admissionDetailsVO.setSiblingDetailsVO(null);
				break;
			}

			case SSLC:case ICSE: {
				modelAndView = new ModelAndView(".jaw.sklStuMasterview");
				break;
			}

			default:
				break;
			}
			
			
			String rowId = request.getParameter("rowid");
			List<StudentMasterListVO> searchStudent = (List<StudentMasterListVO>) WebUtils
					.getSessionAttribute(request, "display_tbl");
			StudentMasterListVO stuMas = searchStudent.get(Integer
					.valueOf(Integer.valueOf(rowId) - 1));
			admissionDetailsVO.setStudentAdmisNo(stuMas.getStudentAdmisNo());
			admissionDetailsVO.setAcademicYear(stuMas.getAcademicYear());
			if (stuMas.getStudentAdmisNo() != null) {
				viewProfileService.getStudentMaster(stuMas.getStudentAdmisNo(),
						admissionDetailsVO,
						sessionCache.getUserSessionDetails(), applicationCache);
			}

			

		}
		return modelAndView;
	}

	@RequestMapping(value = "/viewstuMaster")
	public ModelAndView viewStudentMaster(
			@ModelAttribute("viewuser") AdmissionDetailsVO admissionDetailsVO,
			HttpServletRequest request, HttpSession session,
			final RedirectAttributes redirectAttributes)
			throws PropertyNotFoundException, NoDataFoundException {
		request.setAttribute("admissionDetailsVO", admissionDetailsVO);
		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		ModelAndView modelAndView = null;
		ApplicationCache applicationCache = (ApplicationCache) session
				.getServletContext().getAttribute(
						ApplicationConstant.APPLICATION_CACHE);
		WebUtils.setSessionAttribute(request, "success", null);
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
			
			INBR inbr = INBR.valueOf(sessionCache.getUserSessionDetails()
					.getInbrCategory());
			switch (inbr) {			
			case PUC:case DC: {
				modelAndView = new ModelAndView(".jaw.adminViewClgeStudent");
				admissionDetailsVO.setSiblingDetailsVO(null);
				break;
			}

			case SSLC:case ICSE: {
				
				modelAndView = new ModelAndView(".jaw.adminViewSklStudent");
							
				break;
			}

			default:
				break;
			}
			
			
			
			String rowId = request.getParameter("rowid");
			List<StudentMasterListVO> searchStudent = (List<StudentMasterListVO>) WebUtils
					.getSessionAttribute(request, "display_tbl");
			StudentMasterListVO stuMas = searchStudent.get(Integer
					.valueOf(Integer.valueOf(rowId) - 1));

			admissionDetailsVO.setStudentAdmisNo(stuMas.getStudentAdmisNo());

			admissionDetailsVO.setAcademicYear(stuMas.getAcademicYear());
			viewProfileService.viewStudentDetails(admissionDetailsVO,
					sessionCache.getUserSessionDetails(), applicationCache);

			FileTypeVO fileType = viewProfileService.getFileType(
					sessionCache.getUserSessionDetails(),
					stuMas.getStudentAdmisNo());

			

			modelAndView.getModelMap().addAttribute("preSportPartDetails",
					admissionDetailsVO.getPreSportPartDetails());
			modelAndView.getModelMap().addAttribute("siblingDetails",
					admissionDetailsVO.getSiblingDetailsVO());	
			if (fileType != null) {
				modelAndView.getModelMap().addAttribute("fileType", fileType);
			}
		}

		return modelAndView;
	}

	@RequestMapping(value = "/editstumaster")
	public ModelAndView editStudentMaster(
			@ModelAttribute("viewuser") AdmissionDetailsVO admissionDetailsVO,
			HttpServletRequest request, HttpSession session)
			throws PropertyNotFoundException, NoDataFoundException {		
		String pageNo = admissionDetailsVO.getPageNo();
		ModelAndView mav = editStuMaster(session, request, admissionDetailsVO);
		admissionDetailsVO.setPageNo(pageNo);
		WebUtils.setSessionAttribute(request, "success", null);	
		return mav;
	}

	@RequestMapping(value = "/editstumaster", method = RequestMethod.GET, params = "data")
	public ModelAndView editStudentMasterRender(
			@ModelAttribute("viewuser") AdmissionDetailsVO admissionDetailsVO,
			HttpServletRequest request, HttpSession session)
			throws PropertyNotFoundException, NoDataFoundException {
		ModelAndView mav = editStuMaster(session, request, admissionDetailsVO);
		return mav;
	}

	public ModelAndView editStuMaster(HttpSession session,
			HttpServletRequest request, AdmissionDetailsVO admissionDetailsVO)
			throws PropertyNotFoundException, NoDataFoundException {	
		ModelAndView modelAndView = null;
		ApplicationCache applicationCache = (ApplicationCache) session
				.getServletContext().getAttribute(
						ApplicationConstant.APPLICATION_CACHE);
		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		

		String rowId = request.getParameter("rowid");
		String admisNo = null;
		List<StudentMasterListVO> searchStudent = (List<StudentMasterListVO>) WebUtils
				.getSessionAttribute(request, "display_tbl");
		INBR inbr = INBR.valueOf(sessionCache.getUserSessionDetails()
				.getInbrCategory());
		switch (inbr) {		
		case PUC:case DC:  {
			modelAndView = new ModelAndView(".jaw.adminEditClgeStudent");
			admissionDetailsVO.setSiblingDetailsVO(null);
			break;
		}

		case SSLC: case ICSE:{
			modelAndView = new ModelAndView(".jaw.adminEditSklStudent");		
					
					
			break;
		}

		default:
			break;
		}
		if ((request.getParameter("act") == null)
				|| (!request.getParameter("act").equals("Update"))) {
			StudentMasterListVO stuMas = searchStudent.get(Integer
					.valueOf(Integer.valueOf(rowId) - 1));
			admisNo = stuMas.getStudentAdmisNo();
			admissionDetailsVO.setStudentAdmisNo(admisNo);
			admissionDetailsVO.setAcademicYear(stuMas.getAcademicYear());

		} else {

			admisNo = request.getParameter("admisno");
			admissionDetailsVO.setStudentAdmisNo(admisNo);
			admissionDetailsVO.setAcademicYear(request.getParameter("actrm"));
		}
		viewProfileService.viewStudentDetails(admissionDetailsVO,
				sessionCache.getUserSessionDetails(), applicationCache);
		FileTypeVO fileType = viewProfileService.getFileType(
				sessionCache.getUserSessionDetails(), admisNo);	
		
		// For Previous Sports Part Details
		preSportParticipationDetailsVO.clear();
		if (admissionDetailsVO.getPreSportPartDetails().size() != 0) {
			preSportParticipationDetailsVO.addAll(admissionDetailsVO
					.getPreSportPartDetails());
		}
		if (admissionDetailsVO.getPreSportPartDetails().size() < ApplicationConstant.SIBLING_UITEXTBOX_COUNT) {
			for (int count = admissionDetailsVO.getPreSportPartDetails().size(); count < ApplicationConstant.SIBLING_UITEXTBOX_COUNT; count++) {
				preSportParticipationDetailsVO
						.add(new PreSportParticipationDetailsVO());
			}
		}
		if(admissionDetailsVO.getSiblingDetailsVO()!=null){
		if (admissionDetailsVO.getSiblingDetailsVO().size() < ApplicationConstant.SIBLING_UITEXTBOX_COUNT) {
			for (Integer count = admissionDetailsVO.getSiblingDetailsVO().size(); count < ApplicationConstant.SIBLING_UITEXTBOX_COUNT; ) {
				SiblingDetailsVO newSib = new SiblingDetailsVO();
				count = count+1;
				newSib.setSiblingNo((count).toString());
				admissionDetailsVO.getSiblingDetailsVO()
						.add(newSib);
			}
		}				
		modelAndView.getModelMap().addAttribute("siblingDetails",
				admissionDetailsVO.getSiblingDetailsVO());
		
		}
		if (fileType != null) {
			modelAndView.getModelMap().addAttribute("fileType", fileType);
		}
		modelAndView.getModelMap().addAttribute("preSportPartDetails",
				preSportParticipationDetailsVO);
		modelAndView.getModelMap().addAttribute("actcontrol", "adminedit");
		return modelAndView;
	}

	@RequestMapping(value = "/edituser", method = RequestMethod.POST)
	public String insertContact(@RequestParam("action") String action,
			@ModelAttribute("viewuser") AdmissionDetailsVO admissionDetailsVO,
			HttpSession session, HttpServletRequest httpServletRequest,
			final RedirectAttributes redirectAttributes) throws IOException,
			FileNotFoundInDatabase, DuplicateEntryException, DatabaseException,
			TableNotSpecifiedForAuditException, PropertyNotFoundException,
			UpdateFailedException, NoDataFoundException, DeleteFailedException, IllegalStateException, FileNotSaveException {		
		String modelAndView = null;
		String actioncon = httpServletRequest.getParameter("actioncon");
		ApplicationCache applicationCache = (ApplicationCache) session
				.getServletContext().getAttribute(
						ApplicationConstant.APPLICATION_CACHE);
		httpServletRequest.setAttribute("admissionDetailsVO",
				admissionDetailsVO);

		if (httpServletRequest.getParameter("actt") != null) {

			StudentSearchVO studentSearchVO = (StudentSearchVO) WebUtils
					.getSessionAttribute(httpServletRequest, "search");
			redirectAttributes.addFlashAttribute("stuMaster", studentSearchVO);
			if ((studentSearchVO.getButton() != null)
					&& (studentSearchVO.getButton().equals("Search"))) {
				modelAndView = "redirect:/studentMaster.htm?Get=Search";
			} else if (studentSearchVO.getButton().equals("Get")) {
				modelAndView = "redirect:/studentMaster.htm";
			}

		} else {

			// If any error occurs redirects to particular tiles definition

			SessionCache sessionCache = (SessionCache) session
					.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);

			if (action != null) {				
				viewProfileService.viewProfilEdit(applicationCache,
						admissionDetailsVO,
						sessionCache.getUserSessionDetails(), action,session.getServletContext());
			}

			if (actioncon.equals("adminedit")) {
				WebUtils.setSessionAttribute(httpServletRequest, "success",
						ErrorCodeConstant.UPDATE_SUCCESS_MESS);
				modelAndView = "redirect:/editstumaster.htm?data&admisno="
						+ admissionDetailsVO.getStudentAdmisNo() + "&actrm="
						+ admissionDetailsVO.getAcademicYear() + "&act=Update";				

			} else if (actioncon.equals("useredit")) {
				WebUtils.setSessionAttribute(httpServletRequest, "success",
						ErrorCodeConstant.UPDATE_SUCCESS_MESS);				
				modelAndView = "redirect:/edituser.htm?data";				
			}

			logger.info("Successfully completed Admission details");
		}
		return modelAndView;

	}

	@ModelAttribute("acTermList")
	public Map<String, String> gerAcaTrmList(HttpSession session,
			HttpServletRequest httpSevletRequest, HttpServletResponse response,
			ModelMap model) throws IOException {
		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		Map<String, String> map = null;

		try {
			map = dropDownListService
					.getAcademicTermListForPresentAndFuture(sessionCache
							.getUserSessionDetails());
		} catch (NoDataFoundException e) {
			logger.error("Error occured in Academic Term Dropdown :"
					+ e.getMessage());
		}
		httpSevletRequest.setAttribute("acTermList", map);
		return map;

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

	@ModelAttribute("courseList")
	public Map<String, String> gerCourseNameList(HttpSession session,
			HttpServletRequest httpSevletRequest, HttpServletResponse response,
			ModelMap model) throws IOException {
		Map<String, String> map = null;
		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
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

	@ModelAttribute("termList")
	public Map<String, String> getTermList(HttpSession session,
			HttpServletRequest httpSevletRequest, HttpServletResponse response,
			ModelMap model) throws IOException {
		Map<String, String> map = null;
		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		try {
			map = dropDownListService.getTermList(sessionCache
					.getUserSessionDetails());
		} catch (NoDataFoundException e) {
			logger.error("Error occured in Term list Dropdown :"
					+ e.getMessage());
		}

		httpSevletRequest.setAttribute("termList", map);
		return map;

	}
	

	@ExceptionHandler({ FileNotFoundInDatabase.class,
			DuplicateEntryException.class,
			TableNotSpecifiedForAuditException.class,
			NoDataFoundException.class, UpdateFailedException.class,FileNotSaveException.class })
	public ModelAndView handleException(Exception ex, HttpSession session,
			HttpServletRequest request) {

		AdmissionDetailsVO admissionDetailsVO = (AdmissionDetailsVO) request
				.getAttribute("admissionDetailsVO");

		ModelAndView mav = new ModelAndView(".jaw.newClgeAdmission",
				"insertUser", admissionDetailsVO);
		mav.getModelMap().addAttribute("error", ex.getMessage());
		return mav;

	}
}
