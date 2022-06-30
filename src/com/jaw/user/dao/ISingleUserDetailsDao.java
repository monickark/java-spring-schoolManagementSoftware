package com.jaw.user.dao;

import java.util.List;

import com.jaw.common.exceptions.NoDataFoundException;

public interface ISingleUserDetailsDao {
	public SingleStudentDetails selectSingleStudentDetails(
			final String branchId, final String instId, final String userId)
			throws NoDataFoundException;
	
	List<SingleParentDetails> selectSingleParentDetails(String branchId, String instId,
			String currentYear, String parentId) throws NoDataFoundException;
	
}
