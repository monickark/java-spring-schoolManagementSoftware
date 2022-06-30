package com.jaw.core.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.jaw.common.constants.ApplicationConstant;
import com.jaw.common.exceptions.DeleteFailedException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.UpdateFailedException;
import com.jaw.common.exceptions.batch.DuplicateEntryForHolGenException;
import com.jaw.framework.dao.BaseDao;
//Holiday Maintenance List DAO class
@Repository
public class HolidayMaintenanceListDAO extends BaseDao implements IHolidayMaintenanceListDAO{
	// Logging
		Logger logger = Logger.getLogger(HolidayMaintenanceListDAO.class);
	@Override
	public void insertHolidayMaintenanceListRecs(
			final List<HolidayMaintenance> holidayMaintenanceList)
			throws DuplicateEntryForHolGenException {
		logger.debug("Inside Holiday List Insert Record");
		logger.debug("Holiday List Size   "+holidayMaintenanceList.size());
		StringBuffer query = new StringBuffer();
		
		query = query.append("insert into holm ( ")
				.append("DB_TS,")
				.append("INST_ID,")
				.append("BRANCH_ID,")
				.append("AC_TERM,")
				.append("HOL_DATE,")
				.append("STUDENTGRP_ID,")
				.append("IS_WKLY_HOLIDAY,")
				.append("DEL_FLG,")
				.append("R_MOD_ID,")
				.append("R_MOD_TIME,")
				.append("R_CRE_ID,")
				.append("R_CRE_TIME)")
				.append(" values (?,?,?,?,?,?,?,?,?,now(),?,now())");
		try {
		int[] a = getJdbcTemplate().batchUpdate(query.toString(),
				new BatchPreparedStatementSetter() {

					@Override
					public void setValues(PreparedStatement pss, int i)
							throws SQLException {

						HolidayMaintenance holidayMaintenance = holidayMaintenanceList
								.get(i);

						pss.setInt(1, holidayMaintenance.getDbTs());
						pss.setString(2, holidayMaintenance.getInstId());
						pss.setString(3, holidayMaintenance.getBranchId());
						pss.setString(4, holidayMaintenance.getAcTerm().trim());
						pss.setString(5, holidayMaintenance.getHolDate().trim());
						if( holidayMaintenance.getStudentGrpId()==""){
							 holidayMaintenance.setStudentGrpId(ApplicationConstant.NOT_APPLICABLE);
						}
						pss.setString(6, holidayMaintenance.getStudentGrpId().trim());
						pss.setString(7, holidayMaintenance.getIsWklyHoliday().trim());
						pss.setString(8, holidayMaintenance.getDelFlag().trim());
						pss.setString(9, holidayMaintenance.getrModId().trim());
						pss.setString(10, holidayMaintenance.getrCreId().trim());
					}

					@Override
					public int getBatchSize() {
						return holidayMaintenanceList.size();
					}
				}

		);
		} catch (org.springframework.dao.DuplicateKeyException e) {
			logger.error("Duplicate Entry Exception Occured  "+e.getMessage());
			throw new DuplicateEntryForHolGenException();
		}
		
	}
	@Override
	public void updateHolidayMaintenanceListRecs(
			final List<HolidayMaintenance> holidayMaintenanceList,
			final List<HolidayMaintenanceKey> holidayMaintenanceKeyList)
			throws   UpdateFailedException {
		logger.debug("Inside Holiday List update Record");
		logger.debug("Holiday List Size   "+holidayMaintenanceList.size());
		logger.debug("Holiday List Key Size   "+holidayMaintenanceKeyList.size());
		StringBuffer query = new StringBuffer();
		
		query.append("update holm set").append(" DB_TS= ?,")
		.append("DEL_FLG= 'Y',")
		.append("R_MOD_ID= ?,").append(" R_MOD_TIME=now()")
		.append(" where").append(" DB_TS= ?")
		.append(" and  INST_ID= ?").append(" and  BRANCH_ID= ?")
		.append(" and  AC_TERM= ?").append(" and  HOL_DATE= ?")
		.append(" and  DEL_FLG='N'")
		.append(" and  STUDENTGRP_ID=?");
		
		int[] a = getJdbcTemplate().batchUpdate(query.toString(),
				new BatchPreparedStatementSetter() {

					@Override
					public void setValues(PreparedStatement pss, int i)
							throws SQLException {
						HolidayMaintenance holidayMaintenance = holidayMaintenanceList
								.get(i);
						HolidayMaintenanceKey holidayMaintenanceKey = holidayMaintenanceKeyList
								.get(i);
						pss.setInt(1, holidayMaintenance.getDbTs());
						pss.setString(2, holidayMaintenance.getrModId().trim());

						pss.setInt(3, holidayMaintenanceKey.getDbTs());
						pss.setString(4, holidayMaintenanceKey.getInstId()
								.trim());
						pss.setString(5, holidayMaintenanceKey.getBranchId()
								.trim());
						pss.setString(6, holidayMaintenanceKey.getAcTerm()
								.trim());
						pss.setString(7, holidayMaintenanceKey.getHolDate()
								.trim());
						pss.setString(8, holidayMaintenanceKey.getStudentGrpId()
								.trim());
					}

					@Override
					public int getBatchSize() {
						return holidayMaintenanceList.size();
					}
				}

		);
		if(a.length==0) {
			logger.error("update failed Exception Occured  ");
			throw new UpdateFailedException();
		}
		
	}
	@Override
	public void updateHolidayMaintenanceListToDelFlgNRecs(
			final List<HolidayMaintenance> holidayMaintenanceList,
			final List<HolidayMaintenanceKey> holidayMaintenanceKeyList)
			throws UpdateFailedException {
		logger.debug("Inside Holiday List update Record");
		logger.debug("Holiday List Size   "+holidayMaintenanceList.size());
		logger.debug("Holiday List Key Size   "+holidayMaintenanceKeyList.size());
		StringBuffer query = new StringBuffer();
		
		query.append("update holm set").append(" DB_TS= ?,")
		.append("DEL_FLG= 'N',")
		.append("R_MOD_ID= ?,").append(" R_MOD_TIME=now()")
		.append(" where").append(" DB_TS= ?")
		.append(" and  INST_ID= ?").append(" and  BRANCH_ID= ?")
		.append(" and  AC_TERM= ?").append(" and  HOL_DATE= ?")
		.append(" and  DEL_FLG='Y'")
		.append(" and  STUDENTGRP_ID=?");
		int[] a = getJdbcTemplate().batchUpdate(query.toString(),
				new BatchPreparedStatementSetter() {

					@Override
					public void setValues(PreparedStatement pss, int i)
							throws SQLException {
						HolidayMaintenance holidayMaintenance = holidayMaintenanceList
								.get(i);
					
						HolidayMaintenanceKey holidayMaintenanceKey = holidayMaintenanceKeyList
								.get(i);
						pss.setInt(1, holidayMaintenance.getDbTs());
						pss.setString(2, holidayMaintenance.getrModId().trim());
						pss.setInt(3, holidayMaintenanceKey.getDbTs());
						pss.setString(4, holidayMaintenanceKey.getInstId()
								.trim());
						pss.setString(5, holidayMaintenanceKey.getBranchId()
								.trim());
						pss.setString(6, holidayMaintenanceKey.getAcTerm()
								.trim());
						pss.setString(7, holidayMaintenanceKey.getHolDate()
								.trim());
						pss.setString(8, holidayMaintenanceKey.getStudentGrpId()
								.trim());
					}

					@Override
					public int getBatchSize() {
						return holidayMaintenanceList.size();
					}
				}

		);
		if(a.length==0) {
			logger.error("update failed Exception Occured  ");
			throw new UpdateFailedException();
		}
		
	}
	@Override
	public List<AcademicCalendar> getHolidayDetailsForDashBoard(
			AcademicCalendarListKey academicCalendarListKey,String userMenuProfile,String studentGrpId)
			throws NoDataFoundException {
		
		//select ITEM_START_DATE, ITEM_END_DATE, ITEM_TYPE, ITEM_DESC from acal where AC_TERM='AT3' and INST_ID='ASC' and BRANCH_ID='BR001'
		//and DEL_FLG='N' and ITEM_START_DATE >='2014-02-01' and ITEM_START_DATE<='2014-02-28' order by ITEM_START_DATE, ITEM_END_DATE;
		
		logger.debug("DAO :Inside Holiday details select method");
		logger.debug("DAO :Inside Holiday list key values   :"+academicCalendarListKey.toString());
		logger.debug("DAO :User Menu Profile :"+userMenuProfile);
		logger.debug("DAO :Student group id  :"+studentGrpId);
	
		System.out.println("DAO :User Menu Profile :"+userMenuProfile);
		System.out.println("DAO :Student group id  :"+studentGrpId);
		
		
		List<Object> data = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		    sql.append("select ")
				/*.append("AC_ITEM_ID,")
				.append("AC_TERM,")*/
				.append("ITEM_START_DATE as START_DATE,")
				.append("ITEM_END_DATE as END_DATE,")
				.append("ITEM_DESC as DESCRIPTION ")
				.append(" from acal ")
				.append(" where")
				.append("  INST_ID= ?")
			.append(" and  BRANCH_ID= ?")
			.append(" and  DEL_FLG=?")
		    .append(" and  AC_TERM=?")
		   
		    .append(" and  ((ITEM_START_DATE >=?")
		    .append(" and  ITEM_START_DATE<=?) ")
		    .append(" or  (ITEM_END_DATE >=?")
		    .append(" and  ITEM_END_DATE<=?))")
		     .append(" and  ITEM_TYPE=?");
		     
		    if(((userMenuProfile.equals(ApplicationConstant.STUDENT))||(userMenuProfile.equals(ApplicationConstant.PARENT)))&&
		    		(studentGrpId!=null)&&(!studentGrpId.equals(""))){
		    sql .append(" UNION ")
		     .append("select ")
				/*.append("AC_ITEM_ID,")
				.append("AC_TERM,")*/
				.append("HOL_FROM_DATE as START_DATE,")
				.append("HOL_TO_DATE as END_DATE,")
				.append("AH_RMKS as DESCRIPTION ")
				.append(" from ahol ")
				.append(" where")
				.append("  INST_ID= ?")
			.append(" and  BRANCH_ID= ?")
			.append(" and  DEL_FLG=?")
		    .append(" and  AC_TERM=?")
		   
		    .append(" and  ((HOL_FROM_DATE >=?")
		    .append(" and  HOL_FROM_DATE<=?) ")
		    .append(" or  (HOL_TO_DATE >=?")
		    .append(" and  HOL_TO_DATE<=?))")
		    .append(" and  STUDENTGRP_ID=?");
		    
		    
		    }
		     sql.append(" order by START_DATE,END_DATE ");
		   
			data.add(academicCalendarListKey.getInstId());
			data.add(academicCalendarListKey.getBranchId());
			data.add("N");
			data.add(academicCalendarListKey.getAcTerm());		
			data.add(academicCalendarListKey.getItemStartDate());		
			data.add(academicCalendarListKey.getItemEndDate());
			data.add(academicCalendarListKey.getItemStartDate());		
			data.add(academicCalendarListKey.getItemEndDate());
			data.add(ApplicationConstant.ACT_TRM_ITEM_HOL);
			if(((userMenuProfile.equals(ApplicationConstant.STUDENT))||(userMenuProfile.equals(ApplicationConstant.PARENT)))&&
		    		(studentGrpId!=null)&&(!studentGrpId.equals(""))){
			data.add(academicCalendarListKey.getInstId());
			data.add(academicCalendarListKey.getBranchId());
			data.add("N");
			data.add(academicCalendarListKey.getAcTerm());		
			data.add(academicCalendarListKey.getItemStartDate());		
			data.add(academicCalendarListKey.getItemEndDate());
			data.add(academicCalendarListKey.getItemStartDate());		
			data.add(academicCalendarListKey.getItemEndDate());
			data.add(studentGrpId);
			
			
			}
		Object[] array = data.toArray(new Object[data.size()]);
		logger.debug("Where clause conditions size :" + array.length);

		List<AcademicCalendar> selectedListAcaCal = getJdbcTemplate()
				.query(sql.toString(), array, new HolidayDetailsSelectRowMapper());
		if (selectedListAcaCal.size() == 0) {
			logger.error("No  record found");
			throw new NoDataFoundException();
		}
		logger.debug("DAO : Holiday List value"+selectedListAcaCal.size() );
		return selectedListAcaCal;
		}

}

class HolidayDetailsSelectRowMapper implements RowMapper<AcademicCalendar> {

	@Override
	public AcademicCalendar mapRow(ResultSet rs, int arg1) throws SQLException {
	AcademicCalendar acadeCals = new AcademicCalendar();
	acadeCals.setItemStartDate(rs.getString("START_DATE"));
	acadeCals.setItemEndDate(rs.getString("END_DATE"));
	acadeCals.setItemDesc(rs.getString("DESCRIPTION"));

	return acadeCals;
	}
	}