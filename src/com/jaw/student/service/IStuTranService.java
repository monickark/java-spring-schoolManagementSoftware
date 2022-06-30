package com.jaw.student.service;

import java.util.Map;

import com.jaw.common.exceptions.DatabaseException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.UpdateFailedException;
import com.jaw.common.exceptions.util.TableNotSpecifiedForAuditException;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.sessCache.UserSessionDetails;
import com.jaw.student.admission.dao.StuTranKey;
import com.jaw.student.controller.StuTranSearchVO;
import com.jaw.student.controller.StuTranVO;

public interface IStuTranService {
	
	public String getStuForTransfer(UserSessionDetails userSession,StuTranSearchVO stuTranSearchVO);
	public void studentTransfer(StuTranSearchVO stuTranSearchVO,
			StuTranVO stuTranVO,UserSessionDetails userSession,ApplicationCache applicationCache) throws NoDataFoundException, UpdateFailedException, DuplicateEntryException, DatabaseException, TableNotSpecifiedForAuditException;
	
	public Map<String,String> getStudentNameMap(
			UserSessionDetails userSession, String acy, String stuGrpId) throws NoDataFoundException;

}
