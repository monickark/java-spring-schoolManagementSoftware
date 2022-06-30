package com.jaw.framework.sessCache;

import java.io.Serializable;

public class ManagementSession implements Serializable {

	private String managementId;
	private String managementName;

	public String getManagementId() {
		return managementId;
	}

	public void setManagementId(String managementId) {
		this.managementId = managementId;
	}

	public String getManagementName() {
		return managementName;
	}

	public void setManagementName(String managementName) {
		this.managementName = managementName;
	}

}
