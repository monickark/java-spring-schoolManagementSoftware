package com.jaw.communication.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jaw.common.exceptions.DatabaseException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.UpdateFailedException;
import com.jaw.common.exceptions.util.TableNotSpecifiedForAuditException;
import com.jaw.communication.controller.SMSHistoryMasterVO;
import com.jaw.communication.controller.SMSRequestVO;
import com.jaw.communication.dao.SMSListKey;
import com.jaw.communication.dao.SMSMemberList;
import com.jaw.core.controller.SubjectMaster_MasterVO;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.sessCache.SessionCache;
import com.jaw.framework.sessCache.UserSessionDetails;

public interface ISMSRequestService {
	Map<String,String> getStudentGroupList(SessionCache sessionCache);
	Map<String,String> getDepartementList(UserSessionDetails userSessiondetails);
	public List<SMSMemberList> getSpecificMemberList(UserSessionDetails userSessiondetails,String genGroup,String speGroup)throws NoDataFoundException;
	public void saveSMSRequest(UserSessionDetails userSessiondetails,SMSRequestVO smsRequestVo,String[] specificMembList,ApplicationCache applicationCache)throws  DatabaseException, DuplicateEntryException, NoDataFoundException, UpdateFailedException, TableNotSpecifiedForAuditException;
	public SMSHistoryMasterVO selectSMSRequestList(
			SMSHistoryMasterVO smsHistoryMasterVO,
			UserSessionDetails usersessiondetails) throws NoDataFoundException;
	public SMSHistoryMasterVO selectSMSDetailsList(
			SMSHistoryMasterVO smsHistoryMasterVO,
			UserSessionDetails usersessiondetails) throws NoDataFoundException;
}
