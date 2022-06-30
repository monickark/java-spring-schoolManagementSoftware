package com.jaw.core.dao;

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

import com.jaw.common.constants.CommonCodeConstant;
import com.jaw.common.exceptions.DeleteFailedException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.UpdateFailedException;
import com.jaw.framework.dao.BaseDao;
import com.jaw.framework.sessCache.UserSessionDetails;
//SpecialClass DAO class
@Repository
public class SpecialClassDAO extends BaseDao implements ISpecialClassDAO{
	// Logging
	Logger logger = Logger.getLogger(SpecialClassDAO.class);

	@Override
	public void insertSpecialClassRec(final SpecialClass specialClass)
			throws DuplicateEntryException {
		logger.debug("Inside insert method");
		logger.debug("SpecialClass object values :"
				+ specialClass.toString());
		StringBuffer query = new StringBuffer();		
		query = query.append("insert into scls ( ")
				.append("DB_TS,")
				.append("INST_ID,")
				.append("BRANCH_ID,")
				.append("AC_TERM,")
				.append("SC_ITEM_ID,")
				.append("SC_DATE,")				
				.append("STUDENTGRP_ID,")
				.append("CRSL_ID,")
				.append("FROM_TIME,")
				.append("TO_TIME,")
				.append("SC_RMKS,")		
				.append("DEL_FLG,")
				.append("R_MOD_ID,")
				.append("R_MOD_TIME,")
				.append("R_CRE_ID,")
				.append("R_CRE_TIME)")
				.append(" values (?,?,?,?,?,?,?,?,?,?,?,?,?,now(),?,now())");

		try {
			getJdbcTemplate().update(query.toString(),
					new PreparedStatementSetter() {

						@Override
						public void setValues(PreparedStatement pss)
								throws SQLException {
							pss.setInt(1, specialClass.getDbTs());
							pss.setString(2, specialClass.getInstId());
							pss.setString(3, specialClass.getBranchId());
							pss.setString(4, specialClass.getAcTerm().trim());
							pss.setString(5, specialClass.getScItemId().trim());
							pss.setString(6, specialClass.getScDate().trim());							
							pss.setString(7, specialClass.getStudentGrpId());
							pss.setString(8, specialClass.getCrslId().trim());
							pss.setString(9, specialClass.getFromTime().trim());
							pss.setString(10, specialClass.getToTime().trim());
							pss.setString(11, specialClass.getScRmks().trim());	
							pss.setString(12, specialClass.getDelFlag().trim());
							pss.setString(13, specialClass.getrModId().trim());
							pss.setString(14, specialClass.getrCreId().trim());

						}

					});
		} catch (org.springframework.dao.DuplicateKeyException e) {
			throw new DuplicateEntryException();
		}
		
		
	}

	@Override
	public void updateSpecialClassRec(final SpecialClass specialClass,
			final SpecialClassKey specialClassKey) throws UpdateFailedException {
		logger.debug("Inside update method");
		logger.debug("SpecialClass object values :"+ specialClass.toString());
		logger.debug("SpecialClassKey Details object values :"+ specialClassKey.toString());		
		StringBuffer sql = new StringBuffer();
			sql.append("update scls set")
		        .append(" DB_TS= ?,")				
				.append("SC_DATE=?,")
				.append("STUDENTGRP_ID=?,")
				.append("CRSL_ID=?,")
				.append("FROM_TIME=?,")
				.append("TO_TIME=?,")
				.append("SC_RMKS=?,")	
				.append("DEL_FLG= 'N',")
				.append("R_MOD_ID= ?,")
				.append(" R_MOD_TIME=now()")
				.append(" where")
				.append(" DB_TS= ?")
				.append(" and  INST_ID= ?")
				.append(" and  BRANCH_ID= ?")
				.append(" and  AC_TERM= ?")	
				.append(" and  SC_ITEM_ID= ?")	
				.append(" and  DEL_FLG='N'");

		

		int updateRecs = getJdbcTemplate().update(sql.toString(),
				new PreparedStatementSetter() {

					@Override
					public void setValues(PreparedStatement ps)
							throws SQLException {
						ps.setInt(1, specialClass.getDbTs() + 1);
						ps.setString(2, specialClass.getScDate().trim());
						ps.setString(3, specialClass.getStudentGrpId().trim());	
						ps.setString(4, specialClass.getCrslId().trim());			
						ps.setString(5, specialClass.getFromTime().trim());			
						ps.setString(6, specialClass.getToTime().trim());			
						ps.setString(7, specialClass.getScRmks().trim());			
						ps.setString(8, specialClass.getrModId().trim());	
						
						ps.setInt(9, specialClassKey.getDbTs());
						ps.setString(10, specialClassKey.getInstId().trim());
						ps.setString(11, specialClassKey.getBranchId().trim());
						ps.setString(12, specialClassKey.getAcTerm().trim());
						ps.setString(13, specialClassKey.getScItemId().trim());
						

					}

				});
		if (updateRecs == 0) {
			throw new UpdateFailedException();

		}
		
	}

	@Override
	public void deleteSpecialClassRec(final SpecialClass specialClass,
			final SpecialClassKey specialClassKey) throws DeleteFailedException {
		logger.debug("Inside delete method");
		logger.debug("SpecialClass object values :"+ specialClass.toString());
		logger.debug("SpecialClass Key object values :"+ specialClassKey.toString());
		
		StringBuffer sql = new StringBuffer();
		   sql.append("update scls set")
		        .append(" DB_TS= ?,")
				.append("DEL_FLG= 'Y',")
				.append("R_MOD_ID= ?,")
				.append(" R_MOD_TIME=now()")
				.append(" where")
				.append(" DB_TS= ?")
				.append(" and INST_ID= ?")
				.append(" and  BRANCH_ID= ?")
				.append(" and  AC_TERM= ?")	
				.append(" and  SC_ITEM_ID= ?")
				.append(" and  DEL_FLG='N'");

		int deletedRecs = getJdbcTemplate().update(sql.toString(),
				new PreparedStatementSetter() {

					@Override
					public void setValues(PreparedStatement ps)
							throws SQLException {
						ps.setInt(1, specialClass.getDbTs() + 1);
						ps.setString(2, specialClass.getrModId().trim());
						ps.setInt(3, specialClassKey.getDbTs());
						ps.setString(4, specialClassKey.getInstId().trim());
						ps.setString(5, specialClassKey.getBranchId().trim());
						ps.setString(6, specialClassKey.getAcTerm().trim());
						ps.setString(7, specialClassKey.getScItemId().trim());
					}

				});
		if (deletedRecs == 0) {
			throw new DeleteFailedException();

		}
		
	}

	@Override
	public SpecialClass selectSpecialClassRec(final SpecialClassKey specialClassKey)
			throws NoDataFoundException {
		logger.debug("Inside select method");
		logger.debug("SpecialClassKey object values :"+ specialClassKey.toString());
		List<Object> data = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		    sql.append("select ")
		        .append(" DB_TS,")
				.append("INST_ID,")
				.append("BRANCH_ID,")
				.append("AC_TERM,")
				.append("SC_ITEM_ID,")
				.append("SC_DATE,")				
				.append("STUDENTGRP_ID,")
				.append("CRSL_ID,")
				.append("FROM_TIME,")
				.append("TO_TIME,")
				.append("SC_RMKS,")	
				.append("DEL_FLG,")
				.append("R_MOD_ID,")
				.append("R_MOD_TIME,")
				.append("R_CRE_ID,")
				.append("R_CRE_TIME")
				.append(" from scls ")
				.append(" where")
				.append("   INST_ID= ?")
				.append(" and  BRANCH_ID= ?")
				.append(" and  AC_TERM= ?")
				.append(" and  SC_ITEM_ID= ?")
				.append(" and  DEL_FLG=?");		
		    data.add(specialClassKey.getInstId());
			data.add(specialClassKey.getBranchId());
			data.add(specialClassKey.getAcTerm());
			data.add(specialClassKey.getScItemId());
			data.add("N");
			if ((specialClassKey.getDbTs() !=null)&&(specialClassKey.getDbTs() !=0)
					) {
				sql.append(" and DB_TS=?  ");
				logger.debug("Db Ts Value :" + specialClassKey.getDbTs());
				data.add(specialClassKey.getDbTs());
			}
			
		    SpecialClass selectedSpecialClass = null;
			Object[] array = data.toArray(new Object[data.size()]);
			logger.debug("Where clause conditions size :" + array.length);
		    selectedSpecialClass = (SpecialClass) getJdbcTemplate()
				.query(sql.toString(), array, new ResultSetExtractor<SpecialClass>() {

					@Override
					public SpecialClass extractData(ResultSet rs)
							throws SQLException, DataAccessException {
						SpecialClass specialClass = null;
						if (rs.next()) {
							specialClass = new SpecialClass();
							specialClass.setDbTs(rs.getInt("DB_TS"));
							specialClass.setInstId(rs.getString("INST_ID"));
							specialClass.setBranchId(rs.getString("BRANCH_ID"));
							specialClass.setAcTerm(rs.getString("AC_TERM"));
							specialClass.setScItemId(rs.getString("SC_ITEM_ID"));
							specialClass.setScDate(rs.getString("SC_DATE"));							
							specialClass.setStudentGrpId(rs.getString("STUDENTGRP_ID"));
							specialClass.setCrslId(rs.getString("CRSL_ID"));
							specialClass.setFromTime(rs.getString("FROM_TIME"));
							specialClass.setToTime(rs.getString("TO_TIME"));
							specialClass.setScRmks(rs.getString("SC_RMKS"));							
							specialClass.setDelFlag(rs.getString("DEL_FLG"));
							specialClass.setrModId(rs.getString("R_MOD_ID"));
							specialClass.setrModTime(rs.getString("R_MOD_TIME"));
							specialClass.setrCreId(rs.getString("R_CRE_ID"));
							specialClass.setrCreTime(rs.getString("R_CRE_TIME"));
						}
						return specialClass;
					}

				});

		if (selectedSpecialClass == null) {
			throw new NoDataFoundException();
		}

		return selectedSpecialClass;
	}

	@Override
	public int checkDateHasSpecialClass(SpecialClass specialClass,UserSessionDetails userSessionDetails) {
		logger.debug("Inside Check Date have Special Class");

		logger.debug("Special Class Key object values :"
				+ specialClass.toString());
		StringBuffer sql = new StringBuffer();
		sql.append("select exists(")
		.append("select sc_date ")
		.append("from scls")				
				.append(" where")
					.append("  INST_ID='" + specialClass.getInstId()
						+ "'")
				.append(" and BRANCH_ID='" + specialClass.getBranchId()
						+ "'")
				.append(" and  AC_TERM='" + specialClass.getAcTerm()
						+ "'")
				.append(" and  SC_DATE='" + specialClass.getScDate()
						+ "'")
				.append(" and  studentgrp_id ='"+specialClass.getStudentGrpId()+"'");
				if((!userSessionDetails.getInbrCategory().equals(CommonCodeConstant.IBCAT_SSLC))&&(!userSessionDetails.getInbrCategory().equals(CommonCodeConstant.IBCAT_ICSE))){
					sql.append(" and  CRSL_ID ='"+specialClass.getCrslId()+"'");
				}
				sql.append(" and  DEL_FLG='N')");
		int rows = getJdbcTemplate().queryForInt(
				sql.toString());

		System.out.println("rowsssssssssssssssssssss"+rows);
		
		return rows;
	}

	@Override
	public int checkSpecialClassExist(SpecialClass specialClass,UserSessionDetails userSessionDetails) {
		logger.debug("Inside Check  Special Class");

		logger.debug("Special Class  object values :"
				+ specialClass.toString());
		StringBuffer sql = new StringBuffer();
		
		
		
		sql.append("select exists(")
		.append("select SC_ITEM_ID ")
		.append("from scls")				
				.append(" where")
					.append("  INST_ID='" + specialClass.getInstId()
						+ "'")
				.append(" and BRANCH_ID='" + specialClass.getBranchId()
						+ "'")
				.append(" and  AC_TERM='" + specialClass.getAcTerm()
						+ "'")
				.append(" and  SC_DATE='" + specialClass.getScDate()
						+ "'")
				.append(" and  studentgrp_id ='"+specialClass.getStudentGrpId()+"'");
				if((!userSessionDetails.getInbrCategory().equals(CommonCodeConstant.IBCAT_SSLC))&&(!userSessionDetails.getInbrCategory().equals(CommonCodeConstant.IBCAT_ICSE))){
					sql.append(" and  CRSL_ID ='"+specialClass.getCrslId()+"'");
				}
						sql.append(" and  DEL_FLG='N')");
		int rows1 = getJdbcTemplate().queryForInt(
				sql.toString());

		System.out.println("rowsssssssssssssssssssss"+rows1);
		
		return rows1;
	}
	
}
