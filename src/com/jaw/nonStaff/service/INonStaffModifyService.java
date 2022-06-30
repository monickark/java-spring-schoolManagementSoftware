package com.jaw.nonStaff.service;

import java.io.IOException;

import javax.servlet.ServletContext;

import com.jaw.common.exceptions.DatabaseException;
import com.jaw.common.exceptions.DeleteFailedException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.FileNotFoundInDatabase;
import com.jaw.common.exceptions.FileNotSaveException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.UpdateFailedException;
import com.jaw.common.exceptions.login.InvalidUserIdException;
import com.jaw.common.exceptions.util.TableNotSpecifiedForAuditException;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.sessCache.UserSessionDetails;
import com.jaw.user.controller.BranchAdminVO;

public interface INonStaffModifyService {

	void updateNonStaffDetails(ApplicationCache applicationCache,
			BranchAdminVO NonStaffVO, UserSessionDetails userSessionDetails,ServletContext servletContext)
			throws UpdateFailedException, NoDataFoundException,
			DuplicateEntryException, FileNotFoundInDatabase, DatabaseException,
			TableNotSpecifiedForAuditException, IllegalStateException, IOException, DeleteFailedException, FileNotSaveException;

	void deleteNonStaffDetails(BranchAdminVO branchAdminVO,
			UserSessionDetails userSessionDetails)
			throws DeleteFailedException, NoDataFoundException,
			InvalidUserIdException;

	void selectNonStaff(BranchAdminVO branchAdminVO,
			UserSessionDetails userSessionDetails)
			throws FileNotFoundInDatabase, NoDataFoundException;

}
