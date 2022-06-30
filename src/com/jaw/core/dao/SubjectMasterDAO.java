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

import com.jaw.common.constants.CommonCodeConstant;
import com.jaw.common.exceptions.DeleteFailedException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.UpdateFailedException;
import com.jaw.framework.dao.BaseDao;
//Subject Master DAO class
@Repository
public class SubjectMasterDAO extends BaseDao implements ISubjectMasterDAO{
	// Logging
		Logger logger = Logger.getLogger(SubjectMasterDAO.class);

	@Override
	public void insertSubjectMasterRec(final SubjectMaster subjectMaster)
			throws DuplicateEntryException {
		logger.debug("Inside insert method");
		logger.debug("Subject Master object values :"
				+ subjectMaster.toString());
		
		StringBuffer query = new StringBuffer();		
		query = query.append("insert into sbjm ( ")
				.append("DB_TS,")
				.append("INST_ID,")
				.append("BRANCH_ID,")
				.append("SUB_ID,")
				.append("SUB_NAME,")
				.append("CUST_CODE,")				
				.append("SHORT_CODE,")
				.append("DEPT_ID,")
				.append("COURSEMASTER_ID,")
				.append("IS_COMP,")
				.append("IS_ELECTIVE,")
				.append("IS_LANG,")	
				.append("IS_REL,")		
				.append("IS_PRE_ACA_SUB,")	
				.append("DEL_FLG,")
				.append("R_MOD_ID,")
				.append("R_MOD_TIME,")
				.append("R_CRE_ID,")
				.append("R_CRE_TIME)")
				.append(" values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,now(),?,now())");

		try {
			getJdbcTemplate().update(query.toString(),
					new PreparedStatementSetter() {

						@Override
						public void setValues(PreparedStatement pss)
								throws SQLException {
							pss.setInt(1, subjectMaster.getDbTs());
							pss.setString(2, subjectMaster.getInstId());
							pss.setString(3, subjectMaster.getBranchId());
							pss.setString(4, subjectMaster.getSub_Id());
							pss.setString(5, subjectMaster.getSub_Name().trim());
							pss.setString(6, subjectMaster.getCust_Code().trim());							
							pss.setString(7, subjectMaster.getShort_Code().trim());
							pss.setString(8, subjectMaster.getDepartment().trim());
							pss.setString(9, subjectMaster.getCourseName().trim());
							pss.setString(10, subjectMaster.getIs_Com().trim());
							pss.setString(11, subjectMaster.getIs_Elective().trim());
							pss.setString(12, subjectMaster.getIs_Lang().trim());
							pss.setString(13, subjectMaster.getIs_rel().trim());
							pss.setString(14, subjectMaster.getIsPreAcaSubject().trim());
							pss.setString(15, subjectMaster.getDelFlag().trim());
							pss.setString(16, subjectMaster.getrModId().trim());
							pss.setString(17, subjectMaster.getrCreId().trim());

						}

					});
		} catch (org.springframework.dao.DuplicateKeyException e) {
			throw new DuplicateEntryException();
		}
		
		
	}

	@Override
	public void updateSubjectMasterRec(final SubjectMaster subjectMaster,
			final SubjectMasterKey subjectMasterKey) throws UpdateFailedException {
		logger.debug("Inside update method");
		logger.debug("Subject Master object values :"+ subjectMaster.toString());
		logger.debug("Subject Master Key Details object values :"+ subjectMasterKey.toString());		
		StringBuffer sql = new StringBuffer();	
		
			sql.append("update sbjm set")
		        .append(" DB_TS= ?,")				
				.append("CUST_CODE=?,")
				.append("SHORT_CODE=?,")
				.append("DEPT_ID=?,")
				.append("COURSEMASTER_ID=?,")
				.append("IS_COMP=?,")
				.append("IS_ELECTIVE=?,")
				.append("IS_LANG=?,")
				.append("IS_REL=?,")
				.append("IS_PRE_ACA_SUB=?,")		
				.append("DEL_FLG= 'N',")
				.append("R_MOD_ID= ?,")
				.append(" R_MOD_TIME=now()")
				.append(" where")
				.append(" DB_TS= ?")
				.append(" and  INST_ID= ?")
				.append(" and  BRANCH_ID= ?")
				.append(" and  SUB_ID= ?")	
				.append(" and  DEL_FLG='N'");

		

		int updateRecs = getJdbcTemplate().update(sql.toString(),
				new PreparedStatementSetter() {

					@Override
					public void setValues(PreparedStatement ps)
							throws SQLException {
						ps.setInt(1, subjectMaster.getDbTs() + 1);
						ps.setString(2, subjectMaster.getCust_Code().trim());
						ps.setString(3, subjectMaster.getShort_Code().trim());	
						ps.setString(4, subjectMaster.getDepartment().trim());	
						ps.setString(5, subjectMaster.getCourseName().trim());	
						ps.setString(6, subjectMaster.getIs_Com().trim());		
						ps.setString(7, subjectMaster.getIs_Elective().trim());		
						ps.setString(8, subjectMaster.getIs_Lang().trim());			
						ps.setString(9, subjectMaster.getIs_rel().trim());	
						ps.setString(10, subjectMaster.getIsPreAcaSubject().trim());	
						ps.setString(11, subjectMaster.getrModId().trim());	
						
						ps.setInt(12, subjectMasterKey.getDbTs());
						ps.setString(13, subjectMasterKey.getInstId().trim());
						ps.setString(14, subjectMasterKey.getBranchId().trim());
						ps.setString(15, subjectMasterKey.getSub_Id().trim());
						

					}

				});
		if (updateRecs == 0) {
			throw new UpdateFailedException();

		}
		
	}

	@Override
	public void deleteSubjectMasterRec(final SubjectMaster subjectMaster,
			final SubjectMasterKey subjectMasterKey) throws DeleteFailedException {
		logger.debug("Inside delete method");
		logger.debug("Subject Master object values :"+ subjectMaster.toString());
		logger.debug("Subject Master Key object values :"+ subjectMasterKey.toString());
		
		StringBuffer sql = new StringBuffer();
		   sql.append("update sbjm set")
		        .append(" DB_TS= ?,")
				.append("DEL_FLG= 'Y',")
				.append("R_MOD_ID= ?,")
				.append(" R_MOD_TIME=now()")
				.append(" where")
				.append(" DB_TS= ?")
				.append(" and INST_ID= ?")
				.append(" and  BRANCH_ID= ?")
				.append(" and  SUB_ID= ?")	
				.append(" and  DEL_FLG='N'");

		int deletedRecs = getJdbcTemplate().update(sql.toString(),
				new PreparedStatementSetter() {

					@Override
					public void setValues(PreparedStatement ps)
							throws SQLException {
						ps.setInt(1, subjectMaster.getDbTs() + 1);
						ps.setString(2, subjectMaster.getrModId().trim());
						ps.setInt(3, subjectMasterKey.getDbTs());
						ps.setString(4, subjectMasterKey.getInstId().trim());
						ps.setString(5, subjectMasterKey.getBranchId().trim());
						ps.setString(6, subjectMasterKey.getSub_Id().trim());
					}

				});
		if (deletedRecs == 0) {
			throw new DeleteFailedException();

		}
		
	}

	@Override
	public SubjectMaster selectSubjectMasterRec(SubjectMasterKey subjectMasterKey)
			throws NoDataFoundException {
		logger.debug("Inside select method");
		logger.debug("Subject MAster Key object values :"+ subjectMasterKey.toString());
		List<Object> data = new ArrayList<Object>();
	
		StringBuffer sql = new StringBuffer();
		    sql.append("select ")
		        .append(" DB_TS,")
				.append("INST_ID,")
				.append("BRANCH_ID,")
			    .append("SUB_ID,")
				.append("SUB_NAME,")
				.append("CUST_CODE,")				
				.append("SHORT_CODE,")
				.append("DEPT_ID,")
				.append("COURSEMASTER_ID,")
				.append("IS_COMP,")
				.append("IS_ELECTIVE,")
				.append("IS_LANG,")	
				.append("IS_REL,")
				.append("IS_PRE_ACA_SUB,")	
				.append("DEL_FLG,")
				.append("R_MOD_ID,")
				.append("R_MOD_TIME,")
				.append("R_CRE_ID,")
				.append("R_CRE_TIME")
				.append(" from sbjm ")
				.append(" where")
				.append("   INST_ID= ?")
				.append(" and  BRANCH_ID= ?")
				.append(" and  SUB_ID= ?")
				.append(" and  DEL_FLG=?");		
		    data.add(subjectMasterKey.getInstId());
			data.add(subjectMasterKey.getBranchId());
			data.add(subjectMasterKey.getSub_Id());
			data.add("N");
			if ((subjectMasterKey.getDbTs() !=null)&&(subjectMasterKey.getDbTs() !=0)
					) {
				sql.append(" and DB_TS=?  ");
				logger.debug("Db Ts Value :" + subjectMasterKey.getDbTs());
				data.add(subjectMasterKey.getDbTs());
			}
			System.out.println("sql queryyyyyyyyyyyyy"+sql.toString());
		    SubjectMaster selectedSubjectMaster = null;
			Object[] array = data.toArray(new Object[data.size()]);
			logger.debug("Where clause conditions size :" + array.length);
			System.out.println("sql queryyyyyyyyyyyyy array"+array);
		    selectedSubjectMaster = (SubjectMaster) getJdbcTemplate()
				.query(sql.toString(), array, new ResultSetExtractor<SubjectMaster>() {

					@Override
					public SubjectMaster extractData(ResultSet rs)
							throws SQLException, DataAccessException {
						SubjectMaster subjectMaster = null;
						if (rs.next()) {
							subjectMaster = new SubjectMaster();
							subjectMaster.setDbTs(rs.getInt("DB_TS"));
							subjectMaster.setInstId(rs.getString("INST_ID"));
							subjectMaster.setBranchId(rs.getString("BRANCH_ID"));
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
							subjectMaster.setDelFlag(rs.getString("DEL_FLG"));
							subjectMaster.setrModId(rs.getString("R_MOD_ID"));
							subjectMaster.setrModTime(rs.getString("R_MOD_TIME"));
							subjectMaster.setrCreId(rs.getString("R_CRE_ID"));
							subjectMaster.setrCreTime(rs.getString("R_CRE_TIME"));
						}
						return subjectMaster;
					}

				});

		if (selectedSubjectMaster == null) {
			throw new NoDataFoundException();
		}

		return selectedSubjectMaster;
	}

	@Override
	public void updateSubjectMasterToDelFlgNRec(final SubjectMaster subjectMaster,
			final SubjectMasterKey subjectMasterKey) throws UpdateFailedException {
		logger.debug("Inside delete method");
		logger.debug("Subject Master object values :"+ subjectMaster.toString());
		logger.debug("Subject Master Key object values :"+ subjectMasterKey.toString());
		
		StringBuffer sql = new StringBuffer();
		   sql.append("update sbjm set")
		        .append(" DB_TS= ?,")
		        .append(" SUB_NAME= ?,")
		        .append(" CUST_CODE= ?,")
		        .append(" SHORT_CODE= ?,")
		        .append(" DEPT_ID= ?,")
		        .append(" COURSEMASTER_ID= ?,")
		        .append(" IS_COMP= ?,")
		        .append(" IS_ELECTIVE= ?,")
		        .append(" IS_LANG= ?,")
		        .append(" IS_REL= ?,")
		        .append(" IS_PRE_ACA_SUB= ?,")
				.append("DEL_FLG= 'N',")
				.append("R_MOD_ID= ?,")
				.append(" R_MOD_TIME=now()")
				.append(" where")
				.append(" DB_TS= ?")
				.append(" and INST_ID= ?")
				.append(" and  BRANCH_ID= ?")
				.append(" and  SUB_ID= ?")	
				.append(" and  DEL_FLG='Y'");

		int deletedRecs = getJdbcTemplate().update(sql.toString(),
				new PreparedStatementSetter() {

					@Override
					public void setValues(PreparedStatement ps)
							throws SQLException {
						ps.setInt(1, subjectMaster.getDbTs() + 1);
						ps.setString(2, subjectMaster.getSub_Name());
						ps.setString(3, subjectMaster.getCust_Code());
						ps.setString(4, subjectMaster.getShort_Code());
						ps.setString(5, subjectMaster.getDepartment());
						ps.setString(6, subjectMaster.getCourseName());
						ps.setString(7, subjectMaster.getIs_Com());
						ps.setString(8, subjectMaster.getIs_Elective());
						ps.setString(9, subjectMaster.getIs_Lang());
						ps.setString(10, subjectMaster.getIs_rel());
						ps.setString(11, subjectMaster.getIsPreAcaSubject());
						ps.setString(12, subjectMaster.getrModId().trim());
						ps.setInt(13, subjectMasterKey.getDbTs());
						ps.setString(14, subjectMasterKey.getInstId().trim());
						ps.setString(15, subjectMasterKey.getBranchId().trim());
						ps.setString(16, subjectMasterKey.getSub_Id().trim());
					}

				});
		if (deletedRecs == 0) {
			throw new UpdateFailedException();

		}
		
	}

	@Override
	public SubjectMaster selectSubjectMasterHasDelFlgYRec(
			SubjectMasterKey subjectMasterKey) throws NoDataFoundException {
		logger.debug("Inside select has delflg y method");
		logger.debug("Subject MAster Key object values :"+ subjectMasterKey.toString());
		List<Object> data = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		    sql.append("select ")
		        .append(" DB_TS,")
				.append("INST_ID,")
				.append("BRANCH_ID,")
			    .append("SUB_ID,")
				.append("SUB_NAME,")
				.append("CUST_CODE,")				
				.append("SHORT_CODE,")
				.append("DEPT_ID,")
				.append("COURSEMASTER_ID,")
				.append("IS_COMP,")
				.append("IS_ELECTIVE,")
				.append("IS_LANG,")	
				.append("IS_REL,")		
				.append("IS_PRE_ACA_SUB,")
				.append("DEL_FLG,")
				.append("R_MOD_ID,")
				.append("R_MOD_TIME,")
				.append("R_CRE_ID,")
				.append("R_CRE_TIME")
				.append(" from sbjm ")
				.append(" where")
				.append("   INST_ID= ?")
				.append(" and  BRANCH_ID= ?")
				.append(" and  SUB_ID= ?")
				.append(" and  DEL_FLG=?");		
		    data.add(subjectMasterKey.getInstId());
			data.add(subjectMasterKey.getBranchId());
			data.add(subjectMasterKey.getSub_Id());
			data.add("Y");
			if ((subjectMasterKey.getDbTs() !=null)&&(subjectMasterKey.getDbTs() !=0)
					) {
				sql.append(" and DB_TS=?  ");
				logger.debug("Db Ts Value :" + subjectMasterKey.getDbTs());
				data.add(subjectMasterKey.getDbTs());
			}
			System.out.println("sql queryyyyyyyyyyyyy"+sql.toString());
		    SubjectMaster selectedSubjectMaster = null;
			Object[] array = data.toArray(new Object[data.size()]);
			logger.debug("Where clause conditions size :" + array.length);
			System.out.println("sql queryyyyyyyyyyyyy array"+array);
		    selectedSubjectMaster = (SubjectMaster) getJdbcTemplate()
				.query(sql.toString(), array, new ResultSetExtractor<SubjectMaster>() {

					@Override
					public SubjectMaster extractData(ResultSet rs)
							throws SQLException, DataAccessException {
						SubjectMaster subjectMaster = null;
						if (rs.next()) {
							subjectMaster = new SubjectMaster();
							subjectMaster.setDbTs(rs.getInt("DB_TS"));
							subjectMaster.setInstId(rs.getString("INST_ID"));
							subjectMaster.setBranchId(rs.getString("BRANCH_ID"));
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
							subjectMaster.setDelFlag(rs.getString("DEL_FLG"));
							subjectMaster.setrModId(rs.getString("R_MOD_ID"));
							subjectMaster.setrModTime(rs.getString("R_MOD_TIME"));
							subjectMaster.setrCreId(rs.getString("R_CRE_ID"));
							subjectMaster.setrCreTime(rs.getString("R_CRE_TIME"));
						}
						return subjectMaster;
					}

				});

		if (selectedSubjectMaster == null) {
			throw new NoDataFoundException();
		}

		return selectedSubjectMaster;
	}

	@Override
	public int checkSubjectNameExist(SubjectMaster subjectMaster) {
		 logger.debug("Inside Check Subject Name  method");

			logger.debug("Subject master object values :"
					+ subjectMaster.toString());
			StringBuffer sql = new StringBuffer();	
			
			sql.append("select exists(")
			.append("select SUB_NAME ")
			.append("from sbjm")				
					.append(" where ")			
					.append(" INST_ID='" + subjectMaster.getInstId()+ "'")
					.append(" and BRANCH_ID='" + subjectMaster.getBranchId()+ "'")
					.append(" and  SUB_NAME='" + subjectMaster.getSub_Name()+ "'")
					.append(" and DEL_FLG='N')");
					
					
							
			int rows = getJdbcTemplate().queryForInt(
					sql.toString());

			System.out.println("rows valueeeeeeeeeeeee"+rows);

			
			return rows; 
	 }

	@Override
	public int checkCustomCodeExist(SubjectMaster subjectMaster) {
		 logger.debug("Inside Check Custom code  method");

			logger.debug("Subject master object values :"
					+ subjectMaster.toString());
			StringBuffer sql = new StringBuffer();	
			
			sql.append("select exists(")
			.append("select CUST_CODE ")
			.append("from sbjm")				
					.append(" where ")			
					.append(" INST_ID='" + subjectMaster.getInstId()+ "'")
					.append(" and BRANCH_ID='" + subjectMaster.getBranchId()+ "'")
					.append(" and  CUST_CODE='" + subjectMaster.getCust_Code()+ "'")
					.append(" and DEL_FLG='N')");
					
					
							
			int rows = getJdbcTemplate().queryForInt(
					sql.toString());

			System.out.println("rows valueeeeeeeeeeeee"+rows);

			
			return rows; 
	 }

}
