package com.jaw.core.dao;

import java.util.List;

import com.jaw.common.exceptions.NoDataFoundException;

public interface ICourseMasterListDAO {
	public List<CourseMaster> getCourseMasterList(CourseMasterKey courseMasterKey)	throws NoDataFoundException;
}
