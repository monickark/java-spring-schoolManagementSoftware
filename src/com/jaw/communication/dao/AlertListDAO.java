package com.jaw.communication.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.jaw.common.constants.ApplicationConstant;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.framework.dao.BaseDao;
@Repository
public class AlertListDAO extends BaseDao implements
IAlertListDAO {
Logger logger = Logger.getLogger(AlertListDAO.class);

@Override
public List<Alert> getAlertList(AlertListKey alertListKey)
		throws NoDataFoundException {
	logger.debug("DAO :Inside Alert List select method");
	logger.debug("DAO :Inside Alert List key values :"+alertListKey.toString());
	List<Object> data = new ArrayList<Object>();
	StringBuffer sql = new StringBuffer();
	    sql.append("select ")
			 .append("ALERT_SER_NO,")
				.append("AC_TERM,")
				.append("RQST_CATEGORY,")
				.append("GEN_GRP_LIST,")
				.append("SPECIFIC_GRP_LIST,")
				.append("ALERT_MESSAGE,")
				.append("FROM_DATE,")
				.append("TO_DATE,")
				.append("IMPORTANT,")
				.append("ALERT_STOP")
			.append(" from alrt ")
			.append(" where")
			.append("  INST_ID= ?")
		.append(" and  BRANCH_ID= ?")
		.append(" and  DEL_FLG=?")
	    .append(" and  ALERT_STOP='N'");
		data.add(alertListKey.getInstId());
		data.add(alertListKey.getBranchId());
		data.add("N");
		
		if ((alertListKey.getAcTerm() !=null)&&(!alertListKey.getAcTerm().equals(""))
				) {
			sql.append(" and AC_TERM=?  ");
			logger.debug("AC Term Value :" + alertListKey.getAcTerm());
			data.add(alertListKey.getAcTerm());
		}
		if ((alertListKey.getImportant() !=null)&&(!alertListKey.getImportant().equals(""))
				) {
			sql.append(" and IMPORTANT=?  ");
			logger.debug("IMPORTANT Value :" + alertListKey.getImportant());
			data.add(alertListKey.getImportant());
		}
		
		
		if ((alertListKey.getFromDate() !=null)&&(!alertListKey.getFromDate().equals("")
				&&(alertListKey.getToDate()!=null)&&(!alertListKey.getToDate().equals(""))))
				{			
			sql.append(" and (FROM_DATE>=? or TO_DATE>=?)");
			logger.debug("FROM_DATE :" + alertListKey.getFromDate()+"   TO_DATE :"+alertListKey.getToDate());
			data.add(alertListKey.getFromDate());
			data.add(alertListKey.getToDate());
		}
		if ((alertListKey.getReqstCategory() !=null)&&(!alertListKey.getReqstCategory().equals(""))
				) {
			sql.append(" and RQST_CATEGORY=?  ");
			logger.debug("RQST_CATEGORY Value :" + alertListKey.getReqstCategory());
			data.add(alertListKey.getReqstCategory());
		}
		
		 if ((alertListKey.getGeneralGrpList() !=null)&&(alertListKey.getGeneralGrpList().length!=0)) {
			for(int i=0;i<alertListKey.getGeneralGrpList().length;i++){
				System.out.println("target value sssss"+alertListKey.getGeneralGrpList()[i]);
				if(i==0){
					sql.append(" and  (GEN_GRP_LIST like '%"+alertListKey.getGeneralGrpList()[i]+"%'  ");
				}else{
					sql.append(" or GEN_GRP_LIST like '%"+alertListKey.getGeneralGrpList()[i]+"%'  ");
				}
			}
			sql.append(")");
			logger.debug("Target group Value :" + alertListKey.getGeneralGrpList().length);
			
		}
		 sql.append(" order by FROM_DATE, TO_DATE  ");
	Object[] array = data.toArray(new Object[data.size()]);
	logger.debug("Where clause conditions size :" + array.length);

	List<Alert> selectedListAlert= getJdbcTemplate()
			.query(sql.toString(), array, new AlertSelectRowMapper());
	if (selectedListAlert.size() == 0) {
		logger.error("No  record found");
		throw new NoDataFoundException();
	}
	logger.debug("DAO : Alert List value"+selectedListAlert.size() );
	return selectedListAlert;
	}

@Override
public List<Alert> getAlertListForDashBoard(AlertListKey alertListKey,
		String profileGroup) throws NoDataFoundException {
	logger.debug("DAO :Inside Notice Board List select method");
	System.out.println("profile groupppppppppppppppp"+profileGroup);
	System.out.println("target value sssss"+alertListKey.getGeneralGrpList().length);

	
	List<Object> data = new ArrayList<Object>();
	StringBuffer sql = new StringBuffer();
	    sql.append("select ")
		.append("ALERT_SER_NO,")
		.append("AC_TERM,")
		.append("TARGET_GROUP,")
		.append("ALERT_MESSAGE,")
		.append("FROM_DATE,")
		.append("TO_DATE,")
			.append("IMPORTANT,")
			.append("ALERT_STOP")
			.append(" from alrt ")
			.append(" where")			
			.append("   INST_ID= ?")
		.append(" and  BRANCH_ID= ?")
		.append(" and  DEL_FLG=?")
		 .append(" and  AC_TERM=?")
	    .append(" and  ALERT_STOP='N'");
		  /*.append(" and  NOTICE_TYPE=?");
	    
		 
			if(profileGroup.equals(ApplicationConstant.PG_PARENT)){
				sql .append(" and  INFORM_PARENT='"+ApplicationConstant.INFORM_PARENT+"'");	
			}
	  
	 
	 
	       if ((alertListKey.getGeneralGrpList() !=null)&&(alertListKey.getGeneralGrpList().length!=0)) {
	    	   	for(int i=0;i<alertListKey.getGeneralGrpList().length;i++){
	    	   		System.out.println("target value sssss"+alertListKey.getGeneralGrpList()[i]);
	    	   		if(i==0){
	    	   			sql.append(" and  (GEN_GRP_LIST like '%"+alertListKey.getGeneralGrpList()[i]+"%'  ");
	    	   		}else{
	    	   			sql.append(" or GEN_GRP_LIST like '%"+alertListKey.getGeneralGrpList()[i]+"%'  ");
	    	   		}
	    	   	}
	    	   	sql.append(")");
	    	   	logger.debug("Target group Value :" + alertListKey.getGeneralGrpList().length);

	       }*/
	 
	    sql.append(" order by FROM_DATE, TO_DATE  limit  5");
		data.add(alertListKey.getInstId());
		data.add(alertListKey.getBranchId());
		data.add("N");
		data.add(alertListKey.getAcTerm());
		
		
		System.out.println("queryyyyyyyyyyyyyyyyyyyyyyyy alert"+sql.toString());
		
	Object[] array = data.toArray(new Object[data.size()]);
	logger.debug("Where clause conditions size :" + array.length);

	List<Alert> selectedListAlert = getJdbcTemplate()
			.query(sql.toString(), array, new AlertDetailsRowMapper());
	if (selectedListAlert.size() == 0) {
		logger.error("No  record found");
		throw new NoDataFoundException();
	}
	logger.debug("DAO : AcademicCalendar List value"+selectedListAlert.size() );
	return selectedListAlert;
	}

}
class AlertSelectRowMapper implements RowMapper<Alert> {

	@Override
	public Alert mapRow(ResultSet rs, int arg1) throws SQLException {
		Alert alert = new Alert();	
	alert.setAlertSerialNo(rs.getString("ALERT_SER_NO"));
	alert.setAcTerm(rs.getString("AC_TERM"));
	alert.setReqstCategory(rs.getString("RQST_CATEGORY"));
	alert.setGeneralGrpList(rs.getString("GEN_GRP_LIST"));
	alert.setSpecificGrpList(rs.getString("SPECIFIC_GRP_LIST"));
	alert.setAlertMessage(rs.getString("ALERT_MESSAGE"));
	alert.setFromDate(rs.getString("FROM_DATE"));
	alert.setToDate(rs.getString("TO_DATE"));
	alert.setImportant(rs.getString("IMPORTANT"));
	alert.setAlertStop(rs.getString("ALERT_STOP"));

	return alert;
	}
	}

class AlertDetailsRowMapper implements RowMapper<Alert> {

	@Override
	public Alert mapRow(ResultSet rs, int arg1) throws SQLException {
		Alert alert = new Alert();		
	//alert.setTargetGroup(rs.getString("TARGET_GROUP"));
	alert.setAlertMessage(rs.getString("ALERT_MESSAGE"));
	alert.setFromDate(rs.getString("FROM_DATE"));
	alert.setToDate(rs.getString("TO_DATE"));
	alert.setImportant(rs.getString("IMPORTANT"));
	alert.setAlertStop(rs.getString("ALERT_STOP"));

	return alert;
	}
	}
