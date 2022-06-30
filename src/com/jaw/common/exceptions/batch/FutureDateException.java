package com.jaw.common.exceptions.batch;

import com.jaw.common.constants.ErrorCodeConstant;

public class FutureDateException extends Exception {
	
	String message = ErrorCodeConstant.FUTURE_DATE;

	public String getMessage() {
		return message;
	}

}
