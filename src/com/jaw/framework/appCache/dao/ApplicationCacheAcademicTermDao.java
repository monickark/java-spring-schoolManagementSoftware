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

public class ApplicationCacheAcademicTermDao implements
		IApplicationCacheAcademicTermDao {
	Logger logger = Logger.getLogger(ApplicationCacheAcademicTermDao.class);

	@Autowired
	JdbcTemplate jdbcTemplate;

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public List<AcademicTerm> getAcademicTermData() throws NoDataFoundException {
		StringBuffer query = new StringBuffer();
		query.append("select ").append("atdt.inst_id, ")
				.append("atdt.branch_id, ").append("ac_term, ")
				.append("ac_term_sts, ").append("code_desc ")
				.append("from atdt,cocd ").append(" where ")
				.append("atdt.ac_term=cocd.cm_code  ")
				.append("and atdt.inst_id=cocd.inst_id ")
				.append("and atdt.branch_id=cocd.branch_id and ")
				.append("atdt.del_flg=cocd.del_flg and ")
				.append("code_type=? and ").append("atdt.DEL_FLG=? order by ac_term ");

		logger.debug("Tbpm query :" + query);
		List<AcademicTerm> academicTerms = getJdbcTemplate().query(
				query.toString(), new PreparedStatementSetter() {

					@Override
					public void setValues(java.sql.PreparedStatement ps)
							throws SQLException {
						ps.setString(1, "ACTRM");
						ps.setString(2, "N");

					}

				}, new AcademicTermRowMapper());

		if (academicTerms.size() == 0) {
			throw new NoDataFoundException();
		}
		System.out.println("return list :" + academicTerms.size());
		return academicTerms;

	}

	class AcademicTermRowMapper implements RowMapper<AcademicTerm> {
		@Override
		public AcademicTerm mapRow(ResultSet rs, int arg1) throws SQLException {
			AcademicTerm academicTerm = new AcademicTerm();
			academicTerm.setInstId(rs.getString("INST_ID"));
			academicTerm.setBranchId(rs.getString("branch_id"));
			academicTerm.setAcTerm(rs.getString("ac_term"));
			academicTerm.setAcTermStatus(rs.getString("ac_term_sts"));
			academicTerm.setAcName(rs.getString("code_desc"));
			return academicTerm;

		}

	}

}