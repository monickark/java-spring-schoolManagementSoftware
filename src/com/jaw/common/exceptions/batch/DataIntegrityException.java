package com.jaw.common.exceptions.batch;

//User Defined Exception for DataIntegrityViolationException
public class DataIntegrityException extends Exception {

	private String message;

	@Override
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public DataIntegrityException(String localizedMessage) {
		message = localizedMessage;
	}

}
