package com.jaw.framework.appCache.dao;

import java.io.Serializable;

public class TableMaintenance implements Serializable {

	private String instId;
	private String tableName;
	private String auditRequired = "";
	private String mandatoryAuditRequired = "";

	public String getInstId() {
		return instId;
	}

	public void setInstId(String instId) {
		this.instId = instId;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getAuditRequired() {
		return auditRequired;
	}

	public void setAuditRequired(String auditRequired) {
		this.auditRequired = auditRequired;
	}

	public String getMandatoryAuditRequired() {
		return mandatoryAuditRequired;
	}

	public void setMandatoryAuditRequired(String mandatoryAuditRequired) {
		this.mandatoryAuditRequired = mandatoryAuditRequired;
	}

}
