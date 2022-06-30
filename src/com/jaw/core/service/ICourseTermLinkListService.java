package com.jaw.core.service;

import java.util.List;
import java.util.Map;import com.jaw.common.exceptions.DatabaseException;
import com.jaw.common.exceptions.DeleteFailedException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.UpdateFailedException;
import com.jaw.common.exceptions.util.TableNotSpecifiedForAuditException;
import com.jaw.core.controller.CourseTermLinkingMasterVO;
import com.jaw.core.controller.CourseTermLinkingVO;
import com.jaw.core.controller.StudentGroupVO;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.sessCache.UserSessionDetails;

public interface ICourseTermLinkListService {
	void insertCourseTermLinkRec(CourseTermLinkingMasterVO courseTermLinkingMasterVO,
			UserSessionDetails userSessionDetails)
			throws DuplicateEntryException, DatabaseException, UpdateFailedException;
	public CourseTermLinkingMasterVO selectCourseTermLinkList(
			CourseTermLinkingMasterVO courseTermLinkingMasterVO,UserSessionDetails userSessionDetails)
			throws NoDataFoundException;
	void deleteCourseTermLinkingRec(CourseTermLinkingVO courseTermLinkingVO,
			UserSessionDetails userSessionDetails,ApplicationCache applicationCache)
			throws DeleteFailedException, NoDataFoundException, DuplicateEntryException, DatabaseException, TableNotSpecifiedForAuditException;
public Map<String,String> getTrmIdForSectionAlloc(String instid,String branchId,String courseId);
}
