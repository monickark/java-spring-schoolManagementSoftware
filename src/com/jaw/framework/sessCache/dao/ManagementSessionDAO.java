package com.jaw.framework.sessCache.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import com.jaw.common.exceptions.login.SessionCacheNotLoadedException;
import com.jaw.framework.dao.BaseDao;
import com.jaw.framework.sessCache.ManagementSession;

@Repository
public class ManagementSessionDAO extends BaseDao implements
		IManagementSessionDAO {
	
	// Logging
	Logger logger = Logger.getLogger(ManagementSessionDAO.class);
	
	// method to insert into InstiuteMaster Table
	
	@Override
	public ManagementSession selectManagementSessionRec(
			final String managementId, final String instId,
			final String branchId) throws SessionCacheNotLoadedException {
		
		logger.debug("Inside select method");
		
		StringBuffer sql = new StringBuffer();
		sql.append("select ")
				.append("Management_ID,")
				.append("NAME,")
				.append("R_CRE_TIME from mgmt ")
				.append("where ")
				.append("DEL_FLG=? and ")
				.append("Management_ID=?")
				.append(" and INST_ID=?")
				.append(" and BRANCH_ID=?");
		logger.debug("select query :" + sql.toString());
		
		ManagementSession ManagementSession = null;
		
		ManagementSession = getJdbcTemplate().query(sql.toString(),
				new PreparedStatementSetter() {
					
					@Override
					public void setValues(PreparedStatement pss)
							throws SQLException {
						pss.setString(1, "N");
						pss.setString(2, managementId);
						pss.setString(3, instId);
						pss.setString(4, branchId);
						
					}
					
				}, new ResultSetExtractor<ManagementSession>() {
					
					@Override
					public ManagementSession extractData(ResultSet rs)
							throws SQLException, DataAccessException {
						ManagementSession managementSession = null;
						if (rs.next()) {
							managementSession = new ManagementSession();
							managementSession.setManagementId(rs
									.getString("Management_ID"));
							managementSession.setManagementName(rs
									.getString("NAME"));
						}
						return managementSession;
					}
					
				});
		
		if (ManagementSession == null) {
			throw new SessionCacheNotLoadedException();
		}
		return ManagementSession;
	}
}
