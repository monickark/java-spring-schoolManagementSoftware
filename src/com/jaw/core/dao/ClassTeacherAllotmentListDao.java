package com.jaw.core.dao;

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

import com.jaw.common.constants.CommonCodeConstant;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.framework.dao.BaseDao;
import com.jaw.login.controller.LoginController;

@Repository
public class ClassTeacherAllotmentListDao extends BaseDao implements
		IClassTeacherAllotmentListDao {
	Logger logger = Logger.getLogger(LoginController.class);

	@Override
	public List<AllottedClassTeachers> getClassTeachersListForCollege(final String acTerm,
			final String courseId, final String instId, final String branchid)
			throws NoDataFoundException {

		logger.debug("Inside get classteacher list method in ClassTeacherAllotmentListDao"
				+ acTerm + " " + instId + " " + branchid);

		StringBuffer sql = new StringBuffer();
		
			sql.append("select ").append("clta.staff_id,").append("staff_name, ")
			.append("clta.studentgrp_id, ")
			.append("stgm.student_grp ,student_batch_id,code_desc ")
			.append("FROM clta,stfm,stgm,cocd where ")
			.append("clta.inst_id=stgm.inst_id and ")
			.append("clta.branch_id = stgm.branch_id and  ")
			.append("clta.inst_id=cocd.inst_id and ")
			.append("clta.branch_id = cocd.branch_id and  ")
			.append("clta.studentgrp_id = stgm.studentgrp_id and ")
			.append("clta.del_flg = stgm.del_flg and ")
			.append("clta.inst_id=stfm.inst_id and ")
			.append("clta.branch_id = stfm.branch_id and  ")
			.append("clta.staff_id = stfm.staff_id and ")
			.append("clta.del_flg = stfm.del_flg and ")
			.append("clta.del_flg = cocd.del_flg and ")
			.append("cocd.cm_code = student_batch_id and ")
			.append("clta.INST_ID=? and ").append("clta.BRANCH_ID=? and ")
			.append("clta.DEL_FLG=? and ").append("clta.ac_term=?");
		
		
		if ((courseId != null) && (courseId != "")) {
			sql.append(" and coursemaster_id=?");

		}

		logger.debug("select query in ClassTeacherAllotmentListDao:"
				+ sql.toString());

		List<AllottedClassTeachers> classTeacherList = getJdbcTemplate().query(
				sql.toString(), new PreparedStatementSetter() {

					@Override
					public void setValues(PreparedStatement pss)
							throws SQLException {
						pss.setString(1, instId);
						pss.setString(2, branchid);
						pss.setString(3, "N");
						pss.setString(4, acTerm);
						if ((courseId != null) && (courseId != "")) {
							pss.setString(5, courseId);

						}
					}

				}, new AllottedClassTeachersRowMapper());

		if (classTeacherList.size()==0) {
			throw new NoDataFoundException();
		}
		
		System.out.println("List size :"+classTeacherList.size());
		return classTeacherList;
	}
	
	@Override
	public List<AllottedClassTeachers> getClassTeachersListForSchool(final String acTerm,
			final String courseId, final String instId, final String branchid)
			throws NoDataFoundException {

		logger.debug("Inside get classteacher list method in ClassTeacherAllotmentListDao"
				+ acTerm + " " + instId + " " + branchid);

		StringBuffer sql = new StringBuffer();
	
			sql.append("select ").append("clta.staff_id,").append("staff_name, ")
			.append("clta.studentgrp_id, ")
			.append("stgm.student_grp  ")
			.append("FROM clta,stfm,stgm where ")
			.append("clta.inst_id=stgm.inst_id and ")
			.append("clta.branch_id = stgm.branch_id and  ")
			.append("clta.studentgrp_id = stgm.studentgrp_id and ")
			.append("clta.del_flg = stgm.del_flg and ")
			.append("clta.inst_id=stfm.inst_id and ")
			.append("clta.branch_id = stfm.branch_id and  ")
			.append("clta.staff_id = stfm.staff_id and ")
			.append("clta.del_flg = stfm.del_flg and ")
			.append("clta.INST_ID=? and ").append("clta.BRANCH_ID=? and ")
			.append("clta.DEL_FLG=? and ").append("clta.ac_term=?");
		
		
		if ((courseId != null) && (courseId != "")) {
			sql.append(" and coursemaster_id=?");

		}

		logger.debug("select query in ClassTeacherAllotmentListDao:"
				+ sql.toString());

		List<AllottedClassTeachers> classTeacherList = getJdbcTemplate().query(
				sql.toString(), new PreparedStatementSetter() {

					@Override
					public void setValues(PreparedStatement pss)
							throws SQLException {
						pss.setString(1, instId);
						pss.setString(2, branchid);
						pss.setString(3, "N");
						pss.setString(4, acTerm);
						if ((courseId != null) && (courseId != "")) {
							pss.setString(5, courseId);

						}
					}

				}, new AllottedClassTeachersRowMapperForSchool());

		if (classTeacherList.size()==0) {
			throw new NoDataFoundException();
		}
		
		System.out.println("List size :"+classTeacherList.size());
		return classTeacherList;
	}

	@Override
	public Map<String, String> getStudentBatchList(final String stGroup,
			final String instId, final String branchid,final String acTerm)
			throws NoDataFoundException {

		logger.debug("Inside getStudentBatchList list method in ClassTeacherAllotmentListDao"
				+ stGroup + " " + instId + " " + branchid);

		StringBuffer sql = new StringBuffer();
		sql.append("select ").append(" cm_code , code_desc FROM cocd ")
				.append(" where code_type=? ").append(" and inst_id=? ")
				.append(" and branch_id=? ").append(" and del_flg=? ")
				.append(" and cm_code not in( ")
				.append(" select student_batch_id from clta where")
				.append(" inst_id=? ").append(" and branch_id=? ")
				.append(" and studentgrp_id=? ").append(" and ac_term=? ").append(" and del_flg=?) ");

		logger.debug("select query in ClassTeacherAllotmentListDao:"
				+ sql.toString());

		Map<String, String> classTeacherList = getJdbcTemplate().query(
				sql.toString(), new PreparedStatementSetter() {

					@Override
					public void setValues(PreparedStatement pss)
							throws SQLException {
						pss.setString(1, CommonCodeConstant.STU_BATCH);
						pss.setString(2, instId);
						pss.setString(3, branchid);
						pss.setString(4, "N");
						pss.setString(5, instId);
						pss.setString(6, branchid);
						pss.setString(7, stGroup);
						pss.setString(8, acTerm);
						pss.setString(9, "N");

					}

				}, new StudentBatchExtractor());

		if (classTeacherList.size() == 0) {
			throw new NoDataFoundException();
		}
		return classTeacherList;
	}

	

	class StudentBatchExtractor implements
			ResultSetExtractor<Map<String, String>> {
		@Override
		public Map<String, String> extractData(ResultSet rs)
				throws SQLException {

			Map<String, String> map = new LinkedHashMap<String, String>();
			while (rs.next()) {
				String key = (rs.getString("cm_code"));
				String value = (rs.getString("code_desc"));

				map.put(key, value);
			}
			return map;
		}
	}
	
	class AllottedClassTeachersRowMapper implements RowMapper<AllottedClassTeachers> {

		@Override
		public AllottedClassTeachers mapRow(ResultSet rs, int arg1) throws SQLException {
			AllottedClassTeachers classTeacher = new AllottedClassTeachers();
				classTeacher.setStGroupId(rs.getString("STUDENTGRP_ID"));
				classTeacher.setStBatchId((rs.getString("STUDENT_BATCH_ID")));
				classTeacher.setStaffId((rs.getString("STAFF_ID")));
				classTeacher.setStaffName(rs.getString("staff_name"));
				classTeacher.setStGroupName((rs.getString("student_grp")));
				classTeacher.setStBatchName((rs.getString("code_desc")));
			
			return classTeacher;
		}
	}
	class AllottedClassTeachersRowMapperForSchool implements RowMapper<AllottedClassTeachers> {

		@Override
		public AllottedClassTeachers mapRow(ResultSet rs, int arg1) throws SQLException {
			AllottedClassTeachers classTeacher = new AllottedClassTeachers();
				classTeacher.setStGroupId(rs.getString("STUDENTGRP_ID"));
				classTeacher.setStaffId((rs.getString("STAFF_ID")));
				classTeacher.setStaffName(rs.getString("staff_name"));
				classTeacher.setStGroupName((rs.getString("student_grp")));
			
			return classTeacher;
		}
	}

}
