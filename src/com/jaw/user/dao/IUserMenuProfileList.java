package com.jaw.user.dao;

import java.util.List;

import com.jaw.common.exceptions.NoDataFoundException;

public interface IUserMenuProfileList {
	public List<String> selectMenuProfile(String instid, String branchId)
			throws NoDataFoundException;
}
