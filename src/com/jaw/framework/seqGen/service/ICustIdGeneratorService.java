package com.jaw.framework.seqGen.service;

import com.jaw.common.exceptions.DatabaseException;
import com.jaw.framework.sessCache.UserSessionDetails;

//Interface for BatchIdGeneratorService & SimpleIdGeneratorService
public interface ICustIdGeneratorService {

	Integer getNextId(String key, int granularity,
			UserSessionDetails userSessionDetails) throws DatabaseException;

}
