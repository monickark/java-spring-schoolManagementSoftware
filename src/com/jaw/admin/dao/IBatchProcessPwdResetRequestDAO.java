package com.jaw.admin.dao;

import java.util.List;

import com.jaw.common.exceptions.BatchUpdateFailedException;
import com.jaw.user.dao.User;

public interface IBatchProcessPwdResetRequestDAO {
	public void updateBatch(final List<User> batchup) throws BatchUpdateFailedException ;
}
