package com.jaw.core.dao;

import java.util.List;

import com.jaw.common.exceptions.NoDataFoundException;

public interface ITransportMasterListDAO {

	List<TransportMasterList> getTransportMasterList(
			TransportMasterKey transportMasterKey) throws NoDataFoundException;
	
}
