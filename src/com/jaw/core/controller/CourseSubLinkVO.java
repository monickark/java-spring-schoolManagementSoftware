package com.jaw.core.controller;

import org.apache.log4j.Logger;

public class CourseSubLinkVO {
	// Logging
	Logger logger = Logger.getLogger(CourseSubLinkVO.class);

	private String instId;
	private String branchId;
	private String crslId;
	private String courseMasterId;
	private String termId = "";
	private String subId;
	private String subType = "";
	private Integer optSubOrder;
	private String optSubId = "";
	private String usedOnlyForTT = "";
	private String markGrade;
	private String incForMarkTotal = "";
	private String incForAttCal = "";
	private String requiresLab = "";

	private String requiresTeacher = "";
	private Integer reportCardOrder = 0;

	public String getRequiresTeacher() {
		return requiresTeacher;
	}

	public void setRequiresTeacher(String requiresTeacher) {
		this.requiresTeacher = requiresTeacher;
	}

	public Integer getReportCardOrder() {
		return reportCardOrder;
	}

	public void setReportCardOrder(Integer reportCardOrder) {
		this.reportCardOrder = reportCardOrder;
	}

	private String subName = "";

	public String getSubName() {
		return subName;
	}

	public void setSubName(String subName) {
		this.subName = subName;
	}

	public String getRequiresLab() {
		return requiresLab;
	}

	public void setRequiresLab(String requiresLab) {
		this.requiresLab = requiresLab;
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

	public String getCrslId() {
		return crslId;
	}

	public void setCrslId(String crslId) {
		this.crslId = crslId;
	}

	public Integer getOptSubOrder() {
		return optSubOrder;
	}

	public void setOptSubOrder(Integer optSubOrder) {
		this.optSubOrder = optSubOrder;
	}

	public String getOptSubId() {
		return optSubId;
	}

	public void setOptSubId(String optSubId) {
		this.optSubId = optSubId;
	}

	public String getUsedOnlyForTT() {
		return usedOnlyForTT;
	}

	public void setUsedOnlyForTT(String usedOnlyForTT) {
		this.usedOnlyForTT = usedOnlyForTT;
	}

	public String getCourseMasterId() {
		return courseMasterId;
	}

	public void setCourseMasterId(String courseMasterId) {
		this.courseMasterId = courseMasterId;
	}

	public String getTermId() {
		return termId;
	}

	public void setTermId(String termId) {
		this.termId = termId;
	}

	public String getSubId() {
		return subId;
	}

	public void setSubId(String subId) {
		this.subId = subId;
	}

	public String getSubType() {
		return subType;
	}

	public void setSubType(String subType) {
		this.subType = subType;
	}

	public String getMarkGrade() {
		return markGrade;
	}

	public void setMarkGrade(String markGrade) {
		this.markGrade = markGrade;
	}

	public String getIncForMarkTotal() {
		return incForMarkTotal;
	}

	public void setIncForMarkTotal(String incForMarkTotal) {
		this.incForMarkTotal = incForMarkTotal;
	}

	public String getIncForAttCal() {
		return incForAttCal;
	}

	public void setIncForAttCal(String incForAttCal) {
		this.incForAttCal = incForAttCal;
	}

	@Override
	public String toString() {
		return "CourseSubLinkVO [logger=" + logger + ", instId=" + instId
				+ ", branchId=" + branchId + ", crslId=" + crslId
				+ ", courseMasterId=" + courseMasterId + ", termId=" + termId
				+ ", subId=" + subId + ", subType=" + subType
				+ ", optSubOrder=" + optSubOrder + ", optSubId=" + optSubId
				+ ", usedOnlyForTT=" + usedOnlyForTT + ", markGrade="
				+ markGrade + ", incForMarkTotal=" + incForMarkTotal
				+ ", incForAttCal=" + incForAttCal + ", requiresLab="
				+ requiresLab + ", requiresTeacher=" + requiresTeacher
				+ ", reportCardOrder=" + reportCardOrder + ", subName="
				+ subName + "]";
	}

}
