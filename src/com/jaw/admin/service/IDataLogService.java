package com.jaw.admin.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.jaw.admin.controller.DataLogDetailsVO;
import com.jaw.admin.controller.DataLogListVO;
import com.jaw.admin.controller.DataLogSearchVO;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.sessCache.UserSessionDetails;

public interface IDataLogService {

	public List<DataLogListVO> selectListOfAuditRecords(
			DataLogSearchVO dataLogSearchVO,
			UserSessionDetails userSessionDetails) throws NoDataFoundException;

	public Map<String, ArrayList<String>> getDataLogRecFromListRec(
			DataLogListVO dataLogListVO, DataLogDetailsVO dataLogVO,
			UserSessionDetails userSessionDetails,
			ApplicationCache applicationCache) throws NoDataFoundException;

	public Map<String,ArrayList<String>> getTableKey(DataLogDetailsVO dataLog,ApplicationCache applicationCache,
			UserSessionDetails userSessionDetails) throws NoDataFoundException;

}
