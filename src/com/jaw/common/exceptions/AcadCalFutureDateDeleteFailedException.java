package com.jaw.common.exceptions;

import com.jaw.common.constants.ErrorCodeConstant;

public class AcadCalFutureDateDeleteFailedException extends Exception {

	String message = ErrorCodeConstant.ACADTERM_FUTURE_DATE_DELETE_FAILED;

	public String getMessage() {
		return message;
	}

}
