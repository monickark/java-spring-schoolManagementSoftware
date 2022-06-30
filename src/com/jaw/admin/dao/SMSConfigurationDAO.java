package com.jaw.admin.dao;

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

import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.UpdateFailedException;
import com.jaw.framework.dao.BaseDao;
@Repository
public class SMSConfigurationDAO extends BaseDao implements ISMSConfigurationDAO {
	// Logging
	Logger logger = Logger.getLogger(SMSConfigurationDAO.class);

	@Override
	public void updateSMSConfigurationRec(final SMSConfiguration smsConfigRecord,
			final SMSConfigurationKey smsConfigKey) throws UpdateFailedException {
		logger.debug("Inside update method");
		logger.debug("SMSConfiguration object values :"+ smsConfigRecord.toString());
		logger.debug("SMSConfigurationKey object values :"+ smsConfigKey.toString());
		
		StringBuffer sql = new StringBuffer();
		
		sql.append("update smsc set")
		        .append(" DB_TS= ?,")
		        .append("PROPERTY_VALUE= ?,")
				.append("DEL_FLG= 'N',")
				.append("R_MOD_ID= ?,")
				.append(" R_MOD_TIME=now()")
				.append(" where")
				.append(" DB_TS= ?")
				.append(" and  INST_ID= ?")
				.append(" and  BRANCH_ID= ?")
				.append(" and  PROPERTY_TYPE= ?")			
				.append(" and  PROPERTY_NAME= ?")			
				.append(" and  DEL_FLG='N'");

		

		int updateRecs = getJdbcTemplate().update(sql.toString(),
				new PreparedStatementSetter() {

					@Override
					public void setValues(PreparedStatement ps)
							throws SQLException {
						ps.setInt(1, smsConfigRecord.getDbTs() + 1);
						ps.setString(2, smsConfigRecord.getPropertyValue().trim());
						ps.setString(3, smsConfigRecord.getrModId().trim());
						ps.setInt(4, smsConfigKey.getDbTs());
						ps.setString(5, smsConfigKey.getInstId().trim());
						ps.setString(6, smsConfigKey.getBranchId().trim());
						ps.setString(7, smsConfigKey.getPropertyType().trim());
						ps.setString(8, smsConfigKey.getPropertyName().trim());
						

					}

				});
		if (updateRecs == 0) {
			logger.error("Update Failed Exception Occured");
			throw new UpdateFailedException();

		}

	}

	@Override
	public SMSConfiguration selectSMSConfigurationRec(
			SMSConfigurationKey smsConfigKey) throws NoDataFoundException {
		logger.debug("Inside select method");
		logger.debug("SMSConfigurationKey object values :"+ smsConfigKey.toString());
		List<Object> data = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		
		    sql.append("select ")
		        .append("DB_TS,")
		        .append("INST_ID,")
				.append("BRANCH_ID,")
				.append("PROPERTY_TYPE,")
				.append("PROPERTY_NAME,")
				.append("PROPERTY_VALUE,")
				.append("PROPERTY_DESC,")
				.append("DEL_FLG,")
				.append("R_MOD_ID,")
				.append("R_MOD_TIME,")
				.append("R_CRE_ID,")
				.append("R_CRE_TIME ")
				.append(" from smsc ")
				.append(" where")
				.append("  INST_ID= ?")
				.append(" and  BRANCH_ID= ?")
				.append(" and  PROPERTY_TYPE= ?")			
				.append(" and  PROPERTY_NAME= ?")			
				.append(" and  DEL_FLG=?");
		    data.add(smsConfigKey.getInstId());
			data.add(smsConfigKey.getBranchId());
			data.add(smsConfigKey.getPropertyType());
			data.add(smsConfigKey.getPropertyName());
			data.add("N");
			
			if ((smsConfigKey.getDbTs() !=null)&&(smsConfigKey.getDbTs() !=0)) {
				sql.append(" and DB_TS=?  ");
				logger.debug("Db Ts Value :" + smsConfigKey.getDbTs());
				data.add(smsConfigKey.getDbTs());
			}
			SMSConfiguration selectedListSMSConfiguration = null;
		Object[] array = data.toArray(new Object[data.size()]);
		logger.debug("Where clause conditions size :" + array.length);
		selectedListSMSConfiguration = (SMSConfiguration) getJdbcTemplate()
				.query(sql.toString(), array, new ResultSetExtractor<SMSConfiguration>() {

					@Override
					public SMSConfiguration extractData(ResultSet rs)
							throws SQLException, DataAccessException {
						SMSConfiguration smsConfig = null;
						if (rs.next()) {
						
							smsConfig = new SMSConfiguration();
							smsConfig.setDbTs(rs.getInt("DB_TS"));
							smsConfig.setInstId(rs.getString("INST_ID"));
							smsConfig.setBranchId(rs.getString("BRANCH_ID"));
							smsConfig.setPropertyType(rs.getString("PROPERTY_TYPE"));
							smsConfig.setPropertyName(rs.getString("PROPERTY_NAME"));
							smsConfig.setPropertyValue(rs.getString("PROPERTY_VALUE"));
							smsConfig.setPropertyDesc(rs.getString("PROPERTY_DESC"));
							smsConfig.setDelFlag((rs.getString("DEL_FLG")));
							smsConfig.setrModId(rs.getString("R_MOD_ID"));
							smsConfig.setrModTime(rs.getString("R_MOD_TIME"));
							smsConfig.setrCreId(rs.getString("R_CRE_ID"));
							smsConfig.setrCreTime(rs.getString("R_CRE_TIME"));
							
						}
						return smsConfig;
					}

				});

		if (selectedListSMSConfiguration == null) {
			logger.error("No  record found");
			throw new NoDataFoundException();
		}

		return selectedListSMSConfiguration;
	}

}
