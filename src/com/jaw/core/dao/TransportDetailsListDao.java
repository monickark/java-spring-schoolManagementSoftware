package com.jaw.core.dao;

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
import com.jaw.common.util.DateUtil;
import com.jaw.framework.dao.BaseDao;
@Repository
public class TransportDetailsListDao extends BaseDao implements ITransportDetailsListDao {	
	DateUtil dateUtil = new DateUtil();
	Logger logger = Logger.getLogger(TransportDetailsListDao.class);
	@Override
	public void insertTransportDetailsList(
			final List<TransportDetails> transportDetailsList) throws RuntimeExceptionForBatch {			
		StringBuffer sql = new StringBuffer("insert into transportdetails(")
		.append("DB_TS, ") 
		.append("INST_ID, ") 
		.append("BRANCH_ID, ") 
		.append("STUDENT_ADMIS_NO, ") 
		.append("ACADEMIC_YEAR, ")
		.append("MODE_OF_TRANS, ")
		.append("VEHICLE_NO, ")
		.append("PICKUP_POINT, ")
		.append("DROP_POINT, ")
		.append("PICKUP_ASST_SALUT, ")
		.append("PICKUP_ASST_NAME, ")		
		.append("DEL_FLG, ") 
		.append("R_MOD_ID, ")
		.append("R_MOD_TIME,")		
		.append("R_CRE_ID, ")
		.append("R_CRE_TIME ")
		.append(") values(?,?,?,?,?,?,?,?,?,?,?,?,?,now(),?,now())");	
				try{
					int[] 	 a = getJdbcTemplate().batchUpdate(sql.toString(), new BatchPreparedStatementSetter() {								

				@Override
				public void setValues(PreparedStatement ps, int i) throws SQLException {					
					TransportDetails transportDetails = transportDetailsList.get(i);				
					ps.setInt(1,transportDetails.getDbTs());
					ps.setString(2,transportDetails.getInstId());
					ps.setString(3,transportDetails.getBranchId());
					ps.setString(4,transportDetails.getStudentAdmisNo());
					ps.setString(5,transportDetails.getAcademicYear());
					ps.setString(6,transportDetails.getModeOfTransport());
					ps.setString(7,transportDetails.getVehicleNo());
					ps.setString(8,transportDetails.getPickupPoint());
					ps.setString(9,transportDetails.getDroppingPoint());
					ps.setString(10,transportDetails.getPickupAssSalut());
					ps.setString(11,transportDetails.getPickupAssName());	
					ps.setString(12,"N");
					ps.setString(13,transportDetails.getrModId());
					ps.setString(14,transportDetails.getrCreId());			
								
				}
			 
				@Override
				public int getBatchSize() {
					
					return transportDetailsList.size();
				
				}
				
			  }						
					 );
					
				}catch(RuntimeException e){
					throw new RuntimeExceptionForBatch();
				}	
						
			
	}
	@Override
	public List<TransportDetails> retriveTransportDetailsList() throws NoDataFoundException {
		
		logger.debug("retrive TransportDetails List");
		StringBuffer sql=new StringBuffer()
		.append("select ")
		.append("DB_TS, ") 
		.append("INST_ID, ") 
		.append("BRANCH_ID, ") 
		.append("STUDENT_ADMIS_NO, ") 
		.append("ACADEMIC_YEAR, ")
		.append("MODE_OF_TRANS, ")
		.append("VEHICLE_NO, ")
		.append("PICKUP_POINT, ")
		.append("DROP_POINT, ")
		.append("PICKUP_ASST_SALUT, ")
		.append("PICKUP_ASST_NAME, ")		
		.append("DEL_FLG, ") 
		.append("R_MOD_ID, ")
		.append("R_MOD_TIME, ")
		.append("R_CRE_ID, ")
		.append("R_CRE_TIME")
		.append(" from transportdetails ")
		.append(" where ")
		.append(" DEL_FLG =?")
		.append(" ORDER BY STUDENT_ADMIS_NO");
		logger.debug("select query :"+sql.toString());
		List<TransportDetails> transportDetailsList = getJdbcTemplate().query(sql.toString(), new PreparedStatementSetter(){

			@Override
			public void setValues(PreparedStatement pss)
					throws SQLException {
				pss.setString(1,"N");						
				
			}
			
		},new TransportDetailsRowMapper() );
		if(transportDetailsList.size()==0){
			throw new NoDataFoundException();
		}
																		
		return transportDetailsList;	
}
	
	class TransportDetailsRowMapper implements RowMapper<TransportDetails> {

		@Override
		public TransportDetails mapRow(ResultSet rs, int arg1) throws SQLException {
			TransportDetails transportDetails=new TransportDetails();
			transportDetails.setDbTs(rs.getInt("DB_TS"));	
			transportDetails.setInstId(rs.getString("INST_ID"));	
			transportDetails.setBranchId(rs.getString("BRANCH_ID"));	
			transportDetails.setStudentAdmisNo(rs.getString("STUDENT_ADMIS_NO"));	
			transportDetails.setAcademicYear(rs.getString("ACADEMIC_YEAR"));	
			transportDetails.setModeOfTransport(rs.getString("MODE_OF_TRANS"));	
			transportDetails.setVehicleNo(rs.getString("VEHICLE_NO"));	
			transportDetails.setPickupPoint(rs.getString("PICKUP_POINT"));	
			transportDetails.setDroppingPoint(rs.getString("DROP_POINT"));		
			transportDetails.setPickupAssSalut(rs.getString("PICKUP_ASST_SALUT"));			
			transportDetails.setPickupAssName(rs.getString("PICKUP_ASST_NAME"));
			transportDetails.setDelFlg(rs.getString("DEL_FLG"));	
			transportDetails.setrModId(rs.getString("R_MOD_ID"));	
			transportDetails.setrModTime(rs.getString("R_MOD_TIME"));	
			transportDetails.setrCreId(rs.getString("R_CRE_ID"));	
			transportDetails.setrCreTime(rs.getString("R_CRE_TIME"));			
			return transportDetails;
		}

	}


}
