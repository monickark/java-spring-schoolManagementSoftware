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

import com.jaw.common.constants.ApplicationConstant;
import com.jaw.common.exceptions.DeleteFailedException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.UpdateFailedException;
import com.jaw.framework.dao.BaseDao;

//Holiday Maintenance DAO class
@Repository
public class HolidayMaintenanceDAO extends BaseDao implements
		IHolidayMaintenanceDAO {
	// Logging
	Logger logger = Logger.getLogger(HolidayMaintenanceDAO.class);

	@Override
	public void insertHolidayMaintenanceRec(
			final HolidayMaintenance holidayMaintenance)
			throws DuplicateEntryException {
		logger.debug("Inside insert method");
		logger.debug("Holiday Maintenance object values :"
				+ holidayMaintenance.toString());
		StringBuffer query = new StringBuffer();
if(holidayMaintenance.getStudentGrpId()==""){
	holidayMaintenance.setStudentGrpId(ApplicationConstant.NOT_APPLICABLE);
}
		query = query.append("insert into holm ( ").append("DB_TS,")
				.append("INST_ID,").append("BRANCH_ID,").append("AC_TERM,")
				.append("HOL_DATE,").append("STUDENTGRP_ID,")
				.append("IS_WKLY_HOLIDAY,").append("DEL_FLG,")
				.append("R_MOD_ID,").append("R_MOD_TIME,").append("R_CRE_ID,")
				.append("R_CRE_TIME)")
				.append(" values (?,?,?,?,?,?,?,?,?,now(),?,now())");

		try {
			getJdbcTemplate().update(query.toString(),
					new PreparedStatementSetter() {

						@Override
						public void setValues(PreparedStatement pss)
								throws SQLException {
							pss.setInt(1, holidayMaintenance.getDbTs());
							pss.setString(2, holidayMaintenance.getInstId());
							pss.setString(3, holidayMaintenance.getBranchId());
							pss.setString(4, holidayMaintenance.getAcTerm()
									.trim());
							pss.setString(5, holidayMaintenance.getHolDate()
									.trim());
							pss.setString(6, holidayMaintenance
									.getStudentGrpId().trim());
							pss.setString(7, holidayMaintenance
									.getIsWklyHoliday().trim());
							pss.setString(8, holidayMaintenance.getDelFlag()
									.trim());
							pss.setString(9, holidayMaintenance.getrModId()
									.trim());
							pss.setString(10, holidayMaintenance.getrCreId()
									.trim());

						}

					});
		} catch (org.springframework.dao.DuplicateKeyException e) {
			logger.error("Duplicate Entry Exception Occured");
			throw new DuplicateEntryException();
		}

	}

	@Override
	public void updateHolidayMaintenanceRec(
			final HolidayMaintenance holidayMaintenance,
			final HolidayMaintenanceKey holidayMaintenancekey)
			throws UpdateFailedException {
		logger.debug("Inside update method");
		logger.debug("HolidayMaintenance object values :"
				+ holidayMaintenance.toString());
		logger.debug("HolidayMaintenanceKey Details object values :"
				+ holidayMaintenancekey.toString());
		StringBuffer sql = new StringBuffer();
		sql.append("update holm set").append(" DB_TS= ?,")
				.append("IS_WKLY_HOLIDAY=?,").append("DEL_FLG= 'N',")
				.append("R_MOD_ID= ?,").append(" R_MOD_TIME=now()")
				.append(" where").append(" DB_TS= ?")
				.append(" and  INST_ID= ?").append(" and  BRANCH_ID= ?")
				.append(" and  AC_TERM= ?").append(" and  HOL_DATE= ?")
				.append(" and  DEL_FLG='N'");

		int updateRecs = getJdbcTemplate().update(sql.toString(),
				new PreparedStatementSetter() {

					@Override
					public void setValues(PreparedStatement ps)
							throws SQLException {
						ps.setInt(1, holidayMaintenance.getDbTs() + 1);
						ps.setString(2, holidayMaintenance.getIsWklyHoliday()
								.trim());
						ps.setString(3, holidayMaintenance.getrModId().trim());

						ps.setInt(4, holidayMaintenancekey.getDbTs());
						ps.setString(5, holidayMaintenancekey.getInstId()
								.trim());
						ps.setString(6, holidayMaintenancekey.getBranchId()
								.trim());
						ps.setString(7, holidayMaintenancekey.getAcTerm()
								.trim());
						ps.setString(8, holidayMaintenancekey.getHolDate()
								.trim());

					}

				});
		if (updateRecs == 0) {
			logger.error("Update Failed Exception Occured");
			throw new UpdateFailedException();

		}

	}

	@Override
	public void deleteHolidayMaintenanceRec(
			final HolidayMaintenance holidayMaintenance,
			final HolidayMaintenanceKey holidayMaintenanceKey)
			throws DeleteFailedException {
		logger.debug("Inside delete method");
		logger.debug("HolidayMaintenancer object values :"
				+ holidayMaintenance.toString());
		logger.debug("HolidayMaintenanceKey object values :"
				+ holidayMaintenanceKey.toString());

		StringBuffer sql = new StringBuffer();
		sql.append("update holm set").append(" DB_TS= ?,")
				.append("DEL_FLG= 'Y',").append("R_MOD_ID= ?,")
				.append(" R_MOD_TIME=now()").append(" where")
				.append(" DB_TS= ?").append(" and INST_ID= ?")
				.append(" and  BRANCH_ID= ?").append(" and  AC_TERM= ?")
				.append(" and  HOL_DATE= ?").append(" and  DEL_FLG='N'");

		int deletedRecs = getJdbcTemplate().update(sql.toString(),
				new PreparedStatementSetter() {

					@Override
					public void setValues(PreparedStatement ps)
							throws SQLException {
						ps.setInt(1, holidayMaintenance.getDbTs() + 1);
						ps.setString(2, holidayMaintenance.getrModId().trim());
						ps.setInt(3, holidayMaintenanceKey.getDbTs());
						ps.setString(4, holidayMaintenanceKey.getInstId()
								.trim());
						ps.setString(5, holidayMaintenanceKey.getBranchId()
								.trim());
						ps.setString(6, holidayMaintenanceKey.getAcTerm()
								.trim());
						ps.setString(7, holidayMaintenanceKey.getHolDate()
								.trim());

					}

				});
		if (deletedRecs == 0) {
			logger.error("Delete Failed Exception Occured");
			throw new DeleteFailedException();

		}

	}

	@Override
	public HolidayMaintenance selectHolidayMaintenanceRec(
			final HolidayMaintenanceKey holidayMaintenanceKey)
			throws NoDataFoundException {
		logger.debug("Inside select method");
		logger.debug("HolidayMaintenanceKey object values :"
				+ holidayMaintenanceKey.toString());
		List<Object> data = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		sql.append("select ").append(" DB_TS,").append("INST_ID,")
				.append("BRANCH_ID,").append("AC_TERM,").append("HOL_DATE,")
				.append("STUDENTGRP_ID,").append("IS_WKLY_HOLIDAY,")
				.append("DEL_FLG,").append("R_MOD_ID,").append("R_MOD_TIME,")
				.append("R_CRE_ID,").append("R_CRE_TIME").append(" from holm ")
				.append(" where")//.append(" DB_TS= ?")
				.append("   INST_ID= ?").append(" and  BRANCH_ID= ?")
				.append(" and  AC_TERM= ?").append(" and  HOL_DATE= ?")
				.append(" and  DEL_FLG=?");
		data.add(holidayMaintenanceKey.getInstId());
		data.add(holidayMaintenanceKey.getBranchId());
		data.add(holidayMaintenanceKey.getAcTerm());
		data.add(holidayMaintenanceKey.getHolDate());
		data.add("N");
		if ((holidayMaintenanceKey.getDbTs() !=null)&&(holidayMaintenanceKey.getDbTs() !=0)) {
			sql.append(" and DB_TS=?  ");
			logger.debug("Db Ts Value :" + holidayMaintenanceKey.getDbTs());
			data.add(holidayMaintenanceKey.getDbTs());
		}
		HolidayMaintenance selectedHolidayMaintenance = null;
		Object[] array = data.toArray(new Object[data.size()]);
		logger.debug("Where clause conditions size :" + array.length);
		selectedHolidayMaintenance = (HolidayMaintenance) getJdbcTemplate()
				.query(sql.toString(), array, new ResultSetExtractor<HolidayMaintenance>() {

					@Override
					public HolidayMaintenance extractData(ResultSet rs)
							throws SQLException, DataAccessException {
						HolidayMaintenance holidayMaintenance = null;
						if (rs.next()) {
							holidayMaintenance = new HolidayMaintenance();
							holidayMaintenance.setDbTs(rs.getInt("DB_TS"));
							holidayMaintenance.setInstId(rs
									.getString("INST_ID"));
							holidayMaintenance.setBranchId(rs
									.getString("BRANCH_ID"));
							holidayMaintenance.setAcTerm(rs
									.getString("AC_TERM"));
							holidayMaintenance.setHolDate(rs
									.getString("HOL_DATE"));
							holidayMaintenance.setStudentGrpId(rs
									.getString("STUDENTGRP_ID"));

							holidayMaintenance.setIsWklyHoliday(rs
									.getString("IS_WKLY_HOLIDAY"));
							holidayMaintenance.setDelFlag(rs
									.getString("DEL_FLG"));
							holidayMaintenance.setrModId(rs
									.getString("R_MOD_ID"));
							holidayMaintenance.setrModTime(rs
									.getString("R_MOD_TIME"));
							holidayMaintenance.setrCreId(rs
									.getString("R_CRE_ID"));
							holidayMaintenance.setrCreTime(rs
									.getString("R_CRE_TIME"));
						}
						return holidayMaintenance;
					}

				});

		if (selectedHolidayMaintenance == null) {
			logger.error("No  record found");
			throw new NoDataFoundException();
		}

		return selectedHolidayMaintenance;
	}

	@Override
	public boolean checkHolRecsAvble(
			final HolidayMaintenanceKey holidayMaintenance) {

		logger.debug("Inside Check method");

		logger.debug("Holiday Maintenance object values :"
				+ holidayMaintenance.toString());
		boolean check = false;
		StringBuffer sql = new StringBuffer();
		sql.append("select ")
				.append("COUNT(*) ")
				.append(" from holm ")
				.append(" where")
				.append("  BRANCH_ID='" + holidayMaintenance.getBranchId()
						+ "'")
				.append(" and  AC_TERM='" + holidayMaintenance.getAcTerm()
						+ "'")
				.append(" and  INST_ID='" + holidayMaintenance.getInstId()
						+ "'").append(" and  DEL_FLG='N'");
		int vselectedHolidayMaintenance = getJdbcTemplate().queryForInt(
				sql.toString());

		if (vselectedHolidayMaintenance == 0) {
			check = true;

		} else {
			check = false;
		}

		return check;
	}

	@Override
	public int checkDateIsHoliday(HolidayMaintenanceKey holidayMaintenanceKey,String studentGrpId) {
		logger.debug("Inside Check Date is Holiday  method");

		logger.debug("Holiday Maintenance Key object values :"
				+ holidayMaintenanceKey.toString());
		StringBuffer sql = new StringBuffer();
		sql.append("select exists(")
		.append("select hol_date ")
		.append("from holm")				
				.append(" where")
					.append("  INST_ID='" + holidayMaintenanceKey.getInstId()
						+ "'")
				.append(" and BRANCH_ID='" + holidayMaintenanceKey.getBranchId()
						+ "'")
				.append(" and  AC_TERM='" + holidayMaintenanceKey.getAcTerm()
						+ "'")
				.append(" and  HOL_DATE='" + holidayMaintenanceKey.getHolDate()
						+ "'")
				.append(" and  (studentgrp_id = '"+ApplicationConstant.NOT_APPLICABLE+"' or studentgrp_id = '"+studentGrpId+"')")
						.append(" and  DEL_FLG='N')");
		int rows = getJdbcTemplate().queryForInt(
				sql.toString());

		System.out.println("rows valueeeeeeeeeeeee"+rows);

		
		return rows;
	}

	@Override
	public HolidayMaintenance selectHolidayMaintenanceRecForDelFlgY(
			HolidayMaintenanceKey holidayMaintenanceKey)
			 {
		logger.debug("Inside select method");
		logger.debug("HolidayMaintenanceKey object values :"
				+ holidayMaintenanceKey.toString());
		List<Object> data = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		sql.append("select ").append(" DB_TS,").append("INST_ID,")
				.append("BRANCH_ID,").append("AC_TERM,").append("HOL_DATE,")
				.append("STUDENTGRP_ID,").append("IS_WKLY_HOLIDAY,")
				.append("DEL_FLG,").append("R_MOD_ID,").append("R_MOD_TIME,")
				.append("R_CRE_ID,").append("R_CRE_TIME").append(" from holm ")
				.append(" where")//.append(" DB_TS= ?")
				.append("   INST_ID= ?").append(" and  BRANCH_ID= ?")
				.append(" and  AC_TERM= ?").append(" and  HOL_DATE= ?")
				.append(" and  DEL_FLG=?")
				.append(" and  STUDENTGRP_ID=?");
		data.add(holidayMaintenanceKey.getInstId());
		data.add(holidayMaintenanceKey.getBranchId());
		data.add(holidayMaintenanceKey.getAcTerm());
		data.add(holidayMaintenanceKey.getHolDate());
		data.add("Y");
		data.add(holidayMaintenanceKey.getStudentGrpId());
		if ((holidayMaintenanceKey.getDbTs() !=null)&&(holidayMaintenanceKey.getDbTs() !=0)) {
			sql.append(" and DB_TS=?  ");
			logger.debug("Db Ts Value :" + holidayMaintenanceKey.getDbTs());
			data.add(holidayMaintenanceKey.getDbTs());
		}
		HolidayMaintenance selectedHolidayMaintenance = null;
		Object[] array = data.toArray(new Object[data.size()]);
		logger.debug("Where clause conditions size :" + array.length);
		selectedHolidayMaintenance = (HolidayMaintenance) getJdbcTemplate()
				.query(sql.toString(), array, new ResultSetExtractor<HolidayMaintenance>() {

					@Override
					public HolidayMaintenance extractData(ResultSet rs)
							throws SQLException, DataAccessException {
						HolidayMaintenance holidayMaintenance = null;
						if (rs.next()) {
							holidayMaintenance = new HolidayMaintenance();
							holidayMaintenance.setDbTs(rs.getInt("DB_TS"));
							holidayMaintenance.setInstId(rs
									.getString("INST_ID"));
							holidayMaintenance.setBranchId(rs
									.getString("BRANCH_ID"));
							holidayMaintenance.setAcTerm(rs
									.getString("AC_TERM"));
							holidayMaintenance.setHolDate(rs
									.getString("HOL_DATE"));
							holidayMaintenance.setStudentGrpId(rs
									.getString("STUDENTGRP_ID"));

							holidayMaintenance.setIsWklyHoliday(rs
									.getString("IS_WKLY_HOLIDAY"));
							holidayMaintenance.setDelFlag(rs
									.getString("DEL_FLG"));
							holidayMaintenance.setrModId(rs
									.getString("R_MOD_ID"));
							holidayMaintenance.setrModTime(rs
									.getString("R_MOD_TIME"));
							holidayMaintenance.setrCreId(rs
									.getString("R_CRE_ID"));
							holidayMaintenance.setrCreTime(rs
									.getString("R_CRE_TIME"));
						}
						return holidayMaintenance;
					}

				});

		/*if (selectedHolidayMaintenance == null) {
			logger.error("No  record found");
			throw new NoDataFoundException();
		}*/

		return selectedHolidayMaintenance;
	}
//select exists(select HOL_DATE from holm where INST_ID='ASC' and BRANCH_ID='BR001' and AC_TERM='AT3' and HOL_DATE
	// in ('2014-01-01','2014-01-02', '2014-01-03','2014-01-03' ,'2014-01-10' ,'2014-01-06' ));

	@Override
	public int checkHolidayDateExist(
			HolidayMaintenanceKey holidayMaintenanceKey, List<String> holDates,String studentGrpId) {
		logger.debug("Inside Check Date is Holiday  method");

		logger.debug("Holiday Maintenance Key object values :"
				+ holidayMaintenanceKey.toString());
		StringBuffer sql = new StringBuffer();
		sql.append("select exists(")
		.append("select hol_date ")
		.append("from holm")				
				.append(" where")
					.append("  INST_ID='" + holidayMaintenanceKey.getInstId()
						+ "'")
				.append(" and BRANCH_ID='" + holidayMaintenanceKey.getBranchId()
						+ "'")
				.append(" and  AC_TERM='" + holidayMaintenanceKey.getAcTerm()
						+ "'")
				.append(" and  (studentgrp_id = '"+ApplicationConstant.NOT_APPLICABLE+"' or studentgrp_id = '"+studentGrpId+"')")
				.append(" and  HOL_DATE in ( ");
				for(int i=0;i<holDates.size();i++){
					if(i==0){
						sql.append("'" +holDates.get(i)+"'");
					}else{
						sql.append(", '" +holDates.get(i)+"'");
					}
					
				}
			
						sql.append(" ) and  DEL_FLG='N')");
						System.out.println("Query in hol check method "+sql.toString());
		int rows1 = getJdbcTemplate().queryForInt(
				sql.toString());

		System.out.println("rows valueeeeeeeeeeeee"+rows1);

		
		return rows1;
	}

	@Override
	public int checkHolidayDateExistForAcademicCalendarAddlHol(
			HolidayMaintenanceKey holidayMaintenanceKey, List<String> holDates) {
		logger.debug("Inside Check Date is Holiday  method");

		logger.debug("Holiday Maintenance Key object values :"
				+ holidayMaintenanceKey.toString());
		StringBuffer sql = new StringBuffer();
		sql.append("select exists(")
		.append("select hol_date ")
		.append("from holm")				
				.append(" where")
					.append("  INST_ID='" + holidayMaintenanceKey.getInstId()
						+ "'")
				.append(" and BRANCH_ID='" + holidayMaintenanceKey.getBranchId()
						+ "'")
				.append(" and  AC_TERM='" + holidayMaintenanceKey.getAcTerm()
						+ "'")
				.append(" and  studentgrp_id not in ('"+ApplicationConstant.NOT_APPLICABLE+"')")
				.append(" and  HOL_DATE in ( ");
				for(int i=0;i<holDates.size();i++){
					if(i==0){
						sql.append("'" +holDates.get(i)+"'");
					}else{
						sql.append(", '" +holDates.get(i)+"'");
					}
					
				}
			
						sql.append(" ) and  DEL_FLG='N')");
						System.out.println("Query in hol check method "+sql.toString());
		int rows1 = getJdbcTemplate().queryForInt(
				sql.toString());

		System.out.println("rows valueeeeeeeeeeeee"+rows1);

		
		return rows1;
	}

	@Override
	public int checkHolidayDateExistForAcademicCalendar(
			HolidayMaintenanceKey holidayMaintenanceKey, List<String> holDates) {
		logger.debug("Inside Check Date is Holiday  method");

		logger.debug("Holiday Maintenance Key object values :"
				+ holidayMaintenanceKey.toString());
		StringBuffer sql = new StringBuffer();
		sql.append("select exists(")
		.append("select hol_date ")
		.append("from holm")				
				.append(" where")
					.append("  INST_ID='" + holidayMaintenanceKey.getInstId()
						+ "'")
				.append(" and BRANCH_ID='" + holidayMaintenanceKey.getBranchId()
						+ "'")
				.append(" and  AC_TERM='" + holidayMaintenanceKey.getAcTerm()
						+ "'")
				.append(" and  studentgrp_id='"+ApplicationConstant.NOT_APPLICABLE+"'")
				.append(" and  HOL_DATE in ( ");
				for(int i=0;i<holDates.size();i++){
					if(i==0){
						sql.append("'" +holDates.get(i)+"'");
					}else{
						sql.append(", '" +holDates.get(i)+"'");
					}
					
				}
			
						sql.append(" ) and  DEL_FLG='N')");
						System.out.println("Query in hol check method "+sql.toString());
		int rows1 = getJdbcTemplate().queryForInt(
				sql.toString());

		System.out.println("rows valueeeeeeeeeeeee"+rows1);

		
		return rows1;
	}

	@Override
	public void removeHolidayAcademicCalendarAddlHol(
			final HolidayMaintenanceKey holidayMaintenanceKey, List<String> holDates) {
		logger.debug("Inside remove holiday date  method");

		logger.debug("Holiday Maintenance Key object values :"
				+ holidayMaintenanceKey.toString());
		StringBuffer sql = new StringBuffer();
		
		
		
		sql.append("delete  ")
		.append("from holm")				
				.append(" where")
					.append("  INST_ID=?")
				.append(" and BRANCH_ID=?")
				.append(" and  AC_TERM=?")
				.append(" and  studentgrp_id not in ('"+ApplicationConstant.NOT_APPLICABLE+"')")
				.append(" and  HOL_DATE in ( ");
				for(int i=0;i<holDates.size();i++){
					if(i==0){
						sql.append("'" +holDates.get(i)+"'");
					}else{
						sql.append(", '" +holDates.get(i)+"'");
					}
					
				}
			
						sql.append(" ) and  DEL_FLG=?");
						System.out.println("Query in hol delete method "+sql.toString());
						logger.debug("Delete query :" + sql.toString());
						
							getJdbcTemplate().update(sql.toString(),
									new PreparedStatementSetter() {

										@Override
										public void setValues(PreparedStatement ps)
												throws SQLException {											
												ps.setString(1, holidayMaintenanceKey.getInstId());
											ps.setString(2, holidayMaintenanceKey.getBranchId());
											ps.setString(3,holidayMaintenanceKey.getAcTerm());
											ps.setString(4, "N");

										}

									});

		
	}
}
