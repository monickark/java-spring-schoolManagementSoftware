package com.jaw.communication.dao;

import java.util.List;

import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.batch.DuplicateEntryForHolGenException;
import com.jaw.core.dao.HolidayMaintenance;

public interface ISMSAlertListDAO {
	public void insertSMSAlertListRecs(
			final List<SMSAlert> smsAlertList)
			throws DuplicateEntryForHolGenException;
}
