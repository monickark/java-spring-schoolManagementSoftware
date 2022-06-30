package com.jaw.common.exceptions.batch;

import com.jaw.common.constants.ErrorCodeConstant;

//User defined Exception class for empty result set while selecting the record
public class NoRecordFoundException extends Exception {

	private String message = ErrorCodeConstant.NO_RECORD_FOUND;

	@Override
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
