package com.jaw.student.admission.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.batch.RuntimeExceptionForBatch;
import com.jaw.common.util.DateUtil;
import com.jaw.framework.dao.BaseDao;
@Service
public class StudentInfoListDao extends BaseDao implements IStudentInfoListDao {
	Logger logger = Logger.getLogger(StudentInfoListDao.class);
	DateUtil dateUtil = new DateUtil();
	@Override
	public void insertStudentInfoList(final List<StudentInfo> studentInfoList) throws RuntimeExceptionForBatch {
		
		 StringBuffer sql = new StringBuffer("insert into stin(")
			.append("DB_TS,")
			.append("INST_ID,")
			.append("BRANCH_ID,")
			.append("ACADEMIC_YEAR,")
			.append("STUDENT_ADMIS_NO,")
			.append("ADMIS_DATE,")
			.append("ADMITTED_TO_CLASS,")
			.append("FIRST_NAME,")
			.append("MIDDLE_NAME,")
			.append("LAST_NAME,")
			.append("DOB,")
			.append("GENDER,")
			.append("BIRTH_CERTI_VER,")
			.append("BLOOD_GROUP,")
			.append("ID_MARK1,")
			.append("ID_MARK2,")
			.append("MOBILE_NO,")
			.append("EMAIL,")
			.append("CASTE_CATEGORY,")
			.append("CASTE,")
			.append("RELIGION, ") 
			.append("FEE_PAY_TYPE, ")
			.append("DEL_FLG, ")
			.append("R_MOD_ID, ")	
			.append("R_MOD_TIME,")
			.append("R_CRE_ID,")
			.append("R_CRE_TIME ,")
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
			.append(" ) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,now(),?,now(),?,?,?,?,?,?,?,?,?,?,?)");			
			try{
			 int[] a= getJdbcTemplate().batchUpdate(sql.toString(), new BatchPreparedStatementSetter() {			
				@Override
				public void setValues(PreparedStatement ps, int i) throws SQLException {
					
					StudentInfo studentInfo = studentInfoList.get(i);				
					ps.setInt(1,studentInfo.getDbTs());
					ps.setString(2,studentInfo.getInstId());
					ps.setString(3,studentInfo.getBranchId());
					ps.setString(4,studentInfo.getAcademicYear());
					ps.setString(5,studentInfo.getStudentAdmisNo());				
						if (studentInfo.getAdmisDate().equals("")) {
							ps.setString(6, null);

						} else {
							ps.setString(6, studentInfo.getAdmisDate());
						}
					
					ps.setString(7,studentInfo.getAdmittedToClass());
					ps.setString(8,studentInfo.getFirstName());
					ps.setString(9,studentInfo.getMiddleName());
					ps.setString(10,studentInfo.getLastName());						

						if (studentInfo.getDob().equals("")) {
							ps.setString(11, null);

						} else {
							ps.setString(11, studentInfo.getDob());
						}
					ps.setString(12,studentInfo.getGender());
					ps.setString(13,studentInfo.getBirthCertiVer());
					ps.setString(14,studentInfo.getBloodGroup());
					ps.setString(15,studentInfo.getIdMark1());
					ps.setString(16,studentInfo.getIdMark2());					
					ps.setString(17,studentInfo.getMobileNo());
					ps.setString(18,studentInfo.getEmail());
					ps.setString(19,studentInfo.getCasteCategory());
					ps.setString(20,studentInfo.getCaste());
					ps.setString(21,studentInfo.getReligion());
					ps.setString(22,studentInfo.getFeePayType());
					ps.setString(23,"N");
					ps.setString(24,studentInfo.getrModId());		
					ps.setString(25,studentInfo.getrCreId());		
					ps.setString(26,studentInfo.getNationality());
					ps.setString(27,studentInfo.getPlaceOfBirth());
					ps.setString(28,studentInfo.getSubCaste());
					ps.setString(29,studentInfo.getAdmisCategory());
					ps.setString(30, studentInfo.getMotherTongue()
							.trim());
					ps.setString(31, studentInfo.getRefPersonName()
							.trim());
					ps.setString(32, studentInfo.getForeignPassFlg()
							.trim());
					ps.setString(33, studentInfo.getPassportNo()
							.trim());
					ps.setString(34, studentInfo.getPassportValidity()
							.trim());
					ps.setString(35, studentInfo.getPass_placeOfIssue()
							.trim());
					ps.setString(36, studentInfo.getTutOrColStudying()
							.trim());
					
				
					
				}
			 
				@Override
				public int getBatchSize() {
					return studentInfoList.size();
				}
			  }
			
					 );		
			}catch(RuntimeException e){
				e.printStackTrace();
				throw new RuntimeExceptionForBatch();
			}

	}
	@Override
	public List<StudentInfo> retriveStudentInfoList() throws NoDataFoundException {
		
		logger.debug("retrive StudentInfo List");
		StringBuffer sql=new StringBuffer()
		.append("select ")
		.append("DB_TS,")
				.append("INST_ID,")
				.append("BRANCH_ID,")
				.append("ACADEMIC_YEAR,")
				.append("STUDENT_ADMIS_NO,")
				.append("ADMIS_DATE,")
				.append("ADMITTED_TO_CLASS,")
				.append("FIRST_NAME,")
				.append("MIDDLE_NAME,")
				.append("LAST_NAME,")
				.append("DOB,")
				.append("GENDER,")
				.append("BIRTH_CERTI_VER,")
				.append("BLOOD_GROUP,")
				.append("ID_MARK1,")
				.append("ID_MARK2,")
				.append("MOBILE_NO,")
				.append("EMAIL,")
				.append("CASTE_CATEGORY,")
				.append("CASTE,")
				.append("RELIGION, ") 
				.append("FEE_PAY_TYPE, ")
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
				.append(" from stin ")
		.append("where")
		.append(" DEL_FLG =?")
		.append(" ORDER BY STUDENT_ADMIS_NO");
		logger.debug("select query :"+sql.toString());
		List<StudentInfo> studentInfoList = getJdbcTemplate().query(sql.toString(), new PreparedStatementSetter(){

			@Override
			public void setValues(PreparedStatement pss)
					throws SQLException {
				pss.setString(1,"N");						
				
			}
			
		},new StudentInfoRowMapper() );
		if(studentInfoList.size()==0){
			throw new NoDataFoundException();
		}
																		
		return studentInfoList;	
}
class StudentInfoRowMapper implements RowMapper<StudentInfo>{
	@Override
	public StudentInfo mapRow(ResultSet rs, int arg1) throws SQLException {
		StudentInfo studentInfo=new StudentInfo();
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
		studentInfo.setBirthCertiVer(rs.getString("BIRTH_CERTI_VER"));
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
		return studentInfo;
	
	}
	
}
}
