package com.jaw.communication.dao;

import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.UpdateFailedException;

public interface ISMSDetailsDAO {
	public void saveSMSDetailsRecord(SMSDetails smsDetails)throws DuplicateEntryException;
	public void updateSMSDetailsRecord(SMSDetails smsDetails,final SMSDetailsKey smsDetailsKey)throws UpdateFailedException;
	SMSDetails selectSMSDetailsRec(final SMSDetailsKey smsDetailsKey)	throws NoDataFoundException;
	
}
