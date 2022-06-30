package com.jaw.core.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.jaw.common.exceptions.DeleteFailedException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.UpdateFailedException;
import com.jaw.framework.dao.BaseDao;

@Repository
public class CourseClassesDAO extends BaseDao implements ICourseClassesDAO {
	// Logging
	Logger logger = Logger.getLogger(CourseClassesDAO.class);

	@Override
	public void insertCourseClassesRec(final CourseClasses courseClasses)
			throws DuplicateEntryException {
		logger.debug("Inside insert method");
		logger.debug("CourseClasses object values :" + courseClasses.toString());
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
				.append("DURATION ,")

				.append("DEL_FLG,")
				.append("R_MOD_ID,")
				.append("R_MOD_TIME,")

				.append("R_CRE_ID,")
				.append("R_CRE_TIME)")

				.append(" values (?,?,? ,?,?,? ,?,?,?,? ,? ,?,?,?, ?,?,now(), ?,now())");

		try {
			getJdbcTemplate().update(query.toString(),
					new PreparedStatementSetter() {

						@Override
						public void setValues(PreparedStatement pss)
								throws SQLException {

							pss.setInt(1, courseClasses.getDbTs());
							pss.setString(2, courseClasses.getInstId());
							pss.setString(3, courseClasses.getBranchId());

							pss.setString(4, courseClasses.getCcId().trim());
							pss.setString(5, courseClasses.getAcTerm().trim());
							pss.setString(6, courseClasses.getStudentGrpId()
									.trim());
							pss.setString(7, courseClasses.getCrslId());

							pss.setString(8, courseClasses.getSaNo());
							pss.setString(9, courseClasses.getClsType().trim());
							pss.setString(10, courseClasses.getLabBatch()
									.trim());

							pss.setString(11, courseClasses.getStaffId().trim());

							pss.setString(12, courseClasses
									.getNoOfClassesPerWeek().trim());

							pss.setString(13, courseClasses
									.getNoOfLabClassesPerWeek().trim());
							pss.setString(14, courseClasses.getDuration()
									.trim());
							pss.setString(15, "N");
							pss.setString(16, courseClasses.getrModId().trim());
							pss.setString(17, courseClasses.getrCreId().trim());

						}

					});
		} catch (org.springframework.dao.DuplicateKeyException e) {
			throw new DuplicateEntryException();
		}

	}

	@Override
	public void updateCourseClassesRec(final CourseClasses courseClasses,
			final CourseClassesKey courseClasseskey)
			throws UpdateFailedException {
		logger.debug("Inside update method");
		logger.debug("Course Classes object values :"
				+ courseClasses.toString());
		logger.debug("Course Classes key Details object values :"
				+ courseClasseskey.toString());
		StringBuffer sql = new StringBuffer();
		sql.append("update crcl set ").append("DB_TS=?,").append("INST_ID=?,")
				.append("BRANCH_ID=?,")

				.append("CC_ID=?,").append("AC_TERM=?,")
				.append("STUDENTGRP_ID=?,")

				.append("CRSL_ID=?,")

				.append("SA_NO=?,").append("CLS_TYPE=?,")
				.append("LAB_BATCH=?,")

				.append("STAFF_ID=?,").append("NO_OF_CLSES_PER_WEEK=?,")
				.append("NO_OF_LAB_CLSES_PER_WEEK=?,").append("DURATION =? ,")

				.append("DEL_FLG=?,").append("R_MOD_ID=?,")
				.append("R_MOD_TIME =now() ")

				.append(" where").append(" DB_TS= ?")
				.append(" and  INST_ID= ?").append(" and  BRANCH_ID= ?")
				.append(" and  CC_ID= ?").append(" and  DEL_FLG='N'");

		int updateRecs = getJdbcTemplate().update(sql.toString(),
				new PreparedStatementSetter() {

					@Override
					public void setValues(PreparedStatement pss)
							throws SQLException {
						pss.setInt(1, courseClasseskey.getDbTs() + 1);
						pss.setString(2, courseClasses.getInstId());
						pss.setString(3, courseClasses.getBranchId());

						pss.setString(4, courseClasses.getCcId().trim());
						pss.setString(5, courseClasses.getAcTerm().trim());
						pss.setString(6, courseClasses.getStudentGrpId().trim());

						pss.setString(7, courseClasses.getCrslId());

						pss.setString(8, courseClasses.getSaNo());
						pss.setString(9, courseClasses.getClsType().trim());
						pss.setString(10, courseClasses.getLabBatch().trim());

						pss.setString(11, courseClasses.getStaffId().trim());
						pss.setString(12, courseClasses.getNoOfClassesPerWeek()
								.trim());
						pss.setString(13, courseClasses
								.getNoOfLabClassesPerWeek().trim());
						pss.setString(14, courseClasses
								.getDuration().trim());

						pss.setString(15, courseClasses.getDelFlag());
						pss.setString(16, courseClasses.getrModId().trim());
						pss.setInt(17, courseClasseskey.getDbTs());

						pss.setString(18, courseClasseskey.getInstId().trim());
						pss.setString(19, courseClasseskey.getBranchId().trim());
						pss.setString(20, courseClasseskey.getCcId().trim());

					}

				});
		if (updateRecs == 0) {
			throw new UpdateFailedException();

		}

	}

	@Override
	public void deleteCourseClassesRec(final CourseClasses courseClasses,
			final CourseClassesKey courseClasseskey)
			throws DeleteFailedException {
		logger.debug("Inside delete method");
		logger.debug("Course Classes object values :"
				+ courseClasses.toString());
		logger.debug("Course Classes Key object values :"
				+ courseClasseskey.toString());

		StringBuffer sql = new StringBuffer();
		sql.append("update crcl set").append(" DB_TS= ?,")
				.append("DEL_FLG= 'Y',").append("R_MOD_ID= ?,")
				.append(" R_MOD_TIME=now()").append(" where")
				.append(" DB_TS= ?").append(" and INST_ID= ?")
				.append(" and  BRANCH_ID= ?").append(" and  CC_ID= ?")
				.append(" and  DEL_FLG='N'");

		int deletedRecs = getJdbcTemplate().update(sql.toString(),
				new PreparedStatementSetter() {

					@Override
					public void setValues(PreparedStatement ps)
							throws SQLException {
						ps.setInt(1, courseClasses.getDbTs() + 1);
						ps.setString(2, courseClasses.getrModId().trim());
						ps.setInt(3, courseClasses.getDbTs());
						ps.setString(4, courseClasseskey.getInstId().trim());
						ps.setString(5, courseClasseskey.getBranchId().trim());
						ps.setString(6, courseClasseskey.getCcId().trim());

					}

				});
		if (deletedRecs == 0) {
			throw new DeleteFailedException();

		}

	}

	@Override
	public CourseClasses selectCourseClassesRec(
			final CourseClassesKey courseClassesKey)
			throws NoDataFoundException {
		logger.debug("Inside select method");
		logger.debug("Course Classes Key object values :"
				+ courseClassesKey.toString());
		StringBuffer sql = new StringBuffer();
		sql.append("select ").append(" DB_TS,").append(" INST_ID,")
				.append("BRANCH_ID,")

				.append("CC_ID,").append("AC_TERM,").append("STUDENTGRP_ID,")

				.append(" CRSL_ID,").append(" SA_NO,").append("CLS_TYPE,")
				.append("LAB_BATCH,")

				.append(" STAFF_ID,").append(" NO_OF_CLSES_PER_WEEK,")
				.append(" NO_OF_LAB_CLSES_PER_WEEK,").append("DURATION ,")
				.append("DEL_FLG,").append("R_MOD_ID,").append("R_MOD_TIME,")

				.append("R_CRE_ID,").append("R_CRE_TIME").append(" from crcl ")
				.append(" where")

				.append("   INST_ID= ?").append(" and  BRANCH_ID= ?")
				.append(" and  CC_ID= ?").append(" and  DEL_FLG='N'");
		CourseClasses selectedCourseClassesDetails = null;
		selectedCourseClassesDetails = (CourseClasses) getJdbcTemplate().query(
				sql.toString(), new PreparedStatementSetter() {

					@Override
					public void setValues(PreparedStatement ps)
							throws SQLException {
						ps.setString(1, courseClassesKey.getInstId().trim());
						ps.setString(2, courseClassesKey.getBranchId().trim());
						ps.setString(3, courseClassesKey.getCcId().trim());

					}

				}, new ResultSetExtractor<CourseClasses>() {

					@Override
					public CourseClasses extractData(ResultSet rs)
							throws SQLException, DataAccessException {
						CourseClasses courseClasses = null;
						if (rs.next()) {
							courseClasses = new CourseClasses();
							courseClasses.setDbTs(rs.getInt("DB_TS"));
							courseClasses.setInstId(rs.getString("INST_ID"));
							courseClasses.setBranchId(rs.getString("BRANCH_ID"));
							courseClasses.setCcId(rs.getString("CC_ID"));
							courseClasses.setAcTerm(rs.getString("AC_TERM"));
							courseClasses.setStudentGrpId(rs
									.getString("STUDENTGRP_ID"));

							courseClasses.setCrslId(rs.getString("CRSL_ID"));
							courseClasses.setSaNo(rs.getString("SA_NO"));
							courseClasses.setClsType(rs.getString("CLS_TYPE"));
							courseClasses.setLabBatch(rs.getString("LAB_BATCH"));

							courseClasses.setStaffId(rs.getString("STAFF_ID"));
							courseClasses.setDuration(rs.getString("DURATION"));
							courseClasses.setNoOfClassesPerWeek(rs
									.getString("NO_OF_CLSES_PER_WEEK"));
							courseClasses.setNoOfLabClassesPerWeek(rs
									.getString("NO_OF_LAB_CLSES_PER_WEEK"));
							courseClasses.setDelFlag(rs.getString("DEL_FLG"));
							courseClasses.setrModId(rs.getString("R_MOD_ID"));
							courseClasses.setrModTime(rs
									.getString("R_MOD_TIME"));
							courseClasses.setrCreId(rs.getString("R_CRE_ID"));
							courseClasses.setrCreTime(rs
									.getString("R_CRE_TIME"));
						}
						return courseClasses;
					}

				});

		if (selectedCourseClassesDetails == null) {
			throw new NoDataFoundException();
		}

		return selectedCourseClassesDetails;
	}

	@Override
	public int checkRecordExist(String instId, String branchId, String acTerm,
			String stGroup, String batch, String crslId, String staffId) {
		String SELECT_COUNT = "select count(0) from crcl where inst_id='"
				+ instId + "' and branch_id='" + branchId + "' and ac_term='"
				+ acTerm + "' and studentgrp_id='" + stGroup
				+ "' and crsl_id='" + crslId + "'" + " and staff_id='"
				+ staffId + "'" + " and del_flg='N'";
		if ((batch != null) && (!batch.equals("")) && (!batch.equals("NA"))) {
			SELECT_COUNT += " and lab_batch='" + batch + "'";
		}
		System.out.println("checkRecordExist query :" + SELECT_COUNT);
		return getJdbcTemplate().queryForInt(SELECT_COUNT);
	}
}
