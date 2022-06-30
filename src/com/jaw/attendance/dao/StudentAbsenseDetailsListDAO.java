package com.jaw.attendance.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.stereotype.Repository;

import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.framework.dao.BaseDao;
@Repository
public class StudentAbsenseDetailsListDAO extends BaseDao implements IStudentAbsenseDetailsListDAO{
	// Logging
	Logger logger = Logger.getLogger(StudentAbsenseDetailsListDAO.class);

	@Override
	public void insertAttendanceAbsentRecs(
			final List<StudentAbsenseDetails> studentAbsenseDetailsList)
			throws DuplicateEntryException {
		logger.debug("Inside Holiday List Insert Record");
		logger.debug("Student absent List Size   "+studentAbsenseDetailsList.size());
		StringBuffer query = new StringBuffer();	
		
		query = query.append("insert into stad ( ")
				.append("DB_TS,")
				.append("INST_ID,")
				.append("BRANCH_ID,")	
				.append("STAM_ID,")
				.append("STUDENT_ADMIS_NO,")
				.append("IS_PRESENT,")
				.append("ABSENTEE_RMKS,")
				.append("DEL_FLG,")
				.append("R_MOD_ID,")
				.append("R_MOD_TIME,")
				.append("R_CRE_ID,")
				.append("R_CRE_TIME)")
				.append(" values (?,?,?,?,?,?,?,?,?,now(),?,now())");
		try {
		int[] a = getJdbcTemplate().batchUpdate(query.toString(),
				new BatchPreparedStatementSetter() {

					@Override
					public void setValues(PreparedStatement pss, int i)
							throws SQLException {

						StudentAbsenseDetails studentAbsenseDetails = studentAbsenseDetailsList.get(i);

						pss.setInt(1, 1);
						pss.setString(2, studentAbsenseDetails.getInstId());
						pss.setString(3, studentAbsenseDetails.getBranchId());
						pss.setString(4, studentAbsenseDetails.getStamId());
						pss.setString(5, studentAbsenseDetails.getStudentAdmisNo().trim());
						pss.setString(6, studentAbsenseDetails.getIsPresent().trim());
						pss.setString(7, studentAbsenseDetails.getAbsenteeRmks().trim());
						pss.setString(8, "N");
						pss.setString(9, studentAbsenseDetails.getrModId().trim());
						pss.setString(10, studentAbsenseDetails.getrCreId().trim());
					}

					@Override
					public int getBatchSize() {
						return studentAbsenseDetailsList.size();
					}
				}

		);
		} catch (org.springframework.dao.DuplicateKeyException e) {
			logger.error("Duplicate Entry Exception Occured  "+e.getMessage());
			throw new DuplicateEntryException();
		}
		
	}


}
