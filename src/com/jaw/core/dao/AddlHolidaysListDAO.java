package com.jaw.core.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import org.apache.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.framework.dao.BaseDao;
@Repository
public class AddlHolidaysListDAO extends BaseDao  implements IAddlHolidaysListDAO {
	
	Logger logger = Logger.getLogger(AddlHolidaysListDAO.class);

	@Override
	public List<AddlHolidays> getAddlHolidaysList(
			AddlHolidaysKey addlHolidaysKey) throws NoDataFoundException {

		logger.debug("DAO :Inside AddlHolidaysListDAO List select method");
		logger.debug("DAO : AdditionalHolidays List select Key   :"+  addlHolidaysKey);
		
		
		
		
		
		List<Object> data = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		sql.append("select ")
		.append(" ahol.DB_TS,")
		.append(" AC_TERM,")
		.append("AH_ID,")
		.append("ahol.STUDENTGRP_ID,")
		.append("HOL_FROM_DATE,")
		.append("HOL_TO_DATE,")
		.append("AH_RMKS ,")	
			.append("STUDENT_GRP ")	
		.append("from ahol,stgm")		
		.append(" where")
		.append("  ahol.INST_ID=stgm.INST_ID")
		.append("  and ahol.BRANCH_ID=stgm.BRANCH_ID")
		.append("  and ahol.DEL_FLG=stgm.DEL_FLG")
		.append("  and ahol.STUDENTGRP_ID=stgm.STUDENTGRP_ID")
		.append("  and ahol.DEL_FLG=?")		
		.append(" and  ahol.INST_ID= ?")
		.append(" and  ahol.BRANCH_ID= ?");	
		data.add("N");
		data.add(addlHolidaysKey.getInstId());
		data.add(addlHolidaysKey.getBranchId());
	
		if ((addlHolidaysKey.getAcTerm() != null)
				&& (addlHolidaysKey.getAcTerm() != "")) {
			sql.append(" and AC_TERM=?  ");
			data.add(addlHolidaysKey.getAcTerm());
			
		}
		
		if ((addlHolidaysKey.getStudentGrpId() != null)
				&& (addlHolidaysKey.getStudentGrpId() != "")) {
			sql.append(" and ahol.STUDENTGRP_ID=?  ");
			
			data.add(addlHolidaysKey.getStudentGrpId());
		}
		
		
		if ((addlHolidaysKey.getHolFromDate() != null)
				&& (addlHolidaysKey.getHolFromDate() != "")) {
			sql.append(" and HOL_FROM_DATE >=?  ");
			
			data.add(addlHolidaysKey.getHolFromDate());
		}
		
		if ((addlHolidaysKey.getHolToDate() != null)
				&& (addlHolidaysKey.getHolToDate() != "")) {
			sql.append(" and HOL_TO_DATE<=?  ");
			
			data.add(addlHolidaysKey.getHolToDate());
		}
		
		Object[] array = data.toArray(new Object[data.size()]);

		List<AddlHolidays> addlHolidaysList = getJdbcTemplate()
				.query(sql.toString(), array,
						new AddlHolidaysSelectRowMapper());
		
		if (addlHolidaysList.size() == 0) {
			logger.error("No  record found");
			throw new NoDataFoundException();
		}
		
		logger.debug("DAO : Additional Holidaysr List value"
				+ addlHolidaysList.size());
		
		return addlHolidaysList;
	}

	}


		class AddlHolidaysSelectRowMapper implements
			RowMapper<AddlHolidays> {
			
					@Override
					public AddlHolidays mapRow(ResultSet rs, int arg1)
						throws SQLException {
						AddlHolidays addlHolidays = new AddlHolidays();
						addlHolidays.setDbTs(rs.getInt("DB_TS"));
						addlHolidays.setAcTerm(rs.getString("AC_TERM"));
						addlHolidays.setAhId(rs.getString("AH_ID"));
						addlHolidays.setStudentGrpId(rs.getString("STUDENTGRP_ID"));
						addlHolidays.setHolFromDate(rs.getString("HOL_FROM_DATE"));
						addlHolidays.setHolToDate(rs.getString("HOL_TO_DATE"));
						addlHolidays.setAhRemarks(rs.getString("AH_RMKS"));
						addlHolidays.setStudentGrpName(rs.getString("STUDENT_GRP"));
						
					return addlHolidays;
			}
}

