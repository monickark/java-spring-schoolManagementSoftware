package com.jaw.student.admission.dao;

import java.util.List;

import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.batch.RuntimeExceptionForBatch;

public interface ICommunicationDetailsListDao {
	public void insertCommunicationDetailsList(final List<CommunicationDetails> communicationDetailsList) throws RuntimeExceptionForBatch;
	List<CommunicationDetails> retriveCommunicationDetailsList() throws NoDataFoundException;
}
