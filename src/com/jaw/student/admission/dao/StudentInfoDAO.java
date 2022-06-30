package com.jaw.student.admission.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.InsertFailedException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.UpdateFailedException;
import com.jaw.framework.dao.BaseDao;

@Repository
public class StudentInfoDAO extends BaseDao implements IStudentInfo {

	Logger logger = Logger.getLogger(StudentInfoDAO.class);

	@Override
	public void insertStudentInfo(final StudentInfo studentInfo)
			throws /*DuplicateEntryException, InsertFailedException ,*/RuntimeException {		
		int noOfRowsAff = 0;
		StringBuffer sql = new StringBuffer("insert into stin(")
				.append("DB_TS,")
				.append("INST_ID,")
				.append("BRANCH_ID,")
				.append("ACADEMIC_YEAR,")
				.append("STUDENT_ADMIS_NO,")
				.append("ADMIS_DATE,")
				.append("FIRST_NAME,")
				.append("MIDDLE_NAME,")
				.append("LAST_NAME,")
				.append("DOB,")
				.append("GENDER,")
				.append("BLOOD_GROUP,")
				.append("ID_MARK1,")
				.append("ID_MARK2,")
				.append("MOBILE_NO,")
				.append("EMAIL,")
				.append("CASTE_CATEGORY,")
				.append("CASTE,")
				.append("RELIGION, ")
				.append("DEL_FLG, ")
				.append("R_MOD_ID, ")
				.append("R_MOD_TIME,")
				.append("R_CRE_ID,")
				.append("R_CRE_TIME,")
				.append("NATIONALITY,")
				.append("PLACE_OF_BIRTH,")
				.append("SUB_CASTE,")
				.append("ADMIS_CATEGORY,")				
				.append("MOTHER_TONGUE,")
				.append("REF_PERSON_NAME,")
				.append("FOREIGN_PASS_FLAG,")
				.append("PASS_NO,")
				.append("PASS_VALIDITY,")
				.append("PASS_PLACE_OF_ISSUE,")
				.append("TUT_COL_OR_SCH_STUDYING")
				
				.append(") values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,now(),?,now(),?,?,?,?,?,?,?,?,?,?,?)");

		try {
			noOfRowsAff = getJdbcTemplate().update(sql.toString(),
					new PreparedStatementSetter() {

						@Override
						public void setValues(PreparedStatement ps)
								throws SQLException {

							ps.setInt(1, 1);
							ps.setString(2, studentInfo.getInstId().trim());
							ps.setString(3, studentInfo.getBranchId().trim());
							ps.setString(4, studentInfo.getAcademicYear()
									.trim());
							ps.setString(5, studentInfo.getStudentAdmisNo()
									.trim());							
							if (studentInfo.getAdmisDate().equals("")) {
								ps.setString(6, null);

							} else {
								ps.setString(6, studentInfo.getAdmisDate());
							}
							ps.setString(7, studentInfo.getFirstName().trim());
							ps.setString(8, studentInfo.getMiddleName().trim());
							ps.setString(9, studentInfo.getLastName().trim());
							

							if (studentInfo.getDob().equals("")) {
								ps.setString(10, null);

							} else {
								ps.setString(10, studentInfo.getDob());
							}
							ps.setString(11, studentInfo.getGender().trim());
							ps.setString(12, studentInfo.getBloodGroup().trim());
							ps.setString(13, studentInfo.getIdMark1().trim());
							ps.setString(14, studentInfo.getIdMark2().trim());
							ps.setString(15, studentInfo.getMobileNo());
							ps.setString(16, studentInfo.getEmail().trim());
							ps.setString(17, studentInfo.getCasteCategory()
									.trim());
							ps.setString(18, studentInfo.getCaste().trim());
							ps.setString(19, studentInfo.getReligion().trim());
							ps.setString(20, "N");
							ps.setString(21, studentInfo.getrModId().trim());
							ps.setString(22, studentInfo.getrCreId().trim());
							ps.setString(23, studentInfo.getNationality()
									.trim());
							ps.setString(24, studentInfo.getPlaceOfBirth()
									.trim());
							ps.setString(25, studentInfo.getSubCaste().trim());
							ps.setString(26, studentInfo.getAdmisCategory()
									.trim());
							
							ps.setString(27, studentInfo.getMotherTongue()
									.trim());
							ps.setString(28, studentInfo.getRefPersonName()
									.trim());					
							if(studentInfo.getForeignPassFlg()==null){
								ps.setString(29, "N");
							}else{
								ps.setString(29, studentInfo.getForeignPassFlg()
										.trim());
							}
							
							ps.setString(30, studentInfo.getPassportNo()
									.trim());
							ps.setString(31, studentInfo.getPassportValidity()
									.trim());
							ps.setString(32, studentInfo.getPass_placeOfIssue()
									.trim());
							ps.setString(33, studentInfo.getTutOrColStudying()
									.trim());
							

						}

					});		
		} catch (RuntimeException e) {
			throw new RuntimeException();
		} 
	}

	@Override
	public void updateStudentInfo(final StudentInfo studentInfo,
			final StudentInfoKey studentInfoKey) throws UpdateFailedException {

		logger.debug("Inside update method");

		StringBuffer sql = new StringBuffer();
		sql.append("update stin set ").append("DB_TS=?,")
				.append("ACADEMIC_YEAR=?,").append("ADMIS_DATE=?,")
				.append("FIRST_NAME=?,").append("MIDDLE_NAME=?,")
				.append("LAST_NAME=?,").append("DOB=?,").append("GENDER=?,")
				.append("BLOOD_GROUP=?,")
				.append("ID_MARK1=?,").append("ID_MARK2=?,")
				.append("MOBILE_NO=?,").append("EMAIL=?,")
				.append("CASTE_CATEGORY=?,").append("CASTE=?,")
				.append("RELIGION=?, ").append("R_MOD_ID=?, ")
				.append("R_MOD_TIME=now(),").append("NATIONALITY=?,")
				.append("PLACE_OF_BIRTH=?,").append("SUB_CASTE=?,")
				.append("ADMIS_CATEGORY=?,")
				
				.append("MOTHER_TONGUE=?,")
				.append("REF_PERSON_NAME=?,")
				.append("FOREIGN_PASS_FLAG=?,")
				.append("PASS_NO=?,")
				.append("PASS_VALIDITY=?,")
				.append("PASS_PLACE_OF_ISSUE=?,")
				.append("TUT_COL_OR_SCH_STUDYING=?")				
				
				.append(" where")
				.append(" INST_ID= ?").append(" and ")
				.append(" BRANCH_ID= ?").append(" and ")
				.append(" STUDENT_ADMIS_NO= ?").append(" and ").append(" DB_TS= ?")
				.append(" and ").append(" DEL_FLG='N'");
		
			int updateStatus =getJdbcTemplate().update(sql.toString(),
					new PreparedStatementSetter() {

						@Override
						public void setValues(PreparedStatement ps)
								throws SQLException {
							ps.setInt(1, studentInfo.getDbTs() + 1);
							ps.setString(2, studentInfo.getAcademicYear()
									.trim());
							logger.info("staff details :"
									+ studentInfo.getAdmisDate());

							if (studentInfo.getAdmisDate().equals("")) {
								ps.setString(3, null);

							} else {
								ps.setString(3, studentInfo.getAdmisDate());
							}
							ps.setString(4, studentInfo.getFirstName().trim());
							ps.setString(5, studentInfo.getMiddleName().trim());
							ps.setString(6, studentInfo.getLastName().trim());
							logger.info("staff details :" + studentInfo.getDob());

							if (studentInfo.getDob().equals("")) {
								ps.setString(7, null);

							} else {
								ps.setString(7, studentInfo.getDob());
							}
							ps.setString(8, studentInfo.getGender().trim());
							ps.setString(9, studentInfo.getBloodGroup().trim());
							ps.setString(10, studentInfo.getIdMark1().trim());
							ps.setString(11, studentInfo.getIdMark2().trim());
							ps.setString(12, studentInfo.getMobileNo());
							ps.setString(13, studentInfo.getEmail().trim());
							ps.setString(14, studentInfo.getCasteCategory()
									.trim());
							ps.setString(15, studentInfo.getCaste().trim());
							ps.setString(16, studentInfo.getReligion().trim());
							ps.setString(17, studentInfo.getrModId().trim());
							ps.setString(18, studentInfo.getNationality()
									.trim());
							ps.setString(19, studentInfo.getPlaceOfBirth()
									.trim());
							ps.setString(20, studentInfo.getSubCaste());
							ps.setString(21, studentInfo.getAdmisCategory());
							
							ps.setString(22, studentInfo.getMotherTongue());
							ps.setString(23, studentInfo.getRefPersonName());
							if(studentInfo.getForeignPassFlg()==null){
								ps.setString(24, "N");
							}else{
								ps.setString(24, studentInfo.getForeignPassFlg());
							}
						
							ps.setString(25, studentInfo.getPassportNo());
							ps.setString(26, studentInfo.getPassportValidity());
							ps.setString(27, studentInfo.getPass_placeOfIssue());
							ps.setString(28, studentInfo.getTutOrColStudying());
							
							
							
							
							ps.setString(29, studentInfoKey.getInstId()
									.trim());
							ps.setString(30, studentInfoKey.getBranchId().trim());
							ps.setString(31, studentInfoKey.getStudentAdmisNo().trim());
							ps.setInt(32, studentInfo.getDbTs());
						}

					});
			if (updateStatus == 0) {
			
					throw new UpdateFailedException();
				
			}

		
		logger.debug("update query :" + sql.toString());

	}

	@Override
	public StudentInfo retriveStudentInfo(final StudentInfoKey studentInfoKey) throws NoDataFoundException {

		logger.debug("retrive StudentInfo Details :"+studentInfoKey);
	
		StringBuffer sql = new StringBuffer().append("select ")
				.append("DB_TS,").append("INST_ID,").append("BRANCH_ID,")
				.append("ACADEMIC_YEAR,").append("STUDENT_ADMIS_NO,")
				.append("ADMIS_DATE,").append("ADMITTED_TO_CLASS,")
				.append("FIRST_NAME,").append("MIDDLE_NAME,")
				.append("LAST_NAME,").append("DOB,").append("GENDER,")
				.append("BLOOD_GROUP,").append("ID_MARK1,").append("ID_MARK2,")
				.append("MOBILE_NO,").append("EMAIL,")
				.append("CASTE_CATEGORY,").append("CASTE,")
				.append("RELIGION, ").append("FEE_PAY_TYPE, ")
				.append("DEL_FLG, ").append("R_MOD_ID, ").append("R_MOD_TIME,")
				.append("R_CRE_ID,").append("R_CRE_TIME,")
				.append("NATIONALITY,").append("PLACE_OF_BIRTH,")
				.append("SUB_CASTE,")
				.append("ADMIS_CATEGORY,")
				.append("MOTHER_TONGUE,")
				.append("REF_PERSON_NAME,")
				.append("FOREIGN_PASS_FLAG,")
				.append("PASS_NO,")
				.append("PASS_VALIDITY,")
				.append("PASS_PLACE_OF_ISSUE,")
				.append("TUT_COL_OR_SCH_STUDYING")									
				.append(" from stin ").append("where")
				.append(" INST_ID =?").append(" and ")
				.append(" BRANCH_ID =?").append(" and ")
				.append(" STUDENT_ADMIS_NO =?").append(" and ")
				.append(" DEL_FLG=?");
		logger.debug("select query :" + sql.toString());
		StudentInfo studentInfoResult = null;
		studentInfoResult = getJdbcTemplate().query(sql.toString(),
				new PreparedStatementSetter() {

					@Override
					public void setValues(PreparedStatement pss)
							throws SQLException {
						pss.setString(1, studentInfoKey.getInstId().trim());
						pss.setString(2, studentInfoKey.getBranchId().trim());
						pss.setString(3, studentInfoKey.getStudentAdmisNo().trim());
						pss.setString(4, "N");
					}
				}, new StudentInfoResultSetExtractor());	
		if(studentInfoResult==null){
			throw new NoDataFoundException();
		}
		logger.debug("Student info Object :"+studentInfoResult);
		return studentInfoResult;
	}				

			
}

class StudentInfoResultSetExtractor implements ResultSetExtractor<StudentInfo> {

	@Override
	public StudentInfo extractData(ResultSet rs) throws SQLException,
			DataAccessException {
		StudentInfo studentInfo = null;
		if (rs.next()) {
			studentInfo = new StudentInfo();
			studentInfo.setDbTs(rs.getInt("DB_TS"));
			studentInfo.setInstId(rs.getString("INST_ID"));
			studentInfo.setBranchId(rs.getString("BRANCH_ID"));
			studentInfo.setAcademicYear(rs.getString("ACADEMIC_YEAR"));
			studentInfo.setStudentAdmisNo(rs.getString("STUDENT_ADMIS_NO"));
			studentInfo.setAdmisDate(rs.getString("ADMIS_DATE"));
			studentInfo.setAdmittedToClass(rs.getString("ADMITTED_TO_CLASS"));
			studentInfo.setFirstName(rs.getString("FIRST_NAME"));
			studentInfo.setMiddleName(rs.getString("MIDDLE_NAME"));
			studentInfo.setLastName(rs.getString("LAST_NAME"));
			studentInfo.setDob(rs.getString("DOB"));
			studentInfo.setGender(rs.getString("GENDER"));
			studentInfo.setBloodGroup(rs.getString("BLOOD_GROUP"));
			studentInfo.setIdMark1(rs.getString("ID_MARK1"));
			studentInfo.setIdMark2(rs.getString("ID_MARK2"));
			studentInfo.setMobileNo(rs.getString("MOBILE_NO"));
			studentInfo.setEmail(rs.getString("EMAIL"));
			studentInfo.setCasteCategory(rs.getString("CASTE_CATEGORY"));
			studentInfo.setCaste(rs.getString("CASTE"));
			studentInfo.setReligion(rs.getString("RELIGION"));
			studentInfo.setFeePayType(rs.getString("FEE_PAY_TYPE"));
			studentInfo.setDelFlg(rs.getString("DEL_FLG"));
			studentInfo.setrModId(rs.getString("R_MOD_ID"));
			studentInfo.setrModTime(rs.getString("R_MOD_TIME"));
			studentInfo.setrCreId(rs.getString("R_CRE_ID"));
			studentInfo.setrCreTime(rs.getString("R_CRE_TIME"));
			studentInfo.setNationality(rs.getString("NATIONALITY"));
			studentInfo.setPlaceOfBirth(rs.getString("PLACE_OF_BIRTH"));
			studentInfo.setSubCaste(rs.getString("SUB_CASTE"));
			studentInfo.setAdmisCategory(rs.getString("ADMIS_CATEGORY"));
			
			studentInfo.setMotherTongue(rs.getString("MOTHER_TONGUE"));
			studentInfo.setRefPersonName(rs.getString("REF_PERSON_NAME"));
			studentInfo.setForeignPassFlg(rs.getString("FOREIGN_PASS_FLAG"));
			studentInfo.setPassportNo(rs.getString("PASS_NO"));
			studentInfo.setPassportValidity(rs.getString("PASS_VALIDITY"));
			studentInfo.setPass_placeOfIssue(rs.getString("PASS_PLACE_OF_ISSUE"));
			studentInfo.setTutOrColStudying(rs.getString("TUT_COL_OR_SCH_STUDYING"));
		}
		return studentInfo;
	}

}
