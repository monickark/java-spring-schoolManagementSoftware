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

public class ApplicationCacheTableMaintenanceDao implements
		IApplicationCacheTableMaintenanceDao {
	Logger logger = Logger.getLogger(ApplicationCacheTableMaintenanceDao.class);

	@Autowired
	JdbcTemplate jdbcTemplate;

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public List<TableMaintenance> getTableMaintenanceData()
			throws NoDataFoundException {
		StringBuffer query = new StringBuffer();

		query.append("select ").append("INST_ID, ").append("TABLE_NAME, ")
				.append("AUDIT_REQD, ").append("MANDATORY_AUDIT_REQD ")
				.append("from tbpm ").append(" where ").append("DEL_FLG=? ");

		logger.debug("Tbpm query :" + query);
		List<TableMaintenance> TableMaintenances = getJdbcTemplate().query(
				query.toString(), new PreparedStatementSetter() {

					@Override
					public void setValues(java.sql.PreparedStatement ps)
							throws SQLException {
						ps.setString(1, "N");

					}

				}, new TableMaintenanceRowMapper());

		if (TableMaintenances.size() == 0) {
			throw new NoDataFoundException();
		}
		return TableMaintenances;

	}

	class TableMaintenanceRowMapper implements RowMapper<TableMaintenance> {
		@Override
		public TableMaintenance mapRow(ResultSet rs, int arg1)
				throws SQLException {
			TableMaintenance TableMaintenance = new TableMaintenance();
			TableMaintenance.setInstId(rs.getString("INST_ID"));
			TableMaintenance.setTableName(rs.getString("TABLE_NAME"));
			TableMaintenance.setAuditRequired(rs.getString("AUDIT_REQD"));
			TableMaintenance.setMandatoryAuditRequired(rs
					.getString("MANDATORY_AUDIT_REQD"));

			return TableMaintenance;

		}

	}

}