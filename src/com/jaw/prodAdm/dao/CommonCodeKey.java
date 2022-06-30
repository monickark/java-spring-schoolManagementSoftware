package com.jaw.prodAdm.dao;

import java.io.Serializable;

public class CommonCodeKey implements Serializable {
	private String instId;
	private String branchId;
	private String codeType;
	private String commonCode;
	private String delFlg = "";

	public String getDelFlg() {
		return delFlg;
	}

	public void setDelFlg(String delFlg) {
		this.delFlg = delFlg;
	}

	public String getCodeType() {
		return codeType;
	}

	public void setCodeType(String codeType) {
		this.codeType = codeType;
	}

	/**
	 * @return the commonCode
	 */
	public String getCommonCode() {
		return commonCode;
	}

	/**
	 * @param commonCode
	 *            the commonCode to set
	 */
	public void setCommonCode(String commonCode) {
		this.commonCode = commonCode;
	}

	public String getInstId() {
		return instId;
	}

	public void setInstId(String instId) {
		this.instId = instId;
	}

	public String getBranchId() {
		return branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

}
