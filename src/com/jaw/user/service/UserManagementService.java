package com.jaw.user.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jaw.common.business.CommonBusiness;
import com.jaw.common.constants.AuditConstant;
import com.jaw.common.constants.PropertyManagementConstant;
import com.jaw.common.exceptions.DatabaseException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.UpdateFailedException;
import com.jaw.common.exceptions.login.InvalidUserIdException;
import com.jaw.common.exceptions.util.PropertyNotFoundException;
import com.jaw.common.util.DateUtil;
import com.jaw.common.util.MenuProfileUtil;
import com.jaw.common.util.PropertyManagementUtil;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.audit.service.DoAudit;
import com.jaw.framework.sessCache.SessionCache;
import com.jaw.framework.sessCache.UserSessionDetails;
import com.jaw.login.controller.LoginController;
import com.jaw.login.dao.ILoginDao;
import com.jaw.login.service.LoginHelper;
import com.jaw.mgmtUser.dao.IMgmtUserDetailsDAO;
import com.jaw.nonStaff.dao.INonStaffDetailsDAO;
import com.jaw.security.SecurityCheck;
import com.jaw.staff.dao.IStaffDetailsDAO;
import com.jaw.staff.dao.IStaffMasterDAO;
import com.jaw.user.controller.UserManagementVO;
import com.jaw.user.dao.IMenuprofileListDao;
import com.jaw.user.dao.ISingleUserDetailsDao;
import com.jaw.user.dao.IUserDao;
import com.jaw.user.dao.IUserLinkDao;
import com.jaw.user.dao.IUserManagementListDao;
import com.jaw.user.dao.User;
import com.jaw.user.dao.UserManagement;

@Service
public class UserManagementService implements IUserManagementService {
	Logger logger = Logger.getLogger(LoginController.class);
	@Autowired
	private IUserDao userDao;
	@Autowired
	private IUserLinkDao userLinkDao;
	@Autowired
	private ILoginDao loginDao;
	@Autowired
	private LoginHelper loginBussiness;
	@Autowired
	private IMgmtUserDetailsDAO managementDetailsDAO;
	@Autowired
	private CommonBusiness commonBussiness;
	@Autowired
	private SecurityCheck securityCheck;
	
	@Autowired
	private IUserManagementListDao userListDao;
	@Autowired
	private INonStaffDetailsDAO nonStaffDetailsDAO;
	// Helper class to do Auditing
	@Autowired
	DoAudit doAudit;
	@Autowired
	CommonBusiness commonBusiness;
	@Autowired
	DateUtil dateUtil;
	@Autowired
	PropertyManagementUtil propertyManagementUtil;
	@Autowired
	ISingleUserDetailsDao singleUserDetailsDao;
	@Autowired
	IStaffMasterDAO staffMasterDAO;
	@Autowired
	IStaffDetailsDAO staffDetailsDAO;
	@Autowired
	MenuProfileUtil menuProfileUtil;
	@Autowired
	IMenuprofileListDao menuprofileListDao;
	
	@Transactional(rollbackFor = Exception.class)
	@Override
	public List<UserManagementVO> getUserDetailsForUserManagement(
			ApplicationCache applicationCache, SessionCache sessionCache,
			String branchId, String instId, UserManagementVO userManagementVO,
			String url) throws NoDataFoundException {
		UserSessionDetails userSessionDetails = sessionCache
				.getUserSessionDetails();
		UserManagement user = new UserManagement();
		commonBussiness.changeObject(user, userManagementVO);
		user.setInstId(instId);
		user.setBranchId(branchId);
		
		user.setMenuOptionId(menuProfileUtil.getMenuOption(userSessionDetails, url,
				applicationCache));
		List<UserManagement> users = userListDao
				.selectUserManagementListFromUserManagement(user);
		List<UserManagementVO> managementVOs = new ArrayList<UserManagementVO>();
		
		for (int i = 0; i < users.size(); i++) {
			UserManagementVO userManagementVO2 = new UserManagementVO();
			commonBussiness.changeObject(userManagementVO2, users.get(i));
			userManagementVO2.setRowId(i);
			managementVOs.add(userManagementVO2);
		}
		return managementVOs;
	}
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public UserManagementVO userEnableOrDisable(UserManagementVO managementVO,
			UserSessionDetails userSessionDetails,
			ApplicationCache applicationCache) throws DuplicateEntryException,
			InvalidUserIdException, DatabaseException, NumberFormatException,
			PropertyNotFoundException, UpdateFailedException {
		User user = null;
		String auditConstant = null;
		user = userDao.validateUserId(managementVO.getUserId());
		if (user.getUserEnableFlag().equals("N")) {
			user.setPassword(commonBusiness.createPassword(user.getUserId()));
			user.setPasswordResetFlag("Y");
			user.setPasswordExpiryDate(dateUtil.addDaysToCurrentDate(Integer
					.parseInt(propertyManagementUtil.getPropertyValue(
							applicationCache, userSessionDetails.getInstId(),
							userSessionDetails.getBranchId(),
							PropertyManagementConstant.PWD_EXPIRY_PRD))));
			user.setUserEnableFlag("Y");
			user.setNoOfAttempts(0);
			auditConstant = AuditConstant.USER_ENABLE;
			
		}
		else {
			user.setUserEnableFlag("N");
			auditConstant = AuditConstant.USER_DISABLED;
		}
		user.setRemarks(managementVO.getRemarks());
		user.setrModId(userSessionDetails.getUserId());
		user.setUserId(user.getUserId());
		user.setRemarks(managementVO.getRemarks());
		userDao.updateUser(user);
		
		doAudit.doFunctionalAudit(userSessionDetails, auditConstant,
				managementVO.getRemarks());
		
		user = userDao.validateUserId(user.getUserId());
		commonBusiness.changeObject(managementVO, user);
		return managementVO;
	}
	
	@Override
	public List<String> getMenuProfileList(UserSessionDetails userSessionDetails)
			throws NoDataFoundException {
		return menuprofileListDao.selectMenuProfile(userSessionDetails
				.getInstId());
		
	}
	
}
