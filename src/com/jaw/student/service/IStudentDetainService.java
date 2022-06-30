package com.jaw.student.service;

import java.util.List;
import java.util.Map;

import com.jaw.common.exceptions.DatabaseException;
import com.jaw.common.exceptions.DeleteFailedException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.DuplicatesInList;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.UpdateFailedException;
import com.jaw.common.exceptions.batch.DataIntegrityException;
import com.jaw.common.exceptions.batch.RuntimeExceptionForBatch;
import com.jaw.common.exceptions.util.TableNotSpecifiedForAuditException;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.sessCache.UserSessionDetails;
import com.jaw.student.controller.StudentDetainListVO;
import com.jaw.student.controller.StudentDetainSearchVO;
import com.jaw.student.controller.StudentDetainVO;

public interface IStudentDetainService {
	
	
	public Map<String,String> getStuList(String stuGrpId, String acterm,UserSessionDetails userSessionDetails) throws NoDataFoundException;
	
	public List<StudentDetainListVO> getDetainedStudents(String acyYear,String stuGrpId) throws NoDataFoundException;
	
	public void updateStudentDetainRec(ApplicationCache applicationcache,
			UserSessionDetails userSessionDetails, 
			StudentDetainListVO studentDetainListVO) throws NoDataFoundException, UpdateFailedException, DuplicateEntryException, DatabaseException, TableNotSpecifiedForAuditException;
	
	public void deleteStudentDetainRec(ApplicationCache applicationCache,UserSessionDetails userSessionDetails,
			String admisNo, String acYear) throws NoDataFoundException, UpdateFailedException, DeleteFailedException, DuplicateEntryException, DatabaseException, TableNotSpecifiedForAuditException;
	
	public void insertDetainedStudents(UserSessionDetails userSessionDetails , List<StudentDetainListVO> listOfStuDetain) throws NoDataFoundException, DataIntegrityException, RuntimeExceptionForBatch, DuplicatesInList, DuplicateEntryException, DatabaseException;

}
