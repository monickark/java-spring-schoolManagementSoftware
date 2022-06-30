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
//CourseMaster DAO class
@Repository
public class CourseMasterDAO extends BaseDao implements ICourseMasterDAO{
	// Logging
	Logger logger = Logger.getLogger(CourseMasterDAO.class);

	@Override
	public void insertCourseMasterRec(final CourseMaster courseMaster)
			throws DuplicateEntryException {
		logger.debug("Inside insert method");
		logger.debug("Course Master object values :"
				+ courseMaster.toString());
		StringBuffer query = new StringBuffer();
		
		query = query.append("insert into crsm ( ")
				.append("DB_TS,")
				.append("INST_ID,")
				.append("BRANCH_ID,")
				.append("COURSEMASTER_ID,")
				.append("COURSE_NAME,")
				.append("COURSE_ID,")
				.append("COMB_BRNCH_ID,")
				.append("COURSE_GRP,")				
				.append("CV_APPCBLE,")
				.append("CV_CAT_TYPE,")
				.append("CV_LIST_TYPE,")
				.append("AFF_ID,")
				.append("AFF_DETAILS,")
				.append("TERM_APPLCBLE,")
				.append("DEPT_ID,")				
				.append("MEDIUM,")
				.append("DEL_FLG,")
				.append("R_MOD_ID,")
				.append("R_MOD_TIME,")
				.append("R_CRE_ID,")
				.append("R_CRE_TIME)")
				.append(" values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,now(),?,now())");

		try {
			getJdbcTemplate().update(query.toString(),
					new PreparedStatementSetter() {

						@Override
						public void setValues(PreparedStatement pss)
								throws SQLException {
							pss.setInt(1, courseMaster.getDbTs());
							pss.setString(2, courseMaster.getInstId());
							pss.setString(3, courseMaster.getBranchId());
							pss.setString(4, courseMaster.getCourseMasterId().trim());
							pss.setString(5, courseMaster.getCourseName().trim());
							pss.setString(6, courseMaster.getCourseId().trim());
							pss.setString(7, courseMaster.getCombBranchId().trim());
							pss.setString(8, courseMaster.getCourseGrp().trim());
							pss.setString(9, courseMaster.getCvAppcble());
							pss.setString(10, courseMaster.getCvCatType().trim());
							pss.setString(11, courseMaster.getCvListType().trim());
							pss.setString(12, courseMaster.getAffId().trim());
							pss.setString(13, courseMaster.getAffDetails().trim());
							pss.setString(14, courseMaster.getTermApplcble().trim());
							pss.setString(15, courseMaster.getDepartment().trim());
							pss.setString(16, courseMaster.getMedium().trim());
							pss.setString(17, courseMaster.getDelFlag().trim());
							pss.setString(18, courseMaster.getrModId().trim());
							pss.setString(19, courseMaster.getrCreId().trim());

						}

					});
		} catch (org.springframework.dao.DuplicateKeyException e) {
			logger.error("Duplicate Entry exception occured");
			throw new DuplicateEntryException();
		}
		
	}

	@Override
	public void updateCourseMasterRec(final CourseMaster courseMaster,
			final CourseMasterKey courseMasterkey) throws UpdateFailedException {
		logger.debug("Inside update method");
		logger.debug("Course Master object values :"+ courseMaster.toString());
		logger.debug("Course Master key Details object values :"+ courseMasterkey.toString());		
		StringBuffer sql = new StringBuffer();
			sql.append("update crsm set")
		        .append(" DB_TS= ?,")				
				.append("COURSE_NAME= ?,")
				.append("COURSE_ID= ?,")
				.append("COMB_BRNCH_ID= ?,")
				.append("COURSE_GRP= ?,")				
				.append("CV_APPCBLE= ?,")
				.append("CV_CAT_TYPE= ?,")
				.append("CV_LIST_TYPE= ?,")
				.append("AFF_ID= ?,")
				.append("AFF_DETAILS= ?,")
				.append("TERM_APPLCBLE= ?,")
				.append("DEPT_ID=?,")	
				.append("MEDIUM= ?,")
				.append("DEL_FLG= 'N',")
				.append("R_MOD_ID= ?,")
				.append(" R_MOD_TIME=now()")
				.append(" where")
				.append(" DB_TS= ?")
				.append(" and  INST_ID= ?")
				.append(" and  BRANCH_ID= ?")
				.append(" and  COURSEMASTER_ID= ?")	
				.append(" and  DEL_FLG='N'");

		

		int updateRecs = getJdbcTemplate().update(sql.toString(),
				new PreparedStatementSetter() {

					@Override
					public void setValues(PreparedStatement ps)
							throws SQLException {
						ps.setInt(1, courseMaster.getDbTs() + 1);
						ps.setString(2, courseMaster.getCourseName().trim());
						ps.setString(3, courseMaster.getCourseId().trim());
						ps.setString(4, courseMaster.getCombBranchId().trim());
						ps.setString(5, courseMaster.getCourseGrp().trim());
						ps.setString(6, courseMaster.getCvAppcble().trim());
						ps.setString(7, courseMaster.getCvCatType().trim());
						ps.setString(8, courseMaster.getCvListType().trim());
						ps.setString(9, courseMaster.getAffId().trim());
						ps.setString(10, courseMaster.getAffDetails().trim());
						ps.setString(11, courseMaster.getTermApplcble().trim());
						ps.setString(12, courseMaster.getDepartment().trim());
						ps.setString(13, courseMaster.getMedium().trim());
						ps.setString(14, courseMaster.getrModId().trim());	
						
						ps.setInt(15, courseMasterkey.getDbTs());
						ps.setString(16, courseMasterkey.getInstId().trim());
						ps.setString(17, courseMasterkey.getBranchId().trim());
						ps.setString(18, courseMasterkey.getCourseMasterId().trim());
						

					}

				});
		if (updateRecs == 0) {
			logger.error("Update failed exception occured");
			throw new UpdateFailedException();

		}
		
		
	}

	@Override
	public void deleteCourseMasterRec(final CourseMaster courseMaster,
			final CourseMasterKey courseMasterkey) throws DeleteFailedException {
		logger.debug("Inside delete method");
		logger.debug("Course Master object values :"+ courseMaster.toString());
		logger.debug("Course Master Key object values :"+ courseMasterkey.toString());
		
		StringBuffer sql = new StringBuffer();
		   sql.append("update crsm set")
		        .append(" DB_TS= ?,")
				.append("DEL_FLG= ?,")
				.append("R_MOD_ID= ?,")
				.append(" R_MOD_TIME=now()")
				.append(" where")
				.append(" DB_TS= ?")
				.append(" and INST_ID= ?")
				.append(" and  BRANCH_ID= ?")
				.append(" and  COURSEMASTER_ID= ?")	
				.append(" and  DEL_FLG='N'");

		int deletedRecs = getJdbcTemplate().update(sql.toString(),
				new PreparedStatementSetter() {

					@Override
					public void setValues(PreparedStatement ps)
							throws SQLException {
						ps.setInt(1, courseMaster.getDbTs());
						ps.setString(2, courseMaster.getDelFlag());
						ps.setString(3, courseMaster.getrModId().trim());
						ps.setInt(4, courseMasterkey.getDbTs());
						ps.setString(5, courseMasterkey.getInstId().trim());
						ps.setString(6, courseMasterkey.getBranchId().trim());
						ps.setString(7, courseMasterkey.getCourseMasterId().trim());
						

					}

				});
		if (deletedRecs == 0) {
			logger.error("Delete failed exception occured");
			throw new DeleteFailedException();

		}
		
		
	}

	@Override
	public CourseMaster selectCourseMasterRec(final CourseMasterKey courseMasterKey)
			throws NoDataFoundException {
		logger.debug("Inside select method");
		logger.debug("Course Master Key object values :"+ courseMasterKey.toString());
		List<Object> data = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		    sql.append("select ")
		        .append(" DB_TS,")
				.append("INST_ID,")
				.append("BRANCH_ID,")
				.append("COURSEMASTER_ID,")
				.append("COURSE_NAME,")
				.append("COURSE_ID,")
				.append("COMB_BRNCH_ID,")
				.append("COURSE_GRP,")				
				.append("CV_APPCBLE,")
				.append("CV_CAT_TYPE,")
				.append("CV_LIST_TYPE,")
				.append("AFF_ID,")
				.append("AFF_DETAILS,")
				.append("TERM_APPLCBLE,")
				.append("DEPT_ID,")
				.append("MEDIUM,")
				.append("DEL_FLG,")
				.append("R_MOD_ID,")
				.append("R_MOD_TIME,")
				.append("R_CRE_ID,")
				.append("R_CRE_TIME")
				.append(" from crsm ")
				.append(" where")
				.append("   INST_ID= ?")
				.append(" and  BRANCH_ID= ?")
				.append(" and  COURSEMASTER_ID= ?")
				.append(" and  DEL_FLG=?");	
		    data.add(courseMasterKey.getInstId());
			data.add(courseMasterKey.getBranchId());
			data.add(courseMasterKey.getCourseMasterId());
			data.add("N");
			if ((courseMasterKey.getDbTs()!=null)&&(courseMasterKey.getDbTs() !=0)) {
				sql.append(" and DB_TS=?  ");
				logger.debug("Db Ts Value :" + courseMasterKey.getDbTs());
				data.add(courseMasterKey.getDbTs());
			}
			  Object[] array = data.toArray(new Object[data.size()]);
		    CourseMaster selectedCourseMaster = null;
		    selectedCourseMaster = (CourseMaster) getJdbcTemplate()
				.query(sql.toString(), array, new ResultSetExtractor<CourseMaster>() {

					@Override
					public CourseMaster extractData(ResultSet rs)
							throws SQLException, DataAccessException {
						CourseMaster courseMaster = null;
						if (rs.next()) {
							courseMaster = new CourseMaster();
							courseMaster.setDbTs(rs.getInt("DB_TS"));
							courseMaster.setInstId(rs.getString("INST_ID"));
							courseMaster.setBranchId(rs.getString("BRANCH_ID"));
							courseMaster.setCourseMasterId(rs.getString("COURSEMASTER_ID"));
							courseMaster.setCourseName(rs.getString("COURSE_NAME"));
							courseMaster.setCourseId(rs.getString("COURSE_ID"));
							courseMaster.setCombBranchId(rs.getString("COMB_BRNCH_ID"));
							courseMaster.setCourseGrp(rs.getString("COURSE_GRP"));							
							courseMaster.setCvAppcble(rs.getString("CV_APPCBLE"));
							courseMaster.setCvCatType(rs.getString("CV_CAT_TYPE"));
							courseMaster.setCvListType(rs.getString("CV_LIST_TYPE"));
							courseMaster.setAffId(rs.getString("AFF_ID"));
							courseMaster.setAffDetails(rs.getString("AFF_DETAILS"));
							courseMaster.setTermApplcble(rs.getString("TERM_APPLCBLE"));
							courseMaster.setDepartment(rs.getString("DEPT_ID"));								
							courseMaster.setMedium(rs.getString("MEDIUM"));
							courseMaster.setDelFlag(rs.getString("DEL_FLG"));
							courseMaster.setrModId(rs.getString("R_MOD_ID"));
							courseMaster.setrModTime(rs.getString("R_MOD_TIME"));
							courseMaster.setrCreId(rs.getString("R_CRE_ID"));
							courseMaster.setrCreTime(rs.getString("R_CRE_TIME"));
						}
						return courseMaster;
					}

				});

		if (selectedCourseMaster == null) {
			logger.error("No data found exception occured");
			throw new NoDataFoundException();
		}

		return selectedCourseMaster;
	}

	@Override
	public int checkCourseMasterExist(CourseMaster courseMaster) {
		 logger.debug("Inside Check Course Master  method");

			logger.debug("course master object values :"
					+ courseMaster.toString());
			StringBuffer sql = new StringBuffer();			
		
			sql.append("select exists(")
			.append("select COURSE_ID ")
			.append("from crsm")				
					.append(" where ")			
					.append(" INST_ID='" + courseMaster.getInstId()+ "'")
					.append(" and BRANCH_ID='" + courseMaster.getBranchId()+ "'")
					.append(" and  COURSE_ID='" + courseMaster.getCourseId()+ "'")
					.append(" and  COMB_BRNCH_ID='" + courseMaster.getCombBranchId()+ "'")				
					.append(" and DEL_FLG='N')");
					
					
							
			int rows = getJdbcTemplate().queryForInt(
					sql.toString());		
			
			return rows; 
	 }

	@Override
	public boolean checkForCVForStuUpdate(String stuGrpId,String instId,String branchId) {
			
		logger.debug("Inside select method");	
		List<Object> data = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		    sql.append("select ")
		        .append(" CV_APPCBLE")			
				.append(" from crsm,stgm ")
				.append(" where")
				.append(" crsm.INST_ID=stgm.INST_ID and")
				.append(" crsm.BRANCH_ID=stgm.BRANCH_ID and")
				.append(" crsm.DEL_FLG=stgm.DEL_FLG and")
				.append(" crsm.COURSEMASTER_ID=stgm.COURSEMASTER_ID and")
				.append(" stgm.STUDENTGRP_ID=? AND")
				.append(" crsm.INST_ID= ?")
				.append(" AND  crsm.BRANCH_ID= ?")				
				.append(" AND  crsm.DEL_FLG=?");	
		    data.add(stuGrpId);
			data.add(instId);
			data.add(branchId);
			data.add("N");			
			  Object[] array = data.toArray(new Object[data.size()]);		 
			  String   cvmas =  getJdbcTemplate()
				.query(sql.toString(), array, new ResultSetExtractor<String>() {

					@Override
					public String extractData(ResultSet rs)
							throws SQLException, DataAccessException {
						String cv = null;
						if (rs.next()) {												
							cv = rs.getString("CV_APPCBLE");							
						}
						return cv;
					}

				});

		if (cvmas == null||cvmas.equals("N")) {
		return false;
		}else{
			return true;
		}
		
	}

	@Override
	public boolean checkForCVFromCourseId(String courseId, String instId,
			String branchId) {
		
	logger.debug("Inside checkForCVFromCourseId method :"+courseId+","+instId+","+branchId);	
	List<Object> data = new ArrayList<Object>();
	StringBuffer sql = new StringBuffer();			
	    sql.append("SELECT ")
	        .append(" CV_APPCBLE")			
			.append(" FROM crsm")
			.append(" WHERE")
			.append(" COURSEMASTER_ID=? AND")
			.append(" INST_ID=? AND")
			.append(" BRANCH_ID=? AND")								
			.append(" DEL_FLG=?");	
	    data.add(courseId);
		data.add(instId);
		data.add(branchId);
		data.add("N");			
		  Object[] array = data.toArray(new Object[data.size()]);		 
		  String   cvmas =  getJdbcTemplate()
			.query(sql.toString(), array, new ResultSetExtractor<String>() {

				@Override
				public String extractData(ResultSet rs)
						throws SQLException, DataAccessException {
					String cv = null;
					if (rs.next()) {												
						cv = rs.getString("CV_APPCBLE");							
					}
					return cv;
				}

			});
		
	if (cvmas == null||cvmas.equals("N")||cvmas.trim().equals("")) {		
	return false;
	}else if(cvmas.equals("Y")){
		return true;
	}else{
		return false;
	}
	
}

}
