package com.jaw.student.admission.dao;

import java.util.List;

import com.jaw.common.exceptions.BatchUpdateFailedException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.batch.RuntimeExceptionForBatch;

public interface IPrevAcademicDetailsList {
	
	public void insertPrevAcademicDetailsList(final List<PrevAcademicDetails> prevAcademicDetailsList) throws RuntimeExceptionForBatch;

	List<PrevAcademicDetails> retrivePrevAcademicDetailsList() throws NoDataFoundException;
	
	public List<PrevAcademicDetails> getListForColumnUpdate() throws NoDataFoundException;
	
	public void batchUpdateForColumns(List<PrevAcademicDetails> prevList) throws BatchUpdateFailedException;

}
