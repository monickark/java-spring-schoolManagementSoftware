package com.jaw.common.util.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.framework.dao.BaseDao;

@Repository
public class AttendanceUtilDao extends BaseDao implements IAttendanceUtilDao {
	Logger logger = Logger.getLogger(ComCodeColumnListDao.class);
	
	@Override
	public Date[] getACTermPeriod(
			final String instid, final String branchId, final String acTerm)
			throws NoDataFoundException {
		logger.info("In Dao Layer,Going to select CommonCodeColumnList");
		StringBuffer sql = new StringBuffer();
		sql.append("select  TERM_START_DATE,TERM_END_DATE FROM academictermdetails WHERE")
				.append(" inst_id=? and")
				.append(" BRANCH_ID=? and")
				.append(" DEL_FLG=? and ")
				.append(" AC_TERM=?; ");
		
		Date[] date = (Date[]) getJdbcTemplate().query(
				sql.toString(), new PreparedStatementSetter() {
					
					@Override
					public void setValues(PreparedStatement pss)
							throws SQLException {
						
						pss.setString(1, instid);
						pss.setString(2, branchId);
						pss.setString(3, "N");
						pss.setString(4, acTerm);
						
					}
				}, new SubListResultSetExtractor());
		if (date.length == 0) {
			throw new NoDataFoundException();
		}
		
		logger.info("In Dao Layer,CommonCodeColumnList has been successfully retrieved");
		return date;
	}
}

class SubListResultSetExtractor implements ResultSetExtractor<Date[]> {
	@Override
	public Date[] extractData(ResultSet rs) throws SQLException {
		
		Date[] map = new Date[2];
		while (rs.next()) {
			Date key = rs.getDate("TERM_START_DATE");
			Date value = rs.getDate("TERM_END_DATE");
			map[0] = key;
			map[1] = value;
		}
		return map;
	}
}