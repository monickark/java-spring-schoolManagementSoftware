package com.jaw.user.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.jaw.common.constants.ApplicationConstant;
import com.jaw.common.constants.MenuOptionConstant;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.framework.dao.BaseDao;

@Repository
public class UserManagementListDao extends BaseDao implements
		IUserManagementListDao {
	
	Logger logger = Logger.getLogger(UserManagementListDao.class);
	
	// To check UserManagement name is valid or not
	
	@Override
	public List<UserManagement> selectUserManagementListFromUserManagement(
			UserManagement user) throws NoDataFoundException {
		List<UserManagement> resultUserManagement = null;
		List<String> data = new ArrayList<String>();
		StringBuffer query = new StringBuffer();
		query.append("SELECT ").append("u.user_ID,").append("u.INST_ID,")
				.append("ul.user_id,").append("ul.USER_MENU_PROFILE,")
				.append("user_ENABLE_FLAG,").append("LAST_LOGIN_TIME,")
				.append("ED_REMARKS")
				.append(" from user u,user_link ul where ")
				.append(" (u.del_flg=ul.del_flg and  ")
				.append(" u.del_flg='N' and  ")
				.append("u.user_id=ul.user_id and ")
				.append("u.inst_id = ?  ) ");
		data.add(user.getInstId());
		if (user.getMenuOptionId().equals(
				MenuOptionConstant.USER_MANAGEMENT_SUPERADMIN)) {
			query.append("and( profile_group=? or ");
			data.add(ApplicationConstant.PG_MGMT);
			query.append("USER_MENU_PROFILE=?)");
			data.add(ApplicationConstant.BRANCH_ADMIN);
		}
		else if (user.getMenuOptionId().equals(
				MenuOptionConstant.USER_MANAGEMENT_BRANCHADMIN)) {
			query.append(" and u.branch_id = ? ");
			data.add(user.getBranchId());
			query.append("and USER_MENU_PROFILE!=?  ");
			data.add(ApplicationConstant.BRANCH_ADMIN);
		}
		
		if ((user.getUserEnableFlag() != null)
				&& (user.getUserEnableFlag() != "")) {
			query.append("and user_ENABLE_FLAG=? ");
			data.add(user.getUserEnableFlag());
		}
		if ((user.getProfileGroup() != null) && (user.getProfileGroup() != "")) {
			query.append("and ul.PROFILE_GROUP=?");
			data.add(user.getProfileGroup());
		}
		
		if ((user.getUserId() != null) && (user.getUserId() != "")) {
			query.append("and ul.User_ID=?");
			data.add(user.getUserId());
		}
		
		String[] array = data.toArray(new String[data.size()]);
		
		resultUserManagement = getJdbcTemplate().query(query.toString(), array,
				new UserManagementRowMapper());
		if (resultUserManagement.size() == 0) {
			throw new NoDataFoundException();
		}
		
		return resultUserManagement;
	}
	
	class UserManagementRowMapper implements RowMapper {
		
		@Override
		public UserManagement mapRow(ResultSet rs, int arg1)
				throws SQLException {
			UserManagement UserManagement = new UserManagement();
			UserManagement.setUserId(rs.getString("user_id"));
			UserManagement.setInstId(rs.getString("INST_ID"));
			UserManagement
					.setUserMenuProfile(rs.getString("USER_MENU_PROFILE"));
			UserManagement.setUserEnableFlag(rs.getString("user_ENABLE_FLAG"));
			UserManagement.setLoginTime(rs.getDate("LAST_LOGIN_TIME"));
			UserManagement.setRemarks(rs.getString("ED_REMARKS"));
			
			return UserManagement;
		}
	}
	
}