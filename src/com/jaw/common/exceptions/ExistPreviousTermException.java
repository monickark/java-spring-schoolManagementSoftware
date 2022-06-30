package com.jaw.common.exceptions;

import com.jaw.common.constants.ErrorCodeConstant;

public class ExistPreviousTermException extends Exception{
	String message = ErrorCodeConstant.EXIST_PREVIOUS_TERM;

	public String getMessage() {
		return message;
	}

}
