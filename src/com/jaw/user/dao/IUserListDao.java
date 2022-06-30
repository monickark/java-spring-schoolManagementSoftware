package com.jaw.user.dao;

import java.util.List;

import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.NoDataFoundException;

public interface IUserListDao {
	public List<User> selectUserListFromUserManagement(String instid,
			String branchId, User user) throws NoDataFoundException;

	public void insertUserList(List<User> user) throws DuplicateEntryException;

	public List<User> getUserListForBatchPgm(UserKey userKey)
			throws NoDataFoundException;

}
