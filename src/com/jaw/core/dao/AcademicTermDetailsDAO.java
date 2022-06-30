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

import com.jaw.common.constants.ApplicationConstant;
import com.jaw.common.exceptions.DeleteFailedException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.UpdateFailedException;
import com.jaw.fee.dao.FeeMasterStatus;
import com.jaw.framework.dao.BaseDao;

//Institute Master DAO class
@Repository
public class AcademicTermDetailsDAO extends BaseDao implements IAcademicTermDetailsDAO {
	// Logging
	Logger logger = Logger.getLogger(AcademicTermDetailsDAO.class);
	
	// method to insert into AcademicCalendar Table
	@Override
	public void insertAcademicTermDetailsRec(
			final AcademicTermDetails academicTermDetailsRec)
			throws DuplicateEntryException {
		logger.debug("Inside insert method");
		logger.debug("AcademicTermDetails object values :"
				+ academicTermDetailsRec.toString());
		StringBuffer query = new StringBuffer();
		query = query.append("insert into atdt ( ")
				.append("DB_TS,")
				.append("INST_ID,")
				.append("BRANCH_ID,")
				.append("AC_YEAR,")
				.append("AC_TERM,")
				.append("TERM_START_DATE,")
				.append("TERM_END_DATE,")
				.append("WEEKLY_HOLIDAY,")
				.append("PROMO_PROC_STS,")	
				.append("PROMO_PROC_STS_ID,")	
				.append("AC_TERM_STS,")
				.append("DEL_FLG,")
				.append("R_MOD_ID,")
				.append("R_MOD_TIME,")
				.append("R_CRE_ID,")
				.append("R_CRE_TIME)")
				
				.append(" values (?,?,?,?,?,?,?,?,?,?,?,?,?,now(),?,now())");
		
		try {
			getJdbcTemplate().update(query.toString(),
					new PreparedStatementSetter() {
						
						@Override
						public void setValues(PreparedStatement pss)
								throws SQLException {
							pss.setInt(1, academicTermDetailsRec.getDbTs());
							pss.setString(2, academicTermDetailsRec.getInstId());
							pss.setString(3, academicTermDetailsRec.getBranchId());
							pss.setString(4, academicTermDetailsRec.getAcYear());
							pss.setString(5, academicTermDetailsRec.getAcTerm().trim());
							pss.setString(6, academicTermDetailsRec.getTermStartDate().trim());
							pss.setString(7, academicTermDetailsRec.getTermEndDate().trim());
							pss.setString(8, academicTermDetailsRec.getWeeklyHoliday().trim());							
							pss.setString(9, academicTermDetailsRec.getPromotionStatus().trim());								
							pss.setString(10, academicTermDetailsRec.getPromotionId().trim());	
							pss.setString(11, academicTermDetailsRec.getAcTermSts().trim());
							pss.setString(12, academicTermDetailsRec.getDelFlag().trim());
							pss.setString(13, academicTermDetailsRec.getrModId().trim());
							pss.setString(14, academicTermDetailsRec.getrCreId().trim());
							
						}
						
					});
		}
		catch (org.springframework.dao.DuplicateKeyException e) {
			throw new DuplicateEntryException();
		}
		
	}
	
	@Override
	public void updateAcademicTermDetailsRec(
			final AcademicTermDetails academicTermDetailsRec,
			final AcademicTermDetailsKey academicTermDetailsKey)
			throws UpdateFailedException {
		logger.debug("Inside update method");
		logger.debug("Academic Term Details object values :" + academicTermDetailsRec.toString());
		logger.debug("Academic Term Details object values :" + academicTermDetailsKey.toString());
		StringBuffer sql = new StringBuffer();
		sql.append("update atdt set")
				.append(" DB_TS= ?,")
				.append("TERM_START_DATE= ?,")
				.append("TERM_END_DATE= ?,")
				.append("WEEKLY_HOLIDAY= ?,")
				.append("AC_TERM_STS= ?,")
				.append("PROMO_PROC_STS=?,")
				.append("PROMO_PROC_STS_ID=?,")	
				.append("DEL_FLG= 'N',")
				.append("R_MOD_ID= ?,")
				.append(" R_MOD_TIME=now()")
				.append(" where")
				.append(" DB_TS= ?")
				.append(" and  INST_ID= ?")
				.append(" and  BRANCH_ID= ?")
				.append(" and  AC_TERM= ?")
				.append(" and  DEL_FLG='N'");
		
		int updateRecs = getJdbcTemplate().update(sql.toString(),
				new PreparedStatementSetter() {
					
					@Override
					public void setValues(PreparedStatement ps)
							throws SQLException {
						ps.setInt(1, academicTermDetailsRec.getDbTs() + 1);
						ps.setString(2, academicTermDetailsRec.getTermStartDate().trim());
						ps.setString(3, academicTermDetailsRec.getTermEndDate().trim());
						ps.setString(4, academicTermDetailsRec.getWeeklyHoliday().trim());
						ps.setString(5, academicTermDetailsRec.getAcTermSts().trim());						
						ps.setString(6, academicTermDetailsRec.getPromotionStatus().trim());
						ps.setString(7, academicTermDetailsRec.getPromotionId().trim());
						ps.setString(8, academicTermDetailsRec.getrModId().trim());
						ps.setInt(9, academicTermDetailsKey.getDbTs());
						ps.setString(10, academicTermDetailsKey.getInstId().trim());
						ps.setString(11, academicTermDetailsKey.getBranchId().trim());
						ps.setString(12, academicTermDetailsKey.getAcTerm().trim());
						
					}
					
				});
		if (updateRecs == 0) {
			throw new UpdateFailedException();
			
		}
		
	}
	
	@Override
	public void deleteAcademicTermDetailsRec(
			final AcademicTermDetails academicTermDetailsRec,
			final AcademicTermDetailsKey academicTermDetailsKey)
			throws DeleteFailedException {
		logger.debug("Inside delete method");
		logger.debug("AcademicTermDetails object values :" + academicTermDetailsRec.toString());
		logger.debug("AcademicTermDetailsKey object values :" + academicTermDetailsKey.toString());
		
		StringBuffer sql = new StringBuffer();
		sql.append("update atdt set")
				.append(" DB_TS= ?,")
				.append("DEL_FLG= 'Y',")
				.append("R_MOD_ID= ?,")
				.append(" R_MOD_TIME=now()")
				.append(" where")
				.append(" DB_TS= ?")
				.append(" and INST_ID= ?")
				.append(" and  BRANCH_ID= ?")
				.append(" and  AC_TERM= ?")
				.append(" and  DEL_FLG='N'");
		
		int deletedRecs = getJdbcTemplate().update(sql.toString(),
				new PreparedStatementSetter() {
					
					@Override
					public void setValues(PreparedStatement ps)
							throws SQLException {
						ps.setInt(1, academicTermDetailsRec.getDbTs() + 1);
						ps.setString(2, academicTermDetailsRec.getrModId().trim());
						ps.setInt(3, academicTermDetailsRec.getDbTs());
						ps.setString(4, academicTermDetailsKey.getInstId().trim());
						ps.setString(5, academicTermDetailsKey.getBranchId().trim());
						ps.setString(6, academicTermDetailsKey.getAcTerm().trim());
						
					}
					
				});
		if (deletedRecs == 0) {
			throw new DeleteFailedException();
			
		}
		
	}
	
	@Override
	public AcademicTermDetails selectAcademicTermDetailsRec(
			final AcademicTermDetailsKey academicTermDetailsKey)
			throws NoDataFoundException {
		logger.debug("Inside select method");
		logger.debug("Academic Term Details Key object values :"
				+ academicTermDetailsKey.toString());
		List<Object> data = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		sql.append("select ")
				.append(" DB_TS,")
				.append(" INST_ID,")
				.append("BRANCH_ID,")
				.append("AC_YEAR,")
				.append("AC_TERM,")
				.append("TERM_START_DATE,")
				.append("TERM_END_DATE,")
				.append("WEEKLY_HOLIDAY,")
				.append("AC_TERM_STS,")				
				.append("PROMO_PROC_STS,")
				.append("PROMO_PROC_STS_ID,")
				.append("DEL_FLG,")
				.append("R_MOD_ID,")
				.append("R_MOD_TIME,")
				.append("R_CRE_ID,")
				.append("R_CRE_TIME ")
				.append(" from atdt ")
				.append(" where")
				.append("   INST_ID= ?")
				.append(" and  BRANCH_ID= ?")
				.append(" and  DEL_FLG=?");
		data.add(academicTermDetailsKey.getInstId());
		data.add(academicTermDetailsKey.getBranchId());
		data.add("N");
		
		
		if ((academicTermDetailsKey.getAcTerm() != null)
				&& (academicTermDetailsKey.getAcTerm() != "")) {
			sql.append(" and AC_TERM=?  ");
			logger.debug("AC_TERM:" + academicTermDetailsKey.getAcTerm());
			data.add(academicTermDetailsKey.getAcTerm().trim());
		}
		if ((academicTermDetailsKey.getDbTs() != null)&&(academicTermDetailsKey.getDbTs() != 0)) {
			sql.append(" and DB_TS=?  ");
			logger.debug("DB_TS  :" + academicTermDetailsKey.getDbTs());
			data.add(academicTermDetailsKey.getDbTs());
		}
		AcademicTermDetails selectedListAcademicTermDetails = null;
		Object[] array = data.toArray(new Object[data.size()]);
		logger.debug("Where clause conditions size :" + array.length);
		
		selectedListAcademicTermDetails = (AcademicTermDetails) getJdbcTemplate()
				.query(sql.toString(), array, new ResultSetExtractor<AcademicTermDetails>() {
					
					@Override
					public AcademicTermDetails extractData(ResultSet rs)
							throws SQLException, DataAccessException {
						AcademicTermDetails academicTermDetails = null;
						if (rs.next()) {
							academicTermDetails = new AcademicTermDetails();
							
							academicTermDetails.setDbTs(rs.getInt("DB_TS"));
							academicTermDetails.setInstId(rs.getString("INST_ID"));
							academicTermDetails.setBranchId(rs.getString("BRANCH_ID"));
							academicTermDetails.setAcYear(rs.getString("AC_YEAR"));
							academicTermDetails.setAcTerm(rs.getString("AC_TERM"));
							academicTermDetails.setTermStartDate(rs.getString("TERM_START_DATE"));
							academicTermDetails.setTermEndDate(rs.getString("TERM_END_DATE"));
							academicTermDetails.setWeeklyHoliday(rs.getString("WEEKLY_HOLIDAY"));
							academicTermDetails.setAcTermSts(rs.getString("AC_TERM_STS"));
							academicTermDetails.setDelFlag((rs.getString("DEL_FLG")));
							academicTermDetails.setrModId(rs.getString("R_MOD_ID"));
							academicTermDetails.setrModTime(rs.getString("R_MOD_TIME"));
							academicTermDetails.setrCreId(rs.getString("R_CRE_ID"));
							academicTermDetails.setrCreTime(rs.getString("R_CRE_TIME"));
							academicTermDetails.setPromotionStatus(rs.getString("PROMO_PROC_STS"));
							academicTermDetails.setPromotionId(rs.getString("PROMO_PROC_STS_ID"));
						}
						return academicTermDetails;
					}
					
				});
		
		if (selectedListAcademicTermDetails == null) {
			throw new NoDataFoundException();
		}
		
		return selectedListAcademicTermDetails;
	}
	
	@Override
	public boolean checkAcademicTermStatusRec(final AcademicTermDetailsKey academicTermDetailsKey,String action) {
		
		logger.debug("Inside Check method");
		
		boolean check = false;
		StringBuffer sql = new StringBuffer();
		sql.append("select ")
				.append("AC_TERM_STS ")
				.append(" from atdt ")
				.append(" where")
				.append("   INST_ID= ?")
				.append(" and  BRANCH_ID= ?")
				.append(" and  AC_TERM_STS= '"+academicTermDetailsKey.getAcTermSts()+"'");
				if(action!=""){
				sql.append(" and  AC_TERM NOT in ('"+academicTermDetailsKey.getAcTerm()+"')");
				}
				sql.append(" and  DEL_FLG='N'");
		AcademicTermDetails selectedListAcademicTermDetails = null;
		selectedListAcademicTermDetails = (AcademicTermDetails) getJdbcTemplate()
				.query(sql.toString(), new PreparedStatementSetter() {
					
					@Override
					public void setValues(PreparedStatement ps)
							throws SQLException {
						// ps.setInt(1, academicTermDetailsKey.getDbTs());
						ps.setString(1, academicTermDetailsKey.getInstId().trim());
						ps.setString(2, academicTermDetailsKey.getBranchId().trim());
						
					}
					
				}, new ResultSetExtractor<AcademicTermDetails>() {
					
					@Override
					public AcademicTermDetails extractData(ResultSet rs)
							throws SQLException, DataAccessException {
						AcademicTermDetails academicTermDetails = null;
						if (rs.next()) {
							academicTermDetails = new AcademicTermDetails();
							academicTermDetails.setAcTerm(rs.getString("AC_TERM_STS"));
						}
						return academicTermDetails;
					}
					
				});
		
		if (selectedListAcademicTermDetails != null) {
			check = true;
			
		}
		else {
			check = false;
		}
		
		return check;
	}
	
	@Override
	public AcademicTermDetails getAcaTrmRecForHolidayGeneration(
			final AcademicTermDetailsKey academicTermDetailsKey) {
		logger.debug("Inside select method");
		logger.debug("Academic Term Details Key object values :"
				+ academicTermDetailsKey.toString());
		
		StringBuffer sql = new StringBuffer();
		sql.append("select ")
				.append("TERM_START_DATE,")
				.append("TERM_END_DATE,")
				.append("WEEKLY_HOLIDAY")
				.append(" from atdt ")
				.append(" where")
				.append("  BRANCH_ID= ?")
				.append(" and AC_TERM= ?")
				.append(" and  DEL_FLG='N'")
				.append(" and  (AC_TERM_STS='F' or AC_TERM_STS='P')");
		AcademicTermDetails selectedListAcademicTermDetails = null;
		selectedListAcademicTermDetails = (AcademicTermDetails) getJdbcTemplate()
				.query(sql.toString(), new PreparedStatementSetter() {
					
					@Override
					public void setValues(PreparedStatement ps)
							throws SQLException {
						ps.setString(1, academicTermDetailsKey.getBranchId().trim());
						ps.setString(2, academicTermDetailsKey.getAcTerm().trim());
						
					}
					
				}, new ResultSetExtractor<AcademicTermDetails>() {
					
					@Override
					public AcademicTermDetails extractData(ResultSet rs)
							throws SQLException, DataAccessException {
						AcademicTermDetails academicTermDetails = null;
						if (rs.next()) {
							academicTermDetails = new AcademicTermDetails();
							
							academicTermDetails.setTermStartDate(rs.getString("TERM_START_DATE"));
							academicTermDetails.setTermEndDate(rs.getString("TERM_END_DATE"));
							academicTermDetails.setWeeklyHoliday(rs.getString("WEEKLY_HOLIDAY"));
						}
						return academicTermDetails;
					}
					
				});
		
		if (selectedListAcademicTermDetails == null) {
			
		}
		
		return selectedListAcademicTermDetails;
	}
	
	@Override
	public AcademicTermDetails checkAcTermValidation(
			final AcademicTermDetailsKey academicTermDetailsKey) throws NoDataFoundException {
		logger.debug("Inside Check method");
		
		boolean check = false;
		StringBuffer sql = new StringBuffer();
		sql.append("select ")
				.append("AC_TERM_STS ")
				.append(" from atdt ")
				.append(" where")
				.append("   INST_ID= ?")
				.append(" and  BRANCH_ID= ?")
				.append(" and  AC_TERM= ?")
				.append(" and  DEL_FLG='N'");
		AcademicTermDetails selectedListAcademicTermDetails = null;
		selectedListAcademicTermDetails = (AcademicTermDetails) getJdbcTemplate()
				.query(sql.toString(), new PreparedStatementSetter() {
					
					@Override
					public void setValues(PreparedStatement ps)
							throws SQLException {
						ps.setString(1, academicTermDetailsKey.getInstId().trim());
						ps.setString(2, academicTermDetailsKey.getBranchId().trim());
						ps.setString(3, academicTermDetailsKey.getAcTerm().trim());
						
					}
					
				}, new ResultSetExtractor<AcademicTermDetails>() {
					
					@Override
					public AcademicTermDetails extractData(ResultSet rs)
							throws SQLException, DataAccessException {
						AcademicTermDetails academicTermDetails = null;
						if (rs.next()) {
							academicTermDetails = new AcademicTermDetails();
							academicTermDetails.setAcTermSts(rs.getString("AC_TERM_STS"));
						}
						return academicTermDetails;
					}
					
				});
		
		if (selectedListAcademicTermDetails == null) {
			throw new NoDataFoundException();
		}
		
		return selectedListAcademicTermDetails;
	}

	@Override
	public String getCurrentAcademicTerm(String instId, String branchId,
			String status) throws NoDataFoundException {
		logger.debug("Inside select method");
		logger.debug("Academic Term Details Key object values :"+"Inst Id-"+instId+"Branch Id-"+branchId+"Status-"+status);
		List<Object> data = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		sql.append("select ")
				/*.append(" DB_TS,")
				.append(" INST_ID,")
				.append("BRANCH_ID,")
				.append("AC_YEAR,")*/
				.append("AC_TERM ")
				/*.append("TERM_START_DATE,")
				.append("TERM_END_DATE,")
				.append("WEEKLY_HOLIDAY,")
				.append("AC_TERM_STS,")
				.append("DEL_FLG,")
				.append("R_MOD_ID,")
				.append("R_MOD_TIME,")
				.append("R_CRE_ID,")
				.append("R_CRE_TIME ")*/
				.append(" from atdt ")
				.append(" where")
				.append("   INST_ID= ?")
				.append(" and  BRANCH_ID= ?")
				.append(" and  AC_TERM_STS= ?")
				.append(" and  DEL_FLG=?");
		data.add(instId);
		data.add(branchId);
		data.add(status);
		data.add("N");
		
		/*if ((academicTermDetailsKey.getAcYear() != null)
				&& (academicTermDetailsKey.getAcYear() != ""))
		{
			sql.append(" and AC_YEAR=?  ");
			logger.debug("AC_YEAR :" + academicTermDetailsKey.getAcYear());
			data.add(academicTermDetailsKey.getAcYear().trim());
		}
		if ((academicTermDetailsKey.getAcTerm() != null)
				&& (academicTermDetailsKey.getAcTerm() != "")) {
			sql.append(" and AC_TERM=?  ");
			logger.debug("AC_TERM:" + academicTermDetailsKey.getAcTerm());
			data.add(academicTermDetailsKey.getAcTerm().trim());
		}
		if ((academicTermDetailsKey.getDbTs() != null)) {
			sql.append(" and DB_TS=?  ");
			logger.debug("DB_TS  :" + academicTermDetailsKey.getDbTs());
			data.add(academicTermDetailsKey.getDbTs());
		}*/
		String selectedAcademicTerm = null;
		Object[] array = data.toArray(new Object[data.size()]);
		logger.debug("Where clause conditions size :" + array.length);
		
		selectedAcademicTerm = (String) getJdbcTemplate()
				.query(sql.toString(), array, new ResultSetExtractor<String>() {
					
					@Override
					public String extractData(ResultSet rs)
							throws SQLException, DataAccessException {
						String academicTerm = null;
						if (rs.next()) {
						//	academicTermDetails = new AcademicTermDetails();
							
							/*academicTermDetails.setDbTs(rs.getInt("DB_TS"));
							academicTermDetails.setInstId(rs.getString("INST_ID"));
							academicTermDetails.setBranchId(rs.getString("BRANCH_ID"));
							academicTermDetails.setAcYear(rs.getString("AC_YEAR"));*/
							academicTerm = rs.getString("AC_TERM");
							//academicTermDetails.setAcTerm(rs.getString("AC_TERM"));
							/*academicTermDetails.setTermStartDate(rs.getString("TERM_START_DATE"));
							academicTermDetails.setTermEndDate(rs.getString("TERM_END_DATE"));
							academicTermDetails.setWeeklyHoliday(rs.getString("WEEKLY_HOLIDAY"));
							academicTermDetails.setAcTermSts(rs.getString("AC_TERM_STS"));
							academicTermDetails.setDelFlag((rs.getString("DEL_FLG")));
							academicTermDetails.setrModId(rs.getString("R_MOD_ID"));
							academicTermDetails.setrModTime(rs.getString("R_MOD_TIME"));
							academicTermDetails.setrCreId(rs.getString("R_CRE_ID"));
							academicTermDetails.setrCreTime(rs.getString("R_CRE_TIME"));*/
						}
						return academicTerm;
					}
					
				});
		
		if (selectedAcademicTerm == null) {
			throw new NoDataFoundException();
		}
		
		return selectedAcademicTerm;
	}

	@Override
	public AcademicTermDetails selectAcademicTermDetailsDelFlgRec(
			AcademicTermDetailsKey academicTermDetailsKey)
			 {
		logger.debug("Inside select method");
		logger.debug("Academic Term Details Key object values :"
				+ academicTermDetailsKey.toString());
		List<Object> data = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		sql.append("select ")
				.append(" DB_TS,")
				.append(" INST_ID,")
				.append("BRANCH_ID,")
				.append("AC_YEAR,")
				.append("AC_TERM,")
				.append("TERM_START_DATE,")
				.append("TERM_END_DATE,")
				.append("WEEKLY_HOLIDAY,")
				.append("AC_TERM_STS,")
				.append("DEL_FLG,")
				.append("R_MOD_ID,")
				.append("R_MOD_TIME,")
				.append("R_CRE_ID,")
				.append("R_CRE_TIME ")
				.append(" from atdt ")
				.append(" where")
				.append("   INST_ID= ?")
				.append(" and  BRANCH_ID= ?")
				.append(" and  DEL_FLG=?");
		data.add(academicTermDetailsKey.getInstId());
		data.add(academicTermDetailsKey.getBranchId());
		data.add("Y");
		
		
		if ((academicTermDetailsKey.getAcTerm() != null)
				&& (academicTermDetailsKey.getAcTerm() != "")) {
			sql.append(" and AC_TERM=?  ");
			logger.debug("AC_TERM:" + academicTermDetailsKey.getAcTerm());
			data.add(academicTermDetailsKey.getAcTerm().trim());
		}
		if ((academicTermDetailsKey.getDbTs() != null)&&(academicTermDetailsKey.getDbTs() != 0)) {
			sql.append(" and DB_TS=?  ");
			logger.debug("DB_TS  :" + academicTermDetailsKey.getDbTs());
			data.add(academicTermDetailsKey.getDbTs());
		}
		AcademicTermDetails selectedListAcademicTermDetails = null;
		Object[] array = data.toArray(new Object[data.size()]);
		logger.debug("Where clause conditions size :" + array.length);
		
		selectedListAcademicTermDetails = (AcademicTermDetails) getJdbcTemplate()
				.query(sql.toString(), array, new ResultSetExtractor<AcademicTermDetails>() {
					
					@Override
					public AcademicTermDetails extractData(ResultSet rs)
							throws SQLException, DataAccessException {
						AcademicTermDetails academicTermDetails = null;
						if (rs.next()) {
							academicTermDetails = new AcademicTermDetails();
							
							academicTermDetails.setDbTs(rs.getInt("DB_TS"));
							academicTermDetails.setInstId(rs.getString("INST_ID"));
							academicTermDetails.setBranchId(rs.getString("BRANCH_ID"));
							academicTermDetails.setAcYear(rs.getString("AC_YEAR"));
							academicTermDetails.setAcTerm(rs.getString("AC_TERM"));
							academicTermDetails.setTermStartDate(rs.getString("TERM_START_DATE"));
							academicTermDetails.setTermEndDate(rs.getString("TERM_END_DATE"));
							academicTermDetails.setWeeklyHoliday(rs.getString("WEEKLY_HOLIDAY"));
							academicTermDetails.setAcTermSts(rs.getString("AC_TERM_STS"));
							academicTermDetails.setDelFlag((rs.getString("DEL_FLG")));
							academicTermDetails.setrModId(rs.getString("R_MOD_ID"));
							academicTermDetails.setrModTime(rs.getString("R_MOD_TIME"));
							academicTermDetails.setrCreId(rs.getString("R_CRE_ID"));
							academicTermDetails.setrCreTime(rs.getString("R_CRE_TIME"));
						}
						return academicTermDetails;
					}
					
				});
		
		
		
		return selectedListAcademicTermDetails;
	}

	@Override
	public void updateAcademicTermToDelFlgNRecs(
			final AcademicTermDetails academicTermDetailsRecord,
			final AcademicTermDetailsKey academicTermDetailsKey)
			throws UpdateFailedException {
		logger.debug("Inside update method");
		logger.debug("Academic Term Details object values :" + academicTermDetailsRecord.toString());
		logger.debug("Academic Term Details object values :" + academicTermDetailsKey.toString());
		StringBuffer sql = new StringBuffer();
		sql.append("update atdt set")
				.append(" DB_TS= ?,")
				.append("DEL_FLG= 'N',")
				.append("R_MOD_ID= ?,")
				.append(" R_MOD_TIME=now()")
				.append(" where")
				.append(" DB_TS= ?")
				.append(" and  INST_ID= ?")
				.append(" and  BRANCH_ID= ?")				
				.append(" and  AC_TERM= ?")
				.append(" and  DEL_FLG='Y'");
		
		int updateRecs = getJdbcTemplate().update(sql.toString(),
				new PreparedStatementSetter() {
					
					@Override
					public void setValues(PreparedStatement ps)
							throws SQLException {
						ps.setInt(1, academicTermDetailsRecord.getDbTs() + 1);
						ps.setString(2, academicTermDetailsRecord.getrModId().trim());
						ps.setInt(3, academicTermDetailsKey.getDbTs());
						ps.setString(4, academicTermDetailsKey.getInstId().trim());
						ps.setString(5, academicTermDetailsKey.getBranchId().trim());
						ps.setString(6, academicTermDetailsKey.getAcTerm().trim());
						
					}
					
				});
		if (updateRecs == 0) {
			throw new UpdateFailedException();
			
		}
		
	}

	@Override
	public int checkAcademicTermStatusForStudentPromotion(
			AcademicTermDetailsKey academicTermDetailsKey) {
		 logger.debug("Inside Check Academic Term Status for Student promotion  method");

			logger.debug("AcademicTermDetailsKey object values :"
					+ academicTermDetailsKey.toString());
			StringBuffer sql = new StringBuffer();
			
			
			sql.append("select exists(")
			.append("select AC_TERM ")
			.append("from atdt")				
					.append(" where ")			
					.append(" INST_ID='" + academicTermDetailsKey.getInstId()+ "'")
					.append(" and BRANCH_ID='" + academicTermDetailsKey.getBranchId()+ "'")
					.append(" and  AC_TERM='" + academicTermDetailsKey.getAcTerm()+ "'")				
					.append(" and  AC_TERM_STS='" + academicTermDetailsKey.getAcTermSts()+ "'")			
					.append(" and DEL_FLG='N')");
					
					
							
			int rows = getJdbcTemplate().queryForInt(
					sql.toString());

			System.out.println("Academic term status : "+rows);
			return rows;
	}

	@Override
	public void updateAcademicTermDetailsStatusRec(
			final AcademicTermDetails academicTermDetailsRecord,
			final AcademicTermDetailsKey academicTermDetailsKey)
			throws UpdateFailedException {
		logger.debug("Inside update method");
		logger.debug("Academic Term Details object values :" + academicTermDetailsRecord.toString());
		logger.debug("Academic Term Details object values :" + academicTermDetailsKey.toString());
		StringBuffer sql = new StringBuffer();
		sql.append("update atdt set")
				.append(" DB_TS= ?,")
				.append("PROMO_PROC_STS=?,")
				.append("PROMO_PROC_STS_ID=?,")	
				.append("DEL_FLG= 'N',")
				.append("R_MOD_ID= ?,")
				.append(" R_MOD_TIME=now()")
				.append(" where")
				.append(" DB_TS= ?")
				.append(" and  INST_ID= ?")
				.append(" and  BRANCH_ID= ?")
				.append(" and  AC_TERM= ?")
				.append(" and  DEL_FLG='N'");
		
		int updateRecs = getJdbcTemplate().update(sql.toString(),
				new PreparedStatementSetter() {
					
					@Override
					public void setValues(PreparedStatement ps)
							throws SQLException {
						ps.setInt(1, academicTermDetailsRecord.getDbTs() + 1);					
						ps.setString(2, academicTermDetailsRecord.getPromotionStatus().trim());
						ps.setString(3, academicTermDetailsRecord.getPromotionId().trim());
						ps.setString(4, academicTermDetailsRecord.getrModId().trim());
						ps.setInt(5, academicTermDetailsKey.getDbTs());
						ps.setString(6, academicTermDetailsKey.getInstId().trim());
						ps.setString(7, academicTermDetailsKey.getBranchId().trim());
						ps.setString(8, academicTermDetailsKey.getAcTerm().trim());
						
					}
					
				});
		if (updateRecs == 0) {
			throw new UpdateFailedException();
			
		}
		
	}

	@Override
	public String checkAcademicTermPromoteStatus(AcademicTermDetailsKey academicTermDetailsKey)
			throws NoDataFoundException {
		logger.debug("Inside Check Academic Term promote Status  method");
		logger.debug("AcademicTermDetailsKey object values :"+ academicTermDetailsKey.toString());
		StringBuffer sql = new StringBuffer();	
		List<String> data = new ArrayList<String>();
		

		sql.append("select PROMO_PROC_STS ")
		.append("from atdt")				
				.append(" where ")			
				.append(" INST_ID=?")
				.append(" and BRANCH_ID=?")
				.append(" and  PROMO_PROC_STS_ID=?")		
				.append(" and DEL_FLG='N'");
		data.add(academicTermDetailsKey.getInstId());
		data.add(academicTermDetailsKey.getBranchId());
		data.add(academicTermDetailsKey.getPromoteStsId());

		String[] array = data.toArray(new String[data.size()]);
		AcademicTermDetails academicTrmDeta = null;
		academicTrmDeta = (AcademicTermDetails) getJdbcTemplate().query(sql.toString(), array,
				new ResultSetExtractor<AcademicTermDetails>() {

					@Override
					public AcademicTermDetails extractData(ResultSet rs)
							throws SQLException, DataAccessException {
						AcademicTermDetails acaTrmDe = null;
						if (rs.next()) {

							acaTrmDe = new AcademicTermDetails();
							
							acaTrmDe.setPromotionStatus(rs.getString("PROMO_PROC_STS"));
						}
						return acaTrmDe;
					}

				});

		if (academicTrmDeta == null) {
			throw new NoDataFoundException();
		}
		System.out.println("academic term status value  : "+academicTrmDeta.toString());
		return academicTrmDeta.getPromotionStatus();
	

		
	}

	@Override
	public int checkAcademicTermPromotionStatus(
			AcademicTermDetailsKey academicTermDetailsKey) {
		 logger.debug("Inside Check Academic Term Status for Student promotion  method");

			logger.debug("AcademicTermDetailsKey object values :"
					+ academicTermDetailsKey.toString());
			StringBuffer sql = new StringBuffer();
			
			
			sql.append("select exists(")
			.append("select AC_TERM ")
			.append("from atdt")				
					.append(" where ")			
					.append(" INST_ID='" + academicTermDetailsKey.getInstId()+ "'")
					.append(" and BRANCH_ID='" + academicTermDetailsKey.getBranchId()+ "'")
					.append(" and  AC_TERM='" + academicTermDetailsKey.getAcTerm()+ "'")				
					.append(" and  AC_TERM_STS='" + academicTermDetailsKey.getAcTermSts()+ "'")	
					.append(" and  PROMO_PROC_STS='" + ApplicationConstant.STUDENT_PROMOTION_OPENED+ "'")	
					.append(" and DEL_FLG='N')");
					
					
							
			int rows = getJdbcTemplate().queryForInt(
					sql.toString());

			System.out.println("Academic term status : "+rows);
			return rows;
	}
	
}
