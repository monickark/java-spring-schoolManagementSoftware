package com.jaw.mark.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.framework.dao.BaseDao;

//MarkSubjectLinkDAO DAO class
@Repository
public class StuMrksRmksListDAO extends BaseDao implements IStuMrksRmksListDAO {
	// Logging
	Logger logger = Logger.getLogger(StuMrksRmksListDAO.class);

	@Override
	public List<StuMrksRmksList> getStuMarksRmksList(
			final StuMrksRmksListKey stuMrksRmksListKey)
			throws NoDataFoundException {
		logger.debug("Inside select method");
		logger.debug(" where class for getMarkSubjectLinkList"
				+ stuMrksRmksListKey.toString());
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ")
				.append(" sub_id,sub_type,crsl.crsl_id,min_mark,max_mark,student_admis_no,marks_obtd,grade_obtd,sub_remarks,attendance")
				.append(" FROM stmk,mksl,crsl where")
				.append(" mksl.inst_id=crsl.inst_id and")
				.append(" mksl.branch_id=crsl.branch_id and")
				.append(" mksl.crsl_id=crsl.crsl_id and")
				.append(" mksl.inst_id=stmk.inst_id and")
				.append(" mksl.branch_id=stmk.branch_id and")
				.append(" stmk.mksl_seq_id=mksl.mksl_seq_id and")
				.append(" mksl.del_flg=crsl.del_flg and")
				.append(" stmk.del_flg=crsl.del_flg and")
				.append(" mksl.exam_id=? and").append(" crsl.del_flg='N' and")
				.append(" stmk.inst_id=? and").append(" stmk.branch_id=? and")
				.append(" student_admis_no=?;");

		List<StuMrksRmksList> result = getJdbcTemplate().query(sql.toString(),
				new PreparedStatementSetter() {

					@Override
					public void setValues(PreparedStatement pss)
							throws SQLException {

						pss.setString(1, stuMrksRmksListKey.getExamId());
						pss.setString(2, stuMrksRmksListKey.getInstId());
						pss.setString(3, stuMrksRmksListKey.getBranchId());
						pss.setString(4, stuMrksRmksListKey.getStudentAdmisNo());
					}
				}, new StuMrksRmksListListRowMapper());
		System.out.println("List returned size :" + result.size());
		if (result.size() == 0) {
			throw new NoDataFoundException();
		}
		return result;
	}

	@Override
	public List<StuDetailsListForRemarks> getStuAdmisNoList(
			final StuMrksRmksListKey stuMrksRmksListKey, String flow)
			throws NoDataFoundException {
		logger.debug("Inside select method");
		logger.debug(" where class for getMarkSubjectLinkList"
				+ stuMrksRmksListKey.toString());
		logger.debug(" flow" + flow);
		StringBuffer sql = new StringBuffer();
		if (flow.equals("common")) {
			sql.append(
					"select  stum.student_admis_no, student_name from stum, stgm,stin where")
					.append(" stum.inst_id = stgm.inst_id and ")
					.append(" stum.branch_id = stgm.branch_id and ")
					.append(" stum.student_admis_no=stin.student_admis_no and ")
					.append(" stum.course = stgm.coursemaster_id and ")
					.append(" stum.sec = stgm.sec_id and ")
					.append(" stum.del_flg = stgm.del_flg and ")
					.append(" stum.inst_id=? and ")
					.append("  stum.branch_id = ?  and")
					.append("  stum.academic_year = ? and")
					.append("  stum.del_flg = ? and")
					.append("  stgm.studentgrp_id = ? ");
		} else if (flow.equals("update")) {

			sql.append("select  stum.student_admis_no, student_name from stum, stgm,stin,ster where")
					.append(" stum.inst_id = stgm.inst_id and ")
					.append(" stum.branch_id = stgm.branch_id and ")
					.append(" stum.student_admis_no=stin.student_admis_no and ")
					.append(" stum.course = stgm.coursemaster_id and ")
					.append(" stum.standard = stgm.term_id and stum.sec = stgm.sec_id and ")
					.append(" stum.inst_id = ster.inst_id and ")
					.append(" stum.branch_id = ster.branch_id and ")
					.append(" stum.student_admis_no=ster.student_admis_no and ")
					.append(" stum.del_flg = stgm.del_flg and ")
					.append(" stum.inst_id=? and ")
					.append("  stum.branch_id = ?  and")
					.append("  stum.academic_year = ? and")
					.append("  stum.del_flg = ? and")
					.append("  stgm.studentgrp_id = ? ");
		}

		List<StuDetailsListForRemarks> result = getJdbcTemplate().query(
				sql.toString(), new PreparedStatementSetter() {

					@Override
					public void setValues(PreparedStatement pss)
							throws SQLException {
						pss.setString(1, stuMrksRmksListKey.getInstId());
						pss.setString(2, stuMrksRmksListKey.getBranchId());
						pss.setString(3, stuMrksRmksListKey.getAcTerm());
						pss.setString(4, "N");
						pss.setString(5, stuMrksRmksListKey.getStudentGrpId());
					}
				}, new StuDetailsListRowMapper());
		System.out.println("List returned size :" + result.size());
		if (result.size() == 0) {
			throw new NoDataFoundException();
		}
		return result;
	}

	@Override
	public List<StuDetailsListForRemarks> getStuAdmisNoListForRemarksNotAdded(
			final StuMrksRmksListKey stuMrksRmksListKey)
			throws NoDataFoundException {
		StringBuffer sql = new StringBuffer();
		sql.append(
				"select stum.student_admis_no, student_name from stum left join ster on")
				.append(" stum.inst_id = ster.inst_id and ")
				.append(" stum.branch_id = ster.branch_id and ")
				.append(" stum.student_admis_no = ster.student_admis_no and ")
				.append(" stum.academic_year = ster.ac_term and ")
				.append(" stum.del_flg = ster.del_flg and ")
				.append(" ster.exam_type_id = ? ")
				.append(" where stum.inst_id = ? ")
				.append(" and stum.branch_id = ? ")
				.append(" and stum.del_flg = ? ")
				.append(" and stum.studentgrp_id = ? ")
				.append("  and stum.academic_year = ? ")
				.append("  and ster.student_admis_no is null;");

		List<StuDetailsListForRemarks> result = getJdbcTemplate().query(
				sql.toString(), new PreparedStatementSetter() {

					@Override
					public void setValues(PreparedStatement pss)
							throws SQLException {

						pss.setString(1, stuMrksRmksListKey.getExamId());
						pss.setString(2, stuMrksRmksListKey.getInstId());
						pss.setString(3, stuMrksRmksListKey.getBranchId());
						pss.setString(4, "N");
						pss.setString(5, stuMrksRmksListKey.getStudentGrpId());
						pss.setString(6, stuMrksRmksListKey.getAcTerm());
					}
				}, new StuDetailsListRowMapper());
		System.out.println("List returned size :" + result.size());
		if (result.size() == 0) {
			throw new NoDataFoundException();
		}
		return result;
	}
}

class StuMrksRmksListListRowMapper implements RowMapper<StuMrksRmksList> {

	@Override
	public StuMrksRmksList mapRow(ResultSet rs, int arg1) throws SQLException {

		StuMrksRmksList stuMrksRmksList = new StuMrksRmksList();
		stuMrksRmksList.setSubId(rs.getString("sub_id"));
		stuMrksRmksList.setSubType(rs.getString("sub_type"));
		stuMrksRmksList.setCrslId(rs.getString("crsl_id"));

		stuMrksRmksList.setMinMark(rs.getString("min_mark"));
		stuMrksRmksList.setMaxMark(rs.getString("max_mark"));
		stuMrksRmksList.setStudentAdmisNo(rs.getString("student_admis_no"));

		stuMrksRmksList.setMarksObtd(rs.getString("marks_obtd"));
		stuMrksRmksList.setGradeObtd(rs.getString("grade_obtd"));
		stuMrksRmksList.setSubRemarks(rs.getString("sub_remarks"));

		stuMrksRmksList.setAttendance(rs.getString("attendance"));

		return stuMrksRmksList;
	}

}

class StuDetailsListRowMapper implements RowMapper<StuDetailsListForRemarks> {

	@Override
	public StuDetailsListForRemarks mapRow(ResultSet rs, int arg1)
			throws SQLException {
		StuDetailsListForRemarks stuDetailsListForRemarks = new StuDetailsListForRemarks();
		stuDetailsListForRemarks.setStudentAdmisNo(rs
				.getString("student_admis_no"));
		stuDetailsListForRemarks.setStudentName(rs.getString("student_name"));
		return stuDetailsListForRemarks;
	}
}
