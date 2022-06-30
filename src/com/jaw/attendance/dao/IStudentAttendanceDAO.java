package com.jaw.attendance.dao;

import com.jaw.common.exceptions.DeleteFailedException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.UpdateFailedException;
import com.jaw.framework.sessCache.UserSessionDetails;
public interface IStudentAttendanceDAO {
	void insertStudentAttendanceRec(StudentAttendance studentAttendance)
			throws DuplicateEntryException;

	void updateStudentAttendanceRec(StudentAttendance studentAttendance,final StudentAttendanceKey studentAttendanceKey)
			throws UpdateFailedException;

	void deleteStudentAttendanceRec(StudentAttendance studentAttendance,final StudentAttendanceKey studentAttendanceKey) throws DeleteFailedException;

	StudentAttendance selectSpecialClassRec(
			final StudentAttendanceKey studentAttendanceKey)
			throws NoDataFoundException;
		//public int checkAttendanceExist(StudentAttendance studentAttendance,String studentGrpId);
	public int checkCollegeAttendanceExist(StudentAttendanceMaster studentAttendance,UserSessionDetails userSessionDetails);
}
