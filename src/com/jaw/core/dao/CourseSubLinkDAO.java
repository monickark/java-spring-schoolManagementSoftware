package com.jaw.core.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import com.jaw.common.exceptions.DeleteFailedException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.UpdateFailedException;
import com.jaw.framework.dao.BaseDao;

//CourseSubLinkDAO DAO class
@Repository
public class CourseSubLinkDAO extends BaseDao implements ICourseSubLinkDAO {
	// Logging
	Logger logger = Logger.getLogger(CourseSubLinkDAO.class);

	@Override
	public void insertCourseSubLinkRec(final CourseSubLink courseSubLink)
			throws DuplicateEntryException {
		System.out.println("Inside insert method");
		System.out.println("CourseSubLink object values :"
				+ courseSubLink.toString());
		StringBuffer query = new StringBuffer();
		
		query = query
				.append("insert into crsl ( ")
				.append("DB_TS,")
				.append("INST_ID,")
				.append("BRANCH_ID,")
				.append("CRSL_ID,")
				.append("COURSEMASTER_ID,")
				.append("TERM_ID,")
				.append("SUB_ID,")
				.append("SUB_TYPE,")
				.append("USED_ONLY_FOR_TT,")
				.append("MARK_GRADE,")
				.append("INC_FOR_MARK_TOTAL,")
				.append("INC_FOR_ATT_CAL,")
				.append("CLS_DURATION,")
				.append("REQUIRES_LAB,")
				.append("TECHR_REQD,")
				.append("NO_OF_CONSE_CLASSES,")
				.append("NO_OF_CLSES_PER_WEEK,")
				.append("NO_OF_LAB_CLSES_PER_WEEK,")
				.append("TT_ASSIGNMENT_WITHIN_SG,")
				.append("RC_SUB_ORDER_WITHIN_SG,")
				.append("DEL_FLG,")
				.append("R_MOD_ID,")
				.append("R_MOD_TIME,")
				.append("R_CRE_ID,")
				.append("R_CRE_TIME)")
				.append(" values (?,?,?, ?,?,?, ?,?,?, ?,?,?, ?,?,?, ?,?,?, ?,?,?, ?,now(),?,now())");

		try {
			getJdbcTemplate().update(query.toString(),
					new PreparedStatementSetter() {

						@Override
						public void setValues(PreparedStatement pss)
								throws SQLException {
							pss.setInt(1, courseSubLink.getDbTs());
							pss.setString(2, courseSubLink.getInstId());
							pss.setString(3, courseSubLink.getBranchId());
							pss.setString(4, courseSubLink.getCrslId().trim());
							pss.setString(5, courseSubLink.getCourseMasterId()
									.trim());
							pss.setString(6, courseSubLink.getTermId().trim());
							pss.setString(7, courseSubLink.getSubId().trim());
							pss.setString(8, courseSubLink.getSubType().trim());
							pss.setString(9, courseSubLink.getUsedOnlyForTT()
									.trim());
							pss.setString(10, courseSubLink.getMarkGrade()
									.trim());
							pss.setString(11, courseSubLink
									.getIncForMarkTotal().trim());
							pss.setString(12, courseSubLink.getIncForAttCal()
									.trim());
							pss.setString(13, courseSubLink.getClsDuration()
									.trim());
							pss.setString(14, courseSubLink.getRequiresLab()
									.trim());
							pss.setString(15, courseSubLink.getRequiresTeacher()
									.trim());
							pss.setInt(16, courseSubLink.getNoOfConseClasses());
							pss.setInt(17, courseSubLink.getNoOfClsesPerWeek());
							pss.setInt(18,
									courseSubLink.getNoOfLabClassesPerWeek());
							pss.setInt(19,
									courseSubLink.getTtAssignmentWithinSg());
							pss.setInt(20,
									courseSubLink.getReportCardOrder());
							pss.setString(21, courseSubLink.getDelFlag().trim());
							pss.setString(22, courseSubLink.getrModId().trim());
							pss.setString(23, courseSubLink.getrCreId().trim());

						}

					});
		} catch (org.springframework.dao.DuplicateKeyException e) {
			throw new DuplicateEntryException();
		}

	}

	@Override
	public void updateCourseSubLinkRec(final CourseSubLink courseSubLink,
			final CourseSubLinkKey courseSubLinkkey)
			throws UpdateFailedException {
		System.out.println("Inside update method");
		System.out.println("Course SubLink object values :"
				+ courseSubLink.toString());
		System.out.println("Course SubLink key Details object values :"
				+ courseSubLinkkey.toString());
		StringBuffer sql = new StringBuffer();
		sql.append("update crsl set").append(" DB_TS= ?,")
				.append("COURSEMASTER_ID= ?,").append("TERM_ID= ?,")
				.append("SUB_ID= ?,").append("SUB_TYPE= ?,")
				.append("USED_ONLY_FOR_TT= ?,").append("MARK_GRADE= ?,")
				.append("INC_FOR_MARK_TOTAL= ?,").append("INC_FOR_ATT_CAL= ?,")
				.append("CLS_DURATION= ?,").append("REQUIRES_LAB= ?,").append("TECHR_REQD=?,")
				.append("NO_OF_CONSE_CLASSES= ?,")
				.append("NO_OF_CLSES_PER_WEEK= ?,")
				.append("TT_ASSIGNMENT_WITHIN_SG= ?,").append("RC_SUB_ORDER_WITHIN_SG=?,").append("DEL_FLG= 'N',")
				.append("R_MOD_ID= ?,").append(" R_MOD_TIME=now()")
				.append(" where").append(" DB_TS= ?")
				.append(" and  INST_ID= ?").append(" and  BRANCH_ID= ?")
				.append(" and  CRSL_ID= ?").append(" and  DEL_FLG='N'");

		int updateRecs = getJdbcTemplate().update(sql.toString(),
				new PreparedStatementSetter() {

					@Override
					public void setValues(PreparedStatement ps)
							throws SQLException {
						ps.setInt(1, courseSubLink.getDbTs() + 1);
						ps.setString(2, courseSubLink.getCourseMasterId()
								.trim());
						ps.setString(3, courseSubLink.getTermId().trim());
						ps.setString(4, courseSubLink.getSubId().trim());
						ps.setString(5, courseSubLink.getSubType().trim());
						ps.setString(6, courseSubLink.getUsedOnlyForTT().trim());
						ps.setString(7, courseSubLink.getMarkGrade().trim());
						ps.setString(8, courseSubLink.getIncForMarkTotal()
								.trim());
						ps.setString(9, courseSubLink.getIncForAttCal().trim());
						ps.setString(10, courseSubLink.getClsDuration().trim());
						ps.setString(11, courseSubLink.getRequiresLab().trim());
						ps.setString(12, courseSubLink.getRequiresTeacher().trim());
						ps.setInt(13, courseSubLink.getNoOfConseClasses());
						ps.setInt(14, courseSubLink.getNoOfClsesPerWeek());
						ps.setInt(15, courseSubLink.getTtAssignmentWithinSg());
						ps.setInt(16, courseSubLink.getReportCardOrder());
						ps.setString(17, courseSubLink.getrModId().trim());

						ps.setInt(18, courseSubLink.getDbTs());
						ps.setString(19, courseSubLinkkey.getInstId().trim());
						ps.setString(20, courseSubLinkkey.getBranchId().trim());
						ps.setString(21, courseSubLinkkey.getCrslId().trim());

					}

				});
		if (updateRecs == 0) {
			throw new UpdateFailedException();

		}

	}

	@Override
	public void deleteCourseSubLinkRec(final String userId,
			final CourseSubLinkKey courseSubLinkkey)
			throws DeleteFailedException {
		System.out.println("Inside delete method");
		System.out.println("Course SubLink Key object values :"
				+ courseSubLinkkey.toString());

		StringBuffer sql = new StringBuffer();
		sql.append("update crsl set").append(" DB_TS= ?,")
				.append("DEL_FLG= 'Y',").append("R_MOD_ID= ?,")
				.append(" R_MOD_TIME=now()").append(" where")
				.append(" DB_TS= ?").append(" and INST_ID= ?")
				.append(" and  BRANCH_ID= ?").append(" and  CRSL_ID= ?")
				.append(" and  DEL_FLG='N'");

		int deletedRecs = getJdbcTemplate().update(sql.toString(),
				new PreparedStatementSetter() {

					@Override
					public void setValues(PreparedStatement ps)
							throws SQLException {
						ps.setInt(1, courseSubLinkkey.getDbTs() + 1);
						ps.setString(2, userId);
						ps.setInt(3, courseSubLinkkey.getDbTs());
						ps.setString(4, courseSubLinkkey.getInstId().trim());
						ps.setString(5, courseSubLinkkey.getBranchId().trim());
						ps.setString(6, courseSubLinkkey.getCrslId().trim());

					}

				});
		if (deletedRecs == 0) {
			throw new DeleteFailedException();

		}

	}

	@Override
	public CourseSubLink selectCourseSubLinkRec(
			final CourseSubLinkKey courseSubLinkKey)
			throws NoDataFoundException {
		System.out.println("Inside select method");
		System.out.println("Course SubLink Key object values :"
				+ courseSubLinkKey.toString());
		List<String> data = new ArrayList<String>();
		StringBuffer sql = new StringBuffer();
		sql.append("select ").append(" DB_TS,").append("INST_ID,")
				.append("BRANCH_ID,").append("CRSL_ID,")
				.append("COURSEMASTER_ID,").append("TERM_ID,")
				.append("SUB_ID,").append("SUB_TYPE,")
				.append("USED_ONLY_FOR_TT,").append("MARK_GRADE,")
				.append("INC_FOR_MARK_TOTAL,").append("INC_FOR_ATT_CAL,")
				.append("CLS_DURATION,").append("REQUIRES_LAB,").append("TECHR_REQD,")
				.append("NO_OF_CONSE_CLASSES,").append("NO_OF_CLSES_PER_WEEK,")
				.append("TT_ASSIGNMENT_WITHIN_SG,").append("RC_SUB_ORDER_WITHIN_SG,").append("DEL_FLG,")
				.append("R_MOD_ID,").append("R_MOD_TIME,").append("R_CRE_ID,")
				.append("R_CRE_TIME").append(" from crsl ").append(" where")
				.append("   INST_ID= ?").append(" and  BRANCH_ID= ?")
				.append(" and  CRSL_ID= ?").append(" and  DEL_FLG=?");
		data.add(courseSubLinkKey.getInstId());
		data.add(courseSubLinkKey.getBranchId());
		data.add(courseSubLinkKey.getCrslId());
		data.add("N");
		if ((courseSubLinkKey.getDbTs() != null)
				&& (courseSubLinkKey.getDbTs() != 0)) {
			sql.append(" and  DB_TS= ?");
			data.add(courseSubLinkKey.getDbTs().toString());
		}
		String[] array = data.toArray(new String[data.size()]);
		CourseSubLink selectedCourseSubLink = null;
		selectedCourseSubLink = (CourseSubLink) getJdbcTemplate().query(
				sql.toString(), array, new ResultSetExtractor<CourseSubLink>() {

					@Override
					public CourseSubLink extractData(ResultSet rs)
							throws SQLException, DataAccessException {
						CourseSubLink courseSubLink = null;
						if (rs.next()) {
							courseSubLink = new CourseSubLink();
							courseSubLink.setDbTs(rs.getInt("DB_TS"));
							courseSubLink.setInstId(rs.getString("INST_ID"));
							courseSubLink.setBranchId(rs.getString("BRANCH_ID"));
							courseSubLink.setCrslId(rs.getString("CRSL_ID"));
							courseSubLink.setCourseMasterId(rs
									.getString("COURSEMASTER_ID"));
							courseSubLink.setTermId(rs.getString("TERM_ID"));
							courseSubLink.setSubId(rs.getString("SUB_ID"));
							courseSubLink.setSubType(rs.getString("SUB_TYPE"));
							courseSubLink.setUsedOnlyForTT(rs
									.getString("USED_ONLY_FOR_TT"));
							courseSubLink.setMarkGrade(rs
									.getString("MARK_GRADE"));
							courseSubLink.setIncForMarkTotal(rs
									.getString("INC_FOR_MARK_TOTAL"));
							courseSubLink.setIncForAttCal(rs
									.getString("INC_FOR_ATT_CAL"));
							courseSubLink.setClsDuration(rs
									.getString("CLS_DURATION"));
							courseSubLink.setRequiresLab(rs
									.getString("REQUIRES_LAB"));
							courseSubLink.setRequiresTeacher(rs
									.getString("TECHR_REQD"));
							courseSubLink.setNoOfConseClasses(rs
									.getInt("NO_OF_CONSE_CLASSES"));
							courseSubLink.setNoOfClsesPerWeek(rs
									.getInt("NO_OF_CLSES_PER_WEEK"));
							courseSubLink.setTtAssignmentWithinSg(rs
									.getInt("TT_ASSIGNMENT_WITHIN_SG"));
							courseSubLink.setReportCardOrder(rs
									.getInt("RC_SUB_ORDER_WITHIN_SG"));
							courseSubLink.setDelFlag(rs.getString("DEL_FLG"));
							courseSubLink.setrModId(rs.getString("R_MOD_ID"));
							courseSubLink.setrModTime(rs
									.getString("R_MOD_TIME"));
							courseSubLink.setrCreId(rs.getString("R_CRE_ID"));
							courseSubLink.setrCreTime(rs
									.getString("R_CRE_TIME"));
						}
						return courseSubLink;
					}

				});

		if (selectedCourseSubLink == null) {
			throw new NoDataFoundException();
		}
System.out.println("selectedCourseSubLink in dao :"+selectedCourseSubLink.toString());
		return selectedCourseSubLink;
	}

	@Override
	public int checkRecordExist(String instId, String branchId,
			String courseId, String termId, String subId,String subType) {
		String SELECT_COUNT = "select count(0) from crsl where inst_id='"
				+ instId + "' and branch_id='" + branchId
				+ "' and coursemaster_id='" + courseId + "' and term_id='"
				+ termId + "' and sub_id='" + subId + "' and sub_type='" + subType
				+ "' and del_flg='N'";

		System.out.println("checkRecordExist query :" + SELECT_COUNT);
		return getJdbcTemplate().queryForInt(SELECT_COUNT);
	}
	
	@Override
	public int checkRecordOrderExist(String instId, String branchId,
			String courseId, String termId,int subOrder) {
		String SELECT_COUNT = "select count(0) from crsl where inst_id='"
				+ instId + "' and branch_id='" + branchId
				+ "' and coursemaster_id='" + courseId + "' and term_id='"
				+ termId + "' and RC_SUB_ORDER_WITHIN_SG='" + subOrder
				+ "' and del_flg='N'";

		System.out.println("checkRecordExist query :" + SELECT_COUNT);
		return getJdbcTemplate().queryForInt(SELECT_COUNT);
	}
}
