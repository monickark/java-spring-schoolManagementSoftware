package com.jaw.staff.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.UpdateFailedException;
import com.jaw.framework.dao.BaseDao;

//Institute Master DAO class
@Repository
public class StaffMasterDAO extends BaseDao implements IStaffMasterDAO {
	
	// Logging
	Logger logger = Logger.getLogger(StaffMasterDAO.class);
	
	@Override
	public StaffMaster selectStaffRec(final String staffId,
			final String instId, final String branchId)
			throws NoDataFoundException {
		
		logger.debug("Inside select method");
		logger.debug(" selectStaffRec values Inst id :" + instId
				+ " branch id:" + branchId + " staff id :" + staffId);
		StringBuffer sql = new StringBuffer();
		
		sql.append("select ")
				.append("DB_TS,")
				.append("INST_ID,")
				.append("BRANCH_ID,")
				.append("STAFF_ID,")
				.append("STAFF_NAME,")
				.append("TTG_ID,")
				.append("DOB,")
				.append("BLOOD_GROUP,")
				.append("GENDER,")
				.append("MARITAL_STATUS,")
				.append("FH_NAME,")
				.append("RELIGION,")
				.append("CASTE_GROUP,")
				.append("CASTE_NAME,")
				.append("SUB_CASTE_NAME,")
				.append("PERMENANT_ADDRESS1,")
				.append("PERMENANT_ADDRESS2,")
				.append("PERMENANT_ADDRESS3,")
				.append("PERMENANT_CITY,")
				.append("PERMENANT_STATE,")
				.append("PERMENANT_PINCODE,")
				.append("CURRENT_ADDRESS1,")
				.append("CURRENT_ADDRESS2,")
				.append("CURRENT_ADDRESS3,")
				.append("CURRENT_CITY,")
				.append("CURRENT_STATE,")
				.append("CURRENT_PINCODE,")
				.append("LANDLINE,")
				.append("MOBILE,")
				.append("EMERGENCY_CONTACT,")
				.append("GENDER,")
				.append("EMAIL,")
				.append("PAN_CARD_NUM,")
				.append("DEL_FLG,")
				.append("R_MOD_ID,")
				.append("R_MOD_TIME,")
				.append("R_CRE_ID,")
				.append("R_CRE_TIME from stfm ")
				.append("where ")
				.append("DEL_FLG=? and ")
				.append("STAFF_ID=? and ")
				.append("INST_ID=? and ")
				.append("BRANCH_ID=? ");
		logger.debug("select query :" + sql.toString());
		
		StaffMaster staffMaster = null;
		
		staffMaster = getJdbcTemplate().query(sql.toString(),
				new PreparedStatementSetter() {
					
					@Override
					public void setValues(PreparedStatement pss)
							throws SQLException {
						pss.setString(1, "N");
						pss.setString(2, staffId);
						pss.setString(3, instId);
						pss.setString(4, branchId);
						
					}
					
				}, new ResultSetExtractor<StaffMaster>() {
					
					@Override
					public StaffMaster extractData(ResultSet rs)
							throws SQLException, DataAccessException {
						StaffMaster staff = null;
						if (rs.next()) {
							staff = new StaffMaster();
							staff.setDbTs(rs.getInt("DB_TS"));
							staff.setInstId(rs.getString("INST_ID"));
							staff.setBranchId(rs.getString("BRANCH_ID"));
							staff.setStaffId(rs.getString("STAFF_ID"));
							staff.setStaffName(rs.getString("STAFF_NAME"));
							staff.setTtg(rs.getString("TTG_ID"));
							staff.setDob(rs.getString("DOB"));
							staff.setBloodGroup(rs.getString("BLOOD_GROUP"));
							staff.setGender(rs.getString("GENDER"));
							staff.setMaritalStatus(rs
									.getString("MARITAL_STATUS"));
							staff.setFhName(rs.getString("FH_NAME"));
							staff.setReligion(rs.getString("RELIGION"));
							staff.setCasteGroup(rs.getString("CASTE_GROUP"));
							staff.setCasteName(rs.getString("CASTE_NAME"));
							staff.setSubCasteName(rs
									.getString("SUB_CASTE_NAME"));
							staff.setPermenantAddress1(rs
									.getString("PERMENANT_ADDRESS1"));
							staff.setPermenantAddress2(rs
									.getString("PERMENANT_ADDRESS2"));
							staff.setPermenantAddress3(rs
									.getString("PERMENANT_ADDRESS3"));
							staff.setPermenantCity(rs
									.getString("PERMENANT_CITY"));
							staff.setPermenantState(rs
									.getString("PERMENANT_STATE"));
							staff.setPermenantPincode(rs
									.getString("PERMENANT_PINCODE"));
							staff.setCommunicationAddress1(rs
									.getString("CURRENT_ADDRESS1"));
							staff.setCommunicationAddress2(rs
									.getString("CURRENT_ADDRESS2"));
							staff.setCommunicationAddress3(rs
									.getString("CURRENT_ADDRESS3"));
							staff.setCommunicationCity(rs
									.getString("CURRENT_CITY"));
							staff.setCommunicationState(rs
									.getString("CURRENT_STATE"));
							staff.setCommunicationPincode(rs
									.getString("CURRENT_PINCODE"));
							staff.setLandline(rs.getString("LANDLINE"));
							staff.setMobile(rs.getString("MOBILE"));
							staff.setEmergency(rs
									.getString("EMERGENCY_CONTACT"));
							staff.setEmail(rs.getString("EMAIL"));
							staff.setPanCardNo(rs.getString("PAN_CARD_NUM"));
							staff.setDelFlg(rs.getString("DEL_FLG"));
							staff.setrModId(rs.getString("R_MOD_ID"));
							staff.setrModTime(rs.getString("R_MOD_TIME"));
							staff.setrCreId(rs.getString("R_CRE_ID"));
							staff.setrCreTime(rs.getString("R_CRE_TIME"));
						}
						return staff;
					}
					
				});
		if (staffMaster == null) {
			throw new NoDataFoundException();
		}
		return staffMaster;
	}
	
	@Override
	public void insertStaffMaster(final StaffMaster staffMaster)
			throws DuplicateEntryException {
		
		logger.debug("Inside insert method");
		
		StringBuffer query = new StringBuffer();
		query.append("insert into stfm  (")
				
				.append("DB_TS,")
				.append("INST_ID,")
				.append("BRANCH_ID,")
				
				.append("STAFF_ID,")
				.append("STAFF_NAME,")
				.append("TTG_ID,")
				
				.append("DOB,")
				.append("BLOOD_GROUP,")
				.append("GENDER,")
				.append("MARITAL_STATUS,")
				
				.append("FH_NAME,")
				.append("RELIGION,")
				.append("CASTE_GROUP,")
				
				.append("CASTE_NAME,")
				.append("SUB_CASTE_NAME,")
				.append("PERMENANT_ADDRESS1,")
				
				.append("PERMENANT_ADDRESS2,")
				.append("PERMENANT_ADDRESS3,")
				.append("PERMENANT_CITY,")
				
				.append("PERMENANT_STATE,")
				.append("PERMENANT_PINCODE,")
				.append("CURRENT_ADDRESS1,")
				
				.append("CURRENT_ADDRESS2,")
				.append("CURRENT_ADDRESS3,")
				.append("CURRENT_CITY,")
				
				.append("CURRENT_STATE,")
				.append("CURRENT_PINCODE,")
				.append("LANDLINE,")
				
				.append("MOBILE,")
				.append("EMERGENCY_CONTACT,")
				.append("EMAIL,")
				.append("PAN_CARD_NUM,")
				
				.append("DEL_FLG,")
				.append("R_MOD_ID,")
				.append("R_MOD_TIME,")
				
				.append("R_CRE_ID,")
				.append("R_CRE_TIME )")
				.append(" values (?,?,?, ?,?,?, ?,?,?, ?,?,?, ?,?,?, ?,?,?, ?,?,?, ?,?,?,?, ?,?,?,?, ?,?,?,  ?,?,now(), ?,now())");
		logger.debug("insert query :" + query.toString());
		
		try {
			getJdbcTemplate().update(query.toString(),
					new PreparedStatementSetter() {
						@Override
						public void setValues(PreparedStatement ps)
								throws SQLException {
							
							ps.setInt(1, staffMaster.getDbTs());
							ps.setString(2, staffMaster.getInstId().trim());
							ps.setString(3, staffMaster.getBranchId().trim());
							ps.setString(4, staffMaster.getStaffId().trim());
							ps.setString(5, staffMaster.getStaffName().trim()
									.toUpperCase());
							ps.setString(6, staffMaster.getTtg().trim());
							ps.setString(7, staffMaster.getDob().trim());
							ps.setString(8, staffMaster.getBloodGroup().trim());
							ps.setString(9, staffMaster.getGender().trim());
							ps.setString(10, staffMaster.getMaritalStatus()
									.trim());
							ps.setString(11, staffMaster.getFhName().trim());
							ps.setString(12, staffMaster.getReligion().trim());
							ps.setString(13, staffMaster.getCasteGroup().trim());
							
							ps.setString(14, staffMaster.getCasteName().trim());
							ps.setString(15, staffMaster.getSubCasteName()
									.trim());
							ps.setString(16, staffMaster.getPermenantAddress1()
									.trim());
							ps.setString(17, staffMaster.getPermenantAddress2()
									.trim());
							ps.setString(18, staffMaster.getPermenantAddress3()
									.trim());
							ps.setString(19, staffMaster.getPermenantCity()
									.trim());
							ps.setString(20, staffMaster.getPermenantState()
									.trim());
							ps.setString(21, staffMaster.getPermenantPincode()
									.trim());
							
							ps.setString(22, staffMaster
									.getCommunicationAddress1().trim());
							ps.setString(23, staffMaster
									.getCommunicationAddress2().trim());
							ps.setString(24, staffMaster
									.getCommunicationAddress3().trim());
							ps.setString(25, staffMaster.getCommunicationCity()
									.trim());
							ps.setString(26, staffMaster
									.getCommunicationState().trim());
							ps.setString(27, staffMaster
									.getCommunicationPincode().trim());
							ps.setString(28, staffMaster.getLandline().trim());
							ps.setString(29, staffMaster.getMobile().trim());
							ps.setString(30, staffMaster.getEmergency().trim());
							ps.setString(31, staffMaster.getEmail().trim());
							ps.setString(32, staffMaster.getPanCardNo().trim());
							ps.setString(33, staffMaster.getDelFlg().trim());
							ps.setString(34, staffMaster.getrModId().trim());
							ps.setString(35, staffMaster.getrCreId().trim());
							
						}
						
					});
		}
		catch (org.springframework.dao.DuplicateKeyException e) {
			throw new DuplicateEntryException();
		}
		
	}
	
	@Override
	public void updateStaffMaster(final StaffMaster staffMaster)
			throws UpdateFailedException {
		
		logger.debug("Inside insert method");
		logger.debug("Inst id :" + staffMaster.getInstId() + " branch id:"
				+ staffMaster.getBranchId() + " dbts :" + staffMaster.getDbTs()
				+ " staff id :" + staffMaster.getStaffId());
		StringBuffer query = new StringBuffer();
		query.append("update stfm set ")
				
				.append("DB_TS=?,").append("INST_ID=?,").append("BRANCH_ID=?,")
				
				.append("STAFF_ID=?,")
				.append("STAFF_NAME=?,")
				.append("TTG_ID=?,")
				
				.append("DOB=?,")
				.append("GENDER=?,")
				.append("MARITAL_STATUS=?,")
				
				.append("FH_NAME=?,")
				.append("RELIGION=?,")
				.append("CASTE_GROUP=?,")
				
				.append("CASTE_NAME=?,")
				.append("SUB_CASTE_NAME=?,")
				.append("PERMENANT_ADDRESS1=?,")
				
				.append("PERMENANT_ADDRESS2=?,")
				.append("PERMENANT_ADDRESS3=?,")
				.append("PERMENANT_CITY=?,")
				
				.append("PERMENANT_STATE=?,")
				.append("PERMENANT_PINCODE=?,")
				.append("CURRENT_ADDRESS1=?,")
				
				.append("CURRENT_ADDRESS2=?,")
				.append("CURRENT_ADDRESS3=?,")
				.append("CURRENT_CITY=?,")
				
				.append("CURRENT_STATE=?,")
				.append("CURRENT_PINCODE=?,")
				.append("LANDLINE=?,")
				
				.append("MOBILE=?,")
				.append("EMERGENCY_CONTACT=?,")
				.append("EMAIL=?,")
				.append("PAN_CARD_NUM=?,")
				
				.append("DEL_FLG=?,").append("R_MOD_ID=?,")
				.append("BLOOD_GROUP=?,").append("R_MOD_TIME=now() ")
				.append(" where ").append("INST_ID=?  and ")
				.append("BRANCH_ID=? and ").append("STAFF_ID=? and ")
				.append("DB_TS=?  ");
		
		logger.debug("insert query :" + query.toString());
		
		int affectedRows = getJdbcTemplate().update(query.toString(),
				new PreparedStatementSetter() {
					@Override
					public void setValues(PreparedStatement ps)
							throws SQLException {
						
						ps.setInt(1, staffMaster.getDbTs() + 1);
						ps.setString(2, staffMaster.getInstId().trim());
						ps.setString(3, staffMaster.getBranchId().trim());
						ps.setString(4, staffMaster.getStaffId().trim());
						ps.setString(5, staffMaster.getStaffName().trim()
								.toUpperCase());
						ps.setString(6, staffMaster.getTtg().trim());
						ps.setString(7, staffMaster.getDob().trim());
						ps.setString(8, staffMaster.getGender().trim());
						ps.setString(9, staffMaster.getMaritalStatus().trim());
						ps.setString(10, staffMaster.getFhName().trim());
						ps.setString(11, staffMaster.getReligion().trim());
						ps.setString(12, staffMaster.getCasteGroup().trim());
						
						ps.setString(13, staffMaster.getCasteName().trim());
						ps.setString(14, staffMaster.getSubCasteName().trim());
						ps.setString(15, staffMaster.getPermenantAddress1()
								.trim());
						ps.setString(16, staffMaster.getPermenantAddress2()
								.trim());
						ps.setString(17, staffMaster.getPermenantAddress3()
								.trim());
						ps.setString(18, staffMaster.getPermenantCity().trim());
						ps.setString(19, staffMaster.getPermenantState().trim());
						ps.setString(20, staffMaster.getPermenantPincode()
								.trim());
						
						ps.setString(21, staffMaster.getCommunicationAddress1()
								.trim());
						ps.setString(22, staffMaster.getCommunicationAddress2()
								.trim());
						ps.setString(23, staffMaster.getCommunicationAddress3()
								.trim());
						ps.setString(24, staffMaster.getCommunicationCity()
								.trim());
						ps.setString(25, staffMaster.getCommunicationState()
								.trim());
						ps.setString(26, staffMaster.getCommunicationPincode()
								.trim());
						ps.setString(27, staffMaster.getLandline().trim());
						ps.setString(28, staffMaster.getMobile().trim());
						ps.setString(29, staffMaster.getEmergency().trim());
						ps.setString(30, staffMaster.getEmail().trim());
						ps.setString(31, staffMaster.getPanCardNo().trim());
						ps.setString(32, staffMaster.getDelFlg().trim());
						ps.setString(33, staffMaster.getrModId().trim());
						ps.setString(34, staffMaster.getBloodGroup().trim());
						ps.setString(35, staffMaster.getInstId().trim());
						ps.setString(36, staffMaster.getBranchId().trim());
						ps.setString(37, staffMaster.getStaffId().trim());
						ps.setInt(38, staffMaster.getDbTs());
						
					}
					
				});
		
		if (affectedRows == 0) {
			throw new UpdateFailedException();
		}
		
	}
}
