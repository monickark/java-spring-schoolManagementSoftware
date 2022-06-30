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
public class PrevYearFeeUpdateListDAO extends BaseDao implements IPrevYearFeeUpdateListDAO {
	Logger logger = Logger.getLogger(PrevYearFeeUpdateListDAO.class);

	int[] ret;



	@Override
	public void updateStuList(
			final List<PrevYearFeeUpdateList> PrevYearFeeUpdateLists)
			throws  BatchUpdateFailedException {			
		
		StringBuffer sql = new StringBuffer("update sfdd set ")
		.append("R_MOD_ID=?, ")
		.append("R_MOD_TIME=now(), ").append("last_year_unpaid_amt =?, ")
		.append("fee_due_amt =? ")
	
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
				PrevYearFeeUpdateList stduentMaster = PrevYearFeeUpdateLists.get(i);
				System.out.println("PrevYearFeeUpdateList :"+stduentMaster.toString());
				
				ps.setString(1, stduentMaster.getrModId());						
				ps.setString(2, stduentMaster.getLastYearAmt());
				ps.setString(3, stduentMaster.getFeeDueAmt()); 
				ps.setString(4, stduentMaster.getStudentAdmisNo());
				ps.setString(5, stduentMaster.getAcTerm());
				ps.setString(6, stduentMaster.getInstId());
				ps.setString(7, stduentMaster.getBranchId());
				ps.setString(8, "N");

			}

			@Override
			public int getBatchSize() {
				return PrevYearFeeUpdateLists.size();
			}
		});
for (int sa : ret) {
	if (sa == 0) {
		throw new BatchUpdateFailedException();
	}
}

}
	@Override
	public List<PrevYearFeeUpdateList> selectPrevYearFeeUpdate(
			PrevYearFeeUpdateKey PrevYearFeeUpdateKey)
			throws NoDataFoundException {
		logger.debug("DAO :Inside get Student fee List method");
		logger.debug("DAO :Inside StudentFeeKey values : "
				+ PrevYearFeeUpdateKey.toString());
		List<Object> data = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT sfdd.student_admis_no, student_name, fee_amt,last_year_unpaid_Amt, fee_due_amt FROM sfdd,sfpm,stum where ")
		
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
		data.add(PrevYearFeeUpdateKey.getInstId());
		data.add(PrevYearFeeUpdateKey.getBranchId());
		data.add(PrevYearFeeUpdateKey.getStGroup());
		data.add(PrevYearFeeUpdateKey.getAcTerm());

		Object[] array = data.toArray(new Object[data.size()]);
		List<PrevYearFeeUpdateList> selectedListStudentFee = null;
		selectedListStudentFee = getJdbcTemplate().query(sql.toString(), array,
				new PrevYearFeeUpdateListSelectRowMapper());

		if (selectedListStudentFee.size() == 0) {
			throw new NoDataFoundException();

		}

		logger.debug("DAO : Student List value" + selectedListStudentFee.size());

		return selectedListStudentFee;
	}




	}
class PrevYearFeeUpdateListSelectRowMapper implements RowMapper<PrevYearFeeUpdateList> {

	@Override
	public PrevYearFeeUpdateList mapRow(ResultSet rs, int arg1) throws SQLException {
		
		PrevYearFeeUpdateList stuFee = new PrevYearFeeUpdateList();
		stuFee.setStudentAdmisNo(rs.getString("STUDENT_ADMIS_NO"));
		stuFee.setStudentName(rs.getString("STUDENT_NAME"));
		stuFee.setFeeAmt(rs.getString("FEE_AMT"));
		stuFee.setLastYearAmt(rs.getString("last_year_unpaid_amt"));
		stuFee.setFeeDueAmt(rs.getString("FEE_DUE_AMT"));
		return stuFee;
	}
}




