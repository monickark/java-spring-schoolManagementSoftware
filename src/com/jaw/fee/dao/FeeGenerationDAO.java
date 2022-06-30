package com.jaw.fee.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import com.jaw.common.constants.ApplicationConstant;
import com.jaw.framework.dao.BaseDao;
import com.jaw.student.admission.dao.StudentMasterKey;
@Repository
public class FeeGenerationDAO extends BaseDao implements IFeeGenerationDAO{
	// Logging
	Logger logger = Logger.getLogger(FeeGenerationDAO.class);

	@Override
	public int checkFeeMasterEntered(FeeStatusKey feeStatusKey) {
		logger.debug("Inside Check Fee Master  method");
		logger.debug("FeeStatusKey object values :"+ feeStatusKey.toString());
		StringBuffer sql = new StringBuffer();	sql.append("select exists(")
		.append("select FEE_CATGRY ")
		.append("from fsts")				
				.append(" where ")			
				.append(" INST_ID='" + feeStatusKey.getInstId()+ "'")
				.append(" and BRANCH_ID='" + feeStatusKey.getBranchId()+ "'")
				.append(" and  AC_TERM='" + feeStatusKey.getAcTerm()+ "'")				
				.append(" and  COURSE='" + feeStatusKey.getCourseMasterId()+ "'")
				.append(" and  TERM='" +feeStatusKey.getTermId()+ "'")				
				.append(" and  FEE_STS='" + feeStatusKey.getFeeStatus()+ "'")			
				.append(" and  FEE_CATGRY='" + feeStatusKey.getFeeCategory()+ "'")			
				.append(" and DEL_FLG='N')");
						
		int feeMaster = getJdbcTemplate().queryForInt(
				sql.toString());

		System.out.println("Fee Master Entered"+feeMaster);

		return feeMaster;
	}

	@Override
	public int checkStudentsAvailable(StudentMasterKey studentMasterKey,
			String courseMasterId, String termId) {
		logger.debug("Inside Check Student Available method for fee generation");
		logger.debug("studentMasterKey object values :"+ studentMasterKey.toString());
		logger.debug("course : "+courseMasterId +" Term Id : "+termId);		
		StringBuffer sql = new StringBuffer();	sql.append("select exists(")
		.append("select STUDENT_ADMIS_NO ")
		.append("from stum")				
				.append(" where ")			
				.append(" INST_ID='" + studentMasterKey.getInstId()+ "'")
				.append(" and BRANCH_ID='" + studentMasterKey.getBranchId()+ "'")
				.append(" and  ACADEMIC_YEAR='" + studentMasterKey.getAcademicYear()+ "'")				
				.append(" and  COURSE='" + courseMasterId+ "'")	
				.append(" and DEL_FLG='N')");
						
		int stuAva = getJdbcTemplate().queryForInt(
				sql.toString());

		System.out.println("Students available method :"+stuAva);

		return stuAva;
	}

	@Override
	public FeeMasterListForFeeGen checkCourseVariantExist(
			FeeMasterKey feeMasterKey) {
		/*select  from
		        
		          
		 ;*/
		
		List<String> data = new ArrayList<String>();
		StringBuffer sql = new StringBuffer();
		sql.append("select ").append(" COURSE_VARIANT ").append(" from  ")
				.append(" (select distinct(COURSE_VARIANT) ").append("  from stum ")
				.append("  where INST_ID=? ").append(" and BRANCH_ID=? ")
				.append(" and  DEL_FLG='N'").append("  and COURSE=?")
				.append(" and STANDARD=?")
				.append(" and ACADEMIC_YEAR=?")
				.append(" order by COURSE_VARIANT) ")
				.append(" as STCV left join ")
				.append(" (select CV_SPEC ")
				.append(" from fmst")
				.append(" where INST_ID=?")				
				.append(" and BRANCH_ID=?")
				.append(" and DEL_FLG='N'")
				.append(" and COURSE=?")
				.append(" and TERM=?")
				.append(" and CV_SPEC != '"+ApplicationConstant.NOT_APPLICABLE+"'")
				.append(" and AC_TERM=?")
				
				.append(" order by CV_SPEC) ")
				.append(" as FMCV ")
				.append(" on STCV.COURSE_VARIANT = FMCV.CV_SPEC ")
				.append(" where STCV.COURSE_VARIANT is null");
		data.add(feeMasterKey.getInstId());
		data.add(feeMasterKey.getBranchId());
		data.add(feeMasterKey.getCourse());
		data.add(feeMasterKey.getTerm());
		data.add(feeMasterKey.getAcTerm());
		data.add(feeMasterKey.getInstId());
		data.add(feeMasterKey.getBranchId());
		data.add(feeMasterKey.getCourse());
		data.add(feeMasterKey.getTerm());
		data.add(feeMasterKey.getAcTerm());

		String[] array = data.toArray(new String[data.size()]);
		FeeMasterListForFeeGen feeMasterListForFeeGen = null;
		feeMasterListForFeeGen = (FeeMasterListForFeeGen) getJdbcTemplate().query(sql.toString(), array,
				new ResultSetExtractor<FeeMasterListForFeeGen>() {

					@Override
					public FeeMasterListForFeeGen extractData(ResultSet rs)
							throws SQLException, DataAccessException {
						FeeMasterListForFeeGen FeeMaster = null;
						if (rs.next()) {

							FeeMaster = new FeeMasterListForFeeGen();
							FeeMaster.setCourseVariant(rs.getString("COURSE_VARIANT"));
						}
						return FeeMaster;
					}

				});

		return feeMasterListForFeeGen;
	}

	

}
