package com.jaw.admin.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.framework.dao.BaseDao;

@Repository
public class EventLogListDao extends BaseDao implements IEventLogListDao {
	Logger logger = Logger.getLogger(DataLogListDao.class);

	@Override
	public List<EventLog> getEventLogList(EventLogKey eventLogKey)
			throws NoDataFoundException {
		logger.info("Going to fetch Event Log List from Database");
		List<String> data = new ArrayList<String>();
		StringBuffer sql = new StringBuffer()
				.append("select ")
				.append("ADT_SRL_NO,")
				.append("u.R_CRE_TIME,")
				.append("u.R_CRE_ID,")
				.append("link_id, ")
				.append("ACT_CODE,")
				.append("AUDIT_REMARKS")
				.append(" from uadt u,usrl ul")
				.append(" where ")
				.append(" u.inst_id = ul.inst_id")
				.append(" and")
				.append(" u.R_CRE_ID = ul.user_id")
				.append(" and")
				.append(" u.AUDIT_FLG = ?")
				.append(" and")
				.append(" u.INST_ID = ?")
				.append(" and")
				.append(" u.BRANCH_ID = ?")
				.append(" and")
				.append(" u.R_CRE_TIME >=?")
				.append(" and")
				.append(" u.R_CRE_TIME <=?");
		
		data.add(eventLogKey.getAuditFlg().toUpperCase().trim());
		data.add(eventLogKey.getInstId().trim());
		data.add(eventLogKey.getBranchId().toUpperCase().trim());
		data.add(eventLogKey.getFromDate().concat(" 00:00:01").trim());
		data.add(eventLogKey.getToDate().concat(" 23:59:59").trim());
		
		if (!eventLogKey.getUserId().equals("")) {
			sql.append(" and u.R_CRE_ID = ?");
			data.add(eventLogKey.getUserId().toUpperCase().trim());
		}
		if (!(eventLogKey.getLinkId().equals(""))) {
			sql.append(" and link_id = ?");
			data.add(eventLogKey.getLinkId().toUpperCase().trim());
		}
		if ((!(eventLogKey.getEventType().equals("")))
				&& (!(eventLogKey.getEventType().equals("null")))) {
			sql.append(" and ACT_CODE = ?");
			data.add(eventLogKey.getEventType().toUpperCase().trim());
		}
		sql.append(" order by u.R_CRE_TIME desc");
		String[] array = data.toArray(new String[data.size()]);

		logger.debug("select query :" + sql.toString());
		List<EventLog> auditPojoList = getJdbcTemplate().query(sql.toString(),
				array, new EventLogRowMapper());
		if (auditPojoList.size() == 0) {
			throw new NoDataFoundException();
		}

		return auditPojoList;
	}

	class EventLogRowMapper implements RowMapper<EventLog> {
		@Override
		public EventLog mapRow(ResultSet rs, int arg1) throws SQLException {
			EventLog eventLog = new EventLog();
			eventLog.setAuditSrlNo(rs.getString("ADT_SRL_NO"));
			eventLog.setrCreTime(rs.getString("u.R_CRE_Time"));
			eventLog.setUserId(rs.getString("u.R_CRE_ID"));
			eventLog.setLinkId(rs.getString("link_id"));
			eventLog.setEventType(rs.getString("ACT_CODE"));
			eventLog.setRemarks(rs.getString("AUDIT_REMARKS"));
			return eventLog;
		}

	}
}
