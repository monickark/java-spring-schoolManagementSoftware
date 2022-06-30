package com.jaw.mark.dao;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.framework.dao.BaseDao;
@Repository
public class MarkMasterListDAO extends BaseDao implements
IMarkMasterListDAO {
Logger logger = Logger.getLogger(MarkMasterListDAO.class);

@Override
public List<MarkMaster> getMarkMasterList(MarkMasterListKey markMasterListKey) throws NoDataFoundException {
	logger.debug("DAO :Inside mark Master List select method");
	System.out.println("Mark master list key :"+markMasterListKey.toString());
	List<Object> data = new ArrayList<Object>();
	StringBuffer sql = new StringBuffer();
	 sql.append("select ")   		
		.append("MM_SEQ_ID,")
		.append("AC_TERM,")
		.append("mkmt.STUDENTGRP_ID,")
		.append("EXAM_ID,")
		.append("EXAM_ORDR_WI_SG,")		
		.append("EXAM_TEST,")
		.append("ATT_TERM_START_DATE,")				
		.append("ATT_TERM_END_DATE,")
		.append("EXP_RPT_DATE,")
		.append("ACT_RPT_DATE,")
		.append("STATUS,")
		.append("STUDENT_GRP")
		.append(" from mkmt,stgm ")
		.append(" where")
		.append("  mkmt.INST_ID=stgm.INST_ID")
		.append("  and mkmt.BRANCH_ID=stgm.BRANCH_ID")
		.append("  and mkmt.DEL_FLG=stgm.DEL_FLG")
		.append("  and mkmt.STUDENTGRP_ID=stgm.STUDENTGRP_ID")
			.append("  and mkmt.INST_ID= ?")
		.append(" and  mkmt.BRANCH_ID= ?")
		.append(" and  mkmt.DEL_FLG=?");
		data.add(markMasterListKey.getInstId());
		data.add(markMasterListKey.getBranchId());
		data.add("N");
		if ((markMasterListKey.getAcTerm()!=null)&&(markMasterListKey.getAcTerm()!="")
				) {
			sql.append(" and mkmt.AC_TERM=?  ");
			logger.debug("AC Term Value :" + markMasterListKey.getAcTerm());
			data.add(markMasterListKey.getAcTerm());
		}
		if ((markMasterListKey.getStudentGrpId()!=null)&&(markMasterListKey.getStudentGrpId()!="")
				) {
			sql.append(" and mkmt.STUDENTGRP_ID=?  ");
			logger.debug("Student Group Value :" + markMasterListKey.getStudentGrpId());
			data.add(markMasterListKey.getStudentGrpId());
		}
		if ((markMasterListKey.getExamId()!=null)&&(markMasterListKey.getExamId()!="")
				) {
			sql.append(" and EXAM_ID=?  ");
			logger.debug("EXAM_ID Value :" + markMasterListKey.getExamId());
			data.add(markMasterListKey.getExamId());
		}
		
	sql.append(" order by MM_SEQ_ID ");	

	Object[] array = data.toArray(new Object[data.size()]);
	logger.debug("Where clause conditions size :" + array.length);
	List<MarkMaster> selectedListMarkMaster = getJdbcTemplate()
			.query(sql.toString(), array, new MarkMasterSelectRowMapper());
	if (selectedListMarkMaster.size() == 0) {
		throw new NoDataFoundException();
	}
	logger.debug("DAO : AcademicTermDetails List value"+selectedListMarkMaster.size() );
	return selectedListMarkMaster;
}

}

class MarkMasterSelectRowMapper implements RowMapper<MarkMaster> {

	@Override
	public MarkMaster mapRow(ResultSet rs, int arg1) throws SQLException {
		MarkMaster markMaster = new MarkMaster();		
		
		
	markMaster.setmMSeqId(rs.getString("MM_SEQ_ID"));
	markMaster.setExamId(rs.getString("EXAM_ID"));
	markMaster.setExamOrdrWiSG(rs.getString("EXAM_ORDR_WI_SG"));	
	markMaster.setExamTest(rs.getString("EXAM_TEST"));
	markMaster.setAttTermStartDate(rs.getString("ATT_TERM_START_DATE"));
	markMaster.setAttTermEndDate(rs.getString("ATT_TERM_END_DATE"));
	markMaster.setExpRptDate(rs.getString("EXP_RPT_DATE"));	
	markMaster.setActRptDate(rs.getString("ACT_RPT_DATE"));
	markMaster.setStatus(rs.getString("STATUS"));
	markMaster.setAcTerm(rs.getString("AC_TERM"));
	markMaster.setStudentGrpId(rs.getString("STUDENTGRP_ID"));
	markMaster.setStudentGrpName(rs.getString("STUDENT_GRP"));
	
	return markMaster;
	}
	}
