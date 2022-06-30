package com.jaw.user.dao;

import java.util.List;

import com.jaw.common.exceptions.NoDataFoundException;

public interface IUserManagementListDao {
	public List<UserManagement> selectUserManagementListFromUserManagement(
			UserManagement UserManagement) throws NoDataFoundException;
}
