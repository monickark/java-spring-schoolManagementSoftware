package com.jaw.student.admission.dao;

import java.util.List;

import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.batch.DataIntegrityException;
import com.jaw.common.exceptions.batch.RuntimeExceptionForBatch;

public interface IStudentDetainListDao {
	
	List<StudentDetain> getDetainedStuList(String acyYear,String stuGrpId) throws NoDataFoundException;
	public void insertStudentMasterList(final List<StudentDetain> studentList)
			throws DataIntegrityException, RuntimeExceptionForBatch ;
	
	public List<String> retriveListOfRecForDuplicateCheck(final String academicTerm,
			List<String> valueList,String instId,String branchId);

}
