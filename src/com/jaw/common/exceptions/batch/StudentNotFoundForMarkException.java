package com.jaw.common.exceptions.batch;

import com.jaw.common.constants.ErrorCodeConstant;

public class StudentNotFoundForMarkException extends Exception {

	private String message = ErrorCodeConstant.NO_REC_FOR_MARK;

	@Override
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
