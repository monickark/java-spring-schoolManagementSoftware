package com.jaw.user.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.UpdateFailedException;
import com.jaw.common.exceptions.login.InvalidUserIdException;
import com.jaw.framework.dao.BaseDao;

@Repository
public class UserLinkDao extends BaseDao implements IUserLinkDao {
	
	Logger logger = Logger.getLogger(UserLinkDao.class);
	
	@Override
	public void insertUserLink(final UserLink userLink)
			throws DuplicateEntryException {
		
		logger.debug("Inside insert method");
		
		StringBuffer query = new StringBuffer();
		
		query = query.append("insert into usrl ( ")
				.append("DB_TS,")
				.append("INST_ID,")
				.append("BRANCH_ID,")
				
				.append("USER_ID,")
				.append("PROFILE_GROUP,")
				.append("USER_MENU_PROFILE,")
				
				.append("LINK_ID,")
				.append("ROLE,").append("DEL_FLG ,")
				
				.append("R_MOD_ID,")
				.append("R_MOD_TIME, ")
				.append("R_CRE_ID,")
				
				.append("R_CRE_TIME)")
				.append(" values (?,?,?, ?,?,?, ?,?,?, ?,now(),?, now())");
		logger.debug("insert query :" + query.toString());
		
		try {
			getJdbcTemplate().update(query.toString(),
					new PreparedStatementSetter() {
						
						@Override
						public void setValues(PreparedStatement ps)
								throws SQLException {
							ps.setInt(1, userLink.getDb_ts());
							ps.setString(2, userLink.getInstId().trim());
							ps.setString(3, userLink.getBranchId().trim());
							ps.setString(4, userLink.getUserId().trim());
							ps.setString(5, userLink.getProfileGroup().trim());
							ps.setString(6, userLink.getUserMenuProfile()
									.trim());
							ps.setString(7, userLink.getLinkId().trim());
							ps.setString(8, userLink.getRole().trim());
							ps.setString(9, userLink.getDeleteFlag().trim());
							ps.setString(10, userLink.getrModId().trim());
							ps.setString(11, userLink.getrCreId().trim());
							
						}
						
					});
		}
		catch (org.springframework.dao.DuplicateKeyException e) {
			throw new DuplicateEntryException();
		}
		
	}
	
	@Override
	public UserLink getUserDetails(final String userId)
			throws InvalidUserIdException {
		StringBuffer query = new StringBuffer();
		query.append("SELECT ")
				.append("DB_TS,")
				.append("INST_ID,")
				.append("BRANCH_ID,")
				.append("USER_ID,")
				.append("PROFILE_GROUP,")
				.append("USER_MENU_PROFILE, ")
				.append("LINK_ID,")
				.append("ROLE, ")
				.append(" DEL_FLG, ")
				.append("R_MOD_ID,")
				.append("R_MOD_TIME,")
				.append("R_CRE_ID,")
				.append("R_CRE_TIME ")
				.append(" FROM usrl WHERE user_id = ? ")
				.append("and del_flg=?");
		logger.info("check validate query :" + query.toString());
		
		UserLink user = null;
		user = getJdbcTemplate().query(query.toString(),
				new PreparedStatementSetter() {
					
					@Override
					public void setValues(java.sql.PreparedStatement pss)
							throws SQLException {
						pss.setString(1, userId);
						pss.setString(2, "N");
						
					}
					
				}, new ResultSetExtractor<UserLink>() {
					
					@Override
					public UserLink extractData(ResultSet rs)
							throws SQLException {
						UserLink user = null;
						if (rs.next()) {
							user = new UserLink();
							user.setDb_ts(rs.getInt("DB_TS"));
							user.setInstId(rs.getString("inst_id"));
							user.setBranchId(rs.getString("BRANCH_ID"));
							user.setUserId(rs.getString("user_id"));
							user.setProfileGroup(rs.getString("PROFILE_GROUP"));
							user.setUserMenuProfile(rs
									.getString("USER_MENU_PROFILE"));
							user.setLinkId((rs.getString("LINK_ID")));
							user.setRole(rs.getString("ROLE"));
							user.setDeleteFlag((rs.getString("DEL_FLG")));
							user.setrModId(rs.getString("R_MOD_ID"));
							user.setrModTime(rs.getString("R_MOD_TIME"));
							user.setrCreId(rs.getString("R_CRE_ID"));
							user.setrCreTime(rs.getString("R_CRE_TIME"));
							
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
	public UserLink getUserDetailsByLinkId(final String linkId,
			final String instId, final String branchId)
			throws InvalidUserIdException {
		logger.debug("User where class datas :- Link id :" + linkId
				+ "   inst Id:" + instId + "   Branch Id:" + branchId);
		StringBuffer query = new StringBuffer();
		query.append("SELECT ")
				.append("DB_TS,")
				.append("INST_ID,")
				.append("BRANCH_ID,")
				.append("USER_ID,")
				.append("PROFILE_GROUP,")
				.append("USER_MENU_PROFILE, ")
				.append("LINK_ID,")
				.append("ROLE, ")
				.append(" DEL_FLG, ")
				.append("R_MOD_ID,")
				.append("R_MOD_TIME,")
				.append("R_CRE_ID,")
				.append("R_CRE_TIME ")
				.append(" FROM usrl WHERE LINK_ID = ? ")
				.append("and del_flg=?");
		logger.info("check validate query :" + query.toString());
		
		UserLink user = null;
		user = getJdbcTemplate().query(query.toString(),
				new PreparedStatementSetter() {
					
					@Override
					public void setValues(java.sql.PreparedStatement pss)
							throws SQLException {
						pss.setString(1, linkId);
						pss.setString(2, "N");
						
					}
					
				}, new ResultSetExtractor<UserLink>() {
					
					@Override
					public UserLink extractData(ResultSet rs)
							throws SQLException {
						UserLink user = null;
						if (rs.next()) {
							user = new UserLink();
							user.setDb_ts(rs.getInt("DB_TS"));
							user.setInstId(rs.getString("inst_id"));
							user.setBranchId(rs.getString("BRANCH_id"));
							user.setUserId(rs.getString("user_id"));
							user.setProfileGroup(rs.getString("PROFILE_GROUP"));
							user.setUserMenuProfile(rs
									.getString("USER_MENU_PROFILE"));
							user.setLinkId((rs.getString("LINK_ID")));
							user.setRole(rs.getString("ROLE"));
							user.setDeleteFlag((rs.getString("DEL_FLG")));
							user.setrModId(rs.getString("R_MOD_ID"));
							user.setrModTime(rs.getString("R_MOD_TIME"));
							user.setrCreId(rs.getString("R_CRE_ID"));
							user.setrCreTime(rs.getString("R_CRE_TIME"));
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
	public void updateUserLinkRec(final UserLink userlink)
			throws UpdateFailedException {
		
		logger.debug("Inside update method");
		System.out.println("USer Link Object :" + userlink.toString());
		StringBuffer sql = new StringBuffer();
		sql.append("update usrl set")
				.append(" DB_TS= ?")
				.append(", INST_ID= ?")
				.append(", branch_ID= ?")
				
				.append(", USER_ID= ?")
				.append(", PROFILE_GROUP= ?")
				.append(", USER_MENU_PROFILE= ?")
				.append(", LINK_ID= ?")
				
				.append(", ROLE= ?")
				.append(", DEL_FLG= ?")
				.append(", R_MOD_ID= ?")
				
				.append(", R_MOD_TIME=now()")
				.append(", R_CRE_ID= ?")
				.append(", R_CRE_TIME= ?")
				
				.append(" WHERE user_id = ? ")
				.append("and del_flg=?")
				.append("and db_ts=?");
		
		int affectedRow = getJdbcTemplate().update(sql.toString(),
				new PreparedStatementSetter() {
					@Override
					public void setValues(PreparedStatement ps)
							throws SQLException {
						ps.setInt(1, userlink.getDb_ts() + 1);
						ps.setString(2, userlink.getInstId().trim());
						ps.setString(3, userlink.getBranchId().trim());
						ps.setString(4, userlink.getUserId().trim());
						ps.setString(5, userlink.getProfileGroup().trim());
						ps.setString(6, userlink.getUserMenuProfile().trim());
						ps.setString(7, userlink.getLinkId().trim());
						ps.setString(8, userlink.getRole().trim());
						ps.setString(9, userlink.getDeleteFlag().trim());
						ps.setString(10, userlink.getrModId().trim());
						ps.setString(11, userlink.getrCreId().trim());
						ps.setString(12, userlink.getrCreTime().trim());
						ps.setString(13, userlink.getUserId().trim());
						ps.setString(14, "N");
						ps.setInt(15, userlink.getDb_ts());
					}
					
				});
		
		if (affectedRow == 0) {
			throw new UpdateFailedException();
		}
	}
	
}