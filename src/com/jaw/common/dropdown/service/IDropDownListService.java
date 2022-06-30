package com.jaw.common.dropdown.service;

import java.util.List;
import java.util.Map;

import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.util.PropertyNotFoundException;
import com.jaw.core.dao.CourseSubLink;
import com.jaw.core.dao.StudentGroupMaster;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.sessCache.SessionCache;
import com.jaw.framework.sessCache.UserSessionDetails;

public interface IDropDownListService {
	public Map<String, String> getTermListForCurAcYear(
			UserSessionDetails userSessionDetails) throws NoDataFoundException;

	public Map<String, String> getBranchListTag(
			final UserSessionDetails userSessionDetails)
			throws NoDataFoundException;

	public Map<String, String> getCourseNameListTag(
			final UserSessionDetails userSessionDetails)
			throws NoDataFoundException;

	public List<StudentGroupMaster> getStudentGroupListTag(
			SessionCache sessionCache, String acTerm)
			throws NoDataFoundException;

	public List<StudentGroupMaster> getStudentGroupListTagForDashBoard(
			SessionCache sessionCache, String acTerm)
			throws NoDataFoundException;

	public Map<String, String> getAcademicTermListTag(
			UserSessionDetails userSessionDetails) throws NoDataFoundException;

	public Map<String, String> getAcademicTermListForPresentAndFuture(
			UserSessionDetails userSessionDetails) throws NoDataFoundException;

	public Map<String, String> selectTeachingStaffList(
			final UserSessionDetails userSessionDetails)
			throws NoDataFoundException;

	public Map<String, String> selectExamNameList(
			UserSessionDetails userSessionDetails, String studentGrpId)
			throws NoDataFoundException;

	public List<CourseSubLink> selectSubListForStudentGrp(
			final String stdGrpId, final UserSessionDetails userSessionDetails)
			throws NoDataFoundException;

	public List<CourseSubLink> selectSubListForStudentGrpAnalytics(
			ApplicationCache applicationCache, final String stdGrpId,
			final UserSessionDetails userSessionDetails)
			throws NoDataFoundException, PropertyNotFoundException;

	Map<String, String> selectSubjectList(String stdGrpId,
			UserSessionDetails userSessionDetails) throws NoDataFoundException;

	public Map<String, String> selectAllSubjectList(
			UserSessionDetails userSessionDetails) throws NoDataFoundException;

	Map<String, String> getTermList(final UserSessionDetails userSessionDetails)
			throws NoDataFoundException;

	Map<String, String> getSectionList(
			final UserSessionDetails userSessionDetails, final String courseId,
			final String trmId, final String stuGrpId)
			throws NoDataFoundException;

	public Map<String, String> getPresentAcademicTermTag(
			UserSessionDetails userSessionDetails) throws NoDataFoundException;

	Map<String, String> selectMenuProfileList(String profileGroup,
			UserSessionDetails userSessionDetails) throws NoDataFoundException;

	public Map<String, String> getDepartementList(
			UserSessionDetails userSessionDetails) throws NoDataFoundException;

	Map<String, String> getStGroupModelAttr(
			UserSessionDetails userSessionDetails) throws NoDataFoundException;

	Map<String, String> getStudentAdmisNo(
			UserSessionDetails userSessionDetails, String stGroup)
			throws NoDataFoundException;

	Map<String, String> selectFeeCategoryList(
			UserSessionDetails userSessionDetails) throws NoDataFoundException;

	public List<StudentGroupMaster> getStudentGroupListTag(
			SessionCache sessionCache) throws NoDataFoundException;

	Map<String, String> getPickupPoints(SessionCache sessionCache)
			throws NoDataFoundException;

	Map<String, String> selectAdminUserList() throws NoDataFoundException;
}
