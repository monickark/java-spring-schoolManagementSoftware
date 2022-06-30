package com.jaw.common.dropdown.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.jaw.common.constants.ApplicationConstant;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.core.dao.AcademicTermDetails;
import com.jaw.core.dao.AcademicTermDetailsKey;
import com.jaw.core.dao.CourseMaster;
import com.jaw.framework.dao.BaseDao;
import com.jaw.framework.sessCache.UserSessionDetails;

import com.jaw.common.constants.CommonCodeConstant;
@Repository
public class StudentListDAO extends BaseDao implements IStudentListDAO {

	// Logging
	Logger logger = Logger.getLogger(AcademicTermListDAO.class);

	
	

	@Override
	public Map<String, String> selectStudAdmisList(final UserSessionDetails userSessionDetails,final String stGroup)
			throws NoDataFoundException {
		logger.debug("Inside Academic Term Tag select method");
		logger.debug("Student group :"+stGroup +" user session details :"+userSessionDetails.toString());
		System.out.println("Student group :"+stGroup +" user session details :"+userSessionDetails.toString());

		StringBuffer sql = new StringBuffer();
		sql.append("SELECT student_admis_no,student_name FROM stum ")				
				.append("where ")
				.append("  INST_ID=?")
				.append(" and BRANCH_ID=?")
				.append(" and academic_year=?")
				.append(" and studentgrp_id=?  ")
				.append(" and DEL_FLG=?  ")				
				.append(" order by student_name asc");
		Map<String, String>   academicTermDetails = null;

		academicTermDetails = getJdbcTemplate().query(sql.toString(),
				new PreparedStatementSetter() {

					@Override
					public void setValues(PreparedStatement pss)
							throws SQLException {
						pss.setString(1,userSessionDetails.getInstId() );
						pss.setString(2,userSessionDetails.getBranchId());
						pss.setString(3,userSessionDetails.getCurrAcTerm());
						pss.setString(4,stGroup);
						pss.setString(5, "N");

					}

				}, new StudnetResultSetExtractor());		
		if (academicTermDetails.size() == 0) {
			throw new NoDataFoundException();
		}
		return academicTermDetails;
	}
	
	
	
	
}


class StudnetResultSetExtractor implements ResultSetExtractor<Map<String, String>> {
	@Override
	public Map<String, String> extractData(ResultSet rs) throws SQLException {
		
		Map<String, String> map = new LinkedHashMap<String, String>();
		while (rs.next()) {
			String key = rs.getString("student_admis_no");
			String value = rs.getString("student_name");
			map.put(key, value);
		}
		return map;
	}
}
