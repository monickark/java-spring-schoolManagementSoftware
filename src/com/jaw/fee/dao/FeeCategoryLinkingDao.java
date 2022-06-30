package com.jaw.fee.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import com.jaw.common.exceptions.DeleteFailedException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.UpdateFailedException;
import com.jaw.framework.dao.BaseDao;

//CourseSubLinkDAO DAO class
@Repository
public class FeeCategoryLinkingDao extends BaseDao implements
		IFeeCategoryLinkingDao {
	// Logging
	Logger logger = Logger.getLogger(FeeCategoryLinkingDao.class);

	@Override
	public void insertFeeCategoryLinking(
			final FeeCategoryLinking feeCategoryLinking)
			throws DuplicateEntryException {
		System.out.println("Inside insert method");
		System.out.println("CourseSubLink object values :"
				+ feeCategoryLinking.toString());
		StringBuffer query = new StringBuffer();
		query = query.append("insert into fctl ( ").append("DB_TS,")
				.append("INST_ID,").append("BRANCH_ID,").append("FEE_CATGRY,")
				.append("FEE_TYPE,").append("ELECT_FEE_SUB_ID,").append("DEL_FLG,")
				.append("R_MOD_ID,").append("R_MOD_TIME,").append("R_CRE_ID,")
				.append("R_CRE_TIME)")
				.append(" values (?,?,?,?,?,?,?,?,now(),?,now())");

		try {
			getJdbcTemplate().update(query.toString(),
					new PreparedStatementSetter() {

						@Override
						public void setValues(PreparedStatement pss)
								throws SQLException {
							pss.setInt(1, feeCategoryLinking.getDbTs());
							pss.setString(2, feeCategoryLinking.getInstId());
							pss.setString(3, feeCategoryLinking.getBranchId());
							pss.setString(4, feeCategoryLinking
									.getFeeCategory().trim());
							pss.setString(5, feeCategoryLinking.getFeeType()
									.trim());
							pss.setString(6, feeCategoryLinking.getIsElective()
									.trim());
							pss.setString(7, feeCategoryLinking.getDelFlag()
									.trim());
							pss.setString(8, feeCategoryLinking.getrModId()
									.trim());
							pss.setString(9, feeCategoryLinking.getrCreId()
									.trim());

						}

					});
		} catch (org.springframework.dao.DuplicateKeyException e) {
			throw new DuplicateEntryException();
		}

	}

	@Override
	public void updateFeeCategoryLinking(
			final FeeCategoryLinking feeCategoryLinking,
			final FeeCategoryLinkingKey feeCategoryLinkingKey)
			throws UpdateFailedException {
		System.out.println("Inside update method");
		System.out.println("Fee Category object values :"
				+ feeCategoryLinking.toString());
		System.out.println("Fee Category key Details object values :"
				+ feeCategoryLinkingKey.toString());
		StringBuffer sql = new StringBuffer();
		
		
		sql.append("update fctl set")
		.append(" DB_TS= ?,")
				.append("INST_ID= ?,").append("BRANCH_ID= ?,")
				.append("FEE_CATGRY= ?,").append("FEE_TYPE= ?,")
				.append("ELECT_FEE_SUB_ID= ?,").append("DEL_FLG= 'N',")
				.append("R_MOD_ID= ?,").append(" R_MOD_TIME=now()")
				.append(" where").append(" DB_TS= ?")
				.append(" and  INST_ID= ?").append(" and  BRANCH_ID= ?")
				.append(" and  FEE_CATGRY= ?").append(" and  DEL_FLG='N'");

		int updateRecs = getJdbcTemplate().update(sql.toString(),
				new PreparedStatementSetter() {

					@Override
					public void setValues(PreparedStatement pss)
							throws SQLException {
						pss.setInt(1, feeCategoryLinking.getDbTs());
						pss.setString(2, feeCategoryLinking.getInstId());
						pss.setString(3, feeCategoryLinking.getBranchId());
						pss.setString(4, feeCategoryLinking
								.getFeeCategory().trim());
						pss.setString(5, feeCategoryLinking.getFeeType()
								.trim());
						pss.setString(6, feeCategoryLinking.getIsElective()
								.trim());
						pss.setString(7, feeCategoryLinking.getDelFlag()
								.trim());
						pss.setString(8, feeCategoryLinking.getrModId()
								.trim());
						pss.setString(9, feeCategoryLinking.getrCreId()
								.trim());
						pss.setInt(10, feeCategoryLinking.getDbTs());
						pss.setString(11, feeCategoryLinking.getInstId().trim());
						pss.setString(12, feeCategoryLinking.getBranchId().trim());
						pss.setString(13, feeCategoryLinking.getFeeCategory().trim());

					}

				});
		if (updateRecs == 0) {
			throw new UpdateFailedException();

		}

	}

	

	@Override
	public FeeCategoryLinking selectFeeCategoryLinking(
			final FeeCategoryLinkingKey feeCategoryLinkingKey)
			throws NoDataFoundException {
		System.out.println("Inside select method");
		System.out.println("Fee Category Key object values :"
				+ feeCategoryLinkingKey.toString());
		List<String> data = new ArrayList<String>();
		StringBuffer sql = new StringBuffer();
		
		sql.append("select ").append(" DB_TS,").append("INST_ID,")
				.append("BRANCH_ID,").append("FEE_CATGRY,")
				.append("FEE_TYPE,").append("ELECT_FEE_SUB_ID,").append("DEL_FLG,")
				.append("R_MOD_ID,").append("R_MOD_TIME,").append("R_CRE_ID,")
				.append("R_CRE_TIME").append(" from fctl ").append(" where")
				.append("   INST_ID= ?").append(" and  BRANCH_ID= ?")
				.append(" and  FEE_CATGRY= ?").append(" and  FEE_TYPE= ?").append(" and  DEL_FLG=?");
		data.add(feeCategoryLinkingKey.getInstId());
		data.add(feeCategoryLinkingKey.getBranchId());
		data.add(feeCategoryLinkingKey.getFeeCategory());
		data.add(feeCategoryLinkingKey.getFeeType());
		data.add("N");
	
		String[] array = data.toArray(new String[data.size()]);
		FeeCategoryLinking feeCategoryLinking = null;
		feeCategoryLinking = (FeeCategoryLinking) getJdbcTemplate().query(
				sql.toString(), array, new ResultSetExtractor<FeeCategoryLinking>() {

					@Override
					public FeeCategoryLinking extractData(ResultSet rs)
							throws SQLException, DataAccessException {
						FeeCategoryLinking feeCategoryLinking = null;
						if (rs.next()) {
							
							feeCategoryLinking = new FeeCategoryLinking();
							feeCategoryLinking.setDbTs(rs.getInt("DB_TS"));
							feeCategoryLinking.setInstId(rs.getString("INST_ID"));
							feeCategoryLinking.setBranchId(rs.getString("BRANCH_ID"));
							feeCategoryLinking.setFeeCategory(rs.getString("FEE_CATGRY"));
							feeCategoryLinking.setFeeType(rs
									.getString("FEE_TYPE"));
							feeCategoryLinking.setIsElective(rs
									.getString("ELECT_FEE_SUB_ID"));
							feeCategoryLinking.setDelFlag(rs.getString("DEL_FLG"));
							feeCategoryLinking.setrModId(rs.getString("R_MOD_ID"));
							feeCategoryLinking.setrModTime(rs
									.getString("R_MOD_TIME"));
							feeCategoryLinking.setrCreId(rs.getString("R_CRE_ID"));
							feeCategoryLinking.setrCreTime(rs
									.getString("R_CRE_TIME"));
						}
						return feeCategoryLinking;
					}

				});

		if (feeCategoryLinking == null) {
			throw new NoDataFoundException();
		}

		return feeCategoryLinking;
	}

	@Override
	public void deleteFeeCategoryLinking(final FeeCategoryLinkingKey feeCategoryLinkingKey)
			throws DeleteFailedException {

		logger.debug("Inside insert method in ClassTeacherAllotmentDao :"+feeCategoryLinkingKey.toString());

		StringBuffer sql = new StringBuffer();

		sql.append("delete from fctl where ")
		.append("DB_Ts=?")
		.append(" and inst_id=?")
		.append(" and branch_id=?")
		.append(" and FEE_CATGRY=?")
		.append(" and FEE_type=?")
		.append(" and del_flg=?");
		logger.debug("update query :" + sql.toString());

		int affectedRows = getJdbcTemplate().update(sql.toString(),
				new PreparedStatementSetter() {

			@Override
			public void setValues(PreparedStatement ps)
					throws SQLException {
				
				ps.setInt(1, feeCategoryLinkingKey.getDbTs());
				ps.setString(2, feeCategoryLinkingKey.getInstId().trim());
				ps.setString(3, feeCategoryLinkingKey.getBranchId().trim());
				ps.setString(4, feeCategoryLinkingKey.getFeeCategory().trim());
				ps.setString(5, feeCategoryLinkingKey.getFeeType().trim());
				ps.setString(6, "N");

			}

		});
		if (affectedRows == 0) {
			throw new DeleteFailedException();
		}

	}
	
	/*@Override
	public int checkRecordExist(String instId, String branchId,
			String courseId, String termId, String subId, String subType) {
		String SELECT_COUNT = "select count(0) from crsl where inst_id='"
				+ instId + "' and branch_id='" + branchId
				+ "' and coursemaster_id='" + courseId + "' and term_id='"
				+ termId + "' and sub_id='" + subId + "' and sub_type='"
				+ subType + "' and del_flg='N'";

		System.out.println("checkRecordExist query :" + SELECT_COUNT);
		return getJdbcTemplate().queryForInt(SELECT_COUNT);
	}*/
}
