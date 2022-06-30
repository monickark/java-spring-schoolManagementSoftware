package com.jaw.common.exceptions;

import com.jaw.common.constants.ErrorCodeConstant;

public class InvalidCategoryForUpdateException extends Exception {
	String message = ErrorCodeConstant.STUU_INVALID_SUB_COLUMNs;

	public String getMessage() {
		return message;
	}
}
