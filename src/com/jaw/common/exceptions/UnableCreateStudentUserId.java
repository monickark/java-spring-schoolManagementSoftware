package com.jaw.common.exceptions;

import com.jaw.common.constants.ErrorCodeConstant;

public class UnableCreateStudentUserId extends Exception {

	String message = ErrorCodeConstant.STU_USERID_CRE_FAIL;

	public String getMessage() {
		return message;
	}
}
