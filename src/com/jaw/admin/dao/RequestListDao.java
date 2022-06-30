package com.jaw.admin.dao;

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

import com.jaw.common.constants.ApplicationConstant;
import com.jaw.common.constants.MenuOptionConstant;
import com.jaw.common.exceptions.BatchUpdateFailedException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.NoDataFoundException;

import com.jaw.framework.dao.BaseDao;
import com.jaw.framework.sessCache.UserSessionDetails;
import com.jaw.user.dao.User;

@Repository
public class RequestListDao extends BaseDao implements IRequestListDao {
	Logger logger = Logger.getLogger(RequestListDao.class);

	int[] ret;

	@Override
	public List<Request> getRequestsList(String instId, String branchId,
			String requestType, String requestStatus, String menuOptionId)
			throws NoDataFoundException {
		List<Request> results = null;
		List<String> data = new ArrayList<String>();
		StringBuffer query = new StringBuffer();
		System.out.println("Menu option id :" + menuOptionId);
		query.append("SELECT ").append("R.USER_ID,").append("R.INST_ID,")
				.append("R.Branch_ID,").append("b.branch_name ,")
				.append("RQST_SRL_NUM,").append("USER_MENU_PROFILE,")
				.append("RQST_STATUS,").append("RQST_TYPE,")
				.append("R.R_CRE_TIME, ").append("R.R_MOD_TIME, ")
				.append("CLOSED_DATE ")
				.append(" FROM rqst R ,user_link u,branchmaster b WHERE ")
				.append(" (R.INST_ID=? ").append(" and R.USER_ID=u.USER_ID )");

		query.append(" and  R.DEL_FLG='n' and u.del_flg='N'");
		data.add(instId);
		if (menuOptionId
				.equals(MenuOptionConstant.REQUEST_MANAGEMENT_SUPERADMIN)) {
			query.append("and  R.BRANCH_ID=b.BRANCH_ID  ");
			query.append("and (u.profile_group=? or ");
			data.add(ApplicationConstant.PG_MGMT);
			query.append("u.USER_MENU_PROFILE=?)");
			data.add(ApplicationConstant.BRANCH_ADMIN);

		} else if (menuOptionId
				.equals(MenuOptionConstant.REQUEST_MANAGEMENT_BRANCHADMIN)) {
			query.append("and  R.BRANCH_ID=b.BRANCH_ID  ");
			query.append("and u.USER_MENU_PROFILE!=?  ");
			data.add(ApplicationConstant.BRANCH_ADMIN);
			query.append("and R.BRANCH_ID=?");
			data.add(branchId);
		}

		if ((requestType != null) && (requestType != "")) {
			query.append("and RQST_TYPE=? ");
			data.add(requestType);
		}

		if ((requestStatus != null) && (requestStatus != "")) {
			query.append("and RQST_STATUS=?");
			data.add(requestStatus);
		}
		query.append("order by r_cre_time desc");
		String[] array = data.toArray(new String[data.size()]);

		results = getJdbcTemplate().query(query.toString(), array,
				new UserRowMapper());
		if (results.size() == 0) {
			throw new NoDataFoundException();
		}
		logger.debug("Selected  records :" + results.size());
		return results;
	}

	@Override
	public List<RequestList> selectPendingRequestRecords(String requestType,
			String requestStatus, String branchId, String instId,
			String profileGroup, String columnName, String tableName,
			String linkId, String menuOptionId, String menuProfile)
			throws NoDataFoundException {

		List<RequestList> results = null;
		List<String> data = new ArrayList<String>();
		StringBuffer query = new StringBuffer();

		query.append("SELECT ")
				.append("rqst.USER_ID,")

				.append("user_menu_profile,")
				.append(columnName + ",")
				.append("RQST_SRL_NUM,")
				.append("RQST_STATUS,")
				.append("RQST_TYPE,")
				.append("rqst.R_CRE_TIME, ")
				.append("rqst.R_MOD_TIME, ")
				.append("rqst.DB_TS,")
				.append("u.DB_TS,")
				.append("rqst.INST_ID,")
				.append("rqst.Branch_ID,")
				.append("b.branch_name, ")
				.append("CLOSED_DATE ")

				.append(" FROM usrl, rqst,user u, branchmaster b," + tableName
						+ " WHERE ")
				.append(" rqst.inst_id = usrl.inst_id and ")
				.append("rqst.user_id = usrl.user_id and ")
				.append("rqst.user_id = u.user_id and")
				.append(" usrl.inst_id = " + tableName + ".inst_id and ")
				.append(" usrl.link_id = " + tableName + "." + linkId + " and ")
				.append(" usrl.del_flg='N'").append(" and rqst.inst_id=?")
				.append(" and  rqst.DEL_FLG='n'").append(" and ")
				.append(tableName).append(" .DEL_FLG='n'");

		query.append(" and RQST_STATUS!=?");
		data.add(instId);
		data.add(requestStatus);
		logger.debug("Menu option id:" + menuOptionId);
		if (menuOptionId.equals(MenuOptionConstant.PENDING_REQUEST_SUPERADMIN)) {
			query.append("and  rqst.BRANCH_ID=b.BRANCH_ID  ");
			query.append("and (usrl.profile_group=? or ");
			data.add(ApplicationConstant.PG_MGMT);
			query.append("usrl.USER_MENU_PROFILE=?)");
			data.add(ApplicationConstant.BRANCH_ADMIN);
		} else if (menuOptionId
				.equals(MenuOptionConstant.PENDING_REQUEST_BRANCHADMIN)) {
			query.append("and  rqst.BRANCH_ID=b.BRANCH_ID  ");
			if (profileGroup.equals(ApplicationConstant.STAFF)) {
				query.append("and usrl.USER_MENU_PROFILE!=?  ");
				data.add(ApplicationConstant.BRANCH_ADMIN);
			} else {
				query.append("and usrl.USER_MENU_PROFILE=?  ");
				data.add(menuProfile);
			}
			query.append("and rqst.BRANCH_ID=?");
			data.add(branchId);
		}
		if ((requestType != null) && (requestType != "")) {
			query.append(" and RQST_TYPE=?");
			data.add(requestType);
		}
		query.append("order by r_cre_time desc");
		String[] array = data.toArray(new String[data.size()]);

		results = getJdbcTemplate().query(query.toString(), array,
				new RequestRowMapper());
		logger.debug("Selected pending records :" + results.size());

		if (results.size() == 0) {
			throw new NoDataFoundException();
		}
		return results;
	}

	@Override
	public int[] updateBatch(final List<RequestList> batchup)
			throws DuplicateEntryException, BatchUpdateFailedException {
		StringBuffer query = new StringBuffer();

		query.append("update rqst set ").append("DB_TS=? ,")
				.append("RQST_STATUS = ?, ").append("R_MOD_TIME = NOW() , ")
				.append("CLOSED_DATE = NOW() , ").append("R_MOD_ID =? ")
				.append(" where ").append("RQST_SRL_NUM =? and ")
				.append("db_ts=? ");

		try {
			ret = getJdbcTemplate().batchUpdate(query.toString(),
					new BatchPreparedStatementSetter() {

						@Override
						public int getBatchSize() {
							return batchup.size();
						}

						@Override
						public void setValues(java.sql.PreparedStatement ps,
								int i) throws SQLException {

							RequestList batch = batchup.get(i);
							logger.debug("Batch Update :- Request Serial Number :"
									+ batch.getRequestSerialNumber()
									+ " Inst Id: ");

							ps.setInt(1, batch.getReqDbTs() + 1);
							ps.setString(2, batch.getReqstStatus().trim());
							ps.setString(3, batch.getrModId());
							ps.setString(4, batch.getRequestSerialNumber());
							ps.setInt(5, batch.getReqDbTs());
						}
					});
			for (int sa : ret) {
				if (sa == 0) {
					throw new BatchUpdateFailedException();
				}
			}
		} catch (DuplicateKeyException duplicateKeyException) {
			throw new DuplicateEntryException();
		}
		return ret;

	}

	@Override
	public List<RequestList> getRequestsListOfTransferredUser(String instId,
			String branchId, String linkId) throws NoDataFoundException {
		List<RequestList> results = null;
		List<String> data = new ArrayList<String>();
		StringBuffer query = new StringBuffer();
		query.append("SELECT ").append("r.DB_TS,").append("r.INST_ID,")
				.append("r.Branch_ID,").append("RQST_SRL_NUM ")
				.append(" FROM rqst r,user_link u  WHERE ")
				.append(" u.inst_id=r.inst_id and ")
				.append(" u.branch_id=r.branch_id and ")
				.append(" u.del_flg=r.del_flg and ")
				.append(" u.user_id=r.user_id and ").append(" r.INST_ID=? and")
				.append(" r.BRANCH_ID=? and").append(" RQST_STATUS!=? ")
				.append(" and  r.DEL_FLG='n' ").append(" and u.link_id=?");
		data.add(instId);
		data.add(branchId);

		data.add(ApplicationConstant.REQUEST_STATUS_CLOSED);
		data.add(linkId);
		String[] array = data.toArray(new String[data.size()]);

		results = getJdbcTemplate().query(query.toString(), array,
				new TransferredUserRequestListRowMapper());
		if (results.size() == 0) {
			throw new NoDataFoundException();
		}
		logger.debug("Selected  records :" + results.size());
		return results;
	}

	@Override
	public int[] updateTransferredUserRequest(final List<RequestList> batchup,
			final UserSessionDetails userSessionDetails)
			throws DuplicateEntryException, BatchUpdateFailedException {
		StringBuffer query = new StringBuffer();

		query.append("update rqst set").append(" DB_TS= ?,")
				.append("DEL_FLG= 'Y',").append("R_MOD_ID= ?,")
				.append(" R_MOD_TIME=now()").append(" where")
				.append(" DB_TS= ?").append(" and  INST_ID= ?")
				.append(" and  BRANCH_ID= ?").append(" and  RQST_SRL_NUM= ?")
				.append(" and  DEL_FLG='N'");

		try {
			ret = getJdbcTemplate().batchUpdate(query.toString(),
					new BatchPreparedStatementSetter() {

						@Override
						public int getBatchSize() {
							return batchup.size();
						}

						@Override
						public void setValues(java.sql.PreparedStatement pss,
								int i) throws SQLException {

							RequestList batch = batchup.get(i);
							logger.debug("Batch Update :- Request Serial Number :"
									+ batch.getRequestSerialNumber()
									+ " Inst Id: ");

							pss.setInt(1, batch.getReqDbTs() + 1);
							pss.setString(2, userSessionDetails.getUserId());

							pss.setInt(3, batch.getReqDbTs());
							pss.setString(4, batch.getInstId().trim());
							pss.setString(5, batch.getBranchId().trim());
							pss.setString(6, batch.getRequestSerialNumber()
									.trim());
						}
					});
			for (int sa : ret) {
				if (sa == 0) {
					throw new BatchUpdateFailedException();
				}
			}
		} catch (DuplicateKeyException duplicateKeyException) {
			throw new DuplicateEntryException();
		}
		return ret;

	}

	@Override
	public List<String> selectMenuProfile(final String instid,
			final String branchId) throws NoDataFoundException {
		StringBuffer query = new StringBuffer();
		query.append("SELECT distinct(user_menu_profile) ")
				.append("from user, request where ")
				.append("user.inst_id = request.inst_id and ")
				.append("user.branch_id = request.branch_id and ")
				.append("user.user_id = request.user_id and ")
				.append("user.del_flg = request.DEL_FLG and ")
				.append("request.inst_id = ? and ")
				.append("request.branch_id = ? and ")
				.append("request.rqst_status != 'C'")
				.append(" and  request.DEL_FLG='n'");
		List<String> commDetailsResult = getJdbcTemplate().query(
				query.toString(), new PreparedStatementSetter() {
					@Override
					public void setValues(java.sql.PreparedStatement pss)
							throws SQLException {
						pss.setString(1, instid);
						pss.setString(2, branchId);
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

	class UserRowMapper implements RowMapper<Request> {

		@Override
		public Request mapRow(ResultSet rs, int arg1) throws SQLException {
			Request request = new Request();
			request.setUserId(rs.getString("USER_ID"));
			request.setInstId(rs.getString("INST_ID"));
			request.setBranchId(rs.getString("BRANCH_Name"));
			request.setRequestSerialNumber(rs.getString("RQST_SRL_NUM"));
			request.setReqstStatus(rs.getString("RQST_STATUS"));
			request.setReqstType(rs.getString("RQST_TYPE"));
			request.setUserMenuProfile(rs.getString("USER_MENU_PROFILE"));
			request.setrCreTime(rs.getString("R_CRE_TIME"));
			request.setrModTime(rs.getString("R_MOD_TIME"));
			request.setClosedDate(rs.getString("CLOSED_DATE"));
			return request;
		}
	}

	class RequestRowMapper implements RowMapper<RequestList> {

		@Override
		public RequestList mapRow(ResultSet rs, int arg1) throws SQLException {
			RequestList request = new RequestList();
			request.setUserId(rs.getString(1));
			request.setUserMenuProfile(rs.getString(2));
			request.setName(rs.getString(3));
			request.setRequestSerialNumber(rs.getString(4));
			request.setReqstStatus(rs.getString(5));
			request.setReqstType(rs.getString(6));
			request.setrCreTime(rs.getString(7));
			request.setrModTime(rs.getString(8));
			request.setReqDbTs(rs.getInt(9));
			request.setUserDbts(rs.getInt(10));
			request.setInstId(rs.getString(11));
			request.setBranchId(rs.getString(12));
			request.setBranchName(rs.getString(13));
			request.setClosedDate(rs.getString(14));
			return request;
		}
	}

	class TransferredUserRequestListRowMapper implements RowMapper<RequestList> {

		@Override
		public RequestList mapRow(ResultSet rs, int arg1) throws SQLException {
			RequestList request = new RequestList();
			request.setRequestSerialNumber(rs.getString("RQST_SRL_NUM"));
			request.setInstId(rs.getString("INST_ID"));
			request.setBranchId(rs.getString("BRANCH_ID"));
			request.setReqDbTs(rs.getInt("DB_TS"));
			return request;
		}
	}

}