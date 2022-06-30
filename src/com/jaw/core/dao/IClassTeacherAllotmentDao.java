package com.jaw.core.dao;

import com.jaw.common.exceptions.DeleteFailedException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.UpdateFailedException;

public interface IClassTeacherAllotmentDao {

	void updateStaff(ClassTeacherAllotment standard,
			ClassTeacherAllotmentKey standardKey) throws UpdateFailedException;

	ClassTeacherAllotment selectStaff(ClassTeacherAllotmentKey standardKey)
			throws NoDataFoundException;
	
	void insertStaff(ClassTeacherAllotment classteacher)
			throws DuplicateEntryException;

	void deleteClassTeacherAllotted(
			ClassTeacherAllotmentKey classTeacherAllotmentKey)
			throws DeleteFailedException;
	ClassTeacherAllotment selectStudentBatchForStaff(
			ClassTeacherAllotmentKey classTeacherAllotmentKey,String studentGrpId);


}
