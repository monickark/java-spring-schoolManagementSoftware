package com.jaw.common.dropdown.dao;

import java.util.List;

import com.jaw.admin.dao.BranchMaster;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.core.dao.CourseMaster;
import com.jaw.framework.sessCache.UserSessionDetails;

public interface ICourseNameListDAO {
	List<CourseMaster> selectCourseNameList(final UserSessionDetails userSessionDetails) throws NoDataFoundException;
}
