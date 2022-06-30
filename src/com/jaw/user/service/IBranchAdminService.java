package com.jaw.user.service;

import java.io.IOException;

import javax.servlet.ServletContext;

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
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.sessCache.UserSessionDetails;
import com.jaw.user.controller.BranchAdminVO;

public interface IBranchAdminService {

	public void insertBranchAdmin(BranchAdminVO adminVO,
			UserSessionDetails userSessionDetails,
			ApplicationCache applicationCache,ServletContext servletContext) throws FileNotFoundInDatabase,
			DuplicateEntryException, InvalidUserIdException,
			UpdateFailedException, DatabaseException, NumberFormatException,
			PropertyNotFoundException, IllegalStateException, IOException, DeleteFailedException, FileNotSaveException;

	void selectStaffDetails(BranchAdminVO adminVO,
			UserSessionDetails userSessionDetails) throws NoDataFoundException,
			UserNotCreatedException;
}
