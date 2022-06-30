package com.jaw.framework.sessCache;

import java.io.Serializable;

public class NonStaffSession implements Serializable {

	private String nonStaffId = "";
	private String name = "";

	public String getNonStaffId() {
		return nonStaffId;
	}

	public void setNonStaffId(String nonStaffId) {
		this.nonStaffId = nonStaffId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
