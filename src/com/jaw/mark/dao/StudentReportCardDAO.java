package com.jaw.mark.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.jaw.common.constants.CommonCodeConstant;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.framework.dao.BaseDao;
import com.jaw.student.admission.dao.StudentMaster;
import com.jaw.student.admission.dao.StudentMasterKey;
import com.jaw.analytics.controller.ViewMarkList;
//MarkSubjectLinkDAO DAO class
@Repository
public class StudentReportCardDAO extends BaseDao implements
		IStudentReportCardDAO {
	// Logging
	Logger logger = Logger.getLogger(StuMrksRmksListDAO.class);
@Override
	public List<StudentReportCard> getAbsentListInExamForRC(
			final StuMrksRmksListKey stuMrksRmksListKey)
			throws NoDataFoundException {
		logger.debug("Inside select method");
		logger.debug(" where class for getMarkSubjectLinkList"
				+ stuMrksRmksListKey.toString());

		
		StringBuffer sql = new StringBuffer();
		sql.append("select mksl.crsl_id, attendance, mksl.mksl_seq_id from stmk,mksl where")
				.append(" stmk.inst_id=mksl.inst_id and ")
				.append(" stmk.branch_id = mksl.branch_id and ")
				.append(" stmk.del_flg = mksl.del_flg and ")
				.append(" mksl.mksl_seq_id = stmk.mksl_seq_id and ")
				.append(" mksl.sub_test_id=? and ")
				.append(" mksl.exam_id=? and ")
				.append(" stmk.grade_obtd ='' and")
				.append(" mksl.studentgrp_id=? and ")
				.append(" mksl.inst_id=? and ")
				.append(" mksl.branch_id=? and ")
				.append(" mksl.ac_term= ? and ")
				.append(" student_admis_no = ? and stmk.del_flg = 'N'  group by mksl.crsl_id order by mksl.crsl_id");

		List<StudentReportCard> result = getJdbcTemplate().query(
				sql.toString(), new PreparedStatementSetter() {

					@Override
					public void setValues(PreparedStatement pss)
							throws SQLException {
						pss.setString(1, CommonCodeConstant.MAIN_TEST_EXAM);
						pss.setString(2, stuMrksRmksListKey.getExamId());
						pss.setString(3, stuMrksRmksListKey.getStudentGrpId());
						pss.setString(4, stuMrksRmksListKey.getInstId());
						pss.setString(5, stuMrksRmksListKey.getBranchId());
						pss.setString(6, stuMrksRmksListKey.getAcTerm());
						pss.setString(7, stuMrksRmksListKey.getStudentAdmisNo());
					}
				}, new ExamAttendanceRowMapper());
		System.out.println("List returned size :" + result.size());
		if (result.size() == 0) {
			throw new NoDataFoundException();
		}
		return result;
	}

	@Override
	public List<StudentReportCard> getStuMarkPerClass(
			final StuMrksRmksListKey stuMrksRmksListKey)
			throws NoDataFoundException {
		logger.debug("Inside select method");
		logger.debug(" where class for getMarkSubjectLinkList"
				+ stuMrksRmksListKey.toString());

		StringBuffer sql = new StringBuffer();
		sql.append("select * from (")
				.append(" select e.sub_id, e.crsl_id,e.sub_name, e.STUMAXMARK, e.STUMINMARK, e.STUMARKOBT, IF(STUMARKOBT>=STUMINMARK,'Pass','Fail') as STURESULT, x.AVGCLSMARK, x.MAXCLSMARK, find_in_set(e.STUMARKOBT, x.CLSMARKLIST) AS RANK from ")
				.append(" (select mksl.crsl_id, crsl.sub_id, sbjm.sub_name, sum(max_mark) as STUMAXMARK , sum(min_mark) as STUMINMARK, sum(marks_obtd) as STUMARKOBT ")
				.append(" from stmk, mksl, crsl,sbjm where ")
				.append(" stmk.inst_id = mksl.inst_id and ")
				.append(" stmk.branch_id = mksl.branch_id and ")
				.append(" stmk.mksl_seq_id = mksl.mksl_seq_id and ")
				.append(" stmk.del_flg = mksl.del_flg and ")
				.append(" mksl.inst_id = crsl.inst_id and ")
				.append(" mksl.branch_id = crsl.branch_id and ")
				.append(" mksl.crsl_id = crsl.crsl_id and ")
				.append(" mksl.del_flg = crsl.del_flg and ")
				.append(" crsl.inst_id = sbjm.inst_id and ")
				.append(" crsl.branch_id = sbjm.branch_id and ")
				.append(" crsl.sub_id = sbjm.sub_id and ")
				.append(" crsl.del_flg = sbjm.del_flg and ")
				.append("stmk.inst_id = ? and ")
				.append(" stmk.branch_id  = ? and ")
				.append(" mksl.ac_term = ? and ")
				.append(" mksl.exam_id = ? and ")
				.append(" stmk.grade_obtd ='' and ")
				.append(" stmk.student_admis_no = ? and stmk.del_flg = 'N' group by mksl.crsl_id order by mksl.crsl_id ")
				.append(" ) AS e, ")
				.append(" (select crsl_id, Avg(marks_obtd) as AVGCLSMARK, max(marks_obtd) as MAXCLSMARK, group_concat(marks_obtd order by marks_obtd desc) CLSMARKLIST from ")
				.append(" (select mksl.crsl_id, student_admis_no, sum(marks_obtd) as marks_obtd from stmk, mksl where ")
				.append(" stmk.inst_id = mksl.inst_id and ")
				.append(" stmk.branch_id = mksl.branch_id and ")
				.append(" stmk.mksl_seq_id = mksl.mksl_seq_id and ")
				.append(" stmk.del_flg = mksl.del_flg and ")
				.append(" stmk.inst_id = ? and ")
				.append(" stmk.branch_id  = ? and ")
				.append(" mksl.ac_term = ? and ")
				.append(" mksl.exam_id = ? and ")
				.append(" mksl.studentgrp_id  = ? and ")
				.append(" mksl.del_flg = 'N' and ")
				.append(" mksl.crsl_id in (select mksl.crsl_id ")
				.append(" from stmk, mksl where ")
				.append(" stmk.inst_id = mksl.inst_id and ")
				.append(" stmk.branch_id = mksl.branch_id and ")
				.append(" stmk.mksl_seq_id = mksl.mksl_seq_id and ")
				.append(" stmk.del_flg  = mksl.del_flg and ")
				.append(" stmk.inst_id = ? and ")
				.append(" stmk.branch_id  = ? and ")
				.append(" mksl.ac_term = ? and ")
				.append(" mksl.exam_id = ? and ")
				.append(" stmk.grade_obtd ='' and ")
				.append(" stmk.student_admis_no = ? and stmk.del_flg = 'N' group by mksl.crsl_id order by mksl.crsl_id) ")
				.append(" group by mksl.crsl_id, student_admis_no order by mksl.crsl_id, student_admis_no) as t2 group by crsl_id  ) as x where e.crsl_id = x.crsl_id ) as Z order by crsl_id ; ");

		List<StudentReportCard> result = getJdbcTemplate().query(
				sql.toString(), new PreparedStatementSetter() {

					@Override
					public void setValues(PreparedStatement pss)
							throws SQLException {
						pss.setString(1, stuMrksRmksListKey.getInstId());
						pss.setString(2, stuMrksRmksListKey.getBranchId());
						pss.setString(3, stuMrksRmksListKey.getAcTerm());
						pss.setString(4, stuMrksRmksListKey.getExamId());
						pss.setString(5, stuMrksRmksListKey.getStudentAdmisNo());
						pss.setString(6, stuMrksRmksListKey.getInstId());
						pss.setString(7, stuMrksRmksListKey.getBranchId());
						pss.setString(8, stuMrksRmksListKey.getAcTerm());
						pss.setString(9, stuMrksRmksListKey.getExamId());
						pss.setString(10, stuMrksRmksListKey.getStudentGrpId());
						pss.setString(11, stuMrksRmksListKey.getInstId());
						pss.setString(12, stuMrksRmksListKey.getBranchId());
						pss.setString(13, stuMrksRmksListKey.getAcTerm());
						pss.setString(14, stuMrksRmksListKey.getExamId());
						pss.setString(15,
								stuMrksRmksListKey.getStudentAdmisNo());
					}
				}, new StudentMark());
		System.out.println("List returned size :" + result.size());
		if (result.size() == 0) {
			throw new NoDataFoundException();
		}
		return result;
	}

	@Override
	public List<StudentReportCard> getStuMarksForAllClass(
			final StuMrksRmksListKey stuMrksRmksListKey)
			throws NoDataFoundException {
		logger.debug("Inside select method");
		logger.debug(" where class for getMarkSubjectLinkList"
				+ stuMrksRmksListKey.toString());

		StringBuffer sql = new StringBuffer();
		sql.append(
				"select crsl_id, Avg(marks_obtd) as average, max(marks_obtd) as max_marks  from ")
				.append(" (select mksl.crsl_id, student_admis_no, sum(marks_obtd) as marks_obtd from stmk, mksl where ")
				.append(" stmk.inst_id = mksl.inst_id and ")
				.append(" stmk.branch_id = mksl.branch_id and ")
				.append(" stmk.mksl_seq_id = mksl.mksl_seq_id and ")
				.append(" stmk.inst_id = ? and ")
				.append(" stmk.branch_id  = ? and ")
				.append(" mksl.ac_term = ? and ")
				.append(" mksl.exam_id = ? and ")
				.append(" mksl.studentgrp_id in (select a.studentgrp_id from stgm a inner join stgm b on a.coursemaster_id = b.coursemaster_id ")
				.append(" and a.term_id = b.term_id and b.studentgrp_id = ?) and ")
				.append(" mksl.crsl_id in (select mksl.crsl_id")
				.append(" from stmk, mksl where ")
				.append(" stmk.inst_id = mksl.inst_id and ")
				.append(" stmk.branch_id = mksl.branch_id and ")
				.append(" stmk.mksl_seq_id = mksl.mksl_seq_id and ")
				.append(" stmk.inst_id = ? and ")
				.append(" stmk.branch_id  = ? and ")
				.append(" mksl.ac_term = ? and ")
				.append(" mksl.exam_id = ? and ")
				.append(" stmk.grade_obtd ='' and ")
				.append(" stmk.student_admis_no = ?  group by mksl.crsl_id order by mksl.crsl_id) ")
				.append(" group by mksl.crsl_id, student_admis_no order by mksl.crsl_id, student_admis_no) as t1 group by crsl_id;");

		List<StudentReportCard> result = getJdbcTemplate().query(
				sql.toString(), new PreparedStatementSetter() {

					@Override
					public void setValues(PreparedStatement pss)
							throws SQLException {
						pss.setString(1, stuMrksRmksListKey.getInstId());
						pss.setString(2, stuMrksRmksListKey.getBranchId());
						pss.setString(3, stuMrksRmksListKey.getAcTerm());
						pss.setString(4, stuMrksRmksListKey.getExamId());
						pss.setString(5, stuMrksRmksListKey.getStudentGrpId());
						pss.setString(6, stuMrksRmksListKey.getInstId());
						pss.setString(7, stuMrksRmksListKey.getBranchId());
						pss.setString(8, stuMrksRmksListKey.getAcTerm());
						pss.setString(9, stuMrksRmksListKey.getExamId());
						pss.setString(10,
								stuMrksRmksListKey.getStudentAdmisNo());
					}
				}, new StuReportCardRowMapper());
		System.out.println("List returned size :" + result.size());
		if (result.size() == 0) {
			throw new NoDataFoundException();
		}
		return result;
	}

	@Override
	public List<StudentReportCard> getStuPrevExamConsolidation(
			final StuMrksRmksListKey stuMrksRmksListKey)
			throws NoDataFoundException {
		logger.debug("Inside select method");
		logger.debug(" where class for getMarkSubjectLinkList"
				+ stuMrksRmksListKey.toString());

		StringBuffer sql = new StringBuffer();

		sql.append(
				"select exam_ordr_wi_sg,mksl.exam_id,  mksl.crsl_id, sbjm.sub_id,sbjm.sub_name ,sbjm.short_code, sum(max_mark) as maxSum,sum(min_mark) as minSum,sum(marks_obtd) as maxMarkObt")
				.append(" from stmk,mksl,mkmt, crsl,sbjm where ")
				.append(" stmk.inst_id = mksl.inst_id and ")
				.append(" stmk.branch_id = mksl.branch_id and ")
				.append(" stmk.mksl_seq_id = mksl.mksl_seq_id and")
				.append(" mksl.inst_id = mkmt.inst_id and")
				.append(" mksl.branch_id = mkmt.branch_id and")
				.append(" mksl.exam_id = mkmt.exam_id and")
				.append(" mksl.ac_term = mkmt.ac_term and")
				.append(" mksl.inst_id = crsl.inst_id and")
				.append(" mksl.branch_id = crsl.branch_id and")
				.append(" sbjm.inst_id = crsl.inst_id and")
				.append(" sbjm.branch_id = crsl.branch_id and")
				.append(" crsl.sub_id = sbjm.sub_id and")
				.append(" mksl.crsl_id = crsl.crsl_id and")
				.append(" stmk.inst_id = ? and")
				.append(" stmk.branch_id  = ? and")
				.append(" mksl.ac_term = ? and")
				.append(" stmk.grade_obtd ='' and")
				.append(" stmk.student_admis_no = ? and")
				.append(" mkmt.studentgrp_id  = ?  group by exam_ordr_wi_sg,mksl.exam_id,mksl.crsl_id order by")
				.append(" exam_ordr_wi_sg,mksl.exam_id,mksl.crsl_id;");
		List<StudentReportCard> result = getJdbcTemplate().query(
				sql.toString(), new PreparedStatementSetter() {

					@Override
					public void setValues(PreparedStatement pss)
							throws SQLException {
						pss.setString(1, stuMrksRmksListKey.getInstId());
						pss.setString(2, stuMrksRmksListKey.getBranchId());
						pss.setString(3, stuMrksRmksListKey.getAcTerm());
						pss.setString(4, stuMrksRmksListKey.getStudentAdmisNo());
						pss.setString(5, stuMrksRmksListKey.getStudentGrpId());

					}
				}, new StuReportCardRowMapper1());
		System.out.println("List returned size :" + result.size());
		if (result.size() == 0) {
			throw new NoDataFoundException();
		}
		return result;
	}
	
	@Override
	public StudentReportCard getStuClassRank(
			final StuMrksRmksListKey stuMrksRmksListKey)
			throws NoDataFoundException {
		logger.debug("Inside select method");
		logger.debug(" where class for getMarkSubjectLinkList"
				+ stuMrksRmksListKey.toString());
		StringBuffer sql = new StringBuffer();

		sql.append("select find_in_set(x.stu_tot_marks,y.CLSTOTLST) AS RANK, y.cnt from")
				.append(" (select student_admis_no, sum(marks_obtd) as stu_tot_marks from stmk,mksl where ")
				.append(" stmk.inst_id = mksl.inst_id and ")
				.append(" stmk.branch_id = mksl.branch_id and ")
				.append(" stmk.mksl_seq_id = mksl.mksl_seq_id and")
				.append(" stmk.del_flg = mksl.del_flg and")
				.append(" stmk.inst_id = ? and")
				.append(" stmk.branch_id  = ? and")
				.append(" mksl.ac_term = ? and")
				.append(" mksl.exam_id = ? and")
				.append(" stmk.student_admis_no = ? and")
				.append(" mksl.del_flg = ?) as x,")
				.append(" (select count(*) as cnt, group_concat(tot_marks_obtd order by tot_marks_obtd desc) CLSTOTLST from")
				.append(" (select student_admis_no, sum(marks_obtd) as tot_marks_obtd from stmk, mksl where")
				.append(" stmk.inst_id = mksl.inst_id and ")
				.append(" stmk.branch_id = mksl.branch_id and ")
				.append(" stmk.mksl_seq_id = mksl.mksl_seq_id and")
				.append(" stmk.del_flg = mksl.del_flg and")
				.append(" stmk.inst_id = ? and")
				.append(" stmk.branch_id  = ? and")
				.append(" mksl.ac_term = ? and")
				.append(" mksl.exam_id = ? and")
				.append(" mksl.studentgrp_id  = ? and")
				.append(" mksl.del_flg = ? group by student_admis_no order by student_admis_no) as e ) as y;");
		StudentReportCard result = getJdbcTemplate().query(
				sql.toString(), new PreparedStatementSetter() {

					@Override
					public void setValues(PreparedStatement pss)
							throws SQLException {
						pss.setString(1, stuMrksRmksListKey.getInstId());
						pss.setString(2, stuMrksRmksListKey.getBranchId());
						pss.setString(3, stuMrksRmksListKey.getAcTerm());
						pss.setString(4, stuMrksRmksListKey.getExamId());
						pss.setString(5, stuMrksRmksListKey.getStudentAdmisNo());
						pss.setString(6, "N");
						pss.setString(7, stuMrksRmksListKey.getInstId());
						pss.setString(8, stuMrksRmksListKey.getBranchId());
						pss.setString(9, stuMrksRmksListKey.getAcTerm());
						pss.setString(10, stuMrksRmksListKey.getExamId());
						pss.setString(11, stuMrksRmksListKey.getStudentGrpId());
						pss.setString(12, "N");

					}
				},  new ResultSetExtractor<StudentReportCard>() {

					@Override
					public StudentReportCard extractData(ResultSet rs)
							throws SQLException, DataAccessException {
						StudentReportCard StudentReportCard = null;
						if (rs.next()) {

							StudentReportCard = new StudentReportCard();
							StudentReportCard.setClassRank(rs.getInt("RANK"));
							StudentReportCard.setClassStrength(rs.getInt("cnt"));
						}
						return StudentReportCard;
					}

				});
		return result;
	}

	@Override
	public StudentMaster retriveStudentDetails(
			final StudentMasterKey studentMasterKey)
			throws NoDataFoundException {
		logger.debug("retrive stum");
		logger.info("stum key values :" + studentMasterKey.toString());
		StringBuffer sql = new StringBuffer();
		sql.append("select ")

		.append("STUDENT_ADMIS_NO,").append("STUDENT_NAME,")
				.append("student_grp from stum,stgm where ")
				.append("stum.inst_id=stgm.inst_id and ")
				.append("stum.branch_id=stgm.branch_id and ")
				.append("stum.del_flg=stgm.del_flg and  ")
				.append("stum.studentgrp_id=stgm.studentgrp_id and ")

				.append("stum.INST_ID = ? and ")
				.append(" stum.BRANCH_ID = ? and ")
				.append(" STUDENT_ADMIS_NO = ? ")
				.append(" and  stum.DEL_FLG= ? ");
		StudentMaster studentMasterResult = null;
		studentMasterResult = (StudentMaster) getJdbcTemplate().query(
				sql.toString(), new PreparedStatementSetter() {

					@Override
					public void setValues(PreparedStatement pss)
							throws SQLException {
						pss.setString(1, studentMasterKey.getInstId());
						pss.setString(2, studentMasterKey.getBranchId());
						pss.setString(3, studentMasterKey.getStudentAdmisNo());
						pss.setString(4, "N");

					}
				}, new StudentMasterResultSetExtractor());
		if (studentMasterResult == null) {
			throw new NoDataFoundException();
		}
		return studentMasterResult;
	}

	@Override
	public String getLatestExam(final String instId, final String branchId,
			final String studentGrpId) throws NoDataFoundException {

		StringBuffer sql = new StringBuffer();
		Object[] data = new Object[5];
		sql.append("select ")
			.append("exam_id from mkmt where ")
			.append("INST_ID = ? and ")
			.append(" BRANCH_ID = ? and ")
			.append(" studentgrp_id = ? and ")
			.append(" status = ? and ")
			.append("  DEL_FLG= ?  order by exam_ordr_wi_sg desc LIMIT 1 ");
		data[0] = instId;
		data[1] = branchId;
		data[2] = studentGrpId;
		data[3] = "P";
		data[4] = "N";
		String examId ="";
		try {
			 examId = getJdbcTemplate().queryForObject(sql.toString(), data,
					String.class);
		} catch (EmptyResultDataAccessException e) {
			throw new NoDataFoundException();
		}
		

		return examId;
	}
@Override
	public List<ViewMarkList> getStudentSubjectMarks(
			final StuMrksRmksListKey stuMrksRmksListKey) throws NoDataFoundException {
		logger.debug("Inside select method");
		logger.debug(" where class for getStudentConsolidationMarks"
				+ stuMrksRmksListKey.toString());

		StringBuffer sql = new StringBuffer();

		sql.append(	"select ")
			.append(" exam_ordr_wi_sg, ")
			.append(" mksl.exam_id,  ")
			.append(" mksl.crsl_id, ")
			.append(" crsl.sub_id, ")
			.append(" round(((sum(marks_obtd)*100)/sum(max_mark))) as mark,")
			.append(" SUB_NAME, short_code")
			.append(" from stmk,mksl,mkmt, crsl,sbjm where")
			.append(" stmk.inst_id = mksl.inst_id and")
			.append(" stmk.branch_id = mksl.branch_id and")
			.append(" stmk.del_flg = mksl.del_flg and")			
			.append(" stmk.mksl_seq_id = mksl.mksl_seq_id and")
			.append(" mksl.inst_id = mkmt.inst_id and")			
			.append(" mksl.branch_id = mkmt.branch_id and")
			.append(" mksl.exam_id = mkmt.exam_id and")			
			.append(" mksl.ac_term = mkmt.ac_term and")
			.append(" mksl.del_flg = mkmt.del_flg and")
			.append(" mksl.inst_id = crsl.inst_id and")
			.append(" mksl.branch_id = crsl.branch_id and")
			.append(" mksl.crsl_id = crsl.crsl_id and")
			.append(" mksl.del_flg = crsl.del_flg and")
			.append(" crsl.inst_id = sbjm.inst_id and")
			.append(" crsl.sub_id = sbjm.sub_id and")
			.append(" crsl.branch_id = sbjm.branch_id and")
			.append(" crsl.del_flg = sbjm.del_flg and")
			.append(" stmk.inst_id = ? and")
			.append(" stmk.branch_id  = ? and")
			
			.append(" mksl.ac_term = ? and")
			.append(" stmk.grade_obtd ='' and")
			.append(" stmk.student_admis_no = ? and")
			.append(" mkmt.studentgrp_id  = ? and")
			.append(" mkmt.del_flg  = 'N' ")
			.append(" group by exam_ordr_wi_sg,mksl.exam_id,mksl.crsl_id")
			.append(" order by  exam_ordr_wi_sg,mksl.exam_id,mksl.crsl_id ");
		List<ViewMarkList> consolidateResult = getJdbcTemplate().query(
				sql.toString(), new PreparedStatementSetter() {

					@Override
					public void setValues(PreparedStatement pss)
							throws SQLException {
						pss.setString(1, stuMrksRmksListKey.getInstId());
						pss.setString(2, stuMrksRmksListKey.getBranchId());
						pss.setString(3, stuMrksRmksListKey.getAcTerm());
						pss.setString(4, stuMrksRmksListKey.getStudentAdmisNo());
						pss.setString(5, stuMrksRmksListKey.getStudentGrpId());

					}
				}, new StudentSubjectMarkRowMapper1());
		System.out.println("List returned size :" + consolidateResult.size());
		if (consolidateResult.size() == 0) {
			throw new NoDataFoundException();
		}
		return consolidateResult;

	}

}
class StudentSubjectMarkRowMapper1 implements RowMapper<ViewMarkList> {

	@Override
	public ViewMarkList mapRow(ResultSet rs, int arg1) throws SQLException {
		
		ViewMarkList stuConsolidateMark = new ViewMarkList();
		stuConsolidateMark.setCrslId(rs.getString("CRSL_ID"));
		stuConsolidateMark.setExamOrder(rs.getString("EXAM_ORDR_WI_SG"));
		stuConsolidateMark.setExamId(rs.getString("EXAM_ID"));
		stuConsolidateMark.setSubId(rs.getString("SUB_ID"));
		stuConsolidateMark.setSubName(rs.getString("SUB_NAME"));
		stuConsolidateMark.setShortCode(rs.getString("SHORT_CODE"));
		stuConsolidateMark.setMaxMarkObt(rs.getInt("mark"));
		return stuConsolidateMark;
	}
}


class StuReportCardRowMapper implements RowMapper<StudentReportCard> {

	@Override
	public StudentReportCard mapRow(ResultSet rs, int arg1) throws SQLException {

		StudentReportCard stuMrksRmksList = new StudentReportCard();
		stuMrksRmksList.setCrslId(rs.getString("crsl_id"));
		stuMrksRmksList.setAverage(rs.getFloat("average"));
		stuMrksRmksList.setMaxMarkObt(rs.getInt("max_marks"));
		return stuMrksRmksList;
	}
}

class StuReportCardRowMapper1 implements RowMapper<StudentReportCard> {

	@Override
	public StudentReportCard mapRow(ResultSet rs, int arg1) throws SQLException {

		StudentReportCard stuMrksRmksList = new StudentReportCard();
		stuMrksRmksList.setCrslId(rs.getString("exam_ordr_wi_sg"));
		stuMrksRmksList.setExamId(rs.getString("exam_id"));
		stuMrksRmksList.setSubId(rs.getString("sub_id"));
		stuMrksRmksList.setSubName(rs.getString("sub_name"));		
		stuMrksRmksList.setShortCode(rs.getString("short_code"));
		stuMrksRmksList.setMaxSum(rs.getInt("maxSum"));
		stuMrksRmksList.setMinSum(rs.getInt("minSum"));
		stuMrksRmksList.setMaxMarkObt(rs.getInt("maxMarkObt"));
		return stuMrksRmksList;
	}
}

class StudentMark implements RowMapper<StudentReportCard> {

	@Override
	public StudentReportCard mapRow(ResultSet rs, int arg1) throws SQLException {

		StudentReportCard stuMrksRmksList = new StudentReportCard();

		stuMrksRmksList.setCrslId(rs.getString("crsl_id"));
		stuMrksRmksList.setSubId(rs.getString("sub_id"));
		stuMrksRmksList.setSubName(rs.getString("sub_name"));
		stuMrksRmksList.setStuMaxMark(rs.getInt("STUMAXMARK"));
		stuMrksRmksList.setStuMinMark(rs.getInt("STUMINMARK"));
		stuMrksRmksList.setStuMarkObt(rs.getInt("STUMARKOBT"));
		stuMrksRmksList.setStuResult(rs.getString("STURESULT"));
		stuMrksRmksList.setAvgClassMark(rs.getFloat("AVGCLSMARK"));
		stuMrksRmksList.setMaxClassMark(rs.getInt("MAXCLSMARK"));
		stuMrksRmksList.setRank(rs.getInt("RANK"));
		return stuMrksRmksList;
	}
}
class ExamAttendanceRowMapper implements RowMapper<StudentReportCard> {

	@Override
	public StudentReportCard mapRow(ResultSet rs, int arg1) throws SQLException {

		StudentReportCard stuMrksRmksList = new StudentReportCard();
		stuMrksRmksList.setCrslId(rs.getString("crsl_id"));
		stuMrksRmksList.setAttendance(rs.getString("attendance"));
		return stuMrksRmksList;
	}
}
class StudentMasterResultSetExtractor implements
		ResultSetExtractor<StudentMaster> {
	@Override
	public StudentMaster extractData(ResultSet rs) throws SQLException {
		StudentMaster studentMaster = null;
		if (rs.next()) {
			studentMaster = new StudentMaster();
			studentMaster.setStudentAdmisNo(rs.getString("STUDENT_ADMIS_NO"));
			studentMaster.setStudentName(rs.getString("STUDENT_NAME"));
			studentMaster.setStuGrpId(rs.getString("student_grp"));
		}
		return studentMaster;
	}
}