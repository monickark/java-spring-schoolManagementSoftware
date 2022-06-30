package com.jaw.user.dao;

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
public class UserLinkListDao extends BaseDao implements IUserLinkListDao {
	Logger logger = Logger.getLogger(UserLinkListDao.class);

	@Override
	public List<UserLink> getListOfUserLinkRecords(final UserLinkKey userLinkKey)
			throws NoDataFoundException {
		logger.info("inside getListOfUserLinkRecords method in dao");
		List<String> data = new ArrayList<String>();
		StringBuffer query = new StringBuffer();
		query.append("SELECT ").append("DB_TS,").append("INST_ID,")
				.append("USER_ID,").append("PROFILE_GROUP,")
				.append("USER_MENU_PROFILE, ").append("LINK_ID,")
				.append("ROLE, ").append(" DEL_FLG, ").append("R_MOD_ID,")
				.append("R_MOD_TIME,").append("R_CRE_ID,")
				.append("R_CRE_TIME ").append(" FROM usrl WHERE  ")
				.append("del_flg=? ").append("and INST_ID=?");
		
		data.add("N");
		data.add(userLinkKey.getInstId().trim());
		if((userLinkKey.getUserId()!=null)||(!userLinkKey.getUserId().equals(""))){
			query.append(" and USER_ID=?");
			data.add(userLinkKey.getUserId());
		}
		if((userLinkKey.getLinkId()!=null)||(!userLinkKey.getLinkId().equals(""))){
			query.append(" and LINK_ID=?");
			data.add(userLinkKey.getLinkId());
		}
	
		logger.info("check validate query :" + query.toString());
		String[] array = data.toArray(new String[data.size()]);
		List<UserLink> userList = null;
		userList = getJdbcTemplate().query(query.toString(),
				array, new UserLinkDetailsRowMapper());
		if (userList.size() == 0) {
			throw new NoDataFoundException();
		}
		return userList;

	}

	class UserLinkDetailsRowMapper implements RowMapper<UserLink> {

		@Override
		public UserLink mapRow(ResultSet rs, int arg1) throws SQLException {
			UserLink user = new UserLink();
			user = new UserLink();
			user.setDb_ts(rs.getInt("DB_TS"));
			user.setInstId(rs.getString("inst_id"));
			user.setUserId(rs.getString("user_id"));
			user.setProfileGroup(rs.getString("PROFILE_GROUP"));
			user.setUserMenuProfile(rs.getString("USER_MENU_PROFILE"));
			user.setLinkId((rs.getString("LINK_ID")));
			user.setRole(rs.getString("ROLE"));
			user.setDeleteFlag((rs.getString("DEL_FLG")));
			user.setrModId(rs.getString("R_MOD_ID"));
			user.setrModTime(rs.getString("R_MOD_TIME"));
			user.setrCreId(rs.getString("R_CRE_ID"));
			user.setrCreTime(rs.getString("R_CRE_TIME"));
			return user;
		}
	}

	@Override
	public void insertListOfUserLinkRec(final List<UserLink> listOfUserLink)
			throws DuplicateEntryException {
		logger.info("inside insertListOfUserLinkRec method in dao");

		StringBuffer sql = new StringBuffer("insert into usrl(")
				.append("DB_TS, ").append("INST_ID, ").append("BRANCH_ID, ")
				.append("USER_ID, ").append("PROFILE_GROUP, ")
				.append("USER_MENU_PROFILE, ").append("LINK_ID, ")
				.append("ROLE, ").append("DEL_FLG, ").append("R_MOD_ID, ")
				.append("R_MOD_TIME, ").append("R_CRE_ID, ")
				.append("R_CRE_TIME")
				.append(") values(?,?,?,?,?,?,?,?,?,?,now(),?,now())");
		try {
			getJdbcTemplate().batchUpdate(sql.toString(),
					new BatchPreparedStatementSetter() {

						@Override
						public void setValues(PreparedStatement ps, int i)
								throws SQLException {

							UserLink userLinkRec = listOfUserLink.get(i);

							ps.setInt(1, userLinkRec.getDb_ts());
							ps.setString(2, userLinkRec.getInstId());
							ps.setString(3, userLinkRec.getBranchId());
							ps.setString(4, userLinkRec.getUserId());
							ps.setString(5, userLinkRec.getProfileGroup());
							ps.setString(6, userLinkRec.getUserMenuProfile());
							ps.setString(7, userLinkRec.getLinkId());
							ps.setString(8, userLinkRec.getRole());
							ps.setString(9, userLinkRec.getDeleteFlag());
							ps.setString(10, userLinkRec.getrModId());
							ps.setString(11, userLinkRec.getrCreId());
						}

						@Override
						public int getBatchSize() {
							return listOfUserLink.size();
						}
					}

			);
		} catch (DuplicateKeyException duplicateKeyException) {
			throw new DuplicateEntryException();
		}
	}

}
