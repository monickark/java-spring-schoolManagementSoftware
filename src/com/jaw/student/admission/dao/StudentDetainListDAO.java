package com.jaw.student.admission.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;



import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.batch.DataIntegrityException;
import com.jaw.common.exceptions.batch.RuntimeExceptionForBatch;
import com.jaw.framework.dao.BaseDao;
@Repository
public class StudentDetainListDAO extends BaseDao implements IStudentDetainListDao {
	
	Logger logger = Logger.getLogger(StudentDetainListDAO.class);

	@Override
	public List<StudentDetain> getDetainedStuList( final String acyYear,final String stuGrpId) throws NoDataFoundException {
		logger.debug("retrive StudentMaster List");		
		
		StringBuffer sql = new StringBuffer()
				.append("SELECT ")
				.append("stum.STUDENTGRP_ID,")
				.append("STUDENT_GRP,")
				.append("stum.STUDENT_ADMIS_NO,")
				.append("DETAIN_RMKS,")				
				.append("STUDENT_NAME ")		
				.append(" FROM stum,stdd,stgm ").append("WHERE ")
				.append("stum.INST_ID=stdd.INST_ID AND ")	
				.append("stum.INST_ID=stgm.INST_ID AND ")	
				.append("stum.BRANCH_ID=stdd.BRANCH_ID AND ")
				.append("stum.BRANCH_ID=stgm.BRANCH_ID AND ")	
				.append("stdd.AC_TeRM=stum.ACADEMIC_YEAR AND ")	
				.append("stum.DEL_FLG=stdd.DEL_FLG AND ")
				.append("stum.DEL_FLG=stgm.DEL_FLG AND ")	
				.append("stum.STUDENT_ADMIS_NO=stdd.STUDENT_ADMIS_NO AND ")	
				.append("stum.STUDENTGRP_ID=stgm.STUDENTGRP_ID AND ")	
				.append("stum.ACADEMIC_YEAR=? AND ")
				.append("stum.STUDENTGRP_ID=? ")					
				.append(" AND stum.DEL_FLG ='N'")
				.append(" ORDER BY STUDENT_ADMIS_NO");
		logger.debug("select query :" + sql.toString());
		List<StudentDetain> studentMasterList = getJdbcTemplate().query(
				sql.toString(), new PreparedStatementSetter() {

					@Override
					public void setValues(PreparedStatement pss)
							throws SQLException {
						pss.setString(1, acyYear);
						pss.setString(2, stuGrpId);
								
						

					}

				}, new RowMapper<StudentDetain>(){

					@Override
					public StudentDetain mapRow(ResultSet rs, int arg1)
							throws SQLException {
						StudentDetain stuDetain = new StudentDetain();
						stuDetain.setStuGrpId(rs.getString("STUDENTGRP_ID"));
						stuDetain.setStuAdmisNo(rs.getString("STUDENT_ADMIS_NO"));
						stuDetain.setStuName(rs.getString("STUDENT_NAME"));
						stuDetain.setDetainRemarks(rs.getString("DETAIN_RMKS"));
						stuDetain.setStuGrpName(rs.getString("STUDENT_GRP"));
						return stuDetain;
					}
					
				});
		
		if(studentMasterList.size()==0){
			throw new NoDataFoundException();			
		}

		return studentMasterList;
	}
	
	
	
	@Override
	public List<String> retriveListOfRecForDuplicateCheck(final String academicTerm,
			List<String> valueList,String instId,String branchId) {
		
		final int batchSize = 50;
		int count = 0;
		List<String> listOfString = null;
		
		logger.debug("retrive record List for Duplicate check");		
					
			StringBuffer sql = new StringBuffer().append("SELECT ")					
					.append("STUDENT_ADMIS_NO")					
					.append(" FROM stdd ")					
					.append(" WHERE ")				
					.append("STUDENT_ADMIS_NO")
					.append(" IN (");					
						for(int index = 0; index < valueList.size(); index++){						
							if(index==0){
								sql.append("?");
							}else{
							
								sql.append(",?");	
							}
						}
						sql.append(")");	
						sql.append(" AND INST_ID=?")
						.append(" AND BRANCH_ID=?");
					
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
													   
														String value = rs.getString("STUDENT_ADMIS_NO");
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
													String value = rs.getString("STUDENT_ADMIS_NO");
														list.add(value);
													}											 																	 
									return list;
									
								}
								
							});
											    																		
		return listOfString;
	
	}
	
	
	
	
	
	
	
	public void insertStudentMasterList(final List<StudentDetain> studentList)
			throws DataIntegrityException, RuntimeExceptionForBatch {		
		logger.debug("inside the method to insert student detain list");
		StringBuffer sql = new StringBuffer("insert into student_detain_details(")
	//	DB_TS, INST_ID, BRANCH_ID, AC_TERM, STUDENT_ADMIS_NO, DETAIN_RMKS, DEL_FLG, R_MOD_ID, R_MOD_TIME, R_CRE_ID, R_CRE_TIME
				.append("DB_TS,")
				.append("INST_ID,")
				.append("BRANCH_ID,")
				//.append("PARENT_USER_ID,")
				.append("AC_TERM,")
				.append("STUDENT_ADMIS_NO,")
				.append("DETAIN_RMKS,")				
				.append("DEL_FLG, ")
				.append("R_CRE_ID, ")
				.append("R_MOD_ID, ")
				.append("R_MOD_TIME,")				
				.append("R_CRE_TIME")
				.append(" ) values(?,?,?,?,?,?,?,?,?,now(),now())");

		 try{

		int[] a = getJdbcTemplate().batchUpdate(sql.toString(),
				new BatchPreparedStatementSetter() {

					@Override
					public void setValues(PreparedStatement ps, int i)
							throws SQLException {

						StudentDetain studentDetain = studentList.get(i);

						ps.setInt(1, Integer.valueOf(studentDetain.getDbTs()));
						ps.setString(2, studentDetain.getInstId());
						ps.setString(3, studentDetain.getBranchId());					
						ps.setString(4, studentDetain.getAcademicYear());
						ps.setString(5, studentDetain.getStuAdmisNo());
						ps.setString(6,studentDetain.getDetainRemarks());
						ps.setString(7, "N");
						ps.setString(8,studentDetain.getrCreId());	
						ps.setString(9,studentDetain.getrCreId());					
										
					}

					@Override
					public int getBatchSize() {

						return studentList.size();
					}
				}

		);
		
		 }catch(RuntimeException e){
			 e.printStackTrace();
			 logger.info("exception :"+e);
				throw new RuntimeExceptionForBatch();
			}		
		
	}


}
