package com.jaw.common.exceptions;

import com.jaw.common.constants.ErrorCodeConstant;

public class FeeMasterNotFoundException extends Exception {
	String message = ErrorCodeConstant.FEE_MASTER_NOT_FOUND;

	public String getMessage() {
		return message;
	}

	


}