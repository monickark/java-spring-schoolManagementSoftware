package com.jaw.common.dropdown.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.framework.dao.BaseDao;
import com.jaw.framework.sessCache.UserSessionDetails;

@Repository
public class SubjectListDAO extends BaseDao implements ISubjectListDAO {

	// Logging
	Logger logger = Logger.getLogger(SubjectListDAO.class);

	@Override
	public Map<String, String> getSubList(final String stdGrpId,
			final String staffId, final UserSessionDetails userSessionDetails)
			throws NoDataFoundException {
		logger.debug("Inside Subject  select for Special Class method");
		StringBuffer sql = new StringBuffer();
		if (staffId == null) {
			sql.append("select ").append(" CRSL_ID,").append("SUB_NAME ")
					.append(" from crsl,stgm,sbjm ").append(" where ")
					.append(" crsl.INST_ID = stgm.INST_ID")
					.append(" and  crsl.INST_ID = sbjm.INST_ID")
					.append(" and crsl.BRANCH_ID = stgm.BRANCH_ID")
					.append(" and crsl.BRANCH_ID = sbjm.BRANCH_ID")
					.append(" and crsl.DEL_FLG = stgm.DEL_FLG")
					.append(" and crsl.DEL_FLG = sbjm.DEL_FLG")
					.append(" and crsl.COURSEMASTER_ID = stgm.COURSEMASTER_ID")
					.append(" and crsl.TERM_ID = stgm.TERM_ID")
					.append(" and crsl.SUB_ID = sbjm.SUB_ID")
					.append(" and crsl.INST_ID =?")
					.append(" and crsl.BRANCH_ID =?")
					.append(" and crsl.DEL_FLG = ?")
					.append(" and STUDENTGRP_ID = ?")
					.append(" order by SUB_NAME asc");
		} else {
			sql.append(
					"select crsl.CRSL_ID, SUB_NAME from crsl,stgm,sbjm,crcl where")
					.append(" crsl.INST_ID = stgm.INST_ID and")
					.append(" crsl.INST_ID = sbjm.INST_ID and")
					.append(" crsl.BRANCH_ID = stgm.BRANCH_ID and ")
					.append(" crsl.BRANCH_ID = sbjm.BRANCH_ID and ")
					.append(" crsl.DEL_FLG = stgm.DEL_FLG and")
					.append(" crsl.DEL_FLG = sbjm.DEL_FLG and")
					.append(" crsl.INST_ID = crcl.INST_ID and ")
					.append(" crsl.BRANCH_ID = crcl.BRANCH_ID and ")
					.append(" crsl.DEL_FLG = crcl.DEL_FLG and ")
					.append(" crsl.CRSL_ID = crcl.CRSL_ID and ")
					.append(" stgm.STUDENTGRP_ID = crcl.STUDENTGRP_ID and ")
					.append(" crsl.COURSEMASTER_ID = stgm.COURSEMASTER_ID and ")
					.append(" crsl.TERM_ID = stgm.TERM_ID and ")
					.append(" crsl.SUB_ID = sbjm.SUB_ID and ")
					.append(" crsl.INST_ID = ? and ")
					.append(" crsl.BRANCH_ID = ? and ")
					.append(" crsl.DEL_FLG = ? and ")
					.append(" stgm.STUDENTGRP_ID = ? and ")
					.append(" crcl.staff_id=? ")
					.append(" order by SUB_NAME asc ; ");
		}
		Map<String, String> subMap1 = null;

		subMap1 = getJdbcTemplate().query(sql.toString(),
				new PreparedStatementSetter() {

					@Override
					public void setValues(PreparedStatement pss)
							throws SQLException {
						pss.setString(1, userSessionDetails.getInstId());
						pss.setString(2, userSessionDetails.getBranchId());
						pss.setString(3, "N");
						pss.setString(4, stdGrpId);
						if (staffId != null) {
							pss.setString(5, staffId);
						}
					}

				}, new SubListResultSetExtractor());
		if (subMap1.size() == 0) {
			throw new NoDataFoundException();

		}
		logger.debug("Map size returned :" + subMap1.size());
		return subMap1;
	}
}

class SubListResultSetExtractor implements
		ResultSetExtractor<Map<String, String>> {
	@Override
	public Map<String, String> extractData(ResultSet rs) throws SQLException {

		Map<String, String> map = new LinkedHashMap<String, String>();
		while (rs.next()) {
			String key = rs.getString("CRSL_ID");
			String value = rs.getString("SUB_NAME");
			map.put(key, value);
		}
		return map;
	}
}
