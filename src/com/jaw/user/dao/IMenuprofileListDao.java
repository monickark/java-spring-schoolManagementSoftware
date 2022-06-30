package com.jaw.user.dao;

import java.util.List;

import com.jaw.common.exceptions.NoDataFoundException;

public interface IMenuprofileListDao {

	List<String> selectMenuProfile(String instid) throws NoDataFoundException;

}
