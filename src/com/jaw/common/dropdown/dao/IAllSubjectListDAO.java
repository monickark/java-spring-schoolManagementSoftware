package com.jaw.common.dropdown.dao;

import java.util.List;

import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.core.dao.CourseSubLink;
import com.jaw.framework.sessCache.UserSessionDetails;

public interface IAllSubjectListDAO {
	public List<CourseSubLink> getAllSubjectList(final UserSessionDetails userSessionDetails)
			throws NoDataFoundException ;
}
