package com.jaw.attendance.service;

import java.util.List;

import com.jaw.attendance.controller.AttendanceDetailsListVO;
import com.jaw.attendance.controller.AttendanceListVO;
import com.jaw.attendance.controller.AttendanceMasterVO;
import com.jaw.attendance.dao.StudentAttendance;
import com.jaw.attendance.dao.StudentAttendanceList;
import com.jaw.common.exceptions.DatabaseException;
import com.jaw.common.exceptions.DeleteFailedException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.UpdateFailedException;
import com.jaw.common.exceptions.batch.AttendanceExistException;
import com.jaw.common.exceptions.batch.NoRecordFoundException;
import com.jaw.common.exceptions.util.PropertyNotFoundException;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.sessCache.StudentSession;
import com.jaw.framework.sessCache.UserSessionDetails;
import com.jaw.common.exceptions.batch.DateIsHolidayException;
import com.jaw.common.exceptions.batch.FutureDateException;

public interface IAttendanceService {
	
	void selectAttendanceData(AttendanceMasterVO attendanceMasterVO,
			UserSessionDetails userSessionDetails) throws  NoRecordFoundException, NoDataFoundException;
	
	public int checkDateIsHoliday(String acTerm,String holDate,String studentGrpId,
			UserSessionDetails userSessionDetails,String crslId);
	
	//public int checkAttendanceExist(StudentAttendance studentAttendance, String studentGrpId);
	
	public List<String> selectSubjectListForAtt(String stdGrpId,
			UserSessionDetails userSessionDetails);
	
	public List<StudentAttendanceList> getStudentListForAttendance(AttendanceMasterVO attendanceMasterVo,UserSessionDetails userSessionDetails)throws NoDataFoundException,AttendanceExistException, FutureDateException, DateIsHolidayException;
	
	public void markAttendance(List<StudentAttendanceList> stuAttList,
			UserSessionDetails userSessionDetails, AttendanceMasterVO attendanceMasterVO,ApplicationCache applicationCache)
			throws DatabaseException,
			DuplicateEntryException, PropertyNotFoundException, NoDataFoundException, UpdateFailedException;
	
	void getSubjectWiseAttendance(ApplicationCache applicationCache,
			UserSessionDetails userSessionDetails,
			StudentSession studentSession, AttendanceMasterVO attendanceMasterVO)
			throws NoDataFoundException, PropertyNotFoundException;
	
	void getConsolidatedAttendance(UserSessionDetails userSessionDetails,
			StudentSession studentSession, AttendanceMasterVO attendanceMasterVO)
			throws NoDataFoundException;
	
	void selectAttendanceDetailsData(AttendanceMasterVO attendanceMasterVO,
			AttendanceDetailsListVO attendanceDetailsListVO, UserSessionDetails userSessionDetails)
			throws NoDataFoundException;
	
	void updateAttendanceRec(AttendanceMasterVO attendanceMasterVO,
			AttendanceListVO attendanceListVO, UserSessionDetails userSessionDetails)
			throws UpdateFailedException, NoDataFoundException, DuplicateEntryException,
			DatabaseException, DeleteFailedException;
}
