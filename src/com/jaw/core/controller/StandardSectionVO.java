package com.jaw.core.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class StandardSectionVO implements Serializable {

	private Integer dbTs;
	private String instId;
	private String branchId;
	private String standard;
	private String combination;
	private String section;
	private String ttgId;
	private String sgId;
	private String academicYear;
	private String medium;
	private String isSingleMedium;

	private List<StandardSectionVO> standardSectionVOList;

	private ArrayList<String> stdlist;
	private ArrayList<String> seclist;
	private ArrayList<String> comblist;
	private List<List<String>> stdseclist;

	/**
	 * @return the dbTs
	 */
	public Integer getDbTs() {
		return dbTs;
	}

	/**
	 * @param dbTs
	 *            the dbTs to set
	 */
	public void setDbTs(Integer dbTs) {
		this.dbTs = dbTs;
	}

	/**
	 * @return the instId
	 */
	public String getInstId() {
		return instId;
	}

	/**
	 * @param instId
	 *            the instId to set
	 */
	public void setInstId(String instId) {
		this.instId = instId;
	}

	/**
	 * @return the branchId
	 */
	public String getBranchId() {
		return branchId;
	}

	/**
	 * @param branchId
	 *            the branchId to set
	 */
	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

	/**
	 * @return the standard
	 */
	public String getStandard() {
		return standard;
	}

	/**
	 * @param standard
	 *            the standard to set
	 */
	public void setStandard(String standard) {
		this.standard = standard;
	}

	/**
	 * @return the combination
	 */
	public String getCombination() {
		return combination;
	}

	/**
	 * @param combination
	 *            the combination to set
	 */
	public void setCombination(String combination) {
		this.combination = combination;
	}

	/**
	 * @return the section
	 */
	public String getSection() {
		return section;
	}

	/**
	 * @param section
	 *            the section to set
	 */
	public void setSection(String section) {
		this.section = section;
	}

	/**
	 * @return the ttgId
	 */
	public String getTtgId() {
		return ttgId;
	}

	/**
	 * @param ttgId
	 *            the ttgId to set
	 */
	public void setTtgId(String ttgId) {
		this.ttgId = ttgId;
	}

	/**
	 * @return the sgId
	 */
	public String getSgId() {
		return sgId;
	}

	/**
	 * @param sgId
	 *            the sgId to set
	 */
	public void setSgId(String sgId) {
		this.sgId = sgId;
	}

	/**
	 * @return the academicYear
	 */
	public String getAcademicYear() {
		return academicYear;
	}

	/**
	 * @param academicYear
	 *            the academicYear to set
	 */
	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
	}

	/**
	 * @return the medium
	 */
	public String getMedium() {
		return medium;
	}

	/**
	 * @param medium
	 *            the medium to set
	 */
	public void setMedium(String medium) {
		this.medium = medium;
	}

	/**
	 * @return the stdlist
	 */
	public ArrayList<String> getStdlist() {
		return stdlist;
	}

	/**
	 * @param stdlist
	 *            the stdlist to set
	 */
	public void setStdlist(ArrayList<String> stdlist) {
		this.stdlist = stdlist;
	}

	/**
	 * @return the seclist
	 */
	public ArrayList<String> getSeclist() {
		return seclist;
	}

	/**
	 * @param seclist
	 *            the seclist to set
	 */
	public void setSeclist(ArrayList<String> seclist) {
		this.seclist = seclist;
	}

	/**
	 * @return the comblist
	 */
	public ArrayList<String> getComblist() {
		return comblist;
	}

	/**
	 * @param comblist
	 *            the comblist to set
	 */
	public void setComblist(ArrayList<String> comblist) {
		this.comblist = comblist;
	}

	/**
	 * @return the stdseclist
	 */
	public List<List<String>> getStdseclist() {
		return stdseclist;
	}

	/**
	 * @param stdseclist
	 *            the stdseclist to set
	 */
	public void setStdseclist(List<List<String>> stdseclist) {
		this.stdseclist = stdseclist;
	}

	/**
	 * @return the isSingleMedium
	 */
	public String getIsSingleMedium() {
		return isSingleMedium;
	}

	/**
	 * @param isSingleMedium
	 *            the isSingleMedium to set
	 */
	public void setIsSingleMedium(String isSingleMedium) {
		this.isSingleMedium = isSingleMedium;
	}

	/**
	 * @return the standardSectionVOList
	 */
	public List<StandardSectionVO> getStandardSectionVOList() {
		return standardSectionVOList;
	}

	/**
	 * @param standardSectionVOList
	 *            the standardSectionVOList to set
	 */
	public void setStandardSectionVOList(
			List<StandardSectionVO> standardSectionVOList) {
		this.standardSectionVOList = standardSectionVOList;
	}

}
