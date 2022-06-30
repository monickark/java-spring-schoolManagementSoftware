package com.jaw.login.dao;

import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.user.dao.User;

public interface ILoginDao {
	public boolean insertLogin(User user) throws DuplicateEntryException;

	public void deleteLogin(String branchId, String instId, String userId);
}
