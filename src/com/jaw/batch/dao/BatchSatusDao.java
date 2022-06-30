package com.jaw.batch.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import com.jaw.common.exceptions.DatabaseException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.batch.NoRecordFoundException;
import com.jaw.framework.dao.BaseDao;


//Dao class for single operations in BatchStatus record
@Repository
public class BatchSatusDao extends BaseDao implements IBatchStatusDao {
	
	//Logging
		Logger logger = Logger.getLogger(BatchSatusDao.class);

	//Method to insert the batchstatus record
	@Override
	public void insertBatchStatus(final BatchStatus batchStatus) throws  DatabaseException {
		logger.info("Inside insert method");
		

		StringBuffer query = new StringBuffer();
		query = query
				.append("insert into bsts ( ")
				.append("DB_TS,")
				.append("INST_ID,")
				.append("BRANCH_ID,")				
				.append("BATCH_SRL_NO,")
				.append("BATCH_NAME,")
				.append("TOTAL_NO_OF_REC,")
				.append("UPLD_DATA_TYPE,")
				.append("BATCH_STATUS,")
				.append("EXEC_START_DATE,")		
				.append("REASON_FOR_FAILURE,")																		
				.append("DEL_FLG ,")
				.append("R_MOD_ID,")
				.append("R_MOD_TIME,")
				.append("R_CRE_ID,")
				.append("R_CRE_TIME )")
				.append(" values (?,?,?,?,?,?,?,?,now(),?,?,?,now(),?,now())");				
		
		logger.debug("insert query :" + query.toString());
		
		try {
		  getJdbcTemplate().update(query.toString(), new PreparedStatementSetter() {
				 
				@Override
				public void setValues(PreparedStatement ps) throws SQLException {														
					
					ps.setInt(1, batchStatus.getDbTs());				
					ps.setString(2, batchStatus.getInstId().trim());			
					ps.setString(3, batchStatus.getBranchId().trim());				
					ps.setString(4, batchStatus.getBatchSrlNo().trim());					
					ps.setString(5,batchStatus.getBatchName().trim());				
					ps.setString(6, batchStatus.getTotalNoOfrec().trim());				
					ps.setString(7, batchStatus.getUpldDataType());				
					ps.setString(8, batchStatus.getBatchStatus());														
					ps.setString(9, batchStatus.getReasonForFailure().trim());																					
					ps.setString(10, "N");
					ps.setString(11, batchStatus.getrModId().trim());							
					ps.setString(12, batchStatus.getrCreId().trim());									
					
				}				
				
			  });		
		  
		} catch (org.springframework.dao.DuplicateKeyException e){		
			logger.info("exception :", e);
			throw new DatabaseException();
			} 		

	}

	
	// method to update Batch Table
	@Override
	public void updateBatchStatus(final BatchStatus batchStatus,final BatchStatusKey batchStatusKey) {							
			logger.info("Inside update method");			
			StringBuffer sql = new StringBuffer();		
			sql.append("update bsts set")
			   .append(" DB_TS= ?")		  
			   .append(", INST_ID= ?")		   
			    .append(", BRANCH_ID= ?")		   
			   .append(", BATCH_SRL_NO= ?")		  	
			   .append(", BATCH_NAME= ?")		  
			   .append(", TOTAL_NO_OF_REC= ?")		  
			   .append(", UPLD_DATA_TYPE= ?")		   
			   .append(", BATCH_STATUS= ?")		   			 
			   .append(", EXEC_END_DATE= now()")	      
			   .append(", REASON_FOR_FAILURE= ?")		   		   			     		  	   
			   .append(", DEL_FLG= ?")
			   .append(", R_MOD_ID= ?")
			   .append(", R_MOD_TIME=now()")		   
			   .append(" where")
			   .append(" INST_ID= ?")		   
			   .append(" and ")
			   .append(" BRANCH_ID= ?")		   
			   .append(" and ")
			   .append(" DB_TS= ?")		   
			   .append(" and ")
			    .append(" BATCH_SRL_NO= ?")		   			
			    .append(" and ")			   
			   .append(" DEL_FLG = ?");		
			
			
			getJdbcTemplate().update(sql.toString(), new PreparedStatementSetter(){

				@Override
				public void setValues(PreparedStatement ps) throws SQLException {						
					ps.setInt(1, batchStatus.getDbTs()+1);
					ps.setString(2, batchStatus.getInstId().trim());					
					ps.setString(3, batchStatus.getBranchId().trim());					
					ps.setString(4, batchStatus.getBatchSrlNo().trim());					
					ps.setString(5, batchStatus.getBatchName().trim());					
					ps.setString(6, batchStatus.getTotalNoOfrec().trim());		
					ps.setString(7, batchStatus.getUpldDataType().trim());					
					ps.setString(8, batchStatus.getBatchStatus().trim());						
					ps.setString(9, batchStatus.getReasonForFailure().trim());						
					ps.setString(10, "N");
					ps.setString(11, batchStatus.getrModId().trim());						
					ps.setString(12, batchStatusKey.getInstId());
					ps.setString(13, batchStatusKey.getBranchId());				
					ps.setInt(14, batchStatusKey.getDbTs());				
					ps.setString(15, batchStatusKey.getBatchSrlNo());													
					ps.setString(16, "N");
													
					
				}
				
			});					
				logger.debug("update query :" + sql.toString());						
				
			
		}


	//Method to select single record batchstatus record
	@Override
	public BatchStatus retrieveBatchStatusRec(final String batchId/*,final String instId,final String branchId*/) throws NoRecordFoundException {
		logger.info("Going to fetch the searched record from Database");		
		StringBuffer sql=new StringBuffer()		
		.append("select ")
		.append("DB_TS, ") 
		.append("INST_ID, ") 
		.append("BRANCH_ID, ") 
		.append("BATCH_SRL_NO, ") 
		.append("UPLD_DATA_TYPE, ") 
		.append("BATCH_STATUS, ") 
		.append("EXEC_START_DATE, ")
		.append("EXEC_END_DATE, ") 
		.append("REASON_FOR_FAILURE, ")		
		.append("DEL_FLG, ")
		.append("R_MOD_ID, ")
		.append("R_MOD_TIME, ") 
		.append("R_CRE_ID, ")
		.append("R_CRE_TIME, ")
		.append("BATCH_NAME, ")
		.append("TOTAL_NO_OF_REC,")
		.append("CONTENT_TYPE")
		.append(" from bsts ")
		.append("where ")
		.append(" DEL_FLG =?")
		.append(" and ")
		/*.append(" INST_ID =?")
		.append(" and ")
		.append(" BRANCH_ID =?")
		.append(" and ")*/
		.append(" BATCH_SRL_NO =?");
		
		logger.debug("select query :"+sql.toString());
		BatchStatus batchStatusRec = null;
		
		
		
		batchStatusRec = (BatchStatus) getJdbcTemplate().query(sql.toString(), new PreparedStatementSetter(){

				@Override
				public void setValues(PreparedStatement pss) throws SQLException {
					pss.setString(1, "N");					
					pss.setString(2, batchId);
								
				}
				
			},new ResultSetExtractor<BatchStatus>() {

				@Override
				public BatchStatus extractData(ResultSet rs) throws SQLException,
						DataAccessException {			
					BatchStatus batchStatus  = null;
				if(rs.next()){
					batchStatus  =new BatchStatus();								
					batchStatus.setDbTs(rs.getInt("DB_TS"));
					batchStatus.setInstId(rs.getString("INST_ID"));
					batchStatus.setBranchId(rs.getString("BRANCH_ID"));
					batchStatus.setBatchSrlNo(rs.getString("BATCH_SRL_NO"));
					batchStatus.setUpldDataType(rs.getString("UPLD_DATA_TYPE"));
					batchStatus.setBatchStatus(rs.getString("BATCH_STATUS"));
					batchStatus.setExecStartDate(rs.getString("EXEC_START_DATE"));
					batchStatus.setExecEndDate(rs.getString("EXEC_END_DATE"));
					batchStatus.setReasonForFailure(rs.getString("REASON_FOR_FAILURE"));			
					batchStatus.setDelFlag(rs.getString("DEL_FLG"));		
					batchStatus.setrModId(rs.getString("R_MOD_ID"));
					batchStatus.setrModTime(rs.getString("R_MOD_TIME"));								
					batchStatus.setrCreId(rs.getString("R_CRE_ID"));
					batchStatus.setrCreTime(rs.getString("R_CRE_TIME"));	
					batchStatus.setBatchName(rs.getString("BATCH_NAME"));	
					batchStatus.setTotalNoOfrec(rs.getString("TOTAL_NO_OF_REC"));										
				}
				
										
		return batchStatus;
	}
	
			});
		if(batchStatusRec==null){
			throw new NoRecordFoundException();
		}
		return batchStatusRec;
		}
}
