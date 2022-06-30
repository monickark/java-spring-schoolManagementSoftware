package com.jaw.common.exceptions.util;

import com.jaw.common.constants.ErrorCodeConstant;

public class CommonCodeNotFoundException extends Exception {

	String message = ErrorCodeConstant.COMMON_CODE_NOT_FOUND;

	@Override
	public String getMessage() {
		return message;
	}

}
