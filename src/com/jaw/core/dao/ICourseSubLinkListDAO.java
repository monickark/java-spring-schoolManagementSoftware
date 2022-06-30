package com.jaw.core.dao;

import java.util.List;

import com.jaw.common.exceptions.NoDataFoundException;

public interface ICourseSubLinkListDAO {
	
	List<CourseSubLinkList> getCourseSubjectLinkList(CourseSubLinkKey courseSubLinkKey)
			throws NoDataFoundException;	
	
}
