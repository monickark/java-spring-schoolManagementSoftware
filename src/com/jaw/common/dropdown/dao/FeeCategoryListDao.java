package com.jaw.common.dropdown.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.framework.dao.BaseDao;
import com.jaw.framework.sessCache.UserSessionDetails;
import com.mysql.jdbc.PreparedStatement;

@Repository
public class FeeCategoryListDao extends BaseDao implements IFeeCategoryListDao {

	// Logging
	Logger logger = Logger.getLogger(AcademicTermListDAO.class);

	
	

	@Override
	public Map<String, String> selectFeeCategoryList(final UserSessionDetails userSessionDetails)
			throws NoDataFoundException {
		logger.debug("Inside Academic Term Tag select method");

		StringBuffer sql = new StringBuffer();
		sql.append("select ")
		.append("CM_CODE,")
		.append("CODE_DESC ")
		.append(" from cocd ,fctl where  ")
		.append(" cocd.CM_CODE=fctl.fee_catgry ")
		.append(" and  cocd.INST_ID=fctl.INST_ID")
		.append(" and cocd.BRANCH_ID=fctl.BRANCH_ID")
		.append(" and cocd.DEL_FLG=fctl.DEL_FLG")
		.append(" and cocd.INST_ID=?")
		.append(" and cocd.BRANCH_ID=?")
		.append(" and fctl.DEL_FLG=?  ")
		.append(" order by CODE_DESC asc ");
		Map<String, String>   academicTermDetails = null;

		academicTermDetails = getJdbcTemplate().query(sql.toString(),
				new PreparedStatementSetter() {

									@Override
					public void setValues(java.sql.PreparedStatement pss)
							throws SQLException {
						pss.setString(1,userSessionDetails.getInstId() );
						pss.setString(2,userSessionDetails.getBranchId());
						pss.setString(3, "N");
						
					}

				}, new FeeCategoryExtractor());		
		if (academicTermDetails.size() == 0) {
			throw new NoDataFoundException();
		}
		return academicTermDetails;
	}
	
	
	
	
}


class FeeCategoryExtractor implements ResultSetExtractor<Map<String, String>> {
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
