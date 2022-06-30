package com.jaw.common.util.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import com.jaw.framework.dao.BaseDao;

@Repository
public class CommonCodeColumnDao extends BaseDao implements ICommonCodeColumnDao {
	Logger logger = Logger.getLogger(CommonCodeColumnDao.class);

	@Override
	public CommonCodeColumnRec getCommonCodeFromCCCL(
			final CommonCodeColumnBatchSearch comCodeColumnBatchSearch) {
		logger.info("In Dao Layer,Going to select CommonCodeColumnList");
		StringBuffer sql = new StringBuffer();
		sql.append("select ")
		.append("INST_ID, ")
		.append("BRANCH_ID, ")
		.append("TBL_NAME, ")
		.append("COLUMN_NAME, ")		
		.append("COCD_TYPE, ")
		.append("DEL_FLG, ")		  
		.append("R_MOD_ID, ")
		.append("R_MOD_TIME, ")
		.append("R_CRE_ID, ")
		.append("R_CRE_TIME ")		     
		.append(" from cccl ")
		.append("where ")
		.append("DEL_FLG=? ")
		.append(" and")
		.append(" INST_ID = ?")
		.append(" and")
		.append(" BRANCH_ID = ?")
		.append(" and")
		.append(" TBL_NAME = ?")
		.append(" and")
		.append(" COLUMN_NAME = ?");

		CommonCodeColumnRec selectedColAndCode = (CommonCodeColumnRec) getJdbcTemplate().query(
				sql.toString(), new PreparedStatementSetter() {

					@Override
					
					public void setValues(PreparedStatement pss)
							throws SQLException {

						pss.setString(1, "N");						
						pss.setString(2, comCodeColumnBatchSearch.getInstId());
						pss.setString(3, comCodeColumnBatchSearch.getBranchId());
						pss.setString(4, comCodeColumnBatchSearch.getTableName());
						pss.setString(5, comCodeColumnBatchSearch.getColumnName());

					}
				}, new ResultSetExtractor<CommonCodeColumnRec>(){

					@Override
					public CommonCodeColumnRec extractData(ResultSet rs)
							throws SQLException, DataAccessException {

						CommonCodeColumnRec comCodeColumn = new CommonCodeColumnRec();
						
						if(rs.next()){
						comCodeColumn.setInstId(rs.getString("INST_ID"));
						comCodeColumn.setBranchId(rs.getString("BRANCH_ID"));
						comCodeColumn.setTableName(rs.getString("TBL_NAME"));
						comCodeColumn.setColName(rs.getString("COLUMN_NAME"));
						comCodeColumn.setCodeType(rs.getString("COCD_TYPE"));
						comCodeColumn.setDelFlag(rs.getString("DEL_FLG"));
						comCodeColumn.setrModId(rs.getString("R_MOD_ID"));
						comCodeColumn.setrModTime(rs.getString("R_MOD_TIME"));
						comCodeColumn.setrCreId(rs.getString("R_CRE_ID"));
						comCodeColumn.setrCreTime(rs.getString("R_CRE_TIME"));	
						}
						return comCodeColumn;
					}
					
				});
	
		logger.info("In Dao Layer,CommonCodeColumnRec has been successfully retrieved");
		return selectedColAndCode;
	}

}
