package com.jaw.fee.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.jaw.common.constants.ApplicationConstant;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.fee.dao.FeeMasterListDao.FeeTypeRowMapper;
import com.jaw.framework.dao.BaseDao;

@Repository
public class FeeGenerationListDAO extends BaseDao implements
		IFeeGenerationListDAO {
	// Logging
	Logger logger = Logger.getLogger(FeeGenerationListDAO.class);

	@Override
	public List<StudentMasterForFeeGen> getStudentListForFeeGeneration(
			StudentMasterForFeeGenKey studentMasterForFeeGenKey)
			throws NoDataFoundException {
		logger.debug("DAO :Inside get Student List for fee generation method");
		logger.debug("DAO :Inside StudentMasterForFeeGenKey values : "
				+ studentMasterForFeeGenKey.toString());
		List<Object> data = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		
		sql.append("select (case when stin.admis_date between  atdt.adms_start_date and atdt.ADMS_END_DATE then 'Y' else 'N' end) as ")
	     .append("'IS_NEW_ADMISSION', stum.STUDENT_ADMIS_NO,ADMIS_DATE,ROLL_NUMBER,STUDENT_NAME,COURSE_VARIANT,ELECTIVE_1, ELECTIVE_2  from stum,stin,atdt ")
	.append("where stum.inst_id=stin.inst_id and stum.branch_id=stin.branch_id and stum.del_flg=stin.del_flg ")
			.append("and stum.inst_id=atdt.inst_id and stum.branch_id=atdt.branch_id and stum.del_flg=atdt.del_flg ")
					.append("and stin.academic_year=atdt.ac_term and stum.STUDENT_ADMIS_NO=stin.STUDENT_ADMIS_NO and")
				.append("  stum.INST_ID= ?").append(" and  stum.BRANCH_ID= ?")
				.append(" and  stum.ACADEMIC_YEAR=?").append(" and  stum.COURSE=?")
				.append(" and  stum.DEL_FLG=?");

		data.add(studentMasterForFeeGenKey.getInstId());
		data.add(studentMasterForFeeGenKey.getBranchId());
		data.add(studentMasterForFeeGenKey.getAcademicYear());
		data.add(studentMasterForFeeGenKey.getCourse());
		data.add("N");

		sql.append(" order by STUDENT_ADMIS_NO");
		Object[] array = data.toArray(new Object[data.size()]);
		List<StudentMasterForFeeGen> selectedListStuMstrForFeeGen = null;
		selectedListStuMstrForFeeGen = getJdbcTemplate().query(sql.toString(),
				array, new StudentListForFeeGenSelectRowMapper());

		if (selectedListStuMstrForFeeGen.size() == 0) {
			throw new NoDataFoundException();

		}

		logger.debug("DAO : Student List value"
				+ selectedListStuMstrForFeeGen.size());

		return selectedListStuMstrForFeeGen;
	}

	@Override
	public List<FeeMasterListForFeeGen> getFeeMasterNonElectiveListForFeeGeneration(
			FeeMasterKey feeMasterKey) throws NoDataFoundException {
		System.out.println("Fee master key :" + feeMasterKey.toString());
		List<FeeMasterListForFeeGen> results = null;
		List<String> data = new ArrayList<String>();
		StringBuffer query = new StringBuffer();

		query.append("SELECT ")
				.append("fmst.FEE_CATGRY,")
				.append("fmst.FEE_TERM,")
				.append("fmst.FEE_TYPE,")
				.append("FEE_AMT, ");
				if((feeMasterKey.getFeeTerm()!=null)&&(!feeMasterKey.getFeeTerm().equals(""))){
				query.append("FEE_PMT_TERM, ");
				} else {
					query.append(" '' as FEE_PMT_TERM, ");	
				}
				query.append("fmst.CV_SPEC ")
				.append(" FROM fmst,");
				if((feeMasterKey.getFeeTerm()!=null)&&(!feeMasterKey.getFeeTerm().equals(""))){
					query.append("ftrm,");
				}
				query.append("fctl")
				.append(" where ");
		if((feeMasterKey.getFeeTerm()!=null)&&(!feeMasterKey.getFeeTerm().equals(""))){
			query.append(" fmst.INST_ID=ftrm.INST_ID ")
				.append(" and fmst.BRANCH_ID=ftrm.BRANCH_ID ")
				.append(" and fmst.DEL_FLG=ftrm.DEL_FLG ")
				.append(" and fmst.FEE_TERM=ftrm.FEE_TERM and");
		}
		query.append("  fmst.INST_ID=fctl.INST_ID ")
				.append(" and fmst.BRANCH_ID=fctl.BRANCH_ID ")
				.append(" and fmst.DEL_FLG=fctl.DEL_FLG ")
				.append(" and fmst.FEE_CATGRY=fctl.FEE_CATGRY ")
				.append(" and fmst.FEE_TYPE=fctl.FEE_TYPE ")
				.append("and fmst.CV_SPEC in('"
						+ ApplicationConstant.NOT_APPLICABLE + "' ,'') ")
				.append("and fmst.inst_id=? ")
				.append("and fmst.branch_id=? ")
				.append("and fmst.AC_TERM=? ");
		if((feeMasterKey.getFeeCategory()!=null)&&(!feeMasterKey.getFeeCategory().equals(""))){
			query.append("and fmst.fee_catgry=? ");
		}
			
				query.append("and fmst.COURSE=? ")
				.append("and fmst.TERM=? ")
				.append("and fmst.DEL_FLG=?")
				.append(" and (fctl.ELECT_FEE_SUB_ID is null or fctl.ELECT_FEE_SUB_ID ='')");

		data.add(feeMasterKey.getInstId());
		data.add(feeMasterKey.getBranchId());
		data.add(feeMasterKey.getAcTerm());
		if((feeMasterKey.getFeeCategory()!=null)&&(!feeMasterKey.getFeeCategory().equals(""))){
		data.add(feeMasterKey.getFeeCategory());
		}
		data.add(feeMasterKey.getCourse());
		data.add(feeMasterKey.getTerm());
		data.add("N");

		String[] array = data.toArray(new String[data.size()]);

		results = getJdbcTemplate().query(query.toString(), array,
				new FeeMasterListRowMapper());
		if (results.size() == 0) {
			throw new NoDataFoundException();
		}
		logger.debug("Selected  records non elective :" + results.size());
		return results;
	}
	
	
	

	@Override
	public List<FeeMasterListForFeeGen> getFeeMasterElectiveListForFeeGeneration(
			FeeMasterKey feeMasterKey) throws NoDataFoundException {
		List<FeeMasterListForFeeGen> results = null;
		List<String> data = new ArrayList<String>();
		StringBuffer query = new StringBuffer();

		query.append("SELECT ")
				.append("fmst.FEE_CATGRY,")
				.append("fmst.FEE_TERM,")
				.append("fmst.FEE_TYPE,")
				.append("FEE_AMT, ")
				.append("FEE_PMT_TERM, ")
				.append("crsl.sub_type, ")
				.append("crsl.sub_id, ")
				.append("fmst.CV_SPEC ")
				.append(" FROM fmst,ftrm,fctl,crsl")
				.append(" where ");
				if((feeMasterKey.getFeeTerm()!=null)&&(!feeMasterKey.getFeeTerm().equals(""))){
				query.append(" fmst.INST_ID=ftrm.INST_ID ")
				.append(" and fmst.BRANCH_ID=ftrm.BRANCH_ID ")
				.append(" and fmst.DEL_FLG=ftrm.DEL_FLG ")
				.append(" and fmst.FEE_TERM=ftrm.FEE_TERM and");
				}
				query.append("  fmst.INST_ID=fctl.INST_ID ")
				.append(" and fmst.BRANCH_ID=fctl.BRANCH_ID ")
				.append(" and fmst.DEL_FLG=fctl.DEL_FLG ")
				.append(" and fmst.FEE_CATGRY=fctl.FEE_CATGRY ")
				.append(" and fmst.FEE_TYPE=fctl.FEE_TYPE ")
				.append(" and fctl.inst_id = crsl.inst_id ")
				.append(" and fctl.branch_id = crsl.branch_id ")
				.append(" and fctl.del_flg = crsl.del_flg ")
				.append(" and fmst.COURSE  = crsl.coursemaster_id ")
				.append(" and fmst.TERM=crsl.term_id ")
				.append("and fmst.inst_id=? ")
				.append("and fmst.branch_id=? ")
				.append("and fmst.AC_TERM=? ");
				if((feeMasterKey.getFeeCategory()!=null)&&(!feeMasterKey.getFeeCategory().equals(""))){
				query.append("and fmst.fee_catgry=? ");
				}
				query.append("and fmst.COURSE=? ")
				.append("and fmst.TERM=? ")
				.append("and fmst.DEL_FLG=?")
				.append(" and crsl.sub_type in ('E1','E2') and elect_fee_sub_id like concat('%',crsl.sub_id,'%')");

		data.add(feeMasterKey.getInstId());
		data.add(feeMasterKey.getBranchId());
		data.add(feeMasterKey.getAcTerm());
		if((feeMasterKey.getFeeCategory()!=null)&&(!feeMasterKey.getFeeCategory().equals(""))){
		data.add(feeMasterKey.getFeeCategory());
		}
		data.add(feeMasterKey.getCourse());
		
		data.add(feeMasterKey.getTerm());
		data.add("N");

		String[] array = data.toArray(new String[data.size()]);

		results = getJdbcTemplate().query(query.toString(), array,
				new FeeMasterElectiveListRowMapper());
		if (results.size() == 0) {
			throw new NoDataFoundException();
		}
		logger.debug("Selected  records elective:" + results.size());
		return results;
	}

	@Override
	public List<FeeMasterStatus> getFeeStatusListForValidation(
			FeeMasterStatus feeMasterStatus, List<String> terms)
			throws NoDataFoundException {
		logger.debug("DAO :Inside get Fee Status List for Validation method");
		logger.debug("DAO :Inside FeeMasterStatus values : "
				+ feeMasterStatus.toString());
		logger.debug("DAO :Inside Term size : " + terms.size());
		List<Object> data = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();

		sql.append("select ").append("TERM,").append("FEE_STS ")
				.append(" from fsts ").append(" where").append("  INST_ID= ?")
				.append(" and  BRANCH_ID= ?").append(" and  AC_TERM=?")
				.append(" and  COURSE=?");
		if ((terms != null) && (terms.size() > 0)) {
			sql.append(" and  TERM in ( ");
			for (int i = 0; i < terms.size(); i++) {
				if (i == 0) {
					sql.append("'" + terms.get(i) + "'");
				} else {
					sql.append(", '" + terms.get(i) + "'");
				}

			}
			sql.append(" ) ");
		}
		if ((feeMasterStatus.getFeeCategory() != null)
				&& (!feeMasterStatus.getFeeCategory().equals(""))) {
			sql.append(" and  FEE_CATGRY=?");
		}

		sql.append(" and  DEL_FLG=?");

		data.add(feeMasterStatus.getInstId());
		data.add(feeMasterStatus.getBranchId());
		data.add(feeMasterStatus.getAcTerm());
		data.add(feeMasterStatus.getCourse());
		if ((feeMasterStatus.getFeeCategory() != null)
				&& (!feeMasterStatus.getFeeCategory().equals(""))) {
			data.add(feeMasterStatus.getFeeCategory());
		}

		data.add("N");

		Object[] array = data.toArray(new Object[data.size()]);
		List<FeeMasterStatus> selectedFeeStatusList = null;
		selectedFeeStatusList = getJdbcTemplate().query(sql.toString(), array,
				new FeeStatusListRowMapper());

		if (selectedFeeStatusList.size() == 0) {
			throw new NoDataFoundException();

		}

		logger.debug("Fee status based on term list size" + selectedFeeStatusList.size());

		return selectedFeeStatusList;
	}

	
	@Override
	public List<StudentFee> getTransportFeeForFeeGeneration(
			FeeMasterKey feeMasterKey) throws NoDataFoundException {
		List<StudentFee> results = null;
		List<String> data = new ArrayList<String>();
		StringBuffer query = new StringBuffer();
		query.append("SELECT stum.student_admis_no,amount FROM stum, trsd, trsm");
		query.append(" where  trsd.inst_id=stum.inst_id");
		query.append(" and trsd.branch_id=stum.branch_id");
		query.append(" and stum.student_admis_no=trsd.student_admis_no");
		query.append(" and stum.academic_year = stum.academic_year");
		query.append(" and trsd.inst_id=trsm.inst_id");
		query.append(" and trsd.branch_id=trsm.branch_id");
		query.append(" and trsd.pickup_point=trsm.pickup_point_id");
		query.append(" and trsd.academic_year=trsm.academic_year");
		query.append(" and course=?");
		query.append(" and stum.inst_id=?");
		query.append(" and stum.branch_id=?");
		query.append(" and stum.del_flg=?;");


		data.add(feeMasterKey.getCourse());
		data.add(feeMasterKey.getInstId());
		data.add(feeMasterKey.getBranchId());		
		data.add("N");

		String[] array = data.toArray(new String[data.size()]);

		results =  getJdbcTemplate().query(query.toString(), array,
				new TransportRowMapper());
		if (results.size() == 0) {
			throw new NoDataFoundException();
		}
		logger.debug("Selected  records for transport fee :" + results.size());
		return results;
	}


	
	@Override
	public List<FeeMasterListForFeeGen> getFeeMasterCourseVariantListForFeeGeneration(
			FeeMasterKey feeMasterKey) throws NoDataFoundException {
		List<FeeMasterListForFeeGen> results = null;
		List<String> data = new ArrayList<String>();
		StringBuffer query = new StringBuffer();

		query.append("SELECT ")
				.append("fmst.FEE_CATGRY,")
				.append("fmst.FEE_TERM,")
				.append("fmst.FEE_TYPE,")
				.append("FEE_AMT, ")
				.append("FEE_PMT_TERM, ")
				.append("fmst.CV_SPEC ")
				.append(" FROM fmst,ftrm,fctl")
				.append(" where ")
				.append(" fmst.INST_ID=ftrm.INST_ID ")
				.append(" and fmst.BRANCH_ID=ftrm.BRANCH_ID ")
				.append(" and fmst.DEL_FLG=ftrm.DEL_FLG ")
				.append(" and fmst.FEE_TERM=ftrm.FEE_TERM ")
				.append(" and fmst.INST_ID=fctl.INST_ID ")
				.append(" and fmst.BRANCH_ID=fctl.BRANCH_ID ")
				.append(" and fmst.DEL_FLG=fctl.DEL_FLG ")
				.append(" and fmst.FEE_CATGRY=fctl.FEE_CATGRY ")
				.append(" and fmst.FEE_TYPE=fctl.FEE_TYPE ")
				.append("and fmst.CV_SPEC!='"
						+ ApplicationConstant.NOT_APPLICABLE + "' ")
				.append("and fmst.inst_id=? ")
				.append("and fmst.branch_id=? ")
				.append("and fmst.AC_TERM=? ");
				if((feeMasterKey.getFeeCategory()!=null)&&(!feeMasterKey.getFeeCategory().equals(""))){
				query.append("and fmst.fee_catgry=? ");
				}
				query.append("and fmst.COURSE=? ")
				.append("and fmst.TERM=? ")
				.append("and fmst.DEL_FLG=?")
				.append(" and (fctl.ELECT_FEE_SUB_ID is null or fctl.ELECT_FEE_SUB_ID ='')");

		data.add(feeMasterKey.getInstId());
		data.add(feeMasterKey.getBranchId());
		data.add(feeMasterKey.getAcTerm());
		if((feeMasterKey.getFeeCategory()!=null)&&(!feeMasterKey.getFeeCategory().equals(""))){
		data.add(feeMasterKey.getFeeCategory());
		}
		data.add(feeMasterKey.getCourse());
		data.add(feeMasterKey.getTerm());
		data.add("N");

		String[] array = data.toArray(new String[data.size()]);

		results = getJdbcTemplate().query(query.toString(), array,
				new FeeMasterListRowMapper());
		if (results.size() == 0) {
			throw new NoDataFoundException();
		}
		logger.debug("Selected  records non elective :" + results.size());
		return results;
	}

}

class FeeStatusListRowMapper implements RowMapper<FeeMasterStatus> {

	@Override
	public FeeMasterStatus mapRow(ResultSet rs, int arg1) throws SQLException {
		FeeMasterStatus feeStatList = new FeeMasterStatus();
		feeStatList.setTerm(rs.getString("TERM"));
		feeStatList.setFeeStatus(rs.getString("FEE_STS"));
		return feeStatList;
	}
}

class StudentListForFeeGenSelectRowMapper implements
		RowMapper<StudentMasterForFeeGen> {

	@Override
	public StudentMasterForFeeGen mapRow(ResultSet rs, int arg1)
			throws SQLException {
		StudentMasterForFeeGen stuMstrList = new StudentMasterForFeeGen();
		stuMstrList.setIsNewAdmission(rs.getString("IS_NEW_ADMISSION"));
		stuMstrList.setStudentAdmisNo(rs.getString("STUDENT_ADMIS_NO"));
		stuMstrList.setRollno(rs.getInt("ROLL_NUMBER"));
		stuMstrList.setStudentName(rs.getString("STUDENT_NAME"));
		stuMstrList.setElective1(rs.getString("ELECTIVE_1"));
		stuMstrList.setElective2(rs.getString("ELECTIVE_2"));
		stuMstrList.setCourseVariant(rs.getString("COURSE_VARIANT"));
		return stuMstrList;
	}
}

class FeeMasterListRowMapper implements RowMapper<FeeMasterListForFeeGen> {
	@Override
	public FeeMasterListForFeeGen mapRow(ResultSet rs, int arg1)
			throws SQLException {
		FeeMasterListForFeeGen FeeMasterList = new FeeMasterListForFeeGen();
		FeeMasterList.setFeeCategory(rs.getString("FEE_CATGRY"));
		FeeMasterList.setFeeTerm(rs.getString("FEE_TERM"));
		FeeMasterList.setFeeType(rs.getString("FEE_TYPE"));
		FeeMasterList.setFeeAmt(rs.getInt("FEE_AMT"));
		FeeMasterList.setFeePaymentTerm(rs.getString("FEE_PMT_TERM"));
		FeeMasterList.setCourseVariant(rs.getString("CV_SPEC"));

		return FeeMasterList;
	}
}

class FeeMasterElectiveListRowMapper implements
		RowMapper<FeeMasterListForFeeGen> {
	@Override
	public FeeMasterListForFeeGen mapRow(ResultSet rs, int arg1)
			throws SQLException {
		FeeMasterListForFeeGen feeMasterList = new FeeMasterListForFeeGen();
		feeMasterList.setFeeCategory(rs.getString("FEE_CATGRY"));
		feeMasterList.setFeeTerm(rs.getString("FEE_TERM"));
		feeMasterList.setFeeType(rs.getString("FEE_TYPE"));
		feeMasterList.setFeeAmt(rs.getInt("FEE_AMT"));
		feeMasterList.setFeePaymentTerm(rs.getString("FEE_PMT_TERM"));
		feeMasterList.setSubjectType(rs.getString("SUB_TYPE"));
		feeMasterList.setSubjectId(rs.getString("SUB_ID"));
		feeMasterList.setCourseVariant(rs.getString("CV_SPEC"));

		return feeMasterList;

	}
}
	class TransportRowMapper implements
	RowMapper<StudentFee> {
@Override
public StudentFee mapRow(ResultSet rs, int arg1)
		throws SQLException {
	
	
	StudentFee studentFee = new StudentFee();
	studentFee.setStudentAdmissNo(rs.getString("student_admis_no"));
	studentFee.setFeeAmt((int) rs.getDouble("amount"));
	return studentFee;

}
}