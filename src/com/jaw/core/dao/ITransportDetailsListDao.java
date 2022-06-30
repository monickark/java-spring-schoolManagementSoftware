package com.jaw.core.dao;

import java.util.List;

import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.batch.DataIntegrityException;
import com.jaw.common.exceptions.batch.RuntimeExceptionForBatch;


public interface ITransportDetailsListDao {
	public void insertTransportDetailsList(final List<TransportDetails> transportDetailsList) throws DataIntegrityException, RuntimeExceptionForBatch;
	List<TransportDetails> retriveTransportDetailsList() throws NoDataFoundException;
}
