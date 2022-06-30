package com.jaw.student.admission.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.jaw.common.constants.ApplicationConstant;
import com.jaw.common.exceptions.BatchUpdateFailedException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.SectionNotAllocatedException;
import com.jaw.common.exceptions.batch.DataIntegrityException;
import com.jaw.common.exceptions.batch.RuntimeExceptionForBatch;
import com.jaw.framework.dao.BaseDao;
import com.jaw.student.controller.BatchSectUpdate;
import com.jaw.student.controller.StudentRollSearchVO;
import com.jaw.student.controller.StudentSearchVO;

@Repository
public class StudentMasterListDAO extends BaseDao implements IStudentMasterListDAO {
	Logger logger = Logger.getLogger(StudentMasterListDAO.class);

	int[] ret;

	public static java.sql.Date getCurrentJavaSqlDate() {
		java.util.Date today = new java.util.Date();
		return new java.sql.Date(today.getTime());
	}

	@Override
	public int[] insertBatch(final List<BatchSectUpdate> batchup,final String INSTID,final String BRANCHID,final String ACYEAR) throws DuplicateEntryException {

		try {			
			
			StringBuffer sql = new StringBuffer("UPDATE stum SET DB_TS=?,")
						.append("SEC=?, ")
						.append("STUDENTGRP_ID=?, ")
						.append("R_MOD_ID=?, ")
						.append("R_MOD_TIME=now() ")						
						.append("WHERE ")
						.append("INST_ID=? AND ")
						.append("BRANCH_ID=? AND ")
						.append("ACADEMIC_YEAR=? AND ")						
						.append("STUDENT_ADMIS_NO=?");
				logger.debug("sql query " + sql.toString());
			
			
			ret = getJdbcTemplate()
					.batchUpdate(sql.toString(),
							new BatchPreparedStatementSetter() {

								@Override
								public void setValues(PreparedStatement ps,
										int i) throws SQLException {
									BatchSectUpdate batch = batchup.get(i);
									ps.setInt(1, Integer.valueOf(batch.getDbts().trim())+1);
								//	ps.setString(2, batch.getSec().trim());
									ps.setString(2,batch.getSec().trim());
									ps.setString(3, batch.getStuGrpId().trim());
									ps.setString(4, batch.getrModId().trim());
									ps.setString(5, INSTID);
									ps.setString(6,BRANCHID);
									ps.setString(7,ACYEAR);
									ps.setString(8,batch.getStuAdmisNo());
									
									logger.info(i);
									logger.info("Inst Id : "+INSTID);
									logger.info("Branch Id : "+BRANCHID);
									logger.info("Academic Year : "+ACYEAR);
									logger.info("Admis No : "+batch.getStuAdmisNo());
								
									logger.info(batch.getSec());
								}

								@Override
								public int getBatchSize() {
									return batchup.size();
								}
							});

		} catch (DuplicateKeyException duplicateKeyException) {
			logger.error("Duplicate Entry in FileMasterDAO",
					duplicateKeyException);
			throw new DuplicateEntryException();
		}
		
		
		return ret;

	}

	@Override
	public List<StudentMaster> retrieveStudentMasterList(StudentSearchVO studentSearchVO) throws NoDataFoundException {
		logger.debug("Inside retrieveStudentMasterList method : key values are :"+studentSearchVO);

		StringBuffer sql = new StringBuffer();
		List<Object> data = new ArrayList<Object>();	
		
		sql.append("SELECT ")
		.append("stum.DB_TS,")
		.append("stum.INST_ID,")
		.append("stum.BRANCH_ID,")				
		.append("ACADEMIC_YEAR,").append("STUDENT_ADMIS_NO,")
		.append("ROLL_NUMBER,").append("COURSE,").append("STANDARD, ")
		.append("SEC, ").append("COURSE_VARIANT_CAT, ")
		.append("COURSE_VARIANT, ").append("STUDENT_TYPE, ")
		.append("COMBINATION,").append("STUDENT_NAME,")
		.append("HOUSE_NAME,")
		.append("SECOND_LANG, ").append("SUB_NAME, ").append("THIRD_LANG, ")
		.append("ELECTIVE_1, ").append("ELECTIVE_2, ")
		.append("STUDENT_BATCH, ").append("LAB_BATCH, ")
		.append("REASON_FOR_LEAVING, ").append("RELIGIOUS_SUB, ")
		.append("TRANSFERRED, ").append("ACCOUNT_NO, ")
		.append("stum.DEL_FLG, ").append("stum.R_MOD_ID, ")
		.append("stum.R_MOD_TIME, ").append("stum.R_CRE_ID, ")
		.append("stum.R_CRE_TIME, ").append("REG_NO, ").append("MEDIUM, ")
		.append("TRANSFER_DATE ")
		.append("FROM stum  ")
		.append("LEFT JOIN sbjm  ON  ")
		.append("stum.SECOND_LANG=sbjm.SUB_ID AND  ")
		.append("stum.INST_ID=sbjm.INST_ID AND ")
		.append("stum.BRANCH_ID=sbjm.BRANCH_ID ")
		.append("AND stum.DEL_FLG=sbjm.DEL_FLG WHERE ")	
				.append(" ACADEMIC_YEAR = ?")					
				.append(" AND ").append("stum.INST_ID=?")					
				.append(" AND ").append("stum.BRANCH_ID=?")			
				.append(" AND ").append("stum.DEL_FLG=?");				
		data.add(studentSearchVO.getAcademicYear());
		data.add(studentSearchVO.getInstId());
		data.add(studentSearchVO.getBranchId());			
		data.add("N");
	
		if((studentSearchVO.getCourse()!=null)&&(!studentSearchVO.getCourse().equals(""))){
			sql.append(" AND stum.COURSE=?");
			data.add(studentSearchVO.getCourse());
		}
		
		if((studentSearchVO.getStuGrpId()!=null)&&(!studentSearchVO.getStuGrpId().equals(""))){
			sql.append(" AND stum.STUDENTGRP_ID=?");
			data.add(studentSearchVO.getStuGrpId());
		}
			
		sql.append(" ORDER BY STUDENT_NAME");
			
			
	logger.debug("sql query" + sql.toString());
	logger.debug("key values :"+data);
	Object[] array = data.toArray(new Object[data.size()]);
	List<StudentMaster> searchResult = getJdbcTemplate().query(
			sql.toString(),array, new RowMapper<StudentMaster>() {

				@Override
				public StudentMaster mapRow(ResultSet rs, int arg1)
						throws SQLException {
					StudentMaster studentMaster = new StudentMaster();
					studentMaster.setDbTs(rs
							.getInt("DB_TS"));
					studentMaster.setStudentAdmisNo(rs
							.getString("STUDENT_ADMIS_NO"));
					studentMaster.setRegNo(rs
							.getString("REG_NO"));
					studentMaster.setAcademicYear(rs.getString("ACADEMIC_YEAR"));
					studentMaster.setCourse(rs.getString("COURSE"));
					studentMaster.setStudentName(rs
							.getString("STUDENT_NAME"));
					studentMaster.setStandard(rs.getString("STANDARD"));
					studentMaster.setSec(rs.getString("SEC"));
					studentMaster.setRollno(rs.getInt("ROLL_NUMBER"));
					studentMaster.setCourseVariantCat(rs.getString("COURSE_VARIANT_CAT"));
					studentMaster.setCourseVariant(rs.getString("COURSE_VARIANT"));
					studentMaster.setStudentType(rs.getString("STUDENT_TYPE"));
					studentMaster.setCombination(rs
							.getString("COMBINATION"));
					studentMaster.setSecoundLang(rs
							.getString("SECOND_LANG"));
					studentMaster.setSecLangDesc(rs
							.getString("SUB_NAME"));
					studentMaster.setThirdLang(rs.getString("THIRD_LANG"));
					studentMaster.setHouseName(rs.getString("HOUSE_NAME"));
					studentMaster.setElective1(rs.getString("ELECTIVE_1"));
					studentMaster.setElective2(rs.getString("ELECTIVE_2"));
					studentMaster.setStudentBatch(rs.getString("STUDENT_BATCH"));
					studentMaster.setLabBatch(rs.getString("LAB_BATCH"));

					return studentMaster;
				}

			});
	if(searchResult.size()==0){
		throw new NoDataFoundException();
	}
	return searchResult;
	}

	@Override
	public void updateRollNo(final List<StudentMaster> studentMasterList) throws BatchUpdateFailedException {		
		StringBuffer sql = new StringBuffer("update studentmaster set ")
				.append("DB_TS=?, ").append("R_MOD_ID=?, ")
				.append("R_MOD_TIME=now(), ").append("ROLL_NUMBER=? ")
				.append("where STUDENT_ADMIS_NO=? and ")
				.append("INST_ID=? and ").append("BRANCH_ID=?");
		logger.debug("sql query" + sql.toString());
		int[] ret = null;

		ret = 	getJdbcTemplate().batchUpdate(sql.toString(),
				new BatchPreparedStatementSetter() {

					@Override
					public void setValues(PreparedStatement ps, int i)
							throws SQLException {
						StudentMaster stduentMaster = studentMasterList.get(i);
						ps.setInt(1, stduentMaster.getDbTs());
						ps.setString(2, stduentMaster.getrModId());						
						ps.setInt(3, stduentMaster.getRollno());
						ps.setString(4, stduentMaster.getStudentAdmisNo());
						ps.setString(5, stduentMaster.getInstId());
						ps.setString(6, stduentMaster.getBranchId());

					}

					@Override
					public int getBatchSize() {
						return studentMasterList.size();
					}
				});
		for (int sa : ret) {
			if (sa == 0) {
				throw new BatchUpdateFailedException();
			}
		}
		
	}

	@Override
	public List<StudentMaster> retrieveStudentMasterListForRollNoList(
			StudentRollSearchVO studentRollSearchVO) throws  SectionNotAllocatedException {
		logger.debug("StudentRollSearchVO :"+studentRollSearchVO);
		StringBuffer sql = new StringBuffer()
		
		.append("select stum.DB_TS,stum.STUDENT_ADMIS_NO,REG_NO,STUDENT_NAME,ROLL_NUMBER,stin.GENDER from stum,stin ")
		.append("where stum.INST_ID=stin.INST_ID ")
		.append("and stum.BRANCH_ID=stin.BRANCH_ID ")
		.append("and  stum.STUDENT_ADMIS_NO = stin.STUDENT_ADMIS_NO and stum.DEL_FLG=stin.DEL_FLG ")
		.append("and stum.ACADEMIC_YEAR = stin.ACADEMIC_YEAR and ")
		.append("stum.ACADEMIC_YEAR ='").append(studentRollSearchVO.getAcademicYear().trim())
		.append("' and ").append("STUDENTGRP_ID='")
		.append(studentRollSearchVO.getStudentGrpId().trim());									
		
				/*.append("select DB_TS,STUDENT_ADMIS_NO,REG_NO,STUDENT_NAME,ROLL_NUMBER,GENDER from studentmaster  ")
				.append("where ACADEMIC_YEAR ='")		
		sql.append(studentRollSearchVO.getAcademicYear().trim())				
			.append("' and ").append("STUDENTGRP_ID='")
			.append(studentRollSearchVO.getStudentGrpId().trim());*/
		
		sql.append("' and stum.DEL_FLG='N' ORDER BY ");		
		
		if(studentRollSearchVO.getOrdertwo().equals(ApplicationConstant.ORDER_2_GEN1)){
			sql.append("GENDER DESC,");
		}else if(studentRollSearchVO.getOrdertwo().equals(ApplicationConstant.ORDER_2_GEN2)){
			sql.append("GENDER ASC,");
		}		
		if(studentRollSearchVO.getOrderone().equals(ApplicationConstant.ORDER_1_SORT1)){
			sql.append("STUDENT_ADMIS_NO,");
		}else if(studentRollSearchVO.getOrderone().equals(ApplicationConstant.ORDER_1_SORT2)){
			sql.append("STUDENT_NAME,");
		}		
		
		sql.append("ROLL_NUMBER");
		logger.debug("sql query" + sql.toString());
		List<StudentMaster> searchResult = getJdbcTemplate().query(
				sql.toString(), new RetriveStudentMaster());
		
		if(searchResult.size()==0){
			throw new SectionNotAllocatedException();
		}

		return searchResult;
	}

	@Override
	public void insertStudentMasterList(final List<StudentMaster> studentList)
			throws DataIntegrityException, RuntimeExceptionForBatch {		
		StringBuffer sql = new StringBuffer("insert into stum(")

				.append("DB_TS,")
				.append("INST_ID,")
				.append("BRANCH_ID,")
				//.append("PARENT_USER_ID,")
				.append("ACADEMIC_YEAR,")
				.append("STUDENT_ADMIS_NO,")
				.append("ROLL_NUMBER,")
				.append("COURSE,")
				.append("STANDARD,")
				.append("SEC, ")
				.append("STUDENTGRP_ID, ")
				.append("COURSE_VARIANT_CAT, ")
				.append("COURSE_VARIANT, ")
				.append("STUDENT_TYPE, ")
				.append("COMBINATION,")
				.append("STUDENT_NAME,")				
				.append("HOUSE_NAME,")
				.append("SECOND_LANG, ")
				.append("THIRD_LANG, ")
				.append("ELECTIVE_1, ")
				.append("ELECTIVE_2, ")
				.append("STUDENT_BATCH, ")
				.append("LAB_BATCH, ")
				.append("REASON_FOR_LEAVING, ")
				.append("RELIGIOUS_SUB, ")
				.append("TRANSFERRED, ")
				.append("ACCOUNT_NO, ")
				.append("DEL_FLG, ")
				.append("R_MOD_ID, ")
				.append("R_MOD_TIME,")
				.append("R_CRE_ID,")
				.append("R_CRE_TIME,")
				.append("REG_NO,")
				.append("MEDIUM,")
				.append("TRANSFER_DATE")

				.append(" ) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,now(),?,now(),?,?,?)");

		 try{

		int[] a = getJdbcTemplate().batchUpdate(sql.toString(),
				new BatchPreparedStatementSetter() {

					@Override
					public void setValues(PreparedStatement ps, int i)
							throws SQLException {

						StudentMaster studentMaster = studentList.get(i);

						ps.setInt(1, studentMaster.getDbTs());
						ps.setString(2, studentMaster.getInstId());
						ps.setString(3, studentMaster.getBranchId());					
						ps.setString(4, studentMaster.getAcademicYear());
						ps.setString(5, studentMaster.getStudentAdmisNo());
						ps.setInt(6,studentMaster.getRollno());
						ps.setString(7, studentMaster.getCourse());
						ps.setString(8, studentMaster.getStandard());
						ps.setString(9, studentMaster.getSec());
						ps.setString(10, studentMaster.getStuGrpId());
						ps.setString(11,studentMaster.getCourseVariantCat());
						ps.setString(12,studentMaster.getCourseVariant());
						ps.setString(13,studentMaster.getStudentType());
						ps.setString(14, studentMaster.getCombination());
						ps.setString(15, studentMaster.getStudentName());					
						ps.setString(16, studentMaster.getHouseName());
						ps.setString(17, studentMaster.getSecoundLang());
						ps.setString(18, studentMaster.getThirdLang());
						ps.setString(19, studentMaster.getElective1());
						ps.setString(20, studentMaster.getElective2());
						ps.setString(21,studentMaster.getStudentBatch());
						ps.setString(22,studentMaster.getLabBatch());
						ps.setString(23,studentMaster.getReasonForLeaving());
						ps.setString(24, studentMaster.getReligiousStudies());
						ps.setString(25, studentMaster.getTransfered());
						ps.setString(26, studentMaster.getAccountNo());
						ps.setString(27,"N");
						ps.setString(28, studentMaster.getrModId());

						ps.setString(29, studentMaster.getrCreId());

						ps.setString(30, studentMaster.getRegNo());
						ps.setString(31, studentMaster.getMedium());
						if((studentMaster.getTransferDate()!=null)&&(studentMaster.getTransferDate().equals(""))){
							ps.setString(32, null);
						}else{
							ps.setString(32, studentMaster.getTransferDate());
						}
						
					}

					@Override
					public int getBatchSize() {

						return studentList.size();
					}
				}

		);
		
		 }catch(RuntimeException e){
			 e.printStackTrace();
			 logger.info("exception :"+e);
				throw new RuntimeExceptionForBatch();
			}		
		
	}

	@Override
	public List<StudentMaster> retriveStudentMasterList() throws NoDataFoundException {
		logger.debug("retrive StudentMaster List");
		StringBuffer sql = new StringBuffer()
				.append("select ")
				.append("DB_TS,")
				.append("INST_ID,")
				.append("BRANCH_ID,")				
				.append("ACADEMIC_YEAR,").append("STUDENT_ADMIS_NO,")
				.append("ROLL_NUMBER,").append("COURSE,").append("STANDARD, ")
				.append("SEC, ").append("COURSE_VARIANT_CAT, ")
				.append("COURSE_VARIANT, ").append("STUDENT_TYPE, ")
				.append("COMBINATION,").append("STUDENT_NAME,")
				.append("HOUSE_NAME,")
				.append("SECOND_LANG, ").append("THIRD_LANG, ")
				.append("ELECTIVE_1, ").append("ELECTIVE_2, ")
				.append("STUDENT_BATCH, ").append("LAB_BATCH, ")
				.append("REASON_FOR_LEAVING, ").append("RELIGIOUS_SUB, ")
				.append("TRANSFERRED, ").append("ACCOUNT_NO, ")
				.append("DEL_FLG, ").append("R_MOD_ID, ")
				.append("R_MOD_TIME, ").append("R_CRE_ID, ")
				.append("R_CRE_TIME, ").append("REG_NO, ").append("MEDIUM, ")
				.append("TRANSFER_DATE")				
				.append(" from studentmaster ").append("where")
				.append(" DEL_FLG =?")
				.append(" ORDER BY STUDENT_ADMIS_NO");
		logger.debug("select query :" + sql.toString());
		List<StudentMaster> studentMasterList = getJdbcTemplate().query(
				sql.toString(), new PreparedStatementSetter() {

					@Override
					public void setValues(PreparedStatement pss)
							throws SQLException {
						pss.setString(1, "N");

					}

				}, new StudentMasterRowMapper());
		
		if(studentMasterList.size()==0){
			throw new NoDataFoundException();			
		}

		return studentMasterList;
	}

	@Override
	public List<Map<String,String>> getStuListForColumnUpdates(final String instid,
			final String branchId,final String acYear,final String stuGrp,final String colName) throws NoDataFoundException {

		logger.debug("retrive StudentMaster List");
		StringBuffer sql = new StringBuffer()
				.append("select DB_TS,")
				.append("STUDENT_ADMIS_NO,")
				.append("STUDENT_NAME,")				
				.append(colName)				
				.append(" from stum ")
				.append("where")
				.append(" INST_ID =?")
				.append(" AND BRANCH_ID =?")
				.append(" AND ACADEMIC_YEAR =?")
				.append(" AND STUDENTGRP_ID =?")				
				.append(" AND DEL_FLG =?")
				.append(" ORDER BY ROLL_NUMBER,STUDENT_NAME,STUDENT_ADMIS_NO");
		logger.debug("select query :" + sql.toString());
		List<Map<String,String>> studentMasterList = getJdbcTemplate().query(
				sql.toString(), new PreparedStatementSetter() {

					@Override
					public void setValues(PreparedStatement pss)
							throws SQLException {
						pss.setString(1, instid);
						pss.setString(2, branchId);
						pss.setString(3, acYear);
						pss.setString(4, stuGrp);
						pss.setString(5, "N");

					}

				}, new RowMapper<Map<String,String>>(){

					@Override
					public Map<String,String> mapRow(ResultSet rs, int arg1)
							throws SQLException {

						Map<String,String> studentMasterMap = new LinkedHashMap<String,String>();
						studentMasterMap.put("DB_TS",rs.getString("DB_TS"));
						studentMasterMap.put("ADMIS_NO", rs.getString("STUDENT_ADMIS_NO"));
						studentMasterMap.put("STU_NAME", rs.getString("STUDENT_NAME"));						
						studentMasterMap.put(colName, rs.getString(colName));						
						return studentMasterMap;
					}
					
				});
		
		if(studentMasterList.size()==0){
			throw new NoDataFoundException();			
		}		
		return studentMasterList;
	
	}

	@Override
	public void updateStuList(
			final List<StudentUpdateList> studentUpdateLists,String colName)
			throws  BatchUpdateFailedException {			
		logger.debug("Parameters :"+"ColName :"+colName+",List :"+studentUpdateLists);
		StringBuffer sql = new StringBuffer("update stum set ")
		.append("DB_TS=?, ").append("R_MOD_ID=?, ")
		.append("R_MOD_TIME=now(), ").append(colName+"=? ")
		.append("where STUDENT_ADMIS_NO=? and ")
		.append("INST_ID=? and ").append("BRANCH_ID=? and")
		.append(" DB_TS=? and ").append("DEL_FLG=?");
logger.debug("sql query" + sql.toString());
int[] ret = null;

ret = 	getJdbcTemplate().batchUpdate(sql.toString(),
		new BatchPreparedStatementSetter() {

			@Override
			public void setValues(PreparedStatement ps, int i)
					throws SQLException {
				StudentUpdateList stduentMaster = studentUpdateLists.get(i);
				ps.setInt(1, (stduentMaster.getDbTs()+1));
				ps.setString(2, stduentMaster.getrModId());						
				ps.setString(3, stduentMaster.getColValue());
				ps.setString(4, stduentMaster.getStudentAdmisNo());
				ps.setString(5, stduentMaster.getInstId());
				ps.setString(6, stduentMaster.getBranchId());
				ps.setInt(7,stduentMaster.getDbTs());
				ps.setString(8, "N");

			}

			@Override
			public int getBatchSize() {
				return studentUpdateLists.size();
			}
		});
for (int sa : ret) {
	if (sa == 0) {
		throw new BatchUpdateFailedException();
	}
}

}

	@Override
	public List<StudentUpdateList> selectBefUpdateForStuUpdates(
			List<StudentUpdateList> studentUpdateList,final String colName) throws NoDataFoundException {

		logger.debug("retrive StudentMaster List");
		List<String > data = new ArrayList<String>();
		StringBuffer sql = new StringBuffer()
				.append("select DB_TS,")
				.append("INST_ID,")
				.append("BRANCH_ID,")
				.append("STUDENT_ADMIS_NO,")
				.append("STUDENT_NAME,")				
				.append(colName)				
				.append(" from stum ")
				.append("where")
				.append(" INST_ID =?")
				.append(" AND BRANCH_ID =?")						
				.append(" AND DEL_FLG =?")
				.append(" AND (STUDENT_ADMIS_NO,DB_TS) IN (");
		data.add(studentUpdateList.get(0).getInstId());
		data.add(studentUpdateList.get(0).getBranchId());
		data.add("N");		
		
		for(int index = 0;index<studentUpdateList.size();index++){
			if(index<studentUpdateList.size()-1){
				sql.append("(?,?)");
				
			}else{
				sql.append("(?,?),");
			}
			data.add(studentUpdateList.get(index).getStudentAdmisNo());	
			data.add(studentUpdateList.get(index).getDbTs().toString());
		}						
		sql.append(") ORDER BY STUDENT_ADMIS_NO");
		Object[] array = data.toArray(new Object[data.size()]);
		logger.debug("select query :" + sql.toString());
		List<StudentUpdateList> studentMasterList = getJdbcTemplate().query(
				sql.toString(), array, new RowMapper<StudentUpdateList>(){

					@Override
					public StudentUpdateList mapRow(ResultSet rs, int arg1)
							throws SQLException {

						StudentUpdateList studentMasterMap = new StudentUpdateList();
						studentMasterMap.setDbTs(rs.getInt("DB_TS"));
						studentMasterMap.setInstId(rs.getString("INST_ID"));
						studentMasterMap.setBranchId(rs.getString("BRANCH_ID"));
						studentMasterMap.setStudentAdmisNo(rs.getString("STUDENT_ADMIS_NO"));
						studentMasterMap.setStudentName(rs.getString("STUDENT_NAME"));									
						return studentMasterMap;
					}
					
				});
		
		if(studentMasterList.size()==0){
			throw new NoDataFoundException();			
		}	
		return studentMasterList;
	
	}
	
	
		@Override
	public Map<String, String> getStuListForDetain(final String stuGrpId,
			final String acyYear,final String instId,final String branchId) throws NoDataFoundException {

		logger.debug("retrive StudentMaster List");
		List<String > data = new ArrayList<String>();
		StringBuffer sql = new StringBuffer()
				.append("SELECT ")
				.append("STUDENT_NAME,STUDENT_ADMIS_NO ")
				.append("FROM stum ")
				.append("WHERE STUDENTGRP_ID=? ")
				.append("AND ACADEMIC_YEAR=? ")		
				.append("AND INST_ID=? ")		
				.append("AND BRANCH_ID=? ")		
				.append("AND DEL_FLG='N'");									
		sql.append(" ORDER BY STUDENT_ADMIS_NO");
		Object[] array = data.toArray(new Object[data.size()]);
		logger.debug("select query :" + sql.toString());
		Map<String,String> studentMasterList = getJdbcTemplate().query(
				sql.toString(), new PreparedStatementSetter() {

					@Override
					public void setValues(PreparedStatement pss)
							throws SQLException {
						pss.setString(1, stuGrpId);
						pss.setString(2, acyYear);
						pss.setString(3, instId);
						pss.setString(4, branchId);

					}

				}, new ResultSetExtractor<Map<String,String>>(){

					
					@Override
					public Map<String, String> extractData(ResultSet rs)
							throws SQLException, DataAccessException {
						Map<String, String> map = new LinkedHashMap<String, String>();
							while (rs.next()) {
								String key = (rs.getString("STUDENT_ADMIS_NO"));
								String value = (rs.getString("STUDENT_NAME"));
								map.put(key, value);
							}
					return map;
					}
				});
		
		if(studentMasterList.size()==0){
			throw new NoDataFoundException();			
		}		
		return studentMasterList;
	
	}

@Override
	public List<StudentMaster> selectStudentGroupList(StudentMasterKey studentMasterKey)
			throws NoDataFoundException {
		logger.debug("retrive Student group list");
		logger.info("stum key values :"+studentMasterKey.toString());
		List<Object> data = new ArrayList<Object>();
		StringBuffer sql=new StringBuffer()	
		
		.append("select ")
		.append("distinct(STUDENTGRP_ID) ")
				.append(" from stum ")
				.append("where")					
				.append(" INST_ID=?")
				.append(" and BRANCH_ID=?")
				.append(" and ACADEMIC_YEAR=?")			
				.append(" and DEL_FLG=? ")
				.append(" and STUDENTGRP_ID is not null ")
				.append(" and STUDENTGRP_ID!='' order by studentgrp_id ");
		
		data.add(studentMasterKey.getInstId().trim());
		data.add(studentMasterKey.getBranchId().trim());
		data.add(studentMasterKey.getAcademicYear().trim());	
		data.add("N");		
		 Object[] array = data.toArray(new Object[data.size()]);

		
		logger.debug("select query :"+sql.toString());
		List<StudentMaster> selectedStudentMaster =null;
		 selectedStudentMaster = getJdbcTemplate()
				.query(sql.toString(), array, new StudentGroupResultSetExtractor());
		if(selectedStudentMaster == null ){
			throw new NoDataFoundException();
		}
		System.out.println("selected student master list :"+selectedStudentMaster.size());
		return selectedStudentMaster;
}

	
}


class StudentGroupResultSetExtractor implements RowMapper<StudentMaster> {

	@Override
	public StudentMaster mapRow(ResultSet rs, int arg1) throws SQLException {
		StudentMaster studentMaster = new StudentMaster();	
		studentMaster.setStuGrpId(rs.getString("STUDENTGRP_ID"));

	return studentMaster;
	}
	}
	class StudentMasterRecResultSetExtractor implements ResultSetExtractor<StudentMaster>{
		@Override
		public StudentMaster extractData(ResultSet rs) throws SQLException  {
			StudentMaster studentMaster= null;
			if(rs.next()){
				studentMaster=new StudentMaster();
			studentMaster.setDbTs(rs.getInt("DB_TS"));
			studentMaster.setInstId(rs.getString("INST_ID"));
			studentMaster.setBranchId(rs.getString("BRANCH_ID"));
			studentMaster.setAcademicYear(rs.getString("ACADEMIC_YEAR"));
			studentMaster.setStudentAdmisNo(rs.getString("STUDENT_ADMIS_NO"));
			studentMaster.setCourse(rs.getString("COURSE"));
			studentMaster.setStandard(rs.getString("STANDARD"));
			studentMaster.setSec(rs.getString("SEC"));
			studentMaster.setCourseVariantCat(rs.getString("COURSE_VARIANT_CAT"));
			studentMaster.setCourseVariant(rs.getString("COURSE_VARIANT"));
			studentMaster.setStudentType(rs.getString("STUDENT_TYPE"));
			studentMaster.setCombination(rs.getString("COMBINATION"));
			studentMaster.setStudentName(rs.getString("STUDENT_NAME"));			
			studentMaster.setHouseName(rs.getString("HOUSE_NAME"));
			studentMaster.setSecoundLang(rs.getString("SECOND_LANG"));
			studentMaster.setThirdLang(rs.getString("THIRD_LANG"));
			studentMaster.setElective1(rs.getString("ELECTIVE_1"));
			studentMaster.setElective2(rs.getString("ELECTIVE_2"));
			studentMaster.setStudentBatch(rs.getString("STUDENT_BATCH"));
			studentMaster.setLabBatch(rs.getString("LAB_BATCH"));
			studentMaster.setReasonForLeaving(rs.getString("REASON_FOR_LEAVING"));
			studentMaster.setReligiousStudies(rs.getString("RELIGIOUS_SUB"));
			studentMaster.setTransfered(rs.getString("TRANSFERRED"));
			studentMaster.setAccountNo(rs.getString("ACCOUNT_NO"));
			studentMaster.setDelFlg(rs.getString("DEL_FLG"));
			studentMaster.setrModId(rs.getString("R_MOD_ID"));
			studentMaster.setrModTime(rs.getString("R_MOD_TIME"));
			studentMaster.setrCreId(rs.getString("R_CRE_ID"));
			studentMaster.setrCreTime(rs.getString("R_CRE_TIME"));
			studentMaster.setRegNo(rs.getString("REG_NO"));
			studentMaster.setMedium(rs.getString("MEDIUM"));
			studentMaster.setTransferDate(rs.getString("TRANSFER_DATE"));
			}
			
			return studentMaster;
		}
		}
	

	class StudentMasterRowMapper implements RowMapper<StudentMaster> {

		@Override
		public StudentMaster mapRow(ResultSet rs, int arg1) throws SQLException {
			StudentMaster studentMaster = new StudentMaster();
			
			studentMaster.setDbTs(rs.getInt("DB_TS"));
			studentMaster.setInstId(rs.getString("INST_ID"));
			studentMaster.setBranchId(rs.getString("BRANCH_ID"));
			//studentMaster.setUserId(rs.getString("PARENT_USER_ID"));
			studentMaster.setAcademicYear(rs.getString("ACADEMIC_YEAR"));
			studentMaster.setStudentAdmisNo(rs.getString("STUDENT_ADMIS_NO"));
			studentMaster.setRollno(rs.getInt("ROLL_NUMBER"));
			studentMaster.setCourse(rs.getString("COURSE"));
			studentMaster.setStandard(rs.getString("STANDARD"));
			studentMaster.setSec(rs.getString("SEC"));
			studentMaster.setCourseVariantCat(rs.getString("COURSE_VARIANT_CAT"));
			studentMaster.setCourseVariant(rs.getString("COURSE_VARIANT"));
			studentMaster.setStudentType(rs.getString("STUDENT_TYPE"));
			studentMaster.setCombination(rs.getString("COMBINATION"));
			studentMaster.setStudentName(rs.getString("STUDENT_NAME"));			
			studentMaster.setHouseName(rs.getString("HOUSE_NAME"));
			studentMaster.setSecoundLang(rs.getString("SECOND_LANG"));
			studentMaster.setThirdLang(rs.getString("THIRD_LANG"));
			studentMaster.setElective1(rs.getString("ELECTIVE_1"));
			studentMaster.setElective2(rs.getString("ELECTIVE_2"));
			studentMaster.setStudentBatch(rs.getString("STUDENT_BATCH"));
			studentMaster.setLabBatch(rs.getString("LAB_BATCH"));
			studentMaster.setReasonForLeaving(rs.getString("REASON_FOR_LEAVING"));
			studentMaster.setReligiousStudies(rs.getString("RELIGIOUS_SUB"));
			studentMaster.setTransfered(rs.getString("TRANSFERRED"));
			studentMaster.setAccountNo(rs.getString("ACCOUNT_NO"));
			studentMaster.setDelFlg(rs.getString("DEL_FLG"));
			studentMaster.setrModId(rs.getString("R_MOD_ID"));
			studentMaster.setrModTime(rs.getString("R_MOD_TIME"));
			studentMaster.setrCreId(rs.getString("R_CRE_ID"));
			studentMaster.setrCreTime(rs.getString("R_CRE_TIME"));
			studentMaster.setRegNo((rs.getString("REG_NO")));
			studentMaster.setMedium((rs.getString("MEDIUM")));
			studentMaster.setTransferDate((rs.getString("TRANSFER_DATE")));

			return studentMaster;
		}

	}

	class RetriveStudentMaster implements RowMapper<StudentMaster> {

		@Override
		public StudentMaster mapRow(ResultSet rs, int arg1) throws SQLException {

			StudentMaster studentMaster = new StudentMaster();

			studentMaster.setDbTs(rs.getInt("DB_TS"));
			studentMaster.setStudentAdmisNo(rs.getString("STUDENT_ADMIS_NO"));
			studentMaster.setStudentName(rs.getString("STUDENT_NAME"));			
			studentMaster.setRollno(rs.getInt("ROLL_NUMBER"));		
			studentMaster.setRegNo(rs.getString("REG_NO"));
			return studentMaster;
		}

	}

	
	class RetriveStudentMasterForUpdates implements RowMapper<StudentMaster> {

		@Override
		public StudentMaster mapRow(ResultSet rs, int arg1) throws SQLException {

			StudentMaster studentMaster = new StudentMaster();
		
			studentMaster.setStudentAdmisNo(rs.getString("STUDENT_ADMIS_NO"));
			studentMaster.setStudentName(rs.getString("STUDENT_NAME"));	
			studentMaster.setRollno(rs.getInt("ROLL_NUMBER"));		
			studentMaster.setRegNo(rs.getString("REG_NO"));
			return studentMaster;
		}

	}

