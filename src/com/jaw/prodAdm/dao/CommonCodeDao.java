package com.jaw.prodAdm.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import com.jaw.common.exceptions.DeleteFailedException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.UpdateFailedException;
import com.jaw.framework.appCache.dao.CommonCode;
import com.jaw.framework.dao.BaseDao;

@Repository
public class CommonCodeDao extends BaseDao implements ICommonCodeDao {
	Logger logger = Logger.getLogger(CommonCodeDao.class);

	@Override
	public void insertCocdRecord(final CommonCode code)
			throws DuplicateEntryException {
		logger.debug("Inside insert method");

		StringBuffer sql = new StringBuffer();
		sql = sql.append("insert into cocd ( ").append("DB_TS,")
				.append("INST_ID,").append("BRANCH_ID,").append("CODE_TYPE,")
				.append("CM_CODE,").append("CODE_DESC,").append("DEL_FLG,")
				.append("R_MOD_ID,").append("R_MOD_TIME,").append("R_CRE_ID,")
				.append("R_CRE_TIME)")
				.append(" values (?,?,?,?,?,?,?,?,now(),?,now()) ");

		logger.debug("insert query :" + sql.toString());
		try {
			getJdbcTemplate().update(sql.toString(),
					new PreparedStatementSetter() {

						@Override
						public void setValues(PreparedStatement pss)
								throws SQLException {
							pss.setInt(1, code.getDbTs());
							pss.setString(2, code.getInstId());
							pss.setString(3, code.getBranchId());
							pss.setString(4, code.getCodeType());
							pss.setString(5, code.getCommonCode());
							pss.setString(6, code.getCodeDescription());
							pss.setString(7, code.getDelFlag());
							pss.setString(8, code.getrModId());
							pss.setString(9, code.getrCreId());
						}
					});
		} catch (org.springframework.dao.DuplicateKeyException e) {
			throw new DuplicateEntryException();
		}

	}

	@Override
	public void updateCocdRecord(final CommonCode code,
			final CommonCodeKey codeKey) throws UpdateFailedException {
		logger.debug("Inside update method");

		StringBuffer sql = new StringBuffer();
		sql.append("update cocd set ").append("DB_TS=?,").append("INST_ID=?,")
				.append("BRANCH_ID=?,").append("CODE_TYPE=?,")
				.append("CM_CODE=?,").append("CODE_DESC=?,")
				.append("DEL_FLG=?,").append("R_MOD_ID=?,")
				.append("R_MOD_TIME= now() where ").append("INST_ID=? and ")
				.append("DB_TS=? and ").append("BRANCH_ID=? and ")
				.append("CODE_TYPE=? and ").append("CM_CODE=?  ");
		logger.debug("update query :" + sql.toString());

		int affectedrow = getJdbcTemplate().update(sql.toString(),
				new PreparedStatementSetter() {

					@Override
					public void setValues(PreparedStatement pss)
							throws SQLException {
						pss.setInt(1, code.getDbTs() + 1);
						pss.setString(2, code.getInstId());
						pss.setString(3, code.getBranchId());
						pss.setString(4, code.getCodeType());
						pss.setString(5, code.getCommonCode());
						pss.setString(6, code.getCodeDescription());
						pss.setString(7, code.getDelFlag());
						pss.setString(8, code.getrModId());
						pss.setString(9, codeKey.getInstId());
						pss.setInt(10, code.getDbTs());
						pss.setString(11, codeKey.getBranchId());
						pss.setString(12, codeKey.getCodeType());
						pss.setString(13, codeKey.getCommonCode());
					}
				});
		if (affectedrow == 0) {
			throw new UpdateFailedException();
		}

	}

	@Override
	public void deleteCocdRecord(final CommonCode code,
			final CommonCodeKey codeKey) throws DeleteFailedException {
		logger.debug("Inside update method");

		StringBuffer sql = new StringBuffer();
		sql.append("update cocd set ").append("DB_TS=?,").append("INST_ID=?,")
				.append("BRANCH_ID=?,").append("CODE_TYPE=?,")
				.append("CM_CODE=?,").append("CODE_DESC=?,")
				.append("DEL_FLG=?,").append("R_MOD_ID=?,")
				.append("R_MOD_TIME= now() where ").append("INST_ID=? and ")
				.append("DB_TS=? and ").append("BRANCH_ID=? and ")
				.append("CODE_TYPE=? and ").append("CM_CODE=?  ");
		logger.debug("update query :" + sql.toString());

		int affectedrow = getJdbcTemplate().update(sql.toString(),
				new PreparedStatementSetter() {

					@Override
					public void setValues(PreparedStatement pss)
							throws SQLException {
						pss.setInt(1, code.getDbTs() + 1);
						pss.setString(2, code.getInstId());
						pss.setString(3, code.getBranchId());
						pss.setString(4, code.getCodeType());
						pss.setString(5, code.getCommonCode());
						pss.setString(6, code.getCodeDescription());
						pss.setString(7, code.getDelFlag());
						pss.setString(8, code.getrModId());
						pss.setString(9, codeKey.getInstId());
						pss.setInt(10, code.getDbTs());
						pss.setString(11, codeKey.getBranchId());
						pss.setString(12, codeKey.getCodeType());
						pss.setString(13, codeKey.getCommonCode());
					}
				});
		if (affectedrow == 0) {
			throw new DeleteFailedException();
		}

	}

	@Override
	public CommonCode selectCocdRecord(final CommonCodeKey code)
			throws NoDataFoundException {
		logger.debug("Inside select method");
		StringBuffer sql = new StringBuffer();
		sql.append("select ").append("DB_TS,").append("INST_ID,")
				.append("BRANCH_ID,").append("CODE_TYPE,").append("CM_CODE,")
				.append("CODE_DESC,").append("DEL_FLG,").append("R_MOD_ID,")
				.append("R_MOD_TIME,").append("R_CRE_ID,")
				.append("R_CRE_TIME from cocd ").append("where ")
				.append("INST_ID=? and ").append("BRANCH_ID=? and ")
				.append("CODE_TYPE=? and ").append("CM_CODE=? and ")
				.append("DEL_FLG=?");
		logger.debug("select query :" + sql.toString());

		CommonCode commonCode = getJdbcTemplate().query(sql.toString(),
				new PreparedStatementSetter() {

					@Override
					public void setValues(PreparedStatement pss)
							throws SQLException {
						pss.setString(1, code.getInstId());
						pss.setString(2, code.getBranchId());
						pss.setString(3, code.getCodeType());
						pss.setString(4, code.getCommonCode());

						pss.setString(5, code.getDelFlg());
					}
				}, new ResultSetExtractor<CommonCode>() {

					@Override
					public CommonCode extractData(ResultSet rs)
							throws SQLException {
						CommonCode record = null;
						if (rs.next()) {
							record = new CommonCode();
							record.setDbTs(rs.getInt("DB_TS"));
							record.setBranchId(rs.getString("BRANCH_ID"));
							record.setInstId(rs.getString("inst_id"));
							record.setCodeType((rs.getString("CODE_TYPE")));
							record.setCommonCode((rs.getString("CM_CODE")));
							record.setCodeDescription(((rs
									.getString("CODE_DESC"))));
							record.setDelFlag((rs.getString("DEL_FLG")));
							record.setrModId(rs.getString("R_MOD_ID"));
							record.setrModTime(rs.getString("R_MOD_TIME"));
							record.setrCreId(rs.getString("R_CRE_ID"));
							record.setrCreTime(rs.getString("R_CRE_TIME"));
						}

						return record;
					}

				});
		if (commonCode == null) {
			throw new NoDataFoundException();
		}
		return commonCode;
	}
}
