package com.jaw.user.dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.framework.dao.BaseDao;

@Repository
public class UserListDao extends BaseDao implements IUserListDao {

	Logger logger = Logger.getLogger(UserListDao.class);

	// To check User name is valid or not

	@Override
	public List<User> selectUserListFromUserManagement(String instid,
			String branchId, User user) throws NoDataFoundException {
		List<User> resultuser = null;
		List<String> data = new ArrayList<String>();
		StringBuffer query = new StringBuffer();
		query.append("SELECT ").append("USER_ID,").append("INST_ID,")
				.append("BRANCH_ID,").append("USER_MENU_PROFILE,")
				.append("USER_ENABLE_FLAG,").append("LAST_LOGIN_TIME,")
				.append("ED_REMARKS").append(" from user where ")
				.append("inst_id = ? and ").append("branch_id = ? ");
		data.add(instid);
		data.add(branchId);
		if ((user.getUserEnableFlag() != null)
				&& (user.getUserEnableFlag() != "")) {
			query.append("and USER_ENABLE_FLAG=? ");
			data.add(user.getUserEnableFlag());
		}

		if ((user.getUserId() != null) && (user.getUserId() != "")) {
			query.append("and USER_ID=?");
			data.add(user.getUserId());
		}

		String[] array = data.toArray(new String[data.size()]);

		resultuser = getJdbcTemplate().query(query.toString(), array,
				new UserRowMapper());
		if (resultuser.size() == 0) {
			throw new NoDataFoundException();
		}

		return resultuser;
	}

	class UserRowMapper implements RowMapper {

		@Override
		public User mapRow(ResultSet rs, int arg1) throws SQLException {
			User user = new User();
			user.setUserId(rs.getString("user_id"));
			user.setInstId(rs.getString("INST_ID"));
			user.setBranchId(rs.getString("BRANCH_ID"));
			user.setUserEnableFlag(rs.getString("USER_ENABLE_FLAG"));
			user.setLoginTime(rs.getDate("LAST_LOGIN_TIME"));
			user.setRemarks(rs.getString("ED_REMARKS"));

			return user;
		}
	}

	@Override
	public void insertUserList(final List<User> userList)
			throws DuplicateEntryException {
		StringBuffer sql = new StringBuffer("insert into user(")
				.append("DB_TS, ")
				.append("INST_ID, ")
				.append("BRANCH_ID, ")
				.append("USER_ID, ")
				.append("PWD, ")
				.append("LAST_LOGIN_TIME, ")
				.append("LAST_LOGIN_IP_ADDRESS, ")
				.append("PWD_EXPIRY_DATE, ")
				.append("NO_OF_INVALID_ATTEMPTS, ")
				.append("LAST_LOGOUT_TIME, ")
				.append("PWD_RESET_DATE, ")
				.append("SESSION_ID, ")
				.append("TOTAL_NO_OF_LOGIN, ")
				.append("USER_ENABLE_FLAG, ")
				.append("DEL_FLG, ")
				.append("R_MOD_ID, ")
				.append("R_MOD_TIME,")
				.append("R_CRE_ID, ")
				.append("R_CRE_TIME, ")
				.append("PASSWORD_CHANGE_REQUIRED_FLG, ")
				.append("ED_REMARKS")

				.append(") values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,now(),?,now(),?,?)");
		try {
			int[] a = getJdbcTemplate().batchUpdate(sql.toString(),
					new BatchPreparedStatementSetter() {

						@Override
						public void setValues(PreparedStatement ps, int i)
								throws SQLException {

							User usr = userList.get(i);

							ps.setInt(1, usr.getDb_ts());
							ps.setString(2, usr.getInstId());
							ps.setString(3, usr.getBranchId());
							ps.setString(4, usr.getUserId());
							ps.setString(5, usr.getPassword());
							ps.setDate(6, (Date) usr.getLoginTime());
							ps.setString(7, usr.getIpAddress());
							ps.setString(8, usr.getPasswordExpiryDate());
							ps.setInt(9, usr.getNoOfAttempts());
							ps.setString(10, usr.getLastLogoutTime());
							ps.setDate(11, (Date) usr.getPasswordResetDate());
							ps.setString(12, usr.getSessionId());
							ps.setString(13, usr.getTotalNoOfLogin());
							ps.setString(14, usr.getUserEnableFlag());
							ps.setString(15, usr.getDeleteFlag());
							ps.setString(16, usr.getrModId());
							ps.setString(17, usr.getrCreId());
							ps.setString(18, usr.getPasswordResetFlag());
							ps.setString(19, usr.getRemarks());
						}

						@Override
						public int getBatchSize() {
							return userList.size();
						}
					}

			);
		} catch (DuplicateKeyException duplicateKeyException) {
			throw new DuplicateEntryException();
		}
	}

	@Override
	public List<User> getUserListForBatchPgm(final UserKey userKey)
			throws NoDataFoundException {
		List<User> userList = null;
		List<String> data = new ArrayList<String>();
		StringBuffer query = new StringBuffer();
		query.append("SELECT ").append("USER_ID,")
		/*
		 * .append("INST_ID,") .append("BRANCH_ID,")
		 * .append("USER_MENU_PROFILE,") .append("USER_ENABLE_FLAG,")
		 * .append("LAST_LOGIN_TIME,") .append("ED_REMARKS")
		 */
		.append(" from user where ").append("inst_id = ? and ")
				.append("branch_id = ?  and").append("branch_id = ?");
		/*
		 * data.add(userKey.getInstId()); data.add(userKey.getBranchId());
		 * data.add("N");
		 */
		userList = getJdbcTemplate().query(query.toString(),
				new PreparedStatementSetter() {

					@Override
					public void setValues(PreparedStatement pss)
							throws SQLException {

						pss.setString(1, userKey.getInstId());
						pss.setString(2, userKey.getBranchId());
						pss.setString(3, "N");

					}
				}, new UserListRowMapper());
		if (userList.size() == 0) {
			throw new NoDataFoundException();
		}

		return userList;
	}

	class UserListRowMapper implements RowMapper {

		@Override
		public User mapRow(ResultSet rs, int arg1) throws SQLException {
			User user = new User();
			user.setUserId(rs.getString("user_id"));
			/*
			 * user.setInstId(rs.getString("INST_ID"));
			 * user.setBranchId(rs.getString("BRANCH_ID"));
			 * user.setUserEnableFlag(rs.getString("USER_ENABLE_FLAG"));
			 * user.setLoginTime(rs.getDate("LAST_LOGIN_TIME"));
			 * user.setRemarks(rs.getString("ED_REMARKS"));
			 */

			return user;
		}
	}

}