package com.jaw.framework.appCache.dao;

import java.util.List;

import com.jaw.common.exceptions.NoDataFoundException;

public interface IApplicationCacheProfileOptionLinking {

	List<MenuProfileOptionLinking> getMenuProfileOption()
			throws NoDataFoundException;

	List<MenuProfileOptionLinking> getMenuProfile() throws NoDataFoundException;
}
