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
public class StudentFeePaymentListDAO extends BaseDao implements
IStudentFeePaymentListDAO {
// Logging
Logger logger = Logger.getLogger(StudentFeePaymentListDAO.class);

@Override
public List<StudentFeePayment> selectStudentFeePaymentListForFeePaid(
		StudentFeePaymentKey studentFeePaymentKey) throws NoDataFoundException {
	logger.debug("DAO :Inside get Student fee payment List method");
	logger.debug("DAO :Inside StudentFeePaymentKey values : "+studentFeePaymentKey.toString());
	List<Object> data = new ArrayList<Object>();
	StringBuffer sql = new StringBuffer();	
    sql.append("select ")       
		.append("fee_pmt_srl_no,")
		.append("fee_paid_amt,")
		.append("FEE_RCPT_NO,")
		.append("RCPT_CATGRY,")
		.append("fine_amt ")
		.append(" from sfpm ")
		.append(" where")
		.append("  INST_ID= ?")
		.append(" and  BRANCH_ID= ?")
		.append(" and  sfee_dmd_seq_id=?")
        .append(" and  fee_pmt_srl_no<=?") 
         .append(" and  ") 
           .append("  (rcpt_catgry=? or rcpt_catgry='' or rcpt_catgry is null) ") 
        
         
		.append(" and  DEL_FLG=?")
        .append(" order by fee_pmt_srl_no asc ");
		
    data.add(studentFeePaymentKey.getInstId());
	data.add(studentFeePaymentKey.getBranchId());
	data.add(studentFeePaymentKey.getStudFeeDDId());
	data.add(studentFeePaymentKey.getFeePmtSrlNo());
	data.add(studentFeePaymentKey.getReceiptCategory());
	data.add("N");
	
	Object[] array = data.toArray(new Object[data.size()]);
	List<StudentFeePayment> selectedListStudentFeePayment=null;
	 selectedListStudentFeePayment = getJdbcTemplate()
			.query(sql.toString(), array,
					new StudentPaymentListSelectRowMapper());
	
	if (selectedListStudentFeePayment.size() == 0) {
			throw new NoDataFoundException();
		
	}
		
	
	
	logger.debug("DAO : Student List value"
			+ selectedListStudentFeePayment.size());
	
	return selectedListStudentFeePayment;
	}
}

class StudentPaymentListSelectRowMapper implements
RowMapper<StudentFeePayment> {

@Override
public StudentFeePayment mapRow(ResultSet rs, int arg1)
	throws SQLException {
	StudentFeePayment stuFeePayment = new StudentFeePayment();
	
	stuFeePayment.setFeePmtSrlNo(rs.getString("FEE_PMT_SRL_NO"));
	stuFeePayment.setFineAmt(rs.getInt("FINE_AMT"));
	stuFeePayment.setFeePaidAmt(rs.getInt("FEE_PAID_AMT"));
	stuFeePayment.setFeeReceiptNo(rs.getString("FEE_RCPT_NO"));
	stuFeePayment.setReceiptCategory(rs.getString("RCPT_CATGRY"));
	return stuFeePayment;
}
}