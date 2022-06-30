package com.jaw.framework.audit.dao;

import com.jaw.admin.dao.DataLog;
import com.jaw.admin.dao.DataLogKey;
import com.jaw.common.exceptions.BatchUpdateFailedException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.NoDataFoundException;

public interface IAuditDao {
	public void insert(Audit auditPojo) throws DuplicateEntryException;

	public DataLog getAuditRecord(DataLogKey dataLogKey)
			throws NoDataFoundException;

	int[] insertBatch(Audit auditPojo, String[] userId, String[] slNos)
			throws DuplicateEntryException, BatchUpdateFailedException;
}
