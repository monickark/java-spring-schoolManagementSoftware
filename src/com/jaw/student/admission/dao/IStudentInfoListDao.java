package com.jaw.student.admission.dao;

import java.util.List;

import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.batch.RuntimeExceptionForBatch;

public interface IStudentInfoListDao {

	public void insertStudentInfoList(final List<StudentInfo> studentInfoList) throws RuntimeExceptionForBatch;

	List<StudentInfo> retriveStudentInfoList() throws NoDataFoundException;
	
}
