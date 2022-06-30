package com.jaw.framework.appCache.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;

import com.jaw.common.exceptions.NoDataFoundException;

public class ApplicationCacheProfileOptionLinking implements
		IApplicationCacheProfileOptionLinking {

	@Autowired
	JdbcTemplate jdbcTemplate;

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	// method to select all the files

	@Override
	public List<MenuProfileOptionLinking> getMenuProfileOption()
			throws NoDataFoundException {

		StringBuffer sql = new StringBuffer()
				.append("select ")
				.append("INST_ID, ")
				.append("BRANCH_ID, ")
				.append("MENU_ID, ")
				.append("MENU_PROFILE, ")
				.append("MENU_OPTION_ID,  ")
				.append("MENU_DESC, ")
				.append("MENU_URL, ")
				.append("NODE, ")
				.append("LEVELS , ")
				.append("MENU_ORDER, ")
				.append("LEVEL_ORDER, ")
				.append("SLM_FLG, ")
				.append("MENU_ICON ")
				.append("FROM  ")
				.append("umpl WHERE ")
				.append("DEL_FLG=? order by INST_ID,BRANCH_ID,MENU_PROFILE,NODE,LEVELS,LEVEL_ORDER,MENU_ORDER;");

		List<MenuProfileOptionLinking> commDetailsResult = jdbcTemplate.query(
				sql.toString(), new PreparedStatementSetter() {

					@Override
					public void setValues(java.sql.PreparedStatement pss)
							throws SQLException {
						pss.setString(1, "N");
					}

				}, new FileMasterRowmapper());
		if (commDetailsResult.size() == 0) {
			throw new NoDataFoundException();
		}
		return commDetailsResult;
	}

	// Inner class to map selected columns into File master object
	class FileMasterRowmapper implements RowMapper<MenuProfileOptionLinking> {
		@Override
		public MenuProfileOptionLinking mapRow(ResultSet rs, int arg1)
				throws SQLException {
			MenuProfileOptionLinking menu = new MenuProfileOptionLinking();
			menu.setInstId(rs.getString("INST_ID"));
			menu.setBranchId(rs.getString("BRANCH_ID"));
			menu.setMenuId(rs.getString("MENU_ID"));
			menu.setMenuProfile(rs.getString("MENU_PROFILE"));
			menu.setMenuOption(rs.getString("MENU_OPTION_ID"));
			menu.setMenuDescription(rs.getString("MENU_DESC"));
			menu.setMenuUrl(rs.getString("MENU_URL"));
			menu.setMenuNode(rs.getInt("NODE"));
			menu.setMenuLevel(rs.getInt("LEVELS"));
			menu.setSlmFlg(rs.getString("SLM_FLG"));
			menu.setMenuIcon(rs.getString("MENU_ICON"));
			menu.setMenuOrder(rs.getInt("MENU_ORDER"));
			menu.setLevelOrder(rs.getInt("LEVEL_ORDER"));
			return menu;
		}

	}

	@Override
	public List<MenuProfileOptionLinking> getMenuProfile()
			throws NoDataFoundException {
		StringBuffer sql = new StringBuffer()
				.append("select ")
				.append("distinct (menu_profile), ")
				.append("INST_ID, ")
				.append("BRANCH_ID, ")
				.append("MENU_OPTION_ID  ")
				.append("FROM  ")
				.append("umpl WHERE ")
				.append("DEL_FLG=? group by inst_id, branch_id, menu_profile order by inst_id, branch_id, menu_profile;");

		List<MenuProfileOptionLinking> commDetailsResult = jdbcTemplate.query(
				sql.toString(), new PreparedStatementSetter() {

					@Override
					public void setValues(java.sql.PreparedStatement pss)
							throws SQLException {
						pss.setString(1, "N");
					}

				}, new MenuProfileRowMapper());
		if (commDetailsResult.size() == 0) {
			throw new NoDataFoundException();
		}
		return commDetailsResult;
	}

	// Inner class to map selected columns into File master object
	class MenuProfileRowMapper implements RowMapper<MenuProfileOptionLinking> {
		@Override
		public MenuProfileOptionLinking mapRow(ResultSet rs, int arg1)
				throws SQLException {
			MenuProfileOptionLinking menu = new MenuProfileOptionLinking();
			menu.setInstId(rs.getString("INST_ID"));
			menu.setBranchId(rs.getString("BRANCH_ID"));
			menu.setMenuProfile(rs.getString("MENU_PROFILE"));
			return menu;
		}

	}
}
