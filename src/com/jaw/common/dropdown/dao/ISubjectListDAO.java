package com.jaw.common.dropdown.dao;

import java.util.Map;

import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.framework.sessCache.UserSessionDetails;

public interface ISubjectListDAO {
	Map<String, String> getSubList(String stdGrpId, String staffId,
			UserSessionDetails userSessionDetails) throws NoDataFoundException;

}
