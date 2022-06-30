package com.jaw.attendance.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.jaw.common.constants.ApplicationConstant;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.framework.dao.BaseDao;

//AttendanceListDAO DAO class
@Repository
public class AttendanceListDAO extends BaseDao implements IAttendanceListDAO {
	// Logging
	Logger logger = Logger.getLogger(AttendanceListDAO.class);

	@Override
	public List<AttendanceList> getAttendanceList(
			final AttendanceList attendanceList) throws NoDataFoundException {
		logger.debug("Inside select method");
		logger.debug(" where class for getAttendanceList"
				+ attendanceList.toString());
		StringBuffer sql = new StringBuffer();
		List<String> data = new ArrayList<String>();
		sql.append("select ").append("STAM_ID,").append("stum.roll_number,")
				.append(" stum.reg_no,").append(" stum.student_admis_no,")
				.append(" stum.student_name,").append("is_present,")
				.append("absentee_rmks ")
				.append(" from stum left join stad on ")
				.append(" stum.inst_id = stad.inst_id and")
				.append(" stum.branch_id = stad.branch_id and ")
				.append(" stum.student_admis_no = stad.student_admis_no and ")
				.append("stum.del_flg = stad.del_flg ")
				.append("and stam_id = ? ").append("where ")
				.append(" stum.inst_id = ? ").append("and stum.branch_id = ? ")
				.append("and stum.del_flg = ? ")
				.append("and stum.academic_year = ? ");
		data.add(attendanceList.getStamId());
		data.add(attendanceList.getInstId());
		data.add(attendanceList.getBranchId());
		data.add("N");
		data.add(attendanceList.getAcTerm());
		if (attendanceList.getSubType().equals(
				ApplicationConstant.SUB_TYPE_LANGUAGE2)) {
			sql.append(" and stum.second_lang = ?");

			data.add(attendanceList.getSubId());
		}
		if (attendanceList.getSubType().equals(
				ApplicationConstant.SUB_TYPE_LANGUAGE3)) {
			sql.append(" and stum.third_lang = ?");

			data.add(attendanceList.getSubId());
		}

		if (attendanceList.getSubType().equals(
				ApplicationConstant.SUB_TYPE_ELECTIVE1)) {
			sql.append(" and stum.elective_1 =  ?");

			data.add(attendanceList.getSubId());
		}
		if (attendanceList.getSubType().equals(
				ApplicationConstant.SUB_TYPE_ELECTIVE2)) {
			sql.append(" and stum.elective_2 = ?");

			data.add(attendanceList.getSubId());
		}
		sql.append("and stum.STUDENTGRP_ID=? order by roll_number asc ");
		data.add(attendanceList.getStudentGroupId());
		String[] array = data.toArray(new String[data.size()]);
		
		List<AttendanceList> result = getJdbcTemplate().query(sql.toString(),
				array, new AttendanceListListRowMapper());
		if (result.size() == 0) {
			throw new NoDataFoundException();
		}

		return result;
	}

	class AttendanceListListRowMapper implements RowMapper<AttendanceList> {

		@Override
		public AttendanceList mapRow(ResultSet rs, int arg1)
				throws SQLException {
			AttendanceList attendanceList = new AttendanceList();
			attendanceList.setStamId(rs.getString("STAM_ID"));
			attendanceList.setAdmissionNumber(rs.getString("STUDENT_ADMIS_NO"));
			attendanceList.setStudentName(rs.getString("STUDENT_NAME"));
			attendanceList.setIsAbsent(rs.getString("IS_PRESENT"));
			attendanceList.setRemark(rs.getString("ABSENTEE_RMKS"));
			attendanceList.setRegNo(rs.getString("reg_no"));
			return attendanceList;
		}
	}
}
