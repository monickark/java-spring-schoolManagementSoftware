package com.jaw.framework.appCache.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;

import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.framework.appCache.dao.ApplicationCachePropertyMaintenanceDao.PropertyMaintenanceRowMapper;

public class ApplicationCacheSMSPropertyDao implements
IApplicationCacheSMSPropertyDao {
Logger logger = Logger
	.getLogger(ApplicationCacheSMSPropertyDao.class);

@Autowired
JdbcTemplate jdbcTemplate;

public JdbcTemplate getJdbcTemplate() {
return jdbcTemplate;
}

public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
this.jdbcTemplate = jdbcTemplate;
}

@Override
public List<SMSProperty> getPrpmCodes() throws NoDataFoundException {
	StringBuffer query = new StringBuffer();
	query.append("select ").append("inst_ID, ").append("BRANCH_ID, ")
			.append("PROPERTY_NAME, ").append("PROPERTY_VALUE ")
			.append("from smsc ").append(" where ").append("DEL_FLG=? ");

	logger.debug("Prpm query :" + query);
	List<SMSProperty> smsPropertys = getJdbcTemplate()
			.query(query.toString(), new PreparedStatementSetter() {

				@Override
				public void setValues(java.sql.PreparedStatement ps)
						throws SQLException {
					ps.setString(1, "N");

				}

			}, new SMSPropertyRowMapper());

	if (smsPropertys.size() == 0) {
		throw new NoDataFoundException();
	}
	return smsPropertys;

}

class SMSPropertyRowMapper implements
RowMapper<SMSProperty> {

@Override
public SMSProperty mapRow(ResultSet rs, int arg1)
	throws SQLException {
	SMSProperty smsProperty = new SMSProperty();
smsProperty.setInstId(rs.getString("inst_ID"));
smsProperty.setBranchId(rs.getString("BRANCH_ID"));
smsProperty.setPropertyName(rs.getString("PROPERTY_NAME"));
smsProperty.setPropertyValue(rs.getString("PROPERTY_VALUE"));

return smsProperty;

}

}

}
