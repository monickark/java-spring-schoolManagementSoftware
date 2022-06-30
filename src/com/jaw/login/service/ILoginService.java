package com.jaw.login.service;

import java.io.FileNotFoundException;

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
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.sessCache.SessionCache;
import com.jaw.framework.sessCache.UserSessionDetails;
import com.jaw.user.controller.UserVO;
import com.jaw.user.dao.User;

public interface ILoginService {
	
	void loginUser(UserVO uservo, ApplicationCache applicationCache,
			SessionCache sessionCache) throws DuplicateEntryException,
			PasswordMismatchException, InvalidAttemptException,
			PasswordNotResetException, PasswordExpiredException,
			UserDisabledException, FileNotFoundInDatabase,
			FileNotFoundException, InvalidUserIdException, DatabaseException,
			SessionCacheNotLoadedException,
			PasswordrequestedLoginFailException, NumberFormatException,
			PropertyNotFoundException, UpdateFailedException, NoDataFoundException;
	
	boolean insertLogin(User user) throws DuplicateEntryException;
	
	void updatePassword(UserVO user, ApplicationCache applicationCache)
			throws PasswordMismatchException, WrongPasswordException,
			DuplicatePasswordException, DuplicateEntryException,
			InvalidUserIdException, DatabaseException, UpdateFailedException,
			PropertyNotFoundException;
	
	void logout(UserSessionDetails userSessionDetails)
			throws DuplicateEntryException, InvalidUserIdException,
			DatabaseException, UpdateFailedException;
	
}
