package com.jaw.common.exceptions;

import com.jaw.common.constants.ErrorCodeConstant;



public class InsertFailedException extends Exception {

	String message = ErrorCodeConstant.INSERT_FAILED;

	public String getMessage() {
		return message;
	}

}
