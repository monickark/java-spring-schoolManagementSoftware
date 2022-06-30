package com.jaw.core.dao;

import com.jaw.attendance.dao.StudentAttendanceMaster;
import com.jaw.common.exceptions.DeleteFailedException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.UpdateFailedException;
import com.jaw.framework.sessCache.UserSessionDetails;

public interface ISubjectMasterDAO {
	void insertSubjectMasterRec(SubjectMaster subjectMaster)
			throws DuplicateEntryException;

	void updateSubjectMasterRec(SubjectMaster subjectMaster,final SubjectMasterKey subjectMasterKey)
			throws UpdateFailedException;

	void deleteSubjectMasterRec(SubjectMaster subjectMaster,final SubjectMasterKey subjectMasterKey) throws DeleteFailedException;

	SubjectMaster selectSubjectMasterRec(
			final SubjectMasterKey subjectMasterKey)
			throws NoDataFoundException;
	SubjectMaster selectSubjectMasterHasDelFlgYRec(
			final SubjectMasterKey subjectMasterKey)
			throws NoDataFoundException;
	void updateSubjectMasterToDelFlgNRec(SubjectMaster subjectMaster,final SubjectMasterKey subjectMasterKey)
			throws UpdateFailedException;
	int checkSubjectNameExist(SubjectMaster subjectMaster);
	int checkCustomCodeExist(SubjectMaster subjectMaster);
	
}
