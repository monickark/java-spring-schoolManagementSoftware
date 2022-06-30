package com.jaw.core.dao;

import com.jaw.common.exceptions.DeleteFailedException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.UpdateFailedException;

public interface ICourseTermLinkingDAO {
	void insertCourseTermLinkingRec(CourseTermLinking courseTermLinking)
			throws DuplicateEntryException;		
	
	void deleteCourseTermLinkingRec(CourseTermLinking courseTermLinking, final CourseTermLinkingKey courseTermLinkingKey)
			throws DeleteFailedException;
	
	CourseTermLinking selectCourseTermLinkingRec(
			final CourseTermLinkingKey courseTermLinkingKey)
			throws NoDataFoundException;
	int checkCourseTermLinkingOrderExist(CourseTermLinking courseTermLinking);
	CourseTermLinking selectCourseTermLinkingDelFlgRec(
			final CourseTermLinkingKey courseTermLinkingKey)
			throws NoDataFoundException;
	void updateCrsTmLinkRecDelFlgYesToNo(CourseTermLinking courseTermLinking, final CourseTermLinkingKey courseTermLinkingKey)
			throws UpdateFailedException;
}
