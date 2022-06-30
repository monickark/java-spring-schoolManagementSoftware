package com.jaw.mark.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.UpdateFailedException;
import com.jaw.framework.dao.BaseDao;

//Institute Master DAO 
@Repository
public class StudentExamRemarksDAO extends BaseDao implements IStudentExamRemarksDAO {
	
	// Logging
	Logger logger = Logger.getLogger(StudentExamRemarksDAO.class);
	
	@Override
	public StudentExamRemark selectStudentExamRemark(final StudentExamRemarkKey studentExamRemarkKey)
			throws NoDataFoundException {
		
		System.out.println("Key Object :" + studentExamRemarkKey.toString());
		
		StringBuffer sql = new StringBuffer();
		sql.append("select ")
				.append("DB_TS,")
				.append("INST_ID,")
				.append("BRANCH_ID,")
				.append("AC_TERM,")
				.append("EXAM_TYPE_ID,")
				.append("STUDENT_ADMIS_NO,")
				.append("REMARKS,")
				.append("DEL_FLG,")
				.append("R_MOD_ID,")
				.append("R_MOD_TIME,")
				.append("R_CRE_ID,")
				.append("R_CRE_TIME from ster ")
				.append("where ")
				.append("INST_ID=? and ")
				.append("BRANCH_ID=?  and ")
				.append("AC_TERM=? and ")
				.append("EXAM_TYPE_ID=? and ")
				.append("STUDENT_ADMIS_NO=? ");
		System.out.println("select query :" + sql.toString());
		
		StudentExamRemark staff = null;
		
		staff = getJdbcTemplate().query(sql.toString(),
				new PreparedStatementSetter() {
					
					@Override
					public void setValues(PreparedStatement pss)
							throws SQLException {
						pss.setString(1, studentExamRemarkKey.getInstId());
						pss.setString(2, studentExamRemarkKey.getBranchId());
						pss.setString(3, studentExamRemarkKey.getAcTerm());
						pss.setString(4, studentExamRemarkKey.getExamId());
						pss.setString(5, studentExamRemarkKey.getStudentAdmisNo());
						
					}
					
				}, new ResultSetExtractor<StudentExamRemark>() {
					
					@Override
					public StudentExamRemark extractData(ResultSet rs)
							throws SQLException, DataAccessException {
						StudentExamRemark studentExamRemark = null;
						if (rs.next()) {
							studentExamRemark = new StudentExamRemark();
							studentExamRemark.setDbTs(rs.getInt("DB_TS"));
							studentExamRemark.setInstId(rs.getString("INST_ID"));
							studentExamRemark.setBranchId(rs.getString("BRANCH_ID"));
							studentExamRemark.setAcTerm(rs.getString("AC_TERM"));
							studentExamRemark.setExamTypeId(rs.getString("EXAM_TYPE_ID"));
							studentExamRemark.setStudentAdmisNo(rs
									.getString("STUDENT_ADMIS_NO"));
							studentExamRemark.setRemarks(rs.getString("REMARKS"));
							studentExamRemark.setDelFlg(rs.getString("DEL_FLG"));
							studentExamRemark.setrModId(rs.getString("R_MOD_ID"));
							studentExamRemark.setrModTime(rs.getString("R_MOD_TIME"));
							studentExamRemark.setrCreId(rs.getString("R_CRE_ID"));
							studentExamRemark.setrCreTime(rs.getString("R_CRE_TIME"));
						}
						return studentExamRemark;
					}
					
				});
		if (staff == null) {
			throw new NoDataFoundException();
		}
		
		return staff;
	}
	
	@Override
	public void insertStudentExamRemark(final StudentExamRemark studentExamRemark)
			throws DuplicateEntryException {
		System.out.println("Srudennt remark obj :"+studentExamRemark.toString());
		System.out.println("Inside insert method");
		
		StringBuffer query = new StringBuffer();
		query.append("insert into ster  (")
				
				.append("DB_TS,")
				.append("INST_ID,")
				.append("BRANCH_ID,")
				
				.append("AC_TERM,")
				.append("EXAM_TYPE_ID,")
				.append("STUDENT_ADMIS_NO,")
				
				.append("REMARKS,")
				.append("DEL_FLG,")
				.append("R_MOD_ID,")
				
				.append("R_MOD_TIME,")
				.append("R_CRE_ID,")
				.append("R_CRE_TIME )")
				
				.append(" values (?,?,?, ?,?,?, ?,?,?, now(),?, now())");
		System.out.println("insert query :" + query.toString());
		
		try {
			getJdbcTemplate().update(query.toString(),
					new PreparedStatementSetter() {
						@Override
						public void setValues(PreparedStatement ps)
								throws SQLException {
							
							ps.setInt(1, studentExamRemark.getDbTs());
							ps.setString(2, studentExamRemark.getInstId().trim());
							ps.setString(3, studentExamRemark.getBranchId().trim());
							ps.setString(4, studentExamRemark.getAcTerm().trim());
							ps.setString(5, studentExamRemark.getExamTypeId().trim()
									.toUpperCase());
							ps.setString(6, studentExamRemark.getStudentAdmisNo()
									.trim());
							ps.setString(7, studentExamRemark.getRemarks().trim());
							ps.setString(8, studentExamRemark.getDelFlg().trim());
							ps.setString(9, studentExamRemark.getrModId().trim());
							ps.setString(10, studentExamRemark.getrCreId().trim());
						}
						
					});
		}
		catch (org.springframework.dao.DuplicateKeyException e) {
			throw new DuplicateEntryException();
		}
		
	}
	
	@Override
	public void updateStudentExamRemark(final StudentExamRemark studentExamRemark,
			final StudentExamRemarkKey studentExamRemarkKey)
			throws UpdateFailedException {
		
		System.out.println("Inside insert method");
		System.out.println("Key Object :" + studentExamRemarkKey.toString());
		
		StringBuffer query = new StringBuffer();
		query.append("update ster  set ")
				
				.append("DB_TS=?,")
				.append("INST_ID=?,")
				.append("BRANCH_ID=?,")
				.append("AC_TERM=?,")
				.append("EXAM_TYPE_ID=?,")
				.append("STUDENT_ADMIS_NO=?,")
				.append("REMARKS=?,")
				.append("DEL_FLG=?,")
				.append("R_MOD_ID=?,")
				.append("R_MOD_TIME=now() ")
				.append("where ")
				.append("INST_ID=? and ")
				.append("BRANCH_ID=?  and ")
				.append("AC_TERM=? and ")
				.append("EXAM_TYPE_ID=? and ")
				.append("STUDENT_ADMIS_NO=? and ")
				.append("DB_TS=?  ");
		
		System.out.println("insert query :" + query.toString());
		
		int affectedRows = getJdbcTemplate().update(query.toString(),
				new PreparedStatementSetter() {
					@Override
					public void setValues(PreparedStatement ps)
							throws SQLException {
						
						ps.setInt(1, studentExamRemark.getDbTs() + 1);
						ps.setString(2, studentExamRemark.getInstId().trim());
						ps.setString(3, studentExamRemark.getBranchId().trim());
						ps.setString(4, studentExamRemark.getAcTerm().trim());
						ps.setString(5, studentExamRemark.getExamTypeId().trim()
								.toUpperCase());
						ps.setString(6, studentExamRemark.getStudentAdmisNo()
								.trim());
						ps.setString(7, studentExamRemark.getRemarks().trim());
						ps.setString(8, studentExamRemark.getDelFlg().trim());
						ps.setString(9, studentExamRemark.getrModId().trim());
						ps.setString(10, studentExamRemarkKey.getInstId());
						ps.setString(11, studentExamRemarkKey.getBranchId());
						ps.setString(12, studentExamRemarkKey.getAcTerm());
						ps.setString(13, studentExamRemarkKey.getExamId());
						ps.setString(14, studentExamRemarkKey.getStudentAdmisNo());
						ps.setInt(15, studentExamRemark.getDbTs());
					}
					
				});
		
		if (affectedRows == 0) {
			throw new UpdateFailedException();
		}
	}
}
