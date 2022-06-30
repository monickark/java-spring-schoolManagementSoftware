package com.jaw.core.dao;

import java.util.List;

import com.jaw.common.exceptions.NoDataFoundException;

public interface IStandardCombinationListDao {

	public List<StandardCombinationList> getAllStandardList(String branchId,
			String instId) throws NoDataFoundException;

}
