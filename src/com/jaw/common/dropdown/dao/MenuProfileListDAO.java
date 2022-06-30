package com.jaw.common.dropdown.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.framework.dao.BaseDao;

@Repository
public class MenuProfileListDAO extends BaseDao implements IMenuProfileListDAO {

	// Logging
	Logger logger = Logger.getLogger(AcademicTermListDAO.class);

	@Override
	public Map<String, String> selectMenuProfileList(final String instId,
			final String branchId, final String profileGroup)
			throws NoDataFoundException {
		logger.debug("Inside Menu profile Tag select method");

		StringBuffer sql = new StringBuffer();

		sql.append("select ").append("menu_profile,")
				.append("menu_profile_desc ").append(" from umpr ")
				.append("where ").append("  INST_ID=?")
				.append(" and BRANCH_ID=?").append(" and DEL_FLG=?  ")
				.append(" and profile_grp_id=?  ");
		Map<String, String> subMap1 = null;
		subMap1 = getJdbcTemplate().query(sql.toString(),
				new PreparedStatementSetter() {

					@Override
					public void setValues(PreparedStatement pss)
							throws SQLException {
						pss.setString(1, instId);
						pss.setString(2, branchId);
						pss.setString(3, "N");
						pss.setString(4, profileGroup);

					}

				}, new MenuProfileExtractor());
		if (subMap1.size() == 0) {
			throw new NoDataFoundException();
		}
		return subMap1;
	}

	@Override
	public Map<String, String> selectAdminUserList()
			throws NoDataFoundException {
		logger.debug("Inside Menu profile Tag select method");

		StringBuffer sql = new StringBuffer();

		sql.append("SELECT user.user_id FROM `user`,usrl where user.user_id=usrl.user_id "
				+ "and user_menu_profile in('BRADM','chsr','chrm','prnc') "
				+ " and user.del_flg=usrl.del_flg " + "and user.del_flg='N'; ");
		Map<String, String> subMap1 = null;
		subMap1 = getJdbcTemplate().query(sql.toString(), new UserExtractor());
		if (subMap1.size() == 0) {
			throw new NoDataFoundException();
		}
		return subMap1;
	}

}

class MenuProfileExtractor implements ResultSetExtractor<Map<String, String>> {
	@Override
	public Map<String, String> extractData(ResultSet rs) throws SQLException {

		Map<String, String> map = new LinkedHashMap<String, String>();
		while (rs.next()) {
			String key = rs.getString("menu_profile");
			String value = rs.getString("menu_profile_desc");
			map.put(key, value);
		}
		return map;
	}
}

class UserExtractor implements ResultSetExtractor<Map<String, String>> {
	@Override
	public Map<String, String> extractData(ResultSet rs) throws SQLException {

		Map<String, String> map = new LinkedHashMap<String, String>();
		while (rs.next()) {
			String key = rs.getString("user_id");
			String value = rs.getString("user_id");
			map.put(key, value);
		}
		return map;
	}
}