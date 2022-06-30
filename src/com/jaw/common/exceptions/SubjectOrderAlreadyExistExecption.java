package com.jaw.common.exceptions;

import com.jaw.common.constants.ErrorCodeConstant;

public class SubjectOrderAlreadyExistExecption extends Exception {
	
	String message = ErrorCodeConstant.SUBJECT_ORDER_ALREADY_EXIST;

	public String getMessage() {
		return message;
	}

	
}
