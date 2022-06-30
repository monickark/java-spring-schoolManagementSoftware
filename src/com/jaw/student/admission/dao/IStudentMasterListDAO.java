package com.jaw.student.admission.dao;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import com.jaw.common.exceptions.BatchUpdateFailedException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.SectionNotAllocatedException;
import com.jaw.common.exceptions.batch.DataIntegrityException;
import com.jaw.common.exceptions.batch.RuntimeExceptionForBatch;
import com.jaw.student.controller.BatchSectUpdate;
import com.jaw.student.controller.StudentRollSearchVO;
import com.jaw.student.controller.StudentSearchVO;

public interface IStudentMasterListDAO {

	public int[] insertBatch(final List<BatchSectUpdate> batchup,final String INSTID,final String BRANCHID,final String ACYEAR)
			throws DuplicateEntryException;

	public List<StudentMaster> retrieveStudentMasterList(StudentSearchVO studentSearchVO) throws NoDataFoundException;

	public List<StudentMaster> retrieveStudentMasterListForRollNoList(
			StudentRollSearchVO studentRollSearchVO) throws  SectionNotAllocatedException;

	/* public void secAllocationBatch(String[] studentList); */

	public void updateRollNo(List<StudentMaster> studentMasterList) throws BatchUpdateFailedException;

	public void insertStudentMasterList(final List<StudentMaster> studentList)
			throws DataIntegrityException, RuntimeExceptionForBatch;

	List<StudentMaster> retriveStudentMasterList() throws NoDataFoundException;
	
	public List<Map<String,String>> getStuListForColumnUpdates(final String instid,final String branchId,final String acYear,final String stuGrp,final String colName) throws NoDataFoundException;
	
	public void updateStuList(List<StudentUpdateList> studentUpdateLists,String colName) throws BatchUpdateFailedException;
	
	public List<StudentUpdateList> selectBefUpdateForStuUpdates(List<StudentUpdateList> studentUpdateList,String colName) throws NoDataFoundException;
	
		public Map<String, String> getStuListForDetain(final String stuGrpId,
			final String acyYear,final String instId,final String branchId) throws NoDataFoundException;

public List<StudentMaster> selectStudentGroupList(StudentMasterKey studentMasterKey)
			throws NoDataFoundException;
}
