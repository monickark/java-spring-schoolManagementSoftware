package com.jaw.mark.dao;

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
import com.jaw.core.dao.AcademicTermDetails;
import com.jaw.framework.dao.BaseDao;

@Repository
public class MarkMasterDAO extends BaseDao implements IMarkMasterDAO{
	// Logging
	Logger logger = Logger.getLogger(MarkMasterDAO.class);

	@Override
	public void insertMarkMasterRec(final MarkMaster markMaster)
			throws DuplicateEntryException {
		logger.debug("Inside insert method");
		logger.debug("Mark Master object values :"
				+ markMaster.toString());		
		StringBuffer query = new StringBuffer();		 
		query = query.append("insert into mkmt ( ")
				.append("DB_TS,")
				.append("INST_ID,")
				.append("BRANCH_ID,")
				.append("AC_TERM,")
				.append("MM_SEQ_ID,")
				.append("STUDENTGRP_ID,")
				.append("EXAM_ID,")
				.append("EXAM_ORDR_WI_SG,")
				.append("EXAM_TEST,")
				.append("ATT_TERM_START_DATE,")				
				.append("ATT_TERM_END_DATE,")
				.append("EXP_RPT_DATE,")
				.append("ACT_RPT_DATE,")
				.append("STATUS,")
				.append("DEL_FLG,")
				.append("R_MOD_ID,")
				.append("R_MOD_TIME,")
				.append("R_CRE_ID,")
				.append("R_CRE_TIME)")

				.append(" values (?,?,?,?,?,?,?,?,?,?,?,?,null,?,?,?,now(),?,now())");

		try {
			getJdbcTemplate().update(query.toString(),
					new PreparedStatementSetter() {

						@Override
						public void setValues(PreparedStatement pss)
								throws SQLException {
							pss.setInt(1, markMaster.getDbTs());
							pss.setString(2, markMaster.getInstId());
							pss.setString(3, markMaster.getBranchId());
							pss.setString(4, markMaster.getAcTerm().trim());
							pss.setString(5, markMaster.getmMSeqId());
							pss.setString(6, markMaster.getStudentGrpId().trim());
							pss.setString(7, markMaster.getExamId());
							pss.setString(8, markMaster.getExamOrdrWiSG());
							pss.setString(9, markMaster.getExamTest());
							pss.setString(10, markMaster.getAttTermStartDate());							
							pss.setString(11, markMaster.getAttTermEndDate());
							pss.setString(12, markMaster.getExpRptDate());
							pss.setString(13, markMaster.getStatus().trim());
							pss.setString(14, markMaster.getDelFlag().trim());
							pss.setString(15, markMaster.getrModId().trim());
							pss.setString(16, markMaster.getrCreId().trim());

						}

					});
		} catch (org.springframework.dao.DuplicateKeyException e) {
			throw new DuplicateEntryException();
		}
		
	}

	@Override
	public void updateMarkMasterRec(final MarkMaster markMaster,
			final MarkMasterKey markMasterKey) throws UpdateFailedException {
		logger.debug("Inside update method");
		logger.debug("Mark Master object values :"+ markMaster.toString());
		logger.debug("mark Master Key object values :"+ markMasterKey.toString());		
		StringBuffer sql = new StringBuffer();
		
		
		sql.append("update mkmt set")
		        .append(" DB_TS= ?,")
				.append("AC_TERM= ?,")
				.append("STUDENTGRP_ID= ?,")
				.append("EXAM_ID= ?,")
				.append("EXAM_ORDR_WI_SG=?,")
				.append("EXAM_TEST= ?,")				
				.append("ATT_TERM_START_DATE= ?,")
				.append("ATT_TERM_END_DATE= ?,")
				.append("EXP_RPT_DATE= ?,")
				.append("ACT_RPT_DATE= ?,")
				.append("STATUS= ?,")	
				.append("DEL_FLG= 'N',")
				.append("R_MOD_ID= ?,")
				.append(" R_MOD_TIME=now()")
				.append(" where")
				.append(" DB_TS= ?")
				.append(" and  INST_ID= ?")
				.append(" and  BRANCH_ID= ?")
				.append(" and  MM_SEQ_ID= ?")
				.append(" and  DEL_FLG='N'");

		

		int updateRecs = getJdbcTemplate().update(sql.toString(),
				new PreparedStatementSetter() {

					@Override
					public void setValues(PreparedStatement ps)
							throws SQLException {
						ps.setInt(1, markMaster.getDbTs() + 1);
						ps.setString(2, markMaster.getAcTerm().trim());
						ps.setString(3, markMaster.getStudentGrpId().trim());
						ps.setString(4, markMaster.getExamId().trim());
						ps.setString(5, markMaster.getExamOrdrWiSG().trim());
						ps.setString(6, markMaster.getExamTest().trim());						
						ps.setString(7, markMaster.getAttTermStartDate());
						ps.setString(8, markMaster.getAttTermEndDate());
						ps.setString(9, markMaster.getExpRptDate());
						ps.setString(10, markMaster.getActRptDate());
						ps.setString(11, markMaster.getStatus().trim());						
						ps.setString(12, markMaster.getrModId().trim());						
						ps.setInt(13, markMasterKey.getDbTs());
						ps.setString(14, markMasterKey.getInstId().trim());
						ps.setString(15, markMasterKey.getBranchId().trim());
						ps.setString(16, markMasterKey.getmMSeqId());
						

					}

				});
		if (updateRecs == 0) {
			throw new UpdateFailedException();

		}
		
	}

	@Override
	public void deleteMarkMasterRec(final MarkMaster markMaster,
			final MarkMasterKey markMasterKey) throws DeleteFailedException {
		logger.debug("Inside delete method");
		logger.debug("Mark Master object values :"+ markMaster.toString());
		logger.debug("mark Master Key object values :"+ markMasterKey.toString());
		
		StringBuffer sql = new StringBuffer();
		   sql.append("update mkmt set")
		        .append(" DB_TS= ?,")
				.append("DEL_FLG= 'Y',")
				.append("R_MOD_ID= ?,")
				.append(" R_MOD_TIME=now()")
				.append(" where")
				.append(" DB_TS= ?")
				.append(" and INST_ID= ?")
				.append(" and  BRANCH_ID= ?")
				.append(" and  MM_SEQ_ID= ?")
				.append(" and  DEL_FLG='N'");

		int deletedRecs = getJdbcTemplate().update(sql.toString(),
				new PreparedStatementSetter() {

					@Override
					public void setValues(PreparedStatement ps)
							throws SQLException {
						ps.setInt(1, markMaster.getDbTs() + 1);
						ps.setString(2, markMaster.getrModId().trim());
						ps.setInt(3, markMasterKey.getDbTs());
						ps.setString(4, markMasterKey.getInstId().trim());
						ps.setString(5, markMasterKey.getBranchId().trim());
						ps.setString(6, markMasterKey.getmMSeqId());
						

					}

				});
		if (deletedRecs == 0) {
			throw new DeleteFailedException();

		}
		
	}

	@Override
	public MarkMaster selectMarkMasterRec(MarkMasterKey markMasterKey)
			throws NoDataFoundException {
		logger.debug("Inside select method");
		logger.debug("Mark Master Key object values :"+ markMasterKey.toString());
		List<Object> data = new ArrayList<Object>();
		
		
		StringBuffer sql = new StringBuffer();
		    sql.append("select ")
		        .append(" DB_TS,")
				.append(" INST_ID,")
				.append("BRANCH_ID,")
				.append("AC_TERM,")
				.append("MM_SEQ_ID,")
				.append("STUDENTGRP_ID,")
				.append("EXAM_ID,")
				.append("EXAM_ORDR_WI_SG,")
				.append("EXAM_TEST,")
				.append("ATT_TERM_START_DATE,")				
				.append("ATT_TERM_END_DATE,")
				.append("EXP_RPT_DATE,")
				.append("ACT_RPT_DATE,")
				.append("STATUS,")
				.append("DEL_FLG,")
				.append("R_MOD_ID,")
				.append("R_MOD_TIME,")
				.append("R_CRE_ID,")
				.append("R_CRE_TIME ")
				.append(" from mkmt ")
				.append(" where")
				.append("   INST_ID= ?")
				.append(" and  BRANCH_ID= ?")
				.append(" and  DEL_FLG=?")
		        .append(" and  MM_SEQ_ID= ?");
		    data.add(markMasterKey.getInstId().trim());
			data.add(markMasterKey.getBranchId().trim());
			data.add("N");
			data.add(markMasterKey.getmMSeqId().trim());	
			
			if ((markMasterKey.getDbTs() !=null)&&(markMasterKey.getDbTs() !=0)
					) {
				sql.append(" and DB_TS=?  ");
				logger.debug("DB_TS  :" + markMasterKey.getDbTs());
				data.add(markMasterKey.getDbTs());
			}
		    MarkMaster selectedMarkMasterrRec = null;
			Object[] array = data.toArray(new Object[data.size()]);
			logger.debug("Where clause conditions size :" + array.length);
			
			selectedMarkMasterrRec = (MarkMaster) getJdbcTemplate()
				.query(sql.toString(), array, new ResultSetExtractor<MarkMaster>() {
					
					@Override
					public MarkMaster extractData(ResultSet rs)
							throws SQLException, DataAccessException {						
						MarkMaster markMaster = null;
				if(rs.next()) {
							markMaster = new MarkMaster();
							markMaster.setDbTs(rs.getInt("DB_TS"));
							markMaster.setInstId(rs.getString("INST_ID"));
							markMaster.setBranchId(rs.getString("BRANCH_ID"));
							markMaster.setAcTerm(rs.getString("AC_TERM"));
							markMaster.setmMSeqId(rs.getString("MM_SEQ_ID"));
							markMaster.setStudentGrpId(rs.getString("STUDENTGRP_ID"));
							markMaster.setExamId(rs.getString("EXAM_ID"));
							markMaster.setExamOrdrWiSG(rs.getString("EXAM_ORDR_WI_SG"));							
							markMaster.setExamTest(rs.getString("EXAM_TEST"));
							markMaster.setAttTermStartDate(rs.getString("ATT_TERM_START_DATE"));
							markMaster.setAttTermEndDate(rs.getString("ATT_TERM_END_DATE"));
							markMaster.setExpRptDate(rs.getString("EXP_RPT_DATE"));
							markMaster.setActRptDate(rs.getString("ACT_RPT_DATE"));
							markMaster.setStatus(rs.getString("STATUS"));							
							markMaster.setDelFlag((rs.getString("DEL_FLG")));
							markMaster.setrModId(rs.getString("R_MOD_ID"));
							markMaster.setrModTime(rs.getString("R_MOD_TIME"));
							markMaster.setrCreId(rs.getString("R_CRE_ID"));
							markMaster.setrCreTime(rs.getString("R_CRE_TIME"));							
						}				
						return markMaster;
					}

				});
		   
		if (selectedMarkMasterrRec == null) {
			throw new NoDataFoundException();
		}


		return selectedMarkMasterrRec;
	}
	
	
	@Override
	public MarkMaster selectMarkMasterRecNotById(MarkMasterKey markMasterKey)
			throws NoDataFoundException {
		logger.debug("Inside select method");
		logger.debug("Mark Master Key object values :"+ markMasterKey.toString());
		List<Object> data = new ArrayList<Object>();
		
		
		StringBuffer sql = new StringBuffer();
		    sql.append("select ")
		        .append(" DB_TS,")
				.append(" INST_ID,")
				.append("BRANCH_ID,")
				.append("AC_TERM,")
				.append("MM_SEQ_ID,")
				.append("STUDENTGRP_ID,")
				.append("EXAM_ID,")
				.append("EXAM_ORDR_WI_SG,")
				.append("EXAM_TEST,")
				.append("ATT_TERM_START_DATE,")				
				.append("ATT_TERM_END_DATE,")
				.append("EXP_RPT_DATE,")
				.append("ACT_RPT_DATE,")
				.append("STATUS,")
				.append("DEL_FLG,")
				.append("R_MOD_ID,")
				.append("R_MOD_TIME,")
				.append("R_CRE_ID,")
				.append("R_CRE_TIME ")
				.append(" from mkmt ")
				.append(" where")
				.append("   INST_ID= ?")
				.append(" and  BRANCH_ID= ?")
				.append(" and  DEL_FLG=?")
		        .append(" and  AC_TERM= ?")
		        .append(" and  STUDENTGRP_ID= ?")
		    .append(" and  EXAM_ID= ?");
		    data.add(markMasterKey.getInstId().trim());
			data.add(markMasterKey.getBranchId().trim());
			data.add("N");
			data.add(markMasterKey.getAcTerm());	
			data.add(markMasterKey.getStudentGrpId());	
			data.add(markMasterKey.getExamId());	
		
		    MarkMaster selectedMarkMasterrRec = null;
			Object[] array = data.toArray(new Object[data.size()]);
			logger.debug("Where clause conditions size :" + array.length);
			
			selectedMarkMasterrRec = (MarkMaster) getJdbcTemplate()
				.query(sql.toString(), array, new ResultSetExtractor<MarkMaster>() {
					
					@Override
					public MarkMaster extractData(ResultSet rs)
							throws SQLException, DataAccessException {						
						MarkMaster markMaster = null;
				if(rs.next()) {
							markMaster = new MarkMaster();
							markMaster.setDbTs(rs.getInt("DB_TS"));
							markMaster.setInstId(rs.getString("INST_ID"));
							markMaster.setBranchId(rs.getString("BRANCH_ID"));
							markMaster.setAcTerm(rs.getString("AC_TERM"));
							markMaster.setmMSeqId(rs.getString("MM_SEQ_ID"));
							markMaster.setStudentGrpId(rs.getString("STUDENTGRP_ID"));
							markMaster.setExamId(rs.getString("EXAM_ID"));
							markMaster.setExamOrdrWiSG(rs.getString("EXAM_ORDR_WI_SG"));							
							markMaster.setExamTest(rs.getString("EXAM_TEST"));
							markMaster.setAttTermStartDate(rs.getString("ATT_TERM_START_DATE"));
							markMaster.setAttTermEndDate(rs.getString("ATT_TERM_END_DATE"));
							markMaster.setExpRptDate(rs.getString("EXP_RPT_DATE"));
							markMaster.setActRptDate(rs.getString("ACT_RPT_DATE"));
							markMaster.setStatus(rs.getString("STATUS"));							
							markMaster.setDelFlag((rs.getString("DEL_FLG")));
							markMaster.setrModId(rs.getString("R_MOD_ID"));
							markMaster.setrModTime(rs.getString("R_MOD_TIME"));
							markMaster.setrCreId(rs.getString("R_CRE_ID"));
							markMaster.setrCreTime(rs.getString("R_CRE_TIME"));							
						}				
						return markMaster;
					}

				});
		   
		if (selectedMarkMasterrRec == null) {
			throw new NoDataFoundException();
		}


		return selectedMarkMasterrRec;
	}

	@Override
	public int checkMarkMasterRecExist(MarkMaster markMaster) {
		 logger.debug("Inside Check Mark Master  method");

			logger.debug("Mark Master object values :"
					+ markMaster.toString());
			StringBuffer sql = new StringBuffer();			
			sql.append("select exists(")
			.append("select MM_SEQ_ID ")
			.append("from mkmt")				
					.append(" where ")			
					.append(" INST_ID='" + markMaster.getInstId()+ "'")
					.append(" and BRANCH_ID='" + markMaster.getBranchId()+ "'")
					.append(" and  AC_TERM='" + markMaster.getAcTerm()+ "'")		
					.append(" and  EXAM_ID='" +markMaster.getExamId()+ "'");
					
					sql.append(" and  STUDENTGRP_ID='" + markMaster.getStudentGrpId()+ "'")				
					.append(" and DEL_FLG='N')");
					
					
							
			int rows = getJdbcTemplate().queryForInt(
					sql.toString());

			System.out.println("rows valueeeeeeeeeeeee"+rows);

			
			return rows; 
	 }

	@Override
	public MarkMaster selectMarkMasterRecForStatus(MarkMaster markMaster)
			throws NoDataFoundException {
		logger.debug("Inside select for status method");
		logger.debug("Mark Master  object values :"+ markMaster.toString());
		List<Object> data = new ArrayList<Object>();
		
		
		StringBuffer sql = new StringBuffer();
		    sql.append("select ")
		        .append(" DB_TS,")
				.append(" INST_ID,")
				.append("BRANCH_ID,")
				.append("AC_TERM,")
				.append("MM_SEQ_ID,")
				.append("STUDENTGRP_ID,")
				.append("EXAM_ID,")
				.append("EXAM_ORDR_WI_SG,")
				.append("EXAM_TEST,")
				.append("ATT_TERM_START_DATE,")				
				.append("ATT_TERM_END_DATE,")
				.append("EXP_RPT_DATE,")
				.append("ACT_RPT_DATE,")
				.append("STATUS,")
				.append("DEL_FLG,")
				.append("R_MOD_ID,")
				.append("R_MOD_TIME,")
				.append("R_CRE_ID,")
				.append("R_CRE_TIME ")
				.append(" from mkmt ")
				.append(" where")
				.append("   INST_ID= ?")
				.append(" and  BRANCH_ID= ?")
				.append(" and  DEL_FLG=?")
		        .append(" and  STUDENTGRP_ID= ?")
		    .append(" and  EXAM_ID= ?")
		    .append(" and  AC_TERM= ?");
		    data.add(markMaster.getInstId().trim());
			data.add(markMaster.getBranchId().trim());
			data.add("N");
			data.add(markMaster.getStudentGrpId());
			data.add(markMaster.getExamId());
			data.add(markMaster.getAcTerm());
			
			if ((markMaster.getDbTs() !=null)&&(markMaster.getDbTs() !=0)
					) {
				sql.append(" and DB_TS=?  ");
				logger.debug("DB_TS  :" + markMaster.getDbTs());
				data.add(markMaster.getDbTs());
			}
		    MarkMaster selectedMarkMasterrRec = null;
			Object[] array = data.toArray(new Object[data.size()]);
			logger.debug("Where clause conditions size :" + array.length);
			
			selectedMarkMasterrRec = (MarkMaster) getJdbcTemplate()
				.query(sql.toString(), array, new ResultSetExtractor<MarkMaster>() {
					
					@Override
					public MarkMaster extractData(ResultSet rs)
							throws SQLException, DataAccessException {						
						MarkMaster markMaster = null;
				if(rs.next()) {
							markMaster = new MarkMaster();
							markMaster.setDbTs(rs.getInt("DB_TS"));
							markMaster.setInstId(rs.getString("INST_ID"));
							markMaster.setBranchId(rs.getString("BRANCH_ID"));
							markMaster.setAcTerm(rs.getString("AC_TERM"));
							markMaster.setmMSeqId(rs.getString("MM_SEQ_ID"));
							markMaster.setStudentGrpId(rs.getString("STUDENTGRP_ID"));
							markMaster.setExamId(rs.getString("EXAM_ID"));
							markMaster.setExamOrdrWiSG(rs.getString("EXAM_ORDR_WI_SG"));							
							markMaster.setExamTest(rs.getString("EXAM_TEST"));
							markMaster.setAttTermStartDate(rs.getString("ATT_TERM_START_DATE"));
							markMaster.setAttTermEndDate(rs.getString("ATT_TERM_END_DATE"));
							markMaster.setExpRptDate(rs.getString("EXP_RPT_DATE"));
							markMaster.setActRptDate(rs.getString("ACT_RPT_DATE"));
							markMaster.setStatus(rs.getString("STATUS"));							
							markMaster.setDelFlag((rs.getString("DEL_FLG")));
							markMaster.setrModId(rs.getString("R_MOD_ID"));
							markMaster.setrModTime(rs.getString("R_MOD_TIME"));
							markMaster.setrCreId(rs.getString("R_CRE_ID"));
							markMaster.setrCreTime(rs.getString("R_CRE_TIME"));							
						}				
						return markMaster;
					}

				});
		   
		if (selectedMarkMasterrRec == null) {
			throw new NoDataFoundException();
		}


		return selectedMarkMasterrRec;
	}

	@Override
	public int checkMarkMasterExamOrderExist(MarkMaster markMaster) {
		 logger.debug("Inside Check Mark Master Exam Order  method");

			logger.debug("Mark Master object values :"
					+ markMaster.toString());
			StringBuffer sql = new StringBuffer();			
			sql.append("select exists(")
			.append("select MM_SEQ_ID ")
			.append("from mkmt")				
					.append(" where ")			
					.append(" INST_ID='" + markMaster.getInstId()+ "'")
					.append(" and BRANCH_ID='" + markMaster.getBranchId()+ "'")
					.append(" and  AC_TERM='" + markMaster.getAcTerm()+ "'")		
					.append(" and  EXAM_ORDR_WI_SG='" +markMaster.getExamOrdrWiSG()+ "'")					
					.append(" and  STUDENTGRP_ID='" + markMaster.getStudentGrpId()+ "'")				
					.append(" and DEL_FLG='N')");
					
					
							
			int rows = getJdbcTemplate().queryForInt(
					sql.toString());

			System.out.println("rows valueeeeeeeeeeeee"+rows);

			
			return rows; 
	 }

}
