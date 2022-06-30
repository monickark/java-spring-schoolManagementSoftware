package com.jaw.common.dropdown.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.core.dao.AcademicTermDetails;
import com.jaw.framework.dao.BaseDao;
import com.jaw.framework.sessCache.UserSessionDetails;
import com.jaw.mark.dao.MarkMaster;
@Repository
public class ExamNameListDAO extends BaseDao implements IExamNameListDAO {

	// Logging
	Logger logger = Logger.getLogger(ExamNameListDAO.class);

	@Override
	public List<MarkMaster> selectExamNameList(final UserSessionDetails userSessionDetails,final String studentGrpId) throws NoDataFoundException {
		logger.debug("Inside Exam Tag select method");
		System.out.println("exam list : student "+studentGrpId);
		StringBuffer sql = new StringBuffer();
		


		
		sql.append("select ")
				.append("DISTINCT(CM_CODE),")
				.append("CODE_DESC ")
				.append(" from cocd c,mkmt m ")
				.append("where ")
				.append("c.CM_CODE=m.EXAM_ID")
				.append(" and  c.INST_ID=m.INST_ID")
				.append(" and c.BRANCH_ID=m.BRANCH_ID")
				.append(" and c.DEL_FLG=m.DEL_FLG")
				.append(" and c.INST_ID=?")
				.append(" and c.BRANCH_ID=?")
				.append(" and m.DEL_FLG=?  ")
				.append(" and m.STUDENTGRP_ID=?  ")
				.append(" order by CODE_DESC asc ");

		List<MarkMaster>   markMasters = null;

		markMasters = getJdbcTemplate().query(sql.toString(),
				new PreparedStatementSetter() {

					@Override
					public void setValues(PreparedStatement pss)
							throws SQLException {
						pss.setString(1,userSessionDetails.getInstId() );
						pss.setString(2,userSessionDetails.getBranchId());
						pss.setString(3, "N");
						pss.setString(4,studentGrpId);
					}

				}, new ExamMarkRowmapper());
		if (markMasters.size() == 0) {
			throw new NoDataFoundException();
		}
		return markMasters;
	}

}


class ExamMarkRowmapper implements RowMapper<MarkMaster> {

	@Override
	public MarkMaster mapRow(ResultSet rs, int arg1) throws SQLException {

		MarkMaster markMaster = new MarkMaster();
		markMaster.setExamId((rs.getString("CM_CODE")));
		markMaster.setExamTest(rs.getString("CODE_DESC"));

		return markMaster;
	}
}

