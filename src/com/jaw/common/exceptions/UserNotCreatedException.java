package com.jaw.common.exceptions;

import com.jaw.common.constants.ErrorCodeConstant;



public class UserNotCreatedException extends Exception {

	String message = ErrorCodeConstant.USERID_NOT_CREATED;

	public String getMessage() {
		return message;
	}

}
