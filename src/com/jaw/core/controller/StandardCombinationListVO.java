package com.jaw.core.controller;

import java.io.Serializable;

public class StandardCombinationListVO implements Serializable {
	private String standardId;
	private String combinationId;
	private String pageNo;
	private int rowId;

	public int getRowId() {
		return rowId;
	}

	public void setRowId(int rowId) {
		this.rowId = rowId;
	}

	public String getPageNo() {
		return pageNo;
	}

	public void setPageNo(String pageNo) {
		this.pageNo = pageNo;
	}

	/**
	 * @return the standardId
	 */
	public synchronized String getStandardId() {
		return standardId;
	}

	/**
	 * @param standardId
	 *            the standardId to set
	 */
	public synchronized void setStandardId(String standardId) {
		this.standardId = standardId;
	}

	/**
	 * @return the combinationId
	 */
	public synchronized String getCombinationId() {
		return combinationId;
	}

	/**
	 * @param combinationId
	 *            the combinationId to set
	 */
	public synchronized void setCombinationId(String combinationId) {
		this.combinationId = combinationId;
	}
	/**
	 * @return the extra
	 */
}
