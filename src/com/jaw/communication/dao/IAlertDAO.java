package com.jaw.communication.dao;

import com.jaw.common.exceptions.DeleteFailedException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.UpdateFailedException;

public interface IAlertDAO {
	void insertAlertRec(Alert alertRecord)
			throws DuplicateEntryException;

	void updateAlertRec(Alert alertRecord,final AlertKey alertKey)
			throws UpdateFailedException;

	void deleteAlertRec(Alert alertRecord,final AlertKey alertKey) throws DeleteFailedException;
	void stopAlertRec(Alert alertRecord,final AlertKey alertKey) throws UpdateFailedException;

	Alert selectAlertRec(
			final AlertKey alertKey)
			throws NoDataFoundException;
}
