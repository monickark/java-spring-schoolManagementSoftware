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

import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.UpdateFailedException;
import com.jaw.framework.dao.BaseDao;

//CourseSubLinkDAO DAO class
@Repository
public class FeeMasterDao extends BaseDao implements IFeeMasterDao {
	// Logging
	Logger logger = Logger.getLogger(FeeMasterDao.class);

	@Override
	public void insertFeeMaster(final FeeMaster FeeMaster)
			throws DuplicateEntryException {
		System.out.println("Inside insert method");
		System.out.println("CourseSubLink object values :"
				+ FeeMaster.toString());
		
		StringBuffer query = new StringBuffer();
		query = query.append("insert into fmst ( ").append("DB_TS,")
				.append("INST_ID,").append("BRANCH_ID,").append("AC_TERM,")
				.append("FEE_CATGRY,").append("FEE_TERM,").append("FEE_TYPE,")
				.append("COURSE,").append("TERM,").append("CV_SPEC,").append("FEE_AMT,")
				.append("DEL_FLG,").append("R_MOD_ID,").append("R_MOD_TIME,")
				.append("R_CRE_ID,").append("R_CRE_TIME)")
				.append(" values (?,?,?,?,?,?,?,?,?,?,?,?,?,now(),?,now())");
		try {
			getJdbcTemplate().update(query.toString(),
					new PreparedStatementSetter() {

						@Override
						public void setValues(PreparedStatement pss)
								throws SQLException {
							pss.setInt(1, FeeMaster.getDbTs());
							pss.setString(2, FeeMaster.getInstId());
							pss.setString(3, FeeMaster.getBranchId());
							pss.setString(4, FeeMaster.getAcTerm().trim());
							pss.setString(5, FeeMaster.getFeeCategory().trim());
							pss.setString(6, FeeMaster.getFeeTerm().trim());
							pss.setString(7, FeeMaster.getFeeType());
							pss.setString(8, FeeMaster.getCourse().trim());
							pss.setString(9, FeeMaster.getTerm().trim());
							pss.setString(10, FeeMaster.getCourseVariant().trim());
							pss.setInt(11, FeeMaster.getFeeAmt());
							pss.setString(12, FeeMaster.getDelFlag().trim());
							pss.setString(13, FeeMaster.getrModId().trim());
							pss.setString(14, FeeMaster.getrCreId().trim());
						}

					});
		} catch (org.springframework.dao.DuplicateKeyException e) {
			throw new DuplicateEntryException();
		}

	}

	@Override
	public void updateFeeMaster(final FeeMaster feeMaster,
			final FeeMasterKey FeeMasterKey) throws UpdateFailedException {
		System.out.println("Inside update method");
		System.out.println("Fee Category object values :"
				+ feeMaster.toString());
		System.out.println("Fee Category key Details object values :"
				+ FeeMasterKey.toString());
		StringBuffer sql = new StringBuffer();

		sql.append("update fmst set").append(" DB_TS= ?,")
				.append("INST_ID= ?,").append("BRANCH_ID= ?,")
				.append("AC_TERM= ?,").append("FEE_CATGRY= ?,")
				.append("FEE_TERM= ?,").append("FEE_TYPE= ?,")
				.append("COURSE= ?,").append("TERM= ?,").append("CV_SPEC=?,").append("FEE_AMT= ?,")
				.append("DEL_FLG= ?,").append("R_MOD_ID= ?,")
				.append(" R_MOD_TIME=now()").append(" where")
				.append(" DB_TS= ?").append(" and  INST_ID= ?")
				.append(" and  BRANCH_ID= ?") .append(" and AC_TERM= ?  ") .append(" and  FEE_CATGRY= ?")
				.append(" and FEE_TERM= ?").append(" and FEE_TYPE= ?")
				.append(" and COURSE= ? ").append(" and TERM= ? ")
				.append(" and  DEL_FLG='N'");

		int updateRecs = getJdbcTemplate().update(sql.toString(),
				new PreparedStatementSetter() {

					@Override
					public void setValues(PreparedStatement pss)
							throws SQLException {
						pss.setInt(1, feeMaster.getDbTs() + 1);
						pss.setString(2, feeMaster.getInstId());
						pss.setString(3, feeMaster.getBranchId());
						pss.setString(4, feeMaster.getAcTerm().trim());
						pss.setString(5, feeMaster.getFeeCategory().trim());
						pss.setString(6, feeMaster.getFeeTerm().trim());
						pss.setString(7, feeMaster.getFeeType());
						pss.setString(8, feeMaster.getCourse().trim());
						pss.setString(9, feeMaster.getTerm().trim());
						pss.setString(10, feeMaster.getCourseVariant().trim());
						pss.setInt(11, feeMaster.getFeeAmt());
						pss.setString(12, feeMaster.getDelFlag().trim());
						pss.setString(13, feeMaster.getrModId().trim());
						pss.setInt(14, feeMaster.getDbTs());
						pss.setString(15, feeMaster.getInstId());
						pss.setString(16, feeMaster.getBranchId());
						pss.setString(17, feeMaster.getAcTerm().trim());
						pss.setString(18, feeMaster.getFeeCategory().trim());
						pss.setString(19, feeMaster.getFeeTerm().trim());
						pss.setString(20, feeMaster.getFeeType());
						pss.setString(21, feeMaster.getCourse().trim());
						pss.setString(22, feeMaster.getTerm().trim());

					}

				});
		if (updateRecs == 0) {
			throw new UpdateFailedException();

		}

	}

	@Override
	public FeeMaster selectFeeMaster(final FeeMasterKey FeeMasterKey)
			throws NoDataFoundException {
		System.out.println("Inside select method");
		System.out.println("Fee Category Key object values :"
				+ FeeMasterKey.toString());
		List<String> data = new ArrayList<String>();
		StringBuffer sql = new StringBuffer();
		sql.append("select ").append(" DB_TS,").append("INST_ID,")
				.append("BRANCH_ID,").append("AC_TERM,").append("FEE_CATGRY,")
				.append("FEE_TERM,").append("FEE_TYPE,").append("COURSE,")
				.append("TERM,").append("CV_SPEC,").append("FEE_AMT,").append("DEL_FLG,")
				.append("R_MOD_ID,").append("R_MOD_TIME,").append("R_CRE_ID,")
				.append("R_CRE_TIME").append(" from fmst ").append(" where")
				.append("   INST_ID= ?").append(" and  BRANCH_ID= ?")
				.append(" and  FEE_CATGRY= ?").append(" and  FEE_TYPE= ?")
				.append(" and  DEL_FLG=?");
		data.add(FeeMasterKey.getInstId());
		data.add(FeeMasterKey.getBranchId());
		data.add(FeeMasterKey.getFeeCategory());
		data.add(FeeMasterKey.getFeeType());
		data.add("N");

		String[] array = data.toArray(new String[data.size()]);
		FeeMaster FeeMaster = null;
		FeeMaster = (FeeMaster) getJdbcTemplate().query(sql.toString(), array,
				new ResultSetExtractor<FeeMaster>() {

					@Override
					public FeeMaster extractData(ResultSet rs)
							throws SQLException, DataAccessException {
						FeeMaster FeeMaster = null;
						if (rs.next()) {

							FeeMaster = new FeeMaster();
							FeeMaster.setDbTs(rs.getInt("DB_TS"));
							FeeMaster.setInstId(rs.getString("INST_ID"));
							FeeMaster.setBranchId(rs.getString("BRANCH_ID"));
							FeeMaster.setAcTerm(rs.getString("AC_TERM"));
							FeeMaster.setFeeCategory(rs.getString("FEE_CATGRY"));
							FeeMaster.setFeeTerm(rs.getString("FEE_TERM"));
							FeeMaster.setFeeType(rs.getString("FEE_TYPE"));
							FeeMaster.setCourse(rs.getString("COURSE"));
							FeeMaster.setTerm(rs.getString("TERM"));
							FeeMaster.setCourseVariant(rs.getString("CV_SPEC"));
							FeeMaster.setFeeAmt(rs.getInt("FEE_AMT"));
							FeeMaster.setDelFlag(rs.getString("DEL_FLG"));
							FeeMaster.setrModId(rs.getString("R_MOD_ID"));
							FeeMaster.setrModTime(rs.getString("R_MOD_TIME"));
							FeeMaster.setrCreId(rs.getString("R_CRE_ID"));
							FeeMaster.setrCreTime(rs.getString("R_CRE_TIME"));
						}
						return FeeMaster;
					}

				});

		if (FeeMaster == null) {
			throw new NoDataFoundException();
		}

		return FeeMaster;
	}
	
	
	@Override
	public void deletFeeMaster(final FeeMaster feeMaster) throws UpdateFailedException {
		System.out.println("Inside update method");
		System.out.println("Fee Category object values :"
				+ feeMaster.toString());
		
		StringBuffer sql = new StringBuffer();

		sql.append("delete from fmst ").append(" where")
				.append(" DB_TS= ?").append(" and  INST_ID= ?")
				.append(" and  BRANCH_ID= ?") .append(" and AC_TERM= ?  ") .append(" and  FEE_CATGRY= ?")
				.append(" and FEE_TERM= ?").append(" and FEE_TYPE= ?")
				.append(" and COURSE= ? ").append(" and TERM= ? ").append(" and CV_SPEC= ? ")
				.append(" and  DEL_FLG='N'");

		int updateRecs = getJdbcTemplate().update(sql.toString(),
				new PreparedStatementSetter() {

					@Override
					public void setValues(PreparedStatement pss)
							throws SQLException {
						pss.setInt(1, feeMaster.getDbTs());
						pss.setString(2, feeMaster.getInstId());
						pss.setString(3, feeMaster.getBranchId());
						pss.setString(4, feeMaster.getAcTerm().trim());
						pss.setString(5, feeMaster.getFeeCategory().trim());
						pss.setString(6, feeMaster.getFeeTerm().trim());
						pss.setString(7, feeMaster.getFeeType());
						pss.setString(8, feeMaster.getCourse().trim());
						pss.setString(9, feeMaster.getTerm().trim());
						pss.setString(10, feeMaster.getCourseVariant().trim());

					}

				});
		if (updateRecs == 0) {
			throw new UpdateFailedException();

		}

	}

}
