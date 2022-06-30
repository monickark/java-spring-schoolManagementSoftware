package com.jaw.student.admission.dao;

import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.NoDataFoundException;

public interface IParentDetails {
	public void insertParentDetails(ParentDetails parentDetails)
			throws DuplicateEntryException;

	public ParentDetails retriveParentDetails(ParentDetailsKey studentAdmisNo) throws NoDataFoundException;

	public void updateParentDetails(final ParentDetails communicationDetails,
			final ParentDetailsKey instMasterKey);

	public ParentDetails retriveParentSess(String userID) throws NoDataFoundException;
	

}
