package com.jaw.student.admission.dao;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.jaw.common.exceptions.BatchUpdateFailedException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.batch.RuntimeExceptionForBatch;
import com.jaw.common.util.DateUtil;
import com.jaw.framework.dao.BaseDao;

@Repository
public class PrevAcademicDetailsListDao extends BaseDao implements IPrevAcademicDetailsList {
	
	Logger logger = Logger.getLogger(PrevAcademicDetailsListDao.class);
	DateUtil dateUtil = new DateUtil();
	@Override
	public void insertPrevAcademicDetailsList(
			final List<PrevAcademicDetails> prevAcademicDetailsList) throws RuntimeExceptionForBatch {		
		 StringBuffer sql = new StringBuffer("insert into previousschooldetails(")
			.append("DB_TS, ") 
			.append("INST_ID, ") 
			.append("BRANCH_ID, ") 
			.append("STUDENT_ADMIS_NO, ") 
			.append("PREV_STUDIED_STD, ") 
			.append("PREV_SCHOOL_NAME, ") 
			.append("PREV_SCHOOL_ADD1, ") 
			.append("PREV_SCHOOL_ADD2, ") 
			.append("PREV_SCHOOL_ADD3, ") 
			.append("PREV_STUDIED_YEAR, ") 
			.append("TRANSFER_CERTIFICATE_REFNO, ") 
			.append("MARK_OBT_PREV_STD, ")
			.append("MARKSHEET_REFNO, ")
			.append("DEL_FLG, ") 
			.append("R_MOD_ID, ") 
			.append("R_MOD_TIME,")
			.append("R_CRE_ID, ") 
			.append("R_CRE_TIME,") 
			.append("BOARD_EXAM_FLG, BOARD_EXAM_REG_NO, BOARD, MEDIUM_OF_INST, NO_OF_ATTEMPTS, ")
			.append("MAX_MARKS, PERCEN_OBTAINED, APPEARED_FOR_CET, EXTRA_ACTIVITIES,SPORTS_ENTITY,SPORTS_DETAILS")
			.append(")values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,now(),?,now(),?,?,?,?,?,?,?,?,?,?,?)");			
	try{
			 int[] a= getJdbcTemplate().batchUpdate(sql.toString(), new BatchPreparedStatementSetter() {								 			 
				@Override
				public void setValues(PreparedStatement ps, int i) throws SQLException {				
					PrevAcademicDetails prevAcademicDetails = prevAcademicDetailsList.get(i);						
					ps.setInt(1,prevAcademicDetails.getDbTs());
					ps.setString(2,prevAcademicDetails.getInstId());
					ps.setString(3,prevAcademicDetails.getBranchId());
					ps.setString(4,prevAcademicDetails.getStudentAdmisNo());
					ps.setString(5,prevAcademicDetails.getPrevStudiedStd());
					ps.setString(6,prevAcademicDetails.getPrevSchoolName());
					ps.setString(7,prevAcademicDetails.getPrevSchoolAdd1());
					ps.setString(8,prevAcademicDetails.getPrevSchoolAdd2());
					ps.setString(9,prevAcademicDetails.getPrevSchoolAdd3());
					ps.setString(10,prevAcademicDetails.getPrevStudiedYear());
					ps.setString(11,prevAcademicDetails.getTransCertificateRefno());
					
					if((prevAcademicDetails.getMarkObtPrevStd()==null)||(prevAcademicDetails.getMarkObtPrevStd()==0)) {
						ps.setString(12, null);
					} else {
						ps.setInt(12, prevAcademicDetails.getMarkObtPrevStd());
					}	
					
				//	ps.setInt(12,prevAcademicDetails.getMarkObtPrevStd());
					ps.setString(13,prevAcademicDetails.getMarksheetRefno());
					ps.setString(14,"N");
					ps.setString(15,prevAcademicDetails.getrModId());				
					ps.setString(16,prevAcademicDetails.getrCreId());
					if(prevAcademicDetails.getBoardExamFlg()==null){
						ps.setString(17, "N");		
					}else{
						ps.setString(17, prevAcademicDetails.getBoardExamFlg()
								.trim());	
					}
										
						
						ps.setString(18, prevAcademicDetails.getBoardExamRegNo()
								.trim());
						ps.setString(19, prevAcademicDetails.getBoard()
								.trim());
						ps.setString(20, prevAcademicDetails.getMediumOfInst()
								.trim());
					/*	ps.setString(21, prevAcademicDetails.getPassedPeriod()
								.trim());		*/				
						ps.setInt(21, prevAcademicDetails.getNoOfAttempts()
								);
						/*ps.setString(22, prevAcademicDetails.getMarksObtainedInBoard()
								.trim());*/
						
						if((prevAcademicDetails.getMaxMarks()==null)||(prevAcademicDetails.getMaxMarks()==0)) {
							ps.setString(22, null);
						} else {
							ps.setInt(22, prevAcademicDetails.getMaxMarks());
						}																			
						ps.setDouble(23, prevAcademicDetails.getPercentageObtained()
								);
						ps.setString(24, prevAcademicDetails.getAppearedForCET()
								.trim());
						ps.setString(25, prevAcademicDetails.getExtraActivities()
								.trim());
						ps.setString(26, prevAcademicDetails.getSportsEntity()
								.trim());
						
						ps.setString(27, prevAcademicDetails.getSportsDetails()
								.trim());
						
							
								
				}
			 
				@Override
				public int getBatchSize() {
					return prevAcademicDetailsList.size();
				}
			  }
			
					 );		
	}catch(RuntimeException e){
		e.printStackTrace();
		throw new RuntimeExceptionForBatch();
	}
	}
	@Override
public List<PrevAcademicDetails> retrivePrevAcademicDetailsList() throws NoDataFoundException {
		
		logger.debug("retrive PrevAcademicDetails List");
		StringBuffer sql=new StringBuffer()
		.append("select ")
		.append("DB_TS, ") 
		.append("INST_ID, ") 
		.append("BRANCH_ID, ") 
		.append("STUDENT_ADMIS_NO, ") 
		.append("PREV_STUDIED_STD, ") 
		.append("PREV_SCHOOL_NAME, ") 
		.append("PREV_SCHOOL_ADD1, ") 
		.append("PREV_SCHOOL_ADD2, ") 
		.append("PREV_SCHOOL_ADD3, ") 
		.append("PREV_STUDIED_YEAR, ") 	
		.append("TRANSFER_CERTIFICATE_REFNO, ") 
		.append("MARK_OBT_PREV_STD, ") 
		.append("MARKSHEET_REFNO, ") 
		.append("DEL_FLG, ") 
		.append("R_MOD_ID, ") 
		.append("R_MOD_TIME, ") 
		.append("R_CRE_ID, ") 
		.append("R_CRE_TIME")
		.append(" from previousschooldetails ")
		.append("where ")
		.append(" DEL_FLG =?")
		.append(" ORDER BY STUDENT_ADMIS_NO");
		logger.debug("select query :"+sql.toString());
		List<PrevAcademicDetails> prevAcademicDetailsList = getJdbcTemplate().query(sql.toString(), new PreparedStatementSetter(){

			@Override
			public void setValues(PreparedStatement pss)
					throws SQLException {
				pss.setString(1,"N");						
				
			}
			
		},new PrevAcademicDetailsRowMapper());
		if(prevAcademicDetailsList.size()==0){
			throw new NoDataFoundException();
		}
																		
		return prevAcademicDetailsList;	
}
class PrevAcademicDetailsRowMapper implements RowMapper<PrevAcademicDetails>{
@Override
public PrevAcademicDetails mapRow(ResultSet rs, int arg1) throws SQLException {
	PrevAcademicDetails prevAcademicDetails=new PrevAcademicDetails();
	prevAcademicDetails.setDbTs(rs.getInt("DB_TS"));
	prevAcademicDetails.setInstId(rs.getString("INST_ID"));
	prevAcademicDetails.setBranchId(rs.getString("BRANCH_ID"));
	prevAcademicDetails.setStudentAdmisNo(rs.getString("STUDENT_ADMIS_NO"));
	prevAcademicDetails.setPrevStudiedStd(rs.getString("PREV_STUDIED_STD"));
	prevAcademicDetails.setPrevSchoolName(rs.getString("PREV_SCHOOL_NAME"));
	prevAcademicDetails.setPrevSchoolAdd1(rs.getString("PREV_SCHOOL_ADD1"));
	prevAcademicDetails.setPrevSchoolAdd2(rs.getString("PREV_SCHOOL_ADD2"));
	prevAcademicDetails.setPrevSchoolAdd3(rs.getString("PREV_SCHOOL_ADD3"));
	prevAcademicDetails.setPrevStudiedYear(rs.getString("PREV_STUDIED_YEAR"));
	prevAcademicDetails.setTransCertificateRefno(rs.getString("TRANSFER_CERTIFICATE_REFNO"));
	prevAcademicDetails.setMarkObtPrevStd(rs.getInt("MARK_OBT_PREV_STD"));
	prevAcademicDetails.setMarksheetRefno(rs.getString("MARKSHEET_REFNO"));
	prevAcademicDetails.setDelFlg(rs.getString("DEL_FLG"));
	prevAcademicDetails.setrModId(rs.getString("R_MOD_ID"));
	prevAcademicDetails.setrModTime(rs.getString("R_MOD_TIME"));
	prevAcademicDetails.setrCreId(rs.getString("R_CRE_ID"));
	prevAcademicDetails.setrCreTime(rs.getString("R_CRE_TIME"));				
	return prevAcademicDetails;
	

}
}
@Override
public List<PrevAcademicDetails> getListForColumnUpdate() throws NoDataFoundException {
	
	logger.debug("retrive PrevAcademicDetails List");
	StringBuffer sql=new StringBuffer()
	.append("SELECT ")
	.append("DB_TS, ") 
	.append("INST_ID, ") 
	.append("BRANCH_ID, ") 
	.append("STUDENT_ADMIS_NO, ") 	
	.append("MARK_OBT_PREV_STD, ") 		
	.append("R_MOD_ID, ") 
	.append("R_MOD_TIME ") 	
	.append(" from previousschooldetails ")
	.append("where ")	
	.append(" INST_ID =?")
	.append(" AND BRANCH_ID =?")
//	.append(" AND DEL_FLG =?")
	.append(" ORDER BY STUDENT_ADMIS_NO");
	logger.debug("select query :"+sql.toString());
	List<PrevAcademicDetails> prevAcademicDetailsList = getJdbcTemplate().query(sql.toString(), new PreparedStatementSetter(){

		@Override
		public void setValues(PreparedStatement pss)
				throws SQLException {
			pss.setString(1,"ASC");		
			pss.setString(2,"BR001");	
		//	pss.setString(3,"N");			
			
		}		
	},new PrevAcademicDetailsRowMapperForColumnUpdate());
	if(prevAcademicDetailsList.size()==0){
		throw new NoDataFoundException();
	}
																	
	return prevAcademicDetailsList;	
}
			@Override
			public void batchUpdateForColumns(final List<PrevAcademicDetails> prevList) throws BatchUpdateFailedException {
				
				StringBuffer sql = new StringBuffer("update psde set ")
							.append("DB_TS=?, ")
							.append("R_MOD_ID=?, ")
							.append("R_MOD_TIME=now(), ")
							.append("MARK_OBT_PREV_STD=? ")
							.append(",PERCEN_OBTAINED=? ")
							.append(" where STUDENT_ADMIS_NO=? and ")
							.append("INST_ID=? and ")
							.append("BRANCH_ID=?")
							.append(" and DB_TS=?");
		logger.debug("sql query" + sql.toString());
		int[] ret = null;

		ret = 	getJdbcTemplate().batchUpdate(sql.toString(),
				new BatchPreparedStatementSetter() {

					@Override
					public void setValues(PreparedStatement ps, int i)
							throws SQLException {
						PrevAcademicDetails prevAcademicDetails = prevList.get(i);						
						ps.setInt(1, prevAcademicDetails.getDbTs()+1);
						ps.setString(2, prevAcademicDetails.getrModId());						
						ps.setString(3, null);
						
						if((prevAcademicDetails.getPercentageObtained()==null)||(prevAcademicDetails.getPercentageObtained()==0)) {
							ps.setString(4, null);
						} else {
							ps.setDouble(4, prevAcademicDetails.getPercentageObtained());
						}													
						ps.setString(5, prevAcademicDetails.getStudentAdmisNo());
						ps.setString(6, prevAcademicDetails.getInstId());
						ps.setString(7, prevAcademicDetails.getBranchId());
						ps.setInt(8, prevAcademicDetails.getDbTs());

					}

					@Override
					public int getBatchSize() {
						return prevList.size();
					}
				});
		for (int sa : ret) {
			if (sa == 0) {
				throw new BatchUpdateFailedException();
			}
		}
				
				
			}
				}

class PrevAcademicDetailsRowMapperForColumnUpdate implements RowMapper<PrevAcademicDetails>{
@Override
public PrevAcademicDetails mapRow(ResultSet rs, int arg1) throws SQLException {
	PrevAcademicDetails prevAcademicDetails=null;
	
		prevAcademicDetails=new PrevAcademicDetails();
	prevAcademicDetails.setDbTs(rs.getInt("DB_TS"));
	prevAcademicDetails.setInstId(rs.getString("INST_ID"));
	prevAcademicDetails.setBranchId(rs.getString("BRANCH_ID"));
	prevAcademicDetails.setStudentAdmisNo(rs.getString("STUDENT_ADMIS_NO"));	
	prevAcademicDetails.setMarkObtPrevStd(rs.getInt("MARK_OBT_PREV_STD"));	
	prevAcademicDetails.setrModId(rs.getString("R_MOD_ID"));
	prevAcademicDetails.setrModTime(rs.getString("R_MOD_TIME"));	

	return prevAcademicDetails;
	

}
}
