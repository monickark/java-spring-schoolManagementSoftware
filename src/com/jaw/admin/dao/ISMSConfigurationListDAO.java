package com.jaw.admin.dao;

import java.util.List;

import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.UpdateFailedException;
import com.jaw.core.dao.HolidayMaintenance;
import com.jaw.core.dao.HolidayMaintenanceKey;

public interface ISMSConfigurationListDAO {
	public List<SMSConfiguration> getSMSConfigurationList(final SMSConfigurationListKey smsConfigurationListKey)
			throws NoDataFoundException ;
	 void updateSMSConfigurationDetailsRecs(
				final List<SMSConfiguration> smsConfigurationList,final List<SMSConfigurationListKey> smsConfigurationKeyList) throws  UpdateFailedException;
}
