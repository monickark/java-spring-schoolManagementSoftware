package com.jaw.common.exceptions;

import com.jaw.common.constants.ErrorCodeConstant;

public class AcadTermUpdateFailedException extends Exception {

	String message = ErrorCodeConstant.ACAD_TERM_UPDATE_FAILED;

	public String getMessage() {
		return message;
	}

}