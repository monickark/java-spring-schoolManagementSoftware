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
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.UpdateFailedException;
import com.jaw.core.dao.CourseTermLinking;
import com.jaw.framework.dao.BaseDao;
@Repository
public class FeePaymentDetailDAO extends BaseDao implements IFeePaymentDetailDAO{
	// Logging
	Logger logger = Logger.getLogger(FeePaymentDetailDAO.class);

	@Override
	public void insertFeePaymentDetailRec(final FeePaymentDetail feePaymentDetail)
			throws DuplicateEntryException {
		logger.debug("Inside insert method");
		logger.debug("FeePaymentDetail object values :"
				+ feePaymentDetail.toString());		
		StringBuffer query = new StringBuffer();
		
		query = query.append("insert into fptd ( ")
				.append("DB_TS,")
				.append("INST_ID,")
				.append("BRANCH_ID,")
				.append("AC_TERM,")
				.append("FEE_PMT_TERM,")
				.append("DUE_DATE,")
				.append("FEE_CATGRY,")
				.append("DEL_FLG,")
				.append("R_MOD_ID,")
				.append("R_MOD_TIME,")
				.append("R_CRE_ID,")
				.append("R_CRE_TIME)")

				.append(" values (?,?,?,?,?,?,?,?,?,now(),?,now())");

		try {
			getJdbcTemplate().update(query.toString(),
					new PreparedStatementSetter() {

						@Override
						public void setValues(PreparedStatement pss)
								throws SQLException {
							pss.setInt(1, feePaymentDetail.getDbTs());
							pss.setString(2, feePaymentDetail.getInstId());
							pss.setString(3, feePaymentDetail.getBranchId());
							pss.setString(4, feePaymentDetail.getAcTerm().trim());
							pss.setString(5, feePaymentDetail.getFeePaymentTerm().trim());
							pss.setString(6, feePaymentDetail.getDueDate());
							pss.setString(7, feePaymentDetail.getFeeCategory());
							pss.setString(8, feePaymentDetail.getDelFlag().trim());
							pss.setString(9, feePaymentDetail.getrModId().trim());
							pss.setString(10, feePaymentDetail.getrCreId().trim());

						}

					});
		} catch (org.springframework.dao.DuplicateKeyException e) {
			throw new DuplicateEntryException();
		}
		
	}

	@Override
	public void deleteFeePaymentDetailRec(final FeePaymentDetail feePaymentDetail,
			final FeePaymentDetailKey feePaymentDetailKey)
			throws DeleteFailedException {
		logger.debug("Inside delete method");
		logger.debug("FeePaymentDetail object values :"+ feePaymentDetail.toString());
		logger.debug("FeePaymentDetailKey object values :"+ feePaymentDetailKey.toString());
		
		StringBuffer sql = new StringBuffer();
		   sql.append("update fptd set")
		        .append(" DB_TS= ?,")
				.append("DEL_FLG= 'Y',")
				.append("R_MOD_ID= ?,")
				.append(" R_MOD_TIME=now()")
				.append(" where")
				.append(" DB_TS= ?")
				.append(" and INST_ID= ?")
				.append(" and  BRANCH_ID= ?")
				.append(" and  AC_TERM= ?")
				.append(" and  FEE_PMT_TERM= ?")
				.append(" and  DEL_FLG='N'");

		int deletedRecs = getJdbcTemplate().update(sql.toString(),
				new PreparedStatementSetter() {

					@Override
					public void setValues(PreparedStatement ps)
							throws SQLException {
						ps.setInt(1, feePaymentDetail.getDbTs() + 1);
						ps.setString(2, feePaymentDetail.getrModId().trim());
						ps.setInt(3, feePaymentDetailKey.getDbTs());
						ps.setString(4, feePaymentDetailKey.getInstId().trim());
						ps.setString(5, feePaymentDetailKey.getBranchId().trim());
						ps.setString(6, feePaymentDetailKey.getAcTerm().trim());
						ps.setString(7, feePaymentDetailKey.getFeePaymentTerm().trim());
						

					}

				});
		if (deletedRecs == 0) {
			throw new DeleteFailedException();

		}
		
	}

	@Override
	public FeePaymentDetail selectFeePaymentDetailRec(
			FeePaymentDetailKey feePaymentDetailKey)
			throws NoDataFoundException {
		logger.debug("Inside select method");
		logger.debug("FeePaymentDetailKey object values :"+ feePaymentDetailKey.toString());
		List<Object> data = new ArrayList<Object>();
		
		
		StringBuffer sql = new StringBuffer();
		    sql.append("select ")
		       .append("DB_TS,")
				.append("INST_ID,")
				.append("BRANCH_ID,")
				.append("AC_TERM,")
				.append("FEE_PMT_TERM,")
				.append("DUE_DATE,")
				.append("FEE_CATGRY,")
				.append("DEL_FLG,")
				.append("R_MOD_ID,")
				.append("R_MOD_TIME,")
				.append("R_CRE_ID,")
				.append("R_CRE_TIME ")
				.append(" from fptd ")
				.append(" where")
				.append("   INST_ID= ?")
				.append(" and  BRANCH_ID= ?")
				.append(" and  DEL_FLG=?")
		        .append(" and  AC_TERM= ?")
		        .append(" and  FEE_PMT_TERM= ?");
		    data.add(feePaymentDetailKey.getInstId().trim());
			data.add(feePaymentDetailKey.getBranchId().trim());
			data.add("N");
			data.add(feePaymentDetailKey.getAcTerm().trim());	
			data.add(feePaymentDetailKey.getFeePaymentTerm().trim());	
			
			if ((feePaymentDetailKey.getDbTs() !=null)&&(feePaymentDetailKey.getDbTs() !=0)
					) {
				sql.append(" and DB_TS=?  ");
				logger.debug("DB_TS  :" + feePaymentDetailKey.getDbTs());
				data.add(feePaymentDetailKey.getDbTs());
			}
			FeePaymentDetail selectedFeePaymentDetailRec = null;
			Object[] array = data.toArray(new Object[data.size()]);
			logger.debug("Where clause conditions size :" + array.length);
			
			selectedFeePaymentDetailRec = (FeePaymentDetail) getJdbcTemplate()
				.query(sql.toString(), array, new ResultSetExtractor<FeePaymentDetail>() {
					
					@Override
					public FeePaymentDetail extractData(ResultSet rs)
							throws SQLException, DataAccessException {						
						FeePaymentDetail feePaymentDet = null;
				if(rs.next()) {
				
							feePaymentDet = new FeePaymentDetail();
							feePaymentDet.setDbTs(rs.getInt("DB_TS"));
							feePaymentDet.setInstId(rs.getString("INST_ID"));
							feePaymentDet.setBranchId(rs.getString("BRANCH_ID"));
							feePaymentDet.setAcTerm(rs.getString("AC_TERM"));
							feePaymentDet.setFeePaymentTerm(rs.getString("FEE_PMT_TERM"));
							feePaymentDet.setDueDate(rs.getString("DUE_DATE"));
							feePaymentDet.setFeeCategory(rs.getString("FEE_CATGRY"));
							feePaymentDet.setDelFlag((rs.getString("DEL_FLG")));
							feePaymentDet.setrModId(rs.getString("R_MOD_ID"));
							feePaymentDet.setrModTime(rs.getString("R_MOD_TIME"));
							feePaymentDet.setrCreId(rs.getString("R_CRE_ID"));
							feePaymentDet.setrCreTime(rs.getString("R_CRE_TIME"));							
						}				
						return feePaymentDet;
					}

				});
		   
		if (selectedFeePaymentDetailRec == null) {
			throw new NoDataFoundException();
		}


		return selectedFeePaymentDetailRec;
	}

	@Override
	public FeePaymentDetail selectFeePaymentDetailDelFlgYRec(
			FeePaymentDetailKey feePaymentDetailKey)
			throws NoDataFoundException {
		logger.debug("Inside select method");
		logger.debug("FeePaymentDetailKey object values :"+ feePaymentDetailKey.toString());
		List<Object> data = new ArrayList<Object>();
		
		
		StringBuffer sql = new StringBuffer();
		    sql.append("select ")
		       .append("DB_TS,")
				.append("INST_ID,")
				.append("BRANCH_ID,")
				.append("AC_TERM,")
				.append("FEE_PMT_TERM,")
				.append("DUE_DATE,")
				.append("FEE_CATGRY,")
				.append("DEL_FLG,")
				.append("R_MOD_ID,")
				.append("R_MOD_TIME,")
				.append("R_CRE_ID,")
				.append("R_CRE_TIME ")
				.append(" from fptd ")
				.append(" where")
				.append("   INST_ID= ?")
				.append(" and  BRANCH_ID= ?")
				.append(" and  DEL_FLG=?")
		        .append(" and  AC_TERM= ?")
		        .append(" and  FEE_PMT_TERM= ?");
		    data.add(feePaymentDetailKey.getInstId().trim());
			data.add(feePaymentDetailKey.getBranchId().trim());
			data.add("Y");
			data.add(feePaymentDetailKey.getAcTerm().trim());	
			data.add(feePaymentDetailKey.getFeePaymentTerm().trim());	
			
			if ((feePaymentDetailKey.getDbTs() !=null)&&(feePaymentDetailKey.getDbTs() !=0)
					) {
				sql.append(" and DB_TS=?  ");
				logger.debug("DB_TS  :" + feePaymentDetailKey.getDbTs());
				data.add(feePaymentDetailKey.getDbTs());
			}
			FeePaymentDetail selectedFeePaymentDetailRec = null;
			Object[] array = data.toArray(new Object[data.size()]);
			logger.debug("Where clause conditions size :" + array.length);
			
			selectedFeePaymentDetailRec = (FeePaymentDetail) getJdbcTemplate()
				.query(sql.toString(), array, new ResultSetExtractor<FeePaymentDetail>() {
					
					@Override
					public FeePaymentDetail extractData(ResultSet rs)
							throws SQLException, DataAccessException {						
						FeePaymentDetail feePaymentDet = null;
				if(rs.next()) {
				
							feePaymentDet = new FeePaymentDetail();
							feePaymentDet.setDbTs(rs.getInt("DB_TS"));
							feePaymentDet.setInstId(rs.getString("INST_ID"));
							feePaymentDet.setBranchId(rs.getString("BRANCH_ID"));
							feePaymentDet.setAcTerm(rs.getString("AC_TERM"));
							feePaymentDet.setFeePaymentTerm(rs.getString("FEE_PMT_TERM"));
							feePaymentDet.setDueDate(rs.getString("DUE_DATE"));
							feePaymentDet.setFeeCategory(rs.getString("FEE_CATGRY"));
							feePaymentDet.setDelFlag((rs.getString("DEL_FLG")));
							feePaymentDet.setrModId(rs.getString("R_MOD_ID"));
							feePaymentDet.setrModTime(rs.getString("R_MOD_TIME"));
							feePaymentDet.setrCreId(rs.getString("R_CRE_ID"));
							feePaymentDet.setrCreTime(rs.getString("R_CRE_TIME"));							
						}				
						return feePaymentDet;
					}

				});
		   
		if (selectedFeePaymentDetailRec == null) {
			throw new NoDataFoundException();
		}


		return selectedFeePaymentDetailRec;
	}

	@Override
	public void updateFeePaymentDetailRecDelFlgYesToNo(
			final FeePaymentDetail feePaymentDetail,
			final FeePaymentDetailKey feePaymentDetailKey)
			throws UpdateFailedException {
		logger.debug("Inside update method");
		logger.debug("FeePaymentDetail object values :"+ feePaymentDetail.toString());
		logger.debug("FeePaymentDetailKey object values :"+ feePaymentDetailKey.toString());
		
		
StringBuffer query = new StringBuffer();
		
		query.append("update fptd set").append(" DB_TS=?,")
		.append("DEL_FLG= 'N',")
		.append("R_MOD_ID= ?,").append(" R_MOD_TIME=now()")
		.append(" where").append("   DB_TS= ?")
		.append(" and  INST_ID= ?").append(" and  BRANCH_ID= ?")
		.append(" and  AC_TERM= ?").append(" and  FEE_PMT_TERM= ?")
		.append(" and  DEL_FLG='Y'");

		Integer updateRecs = null;
System.out.println("query : "+query.toString());

	updateRecs = getJdbcTemplate().update(query.toString(),
				new PreparedStatementSetter() {

					@Override
					public void setValues(PreparedStatement ps)
							throws SQLException {
						ps.setInt(1, feePaymentDetail.getDbTs() + 1);						
						ps.setString(2, feePaymentDetail.getrModId().trim());
						ps.setInt(3, feePaymentDetailKey.getDbTs());
						ps.setString(4, feePaymentDetailKey.getInstId().trim());
						ps.setString(5, feePaymentDetailKey.getBranchId().trim());
						ps.setString(6, feePaymentDetailKey.getAcTerm().trim());
						ps.setString(7, feePaymentDetailKey.getFeePaymentTerm().trim());
						

					}

				});

		if (updateRecs == 0) {
			logger.error("Update Failed Exception Occured");
			throw new UpdateFailedException();

		}

	}


}
