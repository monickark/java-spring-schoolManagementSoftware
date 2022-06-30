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
@Repository
public class CourseTermLinkingDAO extends BaseDao implements ICourseTermLinkingDAO{
	// Logging
	Logger logger = Logger.getLogger(CourseTermLinkingDAO.class);

	@Override
	public void insertCourseTermLinkingRec(final CourseTermLinking courseTermLinking)
			throws DuplicateEntryException {
		logger.debug("Inside insert method");
		logger.debug("CourseTermLinking object values :"
				+ courseTermLinking.toString());		
		StringBuffer query = new StringBuffer();	
		query = query.append("insert into crtl ( ")
				.append("DB_TS,")
				.append("INST_ID,")
				.append("BRANCH_ID,")
				.append("COURSEMASTER_ID,")
				.append("TERM_ID,")
				.append("TRM_SRL_ORDER,")
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
							pss.setInt(1, courseTermLinking.getDbTs());
							pss.setString(2, courseTermLinking.getInstId());
							pss.setString(3, courseTermLinking.getBranchId());
							pss.setString(4, courseTermLinking.getCourseMasterId().trim());
							pss.setString(5, courseTermLinking.getTermId().trim());
							pss.setInt(6, courseTermLinking.getTermSerialOrder());
							pss.setString(7, courseTermLinking.getDelFlag().trim());
							pss.setString(8, courseTermLinking.getrModId().trim());
							pss.setString(9, courseTermLinking.getrCreId().trim());

						}

					});
		} catch (org.springframework.dao.DuplicateKeyException e) {
			throw new DuplicateEntryException();
		}
		
	}

	

	@Override
	public void deleteCourseTermLinkingRec(final CourseTermLinking courseTermLinking,
			final CourseTermLinkingKey courseTermLinkingKey)
			throws DeleteFailedException {
		logger.debug("Inside delete method");
		logger.debug("CourseTermLinking object values :"+ courseTermLinking.toString());
		logger.debug("CourseTermLinkingKey object values :"+ courseTermLinkingKey.toString());
		
		StringBuffer sql = new StringBuffer();
		   sql.append("update crtl set")
		        .append(" DB_TS= ?,")
				.append("DEL_FLG= 'Y',")
				.append("R_MOD_ID= ?,")
				.append(" R_MOD_TIME=now()")
				.append(" where")
				.append(" DB_TS= ?")
				.append(" and INST_ID= ?")
				.append(" and  BRANCH_ID= ?")
				.append(" and  COURSEMASTER_ID= ?")
				.append(" and  TERM_ID= ?")
				.append(" and  DEL_FLG='N'");

		int deletedRecs = getJdbcTemplate().update(sql.toString(),
				new PreparedStatementSetter() {

					@Override
					public void setValues(PreparedStatement ps)
							throws SQLException {
						ps.setInt(1, courseTermLinking.getDbTs() + 1);
						ps.setString(2, courseTermLinking.getrModId().trim());
						ps.setInt(3, courseTermLinkingKey.getDbTs());
						ps.setString(4, courseTermLinkingKey.getInstId().trim());
						ps.setString(5, courseTermLinkingKey.getBranchId().trim());
						ps.setString(6, courseTermLinkingKey.getCourseMasterId().trim());
						ps.setString(7, courseTermLinkingKey.getTermId().trim());
						

					}

				});
		if (deletedRecs == 0) {
			throw new DeleteFailedException();

		}
		
	}

	@Override
	public CourseTermLinking selectCourseTermLinkingRec(
			CourseTermLinkingKey courseTermLinkingKey)
			throws NoDataFoundException {
		logger.debug("Inside select method");
		logger.debug("CourseTermLinkingKey object values :"+ courseTermLinkingKey.toString());
		List<Object> data = new ArrayList<Object>();
		
		
		StringBuffer sql = new StringBuffer();
		    sql.append("select ")
		       .append("DB_TS,")
				.append("INST_ID,")
				.append("BRANCH_ID,")
				.append("COURSEMASTER_ID,")
				.append("TERM_ID,")
				.append("TRM_SRL_ORDER,")
				.append("DEL_FLG,")
				.append("R_MOD_ID,")
				.append("R_MOD_TIME,")
				.append("R_CRE_ID,")
				.append("R_CRE_TIME ")
				.append(" from crtl ")
				.append(" where")
				.append("   INST_ID= ?")
				.append(" and  BRANCH_ID= ?")
				.append(" and  DEL_FLG=?")
		        .append(" and  COURSEMASTER_ID= ?")
		        .append(" and  TERM_ID= ?");
		    data.add(courseTermLinkingKey.getInstId().trim());
			data.add(courseTermLinkingKey.getBranchId().trim());
			data.add("N");
			data.add(courseTermLinkingKey.getCourseMasterId().trim());	
			data.add(courseTermLinkingKey.getTermId().trim());	
			
			if ((courseTermLinkingKey.getDbTs() !=null)&&(courseTermLinkingKey.getDbTs() !=0)
					) {
				sql.append(" and DB_TS=?  ");
				logger.debug("DB_TS  :" + courseTermLinkingKey.getDbTs());
				data.add(courseTermLinkingKey.getDbTs());
			}
			CourseTermLinking selectedCourseTermLinkingRec = null;
			Object[] array = data.toArray(new Object[data.size()]);
			logger.debug("Where clause conditions size :" + array.length);
			
			selectedCourseTermLinkingRec = (CourseTermLinking) getJdbcTemplate()
				.query(sql.toString(), array, new ResultSetExtractor<CourseTermLinking>() {
					
					@Override
					public CourseTermLinking extractData(ResultSet rs)
							throws SQLException, DataAccessException {						
						CourseTermLinking courseTermLinking = null;
				if(rs.next()) {
							courseTermLinking = new CourseTermLinking();
							courseTermLinking.setDbTs(rs.getInt("DB_TS"));
							courseTermLinking.setInstId(rs.getString("INST_ID"));
							courseTermLinking.setBranchId(rs.getString("BRANCH_ID"));
							courseTermLinking.setCourseMasterId(rs.getString("COURSEMASTER_ID"));
							courseTermLinking.setTermId(rs.getString("TERM_ID"));
							courseTermLinking.setTermSerialOrder(rs.getInt("TRM_SRL_ORDER"));		
							courseTermLinking.setDelFlag((rs.getString("DEL_FLG")));
							courseTermLinking.setrModId(rs.getString("R_MOD_ID"));
							courseTermLinking.setrModTime(rs.getString("R_MOD_TIME"));
							courseTermLinking.setrCreId(rs.getString("R_CRE_ID"));
							courseTermLinking.setrCreTime(rs.getString("R_CRE_TIME"));							
						}				
						return courseTermLinking;
					}

				});
		   
		if (selectedCourseTermLinkingRec == null) {
			throw new NoDataFoundException();
		}


		return selectedCourseTermLinkingRec;
	}

	@Override
	public int checkCourseTermLinkingOrderExist(
			CourseTermLinking courseTermLinking) {
		 logger.debug("Inside Check CourseTermLinking Order  method");

			logger.debug("CourseTermLinking object values :"
					+ courseTermLinking.toString());
			StringBuffer sql = new StringBuffer();			
			sql.append("select exists(")
			.append("select COURSEMASTER_ID ")
			.append("from crtl")				
					.append(" where ")			
					.append(" INST_ID='" + courseTermLinking.getInstId()+ "'")
					.append(" and BRANCH_ID='" + courseTermLinking.getBranchId()+ "'")
					.append(" and  COURSEMASTER_ID='" + courseTermLinking.getCourseMasterId()+ "'")		
					.append(" and  TRM_SRL_ORDER='" +courseTermLinking.getTermSerialOrder()+ "'")
					.append(" and DEL_FLG='N')");
					
					
							
			int rows = getJdbcTemplate().queryForInt(
					sql.toString());

			System.out.println("rows valueeeeeeeeeeeee"+rows);

			
			return rows; 
	 }
//update crtl set DEL_FLG='N',DB_TS=DB_TS+1 where COURSEMASTER_ID='CM026' and TERM_ID='TRM1' and INST_ID='ASC' and BRANCH_ID='BR001' and DEL_FLG='Y'

	@Override
	public void updateCrsTmLinkRecDelFlgYesToNo(
			final CourseTermLinking courseTermLinking,
			final CourseTermLinkingKey courseTermLinkingKey)
			throws UpdateFailedException {
		logger.debug("Inside update method");
		logger.debug("CourseTermLinking object values :"+ courseTermLinking.toString());
		logger.debug("CourseTermLinkingKey Details object values :"+ courseTermLinkingKey.toString());		
		StringBuffer sql = new StringBuffer();
			sql.append("update crtl set")
		        .append(" DB_TS= ?,")	
		        .append(" TRM_SRL_ORDER= ?,")			        
				.append("DEL_FLG= 'N',")
				.append("R_MOD_ID= ?,")
				.append(" R_MOD_TIME=now()")
				.append(" where")
				.append(" DB_TS= ?")	
				.append(" and  INST_ID= ?")
				.append(" and  BRANCH_ID= ?")
				.append(" and  COURSEMASTER_ID= ?")	
				.append(" and  TERM_ID= ?")	
				.append(" and  DEL_FLG='Y'");

		

		int updateRecs = getJdbcTemplate().update(sql.toString(),
				new PreparedStatementSetter() {

					@Override
					public void setValues(PreparedStatement ps)
							throws SQLException {
						ps.setInt(1, courseTermLinking.getDbTs()+1);
						ps.setInt(2, courseTermLinking.getTermSerialOrder());
						ps.setString(3, courseTermLinking.getrModId().trim());	
						ps.setInt(4, courseTermLinkingKey.getDbTs());	
						ps.setString(5, courseTermLinkingKey.getInstId().trim());
						ps.setString(6, courseTermLinkingKey.getBranchId().trim());
						ps.setString(7, courseTermLinkingKey.getCourseMasterId().trim());
						ps.setString(8, courseTermLinkingKey.getTermId().trim());
						

					}

				});
		if (updateRecs == 0) {
			logger.error("Update failed exception occured");
			throw new UpdateFailedException();

		}
		
		
	}

	@Override
	public CourseTermLinking selectCourseTermLinkingDelFlgRec(
			CourseTermLinkingKey courseTermLinkingKey)
			throws NoDataFoundException {
		logger.debug("Inside select method");
		logger.debug("CourseTermLinkingKey object values :"+ courseTermLinkingKey.toString());
		List<Object> data = new ArrayList<Object>();
		
		
		StringBuffer sql = new StringBuffer();
		    sql.append("select ")
		       .append("DB_TS,")
				.append("INST_ID,")
				.append("BRANCH_ID,")
				.append("COURSEMASTER_ID,")
				.append("TERM_ID,")
				.append("TRM_SRL_ORDER,")
				.append("DEL_FLG,")
				.append("R_MOD_ID,")
				.append("R_MOD_TIME,")
				.append("R_CRE_ID,")
				.append("R_CRE_TIME ")
				.append(" from crtl ")
				.append(" where")
				.append("   INST_ID= ?")
				.append(" and  BRANCH_ID= ?")
				.append(" and  DEL_FLG=?")
		        .append(" and  COURSEMASTER_ID= ?")
		        .append(" and  TERM_ID= ?");
		    data.add(courseTermLinkingKey.getInstId().trim());
			data.add(courseTermLinkingKey.getBranchId().trim());
			data.add("Y");
			data.add(courseTermLinkingKey.getCourseMasterId().trim());	
			data.add(courseTermLinkingKey.getTermId().trim());	
			
			if ((courseTermLinkingKey.getDbTs() !=null)&&(courseTermLinkingKey.getDbTs() !=0)
					) {
				sql.append(" and DB_TS=?  ");
				logger.debug("DB_TS  :" + courseTermLinkingKey.getDbTs());
				data.add(courseTermLinkingKey.getDbTs());
			}
			CourseTermLinking selectedCourseTermLinkingRec = null;
			Object[] array = data.toArray(new Object[data.size()]);
			logger.debug("Where clause conditions size :" + array.length);
			
			selectedCourseTermLinkingRec = (CourseTermLinking) getJdbcTemplate()
				.query(sql.toString(), array, new ResultSetExtractor<CourseTermLinking>() {
					
					@Override
					public CourseTermLinking extractData(ResultSet rs)
							throws SQLException, DataAccessException {						
						CourseTermLinking courseTermLinking = null;
				if(rs.next()) {
							courseTermLinking = new CourseTermLinking();
							courseTermLinking.setDbTs(rs.getInt("DB_TS"));
							courseTermLinking.setInstId(rs.getString("INST_ID"));
							courseTermLinking.setBranchId(rs.getString("BRANCH_ID"));
							courseTermLinking.setCourseMasterId(rs.getString("COURSEMASTER_ID"));
							courseTermLinking.setTermId(rs.getString("TERM_ID"));
							courseTermLinking.setTermSerialOrder(rs.getInt("TRM_SRL_ORDER"));		
							courseTermLinking.setDelFlag((rs.getString("DEL_FLG")));
							courseTermLinking.setrModId(rs.getString("R_MOD_ID"));
							courseTermLinking.setrModTime(rs.getString("R_MOD_TIME"));
							courseTermLinking.setrCreId(rs.getString("R_CRE_ID"));
							courseTermLinking.setrCreTime(rs.getString("R_CRE_TIME"));							
						}				
						return courseTermLinking;
					}

				});
		   
		if (selectedCourseTermLinkingRec == null) {
			throw new NoDataFoundException();
		}


		return selectedCourseTermLinkingRec;
	}

}
