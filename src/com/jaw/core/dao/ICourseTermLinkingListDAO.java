package com.jaw.core.dao;

import java.util.List;
import java.util.Map;

import com.jaw.common.exceptions.NoDataFoundException;

public interface ICourseTermLinkingListDAO {
	public List<CourseTermLinking> getCourseTermLinkingList(CourseTermLinkingKey courseTermLinkingKey)	throws NoDataFoundException;
	
	public Map<String,String> getTermListFromCourse(String instid,String branchId,String courseId);
}
