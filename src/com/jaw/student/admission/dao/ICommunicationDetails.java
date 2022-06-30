package com.jaw.student.admission.dao;

import java.util.List;

import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.UpdateFailedException;

public interface ICommunicationDetails {
	public void insertCommunication(CommunicationDetails communicationDetails) throws DuplicateEntryException;

	public void updateCommDetails(
			final CommunicationDetails communicationDetails,
			final CommunicationDetailsKey instMasterKey) throws UpdateFailedException;

	public CommunicationDetails retriveCommunicationDetails(
			CommunicationDetailsKey communicationDetailsKey) throws NoDataFoundException;
	
	
}
