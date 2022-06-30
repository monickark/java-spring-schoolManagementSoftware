package com.jaw.common.util.dao;

import java.util.List;

import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.core.dao.StudentGroupMaster;
import com.jaw.framework.sessCache.UserSessionDetails;

public interface IStudentGroupListUtilDAO {
	public List<StudentGroupMaster> selectStudentGroupList(final UserSessionDetails userSessionDetails) throws NoDataFoundException ;
}
