package com.jaw.common.dropdown.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.map.LinkedMap;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.core.dao.CourseMaster;
import com.jaw.framework.dao.BaseDao;
import com.jaw.framework.sessCache.UserSessionDetails;
@Repository
public class TermAndSecListDAO extends BaseDao implements ITermAndSecListDAO {
	// Logging
		Logger logger = Logger.getLogger(TermAndSecListDAO.class);
	@Override
	public Map<String, String> selectTermList(
			final UserSessionDetails userSessionDetails) throws NoDataFoundException {

		logger.debug("Going to get list of term from stgm and cocd");
		
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ").append("DISTINCT(TERM_ID),").append("CODE_DESC ")
				.append(" FROM stgm S, cocd C ").append("WHERE ")
				.append("CM_CODE=TERM_ID AND ")
				.append("S.INST_ID=C.INST_ID AND  ")
				.append("S.BRANCH_ID=C.BRANCH_ID AND ")				
				.append("S.DEL_FLG=C.DEL_FLG AND  ")
				.append("S.DEL_FLG=? AND ")							
				.append(" C.INST_ID=? AND  ")
				.append(" C.BRANCH_ID=? AND  ")
				.append(" CODE_TYPE=? ");
		logger.debug("select query :" + sql.toString());

	Map<String,String> termMap= null;
	termMap = getJdbcTemplate().query(sql.toString(),
				new PreparedStatementSetter() {

					@Override
					public void setValues(PreparedStatement pss)
							throws SQLException {
						pss.setString(1, "N");
						pss.setString(2, userSessionDetails.getInstId());
						pss.setString(3, userSessionDetails.getBranchId());
						pss.setString(4, "TRM");

					}

				}, new TermRowMapper());
		if (termMap == null) {
			throw new NoDataFoundException();
		}
		return termMap;
	}

	@Override
	public Map<String, String> selectSectionList(
			final UserSessionDetails userSessionDetails,final String courseId,final String trmId) throws NoDataFoundException {

		logger.debug("Going to get list of term from stgm and cocd");
		logger.debug("Course Id :"+courseId+",Term Id :"+trmId);
				
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ").append("DISTINCT(SEC_ID),").append("CODE_DESC ")
				.append(" FROM stgm S, cocd C ").append("WHERE ")
				.append("CM_CODE=SEC_ID AND ")
				.append("S.INST_ID=C.INST_ID AND  ")
				.append("S.BRANCH_ID=C.BRANCH_ID AND ")				
				.append("S.DEL_FLG=C.DEL_FLG AND  ")
				.append("S.DEL_FLG=? AND ")							
				.append(" C.INST_ID=? AND  ")
				.append(" C.BRANCH_ID=? AND  ")
				.append(" S.COURSEMASTER_ID=?   ")
				.append("AND CODE_TYPE=? ");
				if((trmId!=null)&&(!trmId.equals(""))){
					sql.append(" AND S.TERM_ID=?   ");
				}
				
				
		logger.debug("select query :" + sql.toString());

	Map<String,String> secMap= null;
	secMap = getJdbcTemplate().query(sql.toString(),
				new PreparedStatementSetter() {

					@Override
					public void setValues(PreparedStatement pss)
							throws SQLException {
						pss.setString(1, "N");
						pss.setString(2, userSessionDetails.getInstId());
						pss.setString(3, userSessionDetails.getBranchId());
						pss.setString(4, courseId);
						pss.setString(5, "SEC");
						if((trmId!=null)&&(!trmId.equals(""))){
						pss.setString(6, trmId);
						}

					}

				}, new SectionRowMapper());
		if (secMap == null) {
			throw new NoDataFoundException();
		}
		return secMap;
	}

	@Override
	public Map<String, String> getsecAndTrmListFromStuGrpId(
			final UserSessionDetails userSessionDetails, final String stuGrpId) throws NoDataFoundException {

		logger.debug("Going to get list of term from stgm and cocd");
		logger.debug("Student Grp Id :"+stuGrpId);		

		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ").append("DISTINCT(SEC_ID),CODE_DESC ")
				.append(" FROM stgm,cocd ").append("WHERE ")
				.append("CM_CODE=SEC_ID AND ")
				.append("stgm.INST_ID=cocd.INST_ID AND  ")
				.append("stgm.BRANCH_ID=cocd.BRANCH_ID AND ")				
				.append("stgm.DEL_FLG=cocd.DEL_FLG AND  ")
				.append("stgm.DEL_FLG=? AND ")							
				.append("cocd.INST_ID=? AND  ")
				.append("cocd.BRANCH_ID=? AND  ")	
				.append("stgm.STUDENTGRP_ID=? AND  ")	
				.append(" CODE_TYPE=? ");
		logger.debug("select query :" + sql.toString());

	Map<String,String> secMap= null;
	secMap = getJdbcTemplate().query(sql.toString(),
				new PreparedStatementSetter() {

					@Override
					public void setValues(PreparedStatement pss)
							throws SQLException {
						pss.setString(1, "N");
						pss.setString(2, userSessionDetails.getInstId());
						pss.setString(3, userSessionDetails.getBranchId());	
						pss.setString(4, stuGrpId);	
						pss.setString(5, "SEC");

					}

				}, new SectionRowMapper());
		if (secMap == null) {
			throw new NoDataFoundException();
		}
		return secMap;
	}

	@Override
	public Map<String, Map<String,String>> getsecListForBatch(
			final String INSTID,final String BRANCHID)
			throws NoDataFoundException {

		logger.debug("Going to get list of term from stgm and cocd");
		logger.debug("Institute Id :"+INSTID+",Branch Id :"+BRANCHID);
				
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ").append("DISTINCT(SEC_ID),").append("CODE_DESC, ")
				.append("S.TERM_ID, ").append("S.COURSEMASTER_ID  ")
				.append(" FROM stgm S, cocd C ").append("WHERE ")
				.append("CM_CODE=SEC_ID AND ")
				.append("S.INST_ID=C.INST_ID AND  ")
				.append("S.BRANCH_ID=C.BRANCH_ID AND ")				
				.append("S.DEL_FLG=C.DEL_FLG AND  ")
				.append("S.DEL_FLG=? AND ")							
				.append(" C.INST_ID=? AND  ")
				.append(" C.BRANCH_ID=? AND  ")				
				.append(" CODE_TYPE=? ");
		logger.debug("select query :" + sql.toString());

	Map<String,Map<String,String>> secMap= null;
	secMap = getJdbcTemplate().query(sql.toString(),
				new PreparedStatementSetter() {

					@Override
					public void setValues(PreparedStatement pss)
							throws SQLException {
						pss.setString(1, "N");
						pss.setString(2, INSTID);
						pss.setString(3, BRANCHID);
					/*	pss.setString(4, COURSEID);
						pss.setString(5, TRMID);*/
						pss.setString(4, "SEC");

					}

				}, new SectionRowMapperForBatch());
		if (secMap == null) {
			throw new NoDataFoundException();
		}
		return secMap;
	}

}

class TermRowMapper implements ResultSetExtractor<Map<String,String>> {
	
	@Override
	public Map<String, String> extractData(ResultSet rs) throws SQLException,
			DataAccessException {
		Map<String,String> termAndDescMap =  new LinkedHashMap<String, String>();
		while(rs.next()){			
			termAndDescMap.put(rs.getString("TERM_ID"), rs.getString("CODE_DESC"));
		}
		return termAndDescMap;
	}
}

class SectionRowMapper implements ResultSetExtractor<Map<String,String>> {
	
	@Override
	public Map<String, String> extractData(ResultSet rs) throws SQLException,
			DataAccessException {
		Map<String,String> secAndDescMap = new LinkedHashMap<String, String>();
		while(rs.next()){			
			secAndDescMap.put(rs.getString("SEC_ID"), rs.getString("CODE_DESC"));
		}
		return secAndDescMap;
	}
	
	
}

class SectionRowMapperForBatch implements ResultSetExtractor<Map<String, Map<String,String>>> {
	
	@Override
	public Map<String, Map<String,String>> extractData(ResultSet rs) throws SQLException,
			DataAccessException {
		Map<String, Map<String,String>> secMapWithCrsAndTrm = new LinkedHashMap<String, Map<String,String>>();
		Map<String,String> secAndDesc = new LinkedHashMap<String,String>();
		while(rs.next()){	
			
			secAndDesc.put(rs.getString("SEC_ID"), rs.getString("CODE_DESC"));
			secMapWithCrsAndTrm.put(rs.getString("TERM_ID")+rs.getString("COURSEMASTER_ID"),secAndDesc);
		}
		System.out.println("secMapWithCrsAndTrm :"+secMapWithCrsAndTrm);
		return secMapWithCrsAndTrm;
	}
}
