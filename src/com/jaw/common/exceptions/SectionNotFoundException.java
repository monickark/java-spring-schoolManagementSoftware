package com.jaw.common.exceptions;

import com.jaw.common.constants.ErrorCodeConstant;

public class SectionNotFoundException extends Exception {
	String message = ErrorCodeConstant.SECTION_NOT_FOUND;

	public String getMessage() {
		return message;
	}

	
}
