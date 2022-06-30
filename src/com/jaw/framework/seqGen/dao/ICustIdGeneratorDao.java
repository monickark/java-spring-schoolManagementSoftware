package com.jaw.framework.seqGen.dao;

import com.jaw.common.exceptions.DatabaseException;
import com.jaw.framework.sessCache.UserSessionDetails;

//Interface for BatchIdGeneratorDao & SimpleIdGeneratorDao

public interface ICustIdGeneratorDao {

		int getIdForSequence(String key, UserSessionDetails userSessionDetails)
			throws DatabaseException;

}
