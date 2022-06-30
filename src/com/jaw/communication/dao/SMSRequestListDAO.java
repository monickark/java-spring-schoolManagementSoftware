package com.jaw.communication.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.framework.dao.BaseDao;
@Repository
public class SMSRequestListDAO extends BaseDao implements
ISMSRequestListDAO {
Logger logger = Logger.getLogger(SMSRequestListDAO.class);

@Override
public List<SMSRequest> getSMSRequestList(SMSRequestListKey smsRequestListKey)
		throws NoDataFoundException {
	logger.debug("DAO :Inside SMSRequest List select method");
	
	List<Object> data = new ArrayList<Object>();
	StringBuffer sql = new StringBuffer();
	    sql.append("select ")
			.append("BRANCH_ID,")
			.append("AC_TERM,")
			.append("SMS_RQST_ID,")
			.append("SMS_MSG,")
			.append("RQST_CATEGORY,")
			.append("GEN_GRP_LIST,")
			.append("SPECIFIC_GRP_LIST,")
			.append("SPECIFIC_MEMBER_LIST,")
			.append("RQST_STATUS,")
			.append("RQST_TYPE")			
			.append(" from smsr ")
			.append(" where")
			.append("  INST_ID= ?")
		.append(" and  BRANCH_ID= ?")
		.append(" and  DEL_FLG=?");
		data.add(smsRequestListKey.getInstId());
		data.add(smsRequestListKey.getBranchId());
		data.add("N");
		
		if ((smsRequestListKey.getAcTerm() !=null)&&(!smsRequestListKey.getAcTerm().equals(""))
				) {
			sql.append(" and AC_TERM=?  ");
			logger.debug("AC Term Value :" + smsRequestListKey.getAcTerm());
			data.add(smsRequestListKey.getAcTerm());
		}
		if ((smsRequestListKey.getRequestType()!=null)&&(!smsRequestListKey.getRequestType().equals(""))
				) {
			sql.append(" and RQST_TYPE=?  ");
			logger.debug("Request type Value :" + smsRequestListKey.getRequestType());
			data.add(smsRequestListKey.getRequestType());
		}
		if ((smsRequestListKey.getFromDate() !=null)&&(!smsRequestListKey.getFromDate().equals("")
				&&(smsRequestListKey.getToDate()!=null)&&(!smsRequestListKey.getToDate().equals(""))))
				{			
			sql.append(" and R_CRE_TIME>=? and R_CRE_TIME<=?");
			logger.debug("From Date :" + smsRequestListKey.getFromDate()+"   To Date :"+smsRequestListKey.getToDate());
			data.add(smsRequestListKey.getFromDate());
			data.add(smsRequestListKey.getToDate());
		}
	Object[] array = data.toArray(new Object[data.size()]);
	logger.debug("Where clause conditions size :" + array.length);

	List<SMSRequest> selectedListSMSReq = getJdbcTemplate()
			.query(sql.toString(), array, new selectedListSMSReqSelectRowMapper());
	if (selectedListSMSReq.size() == 0) {
		logger.error("No  record found");
		throw new NoDataFoundException();
	}
	logger.debug("DAO : SMS Request List value"+selectedListSMSReq.size() );
	return selectedListSMSReq;
	}

}
class selectedListSMSReqSelectRowMapper implements RowMapper<SMSRequest> {

	@Override
	public SMSRequest mapRow(ResultSet rs, int arg1) throws SQLException {
		SMSRequest smsRequest = new SMSRequest();	
		
	smsRequest.setBranchId(rs.getString("BRANCH_ID"));
	smsRequest.setAcTerm(rs.getString("AC_TERM"));
	smsRequest.setSmsReqstId(rs.getString("SMS_RQST_ID"));
	smsRequest.setSmsMessage(rs.getString("SMS_MSG"));
	smsRequest.setReqstCategory(rs.getString("RQST_CATEGORY"));
	smsRequest.setGeneralGrpList(rs.getString("GEN_GRP_LIST"));
	smsRequest.setSpecificGrpList(rs.getString("SPECIFIC_GRP_LIST"));
	smsRequest.setSpecificMembrList(rs.getString("SPECIFIC_MEMBER_LIST"));
	smsRequest.setReqstStatus(rs.getString("RQST_STATUS"));
	smsRequest.setReqstType(rs.getString("RQST_TYPE"));

	return smsRequest;
	}
	}