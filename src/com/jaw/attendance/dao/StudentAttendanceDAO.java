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

import com.jaw.common.exceptions.DeleteFailedException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.UpdateFailedException;
import com.jaw.framework.dao.BaseDao;
import com.jaw.framework.sessCache.UserSessionDetails;
import com.jaw.common.constants.CommonCodeConstant;
//StudentAttendance DAO class
@Repository
public class StudentAttendanceDAO extends BaseDao implements IStudentAttendanceDAO {
	// Logging
	Logger logger = Logger.getLogger(StudentAttendanceDAO.class);
	
	@Override
	public void insertStudentAttendanceRec(final StudentAttendance studentAttendance)
			throws DuplicateEntryException {
		logger.debug("Inside insert method");
		logger.debug("StudentAttendance object values :"
				+ studentAttendance.toString());
		StringBuffer query = new StringBuffer();
		query = query.append("insert into stat ( ")
				.append("DB_TS,")
				.append("INST_ID,")
				.append("BRANCH_ID,")
				.append("STAT_SEQ_NO,")
				.append("AC_TERM,")
				.append("ATT_DATE,")
				.append("STUDENT_ADMIS_NO,")
				.append("SHIFT_ID,")
				.append("SUB_ID,")
				.append("OPT_SUB_ID,")
				.append("CRSL_ID,")
				.append("NO_OF_SESSIONS,")
				.append("IS_PRESENT,")
				.append("ABSENTEE_RMKS,")
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
							pss.setInt(1, studentAttendance.getDbTs());
							pss.setString(2, studentAttendance.getInstId());
							pss.setString(3, studentAttendance.getBranchId());
							pss.setString(4, studentAttendance.getSattSeqNo());
							pss.setString(5, studentAttendance.getAcTerm().trim());
							pss.setString(6, studentAttendance.getAttDate().trim());
							pss.setString(7, studentAttendance.getStudentAdmisNo().trim());
							pss.setString(8, studentAttendance.getShiftId());
							pss.setString(9, studentAttendance.getSubId());
							pss.setString(10, studentAttendance.getOptSubId());
							pss.setString(11, studentAttendance.getCrslId().trim());
							pss.setInt(12, studentAttendance.getNoOfSessions());
							pss.setString(13, studentAttendance.getIsPresent().trim());
							pss.setString(14, studentAttendance.getAbsenteeRmks().trim());
							pss.setString(15, studentAttendance.getDelFlag().trim());
							pss.setString(16, studentAttendance.getrModId().trim());
							pss.setString(17, studentAttendance.getrCreId().trim());
							
						}
						
					});
		}
		catch (org.springframework.dao.DuplicateKeyException e) {
			throw new DuplicateEntryException();
		}
		
	}
	
	@Override
	public void updateStudentAttendanceRec(final StudentAttendance studentAttendance,
			final StudentAttendanceKey studentAttendanceKey)
			throws UpdateFailedException {
		logger.debug("Inside update method");
		logger.debug("StudentAttendance object values :" + studentAttendance.toString());
		logger.debug("StudentAttendanceKey Details object values :"
				+ studentAttendanceKey.toString());
		StringBuffer sql = new StringBuffer();
		sql.append("update stat set")
				.append(" DB_TS= ?,")
				.append("AC_TERM= ?,")
				.append("ATT_DATE= ?,")
				.append("STUDENT_ADMIS_NO= ?,")
				.append("SHIFT_ID= ?,")
				.append("SUB_ID= ?,")
				.append("OPT_SUB_ID= ?,")
				.append("CRSL_ID= ?,")
				.append("NO_OF_SESSIONS= ?,")
				.append("IS_PRESENT= ?,")
				.append("ABSENTEE_RMKS= ?,")
				.append("DEL_FLG= 'N',")
				.append("R_MOD_ID= ?,")
				.append(" R_MOD_TIME=now()")
				.append(" where")
				.append(" DB_TS= ?")
				.append(" and  INST_ID= ?")
				.append(" and  BRANCH_ID= ?")
				.append(" and  STAT_SEQ_NO= ?")
				.append(" and  DEL_FLG='N'");
		
		int updateRecs = getJdbcTemplate().update(sql.toString(),
				new PreparedStatementSetter() {
					
					@Override
					public void setValues(PreparedStatement ps)
							throws SQLException {
						ps.setInt(1, studentAttendance.getDbTs() + 1);
						ps.setString(2, studentAttendance.getAcTerm().trim());
						ps.setString(3, studentAttendance.getAttDate().trim());
						ps.setString(4, studentAttendance.getStudentAdmisNo().trim());
						ps.setString(5, studentAttendance.getShiftId());
						ps.setString(6, studentAttendance.getSubId());
						ps.setString(7, studentAttendance.getOptSubId());
						ps.setString(8, studentAttendance.getCrslId().trim());
						ps.setInt(9, studentAttendance.getNoOfSessions());
						ps.setString(10, studentAttendance.getIsPresent().trim());
						ps.setString(11, studentAttendance.getAbsenteeRmks().trim());
						ps.setString(12, studentAttendance.getrModId().trim());
						ps.setInt(13, studentAttendance.getDbTs());
						ps.setString(14, studentAttendanceKey.getInstId().trim());
						ps.setString(15, studentAttendanceKey.getBranchId().trim());
						ps.setString(16, studentAttendanceKey.getSattSeqNo().trim());
						
					}
					
				});
		if (updateRecs == 0) {
			throw new UpdateFailedException();
			
		}
		
	}
	
	@Override
	public void deleteStudentAttendanceRec(final StudentAttendance studentAttendance,
			final StudentAttendanceKey studentAttendanceKey)
			throws DeleteFailedException {
		logger.debug("Inside delete method");
		logger.debug("StudentAttendance object values :" + studentAttendance.toString());
		logger.debug("StudentAttendanceKey object values :" + studentAttendanceKey.toString());
		
		StringBuffer sql = new StringBuffer();
		sql.append("update stat set")
				.append(" DB_TS= ?,")
				.append("DEL_FLG= 'Y',")
				.append("R_MOD_ID= ?,")
				.append(" R_MOD_TIME=now()")
				.append(" where")
				.append(" DB_TS= ?")
				.append(" and  INST_ID= ?")
				.append(" and  BRANCH_ID= ?")
				.append(" and  STAT_SEQ_NO= ?")
				.append(" and  DEL_FLG='N'");
		
		int deletedRecs = getJdbcTemplate().update(sql.toString(),
				new PreparedStatementSetter() {
					
					@Override
					public void setValues(PreparedStatement ps)
							throws SQLException {
						ps.setInt(1, studentAttendance.getDbTs() + 1);
						ps.setString(2, studentAttendance.getrModId().trim());
						
						ps.setInt(3, studentAttendanceKey.getDbTs());
						ps.setString(4, studentAttendanceKey.getInstId().trim());
						ps.setString(5, studentAttendanceKey.getBranchId().trim());
						ps.setString(6, studentAttendanceKey.getSattSeqNo().trim());
					}
					
				});
		if (deletedRecs == 0) {
			throw new DeleteFailedException();
			
		}
		
	}
	
	@Override
	public StudentAttendance selectSpecialClassRec(
			final StudentAttendanceKey studentAttendanceKey)
			throws NoDataFoundException {
		logger.debug("Inside select method");
		logger.debug("StudentAttendanceKey object values :" + studentAttendanceKey.toString());
		List<Object> data = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		sql.append("select ")
				.append(" DB_TS,")
				.append("INST_ID,")
				.append("BRANCH_ID,")
				.append("STAT_SEQ_NO,")
				.append("AC_TERM,")
				.append("ATT_DATE,")
				.append("STUDENT_ADMIS_NO,")
				.append("SHIFT_ID,")
				.append("SUB_ID,")
				.append("OPT_SUB_ID,")
				.append("CRSL_ID,")
				.append("NO_OF_SESSIONS,")
				.append("IS_PRESENT,")
				.append("ABSENTEE_RMKS,")
				.append("DEL_FLG,")
				.append("R_MOD_ID,")
				.append("R_MOD_TIME,")
				.append("R_CRE_ID,")
				.append("R_CRE_TIME")
				.append(" from stat ")
				.append(" where")
				/* .append(" DB_TS= ?") */
				.append("   INST_ID= ?")
				.append(" and  BRANCH_ID= ?")
				.append(" and  STAT_SEQ_NO= ?")
				.append(" and  DEL_FLG=?");
		data.add(studentAttendanceKey.getInstId());
		data.add(studentAttendanceKey.getBranchId());
		data.add(studentAttendanceKey.getSattSeqNo());
		data.add("N");
		if ((studentAttendanceKey.getDbTs() != null)) {
			sql.append(" and DB_TS=?  ");
			logger.debug("Db Ts Value :" + studentAttendanceKey.getDbTs());
			data.add(studentAttendanceKey.getDbTs());
		}
		
		Object[] array = data.toArray(new Object[data.size()]);
		logger.debug("Where clause conditions size :" + array.length);
		StudentAttendance selectedStudentAttendance = null;
		selectedStudentAttendance = (StudentAttendance) getJdbcTemplate()
				.query(sql.toString(), array, new ResultSetExtractor<StudentAttendance>() {
					
					@Override
					public StudentAttendance extractData(ResultSet rs)
							throws SQLException, DataAccessException {
						StudentAttendance studentAttendance = null;
						if (rs.next()) {
							studentAttendance = new StudentAttendance();
							studentAttendance.setDbTs(rs.getInt("DB_TS"));
							studentAttendance.setInstId(rs.getString("INST_ID"));
							studentAttendance.setBranchId(rs.getString("BRANCH_ID"));
							studentAttendance.setAcTerm(rs.getString("AC_TERM"));
							studentAttendance.setAttDate(rs.getString("ATT_DATE"));
							studentAttendance.setStudentAdmisNo(rs.getString("STUDENT_ADMIS_NO"));
							studentAttendance.setShiftId(rs.getString("SHIFT_ID"));
							studentAttendance.setCrslId(rs.getString("CRSL_ID"));
							studentAttendance.setIsPresent(rs.getString("IS_PRESENT"));
							studentAttendance.setAbsenteeRmks(rs.getString("ABSENTEE_RMKS"));
							studentAttendance.setSattSeqNo(rs.getString("STAT_SEQ_NO"));
							studentAttendance.setSubId(rs.getString("SUB_ID"));
							studentAttendance.setOptSubId(rs.getString("OPT_SUB_ID"));
							studentAttendance.setNoOfSessions(rs.getInt("NO_OF_SESSIONS"));
							studentAttendance.setDelFlag(rs.getString("DEL_FLG"));
							studentAttendance.setrModId(rs.getString("R_MOD_ID"));
							studentAttendance.setrModTime(rs.getString("R_MOD_TIME"));
							studentAttendance.setrCreId(rs.getString("R_CRE_ID"));
							studentAttendance.setrCreTime(rs.getString("R_CRE_TIME"));
						}
						return studentAttendance;
					}
					
				});
		
		if (selectedStudentAttendance == null) {
			throw new NoDataFoundException();
		}
		
		return selectedStudentAttendance;
	}
	/* public int checkAttendanceExist(StudentAttendance studentAttendance,String studentGrpId){
	 logger.debug("Inside Check Attendance  method");

		logger.debug("studentAttendance object values :"
				+ studentAttendance.toString());
		StringBuffer sql = new StringBuffer();
		
		
		
		
		sql.append("select exists(")
		.append("select stat.student_admis_no ")
		.append("from stat,stum, stgm")				
				.append(" where ")
				.append("   stat.INST_ID = stum.INST_ID")
				.append(" and  stat.INST_ID = stgm.INST_ID")
				.append(" and stat.BRANCH_ID = stum.BRANCH_ID")
				.append(" and stat.BRANCH_ID = stgm.BRANCH_ID")
				.append(" and stat.DEL_FLG = stum.DEL_FLG")
				.append(" and stat.DEL_FLG = stgm.DEL_FLG")
				.append(" and stum.COURSE = stgm.COURSEMASTER_ID")				
				.append(" and stum.STANDARD = stgm.TERM_ID")
				.append(" and stum.SEC = stgm.SEC_ID")				
					.append(" and stat.AC_TERM = stum.ACADEMIC_YEAR")
				.append(" and stat.STUDENT_ADMIS_NO = stum.STUDENT_ADMIS_NO")
					.append(" and stat.INST_ID='" + studentAttendance.getInstId()
						+ "'")
				.append(" and stat.BRANCH_ID='" + studentAttendance.getBranchId()
						+ "'")
				.append(" and  stat.AC_TERM='" + studentAttendance.getAcTerm()
						+ "'")
				.append(" and  ATT_DATE='" +studentAttendance.getAttDate()
						+ "'")
						.append(" and stat.DEL_FLG='N'")
						.append(" and  STUDENTGRP_ID='" + studentGrpId
						+ "'")
						.append(" and  CRSL_ID='" + studentAttendance.getCrslId()
						+ "'")
						.append(" and  CLS_TYPE='" + studentAttendance.getClsType()
						+ "')");
						
		int rows = getJdbcTemplate().queryForInt(
				sql.toString());

		System.out.println("rows valueeeeeeeeeeeee"+rows);

		
		return rows; 
 }
 */
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
}
