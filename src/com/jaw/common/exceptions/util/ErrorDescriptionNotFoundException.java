package com.jaw.common.exceptions.util;

import com.jaw.common.constants.ErrorCodeConstant;

public class ErrorDescriptionNotFoundException extends Exception {

	String message = ErrorCodeConstant.ERROR_DESCRIPTION_NOT_FOUND;

	@Override
	public String getMessage() {
		return message;
	}

}
