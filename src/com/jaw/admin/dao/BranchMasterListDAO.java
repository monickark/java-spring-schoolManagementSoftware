package com.jaw.admin.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.framework.dao.BaseDao;

@Repository
public class BranchMasterListDAO extends BaseDao implements
		IBranchMasterListDAO {
	Logger logger = Logger.getLogger(BranchMasterListDAO.class);

	@Override
	public List<BranchMasterList> getBranchMasterList(final String instId)
			throws NoDataFoundException {
		logger.debug("Inside select method");
		StringBuffer sql = new StringBuffer();
		sql.append("select ").append("DB_TS,")
				.append("BRANCH_ID,")
				.append("BRANCH_NAME,")
				.append("BRANCH_ADD1,")
				.append("BRANCH_ADD2,")
				.append("BRANCH_CITY,")
				.append("BRANCH_STATE,")
				.append("BRANCH_CONTACT1")
				.append(" from brcm ")
				.append("where ")
				.append("DEL_FLG=? ")
				.append(" and")
				.append(" INST_ID = ?")
				.append(" ORDER BY BRANCH_ID asc");

		List<BranchMasterList> selectedListBranchMaster = getJdbcTemplate()
				.query(sql.toString(), new PreparedStatementSetter() {

					@Override
					public void setValues(PreparedStatement pss)
							throws SQLException {

						pss.setString(1, "N");
						pss.setString(2, instId);

					}
				}, new BranchMasterListSelectRowMapper());
		if (selectedListBranchMaster.size() == 0) {
			throw new NoDataFoundException();
		}
		return selectedListBranchMaster;
	}

}

class BranchMasterListSelectRowMapper implements RowMapper<BranchMasterList> {

	@Override
	public BranchMasterList mapRow(ResultSet rs, int arg1) throws SQLException {

		BranchMasterList code = new BranchMasterList();
		code.setDbTs(rs.getInt("DB_TS"));
		code.setBranchId(rs.getString("BRANCH_ID"));
		code.setBranchName(rs.getString("BRANCH_NAME"));
		code.setAddress1(rs.getString("BRANCH_ADD1"));
		code.setAddress2(rs.getString("BRANCH_ADD2"));
		code.setCity(rs.getString("BRANCH_CITY"));
		code.setState(rs.getString("BRANCH_STATE"));
		code.setContact1(rs.getString("BRANCH_CONTACT1"));

		return code;
	}
}
