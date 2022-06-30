package com.jaw.core.dao;

import java.util.List;
import java.util.Map;

import com.jaw.common.exceptions.NoDataFoundException;

public interface ISubjectMasterListDAO {
	public List<SubjectMaster> getSubjectMasterList(
			SubjectMasterKey subjectMasterKey) throws NoDataFoundException;

	public Map<String, String> getSubjectBasedOnSubType(
			final SubjectMasterKey subjectMasterKey)
			throws NoDataFoundException;

	Map<String, String> getSubjectBasedOnSubTypeAndCourseId(
			SubjectMasterKey subjectMasterKey, String courseId)
			throws NoDataFoundException;
}
