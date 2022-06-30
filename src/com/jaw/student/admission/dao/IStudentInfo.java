package com.jaw.student.admission.dao;

import java.util.List;

import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.InsertFailedException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.UpdateFailedException;

public interface IStudentInfo {
	public void insertStudentInfo(StudentInfo studentInfo)
			throws DuplicateEntryException, InsertFailedException;

	public void updateStudentInfo(final StudentInfo studentInfo,
			final StudentInfoKey instMasterKey) throws UpdateFailedException;

	public StudentInfo retriveStudentInfo(StudentInfoKey studentAdmisNo) throws NoDataFoundException;
	

}
