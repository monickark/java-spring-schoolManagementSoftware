package com.jaw.login.service;

import com.jaw.common.exceptions.DatabaseException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.RequestNotAcceptedException;
import com.jaw.common.exceptions.UpdateFailedException;
import com.jaw.common.exceptions.login.InvalidUserIdException;
import com.jaw.common.exceptions.login.UserDisabledException;
import com.jaw.user.controller.UserVO;

public interface IRequestService {

	void insertRequest(UserVO uivo) throws InvalidUserIdException,
			DuplicateEntryException, RequestNotAcceptedException,
			DatabaseException, UpdateFailedException, UserDisabledException;
}
