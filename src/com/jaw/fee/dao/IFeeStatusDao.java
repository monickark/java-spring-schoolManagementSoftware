package com.jaw.fee.dao;

import com.jaw.common.exceptions.DeleteFailedException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.UpdateFailedException;

public interface IFeeStatusDao {

	void insertFeeStatus(FeeMasterStatus FeeStatus) throws DuplicateEntryException;
	
	void updateFeeStatus(FeeMasterStatus FeeStatus, FeeMasterStatusKey FeeStatusKey)
			throws UpdateFailedException;

	FeeMasterStatus selectFeeStatus(FeeMasterStatusKey FeeStatusKey)
			throws NoDataFoundException;

	void deleteFeeStatus(FeeMasterStatusKey FeeStatusKey)
			throws DeleteFailedException;
	//String checkFeeStatus(FeeMasterStatusKey FeeStatusKey) throws NoDataFoundException;
	
	int checkFeeStatusLocked(String instId, String branchId, String acterm,
			String feeCategory, String courseId, String termId, String feeStatus);

}
