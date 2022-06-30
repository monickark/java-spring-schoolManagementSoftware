package com.jaw.attendance.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.jaw.common.constants.ApplicationConstant;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.core.dao.AcademicTermDetails;
import com.jaw.framework.dao.BaseDao;
import com.jaw.framework.sessCache.UserSessionDetails;

@Repository
public class StudentAttendanceListDAO extends BaseDao implements IStudentAttendanceListDAO{
	// Logging
	Logger logger = Logger.getLogger(StudentAttendanceListDAO.class);

	@Override
	public List<StudentAttendanceList> getStudentsForAttendance(
			StudentAttendanceListKey studentAttendanceListKey) 
		throws NoDataFoundException {
			logger.debug("DAO :Inside Get Student List For attendance select method");
			logger.debug("DAO :StudentAttendance List Key"+studentAttendanceListKey.toString());
			List<Object> data = new ArrayList<Object>();
			StringBuffer sql = new StringBuffer();
			
			
		if((studentAttendanceListKey.getSubType()==null)||(studentAttendanceListKey.getSubType()=="")||(studentAttendanceListKey.getSubType().equals(ApplicationConstant.SUB_TYPE_COMPULSARY))
				){
			//C
			   sql.append("select ")
			       .append("roll_number, REG_NO,student_admis_no, student_name")
			       .append(" from stum ")
			       .append("where ")			      
			    .append("  inst_id=?")
			    .append(" and branch_id = ?")
			    .append(" and academic_year = ?")
			    .append(" and studentgrp_id = ?")
			    .append(" and stum.del_flg = ?");
			     data.add(studentAttendanceListKey.getInstId());
			    data.add(studentAttendanceListKey.getBranchId());
			    data.add(studentAttendanceListKey.getAcTerm());
			    data.add(studentAttendanceListKey.getStudentGroupId());
				data.add("N");
				if((studentAttendanceListKey.getClassType()!=null)&&(studentAttendanceListKey.getClassType()!="")&&(studentAttendanceListKey.getClassType().equals(ApplicationConstant.CLASS_TYPE_PRACTICAL))){
					System.out.println("batch addddddddd");
					sql.append(" and LAB_BATCH=?");
					data.add(studentAttendanceListKey.getLabBatch());
				}
				if((studentAttendanceListKey.getStudentBatch()!=null)&&(studentAttendanceListKey.getStudentBatch()!="")){
					System.out.println("Student batch addddddddd");
					sql.append(" and stum.STUDENT_BATCH=?");
					data.add(studentAttendanceListKey.getStudentBatch());
				}
			    sql.append(" order by roll_number");
			   
		}
				//Other
				
		else{
				 sql.append("select ")
			       .append("roll_number,REG_NO, student_admis_no, student_name")
			       .append(" from stum, crsl ")
			       .append("where ")
			       .append(" stum.inst_id = crsl.inst_id")
			        .append(" and stum.branch_id = crsl.branch_id")
			    .append(" and stum.del_flg = crsl.del_flg")
			    
			    .append(" and stum.inst_id = crsl.inst_id")
			    .append(" and stum.branch_id = crsl.branch_id");
			    
			   if( studentAttendanceListKey.getSubType().equals(ApplicationConstant.SUB_TYPE_LANGUAGE2)){
				   sql.append(" and stum.second_lang = crsl.sub_id") ;
			   }
			   if( studentAttendanceListKey.getSubType().equals(ApplicationConstant.SUB_TYPE_LANGUAGE3)){
				   sql.append(" and stum.third_lang = crsl.sub_id") ;
			   }
			   
				 if(studentAttendanceListKey.getSubType().equals(ApplicationConstant.SUB_TYPE_ELECTIVE1)){
					 sql.append(" and stum.elective_1 = crsl.sub_id");
				 }
				 if(studentAttendanceListKey.getSubType().equals(ApplicationConstant.SUB_TYPE_ELECTIVE2)){
					 sql.append(" and stum.elective_2 = crsl.sub_id");
				 }
			    
				 sql.append(" and stum.inst_id=?")
			    .append(" and stum.branch_id = ?")
			    .append(" and academic_year = ?")
			    .append(" and studentgrp_id = ?")
			    .append(" and stum.del_flg = 'N'")
			    .append(" and crsl.crsl_id = ?");
				
			    data.add(studentAttendanceListKey.getInstId());
			    data.add(studentAttendanceListKey.getBranchId());
			    data.add(studentAttendanceListKey.getAcTerm());
			    data.add(studentAttendanceListKey.getStudentGroupId());
				data.add(studentAttendanceListKey.getCrslId());
				
				if(studentAttendanceListKey.getClassType().equals(ApplicationConstant.CLASS_TYPE_PRACTICAL)){
					sql.append(" and LAB_BATCH=?");
					data.add(studentAttendanceListKey.getLabBatch());
				}
				if((studentAttendanceListKey.getStudentBatch()!=null)&&(studentAttendanceListKey.getStudentBatch()!="")){
					System.out.println("Student batch addddddddd");
					sql.append(" and stum.STUDENT_BATCH=?");
					data.add(studentAttendanceListKey.getStudentBatch());
				}
				sql .append(" order by roll_number");
		}
		
				System.out.println("queryyyyyyyyyyyy"+sql.toString());
			
			Object[] array = data.toArray(new Object[data.size()]);
			logger.debug("Where clause conditions size :" + array.length);

			List<StudentAttendanceList> selectedStudentList = getJdbcTemplate()
					.query(sql.toString(), array, new CollegeAttendanceListSelectRowMapper());
			if (selectedStudentList.size() == 0) {
				logger.error("No  record found");
				throw new NoDataFoundException();
			}
			logger.debug("DAO : AcademicCalendar List value"+selectedStudentList.size() );
			return selectedStudentList;
			}

	
	
	//Mark Attendance
	@Override
	public void insertAttendanceRecs(
			final List<StudentAttendance> studentAttendanceList)
			throws  DuplicateEntryException {
		logger.debug("Inside Holiday List Insert Record");
		logger.debug("Holiday List Size   "+studentAttendanceList.size());
		StringBuffer query = new StringBuffer();		
		query = query.append("insert into stat ( ")
				.append("DB_TS,")
				.append("INST_ID,")
				.append("BRANCH_ID,")	
				.append("STAT_SEQ_NO,")
				.append("AC_TERM,")
				.append("ATT_DATE,")
				.append("STUDENT_ADMIS_NO,")
				.append("CRSL_ID,")
				.append("NO_OF_SESSIONS,")
				.append("CLS_TYPE,")
				.append("IS_PRESENT,")
				.append("ABSENTEE_RMKS,")
				.append("DEL_FLG,")
				.append("R_MOD_ID,")
				.append("R_MOD_TIME,")
				.append("R_CRE_ID,")
				.append("R_CRE_TIME)")
				.append(" values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,now(),?,now())");
		try {
		int[] a = getJdbcTemplate().batchUpdate(query.toString(),
				new BatchPreparedStatementSetter() {

					@Override
					public void setValues(PreparedStatement pss, int i)
							throws SQLException {

						StudentAttendance studentAttendance = studentAttendanceList
								.get(i);

						pss.setInt(1, 1);
						pss.setString(2, studentAttendance.getInstId());
						pss.setString(3, studentAttendance.getBranchId());
						pss.setString(4, studentAttendance.getSattSeqNo().trim());
						pss.setString(5, studentAttendance.getAcTerm().trim());
						pss.setString(6, studentAttendance.getAttDate().trim());
						pss.setString(7, studentAttendance.getStudentAdmisNo().trim());
						pss.setString(8, studentAttendance.getCrslId().trim());
						pss.setInt(9, studentAttendance.getNoOfSessions());
						pss.setString(10, studentAttendance.getClsType().trim());
						pss.setString(11, studentAttendance.getIsPresent().trim());
						pss.setString(12, studentAttendance.getAbsenteeRmks().trim());
						pss.setString(13, "N");
						pss.setString(14, studentAttendance.getrModId().trim());
						pss.setString(15, studentAttendance.getrCreId().trim());
					}

					@Override
					public int getBatchSize() {
						return studentAttendanceList.size();
					}
				}

		);
		} catch (org.springframework.dao.DuplicateKeyException e) {
			logger.error("Duplicate Entry Exception Occured  "+e.getMessage());
			throw new DuplicateEntryException();
		}
		
	}

	
	
			}

			class CollegeAttendanceListSelectRowMapper implements RowMapper<StudentAttendanceList> {

			@Override
			public StudentAttendanceList mapRow(ResultSet rs, int arg1) throws SQLException {
				StudentAttendanceList studentAttendanceList = new StudentAttendanceList();
				studentAttendanceList.setRollNumber(rs.getString("roll_number"));
				studentAttendanceList.setStudentAdmisNo(rs.getString("student_admis_no"));
				studentAttendanceList.setStudentName(rs.getString("student_name"));
				studentAttendanceList.setRegNo(rs.getString("REG_NO"));
			return studentAttendanceList;
			}
	}

