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
import com.jaw.common.constants.TableNameConstant;
import com.jaw.common.exceptions.DatabaseException;
import com.jaw.common.exceptions.DeleteFailedException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.FileNotFoundInDatabase;
import com.jaw.common.exceptions.FileNotSaveException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.UpdateFailedException;
import com.jaw.common.exceptions.login.InvalidUserIdException;
import com.jaw.common.exceptions.util.PropertyNotFoundException;
import com.jaw.common.exceptions.util.TableNotSpecifiedForAuditException;
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
import com.jaw.user.dao.IUserDao;
import com.jaw.user.dao.IUserLinkDao;
import com.jaw.user.dao.User;
import com.jaw.user.dao.UserLink;
import com.jaw.user.util.UserCreation;

//Institute Master Service Class
@Service
public class MgmtUserModifyService implements IMgmtUserModifyService {
	
	// Logging
	Logger logger = Logger.getLogger(MgmtUserModifyService.class);
	
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
	IUserDao userDao;
	@Autowired
	IUserLinkDao userLinkDao;
	
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
				managementVO.getManagementId());
		commonBusiness.changeObject(managementVO, management);
	}
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void updateManagementDetails(ApplicationCache applicationCache,
			MgmtUserVO managementVO, UserSessionDetails userSessionDetails,ServletContext servletContext)
			throws UpdateFailedException, NoDataFoundException,
			DuplicateEntryException, FileNotFoundInDatabase, DatabaseException,
			TableNotSpecifiedForAuditException, IllegalStateException, IOException, DeleteFailedException, FileNotSaveException {
		
		MgmtUser management = managementDetailsDAO.selectManagementRec(
				userSessionDetails.getInstId(),
				userSessionDetails.getBranchId(),
				managementVO.getManagementId());
		String oldRecString = management.stringForDbAudit();
		commonBusiness.changeObject(management, managementVO);
		management.setrModId(userSessionDetails.getUserId());
		management.setInstId(userSessionDetails.getInstId());
		management.setBranchId(userSessionDetails.getBranchId());
		fileMasterHelper.fileUpload(applicationCache,
				managementVO.getManagementPhoto(),
				ApplicationConstant.MANAGEMENT_PHOTO, 1, userSessionDetails,
				userSessionDetails.getUserId(), management.getManagementId(),ApplicationConstant.DEFAULT_FILE_SRL_NO,ApplicationConstant.PG_MGMT,servletContext);
		managementDetailsDAO.updateManagementRec(management);
		managementDetailsDAO.selectManagementRec(
				userSessionDetails.getInstId(),
				userSessionDetails.getBranchId(), management.getManagementId());
		management = managementDetailsDAO.selectManagementRec(
				userSessionDetails.getInstId(),
				userSessionDetails.getBranchId(),
				managementVO.getManagementId());
		String newRecString = management.stringForDbAudit();
		commonBusiness.changeObject(managementVO, management);
		
		doAudit.doDatabaseAudit(applicationCache, userSessionDetails,
				TableNameConstant.MANAGEMENT, management.toStringForDBKey(),
				oldRecString, AuditConstant.TYPE_OF_OPER_UPDATE, newRecString,
				"");
		
	}
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void deleteManagementDetails(MgmtUserVO managementVO,
			UserSessionDetails userSessionDetails)
			throws DeleteFailedException, NoDataFoundException,
			InvalidUserIdException {
		
		try {
			MgmtUser management = managementDetailsDAO.selectManagementRec(
					userSessionDetails.getInstId(),
					userSessionDetails.getBranchId(),
					managementVO.getManagementId());
			management.setrModId(userSessionDetails.getUserId());
			management.setDelFlg("Y");
			management.setrModId(userSessionDetails.getUserId());
			management.setInstId(userSessionDetails.getInstId());
			management.setBranchId(userSessionDetails.getBranchId());
			
			UserLink userLink = userLinkDao.getUserDetailsByLinkId(
					managementVO.getManagementId(),
					userSessionDetails.getInstId(),
					userSessionDetails.getBranchId());
			
			User user = userDao.validateUserId(userLink.getUserId());
			managementDetailsDAO.updateManagementRec(management);
			user.setDeleteFlag("Y");
			user.setrModId(userSessionDetails.getUserId());
			userLink.setDeleteFlag("Y");
			userLink.setrModId(userSessionDetails.getUserId());
			userDao.updateUser(user);
			userLinkDao.updateUserLinkRec(userLink);
			
		}
		catch (UpdateFailedException e) {
			throw new DeleteFailedException();
		}
		
	}
	
}
