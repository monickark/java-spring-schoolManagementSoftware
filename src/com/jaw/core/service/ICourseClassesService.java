package com.jaw.core.service;

import java.util.List;
import java.util.Map;

import com.jaw.common.exceptions.BatchUpdateFailedException;
import com.jaw.common.exceptions.DatabaseException;
import com.jaw.common.exceptions.DeleteFailedException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.UpdateFailedException;
import com.jaw.common.exceptions.util.TableNotSpecifiedForAuditException;
import com.jaw.core.controller.CourseClassesMasterVO;
import com.jaw.core.controller.CourseClassesSearchVO;
import com.jaw.core.controller.CourseClassesVO;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.sessCache.UserSessionDetails;

public interface ICourseClassesService {
	
	void insertCourseClassList(UserSessionDetails userSessionDetails,
			CourseClassesSearchVO classesSearchVO, List<CourseClassesVO> courseClassesVOs,
			String[] selectedRowIds, String[] staffIds, String[] clsType, String[] labSessNo,
			String[] classNo, String[] labBatch, String[] duration, ApplicationCache applicationCache, String[] hiddenRowId)
			throws NoDataFoundException, DuplicateEntryException, BatchUpdateFailedException,
			DatabaseException;
	
	Map<String, String> selectStaffList(String staffId, UserSessionDetails userSessionDetails) throws 
			NoDataFoundException;
	
	void insertCourseClassList(UserSessionDetails userSessionDetails,
			CourseClassesSearchVO classesSearchVO, CourseClassesVO courseClassesVO)
			throws NoDataFoundException, DuplicateEntryException, BatchUpdateFailedException,
			DatabaseException, TableNotSpecifiedForAuditException;
	
	void deleteCourseClassesDAORec(CourseClassesVO courseClassesVO,
			UserSessionDetails userSessionDetails, ApplicationCache applicationCache)
			throws DeleteFailedException, NoDataFoundException, DuplicateEntryException,
			DatabaseException, TableNotSpecifiedForAuditException;
	
	void selectCourseClassesData(CourseClassesMasterVO courseClassesMasterVO,
			UserSessionDetails userSessionDetails) throws NoDataFoundException;
	
	void updateCourseClasses(CourseClassesVO courseClassesVO,
			UserSessionDetails userSessionDetails, ApplicationCache applicationCache)
			throws UpdateFailedException, NoDataFoundException, DuplicateEntryException,
			DatabaseException, TableNotSpecifiedForAuditException;
	
}
