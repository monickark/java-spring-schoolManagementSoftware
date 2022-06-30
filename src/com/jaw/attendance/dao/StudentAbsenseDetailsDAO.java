package com.jaw.attendance.dao;

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

@Repository
public class StudentAbsenseDetailsDAO extends BaseDao implements
		IStudentAbsenseDetailsDAO {
	// Logging
	Logger logger = Logger.getLogger(StudentAbsenseDetailsDAO.class);
	
	@Override
	public void insertStudentAbsenseDetailsRec(
			final StudentAbsenseDetails studentAbsenseDetails)
			throws DuplicateEntryException {
		logger.debug("Inside insert method");
		logger.debug("StudentAbsenseDetails object values :"
				+ studentAbsenseDetails.toString());
		StringBuffer query = new StringBuffer();
		query = query.append("insert into stad ( ")
				.append("DB_TS,")
				.append("INST_ID,")
				.append("BRANCH_ID,")
				.append("STAM_ID,")
				.append("STUDENT_ADMIS_NO,")
				.append("IS_PRESENT,")
				.append("ABSENTEE_RMKS,")
				.append("DEL_FLG,")
				.append("R_MOD_ID,")
				.append("R_MOD_TIME,")
				.append("R_CRE_ID,")
				.append("R_CRE_TIME)")
				.append(" values (?,?,?,?,?,?,?,?,?,now(),?,now())");
		
		try {
			getJdbcTemplate().update(query.toString(),
					new PreparedStatementSetter() {
						
						@Override
						public void setValues(java.sql.PreparedStatement pss)
								throws SQLException {
							pss.setInt(1, 1);
							pss.setString(2, studentAbsenseDetails.getInstId());
							pss.setString(3,
									studentAbsenseDetails.getBranchId());
							pss.setString(4, studentAbsenseDetails.getStamId());
							pss.setString(5, studentAbsenseDetails
									.getStudentAdmisNo().trim());
							pss.setString(6, studentAbsenseDetails
									.getIsPresent().trim());
							pss.setString(7, studentAbsenseDetails
									.getAbsenteeRmks().trim());
							pss.setString(8, "N");
							pss.setString(9, studentAbsenseDetails.getrModId()
									.trim());
							pss.setString(10, studentAbsenseDetails.getrCreId()
									.trim());
							
						}
						
					});
		}
		catch (org.springframework.dao.DuplicateKeyException e) {
			throw new DuplicateEntryException();
		}
		
	}
	
	@Override
	public void updateStudentAbsenseDetailsRec(final StudentAbsenseDetails studentAbsenseDetails,
			final StudentAbsenseDetailsKey studentAbsenseDetailsKey)
			throws UpdateFailedException {
		logger.debug("Inside update method");
		logger.debug("StudentAbsenseDetails object values :" + studentAbsenseDetails.toString());
		logger.debug("StudentAbsenseDetailsKey Details object values :"
				+ studentAbsenseDetailsKey.toString());
		StringBuffer sql = new StringBuffer();
		sql.append("update stad set")
				.append("DB_TS=?,")
				.append("INST_ID=?,")
				.append("BRANCH_ID=?,")
				.append("STAM_ID=?,")
				.append("STUDENT_ADMIS_NO=?,")
				.append("IS_PRESENT=?,")
				.append("ABSENTEE_RMKS=?,")
				.append("DEL_FLG=?,")
				.append("R_MOD_ID=?,")
				.append("R_MOD_TIME=now(),")
				.append(" where")
				.append(" DB_TS= ?")
				.append(" and  INST_ID= ?")
				.append(" and  BRANCH_ID= ?")
				.append(" and  STAM_ID= ?")
				.append(" and  DEL_FLG='N'");
		
		int updateRecs = getJdbcTemplate().update(sql.toString(),
				new PreparedStatementSetter() {
					
					@Override
					public void setValues(java.sql.PreparedStatement pss)
							throws SQLException {
						pss.setInt(1, studentAbsenseDetails.getDbTs() + 1);
						pss.setString(2, studentAbsenseDetails.getInstId());
						pss.setString(3,
								studentAbsenseDetails.getBranchId());
						pss.setString(4, studentAbsenseDetails.getStamId());
						pss.setString(5, studentAbsenseDetails
								.getStudentAdmisNo().trim());
						pss.setString(6, studentAbsenseDetails
								.getIsPresent().trim());
						pss.setString(7, studentAbsenseDetails
								.getAbsenteeRmks().trim());
						pss.setString(8, "N");
						pss.setString(9, studentAbsenseDetails.getrModId()
								.trim());
						pss.setInt(10, studentAbsenseDetails.getDbTs());
						pss.setString(11, studentAbsenseDetails.getInstId());
						pss.setString(12,
								studentAbsenseDetails.getBranchId());
						pss.setString(13, studentAbsenseDetails.getStamId());
						
					}
					
				});
		if (updateRecs == 0) {
			throw new UpdateFailedException();
			
		}
		
	}
	
	@Override
	public void deleteStudentAbsenseDetailsRec(final StudentAbsenseDetails StudentAbsenseDetails,
			final StudentAbsenseDetailsKey StudentAbsenseDetailsKey)
			throws DeleteFailedException {
		logger.debug("Inside delete method");
		logger.debug("StudentAbsenseDetails object values :" + StudentAbsenseDetails.toString());
		logger.debug("StudentAbsenseDetailsKey object values :"
				+ StudentAbsenseDetailsKey.toString());
		
		StringBuffer sql = new StringBuffer();
		sql.append("update stad set")
				.append(" DB_TS= ?,")
				.append("DEL_FLG= 'Y',")
				.append("R_MOD_ID= ?,")
				.append(" R_MOD_TIME=now()")
				.append(" where")
				.append(" DB_TS= ?")
				.append(" and  INST_ID= ?")
				.append(" and  BRANCH_ID= ?")
				.append(" and  STAM_ID= ?")
				.append(" and  DEL_FLG='N'");
		
		int deletedRecs = getJdbcTemplate().update(sql.toString(),
				new PreparedStatementSetter() {
					
					@Override
					public void setValues(java.sql.PreparedStatement ps)
							throws SQLException {
						ps.setInt(1, StudentAbsenseDetails.getDbTs() + 1);
						ps.setString(2, StudentAbsenseDetails.getrModId().trim());
						
						ps.setInt(3, StudentAbsenseDetails.getDbTs());
						ps.setString(4, StudentAbsenseDetailsKey.getInstId().trim());
						ps.setString(5, StudentAbsenseDetailsKey.getBranchId().trim());
						ps.setString(6, StudentAbsenseDetailsKey.getStamId().trim());
					}
					
				});
		if (deletedRecs == 0) {
			throw new DeleteFailedException();
			
		}
		
	}
	
	@Override
	public void manualDeleteStudentAbsenseDetailsRec(
			final StudentAbsenseDetailsKey StudentAbsenseDetailsKey)
			throws DeleteFailedException {
		logger.debug("Inside delete method");
		logger.debug("StudentAbsenseDetailsKey object values :"
				+ StudentAbsenseDetailsKey.toString());
		
		StringBuffer sql = new StringBuffer();
		sql.append("delete from stad")
				.append(" where")
				.append("   INST_ID= ?")
				.append(" and  BRANCH_ID= ?")
				.append(" and  STAM_ID= ?")
				.append(" and  STUDENT_ADMIS_NO= ?")
				.append(" and  DEL_FLG='N'");
		
		int deletedRecs = getJdbcTemplate().update(sql.toString(),
				new PreparedStatementSetter() {
					
					@Override
					public void setValues(java.sql.PreparedStatement ps)
							throws SQLException {
						ps.setString(1, StudentAbsenseDetailsKey.getInstId().trim());
						ps.setString(2, StudentAbsenseDetailsKey.getBranchId().trim());
						ps.setString(3, StudentAbsenseDetailsKey.getStamId().trim());
						ps.setString(4, StudentAbsenseDetailsKey.getStudentAdmisNo().trim());
					}
					
				});
		if (deletedRecs == 0) {
			throw new DeleteFailedException();
			
		}
		
	}
	
	@Override
	public StudentAbsenseDetails selectStudentAbsenseDetailsRec(
			final StudentAbsenseDetailsKey StudentAbsenseDetailsKey)
			throws NoDataFoundException {
		logger.debug("Inside select method");
		logger.debug("StudentAbsenseDetailsKey object values :"
				+ StudentAbsenseDetailsKey.toString());
		List<Object> data = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		sql.append("select ")
				.append("DB_TS,")
				.append("INST_ID,")
				.append("BRANCH_ID,")
				.append("STAM_ID,")
				.append("STUDENT_ADMIS_NO,")
				.append("IS_PRESENT,")
				.append("ABSENTEE_RMKS,")
				.append("DEL_FLG,")
				.append("R_MOD_ID,")
				.append("R_MOD_TIME,")
				.append("R_CRE_ID,")
				.append("R_CRE_TIME")
				.append(" from stad ")
				.append(" where")
				.append("   INST_ID= ?")
				.append(" and  BRANCH_ID= ?")
				.append(" and  STAM_ID= ?")
				.append(" and  STUDENT_ADMIS_NO= ?")
				.append(" and  DEL_FLG=?");
		data.add(StudentAbsenseDetailsKey.getInstId());
		data.add(StudentAbsenseDetailsKey.getBranchId());
		data.add(StudentAbsenseDetailsKey.getStamId());
		data.add(StudentAbsenseDetailsKey.getStudentAdmisNo());
		data.add("N");
		if ((StudentAbsenseDetailsKey.getDbTs() != null)) {
			sql.append(" and DB_TS=?  ");
			logger.debug("Db Ts Value :" + StudentAbsenseDetailsKey.getDbTs());
			data.add(StudentAbsenseDetailsKey.getDbTs());
		}		
		Object[] array = data.toArray(new Object[data.size()]);
		logger.debug("Where clause conditions size :" + array.length);
		StudentAbsenseDetails selectedStudentAbsenseDetails = null;
		selectedStudentAbsenseDetails = (StudentAbsenseDetails) getJdbcTemplate()
				.query(sql.toString(), array, new ResultSetExtractor<StudentAbsenseDetails>() {
					
					@Override
					public StudentAbsenseDetails extractData(ResultSet rs)
							throws SQLException, DataAccessException {
						StudentAbsenseDetails StudentAbsenseDetails = null;
						if (rs.next()) {
							
							StudentAbsenseDetails = new StudentAbsenseDetails();
							StudentAbsenseDetails.setDbTs(rs.getInt("DB_TS"));
							StudentAbsenseDetails.setInstId(rs.getString("INST_ID"));
							StudentAbsenseDetails.setBranchId(rs.getString("BRANCH_ID"));
							StudentAbsenseDetails.setStamId(rs.getString("STAM_ID"));
							StudentAbsenseDetails.setStudentAdmisNo(rs
									.getString("STUDENT_ADMIS_NO"));
							StudentAbsenseDetails.setIsPresent(rs.getString("IS_PRESENT"));
							StudentAbsenseDetails.setAbsenteeRmks(rs.getString("ABSENTEE_RMKS"));
							StudentAbsenseDetails.setDelFlag(rs.getString("DEL_FLG"));
							StudentAbsenseDetails.setrModId(rs.getString("R_MOD_ID"));
							StudentAbsenseDetails.setrModTime(rs.getString("R_MOD_TIME"));
							StudentAbsenseDetails.setrCreId(rs.getString("R_CRE_ID"));
							StudentAbsenseDetails.setrCreTime(rs.getString("R_CRE_TIME"));
						}
						return StudentAbsenseDetails;
					}
					
				});
		
		if (selectedStudentAbsenseDetails == null) {
			throw new NoDataFoundException();
		}
		
		return selectedStudentAbsenseDetails;
	}
	
}
