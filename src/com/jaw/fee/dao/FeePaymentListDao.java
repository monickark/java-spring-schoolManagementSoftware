package com.jaw.fee.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.framework.dao.BaseDao;

//MarkSubjectLinkDAO DAO class
@Repository
public class FeePaymentListDao extends BaseDao implements IFeePaymentListDao {
	// Logging
	Logger logger = Logger.getLogger(FeePaymentListDao.class);

	@Override
	public Map<String, String> getStuAdmisNoList(final String instId,
			final String branchId, final String acTerm, final String stGroup)
			throws NoDataFoundException {
		logger.debug("Inside select method : instId :" + instId + " branch id:"
				+ branchId + " acterm:" + acTerm + " stGroup :" + stGroup);

		StringBuffer sql = new StringBuffer();
		sql.append(
				"select  stum.student_admis_no, student_name from stum, stgm,stin where")
				.append(" stum.inst_id = stgm.inst_id and ")
				.append(" stum.branch_id = stgm.branch_id and ")
				.append(" stum.student_admis_no=stin.student_admis_no and ")
				.append(" stum.course = stgm.coursemaster_id and ")
				.append(" stum.sec = stgm.sec_id and ")
				.append(" stum.del_flg = stgm.del_flg and ")
				.append(" stum.inst_id=? and ")
				.append("  stum.branch_id = ?  and")
				.append("  stum.academic_year = ? and")
				.append("  stum.del_flg = ? and")
				.append("  stgm.studentgrp_id = ? order by student_admis_no");

		Map<String, String> result = getJdbcTemplate().query(sql.toString(),
				new PreparedStatementSetter() {

					@Override
					public void setValues(PreparedStatement pss)
							throws SQLException {
						pss.setString(1, instId);
						pss.setString(2, branchId);
						pss.setString(3, acTerm);
						pss.setString(4, "N");
						pss.setString(5, stGroup);
					}
				}, new StudentListExtractor());
		System.out.println("List returned size :" + result.size());
		if (result.size() == 0) {
			throw new NoDataFoundException();
		}
		return result;
	}

	@Override
	public String getTerm(final ReceiptKey termSelectionForReceipt)
			throws NoDataFoundException {
		logger.debug("Inside select method : termSelectionForReceipt :"
				+ termSelectionForReceipt.toString());

		StringBuffer sql = new StringBuffer();

		sql.append(
				" select group_concat(ACOCD.code_desc separator ', ') as term from")
				.append(" (select distinct(fee_term) from ")
				.append(" sfee where inst_id = ? and")
				.append(" branch_id = ? and").append(" ac_term = ? and ")
				.append(" student_admis_no = ? and ")
				.append(" fee_catgry = ? and ")
				.append(" fee_pmt_term = ? and ")
				.append(" fee_type != ? order by fee_term) as STUFEE,")
				.append(" (select cm_code, code_desc from cocd where ")
				.append("	inst_id = ? and ").append("	branch_id = ? and ")
				.append("	code_type = ?) as ACOCD")
				.append(" where STUFEE.fee_term = ACOCD.cm_code ")
				.append(" order by ACOCD.code_desc;");

		String result = getJdbcTemplate().query(sql.toString(),
				new PreparedStatementSetter() {

					@Override
					public void setValues(PreparedStatement pss)
							throws SQLException {
						pss.setString(1, termSelectionForReceipt.getInstId());
						pss.setString(2, termSelectionForReceipt.getBranchId());
						pss.setString(3, termSelectionForReceipt.getAcTerm());
						pss.setString(4,
								termSelectionForReceipt.getStudentAdmisNo());
						pss.setString(5,
								termSelectionForReceipt.getFeeCategory());
						pss.setString(6,
								termSelectionForReceipt.getFeePaymentTerm());
						pss.setString(7, "FTPD");
						pss.setString(8, termSelectionForReceipt.getInstId());
						pss.setString(9, termSelectionForReceipt.getBranchId());
						pss.setString(10, "FTRM");
					}
				}, new TermListExtractor());
		System.out.println("term got :" + result);

		return result;
	}

	@Override
	public Map<String, String> getAdmissionStudentList(final String instId,
			final String branchId, final String acTerm, final String feeCategory)
			throws NoDataFoundException {
		logger.debug("Inside select method : instId :" + instId + " branch id:"
				+ branchId + " acterm:" + acTerm + " feeCategory :"
				+ feeCategory);

		StringBuffer sql = new StringBuffer();

		sql.append("select A.student_admis_no, A.student_name from")
				.append(" (select stin.student_admis_no,student_name from stin, stum where")
				.append(" stin.inst_id = stum.inst_id and")
				.append(" stin.branch_id = stum.branch_id and")
				.append(" stin.del_flg = stum.del_flg and")
				.append(" stin.academic_year = stum.academic_year and")
				.append(" stin.student_admis_no = stum.student_admis_no and")
				.append(" stin.inst_id = ? and")
				.append(" stin.branch_id = ? and")
				.append(" stin.academic_year = ? and")
				.append(" stin.del_flg =? order by stin.student_admis_no) as A left join")
				.append(" (select student_admis_no from sfdd where")
				.append(" inst_id = ? and")
				.append(" branch_id = ? and")
				.append(" del_flg = ? and")
				.append(" ac_Term = ? and")
				.append(" fee_catgry = ? order by student_admis_no) as B on")
				.append(" A.student_admis_no = B.student_admis_no where B.student_admis_no is null order by student_admis_no ;");

		Map<String, String> result = getJdbcTemplate().query(sql.toString(),
				new PreparedStatementSetter() {

					@Override
					public void setValues(PreparedStatement pss)
							throws SQLException {
						pss.setString(1, instId);
						pss.setString(2, branchId);
						pss.setString(3, acTerm);
						pss.setString(4, "N");
						pss.setString(5, instId);
						pss.setString(6, branchId);
						pss.setString(7, "N");
						pss.setString(8, acTerm);
						pss.setString(9, feeCategory);
					}
				}, new StudentListExtractor());
		System.out.println("List returned size :" + result.size());
		if (result.size() == 0) {
			throw new NoDataFoundException();
		}
		return result;
	}

	@Override
	public FeeReceipt selectReceiptRec(final ReceiptKey receiptKey)
			throws NoDataFoundException {
		logger.debug("Inside select method");
		logger.debug("Course Classes Key object values :"
				+ receiptKey.toString());
		StringBuffer sql = new StringBuffer();

		sql.append(
				" select FEE_PAID_AMT-FINE_AMT as total ,FINE_AMT,FEE_PAID_AMT,instrument_no,fee_rcpt_no,rcpt_catgry,fee_rcpt_date from sfpm,sfdd where ")
				.append(" sfpm.inst_id = sfdd.inst_id and")
				.append(" sfpm.branch_id = sfdd.branch_id and")
				.append(" sfpm.sfee_dmd_seq_id = sfdd.sfee_dmd_seq_id and")
				.append(" sfpm.inst_id = ? ")
				.append(" and sfpm.branch_id = ? ")
				.append(" and sfpm.sfee_dmd_seq_id = ? ")
				.append("and fee_pmt_srl_no=? ;");

		FeeReceipt selectedFeeReceiptDetails = null;
		selectedFeeReceiptDetails = (FeeReceipt) getJdbcTemplate().query(
				sql.toString(), new PreparedStatementSetter() {

					@Override
					public void setValues(PreparedStatement ps)
							throws SQLException {
						ps.setString(1, receiptKey.getInstId().trim());
						ps.setString(2, receiptKey.getBranchId().trim());
						ps.setString(3, receiptKey.getStudFeeDDId().trim());
						ps.setString(4, receiptKey.getFeePmtSrlNo().trim());

					}

				}, new ResultSetExtractor<FeeReceipt>() {

					@Override
					public FeeReceipt extractData(ResultSet rs)
							throws SQLException, DataAccessException {
						FeeReceipt FeeReceipt = null;
						if (rs.next()) {
							FeeReceipt = new FeeReceipt();
							FeeReceipt.setTotalPaid(rs.getInt("total"));
							FeeReceipt.setFineAmt(rs.getInt("FINE_AMT"));
							FeeReceipt.setFeePaidAmt(rs.getInt("FEE_PAID_AMT"));
							FeeReceipt.setInstrumentNo(rs.getString("instrument_no"));
							FeeReceipt.setFeeReceiptNo(rs.getString("fee_rcpt_no"));							
							FeeReceipt.setFeeReceiptDate(rs.getString("fee_rcpt_date"));
						}
						return FeeReceipt;
					}

				});

		if (selectedFeeReceiptDetails == null) {
			throw new NoDataFoundException();
		}

		return selectedFeeReceiptDetails;
	}

	class StudentListExtractor implements
			ResultSetExtractor<Map<String, String>> {
		@Override
		public Map<String, String> extractData(ResultSet rs)
				throws SQLException {

			Map<String, String> map = new LinkedHashMap<String, String>();
			while (rs.next()) {
				String key = rs.getString("student_admis_no");
				String value = rs.getString("student_name");
				map.put(key, value);
			}
			return map;
		}
	}

	class TermListExtractor implements ResultSetExtractor<String> {
		@Override
		public String extractData(ResultSet rs) throws SQLException {
			String key = null;
			while (rs.next()) {
				key = rs.getString("term");
			}
			return key;
		}
	}
}
