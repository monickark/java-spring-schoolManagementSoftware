package com.jaw.framework.audit.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import com.jaw.admin.dao.DataLog;
import com.jaw.admin.dao.DataLogKey;
import com.jaw.common.exceptions.BatchUpdateFailedException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.framework.dao.BaseDao;

//Dao class for Audit Insert
@Repository
public class AuditDao extends BaseDao implements IAuditDao {

	Logger logger = Logger.getLogger(AuditDao.class);

	@Override
	public void insert(final Audit auditPojo) throws DuplicateEntryException {

		logger.debug("Inside insert method");

		StringBuffer sql = new StringBuffer();
		sql = sql.append("insert into uadt ( ").append("INST_ID, ")
				.append("BRANCH_ID, ").append("ADT_SRL_NO, ")
				.append("AUDIT_FLG, ").append("ACT_CODE, ")
				.append("TBL_NAME, ").append("TABLE_KEY, ")
				.append("OLD_RECORD, ").append("NEW_RECORD, ")
				.append("IPADDRESS, ").append("AUDIT_REMARKS,")
				.append("R_CRE_ID, ").append("R_CRE_TIME) ")
				.append("values( ?,?,?,?,?,?,?,?,?,?,?,?,now())");
		try {
			getJdbcTemplate().update(sql.toString(),
					new PreparedStatementSetter() {

						@Override
						public void setValues(PreparedStatement pss)
								throws SQLException {
							pss.setString(1, auditPojo.getInstId().trim());
							pss.setString(2, auditPojo.getBranchId().trim());
							pss.setString(3, auditPojo.getAuditSrlNo().trim());
							pss.setString(4, auditPojo.getAuditFlag().trim());
							pss.setString(5, auditPojo.getActCode().trim());
							pss.setString(6, auditPojo.getTableName().trim());
							pss.setString(7, auditPojo.getTableKey().trim());
							pss.setString(8, auditPojo.getOldRecord().trim());
							pss.setString(9, auditPojo.getNewRecord().trim());
							pss.setString(10, auditPojo.getIpAddress());
							pss.setString(11, auditPojo.getAuditRemarks()
									.trim());
							pss.setString(12, auditPojo.getrCreId().trim());
						}
					});
		} catch (org.springframework.dao.DuplicateKeyException e) {
			throw new DuplicateEntryException();
		}

	}

	@Override
	public int[] insertBatch(final Audit auditPojo, final String[] userIds,
			final String[] slNos) throws DuplicateEntryException,
			BatchUpdateFailedException {

		StringBuffer sql = new StringBuffer();
		sql = sql.append("insert into uadt ( ").append("INST_ID, ")
				.append("BRANCH_ID, ").append("ADT_SRL_NO, ")
				.append("AUDIT_FLG, ").append("ACT_CODE, ")
				.append("TBL_NAME, ").append("TABLE_KEY, ")
				.append("OLD_RECORD, ").append("NEW_RECORD, ")
				.append("IPADDRESS, ").append("AUDIT_REMARKS,")
				.append("R_CRE_ID, ").append("R_CRE_TIME) ")
				.append("values( ?,?,?,?,?,?,?,?,?,?,?,?,now())");
		int[] ret = null;
		try {
			ret = getJdbcTemplate().batchUpdate(sql.toString(),
					new BatchPreparedStatementSetter() {

						@Override
						public int getBatchSize() {
							return userIds.length;
						}

						@Override
						public void setValues(java.sql.PreparedStatement pss,
								int i) throws SQLException {

							pss.setString(1, auditPojo.getInstId().trim());
							pss.setString(2, auditPojo.getBranchId().trim());
							pss.setString(3, slNos[i]);
							pss.setString(4, auditPojo.getAuditFlag().trim());
							pss.setString(5, auditPojo.getActCode().trim());
							pss.setString(6, auditPojo.getTableName().trim());
							pss.setString(7, auditPojo.getTableKey().trim());
							pss.setString(8, auditPojo.getOldRecord().trim());
							pss.setString(9, auditPojo.getNewRecord().trim());
							pss.setString(10, auditPojo.getIpAddress());
							pss.setString(11, userIds[i]);
							pss.setString(12, auditPojo.getrCreId().trim());
						}
					});
			for (int sa : ret) {
				if (sa == 0) {
					throw new BatchUpdateFailedException();
				}
			}
		} catch (DuplicateKeyException duplicateKeyException) {
			throw new DuplicateEntryException();
		}
		return ret;

	}

	@Override
	public DataLog getAuditRecord(final DataLogKey dataLogKey)
			throws NoDataFoundException {

		logger.info("Going to get the DataLog objects from database");
		DataLog dataLog = null;
		StringBuffer sql = new StringBuffer().append("select ")
				.append("u.INST_ID, ").append("u.BRANCH_ID, ")
				.append("ADT_SRL_NO, ").append("AUDIT_FLG, ")
				.append("TBL_NAME, ").append("TABLE_KEY, ")
				.append("OLD_RECORD, ").append("NEW_RECORD, ")
				.append("IPADDRESS, ").append("AUDIT_REMARKS, ")
				.append("u.R_CRE_ID, ").append("u.R_CRE_TIME, ")
				.append("LINK_ID,").append("substring(new_record,14,1) as top")
				.append(" from uadt u,usrl ul").append(" where")
				.append(" u.inst_id = ul.inst_id").append(" AND")
				.append(" u.R_CRE_ID = ul.user_id").append(" AND")
				.append(" AUDIT_FLG = ?")
				.append(" AND ADT_SRL_NO = ? ");

		dataLog = getJdbcTemplate().query(sql.toString(),
				new PreparedStatementSetter() {

					@Override
					public void setValues(PreparedStatement pss)
							throws SQLException {
						pss.setString(1, dataLogKey.getAuditFlg().toUpperCase()
								.trim());						
						pss.setString(2, dataLogKey.getAuditSrlNo()
								.toUpperCase().trim());

					}

				}, new ResultSetExtractor<DataLog>() {

					@Override
					public DataLog extractData(ResultSet rs)
							throws SQLException, DataAccessException {
						DataLog dataLog = new DataLog();
						if (rs.next()) {
							dataLog.setInstId(rs.getString("INST_ID"));
							dataLog.setBranchId(rs.getString("BRANCH_ID"));
							dataLog.setAuditSrlNo(rs.getString("ADT_SRL_NO"));
							dataLog.setAuditFlag(rs.getString("AUDIT_FLG"));
							dataLog.setTableName(rs.getString("TBL_NAME"));
							dataLog.setTableKey(rs.getString("TABLE_KEY"));
							dataLog.setOldRecord(rs.getString("OLD_RECORD"));
							dataLog.setNewRecord(rs.getString("NEW_RECORD"));
							dataLog.setIpAddress(rs.getString("IPADDRESS"));
							dataLog.setAuditRemarks(rs
									.getString("AUDIT_REMARKS"));
							dataLog.setUserId(rs.getString("R_CRE_ID"));
							dataLog.setrCreTime(rs.getString("R_CRE_TIME"));
							dataLog.setLinkId(rs.getString("LINK_ID"));
							dataLog.setTypeOfOperation(rs
									.getString("top"));
						}
						return dataLog;
					}

				});
		if (dataLog == null) {
			throw new NoDataFoundException();
		}

		return dataLog;

	}

}
