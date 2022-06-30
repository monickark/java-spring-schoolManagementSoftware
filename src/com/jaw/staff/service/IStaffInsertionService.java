package com.jaw.staff.service;

import java.io.IOException;

import javax.servlet.ServletContext;

import com.jaw.common.exceptions.DatabaseException;
import com.jaw.common.exceptions.DeleteFailedException;
import com.jaw.common.exceptions.DuplicateEntryException;
import com.jaw.common.exceptions.FileNotFoundInDatabase;
import com.jaw.common.exceptions.FileNotSaveException;
import com.jaw.common.exceptions.NoDataFoundException;
import com.jaw.common.exceptions.util.PropertyNotFoundException;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.sessCache.UserSessionDetails;
import com.jaw.staff.controller.StaffVo;

public interface IStaffInsertionService {

	public void insertStaff(StaffVo staffAdmissionVo,
			UserSessionDetails userSessionDetails,
			ApplicationCache applicationCache,ServletContext servletContext) throws DuplicateEntryException,
			FileNotFoundInDatabase, DatabaseException, NumberFormatException,
			PropertyNotFoundException, IllegalStateException, IOException, DeleteFailedException, FileNotSaveException;

	void selectStaff(StaffVo staffAdmissionVo,
			UserSessionDetails userSessionDetails)
			throws FileNotFoundInDatabase, NoDataFoundException;
}
