package com.jaw.student.admission.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.UpdateFailedException;
import com.jaw.framework.dao.BaseDao;

@Repository
public class SiblingDetailsDAO extends BaseDao implements ISiblingDetails {
	Logger logger = Logger.getLogger(SiblingDetailsDAO.class);

	@Override
	public void insertSiblingDetails(List<SiblingDetails> sibDetails)
			throws DuplicateEntryException {		
		for (final SiblingDetails siblingDetails : sibDetails) {
			StringBuffer sql = new StringBuffer("insert into siblingdetails(")
					.append("DB_TS, ").append("INST_ID, ")
					.append("BRANCH_ID, ").append("STUDENT_ADMIS_NO, ")
					.append("SIBLING_NO, ").append("SIBLING_NAME, ")
					.append("SIBLING_CLASS, ").append("SIBLING_YEAR, 	")
					.append("SIBLING_ADMIS_NO, ").append("DEL_FLG, ")
					.append("R_MOD_ID, ").append("R_MOD_TIME, ")
					.append("R_CRE_ID, ").append("R_CRE_TIME")
					.append(") values(?,?,?,?,?,?,?,?,?,?,?,now(),?,now())");

			try {
				getJdbcTemplate().update(sql.toString(),
						new PreparedStatementSetter() {

							@Override
							public void setValues(PreparedStatement ps)
									throws SQLException {
								ps.setInt(1, 1);
								ps.setString(2, siblingDetails.getInstId()
										.trim());
								ps.setString(3, siblingDetails.getBranchId()
										.trim());
								ps.setString(4, siblingDetails
										.getStudentAdmisNo().trim());
								ps.setString(5, siblingDetails.getSiblingNo());
								ps.setString(6, siblingDetails.getSiblingName()
										.trim());
								ps.setString(7, siblingDetails
										.getSiblingClass().trim());
								ps.setString(8, siblingDetails.getSiblingYear()
										.trim());
								ps.setString(9, siblingDetails
										.getSiblingAdmisNo().trim());
								ps.setString(10, "N");
								ps.setString(11, siblingDetails.getrModId()
										.trim());
								/*ps.setString(12, siblingDetails.getrModTime()
										.trim());*/
								ps.setString(12, siblingDetails.getrCreId()
										.trim());
								/*ps.setString(14, siblingDetails.getrCreTime()
										.trim());*/
							}

						});

			} catch (org.springframework.dao.DuplicateKeyException e) {
				throw new DuplicateEntryException();
			} 
		}
	}

	@Override
	public void updateSibDetails(final SiblingDetails sibDetails, final SiblingDetailsKey siblingDetailsKey) throws UpdateFailedException {

		logger.debug("Inside update method");
		logger.debug("Key Object for update :"+siblingDetailsKey);
		logger.debug("Object for update :"+sibDetails);
	//	for (final SiblingDetails siblingDetails : sibDetails) {
			StringBuffer sql = new StringBuffer();
			sql.append("update siblingdetails set").append(" DB_TS=?, ")
					.append("INST_ID=?, ").append("BRANCH_ID=?, ")
					.append("STUDENT_ADMIS_NO=?, ").append("SIBLING_NO=?, ")
					.append("SIBLING_NAME=?, ").append("SIBLING_CLASS=?, ")
					.append("SIBLING_YEAR=?, ").append("SIBLING_ADMIS_NO=?, ")
					.append("DEL_FLG='N', ").append("R_MOD_ID=?, ")
					.append("R_MOD_TIME=now() ").append(" where")
					.append(" INST_ID=?").append(" and ")
					.append(" BRANCH_ID=?").append(" and ")
					.append(" STUDENT_ADMIS_NO=?").append(" and ")
					.append(" SIBLING_NO=?").append(" and ").append(" DB_TS=?")
					.append(" and ").append(" DEL_FLG='N'");

		int updateStatus =	getJdbcTemplate().update(sql.toString(),
					new PreparedStatementSetter() {

						@Override
						public void setValues(PreparedStatement ps)
								throws SQLException {

							ps.setInt(1, sibDetails.getDbTs() + 1);
							ps.setString(2, sibDetails.getInstId().trim());
							ps.setString(3, sibDetails.getBranchId().trim());
							ps.setString(4, sibDetails.getStudentAdmisNo()
									.trim());
							ps.setString(5, sibDetails.getSiblingNo());
							ps.setString(6, sibDetails.getSiblingName()
									.trim());
							ps.setString(7, sibDetails.getSiblingClass()
									.trim());
							ps.setString(8, sibDetails.getSiblingYear()
									.trim());
							ps.setString(9, sibDetails.getSiblingAdmisNo()
									.trim());
						//	ps.setString(10, sibDetails.getDelFlg().trim());
							ps.setString(10, sibDetails.getrModId().trim());
							ps.setString(11, siblingDetailsKey.getInstId()
									.trim());
							ps.setString(12, siblingDetailsKey.getBranchId()
									.trim());

							ps.setString(13, siblingDetailsKey.getStudentAdmisNo().trim());
							ps.setInt(14, siblingDetailsKey.getSiblingNo());
							ps.setInt(15, sibDetails.getDbTs());
						}

					});
		
		if (updateStatus == 0) {
			throw new UpdateFailedException();

		}
			logger.debug("update query :" + sql.toString());
		//}

	}

	@Override
	public List<SiblingDetails> retriveSiblingDetails(final SiblingDetailsKey siblingDetailsKey) throws NoDataFoundException {

		logger.debug("retrive Sibling Details");
		logger.info("Sibling Details Key Object :"+siblingDetailsKey);
		System.out.println("Sibling Details Key Object :"+siblingDetailsKey);
		StringBuffer sql = new StringBuffer().append("select ")
				.append("DB_TS, ").append("INST_ID, ").append("BRANCH_ID, ")
				.append("STUDENT_ADMIS_NO, ").append("SIBLING_NO, ")
				.append("SIBLING_NAME, ").append("SIBLING_CLASS, ")
				.append("SIBLING_YEAR, ").append("SIBLING_ADMIS_NO, ")
				.append("DEL_FLG, ").append("R_MOD_ID, ")
				.append("R_MOD_TIME, ").append("R_CRE_ID, ")
				.append("R_CRE_TIME").append(" from siblingdetails ")
				.append("where").append(" INST_ID=?").append(" and ")
				.append(" BRANCH_ID=?")
				.append(" and ")
				.append(" STUDENT_ADMIS_NO=?")
				.append(" and ")
				/*.append(" SIBLING_NO=?")
				.append(" and ")*/
				.append(" DEL_FLG=?");
		logger.debug("select query :" + sql.toString());

		List<SiblingDetails> siblingDetailsResult = getJdbcTemplate().query(
				sql.toString(), new PreparedStatementSetter() {

					@Override
					public void setValues(PreparedStatement pss)
							throws SQLException {
						pss.setString(1, siblingDetailsKey.getInstId().trim());
						pss.setString(2, siblingDetailsKey.getBranchId().trim());
						pss.setString(3, siblingDetailsKey.getStudentAdmisNo());
					//	pss.setInt(4, siblingDetailsKey.getSiblingNo());
						pss.setString(4, "N");
					}
				}, new SiblingDetailsResultSetExtractor());
		if(siblingDetailsResult.size()==0){
			throw new NoDataFoundException();
		}
		return siblingDetailsResult;
	}

	@Override
	public SiblingDetails retriveSingleSibDetails(
			final SiblingDetailsKey siblingDetailsKey) throws NoDataFoundException {


		logger.debug("retrive Sibling Details");
		logger.debug("Sibling Details Key Object :"+siblingDetailsKey);
		StringBuffer sql = new StringBuffer().append("select ")
				.append("DB_TS, ").append("INST_ID, ").append("BRANCH_ID, ")
				.append("STUDENT_ADMIS_NO, ").append("SIBLING_NO, ")
				.append("SIBLING_NAME, ").append("SIBLING_CLASS, ")
				.append("SIBLING_YEAR, ").append("SIBLING_ADMIS_NO, ")
				.append("DEL_FLG, ").append("R_MOD_ID, ")
				.append("R_MOD_TIME, ").append("R_CRE_ID, ")
				.append("R_CRE_TIME").append(" from siblingdetails ")
				.append("where").append(" INST_ID=?").append(" and ")
				.append(" BRANCH_ID=?")
				.append(" and ")
				.append(" STUDENT_ADMIS_NO=?")
				.append(" and ")
				.append(" SIBLING_NO=?")
				.append(" and ")
				.append(" DEL_FLG=?");
		logger.debug("select query :" + sql.toString());

		SiblingDetails siblingDetailsResult = (SiblingDetails) getJdbcTemplate().query(
				sql.toString(), new PreparedStatementSetter() {

					@Override
					public void setValues(PreparedStatement pss)
							throws SQLException {
						pss.setString(1, siblingDetailsKey.getInstId().trim());
						pss.setString(2, siblingDetailsKey.getBranchId().trim());
						pss.setString(3, siblingDetailsKey.getStudentAdmisNo());
						pss.setInt(4, siblingDetailsKey.getSiblingNo());
						pss.setString(5, "N");
					}
				}, new SiblingResultSetExtractor());		
		if(siblingDetailsResult==null){
			throw new NoDataFoundException();
		}
		return siblingDetailsResult;
	
	}

	
	


}

class SiblingDetailsResultSetExtractor implements RowMapper<SiblingDetails> {

	@Override
	public SiblingDetails mapRow(ResultSet rs, int arg1) throws SQLException {
		SiblingDetails sibDetail = new SiblingDetails();
 
		sibDetail.setDbTs(rs.getInt("DB_TS"));
		sibDetail.setInstId(rs.getString("INST_ID"));
		sibDetail.setBranchId(rs.getString("BRANCH_ID"));
		sibDetail.setStudentAdmisNo(rs.getString("STUDENT_ADMIS_NO"));
		sibDetail.setSiblingNo(rs.getString("SIBLING_NO"));
		sibDetail.setSiblingName(rs.getString("SIBLING_NAME"));
		sibDetail.setSiblingClass(rs.getString("SIBLING_CLASS"));
		sibDetail.setSiblingYear(rs.getString("SIBLING_YEAR"));
		sibDetail.setSiblingAdmisNo(rs.getString("SIBLING_ADMIS_NO"));
		sibDetail.setDelFlg(rs.getString("DEL_FLG"));
		sibDetail.setrModId(rs.getString("R_MOD_ID"));
		sibDetail.setrModTime(rs.getString("R_MOD_TIME"));
		sibDetail.setrCreId(rs.getString("R_CRE_ID"));
		sibDetail.setrCreTime(rs.getString("R_CRE_TIME"));

		return sibDetail;
	}
	
}
	class SiblingResultSetExtractor implements	ResultSetExtractor<SiblingDetails> {
@Override
public SiblingDetails extractData(ResultSet rs) throws SQLException,
		DataAccessException {
	SiblingDetails siblingDetails = null;
	if (rs.next()) {
		siblingDetails = new SiblingDetails();

		siblingDetails.setDbTs(rs.getInt("DB_TS"));
		siblingDetails.setInstId(rs.getString("INST_ID"));
		siblingDetails.setBranchId(rs.getString("BRANCH_ID"));
		siblingDetails.setStudentAdmisNo(rs.getString("STUDENT_ADMIS_NO"));
		siblingDetails.setSiblingNo(rs.getString("SIBLING_NO"));
		siblingDetails.setSiblingName(rs.getString("SIBLING_NAME"));
		siblingDetails.setSiblingClass(rs.getString("SIBLING_CLASS"));
		siblingDetails.setSiblingYear(rs.getString("SIBLING_YEAR"));
		siblingDetails.setSiblingAdmisNo(rs.getString("SIBLING_ADMIS_NO"));
		siblingDetails.setDelFlg(rs.getString("DEL_FLG"));
		siblingDetails.setrModId(rs.getString("R_MOD_ID"));
		siblingDetails.setrModTime(rs.getString("R_MOD_TIME"));
		siblingDetails.setrCreId(rs.getString("R_CRE_ID"));
		siblingDetails.setrCreTime(rs.getString("R_CRE_TIME"));
}
	return siblingDetails;
}

}
