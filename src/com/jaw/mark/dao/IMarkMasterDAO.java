package com.jaw.mark.dao;

import com.jaw.common.exceptions.DeleteFailedException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.UpdateFailedException;

public interface IMarkMasterDAO {
	void insertMarkMasterRec(MarkMaster markMaster)
			throws DuplicateEntryException;

	void updateMarkMasterRec(MarkMaster markMaster,MarkMasterKey markMasterKey)
			throws UpdateFailedException;

	void deleteMarkMasterRec(MarkMaster markMaster,MarkMasterKey markMasterKey) throws DeleteFailedException;

	MarkMaster selectMarkMasterRec(
			MarkMasterKey markMasterKey)
			throws NoDataFoundException;
	int checkMarkMasterRecExist(MarkMaster markMaster);
	MarkMaster selectMarkMasterRecForStatus(
			MarkMaster markMaster)
			throws NoDataFoundException;
	int checkMarkMasterExamOrderExist(MarkMaster markMaster);

	MarkMaster selectMarkMasterRecNotById(MarkMasterKey markMasterKey)
			throws NoDataFoundException;
}
