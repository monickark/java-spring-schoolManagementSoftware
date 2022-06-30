package com.jaw.staff.dao;

import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.UpdateFailedException;

public interface IStaffDetailsDAO {

	public void insertStaffDetails ( final StaffDetails staffDetails )
	        throws DuplicateEntryException;

	StaffDetails selectStaffRec ( String staffId , String instId ,
	        String branchId ) throws NoDataFoundException;

	void updateStaffDetails ( StaffDetails staffDetails )
	        throws UpdateFailedException;

}
