package com.jaw.user.dao;

import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.UpdateFailedException;
import com.jaw.common.exceptions.login.InvalidUserIdException;

public interface IUserDao {
	public User validateUserId(String string) throws InvalidUserIdException;

	public void updateUser(User l) throws UpdateFailedException;

	void insertSingleUser(User users) throws DuplicateEntryException;

	public void updatePassword(User user) throws UpdateFailedException;

}
