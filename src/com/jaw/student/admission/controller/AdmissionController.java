package com.jaw.student.admission.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.map.HashedMap;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
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
import com.jaw.common.constants.RadioButtons;
import com.jaw.common.dropdown.service.IDropDownListService;
import com.jaw.common.exceptions.DatabaseException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.FileNotFoundInDatabase;
import com.jaw.common.exceptions.FileNotSaveException;
import com.jaw.common.exceptions.InsertFailedException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.UnableCreateParentPassword;
import com.jaw.common.exceptions.UnableCreateParentUserId;
import com.jaw.common.exceptions.UnableCreateStudentPassword;
import com.jaw.common.exceptions.UnableCreateStudentUserId;
import com.jaw.common.exceptions.util.CommonCodeNotFoundException;
import com.jaw.common.exceptions.util.PropertyNotFoundException;
import com.jaw.common.util.CommonCodeUtil;
import com.jaw.common.util.DateUtil;
import com.jaw.common.util.ErrorCodeUtil;
import com.jaw.common.util.PropertyManagementUtil;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.appCache.dao.CommonCode;
import com.jaw.framework.sessCache.SessionCache;
import com.jaw.student.admission.dao.AdmissionList;
import com.jaw.student.admission.service.IAdmissionListService;
import com.jaw.student.admission.service.IAdmissionService;

//Controller class for Admission
@Controller
public class AdmissionController {

	Logger logger = Logger.getLogger(AdmissionController.class);

	@Autowired
	private IAdmissionService admissionService;
	@Autowired
	IAdmissionListService admissionListService;
	@Autowired
	PropertyManagementUtil propertyManagementUtil;
	@Autowired
	ErrorCodeUtil errorCodeUtil;
	@Autowired
	DateUtil dateUtil;
	@Autowired
	IDropDownListService dropDownListService;	
	private final List<SiblingDetailsVO> siblingDetailsVO = new ArrayList<SiblingDetailsVO>();	
	private final List<PreSportParticipationDetailsVO> preSportParticipationDetailsVO = new ArrayList<PreSportParticipationDetailsVO>();	
	public AdmissionController() {
		for (int count = 0; count < ApplicationConstant.SIBLING_UITEXTBOX_COUNT; count++) {
			siblingDetailsVO.add(new SiblingDetailsVO());
			preSportParticipationDetailsVO.add(new PreSportParticipationDetailsVO());
		}	
		
	}

	@RequestMapping(value = "/admissionDetails", method = RequestMethod.GET, params = "newstudent")
	public ModelAndView insertMemDtls(
			@ModelAttribute("insertUser") AdmissionDetailsVO admissionDetailsVO,
		HttpSession session,HttpServletRequest request) throws PropertyNotFoundException {		
		ModelAndView modelAndView = getAdmissionPage(request,session,admissionDetailsVO);
		WebUtils.setSessionAttribute(request, "success", null);
		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		String studentAdmisNO = admissionService.getNextAdmisNo(admissionDetailsVO,sessionCache.getUserSessionDetails());
		admissionDetailsVO.setStudentAdmisNo(studentAdmisNO);

		return modelAndView;
	}
	/*@RequestMapping(value = "/admissionDetails", method = RequestMethod.GET)
	public ModelAndView selectNewAdmissionList(
			@ModelAttribute("newAdmission") NewAdmissionDetailsVO newAdmissionDetailsVO,
		HttpSession session,HttpServletRequest request) throws PropertyNotFoundException {		
		ModelAndView modelAndView = new ModelAndView(".jaw.newAdmissionList");
		WebUtils.setSessionAttribute(request, "success", null);
		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		AdmissionSearchVO admissionSearchVO=new AdmissionSearchVO();
		admissionSearchVO.setAcademicStatus("P");
		newAdmissionDetailsVO.setAdmissionSearchVO(admissionSearchVO);
		try {
			newAdmissionDetailsVO=admissionListService.selectAdmissionList(newAdmissionDetailsVO, sessionCache.getUserSessionDetails());
			System.out.println("Admission List from Serivce"+newAdmissionDetailsVO.getAdmissionList());
			WebUtils.setSessionAttribute(request, "display_tbl",
					newAdmissionDetailsVO.getAdmissionList());
				
		} catch (NoDataFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return modelAndView;
	}
	
	@RequestMapping(value = "/admissionDetails", method = RequestMethod.GET, params = {"newList"})
	public @ResponseBody List<AdmissionCountListVO> selectNewAdmissionCountList(
		HttpSession session,HttpServletRequest request) throws PropertyNotFoundException {
		System.out.println("Inside controller");
		WebUtils.setSessionAttribute(request, "success", null);
		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		AdmissionSearchVO admissionSearchVO=new AdmissionSearchVO();
		admissionSearchVO.setAcademicStatus("P");
		List<AdmissionCountListVO> admissionCountListsVO=null;
		try {
			admissionCountListsVO=admissionListService.selectAdmissionCountList(admissionSearchVO, sessionCache.getUserSessionDetails());
			System.out.println("Admission Count List from Serivce"+admissionCountListsVO);
			WebUtils.setSessionAttribute(request, "display_tbl1",
					admissionCountListsVO);
			
		} catch (NoDataFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return admissionCountListsVO;
		return null;
	}*/
	
	
	@RequestMapping(value = "/admissionDetails", method = RequestMethod.GET, params = "data")
	public ModelAndView insertNewAdmissionPg(
			@ModelAttribute("insertUser") AdmissionDetailsVO admissionDetailsVO,
		HttpSession session,HttpServletRequest request) throws PropertyNotFoundException {		
		ModelAndView modelAndView = getAdmissionPage(request,session,admissionDetailsVO);		
		return modelAndView;
	}
	

	@RequestMapping(value = "/admissionDetails", method = RequestMethod.POST)
	public String insertContact(
			@ModelAttribute("insertUser") AdmissionDetailsVO admissionDetailsVO,
			BindingResult result, HttpSession session,
			HttpServletRequest httpServletRequest, ModelMap model,
			final RedirectAttributes redirectAttributes) throws IOException,
			FileNotFoundInDatabase, DuplicateEntryException, DatabaseException,
			NumberFormatException, PropertyNotFoundException, InsertFailedException, FileNotSaveException, UnableCreateParentUserId, UnableCreateParentPassword, UnableCreateStudentUserId, UnableCreateStudentPassword {
		ApplicationCache applicationCache = (ApplicationCache) session
				.getServletContext().getAttribute(
						ApplicationConstant.APPLICATION_CACHE);
		httpServletRequest.setAttribute("admissionDetailsVO",
				admissionDetailsVO);

		
		
		
		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		
		List<PreSportParticipationDetailsVO> preSportParticipationDetailsVO = admissionDetailsVO
				.getPreSportPartDetails();
		// If any error occurs redirects to particular tiles definition		
		INBR inbr = INBR.valueOf(sessionCache.getUserSessionDetails().getInbrCategory());
		ModelAndView modelAndView = null;
		switch(inbr){
		case DC: case PUC:{
			admissionDetailsVO.setSiblingDetailsVO(null);
			modelAndView = new ModelAndView(".jaw.newClgeAdmission");
			break;
		}
		case ICSE: case SSLC:{
			List<SiblingDetailsVO> siblingDetailsVO = admissionDetailsVO
					.getSiblingDetailsVO();
			
			modelAndView = new ModelAndView(".jaw.newSklAdmission");
			modelAndView.getModelMap().addAttribute("siblingDetails",
					siblingDetailsVO);
			
			break;
		}
			
				
		default:
			break;
		}		
		
		modelAndView.getModelMap().addAttribute("preSportPartDetails",
				preSportParticipationDetailsVO);		
		logger.debug("Checking Wheather admissionNumber Exists or not");
			
			
		// Creating New Admission		
		admissionService.newAdmission(applicationCache, admissionDetailsVO,
				sessionCache.getUserSessionDetails(),session.getServletContext());
				
		

		WebUtils.setSessionAttribute(httpServletRequest, "success",
				ErrorCodeConstant.ADMISSION_SUCCESS);
		redirectAttributes.addFlashAttribute("refNo", admissionDetailsVO.getStudentAdmisNo());

		return "redirect:/admissionDetails.htm?data";

	}
		
		
	@RequestMapping(value = "/admissionDetails", method = RequestMethod.GET, params = { "duplicateParentId" })
	public @ResponseBody
	String duplicateParentId(
			@ModelAttribute("insertUser") AdmissionDetailsVO admissionDetailsVO,
			HttpSession session, HttpServletRequest request,
			HttpServletResponse response) {
		String res = null;		
		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		String parentId = request.getParameter("parentId");	
		String value =admissionService.duplicateParentId(parentId,sessionCache.getUserSessionDetails());		
		res = String.valueOf(value);		
		return value;
	}
	
	@RequestMapping(value = "/admissionDetails", method = RequestMethod.GET, params = { "duplicateAdmisNo" })
	public @ResponseBody
	String duplicateAdmissionNo(
			@ModelAttribute("insertUser") AdmissionDetailsVO admissionDetailsVO,
			HttpSession session, HttpServletRequest request,
			HttpServletResponse response) {
	//	String res = null;		
		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		String stuAdmisNo = request.getParameter("stuAdmisNo");	
		String value =admissionService.duplicateAdmisNo(stuAdmisNo,sessionCache.getUserSessionDetails());		
			
		return value;
	}
	
	//@ModelAttribute("allSubList")
	@RequestMapping(value = "/secAndSubList", method = RequestMethod.GET, params = { "subList" })
	public @ResponseBody Map<String, Map<String,String>>  getSubLists(HttpSession session,
			HttpServletRequest httpSevletRequest, HttpServletResponse response,HttpServletRequest request,
			ModelMap model) throws IOException {
		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		Map<String, Map<String,String>> map = null;
		String courseId = request.getParameter("courseId");
		String trmId = request.getParameter("termId");					
		try {
			map=admissionService.getSubFromCourseAndTrm(null,courseId, trmId, sessionCache.getUserSessionDetails());
						
		} catch (NoDataFoundException e) {
			logger.error("No Data Found For Subject List :" + e.getMessage());			
		}
		httpSevletRequest.setAttribute("allSubList", map);				
		return map;
		//return "Success";

	}
	
	@RequestMapping(value = "/secAndSubList", method = RequestMethod.GET, params = {"courseVariant"})
	public @ResponseBody Map<String,String>  courseVarinatCheck(HttpSession session,
			HttpServletRequest httpSevletRequest, HttpServletResponse response,HttpServletRequest request,
			ModelMap model) throws IOException {
		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		ApplicationCache applicationCache = (ApplicationCache) session
				.getServletContext().getAttribute(
						ApplicationConstant.APPLICATION_CACHE);
		Map<String,String> cocdMap =null;
		Boolean cv ;
		String returnValue = null;
		String courseId = request.getParameter("courseId");		
	
		cv=admissionService.courseVariantCheckWithCourse(courseId,sessionCache.getUserSessionDetails());	
	
		if(cv){
			cocdMap = new LinkedHashMap<String, String>();			
				
			List<CommonCode> listOfCommonCode = null;
			try {
				listOfCommonCode = CommonCodeUtil.getCommonCodeListByType(applicationCache, CommonCodeConstant.COURSE_VARIANT, sessionCache.getUserSessionDetails().getInstId()
						, sessionCache.getUserSessionDetails().getBranchId());
			} catch (CommonCodeNotFoundException e) {
			logger.error("Common code for CourseVarinat not found");			
			}
			if(listOfCommonCode!=null){
			for(CommonCode commonCode:listOfCommonCode){				
				if(commonCode.getCodeType().equals(CommonCodeConstant.COURSE_VARIANT)){
					cocdMap.put(commonCode.getCommonCode(), commonCode.getCodeDescription());
				}
			}
			}
		
			returnValue = "true";
		}else{
			returnValue = "false";
		}		
		
	
		return cocdMap;	

	}
	
	@RequestMapping(value = "/secAndSubList", method = RequestMethod.GET, params = { "subListWithSectionId" })
	public @ResponseBody Map<String, Map<String,String>>  getSubListsBySection(HttpSession session,
			HttpServletRequest httpSevletRequest, HttpServletResponse response,
			ModelMap model) throws IOException {	
		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		Map<String, Map<String,String>> map = null;
		String courseId = httpSevletRequest.getParameter("courseId");
		String trmId = httpSevletRequest.getParameter("termId");			
		String secId = httpSevletRequest.getParameter("secId");			
		try {
		String stuGrpId = 	admissionService.getStudentGrpId(sessionCache.getUserSessionDetails(), courseId, trmId, secId);			
			map=admissionService.getSubFromCourseAndTrm(stuGrpId,courseId, trmId, sessionCache.getUserSessionDetails());
						
		} catch (NoDataFoundException e) {
			logger.error("No Data Found For Subject List :" + e.getMessage());			
		}	
		httpSevletRequest.setAttribute("allSubList", map);		
		return map;
		//return "Success";

	}
	@ModelAttribute("courseList")
	public Map<String, String> gerCourseNameList(HttpSession session,
			HttpServletRequest httpSevletRequest, HttpServletResponse response,
			ModelMap model) throws IOException {
		Map<String, String> map = null;
		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		try {
			map = dropDownListService.getCourseNameListTag(sessionCache.getUserSessionDetails());
		} catch (NoDataFoundException e) {
			logger.error("Error occured in Course Name list Dropdown :" + e.getMessage());
		}
		httpSevletRequest.setAttribute("courseList", map);
		model.addAttribute("courseList", map);
		return map;

	}
		
	@RequestMapping(value = "/secAndSubList", method = RequestMethod.GET, params = { "secList" })
	public @ResponseBody  Map<String, String> getSecList(HttpSession session,
			HttpServletRequest request, HttpServletResponse response,
			ModelMap model) throws IOException {		
		Map<String, String> map = null;
		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		String courseId = request.getParameter("courseId");
		String trmId = request.getParameter("termId");
		String admisNo ="";
		if( request.getParameter("admisNo")!=null){
			admisNo	= request.getParameter("admisNo");
		}
		
		try {
			String stuGrpId = null;
			if(!admisNo.equals("")){
			stuGrpId = admissionService.getStuGrpIdForSecList(admisNo, sessionCache.getUserSessionDetails());		
			}
		
			map = dropDownListService.getSectionList(sessionCache.getUserSessionDetails(),courseId,trmId,stuGrpId);
		} catch (NoDataFoundException e) {
			logger.error("Error occured in Section list Dropdown :" + e.getMessage());
		}		
	//	request.setAttribute("secList", map);	
		return map;

	}
	
	public ModelAndView getAdmissionPage(HttpServletRequest request,HttpSession session,AdmissionDetailsVO admissionDetailsVO){

		
		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
				
		INBR inbr = INBR.valueOf(sessionCache.getUserSessionDetails().getInbrCategory());
	//	WebUtils.setSessionAttribute(request, "success", null);
		ModelAndView modelAndView = null;
		switch(inbr){
		case DC:case PUC:{
			modelAndView = new ModelAndView(".jaw.newClgeAdmission");
			break;
		}
		case ICSE:case SSLC:{
			modelAndView = new ModelAndView(".jaw.newSklAdmission");
			break;
		}										
			
		default:
			break;
		}				
		
		modelAndView.getModelMap().addAttribute("siblingDetails",
				siblingDetailsVO);
		modelAndView.getModelMap().addAttribute("preSportPartDetails",
				preSportParticipationDetailsVO);	
		admissionDetailsVO.setAcademicYear(sessionCache.getUserSessionDetails().getCurrAcTerm());		
		System.out.println("Radio buttons values:"+RadioButtons.values());
		modelAndView.getModel().put("radioButtons", RadioButtons.values());
		return modelAndView; 
	
	}
	
	@ModelAttribute("termList")
	public Map<String, String> getTermList(HttpSession session,
			HttpServletRequest httpSevletRequest, HttpServletResponse response,
			ModelMap model) throws IOException {
		Map<String, String> map = null;
		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		try {
			map = dropDownListService.getTermList(sessionCache.getUserSessionDetails());
		} catch (NoDataFoundException e) {
			logger.error("Error occured in Term list Dropdown :" + e.getMessage());
		}		
		httpSevletRequest.setAttribute("termList", map);	
		return map;

	}
	
	public enum INBR {
		DC,PUC,SSLC,ICSE;
		}


	@ExceptionHandler({ FileNotFoundInDatabase.class,
			DuplicateEntryException.class,InsertFailedException.class,FileNotSaveException.class,
			 UnableCreateParentUserId.class, UnableCreateParentPassword.class, UnableCreateStudentUserId.class, 
			 UnableCreateStudentPassword.class
			})
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
