package com.jaw.common.dropdown.dao;

import java.util.Map;

import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.framework.sessCache.UserSessionDetails;

public interface IStudentListDAO {

	Map<String, String> selectStudAdmisList(
			UserSessionDetails userSessionDetails, String stGroup)
			throws NoDataFoundException;

}
