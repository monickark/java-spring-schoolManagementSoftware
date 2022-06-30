package com.jaw.core.dao;

import java.util.List;

import com.jaw.common.exceptions.DeleteFailedException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.UpdateFailedException;
import com.jaw.common.exceptions.batch.DuplicateEntryForHolGenException;

public interface IHolidayMaintenanceListDAO {
	
	 void insertHolidayMaintenanceListRecs(
			final List<HolidayMaintenance> holidayMaintenanceList) throws DuplicateEntryForHolGenException;
	 void updateHolidayMaintenanceListRecs(
				final List<HolidayMaintenance> holidayMaintenanceList,final List<HolidayMaintenanceKey> holidayMaintenanceKeyList) throws   UpdateFailedException;
	 void updateHolidayMaintenanceListToDelFlgNRecs(
				final List<HolidayMaintenance> holidayMaintenanceList,final List<HolidayMaintenanceKey> holidayMaintenanceKeyList) throws  UpdateFailedException;
	 public List<AcademicCalendar> getHolidayDetailsForDashBoard(
				AcademicCalendarListKey academicCalendarListKey,String userMenuProfile,String studentGrpId)
				throws NoDataFoundException;
}
