package com.jaw.common.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.util.dao.IAttendanceUtilDao;

@Component
public class AttendanceUtil {
	
	static Logger logger = Logger.getLogger(AttendanceUtil.class);
	
	@Autowired
	IAttendanceUtilDao attendanceUtilDao;
	
	public Map<String, String> datesBetween(String instid, String branchId, String acTerm)
			throws NoDataFoundException {
		
		Date[] dates = attendanceUtilDao.getACTermPeriod(instid, branchId, acTerm);
		Map<String, String> ret = new LinkedHashMap<String, String>();
		Calendar c = Calendar.getInstance();
		c.setTime(dates[0]);
		while (c.getTimeInMillis() < dates[1].getTime()) {
			c.add(Calendar.MONTH, 1);
			String key = new SimpleDateFormat("MMM").format(c.getTime());
			String value = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
			System.out.println("key :" + key + " value :" + value);
			ret.put(key, value);
		}
		return ret;
	}
}
