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

import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.UpdateFailedException;
import com.jaw.framework.dao.BaseDao;

@Repository
public class SMSAlertDAO extends BaseDao implements ISMSAlertDAO {
	// Logging
	Logger logger = Logger.getLogger(SMSAlertDAO.class);

	@Override
	public void insertSMSAlertRec(final SMSAlert smsAlertRecord)
			throws DuplicateEntryException {
		logger.debug("Inside insert method");
		logger.debug("SMSAlert object values :"
				+ smsAlertRecord.toString());
		
		StringBuffer query = new StringBuffer();
		query = query.append("insert into smsa ( ")
				.append("DB_TS,")
				.append("INST_ID,")
				.append("BRANCH_ID,")
				.append("AC_TERM,")
				.append("ALERT_TYPE,")
				.append("ALERT_DATE,")
				.append("ATTENDANCE_DATE,")
				.append("SMS_MSG,")
				.append("LINK_ID,")
				.append("MOB_NO,")				
				.append("DELIVERY_STATUS,")
				.append("MSG_GRP_ID,")
				.append("DEL_FLG,")
				.append("R_MOD_ID,")
				.append("R_MOD_TIME,")
				.append("R_CRE_ID,")
				.append("R_CRE_TIME)")
				.append(" values (?,?,?,?,?,CURDATE(),?,?,?,?,?,?,?,?,now(),?,now())");

		try {
			getJdbcTemplate().update(query.toString(),
					new PreparedStatementSetter() {

						@Override
						public void setValues(PreparedStatement pss)
								throws SQLException {
							pss.setInt(1, 1);
							pss.setString(2, smsAlertRecord.getInstId());
							pss.setString(3, smsAlertRecord.getBranchId());
							pss.setString(4, smsAlertRecord.getAcTerm().trim());
							pss.setString(5, smsAlertRecord.getAlertType().trim());
							pss.setString(6, smsAlertRecord.getAttendanceDate().trim());
							pss.setString(7, smsAlertRecord.getSmsMessage());
							pss.setString(8, smsAlertRecord.getLinkId());
							pss.setString(9, smsAlertRecord.getMobileNum());
							pss.setString(10, smsAlertRecord.getDeliveryStatus());
							pss.setString(11, smsAlertRecord.getMsgGrpId());
							pss.setString(12, smsAlertRecord.getDelFlag().trim());
							pss.setString(13, smsAlertRecord.getrModId().trim());
							pss.setString(14, smsAlertRecord.getrCreId().trim());

						}

					});
		} catch (org.springframework.dao.DuplicateKeyException e) {
			logger.error("Duplicate Entry Exception Occured  "+e.getMessage());
			throw new DuplicateEntryException();
		}
	}

	@Override
	public void updateSMSAlertRec(final SMSAlert smsAlertRecord,
			final SMSAlertKey smsAlertKey) throws UpdateFailedException {
		logger.debug("Inside update method");
		logger.debug("SMSAlert object values :"+ smsAlertRecord.toString());
		logger.debug("SMSAlertKey object values :"+ smsAlertKey.toString());
		
		StringBuffer sql = new StringBuffer();
		sql.append("update smsa set")
		        .append(" DB_TS= ?,")
		      .append("DELIVERY_STATUS=?,")
				.append("MSG_GRP_ID=?,")				
				.append("DEL_FLG= 'N',")
				.append("R_MOD_ID= ?,")
				.append(" R_MOD_TIME=now()")
				.append(" where")
				.append("  DB_TS= ?")
				.append(" and INST_ID= ?")
				.append(" and  BRANCH_ID= ?")
				.append(" and  AC_TERM= ?")	
				.append(" and  ALERT_TYPE= ?")
				.append(" and  ALERT_DATE= ?")
				.append(" and  LINK_ID= ?")	
				.append(" and  ATTENDANCE_DATE= ?")	
				.append(" and  DEL_FLG=?");
		

		

		int updateRecs = getJdbcTemplate().update(sql.toString(),
				new PreparedStatementSetter() {

					@Override
					public void setValues(PreparedStatement ps)
							throws SQLException {
						ps.setInt(1, smsAlertRecord.getDbTs() + 1);
						ps.setString(2, smsAlertRecord.getDeliveryStatus().trim());
						ps.setString(3, smsAlertRecord.getMsgGrpId().trim());
						ps.setString(4, smsAlertRecord.getrModId().trim());
						ps.setInt(5, smsAlertKey.getDbTs());
						ps.setString(6, smsAlertKey.getInstId().trim());
						ps.setString(7, smsAlertKey.getBranchId().trim());
						ps.setString(8, smsAlertKey.getAcTerm());
						ps.setString(9, smsAlertKey.getAlertType());
						ps.setString(10, smsAlertKey.getAlertDate());
						ps.setString(11, smsAlertKey.getLinkId());
						ps.setString(12, smsAlertKey.getAttendanceDate());
						ps.setString(13, "N");
						

					}

				});
		if (updateRecs == 0) {
			logger.error("Update Failed Exception Occured");
			throw new UpdateFailedException();

		}

	}

	@Override
	public SMSAlert selectSMSAlertRec(SMSAlertKey smsAlertKey)
			throws NoDataFoundException {
		logger.debug("Inside select method");
		logger.debug("SMSAlertKey object values :"+ smsAlertKey.toString());
		List<Object> data = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		    sql.append("select ")
		     .append("DB_TS,")
				.append("INST_ID,")
				.append("BRANCH_ID,")
				.append("AC_TERM,")
				.append("ALERT_TYPE,")
				.append("ALERT_DATE,")
				.append("ATTENDANCE_DATE,")
				.append("SMS_MSG,")
				.append("LINK_ID,")
				.append("MOB_NO,")				
				.append("DELIVERY_STATUS,")
				.append("MSG_GRP_ID,")
				.append("DEL_FLG,")
				.append("R_MOD_ID,")
				.append("R_MOD_TIME,")
				.append("R_CRE_ID,")
				.append("R_CRE_TIME")
				.append(" from smsa ")
				.append(" where")
				.append("  INST_ID= ?")
				.append(" and  BRANCH_ID= ?")
				.append(" and  AC_TERM= ?")	
				.append(" and  ALERT_TYPE= ?")				
				.append(" and  LINK_ID= ?")				
				.append(" and  DEL_FLG=?");
		//    .append(" and  ALERT_STOP='N'");
		    data.add(smsAlertKey.getInstId());
			data.add(smsAlertKey.getBranchId());
			data.add(smsAlertKey.getAcTerm());
			data.add(smsAlertKey.getAlertType());			
			data.add(smsAlertKey.getLinkId());
			
			data.add("N");
			
			if ((smsAlertKey.getDbTs() !=null)&&(smsAlertKey.getDbTs() !=0)) {
				sql.append(" and DB_TS=?  ");
				logger.debug("Db Ts Value :" + smsAlertKey.getDbTs());
				data.add(smsAlertKey.getDbTs());
			}
			if ((smsAlertKey.getAttendanceDate() !=null)&&(!smsAlertKey.getAttendanceDate().equals(""))) {
				sql.append(" and ATTENDANCE_DATE=?  ");
				logger.debug("ATTENDANCE_DATE Value :" + smsAlertKey.getAttendanceDate());
				data.add(smsAlertKey.getAttendanceDate());
			}
			if ((smsAlertKey.getAlertDate() !=null)&&(!smsAlertKey.getAlertDate().equals(""))) {
				sql.append(" and ALERT_DATE=?  ");
				logger.debug("ALERT_DATE Value :" + smsAlertKey.getAlertDate());
				data.add(smsAlertKey.getAlertDate());
			}
			
			SMSAlert selectedListSMSAlert = null;
		Object[] array = data.toArray(new Object[data.size()]);
		logger.debug("Where clause conditions size :" + array.length);
		selectedListSMSAlert = (SMSAlert) getJdbcTemplate()
				.query(sql.toString(), array, new ResultSetExtractor<SMSAlert>() {

					@Override
					public SMSAlert extractData(ResultSet rs)
							throws SQLException, DataAccessException {
						SMSAlert smsAlert = null;
						if (rs.next()) {							
							smsAlert = new SMSAlert();
							smsAlert.setDbTs(rs.getInt("DB_TS"));
							smsAlert.setInstId(rs.getString("INST_ID"));
							smsAlert.setBranchId(rs.getString("BRANCH_ID"));
							smsAlert.setAcTerm(rs.getString("AC_TERM"));
							smsAlert.setAlertType(rs.getString("ALERT_TYPE"));
							smsAlert.setAlertDate(rs.getString("ALERT_DATE"));
							smsAlert.setAttendanceDate(rs.getString("ATTENDANCE_DATE"));
							smsAlert.setSmsMessage(rs.getString("SMS_MSG"));
							smsAlert.setLinkId(rs.getString("LINK_ID"));
							smsAlert.setMobileNum(rs.getString("MOB_NO"));
							smsAlert.setDeliveryStatus(rs.getString("DELIVERY_STATUS"));
							smsAlert.setMsgGrpId(rs.getString("MSG_GRP_ID"));
							smsAlert.setDelFlag((rs.getString("DEL_FLG")));
							smsAlert.setrModId(rs.getString("R_MOD_ID"));
							smsAlert.setrModTime(rs.getString("R_MOD_TIME"));
							smsAlert.setrCreId(rs.getString("R_CRE_ID"));
							smsAlert.setrCreTime(rs.getString("R_CRE_TIME"));
							
						}
						return smsAlert;
					}

				});

		if (selectedListSMSAlert == null) {
			logger.error("No  record found");
			throw new NoDataFoundException();
		}
System.out.println("sms alertttttttttttttttttt"+selectedListSMSAlert.toString());
		return selectedListSMSAlert;
	}

}
