package com.jaw.core.dao;

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

//CourseSubLinkDAO DAO class
@Repository
public class CourseSubLinkListDAO extends BaseDao implements
		ICourseSubLinkListDAO {
	// Logging
	Logger logger = Logger.getLogger(CourseSubLinkListDAO.class);

	@Override
	public List<CourseSubLinkList> getCourseSubjectLinkList(
			final CourseSubLinkKey courseSubLinkKey)
			throws NoDataFoundException {
		logger.debug("Inside select method");
		logger.debug(" where class for getCourseSubjectLinkList"
				+ courseSubLinkKey);
		StringBuffer sql = new StringBuffer();

		sql.append("select ").append("CRSL_ID,")
				.append("crsl.COURSEMASTER_ID,")
				.append("crsl.RC_SUB_ORDER_WITHIN_SG,")
				.append("crsl.TECHR_REQD,").append("TERM_ID,")
				.append("crsl.SUB_ID,").append("SUB_NAME,").append("SUB_TYPE")
				.append(" from crsl,sbjm ").append(" where")
				.append("   sbjm.inst_id=crsl.inst_id and ")
				.append("  sbjm.branch_id=crsl.branch_id and ")
				.append("   crsl.SUB_ID=sbjm.sub_id and ")
				.append("  sbjm.del_flg=crsl.del_flg and")
				.append("  crsl.INST_ID= ?").append(" and  crsl.BRANCH_ID= ?")
				.append(" and  crsl.COURSEMASTER_ID= ?")
				.append(" and  TERM_ID= ?")
				.append(" and  crsl.DEL_FLG=? order by SUB_TYPE,sub_Name");

		List<CourseSubLinkList> result = getJdbcTemplate().query(
				sql.toString(), new PreparedStatementSetter() {

					@Override
					public void setValues(PreparedStatement pss)
							throws SQLException {
						pss.setString(1, courseSubLinkKey.getInstId().trim());
						pss.setString(2, courseSubLinkKey.getBranchId().trim());
						pss.setString(3, courseSubLinkKey.getCourseMasterId()
								.trim());
						pss.setString(4, courseSubLinkKey.getTermId().trim());
						pss.setString(5, "N");
					}
				}, new CourseSubLinkListRowMapper());

		if (result.size() == 0) {
			throw new NoDataFoundException();
		}
		return result;
	}

}

class CourseSubLinkListRowMapper implements RowMapper<CourseSubLinkList> {

	@Override
	public CourseSubLinkList mapRow(ResultSet rs, int arg1) throws SQLException {

		CourseSubLinkList courseSubLink = new CourseSubLinkList();
		courseSubLink.setCrslId(rs.getString("CRSL_ID"));
		courseSubLink.setCourseMasterId(rs.getString("COURSEMASTER_ID"));
		courseSubLink.setTermId(rs.getString("TERM_ID"));
		courseSubLink.setSubId(rs.getString("SUB_ID"));
		courseSubLink.setSubName(rs.getString("SUB_NAME"));
		courseSubLink.setSubType(rs.getString("SUB_TYPE"));
		courseSubLink.setReportCardOrder(rs.getInt("RC_SUB_ORDER_WITHIN_SG"));
		courseSubLink.setRequiresTeacher(rs.getString("TECHR_REQD"));
		System.out.println("In dao List :" + courseSubLink.toString());
		return courseSubLink;
	}

}
