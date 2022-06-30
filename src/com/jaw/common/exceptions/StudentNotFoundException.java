package com.jaw.common.exceptions;

import com.jaw.common.constants.ErrorCodeConstant;

public class StudentNotFoundException extends Exception {
	String message = ErrorCodeConstant.STUDENT_NOT_FOUND;

	public String getMessage() {
		return message;
	}

	


}