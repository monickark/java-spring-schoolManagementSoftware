package com.jaw.fee.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.jaw.common.exceptions.BatchUpdateFailedException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.batch.DataIntegrityException;
import com.jaw.common.exceptions.batch.RuntimeExceptionForBatch;
import com.jaw.framework.dao.BaseDao;
import com.jaw.student.admission.dao.StudentUpdateList;

@Repository
public class StudentFeeDiscountListDAO extends BaseDao implements IStudentFeeDiscountListDAO {
	Logger logger = Logger.getLogger(StudentFeeDiscountListDAO.class);

	int[] ret;



	@Override
	public void updateStuList(
			final List<StudentFeeDiscountList> studentFeeDiscountLists)
			throws  BatchUpdateFailedException {			
		
		StringBuffer sql = new StringBuffer("update sfdd set ")
		.append("R_MOD_ID=?, ")
		.append("R_MOD_TIME=now(), ").append("concession_amt =?, ")
		.append("fee_due_amt =?, ")
		.append("fee_dmd_remarks =? ")
		.append("where STUDENT_ADMIS_NO=? and ")
		.append("AC_TERM=? and ")		.append("INST_ID=? and ").append("BRANCH_ID=? and ")
		.append("DEL_FLG=?");
logger.debug("sql query" + sql.toString());
int[] ret = null;

ret = 	getJdbcTemplate().batchUpdate(sql.toString(),
		new BatchPreparedStatementSetter() {

			@Override
			public void setValues(PreparedStatement ps, int i)
					throws SQLException {
				StudentFeeDiscountList stduentMaster = studentFeeDiscountLists.get(i);
				System.out.println("StudentFeeDiscountList :"+stduentMaster.toString());
				
				ps.setString(1, stduentMaster.getrModId());						
				ps.setString(2, stduentMaster.getConcessionAmt());
				ps.setString(3, stduentMaster.getFeeDueAmt());
				ps.setString(4, stduentMaster.getFeeDmdremarks());
				ps.setString(5, stduentMaster.getStudentAdmisNo());
				ps.setString(6, stduentMaster.getAcTerm());
				ps.setString(7, stduentMaster.getInstId());
				ps.setString(8, stduentMaster.getBranchId());
				ps.setString(9, "N");

			}

			@Override
			public int getBatchSize() {
				return studentFeeDiscountLists.size();
			}
		});
for (int sa : ret) {
	if (sa == 0) {
		throw new BatchUpdateFailedException();
	}
}

}
	@Override
	public List<StudentFeeDiscountList> selectStudentFeeDiscount(
			StudentFeeDiscountKey studentFeeDiscountKey)
			throws NoDataFoundException {
		
		logger.debug("DAO :Inside get Student fee List method");
		logger.debug("DAO :Inside StudentFeeKey values : "
				+ studentFeeDiscountKey.toString());
		List<Object> data = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT sfdd.student_admis_no, student_name, fee_amt,concession_amt, fee_due_amt,last_year_unpaid_amt, "
				+ "fee_dmd_remarks FROM sfdd,stum where ")
		.append(" sfdd.inst_id=stum.inst_id ")
		.append(" and sfdd.branch_id=stum.branch_id ")
		.append(" and sfdd.ac_term=stum.academic_year ")
		.append(" and sfdd.del_flg=stum.del_flg ")
		.append(" and sfdd.student_admis_no = stum.student_admis_no ")
		.append(" and sfdd.inst_id= ? ")
		.append(" and sfdd.branch_id=? ")
		.append(" and stum.studentgrp_id=? ")
		.append(" and stum.academic_year=?")
		.append(" and stum.del_flg='N' ")
		.append(" group by student_admis_no; ");
		data.add(studentFeeDiscountKey.getInstId());
		data.add(studentFeeDiscountKey.getBranchId());
		data.add(studentFeeDiscountKey.getStGroup());
		data.add(studentFeeDiscountKey.getAcTerm());

		Object[] array = data.toArray(new Object[data.size()]);
		List<StudentFeeDiscountList> selectedListStudentFee = null;
		selectedListStudentFee = getJdbcTemplate().query(sql.toString(), array,
				new StudentFeeDiscountListSelectRowMapper());

		if (selectedListStudentFee.size() == 0) {
			throw new NoDataFoundException();

		}

		logger.debug("DAO : Student List value" + selectedListStudentFee.size());

		return selectedListStudentFee;
	}




	}
class StudentFeeDiscountListSelectRowMapper implements RowMapper<StudentFeeDiscountList> {

	@Override
	public StudentFeeDiscountList mapRow(ResultSet rs, int arg1) throws SQLException {
		
		StudentFeeDiscountList stuFee = new StudentFeeDiscountList();
		stuFee.setStudentAdmisNo(rs.getString("STUDENT_ADMIS_NO"));
		stuFee.setStudentName(rs.getString("STUDENT_NAME"));
		stuFee.setFeeAmt(rs.getString("FEE_AMT"));
		stuFee.setConcessionAmt(rs.getString("CONCESSION_AMT"));
		stuFee.setFeeDueAmt(rs.getString("FEE_DUE_AMT"));
		stuFee.setFeeDmdremarks(rs.getString("FEE_DMD_REMARKS"));
		stuFee.setLastYearPayment(rs.getInt("last_year_unpaid_amt"));
		return stuFee;
	}
}




