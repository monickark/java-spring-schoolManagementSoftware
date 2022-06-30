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
//Academic Calendar DAO class
@Repository
public class AcademicCalendarDAO extends BaseDao implements IAcademicCalendarDAO {
	// Logging
	Logger logger = Logger.getLogger(AcademicCalendarDAO.class);

	// method to insert into AcademicCalendar Table
	@Override
	public void insertAcademicCalRec(final AcademicCalendar academicCalendar)
			throws DuplicateEntryException {
		logger.debug("Inside insert method");
		logger.debug("AcademicCalender object values :"
				+ academicCalendar.toString());
		StringBuffer query = new StringBuffer();
		query = query.append("insert into acal ( ")
				.append("DB_TS,")
				.append("INST_ID,")
				.append("BRANCH_ID,")
				.append("AC_ITEM_ID,")
				.append("AC_TERM,")
				.append("ITEM_START_DATE,")
				.append("ITEM_END_DATE,")
				.append("ITEM_TYPE,")
				.append("ITEM_DESC,")
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
							pss.setInt(1, academicCalendar.getDbTs());
							pss.setString(2, academicCalendar.getInstId());
							pss.setString(3, academicCalendar.getBranchId());
							pss.setString(4, academicCalendar.getAcItemId());
							pss.setString(5, academicCalendar.getAcTerm().trim());
							pss.setString(6, academicCalendar.getItemStartDate().trim());
							pss.setString(7, academicCalendar.getItemEndDate().trim());
							pss.setString(8, academicCalendar.getItemType().trim());
							pss.setString(9, academicCalendar.getItemDesc().trim());
							pss.setString(10, academicCalendar.getDelFlag().trim());
							pss.setString(11, academicCalendar.getrModId().trim());
							pss.setString(12, academicCalendar.getrCreId().trim());

						}

					});
		} catch (org.springframework.dao.DuplicateKeyException e) {
			logger.error("Duplicate Entry Exception Occured  "+e.getMessage());
			throw new DuplicateEntryException();
		}
	}
	// method to update  AcademicCalendar Table
	@Override
	public void updateAcademicCalRec(final AcademicCalendar academicCalendar,final AcademicCalendarKey academicCalendarKey)
			throws UpdateFailedException {
		logger.debug("Inside update method");
		logger.debug("AcademicCalender object values :"+ academicCalendar.toString());
		logger.debug("AcademicCalenderKey object values :"+ academicCalendarKey.toString());
		
		StringBuffer sql = new StringBuffer();
		sql.append("update acal set")
		        .append(" DB_TS= ?,")
		        .append("AC_TERM= ?,")
				.append("ITEM_START_DATE= ?,")
				.append("ITEM_END_DATE= ?,")
				.append("ITEM_TYPE= ?,")
				.append("ITEM_DESC= ?,")
				.append("DEL_FLG= 'N',")
				.append("R_MOD_ID= ?,")
				.append(" R_MOD_TIME=now()")
				.append(" where")
				.append(" DB_TS= ?")
				.append(" and  INST_ID= ?")
				.append(" and  BRANCH_ID= ?")
				.append(" and  AC_ITEM_ID= ?")				
				.append(" and  DEL_FLG='N'");

		

		int updateRecs = getJdbcTemplate().update(sql.toString(),
				new PreparedStatementSetter() {

					@Override
					public void setValues(PreparedStatement ps)
							throws SQLException {
						ps.setInt(1, academicCalendar.getDbTs() + 1);
						ps.setString(2, academicCalendar.getAcTerm().trim());
						ps.setString(3, academicCalendar.getItemStartDate().trim());
						ps.setString(4, academicCalendar.getItemEndDate().trim());
						ps.setString(5, academicCalendar.getItemType().trim());
						ps.setString(6, academicCalendar.getItemDesc().trim());
						ps.setString(7, academicCalendar.getrModId().trim());
						ps.setInt(8, academicCalendarKey.getDbTs());
						ps.setString(9, academicCalendarKey.getInstId().trim());
						ps.setString(10, academicCalendarKey.getBranchId().trim());
						ps.setString(11, academicCalendarKey.getAcItemId().trim());
						

					}

				});
		if (updateRecs == 0) {
			logger.error("Update Failed Exception Occured");
			throw new UpdateFailedException();

		}

	}
//method to delete from Academic Calendar
	@Override
	public void deleteAcademicCalRec(final AcademicCalendar academicCalendar,final AcademicCalendarKey academicCalendarKey) throws DeleteFailedException{
		logger.debug("Inside delete method");
		logger.debug("AcademicCalender object values :"+ academicCalendar.toString());
		logger.debug("AcademicCalenderKey object values :"+ academicCalendarKey.toString());
		
		StringBuffer sql = new StringBuffer();
		   sql.append("update acal set")
		        .append(" DB_TS= ?,")
				.append("DEL_FLG= 'Y',")
				.append("R_MOD_ID= ?,")
				.append(" R_MOD_TIME=now()")
				.append(" where")
				.append(" DB_TS= ?")
				.append(" and INST_ID= ?")
				.append(" and  BRANCH_ID= ?")
				.append(" and  AC_ITEM_ID= ?")
				.append(" and  DEL_FLG='N'");

		int deletedRecs = getJdbcTemplate().update(sql.toString(),
				new PreparedStatementSetter() {

					@Override
					public void setValues(PreparedStatement ps)
							throws SQLException {
						ps.setInt(1, academicCalendar.getDbTs());
						ps.setString(2, academicCalendar.getrModId().trim());
						ps.setInt(3, academicCalendarKey.getDbTs());
						ps.setString(4, academicCalendarKey.getInstId().trim());
						ps.setString(5, academicCalendarKey.getBranchId().trim());
						ps.setString(6, academicCalendarKey.getAcItemId().trim());
						

					}

				});
		if (deletedRecs == 0) {
			logger.error("Delete Failed Exception");
			throw new DeleteFailedException();

		}
	}
	// method to select from Academic Calendar Table
	@Override
	public AcademicCalendar selectAcademicCalRec(final AcademicCalendarKey academicCalendarKey)throws NoDataFoundException {
		logger.debug("Inside select method");
		logger.debug("AcademicCalenderKey object values :"+ academicCalendarKey.toString());
		List<Object> data = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		    sql.append("select ")
		        .append("DB_TS,")
		        .append("INST_ID,")
				.append("BRANCH_ID,")
				.append("AC_ITEM_ID,")
				.append("AC_TERM,")
				.append("ITEM_START_DATE,")
				.append("ITEM_END_DATE,")
				.append("ITEM_TYPE,")
				.append("ITEM_DESC,")
				.append("DEL_FLG,")
				.append("R_MOD_ID,")
				.append("R_MOD_TIME,")
				.append("R_CRE_ID,")
				.append("R_CRE_TIME ")
				.append(" from acal ")
				.append(" where")
				.append("  INST_ID= ?")
				.append(" and  BRANCH_ID= ?")
				.append(" and  AC_ITEM_ID= ?")				
				.append(" and  DEL_FLG=?");
		    data.add(academicCalendarKey.getInstId());
			data.add(academicCalendarKey.getBranchId());
			data.add(academicCalendarKey.getAcItemId());
			data.add("N");
			
			if ((academicCalendarKey.getDbTs() !=null)&&(academicCalendarKey.getDbTs() !=0)) {
				sql.append(" and DB_TS=?  ");
				logger.debug("Db Ts Value :" + academicCalendarKey.getDbTs());
				data.add(academicCalendarKey.getDbTs());
			}
		AcademicCalendar selectedListAcademicCalendar = null;
		Object[] array = data.toArray(new Object[data.size()]);
		logger.debug("Where clause conditions size :" + array.length);
		selectedListAcademicCalendar = (AcademicCalendar) getJdbcTemplate()
				.query(sql.toString(), array, new ResultSetExtractor<AcademicCalendar>() {

					@Override
					public AcademicCalendar extractData(ResultSet rs)
							throws SQLException, DataAccessException {
						AcademicCalendar academicCalendar = null;
						if (rs.next()) {
							academicCalendar = new AcademicCalendar();
							academicCalendar.setDbTs(rs.getInt("DB_TS"));
							academicCalendar.setInstId(rs.getString("INST_ID"));
							academicCalendar.setBranchId(rs.getString("BRANCH_ID"));
							academicCalendar.setAcItemId(rs.getString("AC_ITEM_ID"));
							academicCalendar.setAcTerm(rs.getString("AC_TERM"));
							academicCalendar.setItemStartDate(rs.getString("ITEM_START_DATE"));
							academicCalendar.setItemEndDate(rs.getString("ITEM_END_DATE"));
							academicCalendar.setItemType(rs.getString("ITEM_TYPE"));
							academicCalendar.setItemDesc(rs.getString("ITEM_DESC"));
							academicCalendar.setDelFlag((rs.getString("DEL_FLG")));
							academicCalendar.setrModId(rs.getString("R_MOD_ID"));
							academicCalendar.setrModTime(rs.getString("R_MOD_TIME"));
							academicCalendar.setrCreId(rs.getString("R_CRE_ID"));
							academicCalendar.setrCreTime(rs.getString("R_CRE_TIME"));
							
						}
						return academicCalendar;
					}

				});

		if (selectedListAcademicCalendar == null) {
			logger.error("No  record found");
			throw new NoDataFoundException();
		}

		return selectedListAcademicCalendar;
	}
	
	
	public AcademicCalendar selectAcademicCalDateForValidation(final String acTerm,final UserSessionDetails userSessionDetails )throws NoDataFoundException {
		logger.debug("Inside select method");
		logger.debug("AcademicTerm values :"+acTerm);
		StringBuffer sql = new StringBuffer();
		    sql.append("select ")		       
				.append("min(item_start_date) as ITEM_START_DATE,")
				.append("max(item_end_date) as ITEM_END_DATE ")
				.append(" from acal ")
				.append(" where")
				.append("  INST_ID= ?")
				.append(" and  BRANCH_ID= ?")
				.append(" and  AC_TERM= ?")				
				.append(" and  DEL_FLG=?");
		   
			
			
		AcademicCalendar selectedListAcademicCalendar = null;
		selectedListAcademicCalendar = (AcademicCalendar) getJdbcTemplate()
				.query(sql.toString(), new PreparedStatementSetter() {

					@Override
					public void setValues(PreparedStatement ps)
							throws SQLException {
						ps.setString(1, userSessionDetails.getInstId().trim());
						ps.setString(2, userSessionDetails.getBranchId().trim());
						ps.setString(3, acTerm);
						ps.setString(4, "N");
						

					}

				}, new ResultSetExtractor<AcademicCalendar>() {

					@Override
					public AcademicCalendar extractData(ResultSet rs)
							throws SQLException, DataAccessException {
						AcademicCalendar academicCalendar = null;
						if (rs.next()) {
							academicCalendar = new AcademicCalendar();
							
							academicCalendar.setItemStartDate(rs.getString("ITEM_START_DATE"));
							academicCalendar.setItemEndDate(rs.getString("ITEM_END_DATE"));
							
						}
						return academicCalendar;
					}

				});

		if (selectedListAcademicCalendar == null) {
			logger.error("No  record found");
			throw new NoDataFoundException();
		}

		return selectedListAcademicCalendar;
	}
	@Override
	public int checkAcademicCalRecExist(AcademicCalendar academicCalRecord) {
		 logger.debug("Inside Check Academic Calendar rec  method");

			logger.debug("Academic Calendar object values :"
					+ academicCalRecord.toString());
			StringBuffer sql = new StringBuffer();
			
		/*	select exists(select ac_item_id from acal where INST_ID='ASC' and BRANCH_ID='BR001'
					 and ITEM_START_DATE='2013-08-15' and ITEM_END_DATE='2013-08-17' and ITEM_TYPE='H' and*/
			sql.append("select exists(")
			.append("select ac_item_id ")
			.append("from acal")				
					.append(" where ")			
					.append(" INST_ID='" + academicCalRecord.getInstId()+ "'")
					.append(" and BRANCH_ID='" + academicCalRecord.getBranchId()+ "'")
					.append(" and  AC_TERM='" + academicCalRecord.getAcTerm()+ "'")				
					.append(" and  ITEM_START_DATE='" + academicCalRecord.getItemStartDate()+ "'")
					.append(" and  ITEM_END_DATE='" +academicCalRecord.getItemEndDate()+ "'")					
					.append(" and  ITEM_TYPE='" + academicCalRecord.getItemType()+ "'")				
					.append(" and DEL_FLG='N')");
					
					
							
			int rows = getJdbcTemplate().queryForInt(
					sql.toString());

			System.out.println("rows valueeeeeeeeeeeee"+rows);

			
			return rows; 
	 }
	
	
}
