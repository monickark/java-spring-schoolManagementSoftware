package com.jaw.login.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.framework.dao.BaseDao;



@Repository
public class DashboardDao extends BaseDao implements IDashboardDao {

	Logger logger = Logger.getLogger(DashboardDao.class);

	@Override
	public DashboardConsolidated retriveDashboardDetails(final DashboardConsolidatedKey dashboardConsolidatedKey) throws NoDataFoundException {

		logger.debug("retrive StudentInfo Details :"+dashboardConsolidatedKey);
	
				StringBuffer sql = new StringBuffer().append("select (select sum(fee_paid_amt) as data from sfpm where ")
				.append("inst_id=? and branch_id=? and del_flg=? and ")
				.append("fee_pmt_date between ? and ?)as fee_collection,")
				.append("(select count(student_admis_no) as student from stin where  inst_id=? and branch_id=? ")
				.append("and del_flg=? and ")
				.append("admis_date between (SELECT term_start_date FROM atdt where ac_term_sts='p') and ?) as new_admission,")
				.append("(SELECT count(r_cre_time) FROM useraudittable where inst_id=? and branch_id=? and ")
				.append("r_cre_time between ? and ?) as new_activity,")
				.append("(SELECT count(user_id) FROM request where  inst_id=? and branch_id=? and del_flg=? and ")
				.append("rqst_status='r' and r_cre_time between ? and ?) as new_request");			 ;
		
		logger.debug("select query :" + sql.toString());
		DashboardConsolidated dashboardConsolidated = null;
		dashboardConsolidated = getJdbcTemplate().query(sql.toString(),
				new PreparedStatementSetter() {

					@Override
					public void setValues(PreparedStatement pss)
							throws SQLException {
						pss.setString(1, dashboardConsolidatedKey.getInstId().trim());
						pss.setString(2, dashboardConsolidatedKey.getBranchId().trim());
						pss.setString(3, "N");
						pss.setString(4, dashboardConsolidatedKey.getFromDate());
						pss.setString(5, dashboardConsolidatedKey.getToDate());
						pss.setString(6, dashboardConsolidatedKey.getInstId().trim());
						pss.setString(7, dashboardConsolidatedKey.getBranchId().trim());
						pss.setString(8, "N");
						pss.setString(9, dashboardConsolidatedKey.getToDate());
						pss.setString(10, dashboardConsolidatedKey.getInstId().trim());
						pss.setString(11, dashboardConsolidatedKey.getBranchId().trim());
						pss.setString(12, dashboardConsolidatedKey.getFromDateTime());
						pss.setString(13, dashboardConsolidatedKey.getToDateTime());
						pss.setString(14, dashboardConsolidatedKey.getInstId().trim());
						pss.setString(15, dashboardConsolidatedKey.getBranchId().trim());
						pss.setString(16, "N");
						pss.setString(17, dashboardConsolidatedKey.getFromDateTime());
						pss.setString(18, dashboardConsolidatedKey.getToDateTime());
					}
				}, new StudentInfoResultSetExtractor());	
		if(dashboardConsolidated==null){
			throw new NoDataFoundException();
		}
		logger.debug("Student info Object :"+dashboardConsolidated);
		return dashboardConsolidated;
	}				

	
}


class StudentInfoResultSetExtractor implements ResultSetExtractor<DashboardConsolidated> {

	@Override
	public DashboardConsolidated extractData(ResultSet rs) throws SQLException,
			DataAccessException {
		DashboardConsolidated dashboardConsolidated = null;
		if (rs.next()) {
			dashboardConsolidated = new DashboardConsolidated();
			dashboardConsolidated.setFeeCollection(rs.getFloat("fee_collection"));
			dashboardConsolidated.setNewActivity(rs.getInt("new_Activity"));
			dashboardConsolidated.setNewAdmission(rs.getInt("new_admission"));
			dashboardConsolidated.setNewRequest(rs.getInt("new_request"));
				}
		return dashboardConsolidated;
	}

}
