package com.jaw.communication.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.jaw.common.constants.ApplicationConstant;
import com.jaw.common.constants.BatchConstants;
import com.jaw.common.constants.CommonCodeConstant;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.framework.dao.BaseDao;
@Repository
public class SMSListDAO extends BaseDao implements ISMSListDAO {
	// Logging
	Logger logger = Logger.getLogger(SMSListDAO.class);

	@Override
	public List<SMSMemberList> getMemberListForStudent(
			SMSListKey smsListKey,int offset) throws NoDataFoundException {
		//For Student
		logger.debug("DAO :SMS List select method");
		logger.debug("DAO :SMS List key values :"+smsListKey.toString());
		List<Object> data = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		    sql.append("select ")
				 .append("stum.STUDENT_NAME as studentName,")
					.append("stin.MOBILE_NO as mobileNumber")
					
				.append(" from stum,stin ")
				.append(" where")
				.append(" stum.INST_ID=stin.INST_ID")
				.append("  and stum.BRANCH_ID=stin.BRANCH_ID")
				.append("  and stum.DEL_FLG=stin.DEL_FLG")
				.append("  and stum.STUDENT_ADMIS_NO=stin.STUDENT_ADMIS_NO")
				.append("  and stin.MOBILE_NO<>''")
				.append("  and stum.ACADEMIC_YEAR=?")
				.append(" and  stum.INST_ID= ?")
			.append(" and stum.BRANCH_ID= ?")
			.append(" and  stum.DEL_FLG=?");
		   
			data.add(smsListKey.getAcTerm());
			data.add(smsListKey.getInstId());
			data.add(smsListKey.getBranchId());
			data.add("N");
			
			if((smsListKey.getStudentGrpId()!=null)&&(smsListKey.getStudentGrpId()!="")){
				sql.append(" and stum.STUDENTGRP_ID=?");
				data.add(smsListKey.getStudentGrpId());
			}
			 sql.append(" order by studentName ");
			 if(!smsListKey.getGenCategory().equals(ApplicationConstant.SPECIFIC_MEMBER_LIST)){
				 sql.append(" limit ")
				 .append(" "+Integer.valueOf(BatchConstants.BATCH_SIZE_FOR_SEND_SMS)+" ")
				 .append(" offset  ")
				.append(" "+ offset +" "); 
			 }
		Object[] array = data.toArray(new Object[data.size()]);
		logger.debug("Where clause conditions size :" + array.length);

		List<SMSMemberList> selectedList= getJdbcTemplate()
				.query(sql.toString(), array, new MemberSelectForStudentRowMapper());
		if (selectedList.size() == 0) {
			logger.error("No  record found");
			throw new NoDataFoundException();
		}
		logger.debug("DAO : Alert List value"+selectedList.size() );
		return selectedList;
	}

	@Override
	public List<SMSMemberList> getMemberListForStaff(SMSListKey smsListKey,int offset)
			throws NoDataFoundException {
	//	select stfd.STAFF_NAME as staffName,stfm.MOBILE as mobileNumber from stfd,stfm where stfd.INST_ID=stfm.INST_ID and stfd.BRANCH_ID=stfm.BRANCH_ID and
		//		stfd.DEL_FLG=stfm.DEL_FLG and stfd.STAFF_ID=stfm.STAFF_ID and stfd.INST_ID='ASC' and stfd.BRANCH_ID='BR001' and stfd.DEL_FLG='N' and stfd.DEPT_ID='D2';
		logger.debug("DAO :SMS List select method");
		logger.debug("DAO :SMS List key values :"+smsListKey.toString());
		List<Object> data = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		    sql.append("select ")
				 .append("stfd.STAFF_NAME as staffName,")
					.append("stfm.MOBILE as mobileNumber")
					
				.append(" from stfd,stfm ")
				.append(" where")
				.append(" stfd.INST_ID=stfm.INST_ID")
				.append("  and stfd.BRANCH_ID=stfm.BRANCH_ID")
				.append("  and stfd.DEL_FLG=stfm.DEL_FLG")
				.append("  and stfd.STAFF_ID=stfm.STAFF_ID")	
				.append("  and stfm.MOBILE<>''")
				.append(" and  stfd.INST_ID= ?")
			.append(" and stfd.BRANCH_ID= ?")
			.append(" and  stfd.DEL_FLG=?");
		  //  .append(" and stfd.DEPT_ID=?");
			data.add(smsListKey.getInstId());
			data.add(smsListKey.getBranchId());
			data.add("N");
			
			if((smsListKey.getDepartmentId()!=null)&&(smsListKey.getDepartmentId()!="")){
				sql.append("and stfd.DEPT_ID=?");
				data.add(smsListKey.getDepartmentId());
			}
			 sql.append(" order by staffName ");
			 if(!smsListKey.getGenCategory().equals(ApplicationConstant.SPECIFIC_MEMBER_LIST)){
				 sql.append(" limit ")
				 .append(" "+Integer.valueOf(BatchConstants.BATCH_SIZE_FOR_SEND_SMS)+" ")
				 .append(" offset  ")
				.append(" "+ offset +" "); 
			 }
		Object[] array = data.toArray(new Object[data.size()]);
		logger.debug("Where clause conditions size :" + array.length);

		List<SMSMemberList> selectedList= getJdbcTemplate()
				.query(sql.toString(), array, new MemberSelectForStaffRowMapper());
		if (selectedList.size() == 0) {
			logger.error("No  record found");
			throw new NoDataFoundException();
		}
		logger.debug("DAO : Alert List value"+selectedList.size() );
		return selectedList;
	}

	@Override
	public List<SMSMemberList> getMemberListForParent(SMSListKey smsListKey,int offset)
			throws NoDataFoundException {
		logger.debug("DAO :SMS List select method");
		logger.debug("DAO :SMS List key values :"+smsListKey.toString());
		List<Object> data = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		    sql.append("select ")
				 .append("stum.STUDENT_NAME as studentName,")
				  .append("pard.FATHER_NAME as parentName,")
					.append("comd.MOBILE_NO_SMS as mobileNumber")
					
				.append(" from stum,comd,pard ")
				.append(" where")
				.append(" stum.INST_ID=comd.INST_ID")
				.append("  and stum.BRANCH_ID=comd.BRANCH_ID")
				.append("  and stum.DEL_FLG=comd.DEL_FLG")
				.append("  and stum.STUDENT_ADMIS_NO=comd.STUDENT_ADMIS_NO")
					.append(" and stum.INST_ID=pard.INST_ID")
				.append("  and stum.BRANCH_ID=pard.BRANCH_ID")
				.append("  and stum.DEL_FLG=pard.DEL_FLG")
				.append("  and comd.MOBILE_NO_SMS<>''")
				.append("  and stum.STUDENT_ADMIS_NO=pard.STUDENT_ADMIS_NO")
				.append("  and stum.ACADEMIC_YEAR=?")
				.append(" and  stum.INST_ID= ?")
			.append(" and stum.BRANCH_ID= ?")
			.append(" and  stum.DEL_FLG=?");
		  //  .append(" and stum.STUDENTGRP_ID=?");
			data.add(smsListKey.getAcTerm());
			data.add(smsListKey.getInstId());
			data.add(smsListKey.getBranchId());
			data.add("N");
			if((smsListKey.getStudentGrpId()!=null)&&(smsListKey.getStudentGrpId()!="")){
				sql.append(" and stum.STUDENTGRP_ID=?");
				data.add(smsListKey.getStudentGrpId());
			}
			
			
			 sql.append(" order by studentName ");
			 if(!smsListKey.getGenCategory().equals(ApplicationConstant.SPECIFIC_MEMBER_LIST)){
				 sql.append(" limit ")
				 .append(" "+Integer.valueOf(BatchConstants.BATCH_SIZE_FOR_SEND_SMS)+" ")
				 .append(" offset  ")
				.append(" "+ offset +" "); 
			 }
		Object[] array = data.toArray(new Object[data.size()]);
		logger.debug("Where clause conditions size :" + array.length);

		List<SMSMemberList> selectedList= getJdbcTemplate()
				.query(sql.toString(), array, new MemberSelectForParentRowMapper());
		if (selectedList.size() == 0) {
			logger.error("No  record found");
			throw new NoDataFoundException();
		}
		logger.debug("DAO : Alert List value"+selectedList.size() );
		return selectedList;
	}

	@Override
	public int getMobileNumCountForParent(SMSListKey smsListKey) {
		 logger.debug("Inside get mobile number list for  parent  method");

			logger.debug("SMS List Key object values :"
					+ smsListKey.toString());
			StringBuffer sql = new StringBuffer();
			
			
			
			sql.append("select count(comd.MOBILE_NO_SMS) ")
			.append("from stum,comd,pard ").append(" where ")		
					.append("  stum.INST_ID=comd.INST_ID")
					.append(" and stum.BRANCH_ID=comd.BRANCH_ID")
					.append(" and stum.DEL_FLG=comd.DEL_FLG")
					.append(" and stum.STUDENT_ADMIS_NO=comd.STUDENT_ADMIS_NO")
					.append(" and stum.INST_ID=pard.INST_ID")
						 .append(" and stum.BRANCH_ID=pard.BRANCH_ID ")
					     .append(" and stum.DEL_FLG=pard.DEL_FLG")
					     .append("  and comd.MOBILE_NO_SMS<>''")
					     .append(" and stum.STUDENT_ADMIS_NO=pard.STUDENT_ADMIS_NO")
					     .append(" and stum.ACADEMIC_YEAR='"+smsListKey.getAcTerm()+"'")
					.append(" and  stum.INST_ID= '"+smsListKey.getInstId()+"'")
					.append("  and stum.BRANCH_ID= '"+smsListKey.getBranchId()+"'")				
					.append(" and  stum.DEL_FLG='N'");
					if((smsListKey.getStudentGrpId()!="")&&(smsListKey.getStudentGrpId()!=null)){
						sql.append("and stum.STUDENTGRP_ID='"+smsListKey.getStudentGrpId()+"' ");
					}
				
					
					
					System.out.println("Sql Queryyyyyyyyyyyyy"+sql.toString());
							
			int rows = getJdbcTemplate().queryForInt(
					sql.toString());

			System.out.println("count "+rows);

			
			return rows; 
	 }

	@Override
	public int getMobileNumCountForStaff(SMSListKey smsListKey) {
		 logger.debug("Inside get mobile number list for Staff  method");

			logger.debug("SMS List Key object values :"
					+ smsListKey.toString());
			StringBuffer sql = new StringBuffer();
			
			
			sql.append("select count(stfm.MOBILE) ")
			.append("from stfd,stfm");
			
					sql.append(" where ")		
					.append("  stfd.INST_ID=stfm.INST_ID")
					.append(" and stfd.BRANCH_ID=stfm.BRANCH_ID")
					.append(" and stfd.DEL_FLG=stfm.DEL_FLG")
					.append(" and stfd.STAFF_ID=stfm.STAFF_ID")
					.append("  and stfm.MOBILE<>''")
					.append(" and  stfd.INST_ID= '"+smsListKey.getInstId()+"'")
					.append("  and stfd.BRANCH_ID= '"+smsListKey.getBranchId()+"'")				
					.append(" and  stfd.DEL_FLG='N'");
					if((smsListKey.getDepartmentId()!="")&&(smsListKey.getDepartmentId()!=null)){
						sql.append(" and  stfd.DEPT_ID='"+smsListKey.getDepartmentId()+"'");
					}
					
					
					
					
					System.out.println("Sql Queryyyyyyyyyyyyy"+sql.toString());
							
			int rows = getJdbcTemplate().queryForInt(
					sql.toString());

			System.out.println("count "+rows);

			
			return rows; 
	 }

	@Override
	public int getMobileNumCountForStudent(SMSListKey smsListKey) {
		 logger.debug("Inside get mobile number list for Student   method");

			logger.debug("SMS List Key object values :"
					+ smsListKey.toString());
			StringBuffer sql = new StringBuffer();
			
			
			
			sql.append("select count(stin.MOBILE_NO) ")
			.append("from stum,stin ").append(" where ")		
					.append("  stum.INST_ID=stin.INST_ID")
					.append(" and stum.BRANCH_ID=stin.BRANCH_ID")
					.append(" and stum.DEL_FLG=stin.DEL_FLG")
					.append(" and stum.STUDENT_ADMIS_NO=stin.STUDENT_ADMIS_NO")
					     .append(" and stum.ACADEMIC_YEAR='"+smsListKey.getAcTerm()+"'")
					.append(" and  stum.INST_ID= '"+smsListKey.getInstId()+"'")
					.append("  and stum.BRANCH_ID= '"+smsListKey.getBranchId()+"'")				
					.append(" and  stum.DEL_FLG='N'")
			        .append("and stin.MOBILE_NO<>''");
					if((smsListKey.getStudentGrpId()!="")&&(smsListKey.getStudentGrpId()!=null)){
						sql.append("and stum.STUDENTGRP_ID='"+smsListKey.getStudentGrpId()+"' ");
					}
				
					
					
					System.out.println("Sql Queryyyyyyyyyyyyy"+sql.toString());
							
			int rows = getJdbcTemplate().queryForInt(
					sql.toString());

			System.out.println("count "+rows);

			
			return rows; 
	 }

	@Override
	public List<SMSMemberList> selectParenMobileListForAlert(
			SMSListKey smsListKey,List<String> ids) throws NoDataFoundException {
		logger.debug("DAO :SMS List select method");
		logger.debug("DAO :SMS List key values :"+smsListKey.toString());
		List<Object> data = new ArrayList<Object>();
		/*select  comd.MOBILE_NO_SMS as mobileNumber
		 from stum,comd,pard  where stum.INST_ID=comd.INST_ID and stum.BRANCH_ID=comd.BRANCH_ID
		  and stum.DEL_FLG=comd.DEL_FLG and stum.STUDENT_ADMIS_NO=comd.STUDENT_ADMIS_NO
		and stum.INST_ID=pard.INST_ID and stum.BRANCH_ID=pard.BRANCH_ID  and stum.DEL_FLG=pard.DEL_FLG
		  and comd.MOBILE_NO_SMS<>''  and stum.STUDENT_ADMIS_NO=pard.STUDENT_ADMIS_NO  and stum.ACADEMIC_YEAR='AT3'
		 and  pard.INST_ID= 'ASC' and pard.BRANCH_ID= 'BR001' and  pard.DEL_FLG='N' and pard.STUDENT_ADMIS_NO in ('001/12-13','001/13-14');*/
		StringBuffer sql = new StringBuffer();
		    sql.append("select ")
		    .append("stum.STUDENT_NAME as studentName,")
		    .append("pard.STUDENT_ADMIS_NO,")
				.append("comd.MOBILE_NO_SMS as mobileNumber")					
				.append(" from stum,comd,pard ")
				.append(" where")
				.append(" stum.INST_ID=comd.INST_ID")
				.append("  and stum.BRANCH_ID=comd.BRANCH_ID")
				.append("  and stum.DEL_FLG=comd.DEL_FLG")
				.append("  and stum.STUDENT_ADMIS_NO=comd.STUDENT_ADMIS_NO")
					.append(" and stum.INST_ID=pard.INST_ID")
				.append("  and stum.BRANCH_ID=pard.BRANCH_ID")
				.append("  and stum.DEL_FLG=pard.DEL_FLG")
				.append("  and comd.MOBILE_NO_SMS<>''")
				.append("  and stum.STUDENT_ADMIS_NO=pard.STUDENT_ADMIS_NO")
				.append("  and stum.ACADEMIC_YEAR=?")
				.append(" and  pard.INST_ID= ?")
			.append(" and pard.BRANCH_ID= ?")
			.append(" and  pard.DEL_FLG=?")
			.append(" and  pard.STUDENT_ADMIS_NO in ( ");
		    for(int i=0;i<ids.size();i++){
		    	if(i==0){
					sql.append("'" +ids.get(i)+"'");
				}else{
					sql.append(", '" +ids.get(i)+"'");
				}
		    }
		    sql.append(" ) ");
		  //  .append(" and stum.STUDENTGRP_ID=?");
			data.add(smsListKey.getAcTerm());
			data.add(smsListKey.getInstId());
			data.add(smsListKey.getBranchId());
			data.add("N");
			 sql.append(" order by STUDENT_ADMIS_NO ");
				logger.debug("Sql query:" + sql.toString());
		Object[] array = data.toArray(new Object[data.size()]);
		logger.debug("Where clause conditions size :" + array.length);

		List<SMSMemberList> selectedList= getJdbcTemplate()
				.query(sql.toString(), array, new ParentMobileForAlertRowMapper());
		if (selectedList.size() == 0) {
			logger.error("No  record found");
			throw new NoDataFoundException();
		}
		logger.debug("DAO : Alert List value"+selectedList.size() );
		return selectedList;
	}

	
	
}
class ParentMobileForAlertRowMapper implements RowMapper<SMSMemberList> {

	@Override
	public SMSMemberList mapRow(ResultSet rs, int arg1) throws SQLException {
		SMSMemberList sMSMemberList = new SMSMemberList(); 
		sMSMemberList.setMobileNum(rs.getString("mobileNumber"));
		sMSMemberList.setAdmissionNum(rs.getString("STUDENT_ADMIS_NO"));
		sMSMemberList.setStudentName(rs.getString("studentName"));
		
	return sMSMemberList;
	}
	}
class MemberSelectForStudentRowMapper implements RowMapper<SMSMemberList> {

	@Override
	public SMSMemberList mapRow(ResultSet rs, int arg1) throws SQLException {
		SMSMemberList sMSMemberList = new SMSMemberList(); 	
		sMSMemberList.setStudentName(rs.getString("studentName"));
		sMSMemberList.setMobileNum(rs.getString("mobileNumber"));
	
	return sMSMemberList;
	}
	}

class MemberSelectForParentRowMapper implements RowMapper<SMSMemberList> {

	@Override
	public SMSMemberList mapRow(ResultSet rs, int arg1) throws SQLException {
		SMSMemberList sMSMemberList = new SMSMemberList(); 	
		sMSMemberList.setStudentName(rs.getString("studentName"));
		sMSMemberList.setMobileNum(rs.getString("mobileNumber"));
		sMSMemberList.setParentName(rs.getString("parentName"));
		
	
	return sMSMemberList;
	}
	}
class MemberSelectForStaffRowMapper implements RowMapper<SMSMemberList> {

	@Override
	public SMSMemberList mapRow(ResultSet rs, int arg1) throws SQLException {
		SMSMemberList sMSMemberList = new SMSMemberList(); 	
		sMSMemberList.setStaffName(rs.getString("staffName"));
		sMSMemberList.setMobileNum(rs.getString("mobileNumber"));
		
	
	return sMSMemberList;
	}
	}

