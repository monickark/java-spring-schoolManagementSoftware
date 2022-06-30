package com.jaw.admin.service;

import java.util.List;

import com.jaw.admin.controller.RequestListVo;
import com.jaw.common.exceptions.BatchUpdateFailedException;
import com.jaw.common.exceptions.DatabaseException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.sessCache.SessionCache;
import com.jaw.framework.sessCache.UserSessionDetails;

public interface IRequestListService {

	public void processRequest(RequestListVo requestVo,
			UserSessionDetails userSessionDetails,
			List<RequestListVo> passwordRequestList, String[] rowId)
			throws NoDataFoundException, DuplicateEntryException,
			BatchUpdateFailedException, DatabaseException;

	List<RequestListVo> selectAllRequestList(ApplicationCache applicationCache,
			SessionCache sessionCache, RequestListVo requestVo,
			UserSessionDetails userSessionDetails, String url)
			throws NoDataFoundException;

	List<RequestListVo> selectPendingRequestRecords(RequestListVo requestVo,
			UserSessionDetails userSessionDetails,
			ApplicationCache applicationCache, String url)
			throws NoDataFoundException;
}
