package com.jaw.admin.dao;

import java.util.List;

import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.student.admission.dao.StudentMaster;

public interface IStudentPromotionListDAO {
	public List<StudentMaster> selectStudentDetainList(StudentPromotionKey studentPromotionKey)
			throws NoDataFoundException;
	public List<StudentMaster> selectStudentListForPromotion(StudentPromotionKey studentPromotionKey)
			throws NoDataFoundException;
	void insertPromotedStudentMaster(List<StudentMaster> studentMasterList) throws DuplicateEntryException;
}
