package com.jaw.login.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jaw.admin.dao.BranchMaster;
import com.jaw.admin.dao.BranchMasterKey;
import com.jaw.admin.dao.IBranchMasterDAO;
import com.jaw.admin.dao.IInstituteMasterDAO;
import com.jaw.admin.dao.InstituteMaster;
import com.jaw.common.business.CommonBusiness;
import com.jaw.common.constants.ApplicationConstant;
import com.jaw.common.constants.PropertyManagementConstant;
import com.jaw.common.exceptions.DatabaseException;
import com.jaw.common.exceptions.FileNotFoundInDatabase;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.login.SessionCacheNotLoadedException;
import com.jaw.common.exceptions.util.PropertyNotFoundException;
import com.jaw.common.files.dao.IFileMasterDao;
import com.jaw.common.util.CommonCodeUtil;
import com.jaw.common.util.DateUtil;
import com.jaw.common.util.PropertyManagementUtil;
import com.jaw.core.dao.AcademicTermDetails;
import com.jaw.core.dao.AcademicTermDetailsKey;
import com.jaw.core.dao.IAcademicTermDetailsDAO;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.appCache.dao.MenuProfileOptionLinking;
import com.jaw.framework.sessCache.ManagementSession;
import com.jaw.framework.sessCache.NonStaffSession;
import com.jaw.framework.sessCache.ParentSession;
import com.jaw.framework.sessCache.SessionCache;
import com.jaw.framework.sessCache.StaffSession;
import com.jaw.framework.sessCache.StudentSession;
import com.jaw.framework.sessCache.UserSessionDetails;
import com.jaw.framework.sessCache.dao.IManagementSessionDAO;
import com.jaw.framework.sessCache.dao.INonStaffSessionDAO;
import com.jaw.framework.sessCache.dao.IParentSessionDao;
import com.jaw.framework.sessCache.dao.IStaffSessionDAO;
import com.jaw.framework.sessCache.dao.IStudentSessionDao;
import com.jaw.mgmtUser.dao.IMgmtUserDetailsDAO;
import com.jaw.nonStaff.dao.INonStaffDetailsDAO;
import com.jaw.nonStaff.dao.NonStaff;
import com.jaw.student.admission.dao.ParentDetails;
import com.jaw.student.admission.dao.StudentMasterKey;
import com.jaw.user.dao.User;
import com.jaw.user.dao.UserLink;

@Component
public class LoginSCHelper {
	
	Logger logger = Logger.getLogger(LoginSCHelper.class);
	@Autowired
	ApplicationCache applicationCache;
	@Autowired
	com.jaw.security.SecurityCheck securityCheck;
	@Autowired
	PropertyManagementUtil propertyManagementUtil;
	@Autowired
	IStudentSessionDao studentSessionDao;
	@Autowired
	private IParentSessionDao parentSessionDao;
	@Autowired
	private IFileMasterDao fileMasterDAO;
	@Autowired
	private CommonBusiness commonBusiness;
	@Autowired
	private DateUtil dateUtil;
	@Autowired
	private IManagementSessionDAO managementSessionDao;
	@Autowired
	private IMgmtUserDetailsDAO managementDetailsDAO;
	@Autowired
	private INonStaffSessionDAO nonStaffSessionDAO;
	@Autowired
	private INonStaffDetailsDAO nonStaffDetailsDAO;
	@Autowired
	private IStaffSessionDAO staffSessionDao;
	@Autowired
	private IAcademicTermDetailsDAO academicTermDetailsDao;
	@Autowired
	IInstituteMasterDAO instituteMasterDao;
	@Autowired
	IBranchMasterDAO branchMasDao;
	@Autowired
	CommonCodeUtil commonCodeUtil;
	
	public enum Grade {
		PAR, STU, MGT, NSF, STF
	};
	
	public void setUserSessionObject(SessionCache sessionCache, User user,
			UserLink userLink) throws
			SessionCacheNotLoadedException, PropertyNotFoundException, NoDataFoundException, DatabaseException {
		String inbrCategory = null;
		UserSessionDetails userSessionDetails = new UserSessionDetails();
		 String acaTermDetails = null;
		user.setUserEnableFlag("Y");
		commonBusiness.changeObject(userSessionDetails, user);
		commonBusiness.changeObject(userSessionDetails, userLink);
		sessionCache.setUserSessionDetails(userSessionDetails);
		
		Grade cadre = Grade.valueOf(sessionCache.getUserSessionDetails()
				.getProfileGroup());
		userSessionDetails = sessionCache.getUserSessionDetails();
		if((!sessionCache.getUserSessionDetails().getUserMenuProfile().equals(ApplicationConstant.SUPER_ADMIN)) && 
		(!sessionCache.getUserSessionDetails().getProfileGroup().equals(ApplicationConstant.PG_MGMT)))
		{
		//Getting Ac term details
		AcademicTermDetailsKey academicTermDetailsKey = new AcademicTermDetailsKey();
		academicTermDetailsKey.setInstId(userSessionDetails.getInstId());
		academicTermDetailsKey.setBranchId(userSessionDetails.getBranchId());		
		acaTermDetails = academicTermDetailsDao.getCurrentAcademicTerm(userSessionDetails.getInstId(),userSessionDetails.getBranchId(),ApplicationConstant.ACT_TRM_PRESENT);
		if((acaTermDetails.equals(""))||(acaTermDetails==null)){
			throw new SessionCacheNotLoadedException();
		}else{			
			userSessionDetails.setCurrAcTerm(acaTermDetails);
		}
		
		//Getting INBR category				
		if(userSessionDetails.getInstId().equals(userSessionDetails.getBranchId())){
			InstituteMaster instMas = instituteMasterDao.selectInstituteMasterRec(null, userSessionDetails.getInstId());
			inbrCategory = instMas.getInstCategory();
					
		}else{
			BranchMasterKey branchMasKey = new BranchMasterKey();
			branchMasKey.setInstId(userSessionDetails.getInstId());
			branchMasKey.setBranchId(userSessionDetails.getBranchId());
			
			BranchMaster branchMas = branchMasDao.selectBranchMaster(branchMasKey);
			inbrCategory = branchMas.getBranchCategory();			
			
		}
		if((inbrCategory.equals(""))||(inbrCategory==null)){
				throw new SessionCacheNotLoadedException();
			}else{
				userSessionDetails.setInbrCategory(inbrCategory);
			}	
						
		}
		
		
		switch (cadre) {
			case PAR: {
				ParentDetails parentDetails = parentSessionDao
						.retriveParentDetails(userSessionDetails.getLinkId(),
								userSessionDetails.getInstId(), userSessionDetails.getBranchId());						
				
				List<StudentSession> studentMaster = parentSessionDao
						.retriveStuParent(userSessionDetails.getLinkId(), userSessionDetails.getInstId(),
								userSessionDetails.getBranchId(),acaTermDetails);				
				InputStream parentPhoto = null;
				
				ParentSession parentSession = new ParentSession();			
				userSessionDetails.setUserName(parentDetails.getFatherName());
				parentSession.setParentName(parentDetails.getFatherName());
				parentSession.setStudentSession(studentMaster);			
				sessionCache.setParentSession(parentSession);
				try {
					parentPhoto = fileMasterDAO.getSingleFile(
							userSessionDetails.getInstId(),
							userSessionDetails.getBranchId(),
							studentMaster.get(0).getStudentAdmisNo(),
							ApplicationConstant.FATHER_PHOTO_KEY,ApplicationConstant.DEFAULT_FILE_SRL_NO).getInputStream();
					byte[] bytes = null;
					try {
						if(parentPhoto!=null){
							bytes = IOUtils.toByteArray(parentPhoto);
							userSessionDetails.setUserPhoto(bytes);
						}
						
					}
					catch (IOException e) {
					}
					
				}
				catch (FileNotFoundInDatabase e) {
					
				}
				
				logger.debug("Parent session object");
			}
				break;
			
			case STU: {
				StudentMasterKey studentMasterKey = new StudentMasterKey();
				studentMasterKey.setInstId(userSessionDetails.getInstId());
				studentMasterKey.setBranchId(userSessionDetails.getBranchId());				
				studentMasterKey.setStudentAdmisNo(userSessionDetails.getLinkId());
				studentMasterKey.setAcademicYear(acaTermDetails);			
				StudentSession studentSession = studentSessionDao.selectStudentGrpId(studentMasterKey);					
				userSessionDetails.setUserName(studentSession.getStudentName());
				sessionCache.setStudentSession(studentSession);					
				InputStream studentPhoto = null;
				try {
					studentPhoto = fileMasterDAO.getSingleFile(
							userSessionDetails.getInstId(),
							userSessionDetails.getBranchId(),
							studentSession.getStudentAdmisNo(),
							ApplicationConstant.STUDENT_PHOTO_KEY,ApplicationConstant.DEFAULT_FILE_SRL_NO).getInputStream();
					byte[] bytes = null;
					try {
						if(studentPhoto!=null){
						bytes = IOUtils.toByteArray(studentPhoto);
						}
					}
					catch (IOException e) {
					}
					userSessionDetails.setUserPhoto(bytes);
				}
				catch (FileNotFoundInDatabase e1) {
					
				}
				
				logger.debug("Student session object ");
			}
				break;
			
			case MGT: {
				ManagementSession session = managementSessionDao
						.selectManagementSessionRec(userSessionDetails.getLinkId(),
								userSessionDetails.getInstId(), userSessionDetails.getBranchId());
				
				userSessionDetails.setUserName(session.getManagementName());
				InputStream mangementPhoto = null;
				
				try {
					mangementPhoto = fileMasterDAO.getSingleFile(
							userSessionDetails.getInstId(),
							userSessionDetails.getBranchId(),
							session.getManagementId(),
							ApplicationConstant.MANAGEMENT_PHOTO,ApplicationConstant.DEFAULT_FILE_SRL_NO).getInputStream();
					byte[] bytes = null;
					try {
						if(mangementPhoto!=null){
							bytes = IOUtils.toByteArray(mangementPhoto);
						}
					
					}
					catch (IOException e) {
					}
					userSessionDetails.setUserPhoto(bytes);
				}
				catch (FileNotFoundInDatabase e1) {
					
				}
				
				logger.debug("Admin session object ");
			}
				break;
			
			case STF: {
				
				StaffSession session = staffSessionDao
						.selectStaffSessionRec(userSessionDetails.getLinkId(),
								userSessionDetails.getInstId(), userSessionDetails.getBranchId());
				
				userSessionDetails.setUserName(session.getStaffName());
				InputStream staffFoto = null;
				try {
					staffFoto = fileMasterDAO.getSingleFile(
							userSessionDetails.getInstId(),
							userSessionDetails.getBranchId(), session.getStaffId(),
							ApplicationConstant.STAFF_PHOTO,ApplicationConstant.DEFAULT_FILE_SRL_NO).getInputStream();
					byte[] bytes = null;
					try {
						if(staffFoto!=null){
						bytes = IOUtils.toByteArray(staffFoto);
						}
					}
					catch (IOException e) {
					}
					userSessionDetails.setUserPhoto(bytes);
				}
				catch (FileNotFoundInDatabase e1) {
					
				}
				
				logger.debug("Staff session object ");
			}
				break;
			
			case NSF: {
				NonStaffSession session = nonStaffSessionDAO
						.selectNonStaffRec(userSessionDetails.getLinkId(),
								userSessionDetails.getInstId(), userSessionDetails.getBranchId());
				NonStaff nonStaff;
				try {
					nonStaff = nonStaffDetailsDAO.selectNonStaffRec(
							userSessionDetails.getInstId(),
							userSessionDetails.getBranchId(),
							userSessionDetails.getLinkId());
				}
				catch (NoDataFoundException e2) {
					throw new SessionCacheNotLoadedException();
				}
				
				userSessionDetails.setUserName(session.getName());
				InputStream nonStaffFoto = null;
				
				try {
					nonStaffFoto = fileMasterDAO.getSingleFile(
							userSessionDetails.getInstId(),
							userSessionDetails.getBranchId(),
							nonStaff.getStaffId(),
							ApplicationConstant.NON_STAFF_PHOTO,ApplicationConstant.DEFAULT_FILE_SRL_NO).getInputStream();
					byte[] bytes = null;
					try {
						if(nonStaffFoto!=null){
							bytes = IOUtils.toByteArray(nonStaffFoto);
						}
						
					}
					catch (IOException e) {
					}
					userSessionDetails.setUserPhoto(bytes);
				}
				catch (FileNotFoundInDatabase e1) {
					
				}
				
				logger.debug("No Staff session object ");
			}
				break;
		}
		logger.debug("before getting menus ");
		setUserMenuAndUrl(applicationCache, userLink, sessionCache);
		logger.debug("after getting menus ");
	}
	
	private void setUserMenuAndUrl(ApplicationCache applicationCache,
			UserLink userLink, SessionCache sessionCache) throws SessionCacheNotLoadedException {
		
		List<MenuProfileOptionLinking> userMenu = new ArrayList<MenuProfileOptionLinking>();
		Map<String, ArrayList<MenuProfileOptionLinking>> menuProfileOptionLinking = applicationCache
				.getMenuProfileList();
		List<String> allowedUrl = new ArrayList<String>();
		String key = userLink.getInstId() + "-" + userLink.getBranchId() + "-"
				+ userLink.getUserMenuProfile();
				System.out.println("user menu key :"+key);
		userMenu = menuProfileOptionLinking.get(key);
		sessionCache.setOptionLinkings(userMenu);
		if (userMenu == null) {
			throw new SessionCacheNotLoadedException();
		}
		for (MenuProfileOptionLinking linking : userMenu) {
			allowedUrl.add(linking.getMenuUrl());
		}
		sessionCache.setAllowedUrl(allowedUrl);
		
	}
}
