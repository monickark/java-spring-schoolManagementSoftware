package com.jaw.admin.dao;

import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.NoDataFoundException;

public interface IRequestDao {
	
	public void insertRequest(Request request) throws DuplicateEntryException;
	
	public Request selectRequest(String instid, String branchId, String srlNum)
			throws NoDataFoundException;
	
	Request selectRequestRecordBasedonTypeAndStatus(String instid, String branchId, String userId,
			String requestType, String requestStatus)
			throws NoDataFoundException;
	
}
