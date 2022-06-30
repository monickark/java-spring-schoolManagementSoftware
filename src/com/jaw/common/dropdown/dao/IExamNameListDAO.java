package com.jaw.common.dropdown.dao;

import java.util.List;

import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.core.dao.CourseMaster;
import com.jaw.framework.sessCache.UserSessionDetails;
import com.jaw.mark.dao.MarkMaster;

public interface IExamNameListDAO {
	List<MarkMaster> selectExamNameList(UserSessionDetails userSessionDetails,String studentGrpId) throws NoDataFoundException;
}
