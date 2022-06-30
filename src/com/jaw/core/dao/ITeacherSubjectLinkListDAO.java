package com.jaw.core.dao;

import java.util.List;
import java.util.Map;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.UpdateFailedException;

public interface ITeacherSubjectLinkListDAO {
	public List<TeacherSubjectLink> selectTeacherSubLinkList(final TeacherSubjectLinkListKey teacherSubLinkListKey)
			throws NoDataFoundException;
	Map<String, String> selectStaffList(TeacherSubjectLink teacherSubjectLink)
			throws NoDataFoundException;
	List<TeacherSubjectLinkList> getStaffListForTransferProcess(
			TeacherSubjectLinkList teacherSubjectLinkList)
			throws NoDataFoundException;
	void updateStaffDataOnTransfer(
			List<TeacherSubjectLinkList> teacherSubjectLinkLists)
			throws UpdateFailedException;}
