package com.jaw.admin.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.batch.RuntimeExceptionForBatch;
import com.jaw.framework.dao.BaseDao;
import com.jaw.student.admission.dao.StudentMaster;

@Repository
public class StudentPromotionListDAO extends BaseDao implements
		IStudentPromotionListDAO {
	Logger logger = Logger.getLogger(StudentPromotionListDAO.class);

	@Override
	public List<StudentMaster> selectStudentDetainList(
			StudentPromotionKey studentPromotionKey)
			throws NoDataFoundException {
		logger.debug("retrive detain student  list");
		logger.info("studentPromotionKey key values :"
				+ studentPromotionKey.toString());
		List<Object> data = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer()

		.append("select ").append("stum.DB_TS,").append("stum.INST_ID,")
				.append("stum.BRANCH_ID,").append("ACADEMIC_YEAR,")
				.append("stum.STUDENT_ADMIS_NO,").append("COURSE,")
				.append("STANDARD,").append("SEC, ")
				.append("COURSE_VARIANT_CAT, ").append("COURSE_VARIANT, ")
				.append("STUDENT_TYPE, ").append("COMBINATION,")
				.append("STUDENT_NAME,").append("HOUSE_NAME,")
				.append("SECOND_LANG, ").append("THIRD_LANG, ")
				.append("ELECTIVE_1, ").append("ELECTIVE_2, ")
				.append("STUDENT_BATCH, ").append("LAB_BATCH, ")
				.append("REASON_FOR_LEAVING, ").append("RELIGIOUS_SUB, ")
				.append("TRANSFERRED, ").append("ACCOUNT_NO, ")
				.append("REG_NO,").append("MEDIUM,").append("TRANSFER_DATE")
				.append(" from stum,stdd ").append("where")
				.append(" stum.INST_ID=stdd.INST_ID")
				.append(" and stum.BRANCH_ID=stdd.BRANCH_ID")
				.append(" and stum.DEL_FLG=stdd.DEL_FLG")
				.append(" and stum.ACADEMIC_YEAR=stdd.AC_TERM ")
				.append(" and stum.STUDENT_ADMIS_NO=stdd.STUDENT_ADMIS_NO ")
				.append(" and STUDENTGRP_ID=? ").append(" and stum.INST_ID=? ")
				.append(" and  stum.BRANCH_ID=? ")
				.append(" and stum.DEL_FLG=? ")
				.append(" and stum.ACADEMIC_YEAR=? ");
		data.add(studentPromotionKey.getStudentGroupId().trim());
		data.add(studentPromotionKey.getInstId().trim());
		data.add(studentPromotionKey.getBranchId().trim());
		data.add("N");
		data.add(studentPromotionKey.getAcTerm());

		Object[] array = data.toArray(new Object[data.size()]);
		// data.add();

		logger.debug("select query :" + sql.toString());
		List<StudentMaster> selectedStudentMaster = null;
		selectedStudentMaster = getJdbcTemplate().query(sql.toString(), array,
				new StudentDetainListExtractor());
		System.out.println("Student detain list :"
				+ selectedStudentMaster.size());
		if (selectedStudentMaster.size() ==0) {
			throw new NoDataFoundException();
		}
		
		return selectedStudentMaster;
	}

	@Override
	public List<StudentMaster> selectStudentListForPromotion(
			StudentPromotionKey studentPromotionKey)
			throws NoDataFoundException {
		logger.debug("retrive  student  list");
		logger.info("studentPromotionKey key values :"
				+ studentPromotionKey.toString());
		List<Object> data = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();

		sql.append("select ").append("stum.STUDENT_ADMIS_NO, ")
				.append(" COURSE, ").append("STANDARD, ").append("stum.DB_TS,")
				.append("stum.INST_ID,").append("stum.BRANCH_ID,")
				.append("ACADEMIC_YEAR,").append("SEC, ")
				.append("COURSE_VARIANT_CAT, ").append("COURSE_VARIANT, ")
				.append("STUDENT_TYPE, ").append("COMBINATION,")
				.append("STUDENT_NAME,").append("HOUSE_NAME,")
				.append("SECOND_LANG, ").append("THIRD_LANG, ")
				.append("ELECTIVE_1, ").append("ELECTIVE_2, ")
				.append("STUDENT_BATCH, ").append("LAB_BATCH, ")
				.append("REASON_FOR_LEAVING, ").append("RELIGIOUS_SUB, ")
				.append("TRANSFERRED, ").append("ACCOUNT_NO, ")
				.append("REG_NO,").append("MEDIUM,").append("TRANSFER_DATE")
				.append(" from stum ").append("where")
				.append("  STUDENTGRP_ID=? ").append(" and stum.INST_ID=? ")
				.append(" and  stum.BRANCH_ID=? ")
				.append(" and stum.DEL_FLG=? ")
				.append(" and stum.ACADEMIC_YEAR=? ")
				.append(" and stum.STUDENT_ADMIS_NO not in  ")
				.append("(select ").append("STUDENT_ADMIS_NO ")
				.append(" from stdd ").append("where").append("  INST_ID=? ")
				.append(" and  BRANCH_ID=? ").append(" and DEL_FLG=? ")
				.append(" and AC_TERM=? )");
		data.add(studentPromotionKey.getStudentGroupId().trim());
		data.add(studentPromotionKey.getInstId().trim());
		data.add(studentPromotionKey.getBranchId().trim());
		data.add("N");
		data.add(studentPromotionKey.getAcTerm());
		data.add(studentPromotionKey.getInstId().trim());
		data.add(studentPromotionKey.getBranchId().trim());
		data.add("N");
		data.add(studentPromotionKey.getAcTerm());

		Object[] array = data.toArray(new Object[data.size()]);
		// data.add();

		logger.debug("select query :" + sql.toString());
		List<StudentMaster> selectedStudentMaster = null;
		selectedStudentMaster = getJdbcTemplate().query(sql.toString(), array,
				new StudentListExtractor());
		if (selectedStudentMaster.size()==0) {
			throw new NoDataFoundException();
		}
		System.out.println("Student promotion list :"
				+ selectedStudentMaster.size());

		return selectedStudentMaster;
	}

	@Override
	public void insertPromotedStudentMaster(
			final List<StudentMaster> studentMasterList) throws DuplicateEntryException {	
		System.out.println("Student list to insert into stum :"+studentMasterList.size());	
		StringBuffer sql = new StringBuffer("insert into stum(")
		.append("DB_TS,")
		.append("INST_ID,")
		.append("BRANCH_ID,")
		.append("ACADEMIC_YEAR,")
		.append("STUDENT_ADMIS_NO,")
		.append("ROLL_NUMBER,")
		.append("COURSE,")
		.append("STANDARD,")
		.append("SEC, ")
		.append("STUDENTGRP_ID, ")
		.append("COURSE_VARIANT_CAT, ")
		.append("COURSE_VARIANT, ")
		.append("STUDENT_TYPE, ")
		.append("COMBINATION,")
		.append("STUDENT_NAME,")				
		.append("HOUSE_NAME,")
		.append("SECOND_LANG, ")
		.append("THIRD_LANG, ")
		.append("ELECTIVE_1, ")
		.append("ELECTIVE_2, ")
		.append("STUDENT_BATCH, ")
		.append("LAB_BATCH, ")
		.append("REASON_FOR_LEAVING, ")
		.append("RELIGIOUS_SUB, ")
		.append("TRANSFERRED, ")
		.append("ACCOUNT_NO, ")
		.append("DEL_FLG, ")
		.append("R_MOD_ID, ")
		.append("R_MOD_TIME,")
		.append("R_CRE_ID,")
		.append("R_CRE_TIME,")
		.append("REG_NO,")
		.append("MEDIUM,")
		.append("TRANSFER_DATE")

		.append(" ) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,now(),?,now(),?,?,?)");

 try{

int[] a = getJdbcTemplate().batchUpdate(sql.toString(),
		new BatchPreparedStatementSetter() {

			@Override
			public void setValues(PreparedStatement ps, int i)
					throws SQLException {

				StudentMaster studentMaster = studentMasterList.get(i);

				ps.setInt(1, studentMaster.getDbTs());
				ps.setString(2, studentMaster.getInstId());
				ps.setString(3, studentMaster.getBranchId());					
				ps.setString(4, studentMaster.getAcademicYear());
				ps.setString(5, studentMaster.getStudentAdmisNo());
				ps.setInt(6,studentMaster.getRollno());
				ps.setString(7, studentMaster.getCourse());
				ps.setString(8, studentMaster.getStandard());
				ps.setString(9, studentMaster.getSec());
				ps.setString(10, studentMaster.getStuGrpId());
				ps.setString(11,studentMaster.getCourseVariantCat());
				ps.setString(12,studentMaster.getCourseVariant());
				ps.setString(13,studentMaster.getStudentType());
				ps.setString(14, studentMaster.getCombination());
				ps.setString(15, studentMaster.getStudentName());					
				ps.setString(16, studentMaster.getHouseName());
				ps.setString(17, studentMaster.getSecoundLang());
				ps.setString(18, studentMaster.getThirdLang());
				ps.setString(19, studentMaster.getElective1());
				ps.setString(20, studentMaster.getElective2());
				ps.setString(21,studentMaster.getStudentBatch());
				ps.setString(22,studentMaster.getLabBatch());
				ps.setString(23,studentMaster.getReasonForLeaving());
				ps.setString(24, studentMaster.getReligiousStudies());
				ps.setString(25, studentMaster.getTransfered());
				ps.setString(26, studentMaster.getAccountNo());
				ps.setString(27,"N");
				ps.setString(28, studentMaster.getrModId());

				ps.setString(29, studentMaster.getrCreId());

				ps.setString(30, studentMaster.getRegNo());
				ps.setString(31, studentMaster.getMedium());
				if((studentMaster.getTransferDate()!=null)&&(studentMaster.getTransferDate().equals(""))){
					ps.setString(32, null);
				}else{
					ps.setString(32, studentMaster.getTransferDate());
				}
				
			}

			@Override
			public int getBatchSize() {

				return studentMasterList.size();
			}
		}

);

 }catch(RuntimeException e){
	 e.printStackTrace();
	 logger.info("exception :"+e);
		throw new DuplicateEntryException();
	}		

}
}

class StudentListExtractor implements RowMapper<StudentMaster> {

	@Override
	public StudentMaster mapRow(ResultSet rs, int arg1) throws SQLException {
		StudentMaster studentMaster = new StudentMaster();
		studentMaster.setStudentAdmisNo(rs.getString("STUDENT_ADMIS_NO"));
		studentMaster.setCourse(rs.getString("COURSE"));
		studentMaster.setStandard(rs.getString("STANDARD"));
		studentMaster.setAcademicYear(rs.getString("ACADEMIC_YEAR"));
		studentMaster.setSec(rs.getString("SEC"));
		studentMaster.setCourseVariantCat(rs.getString("COURSE_VARIANT_CAT"));
		studentMaster.setCourseVariant(rs.getString("COURSE_VARIANT"));
		studentMaster.setStudentType(rs.getString("STUDENT_TYPE"));
		studentMaster.setCombination(rs.getString("COMBINATION"));
		studentMaster.setStudentName(rs.getString("STUDENT_NAME"));
		studentMaster.setHouseName(rs.getString("HOUSE_NAME"));
		studentMaster.setSecoundLang(rs.getString("SECOND_LANG"));
		studentMaster.setThirdLang(rs.getString("THIRD_LANG"));
		studentMaster.setElective1(rs.getString("ELECTIVE_1"));
		studentMaster.setElective2(rs.getString("ELECTIVE_2"));
		studentMaster.setLabBatch(rs.getString("LAB_BATCH"));
		studentMaster.setStudentBatch(rs.getString("STUDENT_BATCH"));
		studentMaster.setReasonForLeaving(rs.getString("REASON_FOR_LEAVING"));
		studentMaster.setReligiousStudies(rs.getString("RELIGIOUS_SUB"));
		studentMaster.setTransfered(rs.getString("TRANSFERRED"));
		studentMaster.setAccountNo(rs.getString("ACCOUNT_NO"));
		studentMaster.setRegNo(rs.getString("REG_NO"));
		studentMaster.setMedium(rs.getString("MEDIUM"));
		studentMaster.setTransferDate(rs.getString("TRANSFER_DATE"));

		return studentMaster;
	}
}

class StudentDetainListExtractor implements RowMapper<StudentMaster> {

	@Override
	public StudentMaster mapRow(ResultSet rs, int arg1) throws SQLException {
		StudentMaster studentMaster = new StudentMaster();
		studentMaster.setStudentAdmisNo(rs.getString("STUDENT_ADMIS_NO"));
		studentMaster.setCourse(rs.getString("COURSE"));
		studentMaster.setStandard(rs.getString("STANDARD"));
		studentMaster.setAcademicYear(rs.getString("ACADEMIC_YEAR"));
		studentMaster.setSec(rs.getString("SEC"));
		studentMaster.setCourseVariantCat(rs.getString("COURSE_VARIANT_CAT"));
		studentMaster.setCourseVariant(rs.getString("COURSE_VARIANT"));
		studentMaster.setStudentType(rs.getString("STUDENT_TYPE"));
		studentMaster.setCombination(rs.getString("COMBINATION"));
		studentMaster.setStudentName(rs.getString("STUDENT_NAME"));
		studentMaster.setHouseName(rs.getString("HOUSE_NAME"));
		studentMaster.setSecoundLang(rs.getString("SECOND_LANG"));
		studentMaster.setThirdLang(rs.getString("THIRD_LANG"));
		studentMaster.setElective1(rs.getString("ELECTIVE_1"));
		studentMaster.setElective2(rs.getString("ELECTIVE_2"));
		studentMaster.setLabBatch(rs.getString("LAB_BATCH"));
		studentMaster.setStudentBatch(rs.getString("STUDENT_BATCH"));
		studentMaster.setReasonForLeaving(rs.getString("REASON_FOR_LEAVING"));
		studentMaster.setReligiousStudies(rs.getString("RELIGIOUS_SUB"));
		studentMaster.setTransfered(rs.getString("TRANSFERRED"));
		studentMaster.setAccountNo(rs.getString("ACCOUNT_NO"));
		studentMaster.setRegNo(rs.getString("REG_NO"));
		studentMaster.setMedium(rs.getString("MEDIUM"));
		studentMaster.setTransferDate(rs.getString("TRANSFER_DATE"));

		return studentMaster;
	}
}
