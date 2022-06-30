package com.jaw.nonStaff.service;

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
import com.jaw.common.constants.TableNameConstant;
import com.jaw.common.exceptions.DatabaseException;
import com.jaw.common.exceptions.DeleteFailedException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.FileNotFoundInDatabase;
import com.jaw.common.exceptions.FileNotSaveException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.UpdateFailedException;
import com.jaw.common.exceptions.login.InvalidUserIdException;
import com.jaw.common.exceptions.util.TableNotSpecifiedForAuditException;
import com.jaw.common.files.dao.IFileMasterDao;
import com.jaw.common.files.service.FileMasterHelper;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.audit.service.DoAudit;
import com.jaw.framework.seqGen.service.IIdGeneratorService;
import com.jaw.framework.sessCache.UserSessionDetails;
import com.jaw.nonStaff.dao.INonStaffDetailsDAO;
import com.jaw.nonStaff.dao.NonStaff;
import com.jaw.user.controller.BranchAdminVO;
import com.jaw.user.dao.IUserDao;
import com.jaw.user.dao.IUserLinkDao;
import com.jaw.user.dao.User;
import com.jaw.user.dao.UserLink;
import com.jaw.user.util.UserCreation;

//Institute Master Service Class
@Service
public class NonStaffModifyService implements INonStaffModifyService {

	// Logging
	Logger logger = Logger.getLogger(NonStaffModifyService.class);

	@Autowired
	INonStaffDetailsDAO nonStaffDetailsDAO;
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

	@Override
	public void selectNonStaff(BranchAdminVO NonStaffVO,
			UserSessionDetails userSessionDetails)
			throws FileNotFoundInDatabase, NoDataFoundException {

		NonStaff NonStaff = nonStaffDetailsDAO.selectNonStaffRec(
				userSessionDetails.getInstId(),
				userSessionDetails.getBranchId(), NonStaffVO.getStaffId());
		commonBusiness.changeObject(NonStaffVO, NonStaff);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void updateNonStaffDetails(ApplicationCache applicationCache,
			BranchAdminVO nonStaffVO, UserSessionDetails userSessionDetails,ServletContext servletContext)
			throws UpdateFailedException, NoDataFoundException,
			DuplicateEntryException, FileNotFoundInDatabase, DatabaseException,
			TableNotSpecifiedForAuditException, IllegalStateException, IOException, DeleteFailedException, FileNotSaveException {

		NonStaff nonStaff = nonStaffDetailsDAO.selectNonStaffRec(
				userSessionDetails.getInstId(),
				userSessionDetails.getBranchId(), nonStaffVO.getStaffId());
		String oldrecord = nonStaff.toString();
		commonBusiness.changeObject(nonStaff, nonStaffVO);
		nonStaff.setrModId(userSessionDetails.getUserId());
		nonStaff.setInstId(userSessionDetails.getInstId());
		nonStaff.setBranchId(userSessionDetails.getBranchId());
		fileMasterHelper.fileUpload(applicationCache,
				nonStaffVO.getStaffPhoto(),
				ApplicationConstant.NON_STAFF_PHOTO, 1, userSessionDetails,
				userSessionDetails.getUserId(), nonStaff.getStaffId(),ApplicationConstant.DEFAULT_FILE_SRL_NO,ApplicationConstant.PG_NON_STAFF,servletContext);
		nonStaffDetailsDAO.updateNonStaffRec(nonStaff);
		nonStaffDetailsDAO.selectNonStaffRec(userSessionDetails.getInstId(),
				userSessionDetails.getBranchId(), nonStaff.getStaffId());
		nonStaff = nonStaffDetailsDAO.selectNonStaffRec(
				userSessionDetails.getInstId(),
				userSessionDetails.getBranchId(), nonStaffVO.getStaffId());
		String newrecord = nonStaff.toString();
		commonBusiness.changeObject(nonStaffVO, nonStaff);

		doAudit.doDatabaseAudit(applicationCache, userSessionDetails,
				TableNameConstant.NONSTAFF, nonStaff.toStringForDBKey(),
				oldrecord, AuditConstant.TYPE_OF_OPER_UPDATE, newrecord, "");

	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void deleteNonStaffDetails(BranchAdminVO branchAdmin,
			UserSessionDetails userSessionDetails)
			throws DeleteFailedException, NoDataFoundException,
			InvalidUserIdException {

		try {
			NonStaff nonStaff = nonStaffDetailsDAO.selectNonStaffRec(
					userSessionDetails.getInstId(),
					userSessionDetails.getBranchId(), branchAdmin.getStaffId());
			nonStaff.setrModId(userSessionDetails.getUserId());
			nonStaff.setDelFlg("Y");
			nonStaff.setrModId(userSessionDetails.getUserId());
			nonStaff.setInstId(userSessionDetails.getInstId());
			nonStaff.setBranchId(userSessionDetails.getBranchId());
			UserLink userLink = userLinkDao.getUserDetailsByLinkId(
					branchAdmin.getStaffId(), userSessionDetails.getInstId(),
					userSessionDetails.getBranchId());

			User user = userDao.validateUserId(userLink.getUserId());
			nonStaffDetailsDAO.updateNonStaffRec(nonStaff);
			user.setDeleteFlag("Y");
			user.setrModId(userSessionDetails.getUserId());
			userLink.setDeleteFlag("Y");
			userLink.setrModId(userSessionDetails.getUserId());
			userDao.updateUser(user);
			userLinkDao.updateUserLinkRec(userLink);
		} catch (UpdateFailedException e) {
			throw new DeleteFailedException();
		}

	}

}
