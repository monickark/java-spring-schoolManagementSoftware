package com.jaw.framework.audit.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jaw.common.constants.AuditConstant;
import com.jaw.common.exceptions.BatchUpdateFailedException;
import com.jaw.common.exceptions.DatabaseException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.util.TableNotSpecifiedForAuditException;
import com.jaw.common.util.TableMaintenanceUtil;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.audit.dao.Audit;
import com.jaw.framework.audit.dao.IAuditDao;
import com.jaw.framework.seqGen.service.IIdGeneratorService;
import com.jaw.framework.sessCache.UserSessionDetails;

//Helper Class for Auditing
@Component
public class DoAudit {
	@Autowired
	IAuditDao auditDao;
	// Logging
	static Logger logger = Logger.getLogger(DoAudit.class);

	@Autowired
	// For Auditing Batch Id Generation is used
	IIdGeneratorService batchIdGen;

	// Method to do Functional Auditing
	public void doFunctionalAudit(UserSessionDetails userSessionDetails,
			String activityCode, String auditRemarks)
			throws DuplicateEntryException, DatabaseException {

		logger.info("Inside Functional Audit Method");

		auditDao.insert(getAuditPojo(userSessionDetails, activityCode,
				auditRemarks));

	}

	public Audit getAuditPojo(UserSessionDetails userSessionDetails,
			String activityCode, String auditRemarks)
			throws DuplicateEntryException, DatabaseException {

		logger.info("Inside Functional Audit Method");

		Audit auditPojo = new Audit();
		auditPojo.setInstId(userSessionDetails.getInstId());
		auditPojo.setBranchId(userSessionDetails.getBranchId());
		auditPojo.setAuditSrlNo(batchIdGen.getNextId(
				AuditConstant.AUDIT_SRL_NO_SEQ, 1).toString());
		auditPojo.setAuditFlag(AuditConstant.AUDIT_FLAG_FUNCTIONAL);
		auditPojo.setActCode(activityCode);
		auditPojo.setIpAddress(userSessionDetails.getIpAddress());
		auditPojo.setrCreId(userSessionDetails.getUserId());
		auditPojo.setAuditRemarks(auditRemarks);

		return auditPojo;

	}

	// Method to do Functional Auditing
	public void doFunctionalAudit(UserSessionDetails userSessionDetails,
			String activityCode, String auditRemarks, String[] userIds)
			throws DuplicateEntryException, DatabaseException,
			BatchUpdateFailedException {
		logger.info("Inside Functional Batch Audit Method");
		Audit auditPojo = getAuditPojo(userSessionDetails, activityCode,
				auditRemarks);
		String[] slNos = new String[userIds.length];
		for (int i = 0; i < slNos.length; i++) {
			slNos[i] = batchIdGen.getNextId(AuditConstant.AUDIT_SRL_NO_SEQ, 1)
					.toString();
		}
		auditDao.insertBatch(auditPojo, userIds, slNos);

	}

	// Method to do Database Auditing
	public void doDatabaseAudit(ApplicationCache applicationCache,
			UserSessionDetails userSessionDetails, String tableName,
			String tableKey, String oldRecord, String typeOfOperation,
			String newRecord, String auditRemarks)
			throws DuplicateEntryException, DatabaseException,
			TableNotSpecifiedForAuditException {

		logger.info("Inside Database Audit Method");
		logger.debug("Method Parameters values database audit:-   ApplicationCache :"
				+ applicationCache
				+ " UsersessionDetails :"
				+ userSessionDetails.getInstId()
				+ "  TableName :"
				+ tableName
				+ "  "
				+ "table Key :"
				+ tableKey
				+ "  oldRecord :"
				+ oldRecord
				+ " typeOfOp :"
				+ typeOfOperation
				+ " newrecord :"
				+ newRecord
				+ " Audit Remarks :" + auditRemarks);
		TableMaintenanceUtil tableMaintenanceUtil = new TableMaintenanceUtil();
		if (tableMaintenanceUtil.getPropertyValue(applicationCache,
				userSessionDetails.getInstId(), tableName).equals("Y")) {
			Audit auditPojo = new Audit();
			auditPojo.setInstId(userSessionDetails.getInstId());
			auditPojo.setBranchId(userSessionDetails.getBranchId());
			auditPojo.setAuditFlag(AuditConstant.AUDIT_FLAG_DATABASE);
			auditPojo.setTableName(tableName);
			auditPojo.setAuditSrlNo(batchIdGen.getNextId(
					AuditConstant.AUDIT_SRL_NO_SEQ, 1).toString());
			auditPojo.setIpAddress(userSessionDetails.getIpAddress());
			auditPojo.setAuditRemarks(auditRemarks);
			auditPojo.setrCreId(userSessionDetails.getUserId());
			auditPojo.setTableKey(tableKey);
			auditPojo.setOldRecord(oldRecord);
			auditPojo
					.setNewRecord(typeOfOperation.concat(
							AuditConstant.AUDIT_TYPEOFOPER_SEPERATOR).concat(
							newRecord));

			auditDao.insert(auditPojo);
		}

	}
}
