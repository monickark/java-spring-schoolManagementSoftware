package com.jaw.fee.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.UpdateFailedException;
import com.jaw.framework.dao.BaseDao;

//CourseSubLinkDAO DAO class
@Repository
public class StudentFeeDemandDAO extends BaseDao implements
		IStudentFeeDemandDAO {
	// Logging
	Logger logger = Logger.getLogger(StudentFeeDemandDAO.class);

	/*
	 * @Override public void insertStudentFeeDemandKey(final StudentFeeDemandKey
	 * StudentFeeDemandKey) throws DuplicateEntryException {
	 * System.out.println("Inside insert method");
	 * System.out.println("CourseSubLink object values :" +
	 * StudentFeeDemandKey.toString()); StringBuffer query = new StringBuffer();
	 * 
	 * query = query.append("insert into spfm ( ").append("DB_TS,")
	 * .append("INST_ID,"
	 * ).append("BRANCH_ID,").append("SFEE_DMD_SEQ_ID,").append
	 * ("FEE_PMT_SRL_NO,")
	 * .append("FEE_DUE_AMT_BEFORE_PMT,").append("FINE_AMT,")
	 * .append("FEE_PAID_AMT,")
	 * .append("FEE_PMT_DATE,").append("FEE_PMT_STS,").append("Demand_MODE,")
	 * .append
	 * ("INSTRUMENT_NO,").append("INSTRUMENT_DETAILS,").append("INSTRUMENT_DATE,"
	 * ) .append("FEE_RCPT_NO,").append("RCPT_CATGRY,").append("FEE_RCPT_DATE,")
	 * .append("DEL_FLG,").append("R_MOD_ID,").append("R_MOD_TIME,")
	 * .append("R_CRE_ID,").append("R_CRE_TIME)") .append(
	 * " values (?,?,?, ?,?,?, ?,?,?, ?,?,?, ?,?,?, ?,?,?, ?, now(),?, now())");
	 * try { getJdbcTemplate().update(query.toString(), new
	 * PreparedStatementSetter() {
	 * 
	 * @Override public void setValues(PreparedStatement pss) throws
	 * SQLException {
	 * 
	 * pss.setInt(1, StudentFeeDemandKey.getDbTs()); pss.setString(2,
	 * StudentFeeDemandKey.getInstId()); pss.setString(3,
	 * StudentFeeDemandKey.getBranchId()); pss.setString(4,
	 * StudentFeeDemandKey.getStudFeeDDId().trim()); pss.setString(5,
	 * StudentFeeDemandKey.getFeePmtSrlNo().trim()); pss.setString(6,
	 * StudentFeeDemandKey.getFeeDueAmtBeforePmt().trim()); pss.setString(7,
	 * StudentFeeDemandKey.getFineAmt()); pss.setString(8,
	 * StudentFeeDemandKey.getFeePaidAmt()); pss.setString(9,
	 * StudentFeeDemandKey.getFeePmtDate()); pss.setString(10,
	 * StudentFeeDemandKey.getFeePmtStatus()); pss.setString(11,
	 * StudentFeeDemandKey.getPmtMode()); pss.setString(12,
	 * StudentFeeDemandKey.getInstrumentNo()); pss.setString(13,
	 * StudentFeeDemandKey.getInstrumentDetails()); pss.setString(14,
	 * StudentFeeDemandKey.getInstrumentDate()); pss.setString(15,
	 * StudentFeeDemandKey.getFeeReceiptNo()); pss.setString(16,
	 * StudentFeeDemandKey.getReceiptCategory()); pss.setString(17,
	 * StudentFeeDemandKey.getFeeReceiptDate()); pss.setString(18,
	 * StudentFeeDemandKey.getDelFlag()); pss.setString(19,
	 * StudentFeeDemandKey.getrModId().trim()); pss.setString(20,
	 * StudentFeeDemandKey.getrCreId().trim()); }
	 * 
	 * }); } catch (org.springframework.dao.DuplicateKeyException e) { throw new
	 * DuplicateEntryException(); }
	 * 
	 * }
	 */

	@Override
	public void updateStudentFeeDemandKey(
			final StudentFeeDemand StudentFeeDemand,
			final StudentFeeDemandKey StudentFeeDemandKey)
			throws UpdateFailedException {
		System.out.println("Inside update method");
		System.out.println("Student Demand key object values :"
				+ StudentFeeDemandKey.toString());
		System.out.println("Student Demand  Details object values :"
				+ StudentFeeDemand.toString());
		StringBuffer sql = new StringBuffer();

		sql.append("update sfdd set").append(" DB_TS= ? , ")
				.append("INST_ID=? , ").append("BRANCH_ID=? , ")
				.append("SFEE_DMD_SEQ_ID=? , ").append("AC_TERM=? , ")
				.append("FEE_CATGRY=? , ").append("STUDENT_ADMIS_NO=? , ")
				.append("FEE_PMT_TERM=? , ").append("FEE_AMT=? , ")
				.append("CONCESSION_AMT=? , ").append("FEE_DUE_AMT=? , ")/*.append("MONTHLY_FEE_AMT=?, ").append("MONTHLY_FEE_DUE_AMT=?, ")*/
				.append("STUDENT_ACCOUNT_NO=? , ").append("FEE_DMD_STS=? , ")
				.append("FEE_DMD_REMARKS=? , ").append("DEL_FLG=? , ")
				.append("R_MOD_ID=? , ").append("R_MOD_TIME= now() , ")
				.append("R_CRE_ID=? , ").append("R_CRE_TIME  =?  where")
				.append(" DB_TS= ?").append(" and  INST_ID= ?")
				.append(" and  BRANCH_ID= ?")
				.append(" and  SFEE_DMD_SEQ_ID= ?").append(" and  DEL_FLG='N'");

		int updateRecs = getJdbcTemplate().update(sql.toString(),
				new PreparedStatementSetter() {
					@Override
					public void setValues(java.sql.PreparedStatement pss)
							throws SQLException {
						pss.setInt(1, StudentFeeDemand.getDbTs() + 1);
						System.out.println("StudentFeeDemand.getDbTs() + 1 :"
								+ (StudentFeeDemand.getDbTs() + 1));
						pss.setString(2, StudentFeeDemand.getInstId());
						System.out.println("StudentFeeDemand.getInstId() :"
								+ StudentFeeDemand.getInstId());
						pss.setString(3, StudentFeeDemand.getBranchId());
						System.out.println("StudentFeeDemand.getBranchId() :"
								+ StudentFeeDemand.getBranchId());
						pss.setString(4, StudentFeeDemand.getsFeeDmdSeqId()
								.trim());
						System.out
								.println("StudentFeeDemand.getsFeeDmdSeqId() :"
										+ StudentFeeDemand.getsFeeDmdSeqId());
						pss.setString(5, StudentFeeDemand.getAcTerm().trim());
						System.out.println("StudentFeeDemand.getAcTerm() :"
								+ StudentFeeDemand.getAcTerm());
						pss.setString(6, StudentFeeDemand.getFeeCategory());
						System.out
								.println("StudentFeeDemand.getFeeCategory() :"
										+ StudentFeeDemand.getFeeCategory());
						pss.setString(7, StudentFeeDemand.getStudentAdmissNo());
						System.out
								.println("StudentFeeDemand.getStudentAdmissNo() :"
										+ StudentFeeDemand.getStudentAdmissNo());
						pss.setString(8, StudentFeeDemand.getFeePaymentTerm());
						System.out
								.println("StudentFeeDemand.getFeePaymentTerm() :"
										+ StudentFeeDemand.getFeePaymentTerm());
						pss.setInt(9, StudentFeeDemand.getFeeAmt());
						System.out.println("StudentFeeDemand.getFeeAmt() :"
								+ StudentFeeDemand.getFeeAmt());
						pss.setInt(10, StudentFeeDemand.getConcessionAmt());
						System.out
								.println("StudentFeeDemand.getConcessionAmt() :"
										+ StudentFeeDemand.getConcessionAmt());
						pss.setInt(11, StudentFeeDemand.getFeeDueAmt());
						System.out.println("StudentFeeDemand.getFeeDueAmt() :"
								+ StudentFeeDemand.getFeeDueAmt());
						pss.setString(12, StudentFeeDemand.getStudentAccNum());
						System.out
								.println("StudentFeeDemand.getStudentAccNum() :"
										+ StudentFeeDemand.getStudentAccNum());
						/*pss.setInt(13, StudentFeeDemand.getMonthlyFeeAmt());
						System.out.println("StudentFeeDemand.getMonthlyFeeAmt() :"
								+ StudentFeeDemand.getMonthlyFeeAmt());
						pss.setInt(14, StudentFeeDemand.getMonthlyFeeDueAmt());
						System.out.println("StudentFeeDemand.getMonthlyFeeDueAmt() :"
								+ StudentFeeDemand.getMonthlyFeeDueAmt());*/
						
						pss.setString(13, StudentFeeDemand.getFeeDemandStatus());
						System.out
								.println("StudentFeeDemand.getFeeDemandStatus() :"
										+ StudentFeeDemand.getFeeDemandStatus());
						pss.setString(14,
								StudentFeeDemand.getFeeDemandRemarks());
						System.out
								.println("StudentFeeDemand.getFeeDemandRemarks() :"
										+ StudentFeeDemand
												.getFeeDemandRemarks());
						pss.setString(15, StudentFeeDemand.getDelFlag());
						System.out.println("StudentFeeDemand.getDelFlag() :"
								+ StudentFeeDemand.getDelFlag());
						pss.setString(16, StudentFeeDemand.getrModId());
						System.out.println("StudentFeeDemand.getrModId() :"
								+ StudentFeeDemand.getrModId());
						pss.setString(17, StudentFeeDemand.getrCreId().trim());
						System.out.println("StudentFeeDemand.getrCreId() :"
								+ StudentFeeDemand.getrCreId());
						pss.setString(18, StudentFeeDemand.getrCreTime().trim());
						System.out.println("StudentFeeDemand.getrCreTime() :"
								+ StudentFeeDemand.getrCreTime());
						pss.setInt(19, StudentFeeDemandKey.getDbTs());
						System.out.println("StudentFeeDemandKey.getDbTs() :"
								+ StudentFeeDemand.getDbTs());
						pss.setString(20, StudentFeeDemandKey.getInstId());
						System.out.println("StudentFeeDemandKey.getInstId() :"
								+ StudentFeeDemandKey.getInstId());
						pss.setString(21, StudentFeeDemandKey.getBranchId());
						System.out
								.println("StudentFeeDemandKey.getBranchId() :"
										+ StudentFeeDemandKey.getBranchId());
						pss.setString(22, StudentFeeDemandKey.getsFeeDmdSeqId()
								.trim());
						System.out
								.println("StudentFeeDemandKey.getsFeeDmdSeqId() :"
										+ StudentFeeDemandKey.getsFeeDmdSeqId());

					}

				});

		if (updateRecs == 0) {
			throw new UpdateFailedException();

		}

	}

	@Override
	public StudentFeeDemand selectStudentFeeDemand(
			final StudentFeeDemandKey StudentFeeDemand)
			throws NoDataFoundException {
		System.out.println("Inside select method");
		System.out.println("Student Demand Key object values :"
				+ StudentFeeDemand.toString());
		List<String> data = new ArrayList<String>();
		StringBuffer sql = new StringBuffer();

		sql.append("select ").append(" DB_TS,").append("INST_ID,")
				.append("BRANCH_ID,").append("SFEE_DMD_SEQ_ID,")
				.append("AC_TERM,").append("FEE_CATGRY,")
				.append("STUDENT_ADMIS_NO,").append("FEE_PMT_TERM,")
				.append("FEE_AMT,").append("CONCESSION_AMT,")
				.append("FEE_DUE_AMT,").append("MONTHLY_FEE_AMT,").append("MONTHLY_FEE_DUE_AMT,").append("STUDENT_ACCOUNT_NO,")
				.append("FEE_DMD_STS,").append("FEE_DMD_REMARKS,")
				.append("DEL_FLG,").append("R_MOD_ID,").append("R_MOD_TIME,")
				.append("R_CRE_ID,").append("R_CRE_TIME ")
				.append(" from sfdd ").append(" where").append("   INST_ID= ?")
				.append(" and  BRANCH_ID= ?").append(" and  AC_TERM= ?");
				if ((StudentFeeDemand.getFeeCategory() != null)
				&& (!StudentFeeDemand.getFeeCategory().equals(""))) {
				sql.append(" and  FEE_CATGRY= ?");
				}
				sql.append(" and  STUDENT_ADMIS_NO= ?");
		if ((StudentFeeDemand.getFeePaymentTerm() != null)
				&& (!StudentFeeDemand.getFeePaymentTerm().equals(""))) {
			sql.append(" and  FEE_PMT_TERM= ?");
		}
		sql.append(" and  DEL_FLG=?");
		data.add(StudentFeeDemand.getInstId());
		data.add(StudentFeeDemand.getBranchId());
		data.add(StudentFeeDemand.getAcTerm());
		if ((StudentFeeDemand.getFeeCategory() != null)
				&& (!StudentFeeDemand.getFeeCategory().equals(""))) {
		data.add(StudentFeeDemand.getFeeCategory());
		}
		data.add(StudentFeeDemand.getStudentAdmisNo());
		if ((StudentFeeDemand.getFeePaymentTerm() != null)
				&& (!StudentFeeDemand.getFeePaymentTerm().equals(""))) {
			data.add(StudentFeeDemand.getFeePaymentTerm());
		}

		data.add("N");

		String[] array = data.toArray(new String[data.size()]);
		StudentFeeDemand StudentFeeDemandKey = null;
		StudentFeeDemandKey = (StudentFeeDemand) getJdbcTemplate().query(
				sql.toString(), array,
				new ResultSetExtractor<StudentFeeDemand>() {

					@Override
					public StudentFeeDemand extractData(ResultSet rs)
							throws SQLException, DataAccessException {
						StudentFeeDemand StudentFeeDemandKey = null;
						if (rs.next()) {
							System.out.println("Inside rs.get");
							StudentFeeDemandKey = new StudentFeeDemand();
							StudentFeeDemandKey.setDbTs(rs.getInt("DB_TS"));
							StudentFeeDemandKey.setInstId(rs
									.getString("INST_ID"));
							StudentFeeDemandKey.setBranchId(rs
									.getString("BRANCH_ID"));
							StudentFeeDemandKey.setsFeeDmdSeqId(rs
									.getString("SFEE_DMD_SEQ_ID"));
							StudentFeeDemandKey.setAcTerm(rs
									.getString("AC_TERM"));
							StudentFeeDemandKey.setFeeCategory(rs
									.getString("FEE_CATGRY"));
							StudentFeeDemandKey.setStudentAdmissNo(rs
									.getString("STUDENT_ADMIS_NO"));
							StudentFeeDemandKey.setFeePaymentTerm(rs
									.getString("FEE_PMT_TERM"));
							StudentFeeDemandKey.setFeeAmt(rs.getInt("FEE_AMT"));
							StudentFeeDemandKey.setConcessionAmt(rs
									.getInt("CONCESSION_AMT"));
							StudentFeeDemandKey.setFeeDueAmt(rs
									.getInt("FEE_DUE_AMT"));
							StudentFeeDemandKey.setMonthlyFeeAmt(rs
									.getInt("MONTHLY_FEE_AMT"));
							StudentFeeDemandKey.setMonthlyFeeDueAmt(rs
									.getInt("MONTHLY_FEE_DUE_AMT"));
							StudentFeeDemandKey.setStudentAccNum(rs
									.getString("STUDENT_ACCOUNT_NO"));
							StudentFeeDemandKey.setFeeDemandStatus(rs
									.getString("FEE_DMD_STS"));
							StudentFeeDemandKey.setFeeDemandRemarks(rs
									.getString("FEE_DMD_REMARKS"));
							StudentFeeDemandKey.setDelFlag(rs
									.getString("DEL_FLG"));
							StudentFeeDemandKey.setrModId(rs
									.getString("R_MOD_ID"));
							StudentFeeDemandKey.setrModTime(rs
									.getString("R_MOD_TIME"));
							StudentFeeDemandKey.setrCreId(rs
									.getString("R_CRE_ID"));
							StudentFeeDemandKey.setrCreTime(rs
									.getString("R_CRE_TIME"));

						}
						return StudentFeeDemandKey;
					}

				});

		if (StudentFeeDemandKey == null) {
			System.out.println("No StudentFeeDemandKey exception thrown");
			throw new NoDataFoundException();
		}
		System.out.println("StudentFeeDemandKey :" + StudentFeeDemandKey);
		return StudentFeeDemandKey;
	}

	@Override
	public StudentFeeDemand selectStudentFeeDemandWithId(
			final StudentFeeDemandKey StudentFeeDemand)
			throws NoDataFoundException {
		System.out.println("Inside select method");
		System.out.println("Student Demand Key object values :"
				+ StudentFeeDemand.toString());
		List<String> data = new ArrayList<String>();
		StringBuffer sql = new StringBuffer();
		sql.append("select ").append(" DB_TS,").append("INST_ID,")
				.append("BRANCH_ID,").append("SFEE_DMD_SEQ_ID,")
				.append("AC_TERM,").append("FEE_CATGRY,")
				.append("STUDENT_ADMIS_NO,").append("FEE_PMT_TERM,")
				.append("FEE_AMT,").append("CONCESSION_AMT,")
				.append("FEE_DUE_AMT,").append("MONTHLY_FEE_AMT,").append("MONTHLY_FEE_DUE_AMT,").append("STUDENT_ACCOUNT_NO,")
				.append("FEE_DMD_STS,").append("FEE_DMD_REMARKS,")
				.append("DEL_FLG,").append("R_MOD_ID,").append("R_MOD_TIME,")
				.append("R_CRE_ID,").append("R_CRE_TIME ")
				.append(" from sfdd ").append(" where").append("   INST_ID= ?")
				.append(" and  BRANCH_ID= ?")
				.append(" and  SFEE_DMD_SEQ_ID= ?").append(" and  DEL_FLG=?");
		data.add(StudentFeeDemand.getInstId());
		data.add(StudentFeeDemand.getBranchId());
		data.add(StudentFeeDemand.getsFeeDmdSeqId());
		data.add("N");

		String[] array = data.toArray(new String[data.size()]);
		StudentFeeDemand StudentFeeDemandKey = null;
		StudentFeeDemandKey = (StudentFeeDemand) getJdbcTemplate().query(
				sql.toString(), array,
				new ResultSetExtractor<StudentFeeDemand>() {

					@Override
					public StudentFeeDemand extractData(ResultSet rs)
							throws SQLException, DataAccessException {
						StudentFeeDemand studentFeeDemand = null;
						if (rs.next()) {
							studentFeeDemand = new StudentFeeDemand();
							studentFeeDemand.setDbTs(rs.getInt("DB_TS"));
							studentFeeDemand.setInstId(rs.getString("INST_ID"));
							studentFeeDemand.setBranchId(rs
									.getString("BRANCH_ID"));
							studentFeeDemand.setsFeeDmdSeqId(rs
									.getString("SFEE_DMD_SEQ_ID"));
							studentFeeDemand.setAcTerm(rs.getString("AC_TERM"));
							studentFeeDemand.setFeeCategory(rs
									.getString("FEE_CATGRY"));
							studentFeeDemand.setStudentAdmissNo(rs
									.getString("STUDENT_ADMIS_NO"));
							studentFeeDemand.setFeePaymentTerm(rs
									.getString("FEE_PMT_TERM"));
							studentFeeDemand.setFeeAmt(rs.getInt("FEE_AMT"));
							studentFeeDemand.setConcessionAmt(rs
									.getInt("CONCESSION_AMT"));
							studentFeeDemand.setFeeDueAmt(rs
									.getInt("FEE_DUE_AMT"));
							studentFeeDemand.setMonthlyFeeAmt(rs
									.getInt("MONTHLY_FEE_AMT"));
							studentFeeDemand.setMonthlyFeeAmt(rs
									.getInt("MONTHLY_FEE_DUE_AMT"));
							studentFeeDemand.setStudentAccNum(rs
									.getString("STUDENT_ACCOUNT_NO"));
							studentFeeDemand.setFeeDemandStatus(rs
									.getString("FEE_DMD_STS"));
							studentFeeDemand.setFeeDemandRemarks(rs
									.getString("FEE_DMD_REMARKS"));
							studentFeeDemand.setDelFlag(rs.getString("DEL_FLG"));
							studentFeeDemand.setrModId(rs.getString("R_MOD_ID"));
							studentFeeDemand.setrModTime(rs
									.getString("R_MOD_TIME"));
							studentFeeDemand.setrCreId(rs.getString("R_CRE_ID"));
							studentFeeDemand.setrCreTime(rs
									.getString("R_CRE_TIME"));

						}
						return studentFeeDemand;
					}

				});

		if (StudentFeeDemandKey == null) {
			throw new NoDataFoundException();
		}

		return StudentFeeDemandKey;
	}

}
