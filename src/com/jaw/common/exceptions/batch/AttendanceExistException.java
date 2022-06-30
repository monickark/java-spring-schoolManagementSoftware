package com.jaw.common.exceptions.batch;

import com.jaw.common.constants.ErrorCodeConstant;

public class AttendanceExistException extends Exception {
	
	String message = ErrorCodeConstant.ATTENDANCE_EXIST;

	public String getMessage() {
		return message;
	}

}
