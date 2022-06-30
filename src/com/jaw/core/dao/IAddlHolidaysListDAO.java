package com.jaw.core.dao;

import java.util.List;

import com.jaw.common.exceptions.NoDataFoundException;


public interface IAddlHolidaysListDAO {
	
	public List<AddlHolidays> getAddlHolidaysList(AddlHolidaysKey addlHolidaysKey) throws NoDataFoundException;

	
	
}
