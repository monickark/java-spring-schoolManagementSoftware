package com.jaw.core.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.framework.dao.BaseDao;

@Repository
public class AcademicCalendarListDAO extends BaseDao implements
IAcademicCalendarListDAO {
Logger logger = Logger.getLogger(AcademicCalendarListDAO.class);

@Override
public List<AcademicCalendar> getAcademicCalendarList(final AcademicCalendarListKey academicCalendarListKey)
		throws NoDataFoundException {
	logger.debug("DAO :Inside AcademicCalendar List select method");
	List<Object> data = new ArrayList<Object>();
	StringBuffer sql = new StringBuffer();
	    sql.append("select ")
			.append("AC_ITEM_ID,")
			.append("AC_TERM,")
			.append("ITEM_START_DATE,")
			.append("ITEM_END_DATE,")
			.append("ITEM_TYPE,")
			.append("ITEM_DESC")
			.append(" from acal ")
			.append(" where")
			.append("  INST_ID= ?")
		.append(" and  BRANCH_ID= ?")
		.append(" and  DEL_FLG=?");
		data.add(academicCalendarListKey.getInstId());
		data.add(academicCalendarListKey.getBranchId());
		data.add("N");
		
		if ((academicCalendarListKey.getAcTerm() !=null)&&(!academicCalendarListKey.getAcTerm().equals(""))
				) {
			sql.append(" and AC_TERM=?  ");
			logger.debug("AC Term Value :" + academicCalendarListKey.getAcTerm());
			data.add(academicCalendarListKey.getAcTerm());
		}
		if ((academicCalendarListKey.getItemType() !=null)&&(!academicCalendarListKey.getItemType().equals(""))
				) {
			sql.append(" and ITEM_TYPE=?  ");
			logger.debug("Item type Value :" + academicCalendarListKey.getItemType());
			data.add(academicCalendarListKey.getItemType());
		}
		if ((academicCalendarListKey.getItemStartDate() !=null)&&(!academicCalendarListKey.getItemStartDate().equals("")
				&&(academicCalendarListKey.getItemEndDate()!=null)&&(!academicCalendarListKey.getItemEndDate().equals(""))))
				{			
			sql.append(" and item_start_date>=? and item_end_date<=?");
			logger.debug("item_start_date :" + academicCalendarListKey.getItemStartDate()+"   item_end_date :"+academicCalendarListKey.getItemEndDate());
			data.add(academicCalendarListKey.getItemStartDate());
			data.add(academicCalendarListKey.getItemEndDate());
		}
	Object[] array = data.toArray(new Object[data.size()]);
	logger.debug("Where clause conditions size :" + array.length);

	List<AcademicCalendar> selectedListAcaCal = getJdbcTemplate()
			.query(sql.toString(), array, new AcademicCalendarSelectRowMapper());
	if (selectedListAcaCal.size() == 0) {
		logger.error("No  record found");
		throw new NoDataFoundException();
	}
	logger.debug("DAO : AcademicCalendar List value"+selectedListAcaCal.size() );
	return selectedListAcaCal;
	}

@Override
public List<AcademicCalendar> getAcademicCalendarDetailsForDashBoard(
		AcademicCalendarListKey academicCalendarListKey)
		throws NoDataFoundException {
	
	//select ITEM_START_DATE, ITEM_END_DATE, ITEM_TYPE, ITEM_DESC from acal where AC_TERM='AT3' and INST_ID='ASC' and BRANCH_ID='BR001'
	//and DEL_FLG='N' and ITEM_START_DATE >='2014-02-01' and ITEM_START_DATE<='2014-02-28' order by ITEM_START_DATE, ITEM_END_DATE;
	
	logger.debug("DAO :Inside AcademicCalendar details select method");
	logger.debug("DAO :Inside AcademicCalendar list key values   :"+academicCalendarListKey.toString());
	

	
	List<Object> data = new ArrayList<Object>();
	StringBuffer sql = new StringBuffer();
	    sql.append("select ")
			/*.append("AC_ITEM_ID,")
			.append("AC_TERM,")*/
			.append("ITEM_START_DATE,")
			.append("ITEM_END_DATE,")
			.append("ITEM_TYPE,")
			.append("ITEM_DESC")
			.append(" from acal ")
			.append(" where")
			.append("  INST_ID= ?")
		.append(" and  BRANCH_ID= ?")
		.append(" and  DEL_FLG=?")
	    .append(" and  AC_TERM=?")
	    .append(" and ( (ITEM_START_DATE >=?")
	    .append(" and  ITEM_START_DATE<=?) ")
	    .append(" or  (ITEM_END_DATE >=?")
	    .append(" and  ITEM_END_DATE<=?))")
	    .append(" order by ITEM_START_DATE, ITEM_END_DATE");
		data.add(academicCalendarListKey.getInstId());
		data.add(academicCalendarListKey.getBranchId());
		data.add("N");
		data.add(academicCalendarListKey.getAcTerm());		
		data.add(academicCalendarListKey.getItemStartDate());		
		data.add(academicCalendarListKey.getItemEndDate());
		data.add(academicCalendarListKey.getItemStartDate());		
		data.add(academicCalendarListKey.getItemEndDate());
			
	Object[] array = data.toArray(new Object[data.size()]);
	logger.debug("Where clause conditions size :" + array.length);

	List<AcademicCalendar> selectedListAcaCal = getJdbcTemplate()
			.query(sql.toString(), array, new AcademicCalendarDetailsSelectRowMapper());
	if (selectedListAcaCal.size() == 0) {
		logger.error("No  record found");
		throw new NoDataFoundException();
	}
	logger.debug("DAO : AcademicCalendar List value"+selectedListAcaCal.size() );
	return selectedListAcaCal;
	}





	}

	class AcademicCalendarSelectRowMapper implements RowMapper<AcademicCalendar> {

	@Override
	public AcademicCalendar mapRow(ResultSet rs, int arg1) throws SQLException {
	AcademicCalendar acadeCals = new AcademicCalendar();	
	acadeCals.setAcItemId(rs.getString("AC_ITEM_ID"));
	acadeCals.setAcTerm(rs.getString("AC_TERM"));
	acadeCals.setItemStartDate(rs.getString("ITEM_START_DATE"));
	acadeCals.setItemEndDate(rs.getString("ITEM_END_DATE"));
	acadeCals.setItemType(rs.getString("ITEM_TYPE"));
	acadeCals.setItemDesc(rs.getString("ITEM_DESC"));

	return acadeCals;
	}
	}

	class AcademicCalendarDetailsSelectRowMapper implements RowMapper<AcademicCalendar> {

		@Override
		public AcademicCalendar mapRow(ResultSet rs, int arg1) throws SQLException {
		AcademicCalendar acadeCals = new AcademicCalendar();
		
		acadeCals.setItemStartDate(rs.getString("ITEM_START_DATE"));
		acadeCals.setItemEndDate(rs.getString("ITEM_END_DATE"));
		acadeCals.setItemType(rs.getString("ITEM_TYPE"));
		acadeCals.setItemDesc(rs.getString("ITEM_DESC"));

		return acadeCals;
		}
		}


