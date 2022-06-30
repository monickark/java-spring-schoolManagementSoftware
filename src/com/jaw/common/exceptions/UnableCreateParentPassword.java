package com.jaw.common.exceptions;

import com.jaw.common.constants.ErrorCodeConstant;

public class UnableCreateParentPassword extends Exception {

	String message = ErrorCodeConstant.PAR_PW_CRE_FAIL;

	public String getMessage() {
		return message;
	}
}
