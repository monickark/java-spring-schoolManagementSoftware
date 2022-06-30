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
import com.jaw.common.util.DateUtil;
import com.jaw.framework.dao.BaseDao;
@Repository
public class SiblingDetailsListDao extends BaseDao implements ISiblingDetailsListDao {
	DateUtil dateUtil = new DateUtil();
	Logger logger = Logger.getLogger(SiblingDetailsListDao.class);
	@Override
	public void insertSiblingDetailsList(final List<SiblingDetails> siblingDetailsList) throws RuntimeExceptionForBatch {
		StringBuffer sql = new StringBuffer("insert into siblingdetails(")
		.append("DB_TS, ")
		.append("INST_ID, " )
		.append("BRANCH_ID, " )
		.append("STUDENT_ADMIS_NO, " )		
		.append("SIBLING_NO, ")
		.append("SIBLING_NAME, " )
		.append("SIBLING_CLASS, ")
		.append("SIBLING_YEAR, 	" )
		.append("SIBLING_ADMIS_NO, " )
		.append("DEL_FLG, " )
		.append("R_MOD_ID, " )	
		.append("R_MOD_TIME,")
		.append("R_CRE_ID, " )
		.append("R_CRE_TIME " )
		.append(") values(?,?,?,?,?,?,?,?,?,?,?,now(),?,now())");
try{								
			 int[] a= getJdbcTemplate().batchUpdate(sql.toString(), new BatchPreparedStatementSetter() {		
					 
			 
				@Override
				public void setValues(PreparedStatement ps, int i) throws SQLException {
					
					SiblingDetails siblingDetails = siblingDetailsList.get(i);					
					ps.setInt(1,siblingDetails.getDbTs());
					ps.setString(2,siblingDetails.getInstId());
					ps.setString(3,siblingDetails.getBranchId());
					ps.setString(4,siblingDetails.getStudentAdmisNo());				
					ps.setString(5,siblingDetails.getSiblingNo());
					ps.setString(6,siblingDetails.getSiblingName());
					ps.setString(7,siblingDetails.getSiblingClass());
					ps.setString(8,siblingDetails.getSiblingYear());
					ps.setString(9,siblingDetails.getSiblingAdmisNo());
					ps.setString(10,"N");
					ps.setString(11,siblingDetails.getrModId());				
					ps.setString(12,siblingDetails.getrCreId());				
				}
			 
				@Override
				public int getBatchSize() {
					return siblingDetailsList.size();
				}
			  }
			
					 );		
}catch(RuntimeException e){
	e.printStackTrace();
	throw new RuntimeExceptionForBatch();
}
	}
	@Override
public List<SiblingDetails> retriveSiblingDetailsList() throws NoDataFoundException {
		
		logger.debug("retrive PrevAcademicDetails List");
		StringBuffer sql=new StringBuffer()
		.append("select ")
		.append("DB_TS, ")
			.append("INST_ID, " )
			.append("BRANCH_ID, " )
			.append("STUDENT_ADMIS_NO, " )			
			.append("SIBLING_NO, ")
			.append("SIBLING_NAME, " )
			.append("SIBLING_CLASS, ")
			.append("SIBLING_YEAR, 	" )
			.append("SIBLING_ADMIS_NO, " )
			.append("DEL_FLG, " )
			.append("R_MOD_ID, " )
			.append("R_MOD_TIME, " )
			.append("R_CRE_ID, " )
			.append("R_CRE_TIME" )
			.append(" from siblingdetails ")
		.append("where")
		.append(" DEL_FLG =?")
		.append(" ORDER BY STUDENT_ADMIS_NO");
		logger.debug("select query :"+sql.toString());
		List<SiblingDetails> siblingDetailsList = getJdbcTemplate().query(sql.toString(), new PreparedStatementSetter(){

			@Override
			public void setValues(PreparedStatement pss)
					throws SQLException {
				pss.setString(1,"N");						
				
			}
			
		},new SiblingDetailsRowMapper());
		if(siblingDetailsList.size()==0){
			throw new NoDataFoundException();
		}
																		
		return siblingDetailsList;	
}
class SiblingDetailsRowMapper implements RowMapper<SiblingDetails>{
@Override
public SiblingDetails mapRow(ResultSet rs, int arg1) throws SQLException {
	SiblingDetails siblingDetails=new SiblingDetails();
	siblingDetails.setDbTs(rs.getInt("DB_TS"));
	siblingDetails.setInstId(rs.getString("INST_ID"));
	siblingDetails.setBranchId(rs.getString("BRANCH_ID"));
	siblingDetails.setStudentAdmisNo(rs.getString("STUDENT_ADMIS_NO"));
	siblingDetails.setSiblingNo(rs.getString("SIBLING_NO"));	
	siblingDetails.setSiblingName(rs.getString("SIBLING_NAME"));
	siblingDetails.setSiblingClass(rs.getString("SIBLING_CLASS"));
	siblingDetails.setSiblingYear(rs.getString("SIBLING_YEAR"));
	siblingDetails.setSiblingAdmisNo(rs.getString("SIBLING_ADMIS_NO"));
	siblingDetails.setDelFlg(rs.getString("DEL_FLG"));
	siblingDetails.setrModId(rs.getString("R_MOD_ID"));
	siblingDetails.setrModTime(rs.getString("R_MOD_TIME"));
	siblingDetails.setrCreId(rs.getString("R_CRE_ID"));
	siblingDetails.setrCreTime(rs.getString("R_CRE_TIME"));			
	return siblingDetails;		

}
}
}
