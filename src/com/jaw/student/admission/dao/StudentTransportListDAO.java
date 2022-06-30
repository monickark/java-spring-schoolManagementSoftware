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
public class StudentTransportListDAO extends BaseDao implements IStudentTransportListDAO {
	Logger logger = Logger.getLogger(StudentTransportListDAO.class);

	int[] ret;



	@Override
	public void insertStudentTransportList(final List<StudentTransportList> studentList)
			throws DataIntegrityException, RuntimeExceptionForBatch {		
		StringBuffer sql = new StringBuffer("insert into trsd(")
		
				.append("DB_TS,")
				.append("INST_ID,")
				.append("BRANCH_ID,")
				
				.append("STUDENT_ADMIS_NO,")
				.append("ACADEMIC_YEAR,")				
				.append("MODE_OF_TRANS,")
				
				.append("VEHICLE_NO,")
				.append("PICKUP_POINT,")
				.append("DROP_POINT, ")
				
				.append("PICKUP_ASST_SALUT, ")
				.append("PICKUP_ASST_NAME, ")
				.append("DEL_FLG, ")
				
				.append("R_MOD_ID, ")
				.append("R_MOD_TIME,")
				.append("R_CRE_ID,")
				.append("R_CRE_TIME")

				.append(" ) values(?,?,?, ?,?,?, ?,?,?, ?,?,?, ?,now(),?,now())");

		 try{

		int[] a = getJdbcTemplate().batchUpdate(sql.toString(),
				new BatchPreparedStatementSetter() {

					@Override
					public void setValues(PreparedStatement ps, int i)
							throws SQLException {

						StudentTransportList StudentTransportList = studentList.get(i);
						
						ps.setInt(1, 1);
						ps.setString(2, StudentTransportList.getInstId());
						ps.setString(3, StudentTransportList.getBranchId());
						ps.setString(4, StudentTransportList.getStudentAdmisNo());						
						ps.setString(5, StudentTransportList.getAcademicYear());
						ps.setString(6,StudentTransportList.getModeOfTrans());
						ps.setString(7, StudentTransportList.getVehicleNumber());
						ps.setString(8, StudentTransportList.getPickupPoint());
						ps.setString(9, StudentTransportList.getDropPoint());
						ps.setString(10, StudentTransportList.getPickupAssisSalut());
						ps.setString(11,StudentTransportList.getPickupAssisName());
						ps.setString(12,"N");
						ps.setString(13, StudentTransportList.getrModId());
						ps.setString(14, StudentTransportList.getrCreId());
		
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

	

	}




