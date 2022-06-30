package com.jaw.mgmtUser.dao;

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
public class MgmtUserDetailsDAO extends BaseDao implements IMgmtUserDetailsDAO {

	// Logging
	Logger logger = Logger.getLogger(MgmtUserDetailsDAO.class);

	// method to insert into InstiuteMaster Table
	@Override
	public void insertManagementRec(final MgmtUser management)
			throws DuplicateEntryException {

		logger.debug("Inside insert method");

		StringBuffer query = new StringBuffer();
		query = query
				.append("insert into mgmt ( ")
				.append("DB_TS,")
				.append("INST_ID,")
				.append("BRANCH_ID,")
				.append("MANAGEMENT_ID,")
				.append("NAME,")
				.append("ADDRESS1,")
				.append("ADDRESS2,")
				.append("ADDRESS3,")
				.append("CITY,")
				.append("STATE,")
				.append("PINCODE,")
				.append("CONTACT1,")
				.append("CONTACT2,")
				.append("EMAIL,")
				.append("DESIGNATION,")
				.append("GENDER,")
				.append("DEL_FLG ,")
				.append("R_MOD_ID,")
				.append("R_MOD_TIME, ")
				.append("R_CRE_ID,")
				.append("R_CRE_TIME)")
				.append(" values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,now(),?,now())");
		logger.debug("insert query :" + query.toString());

		try {
			getJdbcTemplate().update(query.toString(),
					new PreparedStatementSetter() {

						@Override
						public void setValues(PreparedStatement ps)
								throws SQLException {

							ps.setInt(1, management.getDbTs());
							ps.setString(2, management.getInstId().trim());
							ps.setString(3, management.getBranchId().trim());
							ps.setString(4, management.getManagementId().trim());
							ps.setString(5, management.getName().trim());
							ps.setString(6, management.getAddress1().trim());
							ps.setString(7, management.getAddress2().trim());
							ps.setString(8, management.getAddress3().trim());
							ps.setString(9, management.getCity().trim());
							ps.setString(10, management.getState().trim());
							ps.setString(11, management.getPincode().trim());
							ps.setString(12, management.getContact1().trim());
							ps.setString(13, management.getContact2().trim());
							ps.setString(14, management.getEmail().trim());
							ps.setString(15, management.getDesignation().trim());
							ps.setString(16, management.getGender().trim());
							ps.setString(17, "N");
							ps.setString(18, management.getrCreId().trim());
							ps.setString(19, management.getrModId().trim());

						}

					});
		} catch (org.springframework.dao.DuplicateKeyException e) {
			throw new DuplicateEntryException();
		}

	}

	@Override
	public MgmtUser selectManagementRec(final String instId,
			final String branchId, final String managementId)
			throws NoDataFoundException {

		logger.debug("Inside select method");
		logger.debug("passing values instid:" + instId + "   branchid :"
				+ branchId + "   management id : " + managementId);
		StringBuffer sql = new StringBuffer();
		sql.append("select ").append("DB_TS,").append("INST_ID,")
				.append("BRANCH_ID,").append("MANAGEMENT_ID,").append("NAME,")
				.append("ADDRESS1,").append("ADDRESS2,").append("ADDRESS3,")
				.append("CITY,").append("STATE,").append("PINCODE,")
				.append("CONTACT1,").append("CONTACT2,").append("EMAIL,")
				.append("DESIGNATION,").append("GENDER,").append("DEL_FLG,")
				.append("R_MOD_ID,").append("R_MOD_TIME,").append("R_CRE_ID,")
				.append("R_CRE_TIME from mgmt ").append("where ")
				.append("DEL_FLG=? and ").append("MANAGEMENT_ID=? and ")
				.append("INST_ID=? and ").append("BRANCH_ID=? ");
		logger.debug("select query :" + sql.toString());

		MgmtUser management = null;

		management = (MgmtUser) getJdbcTemplate().query(sql.toString(),
				new PreparedStatementSetter() {

					@Override
					public void setValues(PreparedStatement pss)
							throws SQLException {
						pss.setString(1, "N");
						pss.setString(2, managementId);
						pss.setString(3, instId);
						pss.setString(4, branchId);

					}

				}, new ResultSetExtractor<MgmtUser>() {

					@Override
					public MgmtUser extractData(ResultSet rs)
							throws SQLException, DataAccessException {
						MgmtUser management = new MgmtUser();
						if (rs.next()) {
							management.setDbTs(rs.getInt("DB_TS"));
							management.setInstId(rs.getString("INST_ID"));
							management.setBranchId(rs.getString("BRANCH_ID"));
							management.setManagementId(rs
									.getString("MANAGEMENT_ID"));
							management.setName(rs.getString("NAME"));
							management.setAddress1(rs.getString("ADDRESS1"));
							management.setAddress2(rs.getString("ADDRESS2"));
							management.setAddress3(rs.getString("ADDRESS3"));
							management.setCity(rs.getString("CITY"));
							management.setState(rs.getString("STATE"));
							management.setPincode(rs.getString("PINCODE"));
							management.setContact1(rs.getString("CONTACT1"));
							management.setContact2(rs.getString("CONTACT2"));
							management.setEmail(rs.getString("EMAIL"));
							management.setDesignation(rs
									.getString("DESIGNATION"));
							management.setGender(rs.getString("GENDER"));
							management.setDelFlg(rs.getString("DEL_FLG"));
							management.setrModId(rs.getString("R_MOD_ID"));
							management.setrModTime(rs.getString("R_MOD_TIME"));
							management.setrCreId(rs.getString("R_CRE_ID"));
							management.setrCreTime(rs.getString("R_CRE_TIME"));
						}
						return management;
					}

				});
		if (management.getName() == "") {
			throw new NoDataFoundException();
		}
		return management;
	}

	@Override
	public void updateManagementRec(final MgmtUser management)
			throws UpdateFailedException {
		logger.debug("passing values :" + management.getManagementId() + "    "
				+ management.getBranchId() + "    " + management.getInstId()
				+ "   " + management.getDbTs() + "   " + management.getDelFlg());
		logger.debug("Inside update method");

		StringBuffer sql = new StringBuffer();
		sql.append("update mgmt set").append(" DB_TS= ?")
				.append(", INST_ID= ?").append(", BRANCH_ID= ?")

				.append(", MANAGEMENT_ID= ?").append(", NAME= ?")
				.append(", ADDRESS1= ?")

				.append(", ADDRESS2= ?").append(", ADDRESS3= ?")
				.append(", CITY= ?")

				.append(", STATE= ?").append(", PINCODE= ?")
				.append(", CONTACT1= ?")

				.append(", CONTACT2= ?").append(", EMAIL= ?")
				.append(", DESIGNATION= ?")

				.append(", GENDER= ?").append(", DEL_FLG= ?")
				.append(", R_CRE_ID= ?")

				.append(", R_CRE_TIME= ?").append(", R_MOD_ID= ?")
				.append(", R_MOD_TIME=now() ").append(" where")

				.append(" INST_ID= ? and ").append(" BRANCH_ID =? and ")
				.append(" DB_TS= ? and ").append(" MANAGEMENT_ID=?");
		int affectedrow = getJdbcTemplate().update(sql.toString(),
				new PreparedStatementSetter() {
					@Override
					public void setValues(PreparedStatement ps)
							throws SQLException {

						ps.setInt(1, management.getDbTs() + 1);
						ps.setString(2, management.getInstId().trim());
						ps.setString(3, management.getBranchId().trim());
						ps.setString(4, management.getManagementId().trim());
						ps.setString(5, management.getName().trim());
						ps.setString(6, management.getAddress1().trim());
						ps.setString(7, management.getAddress2().trim());
						ps.setString(8, management.getAddress3().trim());
						ps.setString(9, management.getCity().trim());
						ps.setString(10, management.getState().trim());
						ps.setString(11, management.getPincode().trim());
						ps.setString(12, management.getContact1().trim());
						ps.setString(13, management.getContact2().trim());
						ps.setString(14, management.getEmail().trim());
						ps.setString(15, management.getDesignation().trim());
						ps.setString(16, management.getGender().trim());
						ps.setString(17, management.getDelFlg().trim());
						ps.setString(18, management.getrCreId().trim());
						ps.setString(19, management.getrCreTime().trim());
						ps.setString(20, management.getrModId().trim());
						ps.setString(21, management.getInstId().trim());
						ps.setString(22, management.getBranchId().trim());
						ps.setInt(23, management.getDbTs());
						ps.setString(24, management.getManagementId());
					}

				});
		if (affectedrow == 0) {
			throw new UpdateFailedException();
		}

	}
}
