package com.jaw.fee.dao;

import java.util.List;

import com.jaw.common.exceptions.DuplicateEntryException;

public interface IStudentFeeDemandListDAO {
	public void insertStudentFeeDemandListRecs(final List<StudentFeeDemand> studentFeeDmdList)
			throws DuplicateEntryException;
}
