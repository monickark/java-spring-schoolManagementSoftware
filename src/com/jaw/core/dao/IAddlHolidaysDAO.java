package com.jaw.core.dao;

import com.jaw.common.exceptions.DeleteFailedException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.UpdateFailedException;
//Add Holidays CRUD DAO
public interface IAddlHolidaysDAO {
	void insertAddHolidaysRec(AddlHolidays addHolidays)
			throws DuplicateEntryException;

	void updateAddHolidaysRec(AddlHolidays addHolidays,final AddlHolidaysKey addHolidayskey)
			throws UpdateFailedException;

	void deleteAddHolidaysRec(AddlHolidays addHolidays,final AddlHolidaysKey addHolidayskey) throws DeleteFailedException;

	AddlHolidays selectAddHolidaysRec(
			final AddlHolidaysKey addHolidayskey)
			throws NoDataFoundException;
}
