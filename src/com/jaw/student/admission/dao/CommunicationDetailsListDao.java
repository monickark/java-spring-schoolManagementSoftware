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
public class CommunicationDetailsListDao extends BaseDao implements
		ICommunicationDetailsListDao {
	Logger logger = Logger.getLogger(CommunicationDetailsListDao.class);
	DateUtil dateUtil = new DateUtil();
	@Override
	public void insertCommunicationDetailsList(
			final List<CommunicationDetails> communicationDetailsList) throws RuntimeExceptionForBatch {		
		StringBuffer sql = new StringBuffer("insert into communicationdetails(")
		.append("DB_TS, ") 
		.append("INST_ID, ") 
		.append("BRANCH_ID, ") 
		.append("STUDENT_ADMIS_NO, ") 
		.append("EMERG_CONTACT_NO, ") 
		.append("MOBILE_NO_SMS, ") 
		.append("RES_ADD1, ")
		.append("RES_ADD2, ") 
		.append("RES_ADD3, ")
		.append("RES_CITY, ")
		.append("RES_PINCODE, ")
		.append("RES_TELE, ")
		.append("DEL_FLG, ") 
		.append("R_MOD_ID, ")	
		.append("R_MOD_TIME,")
		.append("R_CRE_ID, ")
		.append("R_CRE_TIME")
		.append(") values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,now(),?,now())");
					
					try{
				 int[] a= getJdbcTemplate().batchUpdate(sql.toString(), new BatchPreparedStatementSetter() {				
						 
				 
					@Override
					public void setValues(PreparedStatement ps, int i) throws SQLException {
						
						CommunicationDetails communicationDetails = communicationDetailsList.get(i);					
						
						ps.setInt(1,communicationDetails.getDbTs());
						ps.setString(2,communicationDetails.getInstId());
						ps.setString(3,communicationDetails.getBranchId());
						ps.setString(4,communicationDetails.getStudentAdmisNo());					
						ps.setString(5,communicationDetails.getEmergContactNo());
						ps.setString(6,communicationDetails.getMobileNoSms());
						ps.setString(7,communicationDetails.getResidenceAdd1());
						ps.setString(8,communicationDetails.getResidenceAdd2());
						ps.setString(9,communicationDetails.getResidenceAdd3());	
						ps.setString(10,communicationDetails.getCity());	
						ps.setString(11,communicationDetails.getPincode());	
						ps.setString(12,communicationDetails.getResidenceTele());
						ps.setString(13,"N");
						ps.setString(14,communicationDetails.getrModId());				
						ps.setString(15,communicationDetails.getrCreId());				
					}
				 
					@Override
					public int getBatchSize() {
						return communicationDetailsList.size();
					}
				  }
				
						 );		
					}catch(RuntimeException e){						
						throw new RuntimeExceptionForBatch();
						
					}

	}
	@Override
public List<CommunicationDetails> retriveCommunicationDetailsList() throws NoDataFoundException {
		
		logger.debug("retrive PrevAcademicDetails List");
		StringBuffer sql=new StringBuffer()
		.append("select ")
		.append("DB_TS, ") 
		.append("INST_ID, ") 
		.append("BRANCH_ID, ") 
		.append("STUDENT_ADMIS_NO, ") 
		.append("EMERG_CONTACT_NO, ") 
		.append("MOBILE_NO_SMS, ") 
		.append("RES_ADD1, ")
		.append("RES_ADD2, ") 
		.append("RES_ADD3, ")
		.append("RES_CITY, ")
		.append("RES_PINCODE, ")
		.append("RES_TELE, ")
		.append("DEL_FLG, ") 
		.append("R_MOD_ID, ")
		.append("R_MOD_TIME, ")
		.append("R_CRE_ID, ")
		.append("R_CRE_TIME")
		.append(" from communicationdetails ")
		.append("where ")
		.append(" DEL_FLG =?")
		.append(" ORDER BY STUDENT_ADMIS_NO");
		logger.debug("select query :"+sql.toString());
		List<CommunicationDetails> communicationDetailsList = getJdbcTemplate().query(sql.toString(), new PreparedStatementSetter(){
			@Override
			public void setValues(PreparedStatement pss)
					throws SQLException {
				pss.setString(1,"N");						
				
			}
			
		},new CommunicationDetailsRowMapper());
		if(communicationDetailsList.size()==0){
			throw new NoDataFoundException();
		}
																		
		return communicationDetailsList;	
}
class CommunicationDetailsRowMapper implements RowMapper<CommunicationDetails>{
@Override
public CommunicationDetails mapRow(ResultSet rs, int arg1) throws SQLException {
	CommunicationDetails communicationDetails=new CommunicationDetails();
	communicationDetails.setDbTs(rs.getInt("DB_TS"));
	communicationDetails.setInstId(rs.getString("INST_ID"));
	communicationDetails.setBranchId(rs.getString("BRANCH_ID"));
	communicationDetails.setStudentAdmisNo(rs.getString("STUDENT_ADMIS_NO"));
	communicationDetails.setEmergContactNo(rs.getString("EMERG_CONTACT_NO"));
	communicationDetails.setMobileNoSms(rs.getString("MOBILE_NO_SMS"));
	communicationDetails.setResidenceAdd1(rs.getString("RES_ADD1"));
	communicationDetails.setResidenceAdd2(rs.getString("RES_ADD2"));
	communicationDetails.setResidenceAdd3(rs.getString("RES_ADD3"));
	communicationDetails.setCity(rs.getString("RES_CITY"));		
	communicationDetails.setPincode(rs.getString("RES_PINCODE"));		
	communicationDetails.setResidenceTele(rs.getString("RES_TELE"));
	communicationDetails.setDelFlg(rs.getString("DEL_FLG"));
	communicationDetails.setrModId(rs.getString("R_MOD_ID"));
	communicationDetails.setrModTime(rs.getString("R_MOD_TIME"));
	communicationDetails.setrCreId(rs.getString("R_CRE_ID"));
	communicationDetails.setrCreTime(rs.getString("R_CRE_TIME"));			
	return communicationDetails;		
}
}
}
