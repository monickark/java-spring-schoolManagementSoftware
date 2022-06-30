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
import com.jaw.core.dao.CourseSubLink;
import com.jaw.framework.dao.BaseDao;
import com.jaw.framework.sessCache.UserSessionDetails;
import com.jaw.staff.dao.StaffDetails;
@Repository
public class AllSubjectListDAO extends BaseDao implements IAllSubjectListDAO{
	// Logging
		Logger logger = Logger.getLogger(AllSubjectListDAO.class);
	@Override
	public List<CourseSubLink> getAllSubjectList(
			final UserSessionDetails userSessionDetails) throws NoDataFoundException {
		logger.debug("Inside select subjects list method for Dropdown");

		StringBuffer sql = new StringBuffer();	
	
		sql.append("select ")
		.append("SUB_ID,")
		.append("SUB_NAME ")
		.append(" from sbjm ")
		.append("where ")
		.append("  DEL_FLG=?  ")	
		.append(" and INST_ID=?  ")
		.append(" and BRANCH_ID=?")
		.append(" order by SUB_NAME asc");
		logger.debug( "select query :" + sql.toString());

		List<CourseSubLink> courseSubLinks = null;

		courseSubLinks = getJdbcTemplate().query(sql.toString(),
				new PreparedStatementSetter() {

					@Override
					public void setValues(PreparedStatement pss)
							throws SQLException {
						pss.setString(1, "N");
						pss.setString(2, userSessionDetails.getInstId());
						pss.setString(3, userSessionDetails.getBranchId());

					}

				}, new AllSubjectsRowmapper());
		if (courseSubLinks.size() == 0) {
			throw new NoDataFoundException();
		}
		return courseSubLinks;
	}

	
	
}

class AllSubjectsRowmapper implements RowMapper<CourseSubLink> {

	@Override
	public CourseSubLink mapRow(ResultSet rs, int arg1) throws SQLException {		
		CourseSubLink courseSubLink = new CourseSubLink();
		courseSubLink.setSubId(rs.getString("SUB_ID"));
		courseSubLink.setSubType(rs.getString("SUB_NAME"));

		return courseSubLink;
	}
}

