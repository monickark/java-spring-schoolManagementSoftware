package com.jaw.batch.dao;

import java.util.List;

import com.jaw.batch.controller.BatchStatusSearchVO;
import com.jaw.common.exceptions.NoDataFoundException;

//Interface for BatchStatusList dao
public interface IBatchStatusListDao {
	
	public List<BatchStatus> retrieveBatchStatus(BatchStatusSearchVO batchStatus) throws NoDataFoundException;
	
}
