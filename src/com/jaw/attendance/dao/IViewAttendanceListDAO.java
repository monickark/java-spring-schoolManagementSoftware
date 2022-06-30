package com.jaw.attendance.dao;

import java.util.List;

import com.jaw.common.exceptions.NoDataFoundException;

public interface IViewAttendanceListDAO {
		
	List<ViewAttendanceList> getSubjectWiseAttendance(ViewAttendanceList attendanceList)
			throws NoDataFoundException;

	List<ViewAttendanceList> getViewAttendanceList(
			ViewAttendanceList attendanceList, String incLabAttendance)
			throws NoDataFoundException;
	
}
