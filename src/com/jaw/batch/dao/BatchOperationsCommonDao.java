package com.jaw.batch.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import com.jaw.framework.dao.BaseDao;



@Repository
public class BatchOperationsCommonDao extends BaseDao implements IBatchOperationsCommonDao {
	Logger logger = Logger.getLogger(BatchOperationsCommonDao.class);

	@Override
	public List<String> retriveListOfRecForDuplicateCheck(final String uniqueProp,
			List<String> valueList,String tblName,String instId,String branchId) {
		
		final int batchSize = 50;
		int count = 0;
		List<String> listOfString = null;
		
		logger.debug("retrive Communication Details List");		
					
			StringBuffer sql = new StringBuffer().append("select ")					
					.append(uniqueProp)					
					.append(" from  ")
					.append(tblName)	
					.append(" where ")				
					.append(uniqueProp)
					.append(" in (");					
						for(int index = 0; index < valueList.size(); index++){						
							if(index==0){
								sql.append("?");
							}else{
							
								sql.append(",?");	
							}
						}
						sql.append(")");	
						sql.append(" and INST_ID=?")
						.append(" and BRANCH_ID=?");
					
						valueList.add(instId);
						valueList.add(branchId);
					logger.debug("select query in common dao:" + sql.toString());
					String[] array = valueList.toArray(new String[valueList.size()]);
					
					
					if(++count % batchSize == 0) {
						 listOfString =  getJdbcTemplate().query(
								sql.toString(), array, new ResultSetExtractor<List<String>>(){

									@Override
									public List<String> extractData(ResultSet rs) throws SQLException,
											DataAccessException {
										   List<String> list=new ArrayList<String>();
										 
												   while(rs.next()){
													   
														String value = rs.getString(uniqueProp);
															list.add(value);
														}											 
										
																					  
										return list;
										
									}
									
								});
									
				    }
					

					 listOfString =  getJdbcTemplate().query(
							sql.toString(), array, new ResultSetExtractor<List<String>>(){

								@Override
								public List<String> extractData(ResultSet rs) throws SQLException,
										DataAccessException {
									   List<String> list=new ArrayList<String>();									 
											   while(rs.next()){												 
													String value = rs.getString(uniqueProp);
														list.add(value);
													}											 																	 
									return list;
									
								}
								
							});
											    																		
		return listOfString;
	
	}

	@Override
	public String getNoOfRec(String tblName) {

		logger.debug("Inside insert method");

		StringBuffer sql = new StringBuffer("SELECT COUNT(*) FROM ")
				.append(tblName);		
		
		logger.debug("insert query :" + sql.toString());		
			Integer noOfRec = getJdbcTemplate().queryForInt(sql.toString());			
			return noOfRec.toString();
		
	
	}

}
