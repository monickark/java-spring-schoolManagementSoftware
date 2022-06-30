package com.jaw.fee.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.framework.dao.BaseDao;
import com.jaw.login.controller.LoginController;

@Repository
public class FeeMasterListDao extends BaseDao implements IFeeMasterListDao {
	Logger logger = Logger.getLogger(LoginController.class);

	@Override
	public void insertFeeMasterBatch(final List<FeeMaster> FeeMasters)
			throws DuplicateEntryException {
		logger.info("inside insertListOfUserLinkRec method in dao");
		logger.debug("Size of FeeMaster list :" + FeeMasters.size());

		StringBuffer query = new StringBuffer();
		query = query.append("insert into fmst ( ").append("DB_TS,")
				.append("INST_ID,").append("BRANCH_ID,").append("AC_TERM,")
				.append("FEE_CATGRY,").append("FEE_TERM,").append("FEE_TYPE,")
				.append("COURSE,").append("TERM,").append("CV_SPEC,")
				.append("FEE_AMT,").append("DEL_FLG,").append("R_MOD_ID,")
				.append("R_MOD_TIME,").append("R_CRE_ID,")
				.append("R_CRE_TIME)")
				.append(" values (?,?,?,?,?,?,?,?,?,?,?,?,?,now(),?,now())");
		try {
			getJdbcTemplate().batchUpdate(query.toString(),
					new BatchPreparedStatementSetter() {

						@Override
						public void setValues(PreparedStatement pss, int i)
								throws SQLException {
							FeeMaster FeeMaster = FeeMasters.get(i);
							logger.debug("To string of the passed :"
									+ FeeMasters.toString());
							pss.setInt(1, FeeMaster.getDbTs());
							pss.setString(2, FeeMaster.getInstId());
							pss.setString(3, FeeMaster.getBranchId());
							pss.setString(4, FeeMaster.getAcTerm().trim());
							pss.setString(5, FeeMaster.getFeeCategory());
							pss.setString(6, FeeMaster.getFeeTerm());
							pss.setString(7, FeeMaster.getFeeType());
							pss.setString(8, FeeMaster.getCourse().trim());
							pss.setString(9, FeeMaster.getTerm());
							pss.setString(10, FeeMaster.getCourseVariant());
							pss.setInt(11, FeeMaster.getFeeAmt());
							pss.setString(12, FeeMaster.getDelFlag().trim());
							pss.setString(13, FeeMaster.getrModId().trim());
							pss.setString(14, FeeMaster.getrCreId().trim());

						}

						@Override
						public int getBatchSize() {
							return FeeMasters.size();
						}
					}

			);
		} catch (DuplicateKeyException duplicateKeyException) {
			throw new DuplicateEntryException();
		}
	}

	@Override
	public List<FeeMasterList> getFeeMasterList(FeeMasterKey feeMasterKey)
			throws NoDataFoundException {
		List<FeeMasterList> results = null;
		List<String> data = new ArrayList<String>();
		StringBuffer query = new StringBuffer();
		System.out.println("Key Object:" + feeMasterKey.toString());
		query.append("SELECT ").append("FEE_CATGRY,").append("FEE_TERM,")
				.append("FEE_TYPE,").append("FEE_AMT, ").append("CV_SPEC ")
				.append(" FROM fmst where inst_id=?")
				.append("and branch_id=? ").append("and AC_TERM=? ")
				.append("and COURSE=? ");
		if ((feeMasterKey.getTerm() != null)
				&& (!feeMasterKey.getTerm().equals(""))) {
			query.append("and TERM=? ");
		}
		if ((feeMasterKey.getFeeCategory() != null)
				&& (!feeMasterKey.getFeeCategory().equals(""))) {
			query.append("and fee_catgry=? ");
		}
		if ((feeMasterKey.getFeeTerm() != null)
				&& (!feeMasterKey.getFeeTerm().equals(""))) {
			query.append("and FEE_TERM=? ");
		}
		query.append(" and DEL_FLG=?");

		data.add(feeMasterKey.getInstId());
		data.add(feeMasterKey.getBranchId());
		data.add(feeMasterKey.getAcTerm());
		data.add(feeMasterKey.getCourse());
		if ((feeMasterKey.getTerm() != null)
				&& (!feeMasterKey.getTerm().equals(""))) {
			data.add(feeMasterKey.getTerm());
		}

		if ((feeMasterKey.getFeeCategory() != null)
				&& (!feeMasterKey.getFeeCategory().equals(""))) {
			data.add(feeMasterKey.getFeeCategory());
		}

		if ((feeMasterKey.getFeeTerm() != null)
				&& (!feeMasterKey.getFeeTerm().equals(""))) {
			data.add(feeMasterKey.getFeeTerm());
		}

		data.add("N");

		String[] array = data.toArray(new String[data.size()]);

		results = getJdbcTemplate().query(query.toString(), array,
				new FeeTypeRowMapper());
		if (results.size() == 0) {
			throw new NoDataFoundException();
		}
		logger.debug("Selected  records :" + results.size());
		return results;
	}

	@Override
	public List<FeeCategoryLinkingList> getUnAllottedFeetypeList(
			FeeMasterKey feeMasterKey) throws NoDataFoundException {
		List<FeeCategoryLinkingList> results = null;
		List<String> data = new ArrayList<String>();
		StringBuffer query = new StringBuffer();
		query.append(" select * from (")
				.append(" select A.fee_type, A.fee_catgry, A.elect_fee_sub_id from")
				.append(" ((select distinct(fee_type) as fee_type,fee_catgry,ELECT_FEE_SUB_ID from fctl,crsl")
				.append("  where fctl.inst_id = crsl.inst_id  and  fctl.branch_id = crsl.branch_id and")
				.append("  fctl.del_flg = crsl.del_flg and  fctl.inst_id = ? and  fctl.branch_id = ? ");
		if ((feeMasterKey.getFeeCategory() != null)
				&& (!feeMasterKey.getFeeCategory().equals(""))) {
			query.append("  and fctl.fee_catgry = ?");
		}

		query.append("and  crsl.coursemaster_id=? ");
		if ((feeMasterKey.getTerm() != null)
				&& (!feeMasterKey.getTerm().equals(""))) {
			query.append("and crsl.term_id =? ");
		}

		query.append(
				"  and fctl.del_flg = 'N' and crsl.sub_type in ('E1','E2') and elect_fee_sub_id like concat('%',crsl.sub_id,'%'))")
				.append("  union ")
				.append("  (select fee_type,fee_catgry,ELECT_FEE_SUB_ID from fctl where ")
				.append("  fctl.inst_id = ? and  fctl.branch_id = ? ");
		if ((feeMasterKey.getFeeCategory() != null)
				&& (!feeMasterKey.getFeeCategory().equals(""))) {
			query.append("  and fctl.fee_catgry = ?");
		}

		query.append("  and fctl.del_flg = 'N' and elect_fee_sub_id = '' )")
				.append("  )  as A  order by fee_type  ) as C left join")
				.append("  (select fee_type from fmst")
				.append("   where inst_id = ? and branch_id = ?");
		if ((feeMasterKey.getFeeCategory() != null)
				&& (!feeMasterKey.getFeeCategory().equals(""))) {
			query.append("  and fctl.fee_catgry = ?");
		}

		query.append("   and ac_term = ?");
		if ((feeMasterKey.getFeeTerm() != null)
				&& (!feeMasterKey.getFeeTerm().equals(""))) {
			query.append("  and fee_term = ?");
		}
		query.append("  and course = ?");
		if ((feeMasterKey.getTerm() != null)
				&& (!feeMasterKey.getTerm().equals(""))) {
			query.append("and term =? ");
		}

		query.append(" and del_flg = 'N' order by fee_type")
				.append("   ) as D on C.fee_type = D.fee_type where D.fee_type is null;");
		data.add(feeMasterKey.getInstId());
		data.add(feeMasterKey.getBranchId());
		if ((feeMasterKey.getFeeCategory() != null)
				&& (!feeMasterKey.getFeeCategory().equals(""))) {
			data.add(feeMasterKey.getFeeCategory());
		}
		data.add(feeMasterKey.getCourse());
		if ((feeMasterKey.getTerm() != null)
				&& (!feeMasterKey.getTerm().equals(""))) {
			data.add(feeMasterKey.getTerm());
		}
		data.add(feeMasterKey.getInstId());
		data.add(feeMasterKey.getBranchId());
		if ((feeMasterKey.getFeeCategory() != null)
				&& (!feeMasterKey.getFeeCategory().equals(""))) {
			data.add(feeMasterKey.getFeeCategory());
		}
		data.add(feeMasterKey.getInstId());
		data.add(feeMasterKey.getBranchId());
		if ((feeMasterKey.getFeeCategory() != null)
				&& (!feeMasterKey.getFeeCategory().equals(""))) {
			data.add(feeMasterKey.getFeeCategory());
		}
		data.add(feeMasterKey.getAcTerm());
		if ((feeMasterKey.getFeeTerm() != null)
				&& (!feeMasterKey.getFeeTerm().equals(""))) {
			data.add(feeMasterKey.getFeeTerm());
		}
		
		data.add(feeMasterKey.getCourse());
		if ((feeMasterKey.getTerm() != null)
				&& (!feeMasterKey.getTerm().equals(""))) {
			data.add(feeMasterKey.getTerm());
		}

		String[] array = data.toArray(new String[data.size()]);

		results = getJdbcTemplate().query(query.toString(), array,
				new FeeCategoryRowMapper());
		if (results.size() == 0) {
			throw new NoDataFoundException();
		}
		logger.debug("un allotted fee type  records :" + results.size());
		return results;
	}

	class FeeCategoryRowMapper implements RowMapper<FeeCategoryLinkingList> {

		@Override
		public FeeCategoryLinkingList mapRow(ResultSet rs, int arg1)
				throws SQLException {
			FeeCategoryLinkingList feeCategoryLinkingList = new FeeCategoryLinkingList();
			feeCategoryLinkingList.setFeeCategory(rs.getString("FEE_CATGRY"));
			feeCategoryLinkingList.setFeeType(rs.getString("FEE_TYPE"));
			feeCategoryLinkingList.setElectiveFeeSubId(rs
					.getString("ELECT_FEE_SUB_ID"));
			return feeCategoryLinkingList;
		}
	}

	class FeeTypeRowMapper implements RowMapper<FeeMasterList> {

		@Override
		public FeeMasterList mapRow(ResultSet rs, int arg1) throws SQLException {
			FeeMasterList FeeMasterList = new FeeMasterList();
			FeeMasterList.setFeeCategory(rs.getString("FEE_CATGRY"));
			FeeMasterList.setFeeTerm(rs.getString("FEE_TERM"));
			FeeMasterList.setFeeType(rs.getString("FEE_TYPE"));
			FeeMasterList.setFeeAmt(rs.getInt("FEE_AMT"));
			FeeMasterList.setCourseVariant(rs.getString("CV_SPEC"));
			return FeeMasterList;
		}
	}

}
