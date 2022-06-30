package com.jaw.core.dao;

import com.jaw.common.exceptions.DeleteFailedException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.UpdateFailedException;

public interface ITransportMasterDAO {

	void insertTransportMasterRec(TransportMaster TransportMaster)
			throws DuplicateEntryException;

	void updateTransportMasterRec(TransportMaster transportMaster,
			TransportMasterKey transportMasterkey) throws UpdateFailedException;

	void deleteTransportMasterRec(TransportMaster TransportMaster,
			TransportMasterKey TransportMasterkey) throws DeleteFailedException;

	TransportMaster selectTransportMasterRec(
			TransportMasterKey TransportMasterKey) throws NoDataFoundException;
	
}
