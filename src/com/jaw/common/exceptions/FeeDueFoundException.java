package com.jaw.common.exceptions;

import com.jaw.common.constants.ErrorCodeConstant;

public class FeeDueFoundException extends Exception {
	String message = ErrorCodeConstant.FEE_DUE_ERROR;

	public String getMessage() {
		return message;
	}
}
