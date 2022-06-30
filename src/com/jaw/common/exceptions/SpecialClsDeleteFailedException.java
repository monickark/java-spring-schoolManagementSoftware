package com.jaw.common.exceptions;

import com.jaw.common.constants.ErrorCodeConstant;

public class SpecialClsDeleteFailedException extends Exception {

	String message = ErrorCodeConstant.SPL_CLS_DELETE_FAILED;

	public String getMessage() {
		return message;
	}
}