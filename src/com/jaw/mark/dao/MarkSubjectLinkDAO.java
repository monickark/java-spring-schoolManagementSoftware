package com.jaw.mark.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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

@Repository
public class MarkSubjectLinkDAO extends BaseDao implements IMarkSubjectLinkDAO {
	// Logging
	Logger logger = Logger.getLogger(MarkSubjectLinkDAO.class);

	@Override
	public void insertMarkSubjectRec(final MarkSubjectLink markSubjectLink)
			throws DuplicateEntryException {
		logger.debug("Inside insert method");
		logger.debug("MarkSubjectLink object values :"
				+ markSubjectLink.toString());
		StringBuffer query = new StringBuffer();
	
		query = query
				.append("insert into mksl ( ")

				.append("DB_TS,")
				.append("INST_ID,")
				.append("BRANCH_ID,")

				.append("MKSL_SEQ_ID,")
				.append("AC_TERM,")
				.append("STUDENTGRP_ID,")

				.append("EXAM_ID,")
				.append("EXAM_TYPE,")
				.append("SUB_TEST_ID,")
				
				.append("CRSL_ID,")
				.append("LAB_BATCH,")
				.append("EXAM_DATE,")
				
				.append("START_TIME,")				
				.append("DURATION,")
				.append("MIN_MARK,")
				
				.append("MAX_MARK,")				
				.append("REMARKS,")
				.append("SUB_PORTION_DTLS,")
				
				.append("STATUS,")				
				.append("DEL_FLG,")
				.append("R_MOD_ID,")
				
				.append("R_MOD_TIME,")				
				.append("R_CRE_ID,")
				.append("R_CRE_TIME)")

				.append(" values (?,?,?, ?,?,?, ?,?,?, ?,?,?, ?,?,?, ?,?,?, ?,?,?,  now(),?,now())");

		try {
			getJdbcTemplate().update(query.toString(),
					new PreparedStatementSetter() {

						@Override
						public void setValues(PreparedStatement pss)
								throws SQLException {
							pss.setInt(1, markSubjectLink.getDbTs());
							pss.setString(2, markSubjectLink.getInstId());
							pss.setString(3, markSubjectLink.getBranchId());
							pss.setString(4, markSubjectLink
									.getMarkSubjectLinkId().trim());
							pss.setString(5, markSubjectLink.getAcTerm().trim());
							pss.setString(6, markSubjectLink.getStudentGrpId()
									.trim());
							pss.setString(7, markSubjectLink.getExamId().trim());
							pss.setString(8, markSubjectLink.getExamType()
									.trim());
							pss.setString(9, markSubjectLink.getSubTestId()
									.trim());
							pss.setString(10, markSubjectLink.getCrslId()
									.trim());
							pss.setString(11, markSubjectLink.getLabBatch()
									.trim());
							pss.setString(12, markSubjectLink.getExamDate()
									.trim());							
							pss.setString(13, markSubjectLink.getStartTime()
									.trim());
							pss.setString(14, markSubjectLink.getDuration()
											.trim());				
							
							pss.setString(15, markSubjectLink.getMinMark()
									.trim());
							pss.setString(16, markSubjectLink.getMaxMark()
									.trim());
							pss.setString(17, markSubjectLink.getRemarks()
									.trim());
							pss.setString(18, markSubjectLink
									.getSubPortionsDetails().trim());
							pss.setString(19, markSubjectLink.getStatus()
									.trim());
							pss.setString(20, markSubjectLink.getDelFlag()
									.trim());
							pss.setString(21, markSubjectLink.getrModId()
									.trim());
							pss.setString(22, markSubjectLink.getrCreId()
									.trim());

						}

					});
		} catch (org.springframework.dao.DuplicateKeyException e) {
			throw new DuplicateEntryException();
		}

	}

	@Override
	public void updateMarkSubjectRec(final MarkSubjectLink markSubjectLink,
			final MarkSubjectLinkKey markSubjectLinkKey)
			throws UpdateFailedException {

		logger.debug("Inside update method");
		logger.debug("Mark subject Linking object values :"
				+ markSubjectLink.toString());
		logger.debug("Mark subject Linking key Details object values :"
				+ markSubjectLinkKey.toString());

		StringBuffer sql = new StringBuffer();
		sql.append("update mksl set ")

		.append("DB_TS=?,").append("INST_ID=?,").append("BRANCH_ID=?,")

		.append("MKSL_SEQ_ID=?,").append("AC_TERM=?,")
				.append("STUDENTGRP_ID=?,")

				.append("EXAM_ID=?,").append("EXAM_TYPE=?,")
				.append("SUB_TEST_ID=?,")

				.append("CRSL_ID=?,").append("LAB_BATCH=?,")
				.append("EXAM_DATE=?,")
				.append("START_TIME=?,")				
				.append("DURATION=?,")

				.append("MIN_MARK=?,").append("MAX_MARK=?,")
				.append("REMARKS=?,")

				.append("SUB_PORTION_DTLS=?,").append("STATUS=?,")
				.append("DEL_FLG=?,")

				.append("R_MOD_ID=?,").append(" R_MOD_TIME=now()")
				.append(" where").append(" DB_TS= ?")
				.append(" and  INST_ID= ?").append(" and  BRANCH_ID= ?")
				.append(" and  MKSL_SEQ_ID= ?").append(" and  DEL_FLG='N'");

		int updateRecs = getJdbcTemplate().update(sql.toString(),
				new PreparedStatementSetter() {

					@Override
					public void setValues(PreparedStatement pss)
							throws SQLException {
						pss.setInt(1, markSubjectLink.getDbTs() + 1);
						pss.setString(2, markSubjectLink.getInstId());
						pss.setString(3, markSubjectLink.getBranchId());

						pss.setString(4, markSubjectLink.getMarkSubjectLinkId()
								.trim());
						pss.setString(5, markSubjectLink.getAcTerm().trim());
						pss.setString(6, markSubjectLink.getStudentGrpId()
								.trim());

						pss.setString(7, markSubjectLink.getExamId().trim());
						pss.setString(8, markSubjectLink.getExamType().trim());
						pss.setString(9, markSubjectLink.getSubTestId().trim());

						pss.setString(10, markSubjectLink.getCrslId().trim());
						pss.setString(11, markSubjectLink.getLabBatch().trim());
						pss.setString(12, markSubjectLink.getExamDate().trim());
						pss.setString(13, markSubjectLink.getStartTime()
								.trim());
						pss.setString(14, markSubjectLink.getDuration()
										.trim());		
						pss.setString(15, markSubjectLink.getMinMark().trim());
						pss.setString(16, markSubjectLink.getMaxMark().trim());
						pss.setString(17, markSubjectLink.getRemarks().trim());

						pss.setString(18, markSubjectLink
								.getSubPortionsDetails().trim());
						pss.setString(19, markSubjectLink.getStatus().trim());
						pss.setString(20, markSubjectLink.getDelFlag().trim());

						pss.setString(21, markSubjectLink.getrModId().trim());

						pss.setInt(22, markSubjectLink.getDbTs());
						pss.setString(23, markSubjectLinkKey.getInstId().trim());
						pss.setString(24, markSubjectLinkKey.getBranchId()
								.trim());
						pss.setString(25, markSubjectLinkKey
								.getMarkSubjectLinkId().trim());

					}

				});
		if (updateRecs == 0) {
			throw new UpdateFailedException();

		}

	}

	@Override
	public void deleteMarkSubjectRec(final MarkSubjectLink markSubjectLink,
			final MarkSubjectLinkKey markSubjectLinkKey)
			throws DeleteFailedException {
		logger.debug("Inside delete method");
		logger.debug("Mark subject Linking object values :"
				+ markSubjectLink.toString());
		logger.debug("Mark subject Linking Key object values :"
				+ markSubjectLinkKey.toString());

		StringBuffer sql = new StringBuffer();
		sql.append("update mksl set").append(" DB_TS= ?,")
				.append("DEL_FLG= 'Y',").append("R_MOD_ID= ?,")
				.append(" R_MOD_TIME=now()").append(" where")
				.append(" DB_TS= ?").append(" and INST_ID= ?")
				.append(" and  BRANCH_ID= ?").append(" and  MKSL_SEQ_ID= ?")
				.append(" and  DEL_FLG='N'");

		int deletedRecs = getJdbcTemplate().update(sql.toString(),
				new PreparedStatementSetter() {

					@Override
					public void setValues(PreparedStatement ps)
							throws SQLException {
						ps.setInt(1, markSubjectLink.getDbTs() + 1);
						ps.setString(2, markSubjectLink.getrModId().trim());
						ps.setInt(3, markSubjectLink.getDbTs());
						ps.setString(4, markSubjectLinkKey.getInstId().trim());
						ps.setString(5, markSubjectLinkKey.getBranchId().trim());
						ps.setString(6, markSubjectLinkKey
								.getMarkSubjectLinkId().trim());

					}

				});
		if (deletedRecs == 0) {
			throw new DeleteFailedException();

		}

	}

	@Override
	public MarkSubjectLink selectMarkSubjectRec(
			final MarkSubjectLinkKey markSubjectLinkKey)
			throws NoDataFoundException {
		logger.debug("Inside select method");
		logger.debug("Mark subject Linking Key object values :"
				+ markSubjectLinkKey.toString());

		StringBuffer sql = new StringBuffer();
		sql.append("select ").append("DB_TS,").append("INST_ID,")
				.append("BRANCH_ID,")

				.append("MKSL_SEQ_ID,").append("AC_TERM,")
				.append("STUDENTGRP_ID,")

				.append("EXAM_ID,").append("EXAM_TYPE,").append("SUB_TEST_ID,")
				.append("CRSL_ID,")

				.append("LAB_BATCH,").append("EXAM_DATE,")
				.append("START_TIME,")				
				.append("DURATION,")

				.append("MIN_MARK,")

				.append("MAX_MARK,").append("REMARKS,")
				.append("SUB_PORTION_DTLS,")

				.append("DEL_FLG,").append("R_MOD_ID,")

				.append("R_MOD_TIME,").append("R_CRE_ID,")
				.append("R_CRE_TIME,").append("STATUS from mksl ")
				.append(" where").append("  INST_ID= ?")
				.append(" and  BRANCH_ID= ?").append(" and  MKSL_SEQ_ID= ?")
				.append(" and  DEL_FLG='N'");
		MarkSubjectLink selectedMarkSubjectDetails = null;
		selectedMarkSubjectDetails = (MarkSubjectLink) getJdbcTemplate().query(
				sql.toString(), new PreparedStatementSetter() {

					@Override
					public void setValues(PreparedStatement ps)
							throws SQLException {
						ps.setString(1, markSubjectLinkKey.getInstId().trim());
						ps.setString(2, markSubjectLinkKey.getBranchId().trim());
						ps.setString(3, markSubjectLinkKey
								.getMarkSubjectLinkId().trim());

					}

				}, new ResultSetExtractor<MarkSubjectLink>() {

					@Override
					public MarkSubjectLink extractData(ResultSet rs)
							throws SQLException, DataAccessException {
						MarkSubjectLink markSubjectLink = null;
						if (rs.next()) {

							markSubjectLink = new MarkSubjectLink();
							markSubjectLink.setDbTs(rs.getInt("DB_TS"));
							markSubjectLink.setInstId(rs.getString("INST_ID"));
							markSubjectLink.setBranchId(rs
									.getString("BRANCH_ID"));

							markSubjectLink.setMarkSubjectLinkId(rs
									.getString("MKSL_SEQ_ID"));
							markSubjectLink.setAcTerm(rs.getString("AC_TERM"));
							markSubjectLink.setStudentGrpId(rs
									.getString("STUDENTGRP_ID"));

							markSubjectLink.setExamId(rs.getString("EXAM_ID"));
							markSubjectLink.setExamType(rs
									.getString("EXAM_TYPE"));
							markSubjectLink.setSubTestId(rs
									.getString("SUB_TEST_ID"));
							markSubjectLink.setCrslId(rs.getString("CRSL_ID"));

							markSubjectLink.setLabBatch(rs
									.getString("LAB_BATCH"));
							markSubjectLink.setExamDate(rs
									.getString("EXAM_DATE"));
							markSubjectLink.setStartTime(rs
									.getString("START_TIME"));
							markSubjectLink.setDuration(rs
									.getString("DURATION"));
							markSubjectLink.setMinMark(rs.getString("MIN_MARK"));

							markSubjectLink.setMaxMark(rs.getString("MAX_MARK"));
							markSubjectLink.setRemarks(rs.getString("REMARKS"));
							markSubjectLink.setSubPortionsDetails(rs
									.getString("SUB_PORTION_DTLS"));
							markSubjectLink.setDelFlag(rs.getString("DEL_FLG"));
							markSubjectLink.setrModId(rs.getString("R_MOD_ID"));
							markSubjectLink.setStatus(rs.getString("STATUS"));
							markSubjectLink.setrModTime(rs
									.getString("R_MOD_TIME"));
							markSubjectLink.setrCreId(rs.getString("R_CRE_ID"));
							markSubjectLink.setrCreTime(rs
									.getString("R_CRE_TIME"));
						}
						return markSubjectLink;
					}

				});

		if (selectedMarkSubjectDetails == null) {
			throw new NoDataFoundException();
		}

		return selectedMarkSubjectDetails;
	}

	@Override
	public MarkSubjectLink selectMarkSubjectRecNotById(
			final MarkSubjectLinkKey markSubjectLinkKey)
			throws NoDataFoundException {
		logger.debug("Inside select method");
		logger.debug("Mark subject Linking Key object values :"
				+ markSubjectLinkKey.toString());

		StringBuffer sql = new StringBuffer();
		sql.append("select ").append("DB_TS,").append("INST_ID,")
				.append("BRANCH_ID,")

				.append("MKSL_SEQ_ID,").append("AC_TERM,")
				.append("STUDENTGRP_ID,")

				.append("EXAM_ID,").append("EXAM_TYPE,").append("SUB_TEST_ID,")
				.append("CRSL_ID,")

				.append("LAB_BATCH,").append("EXAM_DATE,").append("MIN_MARK,")

				.append("MAX_MARK,").append("REMARKS,")
				.append("SUB_PORTION_DTLS,")

				.append("DEL_FLG,").append("R_MOD_ID,")

				.append("R_MOD_TIME,").append("R_CRE_ID,")
				.append("R_CRE_TIME,").append("STATUS from mksl ")
				.append(" where").append("  INST_ID= ?")
				.append(" and  BRANCH_ID= ?").append(" and  ac_term= ?")
				.append(" and  studentgrp_id= ?").append(" and  exam_id= ?")
				.append(" and  DEL_FLG='N'");
		MarkSubjectLink selectedMarkSubjectDetails = null;
		selectedMarkSubjectDetails = (MarkSubjectLink) getJdbcTemplate().query(
				sql.toString(), new PreparedStatementSetter() {

					@Override
					public void setValues(PreparedStatement ps)
							throws SQLException {
						ps.setString(1, markSubjectLinkKey.getInstId().trim());
						ps.setString(2, markSubjectLinkKey.getBranchId().trim());
						ps.setString(3, markSubjectLinkKey.getAcTerm());
						ps.setString(4, markSubjectLinkKey.getStudentGrpId());
						ps.setString(5, markSubjectLinkKey.getExamId());

					}

				}, new ResultSetExtractor<MarkSubjectLink>() {

					@Override
					public MarkSubjectLink extractData(ResultSet rs)
							throws SQLException, DataAccessException {
						MarkSubjectLink markSubjectLink = null;
						if (rs.next()) {

							markSubjectLink = new MarkSubjectLink();
							markSubjectLink.setDbTs(rs.getInt("DB_TS"));
							markSubjectLink.setInstId(rs.getString("INST_ID"));
							markSubjectLink.setBranchId(rs
									.getString("BRANCH_ID"));

							markSubjectLink.setMarkSubjectLinkId(rs
									.getString("MKSL_SEQ_ID"));
							markSubjectLink.setAcTerm(rs.getString("AC_TERM"));
							markSubjectLink.setStudentGrpId(rs
									.getString("STUDENTGRP_ID"));

							markSubjectLink.setExamId(rs.getString("EXAM_ID"));
							markSubjectLink.setExamType(rs
									.getString("EXAM_TYPE"));
							markSubjectLink.setSubTestId(rs
									.getString("SUB_TEST_ID"));
							markSubjectLink.setCrslId(rs.getString("CRSL_ID"));

							markSubjectLink.setLabBatch(rs
									.getString("LAB_BATCH"));
							markSubjectLink.setExamDate(rs
									.getString("EXAM_DATE"));
							markSubjectLink.setMinMark(rs.getString("MIN_MARK"));

							markSubjectLink.setMaxMark(rs.getString("MAX_MARK"));
							markSubjectLink.setRemarks(rs.getString("REMARKS"));
							markSubjectLink.setSubPortionsDetails(rs
									.getString("SUB_PORTION_DTLS"));
							markSubjectLink.setDelFlag(rs.getString("DEL_FLG"));
							markSubjectLink.setrModId(rs.getString("R_MOD_ID"));
							markSubjectLink.setStatus(rs.getString("STATUS"));
							markSubjectLink.setrModTime(rs
									.getString("R_MOD_TIME"));
							markSubjectLink.setrCreId(rs.getString("R_CRE_ID"));
							markSubjectLink.setrCreTime(rs
									.getString("R_CRE_TIME"));
						}
						return markSubjectLink;
					}

				});

		if (selectedMarkSubjectDetails == null) {
			throw new NoDataFoundException();
		}

		return selectedMarkSubjectDetails;
	}

	@Override
	public int checkFirstRecord(MarkSubjectLinkKey markSubjectLinkKey) {
		System.out.println("mark sub lisyt :" + markSubjectLinkKey.toString());
		String SELECT_COUNT = "select count(0) from mksl where inst_id='"
				+ markSubjectLinkKey.getInstId() + "' and branch_id='"
				+ markSubjectLinkKey.getBranchId() + "' and ac_term='"
				+ markSubjectLinkKey.getAcTerm() + "' and studentgrp_id='"
				+ markSubjectLinkKey.getStudentGrpId() + "' and EXAM_ID='"
				+ markSubjectLinkKey.getExamId() + "' and EXAM_TYPE='"
				+ markSubjectLinkKey.getExamType() + "'  and CRSL_ID='"
				+ markSubjectLinkKey.getCrslId() + "' and DEL_FLG='N'";
		if ((markSubjectLinkKey.getLabBatch() != null)
				&& (!markSubjectLinkKey.getLabBatch().equals(""))
				&& (!markSubjectLinkKey.getLabBatch().equals("NA"))) {
			SELECT_COUNT += " and lab_batch='"
					+ markSubjectLinkKey.getLabBatch() + "'";
		}
		System.out.println("checkRecordExist query :" + SELECT_COUNT);
		return getJdbcTemplate().queryForInt(SELECT_COUNT);
	}

	@Override
	public int checkRecordExist(MarkSubjectLinkKey markSubjectLinkKey) {
		System.out.println("mark sub lisyt :" + markSubjectLinkKey.toString());
		String SELECT_COUNT = "select count(0) from mksl where inst_id='"
				+ markSubjectLinkKey.getInstId() + "' and branch_id='"
				+ markSubjectLinkKey.getBranchId() + "' and ac_term='"
				+ markSubjectLinkKey.getAcTerm() + "' and studentgrp_id='"
				+ markSubjectLinkKey.getStudentGrpId() + "' and EXAM_ID='"
				+ markSubjectLinkKey.getExamId() + "' and EXAM_TYPE='"
				+ markSubjectLinkKey.getExamType() + "' and SUB_TEST_ID='"
				+ markSubjectLinkKey.getSubTestId() + "' and CRSL_ID='"
				+ markSubjectLinkKey.getCrslId() + "' and DEL_FLG='N'";
		if ((markSubjectLinkKey.getLabBatch() != null)
				&& (!markSubjectLinkKey.getLabBatch().equals(""))
				&& (!markSubjectLinkKey.getLabBatch().equals("NA"))) {
			SELECT_COUNT += " and lab_batch='"
					+ markSubjectLinkKey.getLabBatch() + "'";
		}
		System.out.println("checkRecordExist query :" + SELECT_COUNT);
		return getJdbcTemplate().queryForInt(SELECT_COUNT);
	}

}
