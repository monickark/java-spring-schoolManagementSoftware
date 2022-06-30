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
public class TransportMasterListDAO  extends BaseDao implements
ITransportMasterListDAO {
Logger logger = Logger.getLogger(TransportMasterListDAO.class);

@Override
public List<TransportMasterList> getTransportMasterList(final TransportMasterKey transportMasterKey) throws NoDataFoundException {
	logger.debug("DAO :Inside TransportMaster List select method");
	StringBuffer sql = new StringBuffer();
    sql.append("select ")       
		.append("PICKUP_POINT,")
		.append("AMOUNT")
		.append(" from trsm ")
		.append(" where")
		.append("  INST_ID= ?")
		.append(" and  BRANCH_ID= ?")
		.append(" and  DEL_FLG=?");	

	List<TransportMasterList> transportMasterList = getJdbcTemplate()
			.query(sql.toString(),	new PreparedStatementSetter() {

				@Override
				public void setValues(PreparedStatement ps)
						throws SQLException {	
					ps.setString(1, transportMasterKey.getInstId());
					ps.setString(2, transportMasterKey.getBranchId());
					ps.setString(3, "N");
				}

			} 
					, new TransportMasterSelectRowMapper());
	if (transportMasterList.size() == 0) {
		logger.error("No  record found");
		throw new NoDataFoundException();
	}
	logger.debug("DAO : Transport Master List value"+transportMasterList.size() );
	return transportMasterList;
	}

	}

	class TransportMasterSelectRowMapper implements RowMapper<TransportMasterList> {

	@Override
	public TransportMasterList mapRow(ResultSet rs, int arg1) throws SQLException {
		TransportMasterList transportMasterList = new TransportMasterList();
		transportMasterList.setPickupPointName(rs.getString("PICKUP_POINT"));
		transportMasterList.setAmount(rs.getDouble("AMOUNT"));
	return transportMasterList;
	}
	}




