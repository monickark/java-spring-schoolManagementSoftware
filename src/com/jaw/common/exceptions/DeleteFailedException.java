package com.jaw.common.exceptions;

import com.jaw.common.constants.ErrorCodeConstant;



public class DeleteFailedException extends Exception {

	String message = ErrorCodeConstant.DELETE_FAILED;

	public String getMessage() {
		return message;
	}

}
