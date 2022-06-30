package com.jaw.common.exceptions.util;

import com.jaw.common.constants.ErrorCodeConstant;

public class PropertyNotFoundException extends Exception {

	String message = ErrorCodeConstant.PROPERTY_NOT_FOUND;

	@Override
	public String getMessage() {
		return message;
	}

}
