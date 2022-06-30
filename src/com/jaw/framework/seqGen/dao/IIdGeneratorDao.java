package com.jaw.framework.seqGen.dao;

import com.jaw.common.exceptions.DatabaseException;

//Interface for BatchIdGeneratorDao & SimpleIdGeneratorDao

public interface IIdGeneratorDao {

	public int getIdForSequence(String key) throws DatabaseException;

}
