package com.jaw.login.service;

import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.framework.sessCache.UserSessionDetails;
import com.jaw.login.controller.DashboardConsolidatedVO;

public interface IDashboardService {

	DashboardConsolidatedVO selectDashboardRecord(String fromDate,
			String toDate, UserSessionDetails userSessionDetails) throws NoDataFoundException;


}
