package com.jaw.framework.appCache.dao;

import java.util.List;

import com.jaw.common.exceptions.NoDataFoundException;

public interface IApplicationCachePropertyMaintenanceDao {
	public List<PropertyMaintenance> getPrpmCodes() throws NoDataFoundException;
}
