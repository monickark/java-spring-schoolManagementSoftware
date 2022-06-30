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
public class CourseNameListDAO extends BaseDao implements ICourseNameListDAO {

	// Logging
	Logger logger = Logger.getLogger(BranchListDAO.class);

	
	@Override
	public List<CourseMaster> selectCourseNameList(final UserSessionDetails userSessionDetails) throws NoDataFoundException {

		logger.debug("Inside Course Name Tag select method");

		StringBuffer sql = new StringBuffer();
		sql.append("select ").append("COURSEMASTER_ID,").append("COURSE_NAME ")
				.append(" from crsm ").append("where ").append("DEL_FLG=?  ")
				.append(" and INST_ID=?  ")
				.append(" and BRANCH_ID=?")
				.append(" order by COURSE_NAME asc");
		logger.debug("select query :" + sql.toString());

		List<CourseMaster>courseMaster = null;

		courseMaster = getJdbcTemplate().query(sql.toString(),
				new PreparedStatementSetter() {

					@Override
					public void setValues(PreparedStatement pss)
							throws SQLException {
						pss.setString(1, "N");
						pss.setString(2, userSessionDetails.getInstId());
						pss.setString(3, userSessionDetails.getBranchId());

					}

				}, new CourseMasterRowmapper());
		if (courseMaster.size() == 0) {
			throw new NoDataFoundException();
		}
		return courseMaster;
	}

}



class CourseMasterRowmapper implements RowMapper<CourseMaster> {

	@Override
	public CourseMaster mapRow(ResultSet rs, int arg1) throws SQLException {

		CourseMaster courseMaster = new CourseMaster();
		courseMaster.setCourseMasterId(rs.getString("COURSEMASTER_ID"));
		courseMaster.setCourseName(rs.getString("COURSE_NAME"));

		return courseMaster;
	}
}
