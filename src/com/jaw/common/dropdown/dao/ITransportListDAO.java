package com.jaw.common.dropdown.dao;

import java.util.Map;

import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.framework.sessCache.UserSessionDetails;

public interface ITransportListDAO {

	Map<String, String> selectPickupPoint(UserSessionDetails userSessionDetails)
			throws NoDataFoundException;

}
