package com.jaw.fee.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.framework.dao.BaseDao;

@Repository
public class FeeDetailsListDAO extends BaseDao implements IFeeDetailsListDAO {
	// Logging
	Logger logger = Logger.getLogger(FeeDetailsListDAO.class);

	@Override
	public List<FeeDueList> selectFeeDueListDetails(FeeDueListKey feeDueListKey)
			throws NoDataFoundException {
		logger.debug("DAO :Inside get Fee due List  method");
		logger.debug("DAO :Inside FeeDueListKey values : "
				+ feeDueListKey.toString());
		List<Object> data = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();

		sql.append("select ").append("fee_catgry,").append("fee_pmt_term,")
				.append("fee_due_amt, ").append("last_year_unpaid_amt ")
				.append(" from sfdd ").append(" where").append("  INST_ID= ?")
				.append(" and  BRANCH_ID= ?").append(" and  ac_term=?")
				.append(" and  student_admis_no=?")
				.append(" and  fee_dmd_sts != ?")
				.append(" and fee_due_amt!=0.00").append(" and  DEL_FLG=?");

		data.add(feeDueListKey.getInstId());
		data.add(feeDueListKey.getBranchId());
		data.add(feeDueListKey.getAcademicTerm());
		data.add(feeDueListKey.getAdmissionNumber());
		data.add(feeDueListKey.getFeeDemandStatus());
		data.add("N");

		sql.append(" order by fee_catgry, fee_pmt_term");
		Object[] array = data.toArray(new Object[data.size()]);
		List<FeeDueList> selectedListFeeDueList = null;
		selectedListFeeDueList = getJdbcTemplate().query(sql.toString(), array,
				new FeeDueListSelectRowMapper());

		/*
		 * if (selectedListFeeDueList.size() == 0) { throw new
		 * NoDataFoundException(); }
		 */
		logger.debug("DAO : Fee due list size" + selectedListFeeDueList.size());

		return selectedListFeeDueList;
	}

	@Override
	public List<FeePaidList> selectFeePaidListDetails(
			FeePaidListKey feePaidListKey) throws NoDataFoundException {
		logger.debug("DAO :Inside get Fee Paid List  method");
		logger.debug("DAO :Inside FeePaidListKey values : "
				+ feePaidListKey.toString());
		List<Object> data = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();

		sql.append("select ").append("fee_catgry,").append("fee_pmt_term,")
				.append("sfdd.sfee_dmd_seq_id, ").append("fee_pmt_srl_no ,")
				.append("fee_pmt_date,").append("fee_paid_amt ,")
				.append("fee_pmt_sts, ").append("rcpt_catgry, ")
				.append("last_year_unpaid_amt,").append("fee_rcpt_no ")

				.append(" from sfdd,sfpm ").append(" where")
				.append("  sfdd.inst_id = sfpm.inst_id ")
				.append(" and  sfdd.branch_id = sfpm.branch_id")
				.append(" and  sfdd.sfee_dmd_seq_id = sfpm.sfee_dmd_seq_id")
				.append(" and  sfdd.del_flg = sfpm.del_flg")
				.append(" and  sfdd.inst_id = ?")

				.append(" and  sfdd.branch_id = ?")
				.append(" and  sfdd.del_flg = 'N' ")
				.append(" and  sfdd.ac_term = ?")
				.append(" and  sfdd.student_admis_no = ?");

		data.add(feePaidListKey.getInstId());
		data.add(feePaidListKey.getBranchId());
		data.add(feePaidListKey.getAcademicTerm());
		data.add(feePaidListKey.getAdmissionNumber());

		sql.append(" order by  fee_pmt_date asc ");
		Object[] array = data.toArray(new Object[data.size()]);
		List<FeePaidList> selectedListFeePaidList = null;
		selectedListFeePaidList = getJdbcTemplate().query(sql.toString(),
				array, new FeePaidListSelectRowMapper());

		logger.debug("DAO : Fee due list size" + selectedListFeePaidList.size());

		return selectedListFeePaidList;
	}

	@Override
	public List<FeeDueDetailsList> selectFeeDueDetailsList(FeeDueList feeDueList)
			throws NoDataFoundException {
		logger.debug("DAO :Inside get Fee duedetailsList  method");
		logger.debug("DAO :Inside FeeDueList values : " + feeDueList.toString());
		List<Object> data = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();

		sql.append("select ")
				.append("FEE_TERM,")
				.append("FEE_TYPE,")
				.append("ELECT_SPEC, ")
				.append("sfee.FEE_AMT, ")
				.append("last_year_unpaid_amt,")
				.append("sfdd.FEE_AMT as TotalFeeAmt, ")
				.append("sfdd.CONCESSION_AMT, ")
				.append(" (sfdd.FEE_AMT -concession_amt-fee_due_amt+last_year_unpaid_amt) as fee_paid_amt, ")
				.append("sfdd.FEE_DUE_AMT, ")
				.append("sfdd.monthly_fee_due_amt ").append(" from sfee,sfdd ")
				.append(" where")
				.append("  sfee.INST_ID=sfdd.INST_ID")
				.append(" and sfee.BRANCH_ID=sfdd.BRANCH_ID")
				.append(" and  sfee.DEL_FLG=sfdd.DEL_FLG")
				// .append(" and  sfee.FEE_CATGRY=sfdd.FEE_CATGRY")
				.append(" and  sfee.student_admis_no=sfdd.student_admis_no")
				.append(" and  sfee.INST_ID=?")
				.append(" and  sfee.BRANCH_ID=?");
		if ((feeDueList.getFeePaymentTerm() != null)
				&& (!feeDueList.getFeePaymentTerm().equals(""))) {
			sql.append(" and sfee.FEE_PMT_TERM=?");
		}
		sql.append(" and sfee.student_admis_no = ?").append(
				" and sfee.DEL_FLG=?");

		data.add(feeDueList.getInstId());
		data.add(feeDueList.getBranchId());
		if ((feeDueList.getFeePaymentTerm() != null)
				&& (!feeDueList.getFeePaymentTerm().equals(""))) {
			data.add(feeDueList.getFeePaymentTerm());
		}
		data.add(feeDueList.getAdmissionNumber());
		data.add("N");

		Object[] array = data.toArray(new Object[data.size()]);
		List<FeeDueDetailsList> selectedListFeeDueDetailList = null;
		selectedListFeeDueDetailList = getJdbcTemplate().query(sql.toString(),
				array, new FeeDueDetailsListSelectRowMapper());

		if (selectedListFeeDueDetailList.size() == 0) {
			throw new NoDataFoundException();
		}
		logger.debug("DAO : Fee due Detail list size"
				+ selectedListFeeDueDetailList.size());

		return selectedListFeeDueDetailList;
	}

}

class FeeDueDetailsListSelectRowMapper implements RowMapper<FeeDueDetailsList> {

	@Override
	public FeeDueDetailsList mapRow(ResultSet rs, int arg1) throws SQLException {
		FeeDueDetailsList feeDueDetailList = new FeeDueDetailsList();
		feeDueDetailList.setFeeTerm(rs.getString("FEE_TERM"));
		feeDueDetailList.setFeeType(rs.getString("FEE_TYPE"));
		feeDueDetailList.setElectiveSpec(rs.getString("ELECT_SPEC"));
		feeDueDetailList.setFeePaidAmt(rs.getInt("FEE_PAID_AMT"));
		feeDueDetailList.setFeeTermAmt(rs.getInt("FEE_AMT"));
		feeDueDetailList.setTotalFeeAmt(rs.getInt("TotalFeeAmt"));
		feeDueDetailList.setConcessionAmt(rs.getInt("CONCESSION_AMT"));
		feeDueDetailList.setFeeDueAmt(rs.getInt("FEE_DUE_AMT"));
		feeDueDetailList.setLastYearPayment(rs.getInt("last_year_unpaid_amt"));
		feeDueDetailList.setMonthlyFeeDueAmt(rs.getInt("monthly_fee_due_amt"));
		return feeDueDetailList;
	}
}

class FeePaidListSelectRowMapper implements RowMapper<FeePaidList> {

	@Override
	public FeePaidList mapRow(ResultSet rs, int arg1) throws SQLException {
		FeePaidList feePaidList = new FeePaidList();

		feePaidList.setFeeCategory(rs.getString("FEE_CATGRY"));
		feePaidList.setFeePaymentTerm(rs.getString("FEE_PMT_TERM"));
		feePaidList.setFeePaidAmt(rs.getInt("FEE_PAID_AMT"));

		feePaidList.setsFeeDmdSeqId(rs.getString("SFEE_DMD_SEQ_ID"));
		feePaidList.setFeePaymentSrlNo(rs.getString("FEE_PMT_SRL_NO"));
		feePaidList.setFeePaymentDate(rs.getString("FEE_PMT_DATE"));
		feePaidList.setFeePaymentSts(rs.getString("FEE_PMT_STS"));

		feePaidList.setFeeReceiptCatgry(rs.getString("rcpt_catgry"));
		feePaidList.setFeeReceiptNum(rs.getString("fee_rcpt_no"));

		return feePaidList;
	}
}

class FeeDueListSelectRowMapper implements RowMapper<FeeDueList> {

	@Override
	public FeeDueList mapRow(ResultSet rs, int arg1) throws SQLException {
		FeeDueList feeDueList = new FeeDueList();
		feeDueList.setFeeCategory(rs.getString("FEE_CATGRY"));
		feeDueList.setFeePaymentTerm(rs.getString("FEE_PMT_TERM"));
		feeDueList.setFeeDueAmt(rs.getInt("FEE_DUE_AMT"));
		return feeDueList;
	}
}
