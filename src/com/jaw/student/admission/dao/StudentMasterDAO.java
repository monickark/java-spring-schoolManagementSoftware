package com.jaw.student.admission.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.UpdateFailedException;
import com.jaw.framework.dao.BaseDao;
import com.jaw.student.admission.controller.AdmissionDetailsVO;

@Repository
public class StudentMasterDAO extends BaseDao implements IStudentMasterDao {
	Logger logger = Logger.getLogger(StudentMasterDAO.class);

	@Override
	public String getNextAdmisNo (AdmissionDetailsVO admissionDetailsVO) {
		
		String sql="select student_admis_no+1 from stum where inst_id='"+ 
		admissionDetailsVO.getInstId()+"' and Branch_Id= '"+ admissionDetailsVO.getBranchId()+
		"' and Academic_year='"+ admissionDetailsVO.getAcademicYear()+"' order by student_admis_no  desc limit 1";

		String studentAdmisNO = getJdbcTemplate().queryForObject(sql.toString(),
						String.class);
				System.out.println("studentAdmisNO:" + studentAdmisNO);
						
		return studentAdmisNO;

		}

	@Override
	public void insertStudentMaster(final StudentMaster studentMaster)
			throws DuplicateEntryException {
		final StringBuffer sql = new StringBuffer("insert into stum(")

				.append("DB_TS,")
				.append("INST_ID,")
				.append("BRANCH_ID,")
				.append("ACADEMIC_YEAR,")
				.append("STUDENT_ADMIS_NO,")
				.append("COURSE,")
				.append("STANDARD,")
				.append("SEC, ")
				.append("COURSE_VARIANT_CAT, ")
				.append("COURSE_VARIANT, ")
				.append("STUDENT_TYPE, ")
				.append("COMBINATION,")
				.append("STUDENT_NAME,")
				.append("SECOND_LANG, ")
				.append("THIRD_LANG, ")
				.append("ELECTIVE_1, ")
				.append("ELECTIVE_2, ")
				.append("STUDENT_BATCH, ")
				.append("LAB_BATCH, ")
				.append("REASON_FOR_LEAVING, ")
				.append("RELIGIOUS_SUB, ")
				.append("DEL_FLG, ")
				.append("R_MOD_ID, ")
				.append("R_MOD_TIME, ")
				.append("R_CRE_ID, ")
				.append("R_CRE_TIME,")
				.append("REG_NO,")
				.append("MEDIUM, ")
				.append("STUDENTGRP_ID,")				
				.append("ROLL_NUMBER")
				.append(" ) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,now(),?,now(),?,?,?,?)");
		try {
			getJdbcTemplate().update(sql.toString(),
					new PreparedStatementSetter() {

						@Override
						public void setValues(PreparedStatement rs)
								throws SQLException {
							rs.setInt(1, 1);
							rs.setString(2, studentMaster.getInstId().trim());
							rs.setString(3, studentMaster.getBranchId().trim());
							rs.setString(4, studentMaster.getAcademicYear()
									.trim());
							rs.setString(5, studentMaster.getStudentAdmisNo()
									.trim());
							rs.setString(6, studentMaster.getCourse().trim());
							rs.setString(7, studentMaster.getStandard().trim());
							rs.setString(8, studentMaster.getSec().trim());
							rs.setString(9, studentMaster.getCourseVariantCat()
									.trim());
							rs.setString(10, studentMaster.getCourseVariant()
									.trim());
							rs.setString(11, studentMaster.getStudentType()
									.trim());
							rs.setString(12, studentMaster.getCombination()
									.trim());
							rs.setString(13, studentMaster.getStudentName()
									.trim().toUpperCase());
							rs.setString(14, studentMaster.getSecoundLang()
									.trim());
							rs.setString(15, studentMaster.getThirdLang()
									.trim());
							rs.setString(16, studentMaster.getElective1()
									.trim());
							rs.setString(17, studentMaster.getElective2()
									.trim());
							rs.setString(18, studentMaster.getStudentBatch()
									.trim());
							rs.setString(19, studentMaster.getLabBatch().trim());
							rs.setString(20, studentMaster
									.getReasonForLeaving().trim());
							rs.setString(21, studentMaster
									.getReligiousStudies().trim());
							rs.setString(22, "N");
							rs.setString(23, studentMaster.getrModId().trim());
							rs.setString(24, studentMaster.getrCreId().trim());
							rs.setString(25, studentMaster.getRegNo().trim());
							rs.setString(26, studentMaster.getMedium().trim());
							rs.setString(27, studentMaster.getStuGrpId());
							rs.setInt(28, studentMaster.getRollno());

						}
					});

		} catch (org.springframework.dao.DuplicateKeyException e) {
			logger.debug("dupliacate entry");
			throw new DuplicateEntryException();
		}

	}

	@Override
	public void updateStudentMaster(final StudentMaster studentMaster,
			final StudentMasterKey studentMasterKey)
			throws UpdateFailedException {

		logger.debug("Inside update method");
		logger.debug("StudentMasterKey :" + studentMasterKey);
		StringBuffer sql = new StringBuffer();
		sql.append("update stum set ").append("DB_TS=?,")
				.append("ACADEMIC_YEAR=?,").append("COURSE=?,")
				.append("STANDARD=?,").append("SEC=?, ")
				.append("COURSE_VARIANT_CAT=?, ").append("COURSE_VARIANT=?, ")
				.append("STUDENT_TYPE=?, ").append("COMBINATION=?,")
				.append("HOUSE_NAME=?,").append("SECOND_LANG=?, ")
				.append("THIRD_LANG=?, ").append("ELECTIVE_1=?, ")
				.append("ELECTIVE_2=?, ").append("STUDENT_BATCH=?, ")
				.append("LAB_BATCH=?, ").append("REASON_FOR_LEAVING=?, ")
				.append("RELIGIOUS_SUB=?, ").append("R_MOD_ID=?, ")
				.append("R_MOD_TIME=now(), ").append("REG_NO=?,")
				.append("MEDIUM=?").append(" where").append(" INST_ID= ?")
				.append(" and ").append(" BRANCH_ID= ?").append(" and ")
				.append(" STUDENT_ADMIS_NO= ?").append(" and ")
				.append(" ACADEMIC_YEAR= ?").append(" and ")
				.append(" DB_TS= ?").append(" and ").append(" DEL_FLG='N'");

		int updateStatus = getJdbcTemplate().update(sql.toString(),
				new PreparedStatementSetter() {

					@Override
					public void setValues(PreparedStatement rs)
							throws SQLException {

						rs.setInt(1, studentMaster.getDbTs() + 1);
						rs.setString(2, studentMaster.getAcademicYear().trim());
						rs.setString(3, studentMaster.getCourse().trim());
						rs.setString(4, studentMaster.getStandard().trim());
						rs.setString(5, studentMaster.getSec().trim());
						rs.setString(6, studentMaster.getCourseVariantCat()
								.trim());
						rs.setString(7, studentMaster.getCourseVariant().trim());
						rs.setString(8, studentMaster.getStudentType().trim());
						rs.setString(9, studentMaster.getCombination().trim());
						rs.setString(10, studentMaster.getHouseName().trim());
						rs.setString(11, studentMaster.getSecoundLang().trim());
						rs.setString(12, studentMaster.getThirdLang().trim());
						rs.setString(13, studentMaster.getElective1().trim());
						rs.setString(14, studentMaster.getElective2().trim());
						rs.setString(15, studentMaster.getStudentBatch().trim());
						rs.setString(16, studentMaster.getLabBatch().trim());
						rs.setString(17, studentMaster.getReasonForLeaving()
								.trim());
						rs.setString(18, studentMaster.getReligiousStudies()
								.trim());
						rs.setString(19, studentMaster.getrModId().trim());
						rs.setString(20, studentMaster.getRegNo().trim());
						rs.setString(21, studentMaster.getMedium().trim());
						rs.setString(22, studentMasterKey.getInstId().trim());
						rs.setString(23, studentMasterKey.getBranchId().trim());
						rs.setString(24, studentMasterKey.getStudentAdmisNo()
								.trim());
						rs.setString(25, studentMasterKey.getAcademicYear()
								.trim());
						rs.setInt(26, studentMaster.getDbTs());

					}

				});

		if (updateStatus == 0) {
			throw new UpdateFailedException();

		}

		logger.debug("update query :" + sql.toString());

	}

	@Override
	public StudentMaster retriveStudentDetails(
			final StudentMasterKey studentMasterKey)
			throws NoDataFoundException {
		logger.debug("retrive stum");
		logger.info("stum key values :" + studentMasterKey.toString());
		List<Object> data = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer().append("select ")
				.append("stum.DB_TS,").append("stum.INST_ID,")
				.append("stum.BRANCH_ID,").append("ACADEMIC_YEAR,")
				.append("STUDENT_ADMIS_NO,").append("ROLL_NUMBER,").append("COURSE,")
				.append("STANDARD,").append("SEC, ")
				.append("COURSE_VARIANT_CAT, ").append("COURSE_VARIANT, ")
				.append("STUDENT_TYPE, ").append("COMBINATION,")
				.append("STUDENT_NAME,").append("HOUSE_NAME,")
				.append("SECOND_LANG, ").append("SUB_NAME, ")
				.append("THIRD_LANG, ").append("ELECTIVE_1, ")
				.append("ELECTIVE_2, ").append("STUDENT_BATCH, ")
				.append("LAB_BATCH, ").append("REASON_FOR_LEAVING, ")
				.append("RELIGIOUS_SUB, ").append("TRANSFERRED, ")
				.append("ACCOUNT_NO, ")
				.append("stum.DEL_FLG, ")
				.append("stum.R_MOD_ID, ")
				.append("stum.R_MOD_TIME, ")
				.append("stum.R_CRE_ID, ")
				.append("stum.R_CRE_TIME,")
				.append("REG_NO,")
				.append("MEDIUM,")
				.append("TRANSFER_DATE")
				.append(" from stum left join sbjm on")
				// .append("where")
				.append(" stum.INST_ID = sbjm.INST_ID AND")
				.append(" stum.BRANCH_ID = sbjm.BRANCH_ID AND")
				.append(" stum.DEL_FLG = sbjm.DEL_FLG AND")
				.append(" stum.SECOND_LANG = sbjm.SUB_ID ").append("where")
				.append(" stum.INST_ID =?").append(" and ")
				.append(" stum.BRANCH_ID =?").append(" and ")
				.append(" STUDENT_ADMIS_NO =?").append(" and ")
				/*
				 * .append(" ACADEMIC_YEAR= ?") .append(" and ")
				 */
				.append(" stum.DEL_FLG=?");

		data.add(studentMasterKey.getInstId().trim());
		data.add(studentMasterKey.getBranchId().trim());
		data.add(studentMasterKey.getStudentAdmisNo().trim());
		data.add("N");
		if ((studentMasterKey.getAcademicYear() != null)
				&& (!studentMasterKey.getAcademicYear().equals(""))) {
			sql.append(" and ACADEMIC_YEAR=?  ");
			data.add(studentMasterKey.getAcademicYear());
		}
		Object[] array = data.toArray(new Object[data.size()]);
		// data.add();

		logger.debug("select query :" + sql.toString());
		StudentMaster studentMasterResult = null;
		studentMasterResult = (StudentMaster) getJdbcTemplate().query(
				sql.toString(), array, new StudentMasterResultSetExtractor());
		if (studentMasterResult == null) {
			throw new NoDataFoundException();
		}
		return studentMasterResult;
	}

	@Override
	public StudentMaster retriveStuMasterRec(final String userID)
			throws NoDataFoundException {
		logger.info("stum key values :" + userID);
		logger.debug("retrive StudentDetails");
		StringBuffer sql = new StringBuffer().append("select ")
				.append("DB_TS,").append("INST_ID,").append("BRANCH_ID,")
				.append("ACADEMIC_YEAR,").append("STUDENT_ADMIS_NO,")
				.append("COURSE,").append("STANDARD,").append("SEC, ")
				.append("COURSE_VARIANT_CAT, ").append("COURSE_VARIANT, ")
				.append("STUDENT_TYPE, ").append("COMBINATION,")
				.append("STUDENT_NAME,").append("HOUSE_NAME,")
				.append("SECOND_LANG, ").append("THIRD_LANG, ")
				.append("ELECTIVE_1, ").append("ELECTIVE_2, ")
				.append("STUDENT_BATCH, ").append("LAB_BATCH, ")
				.append("REASON_FOR_LEAVING, ").append("RELIGIOUS_SUB, ")
				.append("TRANSFERRED, ").append("ACCOUNT_NO, ")
				.append("DEL_FLG, ").append("R_MOD_ID, ")
				.append("R_MOD_TIME, ").append("R_CRE_ID, ")
				.append("R_CRE_TIME,").append("REG_NO,").append("MEDIUM,")
				.append("TRANSFER_DATE").append(" from stum ").append("where")
				.append(" STUDENT_ADMIS_NO =?").append(" and ")
				.append(" DEL_FLG=?");
		logger.debug("select query :" + sql.toString());
		StudentMaster studentMasterResult = null;
		studentMasterResult = (StudentMaster) getJdbcTemplate().query(
				sql.toString(), new PreparedStatementSetter() {

					@Override
					public void setValues(PreparedStatement pss)
							throws SQLException {
						pss.setString(1, userID);
						pss.setString(2, "N");
					}
				}, new StudentMasterResultSetExtractor());
		if (studentMasterResult == null) {
			throw new NoDataFoundException();
		}

		return studentMasterResult;
	}

	@Override
	public String getStuGrpIdForSecList(final String admisNo,
			final String instId, final String branchId) {

		logger.info("stum key values : Admis No-" + admisNo + " Inst Id-"
				+ instId + " Branch Id-" + branchId);
		logger.debug("retrive StudentDetails");
		StringBuffer sql = new StringBuffer().append("SELECT ")
				.append("STUDENTGRP_ID").append(" FROM stum ").append("WHERE")
				.append(" STUDENT_ADMIS_NO =?").append(" AND INST_ID=? ")
				.append(" AND BRANCH_ID=? ").append(" AND DEL_FLG=? ");
		logger.debug("select query :" + sql.toString());
		String stuGRpId = null;
		stuGRpId = getJdbcTemplate().query(sql.toString(),
				new PreparedStatementSetter() {

					@Override
					public void setValues(PreparedStatement pss)
							throws SQLException {
						pss.setString(1, admisNo);
						pss.setString(2, instId);
						pss.setString(3, branchId);
						pss.setString(4, "N");
					}
				}, new ResultSetExtractor<String>() {

					@Override
					public String extractData(ResultSet rs)
							throws SQLException, DataAccessException {
						String stuGrpId = null;
						if (rs.next()) {
							stuGrpId = rs.getString("STUDENTGRP_ID");
						}
						return stuGrpId;
					}

				});

		return stuGRpId;
	}

}

class StudentMasterResultSetExtractor implements
		ResultSetExtractor<StudentMaster> {
	@Override
	public StudentMaster extractData(ResultSet rs) throws SQLException {
		StudentMaster studentMaster = null;
		if (rs.next()) {
			studentMaster = new StudentMaster();
			studentMaster.setDbTs(rs.getInt("DB_TS"));
			studentMaster.setInstId(rs.getString("INST_ID").trim());
			studentMaster.setBranchId(rs.getString("BRANCH_ID").trim());
			studentMaster.setAcademicYear(rs.getString("ACADEMIC_YEAR").trim());
			studentMaster.setStudentAdmisNo(rs.getString("STUDENT_ADMIS_NO")
					.trim());
			studentMaster.setCourse(rs.getString("COURSE").trim());
			studentMaster.setStandard(rs.getString("STANDARD"));
			studentMaster.setSec(rs.getString("SEC").trim());
			studentMaster.setRollno(rs.getInt("ROLL_NUMBER"));
			studentMaster.setCourseVariantCat(rs
					.getString("COURSE_VARIANT_CAT"));
			studentMaster.setCourseVariant(rs.getString("COURSE_VARIANT"));
			studentMaster.setStudentType(rs.getString("STUDENT_TYPE"));
			studentMaster.setCombination(rs.getString("COMBINATION"));
			studentMaster.setStudentName(rs.getString("STUDENT_NAME").trim());
			studentMaster.setHouseName(rs.getString("HOUSE_NAME"));
			studentMaster.setSecoundLang(rs.getString("SECOND_LANG"));
			studentMaster.setSecLangDesc(rs.getString("SUB_NAME"));
			studentMaster.setThirdLang(rs.getString("THIRD_LANG"));
			studentMaster.setElective1(rs.getString("ELECTIVE_1"));
			studentMaster.setElective2(rs.getString("ELECTIVE_2"));
			studentMaster.setStudentBatch(rs.getString("STUDENT_BATCH"));
			studentMaster.setLabBatch(rs.getString("LAB_BATCH"));
			studentMaster.setReasonForLeaving(rs
					.getString("REASON_FOR_LEAVING"));
			studentMaster.setReligiousStudies(rs.getString("RELIGIOUS_SUB"));
			studentMaster.setTransfered(rs.getString("TRANSFERRED"));
			studentMaster.setAccountNo(rs.getString("ACCOUNT_NO"));
			studentMaster.setDelFlg(rs.getString("DEL_FLG"));
			studentMaster.setrModId(rs.getString("R_MOD_ID"));
			studentMaster.setrModTime(rs.getString("R_MOD_TIME"));
			studentMaster.setrCreId(rs.getString("R_CRE_ID"));
			studentMaster.setrCreTime(rs.getString("R_CRE_TIME"));
			studentMaster.setRegNo(rs.getString("REG_NO"));
			studentMaster.setMedium(rs.getString("MEDIUM"));
			studentMaster.setTransferDate(rs.getString("TRANSFER_DATE"));
		}
		return studentMaster;
	}

}
