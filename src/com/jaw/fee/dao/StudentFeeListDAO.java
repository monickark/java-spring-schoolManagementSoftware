package com.jaw.fee.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.framework.dao.BaseDao;

@Repository
public class StudentFeeListDAO extends BaseDao implements IStudentFeeListDAO {
	// Logging
	Logger logger = Logger.getLogger(StudentFeeListDAO.class);

	@Override
	public void insertStudentFeeListRecs(final List<StudentFee> studentFeeList)
			throws DuplicateEntryException {
		logger.debug("Inside Student Fee List Insert Record");
		logger.debug("Student Fee List Size in dao  " + studentFeeList.size());
		StringBuffer query = new StringBuffer();

		for (int i = 0; i < studentFeeList.size(); i++) {
			System.out.println("student values  : "
					+ studentFeeList.get(i).toString());
		}
		query = query.append("insert into sfee ( ").append("DB_TS,")
				.append("INST_ID,").append("BRANCH_ID,").append("AC_TERM,")
				.append("STUDENT_ADMIS_NO,").append("FEE_CATGRY,")
				.append("FEE_TERM,").append("FEE_TYPE,").append("ELECT_SPEC,")
				.append("FEE_AMT,").append("FEE_PMT_TERM,").append("CV_SPEC,")
				.append("DEL_FLG,").append("R_MOD_ID,").append("R_MOD_TIME,")
				.append("R_CRE_ID,").append("R_CRE_TIME)")
				.append(" values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,now(),?,now())");
		try {
			int[] a = getJdbcTemplate().batchUpdate(query.toString(),
					new BatchPreparedStatementSetter() {

						@Override
						public void setValues(PreparedStatement pss, int i)
								throws SQLException {

							StudentFee stuFee = studentFeeList.get(i);

							pss.setInt(1, stuFee.getDbTs());
							pss.setString(2, stuFee.getInstId());
							pss.setString(3, stuFee.getBranchId());
							pss.setString(4, stuFee.getAcTerm().trim());
							pss.setString(5, stuFee.getStudentAdmissNo());
							pss.setString(6, stuFee.getFeeCategory());
							pss.setString(7, stuFee.getFeeTerm());
							pss.setString(8, stuFee.getFeeType().trim());
							pss.setString(9, stuFee.getElecticeSpec());
							pss.setInt(10, stuFee.getFeeAmt());
							pss.setString(11, stuFee.getFeePaymentTerm());
							pss.setString(12, stuFee.getCourseVariant());
							pss.setString(13, stuFee.getDelFlag().trim());
							pss.setString(14, stuFee.getrModId().trim());
							pss.setString(15, stuFee.getrCreId().trim());
						}

						@Override
						public int getBatchSize() {
							return studentFeeList.size();
						}
					}

			);
		} catch (org.springframework.dao.DuplicateKeyException e) {
			logger.error("Duplicate Entry Exception Occured  " + e.getMessage());
			throw new DuplicateEntryException();
		}

	}

	@Override
	public List<StudentFee> selectStudentFeeListRecsForStuDmd(
			StudentFeeKey studentFeeKey, String course, String term)
			throws NoDataFoundException {
		logger.debug("DAO :Inside get Student fee List method");
		logger.debug("DAO :Inside StudentFeeKey values : "
				+ studentFeeKey.toString());
		logger.debug("DAO :Course : " + course + "Dao :term : " + term);
		List<Object> data = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();

		sql.append("select ").append("sfee.AC_TERM,")
				.append("sfee.STUDENT_ADMIS_NO,").append("sfee.FEE_CATGRY,")
				.append("FEE_PMT_TERM, ")
				.append("sum(sfee.FEE_AMT) as FEEAMOUNT ")
				.append(" from sfee,stum ").append(" where")
				.append(" stum.INST_ID=sfee.INST_ID")
				.append(" and stum.BRANCH_ID=sfee.BRANCH_ID")
				.append(" and stum.DEL_FLG=sfee.DEL_FLG")
				.append(" and stum.academic_year = sfee.AC_TERM")
				.append(" and stum.student_admis_no = sfee.student_admis_no")
				.append(" and sfee.INST_ID= ?")
				.append(" and sfee.BRANCH_ID= ?").append(" and sfee.AC_TERM=?");
		if((studentFeeKey.getFeeCategory()!=null)&&(!studentFeeKey.getFeeCategory().equals(""))){
				sql.append(" and sfee.FEE_CATGRY=?");
		}
				sql.append("  and sfee.DEL_FLG=?")

				.append("  and COURSE=?")
				.append("  group by FEE_PMT_TERM,STUDENT_ADMIS_NO")
				.append("  order by STUDENT_ADMIS_NO");

		data.add(studentFeeKey.getInstId());
		data.add(studentFeeKey.getBranchId());
		data.add(studentFeeKey.getAcTerm());
		if((studentFeeKey.getFeeCategory()!=null)&&(!studentFeeKey.getFeeCategory().equals(""))){
		data.add(studentFeeKey.getFeeCategory());
		}
		data.add("N");
		data.add(course);

		Object[] array = data.toArray(new Object[data.size()]);
		List<StudentFee> selectedListStudentFee = null;
		selectedListStudentFee = getJdbcTemplate().query(sql.toString(), array,
				new StudentFeeListSelectRowMapper());

		if (selectedListStudentFee.size() == 0) {
			throw new NoDataFoundException();

		}

		logger.debug("DAO : Student List value" + selectedListStudentFee.size());

		return selectedListStudentFee;
	}

	@Override
	public List<StudentFee> selectStudentFeeListForFeePaidList(
			StudentFee studentFee) throws NoDataFoundException {
		logger.debug("DAO :Inside get Student fee List method");
		logger.debug("DAO :Inside StudentFee values : " + studentFee.toString());
		List<Object> data = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();

		sql.append("select ").append("code_desc,").append("elect_spec,")
				.append("sum(fee_amt) as fee_amt ").append(" from sfee ,cocd ")
				.append(" where").append(" sfee.BRANCH_ID=cocd.BRANCH_ID AND ")
				.append(" sfee.FEE_TYPE=cocd.CM_CODE AND ")
				.append(" sfee.del_flg=cocd.del_flg AND ")
				.append("  sfee.INST_ID= ?").append(" and  sfee.BRANCH_ID= ?")
				.append(" and  AC_TERM=?").append(" and  STUDENT_ADMIS_NO=?");
				if((studentFee.getFeeCategory()!=null)&&(!studentFee.getFeeCategory().equals(""))){
				sql.append(" and  FEE_CATGRY=?");
				}
				if((studentFee.getFeePaymentTerm()!=null)&&(!studentFee.getFeePaymentTerm().equals(""))){
				sql.append(" and  FEE_PMT_TERM=?");
				}
				sql.append(" and  FEE_TYPE!=?").append(" and  sfee.DEL_FLG=?")
				.append(" group by code_desc,elect_spec ")
				.append(" order by code_desc,elect_spec ");

		data.add(studentFee.getInstId());
		data.add(studentFee.getBranchId());
		data.add(studentFee.getAcTerm());
		data.add(studentFee.getStudentAdmissNo());
		if((studentFee.getFeeCategory()!=null)&&(!studentFee.getFeeCategory().equals(""))){
		data.add(studentFee.getFeeCategory());
		}
		if((studentFee.getFeePaymentTerm()!=null)&&(!studentFee.getFeePaymentTerm().equals(""))){
		data.add(studentFee.getFeePaymentTerm());
		}
		data.add(studentFee.getFeeType());
		data.add("N");

		Object[] array = data.toArray(new Object[data.size()]);
		List<StudentFee> selectedListStudentFee = null;
		selectedListStudentFee = getJdbcTemplate().query(sql.toString(), array,
				new StudentFeeForFeePaidListSelectRowMapper());

		if (selectedListStudentFee.size() == 0) {
			throw new NoDataFoundException();

		}

		logger.debug("DAO : Student List value" + selectedListStudentFee.size());

		return selectedListStudentFee;
	}
}

class StudentFeeForFeePaidListSelectRowMapper implements RowMapper<StudentFee> {

	@Override
	public StudentFee mapRow(ResultSet rs, int arg1) throws SQLException {
		StudentFee stuFee = new StudentFee();
		stuFee.setFeeType(rs.getString("code_desc"));
		stuFee.setElecticeSpec(rs.getString("ELECT_SPEC"));
		stuFee.setFeeAmt(rs.getInt("fee_amt"));
		return stuFee;
	}
}

class StudentFeeListSelectRowMapper implements RowMapper<StudentFee> {

	@Override
	public StudentFee mapRow(ResultSet rs, int arg1) throws SQLException {
		StudentFee stuFee = new StudentFee();
		stuFee.setAcTerm(rs.getString("AC_TERM"));
		stuFee.setStudentAdmissNo(rs.getString("STUDENT_ADMIS_NO"));
		stuFee.setFeeCategory(rs.getString("FEE_CATGRY"));
		stuFee.setFeePaymentTerm(rs.getString("FEE_PMT_TERM"));
		stuFee.setFeeAmt(rs.getInt("FEEAMOUNT"));
		return stuFee;
	}
}
