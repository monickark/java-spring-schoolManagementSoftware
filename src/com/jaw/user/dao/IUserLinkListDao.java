package com.jaw.user.dao;

import java.util.List;

import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.NoDataFoundException;

public interface IUserLinkListDao {

	public void insertListOfUserLinkRec(List<UserLink> listOfUserLink)
			throws DuplicateEntryException;

	public List<UserLink> getListOfUserLinkRecords(UserLinkKey userLinkKey)
			throws NoDataFoundException;

}
