package com.jaw.core.dao;

import java.util.Map;

import com.jaw.common.exceptions.NoDataFoundException;

public interface ITermListBasedOnCourseIdDAO {
	
	Map<String, String> getTermListBasedOnCourseId(String instId, String branchId,
			String courseId) throws NoDataFoundException;
	
}
