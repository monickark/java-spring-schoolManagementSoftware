package com.jaw.fee.dao;

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

//CourseSubLinkDAO DAO class
@Repository
public class FeeMasterStatusDao extends BaseDao implements IFeeStatusDao {
	// Logging
	Logger logger = Logger.getLogger(FeeMasterStatusDao.class);

	@Override
	public void insertFeeStatus(final FeeMasterStatus FeeStatus)
			throws DuplicateEntryException {
		System.out.println("Inside insert method");
		System.out.println("Fee Status object values :" + FeeStatus.toString());
		StringBuffer query = new StringBuffer();

		query = query.append("insert into fsts ( ").append("DB_TS,")
				.append("INST_ID,").append("BRANCH_ID,").append("AC_TERM,")
				.append("FEE_CATGRY,").append("COURSE,").append("TERM,")
				.append("FEE_STS,").append("FEE_GENERATION_STS_ID,")
				.append("DEL_FLG,").append("R_MOD_ID,").append("R_MOD_TIME,")
				.append("R_CRE_ID,").append("R_CRE_TIME)")
				.append(" values (?,?,?, ?,?,?, ?,?,?, ?,?,now(), ?,now())");

		try {
			getJdbcTemplate().update(query.toString(),
					new PreparedStatementSetter() {

						@Override
						public void setValues(PreparedStatement pss)
								throws SQLException {
							pss.setInt(1, FeeStatus.getDbTs());
							pss.setString(2, FeeStatus.getInstId());
							pss.setString(3, FeeStatus.getBranchId());
							pss.setString(4, FeeStatus.getAcTerm().trim());
							pss.setString(5, FeeStatus.getFeeCategory().trim());
							pss.setString(6, FeeStatus.getCourse().trim());
							pss.setString(7, FeeStatus.getTerm().trim());
							pss.setString(8, FeeStatus.getFeeStatus().trim());
							pss.setString(9, FeeStatus.getFeeGenerationStatus()
									.trim());

							pss.setString(10, FeeStatus.getDelFlag().trim());
							pss.setString(11, FeeStatus.getrModId().trim());
							pss.setString(12, FeeStatus.getrCreId().trim());

						}

					});
		} catch (org.springframework.dao.DuplicateKeyException e) {
			throw new DuplicateEntryException();
		}

	}

	@Override
	public void updateFeeStatus(final FeeMasterStatus FeeStatus,
			final FeeMasterStatusKey FeeStatusKey) throws UpdateFailedException {
		System.out.println("Inside update method");
		System.out.println("Fee Category object values :"
				+ FeeStatus.toString());
		System.out.println("Fee Category key Details object values :"
				+ FeeStatusKey.toString());
		StringBuffer sql = new StringBuffer();

		sql.append("update fsts set").append(" DB_TS= ?,")
				.append("INST_ID= ?,").append("BRANCH_ID= ?,")
				.append("AC_TERM= ?,").append("FEE_CATGRY= ?,")
				.append("FEE_STS= ?,").append("FEE_GENERATION_STS_ID= ?,")
				.append("DEL_FLG= 'N',").append("R_MOD_ID= ?,")
				.append(" R_MOD_TIME=now()").append(" where")
				.append(" DB_TS= ?").append(" and  INST_ID= ?")
				.append(" and  BRANCH_ID= ?")
				.append(" and  COURSE= ?")
				.append(" and  AC_TERM= ?").append(" and  DEL_FLG='N'");

		int updateRecs = getJdbcTemplate().update(sql.toString(),
				new PreparedStatementSetter() {

					@Override
					public void setValues(PreparedStatement pss)
							throws SQLException {
						pss.setInt(1, FeeStatus.getDbTs());
						pss.setString(2, FeeStatus.getInstId());
						pss.setString(3, FeeStatus.getBranchId());
						pss.setString(4, FeeStatus.getAcTerm().trim());
						pss.setString(5, FeeStatus.getFeeCategory().trim());
						pss.setString(6, FeeStatus.getFeeStatus().trim());
						pss.setString(7, FeeStatus.getFeeGenerationStatus()
								.trim());
						pss.setString(8, FeeStatus.getrModId().trim());
						pss.setInt(9, FeeStatusKey.getDbTs());
						pss.setString(10, FeeStatusKey.getInstId().trim());
						pss.setString(11, FeeStatusKey.getBranchId().trim());
						pss.setString(12, FeeStatusKey.getCourse().trim());
						pss.setString(13, FeeStatusKey.getAcTerm().trim());

					}

				});
		if (updateRecs == 0) {
			throw new UpdateFailedException();

		}

	}

	@Override
	public FeeMasterStatus selectFeeStatus(final FeeMasterStatusKey FeeStatusKey)
			throws NoDataFoundException {
		System.out.println("Inside select method");
		System.out.println("Fee Category Key object values :"
				+ FeeStatusKey.toString());
		List<String> data = new ArrayList<String>();
		StringBuffer sql = new StringBuffer();

		sql.append("select ").append(" DB_TS,").append("INST_ID,")
				.append("BRANCH_ID,").append("AC_TERM,").append("FEE_CATGRY,").append("COURSE,").append("TERM,")
				.append("FEE_STS,").append("FEE_GENERATION_STS_ID,").append("DEL_FLG,").append("R_MOD_ID,")
				.append("R_MOD_TIME,").append("R_CRE_ID,").append("R_CRE_TIME")
				.append(" from fsts ").append(" where").append("   INST_ID= ?")
				.append(" and  BRANCH_ID= ?").append(" and  AC_TERM= ?");
		if((FeeStatusKey.getFeeCategory()!="")&&(FeeStatusKey.getFeeCategory()!=null)&&(!FeeStatusKey.getFeeCategory().equals(""))){
				sql.append(" and  FEE_CATGRY= ?");
		}
		sql.append(" and  DEL_FLG=?");
		data.add(FeeStatusKey.getInstId());
		data.add(FeeStatusKey.getBranchId());
		data.add(FeeStatusKey.getAcTerm());
		if((FeeStatusKey.getFeeCategory()!="")&&(FeeStatusKey.getFeeCategory()!=null)&&(!FeeStatusKey.getFeeCategory().equals(""))){
		data.add(FeeStatusKey.getFeeCategory());
		}
		data.add("N");
		if((FeeStatusKey.getCourse()!="")&&(FeeStatusKey.getCourse()!=null)&&(!FeeStatusKey.getCourse().equals(""))){
			sql.append(" and COURSE=?  ");
			logger.debug("COURSE Value :" + FeeStatusKey.getCourse());
			data.add(FeeStatusKey.getCourse());
		}
		if((FeeStatusKey.getTerm()!="")&&(FeeStatusKey.getTerm()!=null)&&(!FeeStatusKey.getTerm().equals(""))){
			sql.append(" and TERM=?  ");
			logger.debug("TERM Value :" + FeeStatusKey.getTerm());
			data.add(FeeStatusKey.getTerm());
		}
		

		String[] array = data.toArray(new String[data.size()]);
		FeeMasterStatus FeeStatus = null;
		FeeStatus = (FeeMasterStatus) getJdbcTemplate().query(sql.toString(), array,
				new ResultSetExtractor<FeeMasterStatus>() {

					@Override
					public FeeMasterStatus extractData(ResultSet rs)
							throws SQLException, DataAccessException {
						FeeMasterStatus FeeStatus = null;
						if (rs.next()) {

							FeeStatus = new FeeMasterStatus();
							FeeStatus.setDbTs(rs.getInt("DB_TS"));
							FeeStatus.setInstId(rs.getString("INST_ID"));
							FeeStatus.setBranchId(rs.getString("BRANCH_ID"));
							FeeStatus.setAcTerm(rs
									.getString("AC_TERM"));
							FeeStatus.setFeeCategory(rs.getString("FEE_CATGRY"));
							FeeStatus.setFeeStatus(rs.getString("FEE_STS"));
							
							FeeStatus.setDelFlag(rs.getString("DEL_FLG"));
							FeeStatus.setrModId(rs.getString("R_MOD_ID"));
							FeeStatus.setrModTime(rs.getString("R_MOD_TIME"));
							FeeStatus.setrCreId(rs.getString("R_CRE_ID"));
							FeeStatus.setrCreTime(rs.getString("R_CRE_TIME"));
							FeeStatus.setFeeGenerationStatus(rs.getString("FEE_GENERATION_STS_ID"));
							FeeStatus.setCourse(rs.getString("COURSE"));
							FeeStatus.setTerm(rs.getString("TERM"));
						}
						return FeeStatus;
					}

				});

		if (FeeStatus == null) {
			throw new NoDataFoundException();
		}
System.out.println("fee status in daoooooooooooooooo  : "+FeeStatus.toString());
		return FeeStatus;
	}

	@Override
	public void deleteFeeStatus(final FeeMasterStatusKey FeeStatusKey)
			throws DeleteFailedException {

		logger.debug("Inside insert method in ClassTeacherAllotmentDao :"
				+ FeeStatusKey.toString());

		StringBuffer sql = new StringBuffer();

		sql.append("delete from fsts where ").append("DB_Ts=?")
				.append(" and inst_id=?").append(" and branch_id=?")
				.append(" and AC_TERM=?").append(" and FEE_CATGRY=?")
				.append(" and del_flg=?");
		logger.debug("update query :" + sql.toString());

		int affectedRows = getJdbcTemplate().update(sql.toString(),
				new PreparedStatementSetter() {

					@Override
					public void setValues(PreparedStatement ps)
							throws SQLException {

						ps.setInt(1, FeeStatusKey.getDbTs());
						ps.setString(2, FeeStatusKey.getInstId().trim());
						ps.setString(3, FeeStatusKey.getBranchId().trim());
						ps.setString(4, FeeStatusKey.getAcTerm().trim());
						ps.setString(5, FeeStatusKey.getFeeCategory().trim());
						ps.setString(6, "N");

					}

				});
		if (affectedRows == 0) {
			throw new DeleteFailedException();
		}

	}

	/*
	 * @Override public String checkFeeStatus(FeeMasterStatusKey feeStatusKey)
	 * throws NoDataFoundException {
	 * logger.debug("Inside Check Fee Status  method");
	 * logger.debug("FeeStatusKey object values :"+ feeStatusKey.toString());
	 * StringBuffer sql = new StringBuffer(); List<String> data = new
	 * ArrayList<String>();
	 * 
	 * 
	 * sql.append("select FEE_STS ") .append("from fsts") .append(" where ")
	 * .append(" INST_ID=?") .append(" and BRANCH_ID=?")
	 * .append(" and  FEE_GENERATION_STS_ID=?") .append(" and DEL_FLG='N'");
	 * data.add(feeStatusKey.getInstId()); data.add(feeStatusKey.getBranchId());
	 * data.add(feeStatusKey.getFeeGenerationStatus());
	 * 
	 * String[] array = data.toArray(new String[data.size()]); FeeMasterStatus
	 * FeeStatus = null; FeeStatus = (FeeMasterStatus)
	 * getJdbcTemplate().query(sql.toString(), array, new
	 * ResultSetExtractor<FeeMasterStatus>() {
	 * 
	 * @Override public FeeMasterStatus extractData(ResultSet rs) throws
	 * SQLException, DataAccessException { FeeMasterStatus FeeStatus = null; if
	 * (rs.next()) {
	 * 
	 * FeeStatus = new FeeMasterStatus();
	 * 
	 * FeeStatus.setFeeStatus(rs.getString("FEE_STS")); } return FeeStatus; }
	 * 
	 * });
	 * 
	 * if (FeeStatus == null) { throw new NoDataFoundException(); }
	 * 
	 * return FeeStatus.getFeeStatus();
	 * 
	 * 
	 * 
	 * }
	 */

	@Override
	public int checkFeeStatusLocked(String instId, String branchId,
			String acterm, String feeCategory, String courseId, String termId,
			String feeStatus) {

		String SELECT_COUNT = "select count(0) from fsts where inst_id='"
				+ instId + "' and branch_id='" + branchId + "' and AC_TERM='"
				+ acterm + "' and fee_catgry='" + feeCategory
				+ "' and COURSE='" + courseId + "' and term='" + termId
				+ "' and fee_sts='" + feeStatus + "' and del_flg='N'";

		System.out.println("checkRecordExist query :" + SELECT_COUNT);
		return getJdbcTemplate().queryForInt(SELECT_COUNT);
	}

}
