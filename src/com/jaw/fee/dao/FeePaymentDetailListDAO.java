package com.jaw.fee.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.core.dao.CourseTermLinking;
import com.jaw.framework.dao.BaseDao;

@Repository
public class FeePaymentDetailListDAO extends BaseDao implements IFeePaymentDetailListDAO{
	// Logging
	Logger logger = Logger.getLogger(FeePaymentDetailListDAO.class);

	@Override
	public List<FeePaymentDetail> getFeePaymentDetailsList(
			FeePaymentDetailKey feePaymentDetailsKey)
			throws NoDataFoundException {
		logger.debug("DAO :Inside FeePaymentDetails List select method");
		logger.debug("DAO :Inside FeePaymentDetails Key values : "+feePaymentDetailsKey.toString());
		List<Object> data = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
	
		
	    sql.append("select ")       
			.append("DB_TS,")
			.append("AC_TERM,")
			.append("FEE_PMT_TERM,")
			.append("DUE_DATE, ")
			.append("FEE_CATGRY ")
			.append(" from fptd ")
			.append(" where")
			.append("  INST_ID= ?")
			.append(" and  BRANCH_ID= ?")
			.append(" and  DEL_FLG=?");
			
	    data.add(feePaymentDetailsKey.getInstId());
		data.add(feePaymentDetailsKey.getBranchId());
		data.add("N");
		if ((feePaymentDetailsKey.getAcTerm() != null)
				&& (feePaymentDetailsKey.getAcTerm() != "")) {
			sql.append(" and AC_TERM=?  ");
			
			data.add(feePaymentDetailsKey.getAcTerm());
		}
		if ((feePaymentDetailsKey.getFeePaymentTerm() != null)
				&& (feePaymentDetailsKey.getFeePaymentTerm() != "")) {
			sql.append(" and FEE_PMT_TERM=?  ");
			
			data.add(feePaymentDetailsKey.getFeePaymentTerm());
		}
		sql.append(" order by FEE_PMT_TERM");
		Object[] array = data.toArray(new Object[data.size()]);
		List<FeePaymentDetail> selectedListfeePaymentDet=null;
		 selectedListfeePaymentDet = getJdbcTemplate()
				.query(sql.toString(), array,
						new FeePaymentDetailSelectRowMapper());
		
		if (selectedListfeePaymentDet.size() == 0) {
				throw new NoDataFoundException();
			
		}
			
		
		
		logger.debug("DAO : Fee Payment detail List value"
				+ selectedListfeePaymentDet.size());
		
		return selectedListfeePaymentDet;
		}

	}
	class FeePaymentDetailSelectRowMapper implements
	RowMapper<FeePaymentDetail> {

	@Override
	public FeePaymentDetail mapRow(ResultSet rs, int arg1)
		throws SQLException {
		
		FeePaymentDetail feePaymentDetail = new FeePaymentDetail();
	feePaymentDetail.setDbTs(rs.getInt("DB_TS"));
	feePaymentDetail.setAcTerm(rs.getString("AC_TERM"));
	feePaymentDetail.setFeePaymentTerm(rs.getString("FEE_PMT_TERM"));
	feePaymentDetail.setDueDate(rs.getString("DUE_DATE"));
	feePaymentDetail.setFeeCategory(rs.getString("FEE_CATGRY"));
	return feePaymentDetail;
	}
	}