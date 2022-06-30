package com.jaw.admin.dao;

import java.util.List;

import com.jaw.common.exceptions.BatchUpdateFailedException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.framework.sessCache.UserSessionDetails;

public interface IRequestListDao {

	public List<String> selectMenuProfile(String instid, String branchId)
			throws NoDataFoundException;

	int[] updateBatch(List<RequestList> batchup)
			throws DuplicateEntryException, BatchUpdateFailedException;

	List<Request> getRequestsList(String instId, String branchId,
			String requestType, String requestStatus, String menuOptionId)
			throws NoDataFoundException;

	List<RequestList> selectPendingRequestRecords(String requestType,
			String requestStatus, String branchId, String instId,
			String profileGroup, String columnName, String tableName,
			String linkId, String menuOptionId, String menuProfile)
			throws NoDataFoundException;

	List<RequestList> getRequestsListOfTransferredUser(String instId,
			String branchId, String linkId) throws NoDataFoundException;

	int[] updateTransferredUserRequest(List<RequestList> batchup,
			UserSessionDetails userSessionDetails)
			throws DuplicateEntryException, BatchUpdateFailedException;

}
