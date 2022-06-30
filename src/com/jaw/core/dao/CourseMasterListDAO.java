package com.jaw.core.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.framework.dao.BaseDao;

@Repository
public class CourseMasterListDAO  extends BaseDao implements
ICourseMasterListDAO {
Logger logger = Logger.getLogger(CourseMasterListDAO.class);

@Override
public List<CourseMaster> getCourseMasterList(final CourseMasterKey courseMasterKey) throws NoDataFoundException {
	logger.debug("DAO :Inside CourseMaster List select method");
	StringBuffer sql = new StringBuffer();
    sql.append("select ")       
		.append("COURSEMASTER_ID,")
		.append("COURSE_NAME,")
		.append("COURSE_ID,")
		.append("COMB_BRNCH_ID,")
		.append("COURSE_GRP,")				
		.append("CV_APPCBLE,")
		.append("CV_CAT_TYPE,")
		.append("CV_LIST_TYPE,")
		.append("AFF_ID,")
		.append("AFF_DETAILS,")
		.append("TERM_APPLCBLE,")
		.append("DEPT_ID,")
		.append("MEDIUM ")
		.append(" from crsm ")
		.append(" where")
		.append("  INST_ID= ?")
		.append(" and  BRANCH_ID= ?")
		.append(" and  DEL_FLG=?");	

	List<CourseMaster> selectedListCrseMtr = getJdbcTemplate()
			.query(sql.toString(),	new PreparedStatementSetter() {

				@Override
				public void setValues(PreparedStatement ps)
						throws SQLException {	
					ps.setString(1, courseMasterKey.getInstId());
					ps.setString(2, courseMasterKey.getBranchId());
					ps.setString(3, "N");
				}

			} 
					, new CourseMasterSelectRowMapper());
	if (selectedListCrseMtr.size() == 0) {
		logger.error("No  record found");
		throw new NoDataFoundException();
	}
	logger.debug("DAO : Course Master List value"+selectedListCrseMtr.size() );
	return selectedListCrseMtr;
	}

	}

	class CourseMasterSelectRowMapper implements RowMapper<CourseMaster> {

	@Override
	public CourseMaster mapRow(ResultSet rs, int arg1) throws SQLException {
		CourseMaster courseMaster = new CourseMaster();
		courseMaster.setCourseMasterId(rs.getString("COURSEMASTER_ID"));
		courseMaster.setCourseName(rs.getString("COURSE_NAME"));
		courseMaster.setCourseId(rs.getString("COURSE_ID"));
		courseMaster.setCombBranchId(rs.getString("COMB_BRNCH_ID"));
		courseMaster.setCourseGrp(rs.getString("COURSE_GRP"));							
		courseMaster.setCvAppcble(rs.getString("CV_APPCBLE"));
		courseMaster.setCvCatType(rs.getString("CV_CAT_TYPE"));
		courseMaster.setCvListType(rs.getString("CV_LIST_TYPE"));
		courseMaster.setAffId(rs.getString("AFF_ID"));
		courseMaster.setAffDetails(rs.getString("AFF_DETAILS"));
		courseMaster.setTermApplcble(rs.getString("TERM_APPLCBLE"));	
		courseMaster.setDepartment(rs.getString("DEPT_ID"));	
		
		courseMaster.setMedium(rs.getString("MEDIUM"));
	return courseMaster;
	}
	}




