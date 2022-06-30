package com.jaw.user.dao;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.UpdateFailedException;
import com.jaw.common.exceptions.login.InvalidUserIdException;
import com.jaw.framework.dao.BaseDao;

@Repository
public class UserDao extends BaseDao implements IUserDao {

	Logger logger = Logger.getLogger(UserDao.class);

	@Override
	public void insertSingleUser(final User user)
			throws DuplicateEntryException {
		StringBuffer query = new StringBuffer()

				.append("insert into user( ")
				.append("DB_TS, ")
				.append("INST_ID, ")
				.append("BRANCH_ID, ")

				.append("USER_ID,")
				.append("PWD,")

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
				.append("R_MOD_ID,")
				.append("R_MOD_TIME,")

				.append("R_CRE_ID, ")
				.append("R_CRE_TIME, ")
				.append("PASSWORD_CHANGE_REQUIRED_FLG, ")

				.append("ED_REMARKS)")

				.append(" values(?,?,?,?,?,?,?,?,?,?,now(),?,?,?,?,?,now(),?,now(),?,?)");

		try {
			getJdbcTemplate().update(query.toString(),
					new PreparedStatementSetter() {

						@Override
						public void setValues(java.sql.PreparedStatement rs)
								throws SQLException {
							rs.setInt(1, user.getDb_ts());
							rs.setString(2, user.getInstId().trim());
							rs.setString(3, user.getBranchId().trim());
							rs.setString(4, user.getUserId().trim());
							rs.setString(5, user.getPassword().trim());
							rs.setDate(6, (Date) user.getLoginTime());
							rs.setString(7, user.getIpAddress());
							rs.setString(8, user.getPasswordExpiryDate());
							rs.setInt(9, user.getNoOfAttempts());
							rs.setDate(10, (Date) user.getPasswordResetDate());
							rs.setString(11, user.getSessionId());
							rs.setString(12, user.getTotalNoOfLogin());
							rs.setString(13, user.getUserEnableFlag());
							rs.setString(14, user.getDeleteFlag());
							rs.setString(15, user.getrModId().trim());
							rs.setString(16, user.getrCreId().trim());
							rs.setString(17, user.getPasswordResetFlag().trim());
							rs.setString(18, user.getRemarks());

						}

					});
		} catch (DuplicateKeyException duplicateKeyException) {
			throw new DuplicateEntryException();
		}

	}

	@Override
	public User validateUserId(final String userId)
			throws InvalidUserIdException {
		StringBuffer query = new StringBuffer();
		query.append("SELECT DB_TS, ").append("INST_ID, ")
				.append("BRANCH_ID, ").append("USER_ID,").append("PWD,")
				.append("LAST_LOGIN_TIME,").append("LAST_LOGIN_IP_ADDRESS,")
				.append("PWD_EXPIRY_DATE,").append("NO_OF_INVALID_ATTEMPTS,")
				.append("LAST_LOGOUT_TIME,").append("PWD_RESET_DATE,")
				.append("SESSION_ID,").append("TOTAL_NO_OF_LOGIN,")
				.append("USER_ENABLE_FLAG,").append(" DEL_FLG, ")
				.append("R_MOD_ID,").append("R_MOD_TIME,").append("R_CRE_ID,")
				.append("R_CRE_TIME,").append("ED_REMARKS,")
				.append("PASSWORD_CHANGE_REQUIRED_FLG,").append(" DATE(NOW()) as ")
				.append("currentDate FROM user WHERE user_id = ? ")
				.append("and del_flg=?");
		logger.info("check validate query :" + query.toString());

		User user = null;
		user = (User) getJdbcTemplate().query(query.toString(),
				new PreparedStatementSetter() {

					@Override
					public void setValues(java.sql.PreparedStatement pss)
							throws SQLException {
						pss.setString(1, userId);
						pss.setString(2, "N");

					}

				}, new ResultSetExtractor<User>() {

					@Override
					public User extractData(ResultSet rs) throws SQLException {
						User user = null;
						if (rs.next()) {
							user = new User();
							user.setDb_ts(rs.getInt("DB_TS"));
							user.setBranchId(rs.getString("BRANCH_ID"));
							user.setInstId(rs.getString("inst_id"));
							user.setUserId(rs.getString("user_id"));
							user.setPassword(rs.getString("pwd"));
							user.setLoginTime(rs.getDate("LAST_LOGIN_TIME"));
							user.setIpAddress(rs
									.getString("LAST_LOGIN_IP_ADDRESS"));
							user.setPasswordExpiryDate(rs
									.getString("pwd_expiry_date"));
							user.setNoOfAttempts(rs
									.getInt("NO_OF_INVALID_ATTEMPTS"));
							user.setLastLogoutTime(rs
									.getString("LAST_LOGOUT_TIME"));
							user.setPasswordResetDate(rs
									.getDate("PWD_RESET_DATE"));
							user.setSessionId(rs.getString("SESSION_ID"));
							user.setTotalNoOfLogin(rs
									.getString("TOTAL_NO_OF_LOGIN"));
							user.setUserEnableFlag(rs
									.getString("USER_ENABLE_FLAG"));
							user.setDeleteFlag((rs.getString("DEL_FLG")));
							user.setrModId(rs.getString("R_MOD_ID"));
							user.setrModTime(rs.getString("R_MOD_TIME"));
							user.setrCreId(rs.getString("R_CRE_ID"));
							user.setrCreTime(rs.getString("R_CRE_TIME"));
							user.setCurrentDate(rs.getDate("currentDate"));
							user.setPasswordResetFlag(rs
									.getString("PASSWORD_CHANGE_REQUIRED_FLG"));
							user.setRemarks(rs.getString("ED_REMARKS"));

						}
						return user;
					}

				});
		if (user == null) {
			throw new InvalidUserIdException();
		}
		return user;
	}

	@Override
	public void updateUser(final User user) throws UpdateFailedException {
		System.out.println("update object :"+user.toString());
		StringBuffer query = new StringBuffer();
		query.append("update user set ");
		query.append("DB_TS=?,");
		query.append("inst_id=?,");
		query.append("BRANCH_ID=?,");
		query.append("last_login_time=?,");
		query.append("LAST_LOGIN_IP_ADDRESS=?,");
		query.append("PWD_EXPIRY_DATE=?,");
		query.append("NO_OF_INVALID_ATTEMPTS=?,");
		query.append("LAST_LOGOUT_TIME=?,");
		query.append("PWD_RESET_DATE=?,");
		query.append("SESSION_ID=?,");
		query.append("TOTAL_NO_OF_LOGIN=?,");
		query.append("USER_ENABLE_FLAG=?,");
		query.append("DEL_FLG=?,");
		query.append("R_MOD_ID=?,");
		query.append("R_MOD_TIME=Now(),");
		query.append("PASSWORD_CHANGE_REQUIRED_FLG=?,");
		query.append("R_CRE_ID=?,");
		query.append("ED_REMARKS=?,");
		query.append("R_CRE_TIME=? where ");
		query.append("db_ts=? and ");
		query.append("inst_id=? and ");
		query.append("BRANCH_ID=? and ");
		query.append("USER_ID= ?");
		int affectedRows = getJdbcTemplate().update(query.toString(),
				new PreparedStatementSetter() {

					@Override
					public void setValues(java.sql.PreparedStatement ps)
							throws SQLException {
						ps.setInt(1, user.getDb_ts() + 1);
						ps.setString(2, user.getInstId().trim());
						ps.setString(3, user.getBranchId().trim());
						if (user.getLoginTime() != null) {
							ps.setString(4, user.getLoginTime().toString());
						} else {
							ps.setString(4, null);
						}
						ps.setString(5, user.getIpAddress());
						ps.setString(6, user.getPasswordExpiryDate().trim());
						ps.setInt(7, user.getNoOfAttempts());
						if (user.getLastLogoutTime() != null) {
							ps.setString(8, user.getLastLogoutTime().trim());
						} else {
							ps.setString(8, null);
						}
						if (user.getPasswordResetDate() != null) {
							ps.setString(9, user.getPasswordResetDate()
									.toString());
						} else {
							ps.setString(9, null);
						}
						ps.setString(10, user.getSessionId());
						ps.setString(11, user.getTotalNoOfLogin().trim());
						ps.setString(12, user.getUserEnableFlag().trim());
						ps.setString(13, user.getDeleteFlag().trim());
						ps.setString(14, user.getrModId().trim());
						ps.setString(15, user.getPasswordResetFlag().trim());
						ps.setString(16, user.getrCreId());
						ps.setString(17, user.getRemarks());
						ps.setString(18, user.getrCreTime().trim());
						ps.setInt(19, user.getDb_ts());
						ps.setString(20, user.getInstId().trim());
						ps.setString(21, user.getBranchId().trim());
						ps.setString(22, user.getUserId().trim());

					}

				});
		if (affectedRows == 0) {
			throw new UpdateFailedException();
		}
	}

	@Override
	public void updatePassword(final User user) throws UpdateFailedException {
		StringBuffer query = new StringBuffer();
		query.append("update user set ");
		query.append("DB_TS=?,");
		query.append("PWD=?,");
		query.append("LAST_LOGIN_IP_ADDRESS=?,");
		query.append("NO_OF_INVALID_ATTEMPTS=?,");
		query.append("PWD_EXPIRY_DATE=?,");
		query.append("PWD_RESET_DATE=?,");
		query.append("USER_ENABLE_FLAG=?,");
		query.append("R_MOD_ID=?,");
		query.append("R_MOD_TIME=Now(),");
		query.append("PASSWORD_CHANGE_REQUIRED_FLG=?,");
		query.append("R_CRE_ID=?,");
		query.append("R_CRE_TIME=? where ");
		query.append("db_ts=? and ");
		query.append("inst_id=? and ");
		query.append("BRANCH_ID=? and ");
		query.append("USER_ID= ?");
		int affectedRows = getJdbcTemplate().update(query.toString(),
				new PreparedStatementSetter() {

					@Override
					public void setValues(java.sql.PreparedStatement ps)
							throws SQLException {
						ps.setInt(1, user.getDb_ts() + 1);
						ps.setString(2, user.getPassword());
						ps.setString(3, user.getIpAddress());
						ps.setInt(4, user.getNoOfAttempts());
						ps.setString(5, user.getPasswordExpiryDate());
						ps.setString(6, user.getPasswordResetDate().toString());
						ps.setString(7, user.getUserEnableFlag().trim());
						ps.setString(8, user.getrModId().trim());
						ps.setString(9, user.getPasswordResetFlag().trim());
						ps.setString(10, user.getrCreId());
						ps.setString(11, user.getrCreTime().trim());
						ps.setInt(12, user.getDb_ts());
						ps.setString(13, user.getInstId().trim());
						ps.setString(14, user.getBranchId().trim());
						ps.setString(15, user.getUserId().trim());

					}

				});
		if (affectedRows == 0) {
			throw new UpdateFailedException();
		}
	}

}