package com.jaw.attendance.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.jaw.common.constants.CommonCodeConstant;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.batch.NoRecordFoundException;
import com.jaw.framework.dao.BaseDao;
import com.jaw.framework.sessCache.UserSessionDetails;

//StudentAttendanceMasterListDAO DAO class
@Repository
public class StudentAttendanceMasterListDAO extends BaseDao implements
		IStudentAttendanceMasterListDAO {
	// Logging
	Logger logger = Logger.getLogger(StudentAttendanceMasterListDAO.class);
	
	@Override
	public List<StudentAttendanceMasterList> getStudentAttendanceMasterList(
			final StudentAttendanceMasterList studentAttendanceMasterList,UserSessionDetails userSessionDetails)
			throws  NoRecordFoundException {
		logger.debug("Inside select method");
		logger.debug(" where class for getStudentAttendanceMasterList"
				+ studentAttendanceMasterList.toString());
		StringBuffer sql = new StringBuffer();
		List<String> data = new ArrayList<String>();
		sql.append("select ")
				.append("stam.DB_TS,")
				.append("crsl.sub_type, ")
				.append("crsl.sub_id,")
				.append("sbjm.sub_name, ")	
				.append(" stgm.coursemaster_id,")
				.append(" stgm.term_id,")
				.append(" stam.INST_ID,")
				.append(" stam.BRANCH_ID,")
				.append(" stgm.sec_id,")
				.append(" stam.AC_TERM,")
				.append(" stam.STUDENTGRP_ID,")
				.append(" stam.CRSL_ID, ")
				.append(" stam.STATUS, ")
				.append(" STAM_ID,")
				.append(" OCCUR_NO,")
				.append(" ATT_DATE, ")
				.append(" SHIFT_ID, ")
				.append(" NO_OF_SESSIONS,")
				.append(" stam.CLS_TYPE, ")
				.append(" LAB_BATCH ,")
				.append(" stam.DEL_FLG,")
				.append(" stam.R_MOD_ID,")
				.append(" stam.R_MOD_TIME, ")
				.append(" stam.R_CRE_ID, ")
				.append(" stam.R_CRE_TIME FROM stam,crsl,stgm,sbjm  ")
				.append(" where  ")
				.append("crsl.inst_id  = stam.inst_id and ")
				.append("crsl.branch_id = stam.branch_id and ")
				.append("crsl.inst_id  = sbjm.inst_id and ")
				.append("crsl.branch_id = sbjm.branch_id and ")
				.append("crsl.del_flg = sbjm.del_flg and  ")
				.append("crsl.sub_id = sbjm.sub_id and ");
		if (userSessionDetails.getInbrCategory().equals(CommonCodeConstant.IBCAT_PRE_UNIVERSITY_COLLEGE)) {
				sql.append("crsl.crsl_id = stam.crsl_id and ");
		}
				sql.append("crsl.del_flg = stam.del_flg and  ")
				
				.append(" stam.inst_id  = stgm.inst_id and")
				.append(" stam.branch_id = stgm.branch_id and ")
				.append(" stam.studentgrp_id = stgm.studentgrp_id and ")
				.append(" stam.del_flg = stam.del_flg  ")
				
				.append(" and  stam.inst_id = ? ")
				.append("and stam.branch_id = ? ")
				.append(" and stam.ac_term = ? ")
				.append("and stam.studentgrp_id = ? ");
		
		data.add(studentAttendanceMasterList.getInstId());
		data.add(studentAttendanceMasterList.getBranchId());
		data.add(studentAttendanceMasterList.getAcTerm());
		data.add(studentAttendanceMasterList.getStudentGroupId());
		if ((studentAttendanceMasterList.getAttDate() != null)
				&& (!studentAttendanceMasterList.getAttDate().equals(""))) {
			sql.append("and att_date = ? ");
			data.add(studentAttendanceMasterList.getAttDate());
		}
		if (userSessionDetails.getInbrCategory().equals(CommonCodeConstant.IBCAT_PRE_UNIVERSITY_COLLEGE)) {
		if ((studentAttendanceMasterList.getCrslId() != null)
				&& (!studentAttendanceMasterList.getCrslId().equals(""))) {
			sql.append("and stam.crsl_id = ? ");
			data.add(studentAttendanceMasterList.getCrslId());
		}
		}
		
		String[] array = data.toArray(new String[data.size()]);
		List<StudentAttendanceMasterList> result = getJdbcTemplate()
				.query(sql.toString(), array,
						new StudentAttendanceMasterListListRowMapper());
		if (result.size() == 0) {
			throw new NoRecordFoundException();
		}
		
		return result;
	}
	
	class StudentAttendanceMasterListListRowMapper implements
			RowMapper<StudentAttendanceMasterList> {
		
		@Override
		public StudentAttendanceMasterList mapRow(ResultSet rs, int arg1)
				throws SQLException {
			StudentAttendanceMasterList studentAttendanceMasterList = new StudentAttendanceMasterList();
			studentAttendanceMasterList.setInstId(rs.getString("DB_TS"));
			studentAttendanceMasterList.setInstId(rs.getString("INST_ID"));
			studentAttendanceMasterList.setBranchId(rs.getString("BRANCH_ID"));
			studentAttendanceMasterList.setStamId(rs.getString("STAM_ID"));
			studentAttendanceMasterList.setAcTerm(rs.getString("AC_TERM"));
			studentAttendanceMasterList.setStatus(rs.getString("STATUS"));
			studentAttendanceMasterList.setStudentGroupId(rs
					.getString("STUDENTGRP_ID"));
			studentAttendanceMasterList.setAttDate(rs
					.getString("ATT_DATE"));
			studentAttendanceMasterList.setSubId(rs
					.getString("SUB_ID"));
			studentAttendanceMasterList.setSubName(rs
					.getString("SUB_NAME"));
			studentAttendanceMasterList.setSubType(rs
					.getString("SUB_TYPE"));
			studentAttendanceMasterList.setStudentGroupId(rs
					.getString("studentgrp_id"));
			studentAttendanceMasterList.setCourseId(rs
					.getString("coursemaster_id"));
			studentAttendanceMasterList.setSecId(rs
					.getString("sec_id"));
			studentAttendanceMasterList.setTermId(rs
					.getString("term_id"));
			studentAttendanceMasterList.setCrslId(rs.getString("CRSL_ID"));
			studentAttendanceMasterList.setOccurNo(rs.getString("OCCUR_NO"));
			studentAttendanceMasterList.setShiftId(rs.getString("SHIFT_ID"));
			studentAttendanceMasterList.setNoOfSessions(rs
					.getInt("NO_OF_SESSIONS"));
			studentAttendanceMasterList.setClassType(rs.getString("CLS_TYPE"));
			studentAttendanceMasterList.setLabBatch(rs.getString("LAB_BATCH"));
			return studentAttendanceMasterList;
		}
	}
}
