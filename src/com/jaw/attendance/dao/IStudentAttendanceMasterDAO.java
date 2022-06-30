package com.jaw.attendance.dao;

import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.UpdateFailedException;
import com.jaw.core.dao.AcademicCalendar;
import com.jaw.core.dao.AcademicCalendarKey;
import com.jaw.framework.sessCache.UserSessionDetails;

public interface IStudentAttendanceMasterDAO {
	
	public void insertStudentAttendanceMasterRec(final StudentAttendanceMaster studentAttendanceMaster)
			throws DuplicateEntryException ;
	 public int checkCollegeAttendanceExist(StudentAttendanceMaster studentAttendance,UserSessionDetails userSessionDetails);
	 public int checkAttendanceMarkedForSplCls(StudentAttendanceMaster studentAttendance,UserSessionDetails userSessionDetails);
	 void updateAttendanceStatusLock(StudentAttendanceMaster studentAttendanceMaster,final StudentAttendanceMasterKey studentAttendanceMasterKey)
				throws UpdateFailedException;
	
}
