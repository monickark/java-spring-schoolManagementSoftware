package com.jaw.admin.service;

import java.io.IOException;

import javax.servlet.ServletContext;

import com.jaw.admin.controller.InstituteMasterVO;
import com.jaw.common.exceptions.DatabaseException;
import com.jaw.common.exceptions.DeleteFailedException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.FileNotFoundInDatabase;
import com.jaw.common.exceptions.FileNotSaveException;
import com.jaw.common.exceptions.UpdateFailedException;
import com.jaw.common.exceptions.batch.NoRecordFoundException;
import com.jaw.common.exceptions.util.PropertyNotFoundException;
import com.jaw.common.exceptions.util.TableNotSpecifiedForAuditException;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.sessCache.UserSessionDetails;

//Interface for Institute Master Service Class 
public interface IInstituteMasterService {

	void selectInstituteMasterRec(ApplicationCache applicationCache,
			InstituteMasterVO instVO) throws DatabaseException,
			PropertyNotFoundException;

	void updateInstituteMasterRec(InstituteMasterVO instVO,
			UserSessionDetails userSessionDetails,
			ApplicationCache applicationCache,ServletContext servletContext) throws DuplicateEntryException,
			FileNotFoundInDatabase, NoRecordFoundException, DatabaseException,
			UpdateFailedException, TableNotSpecifiedForAuditException, IllegalStateException, IOException, DeleteFailedException, FileNotSaveException;


}
