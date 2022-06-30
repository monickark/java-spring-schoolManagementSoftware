package com.jaw.core.dao;

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
//TeacherSubjectLink DAO class
@Repository
public class TeacherSubjectLinkDAO extends BaseDao implements ITeacherSubjectLinkDAO{
	// Logging
	Logger logger = Logger.getLogger(TeacherSubjectLinkDAO.class);

	@Override
	public void insertTeacherSubjectLinkRec(
			final TeacherSubjectLink teacherSubjectLink)
			throws DuplicateEntryException {
		logger.debug("Inside insert method");
		logger.debug("TeacherSubjectLink object values :"
				+ teacherSubjectLink.toString());
		StringBuffer query = new StringBuffer();	
	
		query = query.append("insert into trsl ( ")
				.append("DB_TS,")
				.append("INST_ID,")
				.append("BRANCH_ID,")
				.append("TRSL_ID,")
				.append("STAFF_ID,")
				.append("SUB_ID,")	
				.append("DEL_FLG,")
				.append("R_MOD_ID,")
				.append("R_MOD_TIME,")
				.append("R_CRE_ID,")
				.append("R_CRE_TIME)")
				.append(" values (?,?,?,?,?,?,?,?,now(),?,now())");

		try {
			getJdbcTemplate().update(query.toString(),
					new PreparedStatementSetter() {

						@Override
						public void setValues(PreparedStatement pss)
								throws SQLException {
							pss.setInt(1, teacherSubjectLink.getDbTs());
							pss.setString(2, teacherSubjectLink.getInstId());
							pss.setString(3, teacherSubjectLink.getBranchId());
							pss.setString(4, teacherSubjectLink.getTrslId().trim());
							pss.setString(5, teacherSubjectLink.getStaffId().trim());
							pss.setString(6, teacherSubjectLink.getSubId().trim());	
							pss.setString(7, teacherSubjectLink.getDelFlag().trim());
							pss.setString(8, teacherSubjectLink.getrModId().trim());
							pss.setString(9, teacherSubjectLink.getrCreId().trim());

						}

					});
		} catch (org.springframework.dao.DuplicateKeyException e) {
			logger.error("Duplicate Entry Exception Occured "+e.getMessage());
			throw new DuplicateEntryException();
		}
		
	}

	@Override
	public void updateTeacherSubjectLinkRec(
			final TeacherSubjectLink teacherSubjectLink,
			final TeacherSubjectLinkKey teacherSubjectLinkKey)
			throws UpdateFailedException {
		logger.debug("Inside update method");
		logger.debug("TeacherSubjectLink object values :"+ teacherSubjectLink.toString());
		logger.debug("TeacherSubjectLinkKey Details object values :"+ teacherSubjectLinkKey.toString());		
		StringBuffer sql = new StringBuffer();		
			sql.append("update trsl set")
		        .append(" DB_TS= ?,")				
				.append("STAFF_ID=?,")
				.append("SUB_ID=?,")
				.append("DEL_FLG= 'N',")
				.append("R_MOD_ID= ?,")
				.append(" R_MOD_TIME=now()")
				.append(" where")
				.append(" DB_TS= ?")
				.append(" and  INST_ID= ?")
				.append(" and  BRANCH_ID= ?")
				.append(" and  TRSL_ID= ?")	
				.append(" and  DEL_FLG='N'");

		

		int updateRecs = getJdbcTemplate().update(sql.toString(),
				new PreparedStatementSetter() {

					@Override
					public void setValues(PreparedStatement ps)
							throws SQLException {
						ps.setInt(1, teacherSubjectLink.getDbTs() + 1);
						ps.setString(2, teacherSubjectLink.getStaffId().trim());
						ps.setString(3, teacherSubjectLink.getSubId().trim());	
						ps.setString(4, teacherSubjectLink.getrModId().trim());							
						ps.setInt(5, teacherSubjectLinkKey.getDbTs());
						ps.setString(6, teacherSubjectLinkKey.getInstId().trim());
						ps.setString(7, teacherSubjectLinkKey.getBranchId().trim());
						ps.setString(8, teacherSubjectLinkKey.getTrslId().trim());
						

					}

				});
		if (updateRecs == 0) {
			logger.error("Update Failed Exception Occured");
			throw new UpdateFailedException();

		}
		
		
	}

	@Override
	public void deleteTeacherSubjectLinkRec(
			final TeacherSubjectLink teacherSubjectLink,
			final TeacherSubjectLinkKey teacherSubjectLinkKey)
			throws DeleteFailedException {
		logger.debug("Inside delete method");
		logger.debug("TeacherSubjectLink object values :"+teacherSubjectLink.toString());
		logger.debug("TeacherSubjectLinkKey object values :"+ teacherSubjectLinkKey.toString());
		
		StringBuffer sql = new StringBuffer();
		   sql.append("update trsl set")
		        .append(" DB_TS= ?,")
				.append("DEL_FLG= 'Y',")
				.append("R_MOD_ID= ?,")
				.append(" R_MOD_TIME=now()")
				.append(" where")
				.append(" DB_TS= ?")
				.append(" and  INST_ID= ?")
				.append(" and  BRANCH_ID= ?")
				.append(" and  TRSL_ID= ?")	
				.append(" and  DEL_FLG='N'");

		int deletedRecs = getJdbcTemplate().update(sql.toString(),
				new PreparedStatementSetter() {

					@Override
					public void setValues(PreparedStatement ps)
							throws SQLException {
						ps.setInt(1, teacherSubjectLink.getDbTs() );
						ps.setString(2, teacherSubjectLink.getrModId().trim());						
						ps.setInt(3, teacherSubjectLinkKey.getDbTs());
						ps.setString(4, teacherSubjectLinkKey.getInstId().trim());
						ps.setString(5, teacherSubjectLinkKey.getBranchId().trim());
						ps.setString(6, teacherSubjectLinkKey.getTrslId().trim());
					}

				});
		if (deletedRecs == 0) {
			logger.error("Delete Failed Exception Occured");
			throw new DeleteFailedException();

		}
		
	}

	@Override
	public TeacherSubjectLink selectTeacherSubjectLinkRec(
			final TeacherSubjectLinkKey teacherSubjectLinkKey)
			throws NoDataFoundException {
		logger.debug("Inside select method");
		logger.debug("TeacherSubjectLinkKey object values :"+ teacherSubjectLinkKey.toString());
		List<Object> data = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		    sql.append("select ")
		        .append(" DB_TS,")
				.append("INST_ID,")
				.append("BRANCH_ID,")
				.append("TRSL_ID,")
				.append("STAFF_ID,")
				.append("SUB_ID,")		
				.append("DEL_FLG,")
				.append("R_MOD_ID,")
				.append("R_MOD_TIME,")
				.append("R_CRE_ID,")
				.append("R_CRE_TIME")
				.append(" from trsl ")
				.append(" where")
				.append("   INST_ID= ?")
				.append(" and  BRANCH_ID= ?")
				.append(" and  TRSL_ID= ?")	
				.append(" and  DEL_FLG=?");
		    data.add(teacherSubjectLinkKey.getInstId().trim());
			data.add(teacherSubjectLinkKey.getBranchId().trim());
			data.add(teacherSubjectLinkKey.getTrslId().trim());
			data.add("N");			
			if ((teacherSubjectLinkKey.getDbTs() !=null)&&(teacherSubjectLinkKey.getDbTs() !=0)
					) {
				sql.append(" and DB_TS=?  ");
				logger.debug("DB_TS  :" + teacherSubjectLinkKey.getDbTs());
				data.add(teacherSubjectLinkKey.getDbTs());
			}
			
			Object[] array = data.toArray(new Object[data.size()]);
			logger.debug("Where clause conditions size :" + array.length);
		    TeacherSubjectLink selectedTeacherSubjectLink = null;
		    selectedTeacherSubjectLink = (TeacherSubjectLink) getJdbcTemplate()
				.query(sql.toString(),array, new ResultSetExtractor<TeacherSubjectLink>() {

					@Override
					public TeacherSubjectLink extractData(ResultSet rs)
							throws SQLException, DataAccessException {
						TeacherSubjectLink teacherSubjectLink = null;
						if (rs.next()) {
							teacherSubjectLink = new TeacherSubjectLink();
							teacherSubjectLink.setDbTs(rs.getInt("DB_TS"));
							teacherSubjectLink.setInstId(rs.getString("INST_ID"));
							teacherSubjectLink.setBranchId(rs.getString("BRANCH_ID"));
							teacherSubjectLink.setTrslId(rs.getString("TRSL_ID"));
							teacherSubjectLink.setStaffId(rs.getString("STAFF_ID"));
							teacherSubjectLink.setSubId(rs.getString("SUB_ID"));						
							teacherSubjectLink.setDelFlag(rs.getString("DEL_FLG"));
							teacherSubjectLink.setrModId(rs.getString("R_MOD_ID"));
							teacherSubjectLink.setrModTime(rs.getString("R_MOD_TIME"));
							teacherSubjectLink.setrCreId(rs.getString("R_CRE_ID"));
							teacherSubjectLink.setrCreTime(rs.getString("R_CRE_TIME"));
						}
						return teacherSubjectLink;
					}

				});

		if (selectedTeacherSubjectLink == null) {
			logger.error("No  record found");
			throw new NoDataFoundException();
		}

		return selectedTeacherSubjectLink;
	}
	@Override
	public TeacherSubjectLink checkTeacherSubjectLink(final TeacherSubjectLinkListKey teacherSubjectLinkListKey)  {
		logger.debug("Inside Check Teacher Subject Link");

		logger.debug("Teacher Subject Link List Key object values :"
				+ teacherSubjectLinkListKey.toString());
		StringBuffer sql = new StringBuffer();
		
		sql.append("select TRSL_ID ")
		.append("from trsl")				
				.append(" where")
					.append("  INST_ID=?")
				.append(" and BRANCH_ID=?")
				.append(" and  SUB_ID=?")
						.append(" and  DEL_FLG=?")
		.append(" and  STAFF_ID=?");
		 TeacherSubjectLink selectedTeacherSubjectLink = null;
		    selectedTeacherSubjectLink = (TeacherSubjectLink) getJdbcTemplate()
				.query(sql.toString(),new PreparedStatementSetter() {

					@Override
					public void setValues(PreparedStatement ps)
							throws SQLException {
						ps.setString(1, teacherSubjectLinkListKey.getInstId().trim());
						ps.setString(2, teacherSubjectLinkListKey.getBranchId().trim());
						ps.setString(3, teacherSubjectLinkListKey.getSubId());
						ps.setString(4, "N");
						ps.setString(5, teacherSubjectLinkListKey.getStaffId());
						

					}

				}, new ResultSetExtractor<TeacherSubjectLink>() {

					@Override
					public TeacherSubjectLink extractData(ResultSet rs)
							throws SQLException, DataAccessException {
						TeacherSubjectLink teacherSubjectLink = null;
						if (rs.next()) {
							teacherSubjectLink = new TeacherSubjectLink();
							teacherSubjectLink.setTrslId(rs.getString("TRSL_ID"));
						}
						return teacherSubjectLink;
					}

				});

		


		
		
		return selectedTeacherSubjectLink;
	}
}
