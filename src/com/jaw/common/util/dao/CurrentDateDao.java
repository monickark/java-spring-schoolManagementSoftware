package com.jaw.common.util.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.jaw.framework.dao.BaseDao;

//Dao class to get DateTime from Database Server
@Repository
public class CurrentDateDao extends BaseDao implements ICurrentDateDao {

	// Logging
	Logger logger = Logger.getLogger(CurrentDateDao.class);

	@Override
	public Timestamp getServerDateTime() {

		logger.debug("inside getServerDateTime method");

		String query = "select now()";
		Timestamp dateTime = (Timestamp) getJdbcTemplate().queryForObject(
				query, new DateTimeRowMapper());

		logger.debug(query);

		return dateTime;
	}

}

class DateTimeRowMapper implements RowMapper<Timestamp> {

	@Override
	public Timestamp mapRow(ResultSet rs, int index) throws SQLException {

		Timestamp dateTime = rs.getTimestamp("now()");
		return dateTime;
	}

}
