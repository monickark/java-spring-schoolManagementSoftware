package com.jaw.core.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.stereotype.Repository;

import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.framework.dao.BaseDao;

@Repository
public class StandardSectionDao extends BaseDao implements IStandardSectionDao {
	Logger logger = Logger.getLogger(StandardSectionDao.class);

	@Override
	public void insertStandard(final List<StandardSection> standard)
			throws DuplicateEntryException {

		logger.debug("Inside insert method in ClassTeacherAllotmentDao");

		StringBuffer sql = new StringBuffer();
		sql = sql.append("insert into sdsc ( ").append("DB_TS,")
				.append("INST_ID,").append("BRANCH_ID,").append("STD_ID,")
				.append("COMBINATION_ID,").append("SEC_ID,")
				.append("MEDIUM_ID,").append("SG_ID,").append("TTG_ID,")
				.append("TTG_PROCESS,").append("DEL_FLG,").append("R_MOD_ID,")
				.append("R_MOD_TIME,").append("R_CRE_ID,")
				.append("R_CRE_TIME)")
				.append(" values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
		try {

			getJdbcTemplate().batchUpdate(sql.toString(),
					new BatchPreparedStatementSetter() {
						@Override
						public int getBatchSize() {
							return standard.size();
						}

						@Override
						public void setValues(PreparedStatement rs, int i)
								throws SQLException {
							StandardSection batch = standard.get(i);
							rs.setInt(1, batch.getDbTs());
							rs.setString(2, batch.getInstId());
							rs.setString(3, batch.getBranchId());
							rs.setString(4, batch.getStandard());
							rs.setString(5, batch.getCombination());
							rs.setString(6, batch.getSection());
							rs.setString(7, batch.getMedium());
							rs.setString(8, batch.getSgId());
							rs.setString(9, batch.getTtgId());
							rs.setString(10, batch.getTtgProcess());
							rs.setString(11, batch.getDelFlag());
							rs.setString(12, batch.getrModId());
							rs.setString(13, batch.getrModTime());
							rs.setString(14, batch.getrCreId());
							rs.setString(15, batch.getrCreTime());

						}
					});
		} catch (DuplicateKeyException e) {
			e.printStackTrace();
			throw new DuplicateEntryException();
		}

	}

}
