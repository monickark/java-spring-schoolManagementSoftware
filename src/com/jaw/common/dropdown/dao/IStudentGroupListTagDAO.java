package com.jaw.common.dropdown.dao;

import java.util.List;

import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.core.dao.StudentGroupMaster;
import com.jaw.framework.sessCache.UserSessionDetails;

public interface IStudentGroupListTagDAO {
	List<StudentGroupMaster> selectStudentGroupList(final UserSessionDetails userSessionDetails) throws NoDataFoundException;
	List<StudentGroupMaster> selectStudentGroupListForStaff(UserSessionDetails userSessionDetails,String acTerm) ;
	List<StudentGroupMaster> selectStudentGroupListForClassTeacher(
			UserSessionDetails userSessionDetails, String studentGrpId,
			String acTerm);
	List<StudentGroupMaster> selectStudentGroupListForStaffDashBoard(UserSessionDetails userSessionDetails,String acTerm) ;
	
}
