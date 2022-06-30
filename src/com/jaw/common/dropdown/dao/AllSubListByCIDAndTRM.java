package com.jaw.common.dropdown.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.core.dao.CourseSubLink;
import com.jaw.framework.dao.BaseDao;
import com.jaw.framework.sessCache.UserSessionDetails;
@Repository
public class AllSubListByCIDAndTRM extends BaseDao implements IAllSubListByCIDAndTRM {
	// Logging
			Logger logger = Logger.getLogger(AllSubListByCIDAndTRM.class);
	@Override
	public Map<String, CourseSubLink> getSubListFromCrsl(final UserSessionDetails userSessionDetails,final String courseId,final String trmId) throws NoDataFoundException {

		logger.debug("Going to get list of subjects from crsl and cocd");
		
		StringBuffer sql = new StringBuffer();		
		sql.append("SELECT ").append("crsl.sub_type,crsl.SUB_ID, SUB_NAME ")
		.append("FROM crsl ,sbjm ").append("WHERE ")	
		.append("crsl.INST_ID=sbjm.INST_ID AND  ")
		.append("crsl.BRANCH_ID = sbjm.BRANCH_ID AND ")
		.append("crsl.SUB_ID = sbjm.SUB_ID AND  ")
		.append("crsl.DEL_FLG = sbjm.DEL_FLG AND  ")
		.append("crsl.INST_ID = ? AND  ")
		.append("crsl.BRANCH_ID = ? AND  ")
		.append("crsl.DEL_FLG =? AND ")
		.append("crsl.COURSEMASTER_ID =? AND ")
		.append("crsl.TERM_ID =? ");		
					
		
		
		logger.debug("select query :" + sql.toString());

	Map<String,CourseSubLink> termMap= null;
	termMap = getJdbcTemplate().query(sql.toString(),
				new PreparedStatementSetter() {

					@Override
					public void setValues(PreparedStatement pss)
							throws SQLException {						
						pss.setString(1, userSessionDetails.getInstId());
						pss.setString(2, userSessionDetails.getBranchId());
						pss.setString(3,"N");
						pss.setString(4,courseId);
						pss.setString(5,trmId);
						
					}

				}, new SubListRowMapper());
		if (termMap == null) {
			throw new NoDataFoundException();
		}
		return termMap;
	}
	@Override
	public Map<String, CourseSubLink> getSubListFromCrslAndSbjm(
			final UserSessionDetails userSessionDetails, final String stuGrpId)
			throws NoDataFoundException {

		logger.debug("Going to get list of subjects from crsl and sbjm using Student Group Id");						
		
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ").append("crsl.sub_type,crsl.SUB_ID, SUB_NAME ")
				.append("FROM crsl,sbjm,stgm ").append("WHERE ")	
				.append("crsl.INST_ID = sbjm.INST_ID AND  ")
				.append("crsl.BRANCH_ID = sbjm.BRANCH_ID AND ")				
				.append("crsl.SUB_ID = sbjm.SUB_ID AND  ")
				.append("crsl.DEL_FLG = sbjm.DEL_FLG AND  ")
				.append("crsl.INST_ID = stgm.INST_ID AND  ")
				.append("crsl.BRANCH_ID = stgm.BRANCH_ID AND  ")
				.append("crsl.DEL_FLG = stgm.DEL_FLG AND  ")
				.append("crsl.COURSEMASTER_ID = stgm.COURSEMASTER_ID AND ")
				.append("crsl.TERM_ID = stgm.TERM_ID AND ")
				.append("crsl.INST_ID =? AND ")
				.append("crsl.BRANCH_ID = ? AND ")
				.append("crsl.DEL_FLG = ? AND ")
				.append("stgm.STUDENTGRP_ID = ?");
				
											
		logger.debug("select query :" + sql.toString());

	Map<String,CourseSubLink> termMap= null;
	termMap = getJdbcTemplate().query(sql.toString(),
				new PreparedStatementSetter() {

					@Override
					public void setValues(PreparedStatement pss)
							throws SQLException {						
						pss.setString(1, userSessionDetails.getInstId());
						pss.setString(2, userSessionDetails.getBranchId());
						pss.setString(3,"N");						
						pss.setString(4,stuGrpId);
					}

				}, new SubListRowMapper());
		if (termMap == null) {
			throw new NoDataFoundException();
		}
		return termMap;
	}
	@Override
	public Map<String, String> getSubList(
			final UserSessionDetails userSessionDetails, final String stuGrpId,
			final String subType) throws NoDataFoundException {

		logger.debug("Going to get list of subjects from crsl and sbjm using Student Group Id");						
		
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ").append("crsl.SUB_ID, SUB_NAME ")
				.append("FROM crsl,sbjm,stgm ").append("WHERE ")	
				.append("crsl.INST_ID = sbjm.INST_ID AND  ")
				.append("crsl.BRANCH_ID = sbjm.BRANCH_ID AND ")				
				.append("crsl.SUB_ID = sbjm.SUB_ID AND  ")
				.append("crsl.DEL_FLG = sbjm.DEL_FLG AND  ")
				.append("crsl.INST_ID = stgm.INST_ID AND  ")
				.append("crsl.BRANCH_ID = stgm.BRANCH_ID AND  ")
				.append("crsl.DEL_FLG = stgm.DEL_FLG AND  ")
				.append("crsl.COURSEMASTER_ID = stgm.COURSEMASTER_ID AND ")
				.append("crsl.TERM_ID = stgm.TERM_ID AND ")
				.append("crsl.INST_ID =? AND ")
				.append("crsl.BRANCH_ID = ? AND ")
				.append("crsl.DEL_FLG = ? AND ")
				.append("stgm.STUDENTGRP_ID = ? AND ")
				.append("crsl.SUB_TYPE =?");
											
		logger.debug("select query :" + sql.toString());

	Map<String,String> termMap= null;
	termMap = getJdbcTemplate().query(sql.toString(),
				new PreparedStatementSetter() {

					@Override
					public void setValues(PreparedStatement pss)
							throws SQLException {						
						pss.setString(1, userSessionDetails.getInstId());
						pss.setString(2, userSessionDetails.getBranchId());
						pss.setString(3,"N");						
						pss.setString(4,stuGrpId);
						pss.setString(5,subType);
					}

				}, new SelectedSubListRowMapper());	
		if (termMap.size() ==0) {
			throw new NoDataFoundException();
		}	
		return termMap;
	}
	
	
	
	@Override
	public List<CourseSubLink> getAllSubjectListForBatch(
			final String INSTID,final String BRANCHID) throws NoDataFoundException {

		logger.debug("Going to all the subjects list from crsl and cocd");
		
		StringBuffer sql = new StringBuffer();		
		sql.append("SELECT ").append("crsl.sub_type,crsl.SUB_ID, SUB_NAME, ")
		.append("crsl.COURSEMASTER_ID,crsl.TERM_ID ")
		.append("FROM crsl ,sbjm ").append("WHERE ")	
		.append("crsl.INST_ID=sbjm.INST_ID AND  ")
		.append("crsl.BRANCH_ID = sbjm.BRANCH_ID AND ")
		.append("crsl.SUB_ID = sbjm.SUB_ID AND  ")
		.append("crsl.DEL_FLG = sbjm.DEL_FLG AND  ")
		.append("crsl.INST_ID = ? AND  ")
		.append("crsl.BRANCH_ID = ? AND  ")
		.append("crsl.DEL_FLG =?");
			
					
		
		
		logger.debug("select query :" + sql.toString());

	List<CourseSubLink> allSubList= null;
	allSubList = getJdbcTemplate().query(sql.toString(),
				new PreparedStatementSetter() {

					@Override
					public void setValues(PreparedStatement pss)
							throws SQLException {						
						pss.setString(1, INSTID);
						pss.setString(2, BRANCHID);
						pss.setString(3,"N");											
					}

				}, new SubListRowMapperForBatch());
		if (allSubList == null) {
			throw new NoDataFoundException();
		}
		return allSubList;
	}

}

class SubListRowMapper implements ResultSetExtractor<Map<String,CourseSubLink>> {
	
	@Override
	public Map<String, CourseSubLink> extractData(ResultSet rs) throws SQLException,
			DataAccessException {
		Map<String,CourseSubLink> termAndDescMap =  new LinkedHashMap<String, CourseSubLink>();
		CourseSubLink courseSub = null;
		while(rs.next()){
			courseSub = new CourseSubLink();
			courseSub.setSubId(rs.getString("SUB_ID"));
			courseSub.setSubType(rs.getString("SUB_TYPE"));			
			termAndDescMap.put(rs.getString("SUB_NAME"),courseSub);
		}
		return termAndDescMap;
	}
}

class SubListRowMapperForBatch implements RowMapper<CourseSubLink> {		

	@Override
	public CourseSubLink mapRow(ResultSet rs, int arg1) throws SQLException {
		CourseSubLink courseSubLink = new CourseSubLink();		
		courseSubLink.setSubId(rs.getString("SUB_ID"));
		courseSubLink.setSubName(rs.getString("SUB_NAME"));
		courseSubLink.setSubType(rs.getString("SUB_TYPE"));
		courseSubLink.setTermId(rs.getString("TERM_ID"));
		courseSubLink.setCourseMasterId(rs.getString("COURSEMASTER_ID"));
		return courseSubLink;
	}
}

class SelectedSubListRowMapper implements ResultSetExtractor<Map<String,String>> {
	
	@Override
	public Map<String, String> extractData(ResultSet rs) throws SQLException,
			DataAccessException {
		Map<String,String> termAndDescMap =  new LinkedHashMap<String, String>();	
		while(rs.next()){		
			
			termAndDescMap.put(rs.getString("SUB_ID"), rs.getString("SUB_NAME"));			
		}
		return termAndDescMap;
	}
}


