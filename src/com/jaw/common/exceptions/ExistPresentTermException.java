package com.jaw.common.exceptions;

import com.jaw.common.constants.ErrorCodeConstant;

//Exception class for ExistPresentTermException
public class ExistPresentTermException extends Exception{
	String message = ErrorCodeConstant.EXIST_PRESENT_TERM;

	public String getMessage() {
		return message;
	}

}
