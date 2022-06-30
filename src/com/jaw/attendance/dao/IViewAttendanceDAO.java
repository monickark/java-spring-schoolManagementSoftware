package com.jaw.attendance.dao;

import java.util.List;

import com.jaw.attendance.controller.ViewAttendance;
import com.jaw.common.exceptions.NoDataFoundException;

public interface IViewAttendanceDAO {
	List<StudentAttendance> attendanceWithoutLab(StudentAttendance studentAttendance)
			throws NoDataFoundException;
	
	List<StudentAttendance> attendanceWithLabSeperatly(StudentAttendance studentAttendance)
			throws NoDataFoundException;
	
	List<ViewAttendance> consolidateAttendance(StudentAttendance studentAttendance)
			throws NoDataFoundException;
	List<ViewAttendance> getConsolidateAttendance(ViewAttendanceKey viewAttendanceKey)
			throws NoDataFoundException;
	
	List<ViewAttendance> subjectAttendanceWithCheckLab(ViewAttendanceKey viewAttendanceKey)
			throws NoDataFoundException;
	List<ViewAttendance> subjectAttendanceWithLabSeperatly(ViewAttendanceKey viewAttendanceKey)
			throws NoDataFoundException;
}
