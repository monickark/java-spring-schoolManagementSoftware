package com.jaw.fee.dao;

import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.UpdateFailedException;

public interface IFeeMasterDao {

	void insertFeeMaster(FeeMaster FeeMaster) throws DuplicateEntryException;

	void updateFeeMaster(FeeMaster FeeMaster, FeeMasterKey FeeMasterKey)
			throws UpdateFailedException;

	FeeMaster selectFeeMaster(FeeMasterKey FeeMasterKey)
			throws NoDataFoundException;

	void deletFeeMaster(FeeMaster feeMaster) throws UpdateFailedException;

}
