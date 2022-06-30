package com.jaw.attendance.service;

import com.jaw.attendance.controller.ViewAttendanceMasterVO;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.util.PropertyNotFoundException;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.sessCache.ParentSession;
import com.jaw.framework.sessCache.StudentSession;
import com.jaw.framework.sessCache.UserSessionDetails;

public interface IViewAttendanceService {
	
	void getMonth(ViewAttendanceMasterVO attendanceMasterVO, UserSessionDetails userSessionDetails)
			throws NoDataFoundException;
	
	void selectViewAttendanceData(ViewAttendanceMasterVO attendanceMasterVO,
			UserSessionDetails userSessionDetails,
			ApplicationCache applicationCache, StudentSession studentSession,
			ParentSession parentSession) throws NoDataFoundException,
			PropertyNotFoundException;

	void selectSubjectAttendance(ApplicationCache applicationCache,
			ViewAttendanceMasterVO attendanceMasterVO,
			UserSessionDetails userSessionDetails,
			StudentSession studentSession, ParentSession parentSession)
			throws NoDataFoundException;
	
}
