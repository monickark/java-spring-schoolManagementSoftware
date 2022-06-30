package com.jaw.common.dropdown.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.jaw.admin.dao.BranchMaster;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.core.dao.CourseMaster;
import com.jaw.framework.dao.BaseDao;
import com.jaw.framework.sessCache.UserSessionDetails;

@Repository
public class BranchListDAO extends BaseDao implements IBranchListDAO {

	// Logging
	Logger logger = Logger.getLogger(BranchListDAO.class);

	@Override
	public List<BranchMaster> selectBranchList(final UserSessionDetails userSessionDetails) throws NoDataFoundException {

		logger.debug("Inside select method");

		StringBuffer sql = new StringBuffer();
		sql.append("select ").append("BRANCH_ID,").append("BRANCH_NAME ")
				.append(" from brcm ").append("where ").append("DEL_FLG=?  ")
				.append(" and INST_ID=?  ")				
				.append(" order by BRANCH_NAME asc");
		logger.debug("select query :" + sql.toString());

		List<BranchMaster> branchMaster = null;

		branchMaster = getJdbcTemplate().query(sql.toString(),
				new PreparedStatementSetter() {

					@Override
					public void setValues(PreparedStatement pss)
							throws SQLException {
						pss.setString(1, "N");
						pss.setString(2, userSessionDetails.getInstId());

					}

				}, new BranchMasterRowmapper());
		if (branchMaster.size() == 0) {
			throw new NoDataFoundException();
		}
		return branchMaster;
	}

	
	
}

class BranchMasterRowmapper implements RowMapper<BranchMaster> {

	@Override
	public BranchMaster mapRow(ResultSet rs, int arg1) throws SQLException {

		BranchMaster branchMaster = new BranchMaster();
		branchMaster.setBranchId(rs.getString("BRANCH_ID"));
		branchMaster.setBranchName(rs.getString("BRANCH_NAME"));

		return branchMaster;
	}
}

