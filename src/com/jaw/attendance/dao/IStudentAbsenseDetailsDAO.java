package com.jaw.attendance.dao;

import com.jaw.common.exceptions.DeleteFailedException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.UpdateFailedException;

public interface IStudentAbsenseDetailsDAO {
	
	void insertStudentAbsenseDetailsRec(
			StudentAbsenseDetails StudentAbsenseDetails)
			throws DuplicateEntryException;
	
	StudentAbsenseDetails selectStudentAbsenseDetailsRec(
			StudentAbsenseDetailsKey StudentAbsenseDetailsKey) throws NoDataFoundException;
	
	void updateStudentAbsenseDetailsRec(StudentAbsenseDetails studentAbsenseDetails,
			StudentAbsenseDetailsKey studentAbsenseDetailsKey) throws UpdateFailedException;
	
	void deleteStudentAbsenseDetailsRec(StudentAbsenseDetails StudentAbsenseDetails,
			StudentAbsenseDetailsKey StudentAbsenseDetailsKey) throws DeleteFailedException;
	
	void manualDeleteStudentAbsenseDetailsRec(StudentAbsenseDetailsKey StudentAbsenseDetailsKey)
			throws DeleteFailedException;
	
}
