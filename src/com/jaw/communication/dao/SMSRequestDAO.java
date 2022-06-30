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
public class SMSRequestDAO extends BaseDao implements ISMSRequestDAO{
	// Logging
		Logger logger = Logger.getLogger(SMSRequestDAO.class);

		@Override
		public void saveSMSRequestRecord(final SMSRequest smsRequest)
				throws DuplicateEntryException {
			logger.debug("Inside insert method");
			logger.debug("SMS Request object values :"
					+ smsRequest.toString());			
			StringBuffer query = new StringBuffer();
			query = query.append("insert into smsr ( ")
					.append("DB_TS,")
					.append("INST_ID,")
					.append("BRANCH_ID,")
					.append("AC_TERM,")
					.append("SMS_RQST_ID,")
					.append("SMS_MSG,")
					.append("RQST_CATEGORY,")
					.append("GEN_GRP_LIST,")
					.append("SPECIFIC_GRP_LIST,")
					.append("SPECIFIC_MEMBER_LIST,")
					.append("RQST_STATUS,")
					.append("RQST_TYPE,")					
					.append("DEL_FLG,")
					.append("R_MOD_ID,")
					.append("R_MOD_TIME,")
					.append("R_CRE_ID,")
					.append("R_CRE_TIME)")

					.append(" values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,now(),?,now())");

			try {
				getJdbcTemplate().update(query.toString(),
						new PreparedStatementSetter() {

							@Override
							public void setValues(PreparedStatement pss)
									throws SQLException {
								pss.setInt(1, smsRequest.getDbTs());
								pss.setString(2, smsRequest.getInstId());
								pss.setString(3, smsRequest.getBranchId());
								pss.setString(4, smsRequest.getAcTerm());
								pss.setString(5, smsRequest.getSmsReqstId().trim());
								pss.setString(6, smsRequest.getSmsMessage().trim());
								pss.setString(7, smsRequest.getReqstCategory().trim());
								pss.setString(8, smsRequest.getGeneralGrpList().trim());
								pss.setString(9, smsRequest.getSpecificGrpList().trim());
								pss.setString(10, smsRequest.getSpecificMembrList().trim());
								pss.setString(11, smsRequest.getReqstStatus().trim());
								pss.setString(12, smsRequest.getReqstType().trim());
								pss.setString(13, smsRequest.getDelFlag().trim());
								pss.setString(14, smsRequest.getrModId().trim());
								pss.setString(15, smsRequest.getrCreId().trim());

							}

						});
			} catch (org.springframework.dao.DuplicateKeyException e) {
				logger.error("Duplicate Entry Exception Occured  "+e.getMessage());
				throw new DuplicateEntryException();
			}
		}

		@Override
		public void updateSMSRequestStatus(final SMSRequest smsRequest,
				final SMSRequestKey smsRequestKey) throws UpdateFailedException {
			logger.debug("Inside update Status method");
			logger.debug("SMSRequest object values :"+ smsRequest.toString());
			logger.debug("SMSRequestKey object values :"+ smsRequestKey.toString());
			
			StringBuffer sql = new StringBuffer();
			sql.append("update smsr set")
			        .append(" DB_TS= ?,")
			      .append("RQST_STATUS=?,")
					.append("DEL_FLG= 'N',")
					.append("R_MOD_ID= ?,")
					.append(" R_MOD_TIME=now()")
					.append(" where")
					.append(" DB_TS= ?")
					.append(" and  INST_ID= ?")
					.append(" and  BRANCH_ID= ?")
					.append(" and  SMS_RQST_ID= ?")				
					.append(" and  DEL_FLG='N'");

			

			int updateRecs = getJdbcTemplate().update(sql.toString(),
					new PreparedStatementSetter() {

						@Override
						public void setValues(PreparedStatement ps)
								throws SQLException {
							ps.setInt(1, smsRequest.getDbTs() + 1);
							ps.setString(2, smsRequest.getReqstStatus().trim());
							ps.setString(3, smsRequest.getrModId().trim());
							ps.setInt(4, smsRequestKey.getDbTs());
							ps.setString(5, smsRequestKey.getInstId().trim());
							ps.setString(6, smsRequestKey.getBranchId().trim());
							ps.setString(7, smsRequestKey.getSmsReqstId());
							

						}

					});
			if (updateRecs == 0) {
				logger.error("Update Failed Exception Occured");
				throw new UpdateFailedException();

			}

		}

		@Override
		public SMSRequest selectSMSRequestRec(SMSRequestKey smsRequestKey)
				throws NoDataFoundException {
			logger.debug("Inside select method");
			logger.debug("SMSRequestKey object values :"+ smsRequestKey.toString());
			
			List<Object> data = new ArrayList<Object>();
			StringBuffer sql = new StringBuffer();
			    sql.append("select ")
			        .append("DB_TS,")
			        .append("INST_ID,")
					.append("BRANCH_ID,")
				    .append("AC_TERM,")
					.append("SMS_RQST_ID,")
					.append("SMS_MSG,")
					.append("RQST_CATEGORY,")
					.append("GEN_GRP_LIST,")
					.append("SPECIFIC_GRP_LIST,")
					.append("SPECIFIC_MEMBER_LIST,")
					.append("RQST_STATUS,")
					.append("RQST_TYPE,")		
					.append("DEL_FLG,")
					.append("R_MOD_ID,")
					.append("R_MOD_TIME,")
					.append("R_CRE_ID,")
					.append("R_CRE_TIME ")
					.append(" from smsr ")
					.append(" where")
					.append("  INST_ID= ?")
					.append(" and  BRANCH_ID= ?")
					.append(" and  SMS_RQST_ID= ?")				
					.append(" and  DEL_FLG=?");
			//    .append(" and  ALERT_STOP='N'");
			    data.add(smsRequestKey.getInstId());
				data.add(smsRequestKey.getBranchId());
				data.add(smsRequestKey.getSmsReqstId());
				data.add("N");
				
				if ((smsRequestKey.getDbTs() !=null)&&(smsRequestKey.getDbTs() !=0)) {
					sql.append(" and DB_TS=?  ");
					logger.debug("Db Ts Value :" + smsRequestKey.getDbTs());
					data.add(smsRequestKey.getDbTs());
				}
				SMSRequest selectedListSMSRequest = null;
			Object[] array = data.toArray(new Object[data.size()]);
			logger.debug("Where clause conditions size :" + array.length);
			selectedListSMSRequest = (SMSRequest) getJdbcTemplate()
					.query(sql.toString(), array, new ResultSetExtractor<SMSRequest>() {

						@Override
						public SMSRequest extractData(ResultSet rs)
								throws SQLException, DataAccessException {
							SMSRequest smsRequest = null;
							if (rs.next()) {
								smsRequest = new SMSRequest();
								smsRequest.setDbTs(rs.getInt("DB_TS"));
								smsRequest.setInstId(rs.getString("INST_ID"));
								smsRequest.setBranchId(rs.getString("BRANCH_ID"));
								smsRequest.setAcTerm(rs.getString("AC_TERM"));
								smsRequest.setSmsReqstId(rs.getString("SMS_RQST_ID"));
								smsRequest.setSmsMessage(rs.getString("SMS_MSG"));
								smsRequest.setReqstCategory(rs.getString("RQST_CATEGORY"));
								smsRequest.setGeneralGrpList(rs.getString("GEN_GRP_LIST"));
								smsRequest.setSpecificGrpList(rs.getString("SPECIFIC_GRP_LIST"));
								smsRequest.setSpecificMembrList(rs.getString("SPECIFIC_MEMBER_LIST"));
								smsRequest.setReqstStatus(rs.getString("RQST_STATUS"));
								smsRequest.setReqstType(rs.getString("RQST_TYPE"));
								smsRequest.setDelFlag((rs.getString("DEL_FLG")));
								smsRequest.setrModId(rs.getString("R_MOD_ID"));
								smsRequest.setrModTime(rs.getString("R_MOD_TIME"));
								smsRequest.setrCreId(rs.getString("R_CRE_ID"));
								smsRequest.setrCreTime(rs.getString("R_CRE_TIME"));
								
							}
							return smsRequest;
						}

					});

			if(selectedListSMSRequest == null) {
				logger.error("No  record found");
				throw new NoDataFoundException();
			}

			return selectedListSMSRequest;
		}

}
