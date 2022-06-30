package com.jaw.core.service;

import java.text.ParseException;

import com.jaw.common.exceptions.DatabaseException;
import com.jaw.common.exceptions.DeleteFailedException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.UpdateFailedException;
import com.jaw.common.exceptions.batch.DuplicateEntryForHolGenException;
import com.jaw.core.controller.AddlHolidaysMasterVO;
import com.jaw.core.controller.AddlHolidaysVO;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.sessCache.UserSessionDetails;
import com.jaw.common.exceptions.batch.DuplicateEntryForAcaTermHolGenException;
import com.jaw.common.exceptions.util.TableNotSpecifiedForAuditException;
public interface IAdditionalHolidaysService {
	void insertAdditionalHolidaysRec(AddlHolidaysVO addlHVO,
			UserSessionDetails userSessionDetails) throws DuplicateEntryException, DatabaseException,DuplicateEntryForHolGenException,DuplicateEntryForAcaTermHolGenException, NoDataFoundException, UpdateFailedException;
	AddlHolidaysMasterVO getListOfAddlHolidays(AddlHolidaysMasterVO addHolidaysSearch,UserSessionDetails userSessionDetails) throws NoDataFoundException;
	void removeAddHolidaysRec(AddlHolidaysVO addlHolidaysVO,
			UserSessionDetails userSessionDetails,ApplicationCache applicationCache) throws DeleteFailedException, NoDataFoundException, DuplicateEntryException, DatabaseException, TableNotSpecifiedForAuditException, ParseException, DuplicateEntryForHolGenException, UpdateFailedException;
	
	public void updateAddlHolidaysRec(AddlHolidaysVO addlHolidayVO,
			UserSessionDetails userSessionDetails,ApplicationCache applicationCache) throws UpdateFailedException, NoDataFoundException, DuplicateEntryException, DatabaseException, TableNotSpecifiedForAuditException;
	
	
}
