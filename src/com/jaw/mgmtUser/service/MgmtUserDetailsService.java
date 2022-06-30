package com.jaw.mgmtUser.service;

import java.io.IOException;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jaw.common.business.CommonBusiness;
import com.jaw.common.constants.ApplicationConstant;
import com.jaw.common.constants.AuditConstant;
import com.jaw.common.constants.SequenceConstant;
import com.jaw.common.exceptions.DatabaseException;
import com.jaw.common.exceptions.DeleteFailedException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.FileNotFoundInDatabase;
import com.jaw.common.exceptions.FileNotSaveException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.util.PropertyNotFoundException;
import com.jaw.common.files.dao.IFileMasterDao;
import com.jaw.common.files.service.FileMasterHelper;
import com.jaw.common.util.AlphaSequenceUtil;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.audit.service.DoAudit;
import com.jaw.framework.seqGen.service.IIdGeneratorService;
import com.jaw.framework.sessCache.UserSessionDetails;
import com.jaw.mgmtUser.controller.MgmtUserVO;
import com.jaw.mgmtUser.dao.IMgmtUserDetailsDAO;
import com.jaw.mgmtUser.dao.MgmtUser;
import com.jaw.user.controller.UserCreationVO;
import com.jaw.user.util.UserCreation;

//Institute Master Service Class
@Service
public class MgmtUserDetailsService implements IMgmtUserDetailsService {
	
	// Logging
	Logger logger = Logger.getLogger(MgmtUserDetailsService.class);
	
	@Autowired
	IMgmtUserDetailsDAO managementDetailsDAO;
	@Autowired
	CommonBusiness commonBusiness;
	@Autowired
	DoAudit doAudit;
	@Autowired
	FileMasterHelper fileMasterHelper;
	@Autowired
	IFileMasterDao fileMasterDao;
	@Autowired
	UserCreation userCreation;
	
	@Autowired
	@Qualifier(value = "simpleIdGenerator")
	private IIdGeneratorService simpleIdGenerator;
	
	@Transactional(rollbackFor = Exception.class)
	@Override
	// method to Update InstituteMaster
	public void insertManagementDetails(MgmtUserVO managementVO,
			UserSessionDetails userSessionDetails,
			ApplicationCache applicationCache,ServletContext servletContext) throws FileNotFoundInDatabase,
			DuplicateEntryException, DatabaseException, NumberFormatException,
			PropertyNotFoundException, IllegalStateException, IOException, DeleteFailedException, FileNotSaveException {
		managementVO.setManagementId(AlphaSequenceUtil.generateAlphaSequence(
				ApplicationConstant.STRING_MANAGEMENT_USER_SEQ,
				simpleIdGenerator.getNextId(
						SequenceConstant.MANAGEMENT_USER_SEQUENCE, 1)));
		MgmtUser management = new MgmtUser();
		commonBusiness.changeObject(management, managementVO);
		management.setDbTs(1);
		management.setInstId(userSessionDetails.getInstId());
		
		management.setBranchId(userSessionDetails.getBranchId());
		management.setrCreId(userSessionDetails.getUserId());
		management.setrModId(userSessionDetails.getUserId());
		
		managementDetailsDAO.insertManagementRec(management);
		fileMasterHelper.fileUpload(applicationCache,
				managementVO.getManagementPhoto(),
				ApplicationConstant.MANAGEMENT_PHOTO, 1, userSessionDetails,
				userSessionDetails.getUserId(), management.getManagementId(),ApplicationConstant.DEFAULT_FILE_SRL_NO,ApplicationConstant.PG_MGMT,servletContext);
		if (managementVO.getIsUser() != null) {
			
			UserCreationVO userCreationVO = new UserCreationVO();
			userCreationVO.setApplicationCache(applicationCache);
			userCreationVO.setLinkId(management.getManagementId());
			userCreationVO.setMenuProfile(managementVO.getProfile());
			userCreationVO.setPassword(managementVO.getPassword());
			userCreationVO.setProfileGroup(ApplicationConstant.PG_MGMT);
			userCreationVO.setRole("");
			userCreationVO.setUserId(managementVO.getUserId());
			userCreationVO.setUserSessionDetails(userSessionDetails);
			
			userCreation.createUser(userCreationVO);
		}
		String remarks = "inst_id:" + userSessionDetails.getInstId()
				+ ", branch_id:" + userSessionDetails.getBranchId()
				+ ", ManagementId:" + management.getManagementId();
		doAudit.doFunctionalAudit(userSessionDetails,
				AuditConstant.MGMT_USER_CREATE, remarks);
		
	}
	
	@Override
	public void selectManagement(MgmtUserVO managementVO,
			UserSessionDetails userSessionDetails)
			throws FileNotFoundInDatabase, NoDataFoundException {
		
		MgmtUser management = managementDetailsDAO.selectManagementRec(
				userSessionDetails.getInstId(),
				userSessionDetails.getBranchId(),
				userSessionDetails.getLinkId());
		commonBusiness.changeObject(managementVO, management);
	}
	
}
