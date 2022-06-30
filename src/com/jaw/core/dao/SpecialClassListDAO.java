package com.jaw.core.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.jaw.common.constants.CommonCodeConstant;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.framework.dao.BaseDao;
import com.jaw.framework.sessCache.UserSessionDetails;

@Repository
public class SpecialClassListDAO extends BaseDao implements
		ISpecialClassListDAO {
	Logger logger = Logger.getLogger(SpecialClassListDAO.class);
String category;
	@Override
	public List<SpecialClass> getSpecialClassList(
			SpecialClassListKey specialClassListKey,UserSessionDetails userSessionDetails)
			throws NoDataFoundException {
		logger.debug("DAO :Inside Special Class List select method");
		category=userSessionDetails.getInbrCategory();
		List<Object> data = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		if((userSessionDetails.getInbrCategory().equals(CommonCodeConstant.IBCAT_SSLC))||(userSessionDetails.getInbrCategory().equals(CommonCodeConstant.IBCAT_ICSE))){
			sql.append("select ")		
			.append("AC_TERM,").append("SC_ITEM_ID,")
					.append("SC_DATE,").append("scls.STUDENTGRP_ID,").append("scls.CRSL_ID,").append("FROM_TIME,")
					.append("TO_TIME,").append("SC_RMKS,STUDENT_GRP").append(" from scls,stgm ")
					.append(" where")	
					.append(" scls.INST_ID=stgm.INST_ID")	
					.append(" and scls.BRANCH_ID=stgm.BRANCH_ID")	
					.append(" and scls.DEL_FLG=stgm.DEL_FLG")	
					.append(" and scls.STUDENTGRP_ID=stgm.STUDENTGRP_ID")	
					.append(" and  scls.DEL_FLG=?")
					.append(" and  scls.INST_ID= ?")
			.append(" and  scls.BRANCH_ID= ?");
			//.append(" order by SC_DATE");

			data.add("N");
			data.add(specialClassListKey.getInstId());
			data.add(specialClassListKey.getBranchId());
			
			
			
		}else{		
			
			
			
		sql.append("select ")
		.append("AC_TERM,").append("SC_ITEM_ID,")
				.append("SC_DATE,").append("scls.STUDENTGRP_ID,").append("scls.CRSL_ID,").append("FROM_TIME,")
				.append("TO_TIME,").append("SC_RMKS,").append("SUB_NAME ,").append("STUDENT_GRP ").append(" from scls,crsl,sbjm,stgm ")
				.append(" where")
				.append(" scls.INST_ID=crsl.INST_ID")
				.append(" and scls.BRANCH_ID=crsl.BRANCH_ID")
				.append(" and scls.DEL_FLG=crsl.DEL_FLG")
				.append(" and scls.CRSL_ID=crsl.CRSL_ID")
				
				.append(" and scls.INST_ID=stgm.INST_ID ")
				.append(" and scls.BRANCH_ID=stgm.BRANCH_ID" )
				.append(" and scls.DEL_FLG=stgm.DEL_FLG  ")
				.append(" and scls.STUDENTGRP_ID=stgm.STUDENTGRP_ID")
				
				.append(" and scls.INST_ID=sbjm.INST_ID")
				.append(" and scls.BRANCH_ID=sbjm.BRANCH_ID")
				.append(" and scls.DEL_FLG=sbjm.DEL_FLG")
				.append(" and sbjm.SUB_ID=crsl.SUB_ID")
				.append(" and  scls.DEL_FLG=?")
				.append(" and  scls.INST_ID= ?")
		.append(" and  scls.BRANCH_ID= ?");

		data.add("N");
		data.add(specialClassListKey.getInstId());
		data.add(specialClassListKey.getBranchId());
		}
		if ((specialClassListKey.getAcTerm() != null)
				&& (specialClassListKey.getAcTerm() != "")) {
			sql.append(" and AC_TERM=?  ");
			logger.debug("AC_TERM Value :" + specialClassListKey.getAcTerm());
			data.add(specialClassListKey.getAcTerm());
		}
		if ((specialClassListKey.getStudentGrpId() != null)
				&& (specialClassListKey.getStudentGrpId() != "")) {
			sql.append(" and scls.STUDENTGRP_ID=?  ");
			logger.debug("STUDENTGRP_ID Value :"
					+ specialClassListKey.getStudentGrpId());
			data.add(specialClassListKey.getStudentGrpId());
		}
		if ((specialClassListKey.getScDate() != null)
				&& (specialClassListKey.getScDate() != "")) {
			sql.append(" and SC_DATE=?  ");
			logger.debug("SC_DATE Value :"
					+ specialClassListKey.getScDate());
			data.add(specialClassListKey.getScDate());
		}
		if ((specialClassListKey.getCrslId() != null)
				&& (specialClassListKey.getCrslId() != "")) {
			sql.append(" and scls.CRSL_ID=?  ");
			logger.debug("CRSL_ID Value :"
					+ specialClassListKey.getCrslId());
			data.add(specialClassListKey.getCrslId());
		}
		sql.append(" order by SC_DATE");
System.out.println("queryyyyyyyyyyyyyyyyyyyyyyyyyyy"+sql.toString());
		Object[] array = data.toArray(new Object[data.size()]);
		logger.debug("Where clause conditions size :" + array.length);

		List<SpecialClass> selectedListSpecialClass = getJdbcTemplate().query(
				sql.toString(), array, new SpecialClassSelectRowMapper());
		if (selectedListSpecialClass.size() == 0) {
			throw new NoDataFoundException();
		}
		logger.debug("DAO : Special Class List value"
				+ selectedListSpecialClass.size());
		return selectedListSpecialClass;
	}

	

class SpecialClassSelectRowMapper implements RowMapper<SpecialClass> {

	@Override
	public SpecialClass mapRow(ResultSet rs, int arg1) throws SQLException {
		SpecialClass splCls = new SpecialClass();
		System.out.println("ac termmm "+rs.getString("AC_TERM"));
		splCls.setAcTerm(rs.getString("AC_TERM"));
		splCls.setScItemId(rs.getString("SC_ITEM_ID"));
		splCls.setScDate(rs.getString("SC_DATE"));
		splCls.setStudentGrpId(rs.getString("STUDENTGRP_ID"));
		splCls.setCrslId(rs.getString("CRSL_ID"));
		splCls.setFromTime(rs.getString("FROM_TIME"));
		splCls.setToTime(rs.getString("TO_TIME"));
		splCls.setScRmks(rs.getString("SC_RMKS"));
		splCls.setStudentGrpName(rs.getString("STUDENT_GRP"));
		if((!category.equals(CommonCodeConstant.IBCAT_SSLC))&&(!category.equals(CommonCodeConstant.IBCAT_ICSE))){
		splCls.setSubName(rs.getString("SUB_NAME"));
		
		}
		return splCls;
	}
}
}