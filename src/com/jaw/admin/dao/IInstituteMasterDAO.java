package com.jaw.admin.dao;

import com.jaw.common.exceptions.DatabaseException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.UpdateFailedException;

//Interface for Institute Master CRUD DAO 
public interface IInstituteMasterDAO {

	void insertInstituteMasterRec(InstituteMaster instMasterRecord)
			throws DuplicateEntryException;

	void updateInstituteMasterRec(InstituteMaster instMasterRecord,
			InstituteMasterKey instMasterKey) throws UpdateFailedException;

//	InstituteMaster selectInstituteMasterRec() throws DatabaseException;

	InstituteMaster selectInstituteMasterRec(final Integer dbtsValue,
			final String instId) throws DatabaseException;

}
