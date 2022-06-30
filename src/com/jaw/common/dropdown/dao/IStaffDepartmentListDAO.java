package com.jaw.common.dropdown.dao;

import java.util.HashMap;
import java.util.Map;

import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.framework.sessCache.UserSessionDetails;

public interface IStaffDepartmentListDAO {
	Map<String, String> getStaffDepartmentList(
			UserSessionDetails userSessionDetails) throws NoDataFoundException;
}
