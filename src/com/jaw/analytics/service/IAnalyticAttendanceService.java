package com.jaw.analytics.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.jaw.attendance.controller.ViewAttendance;
import com.jaw.attendance.dao.ViewAttendanceKey;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.util.PropertyNotFoundException;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.sessCache.UserSessionDetails;

public interface IAnalyticAttendanceService {
	List<ViewAttendance> selectConsolidateAttendance(ViewAttendanceKey viewAttendanceKey)
			throws NoDataFoundException;
	
	Set<ViewAttendance> selectSubjecteAttendance(ViewAttendanceKey viewAttendanceKey,UserSessionDetails userSessionDetails,
			ApplicationCache applicationCache)
			throws NoDataFoundException, PropertyNotFoundException;
	
	Map<String,String> selectStudentListStaffAnalytic(UserSessionDetails userSessionDetails,String studentGrp,String crslId,String studentBatch)
			throws NoDataFoundException;
}
