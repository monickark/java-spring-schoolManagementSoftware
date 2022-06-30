package com.jaw.nonStaff.dao;

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
public class NonStaffDetailsDAO extends BaseDao implements INonStaffDetailsDAO {
	
	// Logging
	Logger logger = Logger.getLogger(NonStaffDetailsDAO.class);
	
	@Override
	public void insertNonStaffDetails(final NonStaff nonStaff)
			throws DuplicateEntryException {
		
		logger.debug("Inside insert method");
		StringBuffer query = new StringBuffer();
		query.append("insert into nsfm ( ")
				.append("DB_TS,")
				.append("INST_ID,")
				.append("BRANCH_ID,")
				
				.append("NON_STAFF_ID,")
				.append("STAFF_NAME,")
				.append("TTG_ID,")
				
				.append("CURRENT_ADDRESS1,")
				.append("CURRENT_ADDRESS2,")
				.append("CURRENT_ADDRESS3,")
				
				.append("CURRENT_CITY,")
				.append("CURRENT_STATE,")
				.append("CURRENT_PINCODE,")
				
				.append("PERMENANT_ADDRESS1,")
				.append("PERMENANT_ADDRESS2,")
				.append("PERMENANT_ADDRESS3,")
				
				.append("PERMENANT_CITY,")
				.append("PERMENANT_STATE,")
				.append("PERMENANT_PINCODE,")
				
				.append("CONTACT1,")
				.append("CONTACT2,")
				.append("EMERGENCY_CONTACT,")
				
				.append("EMAIL,")
				.append("DESIGNATION,")
				.append("GENDER,")
				
				.append("DEL_FLG,")
				.append("R_MOD_ID,")
				
				.append("R_MOD_TIME,")
				.append("R_CRE_ID,")
				.append("R_CRE_TIME)")
				
				.append(" values (?,?,?, ?,?,?, ?,?,?,  ?,?,?, ?,?,?, ?,?,?, ?,?,?, ?,?,?, ?,?,now(), ?,now())");
		logger.debug("insert query :" + query.toString());
		
		try {
			getJdbcTemplate().update(query.toString(),
					new PreparedStatementSetter() {
						@Override
						public void setValues(PreparedStatement ps)
								throws SQLException {
							ps.setInt(1, nonStaff.getDbTs() + 1);
							ps.setString(2, nonStaff.getInstId().trim());
							ps.setString(3, nonStaff.getBranchId().trim());
							ps.setString(4, nonStaff.getStaffId().trim());
							ps.setString(5, nonStaff.getStaffName().trim());
							ps.setString(6, nonStaff.getTtg());
							ps.setString(7, nonStaff.getCommunicationAddress1()
									.trim());
							ps.setString(8, nonStaff.getCommunicationAddress2()
									.trim());
							ps.setString(9, nonStaff.getCommunicationAddress3()
									.trim());
							ps.setString(10, nonStaff.getCommunicationCity()
									.trim());
							ps.setString(11, nonStaff.getCommunicationState()
									.trim());
							ps.setString(12, nonStaff.getCommunicationPincode()
									.trim());
							ps.setString(13, nonStaff.getPermenantAddress1()
									.trim());
							ps.setString(14, nonStaff.getPermenantAddress2()
									.trim());
							ps.setString(15, nonStaff.getPermenantAddress3()
									.trim());
							ps.setString(16, nonStaff.getPermenantCity().trim());
							ps.setString(17, nonStaff.getPermenantState()
									.trim());
							ps.setString(18, nonStaff.getPermenantPincode()
									.trim());
							ps.setString(19, nonStaff.getContact1().trim());
							ps.setString(20, nonStaff.getContact2().trim());
							ps.setString(21, nonStaff.getEmergencyContact()
									.trim());
							ps.setString(22, nonStaff.getEmail().trim());
							ps.setString(23, nonStaff.getDesignation().trim());
							ps.setString(24, nonStaff.getGender().trim());
							ps.setString(25, nonStaff.getDelFlg().trim());
							ps.setString(26, nonStaff.getrModId().trim());
							ps.setString(27, nonStaff.getrCreId().trim());
							
						}
						
					});
		}
		catch (org.springframework.dao.DuplicateKeyException e) {
			throw new DuplicateEntryException();
		}
		
	}
	
	@Override
	public NonStaff selectNonStaffRec(final String instId,
			final String branchId, final String staffId) throws NoDataFoundException {
		
		logger.debug("Inside select method");
		logger.debug("Inst id :" + instId + "   branch id :" + branchId + " staffId :" + staffId);
		StringBuffer sql = new StringBuffer();
		sql.append("select ")
				.append("DB_TS,")
				.append("INST_ID,")
				.append("BRANCH_ID,")
				.append("NON_STAFF_ID,")
				.append("STAFF_NAME,")
				.append("TTG_ID,")
				.append("CURRENT_ADDRESS1,")
				.append("CURRENT_ADDRESS2,")
				.append("CURRENT_ADDRESS3,")
				.append("CURRENT_CITY,")
				.append("CURRENT_STATE,")
				.append("CURRENT_PINCODE,")
				.append("PERMENANT_ADDRESS1,")
				.append("PERMENANT_ADDRESS2,")
				.append("PERMENANT_ADDRESS3,")
				.append("PERMENANT_CITY,")
				.append("PERMENANT_STATE,")
				.append("PERMENANT_PINCODE,")
				.append("CONTACT1,")
				.append("CONTACT2,")
				.append("EMERGENCY_CONTACT,")
				.append("EMAIL,")
				.append("DESIGNATION,")
				.append("GENDER,")
				.append("DEL_FLG,")
				.append("R_MOD_ID,")
				.append("R_MOD_TIME,")
				.append("R_CRE_ID,")
				.append("R_CRE_TIME from nsfm ")
				.append("where ")
				.append("DEL_FLG=? and ")
				.append("INST_ID=? and ")
				.append("BRANCH_ID=? and ")
				.append("NON_STAFF_ID=? ");
		logger.debug("select query :" + sql.toString());
		
		NonStaff nonStaff = null;
		
		nonStaff = (NonStaff) getJdbcTemplate().query(sql.toString(),
				new PreparedStatementSetter() {
					
					@Override
					public void setValues(PreparedStatement pss)
							throws SQLException {
						pss.setString(1, "N");
						pss.setString(2, instId);
						pss.setString(3, branchId);
						pss.setString(4, staffId);
						
					}
					
				}, new ResultSetExtractor<NonStaff>() {
					
					@Override
					public NonStaff extractData(ResultSet rs)
							throws SQLException, DataAccessException {
						
						NonStaff staff = null;
						if (rs.next()) {
							staff = new NonStaff();
							staff.setDbTs(rs.getInt("DB_TS"));
							staff.setInstId(rs.getString("INST_ID"));
							staff.setBranchId(rs.getString("BRANCH_ID"));
							staff.setStaffId(rs.getString("NON_STAFF_ID"));
							staff.setStaffName(rs.getString("STAFF_NAME"));
							staff.setTtg(rs.getString("TTG_ID"));
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
							staff.setContact1(rs.getString("CONTACT1"));
							staff.setContact2(rs.getString("CONTACT2"));
							staff.setEmergencyContact(rs
									.getString("EMERGENCY_CONTACT"));
							staff.setEmail(rs.getString("EMAIL"));
							staff.setDesignation(rs.getString("DESIGNATION"));
							staff.setGender(rs.getString("GENDER"));
							staff.setDelFlg(rs.getString("DEL_FLG"));
							staff.setrModId(rs.getString("R_MOD_ID"));
							staff.setrModTime(rs.getString("R_MOD_TIME"));
							staff.setrCreId(rs.getString("R_CRE_ID"));
							staff.setrCreTime(rs.getString("R_CRE_TIME"));
						}
						return staff;
					}
					
				});
		if (nonStaff == null) {
			throw new NoDataFoundException();
		}
		
		return nonStaff;
	}
	
	@Override
	public void updateNonStaffRec(final NonStaff nonStaff)
			throws UpdateFailedException {
		
		StringBuffer sql = new StringBuffer();
		sql.append("update nsfm set  ")
				.append("DB_TS=?,")
				.append("INST_ID=?,")
				.append("BRANCH_ID=?,")
				
				.append("NON_STAFF_ID=?,")
				.append("STAFF_NAME=?,")
				.append("TTG_ID=?,")
				
				.append("CURRENT_ADDRESS1=?,")
				
				.append("CURRENT_ADDRESS2=?,")
				.append("CURRENT_ADDRESS3=?,")
				
				.append("CURRENT_CITY=?,")
				.append("CURRENT_STATE=?,")
				.append("CURRENT_PINCODE=?,")
				
				.append("PERMENANT_ADDRESS1=?,")
				.append("PERMENANT_ADDRESS2=?,")
				.append("PERMENANT_ADDRESS3=?,")
				
				.append("PERMENANT_CITY=?,")
				.append("PERMENANT_STATE=?,")
				.append("PERMENANT_PINCODE=?,")
				
				.append("CONTACT1=?,")
				.append("CONTACT2=?,")
				.append("EMERGENCY_CONTACT=?,")
				
				.append("EMAIL=?,")
				.append("DESIGNATION=?,")
				.append("GENDER=?,")
				
				.append("DEL_FLG=?,")
				.append("R_MOD_ID=?,")
				.append("R_MOD_TIME=now(),")
				
				.append("R_CRE_ID=?,")
				.append("R_CRE_TIME=now()  ")
				.append("where ")
				
				.append("DB_TS=? and ")
				.append("INST_ID=? and ")
				
				.append("BRANCH_ID=? and ")
				.append("NON_STAFF_ID=? ");
		
		logger.debug("updateDB_TS :" + nonStaff.getDbTs() + " Del Flg :"
				+ nonStaff.getDelFlg() + " Inst Id :" + nonStaff.getInstId()
				+ "Branch Id :" + nonStaff.getStaffId());
		
		int affectedrow = getJdbcTemplate().update(sql.toString(),
				new PreparedStatementSetter() {
					@Override
					public void setValues(PreparedStatement ps)
							throws SQLException {
						ps.setInt(1, nonStaff.getDbTs() + 1);
						ps.setString(2, nonStaff.getInstId().trim());
						ps.setString(3, nonStaff.getBranchId().trim());
						ps.setString(4, nonStaff.getStaffId().trim());
						ps.setString(5, nonStaff.getStaffName().trim());
						ps.setString(6, nonStaff.getTtg());
						ps.setString(7, nonStaff.getCommunicationAddress1()
								.trim());
						ps.setString(8, nonStaff.getCommunicationAddress2()
								.trim());
						ps.setString(9, nonStaff.getCommunicationAddress3()
								.trim());
						ps.setString(10, nonStaff.getCommunicationCity().trim());
						ps.setString(11, nonStaff.getCommunicationState()
								.trim());
						ps.setString(12, nonStaff.getCommunicationPincode()
								.trim());
						ps.setString(13, nonStaff.getPermenantAddress1().trim());
						ps.setString(14, nonStaff.getPermenantAddress2().trim());
						ps.setString(15, nonStaff.getPermenantAddress3().trim());
						ps.setString(16, nonStaff.getPermenantCity().trim());
						ps.setString(17, nonStaff.getPermenantState().trim());
						ps.setString(18, nonStaff.getPermenantPincode().trim());
						ps.setString(19, nonStaff.getContact1().trim());
						ps.setString(20, nonStaff.getContact2().trim());
						ps.setString(21, nonStaff.getEmergencyContact().trim());
						ps.setString(22, nonStaff.getEmail().trim());
						ps.setString(23, nonStaff.getDesignation().trim());
						ps.setString(24, nonStaff.getGender().trim());
						ps.setString(25, nonStaff.getDelFlg().trim());
						ps.setString(26, nonStaff.getrModId().trim());
						ps.setString(27, nonStaff.getrCreId().trim());
						ps.setInt(28, nonStaff.getDbTs());
						ps.setString(29, nonStaff.getInstId().trim());
						ps.setString(30, nonStaff.getBranchId().trim());
						ps.setString(31, nonStaff.getStaffId().trim());
					}
					
				});
		if (affectedrow == 0) {
			throw new UpdateFailedException();
		}
		
	}
}
