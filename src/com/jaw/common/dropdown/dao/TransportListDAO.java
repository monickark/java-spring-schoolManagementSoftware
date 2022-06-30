package com.jaw.common.dropdown.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.framework.dao.BaseDao;
import com.jaw.framework.sessCache.UserSessionDetails;
@Repository
public class TransportListDAO extends BaseDao implements ITransportListDAO {
	// Logging
		Logger logger = Logger.getLogger(TransportListDAO.class);
	@Override
	public Map<String, String> selectPickupPoint(
			final UserSessionDetails userSessionDetails) throws NoDataFoundException {

		logger.debug("Going to get list of term from stgm and cocd");
		
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT pickup_point_id,pickup_point FROM trsm ")				
				.append("where ")
				.append("  INST_ID=?")
				.append(" and BRANCH_ID=?")
				.append(" and academic_year=?")
				.append(" and DEL_FLG=?  ")				
				.append(" order by pickup_point asc");
		logger.debug("select query :" + sql.toString());

	Map<String,String> termMap= null;
	termMap = getJdbcTemplate().query(sql.toString(),
				new PreparedStatementSetter() {

					@Override
					public void setValues(PreparedStatement pss)
							throws SQLException {
						pss.setString(1, userSessionDetails.getInstId());
						pss.setString(2, userSessionDetails.getBranchId());
						pss.setString(3, userSessionDetails.getCurrAcTerm());
						pss.setString(4, "N");

					}

				}, new PickupPointRowMapper());
		if (termMap == null) {
			throw new NoDataFoundException();
		}
		return termMap;
	}

	
class PickupPointRowMapper implements ResultSetExtractor<Map<String, String>> {
	
	@Override
	public Map<String, String> extractData(ResultSet rs) throws SQLException,
			DataAccessException {
		
		Map<String,String> pickPointList = new LinkedHashMap<String,String>();
		while(rs.next()){	
			
			pickPointList.put(rs.getString("pickup_point_id"), rs.getString("pickup_point"));
		}
		System.out.println("pickPointList :"+pickPointList);
		return pickPointList;
	}
}
}	
