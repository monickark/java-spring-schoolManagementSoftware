package com.jaw.framework.appCache.dao;

import java.util.HashMap;

import com.jaw.common.exceptions.NoDataFoundException;

public interface IApplicationCacheErrorCodeDao {
	public HashMap<String, String> getAllErrorCode()
			throws NoDataFoundException;

}
