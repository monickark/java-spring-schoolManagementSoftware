package com.jaw.attendance.dao;

import java.util.List;

import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.batch.NoRecordFoundException;
import com.jaw.framework.sessCache.UserSessionDetails;

public interface IStudentAttendanceMasterListDAO {
	
		List<StudentAttendanceMasterList> getStudentAttendanceMasterList(
			StudentAttendanceMasterList studentAttendanceMasterList,
			UserSessionDetails userSessionDetails)
			throws NoRecordFoundException;
	
}
