package com.jaw.student.admission.dao;

import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.UpdateFailedException;
import com.jaw.student.admission.controller.AdmissionDetailsVO;

public interface IStudentMasterDao {
	
	public void insertStudentMaster(StudentMaster studentMaster)
			throws DuplicateEntryException;
	
	public void updateStudentMaster(final StudentMaster studentMaster,
			final StudentMasterKey studentMasterKey) throws UpdateFailedException;
	
	public StudentMaster retriveStuMasterRec(String userID) throws NoDataFoundException;
	
//	public StudentMaster retriveStudentDetails(StudentMasterKey studentMasterKey) throws NoDataFoundException;

	StudentMaster retriveStudentDetails(StudentMasterKey studentMasterKey) throws NoDataFoundException;
	
	public String getStuGrpIdForSecList(final String admisNo,final String instId,final String branchId) ;

	String getNextAdmisNo(AdmissionDetailsVO admissionDetailsVO);
	
}
