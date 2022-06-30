package com.jaw.admin.dao;

import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.UpdateFailedException;

public interface IBranchMasterDAO {
	
	
	void insertBranchMasterDetails(BranchMaster code) throws DuplicateEntryException;

	void updateBranchMaster(BranchMaster code, BranchMasterKey codeKey) throws UpdateFailedException;

	BranchMaster selectBranchMaster(BranchMasterKey code) throws NoDataFoundException;

}
