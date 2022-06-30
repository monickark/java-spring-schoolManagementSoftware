package com.jaw.common.exceptions;

import com.jaw.common.constants.ErrorCodeConstant;

public class CustomDuplicateEntryException extends Exception {
	
	String message = ErrorCodeConstant.CUSTOM_DUPLICATE_ENTRY;

	public String getMessage() {
		return message;
	}

	
}
