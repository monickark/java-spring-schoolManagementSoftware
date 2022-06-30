package com.jaw.communication.dao;

import java.util.List;

import com.jaw.attendance.dao.StudentAttendanceMaster;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.framework.sessCache.UserSessionDetails;

public interface ISMSListDAO {
public List<SMSMemberList> getMemberListForStudent(SMSListKey smsListKey,int offset)throws NoDataFoundException;
public List<SMSMemberList> getMemberListForParent(SMSListKey smsListKey,int offset)throws NoDataFoundException;
public List<SMSMemberList> getMemberListForStaff(SMSListKey smsListKey,int offset)throws NoDataFoundException;
public int getMobileNumCountForParent(SMSListKey smsListKey);
public int getMobileNumCountForStaff(SMSListKey smsListKey);
public int getMobileNumCountForStudent(SMSListKey smsListKey);
public List<SMSMemberList> selectParenMobileListForAlert(SMSListKey smsListKey,List<String> ids)throws NoDataFoundException;
}
