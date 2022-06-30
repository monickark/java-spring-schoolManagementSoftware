package com.jaw.communication.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import com.jaw.common.exceptions.DeleteFailedException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.UpdateFailedException;
import com.jaw.framework.dao.BaseDao;
@Repository
public class AlertDAO extends BaseDao implements IAlertDAO {
	// Logging
	Logger logger = Logger.getLogger(AlertDAO.class);

	@Override
	public void insertAlertRec(final Alert alertRecord)
			throws DuplicateEntryException {
		logger.debug("Inside insert method");
		logger.debug("Alert object values :"
				+ alertRecord.toString());
		
		StringBuffer query = new StringBuffer();
		query = query.append("insert into alrt ( ")
				.append("DB_TS,")
				.append("INST_ID,")
				.append("BRANCH_ID,")
				.append("ALERT_SER_NO,")
				.append("AC_TERM,")
				.append("RQST_CATEGORY,")
				.append("GEN_GRP_LIST,")
				.append("SPECIFIC_GRP_LIST,")
				.append("ALERT_MESSAGE,")
				.append("FROM_DATE,")
				.append("TO_DATE,")
				.append("IMPORTANT,")
				.append("ALERT_STOP,")
				.append("DEL_FLG,")
				.append("R_MOD_ID,")
				.append("R_MOD_TIME,")
				.append("R_CRE_ID,")
				.append("R_CRE_TIME)")

				.append(" values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,now(),?,now())");

		try {
			getJdbcTemplate().update(query.toString(),
					new PreparedStatementSetter() {

						@Override
						public void setValues(PreparedStatement pss)
								throws SQLException {
							pss.setInt(1, alertRecord.getDbTs());
							pss.setString(2, alertRecord.getInstId());
							pss.setString(3, alertRecord.getBranchId());
							pss.setString(4, alertRecord.getAlertSerialNo());
							pss.setString(5, alertRecord.getAcTerm().trim());
							pss.setString(6, alertRecord.getReqstCategory().trim());
							pss.setString(7, alertRecord.getGeneralGrpList());
							pss.setString(8, alertRecord.getSpecificGrpList());
							pss.setString(9, alertRecord.getAlertMessage().trim());
							pss.setString(10, alertRecord.getFromDate());
							pss.setString(11, alertRecord.getToDate());
							pss.setString(12, alertRecord.getImportant());
							pss.setString(13, alertRecord.getAlertStop());
							pss.setString(14, alertRecord.getDelFlag().trim());
							pss.setString(15, alertRecord.getrModId().trim());
							pss.setString(16, alertRecord.getrCreId().trim());

						}

					});
		} catch (org.springframework.dao.DuplicateKeyException e) {
			logger.error("Duplicate Entry Exception Occured  "+e.getMessage());
			throw new DuplicateEntryException();
		}
	}

	@Override
	public void updateAlertRec(final Alert alertRecord, final AlertKey alertKey)
			throws UpdateFailedException {
		logger.debug("Inside update method");
		logger.debug("Alert object values :"+ alertRecord.toString());
		logger.debug("AlertKey object values :"+ alertKey.toString());
		
		StringBuffer sql = new StringBuffer();
		sql.append("update alrt set")
		        .append(" DB_TS= ?,")
		        .append("RQST_CATEGORY= ?,")
				.append("GEN_GRP_LIST= ?,")
				.append("SPECIFIC_GRP_LIST= ?,")
				.append("ALERT_MESSAGE=?,")
				.append("FROM_DATE=?,")
				.append("TO_DATE=?,")
				.append("IMPORTANT=?,")
				.append("ALERT_STOP=?,")
				.append("DEL_FLG= 'N',")
				.append("R_MOD_ID= ?,")
				.append(" R_MOD_TIME=now()")
				.append(" where")
				.append(" DB_TS= ?")
				.append(" and  INST_ID= ?")
				.append(" and  BRANCH_ID= ?")
				.append(" and  ALERT_SER_NO= ?")				
				.append(" and  DEL_FLG='N'")
		.append(" and  ALERT_STOP='N'");

		

		int updateRecs = getJdbcTemplate().update(sql.toString(),
				new PreparedStatementSetter() {

					@Override
					public void setValues(PreparedStatement ps)
							throws SQLException {
						ps.setInt(1, alertRecord.getDbTs() + 1);
						ps.setString(2, alertRecord.getReqstCategory().trim());
						ps.setString(3, alertRecord.getGeneralGrpList());
						ps.setString(4, alertRecord.getSpecificGrpList());
						ps.setString(5, alertRecord.getAlertMessage().trim());
						ps.setString(6, alertRecord.getFromDate());
						ps.setString(7, alertRecord.getToDate());
						ps.setString(8, alertRecord.getImportant());
						ps.setString(9, alertRecord.getAlertStop());
						ps.setString(10, alertRecord.getrModId().trim());
						ps.setInt(11, alertKey.getDbTs());
						ps.setString(12, alertKey.getInstId().trim());
						ps.setString(13, alertKey.getBranchId().trim());
						ps.setString(14, alertKey.getAlertSerialNo());
						

					}

				});
		if (updateRecs == 0) {
			logger.error("Update Failed Exception Occured");
			throw new UpdateFailedException();

		}

	}

	@Override
	public void deleteAlertRec(final Alert alertRecord, final AlertKey alertKey)
			throws DeleteFailedException {
		logger.debug("Inside delete method");
		logger.debug("Alert object values :"+ alertRecord.toString());
		logger.debug("AlertKey object values :"+ alertKey.toString());
		
		StringBuffer sql = new StringBuffer();
		   sql.append("update alrt set")
		        .append(" DB_TS= ?,")
				.append("DEL_FLG= 'Y',")
				.append("R_MOD_ID= ?,")
				.append(" R_MOD_TIME=now()")
				.append(" where")
				.append(" DB_TS= ?")
				.append(" and INST_ID= ?")
				.append(" and  BRANCH_ID= ?")
				.append(" and  ALERT_SER_NO= ?")
				.append(" and  DEL_FLG='N'")
		   .append(" and  ALERT_STOP='N'");
		int deletedRecs = getJdbcTemplate().update(sql.toString(),
				new PreparedStatementSetter() {

					@Override
					public void setValues(PreparedStatement ps)
							throws SQLException {
						ps.setInt(1, alertRecord.getDbTs());
						ps.setString(2, alertRecord.getrModId().trim());
						ps.setInt(3, alertKey.getDbTs());
						ps.setString(4, alertKey.getInstId().trim());
						ps.setString(5, alertKey.getBranchId().trim());
						ps.setString(6, alertKey.getAlertSerialNo());
						

					}

				});
		if (deletedRecs == 0) {
			logger.error("Delete Failed Exception");
			throw new DeleteFailedException();

		}
	}

	@Override
	public Alert selectAlertRec(AlertKey alertKey) throws NoDataFoundException {
		logger.debug("Inside select method");
		logger.debug("AlertKey object values :"+ alertKey.toString());
		List<Object> data = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		    sql.append("select ")
		        .append("DB_TS,")
		        .append("INST_ID,")
				.append("BRANCH_ID,")
			    .append("ALERT_SER_NO,")
				.append("AC_TERM,")
				.append("RQST_CATEGORY,")
				.append("GEN_GRP_LIST,")
				.append("SPECIFIC_GRP_LIST,")
				.append("ALERT_MESSAGE,")
				.append("FROM_DATE,")
				.append("TO_DATE,")
				.append("IMPORTANT,")
				.append("ALERT_STOP,")
				.append("DEL_FLG,")
				.append("R_MOD_ID,")
				.append("R_MOD_TIME,")
				.append("R_CRE_ID,")
				.append("R_CRE_TIME ")
				.append(" from alrt ")
				.append(" where")
				.append("  INST_ID= ?")
				.append(" and  BRANCH_ID= ?")
				.append(" and  ALERT_SER_NO= ?")				
				.append(" and  DEL_FLG=?");
		//    .append(" and  ALERT_STOP='N'");
		    data.add(alertKey.getInstId());
			data.add(alertKey.getBranchId());
			data.add(alertKey.getAlertSerialNo());
			data.add("N");
			
			if ((alertKey.getDbTs() !=null)&&(alertKey.getDbTs() !=0)) {
				sql.append(" and DB_TS=?  ");
				logger.debug("Db Ts Value :" + alertKey.getDbTs());
				data.add(alertKey.getDbTs());
			}
			Alert selectedListAlert = null;
		Object[] array = data.toArray(new Object[data.size()]);
		logger.debug("Where clause conditions size :" + array.length);
		selectedListAlert = (Alert) getJdbcTemplate()
				.query(sql.toString(), array, new ResultSetExtractor<Alert>() {

					@Override
					public Alert extractData(ResultSet rs)
							throws SQLException, DataAccessException {
						Alert alert = null;
						if (rs.next()) {
							alert = new Alert();
							alert.setDbTs(rs.getInt("DB_TS"));
							alert.setInstId(rs.getString("INST_ID"));
							alert.setBranchId(rs.getString("BRANCH_ID"));
							alert.setAlertSerialNo(rs.getString("ALERT_SER_NO"));
							alert.setAcTerm(rs.getString("AC_TERM"));
							alert.setReqstCategory(rs.getString("RQST_CATEGORY"));
							alert.setGeneralGrpList(rs.getString("GEN_GRP_LIST"));
							alert.setSpecificGrpList(rs.getString("SPECIFIC_GRP_LIST"));
							alert.setAlertMessage(rs.getString("ALERT_MESSAGE"));
							alert.setFromDate(rs.getString("FROM_DATE"));
							alert.setToDate(rs.getString("TO_DATE"));
							alert.setImportant(rs.getString("IMPORTANT"));
							alert.setAlertStop(rs.getString("ALERT_STOP"));
							alert.setDelFlag((rs.getString("DEL_FLG")));
							alert.setrModId(rs.getString("R_MOD_ID"));
							alert.setrModTime(rs.getString("R_MOD_TIME"));
							alert.setrCreId(rs.getString("R_CRE_ID"));
							alert.setrCreTime(rs.getString("R_CRE_TIME"));
							
						}
						return alert;
					}

				});

		if (selectedListAlert == null) {
			logger.error("No  record found");
			throw new NoDataFoundException();
		}

		return selectedListAlert;
	}

	@Override
	public void stopAlertRec(final Alert alertRecord, final AlertKey alertKey)
			throws UpdateFailedException {
		logger.debug("Inside Alert Stop method");
		logger.debug("Alert object values :"+ alertRecord.toString());
		logger.debug("AlertKey object values :"+ alertKey.toString());
		
		StringBuffer sql = new StringBuffer();
		   sql.append("update alrt set")
		        .append(" DB_TS= ?,")
				.append("ALERT_STOP= 'Y',")
				.append("R_MOD_ID= ?,")
				.append(" R_MOD_TIME=now()")
				.append(" where")
				.append(" DB_TS= ?")
				.append(" and INST_ID= ?")
				.append(" and  BRANCH_ID= ?")
				.append(" and  ALERT_SER_NO= ?")
				.append(" and  DEL_FLG='N'")
		   .append(" and  ALERT_STOP='N'");
		int updateRecs = getJdbcTemplate().update(sql.toString(),
				new PreparedStatementSetter() {

					@Override
					public void setValues(PreparedStatement ps)
							throws SQLException {
						ps.setInt(1, alertRecord.getDbTs());
						ps.setString(2, alertRecord.getrModId().trim());
						ps.setInt(3, alertKey.getDbTs());
						ps.setString(4, alertKey.getInstId().trim());
						ps.setString(5, alertKey.getBranchId().trim());
						ps.setString(6, alertKey.getAlertSerialNo());
						

					}

				});
		if (updateRecs == 0) {
			logger.error("Update Failed Exception");
			throw new UpdateFailedException();

		}
	}

}
