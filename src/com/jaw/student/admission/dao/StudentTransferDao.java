package com.jaw.student.admission.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.UpdateFailedException;
import com.jaw.framework.dao.BaseDao;
import com.jaw.student.controller.StudentUpdateController;

@Repository
public class StudentTransferDao extends BaseDao implements IStudentTransferDao {
	// Logging
		Logger logger = Logger.getLogger(StudentTransferDao.class);
	@Override
	public String feeDueCheckForStuTan(final StuTranKey stuTranKey) throws NoDataFoundException {
		logger.info("StuTranKey values :"+stuTranKey);
		logger.debug("inside feedue check dao");
		/*SELECT SUM(FEE_DUE_AMT) FROM SFDD
		WHERE INST_ID= 'ASC' AND BRANCH_ID = 'BR001' AND AC_TERM = 'AT3' AND DEL_FLG = 'N' AND STUDENT_ADMIS_NO='13-14-060';*/
		
		StringBuffer sql=new StringBuffer()
		.append("SELECT ")
		.append("SUM(FEE_DUE_AMT) ")
				.append("FROM sfdd ")
				.append("WHERE INST_ID=?")
				.append(" AND BRANCH_ID = ?")
				.append(" AND AC_TERM =?")
				.append(" AND DEL_FLG = ?")
				.append(" AND STUDENT_ADMIS_NO=?");
				
		logger.debug("select query :"+sql.toString());
		String feeDue= null;
		feeDue=(String) getJdbcTemplate().query(sql.toString(), new PreparedStatementSetter() {

			@Override
			public void setValues(PreparedStatement pss)
					throws SQLException {
				pss.setString(1, stuTranKey.getInstId());
				pss.setString(2, stuTranKey.getBranchId());
				pss.setString(3, stuTranKey.getAcTrm());
				pss.setString(4, "N");
				pss.setString(5, stuTranKey.getStuAdmisNo());
			}
		}, new ResultSetExtractor<String>(){

			@Override
			public String extractData(ResultSet rs) throws SQLException,
					DataAccessException {
				String fee = null;
				while(rs.next()){
					fee = rs.getString("SUM(FEE_DUE_AMT)");
				}
				return fee;
			}
			
		});
		 if(feeDue== null){
			 throw new NoDataFoundException();
		 }
		
	return feeDue;
	}
	@Override
	public void stuTransfer(final StuTranKey stuTranKey,final StudentMaster stuMas) throws UpdateFailedException {
		/*UPDATE STUM SET DB_TS='3',TRANSFERRED='Y', TRANSFER_DATE='2014-03-30', DEL_FLG='T', 
				R_MOD_ID='SU',R_MOD_TIME=NOW() WHERE INST_ID= 'ASC' AND BRANCH_ID = 'BR001' AND 
				ACADEMIC_YEAR = 'AT3' AND DEL_FLG = 'N' AND STUDENT_ADMIS_NO='13-14-060';*/

		logger.debug("Inside update method");
		logger.debug("StuTranKey :"+stuTranKey);
		logger.debug("StuTransfer :"+stuMas);
		
		StringBuffer sql = new StringBuffer();
		sql.append("UPDATE stum SET ")
				.append("DB_TS=?,")
				.append("TRANSFERRED=?,")
				.append("TRANSFER_DATE=?,")
				.append("DEL_FLG=?,")
				.append("R_MOD_ID=?, ")
				.append("R_MOD_TIME=now() ")				
				.append(" WHERE")
				.append(" INST_ID= ?")
				.append(" AND ")
				.append(" BRANCH_ID= ?")
				.append(" AND ")
				.append(" STUDENT_ADMIS_NO= ?")
				.append(" AND ")
				.append(" ACADEMIC_YEAR= ?")
				.append(" AND ")
				.append(" DB_TS= ?")
				.append(" AND ")
				.append(" DEL_FLG='N'");

		int updateStatus =	getJdbcTemplate().update(sql.toString(), new PreparedStatementSetter() {

			@Override
			public void setValues(PreparedStatement rs) throws SQLException {

				rs.setInt(1,Integer.valueOf(stuMas.getDbTs())+1);
				rs.setString(2,stuMas.getTransfered().trim());
				rs.setString(3,stuMas.getTransferDate().trim());
				rs.setString(4,stuMas.getDelFlg());
				rs.setString(5,stuMas.getrModId());
				rs.setString(6,stuTranKey.getInstId().trim());
				rs.setString(7,stuTranKey.getBranchId().trim());
				rs.setString(8,stuTranKey.getStuAdmisNo().trim());
				rs.setString(9,stuTranKey.getAcTrm().trim());				
				rs.setInt(10,Integer.valueOf(stuMas.getDbTs()));				
			}

		});
	
		if (updateStatus == 0) {
			throw new UpdateFailedException();

		}

		
		logger.debug("update query :" + sql.toString());

		
		
		
		
		
		
	}
	@Override
	public StudentMaster getStuTransferRec(final StuTranKey stuTranKey) throws NoDataFoundException {
		logger.debug("retrive student transfer records");
		logger.info("StuTranKey values :"+stuTranKey.toString());
		/*SELECT * FROM stum WHERE INST_ID= 'ASC' AND BRANCH_ID = 'BR001' 
				AND ACADEMIC_YEAR = 'AT3' AND 
				DEL_FLG = 'T' AND STUDENT_ADMIS_NO='13-14-060';*/
		StringBuffer sql=new StringBuffer()
		.append("SELECT ")
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
				.append("R_MOD_TIME, ")
				.append("R_CRE_ID, ")
				.append("R_CRE_TIME,")
				.append("REG_NO,")
				.append("MEDIUM,")
				.append("TRANSFER_DATE")				
		.append(" FROM stum ")
		.append("WHERE")
		.append(" INST_ID=?")
		.append(" AND ")
		.append(" BRANCH_ID=?")
		.append(" AND ")
		.append(" ACADEMIC_YEAR=?")
		.append(" AND ")
		.append(" DEL_FLG=?")
		.append(" AND ")
		.append(" STUDENT_ADMIS_NO=?");
		
		logger.debug("select query :"+sql.toString());
		StudentMaster studentMasterResult= null;
		 studentMasterResult=(StudentMaster) getJdbcTemplate().query(sql.toString(), new PreparedStatementSetter() {

			@Override
			public void setValues(PreparedStatement pss)
					throws SQLException {
				pss.setString(1, stuTranKey.getInstId());
				pss.setString(2, stuTranKey.getBranchId());
				pss.setString(3, stuTranKey.getAcTrm());
				pss.setString(4, "N");
				pss.setString(5, stuTranKey.getStuAdmisNo());
			}
		}, new StudentTranResultSetExtractor());
		 if(studentMasterResult== null){
			 throw new NoDataFoundException();
		 }
		
	return studentMasterResult;
	}
	
	
	

}
class StudentTranResultSetExtractor implements ResultSetExtractor<StudentMaster>{
	@Override
	public StudentMaster extractData(ResultSet rs) throws SQLException  {
		StudentMaster studentMaster=null;	
		if(rs.next()){
			studentMaster=new StudentMaster();
		studentMaster.setDbTs(rs.getInt("DB_TS"));
		studentMaster.setInstId(rs.getString("INST_ID"));
		studentMaster.setBranchId(rs.getString("BRANCH_ID"));
		studentMaster.setAcademicYear(rs.getString("ACADEMIC_YEAR"));
		studentMaster.setStudentAdmisNo(rs.getString("STUDENT_ADMIS_NO"));
		studentMaster.setCourse(rs.getString("COURSE"));
		studentMaster.setStandard(rs.getString("STANDARD"));
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
		studentMaster.setStudentBatch(rs.getString("STUDENT_BATCH"));
		studentMaster.setLabBatch(rs.getString("LAB_BATCH"));
		studentMaster.setReasonForLeaving(rs.getString("REASON_FOR_LEAVING"));
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
