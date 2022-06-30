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
import com.jaw.framework.sessCache.StaffSession;

@Repository
public class StaffSessionDAO extends BaseDao implements IStaffSessionDAO {
	
	// Logging
	Logger logger = Logger.getLogger(StaffSessionDAO.class);
	
	// method to insert into InstiuteMaster Table
	
	@Override
	public StaffSession selectStaffSessionRec(final String staffId, final String instId,
			final String branchId)
			throws SessionCacheNotLoadedException {
		
		logger.debug("Inside select method");
		
		StringBuffer sql = new StringBuffer();
		sql.append("select ")
				.append("STAFF_ID,")
				.append("STAFF_NAME ")
				.append(" from stfm ")
				.append("where ")
				.append("DEL_FLG=? and ")
				.append("STAFF_ID=?")
				.append(" and INST_ID=?")
				.append(" and BRANCH_ID=?");
		logger.debug("select query :" + sql.toString());
		
		StaffSession staffSession = null;
		
		staffSession = getJdbcTemplate().query(sql.toString(),
				new PreparedStatementSetter() {
					
					@Override
					public void setValues(PreparedStatement pss)
							throws SQLException {
						pss.setString(1, "N");
						pss.setString(2, staffId);
						pss.setString(3, instId);
						pss.setString(4, branchId);
						
					}
					
				}, new ResultSetExtractor<StaffSession>() {
					
					@Override
					public StaffSession extractData(ResultSet rs)
							throws SQLException, DataAccessException {
						StaffSession staffSession = new StaffSession();
						if (rs.next()) {
							staffSession.setStaffId(rs.getString("STAFF_ID"));
							staffSession.setStaffName(rs
									.getString("STAFF_NAME"));
						}
						return staffSession;
					}
					
				});
		
		if (staffSession == null) {
			throw new SessionCacheNotLoadedException();
		}
		
		return staffSession;
	}
	
}
