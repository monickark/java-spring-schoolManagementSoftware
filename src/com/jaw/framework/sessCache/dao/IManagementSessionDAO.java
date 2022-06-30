package com.jaw.framework.sessCache.dao;

import com.jaw.common.exceptions.login.SessionCacheNotLoadedException;
import com.jaw.framework.sessCache.ManagementSession;

public interface IManagementSessionDAO {
	
	ManagementSession selectManagementSessionRec(String managementId, String instId, String branchId)
			throws SessionCacheNotLoadedException;
	
}
