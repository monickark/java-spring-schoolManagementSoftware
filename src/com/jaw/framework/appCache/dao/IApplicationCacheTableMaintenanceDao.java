package com.jaw.framework.appCache.dao;

import java.util.List;

import com.jaw.common.exceptions.NoDataFoundException;

public interface IApplicationCacheTableMaintenanceDao {

	List<TableMaintenance> getTableMaintenanceData()
			throws NoDataFoundException;

}
