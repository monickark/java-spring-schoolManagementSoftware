package com.jaw.student.admission.dao;

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
public class CommunicationDetailsDAO extends BaseDao implements
		ICommunicationDetails {
	Logger logger = Logger.getLogger(CommunicationDetailsDAO.class);

	@Override
	public void insertCommunication(
			final CommunicationDetails communicationDetails)
			throws DuplicateEntryException {
		logger.debug("Inside insert method");

		StringBuffer sql = new StringBuffer("insert into comd(")
				.append("DB_TS, ")
				.append("INST_ID, ")
				.append("BRANCH_ID, ")
				.append("STUDENT_ADMIS_NO, ")
				.append("EMERG_CONTACT_NO, ")
				.append("MOBILE_NO_SMS, ")
				.append("RES_ADD1, ")
				.append("RES_ADD2, ")
				.append("RES_ADD3, ")
				.append("RES_CITY, ")
				.append("RES_STATE, ")
				.append("RES_PINCODE, ")
				.append("RES_TELE, ")
				.append("COMM_ADD1, ")
				.append("COMM_ADD2, ")
				.append("COMM_ADD3, ")
				.append("COMM_CITY, ")
				.append("COMM_STATE, ")
				.append("COMM_PINCODE, ")
				.append("COMM_TELE, ")
				.append("DEL_FLG, ")
				.append("R_MOD_ID, ")
				.append("R_MOD_TIME, ")
				.append("R_CRE_ID, ")
				.append("R_CRE_TIME")
				.append(") values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,now(),?,now())");

		logger.debug("insert query :" + sql.toString());

		try {
			getJdbcTemplate().update(sql.toString(),
					new PreparedStatementSetter() {

						@Override
						public void setValues(PreparedStatement ps)
								throws SQLException {

							ps.setInt(1, 1);
							ps.setString(2, communicationDetails.getInstId()
									.trim());
							ps.setString(3, communicationDetails.getBranchId()
									.trim());
							ps.setString(4, communicationDetails
									.getStudentAdmisNo().trim());
							ps.setString(5,
									communicationDetails.getEmergContactNo());
							ps.setString(6, communicationDetails.getMobileNoSms());
							ps.setString(7, communicationDetails
									.getResidenceAdd1().trim());
							ps.setString(8, communicationDetails
									.getResidenceAdd2().trim());
							ps.setString(9, communicationDetails
									.getResidenceAdd3().trim());
							ps.setString(10, communicationDetails.getRcity()
									.trim());
							ps.setString(11, communicationDetails.getRstate()
									.trim());
							ps.setString(12, communicationDetails.getRpincode());
							ps.setString(13,
									communicationDetails.getRresidenceTele());
							ps.setString(14, communicationDetails
									.getCommunicationAdd1().trim());
							ps.setString(15, communicationDetails
									.getCommunicationAdd2().trim());
							ps.setString(16, communicationDetails
									.getCommunicationAdd3().trim());
							ps.setString(17, communicationDetails.getCity()
									.trim());
							ps.setString(18, communicationDetails.getState()
									.trim());
							ps.setString(19, communicationDetails.getPincode());
							ps.setString(20,
									communicationDetails.getResidenceTele());
							ps.setString(21, "N");
							ps.setString(22, communicationDetails.getrModId()
									.trim());
							ps.setString(23, communicationDetails.getrCreId()
									.trim());
						}

					});
		} catch (org.springframework.dao.DuplicateKeyException e) {
			throw new DuplicateEntryException();
		} 
	}

	@Override
	public void updateCommDetails(
			final CommunicationDetails communicationDetails,
			final CommunicationDetailsKey communicationDetailsKey) throws UpdateFailedException {

		logger.debug("Inside update method");
	
		StringBuffer sql = new StringBuffer();
		sql.append("update comd set").append(" DB_TS=?")
				.append(", EMERG_CONTACT_NO=?").append(", MOBILE_NO_SMS=?")
				.append(", RES_ADD1=?").append(", RES_ADD2=?")
				.append(", RES_ADD3=?").append(", RES_CITY=?").append(", RES_STATE=?").append(", RES_PINCODE=?")
				.append(", RES_TELE=?").append(", COMM_ADD1=?")
				.append(", COMM_ADD2=?").append(", COMM_ADD3=?")
				.append(", COMM_CITY=?").append(", COMM_STATE=?").append(", COMM_PINCODE=?")
				.append(", COMM_TELE=?").append(", R_MOD_ID=?")
				.append(", R_MOD_TIME=now()").append(" where")
				.append(" INST_ID= ?").append(" and ")
				.append(" BRANCH_ID= ?").append(" and ")
				.append(" STUDENT_ADMIS_NO= ?").append(" and ").append(" DB_TS= ?")
				.append(" and ").append(" DEL_FLG='N'");
	
			int updateStatus =	getJdbcTemplate().update(sql.toString(),
					new PreparedStatementSetter() {

						@Override
						public void setValues(PreparedStatement ps)
								throws SQLException {

							ps.setInt(1, communicationDetails.getDbTs() + 1);
							ps.setString(2,
									communicationDetails.getEmergContactNo());
							ps.setString(3, communicationDetails.getMobileNoSms());
							ps.setString(4, communicationDetails
									.getResidenceAdd1().trim());
							ps.setString(5, communicationDetails
									.getResidenceAdd2().trim());
							ps.setString(6, communicationDetails
									.getResidenceAdd3().trim());
							ps.setString(7, communicationDetails.getRcity()
									.trim());
							ps.setString(8, communicationDetails.getRstate()
									.trim());
							ps.setString(9, communicationDetails.getRpincode());
							ps.setString(10,
									communicationDetails.getRresidenceTele());
							ps.setString(11, communicationDetails
									.getCommunicationAdd1().trim());
							ps.setString(12, communicationDetails
									.getCommunicationAdd2().trim());
							ps.setString(13, communicationDetails
									.getCommunicationAdd3().trim());
							ps.setString(14, communicationDetails.getCity()
									.trim());
							ps.setString(15, communicationDetails.getState()
									.trim());
							ps.setString(16, communicationDetails.getPincode());
							ps.setString(17,
									communicationDetails.getResidenceTele());
							ps.setString(18, communicationDetails.getrModId()
									.trim());
							ps.setString(19, communicationDetailsKey
									.getInstId().trim());
							ps.setString(20, communicationDetailsKey.getBranchId()
									.trim());
							ps.setString(21, communicationDetailsKey
									.getStudentAdmisNo().trim());
							ps.setInt(22, communicationDetails.getDbTs());
						}

					});
			if (updateStatus == 0) {
				throw new UpdateFailedException();

			}
		logger.debug("update query :" + sql.toString());

	}

	@Override
	public CommunicationDetails retriveCommunicationDetails(
			final CommunicationDetailsKey communicationDetailsKey) throws NoDataFoundException {

		logger.debug("retrive Communication Details");
		StringBuffer sql = new StringBuffer().append("select ")
				.append("DB_TS, ").append("INST_ID, ").append("BRANCH_ID, ")
				.append("STUDENT_ADMIS_NO, ").append("EMERG_CONTACT_NO, ")
				.append("MOBILE_NO_SMS, ").append("RES_ADD1, ")
				.append("RES_ADD2, ").append("RES_ADD3, ").append("RES_CITY, ").append("RES_STATE, ").append("RES_PINCODE, ")
				.append("RES_TELE, ")
				.append("COMM_ADD1, ").append("COMM_ADD2, ")
				.append("COMM_ADD3, ").append("COMM_CITY, ").append("COMM_STATE, ").append("COMM_PINCODE, ")
				.append("COMM_TELE, ").append("DEL_FLG, ").append("R_MOD_ID, ")
				.append("R_MOD_TIME, ").append("R_CRE_ID, ")
				.append("R_CRE_TIME").append(" from comd ")
				.append("where ").append("INST_ID=?").append(" and ")
				.append("BRANCH_ID=?").append(" and ")
				.append("STUDENT_ADMIS_NO=?").append(" and ")
				.append(" DEL_FLG=?");
		logger.debug("select query :" + sql.toString());
		CommunicationDetails commDetailsResult  = null;
		commDetailsResult = getJdbcTemplate().query(
				sql.toString(), new PreparedStatementSetter() {

					@Override
					public void setValues(PreparedStatement pss)
							throws SQLException {
						pss.setString(1, communicationDetailsKey.getInstId());
						pss.setString(2, communicationDetailsKey.getBranchId());
						pss.setString(3, communicationDetailsKey.getStudentAdmisNo());
						pss.setString(4, "N");
					}
				}, new CommDetailsResultSetExtractor());
		if(commDetailsResult==null){
			throw new NoDataFoundException();
		}
		return commDetailsResult;
	}
	
	

}

class CommDetailsResultSetExtractor implements
		ResultSetExtractor<CommunicationDetails> {
	@Override
	public CommunicationDetails extractData(ResultSet rs) throws SQLException,
			DataAccessException {
		CommunicationDetails communicationDetails = null;
		if (rs.next()) {
			communicationDetails = new CommunicationDetails();
			communicationDetails.setDbTs(rs.getInt("DB_TS"));
			communicationDetails.setInstId(rs.getString("INST_ID"));
			communicationDetails.setBranchId(rs.getString("BRANCH_ID"));
			communicationDetails.setStudentAdmisNo(rs
					.getString("STUDENT_ADMIS_NO"));
			communicationDetails.setEmergContactNo(rs
					.getString("EMERG_CONTACT_NO"));
			communicationDetails.setMobileNoSms(rs.getString("MOBILE_NO_SMS"));
			communicationDetails.setResidenceAdd1(rs.getString("RES_ADD1"));
			communicationDetails.setResidenceAdd2(rs.getString("RES_ADD2"));
			communicationDetails.setResidenceAdd3(rs.getString("RES_ADD3"));
			communicationDetails.setRcity(rs.getString("RES_CITY"));
			communicationDetails.setRstate(rs.getString("RES_STATE"));
			communicationDetails.setRpincode(rs.getString("RES_PINCODE"));
			communicationDetails.setRresidenceTele(rs.getString("RES_TELE"));
			communicationDetails
					.setCommunicationAdd1(rs.getString("COMM_ADD1"));
			communicationDetails
					.setCommunicationAdd2(rs.getString("COMM_ADD2"));
			communicationDetails
					.setCommunicationAdd3(rs.getString("COMM_ADD3"));
			communicationDetails.setCity(rs.getString("COMM_CITY"));
			communicationDetails.setState(rs.getString("COMM_STATE"));
			communicationDetails.setPincode(rs.getString("COMM_PINCODE"));
			communicationDetails.setResidenceTele(rs.getString("COMM_TELE"));
			communicationDetails.setDelFlg(rs.getString("DEL_FLG"));
			communicationDetails.setrModId(rs.getString("R_MOD_ID"));
			communicationDetails.setrModTime(rs.getString("R_MOD_TIME"));
			communicationDetails.setrCreId(rs.getString("R_CRE_ID"));
			communicationDetails.setrCreTime(rs.getString("R_CRE_TIME"));
		}
		return communicationDetails;
	}

}
