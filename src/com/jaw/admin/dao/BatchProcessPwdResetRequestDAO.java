package com.jaw.admin.dao;

import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.stereotype.Repository;

import com.jaw.common.exceptions.BatchUpdateFailedException;
import com.jaw.framework.dao.BaseDao;
import com.jaw.user.dao.User;

@Repository
public class BatchProcessPwdResetRequestDAO extends BaseDao implements IBatchProcessPwdResetRequestDAO {

	@Override
	public void updateBatch(final List<User> batchup)
			throws BatchUpdateFailedException {

		int[] affectedRow = getJdbcTemplate()
				.batchUpdate(
						"update user set pwd = ? , PASSWORD_CHANGE_REQUIRED_FLG = 'Y',R_MOD_ID=?,R_MOD_time=now() where user_id=?",
						new BatchPreparedStatementSetter() {
							@Override
							public int getBatchSize() {
								return batchup.size();
							}

							@Override
							public void setValues(
									java.sql.PreparedStatement ps, int i)
									throws SQLException {
								User batch = batchup.get(i);
								ps.setString(1, batch.getPassword().trim());
								ps.setString(2, batch.getrModId().trim());
								ps.setString(3, batch.getUserId().trim());

							}
						});

		for (int i : affectedRow) {
			if (i == 0) {

				throw new BatchUpdateFailedException();
			}
		}

	}

}