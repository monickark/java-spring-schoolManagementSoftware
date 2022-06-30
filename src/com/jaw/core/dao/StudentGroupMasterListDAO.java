package com.jaw.core.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.jaw.common.constants.CommonCodeConstant;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.framework.dao.BaseDao;

@Repository
public class StudentGroupMasterListDAO extends BaseDao implements
		IStudentGroupMasterListDAO {
	Logger logger = Logger.getLogger(StudentGroupMasterListDAO.class);

	@Override
	public List<StudentGroupMaster> selectGroupMasterList(
			final StudentGroupMasterListKey studentGroupMasterListKey)
			throws NoDataFoundException {

		logger.debug("DAO :Inside StudentGroupMaster List select method");
		logger.debug("DAO : StudentGroupMaster List select Key   :"
				+ studentGroupMasterListKey);

		List<Object> data = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		sql.append("select ").append(" DB_TS,").append("COURSEMASTER_ID,")
				.append("STUDENTGRP_ID,").append("STUDENT_GRP,")
				.append("TERM_ID,").append("SEC_ID,").append("MEDIUM")
				.append(" from stgm ").append(" where").append("  INST_ID= ?")
				.append(" and  BRANCH_ID= ?").append(" and  DEL_FLG=?");
		data.add(studentGroupMasterListKey.getInstId());
		data.add(studentGroupMasterListKey.getBranchId());
		data.add("N");

		if ((studentGroupMasterListKey.getCourseMasterId() != null)
				&& (studentGroupMasterListKey.getCourseMasterId() != "")) {
			sql.append(" and COURSEMASTER_ID=?  ");

			data.add(studentGroupMasterListKey.getCourseMasterId());
		}

		Object[] array = data.toArray(new Object[data.size()]);

		List<StudentGroupMaster> selectedListStdGrpMtr = getJdbcTemplate()
				.query(sql.toString(), array,
						new StudentGroupMasterSelectRowMapper());

		if (selectedListStdGrpMtr.size() == 0) {
			logger.error("No  record found");
			throw new NoDataFoundException();
		}

		logger.debug("DAO : Student Group Master List value"
				+ selectedListStdGrpMtr.size());

		return selectedListStdGrpMtr;
	}

	@Override
	public Map<String, String> getStGroupForClsTchrAltmnt(final String acterm,
			final String courseId, final String instId, final String branchId)
			throws NoDataFoundException {

		logger.debug("Inside get list method in StandardSectionDao " 
				+ " course " + courseId + " acterm :" + acterm + " instid :"
				+ instId + " branchid:" + branchId);
		List<Object> data = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();

		sql.append("select a.studentgrp_id, a.student_grp from stgm a ")
				.append(" where a.inst_id = ? ").append(" and a.branch_id = ?");
		data.add(instId);
		data.add(branchId);
		if ((courseId != null) && (courseId != "")) {
			sql.append(" and coursemaster_id=?");
			data.add(courseId);
		}
		sql.append(" and a.del_flg = ? ")
				.append(" and a.studentgrp_id not in ")
				.append(" (select e.studentgrp_id from ")
				.append(" (select studentgrp_id, count(*) as cnt from clta ")
				.append(" where ac_term=? and ")
				.append("  inst_id = ? ")
				.append("  and branch_id = ?")
				.append(" and del_flg = ?  group by studentgrp_id order by studentgrp_id) as e,")
				.append(" (select count(*) as cocdcnt from cocd ")
				.append(" where inst_id = ? ")
				.append(" and branch_id = ? ")
				.append("   and code_type = ?) ")
				.append(" as f where e.cnt = f.cocdcnt) order by a.student_grp; ");
		data.add("N");
		data.add(acterm);
		data.add(instId);
		data.add(branchId);
		data.add("N");
		data.add(instId);
		data.add(branchId);
		data.add(CommonCodeConstant.STU_BATCH);
		Object[] array = data.toArray(new Object[data.size()]);
		Map<String, String> courseList = getJdbcTemplate().query(
				sql.toString(), array, new StGroupResultSetExtractor());

		if (courseList.size() == 0) {
			logger.error("No  record found");
			throw new NoDataFoundException();
		}

		logger.debug("DAO : Student Group Master List value"
				+ courseList.size());
		return courseList;
	}
}

class StGroupResultSetExtractor implements
		ResultSetExtractor<Map<String, String>> {
	@Override
	public Map<String, String> extractData(ResultSet rs) throws SQLException {

		Map<String, String> map = new LinkedHashMap<String, String>();
		while (rs.next()) {
			String key = (rs.getString("studentgrp_id"));
			String value = (rs.getString("student_grp"));
			map.put(key, value);
		}
		return map;
	}
}

class StudentGroupMasterSelectRowMapper implements
		RowMapper<StudentGroupMaster> {

	@Override
	public StudentGroupMaster mapRow(ResultSet rs, int arg1)
			throws SQLException {
		StudentGroupMaster stdGrpMtr = new StudentGroupMaster();
		stdGrpMtr.setDbTs(rs.getInt("DB_TS"));
		stdGrpMtr.setCourseMasterId(rs.getString("COURSEMASTER_ID"));
		stdGrpMtr.setStudentGrpId(rs.getString("STUDENTGRP_ID"));
		stdGrpMtr.setStudentGrp(rs.getString("STUDENT_GRP"));
		stdGrpMtr.setTermId(rs.getString("TERM_ID"));
		stdGrpMtr.setSecId(rs.getString("SEC_ID"));
		stdGrpMtr.setMedium(rs.getString("MEDIUM"));
		return stdGrpMtr;
	}
}
