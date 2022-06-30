package com.jaw.core.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import com.jaw.common.constants.CommonCodeConstant;
import com.jaw.common.exceptions.DeleteFailedException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.UpdateFailedException;
import com.jaw.framework.dao.BaseDao;

@Repository
public class ClassTeacherAllotmentDao extends BaseDao implements
		IClassTeacherAllotmentDao {

	Logger logger = Logger.getLogger(ClassTeacherAllotmentDao.class);

	@Override
	public void insertStaff(final ClassTeacherAllotment classteacher)
			throws DuplicateEntryException {

		logger.debug("Inside insert method in ClassTeacherAllotmentDao" + classteacher.toString());
		
		StringBuffer sql = new StringBuffer();
		sql = sql.append("insert into clta ( ")
				.append("DB_TS,")
				.append("INST_ID ,")
				.append("BRANCH_ID,")
				
				.append("AC_TERM,")
				.append("CA_SEQ_ID,")
				.append("STUDENTGRP_ID,")
				
				.append("STUDENT_BATCH_ID,")
				.append("STAFF_ID,")
				.append("TTG_ID,")
				
				.append("DEL_FLG,")
				.append("R_MOD_ID,")
				.append("R_MOD_TIME,")
				
				.append("R_CRE_ID,")
				.append("R_CRE_TIME)")
				.append("  values(?,?,?, ?,?,?, ?,?,?, ?,?,NOW(), ?,NOW())");

		try {
			getJdbcTemplate().update(sql.toString(),
					new PreparedStatementSetter() {

					

						@Override
						public void setValues(PreparedStatement rs)
								throws SQLException {
							rs.setInt(1, classteacher.getDbTs());
							rs.setString(2, classteacher.getInstId());
							rs.setString(3, classteacher.getBranchId());
							rs.setString(4, classteacher.getAcTerm());
							rs.setString(5, classteacher.getCaSeqId());
							rs.setString(6, classteacher.getStGroup());
							rs.setString(7, classteacher.getStBatchId());
							rs.setString(8, classteacher.getStaffId());
							rs.setString(9, classteacher.getTtgId());
							rs.setString(10, "N");
							rs.setString(11, classteacher.getrModId());
							rs.setString(12, classteacher.getrCreId());
							
						}
					});
		} catch (DuplicateKeyException e) {
			throw new DuplicateEntryException();
		}

	}

	@Override
	public void updateStaff(final ClassTeacherAllotment classTeacherAllotment,
			final ClassTeacherAllotmentKey classTeacherAllotmentKey)
			throws UpdateFailedException {

		logger.debug("Inside update method in ClassTeacherAllotmentDao :" +classTeacherAllotmentKey.toString());

		StringBuffer sql = new StringBuffer();
		sql.append("update clta set ").append("DB_TS='")
				.append("INST_ID=?, ")
				.append("BRANCH_ID=?, ")
				.append("AC_TERM=?,")
				.append("CA_SEQ_ID=?,")
				.append("STUDENTGRP_ID=?,")
				.append("STUDENT_BATCH_ID=?,")
				.append("STAFF_ID=?,")
				.append("TTG_ID=?,")
				.append("DEL_FLG=?,")
				.append("R_MOD_ID=?.")
				.append("R_CRE_ID=?")
				.append(" where ")
				.append("DB_TS=? AND ")
				.append("INST_ID=? AND")
				.append("BRANCH_ID ? AND ")
				.append("CA_SEQ_ID=? ");
		logger.debug("update query :" + sql.toString());

		int updateRecs = getJdbcTemplate().update(sql.toString(),
				new PreparedStatementSetter() {

					@Override
					public void setValues(PreparedStatement ps)
							throws SQLException {
						ps.setInt(1, classTeacherAllotment.getDbTs());
						ps.setString(2, classTeacherAllotment.getInstId());
						ps.setString(3, classTeacherAllotment.getBranchId());
						ps.setString(4, classTeacherAllotment.getAcTerm());
						ps.setString(5, classTeacherAllotment.getCaSeqId());
						ps.setString(6, classTeacherAllotment.getStGroup());
						ps.setString(7, classTeacherAllotment.getStBatchId());
						ps.setString(8, classTeacherAllotment.getStaffId());
						ps.setString(9, classTeacherAllotment.getTtgId());
						ps.setString(10, classTeacherAllotment.getDelFlag());
						ps.setString(11, classTeacherAllotment.getrModId());
						ps.setString(12, classTeacherAllotment.getrCreId());
						ps.setInt(16, classTeacherAllotment.getDbTs());
						ps.setString(17, classTeacherAllotmentKey.getInstId().trim());
						ps.setString(18, classTeacherAllotmentKey.getBranchId().trim());
						ps.setString(19, classTeacherAllotmentKey.getCaSeqId().trim());

					}

				});
		if (updateRecs == 0) {
			throw new UpdateFailedException();
		}

	}

	@Override
	public void deleteClassTeacherAllotted(final ClassTeacherAllotmentKey classTeacherAllotmentKey)
			throws DeleteFailedException {

		logger.debug("Inside insert method in ClassTeacherAllotmentDao :"+classTeacherAllotmentKey.toString());

		StringBuffer sql = new StringBuffer();

		sql.append("delete from clta where ")
		.append("DB_Ts=?")
		.append(" and inst_id=?")
		.append(" and branch_id=?")
		.append(" and ca_seq_id=?")
		.append(" and del_flg=?");
		logger.debug("update query :" + sql.toString());

		int affectedRows = getJdbcTemplate().update(sql.toString(),
				new PreparedStatementSetter() {

			@Override
			public void setValues(PreparedStatement ps)
					throws SQLException {
				
				ps.setInt(1, classTeacherAllotmentKey.getDbTs());
				ps.setString(2, classTeacherAllotmentKey.getInstId().trim());
				ps.setString(3, classTeacherAllotmentKey.getBranchId().trim());
				ps.setString(4, classTeacherAllotmentKey.getCaSeqId().trim());
				ps.setString(5, "N");

			}

		});
		if (affectedRows == 0) {
			throw new DeleteFailedException();
		}

	}

	@Override
	public ClassTeacherAllotment selectStaff(
			final ClassTeacherAllotmentKey classTeacherAllotmentKey)
			throws NoDataFoundException {
		logger.debug("Inside select method in ClassTeacherAllotmentDao :"+classTeacherAllotmentKey.toString());
		StringBuffer sql = new StringBuffer();
		sql.append("select ").append("DB_TS,").append("INST_ID,").append("BRANCH_ID,")
		.append("AC_TERM,").append("CA_SEQ_ID,").append("STUDENTGRP_ID,").append("STUDENT_BATCH_ID,").append("STAFF_ID,")
				.append("TTG_ID,").append("DEL_FLG,").append("R_MOD_ID,")
				.append("R_MOD_TIME,").append("R_CRE_ID,")
				.append("R_CRE_TIME from clta ").append("where ")
				.append("INST_ID=? and ").append("BRANCH_ID=? and ")
				.append("AC_TERM=? and ")
				.append("STAFF_ID=? and ").append("DEL_FLG='N'");

		logger.debug("select query :" + sql.toString());

		ClassTeacherAllotment classTeacherAllotment = (ClassTeacherAllotment) getJdbcTemplate()
				.query(sql.toString(), new PreparedStatementSetter() {

					@Override
					public void setValues(PreparedStatement ps)
							throws SQLException {
						ps.setString(1,
								classTeacherAllotmentKey.getInstId());
						ps.setString(2,
								classTeacherAllotmentKey.getBranchId());
						ps.setString(3, classTeacherAllotmentKey.getAcTerm());
						ps.setString(4, classTeacherAllotmentKey.getStaffId());
					}
				}, new ResultSetExtractor<ClassTeacherAllotment>() {

					@Override
					public ClassTeacherAllotment extractData(ResultSet rs)
							throws SQLException, DataAccessException {
						ClassTeacherAllotment classTeacher = null;
						if (rs.next()) {
							classTeacher = new ClassTeacherAllotment();
							classTeacher.setDbTs(rs.getInt("DB_TS"));
							classTeacher.setInstId(rs.getString("INST_ID"));
							classTeacher.setBranchId((rs.getString("BRANCH_ID")));
							classTeacher.setAcTerm(rs.getString("AC_TERM"));
							classTeacher.setCaSeqId(rs.getString("CA_SEQ_ID"));
							classTeacher.setStGroup(rs.getString("STUDENTGRP_ID"));
							classTeacher.setStBatchId((rs.getString("STUDENT_BATCH_ID")));
							classTeacher.setStaffId((rs.getString("STAFF_ID")));
							classTeacher.setTtgId((rs.getString("TTG_ID")));
							classTeacher.setDelFlag((rs.getString("DEL_FLG")));
							classTeacher.setrModId(rs.getString("R_MOD_ID"));
							classTeacher.setrModTime(rs.getString("R_MOD_TIME"));
							classTeacher.setrCreId(rs.getString("R_CRE_ID"));
							classTeacher.setrCreTime(rs.getString("R_CRE_TIME"));
						}
						return classTeacher;
					}

				});
		if (classTeacherAllotment == null) {
			throw new NoDataFoundException();
		}
		return classTeacherAllotment;
	}

	@Override
	public ClassTeacherAllotment selectStudentBatchForStaff(
			final ClassTeacherAllotmentKey classTeacherAllotmentKey,final String studentGrpId) {
		 logger.debug("Inside CLass teacher having student group  method");

			logger.debug("ClassTeacherAllotmentKey  object values :"
					+ classTeacherAllotmentKey.toString());
			logger.debug("Student group id values :"
					+ studentGrpId);
			StringBuffer sql = new StringBuffer();
			
			//sql.append("select exists(")
			sql.append("select CA_SEQ_ID,STUDENT_BATCH_ID ")
			.append("from clta")				
					.append(" where ")			
					.append(" INST_ID=?")
					.append(" and BRANCH_ID=?")
					.append(" and  AC_TERM=?")	
					.append(" and  STAFF_ID=?");					
					sql.append(" and  STUDENTGRP_ID=?")				
					.append(" and DEL_FLG='N'");
					
					
					logger.debug("select query :" + sql.toString());

					ClassTeacherAllotment classTeacherAllotment = (ClassTeacherAllotment) getJdbcTemplate()
							.query(sql.toString(), new PreparedStatementSetter() {

								@Override
								public void setValues(PreparedStatement ps)
										throws SQLException {
									ps.setString(1,classTeacherAllotmentKey.getInstId());
									ps.setString(2,classTeacherAllotmentKey.getBranchId());
									ps.setString(3, classTeacherAllotmentKey.getAcTerm());
									ps.setString(4, classTeacherAllotmentKey.getStaffId());
									ps.setString(5, studentGrpId);
								}
							}, new ResultSetExtractor<ClassTeacherAllotment>() {

								@Override
								public ClassTeacherAllotment extractData(ResultSet rs)
										throws SQLException, DataAccessException {
									ClassTeacherAllotment classTeacher = null;
									if (rs.next()) {
										classTeacher=new ClassTeacherAllotment();
										classTeacher.setStBatchId((rs.getString("STUDENT_BATCH_ID")));
										classTeacher.setCaSeqId((rs.getString("CA_SEQ_ID")));
										
									}
									return classTeacher;
								}

							});
					
					return classTeacherAllotment;
	 }
}

