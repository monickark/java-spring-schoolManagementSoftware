package com.jaw.staff.dao;

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
public class StaffInformationListDao extends BaseDao implements
		IStaffInformationListDao {
	// Logging
	Logger logger = Logger.getLogger(StaffInformationListDao.class);
	int[] ret;
	
	@Override
	public List<StaffInformationList> getStaffListForInformation(
			StaffInformationList staffInformationList)
			throws NoDataFoundException {
		List<StaffInformationList> results = null;
		List<String> data = new ArrayList<String>();
		StringBuffer query = new StringBuffer();
		query.append("SELECT ")
				.append("m.STAFF_ID,")
				.append("m.STAFF_NAME,")
				.append("MOBILE,")
				.append("EMERGENCY_CONTACT,")
				.append("DEPT_ID,")
				.append("DESIGNATION, ")
				.append("STAFF_CATEGORY1, ")
				.append("STAFF_CATEGORY2, ")
				.append(" USER_MENU_PROFILE")
				.append(" FROM staff_master m,staff_details d,user_link u where ")
				.append(" m.staff_id=d.staff_id and ")
				.append(" m.staff_id=u.link_id and ")
				.append(" m.inst_id=u.inst_id and ")
				.append(" m.branch_id=u.branch_id and ")
				.append(" m.inst_id=d.inst_id and ")
				.append(" m.branch_id=d.branch_id and ")
				.append(" m.del_flg=d.del_flg and ")
				.append(" m.inst_id=? and ")
				.append(" m.branch_id=? and ")
				.append(" m.del_flg= ?");
		data.add(staffInformationList.getInstId());
		data.add(staffInformationList.getBranchId());
		data.add("N");
		if ((staffInformationList.getStaffId() != "")
				&& (staffInformationList.getStaffId() != null)) {
			query.append(" and m.staff_id=?  ");
			logger.debug("Staff Id :" + staffInformationList.getStaffId());
			data.add(staffInformationList.getStaffId());
		}
		if ((staffInformationList.getStaffName() != null)
				&& (staffInformationList.getStaffName() != "")) {
			query.append(" and m.STAFF_NAME like ?  ");
			data.add(staffInformationList.getStaffName() + "%");
			logger.debug("Staff Name :" + staffInformationList.getStaffName());
		}
		if ((staffInformationList.getDeptId() != null)
				&& (staffInformationList.getDeptId() != "")) {
			query.append(" and DEPT_ID=?  ");
			data.add(staffInformationList.getDeptId());
			logger.debug("Dept Id :" + staffInformationList.getDeptId());
		}
		if ((staffInformationList.getDesignation() != null)
				&& (staffInformationList.getDesignation() != "")) {
			query.append(" and DESIGNATION=?  ");
			data.add(staffInformationList.getDesignation());
			logger.debug("Designation :"
					+ staffInformationList.getDesignation());
		}
		if ((staffInformationList.getStaffCategory1() != null)
				&& (staffInformationList.getStaffCategory1() != "")) {
			query.append(" and STAFF_CATEGORY1=?  ");
			data.add(staffInformationList.getStaffCategory1());
			logger.debug("Category 1 :"
					+ staffInformationList.getStaffCategory1());
		}
		if ((staffInformationList.getStaffCategory2() != null)
				&& (staffInformationList.getStaffCategory2() != "")) {
			query.append(" and STAFF_CATEGORY2=?  ");
			data.add(staffInformationList.getStaffCategory2());
			logger.debug("Category 2 :"
					+ staffInformationList.getStaffCategory2());
		}
		if ((staffInformationList.getMenuProfile() != null)
				&& (staffInformationList.getMenuProfile() != "")) {
			query.append(" and u.USER_MENU_PROFILE=?  ");
			data.add(staffInformationList.getMenuProfile());
			logger.debug("Category 2 :"
					+ staffInformationList.getMenuProfile());
		}
		
		String[] array = data.toArray(new String[data.size()]);
		logger.debug("Where clause conditions size :" + array.length);
		results = getJdbcTemplate().query(query.toString(), array,
				new StaffInformationListRowMapper());
		if (results.size() == 0) {
			throw new NoDataFoundException();
		}
		return results;
	}
	
	class StaffInformationListRowMapper implements
			RowMapper<StaffInformationList> {
		@Override
		public StaffInformationList mapRow(ResultSet rs, int arg1)
				throws SQLException {
			StaffInformationList standard = new StaffInformationList();
			standard.setStaffId((rs.getString("STAFF_ID")));
			standard.setStaffName((rs.getString("STAFF_NAME")));
			standard.setDeptId(rs.getString("DEPT_ID"));
			standard.setDesignation(rs.getString("DESIGNATION"));
			standard.setMobile(rs.getString("MOBILE"));
			standard.setEmergency(rs.getString("EMERGENCY_CONTACT"));
			standard.setStaffCategory1(rs.getString("STAFF_CATEGORY1"));
			standard.setStaffCategory2(rs.getString("STAFF_CATEGORY2"));
			standard.setMenuProfile(rs.getString("USER_MENU_PROFILE"));
			return standard;
		}
		
	}
}