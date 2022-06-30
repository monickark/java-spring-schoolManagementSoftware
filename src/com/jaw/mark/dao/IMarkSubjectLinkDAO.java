package com.jaw.mark.dao;

import com.jaw.common.exceptions.DeleteFailedException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.UpdateFailedException;

public interface IMarkSubjectLinkDAO {
	
	void insertMarkSubjectRec(MarkSubjectLink MarkSubjectLink) throws DuplicateEntryException;
	
	void updateMarkSubjectRec(MarkSubjectLink MarkSubjectLink, MarkSubjectLinkKey MarkSubjectkey)
			throws UpdateFailedException;
	
	void deleteMarkSubjectRec(MarkSubjectLink MarkSubjectLink, MarkSubjectLinkKey MarkSubjectLinkKey)
			throws DeleteFailedException;
	
	MarkSubjectLink selectMarkSubjectRec(MarkSubjectLinkKey MarkSubjectLinkKey)
			throws NoDataFoundException;

	int checkRecordExist(MarkSubjectLinkKey markSubjectLinkKey);

	MarkSubjectLink selectMarkSubjectRecNotById(
			MarkSubjectLinkKey markSubjectLinkKey) throws NoDataFoundException;

	int checkFirstRecord(MarkSubjectLinkKey markSubjectLinkKey);
	
}
