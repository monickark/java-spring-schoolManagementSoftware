package com.jaw.core.controller;

import java.io.Serializable;

import com.jaw.staff.controller.StaffMasterVo;

public class ClassTeacherAllotmentVO implements Serializable {

	private String acTerm;
	private String staffId;
	private String stGroup;
	private String courseId;
	private String isBatchInclude;
	private String isAuditRequired = "Y";
	private String stBatchId;
	private StaffMasterVo staffMasterVO;
	private String pageSize = "10";
	private String acTermSts = "";

	public String getAcTermSts() {
		return acTermSts;
	}

	public void setAcTermSts(String acTermSts) {
		this.acTermSts = acTermSts;
	}

	public String getPageSize() {
		return pageSize;
	}

	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}

	public String getIsAuditRequired() {
		return isAuditRequired;
	}

	public void setIsAuditRequired(String isAuditRequired) {
		this.isAuditRequired = isAuditRequired;
	}

	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

	public String getIsBatchInclude() {
		return isBatchInclude;
	}

	public void setIsBatchInclude(String isBatchInclude) {
		this.isBatchInclude = isBatchInclude;
	}

	public String getStBatchId() {
		return stBatchId;
	}

	public void setStBatchId(String stBatchId) {
		this.stBatchId = stBatchId;
	}

	public String getStGroup() {
		return stGroup;
	}

	public void setStGroup(String stGroup) {
		this.stGroup = stGroup;
	}

	public String getAcTerm() {
		return acTerm;
	}

	public void setAcTerm(String acTerm) {
		this.acTerm = acTerm;
	}

	public String getStaffId() {
		return staffId;
	}

	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}

	public StaffMasterVo getStaffMasterVO() {
		return staffMasterVO;
	}

	public void setStaffMasterVO(StaffMasterVo staffMasterVO) {
		this.staffMasterVO = staffMasterVO;
	}

	@Override
	public String toString() {
		return "ClassTeacherAllotmentVO [acTerm=" + acTerm + ", staffId="
				+ staffId + ", stGroup=" + stGroup + ", courseId=" + courseId
				+ ", isBatchInclude=" + isBatchInclude + ", isAuditRequired="
				+ isAuditRequired + ", stBatchId=" + stBatchId
				+ ", staffMasterVO=" + staffMasterVO + "]";
	}

}
