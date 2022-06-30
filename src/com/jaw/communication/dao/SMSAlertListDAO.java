package com.jaw.communication.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.stereotype.Repository;


import com.jaw.common.exceptions.batch.DuplicateEntryForHolGenException;
import com.jaw.framework.dao.BaseDao;
@Repository
public class SMSAlertListDAO extends BaseDao implements ISMSAlertListDAO{
	// Logging
	Logger logger = Logger.getLogger(SMSAlertListDAO.class);

	@Override
	public void insertSMSAlertListRecs(final List<SMSAlert> smsAlertList)
			throws DuplicateEntryForHolGenException {
		logger.debug("Inside SMS Alert List Insert Record");
		logger.debug("Alert List Size   "+smsAlertList.size());
		StringBuffer query = new StringBuffer();
		
		query = query.append("insert into smsa ( ")
				.append("DB_TS,")
				.append("INST_ID,")
				.append("BRANCH_ID,")
				.append("AC_TERM,")
				.append("ALERT_TYPE,")
				.append("ALERT_DATE,")
				.append("SMS_MSG,")
				.append("LINK_ID,")
				.append("MSG_GRP_ID,")
				.append("DELIVERY_STATUS,")
				.append("SMS_MSG,")
				.append("DEL_FLG,")
				.append("R_MOD_ID,")
				.append("R_MOD_TIME,")
				.append("R_CRE_ID,")
				.append("R_CRE_TIME)")
				.append(" values (?,?,?,?,?,CURDATE(),?,?,?,?,?,?,?,now(),?,now())");
		try {
		int[] a = getJdbcTemplate().batchUpdate(query.toString(),
				new BatchPreparedStatementSetter() {

					@Override
					public void setValues(PreparedStatement pss, int i)
							throws SQLException {

						SMSAlert smsAlert = smsAlertList
								.get(i);

						pss.setInt(1, 1);
						pss.setString(2, smsAlert.getInstId());
						pss.setString(3, smsAlert.getBranchId());
						pss.setString(4, smsAlert.getAcTerm().trim());
						pss.setString(5, smsAlert.getAlertType().trim());
						pss.setString(6, smsAlert.getSmsMessage());
						pss.setString(7, smsAlert.getDelFlag().trim());
						pss.setString(8, smsAlert.getrModId().trim());
						pss.setString(9, smsAlert.getrCreId().trim());
					}

					@Override
					public int getBatchSize() {
						return smsAlertList.size();
					}
				}

		);
		} catch (org.springframework.dao.DuplicateKeyException e) {
			logger.error("Duplicate Entry Exception Occured  "+e.getMessage());
			throw new DuplicateEntryForHolGenException();
		}
		
	}

}
