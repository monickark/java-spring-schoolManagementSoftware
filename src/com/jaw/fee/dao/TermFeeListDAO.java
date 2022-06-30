package com.jaw.fee.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.jaw.common.constants.ApplicationConstant;
import com.jaw.common.constants.CommonCodeConstant;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.UpdateFailedException;
import com.jaw.common.exceptions.batch.DuplicateEntryForHolGenException;
import com.jaw.core.dao.AcademicCalendar;
import com.jaw.core.dao.HolidayMaintenance;
import com.jaw.core.dao.HolidayMaintenanceKey;
import com.jaw.framework.dao.BaseDao;
@Repository
public class TermFeeListDAO extends BaseDao implements ITermFeeListDAO {
	// Logging
	Logger logger = Logger.getLogger(TermFeeListDAO.class);

	@Override
	public List<FeeTerms> selectFeeTermList(final FeeTermsKey feeTermsKey) throws NoDataFoundException {

		logger.debug("Inside Fees Term Tag select method");
		logger.debug("Inside Fees Term Key Values : "+feeTermsKey.toString());
		StringBuffer sql = new StringBuffer();
		sql.append("select ")
				.append("CM_CODE,")
				.append("CODE_DESC ")
				.append(" from cocd  ")
				.append("where ")
				.append(" code_type=?")
				.append(" and INST_ID=?")
				.append(" and BRANCH_ID=?")
				.append(" and DEL_FLG=?  ")
				.append(" and CM_CODE not in  ")
				
				.append(" (select  ")
				.append(" FEE_TERM  ")
				.append(" from ftrm ")
				.append(" where  ")
				.append(" INST_ID=?  ")
				.append(" and BRANCH_ID=?  ")
				.append(" and DEL_FLG=?)  ");
				
				System.out.println("query  : "+sql.toString());
			List<FeeTerms>   feeTermList = null;

		feeTermList = getJdbcTemplate().query(sql.toString(),
				new PreparedStatementSetter() {

					@Override
					public void setValues(PreparedStatement pss)
							throws SQLException {
						pss.setString(1, CommonCodeConstant.FEE_TERM);
						pss.setString(2,feeTermsKey.getInstId() );
						pss.setString(3,feeTermsKey.getBranchId());
						pss.setString(4, "N");
						pss.setString(5,feeTermsKey.getInstId() );
						pss.setString(6,feeTermsKey.getBranchId());
						pss.setString(7, "N");

					}

				}, new FeesTermDetailsRowmapper());		
		
		if (feeTermList.size() == 0) {
			throw new NoDataFoundException();
		}
		return feeTermList;
	}

	@Override
	public List<FeeTerms> selectFeePaymentTermList(final FeeTermsKey feeTermsKey)
			throws NoDataFoundException {

		logger.debug("Inside Fees Term Tag select method");
		logger.debug("Inside Fees Term Key Values : "+feeTermsKey.toString());

		StringBuffer sql = new StringBuffer();
		sql.append("select ")
				.append("CM_CODE,")
				.append("CODE_DESC ")
				.append(" from cocd  ")
				.append("where ")
				.append(" code_type=?")
				.append(" and INST_ID=?")
				.append(" and BRANCH_ID=?")
				.append(" and DEL_FLG=?  ")
				.append(" and CM_CODE not in  ")
				
				.append(" (select  ")
				.append(" FEE_PMT_TERM  ")
				.append(" from ftrm ")
				.append(" where  ")
				.append(" INST_ID=?  ")
				.append(" and BRANCH_ID=?  ")
				.append(" and DEL_FLG=?)  ");
				
		System.out.println("query  : "+sql.toString());
			List<FeeTerms>   feePaymentTerm = null;

		feePaymentTerm = getJdbcTemplate().query(sql.toString(),
				new PreparedStatementSetter() {

					@Override
					public void setValues(PreparedStatement pss)
							throws SQLException {
						pss.setString(1, CommonCodeConstant.FEE_PAYMENT_TERM);
						pss.setString(2,feeTermsKey.getInstId() );
						pss.setString(3,feeTermsKey.getBranchId());
						pss.setString(4, "N");
						pss.setString(5,feeTermsKey.getInstId() );
						pss.setString(6,feeTermsKey.getBranchId());
						pss.setString(7, "N");

					}

				}, new FeesPaymentTermDetailsRowmapper());	
		
		if (feePaymentTerm.size() == 0) {
			throw new NoDataFoundException();
		}
		return feePaymentTerm;
	}

	@Override
	public void insertFeeTermListRecs(final List<FeeTerms> feeTermList)
			throws DuplicateEntryException {
		logger.debug("Inside Fee Term List Insert Record");
		logger.debug("Fee Term List Size   "+feeTermList.size());
		StringBuffer query = new StringBuffer();
		
		query = query.append("insert into ftrm ( ")
				.append("DB_TS,")
				.append("INST_ID,")
				.append("BRANCH_ID,")
				.append("FEE_TERM,")
				.append("FEE_PMT_TERM,")
				.append("DEL_FLG,")
				.append("R_MOD_ID,")
				.append("R_MOD_TIME,")
				.append("R_CRE_ID,")
				.append("R_CRE_TIME)")
				.append(" values (?,?,?,?,?,?,?,now(),?,now())");
		try {
		int[] a = getJdbcTemplate().batchUpdate(query.toString(),
				new BatchPreparedStatementSetter() {

					@Override
					public void setValues(PreparedStatement pss, int i)
							throws SQLException {

						FeeTerms feeTerms = feeTermList
								.get(i);

						pss.setInt(1, feeTerms.getDbTs());
						pss.setString(2, feeTerms.getInstId());
						pss.setString(3, feeTerms.getBranchId());
						pss.setString(4, feeTerms.getFeeTerm());
						pss.setString(5, feeTerms.getFeePaymentTerm());
						pss.setString(6, feeTerms.getDelFlag().trim());
						pss.setString(7, feeTerms.getrModId().trim());
						pss.setString(8, feeTerms.getrCreId().trim());
					}

					@Override
					public int getBatchSize() {
						return feeTermList.size();
					}
				}

		);
		} catch (org.springframework.dao.DuplicateKeyException e) {
			logger.error("Duplicate Entry Exception Occured  "+e.getMessage());
			throw new DuplicateEntryException();
		}
		
	}
//select FEE_TERM,FEE_PMT_TERM from ftrm where INST_ID='ASC' and BRANCH_ID='BR001' and DEL_FLG='N';
	@Override
	public List<FeeTerms> selectTermFeesListRecs(FeeTermsKey feeTermsKey)
			throws NoDataFoundException {
		logger.debug("DAO :Inside AcademicCalendar List select method");
		List<Object> data = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		    sql.append("select ")
				.append(" FEE_TERM,")
				.append("FEE_PMT_TERM ")
				.append(" from ftrm ")
				.append(" where")
				.append("  INST_ID= ?")
			.append(" and  BRANCH_ID= ?")
			.append(" and  DEL_FLG=?");
			data.add(feeTermsKey.getInstId());
			data.add(feeTermsKey.getBranchId());
			data.add("N");
			
			
		Object[] array = data.toArray(new Object[data.size()]);
		logger.debug("Where clause conditions size :" + array.length);

		List<FeeTerms> selectedListTrmFees = getJdbcTemplate()
				.query(sql.toString(), array, new TermFeesSelectRowMapper());
		if (selectedListTrmFees.size() == 0) {
			logger.error("No  record found");
			throw new NoDataFoundException();
		}
		logger.debug("DAO : AcademicCalendar List value"+selectedListTrmFees.size() );
		return selectedListTrmFees;
		}

	@Override
	public void updateTermFeesRecDelFlgYesToNo(final List<FeeTerms> feeTermsList,
			final List<FeeTermsKey> feeTermsKeyList) throws UpdateFailedException {
		logger.debug("Inside Fee Term List update Record");
		logger.debug("FeeTerm List Size   "+feeTermsList.size());
		logger.debug("FeeTermsKey List Key Size   "+feeTermsKeyList.size());
		StringBuffer query = new StringBuffer();
		
		query.append("update ftrm set").append(" DB_TS=?,")
		.append("DEL_FLG= 'N',")
		.append("R_MOD_ID= ?,").append(" R_MOD_TIME=now()")
		.append(" where").append("   DB_TS= ?")
		.append(" and  INST_ID= ?").append(" and  BRANCH_ID= ?")
		.append(" and  FEE_TERM= ?").append(" and  FEE_PMT_TERM= ?")
		.append(" and  DEL_FLG='Y'");
		
		int[] a = getJdbcTemplate().batchUpdate(query.toString(),
				new BatchPreparedStatementSetter() {

					@Override
					public void setValues(PreparedStatement pss, int i)
							throws SQLException {
						FeeTerms feeTerm = feeTermsList
								.get(i);
						FeeTermsKey feeTermKey = feeTermsKeyList
								.get(i);
						System.out.println("fee term to string : "+feeTerm.toString());
						System.out.println("FeeTermsKey to string : "+feeTermKey.toString());
						pss.setInt(1, feeTerm.getDbTs()+1);
						pss.setString(2, feeTerm.getrModId().trim());
						pss.setInt(3, feeTermKey.getDbTs());
						pss.setString(4, feeTermKey.getInstId()
								.trim());
						pss.setString(5, feeTermKey.getBranchId()
								.trim());
						pss.setString(6, feeTermKey.getFeesTerm()
								.trim());
						pss.setString(7, feeTermKey.getFeePaymentTerm()
								.trim());
					}

					@Override
					public int getBatchSize() {
						return feeTermsList.size();
					}
				}

		);
		/*if(a.length==0) {
			logger.error("update failed Exception Occured  ");
			throw new UpdateFailedException();
		}*/
		
	}


}
class TermFeesSelectRowMapper implements RowMapper<FeeTerms> {

	@Override
	public FeeTerms mapRow(ResultSet rs, int arg1) throws SQLException {

		FeeTerms feeTerm = new FeeTerms();
		feeTerm.setFeeTerm((rs.getString("FEE_TERM")));
		feeTerm.setFeePaymentTerm(rs.getString("FEE_PMT_TERM"));

		return feeTerm;
	}
	
}
class FeesTermDetailsRowmapper implements RowMapper<FeeTerms> {

	@Override
	public FeeTerms mapRow(ResultSet rs, int arg1) throws SQLException {

		FeeTerms feeTerm = new FeeTerms();
		feeTerm.setFeeTerm((rs.getString("CM_CODE")));
		feeTerm.setFeeTermValue(rs.getString("CODE_DESC"));

		return feeTerm;
	}
	
}
class FeesPaymentTermDetailsRowmapper implements RowMapper<FeeTerms> {

	@Override
	public FeeTerms mapRow(ResultSet rs, int arg1) throws SQLException {

		FeeTerms feeTerm = new FeeTerms();
		feeTerm.setFeePaymentTerm((rs.getString("CM_CODE")));
		feeTerm.setFeePaymentTermValue(rs.getString("CODE_DESC"));

		return feeTerm;
	}
	
}