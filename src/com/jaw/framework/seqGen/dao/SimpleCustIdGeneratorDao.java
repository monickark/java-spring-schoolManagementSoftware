package com.jaw.framework.seqGen.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import com.jaw.common.constants.ApplicationConstant;
import com.jaw.common.exceptions.DatabaseException;
import com.jaw.framework.dao.BaseDao;
import com.jaw.framework.seqGen.service.SimpleIdGeneratorService;
import com.jaw.framework.sessCache.UserSessionDetails;

//Dao class for Batch Sequence Id Generation
@Repository("simpleCustIdGenDao")
public class SimpleCustIdGeneratorDao extends BaseDao implements ICustIdGeneratorDao {

	// Logging
	Logger logger = Logger.getLogger(SimpleIdGeneratorService.class);

	// method to get Sequence
	@Override
	public int getIdForSequence(final String key,final UserSessionDetails userSessionDetails) throws DatabaseException {

		int ID = 0;
		logger.debug("Inside select method");

		StringBuffer sql = new StringBuffer();
System.out.println("User session details:"+userSessionDetails+" key:"+key);
		sql = sql.append("SELECT ").append("NEXT_SEQUENCE_NO ")
				.append("FROM cseq ").append("WHERE INST_ID = ?")
				.append("and BRANCH_ID = ?")
				.append("and SEQUENCE_TYPE = ?");

		ID = getJdbcTemplate().query(sql.toString(),
				new PreparedStatementSetter() {

					@Override
					public void setValues(PreparedStatement pss)
							throws SQLException {
						pss.setString(1, userSessionDetails.getInstId());
						pss.setString(2, userSessionDetails.getBranchId());
						pss.setString(3, key);
					}

				}, new ResultSetExtractor<Integer>() {

					@Override
					public Integer extractData(ResultSet rs)
							throws SQLException, DataAccessException {

						Integer idNo = null;
						if (rs.next()) {
							idNo = rs.getInt("NEXT_SEQUENCE_NO");
						}

						return idNo;
					}

				});
		final Integer nextSequ = (ID + ApplicationConstant.SIMPLE_ID_GEN_INC);
		logger.debug("select query :" + sql.toString());

		StringBuffer sqlQuery = new StringBuffer();
		sqlQuery = sqlQuery.append("UPDATE cseq set")
				.append(" NEXT_SEQUENCE_NO=?")
				.append(" WHERE INST_ID = ?")
				.append(" and BRANCH_ID = ?")
				.append(" and SEQUENCE_TYPE = ?");
		int updateStatus = getJdbcTemplate().update(sqlQuery.toString(),
				new PreparedStatementSetter() {

					@Override
					public void setValues(PreparedStatement ps)
							throws SQLException {
						ps.setInt(1, nextSequ);
						ps.setString(2, userSessionDetails.getInstId());
						ps.setString(3, userSessionDetails.getBranchId());
						ps.setString(4, key);

					}

				});
		if (updateStatus == 0) {
			throw new DatabaseException();

		}
		logger.debug("update query :" + sqlQuery.toString());
		return ID;
	}

}
