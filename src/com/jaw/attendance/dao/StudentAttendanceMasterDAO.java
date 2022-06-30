package com.jaw.attendance.dao;

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

import com.jaw.common.constants.ApplicationConstant;
import com.jaw.common.constants.CommonCodeConstant;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.UpdateFailedException;
import com.jaw.core.dao.AcademicCalendar;
import com.jaw.framework.dao.BaseDao;
import com.jaw.framework.sessCache.UserSessionDetails;
@Repository
public class StudentAttendanceMasterDAO extends BaseDao implements IStudentAttendanceMasterDAO{
	// Logging
	Logger logger = Logger.getLogger(StudentAttendanceMasterDAO.class);

	@Override
	public void insertStudentAttendanceMasterRec(
			final StudentAttendanceMaster studentAttendanceMaster)
			throws DuplicateEntryException {
		logger.debug("Inside insert method");
		logger.debug("StudentAttendanceMaster object values :"
				+ studentAttendanceMaster.toString());
		StringBuffer query = new StringBuffer();
		query = query.append("insert into stam ( ")
				.append("DB_TS,")
				.append("INST_ID,")
				.append("BRANCH_ID,")
				.append("STAM_ID,")
				.append("AC_TERM,")
				.append("STUDENTGRP_ID,")
				.append("CRSL_ID,")				
				.append("OCCUR_NO,")
				.append("ATT_DATE,")
				.append("SHIFT_ID,")
				.append("NO_OF_SESSIONS,")
				.append("CLS_TYPE,")
				.append("LAB_BATCH,")
				.append("STATUS,")
				.append("DEL_FLG,")
				.append("R_MOD_ID,")
				.append("R_MOD_TIME,")
				.append("R_CRE_ID,")
				.append("R_CRE_TIME)")
				.append(" values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,now(),?,now())");

		try {
			getJdbcTemplate().update(query.toString(),
					new PreparedStatementSetter() {

						@Override
						public void setValues(PreparedStatement pss)
								throws SQLException {
							pss.setInt(1, 1);
							pss.setString(2, studentAttendanceMaster.getInstId());
							pss.setString(3, studentAttendanceMaster.getBranchId());
							pss.setString(4, studentAttendanceMaster.getStamId());
							pss.setString(5, studentAttendanceMaster.getAcTerm().trim());
							pss.setString(6, studentAttendanceMaster.getStudentGroupId());
							pss.setString(7, studentAttendanceMaster.getCrslId());							
							pss.setString(8, studentAttendanceMaster.getOccurNo());
							pss.setString(9, studentAttendanceMaster.getAttDate());
							pss.setString(10, studentAttendanceMaster.getShiftId());
							pss.setInt(11, studentAttendanceMaster.getNoOfSessions());
							pss.setString(12, studentAttendanceMaster.getClassType());
							pss.setString(13, studentAttendanceMaster.getLabBatch());
							pss.setString(14, ApplicationConstant.ATTENDANCE_STATUS_ENTERED);
							pss.setString(15, "N");
							pss.setString(16, studentAttendanceMaster.getrModId().trim());
							pss.setString(17, studentAttendanceMaster.getrCreId().trim());

						}

					});
		} catch (org.springframework.dao.DuplicateKeyException e) {
			throw new DuplicateEntryException();
		}
		
		
	}
	 public int checkCollegeAttendanceExist(StudentAttendanceMaster studentAttendance,UserSessionDetails userSessionDetails){
		 logger.debug("Inside Check Attendance  method");

			logger.debug("studentAttendance master object values :"
					+ studentAttendance.toString());
			StringBuffer sql = new StringBuffer();
			
			
			sql.append("select exists(")
			.append("select stam_id ")
			.append("from stam")				
					.append(" where ")			
					.append(" INST_ID='" + studentAttendance.getInstId()+ "'")
					.append(" and BRANCH_ID='" + studentAttendance.getBranchId()+ "'")
					.append(" and  AC_TERM='" + studentAttendance.getAcTerm()+ "'")				
					.append(" and  OCCUR_NO='" + studentAttendance.getOccurNo()+ "'")
					.append(" and  ATT_DATE='" +studentAttendance.getAttDate()+ "'");
					if((!userSessionDetails.getInbrCategory().equals(CommonCodeConstant.IBCAT_SSLC))&&(!userSessionDetails.getInbrCategory().equals(CommonCodeConstant.IBCAT_ICSE))){
					
					sql.append(" and  CRSL_ID='" + studentAttendance.getCrslId()+ "'")
					.append(" and  CLS_TYPE='" + studentAttendance.getClassType()+ "'");
					if(studentAttendance.getClassType()=="CLSP"){
						sql.append(" and  LAB_BATCH='" + studentAttendance.getLabBatch()+ "'");
					}	
					}
					sql.append(" and  STUDENTGRP_ID='" + studentAttendance.getStudentGroupId()+ "'")				
					.append(" and DEL_FLG='N')");
					
					
							
			int rows = getJdbcTemplate().queryForInt(
					sql.toString());

			System.out.println("rows valueeeeeeeeeeeee"+rows);

			
			return rows; 
	 }
	@Override
	public int checkAttendanceMarkedForSplCls(
			StudentAttendanceMaster studentAttendance,
			UserSessionDetails userSessionDetails) {
		 logger.debug("Inside Check Attendance marked for Spl Cls  method");

			logger.debug("studentAttendance master object values :"
					+ studentAttendance.toString());
			StringBuffer sql = new StringBuffer();
			
			
			sql.append("select exists(")
			.append("select stam_id ")
			.append("from stam")				
					.append(" where ")			
					.append(" INST_ID='" + studentAttendance.getInstId()+ "'")
					.append(" and BRANCH_ID='" + studentAttendance.getBranchId()+ "'")
					.append(" and  AC_TERM='" + studentAttendance.getAcTerm()+ "'")	
					.append(" and  ATT_DATE='" +studentAttendance.getAttDate()+ "'");
					if((!userSessionDetails.getInbrCategory().equals(CommonCodeConstant.IBCAT_SSLC))&&(!userSessionDetails.getInbrCategory().equals(CommonCodeConstant.IBCAT_ICSE))){
					
					sql.append(" and  CRSL_ID='" + studentAttendance.getCrslId()+ "'");
					}
					sql.append(" and  STUDENTGRP_ID='" + studentAttendance.getStudentGroupId()+ "'")				
					.append(" and DEL_FLG='N')");
					
					
							
			int rows = getJdbcTemplate().queryForInt(
					sql.toString());

			System.out.println("rows valueeeeeeeeeeeee"+rows);

			
			return rows; 
	 }
	@Override
	public void updateAttendanceStatusLock(
			final StudentAttendanceMaster studentAttendanceMaster,
			final StudentAttendanceMasterKey studentAttendanceMasterKey)
			throws UpdateFailedException {
		logger.debug("Inside update method");
		logger.debug("StudentAttendanceMaster object values :"+ studentAttendanceMaster.toString());
		logger.debug("StudentAttendanceMasterKey object values :"+ studentAttendanceMasterKey.toString());
		
		StringBuffer sql = new StringBuffer();
		sql.append("update stam set ")
		        .append("STATUS= ?,")
		        .append("DB_TS= DB_TS+1,")
				.append("DEL_FLG= 'N',")
				.append("R_MOD_ID= ?,")
				.append(" R_MOD_TIME=now()")
				.append(" where")				
				.append("   INST_ID= ?")
				.append(" and  BRANCH_ID= ?")
				.append(" and  AC_TERM= ?")
				.append(" and  STATUS<> '"+studentAttendanceMasterKey.getStatus()+"'")	
				.append(" and  DEL_FLG='N'");

		

		int updateRecs = getJdbcTemplate().update(sql.toString(),
				new PreparedStatementSetter() {

					@Override
					public void setValues(PreparedStatement ps)
							throws SQLException {
						ps.setString(1, studentAttendanceMaster.getStatus().trim());
						ps.setString(2, studentAttendanceMaster.getrModId().trim());
						ps.setString(3, studentAttendanceMasterKey.getInstId().trim());
						ps.setString(4, studentAttendanceMasterKey.getBranchId().trim());
						ps.setString(5, studentAttendanceMasterKey.getAcTerm().trim());
						

					}

				});
		if (updateRecs == 0) {
			logger.error("Update Failed Exception Occured");
			throw new UpdateFailedException();

		}

	}

}
