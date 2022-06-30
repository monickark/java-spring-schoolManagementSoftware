package com.jaw.fee.dao;

import java.util.List;

import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.NoDataFoundException;

public interface IFeeMasterListDao {

	void insertFeeMasterBatch(List<FeeMaster> FeeMasters)
			throws DuplicateEntryException;

	List<FeeMasterList> getFeeMasterList(FeeMasterKey feeMasterKey)
			throws NoDataFoundException;

	List<FeeCategoryLinkingList> getUnAllottedFeetypeList(FeeMasterKey feeMasterKey)
			throws NoDataFoundException;

}
