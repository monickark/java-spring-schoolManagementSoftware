package com.jaw.mark.dao;

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

//MarkSubjectLinkDAO DAO class
@Repository
public class MarksValidationDAO extends BaseDao implements IMarksValidationDAO {
	// Logging
	Logger logger = Logger.getLogger(MarksValidationDAO.class);
	
	@Override
	public List<String> getStuMarksStatusList(
			final MarkValidationKey markValidationKey)
			throws NoDataFoundException {
		logger.debug("Inside select method");
		logger.debug(" where class for getMarkSubjectLinkList" + markValidationKey.toString());
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT status")
				.append(" FROM mkmt where ")
				.append(" inst_id=? and")
				.append(" branch_id=? and")
				.append(" studentgrp_Id=? and")
				.append(" exam_id=? and")
				.append(" del_flg='N' ");
		
		List<String> result = getJdbcTemplate()
				.query(sql.toString(), new PreparedStatementSetter() {
					
					@Override
					public void setValues(PreparedStatement pss)
							throws SQLException {
						
						pss.setString(1, markValidationKey.getInstId());
						pss.setString(2, markValidationKey.getBranchId());
						pss.setString(3, markValidationKey.getStudentGrpId());
						pss.setString(4, markValidationKey.getExamId());
					}
				}, new MarkStatusRowMapper());
		if (result.size() == 0) {
			throw new NoDataFoundException();
		}
		return result;
	}
	
}

class MarkStatusRowMapper implements RowMapper<String> {
	
	@Override
	public String mapRow(ResultSet rs, int arg1) throws SQLException {
		
		String status = rs.getString("status");
		return status;
	}
}
