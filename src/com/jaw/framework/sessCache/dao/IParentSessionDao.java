package com.jaw.framework.sessCache.dao;

import java.util.List;

import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.login.SessionCacheNotLoadedException;
import com.jaw.framework.sessCache.StudentSession;
import com.jaw.student.admission.dao.ParentDetails;

public interface IParentSessionDao {
	
	ParentDetails retriveParentDetails(String studentAdmisNo, String instId, String branchId)
			throws SessionCacheNotLoadedException;
		
	
	public List<StudentSession> retriveStuParent(final String userId, final String instId,
			final String branchId,final String academicYear) throws NoDataFoundException;
	

	
}
