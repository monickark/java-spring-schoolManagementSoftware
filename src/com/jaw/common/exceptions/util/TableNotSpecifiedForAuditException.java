package com.jaw.common.exceptions.util;

import com.jaw.common.constants.ErrorCodeConstant;

public class TableNotSpecifiedForAuditException extends Exception {

	String message = ErrorCodeConstant.TABLE_NOT_SPECIFIED_FOR_AUDIT;

	@Override
	public String getMessage() {
		return message;

	}

}
