package com.jaw.common.exceptions;

import com.jaw.common.constants.ErrorCodeConstant;

public class DuplicateEntryException extends Exception {
	
	String message = ErrorCodeConstant.DUPLICATE_ENTRY;

	public String getMessage() {
		return message;
	}

	
}
