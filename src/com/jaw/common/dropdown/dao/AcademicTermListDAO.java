package com.jaw.common.dropdown.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.jaw.common.constants.ApplicationConstant;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.core.dao.AcademicTermDetails;
import com.jaw.core.dao.AcademicTermDetailsKey;
import com.jaw.core.dao.CourseMaster;
import com.jaw.framework.dao.BaseDao;

import com.jaw.common.constants.CommonCodeConstant;
@Repository
public class AcademicTermListDAO extends BaseDao implements IAcademicTermListDAO {

	// Logging
	Logger logger = Logger.getLogger(AcademicTermListDAO.class);

	
	

	@Override
	public List<AcademicTermDetails> selectAcademicTermList(final AcademicTermDetailsKey academicTermDetailsKey)
			throws NoDataFoundException {
		logger.debug("Inside Academic Term Tag select method");

		StringBuffer sql = new StringBuffer();
		sql.append("select ")
				.append("CM_CODE,")
				.append("CODE_DESC ")
				.append(" from cocd c,atdt a ")
				.append("where ")
				.append("c.CM_CODE=a.AC_TERM")
				.append(" and a.inst_id = c.inst_id")
				.append(" and a.branch_id = c.branch_id")
				.append(" and a.del_flg = c.del_flg")
				.append(" and c.INST_ID=?")
				.append(" and c.BRANCH_ID=?")
				.append(" and a.DEL_FLG=?  ")
				.append(" and c.code_type=?  ")
				.append(" order by CODE_DESC asc");
			List<AcademicTermDetails>   academicTermDetails = null;

		academicTermDetails = getJdbcTemplate().query(sql.toString(),
				new PreparedStatementSetter() {

					@Override
					public void setValues(PreparedStatement pss)
							throws SQLException {
						pss.setString(1,academicTermDetailsKey.getInstId() );
						pss.setString(2,academicTermDetailsKey.getBranchId());
						pss.setString(3, "N");
						pss.setString(4, CommonCodeConstant.ACADEMIC_TERM);

					}

				}, new AcademicTermDetailsRowmapper());		
		if (academicTermDetails.size() == 0) {
			throw new NoDataFoundException();
		}
		return academicTermDetails;
	}
	
	
	
	
	
	
	
	
@Override
	public Map<String, String> selectTermForCurAcademicYear(final String instId,
			final String branchId)
			throws NoDataFoundException {
		logger.debug("Inside Academic Term Tag select method");
		logger.debug("Inst id :" + instId + " branch id :" + branchId);
		StringBuffer sql = new StringBuffer();
		sql.append("select ")
				.append("CM_CODE,")
				.append("CODE_DESC ")
				.append(" from cocd c,atdt a ")
				.append("where ")
				.append("c.CM_CODE=a.AC_TERM")
				.append(" and c.INST_ID=?")
				.append(" and c.BRANCH_ID=?")
				.append(" and a.AC_TERM_STS=?")
				.append(" and a.DEL_FLG=?  ")
				.append(" order by CM_CODE ");
		
		Map<String, String> academicTermDetails = null;
		
		academicTermDetails = getJdbcTemplate().query(sql.toString(),
				new PreparedStatementSetter() {
					
					@Override
					public void setValues(PreparedStatement pss)
							throws SQLException {
						pss.setString(1, instId);
						pss.setString(2, branchId);
						pss.setString(3, ApplicationConstant.CURRENT_YEAR_CONSTANT);
						pss.setString(4, "N");
						
					}
					
				}, new TermResultSetExtractor());
		if (academicTermDetails.size() == 0) {
			throw new NoDataFoundException();
		}
		return academicTermDetails;
	}








@Override
public List<AcademicTermDetails> selectPresentAndFutureAcademicTerm(
		final AcademicTermDetailsKey academicTermDetailsKey)
		throws NoDataFoundException {

	logger.debug("Inside Academic Term Tag select method");
	StringBuffer sql = new StringBuffer();
			sql.append("select ")
			.append("CM_CODE,")
			.append("CODE_DESC ")
			.append(" from cocd c,atdt a ")
			.append("where ")
			.append("c.CM_CODE=a.AC_TERM")
			.append(" and a.inst_id = c.inst_id")
			.append(" and a.branch_id = c.branch_id")
			.append(" and a.del_flg = c.del_flg")
			.append(" and c.INST_ID=?")
			.append(" and c.BRANCH_ID=?")
			.append(" and a.DEL_FLG=?  ")
			.append(" and c.CODE_TYPE=?  ")
			.append(" and a.AC_TERM_STS in ('P','F')  ")
			.append(" order by CODE_DESC asc");
			/*.append(" and c.code_type=?  ")
			.append(" order by CODE_DESC asc");*/
		List<AcademicTermDetails>   academicTermDetails = null;

	academicTermDetails = getJdbcTemplate().query(sql.toString(),
			new PreparedStatementSetter() {

				@Override
				public void setValues(PreparedStatement pss)
						throws SQLException {
					pss.setString(1,academicTermDetailsKey.getInstId() );
					pss.setString(2,academicTermDetailsKey.getBranchId());
					pss.setString(3, "N");
					pss.setString(4, CommonCodeConstant.ACADEMIC_TERM);

				}

			}, new AcademicTermDetailsRowmapper());
	if (academicTermDetails.size() == 0) {
		throw new NoDataFoundException();
	}	
	return academicTermDetails;

}








@Override
public List<AcademicTermDetails> selectPresentAcademicTerm(
		final AcademicTermDetailsKey academicTermDetailsKey)
		throws NoDataFoundException {
	logger.debug("Inside Academic Term Tag select method");
	logger.debug("Inst id :" + academicTermDetailsKey.getInstId() + " branch id :" +  academicTermDetailsKey.getInstId());
	StringBuffer sql = new StringBuffer();
	

	
	sql.append("select ")
			.append("CM_CODE,")
			.append("CODE_DESC ")
			.append(" from cocd c,atdt a ")
			.append("where ")
			.append("c.CM_CODE=a.AC_TERM")
			.append(" and a.inst_id = c.inst_id")
			.append(" and a.branch_id = c.branch_id")
			.append(" and a.del_flg = c.del_flg")
			.append(" and c.INST_ID=?")
			.append(" and c.BRANCH_ID=?")
			.append(" and a.AC_TERM_STS=?")
			.append(" and a.DEL_FLG=?  ")
			.append(" and c.code_type=?  ");
	
	List<AcademicTermDetails> academicTermDetails = null;
	
	academicTermDetails = getJdbcTemplate().query(sql.toString(),
			new PreparedStatementSetter() {
				
				@Override
				public void setValues(PreparedStatement pss)
						throws SQLException {
					pss.setString(1, academicTermDetailsKey.getInstId());
					pss.setString(2, academicTermDetailsKey.getBranchId());
					pss.setString(3, ApplicationConstant.CURRENT_YEAR_CONSTANT);
					pss.setString(4, "N");
					pss.setString(5, CommonCodeConstant.ACADEMIC_TERM);
					
				}
				
			}, new PresentAcademicTermRowmapper());
	if (academicTermDetails.size() == 0) {
		throw new NoDataFoundException();
	}
	return academicTermDetails;
}
	
}

class TermResultSetExtractor implements ResultSetExtractor<Map<String, String>> {
	@Override
	public Map<String, String> extractData(ResultSet rs) throws SQLException {
		
		Map<String, String> map = new LinkedHashMap<String, String>();
		while (rs.next()) {
			String key = rs.getString("CM_CODE");
			String value = rs.getString("CODE_DESC");
			map.put(key, value);
		}
		return map;
	}
}





class AcademicTermDetailsRowmapper implements RowMapper<AcademicTermDetails> {

	@Override
	public AcademicTermDetails mapRow(ResultSet rs, int arg1) throws SQLException {

		AcademicTermDetails academicTermDetails = new AcademicTermDetails();
		academicTermDetails.setAcTerm((rs.getString("CM_CODE")));
		academicTermDetails.setAcYear(rs.getString("CODE_DESC"));

		return academicTermDetails;
	}
	
}
class PresentAcademicTermRowmapper implements RowMapper<AcademicTermDetails> {

	@Override
	public AcademicTermDetails mapRow(ResultSet rs, int arg1) throws SQLException {

		AcademicTermDetails academicTermDetails = new AcademicTermDetails();
		academicTermDetails.setAcTerm((rs.getString("CM_CODE")));
		academicTermDetails.setAcYear(rs.getString("CODE_DESC"));

		return academicTermDetails;
	}

}