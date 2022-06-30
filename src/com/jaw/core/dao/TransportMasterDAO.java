package com.jaw.core.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import com.jaw.common.exceptions.DeleteFailedException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.UpdateFailedException;
import com.jaw.framework.dao.BaseDao;
//TransportMaster DAO class
@Repository
public class TransportMasterDAO extends BaseDao implements ITransportMasterDAO{
	// Logging
	Logger logger = Logger.getLogger(TransportMasterDAO.class);

	@Override
	public void insertTransportMasterRec(final TransportMaster TransportMaster)
			throws DuplicateEntryException {
		logger.debug("Inside insert method");
		logger.debug("Transport Master object values :"
				+ TransportMaster.toString());
		StringBuffer query = new StringBuffer();
		
		query = query.append("insert into trsm ( ")
				
				.append("DB_TS,")
				.append("INST_ID,")
				.append("BRANCH_ID,")
				.append("ACADEMIC_YEAR,")
				.append("PICKUP_POINT_ID,")
				.append("PICKUP_POINT,")
				.append("AMOUNT,")
				.append("DEL_FLG,")
				.append("R_MOD_ID,")
				.append("R_MOD_TIME,")
				.append("R_CRE_ID,")
				.append("R_CRE_TIME)")
				.append(" values (?,?,?,?,?,?,?,?,?,now(),?,now())");

		try {
			getJdbcTemplate().update(query.toString(),
					new PreparedStatementSetter() {

						@Override
						public void setValues(PreparedStatement pss)
								throws SQLException {
							pss.setInt(1, TransportMaster.getDbTs());
							pss.setString(2, TransportMaster.getInstId());
							pss.setString(3, TransportMaster.getBranchId());
							pss.setString(4, TransportMaster.getAcademicYear().trim());
							pss.setString(5, TransportMaster.getPickupPointId().trim());
							pss.setString(6, TransportMaster.getPickupPointName().trim());
							pss.setDouble(7, TransportMaster.getAmount());
							pss.setString(8, TransportMaster.getDelFlag().trim());
							pss.setString(9, TransportMaster.getrModId().trim());
							pss.setString(10, TransportMaster.getrCreId().trim());

						}

					});
		} catch (org.springframework.dao.DuplicateKeyException e) {
			logger.error("Duplicate Entry exception occured");
			throw new DuplicateEntryException();
		}
		
	}

	@Override
	public void updateTransportMasterRec(final TransportMaster transportMaster,
			final TransportMasterKey transportMasterkey) throws UpdateFailedException {
		logger.debug("Inside update method");
		logger.debug("Transport Master object values :"+ transportMaster.toString());
		logger.debug("Transport Master key Details object values :"+ transportMasterkey.toString());		
		StringBuffer sql = new StringBuffer();
			sql.append("update TRSM set")
		        .append(" DB_TS= ?,")		
				.append("PICKUP_POINT,")
				.append("AMOUNT,")
				.append("R_MOD_ID,")
				.append(" R_MOD_TIME=now()")
				.append(" where")
				.append(" DB_TS= ?")
				.append(" and  INST_ID= ?")
				.append(" and  BRANCH_ID= ?")
				.append(" and  academic_year= ?")
				.append(" PICKUP_POINT_id=?")
				.append(" and  DEL_FLG='N'");

		

		int updateRecs = getJdbcTemplate().update(sql.toString(),
				new PreparedStatementSetter() {

					@Override
					public void setValues(PreparedStatement ps)
							throws SQLException {
						ps.setInt(1, transportMaster.getDbTs() + 1);
						ps.setString(2, transportMaster.getPickupPointName().trim());
						ps.setDouble(3, transportMaster.getAmount());
						ps.setString(4, transportMaster.getrModId().trim());
						ps.setInt(5, transportMasterkey.getDbTs());
						ps.setString(6, transportMasterkey.getInstId().trim());
						ps.setString(7, transportMasterkey.getBranchId().trim());
						ps.setString(8, transportMasterkey.getAcademicYear().trim());
						ps.setString(9, transportMasterkey.getPickupPointId().trim());
						

					}

				});
		if (updateRecs == 0) {
			logger.error("Update failed exception occured");
			throw new UpdateFailedException();

		}
		
		
	}

	@Override
	public void deleteTransportMasterRec(final TransportMaster TransportMaster,
			final TransportMasterKey TransportMasterkey) throws DeleteFailedException {
		logger.debug("Inside delete method");
		logger.debug("Transport Master object values :"+ TransportMaster.toString());
		logger.debug("Transport Master Key object values :"+ TransportMasterkey.toString());
		
		StringBuffer sql = new StringBuffer();
		   sql.append("update TRSM set")
		        .append(" DB_TS= ?,")
				.append("DEL_FLG= ?,")
				.append("R_MOD_ID= ?,")
				.append(" R_MOD_TIME=now()")
				.append(" where")
				.append(" DB_TS= ?")
				.append(" and INST_ID= ?")
				.append(" and  BRANCH_ID= ?")
				.append(" and  ACADEMIC_YEAR= ?")
				.append(" and  PICKUP_POINT_ID= ?")	
				.append(" and  DEL_FLG='N'");

		int deletedRecs = getJdbcTemplate().update(sql.toString(),
				new PreparedStatementSetter() {

					@Override
					public void setValues(PreparedStatement ps)
							throws SQLException {
						ps.setInt(1, TransportMaster.getDbTs());
						ps.setString(2, TransportMaster.getDelFlag());
						ps.setString(3, TransportMaster.getrModId().trim());
						ps.setInt(4, TransportMasterkey.getDbTs());
						ps.setString(5, TransportMasterkey.getInstId().trim());
						ps.setString(6, TransportMasterkey.getBranchId().trim());
						ps.setString(7, TransportMasterkey.getAcademicYear().trim());
						ps.setString(8, TransportMasterkey.getPickupPointId().trim());
						

					}

				});
		if (deletedRecs == 0) {
			logger.error("Delete failed exception occured");
			throw new DeleteFailedException();

		}
		
		
	}

	@Override
	public TransportMaster selectTransportMasterRec(final TransportMasterKey TransportMasterKey)
			throws NoDataFoundException {
		logger.debug("Inside select method");
		logger.debug("Transport Master Key object values :"+ TransportMasterKey.toString());
		
		List<Object> data = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		    sql.append("select ")
		        .append(" DB_TS,")
				.append("INST_ID,")
				.append("BRANCH_ID,")
				.append("ACADEMIC_YEAR,")
				.append("PICKUP_POINT_ID,")
				.append("PICKUP_POINT,")
				.append("AMOUNT,")
				.append("DEL_FLG,")
				.append("R_MOD_ID,")
				.append("R_MOD_TIME,")
				.append("R_CRE_ID,")
				.append("R_CRE_TIME")
				.append(" from TRSM ")
				.append(" where")
				.append("   INST_ID= ?")
				.append(" and  BRANCH_ID= ?")
				.append(" and  ACADEMIC_YEAR= ?")
				.append(" and  PICKUP_POINT_ID= ?")
				.append(" and  DEL_FLG=?");	
		    data.add(TransportMasterKey.getInstId());
			data.add(TransportMasterKey.getBranchId());
			data.add(TransportMasterKey.getAcademicYear());
			data.add(TransportMasterKey.getPickupPointId());
			data.add("N");
			if ((TransportMasterKey.getDbTs()!=null)&&(TransportMasterKey.getDbTs() !=0)) {
				sql.append(" and DB_TS=?  ");
				logger.debug("Db Ts Value :" + TransportMasterKey.getDbTs());
				data.add(TransportMasterKey.getDbTs());
			}
			  Object[] array = data.toArray(new Object[data.size()]);
		    TransportMaster selectedTransportMaster = null;
		    selectedTransportMaster = (TransportMaster) getJdbcTemplate()
				.query(sql.toString(), array, new ResultSetExtractor<TransportMaster>() {

					@Override
					public TransportMaster extractData(ResultSet rs)
							throws SQLException, DataAccessException {
						TransportMaster transportMaster = null;
						if (rs.next()) {
							
							transportMaster = new TransportMaster();
							transportMaster.setDbTs(rs.getInt("DB_TS"));
							transportMaster.setInstId(rs.getString("INST_ID"));
							transportMaster.setBranchId(rs.getString("BRANCH_ID"));
							transportMaster.setAcademicYear(rs.getString("ACADEMIC_YEAR"));
							transportMaster.setPickupPointId(rs.getString("PICKUP_POINT_ID"));
							transportMaster.setPickupPointName(rs.getString("PICKUP_POINT"));
							transportMaster.setAmount(rs.getDouble("AMOUNT"));
							transportMaster.setDelFlag(rs.getString("DEL_FLG"));
							transportMaster.setrModId(rs.getString("R_MOD_ID"));
							transportMaster.setrModTime(rs.getString("R_MOD_TIME"));
							transportMaster.setrCreId(rs.getString("R_CRE_ID"));
							transportMaster.setrCreTime(rs.getString("R_CRE_TIME"));
						}
						return transportMaster;
					}

				});

		if (selectedTransportMaster == null) {
			logger.error("No data found exception occured");
			throw new NoDataFoundException();
		}

		return selectedTransportMaster;
	}


}
