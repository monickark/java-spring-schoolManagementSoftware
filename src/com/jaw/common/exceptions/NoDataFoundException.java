package com.jaw.common.exceptions;

import com.jaw.common.constants.ErrorCodeConstant;

public class NoDataFoundException extends Exception {
	String message = ErrorCodeConstant.NO_RECORD_FOUND;

	public String getMessage() {
		return message;
	}


}
