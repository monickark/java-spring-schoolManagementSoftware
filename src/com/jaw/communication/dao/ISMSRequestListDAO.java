package com.jaw.communication.dao;

import java.util.List;

import com.jaw.common.exceptions.NoDataFoundException;

public interface ISMSRequestListDAO {
	public List<SMSRequest> getSMSRequestList(final SMSRequestListKey smsRequestListKey)
			throws NoDataFoundException;
}
