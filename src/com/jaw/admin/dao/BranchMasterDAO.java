package com.jaw.admin.dao;

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
public class BranchMasterDAO extends BaseDao implements IBranchMasterDAO {
	Logger logger = Logger.getLogger(BranchMasterDAO.class);

	@Override
	public void insertBranchMasterDetails(final BranchMaster branchMaster)
			throws DuplicateEntryException {
		logger.debug("Inside insert method");
		StringBuffer sql = new StringBuffer();
		sql = sql
				.append("insert into brcm ( ")
				.append("DB_TS,")
				.append("INST_ID,")
				.append("BRANCH_ID,")
				.append("BRANCH_NAME,")
				.append("BRANCH_ADD1,")
				.append("BRANCH_ADD2,")
				.append("BRANCH_ADD3,")
				.append("BRANCH_CITY,")
				.append("BRANCH_PINCODE,")
				.append("BRANCH_STATE,")
				.append("BRANCH_EMAIL,")
				.append("BRANCH_FAX,")
				.append("BRANCH_CONTACT1,")
				.append("BRANCH_CONTACT2,")
				.append("BRANCH_CATEGORY,")
				.append("AFF_ID,")
				.append("AFF_DETAILS,")
				.append("DEL_FLG,")
				.append("R_MOD_ID,")
				.append("R_MOD_TIME,")
				.append("R_CRE_ID,")
				.append("R_CRE_TIME)")
				.append(" values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,now(),?,now())");
		try {
			getJdbcTemplate().update(sql.toString(),
					new PreparedStatementSetter() {

						@Override
						public void setValues(PreparedStatement pss)
								throws SQLException {
							pss.setInt(1, branchMaster.getDbTs());
							pss.setString(2, branchMaster.getInstId().trim());
							pss.setString(3, branchMaster.getBranchId().trim());
							pss.setString(4, branchMaster.getBranchName()
									.trim());
							pss.setString(5, branchMaster.getAddress1().trim());
							pss.setString(6, branchMaster.getAddress2().trim());
							pss.setString(7, branchMaster.getAddress3());
							pss.setString(8, branchMaster.getCity());
							pss.setString(9, branchMaster.getPincode());
							pss.setString(10, branchMaster.getState());
							pss.setString(11, branchMaster.getEmail().trim());
							pss.setString(12, branchMaster.getFax().trim());
							pss.setString(13, branchMaster.getContact1());
							pss.setString(14, branchMaster.getContact2());
							pss.setString(15, branchMaster.getBranchCategory());
							pss.setString(16, branchMaster.getAffId());
							pss.setString(17, branchMaster.getAffDetails());
							pss.setString(18, branchMaster.getDelFlag());
							pss.setString(19, branchMaster.getrModId());
							pss.setString(20, branchMaster.getrCreId());
						}
					});
		} catch (org.springframework.dao.DuplicateKeyException e) {
			throw new DuplicateEntryException();
		}

	}

	@Override
	public void updateBranchMaster(final BranchMaster branchMaster,
			final BranchMasterKey branchMasterKey) throws UpdateFailedException {
		logger.debug("Inside update method");
		StringBuffer sql = new StringBuffer();
		sql.append("update brcm set").append(" DB_TS= ?")
				.append(", INST_ID= ?").append(", BRANCH_ID= ?")
				.append(", BRANCH_NAME= ?").append(", BRANCH_ADD1= ?")
				.append(", BRANCH_ADD2= ?").append(", BRANCH_ADD3= ?")
				.append(", BRANCH_PINCODE= ?").append(", BRANCH_CITY= ?")
				.append(", BRANCH_STATE= ?").append(", BRANCH_EMAIL= ?")
				.append(", BRANCH_FAX= ?").append(", BRANCH_CONTACT1= ?")
				.append(", BRANCH_CONTACT2= ?").append(", BRANCH_CATEGORY= ?")
				.append(", AFF_ID= ?").append(", AFF_DETAILS= ?")
				.append(", DEL_FLG= ?").append(", R_MOD_ID= ?")
				.append(", R_MOD_TIME= now()").append(" where")
				.append(" INST_ID=?").append(" and").append(" BRANCH_ID=?")
				.append(" and")
				.append(" DB_TS=?");
		logger.debug("update query :" + sql.toString());
		int updateStatus = getJdbcTemplate().update(sql.toString(),
				new PreparedStatementSetter() {

					@Override
					public void setValues(PreparedStatement ps)
							throws SQLException {
						ps.setInt(1, branchMaster.getDbTs() + 1);
						ps.setString(2, branchMaster.getInstId().trim());
						ps.setString(3, branchMaster.getBranchId().trim());
						ps.setString(4, branchMaster.getBranchName().trim());
						ps.setString(5, branchMaster.getAddress1());
						ps.setString(6, branchMaster.getAddress2());
						ps.setString(7, branchMaster.getAddress3());
						ps.setString(8, branchMaster.getPincode());
						ps.setString(9, branchMaster.getCity());
						ps.setString(10, branchMaster.getState());
						ps.setString(11, branchMaster.getEmail());
						ps.setString(12, branchMaster.getFax());
						ps.setString(13, branchMaster.getContact1());
						ps.setString(14, branchMaster.getContact2());
						ps.setString(15, branchMaster.getBranchCategory());
						ps.setString(16, branchMaster.getAffId());
						ps.setString(17, branchMaster.getAffDetails());
						ps.setString(18, branchMaster.getDelFlag());
						ps.setString(19, branchMaster.getrModId());
						ps.setString(20, branchMasterKey.getInstId());
						ps.setString(21, branchMasterKey.getBranchId().trim());					
						ps.setInt(22, branchMaster.getDbTs());
					}

				});
		if (updateStatus == 0) {
			throw new UpdateFailedException();

		}

	}

	@Override
	public BranchMaster selectBranchMaster(final BranchMasterKey code)
			throws NoDataFoundException {
		logger.debug("Inside select method");
		StringBuffer sql = new StringBuffer();
		List<String> data=new ArrayList<String>();
		sql.append("select ").append("DB_TS,").append("INST_ID,")
				.append("BRANCH_ID,").append("BRANCH_NAME,")
				.append("BRANCH_ADD1,").append("BRANCH_ADD2,")
				.append("BRANCH_ADD3,").append("BRANCH_PINCODE,")
				.append("BRANCH_CITY,").append("BRANCH_STATE,")
				.append("BRANCH_EMAIL,").append("BRANCH_FAX,")
				.append("BRANCH_CONTACT1,").append("BRANCH_CONTACT2,")
				.append("BRANCH_CATEGORY,").append("AFF_ID,")
				.append("AFF_DETAILS,").append("DEL_FLG,").append("R_MOD_ID,")
				.append("R_MOD_TIME,").append("R_CRE_ID,")
				.append("R_CRE_TIME from brcm ").append("where ")
				.append("INST_ID= ?").append(" and ").append("BRANCH_ID= ?")
				.append(" and ")				
				.append("DEL_FLG= ?");
			data.add(code.getInstId().trim());
			data.add(code.getBranchId().trim());
			data.add("N");
		if(code.getDbTs()!=null){
			sql.append(" and DB_TS=?");
			data.add( code.getDbTs().toString());					
		}
		String[] array = data.toArray(new String[data.size()]);
		logger.debug("select query :" + sql.toString());
		BranchMaster selectedListBranchMaster = null;
		selectedListBranchMaster = (BranchMaster) getJdbcTemplate().query(
				sql.toString(), array, new ResultSetExtractor<BranchMaster>() {

					@Override
					public BranchMaster extractData(ResultSet rs)
							throws SQLException, DataAccessException {
						BranchMaster branchMaster = null;
						if (rs.next()) {
							branchMaster = new BranchMaster();

							branchMaster.setDbTs(rs.getInt("DB_TS"));
							branchMaster.setInstId(rs.getString("INST_ID"));
							branchMaster.setBranchId(rs.getString("BRANCH_ID"));
							branchMaster.setBranchName(rs
									.getString("BRANCH_NAME"));
							branchMaster.setAddress1(rs
									.getString("BRANCH_ADD1"));
							branchMaster.setAddress2(rs
									.getString("BRANCH_ADD2"));
							branchMaster.setAddress3(rs
									.getString("BRANCH_ADD3"));
							branchMaster.setCity(rs.getString("BRANCH_CITY"));
							branchMaster.setPincode(rs
									.getString("BRANCH_PINCODE"));
							branchMaster.setState(rs.getString("BRANCH_STATE"));
							branchMaster.setEmail(rs.getString("BRANCH_EMAIL"));
							branchMaster.setFax(rs.getString("BRANCH_FAX"));
							branchMaster.setContact1(rs
									.getString("BRANCH_CONTACT1"));
							branchMaster.setContact2(rs
									.getString("BRANCH_CONTACT2"));
							branchMaster.setBranchCategory(rs
									.getString("BRANCH_CATEGORY"));
							branchMaster.setAffId(rs.getString("AFF_ID"));
							branchMaster.setAffDetails(rs
									.getString("AFF_DETAILS"));
							branchMaster.setDelFlag((rs.getString("DEL_FLG")));
							branchMaster.setrModId(rs.getString("R_MOD_ID"));
							branchMaster.setrModTime(rs.getString("R_MOD_TIME"));
							branchMaster.setrCreId(rs.getString("R_CRE_ID"));
							branchMaster.setrCreTime(rs.getString("R_CRE_TIME"));
						}
						return branchMaster;
					}

				});

		if (selectedListBranchMaster == null) {
			throw new NoDataFoundException();
		}

		return selectedListBranchMaster;

	}

}
