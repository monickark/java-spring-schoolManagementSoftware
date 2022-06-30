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

public class ApplicationCachePropertyMaintenanceDao implements
		IApplicationCachePropertyMaintenanceDao {
	Logger logger = Logger
			.getLogger(ApplicationCachePropertyMaintenanceDao.class);

	@Autowired
	JdbcTemplate jdbcTemplate;

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public List<PropertyMaintenance> getPrpmCodes() throws NoDataFoundException {
		StringBuffer query = new StringBuffer();
		query.append("select ").append("inst_ID, ").append("BRANCH_ID, ")
				.append("PROPERTY_ID, ").append("PROPERTY_VALUE ")
				.append("from prpm ").append(" where ").append("DEL_FLG=? ");

		logger.debug("Prpm query :" + query);
		List<PropertyMaintenance> propertyMaintenances = getJdbcTemplate()
				.query(query.toString(), new PreparedStatementSetter() {

					@Override
					public void setValues(java.sql.PreparedStatement ps)
							throws SQLException {
						ps.setString(1, "N");

					}

				}, new PropertyMaintenanceRowMapper());

		if (propertyMaintenances.size() == 0) {
			throw new NoDataFoundException();
		}
		return propertyMaintenances;

	}

	class PropertyMaintenanceRowMapper implements
			RowMapper<PropertyMaintenance> {

		@Override
		public PropertyMaintenance mapRow(ResultSet rs, int arg1)
				throws SQLException {
			PropertyMaintenance propertyMaintenance = new PropertyMaintenance();
			propertyMaintenance.setInstId(rs.getString("inst_ID"));
			propertyMaintenance.setBranchId(rs.getString("BRANCH_ID"));
			propertyMaintenance.setPropertyId(rs.getString("PROPERTY_ID"));
			propertyMaintenance
					.setPropertyValue(rs.getString("PROPERTY_VALUE"));

			return propertyMaintenance;

		}

	}

}