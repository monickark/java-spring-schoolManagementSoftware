package com.jaw.core.service;



import java.text.ParseException;
import java.util.List;

import com.jaw.common.exceptions.AcadCalFutureDateDeleteFailedException;
import com.jaw.common.exceptions.AcadCalendarDeleteFailedException;
import com.jaw.common.exceptions.DatabaseException;
import com.jaw.common.exceptions.DeleteFailedException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.UpdateFailedException;
import com.jaw.common.exceptions.batch.DuplicateEntryForAcaTermHolGenException;
import com.jaw.common.exceptions.batch.DuplicateEntryForHolGenException;
import com.jaw.common.exceptions.util.TableNotSpecifiedForAuditException;
import com.jaw.core.controller.AcademicCalendarMasterVO;
import com.jaw.core.controller.AcademicCalendarVO;
import com.jaw.core.controller.StudentGroupMasterVO;
import com.jaw.core.dao.AcademicCalendar;
import com.jaw.core.dao.AcademicCalendarListKey;
import com.jaw.core.dao.AcademicTermDetailsKey;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.sessCache.UserSessionDetails;
//Interface for Academic Calendar Service Class
public interface IAcademicCalendarService {
	
	void insertAcademicCalRec(AcademicCalendarVO acadcaVO,
			UserSessionDetails userSessionDetails) throws DuplicateEntryException, DatabaseException, DuplicateEntryForAcaTermHolGenException, NoDataFoundException, UpdateFailedException , DuplicateEntryForHolGenException ;
	
	public AcademicCalendarVO getAcademicTermDateRec(
			AcademicCalendarVO academicCalKey,UserSessionDetails userSessionDetails) throws NoDataFoundException ;
	public void updateAcademicCalRec(AcademicCalendarVO acadcaVO,
			UserSessionDetails userSessionDetails,ApplicationCache applicationCache) throws UpdateFailedException,NoDataFoundException, DuplicateEntryException, DatabaseException, TableNotSpecifiedForAuditException;
	public AcademicCalendarMasterVO selectStudentGroupList(
			AcademicCalendarMasterVO acadCalMtrVO,UserSessionDetails userSessionDetails)
			throws NoDataFoundException;
	public void deleteAcademicCalendarRec(
			AcademicCalendarVO acadCalVo,
			UserSessionDetails userSessionDetails,ApplicationCache applicationCache)
			throws DeleteFailedException, NoDataFoundException ,AcadCalendarDeleteFailedException, DuplicateEntryException, DatabaseException, TableNotSpecifiedForAuditException, AcadCalFutureDateDeleteFailedException, ParseException, DuplicateEntryForHolGenException, UpdateFailedException;
	boolean checkAcTermValidation(String acTerm,UserSessionDetails userSessionDetails);
	public AcademicCalendarVO getAcTermDateForValidation(String acTerm,
			UserSessionDetails userSessionDetails);
	int checkHolidayEntered(AcademicCalendar academicCalendar) ;
	int checkHolidayExistForStudentGrp(AcademicCalendar academicCalendar) ;
	void removeAddlHolForStudentGrp(AcademicCalendar academicCalendar) ;
	public List<AcademicCalendarVO> getAcademicCalendarDetailsForDashBoard(AcademicCalendarListKey academicCalendarListKey,String monthYear)
			throws NoDataFoundException;
	public List<String> getListOfMonthsForAcademicTerm(UserSessionDetails userSessionDetails)
			throws NoDataFoundException;
	
}
