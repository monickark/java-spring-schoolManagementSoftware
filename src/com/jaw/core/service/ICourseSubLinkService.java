package com.jaw.core.service;

import java.util.Map;

import com.jaw.common.exceptions.DatabaseException;
import com.jaw.common.exceptions.DeleteFailedException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.SubjectOrderAlreadyExistExecption;
import com.jaw.common.exceptions.UpdateFailedException;
import com.jaw.common.exceptions.util.CommonCodeNotFoundException;
import com.jaw.common.exceptions.util.TableNotSpecifiedForAuditException;
import com.jaw.core.controller.CourseSubLinkListVO;
import com.jaw.core.controller.CourseSubLinkMasterVO;
import com.jaw.core.controller.CourseSubLinkVO;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.sessCache.UserSessionDetails;

public interface ICourseSubLinkService {
	
	void insertCouseSubjectLinkRec(CourseSubLinkVO courseSubLinkVO,
			UserSessionDetails userSessionDetails) throws DuplicateEntryException,
			DatabaseException, SubjectOrderAlreadyExistExecption;
	
	Map<String, String> getTermDetailsBasedOnCourseId(String courseId,
			UserSessionDetails userSessionDetails) throws NoDataFoundException;
	
	Map<String, String> getSubjectList(ApplicationCache applicationCache,
			UserSessionDetails userSessionDetails, String subjectType, String courseId)
			throws  NoDataFoundException;
	
	void selectCourseSubjectLinkingData(CourseSubLinkMasterVO courseSubLinkMasterVO,
			UserSessionDetails userSessionDetails) throws NoDataFoundException;
	
	
	void updateCourseSubjectLinking(CourseSubLinkVO courseSubLinkVO,
			UserSessionDetails userSessionDetails, ApplicationCache applicationCache)
			throws UpdateFailedException, NoDataFoundException, DuplicateEntryException,
			DatabaseException, TableNotSpecifiedForAuditException;

	void deleteCourseSubLinkDAORec(CourseSubLinkListVO courseSubLinkVO,
			UserSessionDetails userSessionDetails,
			ApplicationCache applicationCache) throws NoDataFoundException,
			DeleteFailedException, DuplicateEntryException, DatabaseException,
			TableNotSpecifiedForAuditException;

	

	void copyListToVO(CourseSubLinkMasterVO courseSubLinkMasterVO);
	
}
