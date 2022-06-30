package com.jaw.admin.dao;

import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.UpdateFailedException;

public interface ISMSConfigurationDAO {
	void updateSMSConfigurationRec(SMSConfiguration smsConfigRecord,final SMSConfigurationKey smsConfigKey)
			throws UpdateFailedException;
	
	SMSConfiguration selectSMSConfigurationRec(
			final SMSConfigurationKey smsConfigKey)
			throws NoDataFoundException;
}
