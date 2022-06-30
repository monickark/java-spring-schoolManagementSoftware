package com.jaw.staff.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.jaw.common.constants.ApplicationConstant;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.framework.dao.BaseDao;
import com.jaw.login.controller.LoginController;

@Repository
public class StaffMasterListDao extends BaseDao implements IStaffMasterListDao {
	Logger logger = Logger.getLogger(LoginController.class);

	@Override
	public List<StaffMaster> getListForClassTeacherAllotment(
			final String branchId, final String instId,
			final String acTerm) throws NoDataFoundException {

		logger.debug("Inside get list method in StaffMasterDao : instId"+ instId+" branch id:"+branchId+" acTerm:"+acTerm);

		StringBuffer sql = new StringBuffer();
		
		sql.append("select ")
				.append(" stfm.STAFF_ID,")
				.append(" stfm.STAFF_NAME")
				.append(" from stfm,stfd where ")
				.append(" stfm.staff_id not in ")
				.append(" (select staff_id from clta where")
				.append(" clta.ac_term=? and  ")
				.append(" clta.inst_id=? and  ")
				.append(" clta.branch_id=? and  ")
				.append(" clta.DEL_FLG=?) and ")
				.append(" stfm.INST_ID=stfd.INST_ID and ")
				.append("  stfm.BRANCH_ID=stfd.BRANCH_ID and ")
				.append("  stfm.DEL_FLG=stfd.del_flg and ")
				.append("  stfm.staff_id=stfd.staff_id and ")
				.append("  stfd.staff_category1=? and ")
				.append(" stfm.INST_ID=? and ")
				.append(" stfm.BRANCH_ID=? and ")
				.append(" stfm.DEL_FLG=?  order by STAFF_NAME ");

		logger.debug("select query in StaffMasterDao:" + sql.toString());

		List<StaffMaster> staffList = getJdbcTemplate().query(sql.toString(),
				new PreparedStatementSetter() {

					@Override
					public void setValues(java.sql.PreparedStatement pss)
							throws SQLException {
						pss.setString(1, acTerm);
						pss.setString(2, instId);
						pss.setString(3, branchId);
						pss.setString(4, "N");
						pss.setString(5, ApplicationConstant.TEACHING_STAFF);
						pss.setString(6, instId);
						pss.setString(7, branchId);
						pss.setString(8, "N");
					}
				}, new StaffMasterForClassTeacherAllotmentRowMapper());
		if (staffList.size() == 0) {
			throw new NoDataFoundException();
		}
		return staffList;
	}

	class StaffMasterForClassTeacherAllotmentRowMapper implements
			RowMapper<StaffMaster> {
		@Override
		public StaffMaster mapRow(ResultSet rs, int arg1) throws SQLException {
			StaffMaster standard = new StaffMaster();
			standard.setStaffId((rs.getString("STAFF_ID")));
			standard.setStaffName((rs.getString("STAFF_NAME")));
			return standard;
		}
	}
}
