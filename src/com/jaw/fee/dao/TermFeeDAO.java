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

import com.jaw.common.exceptions.DeleteFailedException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.UpdateFailedException;
import com.jaw.core.dao.AcademicCalendar;
import com.jaw.framework.dao.BaseDao;
@Repository
public class TermFeeDAO extends BaseDao implements ITermFeeDAO{
	// Logging
	Logger logger = Logger.getLogger(TermFeeDAO.class);

	@Override
	public void deleteTermFeesRec(final FeeTerms feeTerms, final FeeTermsKey feeTermsKey)
			throws DeleteFailedException {
		logger.debug("Inside delete method");
		logger.debug("Fee Term object values :"+ feeTerms.toString());
		logger.debug("Fee Term key object values :"+ feeTermsKey.toString());
		
		StringBuffer sql = new StringBuffer();
		   sql.append("update ftrm set")
		        .append(" DB_TS= DB_TS+1,")
				.append("DEL_FLG= 'Y',")
				.append("R_MOD_ID= ?,")
				.append(" R_MOD_TIME=now()")
				.append(" where")
				.append("  INST_ID= ?")
				.append(" and  BRANCH_ID= ?")
				.append(" and  FEE_PMT_TERM= ?");
		        if(feeTermsKey.getFeeTerm().length!=0){
		        	String result="";
		        	sql.append(" and  FEE_TERM in (");		        	
		        	for(int i=0;i<feeTermsKey.getFeeTerm().length;i++){
						if(i==0){
							sql.append("'" +feeTermsKey.getFeeTerm()[i]+"'");
						}else{
							sql.append(", '" +feeTermsKey.getFeeTerm()[i]+"'");
						}
						
					}
		        		sql.append(result+")");
		        	
		        }
				
				
				sql.append(" and  DEL_FLG='N'");

		int deletedRecs = getJdbcTemplate().update(sql.toString(),
				new PreparedStatementSetter() {

					@Override
					public void setValues(PreparedStatement ps)
							throws SQLException {
						ps.setString(1, feeTerms.getrModId().trim());
						ps.setString(2, feeTermsKey.getInstId().trim());
						ps.setString(3, feeTermsKey.getBranchId().trim());
						ps.setString(4, feeTermsKey.getFeePaymentTerm().trim());
						

					}

				});
		if (deletedRecs == 0) {
			logger.error("Delete Failed Exception");
			throw new DeleteFailedException();

		}
	}

	@Override
	public FeeTerms selectFeesTermRec(FeeTermsKey feeTermsKey) throws NoDataFoundException
			 {
		logger.debug("Inside select method");
		logger.debug("FeeTermsKey object values :"+ feeTermsKey.toString());
		List<Object> data = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		    sql.append("select ")
		        .append("DB_TS,")
		        .append("INST_ID,")
				.append("BRANCH_ID,")
				.append("FEE_TERM,")
				.append("FEE_PMT_TERM,")
				.append("DEL_FLG,")
				.append("R_MOD_ID,")
				.append("R_MOD_TIME,")
				.append("R_CRE_ID,")
				.append("R_CRE_TIME ")
				.append(" from ftrm ")
				.append(" where")
				.append("  INST_ID= ?")
				.append(" and  BRANCH_ID= ?")
				.append(" and  FEE_PMT_TERM= ?")				
				.append(" and  DEL_FLG=?");
		    data.add(feeTermsKey.getInstId());
			data.add(feeTermsKey.getBranchId());
			data.add(feeTermsKey.getFeePaymentTerm());
			data.add("N");
			
			if ((feeTermsKey.getFeesTerm() !=null)&&(!feeTermsKey.getFeesTerm().equals(""))) {
				sql.append(" and FEE_TERM=?  ");
				logger.debug("FEE_TERM Value :" + feeTermsKey.getFeesTerm());
				data.add(feeTermsKey.getFeesTerm());
			}
			FeeTerms selectedListFeeTerms = null;
		Object[] array = data.toArray(new Object[data.size()]);
		logger.debug("Where clause conditions size :" + array.length);
		System.out.println("queryyyyyyyyyyyyyyyyy "+sql.toString());
		selectedListFeeTerms = (FeeTerms) getJdbcTemplate()
				.query(sql.toString(), array, new ResultSetExtractor<FeeTerms>() {

					@Override
					public FeeTerms extractData(ResultSet rs)
							throws SQLException, DataAccessException {
						FeeTerms feeTerms = null;
						System.out.println("sddsdds "+rs.toString());
						if (rs.next()) {
							System.out.println("ifffffffffff");
							feeTerms = new FeeTerms();
							feeTerms.setDbTs(rs.getInt("DB_TS"));
							feeTerms.setInstId(rs.getString("INST_ID"));
							feeTerms.setBranchId(rs.getString("BRANCH_ID"));
							feeTerms.setFeeTerm(rs.getString("FEE_TERM"));
							feeTerms.setFeePaymentTerm(rs.getString("FEE_PMT_TERM"));
							feeTerms.setDelFlag((rs.getString("DEL_FLG")));
							feeTerms.setrModId(rs.getString("R_MOD_ID"));
							feeTerms.setrModTime(rs.getString("R_MOD_TIME"));
							feeTerms.setrCreId(rs.getString("R_CRE_ID"));
							feeTerms.setrCreTime(rs.getString("R_CRE_TIME"));
							System.out.println("fffff"+feeTerms.toString());
						}
						System.out.println("fffff"+feeTerms.toString());
						return feeTerms;
					}

				});

		if (selectedListFeeTerms == null) {
			logger.error("No  record found");
			throw new NoDataFoundException();
		}
System.out.println("selected item : "+selectedListFeeTerms.toString());
		return selectedListFeeTerms;
	}

	@Override
	public FeeTerms selectFeesTermRecDelFlgY(FeeTermsKey feeTermsKey)
			 {
		logger.debug("Inside select method");
		logger.debug("FeeTermsKey object values :"+ feeTermsKey.toString());
		List<Object> data = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		    sql.append("select ")
		        .append("DB_TS,")
		        .append("INST_ID,")
				.append("BRANCH_ID,")
				.append("FEE_TERM,")
				.append("FEE_PMT_TERM,")
				.append("DEL_FLG,")
				.append("R_MOD_ID,")
				.append("R_MOD_TIME,")
				.append("R_CRE_ID,")
				.append("R_CRE_TIME ")
				.append(" from ftrm ")
				.append(" where")
				.append("  INST_ID= ?")
				.append(" and  BRANCH_ID= ?")
				.append(" and  FEE_PMT_TERM= ?")				
				.append(" and  DEL_FLG=?");
		    data.add(feeTermsKey.getInstId());
			data.add(feeTermsKey.getBranchId());
			data.add(feeTermsKey.getFeePaymentTerm());
			data.add("Y");
			
			if ((feeTermsKey.getFeesTerm() !=null)&&(!feeTermsKey.getFeesTerm().equals(""))) {
				sql.append(" and FEE_TERM=?  ");
				logger.debug("FEE_TERM Value :" + feeTermsKey.getFeesTerm());
				data.add(feeTermsKey.getFeesTerm());
			}
			FeeTerms selectedListFeeTerms = null;
		Object[] array = data.toArray(new Object[data.size()]);
		logger.debug("Where clause conditions size :" + array.length);
		selectedListFeeTerms = (FeeTerms) getJdbcTemplate()
				.query(sql.toString(), array, new ResultSetExtractor<FeeTerms>() {

					@Override
					public FeeTerms extractData(ResultSet rs)
							throws SQLException, DataAccessException {
						FeeTerms feeTerms = null;
						if (rs.next()) {
							feeTerms = new FeeTerms();
							feeTerms.setDbTs(rs.getInt("DB_TS"));
							feeTerms.setInstId(rs.getString("INST_ID"));
							feeTerms.setBranchId(rs.getString("BRANCH_ID"));
							feeTerms.setFeeTerm(rs.getString("FEE_TERM"));
							feeTerms.setFeePaymentTerm(rs.getString("FEE_PMT_TERM"));
							feeTerms.setDelFlag((rs.getString("DEL_FLG")));
							feeTerms.setrModId(rs.getString("R_MOD_ID"));
							feeTerms.setrModTime(rs.getString("R_MOD_TIME"));
							feeTerms.setrCreId(rs.getString("R_CRE_ID"));
							feeTerms.setrCreTime(rs.getString("R_CRE_TIME"));
							System.out.println("fffff"+feeTerms.toString());
						}
						
						return feeTerms;
					}

				});

		/*if (selectedListFeeTerms == null) {
			logger.error("No  record found");
			throw new NoDataFoundException();
		}*/

		return selectedListFeeTerms;
	}

	

}
