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
import com.jaw.framework.sessCache.NonStaffSession;

@Repository
public class NonStaffSessionDAO extends BaseDao implements INonStaffSessionDAO {
	
	// Logging
	Logger logger = Logger.getLogger(NonStaffSessionDAO.class);
	
	// method to insert into InstiuteMaster Table
	
	@Override
	public NonStaffSession selectNonStaffRec(final String staffId, final String instId,
			final String branchId)
			throws SessionCacheNotLoadedException {
		
		logger.debug("Inside select method");
		
		StringBuffer sql = new StringBuffer();
		sql.append("select ")
				.append("NON_STAFF_ID,")
				.append("STAFF_NAME ")
				.append(" from nsfm ")
				.append("where ")
				.append("DEL_FLG=? and ")
				.append("NON_STAFF_ID=?")
				.append(" and INST_ID=?")
				.append(" and BRANCH_ID=?");
		logger.debug("select query :" + sql.toString());
		
		NonStaffSession nonStaffSession = null;
		
		nonStaffSession = getJdbcTemplate().query(sql.toString(),
				new PreparedStatementSetter() {
					
					@Override
					public void setValues(PreparedStatement pss)
							throws SQLException {
						pss.setString(1, "N");
						pss.setString(2, staffId);
						pss.setString(3, instId);
						pss.setString(4, branchId);
						
					}
					
				}, new ResultSetExtractor<NonStaffSession>() {
					
					@Override
					public NonStaffSession extractData(ResultSet rs)
							throws SQLException, DataAccessException {
						NonStaffSession nonStaffSession = null;
						if (rs.next()) {
							nonStaffSession = new NonStaffSession();
							nonStaffSession.setNonStaffId(rs
									.getString("NON_STAFF_ID"));
							nonStaffSession.setName(rs.getString("STAFF_NAME"));
						}
						return nonStaffSession;
					}
					
				});
		
		if (nonStaffSession == null) {
			throw new SessionCacheNotLoadedException();
		}
		
		return nonStaffSession;
	}
	
}
