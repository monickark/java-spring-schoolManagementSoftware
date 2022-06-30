package com.jaw.common.dropdown.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import com.jaw.common.constants.CommonCodeConstant;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.framework.dao.BaseDao;
import com.jaw.framework.sessCache.UserSessionDetails;
@Repository
public class StaffDepartmentListDAO extends BaseDao implements IStaffDepartmentListDAO {

	// Logging
	Logger logger = Logger.getLogger(StaffDepartmentListDAO.class);

	@Override
	public Map<String, String> getStaffDepartmentList(
			final UserSessionDetails userSessionDetails) throws NoDataFoundException {
		// select CM_CODE,CODE_DESC from cocd where INST_ID='ASC' and BRANCH_ID='BR001' and DEL_FLG='N' and code_type='DEPT' order by CODE_DESC;
		logger.debug("Inside Subject  select for Special Class method");
		StringBuffer sql = new StringBuffer();
		
			sql.append("select ").append(" CM_CODE,").append("CODE_DESC ")
					.append(" from cocd ").append(" where ")				
					.append("  INST_ID =?")
					.append(" and BRANCH_ID =?")
					.append(" and DEL_FLG = ?")
					.append(" and CODE_TYPE = ?")
					.append(" order by CODE_DESC asc");
		
		Map<String, String> departments = null;

		departments = getJdbcTemplate().query(sql.toString(),
				new PreparedStatementSetter() {

					@Override
					public void setValues(PreparedStatement pss)
							throws SQLException {
						pss.setString(1, userSessionDetails.getInstId());
						pss.setString(2, userSessionDetails.getBranchId());
						pss.setString(3, "N");
						pss.setString(4, CommonCodeConstant.DEPARTMENT);
						
					}

				}, new DepartmentListResultSetExtractor());
		if (departments.size() == 0) {
			throw new NoDataFoundException();

		}
		logger.debug("Map size returned :" + departments.size());
		return departments;
	
	}
	class DepartmentListResultSetExtractor implements
	ResultSetExtractor<Map<String, String>> {
@Override
public Map<String, String> extractData(ResultSet rs) throws SQLException {

	Map<String, String> map = new LinkedHashMap<String, String>();
	while (rs.next()) {
		String key = rs.getString("CM_CODE");
		String value = rs.getString("CODE_DESC");
		map.put(key, value);
	}
	return map;
}
}

}
