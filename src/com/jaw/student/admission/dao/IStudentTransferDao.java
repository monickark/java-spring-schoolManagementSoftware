package com.jaw.student.admission.dao;

import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.UpdateFailedException;

public interface IStudentTransferDao {
	
	public String feeDueCheckForStuTan(StuTranKey stuTranKey) throws NoDataFoundException;
	public void stuTransfer(final StuTranKey stuTranKey,final StudentMaster stuMas) throws UpdateFailedException;
	public StudentMaster getStuTransferRec(StuTranKey stuTranKey) throws NoDataFoundException;

}
