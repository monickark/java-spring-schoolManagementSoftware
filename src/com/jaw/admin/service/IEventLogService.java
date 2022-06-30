package com.jaw.admin.service;

import java.util.List;

import com.jaw.admin.controller.EventLogListVO;
import com.jaw.admin.controller.EventLogSearchVO;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.framework.sessCache.UserSessionDetails;

public interface IEventLogService {
	public List<EventLogListVO> selectListOfAuditEventLogRecords(
			EventLogSearchVO eventLogSearchVO,
			UserSessionDetails userSessionDetails) throws NoDataFoundException;
}
