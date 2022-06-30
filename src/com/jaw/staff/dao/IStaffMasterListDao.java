package com.jaw.staff.dao;

import java.util.List;

import com.jaw.common.exceptions.NoDataFoundException;

public interface IStaffMasterListDao {

	List<StaffMaster> getListForClassTeacherAllotment(String branchId,
			String instId, String academicYear) throws NoDataFoundException;


	
}
