package com.jaw.common.exceptions;

import com.jaw.common.constants.ErrorCodeConstant;

public class UnableCreateStudentPassword extends Exception {

	String message = ErrorCodeConstant.STU_PW_CRE_FAIL;

	public String getMessage() {
		return message;
	}
}
