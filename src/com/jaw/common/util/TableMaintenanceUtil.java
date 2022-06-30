package com.jaw.common.util;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.jaw.common.exceptions.util.TableNotSpecifiedForAuditException;
import com.jaw.framework.appCache.ApplicationCache;
import com.jaw.framework.appCache.dao.TableMaintenance;

@Component
public class TableMaintenanceUtil {
	Logger logger = Logger.getLogger(TableMaintenanceUtil.class);

	public String getPropertyValue(ApplicationCache applicationCache,
			String instid, String tableName)
			throws TableNotSpecifiedForAuditException {
		logger.debug("TableMaintenanceUtil values :Application cache :"
				+ applicationCache + "  Inst id :" + instid + "   table name :"
				+ tableName);
		for (TableMaintenance tableMaintenance : applicationCache
				.getTableMaintenances()) {

			if (tableMaintenance.getInstId().equals(instid)
					&& (tableMaintenance.getTableName().equals(tableName))) {
				logger.debug("Table maintenance retun value :"
						+ tableMaintenance.getAuditRequired());
				return tableMaintenance.getAuditRequired();
			}

		}
		logger.error("Table name :" + tableName
				+ " Not specified in tbpm table");
		throw new TableNotSpecifiedForAuditException();

	}

}
