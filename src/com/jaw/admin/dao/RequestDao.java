package com.jaw.admin.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.framework.dao.BaseDao;

@Repository
public class RequestDao extends BaseDao implements IRequestDao {
	Logger logger = Logger.getLogger(RequestDao.class);
	int[] ret;
	
	@Override
	public void insertRequest(final Request request)
			throws DuplicateEntryException {
		
		StringBuffer query = new StringBuffer();
		query.append("insert into rqst (DB_TS,")
				.append("USER_ID,")
				.append("INST_ID,")
				.append("BRANCH_ID,")
				.append("RQST_SRL_NUM,")
				.append("RQST_TYPE,")
				.append("RQST_STATUS,")
				.append("DEL_FLG,")
				.append("R_CRE_ID,")
				.append("R_CRE_TIME,")
				.append("R_MOD_ID,")
				.append("R_MOD_TIME)")
				.append(" values (?,?,?,?,?,?,?,?,?,now(),?,now())");
		try {
			getJdbcTemplate().update(query.toString(),
					new PreparedStatementSetter() {
						
						@Override
						public void setValues(java.sql.PreparedStatement ps)
								throws SQLException {
							ps.setInt(1, 1);
							ps.setString(2, (request.getUserId()).trim());
							ps.setString(3, (request.getInstId()).trim());
							ps.setString(4, (request.getBranchId()).trim());
							ps.setString(5,
									(request.getRequestSerialNumber()).trim());
							ps.setString(6, (request.getReqstType()).trim());
							ps.setString(7, (request.getReqstStatus()).trim());
							ps.setString(8, (request.getDelFlg()).trim());
							ps.setString(9, (request.getrCreId()).trim());
							ps.setString(10, request.getrModId().trim());
						}
						
					});
		}
		catch (org.springframework.dao.DuplicateKeyException e) {
			throw new DuplicateEntryException();
		}
		
	}
	
	@Override
	public Request selectRequest(final String instid, final String branchId,
			final String srlNum) throws NoDataFoundException {
		Request resultuser = null;
		StringBuffer query = new StringBuffer();
		query.append("SELECT DB_TS, ").append("USER_ID,").append("INST_ID,")
				.append("BRANCH_ID,").append("RQST_SRL_NUM,")
				.append("RQST_TYPE,").append("RQST_STATUS,").append("DEL_FLG,")
				.append("R_CRE_ID,").append("R_CRE_TIME,").append("R_MOD_ID,")
				.append("R_MOD_TIME ").append(" FROM rqst WHERE ")
				.append(" INST_ID=?").append("and BRANCH_ID=?")
				.append("and RQST_SRL_NUM=?").append(" and  DEL_FLG='n'");
		
		resultuser = getJdbcTemplate().query(query.toString(),
				new PreparedStatementSetter() {
					
					@Override
					public void setValues(java.sql.PreparedStatement pss)
							throws SQLException {
						pss.setString(1, instid);
						pss.setString(2, branchId);
						pss.setString(3, srlNum);
					}
					
				}, new ResultSetExtractor<Request>() {
					
					@Override
					public Request extractData(ResultSet rs)
							throws SQLException {
						logger.debug("Record Selected :" + rs.getFetchSize());
						Request request = null;
						if (rs.next()) {
							request = new Request();
							request.setDbTs(rs.getInt("DB_TS"));
							request.setUserId(rs.getString("USER_ID"));
							request.setInstId(rs.getString("INST_ID"));
							request.setBranchId(rs.getString("BRANCH_ID"));
							request.setRequestSerialNumber(rs
									.getString("RQST_SRL_NUM"));
							request.setReqstStatus(rs.getString("RQST_STATUS"));
							request.setReqstType(rs.getString("RQST_TYPE"));
							request.setDelFlg(rs.getString("DEL_FLG"));
							request.setrCreId(rs.getString("R_CRE_ID"));
							request.setrCreTime(rs.getString("R_CRE_TIME"));
							request.setrModId(rs.getString("R_MOD_ID"));
							request.setrModTime(rs.getString("R_MOD_TIME"));
							
						}
						return request;
					}
					
				});
		
		if (resultuser == null) {
			throw new NoDataFoundException();
		}
		return resultuser;
	}
	
	@Override
	public Request selectRequestRecordBasedonTypeAndStatus(final String instid,
			final String branchId, final String userId,
			final String requestType, final String requestStatus)
			throws NoDataFoundException {
		logger.debug("selectRequestStatus : InstId :" + instid + " BranchId: "
				+ branchId + " UserId:" + userId);
		Request resultuser = null;
		StringBuffer query = new StringBuffer();
		query.append("SELECT DB_TS, ").append("USER_ID,").append("INST_ID,")
				.append("BRANCH_ID,").append("RQST_SRL_NUM,")
				.append("RQST_TYPE,").append("RQST_STATUS,").append("DEL_FLG,")
				.append("R_CRE_ID,").append("R_CRE_TIME,").append("R_MOD_ID,")
				.append("R_MOD_TIME ").append(" FROM rqst").append("  WHERE ")
				.append(" INST_ID=?").append("and BRANCH_ID=?")
				.append("and user_id=?").append("and rqst_type=?")
				.append(" and rqst_status=?").append(" and  DEL_FLG='n'");
		
		resultuser = getJdbcTemplate().query(query.toString(),
				new PreparedStatementSetter() {
					
					@Override
					public void setValues(java.sql.PreparedStatement pss)
							throws SQLException {
						pss.setString(1, instid);
						pss.setString(2, branchId);
						pss.setString(3, userId);
						pss.setString(4, requestType);
						pss.setString(5, requestStatus);
					}
					
				}, new ResultSetExtractor<Request>() {
					
					@Override
					public Request extractData(ResultSet rs)
							throws SQLException {
						logger.debug("Record Selected :" + rs.getFetchSize());
						Request request = null;
						if (rs.next()) {
							request = new Request();
							request.setDbTs(rs.getInt("DB_TS"));
							request.setUserId(rs.getString("USER_ID"));
							request.setInstId(rs.getString("INST_ID"));
							request.setBranchId(rs.getString("BRANCH_ID"));
							request.setRequestSerialNumber(rs
									.getString("RQST_SRL_NUM"));
							request.setReqstStatus(rs.getString("RQST_STATUS"));
							request.setReqstType(rs.getString("RQST_TYPE"));
							request.setDelFlg(rs.getString("DEL_FLG"));
							request.setrCreId(rs.getString("R_CRE_ID"));
							request.setrCreTime(rs.getString("R_CRE_TIME"));
							request.setrModId(rs.getString("R_MOD_ID"));
							request.setrModTime(rs.getString("R_MOD_TIME"));
							
						}
						return request;
					}
					
				});
		if (resultuser == null) {
			throw new NoDataFoundException();
		}
		return resultuser;
	}
}
