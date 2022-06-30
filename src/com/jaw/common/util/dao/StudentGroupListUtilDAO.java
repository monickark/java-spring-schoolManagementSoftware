package com.jaw.common.util.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;


import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.core.dao.StudentGroupMaster;
import com.jaw.framework.sessCache.UserSessionDetails;

public class StudentGroupListUtilDAO implements
IStudentGroupListUtilDAO {
Logger logger = Logger.getLogger(StudentGroupListUtilDAO.class);

@Autowired
JdbcTemplate jdbcTemplate;

public JdbcTemplate getJdbcTemplate() {
return jdbcTemplate;
}

public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
this.jdbcTemplate = jdbcTemplate;
}

@Override
public List<StudentGroupMaster> selectStudentGroupList(
		final UserSessionDetails userSessionDetails) throws NoDataFoundException {

	logger.debug("Inside Student Group Tag select method");

	StringBuffer sql = new StringBuffer();
	sql.append("select ").append("STUDENTGRP_ID,").append("STUDENT_GRP ")
			.append(" from stgm ").append("where ").append("DEL_FLG=?  ").append(" and INST_ID=?  ")
			.append(" and BRANCH_ID=?")
			.append(" order by STUDENT_GRP asc");
	logger.debug("select query :" + sql.toString());

	List<StudentGroupMaster> sGrpMaster = null;

	sGrpMaster = getJdbcTemplate().query(sql.toString(),
			new PreparedStatementSetter() {

				@Override
				public void setValues(PreparedStatement pss)
						throws SQLException {
					pss.setString(1, "N");
					pss.setString(2, userSessionDetails.getInstId());
					pss.setString(3, userSessionDetails.getBranchId());

				}

			}, new StudentGroupMasterRowmapper());
	if (sGrpMaster.size() == 0) {
		throw new NoDataFoundException();
	}
	return sGrpMaster;
}

}
class StudentGroupMasterRowmapper implements RowMapper<StudentGroupMaster> {

	@Override
	public StudentGroupMaster mapRow(ResultSet rs, int arg1) throws SQLException {

		StudentGroupMaster sGrpMaster = new StudentGroupMaster();
		sGrpMaster.setStudentGrpId(rs.getString("STUDENTGRP_ID"));
		sGrpMaster.setStudentGrp(rs.getString("STUDENT_GRP"));

		return sGrpMaster;
	}
	
	
}
