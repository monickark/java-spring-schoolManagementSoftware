package com.jaw.framework.appCache.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;

import com.jaw.common.dao.StandardSection;

public class ApplicationCacheStandardSectionDao implements
		IApplicationCacheStandardSectionDao {

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
	public List<StandardSection> getStandardSection() {

		StringBuffer sql = new StringBuffer().append("select ")
				.append("INST_ID, ").append("BRANCH_ID, ").append("STD_ID, ")
				.append("COMBINATION_ID, ").append("SEC_ID, ")
				.append("MEDIUM_ID ").append("FROM  ").append("sdsc WHERE ")
				.append("DEL_FLG=? ");
		List<StandardSection> commDetailsResult = jdbcTemplate.query(
				sql.toString(), new PreparedStatementSetter() {
					@Override
					public void setValues(java.sql.PreparedStatement pss)
							throws SQLException {
						pss.setString(1, "N");
					}

				}, new StandardSectionRowMapper());

		return commDetailsResult;
	}

	// Inner class to map selected columns into File master object
	class StandardSectionRowMapper implements RowMapper<StandardSection> {

		@Override
		public StandardSection mapRow(ResultSet rs, int arg1)
				throws SQLException {
			StandardSection stdsec = new StandardSection();
			stdsec.setInstId(rs.getString("INST_ID"));
			stdsec.setBranchId(rs.getString("BRANCH_ID"));
			stdsec.setStandard(rs.getString("STD_ID"));
			stdsec.setCombination(rs.getString("COMBINATION_ID"));
			stdsec.setSection(rs.getString("SEC_ID"));
			stdsec.setMedium(rs.getString("MEDIUM_ID"));
			return stdsec;
		}

	}
}
