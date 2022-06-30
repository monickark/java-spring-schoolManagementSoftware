package com.jaw.user.controller;

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
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import com.jaw.common.constants.ApplicationConstant;
import com.jaw.common.dropdown.service.IDropDownListService;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.FileNotFoundInDatabase;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.login.InvalidUserIdException;
import com.jaw.common.exceptions.util.PropertyNotFoundException;
import com.jaw.common.files.service.IFileMasterService;
import com.jaw.common.util.ProfileGroup;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.sessCache.SessionCache;
import com.jaw.mgmtUser.controller.MgmtUserVO;
import com.jaw.mgmtUser.service.IMgmtUserDetailsService;
import com.jaw.nonStaff.service.INonStaffService;
import com.jaw.staff.controller.StaffVo;
import com.jaw.staff.service.IStaffInsertionService;
import com.jaw.student.admission.controller.AdmissionDetailsVO;
import com.jaw.student.admission.controller.FileTypeVO;
import com.jaw.student.admission.controller.PreSportParticipationDetailsVO;
import com.jaw.student.admission.controller.SiblingDetailsVO;
import com.jaw.student.admission.controller.AdmissionController.INBR;
import com.jaw.student.service.IViewProfileService;
import com.jaw.user.service.IUserManagementService;

//Common Controller class for Viewing a file
@Controller
public class ProfileController {
	
	// Logging
	Logger logger = Logger.getLogger(ProfileController.class);
	@Autowired
	private IViewProfileService viewProfileService;
	@Autowired
	IFileMasterService fileMasterService;
	@Autowired
	private IStaffInsertionService staffInsertionService;
	@Autowired
	private IMgmtUserDetailsService managementDetailsService;
	@Autowired
	IUserManagementService userManagementService;
	@Autowired
	private INonStaffService nonStaffService;
	@Autowired
	IDropDownListService dropDownListService;
	
	private final List<PreSportParticipationDetailsVO> preSportParticipationDetailsVO = new ArrayList<PreSportParticipationDetailsVO>();
	private final List<SiblingDetailsVO> siblingDetailsVO = new ArrayList<SiblingDetailsVO>();	

	@RequestMapping(value = "/viewuser")
	public ModelAndView viewUser(
			@ModelAttribute("viewuser") AdmissionDetailsVO admissionDetailsVO,
			HttpSession session,HttpServletRequest httpRequest) throws FileNotFoundInDatabase,
			NoDataFoundException, InvalidUserIdException, PropertyNotFoundException {
		ModelAndView modelAndView = null;
		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		ApplicationCache applicationCache = (ApplicationCache) session
				.getServletContext().getAttribute(
						ApplicationConstant.APPLICATION_CACHE);	
		admissionDetailsVO.setAcademicYear(sessionCache.getUserSessionDetails().getCurrAcTerm());		
		ProfileGroup profileGroup = ProfileGroup.valueOf(sessionCache.getUserSessionDetails()
				.getProfileGroup());
		WebUtils.setSessionAttribute(httpRequest, "success", null);
		FileTypeVO fileType = null;
		
				
				switch (profileGroup) {
				case PAR: {
					admissionDetailsVO.setStudentAdmisNo(sessionCache
							.getParentSession().getStudentSession().get(Integer.valueOf(sessionCache.getParentSession().getSelectedStuIndex()))
							.getStudentAdmisNo().trim());					
					
											
					INBR inbr = INBR.valueOf(sessionCache.getUserSessionDetails().getInbrCategory());
					switch(inbr){
					
						
					case PUC: case DC:{
						modelAndView = new ModelAndView(".jaw.viewClgeStudent");
						admissionDetailsVO.setSiblingDetailsVO(null);
						
						break;
					}
						
					case SSLC:case ICSE:{
						modelAndView = new ModelAndView(".jaw.viewSklStudent");	
						
						break;
					}
						
					default:
						break;
					}
					
				
					fileType = viewProfileService.getFileType(
							sessionCache.getUserSessionDetails(), sessionCache
									.getParentSession().getStudentSession().get(Integer.valueOf(sessionCache.getParentSession().getSelectedStuIndex()))
									.getStudentAdmisNo().trim());
					viewProfileService.viewStudentDetails(admissionDetailsVO,
							sessionCache.getUserSessionDetails(), applicationCache);
					
					modelAndView.getModelMap().addAttribute("siblingDetails",
							admissionDetailsVO.getSiblingDetailsVO());
				modelAndView.getModelMap().addAttribute("preSportPartDetails",
						admissionDetailsVO.getPreSportPartDetails());			
				break;
			}
			case STU: {
				
				admissionDetailsVO.setStudentAdmisNo(sessionCache
						.getStudentSession().getStudentAdmisNo());				
				
				INBR inbr = INBR.valueOf(sessionCache.getUserSessionDetails().getInbrCategory());
				switch(inbr){				
					
				case PUC:case DC:{
					modelAndView = new ModelAndView(".jaw.viewClgeStudent");	
					admissionDetailsVO.setSiblingDetailsVO(null);				
					break;
				}
					
				case SSLC:case ICSE:{
					modelAndView = new ModelAndView(".jaw.viewSklStudent");
					
							
					break;
				}
					
				default:
					break;
				}
				
				viewProfileService.viewStudentDetails(admissionDetailsVO,
						sessionCache.getUserSessionDetails(), applicationCache);
				fileType = viewProfileService.getFileType(
						sessionCache.getUserSessionDetails(), sessionCache
								.getStudentSession().getStudentAdmisNo().trim());
							
				modelAndView.getModelMap().addAttribute("preSportPartDetails",
						admissionDetailsVO.getPreSportPartDetails());	
				modelAndView.getModelMap().addAttribute("siblingDetails",
						admissionDetailsVO.getSiblingDetailsVO());					
				break;
			}
			case STF: {
				
				StaffVo staffAdmissionVo = new StaffVo();
				staffInsertionService.selectStaff(staffAdmissionVo,
						sessionCache.getUserSessionDetails());
				modelAndView = new ModelAndView(".jaw.StaffViewProfile", "staff",
						staffAdmissionVo);
				break;
			}
			case MGT: {
				MgmtUserVO managementVO = new MgmtUserVO();
				managementDetailsService.selectManagement(managementVO,
						sessionCache.getUserSessionDetails());
				modelAndView = new ModelAndView(".jaw.managementViewProfile",
						"singleUser", managementVO);
				break;
			}
			case NSF: {
				BranchAdminVO branchAdminVO = new BranchAdminVO();
				nonStaffService.getSingleNonStaff(branchAdminVO,
						sessionCache.getUserSessionDetails());
				modelAndView = new ModelAndView(".jaw.nonStaffViewProfile",
						"singleUser", branchAdminVO);
				break;
			}
		}				
		if (fileType != null) {
			modelAndView.getModelMap().addAttribute("fileType", fileType);
		}
		
		return modelAndView;
	}
	
	@RequestMapping(value = "/edituser")
	public ModelAndView editUser(
			@ModelAttribute("viewuser") AdmissionDetailsVO admissionDetailsVO,
			HttpSession session, HttpServletRequest httpServletRequest)
			throws NoDataFoundException, InvalidUserIdException,
			FileNotFoundInDatabase, PropertyNotFoundException {		
		
		ModelAndView mav =  editUserRender(session,admissionDetailsVO,httpServletRequest);
		WebUtils.setSessionAttribute(httpServletRequest, "success", null);
		return mav;
	}
	
	@RequestMapping(value = "/edituser",method=RequestMethod.GET,params = "data")
	public ModelAndView editUserRender(
			@ModelAttribute("viewuser") AdmissionDetailsVO admissionDetailsVO,
			HttpSession session, HttpServletRequest httpServletRequest)
			throws NoDataFoundException, InvalidUserIdException,
			FileNotFoundInDatabase, PropertyNotFoundException {
		
		return  editUserRender(session,admissionDetailsVO,httpServletRequest);			
	}
	
	
	public ModelAndView editUserRender(HttpSession session,AdmissionDetailsVO admissionDetailsVO,HttpServletRequest httpServletRequest) throws PropertyNotFoundException, NoDataFoundException, FileNotFoundInDatabase, InvalidUserIdException{

		ModelAndView modelAndView = null; 				
		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		ApplicationCache applicationCache = (ApplicationCache) session
				.getServletContext().getAttribute(
						ApplicationConstant.APPLICATION_CACHE);
		admissionDetailsVO.setAcademicYear(sessionCache.getUserSessionDetails().getCurrAcTerm());
		
		ProfileGroup profileGroup = ProfileGroup.valueOf(sessionCache.getUserSessionDetails()
				.getProfileGroup());		
		FileTypeVO fileType= null;
		switch (profileGroup) {
			case PAR: {
				admissionDetailsVO.setStudentAdmisNo(sessionCache
						.getParentSession().getStudentSession().get(Integer.valueOf(sessionCache.getParentSession().getSelectedStuIndex()))
						.getStudentAdmisNo().trim());
				
				 
				 
				 INBR inbr = INBR.valueOf(sessionCache.getUserSessionDetails().getInbrCategory());
					switch(inbr){
					case DC:case PUC:{
						 modelAndView = new ModelAndView(".jaw.editClgeParent");
						 admissionDetailsVO.setSiblingDetailsVO(null);
							break;
					}										
					case SSLC:case ICSE:{
						 modelAndView = new ModelAndView(".jaw.editSklParent");
												
							
						break;
					}
						
					default:
						break;
					}
				 
				 
				//For Previous Sports Part Details
				 preSportParticipationDetailsVO.clear();				
					if(admissionDetailsVO.getPreSportPartDetails().size()!=0){						
						preSportParticipationDetailsVO.addAll(admissionDetailsVO.getPreSportPartDetails());
					}
					if(admissionDetailsVO.getPreSportPartDetails().size()<ApplicationConstant.SIBLING_UITEXTBOX_COUNT){
						for (int count = admissionDetailsVO.getPreSportPartDetails().size(); count < ApplicationConstant.SIBLING_UITEXTBOX_COUNT; count++) {						
							preSportParticipationDetailsVO.add(new PreSportParticipationDetailsVO());
						}	
					}
					modelAndView.getModelMap().addAttribute("preSportPartDetails",
							preSportParticipationDetailsVO);
					
					
					viewProfileService.viewStudentDetails(admissionDetailsVO,
							sessionCache.getUserSessionDetails(), applicationCache);
					 fileType = viewProfileService.
							getFileType(sessionCache.getUserSessionDetails(),admissionDetailsVO.getStudentAdmisNo().trim());
					 
					 
					//Sibling Details
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
						}
							
							modelAndView.getModelMap().addAttribute("siblingDetails",
									admissionDetailsVO.getSiblingDetailsVO());
								
				break;
			}
			case STU: {
				admissionDetailsVO.setStudentAdmisNo(sessionCache
						.getStudentSession().getStudentAdmisNo());
			
				 
				 INBR inbr = INBR.valueOf(sessionCache.getUserSessionDetails().getInbrCategory());
					switch(inbr){
					case DC:case PUC:{
						 modelAndView = new ModelAndView(".jaw.editClgeStudent");
						 admissionDetailsVO.setSiblingDetailsVO(null);
						break;
					}					
						
					case SSLC:case ICSE:{
						 modelAndView = new ModelAndView(".jaw.editSklStudent");

						
							
						break;
					}
						
					default:
						break;
					}
				 
				//For Previous Sports Part Details
				 preSportParticipationDetailsVO.clear();
					if(admissionDetailsVO.getPreSportPartDetails().size()!=0){						
						preSportParticipationDetailsVO.addAll(admissionDetailsVO.getPreSportPartDetails());
					}
					if(admissionDetailsVO.getPreSportPartDetails().size()<ApplicationConstant.SIBLING_UITEXTBOX_COUNT){
						for (int count = admissionDetailsVO.getPreSportPartDetails().size(); count < ApplicationConstant.SIBLING_UITEXTBOX_COUNT; count++) {				
							preSportParticipationDetailsVO.add(new PreSportParticipationDetailsVO());
						}	
					}
					modelAndView.getModelMap().addAttribute("preSportPartDetails",
							preSportParticipationDetailsVO);
					
					
					viewProfileService.viewStudentDetails(admissionDetailsVO,
							sessionCache.getUserSessionDetails(), applicationCache);
					 fileType = viewProfileService.
							 getFileType(sessionCache.getUserSessionDetails(),admissionDetailsVO.getStudentAdmisNo().trim());					
					//Sibling Details
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
					}
						
						modelAndView.getModelMap().addAttribute("siblingDetails",
								admissionDetailsVO.getSiblingDetailsVO());
					
					
					
				break;
			}
			case STF: {
				
				StaffVo staffAdmissionVo = new StaffVo();
				staffInsertionService.selectStaff(staffAdmissionVo,
						sessionCache.getUserSessionDetails());
				modelAndView = new ModelAndView(".jaw.StaffEditProfile", "staff",
						staffAdmissionVo);
				WebUtils.setSessionAttribute(httpServletRequest, "staffResetValue",
						staffAdmissionVo);
				break;
			}
			case MGT: {
				MgmtUserVO managementVO = new MgmtUserVO();
				managementDetailsService.selectManagement(managementVO,
						sessionCache.getUserSessionDetails());
				modelAndView = new ModelAndView(".jaw.managementEdit",
						"singleUser", managementVO);
				break;
			}
			case NSF: {
				BranchAdminVO branchAdminVO = new BranchAdminVO();
				nonStaffService.getSingleNonStaff(branchAdminVO,
						sessionCache.getUserSessionDetails());
				modelAndView = new ModelAndView(".jaw.nonStaffEdit", "singleUser",
						branchAdminVO);
				break;
			}
		}
		
		modelAndView.getModelMap().addAttribute("actcontrol", "useredit");
		if (fileType != null){
			modelAndView.getModelMap().addAttribute("fileType", fileType);
		}	
		return modelAndView;
	
		
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
			map = dropDownListService.getTermList(sessionCache.getUserSessionDetails());
		} catch (NoDataFoundException e) {
			logger.error("Error occured in Term list Dropdown :" + e.getMessage());
		}		
		httpSevletRequest.setAttribute("termList", map);
		return map;

	}
	
	@ModelAttribute("acTermList")
	public Map<String, String> gerAcaTrmList(HttpSession session,
			HttpServletRequest httpSevletRequest, HttpServletResponse response,
			ModelMap model) throws IOException {
		SessionCache sessionCache = (SessionCache) session
				.getAttribute(ApplicationConstant.SESSION_CACHE_KEY);
		Map<String, String> map = null;

		try {
			map = dropDownListService.getAcademicTermListForPresentAndFuture(sessionCache
					.getUserSessionDetails());
		} catch (NoDataFoundException e) {
			logger.error("Error occured in Academic Term Dropdown :" + e.getMessage());			
		}
		httpSevletRequest.setAttribute("acTermList", map);
		return map;

	}	
	@ExceptionHandler({
			DuplicateEntryException.class,
			FileNotFoundInDatabase.class
	})
	public ModelAndView handleException(Exception ex, HttpSession session,
			HttpServletRequest request) {
		logger.info("Error in staff Admission or staff profile");
		ModelAndView mav = (ModelAndView) request.getAttribute("page");
		mav.getModelMap().addAttribute("error", ex.getMessage());
		logger.info("Redirecting with error message");
		return mav;
		
	}
	
}
