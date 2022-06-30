package com.jaw.batch.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import com.jaw.common.exceptions.batch.NoRecordFoundException;

import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.UpdateFailedException;
import com.jaw.framework.dao.BaseDao;
@Repository
//Dao List class for BatchList
public class BatchPgmsRecordDao extends BaseDao implements IBatchPgmsRecordDao {
	Logger logger = Logger.getLogger(BatchStatusListDao.class);

	//Method to select the list of BatchList Records 
	@Override
	public BatchPgms retrieveBatchPgmsRec(final BatchPgmsKey batchPgms) throws NoRecordFoundException {
		logger.debug("retrive batchlist List");		
		BatchPgms batchPgmRec = null;
		StringBuffer sql=new StringBuffer()		
		.append("select ")
		.append("DB_TS, ") 
		.append("INST_ID, ") 
		.append("BRANCH_ID, ") 
		.append("BATCH_PGM_ID, ") 
		.append("BATCH_NAME, ") 
		.append("UPLD_DATA_TYPE, ") 
		.append("XML_FILE_NAME, ")
		.append("EXCEL_FILE_NAME, ")
		.append("XML_FILE_SRL_NO, ") 
		.append("SHEET_NAME, ")		
		.append("DEL_FLG, ")
		.append("R_MOD_ID, ")
		.append("R_MOD_TIME, ") 
		.append("R_CRE_ID, ")
		.append("R_CRE_TIME ")		
		.append(" from bpgm ")
		.append(" where ")
		.append(" DEL_FLG = ? ")
		.append(" AND INST_ID = ? ")
		.append(" AND BRANCH_ID = ? ")
	//will be added later	.append(" AND BATCH_PGM_ID = ? ")
		.append(" AND BATCH_NAME = ? ")
		.append(" AND UPLD_DATA_TYPE = ? ");
			
			batchPgmRec = (BatchPgms) getJdbcTemplate().query(sql.toString(), new PreparedStatementSetter(){

					@Override
					public void setValues(PreparedStatement pss) throws SQLException {
						pss.setString(1, "N");					
						pss.setString(2, batchPgms.getInstId() );					
						pss.setString(3, batchPgms.getBranchId());
						//will be added later pss.setString(4, batchPgms.getBatchPgmId());							
						pss.setString(4, batchPgms.getBatchName());					
						pss.setString(5, batchPgms.getDataType());
									
					}
					
				},new ResultSetExtractor<BatchPgms>() {

					@Override
					public BatchPgms extractData(ResultSet rs) throws SQLException,
							DataAccessException {			
						BatchPgms batchPgm = new BatchPgms();
						
						if(rs.next()){
						batchPgm.setDbTs(rs.getInt("DB_TS"));
						batchPgm.setInstId(rs.getString("INST_ID"));
						batchPgm.setBranchId(rs.getString("BRANCH_ID"));	
						batchPgm.setBatchPgmId(rs.getString("BATCH_PGM_ID"));
						batchPgm.setBatchPgmName(rs.getString("BATCH_NAME"));
						batchPgm.setUpldDataType(rs.getString("UPLD_DATA_TYPE"));
						batchPgm.setXmlFileName(rs.getString("XML_FILE_NAME"));
						batchPgm.setExcelFileName(rs.getString("EXCEL_FILE_NAME"));
						batchPgm.setXmlFileSrlNo(rs.getString("XML_FILE_SRL_NO"));
						batchPgm.setSheetName(rs.getString("SHEET_NAME"));
						batchPgm.setDelFlag(rs.getString("DEL_FLG"));
						batchPgm.setrModId(rs.getString("R_MOD_ID"));
						batchPgm.setrModTime(rs.getString("R_MOD_TIME"));
						batchPgm.setrCreId(rs.getString("R_CRE_ID"));									
						}
						return batchPgm;
					}
					
				} );							
		if(batchPgmRec==null){
			throw new NoRecordFoundException();
		}
											
			return batchPgmRec;
	}
	
	@Override
	public void insertBatchPgmRec(final BatchPgms batchPgms) throws DuplicateEntryException {
		logger.debug("Inside insert method");		
		StringBuffer sql=new StringBuffer();
		sql=sql.append("insert into bpgm ( ")
				.append("DB_TS, ") 
				.append("INST_ID, ") 
				.append("BRANCH_ID, ") 
				.append("BATCH_PGM_ID, ") 
				.append("BATCH_NAME, ") 
				.append("UPLD_DATA_TYPE, ") 
				.append("XML_FILE_NAME, ")
				.append("EXCEL_FILE_NAME, ")
				.append("XML_FILE_SRL_NO, ") 
				.append("SHEET_NAME, ")		
				.append("DEL_FLG, ")
				.append("R_MOD_ID, ")
				.append("R_MOD_TIME, ") 
				.append("R_CRE_ID, ")				
				.append("R_CRE_TIME)")
		.append(" values (?,?,?,?,?,?,?,?,?,?,?,?,now(),?,now())");
		try{
			getJdbcTemplate().update(sql.toString(),new PreparedStatementSetter() {
				
				
				@Override
				public void setValues(PreparedStatement pss) throws SQLException {
					pss.setInt(1,batchPgms.getDbTs());
					pss.setString(2,batchPgms.getInstId().trim());
					pss.setString(3,batchPgms.getBranchId().trim());
					pss.setString(4,batchPgms.getBatchPgmId().trim());						
					pss.setString(5,batchPgms.getBatchPgmName().trim());
					pss.setString(6,batchPgms.getUpldDataType().trim());
					pss.setString(7,batchPgms.getXmlFileName());
					pss.setString(8,batchPgms.getExcelFileName());
					pss.setString(9,batchPgms.getXmlFileSrlNo());	
					pss.setString(10,batchPgms.getSheetName());
					pss.setString(11,batchPgms.getDelFlag().trim());		
					pss.setString(12,batchPgms.getrModId().trim());								
					pss.setString(20,batchPgms.getrCreId().trim());										
				}
			});		 
		}
		catch ( org.springframework.dao.DuplicateKeyException e) {
			throw new DuplicateEntryException();
		} 
		
		

	}
	
	@Override
	public void updateBatchPgmRec(final BatchPgmsKey batchPgmsKey, final BatchPgms batchPgms) throws UpdateFailedException {
logger.debug("Inside update method");
		StringBuffer sql=new StringBuffer();
		sql.append("update bpgm set")
				.append("DB_TS = ?") 
				.append("INST_ID = ?") 
				.append("BRANCH_ID = ?") 
				.append("BATCH_PGM_ID = ?") 
				.append("BATCH_NAME = ?") 
				.append("UPLD_DATA_TYPE = ?") 
				.append("XML_FILE_NAME = ?")
				.append("EXCEL_FILE_NAME = ?")
				.append("XML_FILE_SRL_NO = ?") 
				.append("SHEET_NAME = ?")		
				.append("DEL_FLG = ?")
				.append("R_MOD_ID = ?")
				.append(", R_MOD_TIME= now()")
				.append(" where")
				.append(" DEL_FLG = ? ")
				.append(" AND INST_ID = ? ")
				.append(" AND BRANCH_ID = ? ")
	//will be added later	.append(" AND BATCH_PGM_ID = ? ")
				.append(" AND BATCH_NAME = ? ")
				.append(" AND UPLD_DATA_TYPE = ? ")
				.append(" and")
				.append(" DB_TS=?")	;																
		logger.debug("update query :"+sql.toString());
		int updateStatus =	getJdbcTemplate().update(sql.toString(),new PreparedStatementSetter(){

			@Override
			public void setValues(PreparedStatement ps) throws SQLException {				
				ps.setInt(1, batchPgms.getDbTs()+1);			
				ps.setString(2, batchPgms.getInstId().trim());				
				ps.setString(3, batchPgms.getBranchId().trim());			
				ps.setString(4, batchPgms.getBatchPgmId().trim());				
				ps.setString(5, batchPgms.getBatchPgmName().trim());				
				ps.setString(6, batchPgms.getUpldDataType().trim());						
				ps.setString(7, batchPgms.getXmlFileName().trim());					
				ps.setString(8, batchPgms.getExcelFileName().trim());						
				ps.setString(9, batchPgms.getXmlFileSrlNo().trim());										
				ps.setString(10, batchPgms.getSheetName());			
				ps.setString(11, batchPgms.getDelFlag());								
				ps.setString(12, batchPgms.getrModId());			
				ps.setString(13, "N");												
				ps.setString(14, batchPgmsKey.getInstId());			
				ps.setString(15, batchPgmsKey.getBranchId().trim());		
				ps.setString(16, batchPgmsKey.getBatchName());							
				ps.setString(17, batchPgmsKey.getDataType());														
			}
			
		});
		if(updateStatus == 0){
			throw new UpdateFailedException();
			
		}

	}


}
