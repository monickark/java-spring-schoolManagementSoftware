package com.jaw.user.dao;

import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.UpdateFailedException;
import com.jaw.common.exceptions.login.InvalidUserIdException;

public interface IUserLinkDao {
	void insertUserLink(UserLink userLink) throws DuplicateEntryException;
	
	UserLink getUserDetails(String userId)
			throws InvalidUserIdException;
	
	UserLink getUserDetailsByLinkId(String linkId, String instId, String branchId)
			throws InvalidUserIdException;
	
	void updateUserLinkRec(UserLink userlink) throws UpdateFailedException;
}
