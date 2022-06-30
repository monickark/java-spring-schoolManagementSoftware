package com.jaw.student.service;

import java.util.List;
import java.util.Map;

import com.jaw.common.exceptions.BatchUpdateFailedException;
import com.jaw.common.exceptions.DatabaseException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.InvalidCategoryForUpdateException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.core.controller.CourseSubLinkMasterVO;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.sessCache.UserSessionDetails;
import com.jaw.student.admission.controller.StudentMasterVO;
import com.jaw.student.controller.StudentUpdateListVO;
import com.jaw.student.controller.StudentUpdateMasterVO;

public interface IStudentUpdatesService {
	
	public  void getStuListForUpdates(UserSessionDetails sessionDetails,StudentUpdateMasterVO studentUpdateMasterVO) throws NoDataFoundException, InvalidCategoryForUpdateException;
	public void updatedStuList(UserSessionDetails sessionDetails,StudentUpdateMasterVO studentUpdateMasterVO) throws BatchUpdateFailedException, DuplicateEntryException, DatabaseException;
	public Map<String, String> getSubList(
			String StuGrpId,UserSessionDetails userSessionDetails,String subType) throws NoDataFoundException; 
	

}
