package com.jaw.common.exceptions.login;

import com.jaw.common.constants.ErrorCodeConstant;

public class UserDisabledException extends Exception {
	String message = ErrorCodeConstant.USER_DISABLED;

	@Override
	public String getMessage() {
		return message;
	}

}
