package com.jaw.core.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.UpdateFailedException;
import com.jaw.framework.dao.BaseDao;

//CourseClassesListDAO DAO class
@Repository
public class CourseClassesListDAO extends BaseDao implements
		ICourseClassesListDAO {
	// Logging
	Logger logger = Logger.getLogger(CourseClassesListDAO.class);

	@Override
	public List<CourseClassesList> getCourseSubjectLinkList(
			final CourseClassesList courseClassesList)
			throws NoDataFoundException {
		logger.debug("Inside select method");
		logger.debug(" where class for getCourseSubjectLinkList"
				+ courseClassesList.toString());
		StringBuffer sql = new StringBuffer();

		sql.append("select ")
				.append("crsl.inst_id,")
				.append(" crsl.branch_id,")
				.append(" crcl.cc_id,")
				.append("crsl.crsl_id,")
				.append("crsl.requires_lab,")
				.append("crsl.sub_id, ")
				.append("sbjm.sub_name, ")
				.append("crsl.sub_type,")
				.append(" crcl.staff_id, ")
				.append(" crcl.CLS_TYPE, ")
				.append(" crcl.LAB_BATCH, ")
				.append(" crcl.NO_OF_CLSES_PER_WEEK, ")
				.append(" crcl.NO_OF_LAB_CLSES_PER_WEEK, ")
				.append(" crcl.DURATION, ")
				.append(" staff_name")
				.append(" from crsl left join crcl on ")
				.append(" crsl.inst_id = crcl.inst_id ")
				.append(" and crsl.branch_id = crcl.branch_id")
				.append(" and crsl.del_flg = crcl.del_flg")
				.append(" and crsl.crsl_id = crcl.crsl_id ")
				.append(" and crcl.ac_term = ? ")
				.append(" and crcl.del_flg = 'N' and crcl.studentgrp_id = ? ")
				.append(" left join stfm on ")
				.append(" crcl.inst_id = stfm.inst_id ")
				.append("  and crcl.branch_id = stfm.branch_id ")
				.append(" and crcl.staff_id = stfm.staff_id ")
				.append(" and crcl.del_flg = stfm.del_flg ")
				.append("  left join sbjm on crsl.sub_id = sbjm.sub_id and sbjm.del_flg = ?")
				.append(" where crsl.crsl_id").append(" in (")

				.append("select crsl_id from crsl, stgm ")
				.append("where crsl.inst_id = stgm.inst_id and ")
				.append(" crsl.branch_id = stgm.branch_id and ")
				.append("crsl.coursemaster_id = stgm.coursemaster_id and ")
				.append("crsl.term_id = stgm.term_id and ")
				.append("crsl.inst_id = ? and ")
				.append("crsl.branch_id = ? and ")
				.append("crsl.del_flg='N' and ")
				.append("stgm.studentgrp_id = ?)")
				.append("order by crcl.staff_id desc;");

		List<CourseClassesList> result = getJdbcTemplate().query(
				sql.toString(), new PreparedStatementSetter() {

					@Override
					public void setValues(PreparedStatement pss)
							throws SQLException {
						pss.setString(1, courseClassesList.getAcTerm());
						pss.setString(2, courseClassesList.getStudentGrpId());
						pss.setString(3, "N");
						pss.setString(4, courseClassesList.getInstId());
						pss.setString(5, courseClassesList.getBranchId());
						pss.setString(6, courseClassesList.getStudentGrpId());
					}
				}, new CourseClassesListRowMapper());
		logger.debug("list size returned :" + result.size());
		if (result.size() == 0) {
			throw new NoDataFoundException();
		}
		return result;
	}

	@Override
	public void insertCourseClassesList(
			final List<CourseClasses> courseClassesList)
			throws DuplicateEntryException {
		logger.info("inside insertListOfUserLinkRec method in dao");
		logger.debug("Size of courseclass list :" + courseClassesList.size());
		StringBuffer query = new StringBuffer();
		query = query
				.append("insert into crcl ( ")
				.append("DB_TS,")
				.append("INST_ID,")
				.append("BRANCH_ID,")

				.append("CC_ID,")
				.append("AC_TERM,")
				.append("STUDENTGRP_ID,")

				.append("CRSL_ID,")

				.append("SA_NO,")
				.append("CLS_TYPE,")
				.append("LAB_BATCH,")

				.append("STAFF_ID,")
				.append("NO_OF_CLSES_PER_WEEK,")
				.append("NO_OF_LAB_CLSES_PER_WEEK,")
				.append("DURATION,")
				.append("DEL_FLG,")
				.append("R_MOD_ID,")
				.append("R_MOD_TIME,")

				.append("R_CRE_ID,")
				.append("R_CRE_TIME)")
				.append(" values (?,?,?, ?,?,?, ?,?, ?,?,?, ?,?,?, ?,?,now(), ?,now())");
		try {
			getJdbcTemplate().batchUpdate(query.toString(),
					new BatchPreparedStatementSetter() {

						@Override
						public void setValues(PreparedStatement pss, int i)
								throws SQLException {
							CourseClasses courseClasses = courseClassesList
									.get(i);
							logger.debug("To string of the passed :"
									+ courseClasses.toString());

							pss.setInt(1, courseClasses.getDbTs());
							pss.setString(2, courseClasses.getInstId());
							pss.setString(3, courseClasses.getBranchId());
							pss.setString(4, courseClasses.getCcId().trim());
							pss.setString(5, courseClasses.getAcTerm().trim());
							pss.setString(6, courseClasses.getStudentGrpId()
									.trim());

							pss.setString(7, courseClasses.getCrslId().trim());
							pss.setString(8, courseClasses.getSaNo().trim());
							pss.setString(9, courseClasses.getClsType().trim());
							pss.setString(10, courseClasses.getLabBatch()
									.trim());
							pss.setString(11, courseClasses.getStaffId().trim());
							pss.setString(12, courseClasses
									.getNoOfClassesPerWeek().trim());
							pss.setString(13, courseClasses
									.getNoOfLabClassesPerWeek().trim());
							pss.setString(14, courseClasses
									.getDuration().trim());
							pss.setString(15, courseClasses.getDelFlag().trim());
							pss.setString(16, courseClasses.getrModId().trim());
							pss.setString(17, courseClasses.getrCreId().trim());
						}

						@Override
						public int getBatchSize() {
							return courseClassesList.size();
						}
					}

			);
		} catch (DuplicateKeyException duplicateKeyException) {
			throw new DuplicateEntryException();
		}
	}

	@Override
	public List<CourseClasses> getStaffListForTransferProcess(
			final CourseClassesList courseClassesList)
			throws NoDataFoundException {
		logger.debug("Inside select method");
		logger.debug(" where class for getCourseSubjectLinkList"
				+ courseClassesList.toString());
		StringBuffer sql = new StringBuffer();

		sql.append("select ").append(" DB_TS,").append(" INST_ID,")
				.append("BRANCH_ID,")

				.append("CC_ID,").append(" STAFF_ID from crcl")

				.append(" where")

				.append("   INST_ID= ?").append(" and  BRANCH_ID= ?")
				.append(" and  STAFF_ID= ?").append(" and  DEL_FLG='N'");

		List<CourseClasses> result = getJdbcTemplate().query(sql.toString(),
				new PreparedStatementSetter() {

					@Override
					public void setValues(PreparedStatement pss)
							throws SQLException {
						pss.setString(1, courseClassesList.getInstId());
						pss.setString(2, courseClassesList.getBranchId());
						pss.setString(3, courseClassesList.getStaffId());
					}
				}, new StaffListRowMapper());
		logger.debug("list size returned :" + result.size());
		if (result.size() == 0) {
			throw new NoDataFoundException();
		}
		return result;
	}

	@Override
	public void updateStaffDataOnTransfer(
			final List<CourseClasses> courseClassesLists)
			throws UpdateFailedException {

		logger.debug("updateStaffDataOnTransfer   " + courseClassesLists.size());
		StringBuffer query = new StringBuffer();

		query.append("update crcl set").append(" DB_TS= ?,")
				.append("DEL_FLG= 'T',").append("R_MOD_ID= ?,")
				.append(" R_MOD_TIME=now()").append(" where")
				.append(" DB_TS= ?").append(" and  INST_ID= ?")
				.append(" and  BRANCH_ID= ?").append(" and  CC_ID= ?")
				.append(" and  DEL_FLG='N'");

		int[] a = getJdbcTemplate().batchUpdate(query.toString(),
				new BatchPreparedStatementSetter() {

					@Override
					public void setValues(PreparedStatement pss, int i)
							throws SQLException {
						CourseClasses courseClasses = courseClassesLists.get(i);
						System.out.println("update records :"
								+ courseClasses.toString());
						pss.setInt(1, courseClasses.getDbTs() + 1);
						pss.setString(2, courseClasses.getrModId().trim());

						pss.setInt(3, courseClasses.getDbTs());
						pss.setString(4, courseClasses.getInstId().trim());
						pss.setString(5, courseClasses.getBranchId().trim());
						pss.setString(6, courseClasses.getCcId().trim());
					}

					@Override
					public int getBatchSize() {
						return courseClassesLists.size();
					}
				}

		);
		if (a.length == 0) {
			logger.error("update failed Exception Occured  ");
			throw new UpdateFailedException();
		}

	}

}

class StaffListRowMapper implements RowMapper<CourseClasses> {

	@Override
	public CourseClasses mapRow(ResultSet rs, int arg1) throws SQLException {

		CourseClasses courseClasses = new CourseClasses();
		courseClasses.setDbTs(rs.getInt("DB_TS"));
		courseClasses.setInstId(rs.getString("INST_ID"));
		courseClasses.setBranchId(rs.getString("BRANCH_ID"));
		courseClasses.setCcId(rs.getString("CC_ID"));
		courseClasses.setStaffId(rs.getString("STAFF_ID"));
		System.out.println("In dao :" + courseClasses.getStaffId());
		return courseClasses;
	}
}

class CourseClassesListRowMapper implements RowMapper<CourseClassesList> {

	@Override
	public CourseClassesList mapRow(ResultSet rs, int arg1) throws SQLException {

		CourseClassesList courseClassesList = new CourseClassesList();
		courseClassesList.setInstId(rs.getString("INST_ID"));
		courseClassesList.setBranchId(rs.getString("BRANCH_ID"));
		courseClassesList.setCcId(rs.getString("CC_ID"));
		courseClassesList.setCrslId(rs.getString("CRSL_ID"));
		courseClassesList.setSubId(rs.getString("SUB_ID"));
		courseClassesList.setSubName(rs.getString("SUB_NAME"));
		courseClassesList.setClsType(rs.getString("CLS_TYPE"));
		courseClassesList.setLabBatch(rs.getString("LAB_BATCH"));
		courseClassesList.setNoOfClassesPerWeek(rs
				.getString("NO_OF_CLSES_PER_WEEK"));
		courseClassesList.setNoOfLabClassesPerWeek(rs
				.getString("NO_OF_LAB_CLSES_PER_WEEK"));
		courseClassesList.setDuration(rs
				.getString("DURATION"));
		courseClassesList.setStaffId(rs.getString("STAFF_ID"));
		courseClassesList.setStaffName(rs.getString("STAFF_NAME"));
		courseClassesList.setSubType(rs.getString("SUB_TYPE"));
		courseClassesList.setRequiresLab(rs.getString("requires_lab"));
		System.out.println("In dao :" + courseClassesList.getStaffId());
		return courseClassesList;
	}
}
