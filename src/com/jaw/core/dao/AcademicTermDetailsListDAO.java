package com.jaw.core.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.framework.dao.BaseDao;

//Holiday Maintenance List DAO class
@Repository
public class AcademicTermDetailsListDAO extends BaseDao implements IAcademicTermDetailsListDAO {
	Logger logger = Logger.getLogger(AcademicTermDetailsListDAO.class);
	
	@Override
	public List<AcademicTermList> getAcademicTermList(final String instId, final String branchId)
			throws NoDataFoundException {
		logger.debug("Inside select method");
		StringBuffer sql = new StringBuffer();
		sql.append("select ")
				.append("DB_TS,")
				.append("BRANCH_ID,")
				.append("AC_YEAR,")
				.append("AC_TERM,")
				.append("TERM_START_DATE,")
				.append("TERM_END_DATE,")
				.append("WEEKLY_HOLIDAY,")
				.append("AC_TERM_STS ")
				.append(" from atdt ")
				.append("where ")
				.append("  INST_ID= ?")
				.append(" and  BRANCH_ID= ?")
				.append(" and DEL_FLG=? ");
		
		List<AcademicTermList> result = getJdbcTemplate()
				.query(sql.toString(), new PreparedStatementSetter() {
					
					@Override
					public void setValues(PreparedStatement pss)
							throws SQLException {
						
						pss.setString(1, instId);
						pss.setString(2, branchId);
						pss.setString(3, "N");
						
					}
				}, new AcademicTermListListRowMapper());
		if (result.size() == 0) {
			throw new NoDataFoundException();
		}
		return result;
	}
	
	@Override
	public List<AcademicTermDetails> getAcaTrmBasedBranchForHlyGen(
			final AcademicTermDetailsKey academicTermDetailsKey) throws NoDataFoundException {
		logger.debug("Inside select method");
		logger.debug("Academic Term Details Key object values :"
				+ academicTermDetailsKey.toString());
		
		StringBuffer sql = new StringBuffer();
		sql.append("select ")
				.append("DISTINCT(CM_CODE),")
				.append("CODE_DESC ")
				.append(" from cocd c,atdt a ")
				.append("where ")
				.append("c.CM_CODE=a.AC_TERM ")
				.append("and a.INST_ID=c.INST_ID ")
				.append("and a.BRANCH_ID=c.BRANCH_ID ")
				.append(" and a.INST_ID=?")
				.append(" and a.BRANCH_ID=?")
				.append(" and a.DEL_FLG='N'  ")
				.append(" and  (a.AC_TERM_STS='F' or a.AC_TERM_STS='P')");
		List<AcademicTermDetails> selectedListAcaTrmDForBranch = getJdbcTemplate()
				.query(sql.toString(), new PreparedStatementSetter() {
					
					@Override
					public void setValues(PreparedStatement ps)
							throws SQLException {
						ps.setString(1, academicTermDetailsKey.getInstId().trim());
						ps.setString(2, academicTermDetailsKey.getBranchId().trim());
						
					}
					
				}, new AcademicTermDetailsSelectForBranchRowMapper());
		
		if (selectedListAcaTrmDForBranch == null) {
			throw new NoDataFoundException();
		}
		return selectedListAcaTrmDForBranch;
	}
	
}

class AcademicTermDetailsSelectForBranchRowMapper implements RowMapper<AcademicTermDetails> {
	
	@Override
	public AcademicTermDetails mapRow(ResultSet rs, int arg1) throws SQLException {
		AcademicTermDetails academicTermDetails = new AcademicTermDetails();
		academicTermDetails.setAcTerm((rs.getString("CM_CODE")));
		academicTermDetails.setAcYear(rs.getString("CODE_DESC"));
		return academicTermDetails;
	}
}

class AcademicTermListListRowMapper implements RowMapper<AcademicTermList> {
	
	@Override
	public AcademicTermList mapRow(ResultSet rs, int arg1) throws SQLException {
		
		AcademicTermList academicTerm = new AcademicTermList();
		
		academicTerm.setDbTs(rs.getInt("DB_TS"));
		academicTerm.setBranchId(rs.getString("BRANCH_ID"));
		academicTerm.setAcYear(rs.getString("AC_YEAR"));
		academicTerm.setAcTerm(rs.getString("AC_TERM"));
		academicTerm.setTermStartDate(rs.getString("TERM_START_DATE"));
		academicTerm.setTermEndDate(rs.getString("TERM_END_DATE"));
		academicTerm.setWeeklyHoliday(rs.getString("WEEKLY_HOLIDAY"));
		academicTerm.setAcTermSts(rs.getString("AC_TERM_STS"));
		return academicTerm;
	}
	
}
