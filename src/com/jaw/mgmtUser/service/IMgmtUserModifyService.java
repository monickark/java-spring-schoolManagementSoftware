package com.jaw.mgmtUser.service;

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
import com.jaw.common.exceptions.util.PropertyNotFoundException;
import com.jaw.common.exceptions.util.TableNotSpecifiedForAuditException;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.sessCache.UserSessionDetails;
import com.jaw.mgmtUser.controller.MgmtUserVO;

//Interface for Institute Master Service Class 
public interface IMgmtUserModifyService {
	void insertManagementDetails(MgmtUserVO managementVO,
			UserSessionDetails userSessionDetails,
			ApplicationCache applicationCache,ServletContext servletContext) throws FileNotFoundInDatabase,
			DuplicateEntryException, DatabaseException, NumberFormatException,
			PropertyNotFoundException, IllegalStateException, IOException, DeleteFailedException, FileNotSaveException;

	public void deleteManagementDetails(MgmtUserVO managementVO,
			UserSessionDetails userSessionDetails)
			throws DeleteFailedException, NoDataFoundException,
			InvalidUserIdException;

	void selectManagement(MgmtUserVO managementVO,
			UserSessionDetails userSessionDetails)
			throws FileNotFoundInDatabase, NoDataFoundException;

	void updateManagementDetails(ApplicationCache applicationCache,
			MgmtUserVO managementVO, UserSessionDetails userSessionDetails,ServletContext servletContext)
			throws UpdateFailedException, NoDataFoundException,
			DuplicateEntryException, FileNotFoundInDatabase, DatabaseException,
			TableNotSpecifiedForAuditException, IllegalStateException, IOException, DeleteFailedException, FileNotSaveException;

}
