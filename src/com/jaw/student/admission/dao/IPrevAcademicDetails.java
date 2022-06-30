package com.jaw.student.admission.dao;

import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.UpdateFailedException;

public interface IPrevAcademicDetails {
	public void insertPrevAcademicDetaisl(
			PrevAcademicDetails prevAcademicDetails)
			throws DuplicateEntryException;

	public void updatePrevAcaDetails(
			final PrevAcademicDetails prevAcademicDetails,
			final PrevAcademicDetailsKey prevAcademicDetailsKey) throws UpdateFailedException;

	public PrevAcademicDetails retrivePrevAcademicDetails(
			PrevAcademicDetailsKey prevAcademicDetailsKey) throws NoDataFoundException;
		
}
