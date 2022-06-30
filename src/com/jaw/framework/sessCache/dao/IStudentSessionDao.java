package com.jaw.framework.sessCache.dao;

import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.login.SessionCacheNotLoadedException;
import com.jaw.core.dao.StudentGroupMaster;
import com.jaw.core.dao.StudentGroupMasterKey;
import com.jaw.framework.sessCache.StudentSession;
import com.jaw.student.admission.dao.StudentMasterKey;

public interface IStudentSessionDao {
	
	public StudentSession selectStudentGrpId(StudentMasterKey studentMasterKey)
			throws NoDataFoundException;

}
