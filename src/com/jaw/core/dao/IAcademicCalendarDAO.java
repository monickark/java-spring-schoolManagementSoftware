package com.jaw.core.dao;

import com.jaw.common.exceptions.DeleteFailedException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.UpdateFailedException;
import com.jaw.framework.sessCache.UserSessionDetails;

//Interface for Academic Calendar CRUD DAO 
public interface IAcademicCalendarDAO {
	void insertAcademicCalRec(AcademicCalendar academicCalRecord)
			throws DuplicateEntryException;

	void updateAcademicCalRec(AcademicCalendar academicCalRecord,final AcademicCalendarKey academicCalendarKey)
			throws UpdateFailedException;

	void deleteAcademicCalRec(AcademicCalendar academicCalRecord,final AcademicCalendarKey academicCalendarKey) throws DeleteFailedException;

	AcademicCalendar selectAcademicCalRec(
			final AcademicCalendarKey academicCalendarKey)
			throws NoDataFoundException;
	public AcademicCalendar selectAcademicCalDateForValidation(final String acTerm,UserSessionDetails userSessionDetails )throws NoDataFoundException;
	int checkAcademicCalRecExist(AcademicCalendar academicCalRecord);
}
