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
import com.jaw.login.controller.LoginController;

@Repository
public class ClassTeacherAndStandardSectionListDao extends BaseDao implements
		IClassTeacherAndStandardSectionListDao {
	Logger logger = Logger.getLogger(LoginController.class);

	@Override
	public List<ClassTeacherListWithStudentGroup> getClassTeachersListWithStandardAndSection(
			final String academicYear, final String instId,
			final String branchid) throws NoDataFoundException {

		logger.debug("Inside get classteacher list method in ClassTeacherAllotmentListDao");

		StringBuffer sql = new StringBuffer();

		sql.append("select ").append("sdsc.sg_id,").append("std_id,")
				.append("combination_id,").append("staff_id,")
				.append("sec_id from sdsc,clta ").append("where ")
				.append("clta.sg_id = sdsc.sg_id and ")
				.append("INST_ID=? and ").append("BRANCH_ID=? and ")
				.append("clta.DEL_FLG=? and ").append("clta.ACADEMIC_YEAR=?");

		logger.debug("select query in ClassTeacherAllotmentListDao:"
				+ sql.toString());

		List<ClassTeacherListWithStudentGroup> classTeacherList = getJdbcTemplate()
				.query(sql.toString(), new PreparedStatementSetter() {

					@Override
					public void setValues(PreparedStatement pss)
							throws SQLException {
						pss.setString(1, instId);
						pss.setString(2, branchid);
						pss.setString(3, "N");
						pss.setString(4, academicYear);

					}

				}, new ClassTeacherAllotmentWithCourse());

		if (classTeacherList.size() == 0) {
			throw new NoDataFoundException();
		}
		return classTeacherList;
	}

	@Override
	public List<ClassTeacherListWithStudentGroup> getStandardSectionListForClassTeacherAllotment(
			final String academicYear, final String instId,
			final String branchId) {

		logger.debug("Inside get list method in StandardSectionDao");

		StringBuffer sql = new StringBuffer();

		sql.append("select ")
				.append("sdsc.sg_id,")
				.append("std_id,")
				.append("combination_id,")
				.append("sec_id from sdsc ")
				.append("where ")
				.append("sdsc.sg_id not in(select sg_id from clta where clta.DEL_FLG=? and  ")
				.append("clta.ACADEMIC_YEAR=?) and ").append("INST_ID=? and ")
				.append("BRANCH_ID=? and ").append("sdsc.DEL_FLG='?");

		logger.debug("select query in StandardSectionDao:" + sql.toString());

		List<ClassTeacherListWithStudentGroup> courseList = getJdbcTemplate()
				.query(sql.toString(), new PreparedStatementSetter() {

					@Override
					public void setValues(PreparedStatement pss)
							throws SQLException {
						pss.setString(1, "N");
						pss.setString(2, academicYear);
						pss.setString(3, instId);
						pss.setString(4, branchId);
						pss.setString(5, "N");

					}

				}, new StandardSectionWithcourse());

		return courseList;
	}

	class ClassTeacherAllotmentWithCourse implements
			RowMapper<ClassTeacherListWithStudentGroup> {

		@Override
		public ClassTeacherListWithStudentGroup mapRow(ResultSet rs, int arg1)
				throws SQLException {

			ClassTeacherListWithStudentGroup stdsec = new ClassTeacherListWithStudentGroup();
			stdsec.setStandard((rs.getString("STD_ID")));
			stdsec.setCombination((rs.getString("COMBINATION_ID")));
			stdsec.setSection(rs.getString("SEC_ID"));
			stdsec.setSgId(rs.getString("SG_ID"));
			stdsec.setStaffId(rs.getString("STAFF_ID"));
			return stdsec;
		}
	}

	class StandardSectionWithcourse implements
			RowMapper<ClassTeacherListWithStudentGroup> {

		@Override
		public ClassTeacherListWithStudentGroup mapRow(ResultSet rs, int arg1)
				throws SQLException {

			ClassTeacherListWithStudentGroup stdsec = new ClassTeacherListWithStudentGroup();
			stdsec.setStandard((rs.getString("STD_ID")));
			stdsec.setCombination((rs.getString("COMBINATION_ID")));
			stdsec.setSection(rs.getString("SEC_ID"));
			stdsec.setSgId(rs.getString("SG_ID"));

			return stdsec;
		}
	}

}
