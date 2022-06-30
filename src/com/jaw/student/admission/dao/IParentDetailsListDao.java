package com.jaw.student.admission.dao;

import java.util.List;

import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.batch.RuntimeExceptionForBatch;

public interface IParentDetailsListDao {
	public void insertParentDetailsList(final List<ParentDetails> parentDetailsList) throws RuntimeExceptionForBatch;
	List<ParentDetails> retriveParentDetailsList() throws NoDataFoundException;
	List<ParentDetails> retriveParentDetailsListFromStuAdmisNo(ParentDetailsKey parentDetailsKey) throws NoDataFoundException;
}
