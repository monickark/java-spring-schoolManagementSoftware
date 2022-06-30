package com.jaw.common.exceptions.batch;

import com.jaw.common.constants.ErrorCodeConstant;

public class DuplicateEntryForHolGenException extends Exception {
	
	String message = ErrorCodeConstant.DUPLICATE_ENTRY_HOL_BATCH;

	public String getMessage() {
		return message;
	}

}
