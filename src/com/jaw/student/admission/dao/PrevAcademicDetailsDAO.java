package com.jaw.student.admission.dao;

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

import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.UpdateFailedException;
import com.jaw.framework.dao.BaseDao;

@Repository
public class PrevAcademicDetailsDAO extends BaseDao implements
		IPrevAcademicDetails {
	Logger logger = Logger.getLogger(PrevAcademicDetailsDAO.class);

	@Override
	public void insertPrevAcademicDetaisl(
			final PrevAcademicDetails prevAcademicDetails)
			throws DuplicateEntryException {

		StringBuffer sql = new StringBuffer(
				"insert into previousschooldetails(").append("DB_TS, ")
				.append("INST_ID, ").append("BRANCH_ID, ")
				.append("STUDENT_ADMIS_NO, ").append("PREV_STUDIED_STD, ")
				.append("PREV_SCHOOL_NAME, ").append("PREV_SCHOOL_ADD1, ")
				.append("PREV_SCHOOL_ADD2, ").append("PREV_SCHOOL_ADD3, ")
				.append("PREV_STUDIED_YEAR, ").append("MARK_OBT_PREV_STD, ")
				.append("DEL_FLG, ").append("R_MOD_ID, ")
				.append("R_MOD_TIME, ").append("R_CRE_ID, ")
				.append("R_CRE_TIME,")
				.append("BOARD_EXAM_FLG, BOARD_EXAM_REG_NO, BOARD, MEDIUM_OF_INST, NO_OF_ATTEMPTS,")
				.append("MAX_MARKS, PERCEN_OBTAINED, APPEARED_FOR_CET, EXTRA_ACTIVITIES,SPORTS_ENTITY,SPORTS_DETAILS")
				.append(") values(?,?,?,?,?,?,?,?,?,?,?,?,?,now(),?,now(),?,?,?,?,?,?,?,?,?,?,?)");

		try {
			getJdbcTemplate().update(sql.toString(),
					new PreparedStatementSetter() {

						@Override
						public void setValues(PreparedStatement ps)
								throws SQLException {
							ps.setInt(1, 1);
							ps.setString(2, prevAcademicDetails.getInstId()
									.trim());
							ps.setString(3, prevAcademicDetails.getBranchId()
									.trim());
							ps.setString(4, prevAcademicDetails
									.getStudentAdmisNo().trim());
							ps.setString(5, prevAcademicDetails
									.getPrevStudiedStd().trim());
							ps.setString(6, prevAcademicDetails
									.getPrevSchoolName().trim());
							ps.setString(7, prevAcademicDetails
									.getPrevSchoolAdd1().trim());
							ps.setString(8, prevAcademicDetails
									.getPrevSchoolAdd2().trim());
							ps.setString(9, prevAcademicDetails
									.getPrevSchoolAdd3().trim());
							ps.setString(10, prevAcademicDetails
									.getPrevStudiedYear().trim());
							
							if((prevAcademicDetails.getMarkObtPrevStd()==null)||(prevAcademicDetails.getMarkObtPrevStd()==0)) {
								ps.setString(11, null);
							} else {
								ps.setInt(11, prevAcademicDetails.getMarkObtPrevStd());
							}																					
							ps.setString(12, "N");
							ps.setString(13, prevAcademicDetails.getrModId()
									.trim());
							ps.setString(14, prevAcademicDetails.getrCreId()
									.trim());
							
						if(prevAcademicDetails.getBoardExamFlg()==null){
							ps.setString(15, "N");		
						}else{
							ps.setString(15, prevAcademicDetails.getBoardExamFlg()
									.trim());	
						}
											
							
							ps.setString(16, prevAcademicDetails.getBoardExamRegNo()
									.trim());
							ps.setString(17, prevAcademicDetails.getBoard()
									.trim());
							ps.setString(18, prevAcademicDetails.getMediumOfInst()
									.trim());
						/*	ps.setString(19, prevAcademicDetails.getPassedPeriod()
									.trim());		*/					
							ps.setInt(19, prevAcademicDetails.getNoOfAttempts()
									);
							/*ps.setString(21, prevAcademicDetails.getMarksObtainedInBoard()
									.trim());*/
							
							if((prevAcademicDetails.getMaxMarks()==null)||(prevAcademicDetails.getMaxMarks()==0)) {
								ps.setString(20, null);
							} else {
								ps.setInt(20, prevAcademicDetails.getMaxMarks());
							}	
							
							if((prevAcademicDetails.getPercentageObtained()==null)||(prevAcademicDetails.getPercentageObtained()==0)) {
								ps.setString(21, null);
							} else {
								ps.setDouble(21, prevAcademicDetails.getPercentageObtained());
							}	
														
							if(prevAcademicDetails.getBoardExamFlg()==null){
								ps.setString(22, "N");
							}else{
								if(prevAcademicDetails.getAppearedForCET()==null){
								ps.setString(22, "N");
								}else{
								ps.setString(22, prevAcademicDetails.getAppearedForCET()
										.trim());
										}
							}
							
							ps.setString(23, prevAcademicDetails.getExtraActivities()
									.trim());
							ps.setString(24, prevAcademicDetails.getSportsEntity()
									.trim());
							
							ps.setString(25, prevAcademicDetails.getSportsDetails()
									.trim());
							
						}

					});
		} catch (org.springframework.dao.DuplicateKeyException e) {
			throw new DuplicateEntryException();
		} 
	}

	@Override
	public void updatePrevAcaDetails(
			final PrevAcademicDetails prevAcademicDetails,
			final PrevAcademicDetailsKey prevAcademicDetailsKey) throws UpdateFailedException {

		logger.debug("Inside update method");
		StringBuffer sql = new StringBuffer();
		sql.append("update psde set").append(" DB_TS=?, ")
				.append("PREV_STUDIED_STD=?, ").append("PREV_SCHOOL_NAME=?, ")
				.append("PREV_SCHOOL_ADD1=?, ").append("PREV_SCHOOL_ADD2=?, ")
				.append("PREV_SCHOOL_ADD3=?, ").append("PREV_STUDIED_YEAR=?, ")
				.append("MARK_OBT_PREV_STD=?, ")
				.append("BOARD_EXAM_FLG = ?,") 
				.append(" BOARD_EXAM_REG_NO=?,")
				.append(" BOARD=?,")
				.append(" MEDIUM_OF_INST=?,")
			/*	.append(" PASSED_PERIOD=?,")*/
				.append("NO_OF_ATTEMPTS=?,")
			/*	.append("MARKS_OBTAINED=?,")*/
				.append("MAX_MARKS=?,")
				.append("PERCEN_OBTAINED=?,")
				.append("APPEARED_FOR_CET=?,")
				.append("EXTRA_ACTIVITIES=?,")
				.append("SPORTS_ENTITY=?,")
				.append("SPORTS_DETAILS=?,")
				.append("DEL_FLG=?, ")
				.append("R_MOD_ID=?, ").append("R_MOD_TIME=now() ")
				.append(" where").append(" INST_ID= ?")
				.append(" and ").append(" BRANCH_ID= ?")
				.append(" and ").append(" STUDENT_ADMIS_NO= ?").append(" and ")
				.append(" DB_TS= ?").append(" and ").append(" DEL_FLG='N'");
		
		int updateStatus =	getJdbcTemplate().update(sql.toString(),
					new PreparedStatementSetter() {

						@Override
						public void setValues(PreparedStatement ps)
								throws SQLException {

							ps.setInt(1, prevAcademicDetails.getDbTs() + 1);

							ps.setString(2, prevAcademicDetails
									.getPrevStudiedStd().trim());
							ps.setString(3, prevAcademicDetails
									.getPrevSchoolName().trim());
							ps.setString(4, prevAcademicDetails
									.getPrevSchoolAdd1().trim());
							ps.setString(5, prevAcademicDetails
									.getPrevSchoolAdd2().trim());
							ps.setString(6, prevAcademicDetails
									.getPrevSchoolAdd3().trim());
							ps.setString(7, prevAcademicDetails
									.getPrevStudiedYear().trim());
							if((prevAcademicDetails.getMarkObtPrevStd()==null)||(prevAcademicDetails.getMarkObtPrevStd()==0)) {
								ps.setString(8, null);
							} else {
								ps.setInt(8, prevAcademicDetails.getMarkObtPrevStd());
							}	
							if(prevAcademicDetails.getBoardExamFlg()==null){
								ps.setString(9, "N");
							}else{
								ps.setString(9, prevAcademicDetails.getBoardExamFlg()
										.trim());
							}
							ps.setString(10, prevAcademicDetails.getBoardExamRegNo()
									.trim());
							ps.setString(11, prevAcademicDetails.getBoard()
									.trim());
							ps.setString(12, prevAcademicDetails.getMediumOfInst()
									.trim());
							/*ps.setString(13, prevAcademicDetails.getPassedPeriod()
									.trim());	*/						
							ps.setInt(13, prevAcademicDetails.getNoOfAttempts()
									);
							/*ps.setString(15, prevAcademicDetails.getMarksObtainedInBoard()
									.trim());*/														
							if((prevAcademicDetails.getMaxMarks()==null)||(prevAcademicDetails.getMaxMarks()==0)) {
								ps.setString(14, null);
							} else {
								ps.setInt(14, prevAcademicDetails.getMaxMarks());
							}	
							
							if((prevAcademicDetails.getPercentageObtained()==null)||(prevAcademicDetails.getPercentageObtained()==0)) {
								ps.setString(15, null);
							} else {
								ps.setDouble(15, prevAcademicDetails.getPercentageObtained());
							}														
							if(prevAcademicDetails.getAppearedForCET()==null){
								ps.setString(16, "N");
							}else{
								ps.setString(16, prevAcademicDetails.getAppearedForCET()
										.trim());
							}
							
							ps.setString(17, prevAcademicDetails.getExtraActivities()
									.trim());
							ps.setString(18, prevAcademicDetails.getSportsEntity()
									.trim());
							ps.setString(19, prevAcademicDetails.getSportsDetails()
									.trim());
							
							
							
							ps.setString(20, "N");
							ps.setString(21, prevAcademicDetails.getrModId()
									.trim());													
							
							ps.setString(22, prevAcademicDetailsKey
									.getInstId().trim());
							ps.setString(23, prevAcademicDetailsKey.getBranchId()
									.trim());
							ps.setString(24, prevAcademicDetailsKey.getStudentAdmisNo().trim());
							ps.setInt(25, prevAcademicDetails.getDbTs());
							
						}

					});
		if (updateStatus == 0) {
			throw new UpdateFailedException();

		}
		
		
		logger.debug("update query :" + sql.toString());

	}

	@Override
	public PrevAcademicDetails retrivePrevAcademicDetails(
			final PrevAcademicDetailsKey prevAcademicDetailsKey) throws NoDataFoundException {
		
		logger.debug("retrive PrevAcademicDetails");
		StringBuffer sql = new StringBuffer().append("select ")
				.append("DB_TS, ").append("INST_ID, ").append("BRANCH_ID, ")
				.append("STUDENT_ADMIS_NO, ").append("PREV_STUDIED_STD, ")
				.append("PREV_SCHOOL_NAME, ").append("PREV_SCHOOL_ADD1, ")
				.append("PREV_SCHOOL_ADD2, ").append("PREV_SCHOOL_ADD3, ")
				.append("PREV_STUDIED_YEAR, ").append("MARK_OBT_PREV_STD, ")
				.append("DEL_FLG, ").append("R_MOD_ID, ")
				.append("R_MOD_TIME, ").append("R_CRE_ID, ")
				.append("R_CRE_TIME,")
				.append("BOARD_EXAM_FLG,") 
				.append(" BOARD_EXAM_REG_NO,")
				.append(" BOARD,")
				.append(" MEDIUM_OF_INST,")				
				.append("NO_OF_ATTEMPTS,")				
				.append("MAX_MARKS,")
				.append("PERCEN_OBTAINED,")
				.append("APPEARED_FOR_CET,")
				.append("EXTRA_ACTIVITIES,")
				.append("SPORTS_ENTITY,")
				.append("SPORTS_DETAILS")
				.append(" from psde ").append("where ")
				.append("INST_ID=?").append(" and ")
				.append("BRANCH_ID=?").append(" and ")
				.append("STUDENT_ADMIS_NO=?").append(" and ")
				.append(" DEL_FLG=?");
		logger.debug("select query :" + sql.toString());
		PrevAcademicDetails prevAcademicDetails = null;
		
			prevAcademicDetails = getJdbcTemplate().query(sql.toString(),
					new PreparedStatementSetter() {

						@Override
						public void setValues(PreparedStatement pss)
								throws SQLException {
							pss.setString(1, prevAcademicDetailsKey.getInstId().trim());
							pss.setString(2, prevAcademicDetailsKey.getBranchId().trim());
							pss.setString(3, prevAcademicDetailsKey.getStudentAdmisNo().trim());
							pss.setString(4, "N");

						}

					}, new PrevAcademicDetailsResultSetExtractor());
			if(prevAcademicDetails == null){
				throw new NoDataFoundException();
			}		
		return prevAcademicDetails;
	}
	

		
}

class PrevAcademicDetailsResultSetExtractor implements
		ResultSetExtractor<PrevAcademicDetails> {
	@Override
	public PrevAcademicDetails extractData(ResultSet rs) throws SQLException,
			DataAccessException {
		PrevAcademicDetails prevAcademicDetails = null;
		if (rs.next()) {
			prevAcademicDetails = new PrevAcademicDetails();
			prevAcademicDetails.setDbTs(rs.getInt("DB_TS"));
			prevAcademicDetails.setInstId(rs.getString("INST_ID"));
			prevAcademicDetails.setBranchId(rs.getString("BRANCH_ID"));
			prevAcademicDetails.setStudentAdmisNo(rs
					.getString("STUDENT_ADMIS_NO"));
			prevAcademicDetails.setPrevStudiedStd(rs
					.getString("PREV_STUDIED_STD"));
			prevAcademicDetails.setPrevSchoolName(rs
					.getString("PREV_SCHOOL_NAME"));
			prevAcademicDetails.setPrevSchoolAdd1(rs
					.getString("PREV_SCHOOL_ADD1"));
			prevAcademicDetails.setPrevSchoolAdd2(rs
					.getString("PREV_SCHOOL_ADD2"));
			prevAcademicDetails.setPrevSchoolAdd3(rs
					.getString("PREV_SCHOOL_ADD3"));
			prevAcademicDetails.setPrevStudiedYear(rs
					.getString("PREV_STUDIED_YEAR"));
			prevAcademicDetails.setMarkObtPrevStd(rs
					.getInt("MARK_OBT_PREV_STD"));
			prevAcademicDetails.setDelFlg(rs.getString("DEL_FLG"));
			prevAcademicDetails.setrModId(rs.getString("R_MOD_ID"));
			prevAcademicDetails.setrModTime(rs.getString("R_MOD_TIME"));
			prevAcademicDetails.setrCreId(rs.getString("R_CRE_ID"));
			prevAcademicDetails.setrCreTime(rs.getString("R_CRE_TIME"));	
			
			
			prevAcademicDetails.setBoardExamFlg(rs.getString("BOARD_EXAM_FLG"));	
			prevAcademicDetails.setBoardExamRegNo(rs.getString("BOARD_EXAM_REG_NO"));	
			prevAcademicDetails.setBoard(rs.getString("BOARD"));	
			prevAcademicDetails.setMediumOfInst(rs.getString("MEDIUM_OF_INST"));							
			prevAcademicDetails.setNoOfAttempts(rs.getInt("NO_OF_ATTEMPTS"));				
			prevAcademicDetails.setMaxMarks(rs.getInt("MAX_MARKS"));				
			prevAcademicDetails.setPercentageObtained(rs.getDouble("PERCEN_OBTAINED"));	
			prevAcademicDetails.setAppearedForCET(rs.getString("APPEARED_FOR_CET"));	
			prevAcademicDetails.setExtraActivities(rs.getString("EXTRA_ACTIVITIES"));		
			prevAcademicDetails.setSportsEntity(rs.getString("SPORTS_ENTITY"));		
			prevAcademicDetails.setSportsDetails(rs.getString("SPORTS_DETAILS"));		
			
		}

		if (prevAcademicDetails == null) {
			try {			
				throw new NoDataFoundException();

			} catch (NoDataFoundException e) {
				e.printStackTrace();
			}
		}
		return prevAcademicDetails;
	}
	

}
