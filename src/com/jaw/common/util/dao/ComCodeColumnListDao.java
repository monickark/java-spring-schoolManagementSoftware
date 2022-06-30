package com.jaw.common.util.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.framework.dao.BaseDao;

@Repository
public class ComCodeColumnListDao extends BaseDao implements IComCodeColumnListDao {
	Logger logger = Logger.getLogger(ComCodeColumnListDao.class);
	
	@Override
	public List<ComCodeColumn> getCommonCodeColumnList(
			final ComCodeColumnSearch comCodeColumnKey) throws NoDataFoundException {
		logger.info("In Dao Layer,Going to select CommonCodeColumnList");
		System.out.println("key Data :" + comCodeColumnKey.toString());
		StringBuffer sql = new StringBuffer();
		sql.append("select ")
				.append("TBL_NAME,")
				.append("COLUMN_NAME,")
				.append("COCD_TYPE")
				.append(" from cccl ")
				.append("where ")
				.append("DEL_FLG=? ")
				.append(" and")
				.append(" INST_ID = ?")
				.append(" and")
				.append(" BRANCH_ID = ?")
				.append(" and")
				.append(" TBL_NAME = ?");
		
		List<ComCodeColumn> selectedColAndCode = getJdbcTemplate().query(
				sql.toString(), new PreparedStatementSetter() {
					
					@Override
					public void setValues(PreparedStatement pss)
							throws SQLException {
						
						pss.setString(1, "N");
						pss.setString(2, comCodeColumnKey.getInstId());
						pss.setString(3, comCodeColumnKey.getBranchId());
						pss.setString(4, comCodeColumnKey.getTableName());
						
					}
				}, new ComColCodeListSelectRowMapper());
		if (selectedColAndCode.size() == 0) {
			throw new NoDataFoundException();
		}
		
		logger.info("In Dao Layer,CommonCodeColumnList has been successfully retrieved");
		return selectedColAndCode;
	}
}

class ComColCodeListSelectRowMapper implements RowMapper<ComCodeColumn> {
	@Override
	public ComCodeColumn mapRow(ResultSet rs, int arg1) throws SQLException {
		
		ComCodeColumn comCodeColumn = new ComCodeColumn();
		comCodeColumn.setTableName(rs.getString("TBL_NAME"));
		comCodeColumn.setColName(rs.getString("COLUMN_NAME"));
		comCodeColumn.setCodeType(rs.getString("COCD_TYPE"));
		return comCodeColumn;
	}
}
