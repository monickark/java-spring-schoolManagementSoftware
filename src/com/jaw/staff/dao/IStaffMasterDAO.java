package com.jaw.staff.dao;

import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.UpdateFailedException;

public interface IStaffMasterDAO {

	public void insertStaffMaster(final StaffMaster staffMaster)
			throws DuplicateEntryException;

	public StaffMaster selectStaffRec(String staffId, String instId,
			String branchId) throws NoDataFoundException;

	void updateStaffMaster(StaffMaster staffMaster)
			throws UpdateFailedException;

}
