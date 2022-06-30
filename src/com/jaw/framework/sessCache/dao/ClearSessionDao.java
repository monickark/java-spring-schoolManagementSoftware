package com.jaw.framework.sessCache.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;

public class ClearSessionDao implements IClearSessionDao {

	Logger logger = Logger.getLogger(ClearSessionDao.class);

	@Autowired
	JdbcTemplate jdbcTemplate;

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	@Autowired
	public void deleteLogin(final String sessionId) {
		StringBuffer query = new StringBuffer();
		query.append("delete from login ").append("where session_id= ?;");
		getJdbcTemplate().update(query.toString(),
				new PreparedStatementSetter() {

					@Override
					public void setValues(PreparedStatement ps)
							throws SQLException {

						ps.setString(1, sessionId);

					}

				});

	}

	@Override
	@Autowired
	public int selectLogin() {
		StringBuffer query = new StringBuffer();
		query.append("select count(*) as count from login");
		int count = getJdbcTemplate().query(query.toString(),
				new ResultSetExtractor<Integer>() {

					@Override
					public Integer extractData(ResultSet rs)
							throws SQLException, DataAccessException {
						if (rs.next()) {

							Integer s = rs.getInt("count");
							return s;
						}
						return 0;
					}
				});

		return count;

	}

	@Override
	@Autowired
	public void truncateLogin() {
		StringBuffer query = new StringBuffer();
		query.append("truncate login");
		getJdbcTemplate().execute(query.toString());

	}

}
