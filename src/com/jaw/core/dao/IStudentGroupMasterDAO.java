package com.jaw.core.dao;

import com.jaw.common.exceptions.DeleteFailedException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.UpdateFailedException;

public interface IStudentGroupMasterDAO {
	void insertStudentGrpMasterRec(StudentGroupMaster studentGrpMaster)
			throws DuplicateEntryException;

	void updateStudentGrpMasterRec(StudentGroupMaster studentGrpMaster,final StudentGroupMasterKey studentGrpMasterKey)
			throws UpdateFailedException;

	void deleteStudentGrpMasterRec(StudentGroupMaster studentGrpMaster,final StudentGroupMasterKey studentGrpMasterKey) throws DeleteFailedException;

	StudentGroupMaster selectStudentGrpMasterRec(
			final StudentGroupMasterKey studentGrpMasterKey)
			throws NoDataFoundException;
	
	int validateStudentGrpMasterRec(StudentGroupMaster studentGrpMaster) ;
	
	public String getStuGrpIdForSTUM(String instId,String branchid,String courseId,String termId,String secId);
}
