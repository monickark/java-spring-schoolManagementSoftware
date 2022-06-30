package com.jaw.core.dao;

import java.util.List;

import com.jaw.common.exceptions.NoDataFoundException;

public interface IAcademicCalenderListDAO {
	
	List<AcademicCalendarList> getAcademicCalenderList() throws NoDataFoundException;
	
}
