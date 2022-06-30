package com.jaw.common.dropdown.dao;

import java.util.Map;

import com.jaw.common.exceptions.NoDataFoundException;

public interface IMenuProfileListDAO {

	Map<String, String> selectMenuProfileList(String instId, String branchId,
			String profileGroup) throws NoDataFoundException;
	Map<String, String> selectAdminUserList() throws NoDataFoundException;

}
