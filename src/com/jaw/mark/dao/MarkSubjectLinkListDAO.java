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
import com.jaw.mark.controller.IMarkSubjectLinkListDAO;

//MarkSubjectLinkDAO DAO class
@Repository
public class MarkSubjectLinkListDAO extends BaseDao implements
		IMarkSubjectLinkListDAO {
	// Logging
	Logger logger = Logger.getLogger(MarkSubjectLinkListDAO.class);

	@Override
	public List<MarkSubjectLinkList> getMarkSubjectLinkList(
			final MarkSubjectLinkKey markSubjectLinkKey)
			throws NoDataFoundException {
		logger.debug("Inside select method");
		logger.debug(" where class for getMarkSubjectLinkList"
				+ markSubjectLinkKey);
		StringBuffer sql = new StringBuffer();
		sql.append(
				"select crsl.sub_type, crsl.sub_id,sbjm.sub_name,crsl.requires_lab,remarks,sub_portion_dtls,crsl.mark_grade, crsl.crsl_id,mksl_seq_id, exam_id, exam_type, sub_test_id, lab_batch, exam_date, min_mark, max_mark,start_time,duration ,status")
				.append(" from crsl left join mksl on ")
				.append("crsl.inst_id  = mksl.inst_id and ")
				.append("crsl.branch_id = mksl.branch_id and ")

				.append("crsl.crsl_id = mksl.crsl_id and ")
				.append("crsl.del_flg = mksl.del_flg and ")
				.append("mksl.ac_term =  ? and ")
				.append("mksl.exam_id =  ? left join sbjm on crsl.sub_id = sbjm.sub_id and sbjm.del_flg = ? and ")
				.append("sbjm.inst_id = ? and ")//new
				.append("sbjm.branch_id = ? ")//new
				.append("where  crsl.crsl_id in ")
				.append("(select crsl_id from crsl,stgm where crsl.inst_id = stgm.inst_id and ")
				.append("crsl.branch_id = stgm.branch_id and ")
				.append("crsl.coursemaster_id = stgm.coursemaster_id and ")

				.append("crsl.term_id = stgm.term_id and ")
				.append("crsl.del_flg = stgm.del_flg and ")
				.append("crsl.inst_id = ? and ")

				.append("crsl.branch_id = ? and ")
				.append("stgm.studentgrp_id = ?  and ")
				.append("crsl.del_flg = ? ) and ")
				.append("crsl.inst_id = ? and ")//new
				.append("crsl.branch_id = ? ")//new

				.append("order by crsl_id; ");

		List<MarkSubjectLinkList> result = getJdbcTemplate().query(
				sql.toString(), new PreparedStatementSetter() {

					@Override
					public void setValues(PreparedStatement pss)
							throws SQLException {

						pss.setString(1, markSubjectLinkKey.getAcTerm());
						pss.setString(2, markSubjectLinkKey.getExamId());
						pss.setString(3, "N");
						pss.setString(4, markSubjectLinkKey.getInstId());
						pss.setString(5, markSubjectLinkKey.getBranchId());
						pss.setString(6, markSubjectLinkKey.getInstId());
						pss.setString(7, markSubjectLinkKey.getBranchId());
						pss.setString(8, markSubjectLinkKey.getStudentGrpId());
						pss.setString(9, "N");
						pss.setString(10, markSubjectLinkKey.getInstId());
						pss.setString(11, markSubjectLinkKey.getBranchId());
					}
				}, new MarkSubjectLinkListRowMapper());
		if (result.size() == 0) {
			throw new NoDataFoundException();
		}
		return result;
	}

}

class MarkSubjectLinkListRowMapper implements RowMapper<MarkSubjectLinkList> {

	@Override
	public MarkSubjectLinkList mapRow(ResultSet rs, int arg1)
			throws SQLException {

		MarkSubjectLinkList markSubjectLinkList = new MarkSubjectLinkList();
		markSubjectLinkList = new MarkSubjectLinkList();
		markSubjectLinkList.setMarkSubjectLinkId(rs.getString("MKSL_SEQ_ID"));
		markSubjectLinkList.setSubType(rs.getString("SUB_TYPE"));
		markSubjectLinkList.setSubId(rs.getString("SUB_ID"));
		markSubjectLinkList.setSubName(rs.getString("SUB_NAME"));

		markSubjectLinkList.setExamId(rs.getString("EXAM_ID"));
		markSubjectLinkList.setExamType(rs.getString("EXAM_TYPE"));
		markSubjectLinkList.setSubTestId(rs.getString("SUB_TEST_ID"));
		
		markSubjectLinkList.setStartTime(rs.getString("START_TIME"));
		markSubjectLinkList.setDuration(rs.getString("DURATION"));
		markSubjectLinkList.setStatus(rs.getString("STATUS"));

		markSubjectLinkList.setCrslId(rs.getString("CRSL_ID"));
		markSubjectLinkList.setLabBatch(rs.getString("LAB_BATCH"));
		markSubjectLinkList.setExamDate(rs.getString("EXAM_DATE"));

		markSubjectLinkList.setMinMark(rs.getString("MIN_MARK"));
		markSubjectLinkList.setMaxMark(rs.getString("MAX_MARK"));
		markSubjectLinkList.setMarkOrGrade(rs.getString("mark_grade"));
		markSubjectLinkList.setRequiresLab(rs.getString("requires_lab"));

		markSubjectLinkList.setRemarks(rs.getString("remarks"));
		markSubjectLinkList.setSubPortionDetails(rs
				.getString("sub_portion_dtls"));

		return markSubjectLinkList;
	}
}
