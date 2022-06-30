package com.jaw.admin.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.UpdateFailedException;
import com.jaw.core.dao.AcademicCalendar;
import com.jaw.core.dao.AcademicCalendarDAO;
import com.jaw.core.dao.HolidayMaintenance;
import com.jaw.core.dao.HolidayMaintenanceKey;
import com.jaw.core.dao.IAcademicCalendarDAO;
import com.jaw.framework.dao.BaseDao;
@Repository
public class SMSConfigurationListDAO extends BaseDao implements ISMSConfigurationListDAO {
	// Logging
	Logger logger = Logger.getLogger(SMSConfigurationListDAO.class);


	@Override
	public List<SMSConfiguration> getSMSConfigurationList(
			SMSConfigurationListKey smsConfigurationListKey)
			throws NoDataFoundException {
		logger.debug("DAO :Inside SMS Config List select method");
		
		List<Object> data = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		    sql.append("select ")
		    .append("DB_TS,")
		    .append("INST_ID,")
				.append("BRANCH_ID,")
				.append("PROPERTY_TYPE,")
				.append("PROPERTY_NAME,")
				.append("PROPERTY_VALUE,")
				.append("PROPERTY_DESC")
				.append(" from smsc ")
				.append(" where")
				.append("  INST_ID= ?")
			.append(" and  DEL_FLG=?");
		    
			data.add(smsConfigurationListKey.getInstId());
			data.add("N");
			
			if ((smsConfigurationListKey.getBranchId() !=null)&&(!smsConfigurationListKey.getBranchId().equals(""))
					) {
				sql.append(" and BRANCH_ID=?  ");
				logger.debug(" BRANCH_ID Value :" + smsConfigurationListKey.getBranchId());
				data.add(smsConfigurationListKey.getBranchId());
			}
			if ((smsConfigurationListKey.getPropertyType() !=null)&&(!smsConfigurationListKey.getPropertyType().equals(""))
					) {
				sql.append(" and PROPERTY_TYPE=?  ");
				logger.debug("PROPERTY_TYPE Value :" + smsConfigurationListKey.getPropertyType());
				data.add(smsConfigurationListKey.getPropertyType());
			}
			sql.append(" order by PROPERTY_NAME"); 
		Object[] array = data.toArray(new Object[data.size()]);
		logger.debug("Where clause conditions size :" + array.length);

		List<SMSConfiguration> selectedListSMSConfig = getJdbcTemplate()
				.query(sql.toString(), array, new SMSConfigurationSelectRowMapper());
		if (selectedListSMSConfig.size() == 0) {
			logger.error("No  record found");
			throw new NoDataFoundException();
		}
		logger.debug("DAO : SMS Configuration List value"+selectedListSMSConfig.size() );
		return selectedListSMSConfig;
		}


	@Override
	public void updateSMSConfigurationDetailsRecs(
			final List<SMSConfiguration> smsConfigurationList,
			final List<SMSConfigurationListKey> smsConfigurationKeyList)
			throws UpdateFailedException {
		logger.debug("Inside SMS Config list update Record");
		logger.debug("SMS Config List Size   "+smsConfigurationList.size());
		logger.debug("SMS Config List Key Size   "+smsConfigurationKeyList.size());
		StringBuffer query = new StringBuffer();
		
		query.append("update smsc set").append(" DB_TS= ?,")
		.append("DEL_FLG= 'N',")
		.append("PROPERTY_VALUE= ?,")
		.append("R_MOD_ID= ?,").append(" R_MOD_TIME=now()")
		.append(" where").append(" DB_TS= ?")
		.append(" and  INST_ID= ?").append(" and  BRANCH_ID= ?")
		.append(" and  DEL_FLG='N'")
		.append(" and  PROPERTY_NAME=?");
		
		int[] a = getJdbcTemplate().batchUpdate(query.toString(),
				new BatchPreparedStatementSetter() {

					@Override
					public void setValues(PreparedStatement pss, int i)
							throws SQLException {
						SMSConfiguration smsConfig = smsConfigurationList
								.get(i);
						SMSConfigurationListKey smsConfigKey = smsConfigurationKeyList
								.get(i);
						pss.setInt(1, smsConfig.getDbTs()+1);
						pss.setString(2, smsConfig.getPropertyValue().trim());
						pss.setString(3, smsConfig.getrModId().trim());

						pss.setInt(4, smsConfigKey.getDbTs());
						pss.setString(5, smsConfigKey.getInstId()
								.trim());
						pss.setString(6, smsConfigKey.getBranchId()
								.trim());
						pss.setString(7, smsConfigKey.getPropertyName()
								.trim());
					}

					@Override
					public int getBatchSize() {
						return smsConfigurationList.size();
					}
				}

		);
		if(a.length==0) {
			logger.error("update failed Exception Occured  ");
			throw new UpdateFailedException();
		}
		
	}

}
class SMSConfigurationSelectRowMapper implements RowMapper<SMSConfiguration> {

	@Override
	public SMSConfiguration mapRow(ResultSet rs, int arg1) throws SQLException {
		SMSConfiguration smsConfig = new SMSConfiguration();	
		smsConfig.setDbTs(rs.getInt("DB_TS"));
	smsConfig.setBranchId(rs.getString("BRANCH_ID"));
	smsConfig.setPropertyType(rs.getString("PROPERTY_TYPE"));
	smsConfig.setPropertyName(rs.getString("PROPERTY_NAME"));
	smsConfig.setPropertyValue(rs.getString("PROPERTY_VALUE"));
	smsConfig.setPropertyDesc(rs.getString("PROPERTY_DESC"));
	smsConfig.setInstId(rs.getString("INST_ID"));
	
	return smsConfig;
	}
	}
