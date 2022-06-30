package com.jaw.core.dao;

import java.util.List;

import com.jaw.common.exceptions.DeleteFailedException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.UpdateFailedException;
import com.jaw.framework.sessCache.UserSessionDetails;

public interface IHolidayMaintenanceDAO {
	void insertHolidayMaintenanceRec(HolidayMaintenance holidayMaintenance)
			throws DuplicateEntryException;

	void updateHolidayMaintenanceRec(HolidayMaintenance holidayMaintenance,final HolidayMaintenanceKey holidayMaintenancekey)
			throws UpdateFailedException;

	void deleteHolidayMaintenanceRec(HolidayMaintenance holidayMaintenance,final HolidayMaintenanceKey holidayMaintenanceKey) throws DeleteFailedException;

	HolidayMaintenance selectHolidayMaintenanceRec(
			final HolidayMaintenanceKey holidayMaintenanceKey)
			throws NoDataFoundException;
	HolidayMaintenance selectHolidayMaintenanceRecForDelFlgY(
			final HolidayMaintenanceKey holidayMaintenanceKey)
			;
	
	 boolean checkHolRecsAvble(final HolidayMaintenanceKey holidayMaintenanceKey) ;
	int checkDateIsHoliday(final HolidayMaintenanceKey holidayMaintenanceKey,String studentGrpId);
	int checkHolidayDateExist(final HolidayMaintenanceKey holidayMaintenanceKey,List<String> holDates,String studentGrpId);
	int checkHolidayDateExistForAcademicCalendarAddlHol(final HolidayMaintenanceKey holidayMaintenanceKey,List<String> holDates);
	int checkHolidayDateExistForAcademicCalendar(final HolidayMaintenanceKey holidayMaintenanceKey,List<String> holDates);
	
	void removeHolidayAcademicCalendarAddlHol(final HolidayMaintenanceKey holidayMaintenanceKey,List<String> holDates);
}
