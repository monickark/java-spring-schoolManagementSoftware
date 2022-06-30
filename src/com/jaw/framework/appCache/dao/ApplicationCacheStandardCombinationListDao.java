package com.jaw.framework.appCache.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;

import com.jaw.common.dao.StandardCombinationList;

public class ApplicationCacheStandardCombinationListDao implements
		IApplicationCacheStandardCombinationListDao {

	@Autowired
	JdbcTemplate jdbcTemplate;

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	// method to select all the files
	@Override
	public List<StandardCombinationList> getStandardCombination() {

		StringBuffer sql = new StringBuffer().append("SELECT ")
				.append("INST_ID, ").append("BRANCH_ID, ").append("STD_ID, ")
				.append("COMBINATION_ID ").append("FROM  ")
				.append("sdsc WHERE ").append("DEL_FLG=? ");
		List<StandardCombinationList> standardCombinationList = jdbcTemplate
				.query(sql.toString(), new PreparedStatementSetter() {

					@Override
					public void setValues(java.sql.PreparedStatement pss)
							throws SQLException {
						pss.setString(1, "N");
					}

				}, new FileMasterRowmapper());

		return standardCombinationList;
	}

	// Inner class to map selected columns into File master object
	class FileMasterRowmapper implements RowMapper {

		@Override
		public StandardCombinationList mapRow(ResultSet rs, int arg1)
				throws SQLException {
			StandardCombinationList stdsec = new StandardCombinationList();
			stdsec.setInstId(rs.getString("INST_ID"));
			stdsec.setBranchId(rs.getString("BRANCH_ID"));
			stdsec.setStandardId(rs.getString("STD_ID"));
			stdsec.setCombinationId(rs.getString("COMBINATION_ID"));
			return stdsec;
		}

	}
}
