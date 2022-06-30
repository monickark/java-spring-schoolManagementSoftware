package com.jaw.mark.service;

import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.framework.sessCache.ParentSession;
import com.jaw.framework.sessCache.StudentSession;
import com.jaw.framework.sessCache.UserSessionDetails;
import com.jaw.mark.controller.ReportCardMasterVo;

public interface IViewReportCardService {

	void viewRepordCard(ReportCardMasterVo ReportCardMasterVo,
			UserSessionDetails userSessionDetails,
			StudentSession studentSession, ParentSession parentSession)
			throws NoDataFoundException;

	void getStudentAdmisNo(ReportCardMasterVo ReportCardMasterVo,
			UserSessionDetails userSessionDetails, String flow)
			throws NoDataFoundException;

}
