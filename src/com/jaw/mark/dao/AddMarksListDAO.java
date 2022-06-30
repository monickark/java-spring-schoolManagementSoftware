package com.jaw.mark.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import org.springframework.stereotype.Repository;

import com.jaw.common.constants.ApplicationConstant;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.batch.StudentNotFoundForMarkException;
import com.jaw.framework.dao.BaseDao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;

@Repository
public class AddMarksListDAO extends BaseDao implements IAddMarksListDAO {
	// Logging
	Logger logger = Logger.getLogger(AddMarksListDAO.class);
	@Override
	public List<MarkSubjectLinkListForAddMarks> getMarkSubjectLinkListForAddMarks(
			final MarkSubjectLinkKey markSubjectLinkKey,String staffId)
			throws NoDataFoundException {
		List<Object> data = new ArrayList<Object>();
		logger.debug("Inside select method");
		logger.debug(" where class for getMarkSubjectLinkList For Add Marks" + markSubjectLinkKey);
		System.out.println("query string          ff:"+staffId);
		StringBuffer sql = new StringBuffer();
		sql.append(
				"select distinct(mksl_seq_id),mksl.crsl_id, exam_type,exam_id,crsl.sub_id,sub_type,SUB_NAME, sub_test_id, mksl.lab_batch, exam_date," +
				" min_mark, max_mark,REMARKS,SUB_PORTION_DTLS,mark_grade,status ")
				.append(" from  mksl,crsl,sbjm ");
				if (!staffId.trim().equals("")){
					sql.append(",crcl ");
				}
				sql.append("where ") 
				.append(" mksl.inst_id=crsl.inst_id")
				.append(" and mksl.branch_id=crsl.branch_id")
				.append(" and mksl.del_flg=crsl.del_flg")
				.append(" and sbjm.inst_id=crsl.inst_id")
				.append(" and sbjm.branch_id=crsl.branch_id")
				.append(" and sbjm.del_flg=crsl.del_flg")
				.append(" and  crsl.SUB_ID = sbjm.SUB_ID")				
				.append(" and mksl.crsl_id=crsl.crsl_id ");
				
				if (!staffId.trim().equals("")){
					sql.append(" and crsl.INST_ID = crcl.INST_ID ")
					.append(" and crsl.BRANCH_ID = crcl.BRANCH_ID ")
					.append(" and crsl.DEL_FLG = crcl.DEL_FLG ")
					.append(" and crsl.CRSL_ID = crcl.CRSL_ID ")
					.append(" and mksl.AC_TERM = crcl.AC_TERM ")
					.append(" and crcl.staff_id='"+staffId.trim()+"' ");
				
				}
				
				sql.append(" and mksl.inst_id = ?  ")
				
				.append(" and mksl.branch_id = ?  ");
				
		 data.add(markSubjectLinkKey.getInstId());
				 data.add(markSubjectLinkKey.getBranchId());
				if ((markSubjectLinkKey.getStudentGrpId()!=null)&&(markSubjectLinkKey.getStudentGrpId()!="")) {
			        sql.append(" and  mksl.studentgrp_id =?  ");
			        logger.debug("Student Group Value :" + markSubjectLinkKey.getStudentGrpId());
			        data.add(markSubjectLinkKey.getStudentGrpId());
		        }
				
				if ((markSubjectLinkKey.getAcTerm()!=null)&&(markSubjectLinkKey.getAcTerm()!="")) {
			        sql.append(" and mksl.AC_TERM =?  ");
			        logger.debug("AC Term Value :" + markSubjectLinkKey.getAcTerm());
			        data.add(markSubjectLinkKey.getAcTerm());
		        }
				
				if ((markSubjectLinkKey.getExamId()!=null)&&(markSubjectLinkKey.getExamId()!="")) {
			        sql.append(" and mksl.EXAM_ID=?  ");
			        logger.debug("Exam Value :" + markSubjectLinkKey.getExamId());
			        data.add(markSubjectLinkKey.getExamId());
		        }
				
				if ((markSubjectLinkKey.getCrslId()!=null)&&(markSubjectLinkKey.getCrslId()!="")&&(!markSubjectLinkKey.getCrslId().isEmpty())) {
			        sql.append(" and mksl.crsl_id =?  ");
			        logger.debug("crsl Value :" + markSubjectLinkKey.getCrslId());
			        data.add(markSubjectLinkKey.getCrslId());
		        }
				
				
				
				
				
				sql.append(" and mksl.del_flg = 'N'   ")
				
				
				.append("order by mksl.crsl_id; ");
				System.out.println("query string          :"+sql.toString());
				Object[] array = data.toArray(new Object[data.size()]);
		List<MarkSubjectLinkListForAddMarks> result = getJdbcTemplate()
				.query(sql.toString(),array, new MarkSubjectLinkListForAddmarksRowMapper());
		if (result.size() == 0) {
			throw new NoDataFoundException();
		}
		return result;
	}
	@Override
	public List<StudentListForAddMarks> getStudentListForAddMarks(
			AddMarksListKey addMarksListKey,String add) throws  StudentNotFoundForMarkException {
		List<Object> data = new ArrayList<Object>();
		logger.debug("Inside select method");
		
		logger.debug(" where class for get Student List For Add Marks" + addMarksListKey);
		System.out.println("lab batch  :"+addMarksListKey.getLabBatch()+"value");
		StringBuffer sql = new StringBuffer();
		
		
		
		
		
		sql.append("select stum.roll_number,")
		.append("stum.student_admis_no,")
		.append("stum.REG_NO,")
		.append("stum.student_name,")
		.append("stmk.marks_obtd,")
		.append("stmk.grade_obtd,")
		.append("stmk.sub_remarks,")
		.append("stmk.attendance ")
				.append(" from ")	;
				if((addMarksListKey.getSubType()!=null)&&(!addMarksListKey.getSubType().equals(ApplicationConstant.SUB_TYPE_COMPULSARY))){
					sql.append("crsl,");
				}
				sql.append(" stum ")
		        .append("left join stmk on ") 
				.append(" stum.inst_id = stmk.inst_id")
				.append("  and stum.branch_id = stmk.branch_id")
				.append(" and stum.student_admis_no = stmk.student_admis_no")
				.append(" and stum.del_flg = stmk.del_flg")
				.append(" and mksl_seq_id = ?")
				/*.append(" and stmk.marks_obtd is null")
				.append(" and stmk.grade_obtd is null")*/
				.append(" where ")				
				.append("  stum.inst_id = ?  ");
				if(add.equals("ADD")){
					sql.append(" and stmk.attendance is null");
				}else{
					sql.append(" and stmk.attendance is not null");
				}
				
				sql.append(" and stum.branch_id = ?  ")
		.append(" and stum.del_flg = 'N'  ")
		.append(" and stum.academic_year = ? ")
		.append(" and stum.STUDENTGRP_ID = ?   ");
				if ((addMarksListKey.getLabBatch() != null)&&(!addMarksListKey.getLabBatch().trim().equals(""))
						&& (!addMarksListKey.getLabBatch().equals("NA"))) {
					sql.append("and LAB_BATCH='"+addMarksListKey.getLabBatch()+"'");
				}
				
						
						
				if((addMarksListKey.getSubType()!=null)&&(!addMarksListKey.getSubType().equals(ApplicationConstant.SUB_TYPE_COMPULSARY))){
					sql.append(" and crsl.crsl_Id='"+addMarksListKey.getCrslId()+"'")
					.append(" and stum.inst_id = crsl.inst_id")
					.append(" and stum.branch_id = crsl.branch_id")
					.append(" and stum.del_flg = crsl.del_flg");
				}
				
						  
						   
		if((addMarksListKey.getSubType()!=null)&&( addMarksListKey.getSubType().equals(ApplicationConstant.SUB_TYPE_LANGUAGE2))){
			   sql.append(" and stum.second_lang = crsl.sub_id") ;
		   }
		   if((addMarksListKey.getSubType()!=null)&&( addMarksListKey.getSubType().equals(ApplicationConstant.SUB_TYPE_LANGUAGE3))){
			   sql.append(" and stum.third_lang = crsl.sub_id") ;
		   }
		   
			 if((addMarksListKey.getSubType()!=null)&&(addMarksListKey.getSubType().equals(ApplicationConstant.SUB_TYPE_ELECTIVE1))){
				 sql.append(" and stum.elective_1 = crsl.sub_id");
			 }
			 if((addMarksListKey.getSubType()!=null)&&(addMarksListKey.getSubType().equals(ApplicationConstant.SUB_TYPE_ELECTIVE2))){
				 sql.append(" and stum.elective_2 = crsl.sub_id");
			 }
		sql.append(" order by roll_number ");
		data.add(addMarksListKey.getMkslId());
		data.add(addMarksListKey.getInstId());
		data.add(addMarksListKey.getBranchId());
		data.add(addMarksListKey.getAcTerm());
		data.add(addMarksListKey.getStudentGrpId());
		
				System.out.println("sqlllllllllllllll"+sql.toString());
				for(int i=0;i<data.size();i++){
					System.out.println("sqlllllllllllllll"+data.get(i));
				}
				
				Object[] array = data.toArray(new Object[data.size()]);
				System.out.println("sqlllllllllllllll"+data);
		List<StudentListForAddMarks> result = getJdbcTemplate()
				.query(sql.toString(),array, new StudentListForAddmarksRowMapper());
		if (result.size() == 0) {
			throw new StudentNotFoundForMarkException();
		}
		return result;
	}
	@Override
	public void insertMarkListRecs(final List<StudentMarks> studentMarksList)
			throws DuplicateEntryException {
		logger.debug("Inside Mark Details Insert Record");
		logger.debug(" List Size   "+studentMarksList.size());
		StringBuffer query = new StringBuffer();	
		query = query.append("insert into stmk ( ")
				.append("DB_TS,")
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
				.append("R_CRE_TIME)")
				.append(" values (?,?,?,?,?,?,?,?,?,?,?,now(),?,now())");
		try {
		int[] a = getJdbcTemplate().batchUpdate(query.toString(),
				new BatchPreparedStatementSetter() {

					@Override
					public void setValues(PreparedStatement pss, int i)
							throws SQLException {

						StudentMarks studentMark = studentMarksList
								.get(i);

						pss.setInt(1, 1);
						pss.setString(2, studentMark.getInstId());
						pss.setString(3, studentMark.getBranchId());
						pss.setString(4, studentMark.getMkslSeqId().trim());
						pss.setString(5, studentMark.getStudentAdmisNo().trim());
						pss.setInt(6, studentMark.getMarksObtd());
						pss.setString(7, studentMark.getGradeObtd().trim());
						pss.setString(8, studentMark.getSubRemarks().trim());
						pss.setString(9, studentMark.getAttendance());						
						pss.setString(10, "N");
						pss.setString(11, studentMark.getrModId().trim());
						pss.setString(12, studentMark.getrCreId().trim());
					}

					@Override
					public int getBatchSize() {
						return studentMarksList.size();
					}
				}

		);
		} catch (org.springframework.dao.DuplicateKeyException e) {
			logger.error("Duplicate Entry Exception Occured  "+e.getMessage());
			throw new DuplicateEntryException();
		}
		
	}
	
}

class MarkSubjectLinkListForAddmarksRowMapper implements RowMapper<MarkSubjectLinkListForAddMarks> {
	
	@Override
	public MarkSubjectLinkListForAddMarks mapRow(ResultSet rs, int arg1) throws SQLException {
		
		MarkSubjectLinkListForAddMarks markSubjectLinkListForAdd = new MarkSubjectLinkListForAddMarks();
		markSubjectLinkListForAdd.setStatus(rs.getString("status"));
		markSubjectLinkListForAdd.setMarkGrade(rs.getString("mark_grade"));
		markSubjectLinkListForAdd.setMkslId(rs.getString("mksl_seq_id"));
		markSubjectLinkListForAdd.setCrslId(rs.getString("crsl_id"));
		markSubjectLinkListForAdd.setExamType(rs.getString("EXAM_TYPE"));
		markSubjectLinkListForAdd.setSubTestId(rs.getString("SUB_TEST_ID"));
		markSubjectLinkListForAdd.setLabBatch(rs.getString("LAB_BATCH"));
		markSubjectLinkListForAdd.setExamDate(rs.getString("EXAM_DATE"));
		markSubjectLinkListForAdd.setMinMark(rs.getString("MIN_MARK"));
		markSubjectLinkListForAdd.setMaxMark(rs.getString("MAX_MARK"));
		markSubjectLinkListForAdd.setRemarks(rs.getString("REMARKS"));
		markSubjectLinkListForAdd.setSubPortionDetails(rs.getString("SUB_PORTION_DTLS"));
		markSubjectLinkListForAdd.setExamId(rs.getString("exam_id"));
		markSubjectLinkListForAdd.setSubject(rs.getString("SUB_NAME"));
		markSubjectLinkListForAdd.setSubjectType(rs.getString("SUB_TYPE"));
		
		
		
		
		return markSubjectLinkListForAdd;
	}
}


class StudentListForAddmarksRowMapper implements RowMapper<StudentListForAddMarks> {
	
	@Override
	public StudentListForAddMarks mapRow(ResultSet rs, int arg1) throws SQLException {
		StudentListForAddMarks addMarks = new StudentListForAddMarks();
		addMarks.setRollNumber(rs.getString("ROLL_NUMBER"));
		addMarks.setStudentAdmisNo(rs.getString("STUDENT_ADMIS_NO"));
		addMarks.setStudentName(rs.getString("STUDENT_NAME"));
		addMarks.setMarksObtd(rs.getString("MARKS_OBTD"));
		addMarks.setGradeObtd(rs.getString("GRADE_OBTD"));
		addMarks.setSubRemarks(rs.getString("SUB_REMARKS"));
		addMarks.setAttendance(rs.getString("ATTENDANCE"));
		addMarks.setRegNo(rs.getString("REG_NO"));
		
		System.out.println("mark obtaineddddddddddddddd"+rs.getString("MARKS_OBTD"));
		
		
		return addMarks;
	}
}
