package com.jaw.student.admission.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.framework.dao.BaseDao;

@Repository
public class AdmissionListDao extends BaseDao implements IAdmissionListDao{
	Logger logger = Logger.getLogger(AdmissionList.class);

	@Override
	public List<AdmissionList> selectAdmissionListDetails(
			AdmissionKey admissionKey) throws NoDataFoundException {
		logger.debug("DAO :Inside get Fee due List  method");
		logger.debug("DAO :Inside FeeReportListKey values : "
				+ admissionKey.toString());
		List<Object> data = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		sql.append(
				" SELECT stin.student_admis_no,admis_date,first_name,course,sec FROM stin,stum where ")
				.append(" stin.inst_id=stum.inst_id ")
				.append(" and stin.branch_id=stum.branch_id ")
				.append(" and stin.student_admis_no=stum.student_admis_no ")
				.append(" and stin.del_flg=stum.del_flg ")
				.append(" and stin.inst_id=?").append(" and stin.branch_id=?")
				.append(" and stin.admis_date between (SELECT term_start_date FROM atdt where ")
				.append(" inst_id=?").append(" and branch_id=?")
				.append(" and ac_term_sts=?")
				.append(" and del_flg=? )")
				.append("and current_date")
				.append(" and stin.del_flg=? ")
				.append(" order by stum.student_admis_no;  ");

		data.add(admissionKey.getInstId());
		data.add(admissionKey.getBranchId());
		data.add(admissionKey.getInstId());
		data.add(admissionKey.getBranchId());
		data.add(admissionKey.getAcademicStatus());
		data.add("N");
		data.add("N");

		Object[] array = data.toArray(new Object[data.size()]);
		List<AdmissionList> selectedListAdmissionList = null;
		selectedListAdmissionList = getJdbcTemplate().query(sql.toString(),
				array, new AdmissionListRowMapper());

		/*
		 * if (selectedListFeeReportList.size() == 0) { throw new
		 * NoDataFoundException(); }
		 */
		logger.debug("DAO : Number of New Admission"
				+ selectedListAdmissionList.size());
		if (selectedListAdmissionList.size() == 0) {
			throw new NoDataFoundException();
		}
		return selectedListAdmissionList;
	}
	class AdmissionListRowMapper implements RowMapper<AdmissionList> {

		@Override
		public AdmissionList mapRow(ResultSet rs, int arg1) throws SQLException {

			AdmissionList admissionList = new AdmissionList();
			admissionList.setAdmissionNumber(rs.getString("STUDENT_ADMIS_NO"));
			admissionList.setAdmissionDate(rs.getString("ADMIS_DATE"));
			admissionList.setStudentName(rs.getString("FIRST_NAME"));
			admissionList.setCourse(rs.getString("COURSE"));
			admissionList.setSection(rs.getString("SEC"));
			return admissionList;
		}

	}
	
	@Override
	public List<AdmissionCountList> selectAdmissionCountListDetails(
			AdmissionKey admissionKey) throws NoDataFoundException {
		logger.debug("DAO :Inside get New Admission List  method");
		logger.debug("DAO :Inside AdmissionListKey values : "
				+ admissionKey.toString());
		List<Object> data = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		sql.append(
				" SELECT course,count(stum.student_admis_no) as TOTAL FROM stin,stum where ")
				.append(" stin.inst_id=stum.inst_id ")
				.append(" and stin.branch_id=stum.branch_id ")
				.append(" and stin.student_admis_no=stum.student_admis_no ")
				.append(" and stin.del_flg=stum.del_flg ")
				.append(" and stin.inst_id=?").append(" and stin.branch_id=?")
				.append(" and stin.admis_date between (SELECT term_start_date FROM atdt where ")
				.append(" inst_id=?").append(" and branch_id=?")
				.append(" and ac_term_sts=?")
				.append(" and del_flg=? )")
				.append("and current_date")
				.append(" and stin.del_flg=? ")
				.append(" group by stum.course ")
				.append(" order by stum.course;  ");

		data.add(admissionKey.getInstId());
		data.add(admissionKey.getBranchId());
		data.add(admissionKey.getInstId());
		data.add(admissionKey.getBranchId());
		data.add(admissionKey.getAcademicStatus());
		data.add("N");
		data.add("N");

		Object[] array = data.toArray(new Object[data.size()]);
		List<AdmissionCountList> selectedListCountAdmissionList = null;
		selectedListCountAdmissionList = getJdbcTemplate().query(sql.toString(),
				array, new AdmissionCountListRowMapper());

		/*
		 * if (selectedListFeeReportList.size() == 0) { throw new
		 * NoDataFoundException(); }
		 */
		logger.debug("DAO : Number of New Admission"
				+ selectedListCountAdmissionList.size());
		System.out.println("Count List to Service"+selectedListCountAdmissionList);
		if (selectedListCountAdmissionList.size() == 0) {
			throw new NoDataFoundException();
		}
		return selectedListCountAdmissionList;
	}
	class AdmissionCountListRowMapper implements RowMapper<AdmissionCountList> {

		@Override
		public AdmissionCountList mapRow(ResultSet rs, int arg1) throws SQLException {

			AdmissionCountList admissionCountList = new AdmissionCountList();
			admissionCountList.setCourse(rs.getString("COURSE"));
			admissionCountList.setCount(rs.getInt("TOTAL"));
			return admissionCountList;
		}

	}
	
	
}
