package com.jaw.attendance.dao;

import java.util.List;


import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.framework.sessCache.UserSessionDetails;

public interface IAttendanceListDAO {
	
	List<AttendanceList> getAttendanceList(AttendanceList attendanceList)
			throws NoDataFoundException;
			
/*
List<StudentAttendanceList> getStudentsForAttendance(StudentAttendanceListKey studentAttendanceListKey)throws NoDataFoundException;

void insertAttendanceRecs(List<StudentAttendance> studentAttendanceList)
		throws  DuplicateEntryException;
*/
	
}
