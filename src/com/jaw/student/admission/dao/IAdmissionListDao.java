package com.jaw.student.admission.dao;

import java.util.List;

import com.jaw.common.exceptions.NoDataFoundException;

public interface IAdmissionListDao {
	public List<AdmissionList> selectAdmissionListDetails(
			AdmissionKey admissionKey) throws NoDataFoundException;

	List<AdmissionCountList> selectAdmissionCountListDetails(
			AdmissionKey admissionKey) throws NoDataFoundException;
}
