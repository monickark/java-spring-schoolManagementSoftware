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
public class DataLogListDao extends BaseDao implements IDataLogListDao {
	Logger logger = Logger.getLogger(DataLogListDao.class);

	@Override
	public List<DataLog> getDataLogList(DataLogKey dataLogKey)
			throws NoDataFoundException {
		logger.info("Going to fetch Data Log List from Database");
		List<String> data = new ArrayList<String>();
		StringBuffer sql = new StringBuffer().append("select ")
				.append("u.INST_ID,")
				.append("u.BRANCH_ID,")
				.append("ADT_SRL_NO,")
				.append("u.R_CRE_TIME,")
				.append("u.R_CRE_ID,")
				.append("link_id, ")
				.append("TBL_NAME,")
				.append("substring(new_record,14,1) ")
				.append(" from uadt u,usrl ul")
				.append(" where ")
				.append(" u.inst_id = ul.inst_id ")
				.append(" and")
				.append(" u.R_CRE_ID = ul.user_id ")
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
		data.add(dataLogKey.getAuditFlg().toUpperCase().trim());
		data.add(dataLogKey.getInstId().toUpperCase().trim());
		data.add(dataLogKey.getBranchId().toUpperCase().trim());
		data.add(dataLogKey.getFromDate().concat(" 00:00:00"));
		data.add(dataLogKey.getToDate().concat(" 23:59:59"));
		
		if (!(dataLogKey.getUserId().equals(""))) {
			sql.append(" and u.R_CRE_ID = ?");
			data.add(dataLogKey.getUserId().toUpperCase().trim());
		}
		if (!(dataLogKey.getLinkId().equals(""))) {
			sql.append(" and link_id = ?");
			data.add(dataLogKey.getLinkId().toUpperCase().trim());
		}
		if (!(dataLogKey.getTableName().equals(""))) {
			sql.append(" and TBL_NAME = ?");
			data.add(dataLogKey.getTableName().toUpperCase().trim());
		}
		if ((!(dataLogKey.getTypeOfOperation().equals("")))
				&& (!(dataLogKey.getTypeOfOperation().equals("null")))) {
			sql.append(" and new_record like 'TYPE_OF_OPER="
					+ dataLogKey.getTypeOfOperation().toUpperCase().trim()
					+ "%' ");
		}

		sql.append(" order by u.R_CRE_TIME desc");

		String[] array = data.toArray(new String[data.size()]);

		logger.debug("select query :" + sql.toString());
		List<DataLog> auditPojoList = getJdbcTemplate().query(sql.toString(),
				array, new AuditPojoRowMapper());
		if (auditPojoList.size() == 0) {
			throw new NoDataFoundException();
		}

		return auditPojoList;
	}

	class AuditPojoRowMapper implements RowMapper<DataLog> {
		@Override
		public DataLog mapRow(ResultSet rs, int arg1) throws SQLException {
			DataLog dataLog = new DataLog();
			dataLog.setInstId(rs.getString("INST_ID"));
			dataLog.setBranchId(rs.getString("BRANCH_ID"));
			dataLog.setAuditSrlNo(rs.getString("ADT_SRL_NO"));
			dataLog.setrCreTime(rs.getString("u.R_CRE_Time"));
			dataLog.setUserId(rs.getString("u.R_CRE_ID"));
			dataLog.setLinkId(rs.getString("link_id"));
			dataLog.setTableName(rs.getString("TBL_NAME"));
			dataLog.setTypeOfOperation(rs
					.getString("substring(new_record,14,1)"));
			return dataLog;
		}

	}

}
