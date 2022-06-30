package com.jaw.user.service;

import java.util.List;

import com.jaw.common.exceptions.DatabaseException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.UpdateFailedException;
import com.jaw.common.exceptions.login.InvalidUserIdException;
import com.jaw.common.exceptions.util.PropertyNotFoundException;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.sessCache.SessionCache;
import com.jaw.framework.sessCache.UserSessionDetails;
import com.jaw.user.controller.UserManagementVO;

public interface IUserManagementService {
	List<String> getMenuProfileList(UserSessionDetails userSessionDetails)
			throws NoDataFoundException;
	
	UserManagementVO userEnableOrDisable(UserManagementVO managementVO,
			UserSessionDetails userSessionDetails,
			ApplicationCache applicationCache) throws DuplicateEntryException,
			InvalidUserIdException, DatabaseException, NumberFormatException,
			PropertyNotFoundException, UpdateFailedException;
	
	List<UserManagementVO> getUserDetailsForUserManagement(
			ApplicationCache applicationCache, SessionCache sessionCache,
			String branchId, String instId, UserManagementVO userManagementVO,
			String url) throws NoDataFoundException;
	
}
