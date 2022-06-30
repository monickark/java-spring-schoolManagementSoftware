package com.jaw.attendance.dao;

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

//AttendanceListDAO DAO class
@Repository
public class ViewAttendanceListDAO extends BaseDao implements
		IViewAttendanceListDAO {
	// Logging
	Logger logger = Logger.getLogger(ViewAttendanceListDAO.class);

	@Override
	public List<ViewAttendanceList> getViewAttendanceList(
			final ViewAttendanceList attendanceList, String incLabAttendance)
			throws NoDataFoundException {
		logger.debug("Inside select method");
		logger.debug(" where class for getAttendanceList"
				+ attendanceList.toString()+ "incLabAttendance :"+incLabAttendance);
		StringBuffer sql = new StringBuffer();

		sql.append(
				"(select distinct(att_date), is_present, '' as remarks from stad, stam where")
				.append(" stad.inst_id = stam.inst_id and ")
				.append(" stad.branch_id = stam.branch_id and ")
				.append(" stad.stam_id = stam.stam_id and ")
				.append(" stad.del_flg = stam.del_flg and ")
				.append(" stad.inst_id = ? and ")
				.append(" stad.branch_id = ? and ")
				.append(" stam.ac_term = ? and ")
				.append(" studentgrp_id = ? and ")
				.append(" student_admis_no = ?  ")
				.append(" and stad.del_flg = ? ")
				.append(" and is_present = 'A' ");
		if (incLabAttendance.equals("N")) {
			sql.append(" and cls_type = 'CLST' ");
		}

		sql.append("order by att_date ) ")

				.append(" union (select adate, 'P' as is_present, '' as remarks from ")
				.append(" (select distinct(att_date) as adate from stam where ")
				.append(" stam.inst_id = ? and ")
				.append(" stam.branch_id =  ? and ")
				.append(" stam.ac_term = ? and ")
				.append(" stam.studentgrp_id = ? and ")
				.append(" stam.del_flg = ? and ")
				.append(" cls_type = 'CLST' and crsl_id in ( ")
				.append(" select crsl_id from stum,crsl where ")
				.append(" stum.inst_id = crsl.inst_id and ")
				.append(" stum.branch_id = crsl.branch_id and ")
				.append(" stum.del_flg = crsl.del_flg and ")
				.append(" stum.course = crsl.coursemaster_id and ")
				/*.append(" stum.standard = crsl.term_id and ")*/
				.append(" stum.inst_id = ? and ")
				.append(" stum.branch_id = ? and ")
				.append(" stum.del_flg = ? and ")
				.append(" stum.student_admis_no = ? and ")
				.append(" stum.academic_year = ? and ")
				.append(" ((sub_type = 'C') ")
				.append(" or (sub_type = 'L2' and stum.second_lang = crsl.sub_id) ")
				.append(" or (sub_type = 'L3' and stum.third_lang = crsl.sub_id)  ")
				.append(" or (sub_type = 'E1' and stum.elective_1 = crsl.sub_id) ")
				.append(" or (sub_type = 'E2' and stum.elective_2 = crsl.sub_id))) order by att_date) as a left outer join ")
				.append(" (select distinct(att_date) as bdate from stad, stam where ")
				.append(" stad.inst_id = stam.inst_id and ")
				.append(" stad.branch_id = stam.branch_id and ")
				.append(" stad.stam_id = stam.stam_id and ")
				.append(" stad.del_flg = stam.del_flg and ")
				.append(" stad.inst_id = ? and ")
				.append(" stad.branch_id = ? and ")
				.append(" stam.ac_term = ? ")
				.append(" and studentgrp_id = ? ")
				.append(" and student_admis_no = ? ")
				.append(" and stad.del_flg = ?  ")
				.append(" and is_present = 'A' and cls_type = 'CLST' ")
				.append(" order by att_date ) as b on adate = bdate where bdate is null ) ")

				.append(" union")
				.append(" (select hol_date as date, 'W' as is_present, '' as remarks from holm where ")
				.append(" holm.inst_id = ? and ")
				.append(" holm.branch_id = ? and ")
				.append(" holm.ac_term = ?  ")
				.append(" and holm.del_flg = ? ")

				.append(" and is_wkly_holiday = 'Y' order by hol_date)")
				.append(" union")
				.append(" (select hol_date as date, 'H' as is_present, acal.item_desc as remarks from holm,acal where holm.inst_id = acal.inst_id and")
				.append(" holm.branch_id = acal.branch_id and")
				.append(" holm.ac_term = acal.ac_term and")
				.append(" holm.del_flg = acal.del_flg and")
				.append(" holm.hol_date >= acal.item_start_date and")
				.append(" holm.hol_date <= acal.item_end_date and")
				.append(" holm.inst_id = ? and ")
				.append(" holm.branch_id = ? and ")
				.append(" holm.ac_term = ? and ")
				.append(" holm.del_flg = ? ")
				.append(" and is_wkly_holiday = 'N'")
				.append(" and holm.studentgrp_id = ? order by hol_date)")

				.append(" union")
				.append(" (select hol_date, 'H' as is_present, ahol.ah_rmks from holm,ahol where holm.inst_id = ahol.inst_id and")
				.append(" holm.branch_id = ahol.branch_id and")
				.append(" holm.ac_term = ahol.ac_term and")
				.append(" holm.del_flg = ahol.del_flg and")
				.append(" holm.studentgrp_id = ahol.studentgrp_id and")
				.append(" holm.hol_Date >=ahol.hol_from_date and")
				.append(" holm.hol_date <= ahol.hol_to_date and")
				.append(" holm.inst_id = ? and ")
				.append(" holm.branch_id = ? and ")
				.append(" holm.ac_term = ? and ").append(" holm.del_flg = ? ")
				.append(" and is_wkly_holiday = 'N'")
				.append(" and holm.studentgrp_id = ? order by att_date)");

		List<ViewAttendanceList> result = getJdbcTemplate().query(
				sql.toString(), new PreparedStatementSetter() {

					@Override
					public void setValues(PreparedStatement pss)
							throws SQLException {
						pss.setString(1, attendanceList.getInstId());
						pss.setString(2, attendanceList.getBranchId());
						pss.setString(3, attendanceList.getAcademicTerm());
						pss.setString(4, attendanceList.getStudentGroupId());
						pss.setString(5, attendanceList.getAdmissionNumber());
						pss.setString(6, "N");

						pss.setString(7, attendanceList.getInstId());
						pss.setString(8, attendanceList.getBranchId());
						pss.setString(9, attendanceList.getAcademicTerm());
						pss.setString(10, attendanceList.getStudentGroupId());
						pss.setString(11, "N");

						pss.setString(12, attendanceList.getInstId());
						pss.setString(13, attendanceList.getBranchId());
						pss.setString(14, "N");
						pss.setString(15, attendanceList.getAdmissionNumber());
						pss.setString(16, attendanceList.getAcademicTerm());

						pss.setString(17, attendanceList.getInstId());
						pss.setString(18, attendanceList.getBranchId());
						pss.setString(19, attendanceList.getAcademicTerm());
						pss.setString(20, attendanceList.getStudentGroupId());
						pss.setString(21, attendanceList.getAdmissionNumber());
						pss.setString(22, "N");

						pss.setString(23, attendanceList.getInstId());
						pss.setString(24, attendanceList.getBranchId());
						pss.setString(25, attendanceList.getAcademicTerm());
						pss.setString(26, "N");

						pss.setString(27, attendanceList.getInstId());
						pss.setString(28, attendanceList.getBranchId());
						pss.setString(29, attendanceList.getAcademicTerm());
						pss.setString(30, "N");
						pss.setString(31, "NA");

						pss.setString(32, attendanceList.getInstId());
						pss.setString(33, attendanceList.getBranchId());
						pss.setString(34, attendanceList.getAcademicTerm());
						pss.setString(35, "N");
						pss.setString(36, attendanceList.getStudentGroupId());
					}
				}, new AttendanceListListForViewRowMapper());
		if (result.size() == 0) {
			throw new NoDataFoundException();
		}
		logger.debug("List size :" + result.size());
		return result;
	}

	@Override
	public List<ViewAttendanceList> getSubjectWiseAttendance(
			final ViewAttendanceList attendanceList)
			throws NoDataFoundException {
		logger.debug("Inside select method");
		logger.debug(" where class for getAttendanceList"
				+ attendanceList.toString());
		StringBuffer sql = new StringBuffer();

		sql.append(
				"select sbjm.sub_name,is_present,absentee_rmks from stam, stad, crsl, sbjm where ")
				.append(" stam.inst_id = stad.inst_id ")
				.append(" and stam.branch_id = stad.branch_id ")
				.append(" and stam.stam_id = stad.stam_id ")
				.append(" and stam.del_flg = stad.del_flg ")
				.append(" and stam.inst_id = crsl.inst_id ")
				.append(" and stam.branch_id = crsl.branch_id ")
				.append(" and stam.crsl_id = crsl.crsl_id ")
				.append(" and stam.del_flg = crsl.del_flg")
				.append(" and crsl.inst_id = sbjm.inst_id ")
				.append(" and crsl.branch_id = sbjm.branch_id ")
				.append(" and crsl.del_flg = sbjm.del_flg ")
				.append(" and crsl.sub_id = sbjm.sub_id ")
				.append(" and stam. inst_id = ? ")
				.append(" and stam.branch_id = ? ")
				.append(" and stam.del_flg = ? ")
				.append(" and stam.ac_term = ? ")
				.append(" and stam.studentgrp_id = ? ")
				.append(" and stam.att_date = ? ")
				.append(" and stad.student_admis_no = ?  ");
		List<ViewAttendanceList> result = getJdbcTemplate().query(
				sql.toString(), new PreparedStatementSetter() {

					@Override
					public void setValues(PreparedStatement pss)
							throws SQLException {
						pss.setString(1, attendanceList.getInstId());
						pss.setString(2, attendanceList.getBranchId());
						pss.setString(3, "N");
						pss.setString(4, attendanceList.getAcademicTerm());
						pss.setString(5, attendanceList.getStudentGroupId());
						pss.setString(6, attendanceList.getDate());
						pss.setString(7, attendanceList.getAdmissionNumber());
					}
				}, new SubjectAttendanceForViewRowMapper());
		if (result.size() == 0) {
			throw new NoDataFoundException();
		}
		logger.debug("List size :" + result.size());
		return result;
	}

	class AttendanceListListForViewRowMapper implements
			RowMapper<ViewAttendanceList> {

		@Override
		public ViewAttendanceList mapRow(ResultSet rs, int arg1)
				throws SQLException {

			ViewAttendanceList attendanceList = new ViewAttendanceList();
			attendanceList.setDate(rs.getString("att_date"));
			attendanceList.setIsAbsent(rs.getString("is_present"));
			attendanceList.setRemark(rs.getString("remarks"));
			return attendanceList;
		}
	}

	class SubjectAttendanceForViewRowMapper implements
			RowMapper<ViewAttendanceList> {

		@Override
		public ViewAttendanceList mapRow(ResultSet rs, int arg1)
				throws SQLException {

			ViewAttendanceList attendanceList = new ViewAttendanceList();

			attendanceList.setSubId(rs.getString("sub_name"));
			attendanceList.setRemark(rs.getString("absentee_rmks"));
			attendanceList.setIsAbsent(rs.getString("is_present"));
			return attendanceList;
		}
	}

}
