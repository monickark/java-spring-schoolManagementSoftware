package com.jaw.core.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.framework.dao.BaseDao;
import com.jaw.login.controller.LoginController;

@Repository
public class StandardCombinationListDao extends BaseDao implements
		IStandardCombinationListDao {
	Logger logger = Logger.getLogger(LoginController.class);

	@Override
	public List<StandardCombinationList> getAllStandardList(
			final String branchId, final String instId)
			throws NoDataFoundException {
		logger.debug("Inside get all list method in StandardCombinationList");
		StringBuffer sql = new StringBuffer();
		sql.append("select ").append("BRANCH_ID,").append("INST_ID,")
				.append("STD_ID,").append("COMBINATION_ID,")
				.append("R_CRE_TIME from stdl where ").append("INST_ID=? and ")
				.append("BRANCH_ID=? and ").append("DEL_FLG=?");

		logger.debug("select query :" + sql.toString());

		List<StandardCombinationList> standardCombinationLists = getJdbcTemplate()
				.query(sql.toString(), new PreparedStatementSetter() {

					@Override
					public void setValues(java.sql.PreparedStatement pss)
							throws SQLException {
						pss.setString(1, instId);
						pss.setString(2, branchId);
						pss.setString(3, "N");
					}
				}, new StandardCombinationListRowMapper());
		if (standardCombinationLists.size() == 0) {
			throw new NoDataFoundException();
		}
		return standardCombinationLists;

	}

	class StandardCombinationListRowMapper implements
			RowMapper<StandardCombinationList> {

		@Override
		public StandardCombinationList mapRow(ResultSet rs, int arg1)
				throws SQLException {
			StandardCombinationList list = new StandardCombinationList();
			list.setBranchId(rs.getString("BRANCH_ID"));
			list.setInstId(rs.getString("INST_ID"));
			list.setStandardId(rs.getString("STD_ID"));
			list.setCombinationId(rs.getString("COMBINATION_ID"));

			return list;
		}
	}
}
