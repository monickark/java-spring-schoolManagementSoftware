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
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.jaw.common.exceptions.DatabaseException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.UpdateFailedException;
import com.jaw.framework.dao.BaseDao;

//Institute Master DAO class
@Repository
public class InstituteMasterDAO extends BaseDao implements IInstituteMasterDAO {

	// Logging
	Logger logger = Logger.getLogger(InstituteMasterDAO.class);

	// method to insert into InstiuteMaster Table
	@Override
	public void insertInstituteMasterRec(final InstituteMaster instMaster)
			throws DuplicateEntryException {

		logger.debug("Inside insert method");

		StringBuffer query = new StringBuffer();
		query = query.append("insert into insm ( ")
				.append("DB_TS,")
				.append("INST_ID,")
				.append("INST_NAME,")
				.append("INST_ADD1,")
				.append("INST_ADD2,")
				.append("INST_ADD3,")
				.append("INST_CITY,")
				.append("INST_PINCODE,")
				.append("INST_STATE,")
				.append("INST_EMAIL,")
				.append("INST_FAX,")
				.append("INST_CONTACT1,")
				.append("INST_CONTACT2,")
				.append("INST_CATEGORY,")
				.append("AFF_ID,")
				.append("AFF_DETAILS,")
				.append("DEL_FLG = 'N',")
				.append("R_MOD_ID,")
				.append("R_MOD_TIME = 'now()',")
				.append("R_CRE_ID,")
				.append("R_CRE_TIME)")

				.append(" values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

		logger.debug("insert query :" + query.toString());

		try {
			getJdbcTemplate().update(query.toString(),
					new PreparedStatementSetter() {

						@Override
						public void setValues(PreparedStatement ps)
								throws SQLException {

							ps.setInt(1, instMaster.getDbTs());
							ps.setString(2, instMaster.getInstId().trim());
							ps.setString(3, instMaster.getInstName().trim());
							ps.setString(4, instMaster.getAdd1().trim());
							ps.setString(5, instMaster.getAdd2().trim());
							ps.setString(6, instMaster.getAdd3().trim());
							ps.setString(7, instMaster.getCity().trim());
							ps.setString(8, instMaster.getPincode().trim());
							ps.setString(9, instMaster.getState().trim());
							ps.setString(10, instMaster.getEmail().trim());
							ps.setString(11, instMaster.getFax().trim());
							ps.setString(12, instMaster.getContact1().trim());
							ps.setString(13, instMaster.getContact2().trim());
							ps.setString(14, instMaster.getInstCategory()
									.trim());
							ps.setString(15, instMaster.getAffId().trim());
							ps.setString(16, instMaster.getAffDetails().trim());
							ps.setString(17, instMaster.getrModId().trim());
							ps.setString(18, instMaster.getrCreId().trim());

						}

					});
		} catch (org.springframework.dao.DuplicateKeyException e) {
			throw new DuplicateEntryException();
		}

	}

	// method to update InstiuteMaster Table
	@Override
	public void updateInstituteMasterRec(final InstituteMaster instMaster,
			final InstituteMasterKey instMasterKey)
			throws UpdateFailedException {

		logger.debug("Inside update method");
		StringBuffer sql = new StringBuffer();
		sql.append("update insm set")
				.append(" DB_TS= ?")
				.append(", INST_ID= ?")
				.append(", INST_NAME= ?")
				.append(", INST_ADD1= ?")
				.append(", INST_ADD2= ?")
				.append(", INST_ADD3= ?")
				.append(", INST_CITY= ?")
				.append(", INST_PINCODE= ?")
				.append(", INST_STATE= ?")
				.append(", INST_EMAIL= ?")
				.append(", INST_FAX= ?")
				.append(", INST_CONTACT1= ?")
				.append(", INST_CONTACT2= ?")
				.append(", INST_CATEGORY= ?")
				.append(", AFF_ID= ?")
				.append(", AFF_DETAILS= ?")
				.append(", DEL_FLG= ?")
				.append(", R_MOD_ID= ?")
				.append(", R_MOD_TIME=now()")
				.append(" where")
				.append(" INST_ID= ?")
				.append(" and ")
				.append(" DB_TS= ?")
				.append(" and ")
				.append(" DEL_FLG='N'");

		int updateStatus = getJdbcTemplate().update(sql.toString(),
				new PreparedStatementSetter() {

					@Override
					public void setValues(PreparedStatement ps)
							throws SQLException {
						ps.setInt(1, instMaster.getDbTs() + 1);
						ps.setString(2, instMaster.getInstId().trim());
						ps.setString(3, instMaster.getInstName().trim());
						ps.setString(4, instMaster.getAdd1().trim());
						ps.setString(5, instMaster.getAdd2().trim());
						ps.setString(6, instMaster.getAdd3().trim());
						ps.setString(7, instMaster.getCity().trim());
						ps.setString(8, instMaster.getPincode().trim());
						ps.setString(9, instMaster.getState().trim());
						ps.setString(10, instMaster.getEmail().trim());
						ps.setString(11, instMaster.getFax().trim());
						ps.setString(12, instMaster.getContact1().trim());
						ps.setString(13, instMaster.getContact2().trim());
						ps.setString(14, instMaster.getInstCategory().trim());
						ps.setString(15, instMaster.getAffId().trim());
						ps.setString(16, instMaster.getAffDetails().trim());
						ps.setString(17, instMaster.getDelFlag().trim());
						ps.setString(18, instMaster.getrModId().trim());
						ps.setString(19, instMasterKey.getInstId().trim());
						ps.setInt(20, instMaster.getDbTs());

					}

				});
		if (updateStatus == 0) {
			throw new UpdateFailedException();

		}

		logger.debug("update query :" + sql.toString());

	}
	

	@Override
	public InstituteMaster selectInstituteMasterRec(final Integer dbtsValue,
			final String instId) throws DatabaseException {
		logger.debug("Inside select method");
		StringBuffer sql = new StringBuffer();
		List<String> data=new ArrayList<String>();

		sql.append("select ")
				.append("DB_TS,")
				.append("INST_ID,")
				.append("INST_NAME,")
				.append("INST_ADD1,")
				.append("INST_ADD2,")
				.append("INST_ADD3,")
				.append("INST_CITY,")
				.append("INST_PINCODE,")
				.append("INST_STATE,")
				.append("INST_EMAIL,")
				.append("INST_FAX,")
				.append("INST_CONTACT1,")
				.append("INST_CONTACT2,")
				.append("INST_CATEGORY,")
				.append("AFF_ID,")
				.append("AFF_DETAILS,")
				.append("DEL_FLG,")
				.append("R_MOD_ID,")
				.append("R_MOD_TIME,")
				.append("R_CRE_ID,")
				.append("R_CRE_TIME from insm ")
				.append("where ")
				.append(" DEL_FLG=?");				
				data.add("N");
				if(dbtsValue!=null){
					sql.append(" and DB_TS=?");
					data.add(dbtsValue.toString());					
				}
				if(instId!=null){
					sql.append(" and INST_ID=?");
					data.add(instId.toString());					
				}
				String[] array = data.toArray(new String[data.size()]);
		logger.debug("select query :" + sql.toString());

		InstituteMaster selectedInstMaster = null;

		selectedInstMaster = (InstituteMaster) getJdbcTemplate().query(
				sql.toString(),array, new ResultSetExtractor<InstituteMaster>() {

					@Override
					public InstituteMaster extractData(final ResultSet rs)
							throws SQLException, DataAccessException {
						InstituteMaster instMaster = null;
						if (rs.next()) {
							instMaster = new InstituteMaster();

							instMaster.setDbTs(rs.getInt("DB_TS"));
							instMaster.setInstId(rs.getString("INST_ID"));
							instMaster.setInstName(rs.getString("INST_NAME"));
							instMaster.setAdd1(rs.getString("INST_ADD1"));
							instMaster.setAdd2(rs.getString("INST_ADD2"));
							instMaster.setAdd3(rs.getString("INST_ADD3"));
							instMaster.setCity(rs.getString("INST_CITY"));
							instMaster.setPincode(rs.getString("INST_PINCODE"));
							instMaster.setState(rs.getString("INST_STATE"));
							instMaster.setEmail(rs.getString("INST_EMAIL"));
							instMaster.setFax(rs.getString("INST_FAX"));
							instMaster.setContact1(rs
									.getString("INST_CONTACT1"));
							instMaster.setContact2(rs
									.getString("INST_CONTACT2"));
							instMaster.setInstCategory(rs
									.getString("INST_CATEGORY"));
							instMaster.setAffId(rs.getString("AFF_ID"));
							instMaster.setAffDetails(rs
									.getString("AFF_DETAILS"));
							instMaster.setDelFlag((rs.getString("DEL_FLG")));
							instMaster.setrModId(rs.getString("R_MOD_ID"));
							instMaster.setrModTime(rs.getString("R_MOD_TIME"));
							instMaster.setrCreId(rs.getString("R_CRE_ID"));
							instMaster.setrCreTime(rs.getString("R_CRE_TIME"));
						}
						return instMaster;
					}

				});
		if (selectedInstMaster == null) {
			throw new DatabaseException();
		}

		return selectedInstMaster;
	}

}

// Inner Class for select Method
class InstituteMasterRowMapper implements RowMapper<InstituteMaster> {

	// method mapping database columns into objects
	@Override
	public InstituteMaster mapRow(ResultSet rs, int arg1) throws SQLException {
		InstituteMaster instMaster = new InstituteMaster();
		instMaster.setDbTs(rs.getInt("DB_TS"));
		instMaster.setInstId(rs.getString("INST_ID"));
		instMaster.setInstName(rs.getString("INST_NAME"));
		instMaster.setAdd1(rs.getString("INST_ADD1"));
		instMaster.setAdd2(rs.getString("INST_ADD2"));
		instMaster.setAdd3(rs.getString("INST_ADD3"));
		instMaster.setCity(rs.getString("INST_CITY"));
		instMaster.setPincode(rs.getString("INST_PINCODE"));
		instMaster.setState(rs.getString("INST_STATE"));
		instMaster.setEmail(rs.getString("INST_EMAIL"));
		instMaster.setFax(rs.getString("INST_FAX"));
		instMaster.setContact1(rs.getString("INST_CONTACT1"));
		instMaster.setContact2(rs.getString("INST_CONTACT2"));
		instMaster.setContact2(rs.getString("INST_CATEGORY"));
		instMaster.setContact2(rs.getString("AFF_ID"));
		instMaster.setContact2(rs.getString("AFF_DETAILS"));
		instMaster.setDelFlag((rs.getString("DEL_FLG")));
		instMaster.setrModId(rs.getString("R_MOD_ID"));
		instMaster.setrModTime(rs.getString("R_MOD_TIME"));
		instMaster.setrCreId(rs.getString("R_CRE_ID"));
		instMaster.setrCreTime(rs.getString("R_CRE_TIME"));
		return instMaster;
	}
}
