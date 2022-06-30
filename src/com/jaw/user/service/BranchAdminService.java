package com.jaw.user.service;

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
import com.jaw.common.exceptions.UpdateFailedException;
import com.jaw.common.exceptions.UserNotCreatedException;
import com.jaw.common.exceptions.login.InvalidUserIdException;
import com.jaw.common.exceptions.util.PropertyNotFoundException;
import com.jaw.common.files.service.FileMasterHelper;
import com.jaw.common.util.AlphaSequenceUtil;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.audit.service.DoAudit;
import com.jaw.framework.seqGen.service.IIdGeneratorService;
import com.jaw.framework.sessCache.UserSessionDetails;
import com.jaw.nonStaff.dao.INonStaffDetailsDAO;
import com.jaw.nonStaff.dao.NonStaff;
import com.jaw.staff.dao.IStaffMasterDAO;
import com.jaw.staff.dao.StaffMaster;
import com.jaw.user.controller.BranchAdminVO;
import com.jaw.user.controller.UserCreationVO;
import com.jaw.user.dao.IUserDao;
import com.jaw.user.dao.IUserLinkDao;
import com.jaw.user.dao.UserLink;
import com.jaw.user.util.UserCreation;

//Institute Master Service Class
@Service
public class BranchAdminService implements IBranchAdminService {
	@Autowired
	IStaffMasterDAO staffMasterDAO;
	@Autowired
	CommonBusiness commonBusiness;
	@Autowired
	UserCreation userCreation;
	@Autowired
	IUserLinkDao userLinkDao;
	@Autowired
	IUserDao userDao;
	@Autowired
	INonStaffDetailsDAO nonStaffDetailsDAO;
	@Autowired
	FileMasterHelper fileMasterHelper;
	@Autowired
	AlphaSequenceUtil alphaSequenceUtil;
	@Autowired
	DoAudit doAudit;
	@Autowired
	@Qualifier(value = "simpleIdGenerator")
	private IIdGeneratorService simpleIdGenerator;
	// Logging
	Logger logger = Logger.getLogger(BranchAdminService.class);
	
	@Override
	public void selectStaffDetails(BranchAdminVO adminVO,
			UserSessionDetails userSessionDetails) throws NoDataFoundException,
			UserNotCreatedException {
		if (adminVO.getIsSingleBranch().equals("Y")) {
			adminVO.setBranchId(userSessionDetails.getInstId());
		}
		if (adminVO.getIsStaff().equals(ApplicationConstant.PG_STAFF)) {
			StaffMaster staff = staffMasterDAO.selectStaffRec(
					adminVO.getStaffId(), userSessionDetails.getInstId(),
					adminVO.getBranchId());
			
			try {
				userLinkDao.getUserDetailsByLinkId(adminVO.getStaffId(),
						userSessionDetails.getInstId(), adminVO.getBranchId());
			}
			catch (InvalidUserIdException e) {
				throw new UserNotCreatedException();
			}
			commonBusiness.changeObject(adminVO, staff);
			adminVO.setContact1(staff.getLandline());
			adminVO.setContact2(staff.getMobile());
			adminVO.setEmergencyContact(staff.getEmergency());
		}
		
	}
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void insertBranchAdmin(BranchAdminVO adminVO,
			UserSessionDetails userSessionDetails,
			ApplicationCache applicationCache,ServletContext servletContext) throws FileNotFoundInDatabase,
			DuplicateEntryException, InvalidUserIdException,
			UpdateFailedException, DatabaseException, NumberFormatException,
			PropertyNotFoundException, IllegalStateException, IOException, DeleteFailedException, FileNotSaveException {
		if (adminVO.getIsSingleBranch().equals("Y")) {
			adminVO.setBranchId(userSessionDetails.getInstId());
			
		}
		if (adminVO.getIsStaff().equals(ApplicationConstant.PG_NON_STAFF)) {
			NonStaff nonStaff = new NonStaff();
			commonBusiness.changeObject(nonStaff, adminVO);
			nonStaff.setDbTs(1);
			nonStaff.setStaffId(alphaSequenceUtil.generateAlphaSequence(
					ApplicationConstant.STRING_NON_STAFF_SEQ, simpleIdGenerator
							.getNextId(SequenceConstant.NONSTAFF_USER_SEQUENCE,
									1)));
			nonStaff.setInstId(userSessionDetails.getInstId());
			nonStaff.setrCreId(userSessionDetails.getUserId());
			nonStaff.setrModId(userSessionDetails.getUserId());
			nonStaff.setDelFlg("N");
			logger.debug("Single Branch or not :" + adminVO.getIsSingleBranch());
			
			nonStaffDetailsDAO.insertNonStaffDetails(nonStaff);
			fileMasterHelper.fileUpload(applicationCache,
					adminVO.getStaffPhoto(),
					ApplicationConstant.NON_STAFF_PHOTO, 1, userSessionDetails,
					userSessionDetails.getUserId(),
					userSessionDetails.getInstId(), adminVO.getBranchId(),
					nonStaff.getStaffId(),ApplicationConstant.DEFAULT_FILE_SRL_NO,ApplicationConstant.PG_NON_STAFF,servletContext);
			
			UserCreationVO userCreationVO = new UserCreationVO();
			userCreationVO.setApplicationCache(applicationCache);
			userCreationVO.setLinkId(nonStaff.getStaffId());
			userCreationVO.setMenuProfile(ApplicationConstant.BRANCH_ADMIN);
			userCreationVO.setPassword(adminVO.getPassword());
			userCreationVO.setProfileGroup(ApplicationConstant.PG_NON_STAFF);
			userCreationVO.setRole("");
			userCreationVO.setUserId(adminVO.getUserId());
			userCreationVO.setUserSessionDetails(userSessionDetails);
			
			userCreation.createUser(userCreationVO);
			String remarks = "inst_id:" + userSessionDetails.getInstId()
					+ ",branch_id:" + adminVO.getBranchId() + ",admin_id:"
					+ adminVO.getStaffId();
			doAudit.doFunctionalAudit(userSessionDetails,
					AuditConstant.NSTF_USER_CREATE, remarks);
		}
		else if (adminVO.getIsStaff().equals(ApplicationConstant.PG_STAFF)) {
			UserLink userlink = new UserLink();
			
			UserLink link = userLinkDao.getUserDetailsByLinkId(
					adminVO.getStaffId(), userSessionDetails.getInstId(),
					adminVO.getBranchId());
			commonBusiness.changeObject(userlink, link);
			userlink.setrModId(userSessionDetails.getUserId());
			userlink.setUserMenuProfile(ApplicationConstant.BRANCH_ADMIN);
			userLinkDao.updateUserLinkRec(userlink);
			String remarks = "inst_id:" + userSessionDetails.getInstId()
					+ ",branch_id:" + adminVO.getBranchId() + ",admin_id:"
					+ adminVO.getStaffId();
			doAudit.doFunctionalAudit(userSessionDetails,
					AuditConstant.STF_BR_CREATE, remarks);
		}
	}
}
