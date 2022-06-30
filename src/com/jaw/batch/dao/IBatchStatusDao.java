package com.jaw.batch.dao;


import com.jaw.common.exceptions.DatabaseException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.batch.NoRecordFoundException;

//Interface for BatchStatus Dao class
public interface IBatchStatusDao {
	
	public void insertBatchStatus(BatchStatus batchStatus) throws DatabaseException;		
	public void updateBatchStatus(BatchStatus batchStatus,
			BatchStatusKey batchStatusKey);		
	public BatchStatus retrieveBatchStatusRec(String batchId) throws NoRecordFoundException;
	

}
