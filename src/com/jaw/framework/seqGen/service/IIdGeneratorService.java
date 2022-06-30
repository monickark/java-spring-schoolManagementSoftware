package com.jaw.framework.seqGen.service;

import com.jaw.common.exceptions.DatabaseException;

//Interface for BatchIdGeneratorService & SimpleIdGeneratorService
public interface IIdGeneratorService {

	public Integer getNextId(String key, int granularity)
			throws DatabaseException;

}
