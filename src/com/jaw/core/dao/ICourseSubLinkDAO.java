package com.jaw.core.dao;

import com.jaw.common.exceptions.DeleteFailedException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.UpdateFailedException;

public interface ICourseSubLinkDAO {
	void insertCourseSubLinkRec(CourseSubLink courseSubLink)
			throws DuplicateEntryException;
	
	void updateCourseSubLinkRec(CourseSubLink courseSubLink, final CourseSubLinkKey courseSubLinkkey)
			throws UpdateFailedException;
	
	void deleteCourseSubLinkRec(String userId, CourseSubLinkKey courseSubLinkkey)
			throws DeleteFailedException;
	
	CourseSubLink selectCourseSubLinkRec(CourseSubLinkKey courseSubLinkKey)
			throws NoDataFoundException;
	int checkRecordExist(String instId, String branchId, String courseId,
			String termId, String subId, String subType);

	int checkRecordOrderExist(String instId, String branchId, String courseId,
			String termId, int subOrder);
}
