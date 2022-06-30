package com.jaw.framework.sessCache.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.framework.dao.BaseDao;
import com.jaw.framework.sessCache.StudentSession;
import com.jaw.student.admission.dao.StudentMasterKey;

@Repository
public class StudentSessionDao extends BaseDao implements IStudentSessionDao {
	Logger logger = Logger.getLogger(StudentSessionDao.class);
		
	@Override
	public StudentSession selectStudentGrpId(
			StudentMasterKey studentMasKey)
			throws NoDataFoundException {
		
		logger.debug("Inside select method");					
		logger.info("StudentMasterKey Object :"+studentMasKey);
		List<Object> data = new ArrayList<Object>();		
		StringBuffer sql = new StringBuffer();
	    sql.append("SELECT  ")		        
			.append("STUDENT_ADMIS_NO, ")	
			.append("STANDARD, ")	
			.append("SEC,")	
			.append("COMBINATION, ")	
			.append("COURSE, ")	
			.append("STUDENT_NAME, ")	
			.append("stum.STUDENTGRP_ID, ")	
			.append("stgm.STUDENT_GRP ")				
			.append(" FROM stum LEFT JOIN stgm")			
			.append(" ON")
			.append(" stum.INST_ID = stgm.INST_ID AND")
			.append(" stum.BRANCH_ID = stgm.BRANCH_ID AND")
			.append(" stum.STUDENTGRP_ID = stgm.STUDENTGRP_ID AND ")
			.append(" stum.DEL_FLG = stgm.DEL_FLG WHERE")			
			.append("  STUDENT_ADMIS_NO= ?")			
			.append(" AND stum.INST_ID= ?")
			.append(" AND stum.BRANCH_ID= ?")
			.append(" AND ACADEMIC_YEAR= ?")
			.append(" AND stum.DEL_FLG= ?");
						
	    	data.add(studentMasKey.getStudentAdmisNo());
		    data.add(studentMasKey.getInstId());
			data.add(studentMasKey.getBranchId());			
			data.add(studentMasKey.getAcademicYear());			
			data.add("N");			
			
			  Object[] array = data.toArray(new Object[data.size()]);
			  
			  StudentSession selectedStudentGrpId = null;
		    
			  selectedStudentGrpId = (StudentSession) getJdbcTemplate()
				.query(sql.toString(), array, new StudentMasterResultSetExtractor());

		if (selectedStudentGrpId == null) {
			logger.error("No records found.");
			throw new NoDataFoundException();
		}
		return selectedStudentGrpId;
	}

	
	class StudentMasterResultSetExtractor implements
			ResultSetExtractor<StudentSession> {
		@Override
		public StudentSession extractData(ResultSet rs) throws SQLException {
			StudentSession studentMaster= null;
			if (rs.next()) {
				studentMaster = new StudentSession();
				studentMaster.setStudentAdmisNo(rs
						.getString("STUDENT_ADMIS_NO"));
				studentMaster.setStandard(rs.getString("STANDARD"));
				studentMaster.setSec(rs.getString("SEC"));				
				studentMaster.setCombination(rs.getString("COMBINATION"));
				studentMaster.setCourse(rs.getString("COURSE"));
				studentMaster.setStudentName(rs.getString("STUDENT_NAME"));		
				studentMaster.setStuGrpId(rs.getString("STUDENTGRP_ID"));
				studentMaster.setStuGrpName(rs.getString("STUDENT_GRP"));
			}
			
			return studentMaster;
		}
		
	}
	
}