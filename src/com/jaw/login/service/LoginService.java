package com.jaw.login.service;

import java.io.FileNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jaw.common.business.CommonBusiness;
import com.jaw.common.constants.AuditConstant;
import com.jaw.common.constants.PropertyManagementConstant;
import com.jaw.common.exceptions.DatabaseException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.DuplicatePasswordException;
import com.jaw.common.exceptions.FileNotFoundInDatabase;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.PasswordMismatchException;
import com.jaw.common.exceptions.PasswordrequestedLoginFailException;
import com.jaw.common.exceptions.UpdateFailedException;
import com.jaw.common.exceptions.login.InvalidAttemptException;
import com.jaw.common.exceptions.login.InvalidUserIdException;
import com.jaw.common.exceptions.login.PasswordExpiredException;
import com.jaw.common.exceptions.login.PasswordNotResetException;
import com.jaw.common.exceptions.login.SessionCacheNotLoadedException;
import com.jaw.common.exceptions.login.UserDisabledException;
import com.jaw.common.exceptions.login.WrongPasswordException;
import com.jaw.common.exceptions.util.PropertyNotFoundException;
import com.jaw.common.util.DateUtil;
import com.jaw.common.util.PropertyManagementUtil;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.audit.dao.IAuditDao;
import com.jaw.framework.audit.service.DoAudit;
import com.jaw.framework.sessCache.SessionCache;
import com.jaw.framework.sessCache.UserSessionDetails;
import com.jaw.login.dao.ILoginDao;
import com.jaw.security.SecurityCheck;
import com.jaw.user.controller.UserVO;
import com.jaw.user.dao.ISingleUserDetailsDao;
import com.jaw.user.dao.IUserDao;
import com.jaw.user.dao.IUserLinkDao;
import com.jaw.user.dao.User;
import com.jaw.user.dao.UserLink;

@Service
public class LoginService implements ILoginService {
	@Autowired
	private IUserDao userDao;
	@Autowired
	private ILoginDao loginDao;
	@Autowired
	private LoginHelper loginBussiness;
	@Autowired
	IAuditDao auditDao;
	@Autowired
	private CommonBusiness commonBussiness;
	@Autowired
	private SecurityCheck securityCheck;
	
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
	IUserLinkDao userLinkDao;
	
/*	@Transactional(rollbackFor = Exception.class)*/
	@Override
	public void loginUser(UserVO uservo, ApplicationCache applicationCache,
			SessionCache sessionCache) throws DuplicateEntryException,
			PasswordMismatchException, InvalidAttemptException,
			PasswordNotResetException, PasswordExpiredException,
			UserDisabledException,
			FileNotFoundException, InvalidUserIdException, DatabaseException,
			SessionCacheNotLoadedException,
			PasswordrequestedLoginFailException, NumberFormatException,
			PropertyNotFoundException, UpdateFailedException, NoDataFoundException {
		String auditActCode = "";
		User user = userDao.validateUserId(uservo.getUserId());
		UserSessionDetails userSessionDetails = new UserSessionDetails();
		commonBussiness.changeObject(userSessionDetails, user);
		
		try {
			loginBussiness.checkUserEnabled(user);
			loginBussiness.checkUserRequestedForPassword(user);
			loginBussiness.validatePassword(user, uservo);
			loginBussiness.checkPasswordResetRequired(user);
			loginBussiness.checkPasswordExpiredDate(user);
			UserLink userLink = userLinkDao.getUserDetails(user.getUserId());
			try {
				loginBussiness.createUserObjectLoginSuccess(user, uservo, userLink,
						sessionCache);
			}
			catch (FileNotFoundInDatabase e) {
				
			}
			
			updateUser(user);
			/*insertLogin(user);*/
			auditActCode = AuditConstant.LOGIN_SUCCESS;
			
		}
		catch (UserDisabledException e) {
			auditActCode = AuditConstant.DISABLED_USER;
			throw e;
			
		}
		catch (PasswordMismatchException e) {
			try {
				loginBussiness.checkNumberOfInvalidAttempts(applicationCache,
						user);
				user.setNoOfAttempts(user.getNoOfAttempts() + 1);
				updateUser(user);
				auditActCode = AuditConstant.WRONG_PASSWORD;
				throw e;
			}
			catch (InvalidAttemptException en) {
				auditActCode = AuditConstant.INVALID_ATTEMPTS;
				user.setUserEnableFlag("D");
				user.setNoOfAttempts(0);
				updateUser(user);
				throw en;
			}
			
		}
		catch (PasswordNotResetException e) {
			updateUser(user);
			auditActCode = AuditConstant.PASSWORD_RESET_REQUIRED;
			throw e;
		}
		catch (PasswordExpiredException e) {
			updateUser(user);
			auditActCode = AuditConstant.PASSWORD_EXPIRED;
			throw e;
		}
		catch (PropertyNotFoundException e) {
			
		}
		finally {
			
			doAudit.doFunctionalAudit(userSessionDetails, auditActCode, " ");
			
			uservo.setPassword("");
		}
		
	}
	
	public void updateUser(User user) throws UpdateFailedException {
		userDao.updateUser(user);
	}
	
	@Override
	public boolean insertLogin(User user) throws DuplicateEntryException {
		
		return loginDao.insertLogin(user);
		
	}
	
	@Transactional(rollbackFor = Exception.class)
	@Override
	public void updatePassword(UserVO userVO,
			ApplicationCache applicationCache)
			throws PasswordMismatchException, WrongPasswordException,
			DuplicatePasswordException, DuplicateEntryException,
			InvalidUserIdException, DatabaseException, UpdateFailedException,
			PropertyNotFoundException {
		User user = userDao.validateUserId(userVO.getUserId());
		
		if (!securityCheck.checkPassword(userVO.getPassword(),
				user.getPassword())) {
			throw new WrongPasswordException();
		}
		else {
			
			UserSessionDetails userSessionDetails = new UserSessionDetails();
			userSessionDetails.setInstId(user.getInstId());
			userSessionDetails.setBranchId(user.getBranchId());
			userSessionDetails.setUserId(user.getUserId());
			userSessionDetails.setIpAddress(userVO.getIpAddress());
			int days = Integer.parseInt(propertyManagementUtil.getPropertyValue(
					applicationCache, user.getInstId(), user.getBranchId(),
					PropertyManagementConstant.PWD_EXPIRY_PRD).toString());
			user.setPasswordExpiryDate(dateUtil.addDaysToCurrentDate(days));
			user.setNoOfAttempts(0);
			user.setPasswordResetDate(user.getCurrentDate());
			user.setPasswordResetFlag("N");
			doAudit.doFunctionalAudit(userSessionDetails,
					AuditConstant.PASSWORD_CHANGE, " ");
			user.setPassword(commonBusiness.createPasswordForUser(userVO
					.getChangePassword()));
			userDao.updatePassword(user);
			
		}
		
	}
	
	@Transactional(rollbackFor = Exception.class)
	@Override
	public void logout(UserSessionDetails userSessionDetails)
			throws DuplicateEntryException, InvalidUserIdException,
			DatabaseException, UpdateFailedException {
		deleteLogin(userSessionDetails.getBranchId(),
				userSessionDetails.getInstId(), userSessionDetails.getUserId());
		User user = new User();
		commonBussiness.changeObject(user, userSessionDetails);
		user = userDao.validateUserId(user.getUserId());
		user.setLastLogoutTime(dateUtil.getCurrentDateWithTime());
		user.setrModId(user.getUserId());
		updateUser(user);
		/*deleteLogin(userSessionDetails.getBranchId(),
				userSessionDetails.getInstId(), userSessionDetails.getUserId());*/
		doAudit.doFunctionalAudit(userSessionDetails, AuditConstant.LOGOUT, " ");
		
	}
	
	private void deleteLogin(String branchId, String instId, String userId) {
		loginDao.deleteLogin(branchId, instId, userId);
	}
	
}
