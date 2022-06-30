package com.jaw.core.dao;

import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.UpdateFailedException;

public interface ITransportDetails {

	public void insertTransportsDetails(TransportDetails transportDetaisl)
			throws DuplicateEntryException;

	public void updateTransportDetails(
			final TransportDetails communicationDetails,
			final TransportDetailsKey instMasterKey) throws UpdateFailedException;

	public TransportDetails retriveTransportDetails(TransportDetailsKey transportDetailsKey) throws NoDataFoundException;
		


	

}
