package com.jaw.core.service;

import com.jaw.common.exceptions.DatabaseException;
import com.jaw.common.exceptions.DeleteFailedException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.UpdateFailedException;
import com.jaw.common.exceptions.util.TableNotSpecifiedForAuditException;
import com.jaw.core.controller.StudentGroupMasterVO;
import com.jaw.core.controller.StudentGroupVO;
import com.jaw.core.dao.StudentGroupMaster;
import com.jaw.core.dao.StudentGroupMasterKey;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.sessCache.UserSessionDetails;

public interface IStudentGroupMasterService {
	void insertStudentGroupMasterRec(StudentGroupVO stuGrpVO,
			UserSessionDetails userSessionDetails)
			throws DuplicateEntryException, DatabaseException;

	void updateStudentGroupMasterRec(StudentGroupVO stuGrpVO,
			UserSessionDetails userSessionDetails,ApplicationCache applicationCache) throws UpdateFailedException, NoDataFoundException, DuplicateEntryException, DatabaseException, TableNotSpecifiedForAuditException;

	public StudentGroupMasterVO selectStudentGroupList(
			StudentGroupMasterVO studentGroupMasterVO,UserSessionDetails userSessionDetails)
			throws NoDataFoundException;

	


	void deleteStudentGrpMasterRec(StudentGroupVO studentGroupVO,
			UserSessionDetails userSessionDetails,ApplicationCache applicationCache)
			throws DeleteFailedException, NoDataFoundException, DuplicateEntryException, DatabaseException, TableNotSpecifiedForAuditException;
}
