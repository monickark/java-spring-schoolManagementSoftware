package com.jaw.core.service;

import java.util.List;

import com.jaw.common.exceptions.CustomAndSubjectCodeExistException;
import com.jaw.common.exceptions.DatabaseException;
import com.jaw.common.exceptions.DeleteFailedException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.UpdateFailedException;
import com.jaw.common.exceptions.util.TableNotSpecifiedForAuditException;
import com.jaw.core.controller.SpecialClassMasterVO;
import com.jaw.core.controller.SpecialClassVO;
import com.jaw.core.controller.SubjectMasterVO;
import com.jaw.core.controller.SubjectMaster_MasterVO;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.sessCache.UserSessionDetails;

public interface ISubjectMasterService {
	void insertSubjectMasterRec(SubjectMasterVO subjectMasterVO,
			UserSessionDetails userSessionDetails,ApplicationCache applicationCache)
			throws DuplicateEntryException, DatabaseException, NoDataFoundException, UpdateFailedException, TableNotSpecifiedForAuditException,  CustomAndSubjectCodeExistException;

	void updateSubjectMasterRec(SubjectMasterVO subjectMasterVO,
			UserSessionDetails userSessionDetails,ApplicationCache applicationCache) throws UpdateFailedException, NoDataFoundException, DuplicateEntryException, DatabaseException, TableNotSpecifiedForAuditException, CustomAndSubjectCodeExistException;

	public SubjectMaster_MasterVO selectSubjectMasterList(
			SubjectMaster_MasterVO subjectMaster_MasterVO,UserSessionDetails usersessiondetails)
			throws NoDataFoundException;

	public void deleteSubjectMasterRec(SubjectMasterVO subjectMasterVO,UserSessionDetails userSessionDetails,ApplicationCache applicationCache)
			throws DeleteFailedException, NoDataFoundException, DuplicateEntryException, DatabaseException, TableNotSpecifiedForAuditException;
	
}
