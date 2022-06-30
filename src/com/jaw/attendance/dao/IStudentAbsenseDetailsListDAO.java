package com.jaw.attendance.dao;

import java.util.List;

import com.jaw.common.exceptions.DuplicateEntryException;

public interface IStudentAbsenseDetailsListDAO {
	void insertAttendanceAbsentRecs(List<StudentAbsenseDetails> studentAbsenseDetails)
			throws  DuplicateEntryException;
}
