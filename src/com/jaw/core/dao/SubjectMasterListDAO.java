package com.jaw.core.dao;

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

import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.framework.dao.BaseDao;

@Repository
public class SubjectMasterListDAO extends BaseDao implements
		ISubjectMasterListDAO {
	Logger logger = Logger.getLogger(SubjectMasterListDAO.class);

	@Override
	public List<SubjectMaster> getSubjectMasterList(
			final SubjectMasterKey subjectMasterKey)
			throws NoDataFoundException {			
			
		
		logger.debug("DAO :Inside SubjectMaster List select method");
		StringBuffer sql = new StringBuffer();
		sql.append("select ").append("SUB_ID,").append("SUB_NAME,")
				.append("CUST_CODE,").append("SHORT_CODE,").append("DEPT_ID,")
				.append("COURSEMASTER_ID,")
				.append("IS_COMP,")
				.append("IS_ELECTIVE,").append("IS_LANG,").append("IS_REL,").append("IS_PRE_ACA_SUB ")
				.append(" from sbjm ").append(" where").append("  INST_ID= ?")
				.append(" and  BRANCH_ID= ?").append(" and  DEL_FLG=?");

		List<SubjectMaster> selectedListSubMaster = getJdbcTemplate().query(
				sql.toString(), new PreparedStatementSetter() {

					@Override
					public void setValues(PreparedStatement ps)
							throws SQLException {
						ps.setString(1, subjectMasterKey.getInstId());
						ps.setString(2, subjectMasterKey.getBranchId());
						ps.setString(3, "N");
					}

				}, new SubjectMasterSelectRowMapper());
		
		if (selectedListSubMaster.size() == 0) {
			logger.error("No  record found");
			throw new NoDataFoundException();
		}
		logger.debug("DAO : Subject Master List value"
				+ selectedListSubMaster.size());
		return selectedListSubMaster;
	}

	@Override
	public Map<String, String> getSubjectBasedOnSubType(
			final SubjectMasterKey subjectMasterKey)
			throws NoDataFoundException {
		logger.debug("DAO :Inside SubjectMaster List select method");
		StringBuffer sql = new StringBuffer();
		sql.append("select ").append("SUB_ID,").append("SUB_NAME ")
				.append(" from sbjm ").append(" where").append("  INST_ID= ?")
				.append(" and  BRANCH_ID= ?");
		if (subjectMasterKey.getSubType() != "") {
			if (subjectMasterKey.getSubType().equals("C")) {
				sql.append(" and  IS_COMP= 'Y'");
			} else if ((subjectMasterKey.getSubType().equals("L2"))
					|| ((subjectMasterKey.getSubType().equals("L3")))) {
				sql.append(" and  IS_LANG= 'Y'");
			} else if ((subjectMasterKey.getSubType().equals("E1"))
					|| ((subjectMasterKey.getSubType().equals("E2")))) {
				sql.append(" and  IS_ELECTIVE= 'Y'");
			}
		}
		System.out.println("Subject Query:"+sql.toString());
		sql.append(" and  DEL_FLG=? order by sub_name");
		Map<String, String> selectedListSubMaster=new LinkedHashMap<String, String>();
		if (subjectMasterKey.getSubType() != "") {
	 selectedListSubMaster = getJdbcTemplate().query(
				sql.toString(), new PreparedStatementSetter() {

					@Override
					public void setValues(PreparedStatement ps)
							throws SQLException {
						ps.setString(1, subjectMasterKey.getInstId());
						ps.setString(2, subjectMasterKey.getBranchId());
						ps.setString(3, "N");
					}

				}, new SubjectListExtractor());
		}
		if (selectedListSubMaster.size() == 0) {
			logger.error("No  record found");
			throw new NoDataFoundException();
		}
		logger.debug("DAO : Subject Master List value"
				+ selectedListSubMaster.size());
		return selectedListSubMaster;
	}
	
	@Override
	public Map<String, String> getSubjectBasedOnSubTypeAndCourseId(
			final SubjectMasterKey subjectMasterKey, final String courseId)
			throws NoDataFoundException {
		logger.debug("DAO :Inside SubjectMaster List select method");
		StringBuffer sql = new StringBuffer();
		sql.append("select ").append("SUB_ID,").append("SUB_NAME ")
				.append(" from sbjm ").append(" where").append("  INST_ID= ?")
				.append(" and  BRANCH_ID= ?").append(" and  COURSEMASTER_ID= ?");
		if (subjectMasterKey.getSubType() != "") {
			if (subjectMasterKey.getSubType().equals("C")) {
				sql.append(" and  IS_COMP= 'Y'");
			} else if ((subjectMasterKey.getSubType().equals("L2"))
					|| ((subjectMasterKey.getSubType().equals("L3")))) {
				sql.append(" and  IS_LANG= 'Y'");
			} else if ((subjectMasterKey.getSubType().equals("E1"))
					|| ((subjectMasterKey.getSubType().equals("E2")))) {
				sql.append(" and  IS_ELECTIVE= 'Y'");
			}
		}
		System.out.println("Subject Query:"+sql.toString());
		sql.append(" and  DEL_FLG=? order by sub_name");
		Map<String, String> selectedListSubMaster=new LinkedHashMap<String, String>();
		if (subjectMasterKey.getSubType() != "") {
	 selectedListSubMaster = getJdbcTemplate().query(
				sql.toString(), new PreparedStatementSetter() {

					@Override
					public void setValues(PreparedStatement ps)
							throws SQLException {
						ps.setString(1, subjectMasterKey.getInstId());
						ps.setString(2, subjectMasterKey.getBranchId());
						ps.setString(3, courseId);
						ps.setString(4, "N");
					}

				}, new SubjectListExtractor());
		}
		if (selectedListSubMaster.size() == 0) {
			logger.error("No  record found");
			throw new NoDataFoundException();
		}
		logger.debug("DAO : Subject Master List value"
				+ selectedListSubMaster.size());
		return selectedListSubMaster;
	}

}

class SubjectMasterSelectRowMapper implements RowMapper<SubjectMaster> {

	@Override
	public SubjectMaster mapRow(ResultSet rs, int arg1) throws SQLException {
		SubjectMaster subjectMaster = new SubjectMaster();

		subjectMaster.setSub_Id(rs.getString("SUB_ID"));
		subjectMaster.setSub_Name(rs.getString("SUB_NAME"));
		subjectMaster.setCust_Code(rs.getString("CUST_CODE"));
		subjectMaster.setShort_Code(rs.getString("SHORT_CODE"));
		subjectMaster.setDepartment(rs.getString("DEPT_ID"));
		subjectMaster.setCourseName(rs.getString("COURSEMASTER_ID"));
		subjectMaster.setIs_Com(rs.getString("IS_COMP"));
		subjectMaster.setIs_Elective(rs.getString("IS_ELECTIVE"));
		subjectMaster.setIs_Lang(rs.getString("IS_LANG"));
		subjectMaster.setIs_rel(rs.getString("IS_REL"));
		subjectMaster.setIsPreAcaSubject(rs.getString("IS_PRE_ACA_SUB"));
		return subjectMaster;
	}

}


class SubjectListExtractor implements ResultSetExtractor<Map<String, String>> {
	@Override
	public Map<String, String> extractData(ResultSet rs) throws SQLException {
		
		Map<String, String> map = new LinkedHashMap<String, String>();
		while (rs.next()) {
			String key = rs.getString("SUB_ID");
			String value = rs.getString("SUB_NAME");
			map.put(key, value);
		}
		return map;
	}
}
