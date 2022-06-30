package com.jaw.user.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.framework.dao.BaseDao;

@Repository
public class UserMenuProfileList extends BaseDao implements
		IUserMenuProfileList {

	Logger logger = Logger.getLogger(UserMenuProfileList.class);

	@Override
	public List<String> selectMenuProfile(final String instid,
			final String branchId) throws NoDataFoundException {
		List<String> resultuser = null;
		StringBuffer query = new StringBuffer();
		query.append("SELECT distinct(user_menu_profile) ")
				.append("from user, request where ")
				.append("user.inst_id = request.inst_id and ")
				.append("user.branch_id = request.branch_id and ")
				.append("user.user_id = request.user_id and ")
				.append("request.inst_id = ? and ")
				.append("request.branch_id =? and ")
				.append("request.rqst_status != ?");
		logger.debug("select query :" + query.toString());
		resultuser = getJdbcTemplate().query(query.toString(),
				new PreparedStatementSetter() {
					@Override
					public void setValues(java.sql.PreparedStatement ps)
							throws SQLException {

						ps.setString(1, instid);
						ps.setString(2, branchId);
						ps.setString(3, "C");

					}

				}, new MenuProfileRowMapper());
		if (resultuser.size() == 0) {
			throw new NoDataFoundException();
		}
		return resultuser;
	}

	class MenuProfileRowMapper implements RowMapper<String> {

		@Override
		public String mapRow(ResultSet rs, int arg1) throws SQLException {
			return rs.getString("USER_MENU_PROFILE");

		}
	}

}