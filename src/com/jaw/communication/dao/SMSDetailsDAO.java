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
public class SMSDetailsDAO extends BaseDao implements ISMSDetailsDAO{
	// Logging
	Logger logger = Logger.getLogger(SMSDetailsDAO.class);

	@Override
	public void saveSMSDetailsRecord(final SMSDetails smsDetails)
			throws DuplicateEntryException {
		logger.debug("Inside insert method");
		logger.debug("SMS Details object values :"
				+ smsDetails.toString());			
		StringBuffer query = new StringBuffer();
		query = query.append("insert into smsd ( ")
				.append("DB_TS,")
				.append("INST_ID,")
				.append("BRANCH_ID,")
				.append("AC_TERM,")
				.append("SMS_RQST_ID,")
				.append("SMS_SRL_NO,")
				.append("MOBILE_NUM_LIST,")
				.append("MOBILE_NUM_CNT,")
				.append("DELIVERED_NUM_CNT,")
				.append("UNDELIVERED_NUM_CNT,")
				.append("UNDELIVERED_NUM_LIST,")
				.append("DETAILS_STATUS,")
				.append("MSG_GRP_ID,")
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
							pss.setInt(1, smsDetails.getDbTs());
							pss.setString(2, smsDetails.getInstId());
							pss.setString(3, smsDetails.getBranchId());
							pss.setString(4, smsDetails.getAcTerm());
							pss.setString(5, smsDetails.getSmsReqstId().trim());
							pss.setInt(6, smsDetails.getSmsSrlNo());
							pss.setString(7, smsDetails.getMobileNumList().trim());
							pss.setInt(8, smsDetails.getMobileNumCnt());
							pss.setInt(9, smsDetails.getDeliveredNumCnt());
							pss.setInt(10, smsDetails.getUnDeliveredNumCnt());
							pss.setString(11, smsDetails.getUnDeliveredNumList().trim());
							pss.setString(12, smsDetails.getDetailsStatus().trim());
							pss.setString(13, smsDetails.getMsgGrpId().trim());
							pss.setString(14, smsDetails.getDelFlag().trim());
							pss.setString(15, smsDetails.getrModId().trim());
							pss.setString(16, smsDetails.getrCreId().trim());

						}

					});
		} catch (org.springframework.dao.DuplicateKeyException e) {
			logger.error("Duplicate Entry Exception Occured  "+e.getMessage());
			throw new DuplicateEntryException();
		}
	}
//select SMS_SRL_NO from smsd where SMS_RQST_ID='SMSR039' and INST_ID='ASC' and BRANCH_ID='BR001' and DEL_FLG='N' order by R_CRE_TIME asc;

	@Override
	public void updateSMSDetailsRecord(final SMSDetails smsDetails,
			final SMSDetailsKey smsDetailsKey) throws UpdateFailedException {
		logger.debug("Inside update Status method");
		logger.debug("SMSDetails object values :"+ smsDetails.toString());
		logger.debug("SMSDetailsKey object values :"+ smsDetailsKey.toString());
		
		StringBuffer sql = new StringBuffer();
		sql.append("update smsd set")
		        .append(" DB_TS= ?,")
		        .append("DELIVERED_NUM_CNT=?,")
		        .append("UNDELIVERED_NUM_CNT=?,")
		        .append("UNDELIVERED_NUM_LIST=?,")
		        .append("DETAILS_STATUS=?,")
		        .append("MSG_GRP_ID=?,")
				.append("DEL_FLG= 'N',")
				.append("R_MOD_ID= ?,")
				.append(" R_MOD_TIME=now()")
				.append(" where")
				.append(" DB_TS= ?")
				.append(" and  INST_ID= ?")
				.append(" and  BRANCH_ID= ?")
				.append(" and  SMS_RQST_ID= ?")		
				.append(" and  SMS_SRL_NO= ?")		
				.append(" and  AC_TERM= ?")		
				.append(" and  DEL_FLG='N'");

		

		int updateRecs = getJdbcTemplate().update(sql.toString(),
				new PreparedStatementSetter() {

					@Override
					public void setValues(PreparedStatement ps)
							throws SQLException {
						ps.setInt(1, smsDetails.getDbTs() + 1);
						ps.setInt(2, smsDetails.getDeliveredNumCnt());
						ps.setInt(3, smsDetails.getUnDeliveredNumCnt());
						ps.setString(4, smsDetails.getUnDeliveredNumList());
						ps.setString(5, smsDetails.getDetailsStatus());
						ps.setString(6, smsDetails.getMsgGrpId());						
						ps.setString(7, smsDetails.getrModId().trim());
						
						ps.setInt(8, smsDetailsKey.getDbTs());
						ps.setString(9, smsDetailsKey.getInstId().trim());
						ps.setString(10, smsDetailsKey.getBranchId().trim());
						ps.setString(11, smsDetailsKey.getSmsReqstId());
						ps.setInt(12, smsDetailsKey.getSmsSrlNo());
						ps.setString(13, smsDetailsKey.getAcTerm());
						

					}

				});
		if (updateRecs == 0) {
			logger.error("Update Failed Exception Occured");
			throw new UpdateFailedException();

		}

	}

	@Override
	public SMSDetails selectSMSDetailsRec(SMSDetailsKey smsDetailsKey)
			throws NoDataFoundException {
		logger.debug("Inside select method");
		logger.debug("SMSDetailsKey object values :"+ smsDetailsKey.toString());
		
		List<Object> data = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		    sql.append("select ")
		        .append("DB_TS,")
		        .append("INST_ID,")
				.append("BRANCH_ID,")
			    .append("AC_TERM,")
				.append("SMS_RQST_ID,")
				.append("SMS_SRL_NO,")
				.append("MOBILE_NUM_LIST,")
				.append("MOBILE_NUM_CNT,")
				.append("DELIVERED_NUM_CNT,")
				.append("UNDELIVERED_NUM_CNT,")
				.append("UNDELIVERED_NUM_LIST,")
				.append("DETAILS_STATUS,")
				.append("MSG_GRP_ID,")
				.append("DEL_FLG,")
				.append("R_MOD_ID,")
				.append("R_MOD_TIME,")
				.append("R_CRE_ID,")
				.append("R_CRE_TIME ")
				.append(" from smsd ")
				.append(" where")
				.append("  INST_ID= ?")
				.append(" and  BRANCH_ID= ?")
				.append(" and  SMS_RQST_ID= ?")		
				.append(" and  SMS_SRL_NO= ?")		
				.append(" and  AC_TERM= ?")		
				.append(" and  DEL_FLG=?");
		//    .append(" and  ALERT_STOP='N'");
		    data.add(smsDetailsKey.getInstId());
			data.add(smsDetailsKey.getBranchId());
			data.add(smsDetailsKey.getSmsReqstId());
			data.add(smsDetailsKey.getSmsSrlNo());
			data.add(smsDetailsKey.getAcTerm());
			data.add("N");
			
			if ((smsDetailsKey.getDbTs() !=null)&&(smsDetailsKey.getDbTs() !=0)) {
				sql.append(" and DB_TS=?  ");
				logger.debug("Db Ts Value :" + smsDetailsKey.getDbTs());
				data.add(smsDetailsKey.getDbTs());
			}
			SMSDetails selectedListSMSDetails = null;
		Object[] array = data.toArray(new Object[data.size()]);
		logger.debug("Where clause conditions size :" + array.length);
		selectedListSMSDetails = (SMSDetails) getJdbcTemplate()
				.query(sql.toString(), array, new ResultSetExtractor<SMSDetails>() {

					@Override
					public SMSDetails extractData(ResultSet rs)
							throws SQLException, DataAccessException {
						SMSDetails smsDetails = null;
						if (rs.next()) {
							
							smsDetails = new SMSDetails();
							smsDetails.setDbTs(rs.getInt("DB_TS"));
							smsDetails.setInstId(rs.getString("INST_ID"));
							smsDetails.setBranchId(rs.getString("BRANCH_ID"));
							smsDetails.setAcTerm(rs.getString("AC_TERM"));
							smsDetails.setSmsReqstId(rs.getString("SMS_RQST_ID"));
							smsDetails.setSmsSrlNo(rs.getInt("SMS_SRL_NO"));
							smsDetails.setMobileNumList(rs.getString("MOBILE_NUM_LIST"));
							smsDetails.setMobileNumCnt(rs.getInt("MOBILE_NUM_CNT"));
							smsDetails.setDeliveredNumCnt(rs.getInt("DELIVERED_NUM_CNT"));
							smsDetails.setUnDeliveredNumCnt(rs.getInt("UNDELIVERED_NUM_CNT"));
							smsDetails.setUnDeliveredNumList(rs.getString("UNDELIVERED_NUM_LIST"));
							smsDetails.setDetailsStatus(rs.getString("DETAILS_STATUS"));
							smsDetails.setMsgGrpId(rs.getString("MSG_GRP_ID"));
							smsDetails.setDelFlag((rs.getString("DEL_FLG")));
							smsDetails.setrModId(rs.getString("R_MOD_ID"));
							smsDetails.setrModTime(rs.getString("R_MOD_TIME"));
							smsDetails.setrCreId(rs.getString("R_CRE_ID"));
							smsDetails.setrCreTime(rs.getString("R_CRE_TIME"));
							
						}
						return smsDetails;
					}

				});

		if (selectedListSMSDetails == null) {
			logger.error("No  record found");
			throw new NoDataFoundException();
		}

		return selectedListSMSDetails;
	}
}
