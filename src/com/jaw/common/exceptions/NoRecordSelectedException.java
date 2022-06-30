package com.jaw.common.exceptions;

import com.jaw.common.constants.ErrorCodeConstant;

public class NoRecordSelectedException extends Exception {

	String message = ErrorCodeConstant.NO_RECORD_SELECTED;

	@Override
	public String getMessage() {
		return message;
	}

}
