package com.jaw.student.admission.dao;

import java.util.List;

import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.batch.RuntimeExceptionForBatch;

public interface ISiblingDetailsListDao {
	
	public void insertSiblingDetailsList(final List<SiblingDetails> siblingDetailsList) throws RuntimeExceptionForBatch;

	List<SiblingDetails> retriveSiblingDetailsList() throws NoDataFoundException;
}
