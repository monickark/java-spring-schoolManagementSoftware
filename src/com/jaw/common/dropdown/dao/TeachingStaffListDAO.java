package com.jaw.common.dropdown.dao;

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
import com.jaw.framework.sessCache.UserSessionDetails;
import com.jaw.staff.dao.StaffDetails;
@Repository
public class TeachingStaffListDAO extends BaseDao implements ITeachingStaffListDAO{
	// Logging
		Logger logger = Logger.getLogger(TeachingStaffListDAO.class);

		@Override
	public List<StaffDetails> selectTeachingStaffList(final UserSessionDetails userSessionDetails)
			throws NoDataFoundException {
			logger.debug("Inside select Teaching Staff method for Dropdown");

			StringBuffer sql = new StringBuffer();
			sql.append("select ")
			.append("STAFF_ID,")
			.append("STAFF_NAME ")
			.append(" from stfd ")
			.append("where ")
			.append("DEL_FLG=?  ")
			.append("and STAFF_CATEGORY1=?")
			.append(" and INST_ID=?  ")
				.append(" and BRANCH_ID=?")
				.append(" order by STAFF_NAME asc");
			logger.debug( "select query :" + sql.toString());

			List<StaffDetails> staffDetails = null;

			staffDetails = getJdbcTemplate().query(sql.toString(),
					new PreparedStatementSetter() {

						@Override
						public void setValues(PreparedStatement pss)
								throws SQLException {
							pss.setString(1, "N");
							pss.setString(2, "TCH");
							pss.setString(3, userSessionDetails.getInstId());
							pss.setString(4, userSessionDetails.getBranchId());

						}

					}, new StaffDetailsRowmapper());
			if (staffDetails.size() == 0) {
				throw new NoDataFoundException();
			}
			return staffDetails;
		}

		
		
	}

	class StaffDetailsRowmapper implements RowMapper<StaffDetails> {

		@Override
		public StaffDetails mapRow(ResultSet rs, int arg1) throws SQLException {

			StaffDetails staffDetails = new StaffDetails();
			staffDetails.setStaffId(rs.getString("STAFF_ID"));
			staffDetails.setStaffName(rs.getString("STAFF_NAME"));

			return staffDetails;
		}
	}

