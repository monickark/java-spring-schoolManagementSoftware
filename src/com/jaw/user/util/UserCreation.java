package com.jaw.user.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.jaw.common.business.CommonBusiness;
import com.jaw.common.constants.ApplicationConstant;
import com.jaw.common.constants.PropertyManagementConstant;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.util.PropertyNotFoundException;
import com.jaw.common.util.DateUtil;
import com.jaw.common.util.PropertyManagementUtil;
import com.jaw.security.SecurityCheck;
import com.jaw.user.controller.UserCreationVO;
import com.jaw.user.dao.IUserDao;
import com.jaw.user.dao.IUserLinkDao;
import com.jaw.user.dao.User;
import com.jaw.user.dao.UserLink;

@Component
public class UserCreation {
	
	@Autowired
	PropertyManagementUtil propertyManagementUtil;
	@Autowired
	DateUtil dateUtil;
	@Autowired
	IUserLinkDao userLinkDao;
	@Autowired
	IUserDao userDao;
	@Autowired
	CommonBusiness commonBusiness;
	
	public String generatePassword(String username) {
		SecurityCheck createPass = new SecurityCheck();
		String password = createPass
				.createPassword(generateUserId(username));
		return password;
	}
	
	public String generateUserId(String userName) {
		int value = (int) (Math.random() * 88477);
		
		try {
			if (userName.length() > 4) {
				userName = (String) userName.subSequence(0, 5);
			}
			else {
				
				int i = 5 - userName.length();
				int count = 0;
				while (count < i) {
					userName = userName.concat("a");
					count++;
				}
			}
		}
		catch (StringIndexOutOfBoundsException stringIndexOutOfBoundException) {
			
		}
		
		return userName + "" + value;
		
	}
	
	public String recreateUserID(String userid) {
		int value = (int) (Math.random() * 884777);
		userid = (String) userid.subSequence(0, 4);
		return userid + "" + value;
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void createUser(UserCreationVO userCreation)
			throws DuplicateEntryException, NumberFormatException,
			PropertyNotFoundException {
		insertUser(userCreation);
		insertUserLink(userCreation);
	}
	
	public void insertUser(UserCreationVO userCreation)
			throws NumberFormatException, PropertyNotFoundException,
			DuplicateEntryException {
		
		User user = new User();
		user.setDb_ts(1);
		user.setInstId(userCreation.getUserSessionDetails().getInstId());
		user.setBranchId(userCreation.getUserSessionDetails().getBranchId());
		user.setUserId(userCreation.getUserId());
		user.setPassword(commonBusiness
				.createPasswordForUser(userCreation.getPassword()));
		
		int days = Integer.parseInt(propertyManagementUtil.getPropertyValue(
				userCreation.getApplicationCache(),
				userCreation.getUserSessionDetails().getInstId(),
				userCreation.getUserSessionDetails().getBranchId(),
				PropertyManagementConstant.PWD_EXPIRY_PRD).toString());
		
		user.setPasswordExpiryDate(dateUtil.addDaysToCurrentDate(days));
		user.setLastLogoutTime(ApplicationConstant.LAST_LOGOUT_TIME);
		user.setTotalNoOfLogin("0");
		user.setUserEnableFlag("Y");
		user.setDeleteFlag("N");
		user.setrModId(userCreation.getUserSessionDetails().getUserId());
		user.setrCreId(userCreation.getUserSessionDetails().getUserId());
		user.setPasswordResetFlag("Y");
		userDao.insertSingleUser(user);
	}
	
	public void insertUserLink(UserCreationVO userCreation)
			throws DuplicateEntryException {
		
		UserLink user = new UserLink();
		user.setDb_ts(1);
		user.setInstId(userCreation.getUserSessionDetails().getInstId());
		user.setBranchId(userCreation.getUserSessionDetails().getBranchId());
		user.setUserId(userCreation.getUserId());
		user.setProfileGroup(userCreation.getProfileGroup());
		user.setUserMenuProfile(userCreation.getMenuProfile());
		user.setLinkId(userCreation.getLinkId());
		user.setRole(userCreation.getRole());
		user.setDeleteFlag("N");
		user.setrModId(userCreation.getUserSessionDetails().getUserId());
		user.setrCreId(userCreation.getUserSessionDetails().getUserId());
		userLinkDao.insertUserLink(user);
	}
	
}
