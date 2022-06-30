package com.jaw.core.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import com.jaw.common.exceptions.DeleteFailedException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.framework.dao.BaseDao;

//This class is used to perform crud operation of standard combination table

@Repository
public class StandardCombinationCrudDao extends BaseDao implements
		IStandardCombinationCrudDao {

	Logger logger = Logger.getLogger(StandardCombinationCrudDao.class);

	@Override
	public void insertStandard(final StandardCombination standard)
			throws DuplicateEntryException {
		logger.debug("Inside insert method in Standard Crud DAO");
		StringBuffer sql = new StringBuffer();
		sql = sql.append("insert into stdl ( ").append("DB_TS,")
				.append("INST_ID,").append("BRANCH_ID,").append("STD_ID,")
				.append("COMBINATION_ID,").append("DEL_FLG,")
				.append("R_MOD_ID,").append("R_MOD_TIME,").append("R_CRE_ID,")
				.append("R_CRE_TIME)")
				.append(" values (?,?,?,?,?,?,?,now(),?,now())");
		logger.debug("insert query :" + sql.toString());
		try {
			getJdbcTemplate().update(sql.toString(),
					new PreparedStatementSetter() {

						@Override
						public void setValues(java.sql.PreparedStatement pss)
								throws SQLException {
							pss.setInt(1, standard.getDbTs());
							pss.setString(2, standard.getInstId());
							pss.setString(3, standard.getBranchId());
							pss.setString(4, standard.getStandardId());
							pss.setString(5, standard.getCombinationId());
							pss.setString(6, standard.getDelFlag());
							pss.setString(7, standard.getrModId());
							pss.setString(8, standard.getrCreId());
						}
					});
		} catch (DuplicateKeyException e) {
			logger.debug("Duplicat entry");
			throw new DuplicateEntryException();
		}
	}

	@Override
	public void deleteStandardCombination(final StandardCombination standard,
			final StandardCombinationKey standardKey)
			throws DeleteFailedException {
		logger.debug("Inside update method");
		StringBuffer sql = new StringBuffer();
		sql.append("update stdl set ").append("DB_TS=?,").append("INST_ID=?,")
				.append("BRANCH_ID=?,").append("STD_ID=?,")
				.append("COMBINATION_ID=?,").append("DEL_FLG=?,")
				.append("R_MOD_ID=?,").append("R_MOD_TIME=now() where ")
				.append("INST_ID=? and ").append("BRANCH_ID=? and ")
				.append("STD_ID=? and ").append("COMBINATION_ID=? and ")
				.append("DB_TS=? ");
		logger.debug("update query :" + sql.toString());

		int affectedRows = getJdbcTemplate().update(sql.toString(),
				new PreparedStatementSetter() {

					@Override
					public void setValues(java.sql.PreparedStatement pss)
							throws SQLException {
						pss.setInt(1, standard.getDbTs() + 1);
						pss.setString(2, standard.getInstId());
						pss.setString(3, standard.getBranchId());
						pss.setString(4, standard.getStandardId());
						pss.setString(5, standard.getCombinationId());
						pss.setString(6, standard.getDelFlag());
						pss.setString(7, standard.getrModId());
						pss.setString(8, standardKey.getInstId());
						pss.setString(9, standardKey.getBranchId());
						pss.setString(10, standardKey.getStandardId());
						pss.setString(11, standardKey.getCombinationId());
						pss.setInt(12, standard.getDbTs());
					}
				});

		if (affectedRows == 0) {
			throw new DeleteFailedException();
		}
	}

	@Override
	public StandardCombination selectStandardCombination(
			final StandardCombinationKey standardKey)
			throws NoDataFoundException {
		logger.debug("Inside update method");
		StringBuffer sql = new StringBuffer();
		sql.append("select ").append("DB_TS,").append("INST_ID,")
				.append("BRANCH_ID,").append("STD_ID,")
				.append("COMBINATION_ID,").append("DEL_FLG,")
				.append("R_MOD_ID,").append("R_MOD_TIME,").append("R_CRE_ID,")
				.append("R_CRE_TIME from stdl ").append("where ")
				.append("INST_ID=? and ").append("BRANCH_ID=? and ")
				.append("STD_ID=? and ").append("COMBINATION_ID=? and ")
				.append("DEL_FLG=?");
		logger.debug("select query :" + sql.toString());

		StandardCombination standardList = (StandardCombination) getJdbcTemplate()
				.query(sql.toString(), new PreparedStatementSetter() {

					@Override
					public void setValues(java.sql.PreparedStatement pss)
							throws SQLException {
						pss.setString(1, standardKey.getInstId());
						pss.setString(2, standardKey.getBranchId());
						pss.setString(3, standardKey.getStandardId());
						pss.setString(4, standardKey.getCombinationId());
						pss.setString(5, standardKey.getDelFlg());
					}
				}, new ResultSetExtractor<StandardCombination>() {

					@Override
					public StandardCombination extractData(ResultSet rs)
							throws SQLException {
						StandardCombination standard = null;
						if (rs.next()) {
							standard = new StandardCombination();
							standard.setDbTs(rs.getInt("DB_TS"));
							standard.setBranchId(rs.getString("BRANCH_ID"));
							standard.setInstId(rs.getString("inst_id"));
							standard.setStandardId((rs.getString("STD_ID")));
							standard.setCombinationId((rs
									.getString("COMBINATION_ID")));
							standard.setDelFlag((rs.getString("DEL_FLG")));
							standard.setrModId(rs.getString("R_MOD_ID"));
							standard.setrModTime(rs.getString("R_MOD_TIME"));
							standard.setrCreId(rs.getString("R_CRE_ID"));
							standard.setrCreTime(rs.getString("R_CRE_TIME"));

						}
						return standard;
					}

				});
		if (standardList == null) {
			throw new NoDataFoundException();
		}
		return standardList;

	}
}
