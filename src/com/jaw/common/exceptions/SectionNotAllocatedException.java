package com.jaw.common.exceptions;

import com.jaw.common.constants.ErrorCodeConstant;

public class SectionNotAllocatedException extends Exception {
	
	String message = ErrorCodeConstant.SECTION_NOT_ALLOCATED;
	
	@Override
	public String getMessage() {
		return message;
	}
	
}
