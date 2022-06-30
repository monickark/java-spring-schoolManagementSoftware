package com.jaw.core.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import com.jaw.common.constants.CommonCodeConstant;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.framework.dao.BaseDao;

//StudentGrpMaster DAO class
@Repository
public class TermListBasedOnCourseIdDAO extends BaseDao implements ITermListBasedOnCourseIdDAO {
	// Logging
	Logger logger = Logger.getLogger(TermListBasedOnCourseIdDAO.class);
	
	@Override
	public Map<String, String> getTermListBasedOnCourseId(
			final String instId, final String branchId, final String courseId)
			throws NoDataFoundException {
		System.out.println("Course Id:"+courseId);
		StringBuffer sql = new StringBuffer();
		
		sql.append("select ")
				.append("distinct(term_id),")
				.append("CODE_DESC ")
				.append(" from cocd c,stgm a ")
				.append("where ")
				.append("a.inst_id = c.inst_id and ")
				.append("a.branch_id = c.branch_id and ")
				.append("a.del_flg = c.del_flg and ")
				.append("c.CM_CODE=a.term_id")
				.append(" and c.INST_ID=?")
				.append(" and c.BRANCH_ID=?")
				.append(" and a.DEL_FLG=?  ")
				.append(" and a.coursemaster_id=?  ")
				.append(" and c.code_type=?  ")
				.append(" order by CM_CODE ");
		System.out.println("Sql Query:"+sql.toString());
		Map<String, String> map = null;
		
		map = getJdbcTemplate().query(sql.toString(),
				new PreparedStatementSetter() {
					
					@Override
					public void setValues(PreparedStatement pss)
							throws SQLException {
						pss.setString(1, instId);
						pss.setString(2, branchId);
						pss.setString(3, "N");
						pss.setString(4, courseId);
						pss.setString(5, CommonCodeConstant.TERM);
						
					}
					
				}, new TermResultSetExtractor());
		logger.debug("Size of the map returned :" + map.size());
		if (map.size() == 0) {
			throw new NoDataFoundException();
		}
		return map;
	}
}

class TermResultSetExtractor implements ResultSetExtractor<Map<String, String>> {
	@Override
	public Map<String, String> extractData(ResultSet rs) throws SQLException {
		
		Map<String, String> map = new LinkedHashMap<String, String>();
		while (rs.next()) {
			String key = rs.getString("term_id");
			String value = rs.getString("CODE_DESC");
			map.put(key, value);
		}
		return map;
	}
}
