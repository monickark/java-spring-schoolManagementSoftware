package com.jaw.login.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jaw.common.business.CommonBusiness;
import com.jaw.common.constants.ApplicationConstant;
import com.jaw.common.constants.PropertyManagementConstant;
import com.jaw.common.exceptions.DatabaseException;
import com.jaw.common.exceptions.FileNotFoundInDatabase;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.PasswordMismatchException;
import com.jaw.common.exceptions.PasswordrequestedLoginFailException;
import com.jaw.common.exceptions.login.InvalidAttemptException;
import com.jaw.common.exceptions.login.PasswordExpiredException;
import com.jaw.common.exceptions.login.PasswordNotResetException;
import com.jaw.common.exceptions.login.SessionCacheNotLoadedException;
import com.jaw.common.exceptions.login.UserDisabledException;
import com.jaw.common.exceptions.util.PropertyNotFoundException;
import com.jaw.common.files.dao.IFileMasterDao;
import com.jaw.common.util.DateUtil;
import com.jaw.common.util.PropertyManagementUtil;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.sessCache.SessionCache;
import com.jaw.framework.sessCache.dao.IManagementSessionDAO;
import com.jaw.framework.sessCache.dao.INonStaffSessionDAO;
import com.jaw.framework.sessCache.dao.IParentSessionDao;
import com.jaw.framework.sessCache.dao.IStudentSessionDao;
import com.jaw.mgmtUser.dao.IMgmtUserDetailsDAO;
import com.jaw.nonStaff.dao.INonStaffDetailsDAO;
import com.jaw.user.controller.UserVO;
import com.jaw.user.dao.User;
import com.jaw.user.dao.UserLink;
import com.jaw.user.service.UserProfileHelper;

@Component
public class LoginHelper {
	Logger logger = Logger.getLogger(LoginHelper.class);
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
	private UserProfileHelper userProfileHelper;
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
	private LoginSCHelper loginSCHelper;
	
	public boolean checkUserEnabled(User user) throws UserDisabledException {
		logger.debug("checking whether user is enable r not");
		if (user.getUserEnableFlag().equals(ApplicationConstant.USER_DISABLE_FLG)) {
			logger.info(user.getUserId() + " is disabled unable to login.");
			
			throw new UserDisabledException();
		}
		
		return true;
	}
	
	public boolean checkUserRequestedForPassword(User user)
			throws PasswordrequestedLoginFailException {
		logger.debug("checking whether user is erequested for password or not");
		
		if (user.getUserEnableFlag().equals(ApplicationConstant.REQUEST_STATUS_REQUESTED)) {
			
			logger.info(user.getUserId()
					+ " is requested for new password, unable to login.");
			throw new PasswordrequestedLoginFailException();
		}
		return true;
	}
	
	public void validatePassword(User user, UserVO uservo)
			throws PasswordMismatchException {
		if (!securityCheck.checkPassword(uservo.getPassword(),
				user.getPassword())) {
			user.setNoOfAttempts(user.getNoOfAttempts() + 1);
			throw new PasswordMismatchException();
		}
		
	}
	
	public void checkNumberOfInvalidAttempts(
			ApplicationCache applicationCache2, User user)
			throws InvalidAttemptException, NumberFormatException,
			PropertyNotFoundException {
		logger.debug("checking number of invalid attempts");
		Integer maxInvalidAttemptsAllowed = Integer
				.parseInt(propertyManagementUtil.getPropertyValue(
						applicationCache,
						user.getInstId(),
						user.getBranchId(),
						PropertyManagementConstant.NO_OF_INVALID_ATTEMPTS_ALLOWED));
		logger.debug("User :" + user.getUserId() + "'s invalid attempts is "
				+ user.getNoOfAttempts() + " , Max invalid attemmpts :"
				+ maxInvalidAttemptsAllowed);
		if (user.getNoOfAttempts() > maxInvalidAttemptsAllowed) {
			logger.error("User :" + user.getUserId()
					+ " exceed maximum number of invalid attempts"
					+ user.getNoOfAttempts() + " , User  disabled");
			user.setUserEnableFlag("N");
			user.setNoOfAttempts(0);
			throw new InvalidAttemptException();
		}
	}
	
	public boolean checkPasswordResetRequired(User user)
			throws PasswordNotResetException {
		logger.debug("checking whether password reset is required r not");
		if (user.getPasswordResetFlag().equals(ApplicationConstant.PASSWORD_RESET_REQUIRED)) {
			logger.info("Password reset required for user :" + user.getUserId());
			throw new PasswordNotResetException();
		}
		
		return true;
	}
	
	public boolean checkPasswordExpiredDate(User user)
			throws PasswordExpiredException
	
	{
		logger.debug("checking whether password  is expires r not");
		if (dateUtil.checkDate(user.getPasswordExpiryDate())) {
			logger.info("Password expired for user :" + user.getUserId());
			throw new PasswordExpiredException();
		}
		
		return true;
	}
	
	public void createUserObjectLoginSuccess(User user, UserVO userVO,
			UserLink userLink, SessionCache sessionCache)
			throws FileNotFoundInDatabase, SessionCacheNotLoadedException,
			PropertyNotFoundException {
		logger.debug("Login success");
		Integer num = Integer.parseInt(user.getTotalNoOfLogin()) + 1;
		user.setTotalNoOfLogin(num.toString());
		user.setNoOfAttempts(0);
		user.setIpAddress(userVO.getIpAddress());
		user.setSessionId(userVO.getSessionId());
		user.setLoginTime(dateUtil.getCurrentDateInDateDataType());
		try {
			loginSCHelper.setUserSessionObject(sessionCache, user, userLink);
		} catch (NoDataFoundException e) {
			throw new SessionCacheNotLoadedException();
		} catch (DatabaseException e) {
			throw new SessionCacheNotLoadedException();
		}
		
	}
	
}
