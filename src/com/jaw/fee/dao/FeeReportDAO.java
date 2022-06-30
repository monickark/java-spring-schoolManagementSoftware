package com.jaw.fee.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.jaw.common.constants.CommonCodeConstant;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.framework.dao.BaseDao;

@Repository
public class FeeReportDAO extends BaseDao implements IFeeReportDAO {
	// Logging
	Logger logger = Logger.getLogger(FeeReportDAO.class);

	@Override
	public List<FeeReportList> selectFeeReportListDetails(
			FeeReportKey FeeReportListKey) throws NoDataFoundException {
		logger.debug("DAO :Inside get Fee due List  method");
		logger.debug("DAO :Inside FeeReportListKey values : "
				+ FeeReportListKey.toString());
		List<Object> data = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		sql.append(
				" SELECT sfdd.student_admis_no, sfpm.r_cre_id,student_name, stgm.studentgrp_id,stgm.student_grp, fee_amt,sum(fee_paid_amt) as fee_paid_amt, fee_due_amt,concession_amt,last_year_unpaid_amt, fee_pmt_date FROM sfdd,sfpm,stum,stgm where ")
				.append(" sfdd.sfee_dmd_seq_id=sfpm.sfee_dmd_seq_id ")
				.append(" and sfdd.inst_id=sfpm.inst_id ")
				.append(" and sfdd.branch_id=sfpm.branch_id ")
				.append(" and sfdd.inst_id=stum.inst_id ")
				.append(" and sfdd.branch_id=stum.branch_id ")
				.append(" and sfdd.ac_term=stum.academic_year ")
				.append(" and sfdd.del_flg=stum.del_flg ")				
				.append("and stgm.inst_id=stum.inst_id ")
				.append("and stgm.branch_id=stum.branch_id ")
				.append("and stgm.studentgrp_id=stum.studentgrp_id ")
				.append("and stgm.del_flg=stum.del_flg ")
				.append(" and sfdd.student_admis_no = stum.student_admis_no ")
				.append(" and sfdd.inst_id=?").append(" and sfdd.branch_id=?");
				if (((FeeReportListKey.getFromDate() != null)
				&& (!FeeReportListKey.getFromDate().equals("")))&&(((FeeReportListKey.getToDate() != null)
				&& (!FeeReportListKey.getToDate().equals(""))))) {
				sql.append(" and fee_pmt_date between ? and ? ");
				}
				if (((FeeReportListKey.getStudentGroupId() != null)
						&& (!FeeReportListKey.getStudentGroupId().equals("")))){
				sql.append(" and stgm.studentgrp_id=? ");
				}
				if (((FeeReportListKey.getFeeReceipt() != null)
						&& (!FeeReportListKey.getFeeReceipt().equals("")))){
				sql.append(" and sfpm.rcpt_catgry=? ");
				}
				if (((FeeReportListKey.getMenuProfile() != null)
						&& (!FeeReportListKey.getMenuProfile().equals("")))){
				sql.append(" and sfpm.r_cre_id=? ");
				}
				sql.append(" and stum.academic_year=? ")
				.append(" and stum.del_flg=? ")
				.append(" group by student_admis_no;  ");

		data.add(FeeReportListKey.getInstId());
		data.add(FeeReportListKey.getBranchId());
		if (((FeeReportListKey.getFromDate() != null)
				&& (!FeeReportListKey.getFromDate().equals("")))&&(((FeeReportListKey.getToDate() != null)
				&& (!FeeReportListKey.getToDate().equals(""))))) {
		data.add(FeeReportListKey.getFromDate());
		data.add(FeeReportListKey.getToDate());
		}
		if (((FeeReportListKey.getStudentGroupId() != null)
				&& (!FeeReportListKey.getStudentGroupId().equals("")))){
		data.add(FeeReportListKey.getStudentGroupId());
		}
		if (((FeeReportListKey.getFeeReceipt() != null)
				&& (!FeeReportListKey.getFeeReceipt().equals("")))){
		data.add(FeeReportListKey.getFeeReceipt());
		}
		if (((FeeReportListKey.getMenuProfile() != null)
				&& (!FeeReportListKey.getMenuProfile().equals("")))){
		data.add(FeeReportListKey.getMenuProfile());
		}
		data.add(FeeReportListKey.getAcademicTerm());
		data.add("N");

		Object[] array = data.toArray(new Object[data.size()]);
		List<FeeReportList> selectedListFeeReportList = null;
		selectedListFeeReportList = getJdbcTemplate().query(sql.toString(),
				array, new FeeReportListRowMapper());

		/*
		 * if (selectedListFeeReportList.size() == 0) { throw new
		 * NoDataFoundException(); }
		 */
		logger.debug("DAO : Fee due list size"
				+ selectedListFeeReportList.size());

		return selectedListFeeReportList;
	}
	
	
	@Override
	public List<FeeReportList> selectFeePaidStudent(
			FeeReportKey FeeReportListKey) throws NoDataFoundException {
		logger.debug("DAO :Inside get Fee due List  method");
		logger.debug("DAO :Inside FeeReportListKey values : "
				+ FeeReportListKey.toString());
		List<Object> data = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		sql.append(
				" SELECT sfdd.student_admis_no, student_name, stgm.studentgrp_id,stgm.student_grp, fee_amt,sum(fee_paid_amt) as fee_paid_amt,FEE_DUE_AMT,concession_amt,last_year_unpaid_amt FROM sfdd,sfpm,stum,stgm where ")
				.append(" sfdd.sfee_dmd_seq_id=sfpm.sfee_dmd_seq_id ")
				.append(" and sfdd.inst_id=sfpm.inst_id ")
				.append(" and sfdd.branch_id=sfpm.branch_id ")
				.append(" and sfdd.inst_id=stum.inst_id ")
				.append(" and sfdd.branch_id=stum.branch_id ")
				.append(" and sfdd.ac_term=stum.academic_year ")
				.append(" and sfdd.del_flg=stum.del_flg ")				
				.append("and stgm.inst_id=stum.inst_id ")
				.append("and stgm.branch_id=stum.branch_id ")
				.append("and stgm.studentgrp_id=stum.studentgrp_id ")
				.append("and stgm.del_flg=stum.del_flg ")
				.append(" and sfdd.student_admis_no = stum.student_admis_no ");
		if(FeeReportListKey.getReportType().equals(CommonCodeConstant.FEE_DUE_STUDENT_DETAILS)){
			sql.append("and sfdd.fee_due_amt>0  ");	
		} else if(FeeReportListKey.getReportType().equals(CommonCodeConstant.FEE_COMPLETED_STUDNET_DETAILS)){
			sql.append("and sfdd.fee_due_amt=0  ");	
		}
		
		sql.append("and sfdd.inst_id=?")
		.append("and sfdd.branch_id=? ");
				if (((FeeReportListKey.getStudentGroupId() != null)
						&& (!FeeReportListKey.getStudentGroupId().equals("")))){
				sql.append(" and stgm.studentgrp_id=? ");
				}
				sql.append(" and stum.academic_year=? ")
				.append(" and stum.del_flg=? ")
				.append(" group by student_admis_no;  ");

		data.add(FeeReportListKey.getInstId());
		data.add(FeeReportListKey.getBranchId());
		if (((FeeReportListKey.getStudentGroupId() != null)
				&& (!FeeReportListKey.getStudentGroupId().equals("")))){
		data.add(FeeReportListKey.getStudentGroupId());
		}
		data.add(FeeReportListKey.getAcademicTerm());
		data.add("N");

		Object[] array = data.toArray(new Object[data.size()]);
		List<FeeReportList> selectedListFeeReportList = null;
		selectedListFeeReportList = getJdbcTemplate().query(sql.toString(),
				array, new FeeStatusReportListRowMapper());

		/*
		 * if (selectedListFeeReportList.size() == 0) { throw new
		 * NoDataFoundException(); }
		 */
		logger.debug("DAO : Fee due list size"
				+ selectedListFeeReportList.size());

		return selectedListFeeReportList;
	
}

@Override
public List<FeeReportList> selectFeeDueStudent(
		FeeReportKey FeeReportListKey) throws NoDataFoundException {
	logger.debug("DAO :Inside get Fee due List  method");
	logger.debug("DAO :Inside FeeReportListKey values : "
			+ FeeReportListKey.toString());
	List<Object> data = new ArrayList<Object>();
	StringBuffer sql = new StringBuffer();
	sql.append(
			" SELECT sfdd.student_admis_no, student_name, stgm.studentgrp_id,stgm.student_grp, fee_amt,'' as fee_paid_amt,FEE_DUE_AMT,concession_amt,last_year_unpaid_amt FROM sfdd,stum,stgm where ")
			.append(" sfdd.inst_id=stum.inst_id ")
			.append(" and sfdd.branch_id=stum.branch_id ")
			.append(" and sfdd.ac_term=stum.academic_year ")
			.append(" and sfdd.del_flg=stum.del_flg ")				
			.append("and stgm.inst_id=stum.inst_id ")
			.append("and stgm.branch_id=stum.branch_id ")
			.append("and stgm.studentgrp_id=stum.studentgrp_id ")
			.append("and stgm.del_flg=stum.del_flg ")
			.append(" and sfdd.student_admis_no = stum.student_admis_no ")
			.append("and sfdd.fee_due_amt>0  ")	
			.append("and sfdd.inst_id=?")
			.append("and sfdd.branch_id=? ");
			if (((FeeReportListKey.getStudentGroupId() != null)
					&& (!FeeReportListKey.getStudentGroupId().equals("")))){
			sql.append(" and stgm.studentgrp_id=? ");
			}
			sql.append(" and stum.academic_year=? ")
			.append(" and stum.del_flg=? ")
			.append(" group by student_admis_no;  ");

	data.add(FeeReportListKey.getInstId());
	data.add(FeeReportListKey.getBranchId());
	if (((FeeReportListKey.getStudentGroupId() != null)
			&& (!FeeReportListKey.getStudentGroupId().equals("")))){
	data.add(FeeReportListKey.getStudentGroupId());
	}
	data.add(FeeReportListKey.getAcademicTerm());
	data.add("N");

	Object[] array = data.toArray(new Object[data.size()]);
	List<FeeReportList> selectedListFeeReportList = null;
	selectedListFeeReportList = getJdbcTemplate().query(sql.toString(),
			array, new FeeStatusReportListRowMapper());

	/*
	 * if (selectedListFeeReportList.size() == 0) { throw new
	 * NoDataFoundException(); }
	 */
	logger.debug("DAO : Fee due list size"
			+ selectedListFeeReportList.size());

	return selectedListFeeReportList;
}
}

class FeeReportListRowMapper implements RowMapper<FeeReportList> {

	@Override
	public FeeReportList mapRow(ResultSet rs, int arg1) throws SQLException {

		FeeReportList feeReportList = new FeeReportList();
		feeReportList.setAdmissionNumber(rs.getString("STUDENT_ADMIS_NO"));
		feeReportList.setStudentName(rs.getString("STUDENT_NAME"));
		feeReportList.setStGrpId(rs.getString("studentgrp_id"));
		feeReportList.setStGrpName(rs.getString("student_grp"));
		feeReportList.setFeeAmount(rs.getString("FEE_AMT"));
		feeReportList.setFeePaidAmt(rs.getString("fee_paid_amt"));
		feeReportList.setFeeDueAmt(rs.getString("FEE_DUE_AMT"));
		feeReportList.setFeePaymentDate(rs.getString("FEE_PMT_DATE"));
		feeReportList.setFeeConcessionAmount(rs.getString("concession_amt"));
		feeReportList.setPrevYearPendingAmt(rs.getString("last_year_unpaid_amt"));
		feeReportList.setMenuProfile(rs.getString("r_cre_id"));
		return feeReportList;
	}

}

class FeeStatusReportListRowMapper implements RowMapper<FeeReportList> {

	@Override
	public FeeReportList mapRow(ResultSet rs, int arg1) throws SQLException {
		
		FeeReportList feeReportList = new FeeReportList();
		feeReportList.setAdmissionNumber(rs.getString("STUDENT_ADMIS_NO"));
		feeReportList.setStudentName(rs.getString("STUDENT_NAME"));
		feeReportList.setStGrpId(rs.getString("studentgrp_id"));
		feeReportList.setStGrpName(rs.getString("student_grp"));
		feeReportList.setFeeAmount(rs.getString("FEE_AMT"));
		feeReportList.setFeePaidAmt(rs.getString("fee_paid_amt"));
		feeReportList.setFeeDueAmt(rs.getString("FEE_DUE_AMT"));
		feeReportList.setFeeConcessionAmount(rs.getString("concession_amt"));
		feeReportList.setPrevYearPendingAmt(rs.getString("last_year_unpaid_amt"));
		return feeReportList;
	}

}

