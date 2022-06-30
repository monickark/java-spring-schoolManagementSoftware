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

import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.UpdateFailedException;
import com.jaw.framework.dao.BaseDao;

@Repository
public class TransportDetailsDAO extends BaseDao implements ITransportDetails {
	Logger logger = Logger.getLogger(TransportDetailsDAO.class);

	@Override
	public void insertTransportsDetails(final TransportDetails transportDetails)
			throws DuplicateEntryException {
		

		StringBuffer sql = new StringBuffer("insert into transportdetails(")
				.append("DB_TS, ").append("INST_ID, ").append("BRANCH_ID, ")
				.append("STUDENT_ADMIS_NO, ").append("ACADEMIC_YEAR, ")
				.append("MODE_OF_TRANS, ").append("VEHICLE_NO, ")
				.append("PICKUP_POINT, ").append("DROP_POINT, ")
				.append("PICKUP_ASST_SALUT, ").append("PICKUP_ASST_NAME, ")
				.append("DEL_FLG, ").append("R_MOD_ID, ")
				.append("R_MOD_TIME, ").append("R_CRE_ID, ")
				.append("R_CRE_TIME")
				.append(") values(?,?,?,?,?,?,?,?,?,?,?,?,?,now(),?,now())");

		try {
			getJdbcTemplate().update(sql.toString(),
					new PreparedStatementSetter() {

						@Override
						public void setValues(PreparedStatement ps)
								throws SQLException {

							ps.setInt(1, 1);
							ps.setString(2, transportDetails.getInstId().trim());
							ps.setString(3, transportDetails.getBranchId()
									.trim());
							ps.setString(4, transportDetails
									.getStudentAdmisNo().trim());
							ps.setString(5, transportDetails.getAcademicYear()
									.trim());
							ps.setString(6, transportDetails
									.getModeOfTransport().trim());
							ps.setString(7, transportDetails.getVehicleNo()
									.trim());
							ps.setString(8, transportDetails.getPickupPoint()
									.trim());
							ps.setString(9, transportDetails.getDroppingPoint()
									.trim());
							ps.setString(10, transportDetails
									.getPickupAssSalut().trim());
							ps.setString(11, transportDetails
									.getPickupAssName().trim().toUpperCase());
							ps.setString(12, "N");
							ps.setString(13, transportDetails.getrModId()
									.trim());
							ps.setString(14, transportDetails.getrCreId()
									.trim());

						}

					});
		} catch (org.springframework.dao.DuplicateKeyException e) {
			throw new DuplicateEntryException();
		} 
	}

	@Override
	public void updateTransportDetails(final TransportDetails transportDetails,
			final TransportDetailsKey transportDetailsKey) throws UpdateFailedException {

		logger.debug("Inside update method");

		StringBuffer sql = new StringBuffer();
		sql.append("update transportdetails set ").append("DB_TS=?, ")
				.append("ACADEMIC_YEAR=?, ").append("MODE_OF_TRANS=?, ")
				.append("VEHICLE_NO=?, ").append("PICKUP_POINT=?, ")
				.append("DROP_POINT=?, ").append("PICKUP_ASST_SALUT=?, ")
				.append("PICKUP_ASST_NAME=?, ").append("R_MOD_ID=?, ")
				.append("R_MOD_TIME=now()").append(" where")
				.append(" INST_ID= ?")
				.append(" and ")
				.append(" BRANCH_ID= ?")
				.append(" and ")
				.append(" STUDENT_ADMIS_NO= ?")
				.append(" and ")
				.append(" ACADEMIC_YEAR= ?")
				.append(" and ")
				.append(" DB_TS= ?")
				.append(" and ")
				.append(" DEL_FLG='N'");
		
		int updateStatus =	getJdbcTemplate().update(sql.toString(),
					new PreparedStatementSetter() {

						@Override
						public void setValues(PreparedStatement ps)
								throws SQLException {

							ps.setInt(1, transportDetails.getDbTs() + 1);
							ps.setString(2, transportDetails.getAcademicYear()
									.trim());
							ps.setString(3, transportDetails
									.getModeOfTransport().trim());
							ps.setString(4, transportDetails.getVehicleNo()
									.trim());
							ps.setString(5, transportDetails.getPickupPoint()
									.trim());
							ps.setString(6, transportDetails.getDroppingPoint()
									.trim());
							ps.setString(7, transportDetails
									.getPickupAssSalut().trim());
							ps.setString(8, transportDetails.getPickupAssName()
									.trim());
							ps.setString(9, transportDetails.getrModId().trim());
							ps.setString(10, transportDetailsKey
									.getInstId().trim());
							ps.setString(11, transportDetailsKey
									.getBranchId().trim());
							ps.setString(12, transportDetailsKey
									.getAcademicYear().trim());
							ps.setString(13, transportDetailsKey.getStudentAdmisNo()
									.trim());
							ps.setInt(14, transportDetails.getDbTs());

						}

					});
			if (updateStatus == 0) {
				throw new UpdateFailedException();

			}

		logger.debug("update query :" + sql.toString());

	}

	@Override
	public TransportDetails retriveTransportDetails(final TransportDetailsKey transportDetailsKey) throws NoDataFoundException {

		logger.debug("retrive Transport Details");
		StringBuffer sql = new StringBuffer().append("select ")
				.append("DB_TS, ").append("INST_ID, ").append("BRANCH_ID, ")
				.append("STUDENT_ADMIS_NO, ").append("ACADEMIC_YEAR, ")
				.append("MODE_OF_TRANS, ").append("VEHICLE_NO, ")
				.append("PICKUP_POINT, ").append("DROP_POINT, ")
				.append("PICKUP_ASST_SALUT, ").append("PICKUP_ASST_NAME, ")
				.append("DEL_FLG, ").append("R_MOD_ID, ")
				.append("R_MOD_TIME, ").append("R_CRE_ID, ")
				.append("R_CRE_TIME").append(" from transportdetails ")
				.append(" where ")
				.append("INST_ID=?")
				.append(" and ")
				.append("BRANCH_ID=?")
				.append(" and ")
				.append("STUDENT_ADMIS_NO=?")
				.append(" and ")
				.append(" ACADEMIC_YEAR= ?")
				.append(" and ")
				.append(" DEL_FLG=?");
		logger.debug("select query :" + sql.toString());
		TransportDetails transportDetailsResult = null;
		transportDetailsResult = getJdbcTemplate().query(
				sql.toString(), new PreparedStatementSetter() {

					@Override
					public void setValues(PreparedStatement pss)
							throws SQLException {
						pss.setString(1, transportDetailsKey.getInstId().trim());
						pss.setString(2, transportDetailsKey.getBranchId().trim());
						pss.setString(3, transportDetailsKey.getStudentAdmisNo().trim());
						pss.setString(4, transportDetailsKey.getAcademicYear().trim());
						pss.setString(5, "N");
					}
				}, new TransportDetailsResultSetExtractor());
		if(transportDetailsResult == null){
			throw new NoDataFoundException();
		}
		return transportDetailsResult;
	}
	

}

class TransportDetailsResultSetExtractor implements
		ResultSetExtractor<TransportDetails> {
	@Override
	public TransportDetails extractData(ResultSet rs) throws SQLException,
			DataAccessException {
		TransportDetails transportDetails = null;
		if (rs.next()) {
			transportDetails = new TransportDetails();
			transportDetails.setDbTs(rs.getInt("DB_TS"));
			transportDetails.setInstId(rs.getString("INST_ID"));
			transportDetails.setBranchId(rs.getString("BRANCH_ID"));
			transportDetails
					.setStudentAdmisNo(rs.getString("STUDENT_ADMIS_NO"));
			transportDetails.setAcademicYear(rs.getString("ACADEMIC_YEAR"));
			transportDetails.setModeOfTransport(rs.getString("MODE_OF_TRANS"));
			transportDetails.setVehicleNo(rs.getString("VEHICLE_NO"));
			transportDetails.setPickupPoint(rs.getString("PICKUP_POINT"));
			transportDetails.setDroppingPoint(rs.getString("DROP_POINT"));
			transportDetails.setPickupAssSalut(rs
					.getString("PICKUP_ASST_SALUT"));
			transportDetails.setPickupAssName(rs.getString("PICKUP_ASST_NAME"));
			transportDetails.setDelFlg(rs.getString("DEL_FLG"));
			transportDetails.setrModId(rs.getString("R_MOD_ID"));
			transportDetails.setrModTime(rs.getString("R_MOD_TIME"));
			transportDetails.setrCreId(rs.getString("R_CRE_ID"));
			transportDetails.setrCreTime(rs.getString("R_CRE_TIME"));
		}
		return transportDetails;
	}

}
