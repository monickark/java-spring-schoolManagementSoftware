package com.jaw.student.admission.dao;

import java.util.List;

import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.UpdateFailedException;

public interface ISiblingDetails {
	public void insertSiblingDetails(List<SiblingDetails> siblingDetails)
			throws DuplicateEntryException;

	public void updateSibDetails(SiblingDetails sibDetails,
			SiblingDetailsKey siblingDetailsKey) throws UpdateFailedException;

	public List<SiblingDetails> retriveSiblingDetails(SiblingDetailsKey siblingDetailsKey) throws NoDataFoundException;
	
	public SiblingDetails retriveSingleSibDetails(SiblingDetailsKey siblingDetailsKey) throws NoDataFoundException;
	
}

