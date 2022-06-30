package com.jaw.communication.dao;

import com.jaw.common.exceptions.DeleteFailedException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.UpdateFailedException;

public interface ISMSAlertDAO {
	void insertSMSAlertRec(SMSAlert smsAlertRecord)
			throws DuplicateEntryException;

	void updateSMSAlertRec(SMSAlert smsAlertRecord,final SMSAlertKey smsAlertKey)
			throws UpdateFailedException;
	SMSAlert selectSMSAlertRec(final SMSAlertKey alertKey)throws NoDataFoundException;
}
