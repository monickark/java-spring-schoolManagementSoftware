package com.jaw.framework.sessCache.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.login.SessionCacheNotLoadedException;
import com.jaw.framework.dao.BaseDao;
import com.jaw.framework.sessCache.StudentSession;
import com.jaw.student.admission.dao.ParentDetails;

@Repository
public class ParentSessionDao extends BaseDao implements IParentSessionDao {
	Logger logger = Logger.getLogger(ParentSessionDao.class);
	
	@Override
	public ParentDetails retriveParentDetails(final String studentAdmisNo, final String instId,
			final String branchId)
			throws SessionCacheNotLoadedException {
		
		StringBuffer sql = new StringBuffer().append("select ")
				.append("FATHER_NAME").append(" from pard ").append("where ")
				.append("STUDENT_ADMIS_NO=?").append(" and ")
				.append(" DEL_FLG=?")
				.append(" and INST_ID=?")
				.append(" and BRANCH_ID=?");
		
		ParentDetails parentDetailsResult = getJdbcTemplate().query(
				sql.toString(), new PreparedStatementSetter() {
					
					@Override
					public void setValues(PreparedStatement pss)
							throws SQLException {
						pss.setString(1, studentAdmisNo);
						pss.setString(2, "N");
						pss.setString(3, instId);
						pss.setString(4, branchId);
						
					}
					
				}, new ParentDetailsResultSetExtractor());
		
		if (parentDetailsResult == null) {
			throw new SessionCacheNotLoadedException();
		}
		return parentDetailsResult;
	}
	
	@Override
	public List<StudentSession> retriveStuParent(final String userId, final String instId,
			final String branchId,final String academicYear) throws NoDataFoundException {
		logger.debug("retrive StudentDetails");
		List<String> dataList = new ArrayList<String>();		
		StringBuffer sql = new StringBuffer().append("SELECT ")
				.append("stum.STUDENT_ADMIS_NO,")
				.append("stum.STUDENT_NAME,")
				.append("stum.STUDENTGRP_ID, ")
				.append("stgm.STUDENT_GRP, ")					
				.append("stum.STANDARD, ")	
				.append("stum.COMBINATION, ")	
				.append("stum.COURSE, ")	
				.append("stum.SEC ")					
				.append("FROM pard ")
				.append(",stum LEFT JOIN stgm ON ")						
				.append("stum.INST_ID = stgm.INST_ID AND ")
				.append("stum.BRANCH_ID = stgm.BRANCH_ID AND ")
				.append("stum.STUDENTGRP_ID = stgm.STUDENTGRP_ID AND ")
				.append("stum.DEL_FLG = stgm.DEL_FLG ")					
				.append(" WHERE ")
				.append("pard.INST_ID = stum.INST_ID AND ")
				.append("pard.BRANCH_ID = stum.BRANCH_ID AND ")
				.append("pard.STUDENT_ADMIS_NO = stum.STUDENT_ADMIS_NO AND ")				
				.append("pard.INST_ID = ?")					
				.append(" AND pard.PARENT_ID = ? ")
				.append(" AND pard.BRANCH_ID =? ")
				.append(" AND stum.ACADEMIC_YEAR = ? ")
				.append(" AND stum.DEL_FLG =?")
				.append(" ORDER BY stum.STUDENT_ADMIS_NO ASC;");											
											
		dataList.add(instId);								
		dataList.add(userId);
		
		dataList.add(branchId);
		dataList.add(academicYear);		
		dataList.add("N");		
		
		String[] array = dataList.toArray(new String[dataList.size()]);
		logger.debug("select query :" + sql.toString());
		
		List<StudentSession> studentMasterResult = getJdbcTemplate().query(
				sql.toString(), array, new StudentMasterRowMapper());
		if(studentMasterResult.size()==0){
			throw new NoDataFoundException();
		}	
		return studentMasterResult;
	}
	
	}
class ParentDetailsResultSetExtractor implements
		ResultSetExtractor<ParentDetails> {
	@Override
	public ParentDetails extractData(ResultSet rs) throws SQLException,
			DataAccessException {
		ParentDetails parentDetails = new ParentDetails();
		if (rs.next()) {
			parentDetails.setFatherName(rs.getString("FATHER_NAME"));
		}
		
		return parentDetails;
	}
}


class StudentMasterRowMapper implements RowMapper<StudentSession> {
	@Override
	public StudentSession mapRow(ResultSet rs, int arg1) throws SQLException {
		
		StudentSession studentMaster = new StudentSession();
		studentMaster.setStudentAdmisNo(rs.getString("STUDENT_ADMIS_NO"));
		studentMaster.setStudentName(rs.getString("STUDENT_NAME"));
		studentMaster.setStuGrpId(rs.getString("STUDENTGRP_ID"));
		studentMaster.setStuGrpName(rs.getString("STUDENT_GRP"));
		studentMaster.setStandard(rs.getString("STANDARD"));
		studentMaster.setSec(rs.getString("SEC"));
		studentMaster.setCourse(rs.getString("COURSE"));
		studentMaster.setCombination(rs.getString("COMBINATION"));
		
		
		return studentMaster;
	}
}
