package com.jaw.communication.dao;

import java.util.List;

import com.jaw.common.exceptions.NoDataFoundException;

public interface ISMSDetailsListDAO {
	public List<SMSDetails> getSMSDetailsList(final SMSDetailsKey smsDetailsKey)
			throws NoDataFoundException;
}
