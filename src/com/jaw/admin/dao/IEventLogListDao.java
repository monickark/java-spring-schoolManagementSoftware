package com.jaw.admin.dao;

import java.util.List;

import com.jaw.common.exceptions.NoDataFoundException;

public interface IEventLogListDao {
	public List<EventLog> getEventLogList(EventLogKey eventLogKey) throws NoDataFoundException;

}
