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

import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.batch.RuntimeExceptionForBatch;
import com.jaw.framework.dao.BaseDao;
import com.jaw.student.admission.dao.PrevAcademicDetailsListDao.PrevAcademicDetailsRowMapper;
@Repository
public class PreSportsPartDetailsListDao extends BaseDao implements IPreSportspartListDao {
	Logger logger = Logger.getLogger(PrevAcademicDetailsListDao.class);
	@Override
	public void insertPreSportspartList(
			final List<PreSportParticipationDetails> preSportParticipationDetails) {			
		 StringBuffer sql = new StringBuffer("insert into pspd(")
		 .append("DB_TS, ")
			.append("INST_ID, ")
			.append("BRANCH_ID, ")
			.append("STUDENT_ADMIS_NO, ")
			.append("SE_SRL_NO, ")
			.append("SPORTS_LEVEL, ")
			.append("PART_DETAILS, ")
			.append("DEL_FLG, ")
			.append("R_MOD_ID, ")
			.append("R_MOD_TIME,")
			.append("R_CRE_ID, ")
			.append("R_CRE_TIME ")	
			.append(")values(")
			.append("?,?,?,?,?,?,?,?,?,now(),?,now())");
	//	 try{
			 int[] a= getJdbcTemplate().batchUpdate(sql.toString(), new BatchPreparedStatementSetter() {								 			 
				@Override
				public void setValues(PreparedStatement ps, int i) throws SQLException {				
					PreSportParticipationDetails preSportParticipationDetail = preSportParticipationDetails.get(i);						
					ps.setInt(1,1);
					ps.setString(2,preSportParticipationDetail.getInstId());
					ps.setString(3,preSportParticipationDetail.getBranchId());
					ps.setString(4,preSportParticipationDetail.getStudentAdmisNo());
					ps.setString(5,preSportParticipationDetail.getSportsEntrySerialNo());
					ps.setString(6,preSportParticipationDetail.getSportsLevel());
					ps.setString(7,preSportParticipationDetail.getPartDetails());					
					ps.setString(8,"N");
					ps.setString(9,preSportParticipationDetail.getrModId());				
					ps.setString(10,preSportParticipationDetail.getrCreId());					
							
								
				}
			 
				@Override
				public int getBatchSize() {
					return preSportParticipationDetails.size();
				}
			  }
			
					 );		
	//	 }
		/* catch(Exception e){
			 e.printStackTrace();
		 }*/
}

	@Override
	public List<PreSportParticipationDetails> retrivePreSportParticipationDetailsList(final PreSportsPartDetailsKey partDetailsKey)
			{
		
		logger.debug("retrive PrevAcademicDetails List");
		StringBuffer sql=new StringBuffer()
		.append("SELECT ")
		 .append("DB_TS, ")
			.append("INST_ID, ")
			.append("BRANCH_ID, ")
			.append("STUDENT_ADMIS_NO, ")
			.append("SE_SRL_NO, ")
			.append("SPORTS_LEVEL, ")
			.append("PART_DETAILS, ")
			.append("DEL_FLG, ")
			.append("R_MOD_ID, ")
			.append("R_MOD_TIME,")
			.append("R_CRE_ID, ")
			.append("R_CRE_TIME ")
		.append(" from pspd ")
		.append("where ")
		.append("INST_ID=?")
			.append(" and ")
			.append("BRANCH_ID=?")
			.append(" and ")
			.append("STUDENT_ADMIS_NO=?")
			.append(" and ")
			.append(" DEL_FLG=?");
		logger.debug("select query :"+sql.toString());
		List<PreSportParticipationDetails> preSportParticipationDetails = getJdbcTemplate().query(sql.toString(), new PreparedStatementSetter(){

			@Override
			public void setValues(PreparedStatement pss)
					throws SQLException {
				pss.setString(1, partDetailsKey.getInstId().trim());
				pss.setString(2, partDetailsKey.getBranchId().trim());
				pss.setString(3, partDetailsKey.getStudentAdmisNo().trim());		
				pss.setString(4, "N");					
				
			}
			
		},new PreSportParticipationDetailsRowMapper());
		
																		
		return preSportParticipationDetails;	
}
	
	
	class PreSportParticipationDetailsRowMapper implements RowMapper<PreSportParticipationDetails>{
		@Override
		public PreSportParticipationDetails mapRow(ResultSet rs, int arg1) throws SQLException {
			PreSportParticipationDetails preSportParticipationDetails=new PreSportParticipationDetails();
			preSportParticipationDetails=new PreSportParticipationDetails();
			preSportParticipationDetails.setDbTs(rs.getInt("DB_TS"));
			preSportParticipationDetails.setInstId(rs.getString("INST_ID"));
			preSportParticipationDetails.setBranchId(rs.getString("BRANCH_ID"));			
		preSportParticipationDetails.setStudentAdmisNo(rs.getString("STUDENT_ADMIS_NO"));			
		preSportParticipationDetails.setSportsEntrySerialNo(rs.getString("SE_SRL_NO"));
		preSportParticipationDetails.setSportsLevel(rs.getString("SPORTS_LEVEL"));
		preSportParticipationDetails.setPartDetails(rs.getString("PART_DETAILS"));
		preSportParticipationDetails.setDelFlg(rs.getString("DEL_FLG"));
		preSportParticipationDetails.setrModId(rs.getString("R_MOD_ID"));
		preSportParticipationDetails.setrModTime(rs.getString("R_MOD_TIME"));
		preSportParticipationDetails.setrCreId(rs.getString("R_CRE_ID"));
		preSportParticipationDetails.setrCreTime(rs.getString("R_CRE_TIME"));			
			return preSportParticipationDetails;
			

		}
	}
}

