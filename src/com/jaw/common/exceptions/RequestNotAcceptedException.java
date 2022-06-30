package com.jaw.common.exceptions;

import com.jaw.common.constants.ErrorCodeConstant;



public class RequestNotAcceptedException extends Exception {

	String message = ErrorCodeConstant.BATCH_UPDATE_FAILED;

	public String getMessage() {
		return message;
	}

}
