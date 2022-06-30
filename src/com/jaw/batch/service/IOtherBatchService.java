package com.jaw.batch.service;

import com.jaw.common.exceptions.BatchUpdateFailedException;
import com.jaw.common.exceptions.NoDataFoundException;

public interface IOtherBatchService {
	
	public void updatePsdeTableColumns() throws NoDataFoundException, BatchUpdateFailedException;

}
