package com.jaw.common.util.dao;

import java.util.Date;

import com.jaw.common.exceptions.NoDataFoundException;

public interface IAttendanceUtilDao {
	
	Date[] getACTermPeriod(String instid, String branchId, String acTerm)
			throws NoDataFoundException;
	
}
