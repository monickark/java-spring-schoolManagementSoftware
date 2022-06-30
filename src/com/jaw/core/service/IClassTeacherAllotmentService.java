package com.jaw.core.service;

import java.util.List;
import java.util.Map;

import com.jaw.common.exceptions.DatabaseException;
import com.jaw.common.exceptions.DeleteFailedException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.util.TableNotSpecifiedForAuditException;
import com.jaw.core.controller.AllottedClassTeachersVO;
import com.jaw.core.controller.ClassTeacherAllotmentVO;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.sessCache.UserSessionDetails;
import com.jaw.staff.controller.StaffMasterVo;

public interface IClassTeacherAllotmentService {

	List<StaffMasterVo> selectStaff(String string,
			UserSessionDetails userSessionDetails) throws NoDataFoundException;

	void insertClassTeacher(ClassTeacherAllotmentVO classTeacher,
			UserSessionDetails userSessionDetails) throws DatabaseException, DuplicateEntryException;

	Map<String, String> getStudentGroupList(String academicYear,
			String courseId, UserSessionDetails userSessionDetails)
			throws NoDataFoundException;


	void deleteClassTeacher(ClassTeacherAllotmentVO classTeacher,
			UserSessionDetails userSessionDetails,
			ApplicationCache applicationCache) throws DeleteFailedException,
			NoDataFoundException, DuplicateEntryException, DatabaseException, TableNotSpecifiedForAuditException;

	Map<String, String> getClassTeachersList(
			ClassTeacherAllotmentVO classTeacherAllotmentVO,
			UserSessionDetails userSessionDetails);

	List<AllottedClassTeachersVO> getOldClassTeacherList(
			ClassTeacherAllotmentVO classTeacherAllotmentVO,
			UserSessionDetails userSessionDetails) throws NoDataFoundException;

	Map<String, String> getStudentBatchList(String stGroup,
			UserSessionDetails userSessionDetails, String acTerm)
			throws NoDataFoundException;

	void getAcTemStatus(ClassTeacherAllotmentVO classTeacherAllotmentVO,
			UserSessionDetails userSessionDetails) throws NoDataFoundException;

}
