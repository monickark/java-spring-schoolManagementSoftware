package com.jaw.core.dao;

import com.jaw.common.exceptions.DeleteFailedException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.UpdateFailedException;

public interface ICourseMasterDAO {
	void insertCourseMasterRec(CourseMaster courseMaster)
			throws DuplicateEntryException;

	void updateCourseMasterRec(CourseMaster courseMaster,final CourseMasterKey courseMasterkey)
			throws UpdateFailedException;

	void deleteCourseMasterRec(CourseMaster courseMaster,final CourseMasterKey courseMasterkey) throws DeleteFailedException;

	CourseMaster selectCourseMasterRec(
			final CourseMasterKey courseMasterKey)
			throws NoDataFoundException;
	public int checkCourseMasterExist(CourseMaster courseMaster);
	
	public boolean checkForCVForStuUpdate(String stuGrpId,String instId,String branchId);
	public boolean checkForCVFromCourseId(String courseId,String instId,String branchId);
}
