package com.jaw.framework.appCache.dao;

import java.util.List;

import com.jaw.common.exceptions.NoDataFoundException;

public interface IApplicationCacheCommonCodeDao {
	public List<CommonCode> getAllCommonCodeList() throws NoDataFoundException;

}
