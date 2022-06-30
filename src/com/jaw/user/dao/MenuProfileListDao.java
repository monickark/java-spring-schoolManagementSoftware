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
public class MenuProfileListDao extends BaseDao implements IMenuprofileListDao {

	Logger logger = Logger.getLogger(UserLinkDao.class);

	@Override
	public List<String> selectMenuProfile(final String instid)
			throws NoDataFoundException {
		StringBuffer query = new StringBuffer();
		query.append("SELECT distinct(user_menu_profile) ")
				.append("from usrl where ").append("inst_id = ? and ")
				.append("del_flg=? ");
		List<String> commDetailsResult = getJdbcTemplate().query(
				query.toString(), new PreparedStatementSetter() {

					@Override
					public void setValues(java.sql.PreparedStatement pss)
							throws SQLException {
						pss.setString(1, instid);
						pss.setString(2, "N");
					}

				}, new MenuProfileRowMapper());
		if (commDetailsResult.size() == 0) {
			throw new NoDataFoundException();
		}
		return commDetailsResult;
	}

	class MenuProfileRowMapper implements RowMapper<String> {

		@Override
		public String mapRow(ResultSet rs, int arg1) throws SQLException {

			return rs.getString("USER_MENU_PROFILE");

		}
	}

}