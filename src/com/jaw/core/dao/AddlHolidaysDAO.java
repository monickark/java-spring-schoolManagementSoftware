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

import com.jaw.common.exceptions.DeleteFailedException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.UpdateFailedException;
import com.jaw.framework.dao.BaseDao;
//AddHolidays DAO class
@Repository
public class AddlHolidaysDAO extends BaseDao implements IAddlHolidaysDAO {
	// Logging
		Logger logger = Logger.getLogger(AddlHolidaysDAO.class);

		@Override
		public void insertAddHolidaysRec(final AddlHolidays addHolidays)
				throws DuplicateEntryException {
			logger.debug("Inside insert method");
			logger.debug("AddHolidays object values :"
					+ addHolidays.toString());
			StringBuffer query = new StringBuffer();
			query = query.append("insert into ahol ( ")
					.append("DB_TS,")
					.append("INST_ID,")
					.append("BRANCH_ID,")
					.append("AC_TERM,")
					.append("AH_ID,")
					.append("STUDENTGRP_ID,")
					.append("HOL_FROM_DATE,")
					.append("HOL_TO_DATE,")
					.append("AH_RMKS,")
					.append("DEL_FLG,")
					.append("R_MOD_ID,")
					.append("R_MOD_TIME,")
					.append("R_CRE_ID,")
					.append("R_CRE_TIME)")

					.append(" values (?,?,?,?,?,?,?,?,?,?,?,now(),?,now())");

			try {
				getJdbcTemplate().update(query.toString(),
						new PreparedStatementSetter() {

							@Override
							public void setValues(PreparedStatement pss)
									throws SQLException {
								pss.setInt(1, addHolidays.getDbTs());
								pss.setString(2, addHolidays.getInstId());
								pss.setString(3, addHolidays.getBranchId());
								pss.setString(4, addHolidays.getAcTerm().trim());
								pss.setString(5, addHolidays.getAhId().trim());
								pss.setString(6, addHolidays.getStudentGrpId().trim());
								pss.setString(7, addHolidays.getHolFromDate().trim());
								pss.setString(8, addHolidays.getHolToDate().trim());
								pss.setString(9, addHolidays.getAhRemarks().trim());
								pss.setString(10, addHolidays.getDelFlag().trim());
								pss.setString(11, addHolidays.getrModId().trim());
								pss.setString(12, addHolidays.getrCreId().trim());

							}

						});
			} catch (org.springframework.dao.DuplicateKeyException e) {
				throw new DuplicateEntryException();
			}
			
		}

		@Override
		public void updateAddHolidaysRec(final AddlHolidays addHolidays,final AddlHolidaysKey addHolidayskey)
				throws UpdateFailedException {
			logger.debug("Inside update method");
			logger.debug("AddHolidays object values :"+ addHolidays.toString());	
			logger.debug("AddHolidaysKey object values :"+ addHolidayskey.toString());	
			StringBuffer sql = new StringBuffer();
			sql.append("update ahol set")
			        .append(" DB_TS= ?,")			       
					.append("STUDENTGRP_ID= ?,")
					.append("HOL_FROM_DATE= ?,")
					.append("HOL_TO_DATE= ?,")
					.append("AH_RMKS= ?,")
					.append("DEL_FLG= 'N',")
					.append("R_MOD_ID= ?,")
					.append(" R_MOD_TIME=now()")
					.append(" where")
					.append(" DB_TS= ?")
					.append(" and  INST_ID= ?")
					.append(" and  BRANCH_ID= ?")
					.append(" and  AC_TERM= ?")	
					.append(" and  AH_ID= ?")	
					.append(" and  DEL_FLG='N'");

			

			int updateRecs = getJdbcTemplate().update(sql.toString(),
					new PreparedStatementSetter() {

						@Override
						public void setValues(PreparedStatement ps)
								throws SQLException {
							ps.setInt(1, addHolidays.getDbTs() + 1);
							ps.setString(2, addHolidays.getStudentGrpId().trim());
							ps.setString(3, addHolidays.getHolFromDate().trim());
							ps.setString(4, addHolidays.getHolToDate().trim());
							ps.setString(5, addHolidays.getAhRemarks().trim());
							ps.setString(6, addHolidays.getrModId().trim());
							ps.setInt(7, addHolidayskey.getDbTs());
							ps.setString(8, addHolidayskey.getInstId().trim());
							ps.setString(9, addHolidayskey.getBranchId().trim());
							ps.setString(10, addHolidayskey.getAcTerm().trim());
							ps.setString(11, addHolidayskey.getAhId().trim());
							

						}

					});
			if (updateRecs == 0) {
				throw new UpdateFailedException();

			}
			
		}

		@Override
		public void deleteAddHolidaysRec(final AddlHolidays addHolidays,final AddlHolidaysKey addHolidayskey)
				throws DeleteFailedException {
			logger.debug("Inside delete method");
			logger.debug("Add Holidays object values :"+ addHolidays.toString());
			logger.debug("Add Holidays Key object values :"+ addHolidayskey.toString());
			
			StringBuffer sql = new StringBuffer();
			   sql.append("update ahol set")
			        .append(" DB_TS= ?,")
					.append("DEL_FLG= 'Y',")
					.append("R_MOD_ID= ?,")
					.append(" R_MOD_TIME=now()")
					.append(" where")
					.append(" DB_TS= ?")
					.append(" and INST_ID= ?")
					.append(" and  BRANCH_ID= ?")
					.append(" and  AC_TERM= ?")
					.append(" and  AH_ID= ?")
					.append(" and  DEL_FLG='N'");

			int deleteRecs = getJdbcTemplate().update(sql.toString(),
					new PreparedStatementSetter() {

						@Override
						public void setValues(PreparedStatement ps)
								throws SQLException {
							ps.setInt(1, addHolidays.getDbTs() + 1);
							ps.setString(2, addHolidays.getrModId().trim());
							ps.setInt(3, addHolidayskey.getDbTs());
							ps.setString(4, addHolidayskey.getInstId().trim());
							ps.setString(5, addHolidayskey.getBranchId().trim());
							ps.setString(6, addHolidayskey.getAcTerm().trim());
							ps.setString(7, addHolidayskey.getAhId().trim());
							

						}

					});
			if (deleteRecs == 0) {
				throw new DeleteFailedException();

			}
			
		}

		@Override
		public AddlHolidays selectAddHolidaysRec(final AddlHolidaysKey addHolidayskey)
				throws NoDataFoundException {
			logger.debug("Inside select method");
			logger.debug("Add Holidays Key object values :"+ addHolidayskey.toString());
			List<Object> data = new ArrayList<Object>();
			StringBuffer sql = new StringBuffer();
			    sql.append("select ")
			        .append("DB_TS,")
			        .append("INST_ID,")
					.append("BRANCH_ID,")
					.append("AC_TERM,")
					.append("AH_ID,")
					.append("STUDENTGRP_ID,")
					.append("HOL_FROM_DATE,")
					.append("HOL_TO_DATE,")
					.append("AH_RMKS,")
					.append("DEL_FLG,")
					.append("R_MOD_ID,")
					.append("R_MOD_TIME,")
					.append("R_CRE_ID,")
					.append("R_CRE_TIME ")
					.append(" from ahol ")
					.append(" where")				
					.append(" INST_ID= ?")
					.append(" and  BRANCH_ID= ?")
					.append(" and  AC_TERM= ?")		
					.append(" and  AH_ID= ?")		
					.append(" and  DEL_FLG=?");		
			    data.add(addHolidayskey.getInstId());
				data.add(addHolidayskey.getBranchId());
				data.add(addHolidayskey.getAcTerm());
				data.add(addHolidayskey.getAhId());
				data.add("N");
				
				if ((addHolidayskey.getDbTs() !=null)&&(addHolidayskey.getDbTs() !=0)) {
					sql.append(" and DB_TS=?  ");
					logger.debug("Db Ts Value :" + addHolidayskey.getDbTs());
					data.add(addHolidayskey.getDbTs());
				}
				Object[] array = data.toArray(new Object[data.size()]);
			    AddlHolidays selectedListAddHolidays = null;
			selectedListAddHolidays = (AddlHolidays) getJdbcTemplate()
					.query(sql.toString(), array, new ResultSetExtractor<AddlHolidays>() {

						@Override
						public AddlHolidays extractData(ResultSet rs)
								throws SQLException, DataAccessException {
							AddlHolidays addHolidays = null;
							if (rs.next()) {
								addHolidays = new AddlHolidays();

								addHolidays.setDbTs(rs.getInt("DB_TS"));
								addHolidays.setInstId(rs.getString("INST_ID"));
								addHolidays.setBranchId(rs.getString("BRANCH_ID"));
								addHolidays.setAcTerm(rs.getString("AC_TERM"));
								addHolidays.setAhId(rs.getString("AH_ID"));
								addHolidays.setStudentGrpId(rs.getString("STUDENTGRP_ID"));
								addHolidays.setHolFromDate(rs.getString("HOL_FROM_DATE"));
								addHolidays.setHolToDate(rs.getString("HOL_TO_DATE"));
								addHolidays.setAhRemarks(rs.getString("AH_RMKS"));
								addHolidays.setDelFlag((rs.getString("DEL_FLG")));
								addHolidays.setrModId(rs.getString("R_MOD_ID"));
								addHolidays.setrModTime(rs.getString("R_MOD_TIME"));
								addHolidays.setrCreId(rs.getString("R_CRE_ID"));
								addHolidays.setrCreTime(rs.getString("R_CRE_TIME"));
							}
							return addHolidays;
						}

					});

			if (selectedListAddHolidays == null) {
				throw new NoDataFoundException();
			}

			return selectedListAddHolidays;

		}

	
}
