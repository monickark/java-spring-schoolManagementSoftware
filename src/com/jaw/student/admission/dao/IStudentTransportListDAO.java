package com.jaw.student.admission.dao;

import java.util.List;

import com.jaw.common.exceptions.batch.DataIntegrityException;
import com.jaw.common.exceptions.batch.RuntimeExceptionForBatch;

public interface IStudentTransportListDAO {


	void insertStudentTransportList(List<StudentTransportList> studentList)
			throws DataIntegrityException, RuntimeExceptionForBatch;

}
