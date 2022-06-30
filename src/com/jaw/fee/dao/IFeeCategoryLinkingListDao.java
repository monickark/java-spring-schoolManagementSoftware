package com.jaw.fee.dao;

import java.util.List;
import java.util.Map;

import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.NoDataFoundException;

public interface IFeeCategoryLinkingListDao {

	
	void insertFeeCategoryBatch(List<FeeCategoryLinking> feeCategoryLinkings)
			throws DuplicateEntryException;

	Map<String, String> getUnAllottedFeeType(String instId, String branchid,
			String feeCategory) throws NoDataFoundException;

	Map<String, String> getAllottedFeeCategory(String instId,
			String branchid, String feeCategory) throws NoDataFoundException;

	Map<String, String> getElectiveSubjects(String instId, String branchid)
			throws NoDataFoundException;

	List<FeeCategoryLinkingList> getFeeCategoryList(FeeMasterKey feeMasterKey)
			throws NoDataFoundException;

}
