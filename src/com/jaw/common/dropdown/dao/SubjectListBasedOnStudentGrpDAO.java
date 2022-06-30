package com.jaw.common.dropdown.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.jaw.admin.dao.BranchMaster;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.core.dao.AcademicTermDetails;
import com.jaw.core.dao.CourseSubLink;
import com.jaw.framework.dao.BaseDao;
import com.jaw.framework.sessCache.UserSessionDetails;

@Repository
public class SubjectListBasedOnStudentGrpDAO extends BaseDao implements
		ISubjectListBasedOnStudentGrpDAO {
	// Logging
	Logger logger = Logger.getLogger(SubjectListBasedOnStudentGrpDAO.class);

	@Override
	public List<CourseSubLink> getSubListForStudentGrp(final String stdGrpId,final String staffId,final UserSessionDetails userSessionDetails)
			throws NoDataFoundException {
		logger.debug("Inside Subject  select for Special Class method");
		Map<String, String> subMap = new HashMap<String, String>();
		StringBuffer sql = new StringBuffer();
		
		if (staffId == null) {
			sql.append("select ").append(" distinct(crsl.SUB_ID), ").append(" REQUIRES_LAB,").append(" crsl.CRSL_ID, ")
				.append("SUB_TYPE, ").append("SUB_NAME ").append(" from crsl,stgm,sbjm ").append(" where ")
					.append(" crsl.INST_ID = stgm.INST_ID")
					.append(" and  crsl.INST_ID = sbjm.INST_ID")
					.append(" and crsl.BRANCH_ID = stgm.BRANCH_ID")
					.append(" and crsl.BRANCH_ID = sbjm.BRANCH_ID")
					.append(" and crsl.DEL_FLG = stgm.DEL_FLG")
					.append(" and crsl.DEL_FLG = sbjm.DEL_FLG")
					.append(" and crsl.COURSEMASTER_ID = stgm.COURSEMASTER_ID")
					.append(" and crsl.TERM_ID = stgm.TERM_ID")
					.append(" and crsl.SUB_ID = sbjm.SUB_ID")
					.append(" and crsl.INST_ID =?")
					.append(" and crsl.BRANCH_ID =?")
					.append(" and crsl.DEL_FLG = ?")
					.append(" and STUDENTGRP_ID = ?")
					.append(" group by crsl.sub_id order by SUB_NAME asc");
		} else {
			sql.append("select ").append(" distinct(crsl.SUB_ID), ").append(" REQUIRES_LAB,").append(" crsl.CRSL_ID, ")
				.append("SUB_TYPE, ").append("SUB_NAME ").append(" from crsl,stgm,sbjm,crcl ").append(" where ")
					.append(" crsl.INST_ID = stgm.INST_ID and")
					.append(" crsl.INST_ID = sbjm.INST_ID and")
					.append(" crsl.BRANCH_ID = stgm.BRANCH_ID and ")
					.append(" crsl.BRANCH_ID = sbjm.BRANCH_ID and ")
					.append(" crsl.DEL_FLG = stgm.DEL_FLG and")
					.append(" crsl.DEL_FLG = sbjm.DEL_FLG and")
					.append(" crsl.INST_ID = crcl.INST_ID and ")
					.append(" crsl.BRANCH_ID = crcl.BRANCH_ID and ")
					.append(" crsl.DEL_FLG = crcl.DEL_FLG and ")
					.append(" crsl.CRSL_ID = crcl.CRSL_ID and ")
					.append(" stgm.STUDENTGRP_ID = crcl.STUDENTGRP_ID and ")
					.append(" crsl.COURSEMASTER_ID = stgm.COURSEMASTER_ID and ")
					.append(" crsl.TERM_ID = stgm.TERM_ID and ")
					.append(" crsl.SUB_ID = sbjm.SUB_ID and ")
					.append(" crsl.INST_ID = ? and ")
					.append(" crsl.BRANCH_ID = ? and ")
					.append(" crsl.DEL_FLG = ? and ")
					.append(" stgm.STUDENTGRP_ID = ? and ")
					.append(" crcl.staff_id=? ")
					.append(" group by crsl.sub_id order by SUB_NAME asc ; ");
		}
		List<CourseSubLink> courseSubLinkDetails = null;

		courseSubLinkDetails = getJdbcTemplate().query(sql.toString(),
				new PreparedStatementSetter() {

					@Override
					public void setValues(PreparedStatement pss)
							throws SQLException {
						pss.setString(1, userSessionDetails.getInstId());
						pss.setString(2, userSessionDetails.getBranchId());
						pss.setString(3, "N");
						pss.setString(4, stdGrpId);
						if (staffId != null) {
							pss.setString(5, staffId);
						}

					}

				}, new CourseSubLinkRowmapper());
		if (courseSubLinkDetails.size() == 0) {			
			throw new NoDataFoundException();
			
		}

		return courseSubLinkDetails;
	}

}

class CourseSubLinkRowmapper implements RowMapper<CourseSubLink> {

	@Override
	public CourseSubLink mapRow(ResultSet rs, int arg1) throws SQLException {

		CourseSubLink courseSubLink = new CourseSubLink();
		courseSubLink.setCrslId(rs.getString("CRSL_ID"));
		courseSubLink.setSubId(rs.getString("SUB_NAME"));
		courseSubLink.setSubType(rs.getString("SUB_TYPE"));
		courseSubLink.setRequiresLab(rs.getString("REQUIRES_LAB"));

		return courseSubLink;
	}
}