package com.jaw.common.exceptions;

import com.jaw.common.constants.ErrorCodeConstant;

public class ExamOrderExistException extends Exception {
	
	String message = ErrorCodeConstant.EXAM_ORDER_EXIST;

	public String getMessage() {
		return message;
	}

	
}
