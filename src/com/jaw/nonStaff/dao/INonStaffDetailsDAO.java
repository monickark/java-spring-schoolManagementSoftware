package com.jaw.nonStaff.dao;

import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.UpdateFailedException;

public interface INonStaffDetailsDAO {
	
	void insertNonStaffDetails(NonStaff nonStaff)
			throws DuplicateEntryException;
	
	NonStaff selectNonStaffRec(String staffId, String string, String string2)
			throws NoDataFoundException;
	
	void updateNonStaffRec(NonStaff nonStaff) throws UpdateFailedException;
	
}
