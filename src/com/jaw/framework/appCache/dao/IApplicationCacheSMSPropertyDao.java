package com.jaw.framework.appCache.dao;

import java.util.List;

import com.jaw.common.exceptions.NoDataFoundException;

public interface IApplicationCacheSMSPropertyDao {
	public List<SMSProperty> getPrpmCodes() throws NoDataFoundException;
}
