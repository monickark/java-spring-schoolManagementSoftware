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
public class StaffDetailsDAO extends BaseDao implements IStaffDetailsDAO {
	
	// Logging
	Logger logger = Logger.getLogger(StaffDetailsDAO.class);
	
	@Override
	public StaffDetails selectStaffRec(final String instId,
			final String branchId, final String staffId)
			throws NoDataFoundException {
		System.out.println("Staff details : Select Staff Record :-  		Inst id :"
				+ instId + " branch id :" + branchId + " staffid" + staffId);
		System.out.println("Inside select method");
		
		StringBuffer sql = new StringBuffer();
		
		sql.append("select ").append("DB_TS,").append("INST_ID,")
				.append("BRANCH_ID,").append("STAFF_ID,").append("STAFF_NAME,")
				.append("QUALIFICATION,").append("NOY_EXP,").append("SKILLS,")
				.append("PREV_WORKPLACE,").append("DOJ,").append("DEPT_ID,")
				.append("DESIGNATION,").append("STAFF_CATEGORY1,")
				.append("STAFF_CATEGORY2,").append("STAFF_ROOM,")
				.append("SALARY_ACT_NO,").append("SALARY,")
				.append("TRANSFERED,").append("TRANSFERED_FROM,")
				.append("TRANSFERED_DATE,").append("REASON_FOR_LEAVING,")
				.append("TRANSFER_DATE,").append("TRANSFER_TO,")
				.append("DEL_FLG,").append("R_MOD_ID,").append("R_MOD_TIME,")
				.append("R_CRE_ID,").append("R_CRE_TIME from stfd ")
				.append("where ").append("DEL_FLG=? and ")
				.append("STAFF_ID=? and ").append("INST_ID=? and ")
				.append("BRANCH_ID=? ");
		System.out.println("select query :" + sql.toString());
		
		StaffDetails staff = null;
		
		staff = getJdbcTemplate().query(sql.toString(),
				new PreparedStatementSetter() {
					
					@Override
					public void setValues(PreparedStatement pss)
							throws SQLException {
						pss.setString(1, "N");
						pss.setString(2, staffId);
						pss.setString(3, instId);
						pss.setString(4, branchId);
						
					}
					
				}, new ResultSetExtractor<StaffDetails>() {
					
					@Override
					public StaffDetails extractData(ResultSet rs)
							throws SQLException, DataAccessException {
						StaffDetails staff = null;
						if (rs.next()) {
							staff = new StaffDetails();
							staff.setDbTs(rs.getInt("DB_TS"));
							staff.setInstId(rs.getString("INST_ID"));
							staff.setBranchId(rs.getString("BRANCH_ID"));
							staff.setStaffId(rs.getString("STAFF_ID"));
							staff.setStaffName(rs.getString("STAFF_NAME"));
							staff.setQualification(rs
									.getString("QUALIFICATION"));
							staff.setNoOfYrsExp(rs.getString("NOY_EXP"));
							staff.setSkills(rs.getString("SKILLS"));
							staff.setPrevWorkPlace(rs
									.getString("PREV_WORKPLACE"));
							staff.setDoj(rs.getString("DOJ"));
							staff.setDeptId(rs.getString("DEPT_ID"));
							staff.setDesignation(rs.getString("DESIGNATION"));
							staff.setStaffCategory1(rs
									.getString("STAFF_CATEGORY1"));
							staff.setStaffCategory2(rs
									.getString("STAFF_CATEGORY2"));
							staff.setStaffRoom(rs.getString("STAFF_ROOM"));
							staff.setSalaryActNo(rs.getString("SALARY_ACT_NO"));
							staff.setSalary(rs.getString("SALARY"));
							staff.setTransfered(rs.getString("TRANSFERED"));
							staff.setPrevTransferedFrom(rs
									.getString("TRANSFERED_FROM"));
							staff.setTransferedDate(rs
									.getString("TRANSFERED_DATE"));
							staff.setReasonForLeaving(rs
									.getString("REASON_FOR_LEAVING"));
							staff.setTransferDate(rs.getString("TRANSFER_DATE"));
							staff.setTransferedTo(rs.getString("TRANSFER_TO"));
							staff.setDelFlg(rs.getString("DEL_FLG"));
							staff.setrModId(rs.getString("R_MOD_ID"));
							staff.setrModTime(rs.getString("R_MOD_TIME"));
							staff.setrCreId(rs.getString("R_CRE_ID"));
							staff.setrCreTime(rs.getString("R_CRE_TIME"));
						}
						return staff;
					}
					
				});
		if (staff == null) {
			throw new NoDataFoundException();
		}
		
		return staff;
	}
	
	@Override
	public void insertStaffDetails(final StaffDetails staffDetails)
			throws DuplicateEntryException {
		
		System.out.println("Inside insert method");
		
		StringBuffer query = new StringBuffer();
		query.append("insert into stfd  (")
				
				.append("DB_TS,")
				.append("INST_ID,")
				.append("BRANCH_ID,")
				
				.append("STAFF_ID,")
				.append("STAFF_NAME,")
				.append("QUALIFICATION,")
				
				.append("NOY_EXP,")
				.append("SKILLS,")
				.append("PREV_WORKPLACE,")
				
				.append("DOJ,")
				.append("DEPT_ID,")
				.append("DESIGNATION,")
				
				.append("STAFF_CATEGORY1,")
				.append("STAFF_CATEGORY2,")
				.append("STAFF_ROOM,")
				
				.append("SALARY_ACT_NO,")
				.append("SALARY,")
				.append("TRANSFERED,")
				
				.append("TRANSFERED_FROM,")
				.append("TRANSFERED_DATE,")
				.append("REASON_FOR_LEAVING,")
				
				.append("TRANSFER_DATE,")
				.append("TRANSFER_TO,")
				.append("DEL_FLG,")
				
				.append("R_MOD_ID,")
				.append("R_MOD_TIME,")
				.append("R_CRE_ID,")
				
				.append("R_CRE_TIME )")
				.append(" values (?,?,?, ?,?,?, ?,?,?, ?,?,?, ?,?,?, ?,?,?, ?,?,?,  ?,?,?, ?,now(),?, now())");
		System.out.println("insert query :" + query.toString());
		
		try {
			getJdbcTemplate().update(query.toString(),
					new PreparedStatementSetter() {
						@Override
						public void setValues(PreparedStatement ps)
								throws SQLException {
							
							ps.setInt(1, staffDetails.getDbTs());
							ps.setString(2, staffDetails.getInstId().trim());
							ps.setString(3, staffDetails.getBranchId().trim());
							ps.setString(4, staffDetails.getStaffId().trim());
							ps.setString(5, staffDetails.getStaffName().trim()
									.toUpperCase());
							ps.setString(6, staffDetails.getQualification()
									.trim());
							if((staffDetails.getNoOfYrsExp()=="")||(staffDetails.getNoOfYrsExp().equals(""))) {
								ps.setString(7, null);
							} else {
								ps.setString(7, staffDetails.getNoOfYrsExp().trim());
							}
							ps.setString(8, staffDetails.getSkills().trim());
							ps.setString(9, staffDetails.getPrevWorkPlace()
									.trim());
							ps.setString(10, staffDetails.getDoj());
							ps.setString(11, staffDetails.getDeptId().trim());
							ps.setString(12, staffDetails.getDesignation()
									.trim());
							
							ps.setString(13, staffDetails.getStaffCategory1()
									.trim());
							ps.setString(14, staffDetails.getStaffCategory2()
									.trim());
							ps.setString(15, staffDetails.getStaffRoom().trim());
							ps.setString(16, staffDetails.getSalaryActNo()
									.trim());
							if((staffDetails.getSalary()=="")||(staffDetails.getSalary().equals(""))) {
							ps.setString(17, null);
							}
							else {
								ps.setString(17,
										staffDetails.getSalary());
							}
							
							
							ps.setString(18, staffDetails.getTransfered());
							ps.setString(19, staffDetails
									.getPrevTransferedFrom().trim());
							logger.info("staff details :"
									+ staffDetails.getTransferedDate());
							
							if (staffDetails.getTransferedDate() == ("")
									|| (staffDetails.getTransferedDate()
											.equals(""))) {
								ps.setString(20, null);
							}
							else {
								ps.setString(20,
										staffDetails.getTransferedDate());
							}
							
							ps.setString(21, staffDetails.getReasonForLeaving()
									.trim());
							if (staffDetails.getTransferDate() == ("")) {
								if (staffDetails.getTransferDate().equals("")) {
									ps.setString(22, null);
								}
							}
							else {
								ps.setString(22, staffDetails.getTransferDate());
							}
							ps.setString(23, staffDetails.getTransferedTo()
									.trim());
							ps.setString(24, staffDetails.getDelFlg().trim());
							ps.setString(25, staffDetails.getrModId().trim());
							ps.setString(26, staffDetails.getrCreId().trim());
							
						}
						
					});
		}
		catch (org.springframework.dao.DuplicateKeyException e) {
			throw new DuplicateEntryException();
		}
		
	}
	
	@Override
	public void updateStaffDetails(final StaffDetails staffDetails)
			throws UpdateFailedException {
		
		System.out.println("Inside insert method");
		System.out.println("Inst id :" + staffDetails.getInstId() + " branch id:"
				+ staffDetails.getBranchId() + " dbts :"
				+ staffDetails.getDbTs() + " staff id :"
				+ staffDetails.getStaffId());
		
		StringBuffer query = new StringBuffer();
		query.append("update stfd  set ")
				
				.append("DB_TS=?,").append("INST_ID=?,").append("BRANCH_ID=?,")
				.append("STAFF_ID=?,").append("STAFF_NAME=?,")
				.append("QUALIFICATION=?,").append("NOY_EXP=?,")
				.append("SKILLS=?,").append("PREV_WORKPLACE=?,")
				.append("DOJ=?,").append("DEPT_ID=?,").append("DESIGNATION=?,")
				.append("STAFF_CATEGORY1=?,").append("STAFF_CATEGORY2=?,")
				.append("STAFF_ROOM=?,").append("SALARY_ACT_NO=?,")
				.append("SALARY=?,").append("TRANSFERED=?,")
				.append("TRANSFERED_FROM=?,").append("TRANSFERED_DATE=?,")
				.append("REASON_FOR_LEAVING=?,").append("TRANSFER_DATE=?,")
				.append("TRANSFER_TO=?,").append("DEL_FLG=?,")
				.append("R_MOD_ID=?,").append("R_MOD_TIME = now()")
				.append(" where ").append("INST_ID=?  and ")
				.append("BRANCH_ID=? and ").append("STAFF_ID=?  and ")
				.append("DB_TS=?  ");
		
		System.out.println("insert query :" + query.toString());
		
		int affectedRows = getJdbcTemplate().update(query.toString(),
				new PreparedStatementSetter() {
					@Override
					public void setValues(PreparedStatement ps)
							throws SQLException {
						
						ps.setInt(1, staffDetails.getDbTs() + 1);
						ps.setString(2, staffDetails.getInstId().trim());
						ps.setString(3, staffDetails.getBranchId().trim());
						ps.setString(4, staffDetails.getStaffId().trim());
						ps.setString(5, staffDetails.getStaffName().trim()
								.toUpperCase());
						ps.setString(6, staffDetails.getQualification().trim());
						if((staffDetails.getNoOfYrsExp()=="")||(staffDetails.getNoOfYrsExp().equals(""))) {
							ps.setString(7, null);
						} else {
							ps.setString(7, staffDetails.getNoOfYrsExp().trim());
						}
						ps.setString(8, staffDetails.getSkills().trim());
						ps.setString(9, staffDetails.getPrevWorkPlace().trim());
						ps.setString(10, staffDetails.getDoj());
						ps.setString(11, staffDetails.getDeptId().trim());
						ps.setString(12, staffDetails.getDesignation().trim());
						ps.setString(13, staffDetails.getStaffCategory1()
								.trim());
						ps.setString(14, staffDetails.getStaffCategory2()
								.trim());
						ps.setString(15, staffDetails.getStaffRoom().trim());
						ps.setString(16, staffDetails.getSalaryActNo().trim());
						if((staffDetails.getSalary()=="")||(staffDetails.getSalary().equals(""))) {
							ps.setString(17, null);
							}
							else {
								ps.setString(17,
										staffDetails.getSalary());
							}

						ps.setString(18, staffDetails.getTransfered());
						ps.setString(19, staffDetails.getPrevTransferedFrom()
								.trim());
						
						if (staffDetails.getTransferedDate() == ("")
								|| (staffDetails.getTransferedDate().equals(""))) {
							ps.setString(20, null);
						}
						else {
							ps.setString(20, staffDetails.getTransferedDate());
						}
						
						ps.setString(21, staffDetails.getReasonForLeaving()
								.trim());
						if (staffDetails.getTransferDate() == ("")
								|| (staffDetails.getTransferDate().equals(""))) {
							
							ps.setString(22, null);
							
						}
						else {
							ps.setString(22, staffDetails.getTransferDate());
						}
						ps.setString(23, staffDetails.getTransferedTo().trim());
						ps.setString(24, staffDetails.getDelFlg().trim());
						ps.setString(25, staffDetails.getrModId().trim());
						ps.setString(26, staffDetails.getInstId().trim());
						ps.setString(27, staffDetails.getBranchId().trim());
						ps.setString(28, staffDetails.getStaffId().trim());
						ps.setInt(29, staffDetails.getDbTs());
						
					}
					
				});
		
		if (affectedRows == 0) {
			throw new UpdateFailedException();
		}
	}
}
