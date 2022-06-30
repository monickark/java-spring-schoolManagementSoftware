package com.jaw.core.dao;

import java.util.List;

import com.jaw.common.exceptions.NoDataFoundException;

public interface IAcademicCalendarListDAO {
	public List<AcademicCalendar> getAcademicCalendarList(AcademicCalendarListKey academicCalendarListKey)
			throws NoDataFoundException;
	public List<AcademicCalendar> getAcademicCalendarDetailsForDashBoard(AcademicCalendarListKey academicCalendarListKey)
			throws NoDataFoundException;
}
