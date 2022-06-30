package com.jaw.core.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.UpdateFailedException;
import com.jaw.framework.dao.BaseDao;
import com.jaw.login.controller.LoginController;

@Repository
public class StandardSectionListDao extends BaseDao implements
		IStandardSectionListDao {
	Logger logger = Logger.getLogger(LoginController.class);

	@Override
	public List<StandardSection> getSection(final StandardSection standard)
			throws NoDataFoundException {

		logger.debug("Inside get list method in StandardSectionDao");

		StringBuffer sql = new StringBuffer();
		sql.append("select ").append("STD_ID,").append("COMBINATION_ID,")
				.append("SEC_ID,").append("SG_ID,").append("MEDIUM_ID,")
				.append("R_CRE_TIME from sdsc where ").append("INST_ID=? and ")
				.append("BRANCH_ID=? and ").append("STD_ID=? and ")
				.append("COMBINATION_ID=? and ").append("DEL_FLG=? ");

		logger.debug("select query in StandardSectionDao:" + sql.toString());

		List<StandardSection> stdsec = getJdbcTemplate().query(sql.toString(),
				new PreparedStatementSetter() {

					@Override
					public void setValues(java.sql.PreparedStatement pss)
							throws SQLException {
						pss.setString(1, standard.getInstId());
						pss.setString(2, standard.getBranchId());
						pss.setString(3, standard.getStandard());
						pss.setString(4, standard.getCombination());
						pss.setString(5, standard.getDelFlag());
					}
				}, new StandardSectionRowMapper());
		if (stdsec.size() == 0) {
			throw new NoDataFoundException();
		}

		return stdsec;

	}

	@Override
	public List<StandardSection> getStandardSectionList(final String instId,
			final String branchId) {

		logger.debug("Inside get list method in StandardSectionDao");

		StringBuffer sql = new StringBuffer();
		sql.append("select ").append("INST_ID,").append("BRANCH_ID,")
				.append("STD_ID,").append("COMBINATION_ID,").append("SEC_ID,")
				.append("SG_ID,").append("MEDIUM_ID,")
				.append("R_CRE_TIME from sdsc where ").append("INST_ID=? and ")
				.append("BRANCH_ID=? and ").append("DEL_FLG='N'  ");

		logger.debug("select query in StandardSectionDao:" + sql.toString());

		List<StandardSection> stdSec = getJdbcTemplate().query(sql.toString(),
				new PreparedStatementSetter() {

					@Override
					public void setValues(java.sql.PreparedStatement pss)
							throws SQLException {
						pss.setString(1, instId);
						pss.setString(2, branchId);
					}
				}, new StandardSectionRowMapper());

		return stdSec;

	}

	@Override
	public void deleteStandardSection(final String instId, final String branchId)
			throws UpdateFailedException {

		logger.debug("Inside update method");
		StringBuffer sql = new StringBuffer();
		sql.append("DELETE FROM sdsc WHERE ").append("INST_ID=? and ")
				.append("BRANCH_ID=? ");

		logger.debug("delete query in FeeMasterListDao :" + sql.toString());

		int affectedRow = getJdbcTemplate().update(sql.toString(),
				new PreparedStatementSetter() {

					@Override
					public void setValues(java.sql.PreparedStatement pss)
							throws SQLException {
						pss.setString(1, instId);
						pss.setString(2, branchId);
					}
				});
		if (affectedRow == 0) {
			throw new UpdateFailedException();
		}

	}

	@Override
	public void deleteStandardSectionBasedOnMedium(final String instId,
			final String branchId, final String medium) {

		logger.debug("Inside update method");

		StringBuffer sql = new StringBuffer();
		sql.append("DELETE FROM sdsc WHERE ").append("INST_ID=? and ")
				.append("BRANCH_ID=? and ").append("MEDIUM_ID=? ");

		logger.debug("delete query in FeeMasterListDao :" + sql.toString());
		int affectedRow = getJdbcTemplate().update(sql.toString(),
				new PreparedStatementSetter() {

					@Override
					public void setValues(java.sql.PreparedStatement pss)
							throws SQLException {
						pss.setString(1, instId);
						pss.setString(2, branchId);
						pss.setString(3, medium);
					}
				});

	}

}

class StandardSectionRowMapper implements RowMapper<StandardSection> {

	@Override
	public StandardSection mapRow(ResultSet rs, int arg1) throws SQLException {

		StandardSection stdsec = new StandardSection();
		stdsec.setStandard((rs.getString("STD_ID")));
		stdsec.setCombination((rs.getString("COMBINATION_ID")));
		stdsec.setSection(rs.getString("SEC_ID"));
		stdsec.setSgId(rs.getString("SG_ID"));
		stdsec.setMedium(rs.getString("MEDIUM_ID"));
		return stdsec;
	}
}
