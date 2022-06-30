package com.jaw.student.admission.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import com.jaw.common.exceptions.DeleteFailedException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.UpdateFailedException;
import com.jaw.framework.dao.BaseDao;


@Repository
public class StudentDetainedDAO extends BaseDao implements IStudentDetainedDAO {

	Logger logger = Logger.getLogger(StudentDetainedDAO.class);
	
	@Override
	public StudentDetain getStuDetainRec(final StudentDetainKey studentDetainKey) throws NoDataFoundException {
		logger.debug("retrive Communication Details");
		
		/*SELECT DB_TS, INST_ID, BRANCH_ID, AC_TERM, STUDENT_ADMIS_NO, DETAIN_RMKS, DEL_FLG, R_MOD_ID, 
		R_MOD_TIME, R_CRE_ID, R_CRE_TIME FROM STDD WHERE INST_ID='MYS' AND BRANCH_ID='BR001' AND AC_TERM='AT3' 
		AND STUDENT_ADMIS_NO='13-14-101';*/
		
		StringBuffer sql = new StringBuffer().append("SELECT ")
				.append("DB_TS, ").append("INST_ID, ").append("BRANCH_ID, ")
				.append("AC_TERM, ").append("STUDENT_ADMIS_NO, ")
				.append("DETAIN_RMKS ")				
				.append(" FROM stdd ")
				.append("WHERE ").append("INST_ID=?").append(" and ")
				.append("BRANCH_ID=?").append(" and ")
				.append("STUDENT_ADMIS_NO=?").append(" and ")
				.append("AC_TERM=?").append(" and ")
				.append(" DEL_FLG='N'");
		logger.debug("select query :" + sql.toString());
		StudentDetain studentDetain  = null;
		studentDetain = getJdbcTemplate().query(
				sql.toString(), new PreparedStatementSetter() {

					@Override
					public void setValues(PreparedStatement pss)
							throws SQLException {
						pss.setString(1, studentDetainKey.getInstId());
						pss.setString(2, studentDetainKey.getBranchId());
						pss.setString(3, studentDetainKey.getStuAdmisNo());
						pss.setString(4, studentDetainKey.getAcademicYear());					
					}
				}, new ResultSetExtractor<StudentDetain>() {

					@Override
					public StudentDetain extractData(ResultSet rs)
							throws SQLException, DataAccessException {
						StudentDetain studentDetain = null;
						
						if (rs.next()) {
							studentDetain = new StudentDetain();
							studentDetain.setAcademicYear(rs.getString("AC_TERM"));
							studentDetain.setBranchId(rs.getString("BRANCH_ID"));
							studentDetain.setDbTs(rs.getString("DB_TS"));
							studentDetain.setDetainRemarks(rs.getString("DETAIN_RMKS"));
							studentDetain.setInstId(rs.getString("INST_ID"));													
							studentDetain.setStuAdmisNo(rs.getString("STUDENT_ADMIS_NO"));							
						}
						return studentDetain;
					}
				});
		if(studentDetain==null){
			throw new NoDataFoundException();
		}
		return studentDetain;
	}

	@Override
	public void updateStudentDetainedRec(final StudentDetain studentDetain,
			final StudentDetainKey stuDetainKey) throws UpdateFailedException {

		logger.debug("Inside update method");
				
		StringBuffer sql = new StringBuffer();
		sql.append("UPDATE stdd SET").append(" DB_TS=?")
				.append(", DETAIN_RMKS=?").append(", R_MOD_ID=?")
				.append(", R_MOD_TIME=now()").append(" WHERE")
				.append(" INST_ID= ?").append(" and ")
				.append(" BRANCH_ID= ?").append(" and ")
				.append(" STUDENT_ADMIS_NO= ?").append(" and ").append(" AC_TERM= ?")
				.append(" and ").append(" DEL_FLG='N'");
	
			int updateStatus =	getJdbcTemplate().update(sql.toString(),
					new PreparedStatementSetter() {

						@Override
						public void setValues(PreparedStatement ps)
								throws SQLException {

							ps.setInt(1, Integer.valueOf(stuDetainKey.getDbTs()) + 1);
							ps.setString(2,
									studentDetain.getDetainRemarks().trim());
							ps.setString(3, studentDetain.getrModId().trim());							
							ps.setString(4, stuDetainKey.getInstId().trim());
							ps.setString(5, stuDetainKey.getBranchId().trim());
							ps.setString(6, stuDetainKey.getStuAdmisNo()
									.trim());
							ps.setString(7, stuDetainKey.getAcademicYear()
									.trim());							
						}
						

					});
			if (updateStatus == 0) {
				throw new UpdateFailedException();

			}
		logger.debug("update query :" + sql.toString());

	}

	@Override
	public void deleteStudentDetainedRec(final StudentDetainKey stuDetainKey,final StudentDetain studentDetain) throws DeleteFailedException {


		logger.debug("Inside update method");
		
		
		/*UPDATE STDD SET DB_TS='',R_MOD_ID='',R_MOD_TIME='',DEL_FLG='' WHERE INST_ID='MYS' AND 
				BRANCH_ID='BR001' AND AC_TERM='AT3' AND STUDENT_ADMIS_NO='13-14-101';
		*/
		

/*DELETE FROM stdd
WHERE INST_ID='ASC' AND BRANCH_ID='BR001' AND AC_TERM='AT3' AND STUDENT_ADMIS_NO='001/12-13' ;*/
				
		StringBuffer sql = new StringBuffer();
		sql.append("DELETE FROM stdd")
				.append(" WHERE")
				.append(" INST_ID= ?").append(" AND ")
				.append(" BRANCH_ID= ?").append(" AND ")
				.append(" AC_TERM= ?").append(" AND STUDENT_ADMIS_NO= ?");
	
			int updateStatus =	getJdbcTemplate().update(sql.toString(),
					new PreparedStatementSetter() {

						@Override
						public void setValues(PreparedStatement ps)
								throws SQLException {									
							ps.setString(1,stuDetainKey.getInstId().trim());						
							ps.setString(2,	stuDetainKey.getBranchId().trim());
							ps.setString(3, stuDetainKey.getAcademicYear().trim());						
							ps.setString(4, stuDetainKey.getStuAdmisNo()
									.trim());
											
						}
						

					});
			if (updateStatus == 0) {
				throw new DeleteFailedException();

			}
		logger.debug("update query :" + sql.toString());

	

	}

}
