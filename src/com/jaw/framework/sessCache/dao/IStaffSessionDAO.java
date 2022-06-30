package com.jaw.framework.sessCache.dao;

import com.jaw.common.exceptions.login.SessionCacheNotLoadedException;
import com.jaw.framework.sessCache.StaffSession;

public interface IStaffSessionDAO {
	StaffSession selectStaffSessionRec(String staffId, String instId, String branchId)
			throws SessionCacheNotLoadedException;
	
}
