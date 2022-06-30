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

public class ApplicationCacheCommonCodeDao implements
		IApplicationCacheCommonCodeDao {

	Logger logger = Logger.getLogger(ApplicationCacheCommonCodeDao.class);

	@Autowired
	JdbcTemplate jdbcTemplate;

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public List<CommonCode> getAllCommonCodeList() throws NoDataFoundException {
		StringBuffer query = new StringBuffer();
		query.append("select ").append("INST_ID, ").append("BRANCH_ID, ")
				.append("CODE_TYPE, ").append("CM_CODE, ").append("CODE_DESC ")
				.append("from cocd ").append(" where ").append("DEL_FLG=? ")
				.append("order by ").append("code_type ,").append(" RIGHT(cm_code, 4)");

		logger.debug("Cocd query :" + query);
		List<CommonCode> commonCodes = getJdbcTemplate().query(
				query.toString(), new PreparedStatementSetter() {

					@Override
					public void setValues(java.sql.PreparedStatement ps)
							throws SQLException {
						ps.setString(1, "N");

					}

				}, new CommonCodeRowMapper());

		if (commonCodes.size() == 0) {
			throw new NoDataFoundException();
		}
		return commonCodes;

	}

	class CommonCodeRowMapper implements RowMapper<CommonCode> {

		@Override
		public CommonCode mapRow(ResultSet rs, int arg1) throws SQLException {
			CommonCode commonCode = new CommonCode();
			commonCode.setInstId(rs.getString("INST_ID"));
			commonCode.setBranchId(rs.getString("BRANCH_ID"));
			commonCode.setCodeType(rs.getString("CODE_TYPE"));
			commonCode.setCommonCode(rs.getString("CM_CODE"));
			commonCode.setCodeDescription(rs.getString("CODE_DESC"));
			return commonCode;
		}

	}

}
