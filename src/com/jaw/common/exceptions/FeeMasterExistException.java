package com.jaw.common.exceptions;

import com.jaw.common.constants.ErrorCodeConstant;

public class FeeMasterExistException extends Exception {

	String message = ErrorCodeConstant.FEE_MASTER_EXIST;

	public String getMessage() {
		return message;
	}

}
