package com.jaw.admin.service;

import com.jaw.admin.controller.SMSConfigurationMasterVO;
import com.jaw.admin.controller.SMSConfigurationVO;
import com.jaw.common.exceptions.DatabaseException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.UpdateFailedException;
import com.jaw.common.exceptions.util.TableNotSpecifiedForAuditException;
import com.jaw.core.controller.AcademicCalendarVO;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.sessCache.UserSessionDetails;

public interface ISMSConfigurationService {
	public SMSConfigurationMasterVO selectStudentGroupList(SMSConfigurationMasterVO smsConfigurationMasterVO,UserSessionDetails userSessionDetails)
			throws NoDataFoundException;
	public void saveSMSConfigurationDetails(SMSConfigurationMasterVO smsConfigurationMasterVO,UserSessionDetails userSessionDetails,String[] rowIds,
			String[] propertyValues,String[] propertyNames)
			throws NoDataFoundException, UpdateFailedException;
	public void updateSMSConfigurationRec(SMSConfigurationVO smsConfigurationVO,
			UserSessionDetails userSessionDetails,ApplicationCache applicationCache) throws UpdateFailedException,NoDataFoundException, DuplicateEntryException, DatabaseException, TableNotSpecifiedForAuditException;
}
