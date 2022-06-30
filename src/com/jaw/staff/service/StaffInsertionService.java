package com.jaw.staff.service;

import java.io.IOException;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jaw.admin.service.InstituteMasterService;
import com.jaw.common.business.CommonBusiness;
import com.jaw.common.constants.ApplicationConstant;
import com.jaw.common.constants.AuditConstant;
import com.jaw.common.exceptions.DatabaseException;
import com.jaw.common.exceptions.DeleteFailedException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.FileNotFoundInDatabase;
import com.jaw.common.exceptions.FileNotSaveException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.util.PropertyNotFoundException;
import com.jaw.common.files.service.FileMasterHelper;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.audit.service.DoAudit;
import com.jaw.framework.sessCache.UserSessionDetails;
import com.jaw.staff.controller.StaffDetailsVo;
import com.jaw.staff.controller.StaffMasterVo;
import com.jaw.staff.controller.StaffVo;
import com.jaw.staff.dao.IStaffDetailsDAO;
import com.jaw.staff.dao.IStaffMasterDAO;
import com.jaw.staff.dao.StaffDetails;
import com.jaw.staff.dao.StaffMaster;
import com.jaw.user.controller.UserCreationVO;
import com.jaw.user.util.UserCreation;

//Service class for FileMaster 
@Service
public class StaffInsertionService implements IStaffInsertionService {
	
	// Logging
	Logger logger = Logger.getLogger(InstituteMasterService.class);
	
	@Autowired
	IStaffMasterDAO staffMasterDAO;
	@Autowired
	IStaffDetailsDAO staffDetailsDAO;
	@Autowired
	CommonBusiness commonBusiness;
	@Autowired
	UserCreation userCreation;
	@Autowired
	DoAudit doAudit;
	@Autowired
	FileMasterHelper fileMasterHelper;
	
	@Transactional(rollbackFor = Exception.class)
	@Override
	public void insertStaff(StaffVo staffVo,
			UserSessionDetails userSessionDetails,
			ApplicationCache applicationCache,ServletContext servletContext) throws DuplicateEntryException,
			FileNotFoundInDatabase, DatabaseException, NumberFormatException,
			PropertyNotFoundException, IllegalStateException, IOException, DeleteFailedException, FileNotSaveException {
		StaffMaster staffMaster = new StaffMaster();
		StaffDetails staffDetails = new StaffDetails();
		commonBusiness.changeObject(staffMaster,
				staffVo.getStaffMasterVo());
		commonBusiness.changeObject(staffDetails,
				staffVo.getStaffDetailsVo());
		logger.debug("Input branch id :" + staffVo.getBranchId());
		staffMaster.setDbTs(1);
		staffMaster.setDelFlg("N");
		staffMaster.setInstId(userSessionDetails.getInstId());
		
		if (staffVo.getBranchId() == null) {
			staffMaster.setBranchId(userSessionDetails.getBranchId());
		}
		else {
			staffMaster.setBranchId(staffVo.getBranchId());
		}
		staffMaster.setrCreId(userSessionDetails.getUserId());
		staffMaster.setrModId(userSessionDetails.getUserId());
		
		staffDetails.setStaffId(staffMaster.getStaffId());
		staffDetails.setStaffName(staffMaster.getStaffName());
		staffDetails.setDbTs(1);
		staffDetails.setDelFlg("N");
		staffDetails.setInstId(userSessionDetails.getInstId());
		
		if (staffVo.getBranchId() == null) {
			staffDetails.setBranchId(userSessionDetails.getBranchId());
		}
		else {
			staffDetails.setBranchId(staffVo.getBranchId());
		}
		staffDetails.setrCreId(userSessionDetails.getUserId());
		staffDetails.setrModId(userSessionDetails.getUserId());
		
		staffMasterDAO.insertStaffMaster(staffMaster);
		
		staffDetailsDAO.insertStaffDetails(staffDetails);
		
		fileMasterHelper.fileUpload(applicationCache, staffVo
				.getStaffPhoto(), ApplicationConstant.STAFF_PHOTO, 1,
				userSessionDetails, userSessionDetails.getUserId(),
				userSessionDetails.getInstId(), staffVo.getBranchId(),
				staffVo.getStaffMasterVo().getStaffId(),ApplicationConstant.DEFAULT_FILE_SRL_NO,ApplicationConstant.PG_STAFF,servletContext);
		staffVo.getFileTypeVO().setStaffId(staffVo.getStaffMasterVo().getStaffId());
		fileMasterHelper.fileUpload(applicationCache, staffVo.getFileTypeVO(), userSessionDetails,ApplicationConstant.PG_STAFF,servletContext);
		logger.debug("Staff user created or not :"
				+ staffVo.getIsUser());
		if (staffVo.getIsUser() != null) {
			
			UserCreationVO userCreationVO = new UserCreationVO();
			userCreationVO.setApplicationCache(applicationCache);
			userCreationVO.setBranchId(staffVo.getBranchId());
			userCreationVO.setInstId("");
			userCreationVO.setLinkId(staffVo
					.getStaffMasterVo().getStaffId());
			userCreationVO.setMenuProfile(staffVo.getMenuProfile());
			userCreationVO.setPassword(staffVo.getPassword());
			userCreationVO.setProfileGroup(ApplicationConstant.PG_STAFF);
			userCreationVO.setRole("");
			userCreationVO.setUserId(staffVo.getUserid());
			userCreationVO.setUserSessionDetails(userSessionDetails);
			userCreation.createUser(userCreationVO);
			String remarks = "inst_id:" + staffMaster.getInstId()
					+ ",branch_id:" + staffMaster.getBranchId() + ",staff_id:"
					+ staffMaster.getStaffId();
			doAudit.doFunctionalAudit(userSessionDetails,
					AuditConstant.STF_USER_CREATE, remarks);
		}
	}
	
	@Override
	public void selectStaff(StaffVo staffAdmissionVo,
			UserSessionDetails userSessionDetails)
			throws FileNotFoundInDatabase, NoDataFoundException {
		
		StaffMaster staffMaster = staffMasterDAO.selectStaffRec(
				userSessionDetails.getLinkId(), userSessionDetails.getInstId(),
				userSessionDetails.getBranchId());
		
		StaffDetails staffDetails = staffDetailsDAO.selectStaffRec(
				userSessionDetails.getInstId(),
				userSessionDetails.getBranchId(),
				userSessionDetails.getLinkId());
		
		StaffMasterVo staffMasterVo = new StaffMasterVo();
		StaffDetailsVo staffDetailsVo = new StaffDetailsVo();
		commonBusiness.changeObject(staffMasterVo, staffMaster);
		commonBusiness.changeObject(staffDetailsVo, staffDetails);
		staffAdmissionVo.setStaffMasterVo(staffMasterVo);
		staffAdmissionVo.setStaffDetailsVo(staffDetailsVo);
		staffAdmissionVo.setDbTs(staffMaster.getDbTs());
	}
}
