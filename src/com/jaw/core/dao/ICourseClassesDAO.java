package com.jaw.core.dao;

import com.jaw.common.exceptions.DeleteFailedException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.UpdateFailedException;

public interface ICourseClassesDAO {
	void insertCourseClassesRec(CourseClasses courseClasses)
			throws DuplicateEntryException;
	
	void updateCourseClassesRec(CourseClasses courseClasses, final CourseClassesKey courseClasseskey)
			throws UpdateFailedException;
	
	void deleteCourseClassesRec(CourseClasses courseClasses, final CourseClassesKey courseClasseskey)
			throws DeleteFailedException;
	
	CourseClasses selectCourseClassesRec(
			final CourseClassesKey courseClassesKey)
			throws NoDataFoundException;

	int checkRecordExist(String instId, String branchId, String acTerm,
			String stGroup, String batch, String subId, String staffId);
}
