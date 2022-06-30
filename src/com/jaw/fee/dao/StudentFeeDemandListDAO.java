package com.jaw.fee.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.stereotype.Repository;

import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.framework.dao.BaseDao;
@Repository
public class StudentFeeDemandListDAO extends BaseDao implements IStudentFeeDemandListDAO{
	// Logging
	Logger logger = Logger.getLogger(StudentFeeDemandListDAO.class);

	@Override
	public void insertStudentFeeDemandListRecs(
			final List<StudentFeeDemand> studentFeeDmdList)
			throws DuplicateEntryException {
		logger.debug("Inside Student Fee Demand List Insert Record");
		logger.debug("Student Fee Demand List Size   "+studentFeeDmdList.size());
		StringBuffer query = new StringBuffer();
		
		query = query.append("insert into sfdd ( ")
				.append("DB_TS,")
				.append("INST_ID,")
				.append("BRANCH_ID,")
				.append("SFEE_DMD_SEQ_ID,")
				.append("AC_TERM,")
				.append("FEE_CATGRY,")
				.append("STUDENT_ADMIS_NO,")
				.append("FEE_PMT_TERM,")
				.append("MONTHLY_FEE_AMT,")
				.append("MONTHLY_FEE_DUE_AMT,")
				.append("FEE_AMT,")
				.append("LAST_YEAR_UNPAID_AMT,")
				.append("CONCESSION_AMT,")
				.append("FEE_DUE_AMT,")
				.append("STUDENT_ACCOUNT_NO,")
				.append("FEE_DMD_STS,")
				.append("FEE_DMD_REMARKS,")
				.append("DEL_FLG,")
				.append("R_MOD_ID,")
				.append("R_MOD_TIME,")
				.append("R_CRE_ID,")
				.append("R_CRE_TIME)")
				.append(" values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,now(),?,now())");
		try {
		int[] a = getJdbcTemplate().batchUpdate(query.toString(),
				new BatchPreparedStatementSetter() {

					@Override
					public void setValues(PreparedStatement pss, int i)
							throws SQLException {

						StudentFeeDemand stuFeeDmd = studentFeeDmdList
								.get(i);
						logger.debug("Student Fee Demand List object   "+studentFeeDmdList.get(i).toString());
						
						pss.setInt(1, stuFeeDmd.getDbTs());
						pss.setString(2, stuFeeDmd.getInstId());
						pss.setString(3, stuFeeDmd.getBranchId());
						pss.setString(4, stuFeeDmd.getsFeeDmdSeqId());
						pss.setString(5, stuFeeDmd.getAcTerm().trim());
						pss.setString(6, stuFeeDmd.getFeeCategory().trim());
						pss.setString(7, stuFeeDmd.getStudentAdmissNo().trim());						
						pss.setString(8, stuFeeDmd.getFeePaymentTerm().trim());
						pss.setInt(9, stuFeeDmd.getMonthlyFeeAmt());
						pss.setInt(10, stuFeeDmd.getMonthlyFeeDueAmt());
						pss.setInt(11, stuFeeDmd.getFeeAmt());
						pss.setInt(12, stuFeeDmd.getConcessionAmt());
						pss.setInt(13, stuFeeDmd.getLastYearUnpaidAmt());
						pss.setInt(14, stuFeeDmd.getFeeDueAmt());
						pss.setString(15, stuFeeDmd.getStudentAccNum().trim());
						pss.setString(16, stuFeeDmd.getFeeDemandStatus().trim());
						pss.setString(17, stuFeeDmd.getFeeDemandRemarks().trim());
						pss.setString(18, stuFeeDmd.getDelFlag().trim());
						pss.setString(19, stuFeeDmd.getrModId().trim());
						pss.setString(20, stuFeeDmd.getrCreId().trim());
					}

					@Override
					public int getBatchSize() {
						return studentFeeDmdList.size();
					}
				}

		);
		} catch (org.springframework.dao.DuplicateKeyException e) {
			logger.error("Duplicate Entry Exception Occured  "+e.getMessage());
			throw new DuplicateEntryException();
		}
		
	}

}
