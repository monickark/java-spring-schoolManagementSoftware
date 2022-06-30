package com.jaw.mark.dao;

import java.util.List;

import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.student.admission.dao.StudentMaster;
import com.jaw.student.admission.dao.StudentMasterKey;
import com.jaw.analytics.controller.ViewMarkList;
public interface IStudentReportCardDAO {

	List<StudentReportCard> getStuMarksForAllClass(
			StuMrksRmksListKey stuMrksRmksListKey) throws NoDataFoundException;

	List<StudentReportCard> getStuPrevExamConsolidation(
			StuMrksRmksListKey stuMrksRmksListKey) throws NoDataFoundException;

	List<StudentReportCard> getStuMarkPerClass(
			StuMrksRmksListKey stuMrksRmksListKey) throws NoDataFoundException;

	StudentMaster retriveStudentDetails(StudentMasterKey studentMasterKey)
			throws NoDataFoundException;

	String getLatestExam(String instId, String branchId, String studentGrpId)
			throws NoDataFoundException;

	StudentReportCard getStuClassRank(StuMrksRmksListKey stuMrksRmksListKey)
			throws NoDataFoundException;
	List<ViewMarkList> getStudentSubjectMarks(
			StuMrksRmksListKey stuMrksRmksListKey) throws NoDataFoundException;

	List<StudentReportCard> getAbsentListInExamForRC(
			StuMrksRmksListKey stuMrksRmksListKey) throws NoDataFoundException;
}
