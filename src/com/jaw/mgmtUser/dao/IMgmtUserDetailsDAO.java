package com.jaw.mgmtUser.dao;

import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.UpdateFailedException;

//Interface for Institute Master CRUD DAO 
public interface IMgmtUserDetailsDAO {

	void insertManagementRec(MgmtUser instMasterRecord)
			throws DuplicateEntryException;

	MgmtUser selectManagementRec(String instId, String branchId,String managementId) throws NoDataFoundException;

	void updateManagementRec(MgmtUser management)
			throws UpdateFailedException;


}
