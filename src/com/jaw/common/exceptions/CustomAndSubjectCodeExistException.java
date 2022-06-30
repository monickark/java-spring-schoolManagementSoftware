package com.jaw.common.exceptions;

import com.jaw.common.constants.ErrorCodeConstant;

public class CustomAndSubjectCodeExistException extends Exception {
	
	String message = ErrorCodeConstant.CUST_SUB_CODE_EXIST;

	public String getMessage() {
		return message;
	}

	
}
