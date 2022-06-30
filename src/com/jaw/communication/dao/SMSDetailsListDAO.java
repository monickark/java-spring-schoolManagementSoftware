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
public class SMSDetailsListDAO extends BaseDao implements
ISMSDetailsListDAO {
Logger logger = Logger.getLogger(SMSDetailsListDAO.class);

@Override
public List<SMSDetails> getSMSDetailsList(SMSDetailsKey smsDetailsKey)
		throws NoDataFoundException {
	logger.debug("DAO :Inside SMS Details List select method");
	
	List<Object> data = new ArrayList<Object>();
	StringBuffer sql = new StringBuffer();
	    sql.append("select ")
			.append("BRANCH_ID,")
			.append("AC_TERM,")
			.append("SMS_RQST_ID,")
			.append("SMS_SRL_NO,")
			.append("MOBILE_NUM_LIST,")
			.append("MOBILE_NUM_CNT,")
			.append("DELIVERED_NUM_CNT,")
			.append("UNDELIVERED_NUM_CNT,")
			.append("UNDELIVERED_NUM_LIST,")
			.append("DETAILS_STATUS,")	
			.append("MSG_GRP_ID")	
			.append(" from smsd ")
			.append(" where")
			.append("  INST_ID= ?")
		.append(" and  BRANCH_ID= ?")
		.append(" and  DEL_FLG=?");
		data.add(smsDetailsKey.getInstId());
		data.add(smsDetailsKey.getBranchId());
		data.add("N");
		
		if ((smsDetailsKey.getAcTerm() !=null)&&(!smsDetailsKey.getAcTerm().equals(""))
				) {
			sql.append(" and AC_TERM=?  ");
			logger.debug("AC Term Value :" + smsDetailsKey.getAcTerm());
			data.add(smsDetailsKey.getAcTerm());
		}
		if ((smsDetailsKey.getSmsReqstId()!=null)&&(!smsDetailsKey.getSmsReqstId().equals(""))
				) {
			sql.append(" and SMS_RQST_ID=?  ");
			logger.debug("Request Id Value :" + smsDetailsKey.getSmsReqstId());
			data.add(smsDetailsKey.getSmsReqstId());
		}
		logger.debug("Sql query : "+sql.toString());
	Object[] array = data.toArray(new Object[data.size()]);
	logger.debug("Where clause conditions size :" + array.length);

	List<SMSDetails> selectedListSMSDetails = getJdbcTemplate()
			.query(sql.toString(), array, new selectedListSMSDetailsSelectRowMapper());
	if (selectedListSMSDetails.size() == 0) {
		logger.error("No  record found");
		throw new NoDataFoundException();
	}
	logger.debug("DAO : SMS Details List value"+selectedListSMSDetails.size() );
	return selectedListSMSDetails;
	}
	
}
class selectedListSMSDetailsSelectRowMapper implements RowMapper<SMSDetails> {

	@Override
	public SMSDetails mapRow(ResultSet rs, int arg1) throws SQLException {
		SMSDetails smsDetails = new SMSDetails();
	smsDetails.setBranchId(rs.getString("BRANCH_ID"));
	smsDetails.setAcTerm(rs.getString("AC_TERM"));
	smsDetails.setSmsReqstId(rs.getString("SMS_RQST_ID"));
	smsDetails.setSmsSrlNo(rs.getInt("SMS_SRL_NO"));
	smsDetails.setMobileNumList(rs.getString("MOBILE_NUM_LIST"));
	smsDetails.setMobileNumCnt(rs.getInt("MOBILE_NUM_CNT"));
	smsDetails.setDeliveredNumCnt(rs.getInt("DELIVERED_NUM_CNT"));
	smsDetails.setUnDeliveredNumCnt(rs.getInt("UNDELIVERED_NUM_CNT"));
	smsDetails.setUnDeliveredNumList(rs.getString("UNDELIVERED_NUM_LIST"));
	smsDetails.setDetailsStatus(rs.getString("DETAILS_STATUS"));
	smsDetails.setMsgGrpId(rs.getString("MSG_GRP_ID"));

	return smsDetails;
	}
	}