package com.jaw.core.controller;

import java.io.Serializable;
import java.util.ArrayList;

public class StandardCombinationVO implements Serializable {
	private String standardId;
	private String combinationId;
	private String pageNo;
	private String rowId;
	private ArrayList<StandardCombinationListVO> standardCombinationList;

	public String getPageNo() {
		return pageNo;
	}

	public void setPageNo(String pageNo) {
		this.pageNo = pageNo;
	}

	public String getRowId() {
		return rowId;
	}

	public void setRowId(String rowId) {
		this.rowId = rowId;
	}

	/**
	 * @return the standardId
	 */
	public String getStandardId() {
		return standardId;
	}

	/**
	 * @param standardId
	 *            the standardId to set
	 */
	public void setStandardId(String standardId) {
		this.standardId = standardId;
	}

	/**
	 * @return the combinationId
	 */
	public String getCombinationId() {
		return combinationId;
	}

	/**
	 * @param combinationId
	 *            the combinationId to set
	 */
	public void setCombinationId(String combinationId) {
		this.combinationId = combinationId;
	}

	/**
	 * @return the standardCombinationList
	 */
	public ArrayList<StandardCombinationListVO> getStandardCombinationList() {
		return standardCombinationList;
	}

	/**
	 * @param standardCombinationList
	 *            the standardCombinationList to set
	 */
	public void setStandardCombinationList(
			ArrayList<StandardCombinationListVO> standardCombinationList) {
		this.standardCombinationList = standardCombinationList;
	}

}
