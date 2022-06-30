package com.jaw.common.dropdown.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.core.dao.StudentGroupMaster;
import com.jaw.framework.dao.BaseDao;
import com.jaw.framework.sessCache.UserSessionDetails;

@Repository
public class StudentGroupListTagDAO extends BaseDao implements
		IStudentGroupListTagDAO {

	// Logging
	Logger logger = Logger.getLogger(StudentGroupListTagDAO.class);

	@Override
	public List<StudentGroupMaster> selectStudentGroupList(
			final UserSessionDetails userSessionDetails)
			throws NoDataFoundException {

		logger.debug("Inside Student Group Tag select method");

		StringBuffer sql = new StringBuffer();
		sql.append("select ").append("STUDENTGRP_ID,").append("STUDENT_GRP ")
				.append(" from stgm ").append("where ").append("DEL_FLG=?  ")
				.append(" and INST_ID=?  ").append(" and BRANCH_ID=?")
				.append(" order by STUDENT_GRP asc");
		logger.debug("select query :" + sql.toString());

		List<StudentGroupMaster> sGrpMaster = null;

		sGrpMaster = getJdbcTemplate().query(sql.toString(),
				new PreparedStatementSetter() {

					@Override
					public void setValues(PreparedStatement pss)
							throws SQLException {
						pss.setString(1, "N");
						pss.setString(2, userSessionDetails.getInstId());
						pss.setString(3, userSessionDetails.getBranchId());

					}

				}, new StudentGroupMasterRowmapper());
		if (sGrpMaster.size() == 0) {
			throw new NoDataFoundException();
		}
		return sGrpMaster;
	}

	@Override
	public List<StudentGroupMaster> selectStudentGroupListForStaff(
			final UserSessionDetails userSessionDetails, final String acTerm) {

		logger.debug("Inside Student Group Tag For Staff select method");
		logger.debug("Inside Student Group Tag For Staff select method values :"
				+ userSessionDetails.getLinkId() + "academic Term :" + acTerm);
		logger.info("Inside Student Group Tag For Staff select method");
		logger.info("Inside Student Group Tag For Staff select method values :"
				+ userSessionDetails.getLinkId() + "academic Term :" + acTerm);		
		StringBuffer sql = new StringBuffer();
		sql.append("select ").append("distinct(crcl.STUDENTGRP_ID),")
				.append("STUDENT_GRP ").append(" from crcl,stgm ")
				.append("where ").append(" crcl.INST_ID=stgm.INST_ID")
				.append(" and crcl.BRANCH_ID=stgm.BRANCH_ID")
				.append(" and crcl.DEL_FLG=stgm.DEL_FLG")
				.append(" and crcl.STUDENTGRP_ID=stgm.STUDENTGRP_ID")
				.append(" and crcl.DEL_FLG=?  ")
				.append(" and crcl.INST_ID=?  ")
				.append(" and crcl.BRANCH_ID=?").append(" and crcl.AC_TERM=?")
				.append(" and crcl.STAFF_ID=?")
				.append(" order by STUDENT_GRP asc");
		logger.debug("select query :" + sql.toString());

		List<StudentGroupMaster> sGrpMaster = null;

		sGrpMaster = getJdbcTemplate().query(sql.toString(),
				new PreparedStatementSetter() {

					@Override
					public void setValues(PreparedStatement pss)
							throws SQLException {
						pss.setString(1, "N");
						pss.setString(2, userSessionDetails.getInstId());
						pss.setString(3, userSessionDetails.getBranchId());
						pss.setString(4, acTerm);
						pss.setString(5, userSessionDetails.getLinkId());
						}

				}, new StudentGroupMasterRowmapper());	
		return sGrpMaster;
	}

	@Override
	public List<StudentGroupMaster> selectStudentGroupListForClassTeacher(
			final UserSessionDetails userSessionDetails, final String studentGrpId,final String acTerm) {

		
		StringBuffer sql = new StringBuffer();
		sql.append("select ").append("STUDENTGRP_ID,")
				.append("STUDENT_GRP ").append(" from stgm ")
				.append("where ")
				.append("  INST_ID=?  ")
				.append(" and BRANCH_ID=?")
				.append(" and STUDENTGRP_ID=?")
				.append(" and DEL_FLG=?")
				.append(" order by STUDENT_GRP asc");
		logger.debug("select query :" + sql.toString());

		List<StudentGroupMaster> sGrpMaster = null;

		sGrpMaster = getJdbcTemplate().query(sql.toString(),
				new PreparedStatementSetter() {

					@Override
					public void setValues(PreparedStatement pss)
							throws SQLException {
						pss.setString(1, userSessionDetails.getInstId());
						pss.setString(2, userSessionDetails.getBranchId());
						pss.setString(3, studentGrpId);
						pss.setString(4, "N");
						}

				}, new StudentGroupMasterRowmapper());	
		return sGrpMaster;
	}

	@Override
	public List<StudentGroupMaster> selectStudentGroupListForStaffDashBoard(
			final UserSessionDetails userSessionDetails, final String acTerm) {

		logger.debug("Inside Student Group Tag For Staff Dashboard select method");
		logger.debug("Inside Student Group Tag For Staff select method values :"
				+ userSessionDetails.getLinkId() + "academic Term :" + acTerm);
		/*select distinct(crcl.STUDENTGRP_ID) as studentGrpId,STUDENT_GRP  from crcl,stgm where  crcl.INST_ID=stgm.INST_ID
				 and crcl.BRANCH_ID=stgm.BRANCH_ID and crcl.DEL_FLG=stgm.DEL_FLG
				 and crcl.STUDENTGRP_ID=stgm.STUDENTGRP_ID and crcl.DEL_FLG='N'
				 and crcl.INST_ID='ASC'   and crcl.BRANCH_ID='BR001' and crcl.AC_TERM='AT3' and crcl.STAFF_ID='stf114'  union

select clta.STUDENTGRP_ID,STUDENT_GRP from clta,stgm where clta.INST_ID=stgm.INST_ID
				 and clta.BRANCH_ID=stgm.BRANCH_ID and clta.DEL_FLG=stgm.DEL_FLG
				 and clta.STUDENTGRP_ID=stgm.STUDENTGRP_ID and clta.DEL_FLG='N'
				 and clta.INST_ID='ASC'   and clta.BRANCH_ID='BR001' and clta.AC_TERM='AT3' and clta.STAFF_ID='stf114' order by STUDENT_GRP asc;*/
		
		StringBuffer sql = new StringBuffer();
		sql.append("select ").append("distinct(crcl.STUDENTGRP_ID) as studentGrpId,")
				.append("STUDENT_GRP ").append(" from crcl,stgm ")
				.append("where ").append(" crcl.INST_ID=stgm.INST_ID")
				.append(" and crcl.BRANCH_ID=stgm.BRANCH_ID")
				.append(" and crcl.DEL_FLG=stgm.DEL_FLG")
				.append(" and crcl.STUDENTGRP_ID=stgm.STUDENTGRP_ID")
				.append(" and crcl.DEL_FLG='N'  ")
				.append(" and crcl.INST_ID=?  ")
				.append(" and crcl.BRANCH_ID=?").append(" and crcl.AC_TERM=?")
				.append(" and crcl.STAFF_ID=?")
				.append(" union ");
		sql.append("select ").append("distinct(clta.STUDENTGRP_ID) as studentGrpId,")
		.append("STUDENT_GRP ").append(" from clta,stgm ")
		.append("where ").append(" clta.INST_ID=stgm.INST_ID")
		.append(" and clta.BRANCH_ID=stgm.BRANCH_ID")
		.append(" and clta.DEL_FLG=stgm.DEL_FLG")
		.append(" and clta.STUDENTGRP_ID=stgm.STUDENTGRP_ID")
		.append(" and clta.DEL_FLG='N'  ")
		.append(" and clta.INST_ID=?  ")
		.append(" and clta.BRANCH_ID=?").append(" and clta.AC_TERM=?")
		.append(" and clta.STAFF_ID=?")
		.append(" order by STUDENT_GRP asc ");
		logger.debug("select query :" + sql.toString());

		List<StudentGroupMaster> sGrpMaster = null;

		sGrpMaster = getJdbcTemplate().query(sql.toString(),
				new PreparedStatementSetter() {

					@Override
					public void setValues(PreparedStatement pss)
							throws SQLException {
						pss.setString(1, userSessionDetails.getInstId());
						pss.setString(2, userSessionDetails.getBranchId());
						pss.setString(3, acTerm);
						pss.setString(4, userSessionDetails.getLinkId());
						pss.setString(5, userSessionDetails.getInstId());
						pss.setString(6, userSessionDetails.getBranchId());
						pss.setString(7, acTerm);
						pss.setString(8, userSessionDetails.getLinkId());
						}

				}, new StudentGroupMasterForDashBoardRowmapper());
	
		return sGrpMaster;
	}
	
}
class StudentGroupMasterForDashBoardRowmapper implements RowMapper<StudentGroupMaster> {

	@Override
	public StudentGroupMaster mapRow(ResultSet rs, int arg1)
			throws SQLException {

		StudentGroupMaster sGrpMaster = new StudentGroupMaster();
		sGrpMaster.setStudentGrpId(rs.getString("studentGrpId"));
		sGrpMaster.setStudentGrp(rs.getString("STUDENT_GRP"));

		return sGrpMaster;
	}

}

class StudentGroupMasterRowmapper implements RowMapper<StudentGroupMaster> {

	@Override
	public StudentGroupMaster mapRow(ResultSet rs, int arg1)
			throws SQLException {

		StudentGroupMaster sGrpMaster = new StudentGroupMaster();
		sGrpMaster.setStudentGrpId(rs.getString("STUDENTGRP_ID"));
		sGrpMaster.setStudentGrp(rs.getString("STUDENT_GRP"));

		return sGrpMaster;
	}

}
