package com.jaw.login.dao;

import com.jaw.common.exceptions.NoDataFoundException;

public interface IDashboardDao {

	DashboardConsolidated retriveDashboardDetails(
			DashboardConsolidatedKey dashboardConsolidatedKey)
			throws NoDataFoundException;

}
