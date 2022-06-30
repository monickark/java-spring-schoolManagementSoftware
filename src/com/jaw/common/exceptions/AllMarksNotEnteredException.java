package com.jaw.common.exceptions;

import com.jaw.common.constants.ErrorCodeConstant;

public class AllMarksNotEnteredException extends Exception {
	
	String message = ErrorCodeConstant.ALL_MARKS_NOT_ENTERED;
	
	@Override
	public String getMessage() {
		return message;
	}
	
}
