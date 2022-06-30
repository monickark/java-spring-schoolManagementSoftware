package com.jaw.user.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.framework.dao.BaseDao;

@Repository
public class SingleUserDetailsDao extends BaseDao implements
		ISingleUserDetailsDao {
	
	Logger logger = Logger.getLogger(SingleUserDetailsDao.class);
	
	@Override
	public SingleStudentDetails selectSingleStudentDetails(
			final String branchId, final String instId, final String userId)
			throws NoDataFoundException {
		SingleStudentDetails results = null;
		StringBuffer query = new StringBuffer();
		query.append("SELECT ").append("INST_ID,").append("BRANCH_ID,")
				.append("ACADEMIC_YEAR,").append("STANDARD,").append("SEC,")
				.append("COMBINATION,").append("STUDENT_NAME,")
				.append("STUDENT_ADMIS_NO")
				.append(" FROM studentmaster  WHERE ").append("del_flg='N'")
				.append("and inst_id=?").append("and branch_id=?")
				.append(" and STUDENT_ADMIS_NO=?");
		try {
			results = (SingleStudentDetails) getJdbcTemplate().query(
					query.toString(), new PreparedStatementSetter() {
						
						@Override
						public void setValues(java.sql.PreparedStatement pss)
								throws SQLException {
							pss.setString(1, instId);
							pss.setString(2, branchId);
							pss.setString(3, userId);
						}
					},
					
					new ResultSetExtractor<SingleStudentDetails>() {
						
						@Override
						public SingleStudentDetails extractData(ResultSet rs)
								throws SQLException, DataAccessException {
							SingleStudentDetails request = new SingleStudentDetails();
							if (rs.next()) {
								request.setInstId(rs.getString("INST_ID"));
								request.setBranchId(rs.getString("BRANCH_ID"));
								request.setAcademicYear(rs
										.getString("ACADEMIC_YEAR"));
								request.setStudentAdmissionNumber(rs
										.getString("STUDENT_ADMIS_NO"));
								request.setStandard(rs.getString("STANDARD"));
								request.setSec(rs.getString("SEC"));
								request.setCombination(rs.getString("COMBINATION"));
								request.setStudentName(rs.getString("STUDENT_NAME"));
							}
							return request;
						}
						
					});
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		if (results == null) {
			throw new NoDataFoundException();
		}
		return results;
	}
	
	@Override
	public List<SingleParentDetails> selectSingleParentDetails(
			final String branchId, final String instId,
			final String currentYear, final String parentId) throws NoDataFoundException {
		List<SingleParentDetails> results = null;
		StringBuffer query = new StringBuffer();
		
		logger.debug("Select single parent details where condition :  branchid:"
				+ branchId + " instId :" + instId + " currentyear :"
				+ currentYear + " parentId :" + parentId);
		
		query.append("SELECT ")
				.append("a.INST_ID,")
				.append("a.BRANCH_ID,")
				.append("a.ACADEMIC_YEAR,")
				.append("STANDARD,")
				.append("a.STUDENT_ADMIS_NO,")
				.append("SEC,")
				.append("COMBINATION,")
				.append("STUDENT_NAME, ")
				.append("FATHER_NAME ")
				.append(" FROM studentmaster a ,parentdetails b   ")
				.append("where ")
				.append("a.inst_id = b.inst_id and")
				.append("  a.branch_id = b.branch_id and ")
				.append("  a.student_admis_no = b.student_admis_no ")
				.append("  and a.del_flg = b.del_flg  ")
				.append("and a.inst_id =? ")
				.append("and a.branch_id =? ")
				.append("and a.academic_year = ?  ")
				.append("and a.del_flg = ? ")
				.append("and a.student_admis_no in ( ")
				.append(" SELECT a.STUDENT_ADMIS_NO ")
				.append(" FROM studentmaster a,parentdetails b  where ")
				.append("  a. inst_id = b.inst_id and  ")
				.append("  a.branch_id = b.branch_id and ")
				.append("  a.student_admis_no = b.student_admis_no ")
				.append("  and a.del_flg = b.del_flg  ")
				.append("  and b.del_flg = ?")
				.append("  and a.academic_year = ?")
				.append("  and a.inst_id = ?")
				.append("  and a.branch_id = ? and b.parent_id=?) ");
		results = getJdbcTemplate().query(
				query.toString(), new PreparedStatementSetter() {
					
					@Override
					public void setValues(java.sql.PreparedStatement pss)
							throws SQLException {
						
						pss.setString(1, instId);
						pss.setString(2, branchId);
						pss.setString(3, currentYear);
						pss.setString(4, "N");
						pss.setString(5, "N");
						pss.setString(6, currentYear);
						pss.setString(7, instId);
						pss.setString(8, branchId);
						pss.setString(9, parentId);
					}
					
				}, new SingleParentRowMapper());
		logger.debug("List returned :" + results.size());
		if (results.size() == 0) {
			throw new NoDataFoundException();
		}
		
		return results;
	}
}

class SingleParentRowMapper implements RowMapper<SingleParentDetails> {
	@Override
	public SingleParentDetails mapRow(ResultSet rs, int arg1)
			throws SQLException {
		System.out.println("Inside row mapper");
		SingleParentDetails singleParentDetails = new SingleParentDetails();
		singleParentDetails.setInstId(rs.getString("INST_ID"));
		singleParentDetails.setBranchId(rs.getString("BRANCH_ID"));
		singleParentDetails.setAcademicYear(rs.getString("ACADEMIC_YEAR"));
		singleParentDetails.setStudentAdmisNo(rs
				.getString("STUDENT_ADMIS_NO"));
		singleParentDetails.setStandard(rs.getString("STANDARD"));
		singleParentDetails.setSec(rs.getString("SEC"));
		singleParentDetails.setCombination(rs.getString("COMBINATION"));
		singleParentDetails.setStudentName(rs.getString("STUDENT_NAME"));
		singleParentDetails.setFatherName("FATHER_NAME");
		
		System.out.println("Parent request object :" + singleParentDetails);
		return singleParentDetails;
	}
	
}
