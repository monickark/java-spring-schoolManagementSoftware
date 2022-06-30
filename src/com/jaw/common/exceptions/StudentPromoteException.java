package com.jaw.common.exceptions;

import com.jaw.common.constants.ErrorCodeConstant;

public class StudentPromoteException extends Exception {
	String message = ErrorCodeConstant.STUDENT_PROMOTE_EXIST;

	public String getMessage() {
		return message;
	}


}