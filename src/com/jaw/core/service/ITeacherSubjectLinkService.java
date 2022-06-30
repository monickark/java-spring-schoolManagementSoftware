package com.jaw.core.service;

import com.jaw.common.exceptions.DatabaseException;
import com.jaw.common.exceptions.DeleteFailedException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.UpdateFailedException;
import com.jaw.common.exceptions.util.TableNotSpecifiedForAuditException;
import com.jaw.core.controller.TeacherSubjectLinkMasterVO;
import com.jaw.core.controller.TeacherSubjectLinkVO;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.sessCache.UserSessionDetails;

public interface ITeacherSubjectLinkService {
	void insertTeacherSubLinkRec(TeacherSubjectLinkVO trSubLinkVO,
			UserSessionDetails userSessionDetails,ApplicationCache applicationCache)
			throws DuplicateEntryException, DatabaseException, DeleteFailedException, NoDataFoundException, TableNotSpecifiedForAuditException;

	void updateTeacherSubLinkRec(TeacherSubjectLinkVO trSubLinkVO,
			UserSessionDetails userSessionDetails,ApplicationCache applicationCache) throws UpdateFailedException, NoDataFoundException, DuplicateEntryException, DatabaseException, TableNotSpecifiedForAuditException;

	public TeacherSubjectLinkMasterVO selectTeacherSubjectLinkList(
			TeacherSubjectLinkMasterVO trSubLinkMtrVO,UserSessionDetails userSessionDetails)
			throws NoDataFoundException;

	
	public void deleteTeacherSubLinkRec(TeacherSubjectLinkVO trSubLinkVO,UserSessionDetails userSessionDetails,ApplicationCache applicationCache)
			throws DeleteFailedException, NoDataFoundException, DuplicateEntryException, DatabaseException, TableNotSpecifiedForAuditException;
}
