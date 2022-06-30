package com.jaw.common.exceptions;

import com.jaw.common.constants.ErrorCodeConstant;

public class AcadCalendarDeleteFailedException extends Exception {

	String message = ErrorCodeConstant.ACADTERM_CLOSED_DELETE_FAILED;

	public String getMessage() {
		return message;
	}
}
