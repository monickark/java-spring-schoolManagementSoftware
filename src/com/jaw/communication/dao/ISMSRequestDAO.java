package com.jaw.communication.dao;

import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.UpdateFailedException;
import com.jaw.communication.controller.SMSRequestVO;

public interface ISMSRequestDAO {
public void saveSMSRequestRecord(SMSRequest smsRequest)throws DuplicateEntryException;
public void updateSMSRequestStatus(SMSRequest smsRequest,final SMSRequestKey smsRequestKey)throws UpdateFailedException;
SMSRequest selectSMSRequestRec(final SMSRequestKey smsRequestKey)	throws NoDataFoundException;
}
