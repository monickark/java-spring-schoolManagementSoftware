package com.jaw.common.exceptions;

import com.jaw.common.constants.ErrorCodeConstant;



public class UpdateFailedException extends Exception {

	String message = ErrorCodeConstant.UPDATE_FAILED;

	public String getMessage() {
		return message;
	}

}
