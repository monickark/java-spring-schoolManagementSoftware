package com.jaw.core.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import com.jaw.common.exceptions.DeleteFailedException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.UpdateFailedException;
import com.jaw.framework.dao.BaseDao;
//StudentGrpMaster DAO class
@Repository
public class StudentGroupMasterDAO extends BaseDao implements IStudentGroupMasterDAO{
	// Logging
	Logger logger = Logger.getLogger(StudentGroupMasterDAO.class);

	@Override
	public void insertStudentGrpMasterRec(final StudentGroupMaster studentGrpMaster)
			throws DuplicateEntryException {
		logger.debug("Inside insert method");
		logger.debug("StudentGrpMaster object values :"
				+ studentGrpMaster.toString());
		StringBuffer query = new StringBuffer();	
				query = query.append("insert into stgm ( ")
				.append("DB_TS,")
				.append("INST_ID,")
				.append("BRANCH_ID,")
				.append("COURSEMASTER_ID,")
				.append("STUDENTGRP_ID,")
				.append("STUDENT_GRP,")				
				.append("TERM_ID,")
				.append("SEC_ID,")
				.append("TTG_ID,")
				.append("TTG_PROCESS,")
				.append("TTG_ASSIGNMENT_ORDER,")
				.append("MEDIUM,")
				.append("DEL_FLG,")
				.append("R_MOD_ID,")
				.append("R_MOD_TIME,")
				.append("R_CRE_ID,")
				.append("R_CRE_TIME)")
				.append(" values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,now(),?,now())");

		try {
			getJdbcTemplate().update(query.toString(),
					new PreparedStatementSetter() {

						@Override
						public void setValues(PreparedStatement pss)
								throws SQLException {
							pss.setInt(1, studentGrpMaster.getDbTs());
							pss.setString(2, studentGrpMaster.getInstId());
							pss.setString(3, studentGrpMaster.getBranchId());
							pss.setString(4, studentGrpMaster.getCourseMasterId().trim());
							pss.setString(5, studentGrpMaster.getStudentGrpId().trim());
							pss.setString(6, studentGrpMaster.getStudentGrp().trim());							
							pss.setString(7, studentGrpMaster.getTermId());
							pss.setString(8, studentGrpMaster.getSecId().trim());
							pss.setString(9, studentGrpMaster.getTtgId().trim());
							pss.setString(10, studentGrpMaster.getTtgProcess().trim());
							pss.setInt(11, studentGrpMaster.getTtgAssignmentOrder());
							pss.setString(12, studentGrpMaster.getMedium().trim());
							pss.setString(13, studentGrpMaster.getDelFlag().trim());
							pss.setString(14, studentGrpMaster.getrModId().trim());
							pss.setString(15, studentGrpMaster.getrCreId().trim());

						}

					});
		} catch (org.springframework.dao.DuplicateKeyException e) {
			logger.error("Duplicate entry exception occured."+e.getMessage());
			throw new DuplicateEntryException();
		}
		
		
		
	}

	@Override
	public void updateStudentGrpMasterRec(final StudentGroupMaster studentGrpMaster,
			final StudentGroupMasterKey studentGrpMasterKey)
			throws UpdateFailedException {
		
		logger.debug("Inside update method");
		logger.debug("StudentGrpMaster object values :"+ studentGrpMaster.toString());
		logger.debug("StudentGrpMasterKey Details object values :"+ studentGrpMasterKey.toString());		
		
		StringBuffer sql = new StringBuffer();		
			sql.append("update stgm set")
		        .append(" DB_TS= ?,")				
				.append("COURSEMASTER_ID=?,")
				.append("STUDENT_GRP=?,")
				.append("TERM_ID=?,")
				.append("SEC_ID=?,")				
				.append("TTG_ID=?,")
				.append("TTG_PROCESS=?,")
				.append("TTG_ASSIGNMENT_ORDER=?,")
				.append("MEDIUM=?,")
				.append("DEL_FLG= 'N',")
				.append("R_MOD_ID= ?,")
				.append(" R_MOD_TIME=now()")
				.append(" where")
				.append(" DB_TS= ?")
				.append(" and  INST_ID= ?")
				.append(" and  BRANCH_ID= ?")
				.append(" and  STUDENTGRP_ID= ?")	
				.append(" and  DEL_FLG='N'");

		

		int updateRecs = getJdbcTemplate().update(sql.toString(),
				new PreparedStatementSetter() {

					@Override
					public void setValues(PreparedStatement ps)
							throws SQLException {
						ps.setInt(1, studentGrpMaster.getDbTs() + 1);
						ps.setString(2, studentGrpMaster.getCourseMasterId().trim());
						ps.setString(3, studentGrpMaster.getStudentGrp().trim());			
						ps.setString(4, studentGrpMaster.getTermId().trim());			
						ps.setString(5, studentGrpMaster.getSecId().trim());						
						ps.setString(6, studentGrpMaster.getTtgId().trim());
						ps.setString(7, studentGrpMaster.getTtgProcess().trim());			
						ps.setInt(8, studentGrpMaster.getTtgAssignmentOrder());			
						ps.setString(9, studentGrpMaster.getMedium().trim());
						ps.setString(10, studentGrpMaster.getrModId().trim());							
						ps.setInt(11, studentGrpMasterKey.getDbTs());
						ps.setString(12, studentGrpMasterKey.getInstId().trim());
						ps.setString(13, studentGrpMasterKey.getBranchId().trim());
						ps.setString(14, studentGrpMasterKey.getStudentGrpId().trim());
						

					}

				});
		if (updateRecs == 0) {
			logger.error("Update Failed exception occured.");
			throw new UpdateFailedException();

		}
		
	}

	@Override
	public void deleteStudentGrpMasterRec(final StudentGroupMaster studentGrpMaster,
			final StudentGroupMasterKey studentGrpMasterKey)
			throws DeleteFailedException {
		logger.debug("Inside delete method");
		logger.debug("StudentGrpMaster object values :"+ studentGrpMaster.toString());
		logger.debug("StudentGrpMasterKey object values :"+ studentGrpMasterKey.toString());
		
		StringBuffer sql = new StringBuffer();
		   sql.append("update stgm set")
		        .append(" DB_TS= ?,")
				.append("DEL_FLG= 'Y',")
				.append("R_MOD_ID= ?,")
				.append(" R_MOD_TIME=now()")
				.append(" where")
				.append(" DB_TS= ?")
				.append(" and  INST_ID= ?")
				.append(" and  BRANCH_ID= ?")
				.append(" and  STUDENTGRP_ID= ?")	
				.append(" and  DEL_FLG='N'");

		int deletedRecs = getJdbcTemplate().update(sql.toString(),
				new PreparedStatementSetter() {

					@Override
					public void setValues(PreparedStatement ps)
							throws SQLException {
						ps.setInt(1, studentGrpMaster.getDbTs() );
						ps.setString(2, studentGrpMaster.getrModId().trim());						
						ps.setInt(3, studentGrpMasterKey.getDbTs());
						ps.setString(4, studentGrpMasterKey.getInstId().trim());
						ps.setString(5, studentGrpMasterKey.getBranchId().trim());
						ps.setString(6, studentGrpMasterKey.getStudentGrpId().trim());
					}

				});
		if (deletedRecs == 0) {
			logger.error("No record deleted.");
			throw new DeleteFailedException();

		}
		
	}

	@Override
	public StudentGroupMaster selectStudentGrpMasterRec(
			final StudentGroupMasterKey studentGrpMasterKey)
			throws NoDataFoundException {
		
		logger.debug("Inside select method");
		logger.debug("StudentGrpMasterKey object values :"+ studentGrpMasterKey.toString());
		
		List<Object> data = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		    sql.append("select ")
		        .append(" DB_TS,")
				.append("INST_ID,")
				.append("BRANCH_ID,")
				.append("COURSEMASTER_ID,")
				.append("STUDENTGRP_ID,")
				.append("STUDENT_GRP,")				
				.append("TERM_ID,")
				.append("SEC_ID,")
				.append("TTG_ID,")
				.append("TTG_PROCESS,")
				.append("TTG_ASSIGNMENT_ORDER,")
				.append("MEDIUM,")
				.append("DEL_FLG,")
				.append("R_MOD_ID,")
				.append("R_MOD_TIME,")
				.append("R_CRE_ID,")
				.append("R_CRE_TIME")
				.append(" from stgm ")
				.append(" where")
				.append("  INST_ID= ?")
				.append(" and  BRANCH_ID= ?")
				.append(" and  STUDENTGRP_ID= ?")	
				.append(" and  DEL_FLG=?");
		    
		    data.add(studentGrpMasterKey.getInstId());
			data.add(studentGrpMasterKey.getBranchId());
			data.add(studentGrpMasterKey.getStudentGrpId());
			data.add("N");
			if ((studentGrpMasterKey.getDbTs() !=null)&&(studentGrpMasterKey.getDbTs() !=0)) {
				sql.append(" and DB_TS=?  ");
				logger.debug("Db Ts Value :" + studentGrpMasterKey.getDbTs());
				data.add(studentGrpMasterKey.getDbTs());
			}
			
			  Object[] array = data.toArray(new Object[data.size()]);
			  
		    StudentGroupMaster selectedStudentGrpMaster = null;
		    
		    selectedStudentGrpMaster = (StudentGroupMaster) getJdbcTemplate()
				.query(sql.toString(), array, new ResultSetExtractor<StudentGroupMaster>() {

					@Override
					public StudentGroupMaster extractData(ResultSet rs)
							throws SQLException, DataAccessException {
						StudentGroupMaster studentGrpMaster = null;
						if (rs.next()) {
							studentGrpMaster = new StudentGroupMaster();
							studentGrpMaster.setDbTs(rs.getInt("DB_TS"));
							studentGrpMaster.setInstId(rs.getString("INST_ID"));
							studentGrpMaster.setBranchId(rs.getString("BRANCH_ID"));
							studentGrpMaster.setCourseMasterId(rs.getString("COURSEMASTER_ID"));
							studentGrpMaster.setStudentGrpId(rs.getString("STUDENTGRP_ID"));
							studentGrpMaster.setStudentGrp(rs.getString("STUDENT_GRP"));							
							studentGrpMaster.setTermId(rs.getString("TERM_ID"));
							studentGrpMaster.setSecId(rs.getString("SEC_ID"));
							studentGrpMaster.setTtgId(rs.getString("TTG_ID"));
							studentGrpMaster.setTtgProcess(rs.getString("TTG_PROCESS"));	
							studentGrpMaster.setTtgAssignmentOrder(rs.getInt("TTG_ASSIGNMENT_ORDER"));
							studentGrpMaster.setMedium(rs.getString("MEDIUM"));								
							studentGrpMaster.setDelFlag(rs.getString("DEL_FLG"));
							studentGrpMaster.setrModId(rs.getString("R_MOD_ID"));
							studentGrpMaster.setrModTime(rs.getString("R_MOD_TIME"));
							studentGrpMaster.setrCreId(rs.getString("R_CRE_ID"));
							studentGrpMaster.setrCreTime(rs.getString("R_CRE_TIME"));
						}
						return studentGrpMaster;
					}

				});

		if (selectedStudentGrpMaster == null) {
			logger.error("No records found.");
			throw new NoDataFoundException();
		}

		return selectedStudentGrpMaster;
	}

	@Override
	public int validateStudentGrpMasterRec(
			final StudentGroupMaster studentGrpMaster)  {

		logger.debug("Inside Validate Student Group Master method");
		logger.debug("Inside Validate Student Group Master method Values  : "+studentGrpMaster);
		
			
			StringBuffer sql = new StringBuffer();
			sql.append("select exists(")
			    .append("select ")		   
					.append("COURSEMASTER_ID, ")
					.append("TERM_ID, ")
					.append("SEC_ID ")
					.append(" from stgm ")
					.append(" where")
					.append("   INST_ID= '"+studentGrpMaster.getInstId().trim()+"'")
					.append(" and  BRANCH_ID= '"+studentGrpMaster.getBranchId().trim()+"'")
					.append(" and  COURSEMASTER_ID='"+studentGrpMaster.getCourseMasterId().trim()+"'")	;
					if((studentGrpMaster.getTermId()!=null)&&(!studentGrpMaster.getTermId().equals(""))){
					sql.append(" and  TERM_ID= '"+studentGrpMaster.getTermId().trim()+"'");
					}
					sql.append(" and  SEC_ID= '"+studentGrpMaster.getSecId().trim()+"'")
					.append(" and  DEL_FLG='N')" );	
			    
			int rows = getJdbcTemplate().queryForInt(
					sql.toString());

			return rows; 
	}

	@Override
	public String getStuGrpIdForSTUM(final String instId,final String branchid,
			final String courseId,final String termId,final String secId) {		
		logger.debug("Inside select method");
		logger.debug("Key object values :"+"Inst Id:"+instId+",Branch Id:"+branchid+",CourseId:"+courseId+",TermId:"+termId+",SecId:"+secId);
				
		StringBuffer sql = new StringBuffer();
		    sql.append("select ")
		        .append(" STUDENTGRP_ID")				
				.append(" from stgm ")
				.append(" where")
				.append("  INST_ID= ?")
				.append(" and  BRANCH_ID= ?")
				.append(" and  COURSEMASTER_ID= ?")
				.append(" and  SEC_ID= ?")				
				.append(" and  DEL_FLG=?");
		    if((termId!=null)&&(!termId.equals(""))){
		    	sql.append(" and  TERM_ID= ?");		    		   		
		    }
		    String studentGrpMaster = null;
		    
		    studentGrpMaster = getJdbcTemplate().query(
					sql.toString(), new PreparedStatementSetter() {

						@Override
						public void setValues(PreparedStatement pss)
								throws SQLException {
							pss.setString(1, instId);
							pss.setString(2, branchid);
							pss.setString(3, courseId);
							pss.setString(4, secId);
							pss.setString(5, "N");
							if((termId!=null)&&(!termId.equals(""))){
							pss.setString(6, termId);
							}
						}
					},new ResultSetExtractor<String>() {

			
						@Override
						public String extractData(ResultSet rs)
								throws SQLException, DataAccessException {	
							String stuGrpId = null;
							if(rs.next()){
								stuGrpId = rs.getString("STUDENTGRP_ID");
							}
							return stuGrpId;
						}

					});

		if (studentGrpMaster == null) {
			logger.error("There is no Student Grp.");			
		}

		return studentGrpMaster;
	}

}
