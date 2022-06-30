package com.jaw.common.exceptions;

import com.jaw.common.constants.ErrorCodeConstant;

public class UnableCreateParentUserId extends Exception{

	String message = ErrorCodeConstant.PAR_USERID_CRE_FAIL;

	public String getMessage() {
		return message;
	}
}
