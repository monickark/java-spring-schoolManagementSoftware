package com.jaw.core.dao;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.core.controller.CourseTermLinkingVO;
import com.jaw.framework.dao.BaseDao;
@Repository
public class CourseTermLinkingListDAO extends BaseDao implements
ICourseTermLinkingListDAO {
Logger logger = Logger.getLogger(CourseTermLinkingListDAO.class);

@Override
public List<CourseTermLinking> getCourseTermLinkingList(
		CourseTermLinkingKey courseTermLinkingKey) throws NoDataFoundException {
	logger.debug("DAO :Inside CourseMaster List select method");
	List<Object> data = new ArrayList<Object>();
	StringBuffer sql = new StringBuffer();
	List<CourseTermLinking> selectedListCrseTrmLinking=null;
    sql.append("select ")       
		.append("DB_TS,")
		.append("COURSEMASTER_ID,")
		.append("TERM_ID,")
		.append("TRM_SRL_ORDER ")
		.append(" from crtl ")
		.append(" where")
		.append("  INST_ID= ?")
		.append(" and  BRANCH_ID= ?")
		.append(" and  DEL_FLG=?");
	
    data.add(courseTermLinkingKey.getInstId());
	data.add(courseTermLinkingKey.getBranchId());
	data.add("N");
	if ((courseTermLinkingKey.getCourseMasterId() != null)
			&& (courseTermLinkingKey.getCourseMasterId() != "")) {
		sql.append(" and COURSEMASTER_ID=?  ");
		
		data.add(courseTermLinkingKey.getCourseMasterId());
	}
	sql.append(" order by TRM_SRL_ORDER");
	Object[] array = data.toArray(new Object[data.size()]);

	 selectedListCrseTrmLinking = getJdbcTemplate()
			.query(sql.toString(), array,
					new CourseTermLinkingSelectRowMapper());
	
	if (selectedListCrseTrmLinking.size() == 0) {
		
		/*if(add){
			System.out.println("else get controller size 0");
			CourseTermLinking courseLinkListVo=new CourseTermLinking();
			courseLinkListVo.setCourseMasterId("EmptyAdd");
			selectedListCrseTrmLinking.add(courseLinkListVo);
		}else{
			logger.error("No  record found");*/
			throw new NoDataFoundException();
		//}
		
	}
		
	
	
	logger.debug("DAO : Course Term Linking List value"
			+ selectedListCrseTrmLinking.size());
	
	return selectedListCrseTrmLinking;
	}
@Override
public Map<String,String> getTermListFromCourse(String instid, String branchId,
		String courseId) {
	logger.debug("crtl DAO, Inst id :"+instid+", Branch Id :"+branchId+", Course Id :"+courseId);
	Map<String,String> listOfTrmId = new LinkedHashMap<String,String>();
	List<Object> data = new ArrayList<Object>();
	StringBuffer sql = new StringBuffer();
	//SELECT TERM_ID,CODE_DESC FROM crtl,cocd WHERE CM_CODE = TERM_ID AND crtl.INST_ID=cocd.INST_ID AND crtl.BRANCH_ID=cocd.BRANCH_ID AND crtl.DEL_FLG=cocd.DEL_FLG AND  crtl.INST_ID='MYS' AND crtl.BRANCH_ID='BR001' AND COURSEMASTER_ID='CM005' AND crtl.DEL_FLG='N' ORDER BY TERM_ID ASC;
	
    sql.append("SELECT")       
		.append(" TERM_ID,")
		.append(" CODE_DESC")
		.append(" FROM crtl,cocd")
		.append(" WHERE")		
		.append("  CM_CODE = TERM_ID AND")
		.append("  crtl.INST_ID=cocd.INST_ID AND")
		.append("  crtl.BRANCH_ID=cocd.BRANCH_ID AND")
		.append("  crtl.DEL_FLG=cocd.DEL_FLG AND")		
		.append("  crtl.INST_ID= ?")
		.append(" AND  crtl.BRANCH_ID= ?")
		.append(" AND  COURSEMASTER_ID= ?")
		.append(" AND  crtl.DEL_FLG=?")
		.append(" ORDER BY TERM_ID ASC");
    data.add(instid);
	data.add(branchId);
	data.add(courseId);
	data.add("N");	
	
	Object[] array = data.toArray(new Object[data.size()]);

	listOfTrmId = getJdbcTemplate()
			.query(sql.toString(), array,
					/*new RowMapper<Map<String,String>>(){

						@Override
						public Map<String,String> mapRow(ResultSet rs, int arg1)
								throws SQLException {
							Map<String,String> map = new LinkedHashMap<String,String>();
							 map.put(rs.getString("TERM_ID"), rs.getString("CODE_DESC"));
							 return map;
						}*/ new ResultSetExtractor<Map<String,String>>(){

							@Override
							public Map<String, String> extractData(
									ResultSet rs) throws SQLException,
									DataAccessException {
								Map<String, String> map = new LinkedHashMap<String, String>();
									while (rs.next()) {
										String key = (rs.getString("TERM_ID"));
										String value = (rs.getString("CODE_DESC"));

										map.put(key, value);
									}
									return map;
									}
				
				
			});	
		
	
	
	logger.debug("crtl DAO : Term Id List value"
			+ listOfTrmId);
	
	return listOfTrmId;
	}

}
class CourseTermLinkingSelectRowMapper implements
RowMapper<CourseTermLinking> {

@Override
public CourseTermLinking mapRow(ResultSet rs, int arg1)
	throws SQLException {
	CourseTermLinking courseTrmLinking = new CourseTermLinking();
courseTrmLinking.setDbTs(rs.getInt("DB_TS"));
courseTrmLinking.setCourseMasterId(rs.getString("COURSEMASTER_ID"));
courseTrmLinking.setTermId(rs.getString("TERM_ID"));
courseTrmLinking.setTermSerialOrder(rs.getInt("TRM_SRL_ORDER"));
return courseTrmLinking;
}
}

