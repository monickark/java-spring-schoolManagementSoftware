package com.jaw.common.exceptions.batch;

import com.jaw.common.constants.ErrorCodeConstant;

public class DuplicateEntryForAcaTermHolGenException extends Exception {
	
	String message = ErrorCodeConstant.HOL_INSERT_FAILED_IN_ACACAL;

	public String getMessage() {
		return message;
	}

}
