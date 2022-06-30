package com.jaw.mark.dao;

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

import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.UpdateFailedException;
import com.jaw.core.dao.CourseMaster;
import com.jaw.core.dao.CourseMasterDAO;
import com.jaw.core.dao.CourseMasterKey;
import com.jaw.core.dao.ICourseMasterDAO;
import com.jaw.framework.dao.BaseDao;
@Repository
public class StudentMarkDAO extends BaseDao implements IStudentMarkDAO{
	// Logging
	Logger logger = Logger.getLogger(StudentMarkDAO.class);
	@Override
	public void updateStudentMarksRec(final StudentMarks studentMarks,
			final StudentMarksKey studentMarksKey) throws UpdateFailedException {
		logger.debug("Inside update method");
		logger.debug("Student Marks object values :"+ studentMarks.toString());
		logger.debug("Student Marks key Details object values :"+ studentMarksKey.toString());		
		StringBuffer sql = new StringBuffer();	
		
			sql.append("update stmk set")
		        .append(" DB_TS= ?,")				
				.append("MARKS_OBTD= ?,")
				.append("GRADE_OBTD= ?,")
				.append("SUB_REMARKS= ?,")
				.append("ATTENDANCE= ?,")	
				.append("DEL_FLG= 'N',")
				.append("R_MOD_ID= ?,")
				.append(" R_MOD_TIME=now()")
				.append(" where")
				.append(" DB_TS= ?")
				.append(" and  INST_ID= ?")
				.append(" and  BRANCH_ID= ?")
				.append(" and  MKSL_SEQ_ID= ?")	
				.append(" and  STUDENT_ADMIS_NO= ?")
				.append(" and  DEL_FLG='N'");

		

		int updateRecs = getJdbcTemplate().update(sql.toString(),
				new PreparedStatementSetter() {

					@Override
					public void setValues(PreparedStatement ps)
							throws SQLException {
						ps.setInt(1, studentMarks.getDbTs() + 1);
						ps.setInt(2, studentMarks.getMarksObtd());
						ps.setString(3, studentMarks.getGradeObtd().trim());
						ps.setString(4, studentMarks.getSubRemarks().trim());
						ps.setString(5, studentMarks.getAttendance().trim());
						ps.setString(6, studentMarks.getrModId().trim());	
						
						ps.setInt(7, studentMarksKey.getDbTs());
						ps.setString(8, studentMarksKey.getInstId().trim());
						ps.setString(9, studentMarksKey.getBranchId().trim());
						ps.setString(10, studentMarksKey.getMkslSeqId().trim());
						ps.setString(11, studentMarksKey.getStudentAdmisNo().trim());
						

					}

				});
		if (updateRecs == 0) {
			logger.error("Update failed exception occured");
			throw new UpdateFailedException();

		}
		
		
	}
	
	public StudentMarks selectStudentMarksRec(final StudentMarksKey studentMarksKey)
			throws NoDataFoundException {
		logger.debug("Inside select method");
		logger.debug("Student Marks Key object values :"+ studentMarksKey.toString());
		List<Object> data = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();	
		    sql.append("select ")
		        .append(" DB_TS,")
				.append("INST_ID,")
				.append("BRANCH_ID,")
				.append("MKSL_SEQ_ID,")
				.append("STUDENT_ADMIS_NO,")
				.append("MARKS_OBTD,")
				.append("GRADE_OBTD,")
				.append("SUB_REMARKS,")				
				.append("ATTENDANCE,")
				.append("DEL_FLG,")
				.append("R_MOD_ID,")
				.append("R_MOD_TIME,")
				.append("R_CRE_ID,")
				.append("R_CRE_TIME")
				.append(" from stmk ")
				.append(" where")
				.append("   INST_ID= ?")
				.append(" and  BRANCH_ID= ?")
				.append(" and  MKSL_SEQ_ID= ?")
				.append(" and  STUDENT_ADMIS_NO= ?")
				.append(" and  DEL_FLG=?");	
		    data.add(studentMarksKey.getInstId());
			data.add(studentMarksKey.getBranchId());
			data.add(studentMarksKey.getMkslSeqId());
			data.add(studentMarksKey.getStudentAdmisNo());
			data.add("N");
			if ((studentMarksKey.getDbTs() !=null)&&(studentMarksKey.getDbTs() !=0)) {
				sql.append(" and DB_TS=?  ");
				logger.debug("Db Ts Value :" + studentMarksKey.getDbTs());
				data.add(studentMarksKey.getDbTs());
			}
			  Object[] array = data.toArray(new Object[data.size()]);
			  StudentMarks selectedStudentMarks = null;
		    selectedStudentMarks = (StudentMarks) getJdbcTemplate()
				.query(sql.toString(), array, new ResultSetExtractor<StudentMarks>() {

					@Override
					public StudentMarks extractData(ResultSet rs)
							throws SQLException, DataAccessException {
						StudentMarks studentMarks = null;
						if (rs.next()) {						
							studentMarks = new StudentMarks();
							studentMarks.setDbTs(rs.getInt("DB_TS"));
							studentMarks.setInstId(rs.getString("INST_ID"));
							studentMarks.setBranchId(rs.getString("BRANCH_ID"));
							studentMarks.setMkslSeqId(rs.getString("MKSL_SEQ_ID"));
							studentMarks.setStudentAdmisNo(rs.getString("STUDENT_ADMIS_NO"));
							studentMarks.setMarksObtd(rs.getInt("MARKS_OBTD"));
							studentMarks.setGradeObtd(rs.getString("GRADE_OBTD"));
							studentMarks.setSubRemarks(rs.getString("SUB_REMARKS"));							
							studentMarks.setAttendance(rs.getString("ATTENDANCE"));
							studentMarks.setDelFlag(rs.getString("DEL_FLG"));
							studentMarks.setrModId(rs.getString("R_MOD_ID"));
							studentMarks.setrModTime(rs.getString("R_MOD_TIME"));
							studentMarks.setrCreId(rs.getString("R_CRE_ID"));
							studentMarks.setrCreTime(rs.getString("R_CRE_TIME"));
						}
						return studentMarks;
					}

				});

		if (selectedStudentMarks == null) {
			logger.error("No data found exception occured");
			throw new NoDataFoundException();
		}

		return selectedStudentMarks;
	}


}
