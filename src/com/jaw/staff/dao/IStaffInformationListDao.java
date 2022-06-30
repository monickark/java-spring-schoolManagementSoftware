package com.jaw.staff.dao;

import java.util.List;

import com.jaw.common.exceptions.NoDataFoundException;

public interface IStaffInformationListDao {

	List<StaffInformationList> getStaffListForInformation(
			StaffInformationList staffInformationList)
			throws NoDataFoundException;

}
