package com.jaw.batch.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.jaw.batch.controller.BatchStatusSearchVO;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.framework.dao.BaseDao;


//Dao class for list of BatchStatus Reocrd
@Repository
public class BatchStatusListDao extends BaseDao implements IBatchStatusListDao {
	Logger logger = Logger.getLogger(BatchStatusListDao.class);

	
	//Method to select the list of BatchList Record 
	@Override
	public List<BatchStatus> retrieveBatchStatus(final BatchStatusSearchVO batchStatus) throws NoDataFoundException {
		logger.debug("retrive batchStatus List");			
		
		List<String> data=new ArrayList<String>();
		StringBuffer sql=new StringBuffer()		
		.append("select ")		
		.append("BATCH_SRL_NO, ") 
		.append("UPLD_DATA_TYPE, ") 
		.append("BATCH_STATUS, ") 
		.append("EXEC_START_DATE, ")
		.append("EXEC_END_DATE, ") 		
		.append("BATCH_NAME, ")
		.append("TOTAL_NO_OF_REC")		
		.append(" from bsts ")
		.append("where ")
		.append(" DEL_FLG = ?" )
		.append(" and")
		.append(" INST_ID = ?" )
		.append(" and")
		.append(" BRANCH_ID = ?" )
		.append(" and")
		.append(" BATCH_NAME =?");		
		data.add("N");	
		data.add(batchStatus.getInstId());
		data.add(batchStatus.getBranchId());
		data.add(batchStatus.getBatchName());		
		
		if(batchStatus.getBatchStatus()!=""){
			sql.append( " and BATCH_STATUS = ?");	
			data.add(batchStatus.getBatchStatus());
		}
		
		if((batchStatus.getBatchIdFrom()!="")) {
			sql.append( " and BATCH_SRL_NO>=?");				
			data.add(batchStatus.getBatchIdFrom());
		}
		if((batchStatus.getBatchIdTo()!="")) {
			sql.append( " and BATCH_SRL_NO<=?");				
			data.add(batchStatus.getBatchIdTo());
		}
		if((batchStatus.getExecStartDate()!="")) {			
			sql.append( " and EXEC_START_DATE>=?");				
			StringBuffer exeStartDate = new StringBuffer(batchStatus.getExecStartDate());			
			exeStartDate.append(" 00:00:01");			
			data.add(exeStartDate.toString());
		}
		if((batchStatus.getExecEndDate()!="")) {
			sql.append( " and EXEC_END_DATE<=?");				
			StringBuffer exeEndtDate = new StringBuffer(batchStatus.getExecEndDate());
			exeEndtDate.append(" 23:59:59");			
			data.add(exeEndtDate.toString());
		}
		sql.append(" ORDER BY BATCH_SRL_NO desc");
		String[] array = data.toArray(new String[data.size()]);
		
		logger.debug("select query :"+sql.toString());
		List<BatchStatus> batchStatusList = getJdbcTemplate().query(sql.toString(),array,new BatchStatusRowMapper());	
		if(batchStatusList.size()==0) {
			throw new NoDataFoundException();
		}
		return batchStatusList;
	}
	class BatchStatusRowMapper implements RowMapper<BatchStatus>{
		@Override
		public BatchStatus mapRow(ResultSet rs, int arg1) throws SQLException {
			BatchStatus batchStatus  =new BatchStatus();			
			batchStatus.setBatchSrlNo(rs.getString("BATCH_SRL_NO"));
			batchStatus.setUpldDataType(rs.getString("UPLD_DATA_TYPE"));
			batchStatus.setBatchStatus(rs.getString("BATCH_STATUS"));
			batchStatus.setExecStartDate(rs.getString("EXEC_START_DATE"));
			batchStatus.setExecEndDate(rs.getString("EXEC_END_DATE"));			
			batchStatus.setBatchName(rs.getString("BATCH_NAME"));				
			batchStatus.setTotalNoOfrec(rs.getString("TOTAL_NO_OF_REC"));				
			return batchStatus;		
		}
		}
}
