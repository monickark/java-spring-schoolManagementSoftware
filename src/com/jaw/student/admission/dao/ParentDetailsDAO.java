package com.jaw.student.admission.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.framework.dao.BaseDao;

@Repository
public class ParentDetailsDAO extends BaseDao implements
		IParentDetails {
	Logger logger = Logger.getLogger(ParentDetailsDAO.class);

	@Override
	public void insertParentDetails(final ParentDetails parentDetails) throws  DuplicateEntryException {

		StringBuffer sql = new StringBuffer("insert into pard(")
				.append("DB_TS, INST_ID, BRANCH_ID, PARENT_ID,")
				.append("STUDENT_ADMIS_NO, FATHER_SALUT, ")
				.append("FATHER_NAME, FATHER_RELIGION, FATHER_MOTHERTONGUE, ")
				.append("FATHER_QUAL, FATHER_OCCU_DES, ")
				.append("FATHER_ANNUAL_INCOME, FATHER_MOBILE_N0, FATHER_EMAIL, ")
				.append("FATHER_COMPANY, FATHER_OFF_ADD1, FATHER_OFF_ADD2,FATHER_OFF_ADD3, ")
				.append("FATHER_CITY, FATHER_STATE, ")
				.append("FATHER_PINCODE, FATHER_OFF_TELE, FATHER_OLD_STUDENT, ")
				.append("FATHER_PASSED_OUT, MOTHER_SALUT, MOTHER_NAME, ")
				.append("MOTHER_RELIGION, MOTHER_MOTHERTONGUE,")
				.append("MOTHER_QUAL, MOTHER_OCC_DES, MOTHER_ANNUAL_INCOME, ")
				.append("MOTHER_MOBILE_NO, MOTHER_EMAIL, MOTHER_COMPANY, ")
				.append("MOTHER_OFF_ADD1, MOTHER_OFF_ADD2,MOTHER_OFF_ADD3, ")
				.append("MOTHER_CITY, MOTHER_STATE, MOTHER_PINCODE, ")
				.append("MOTHER_OFF_TEL, MOTHER_OLD_STUDENT, MOTHER_PASSED_OUT, ")
				.append("LOCAL_GUARDIAN, GUARDIAN_SALUT, GUARDIAN_NAME, ")
				.append("GUARDIAN_ADD1, GUARDIAN_ADD2,GUARDIAN_ADD3, ")
				.append("GUARDIAN_CITY, GUARDIAN_STATE, ")
				.append("GUARDIAN_PINCODE, GUARDIAN_LANDLINE_NO, GUARDIAN_MOBILE_NO, ")
				.append("DEL_FLG, R_MOD_ID, R_MOD_TIME, R_CRE_ID, R_CRE_TIME,NO_OF_CHILD_BOYS, NO_OF_CHILD_GIRLS,")
				.append("RELATIONSHIP, GUARDIAN_OCC_DES, GUARDIAN_ANNUAL_INCOME, GUARDIAN_EMAIL")
				.append(") values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,")
				.append("?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,now(),?,now(),?,?,?,?,?,?)");

				

		try {
			getJdbcTemplate().update(sql.toString(),new PreparedStatementSetter(){

				@Override
				public void setValues(PreparedStatement ps)
						throws SQLException {
					ps.setInt(1,1);
					ps.setString(2,parentDetails.getInstId().trim());
					ps.setString(3,parentDetails.getBranchId().trim());
					ps.setString(4,parentDetails.getParentId().trim());
					ps.setString(5,parentDetails.getStudentAdmisNo().trim());
					ps.setString(6,parentDetails.getFatherSalut().trim());
					ps.setString(7,parentDetails.getFatherName().trim().toUpperCase());
					ps.setString(8,parentDetails.getFatherReligion().trim());
					ps.setString(9,parentDetails.getFatherMothertongue().trim());
					ps.setString(10,parentDetails.getFatherQual().trim());
					ps.setString(11,parentDetails.getFatherOccuDes().trim());
					ps.setString(12,parentDetails.getFatherAnnualIncome());
					ps.setString(13,parentDetails.getFatherMobileNo().trim());
					ps.setString(14,parentDetails.getFatherEmail().trim());
					ps.setString(15,parentDetails.getFatherCompany().trim());
					ps.setString(16,parentDetails.getFatherOffAdd1().trim());
					ps.setString(17,parentDetails.getFatherOffAdd2().trim());
					ps.setString(18,parentDetails.getFatherOffAdd3().trim());
					ps.setString(19,parentDetails.getFatherCity().trim());
					ps.setString(20,parentDetails.getFatherState().trim());
					ps.setString(21,parentDetails.getFatherPincode());
					ps.setString(22,parentDetails.getFatherOffTele());
					ps.setString(23,parentDetails.getFatherOldStudent().trim());
					ps.setString(24,parentDetails.getFatherPassedOut().trim());
					ps.setString(25,parentDetails.getMotherSalut().trim());
					ps.setString(26,parentDetails.getMotherName().trim().toUpperCase());
					ps.setString(27,parentDetails.getMotherReligion().trim());
					ps.setString(28,parentDetails.getMotherMothertongue().trim());
					ps.setString(29,parentDetails.getMotherQual().trim());
					ps.setString(30,parentDetails.getMotherOccDes().trim());
					ps.setString(31,parentDetails.getMotherAnnualIncome());
					ps.setString(32,parentDetails.getMotherMobileNo());
					ps.setString(33,parentDetails.getMotherEmail().trim());
					ps.setString(34,parentDetails.getMotherCompany().trim());
					ps.setString(35,parentDetails.getMotherOffAdd1().trim());
					ps.setString(36,parentDetails.getMotherOffAdd2().trim());
					ps.setString(37,parentDetails.getMotherOffAdd3().trim());
					ps.setString(38,parentDetails.getMotherCity().trim());
					ps.setString(39,parentDetails.getMotherState().trim());
					ps.setString(40,parentDetails.getMotherPincode());
					ps.setString(41,parentDetails.getMotherOffTele());
					ps.setString(42,parentDetails.getMotherOldStudent().trim());
					ps.setString(43,parentDetails.getMotherPassedOut().trim());
					ps.setString(44,parentDetails.getLocalGuardian().trim());
					ps.setString(45,parentDetails.getGuardianSalut().trim());
					ps.setString(46,parentDetails.getGuardianName().trim().toUpperCase());
					ps.setString(47,parentDetails.getGuardianAdd1().trim());
					ps.setString(48,parentDetails.getGuardianAdd2().trim());
					ps.setString(49,parentDetails.getGuardianAdd3().trim());
					ps.setString(50,parentDetails.getGuardianCity().trim());
					ps.setString(51,parentDetails.getGuardianState().trim());
					ps.setString(52,parentDetails.getGuardianPincode());
					ps.setString(53,parentDetails.getGuardianLandlineNo());
					ps.setString(54,parentDetails.getGuardianMobileNo());
					ps.setString(55,"N");
					ps.setString(56,parentDetails.getrModId().trim());
					ps.setString(57,parentDetails.getrCreId().trim());
					ps.setString(58,parentDetails.getNoOfChildBoys().trim());
					ps.setString(59,parentDetails.getNoOfChildGirls().trim());
					
					ps.setString(60,parentDetails.getRelationShip().trim());
					ps.setString(61,parentDetails.getGuardOccDes().trim());
					ps.setString(62,parentDetails.getGuardAnnualIncome().trim());
					ps.setString(63,parentDetails.getGuardEmail().trim());
					
					
				}
				
			});
		} catch (DuplicateKeyException e) {
			e.printStackTrace();
			logger.debug("Runtime exception has been occured");
			throw new DuplicateEntryException();
		} 		
	}

	

	@Override
	public ParentDetails retriveParentDetails(final ParentDetailsKey parentDetailsKey) throws NoDataFoundException {
			logger.debug("ParentDetailsKey object :"+parentDetailsKey);
		StringBuffer sql=new StringBuffer()
				.append("select ")
				.append("DB_TS, INST_ID, BRANCH_ID, PARENT_ID,")
				.append("STUDENT_ADMIS_NO, FATHER_SALUT, ")
				.append("FATHER_NAME, FATHER_RELIGION, FATHER_MOTHERTONGUE, ")
				.append("FATHER_QUAL, FATHER_OCCU_DES, ")
				.append("FATHER_ANNUAL_INCOME, FATHER_MOBILE_N0, FATHER_EMAIL, ")
				.append("FATHER_COMPANY, FATHER_OFF_ADD1, FATHER_OFF_ADD2, ")
				.append("FATHER_OFF_ADD3, FATHER_CITY, FATHER_STATE, ")
				.append("FATHER_PINCODE, FATHER_OFF_TELE, FATHER_OLD_STUDENT, ")
				.append("FATHER_PASSED_OUT, MOTHER_SALUT, MOTHER_NAME, ")
				.append("MOTHER_RELIGION, MOTHER_MOTHERTONGUE,")
				.append("MOTHER_QUAL, MOTHER_OCC_DES, MOTHER_ANNUAL_INCOME, ")
				.append("MOTHER_MOBILE_NO, MOTHER_EMAIL, MOTHER_COMPANY, ")
				.append("MOTHER_OFF_ADD1, MOTHER_OFF_ADD2, MOTHER_OFF_ADD3, ")
				.append("MOTHER_CITY, MOTHER_STATE, MOTHER_PINCODE, ")
				.append("MOTHER_OFF_TEL, MOTHER_OLD_STUDENT, MOTHER_PASSED_OUT, ")
				.append("LOCAL_GUARDIAN, GUARDIAN_SALUT, GUARDIAN_NAME, ")
				.append("GUARDIAN_ADD1, GUARDIAN_ADD2, ")
				.append("GUARDIAN_ADD3, GUARDIAN_CITY, GUARDIAN_STATE, ")
				.append("GUARDIAN_PINCODE, GUARDIAN_LANDLINE_NO, GUARDIAN_MOBILE_NO, ")
				.append("DEL_FLG, R_MOD_ID, R_MOD_TIME, R_CRE_ID, R_CRE_TIME,NO_OF_CHILD_BOYS, NO_OF_CHILD_GIRLS, ")
				.append("RELATIONSHIP, GUARDIAN_OCC_DES, GUARDIAN_ANNUAL_INCOME, GUARDIAN_EMAIL")
				.append(" from pard ")
				.append("where ")
				.append("INST_ID=?")
				.append(" and ")
				.append("BRANCH_ID=?")
				.append(" and ")
				.append("STUDENT_ADMIS_NO=?")
				.append(" and ")
				/*.append("PARENT_ID=?")
				.append(" and ")*/
				.append(" DEL_FLG=?");
		ParentDetails parentDetailsResult =null;
		
		 parentDetailsResult=getJdbcTemplate().query(sql.toString(),new PreparedStatementSetter() {

			@Override
			public void setValues(PreparedStatement pss)
					throws SQLException {							
				pss.setString(1, parentDetailsKey.getInstId().trim());
				pss.setString(2, parentDetailsKey.getBranchId().trim());
				pss.setString(3, parentDetailsKey.getStudentAdmisNo().trim());
			//	pss.setString(4, parentDetailsKey.getParentId().trim());
				pss.setString(4, "N");

			}

		}, new ParentDetailsResultSetExtractor());
		 if(parentDetailsResult==null){
			 throw new NoDataFoundException();
		 }

		return parentDetailsResult;	
	}
	
	@Override
	public void updateParentDetails(final ParentDetails parentDetails,
			final ParentDetailsKey parentDetailsKey) {

		StringBuffer sql = new StringBuffer();
		sql.append("update pard set ")
				.append("DB_TS =?,")
				.append("FATHER_SALUT=?, ")
				.append("FATHER_NAME=?, FATHER_RELIGION=?, FATHER_MOTHERTONGUE=?, ")
				.append("FATHER_QUAL=?, FATHER_OCCU_DES=?, ")
				.append("FATHER_ANNUAL_INCOME=?, FATHER_MOBILE_N0=?, FATHER_EMAIL=?, ")
				.append("FATHER_COMPANY=?, FATHER_OFF_ADD1=?, FATHER_OFF_ADD2=?, ")
				.append("FATHER_OFF_ADD3=?, FATHER_CITY=?, FATHER_STATE=?, ")
				.append("FATHER_PINCODE=?, FATHER_OFF_TELE=?, FATHER_OLD_STUDENT=?, ")
				.append("FATHER_PASSED_OUT=?, MOTHER_SALUT=?, MOTHER_NAME=?, ")
				.append("MOTHER_RELIGION=?, MOTHER_MOTHERTONGUE=?,")
				.append("MOTHER_QUAL=?, MOTHER_OCC_DES=?, MOTHER_ANNUAL_INCOME=?, ")
				.append("MOTHER_MOBILE_NO=?, MOTHER_EMAIL=?, MOTHER_COMPANY=?, ")
				.append("MOTHER_OFF_ADD1=?, MOTHER_OFF_ADD2=?, MOTHER_OFF_ADD3=?, ")
				.append("MOTHER_CITY=?, MOTHER_STATE=?, MOTHER_PINCODE=?, ")
				.append("MOTHER_OFF_TEL=?, MOTHER_OLD_STUDENT=?, MOTHER_PASSED_OUT=?, ")
				.append("LOCAL_GUARDIAN=?, GUARDIAN_SALUT=?, GUARDIAN_NAME=?, ")
				.append("GUARDIAN_ADD1=?, GUARDIAN_ADD2=?, ")
				.append("GUARDIAN_ADD3=?, GUARDIAN_CITY=?, GUARDIAN_STATE=?, ")
				.append("GUARDIAN_PINCODE=?, GUARDIAN_LANDLINE_NO=?, GUARDIAN_MOBILE_NO=?, ")
				.append("R_MOD_ID=?,R_MOD_TIME=now(),NO_OF_CHILD_BOYS=?, NO_OF_CHILD_GIRLS=?,")
				.append("RELATIONSHIP =?, GUARDIAN_OCC_DES=?, GUARDIAN_ANNUAL_INCOME=?, GUARDIAN_EMAIL=?")
				.append(" where")
				.append(" INST_ID= ?")
				.append(" and ")
				.append(" BRANCH_ID= ?")
				.append(" and ")
				.append(" STUDENT_ADMIS_NO= ?")
				.append(" and ")
				/*.append(" PARENT_ID= ?")
				.append(" and ")*/
				.append(" DB_TS= ?")
				.append(" and ")
				.append(" DEL_FLG='N'");

		getJdbcTemplate().update(sql.toString(), new PreparedStatementSetter() {

			@Override
			public void setValues(PreparedStatement ps) throws SQLException {

				ps.setInt(1,parentDetails.getDbTs()+1);
				ps.setString(2,parentDetails.getFatherSalut().trim());
				ps.setString(3,parentDetails.getFatherName().trim().toUpperCase());
				ps.setString(4,parentDetails.getFatherReligion().trim());
				ps.setString(5,parentDetails.getFatherMothertongue().trim());
				ps.setString(6,parentDetails.getFatherQual().trim());
				ps.setString(7,parentDetails.getFatherOccuDes().trim());
				ps.setString(8,parentDetails.getFatherAnnualIncome());
				ps.setString(9,parentDetails.getFatherMobileNo().trim());
				ps.setString(10,parentDetails.getFatherEmail().trim());
				ps.setString(11,parentDetails.getFatherCompany().trim());
				ps.setString(12,parentDetails.getFatherOffAdd1().trim());
				ps.setString(13,parentDetails.getFatherOffAdd2().trim());
				ps.setString(14,parentDetails.getFatherOffAdd3().trim());
				ps.setString(15,parentDetails.getFatherCity().trim());
				ps.setString(16,parentDetails.getFatherState().trim());
				ps.setString(17,parentDetails.getFatherPincode());
				ps.setString(18,parentDetails.getFatherOffTele());
				ps.setString(19,parentDetails.getFatherOldStudent().trim());
				ps.setString(20,parentDetails.getFatherPassedOut().trim());
				ps.setString(21,parentDetails.getMotherSalut().trim());
				ps.setString(22,parentDetails.getMotherName().trim().toUpperCase());
				ps.setString(23,parentDetails.getMotherReligion().trim());
				ps.setString(24,parentDetails.getMotherMothertongue().trim());
				ps.setString(25,parentDetails.getMotherQual().trim());
				ps.setString(26,parentDetails.getMotherOccDes().trim());
				ps.setString(27,parentDetails.getMotherAnnualIncome());
				ps.setString(28,parentDetails.getMotherMobileNo());
				ps.setString(29,parentDetails.getMotherEmail().trim());
				ps.setString(30,parentDetails.getMotherCompany().trim());
				ps.setString(31,parentDetails.getMotherOffAdd1().trim());
				ps.setString(32,parentDetails.getMotherOffAdd2().trim());
				ps.setString(33,parentDetails.getMotherOffAdd3().trim());
				ps.setString(34,parentDetails.getMotherCity().trim());
				ps.setString(35,parentDetails.getMotherState().trim());
				ps.setString(36,parentDetails.getMotherPincode());
				ps.setString(37,parentDetails.getMotherOffTele());
				ps.setString(38,parentDetails.getMotherOldStudent().trim());
				ps.setString(39,parentDetails.getMotherPassedOut().trim());
				ps.setString(40,parentDetails.getLocalGuardian().trim());
				ps.setString(41,parentDetails.getGuardianSalut().trim());
				ps.setString(42,parentDetails.getGuardianName().trim().toUpperCase());
				ps.setString(43,parentDetails.getGuardianAdd1().trim());
				ps.setString(44,parentDetails.getGuardianAdd2().trim());
				ps.setString(45,parentDetails.getGuardianAdd3().trim());
				ps.setString(46,parentDetails.getGuardianCity().trim());
				ps.setString(47,parentDetails.getGuardianState().trim());
				ps.setString(48,parentDetails.getGuardianPincode());
				ps.setString(49,parentDetails.getGuardianLandlineNo());
				ps.setString(50,parentDetails.getGuardianMobileNo());
				ps.setString(51,parentDetails.getrModId().trim());
				ps.setString(52,parentDetails.getNoOfChildBoys().trim());
				ps.setString(53,parentDetails.getNoOfChildGirls().trim());
				ps.setString(54,parentDetails.getRelationShip().trim());
				ps.setString(55,parentDetails.getGuardOccDes().trim());
				ps.setString(56,parentDetails.getGuardAnnualIncome().trim());
				ps.setString(57,parentDetails.getGuardEmail().trim());												
				ps.setString(58,parentDetailsKey.getInstId().trim());
				ps.setString(59,parentDetailsKey.getBranchId().trim());
				ps.setString(60,parentDetailsKey.getStudentAdmisNo().trim());
			//	ps.setString(57,parentDetailsKey.getParentId().trim());
				ps.setInt(61,parentDetails.getDbTs());
			}

		});
	
		logger.debug("update query :" + sql.toString());

	}
	@Override
	public ParentDetails retriveParentSess(final String userId) throws NoDataFoundException {
		
		logger.debug("retrive ParentDetails");
		ParentDetails parentDetailsResult = null;
	
		StringBuffer sql=new StringBuffer()
				.append("select ")
				.append("DB_TS, INST_ID, BRANCH_ID, PARENT_ID,")
				.append("STUDENT_ADMIS_NO, FATHER_SALUT, ")
				.append("FATHER_NAME, FATHER_RELIGION, FATHER_MOTHERTONGUE, ")
				.append("FATHER_QUAL, FATHER_OCCU_DES, ")
				.append("FATHER_ANNUAL_INCOME, FATHER_MOBILE_N0, FATHER_EMAIL, ")
				.append("FATHER_COMPANY, FATHER_OFF_ADD1, FATHER_OFF_ADD2, ")
				.append("FATHER_OFF_ADD3, FATHER_CITY, FATHER_STATE, ")
				.append("FATHER_PINCODE, FATHER_OFF_TELE, FATHER_OLD_STUDENT, ")
				.append("FATHER_PASSED_OUT, MOTHER_SALUT, MOTHER_NAME, ")
				.append("MOTHER_RELIGION, MOTHER_MOTHERTONGUE,")
				.append("MOTHER_QUAL, MOTHER_OCC_DES, MOTHER_ANNUAL_INCOME, ")
				.append("MOTHER_MOBILE_NO, MOTHER_EMAIL, MOTHER_COMPANY, ")
				.append("MOTHER_OFF_ADD1, MOTHER_OFF_ADD2, MOTHER_OFF_ADD3, ")
				.append("MOTHER_CITY, MOTHER_STATE, MOTHER_PINCODE, ")
				.append("MOTHER_OFF_TEL, MOTHER_OLD_STUDENT, MOTHER_PASSED_OUT, ")
				.append("LOCAL_GUARDIAN, GUARDIAN_SALUT, GUARDIAN_NAME, ")
				.append("GUARDIAN_ADD1, GUARDIAN_ADD2, ")
				.append("GUARDIAN_ADD3, GUARDIAN_CITY, GUARDIAN_STATE, ")
				.append("GUARDIAN_PINCODE, GUARDIAN_LANDLINE_NO, GUARDIAN_MOBILE_NO, ")
				.append("DEL_FLG, R_MOD_ID, R_MOD_TIME, R_CRE_ID, R_CRE_TIME,NO_OF_CHILD_BOYS, NO_OF_CHILD_GIRLS, ")
				.append("RELATIONSHIP, GUARDIAN_OCC_DES, GUARDIAN_ANNUAL_INCOME, GUARDIAN_EMAIL")
				.append(" from pard ")
				.append("where ")
				.append("PARENT_ID=?")
				.append(" and ")
				.append(" DEL_FLG=?");
		logger.debug("select query :"+sql.toString());
		parentDetailsResult=getJdbcTemplate().query(sql.toString(),new PreparedStatementSetter() {

			@Override
			public void setValues(PreparedStatement pss)
					throws SQLException {
				pss.setString(1, userId);
				pss.setString(2, "N");

			}

		}, new ParentDetailsResultSetExtractor());
		if(parentDetailsResult==null){
			throw new NoDataFoundException();
		}

		return parentDetailsResult;	
}



	

}
class ParentDetailsResultSetExtractor implements ResultSetExtractor<ParentDetails>{
@Override
public ParentDetails extractData(ResultSet rs) throws SQLException, DataAccessException {
	ParentDetails parentDetails=null;
	if(rs.next()){
		parentDetails=new ParentDetails();
	parentDetails.setDbTs(rs.getInt("DB_TS"));
	parentDetails.setInstId(rs.getString("INST_ID"));
	parentDetails.setBranchId(rs.getString("BRANCH_ID"));
	parentDetails.setParentId(rs.getString("PARENT_ID"));
	parentDetails.setStudentAdmisNo(rs.getString("STUDENT_ADMIS_NO"));
	parentDetails.setFatherSalut(rs.getString("FATHER_SALUT"));
	parentDetails.setFatherName(rs.getString("FATHER_NAME"));
	parentDetails.setFatherReligion(rs.getString("FATHER_RELIGION"));
	parentDetails.setFatherMothertongue(rs.getString("FATHER_MOTHERTONGUE"));
	parentDetails.setFatherQual(rs.getString("FATHER_QUAL"));
	parentDetails.setFatherOccuDes(rs.getString("FATHER_OCCU_DES"));
	parentDetails.setFatherAnnualIncome(rs.getString("FATHER_ANNUAL_INCOME"));
	parentDetails.setFatherMobileNo(rs.getString("FATHER_MOBILE_N0"));
	parentDetails.setFatherEmail(rs.getString("FATHER_EMAIL"));
	parentDetails.setFatherCompany(rs.getString("FATHER_COMPANY"));
	parentDetails.setFatherOffAdd1(rs.getString("FATHER_OFF_ADD1"));
	parentDetails.setFatherOffAdd2(rs.getString("FATHER_OFF_ADD2"));
	parentDetails.setFatherOffAdd3(rs.getString("FATHER_OFF_ADD3"));
	parentDetails.setFatherCity(rs.getString("FATHER_CITY"));
	parentDetails.setFatherState(rs.getString("FATHER_STATE"));
	parentDetails.setFatherPincode(rs.getString("FATHER_PINCODE"));
	parentDetails.setFatherOffTele(rs.getString("FATHER_OFF_TELE"));
	parentDetails.setFatherOldStudent(rs.getString("FATHER_OLD_STUDENT"));
	parentDetails.setFatherPassedOut(rs.getString("FATHER_PASSED_OUT"));
	parentDetails.setMotherSalut(rs.getString("MOTHER_SALUT"));
	parentDetails.setMotherName(rs.getString("MOTHER_NAME"));
	parentDetails.setMotherReligion(rs.getString("MOTHER_RELIGION"));
	parentDetails.setMotherMothertongue(rs.getString("MOTHER_MOTHERTONGUE"));
	parentDetails.setMotherQual(rs.getString("MOTHER_QUAL"));
	parentDetails.setMotherOccDes(rs.getString("MOTHER_OCC_DES"));
	parentDetails.setMotherAnnualIncome(rs.getString("MOTHER_ANNUAL_INCOME"));
	parentDetails.setMotherMobileNo(rs.getString("MOTHER_MOBILE_NO"));
	parentDetails.setMotherEmail(rs.getString("MOTHER_EMAIL"));
	parentDetails.setMotherCompany(rs.getString("MOTHER_COMPANY"));
	parentDetails.setMotherOffAdd1(rs.getString("MOTHER_OFF_ADD1"));
	parentDetails.setMotherOffAdd2(rs.getString("MOTHER_OFF_ADD2"));
	parentDetails.setMotherOffAdd3(rs.getString("MOTHER_OFF_ADD3"));
	parentDetails.setMotherCity(rs.getString("MOTHER_CITY"));
	parentDetails.setMotherState(rs.getString("MOTHER_STATE"));
	parentDetails.setMotherPincode(rs.getString("MOTHER_PINCODE"));
	parentDetails.setMotherOffTele(rs.getString("MOTHER_OFF_TEL"));
	parentDetails.setMotherOldStudent(rs.getString("MOTHER_OLD_STUDENT"));
	parentDetails.setMotherPassedOut(rs.getString("MOTHER_PASSED_OUT"));
	parentDetails.setLocalGuardian(rs.getString("LOCAL_GUARDIAN"));
	parentDetails.setGuardianSalut(rs.getString("GUARDIAN_SALUT"));
	parentDetails.setGuardianName(rs.getString("GUARDIAN_NAME"));
	parentDetails.setGuardianAdd1(rs.getString("GUARDIAN_ADD1"));
	parentDetails.setGuardianAdd2(rs.getString("GUARDIAN_ADD2"));
	parentDetails.setGuardianAdd3(rs.getString("GUARDIAN_ADD3"));
	parentDetails.setGuardianCity(rs.getString("GUARDIAN_CITY"));
	parentDetails.setGuardianState(rs.getString("GUARDIAN_STATE"));
	parentDetails.setGuardianPincode(rs.getString("GUARDIAN_PINCODE"));
	parentDetails.setGuardianLandlineNo(rs.getString("GUARDIAN_LANDLINE_NO"));
	parentDetails.setGuardianMobileNo(rs.getString("GUARDIAN_MOBILE_NO"));
	parentDetails.setDelFlg(rs.getString("DEL_FLG"));
	parentDetails.setrModId(rs.getString("R_MOD_ID"));
	parentDetails.setrModTime(rs.getString("R_MOD_TIME"));
	parentDetails.setrCreId(rs.getString("R_CRE_ID"));
	parentDetails.setrCreTime(rs.getString("R_CRE_TIME"));
	parentDetails.setNoOfChildBoys(rs.getString("NO_OF_CHILD_BOYS"));
	parentDetails.setNoOfChildGirls(rs.getString("NO_OF_CHILD_GIRLS"));	
	
	parentDetails.setRelationShip(rs.getString("RELATIONSHIP"));
	parentDetails.setGuardOccDes(rs.getString("GUARDIAN_OCC_DES"));	
	parentDetails.setGuardAnnualIncome(rs.getString("GUARDIAN_ANNUAL_INCOME"));	
	parentDetails.setGuardEmail(rs.getString("GUARDIAN_EMAIL"));	
	}
	return parentDetails;
}
}
