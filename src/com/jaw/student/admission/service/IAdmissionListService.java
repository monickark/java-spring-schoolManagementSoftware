package com.jaw.student.admission.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.framework.sessCache.UserSessionDetails;
import com.jaw.student.admission.controller.AdmissionCountListVO;
import com.jaw.student.admission.controller.AdmissionSearchVO;
import com.jaw.student.admission.controller.NewAdmissionDetailsVO;

public interface IAdmissionListService {
	public NewAdmissionDetailsVO selectAdmissionList(NewAdmissionDetailsVO newAdmissionDetailsVO,
			UserSessionDetails userSessionDetails) throws NoDataFoundException ;

	/*public Map<String, Integer> selectAdmissionCountList(AdmissionSearchVO admissionSearchVO,
			UserSessionDetails userSessionDetails) throws NoDataFoundException;*/
	public List<AdmissionCountListVO> selectAdmissionCount(AdmissionSearchVO admissionSearchVO,
			UserSessionDetails userSessionDetails, HttpSession session) throws NoDataFoundException;
}
