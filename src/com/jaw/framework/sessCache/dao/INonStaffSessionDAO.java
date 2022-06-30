package com.jaw.framework.sessCache.dao;

import com.jaw.common.exceptions.login.SessionCacheNotLoadedException;
import com.jaw.framework.sessCache.NonStaffSession;

public interface INonStaffSessionDAO {
	
	NonStaffSession selectNonStaffRec(String staffId, String instId, String branchId)
			throws SessionCacheNotLoadedException;
	
}
