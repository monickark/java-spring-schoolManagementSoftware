package com.jaw.fee.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.jaw.common.constants.CommonCodeConstant;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.framework.dao.BaseDao;
import com.jaw.login.controller.LoginController;

@Repository
public class FeeCategoryLinkingListDao extends BaseDao implements
		IFeeCategoryLinkingListDao {
	Logger logger = Logger.getLogger(LoginController.class);

	@Override
	public void insertFeeCategoryBatch(
			final List<FeeCategoryLinking> feeCategoryLinkings)
			throws DuplicateEntryException {
		logger.info("inside insertListOfUserLinkRec method in dao");
		logger.debug("Size of FeeCategory list :" + feeCategoryLinkings.size());
		StringBuffer query = new StringBuffer();
		query = query.append("insert into fctl ( ").append("DB_TS,")
				.append("INST_ID,").append("BRANCH_ID,").append("FEE_CATGRY,")
				.append("FEE_TYPE,").append("ELECT_FEE_SUB_ID,").append("DEL_FLG,")
				.append("R_MOD_ID,").append("R_MOD_TIME,").append("R_CRE_ID,")
				.append("R_CRE_TIME)")
				.append(" values (?,?,?,?,?,?,?,?,now(),?,now())");
		try {
			getJdbcTemplate().batchUpdate(query.toString(),
					new BatchPreparedStatementSetter() {

						@Override
						public void setValues(PreparedStatement pss, int i)
								throws SQLException {
							FeeCategoryLinking feeCategoryLinking = feeCategoryLinkings
									.get(i);
							logger.debug("To string of the passed :"
									+ feeCategoryLinking.toString());

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

						@Override
						public int getBatchSize() {
							return feeCategoryLinkings.size();
						}
					}

			);
		} catch (DuplicateKeyException duplicateKeyException) {
			throw new DuplicateEntryException();
		}
	}

	@Override
	public Map<String, String> getUnAllottedFeeType(final String instId,
			final String branchid, final String feeCategory)
			throws NoDataFoundException {

		logger.debug("Inside get FeeCategory list method in FeeCategoryAllotmentListDao"
				+ instId + " " + branchid+" "+feeCategory);

		StringBuffer sql = new StringBuffer();

		sql.append("select ").append("  cm_code,code_desc from cocd where ")
				.append(" inst_id=? and ").append(" branch_id = ? and ")
				.append(" code_type = ? and ").append(" del_flg = ? and ")
				.append(" cm_code not in ")
				.append(" (select fee_type from fctl where ")
				.append(" inst_id=? and ").append(" branch_id = ? and ")
				.append(" fee_catgry = ? and ")
				.append(" del_flg = ? ) ");

		logger.debug("select query in FeeCategoryAllotmentListDao:"
				+ sql.toString());

		Map<String, String> feeType = getJdbcTemplate().query(sql.toString(),
				new PreparedStatementSetter() {

					@Override
					public void setValues(PreparedStatement pss)
							throws SQLException {
						pss.setString(1, instId);
						pss.setString(2, branchid);
						pss.setString(3, CommonCodeConstant.FEE_TYPE);
						pss.setString(4, "N");
						pss.setString(5, instId);
						pss.setString(6, branchid);
						pss.setString(7, feeCategory);
						pss.setString(8, "N");

					}

				}, new FeeTypeExtractor());

		if (feeType.size() == 0) {
			throw new NoDataFoundException();
		}

		System.out.println("List size :" + feeType.size());
		return feeType;
	}

	@Override
	public Map<String, String> getAllottedFeeCategory(
			final String instId, final String branchid, final String feeCategory)
			throws NoDataFoundException {

		logger.debug("Inside get FeeCategory list method in FeeCategoryAllotmentListDao"
				+ instId + " " + branchid+" "+feeCategory);

		StringBuffer sql = new StringBuffer();

		sql.append("select ").append("FEE_TYPE, ")
				.append("fctl.ELECT_FEE_SUB_ID ").append("FROM fctl where ")
				.append("fctl.INST_ID=? and ").append("fctl.BRANCH_ID=? and ")
				.append("fctl.fee_catgry=? and ")
				.append("fctl.DEL_FLG=? ");

		logger.debug("select query in FeeCategoryAllotmentListDao:"
				+ sql.toString());

		Map<String, String> FeeCategoryList = getJdbcTemplate()
				.query(sql.toString(), new PreparedStatementSetter() {

					@Override
					public void setValues(PreparedStatement pss)
							throws SQLException {
						pss.setString(1, instId);
						pss.setString(2, branchid);
						pss.setString(3, feeCategory);
						pss.setString(4, "N");

					}

				}, new FeeCategoryExtractor());

		if (FeeCategoryList.size() == 0) {
			throw new NoDataFoundException();
		}

		System.out.println("List size :" + FeeCategoryList.size());
		return FeeCategoryList;
	}
	
	
	@Override
	public Map<String, String> getElectiveSubjects(
			final String instId, final String branchid)
			throws NoDataFoundException {

		logger.debug("Inside get FeeCategory list method in FeeCategoryAllotmentListDao"
				+ instId + " " + branchid);

		StringBuffer sql = new StringBuffer();

		sql.append("select sub_id,sub_name from sbjm where ")
				.append("INST_ID=? and ").append("BRANCH_ID=? and ")
				.append("is_elective=? and ")
				.append("DEL_FLG=? ");

		logger.debug("select query in FeeCategoryAllotmentListDao:"
				+ sql.toString());

		Map<String, String> subjectList = getJdbcTemplate()
				.query(sql.toString(), new PreparedStatementSetter() {

					@Override
					public void setValues(PreparedStatement pss)
							throws SQLException {
						pss.setString(1, instId);
						pss.setString(2, branchid);
						pss.setString(3, "Y");
						pss.setString(4, "N");

					}

				}, new SubjectExtractor());

		if (subjectList.size() == 0) {
			throw new NoDataFoundException();
		}

		System.out.println("List size :" + subjectList.size());
		return subjectList;
	}
	

	@Override
	public List<FeeCategoryLinkingList> getFeeCategoryList(FeeMasterKey feeMasterKey)
			throws NoDataFoundException {
		List<FeeCategoryLinkingList> results = null;
		List<String> data = new ArrayList<String>();
		StringBuffer query = new StringBuffer();

		query.append("SELECT ")
				.append("FEE_CATGRY,").append("FEE_TYPE,")
				.append("ELECT_FEE_SUB_ID ")
				.append(" FROM fctl where inst_id=? and branch_id=?");
		if ((feeMasterKey.getFeeCategory() != null)
				&& (!feeMasterKey.getFeeCategory().equals(""))) {
			query.append("and fee_catgry=? ");
		} 

	
		data.add(feeMasterKey.getInstId());
		data.add(feeMasterKey.getBranchId());
		if ((feeMasterKey.getFeeCategory() != null)
				&& (!feeMasterKey.getFeeCategory().equals(""))) {
			data.add(feeMasterKey.getFeeCategory());
		}
		
		String[] array = data.toArray(new String[data.size()]);

		results = getJdbcTemplate().query(query.toString(), array,
				new FeeTypeRowMapper());
		if (results.size() == 0) {
			throw new NoDataFoundException();
		}
		logger.debug("Selected  records :" + results.size());
		return results;
	}

	class FeeTypeRowMapper implements RowMapper<FeeCategoryLinkingList> {

		@Override
		public FeeCategoryLinkingList mapRow(ResultSet rs, int arg1) throws SQLException {
			FeeCategoryLinkingList feeCategoryLinkingList = new FeeCategoryLinkingList();
			feeCategoryLinkingList.setFeeCategory(rs.getString("FEE_CATGRY"));
			feeCategoryLinkingList.setFeeType(rs.getString("FEE_TYPE"));
			feeCategoryLinkingList.setElectiveFeeSubId(rs.getString("ELECT_FEE_SUB_ID"));
			return feeCategoryLinkingList;
		}
	}

	class SubjectExtractor implements ResultSetExtractor<Map<String, String>> {
		@Override
		public Map<String, String> extractData(ResultSet rs)
				throws SQLException {

			Map<String, String> map = new LinkedHashMap<String, String>();
			while (rs.next()) {
				String key = (rs.getString("sub_id"));
				String value = (rs.getString("sub_name"));

				map.put(key, value);
			}
			return map;
		}
	}
	
	class FeeCategoryExtractor implements ResultSetExtractor<Map<String, String>> {
		@Override
		public Map<String, String> extractData(ResultSet rs)
				throws SQLException {

			Map<String, String> map = new LinkedHashMap<String, String>();
			while (rs.next()) {
				String key = (rs.getString("FEE_TYPE"));
				String value = (rs.getString("ELECT_FEE_SUB_ID"));

				map.put(key, value);
			}
			return map;
		}
	}
	
	class FeeTypeExtractor implements ResultSetExtractor<Map<String, String>> {
		@Override
		public Map<String, String> extractData(ResultSet rs)
				throws SQLException {

			Map<String, String> map = new LinkedHashMap<String, String>();
			while (rs.next()) {
				String key = (rs.getString("cm_code"));
				String value = (rs.getString("code_desc"));

				map.put(key, value);
			}
			return map;
		}
	}
}
