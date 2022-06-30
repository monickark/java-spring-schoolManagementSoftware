package com.jaw.mark.dao;

import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.UpdateFailedException;

public interface IStudentExamRemarksDAO {
	
	StudentExamRemark selectStudentExamRemark(StudentExamRemarkKey studentExamRemarkKey)
			throws NoDataFoundException;
	
	void insertStudentExamRemark(StudentExamRemark StudentExamRemark)
			throws DuplicateEntryException;
	
	void updateStudentExamRemark(StudentExamRemark StudentExamRemark,
			StudentExamRemarkKey studentExamRemarkKey) throws UpdateFailedException;
	
}
