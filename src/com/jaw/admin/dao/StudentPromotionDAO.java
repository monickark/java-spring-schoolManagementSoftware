package com.jaw.admin.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.core.dao.CourseTermLinking;
import com.jaw.core.dao.CourseTermLinkingKey;
import com.jaw.framework.dao.BaseDao;
@Repository
public class StudentPromotionDAO extends BaseDao implements IStudentPromotionDAO {
	Logger logger = Logger.getLogger(StudentPromotionDAO.class);

	@Override
	public CourseTermLinking selectCourseTermForPromotion(
			CourseTermLinkingKey courseTermLinkingKey) throws NoDataFoundException {
		logger.debug("Inside select method");
		logger.debug("CourseTermLinkingKey object values :"+ courseTermLinkingKey.toString());
		List<Object> data = new ArrayList<Object>();
		
		
		StringBuffer sql = new StringBuffer();
		    sql.append("select ")
		       .append("COURSEMASTER_ID,")
				.append("TERM_ID ")
				.append(" from crtl ")
				.append(" where")
				.append("   INST_ID= ?")
				.append(" and  BRANCH_ID= ?")
				.append(" and  DEL_FLG=?")
		        .append(" and  COURSEMASTER_ID= ?")
		        .append(" and  TRM_SRL_ORDER= (")		    
		    .append(" (select TRM_SRL_ORDER")
		    .append(" from crtl ")
		    .append(" where INST_ID=? ")
		    .append(" and BRANCH_ID=?")
		    .append(" and COURSEMASTER_ID=?")		    
		    .append(" and TERM_ID=?")
		    .append(" and DEL_FLG=?)+1)");
		    data.add(courseTermLinkingKey.getInstId().trim());
			data.add(courseTermLinkingKey.getBranchId().trim());
			data.add("N");
			data.add(courseTermLinkingKey.getCourseMasterId().trim());	
			data.add(courseTermLinkingKey.getInstId().trim());
			data.add(courseTermLinkingKey.getBranchId().trim());
			data.add(courseTermLinkingKey.getCourseMasterId().trim());	
			data.add(courseTermLinkingKey.getTermId().trim());	
			data.add("N");
			
			
			CourseTermLinking selectedCourseTermLinkingRec = null;
			Object[] array = data.toArray(new Object[data.size()]);
			logger.debug("Where clause conditions size :" + array.length);
			
			selectedCourseTermLinkingRec = (CourseTermLinking) getJdbcTemplate()
				.query(sql.toString(), array, new ResultSetExtractor<CourseTermLinking>() {
					
					@Override
					public CourseTermLinking extractData(ResultSet rs)
							throws SQLException, DataAccessException {						
						CourseTermLinking courseTermLinking = null;
				if(rs.next()) {					
							courseTermLinking = new CourseTermLinking();
							courseTermLinking.setCourseMasterId(rs.getString("COURSEMASTER_ID"));
							courseTermLinking.setTermId(rs.getString("TERM_ID"));				
						}				
						return courseTermLinking;
					}

				});
	
		if (selectedCourseTermLinkingRec == null) {
			throw new NoDataFoundException();
		}


		return selectedCourseTermLinkingRec;
	}

	
	
}
