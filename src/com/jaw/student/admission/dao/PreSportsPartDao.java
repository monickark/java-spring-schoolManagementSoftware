package com.jaw.student.admission.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
public class PreSportsPartDao extends BaseDao implements IPreSportsPartDao {
	Logger logger = Logger.getLogger(PreSportsPartDao.class);
	@Override
	public void insertPreSportsPart(final PreSportParticipationDetails preSportsPart)
			throws DuplicateEntryException {

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
				.append(") values(?,?,?,?,?,?,?,?,?,now(),?,now())");

				

		try {
			getJdbcTemplate().update(sql.toString(),new PreparedStatementSetter(){

				@Override
				public void setValues(PreparedStatement ps)
						throws SQLException {
					ps.setInt(1,1);
					ps.setString(2,preSportsPart.getInstId().trim());
					ps.setString(3,preSportsPart.getBranchId().trim());					
					ps.setString(4,preSportsPart.getStudentAdmisNo().trim());
					ps.setString(5,preSportsPart.getSportsEntrySerialNo());
					ps.setString(6,preSportsPart.getSportsLevel().trim());
					ps.setString(7,preSportsPart.getPartDetails().trim());
					ps.setString(8,"N");					
					ps.setString(9,preSportsPart.getrModId().trim());
					ps.setString(10,preSportsPart.getrCreId().trim());										
				}
				
			});
		} catch (DuplicateKeyException e) {
			logger.debug("dupliacate entry");
			throw new DuplicateEntryException();
		} 
	}

	@Override
	public PreSportParticipationDetails retrivePreSportsPart(
			final PreSportsPartDetailsKey preSportsPartDetailsKey ) throws NoDataFoundException {
		logger.debug("PreSportsPartDetailsKey object :"+preSportsPartDetailsKey);
	StringBuffer sql=new StringBuffer()
			.append("select ")
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
			.append("SE_SRL_NO=?")
			.append(" and ")
			.append(" DEL_FLG=?");
	PreSportParticipationDetails preSportParticipationDetails =null;
	
	preSportParticipationDetails=getJdbcTemplate().query(sql.toString(),new PreparedStatementSetter() {

		@Override
		public void setValues(PreparedStatement pss)
				throws SQLException {							
			pss.setString(1, preSportsPartDetailsKey.getInstId().trim());
			pss.setString(2, preSportsPartDetailsKey.getBranchId().trim());
			pss.setString(3, preSportsPartDetailsKey.getStudentAdmisNo().trim());	
			pss.setString(4, preSportsPartDetailsKey.getSportsEntrySerialNo().trim());	
			pss.setString(5, "N");

		}

	}, new PreSportPartDetailsResultSetExtractor());
	 if(preSportParticipationDetails==null){
		 throw new NoDataFoundException();
	 }

	return preSportParticipationDetails;	
}

	@Override
	public void updatePreSportsPart(final PreSportParticipationDetails preSportsPart,
			final PreSportsPartDetailsKey preSportsPartKey) {
		StringBuffer sql = new StringBuffer();
		sql.append("update pspd set ")
				.append("DB_TS = ?, ")								
				.append("SPORTS_LEVEL= ?, ")
				.append("PART_DETAILS= ?, ")
				.append("DEL_FLG= ?, ")
				.append("R_MOD_ID= ?, ")
				.append("R_MOD_TIME= now(),")
				.append("R_CRE_ID= ?, ")
				.append("R_CRE_TIME=? ")	
				.append(" where")
				.append(" INST_ID= ?")
				.append(" and ")
				.append(" BRANCH_ID= ?")
				.append(" and ")
				.append(" STUDENT_ADMIS_NO= ?")
				.append(" and ")			
				.append(" SE_SRL_NO= ?")
				.append(" and ")
				.append(" DB_TS= ?")
				.append(" and ")
				.append(" DEL_FLG='N'");

		getJdbcTemplate().update(sql.toString(), new PreparedStatementSetter() {

			@Override
			public void setValues(PreparedStatement ps) throws SQLException {

				ps.setInt(1,preSportsPart.getDbTs()+1);				
				ps.setString(2,preSportsPart.getSportsLevel().trim());
				ps.setString(3,preSportsPart.getPartDetails().trim());
				ps.setString(4,preSportsPart.getDelFlg().trim());
				ps.setString(5,preSportsPart.getrModId().trim());		
				
				ps.setString(6,preSportsPart.getrCreId().trim());		
				ps.setString(7,preSportsPart.getrCreTime().trim());
				
				ps.setString(8,preSportsPartKey.getInstId().trim());
				ps.setString(9,preSportsPartKey.getBranchId().trim());
				ps.setString(10,preSportsPartKey.getStudentAdmisNo().trim());		
				ps.setString(11,preSportsPartKey.getSportsEntrySerialNo().trim());		
				ps.setInt(12,preSportsPart.getDbTs());
			}

		});
	
		logger.debug("update query :" + sql.toString());

	}
}
	class PreSportPartDetailsResultSetExtractor implements ResultSetExtractor<PreSportParticipationDetails>{
		@Override
		public PreSportParticipationDetails extractData(ResultSet rs) throws SQLException, DataAccessException {
			PreSportParticipationDetails preSportParticipationDetails=null;
			if(rs.next()){
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
			
			}
			return preSportParticipationDetails;
		}
	}
