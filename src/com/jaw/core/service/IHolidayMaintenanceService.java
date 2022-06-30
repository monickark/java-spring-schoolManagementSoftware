package com.jaw.core.service;

import java.util.List;

import com.jaw.common.exceptions.DatabaseException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.batch.DuplicateEntryForHolGenException;
import com.jaw.core.controller.AcademicCalendarVO;
import com.jaw.core.controller.HolidayGenerationVO;
import com.jaw.core.dao.AcademicCalendarListKey;
import com.jaw.core.dao.AcademicTermDetailsKey;
import com.jaw.core.dao.HolidayMaintenance;
import com.jaw.framework.sessCache.SessionCache;
import com.jaw.framework.sessCache.UserSessionDetails;

public interface IHolidayMaintenanceService {
	public void insertHolidayMaintenanceBatchRec(
			HolidayGenerationVO holidayGenerationVO,UserSessionDetails userSessionDetails) throws  DuplicateEntryForHolGenException, DuplicateEntryException, DatabaseException;
	public boolean checkHolRecsAvble(AcademicTermDetailsKey academicTermDetailsKey,UserSessionDetails userSessionDetails);
	public List<String> selectAcTrmForHlyGen(UserSessionDetails userSessionDetails,String branchId);
	public List<AcademicCalendarVO> getHolidayDetailsForDashBoard(
			AcademicCalendarListKey academicCalendarListKey,String monthYear,SessionCache sessionCache)
			throws NoDataFoundException;
}
