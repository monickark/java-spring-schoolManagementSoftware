package com.jaw.fee.dao;

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
public class FeeMasterStatusListDAO extends BaseDao implements IFeeMasterStatusListDAO {
	// Logging
	Logger logger = Logger.getLogger(FeeMasterStatusListDAO.class);

	@Override
	public List<FeeMasterStatus> checkFeeStatus(FeeMasterStatusKey feeStatusKey)
			throws NoDataFoundException {
		logger.debug("Inside Check Fee Status  method");
		logger.debug("FeeStatusKey object values :"+ feeStatusKey.toString());
		StringBuffer sql = new StringBuffer();	
		List<String> data = new ArrayList<String>();
	

		sql.append("select FEE_STS ")
		.append("from fsts")				
				.append(" where ")			
				.append(" INST_ID=?")
				.append(" and BRANCH_ID=?")
				.append(" and  FEE_GENERATION_STS_ID=?")		
				.append(" and DEL_FLG='N'");
		data.add(feeStatusKey.getInstId());
		data.add(feeStatusKey.getBranchId());
		data.add(feeStatusKey.getFeeGenerationStatus());

		String[] array = data.toArray(new String[data.size()]);
		
		List<FeeMasterStatus> selectedListFeeMasterStatus=null;
		selectedListFeeMasterStatus = getJdbcTemplate()
				.query(sql.toString(), array,
						new FeeMasterStatusListRowMapper());
		
		 
		

		return selectedListFeeMasterStatus;
	

		
	}

	class FeeMasterStatusListRowMapper implements
	RowMapper<FeeMasterStatus> {

	@Override
	public FeeMasterStatus mapRow(ResultSet rs, int arg1)
		throws SQLException {
		FeeMasterStatus feeStatus = new FeeMasterStatus();
		feeStatus.setFeeStatus(rs.getString("FEE_STS"));
		return feeStatus;
	}
	}
}
