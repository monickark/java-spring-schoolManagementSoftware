package com.jaw.core.dao;

import java.util.List;
import java.util.Map;

import com.jaw.common.exceptions.NoDataFoundException;

public interface IStudentGroupMasterListDAO {
	public List<StudentGroupMaster> selectGroupMasterList(final StudentGroupMasterListKey stuGrpMtrListKey)
			throws NoDataFoundException;
	Map<String, String> getStGroupForClsTchrAltmnt(String acterm,
			String courseId, String instId, String branchId)
			throws NoDataFoundException;
}
