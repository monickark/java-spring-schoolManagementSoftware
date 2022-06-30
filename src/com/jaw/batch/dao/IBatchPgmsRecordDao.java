package com.jaw.batch.dao;

import com.jaw.common.exceptions.batch.NoRecordFoundException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.UpdateFailedException;

//Interface for BatchList dao
public interface IBatchPgmsRecordDao {
	
	public BatchPgms retrieveBatchPgmsRec(BatchPgmsKey batchPgmsKey) throws NoRecordFoundException;
	public void insertBatchPgmRec(final BatchPgms batchPgms) throws DuplicateEntryException;
	public void updateBatchPgmRec(final BatchPgmsKey batchPgmsKey, final BatchPgms batchPgms) throws UpdateFailedException;


}
