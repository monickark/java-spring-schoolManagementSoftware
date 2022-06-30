package com.jaw.prodAdm.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.framework.appCache.dao.CommonCode;
import com.jaw.framework.dao.BaseDao;

@Repository
public class CommonCodeListDao extends BaseDao implements ICommonCodeListDao {
	Logger logger = Logger.getLogger(CommonCodeListDao.class);

	@Override
	public List<CommonCode> getCocdListByType(final CommonCodeKey code)
			throws NoDataFoundException {
		StringBuffer sql = new StringBuffer();
		sql.append("select ").append("CODE_TYPE,").append("CM_CODE,")
				.append("CODE_DESC").append(" from cocd where ")
				.append("INST_ID=? and ").append("BRANCH_ID=? and ")
				.append("DEL_FLG=? and ").append("CODE_TYPE=? ");
		logger.debug("select query :" + sql.toString());

		List<CommonCode> cocdResult = getJdbcTemplate().query(sql.toString(),
				new PreparedStatementSetter() {

					@Override
					public void setValues(PreparedStatement pss)
							throws SQLException {

						pss.setString(1, code.getInstId());
						pss.setString(2, code.getBranchId());
						pss.setString(3, "N");
						pss.setString(4, code.getCodeType());

					}
				}, new CocdRowMapper());
		return cocdResult;

	}

	@Override
	public List<CommonCode> retriveType() throws NoDataFoundException {
		StringBuffer sql = new StringBuffer();
		sql.append("select ").append("distinct code_type ")
				.append("from cocd ").append("where DEL_FLG=? ");

		List<CommonCode> searchResult = getJdbcTemplate().query(sql.toString(),
				new PreparedStatementSetter() {

					@Override
					public void setValues(PreparedStatement pss)
							throws SQLException {

						pss.setString(1, "N");

					}
				}, new CommonCodeRowMapper());
		if (searchResult.size() == 0) {
			throw new NoDataFoundException();
		}
		return searchResult;
	}
}

class CommonCodeRowMapper implements RowMapper<CommonCode> {

	@Override
	public CommonCode mapRow(ResultSet rs, int arg1) throws SQLException {
		CommonCode list = new CommonCode();
		list.setCodeType((rs.getString("CODE_TYPE")));

		return list;
	}
}

class CocdRowMapper implements RowMapper<CommonCode> {

	@Override
	public CommonCode mapRow(ResultSet rs, int arg1) throws SQLException {
		CommonCode list = new CommonCode();
		list.setCodeType((rs.getString("CODE_TYPE")));
		list.setCommonCode((rs.getString("CM_CODE")));
		list.setCodeDescription(((rs.getString("CODE_DESC"))));

		return list;
	}
}
