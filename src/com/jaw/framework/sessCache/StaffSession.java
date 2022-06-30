package com.jaw.framework.sessCache;

import java.io.Serializable;

public class StaffSession implements Serializable {

	private String staffId;
	private String staffName;

	public String getStaffId() {
		return staffId;
	}

	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}

	public String getStaffName() {
		return staffName;
	}

	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}

}
