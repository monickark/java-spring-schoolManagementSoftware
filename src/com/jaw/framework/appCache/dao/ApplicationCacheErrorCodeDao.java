package com.jaw.framework.appCache.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.jaw.common.exceptions.NoDataFoundException;

public class ApplicationCacheErrorCodeDao implements
		IApplicationCacheErrorCodeDao {

	Logger logger = Logger.getLogger(ApplicationCacheErrorCodeDao.class);

	@Autowired
	JdbcTemplate jdbcTemplate;

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public HashMap<String, String> getAllErrorCode()
			throws NoDataFoundException {

		StringBuffer query = new StringBuffer();
		query.append("select ").append("error_code,")
				.append("error_description ").append("from ercd ")
				.append("where ")

				.append("DEL_FLG=?").append("order by ").append("error_code");

		logger.debug("Error code query :" + query);

		HashMap<String, String> searchResult = getJdbcTemplate().query(
				query.toString(), new PreparedStatementSetter() {

					@Override
					public void setValues(java.sql.PreparedStatement ps)
							throws SQLException {
						ps.setString(1, "N");

					}

				}, new ErrorCodeRowMapper());

		if (searchResult.size() == 0) {
			throw new NoDataFoundException();
		}
		return searchResult;

	}

}

class ErrorCodeRowMapper implements ResultSetExtractor<HashMap<String, String>> {

	@Override
	public HashMap<String, String> extractData(ResultSet rs)
			throws SQLException {
		HashMap<String, String> map = new HashMap<String, String>();
		while (rs.next()) {
			String col1 = rs.getString("error_code");
			String col2 = rs.getString("error_description");
			map.put(col1, col2);
		}
		return map;
	}

}
