package com.jaw.common.util;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.jaw.common.exceptions.util.PropertyNotFoundException;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.appCache.dao.PropertyMaintenance;

@Component
public class PropertyManagementUtil {
	Logger logger = Logger.getLogger(PropertyManagementUtil.class);

	public String getPropertyValue(ApplicationCache applicationCache,
			String instid, String branchId, String code)
			throws PropertyNotFoundException {
		logger.debug("PropertyManagementUtil values :Application cache :"
				+ applicationCache + "  instid :" + instid + "  branchId :"
				+ branchId + "  code :" + code);
		for (PropertyMaintenance propertyMaintenance : applicationCache
				.getPropertyManagement()) {
			if (propertyMaintenance.getInstId().equals(instid)
					&& (propertyMaintenance.getBranchId().equals(branchId))
					&& (propertyMaintenance.getPropertyId().equals(code))) {
				return propertyMaintenance.getPropertyValue();
			}

		}
		logger.error("No value found for " + applicationCache + "  instid :"
				+ instid + "  branchId :" + branchId + "  code :" + code);
		throw new PropertyNotFoundException();
	}

}
