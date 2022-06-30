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

import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.UpdateFailedException;
import com.jaw.framework.dao.BaseDao;

//CourseSubLinkDAO DAO class
@Repository
public class StudentFeepaymentDAO extends BaseDao implements
		IStudentFeePaymentDao {
	// Logging
	Logger logger = Logger.getLogger(StudentFeepaymentDAO.class);

	@Override
	public void insertStudentFeePayment(
			final StudentFeePayment StudentFeePayment)
			throws DuplicateEntryException {
		System.out.println("Inside insert method");
		System.out.println("CourseSubLink object values :"
				+ StudentFeePayment.toString());
		StringBuffer query = new StringBuffer();

		query = query
				.append("insert into sfpm ( ")
				.append("DB_TS,")
				.append("INST_ID,")
				.append("BRANCH_ID,")
				.append("SFEE_DMD_SEQ_ID,")
				.append("FEE_PMT_SRL_NO,")
				.append("FEE_DUE_AMT_BEFORE_PMT,")
				.append("FINE_AMT,")
				.append("FEE_PAID_AMT,")
				.append("MONTHLY_FEE_DUE_AMT_BEFORE_PMT ,")
				.append("MONTHLY_FEE_PAID_AMT ,")
				.append("FEE_PMT_DATE,")
				.append("FEE_PMT_STS,")
				.append("PAYMENT_MODE,")
				.append("INSTRUMENT_NO,")
				.append("INSTRUMENT_DETAILS,")
				.append("INSTRUMENT_DATE,")
				.append("FEE_RCPT_NO,")
				.append("RCPT_CATGRY,")
				.append("FEE_RCPT_DATE,")
				.append("DEL_FLG,")
				.append("R_MOD_ID,")
				.append("R_MOD_TIME,")
				.append("R_CRE_ID,")
				.append("R_CRE_TIME)")
				.append(" values (?,?,?, ?,?,?, ?,?,?, ?,?,?,?,?, ?,?,?, ?,?,?, ?, now(),?, now())");
		try {
			getJdbcTemplate().update(query.toString(),
					new PreparedStatementSetter() {

						@Override
						public void setValues(PreparedStatement pss)
								throws SQLException {

							pss.setInt(1, StudentFeePayment.getDbTs());
							pss.setString(2, StudentFeePayment.getInstId());
							pss.setString(3, StudentFeePayment.getBranchId());
							pss.setString(4, StudentFeePayment.getStudFeeDDId()
									.trim());
							pss.setString(5, StudentFeePayment.getFeePmtSrlNo()
									.trim());
							pss.setInt(6,
									StudentFeePayment.getFeeDueAmtBeforePmt());
							pss.setInt(7, StudentFeePayment.getFineAmt());
							pss.setInt(8, StudentFeePayment.getFeePaidAmt());
							pss.setInt(9, StudentFeePayment.getMonthlyFeeDueAmtBeforePmt());
							pss.setInt(10, StudentFeePayment.getMonthlyFeePaidAmt());
							pss.setString(11, StudentFeePayment.getFeePmtDate());
							pss.setString(12,
									StudentFeePayment.getFeePmtStatus());
							pss.setString(13, StudentFeePayment.getPmtMode());
							pss.setString(14,
									StudentFeePayment.getInstrumentNo());
							pss.setString(15,
									StudentFeePayment.getInstrumentDetails());
							pss.setString(16,
									StudentFeePayment.getInstrumentDate());
							pss.setString(17,
									StudentFeePayment.getFeeReceiptNo());
							pss.setString(18,
									StudentFeePayment.getReceiptCategory());
							pss.setString(19,
									StudentFeePayment.getFeeReceiptDate());
							pss.setString(20, StudentFeePayment.getDelFlag());
							pss.setString(21, StudentFeePayment.getrModId()
									.trim());
							pss.setString(22, StudentFeePayment.getrCreId()
									.trim());
						}

					});
		} catch (org.springframework.dao.DuplicateKeyException e) {
			throw new DuplicateEntryException();
		}

	}

	@Override
	public void updateStudentFeePayment(
			final StudentFeePayment StudentFeePayment,
			final StudentFeePaymentKey StudentFeePaymentKey)
			throws UpdateFailedException {
		System.out.println("Inside update method");
		System.out.println("Fee Category object values :"
				+ StudentFeePayment.toString());
		System.out.println("Fee Category key Details object values :"
				+ StudentFeePaymentKey.toString());
		StringBuffer sql = new StringBuffer();

		sql.append("update sfpm set ")
				.append(" DB_TS=? , ").append(" INST_ID=? , ")
				.append("BRANCH_ID=? , ").append(" SFEE_DMD_SEQ_ID=? , ")
				.append(" FEE_PMT_SRL_NO=? , ")
				.append(" FEE_DUE_AMT_BEFORE_PMT=? , ")
				.append(" FINE_AMT=? , ").append(" FEE_PAID_AMT=? , ")
				.append(" FEE_PMT_DATE=? , ").append(" FEE_PMT_STS=? , ")
				.append(" PAYMENT_MODE=? , ").append(" INSTRUMENT_NO=? , ")
				.append(" INSTRUMENT_DETAILS=? , ")
				.append(" INSTRUMENT_DATE=? , ").append(" FEE_RCPT_NO=? , ")
				.append(" RCPT_CATGRY=? , ").append(" FEE_RCPT_DATE=? , ")
				.append(" DEL_FLG=? , ").append(" R_MOD_ID=? , ")
				.append(" R_MOD_TIME=now() , ").append(" R_CRE_ID=? , ")
				.append(" R_CRE_TIME=? where").append(" DB_TS= ?")
				.append(" and  INST_ID= ?").append(" and  BRANCH_ID= ?")
				.append(" and  SFEE_DMD_SEQ_ID= ?")
				.append(" and  FEE_PMT_SRL_NO= ?").append(" and  DEL_FLG='N'");

		int updateRecs = getJdbcTemplate().update(sql.toString(),
				new PreparedStatementSetter() {

					@Override
					public void setValues(PreparedStatement pss)
							throws SQLException {
						pss.setInt(1, StudentFeePayment.getDbTs() + 1);
						pss.setString(2, StudentFeePayment.getInstId());
						pss.setString(3, StudentFeePayment.getBranchId());
						pss.setString(4, StudentFeePayment.getStudFeeDDId()
								.trim());
						pss.setString(5, StudentFeePayment.getFeePmtSrlNo()
								.trim());
						pss.setInt(6, StudentFeePayment.getFeeDueAmtBeforePmt());
						pss.setInt(7, StudentFeePayment.getFineAmt());
						pss.setInt(8, StudentFeePayment.getFeePaidAmt());
						pss.setString(9, StudentFeePayment.getFeePmtDate());
						pss.setString(10, StudentFeePayment.getFeePmtStatus());
						pss.setString(11, StudentFeePayment.getPmtMode());
						pss.setString(12, StudentFeePayment.getInstrumentNo());
						pss.setString(13,
								StudentFeePayment.getInstrumentDetails());
						pss.setString(14, StudentFeePayment.getInstrumentDate());
						pss.setString(15, StudentFeePayment.getFeeReceiptNo());
						pss.setString(16,
								StudentFeePayment.getReceiptCategory());
						pss.setString(17, StudentFeePayment.getFeeReceiptDate());
						pss.setString(18, StudentFeePayment.getDelFlag());
						pss.setString(19, StudentFeePayment.getrModId().trim());
						pss.setString(20, StudentFeePayment.getrCreId().trim());
						pss.setString(21, StudentFeePayment.getrCreTime().trim());
						pss.setInt(22, StudentFeePayment.getDbTs());
						pss.setString(23, StudentFeePayment.getInstId());
						pss.setString(24, StudentFeePayment.getBranchId());
						pss.setString(25, StudentFeePayment.getStudFeeDDId()
								.trim());
						pss.setString(26, StudentFeePayment.getFeePmtSrlNo()
								.trim());

					}

				});
		if (updateRecs == 0) {
			throw new UpdateFailedException();

		}

	}

	@Override
	public StudentFeePayment selectStudentFeePaymentWithPk(
			final StudentFeePaymentKey StudentFeePaymentKey)
			throws NoDataFoundException {
		System.out.println("Inside select method");
		System.out.println("Fee Category Key object values :"
				+ StudentFeePaymentKey.toString());
		List<String> data = new ArrayList<String>();
		StringBuffer sql = new StringBuffer();
		sql.append("select ").append(" DB_TS,").append("DB_TS,")
				.append("INST_ID,").append("BRANCH_ID,")
				.append("SFEE_DMD_SEQ_ID,").append("FEE_PMT_SRL_NO,")
				.append("FEE_DUE_AMT_BEFORE_PMT,").append("FINE_AMT,")
				.append("FEE_PAID_AMT,").append("FEE_PMT_DATE,")
				.append("FEE_PMT_STS,").append("PAYMENT_MODE,")
				.append("INSTRUMENT_NO,").append("INSTRUMENT_DETAILS,")
				.append("INSTRUMENT_DATE,").append("FEE_RCPT_NO,")
				.append("RCPT_CATGRY,").append("FEE_RCPT_DATE,")
				.append("DEL_FLG,").append("R_MOD_ID,").append("R_MOD_TIME,")
				.append("R_CRE_ID,").append("R_CRE_TIME ")
				.append(" from sfpm ").append(" where").append("   INST_ID= ?")
				.append(" and  BRANCH_ID= ?")
				.append(" and  SFEE_DMD_SEQ_ID= ?")
				.append(" and  FEE_PMT_SRL_NO= ?").append(" and  DEL_FLG=?");
		data.add(StudentFeePaymentKey.getInstId());
		data.add(StudentFeePaymentKey.getBranchId());
		data.add(StudentFeePaymentKey.getStudFeeDDId());
		data.add(StudentFeePaymentKey.getFeePmtSrlNo());
		data.add("N");

		String[] array = data.toArray(new String[data.size()]);
		StudentFeePayment StudentFeePayment = null;
		StudentFeePayment = (StudentFeePayment) getJdbcTemplate().query(
				sql.toString(), array,
				new ResultSetExtractor<StudentFeePayment>() {

					@Override
					public StudentFeePayment extractData(ResultSet rs)
							throws SQLException, DataAccessException {
						StudentFeePayment StudentFeePayment = null;
						if (rs.next()) {

							StudentFeePayment = new StudentFeePayment();
							StudentFeePayment.setDbTs(rs.getInt("DB_TS"));
							StudentFeePayment.setInstId(rs.getString("INST_ID"));
							StudentFeePayment.setBranchId(rs
									.getString("BRANCH_ID"));
							StudentFeePayment.setStudFeeDDId(rs
									.getString("SFEE_DMD_SEQ_ID"));
							StudentFeePayment.setFeePmtSrlNo(rs
									.getString("FEE_PMT_SRL_NO"));
							StudentFeePayment.setFeeDueAmtBeforePmt(rs
									.getInt("FEE_DUE_AMT_BEFORE_PMT"));
							StudentFeePayment.setFineAmt(rs.getInt("FINE_AMT"));
							StudentFeePayment.setFeePaidAmt(rs
									.getInt("FEE_PAID_AMT"));
							StudentFeePayment.setFeePmtDate(rs
									.getString("FEE_PMT_DATE"));
							StudentFeePayment.setFeePmtStatus(rs
									.getString("FEE_PMT_STS"));
							StudentFeePayment.setPmtMode(rs
									.getString("PAYMENT_MODE"));
							StudentFeePayment.setInstrumentNo(rs
									.getString("INSTRUMENT_NO"));
							StudentFeePayment.setInstrumentDetails(rs
									.getString("INSTRUMENT_DETAILS"));
							StudentFeePayment.setInstrumentDate(rs
									.getString("INSTRUMENT_DATE"));
							StudentFeePayment.setFeeReceiptNo(rs
									.getString("FEE_RCPT_NO"));
							StudentFeePayment.setReceiptCategory(rs
									.getString("RCPT_CATGRY"));
							StudentFeePayment.setFeeReceiptDate(rs
									.getString("FEE_RCPT_DATE"));
							StudentFeePayment.setDelFlag(rs
									.getString("DEL_FLG"));
							StudentFeePayment.setrModId(rs
									.getString("R_MOD_ID"));
							StudentFeePayment.setrModTime(rs
									.getString("R_MOD_TIME"));
							StudentFeePayment.setrCreId(rs
									.getString("R_CRE_ID"));
							StudentFeePayment.setrCreTime(rs
									.getString("R_CRE_TIME"));

						}
						return StudentFeePayment;
					}

				});

		if (StudentFeePayment == null) {
			throw new NoDataFoundException();
		}

		return StudentFeePayment;
	}

	@Override
	public StudentFeePayment selectStudentFeePayment(
			final StudentFeePaymentKey StudentFeePaymentKey)
			throws NoDataFoundException {
		System.out.println("Inside select method");
		System.out.println("Fee Category Key object values :"
				+ StudentFeePaymentKey.toString());
		List<String> data = new ArrayList<String>();
		StringBuffer sql = new StringBuffer();
		sql.append("select ").append(" DB_TS,").append("INST_ID,")
				.append("BRANCH_ID,").append("SFEE_DMD_SEQ_ID,")
				.append("max(FEE_PMT_SRL_NO) as FEE_PMT_SRL_NO,")
				.append("FEE_DUE_AMT_BEFORE_PMT,").append("MONTHLY_FEE_DUE_AMT_BEFORE_PMT,").append("MONTHLY_FEE_PAID_AMT,")
				.append("FINE_AMT,")
				.append("FEE_PAID_AMT,").append("FEE_PMT_DATE,")
				.append("FEE_PMT_STS,").append("PAYMENT_MODE,")
				.append("INSTRUMENT_NO,").append("INSTRUMENT_DETAILS,")
				.append("INSTRUMENT_DATE,").append("FEE_RCPT_NO,")
				.append("RCPT_CATGRY,").append("FEE_RCPT_DATE,")
				.append("DEL_FLG,").append("R_MOD_ID,").append("R_MOD_TIME,")
				.append("R_CRE_ID,").append("R_CRE_TIME ")
				.append(" from sfpm ").append(" where").append("   INST_ID= ?")
				.append(" and  BRANCH_ID= ?")
				.append(" and  SFEE_DMD_SEQ_ID= ?")
				.append(" and  DEL_FLG=? group by sfee_dmd_seq_id");
		data.add(StudentFeePaymentKey.getInstId());
		data.add(StudentFeePaymentKey.getBranchId());
		data.add(StudentFeePaymentKey.getStudFeeDDId());
		data.add("N");

		String[] array = data.toArray(new String[data.size()]);
		StudentFeePayment StudentFeePayment = null;
		StudentFeePayment = (StudentFeePayment) getJdbcTemplate().query(
				sql.toString(), array,
				new ResultSetExtractor<StudentFeePayment>() {

					@Override
					public StudentFeePayment extractData(ResultSet rs)
							throws SQLException, DataAccessException {
						StudentFeePayment StudentFeePayment = null;
						if (rs.next()) {

							StudentFeePayment = new StudentFeePayment();
							StudentFeePayment.setDbTs(rs.getInt("DB_TS"));
							StudentFeePayment.setInstId(rs.getString("INST_ID"));
							StudentFeePayment.setBranchId(rs
									.getString("BRANCH_ID"));
							StudentFeePayment.setStudFeeDDId(rs
									.getString("SFEE_DMD_SEQ_ID"));
							StudentFeePayment.setFeePmtSrlNo(rs
									.getString("FEE_PMT_SRL_NO"));
							StudentFeePayment.setFeeDueAmtBeforePmt(rs
									.getInt("FEE_DUE_AMT_BEFORE_PMT"));
							StudentFeePayment.setMonthlyFeeDueAmtBeforePmt(rs
									.getInt("MONTHLY_FEE_DUE_AMT_BEFORE_PMT"));
							StudentFeePayment.setMonthlyFeePaidAmt(rs
									.getInt("MONTHLY_FEE_PAID_AMT"));
							StudentFeePayment.setFineAmt(rs.getInt("FINE_AMT"));
							StudentFeePayment.setFeePaidAmt(rs
									.getInt("FEE_PAID_AMT"));
							StudentFeePayment.setFeePmtDate(rs
									.getString("FEE_PMT_DATE"));
							StudentFeePayment.setFeePmtStatus(rs
									.getString("FEE_PMT_STS"));
							StudentFeePayment.setPmtMode(rs
									.getString("PAYMENT_MODE"));
							StudentFeePayment.setInstrumentNo(rs
									.getString("INSTRUMENT_NO"));
							StudentFeePayment.setInstrumentDetails(rs
									.getString("INSTRUMENT_DETAILS"));
							StudentFeePayment.setInstrumentDate(rs
									.getString("INSTRUMENT_DATE"));
							StudentFeePayment.setFeeReceiptNo(rs
									.getString("FEE_RCPT_NO"));
							StudentFeePayment.setReceiptCategory(rs
									.getString("RCPT_CATGRY"));
							StudentFeePayment.setFeeReceiptDate(rs
									.getString("FEE_RCPT_DATE"));
							StudentFeePayment.setDelFlag(rs
									.getString("DEL_FLG"));
							StudentFeePayment.setrModId(rs
									.getString("R_MOD_ID"));
							StudentFeePayment.setrModTime(rs
									.getString("R_MOD_TIME"));
							StudentFeePayment.setrCreId(rs
									.getString("R_CRE_ID"));
							StudentFeePayment.setrCreTime(rs
									.getString("R_CRE_TIME"));

						}
						return StudentFeePayment;
					}

				});

		if (StudentFeePayment == null) {
			System.out.println("No demand exception thrown");
			throw new NoDataFoundException();
		}
		System.out.println("StudentFeePayment :" + StudentFeePayment);
		return StudentFeePayment;
	}

}
