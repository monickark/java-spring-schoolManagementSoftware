package com.jaw.login.dao;

import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Repository;

import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.framework.dao.BaseDao;
import com.jaw.user.dao.User;

@Repository
public class LoginDao extends BaseDao implements ILoginDao {

	Logger logger = Logger.getLogger(LoginDao.class);

	@Override
	public boolean insertLogin(final User user) throws DuplicateEntryException {
		StringBuffer query = new StringBuffer();
		query.append("insert into login( ").append("INST_ID,")
				.append("BRANCH_ID,").append("USER_ID,").append("SESSION_ID,")
				.append("R_CRE_TIME)").append("values (?,?,?,?,?)");
		try {
			getJdbcTemplate().update(query.toString(),
					new PreparedStatementSetter() {

						@Override
						public void setValues(java.sql.PreparedStatement ps)
								throws SQLException {
							ps.setString(1, user.getInstId());
							ps.setString(2, user.getBranchId());
							ps.setString(3, user.getUserId());
							ps.setString(4, user.getSessionId());
							ps.setString(5, user.getLoginTime().toString());
						}
					});
			return true;
		} catch (DuplicateKeyException e) {
			throw new DuplicateEntryException();

		}

	}

	@Override
	public void deleteLogin(final String branchId, final String instId,
			final String userId) {
		StringBuffer query = new StringBuffer();
		query.append("delete from login ").append("where user_id= ? ")
				.append("and inst_id= ? ").append("and branch_id= ? ");
		getJdbcTemplate().update(query.toString(),
				new PreparedStatementSetter() {
					@Override
					public void setValues(java.sql.PreparedStatement ps)
							throws SQLException {
						ps.setString(1, userId);
						ps.setString(2, instId);
						ps.setString(3, branchId);

					}

				});
		logger.debug("Delete Query is :" + query.toString());

	}

}
