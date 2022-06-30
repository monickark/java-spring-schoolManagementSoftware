package com.jaw.common.dropdown.dao;

import java.util.Map;

import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.framework.sessCache.UserSessionDetails;

public interface IFeeCategoryListDao {

	Map<String, String> selectFeeCategoryList(
			UserSessionDetails userSessionDetails) throws NoDataFoundException;

}
