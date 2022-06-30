package com.jaw.core.dao;

import java.io.Serializable;

import org.apache.log4j.Logger;

import com.jaw.common.constants.AuditConstant;
//StudentGrpMaster Pojo class
public class StudentGroupMaster implements Serializable{
	// Logging
	 Logger logger = Logger.getLogger(StudentGroupMaster.class);
	 
	// Properties
	private Integer dbTs;
	private String instId;
	private String branchId;
	private String courseMasterId ;
	private String studentGrpId ;
	private String studentGrp ;		
	private String termId = "" ;
	private String secId = "" ;
	private String ttgId  = "" ;
	private String ttgProcess = "";
	private int ttgAssignmentOrder  ;
	private String medium = "";	
	private String delFlag = "";
	private String rModId = "";
	private String rModTime = "";
	private String rCreId = "";
	private String rCreTime = "";
	public Integer getDbTs() {
		return dbTs;
	}
	public void setDbTs(Integer dbTs) {
		this.dbTs = dbTs;
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
	public String getCourseMasterId() {
		return courseMasterId;
	}
	public void setCourseMasterId(String courseMasterId) {
		this.courseMasterId = courseMasterId;
	}
	public String getStudentGrpId() {
		return studentGrpId;
	}
	public void setStudentGrpId(String studentGrpId) {
		this.studentGrpId = studentGrpId;
	}
	public String getStudentGrp() {
		return studentGrp;
	}
	public void setStudentGrp(String studentGrp) {
		this.studentGrp = studentGrp;
	}
	public String getTermId() {
		return termId;
	}
	public void setTermId(String termId) {
		this.termId = termId;
	}
	public String getSecId() {
		return secId;
	}
	public void setSecId(String secId) {
		this.secId = secId;
	}
	public String getTtgId() {
		return ttgId;
	}
	public void setTtgId(String ttgId) {
		this.ttgId = ttgId;
	}
	public String getTtgProcess() {
		return ttgProcess;
	}
	public void setTtgProcess(String ttgProcess) {
		this.ttgProcess = ttgProcess;
	}
	public int getTtgAssignmentOrder() {
		return ttgAssignmentOrder;
	}
	public void setTtgAssignmentOrder(int ttgAssignmentOrder) {
		this.ttgAssignmentOrder = ttgAssignmentOrder;
	}
	public String getMedium() {
		return medium;
	}
	public void setMedium(String medium) {
		this.medium = medium;
	}
	public String getDelFlag() {
		return delFlag;
	}
	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}
	public String getrModId() {
		return rModId;
	}
	public void setrModId(String rModId) {
		this.rModId = rModId;
	}
	public String getrModTime() {
		return rModTime;
	}
	public void setrModTime(String rModTime) {
		this.rModTime = rModTime;
	}
	public String getrCreId() {
		return rCreId;
	}
	public void setrCreId(String rCreId) {
		this.rCreId = rCreId;
	}
	public String getrCreTime() {
		return rCreTime;
	}
	public void setrCreTime(String rCreTime) {
		this.rCreTime = rCreTime;
	}
	@Override
	public String toString() {
		return "StudentGrpMaster [dbTs=" + dbTs + ", instId=" + instId
				+ ", branchId=" + branchId + ", courseMasterId="
				+ courseMasterId + ", studentGrpId=" + studentGrpId
				+ ", studentGrp=" + studentGrp + ", termId=" + termId
				+ ", secId=" + secId + ", ttgId=" + ttgId + ", ttgProcess="
				+ ttgProcess + ", ttgAssignmentOrder=" + ttgAssignmentOrder
				+ ", medium=" + medium + ", delFlag=" + delFlag + ", rModId="
				+ rModId + ", rModTime=" + rModTime + ", rCreId=" + rCreId
				+ ", rCreTime=" + rCreTime + "]";
	}
	
	// method for create Course Master Record for Audit
				public String toStringForAuditStudentGroupMasterRecord() {
					StringBuffer stringBuffer = new StringBuffer()
					.append("DB_TS=").append(getDbTs()).append(AuditConstant.AUDIT_REC_SEPERATOR)
					.append("INST_ID=").append(getInstId()).append(AuditConstant.AUDIT_REC_SEPERATOR)
					.append("BRANCH_ID=").append(getBranchId()).append(AuditConstant.AUDIT_REC_SEPERATOR)
					.append("COURSEMASTER_ID=").append(getCourseMasterId()).append(AuditConstant.AUDIT_REC_SEPERATOR)
					.append("STUDENTGRP_ID=").append(getStudentGrpId()).append(AuditConstant.AUDIT_REC_SEPERATOR)
					.append("STUDENT_GRP=").append(getStudentGrp()).append(AuditConstant.AUDIT_REC_SEPERATOR)
					.append("TERM_ID=").append(getTermId()).append(AuditConstant.AUDIT_REC_SEPERATOR)
					.append("SEC_ID=").append(getSecId()).append(AuditConstant.AUDIT_REC_SEPERATOR)
					.append("TTG_ID=").append(getTtgId()).append(AuditConstant.AUDIT_REC_SEPERATOR)
					.append("TTG_PROCESS=").append(getTtgProcess()).append(AuditConstant.AUDIT_REC_SEPERATOR)
					.append("TTG_ASSIGNMENT_ORDER=").append(getTtgAssignmentOrder() ).append(AuditConstant.AUDIT_REC_SEPERATOR)
					.append("MEDIUM=").append(getMedium()).append(AuditConstant.AUDIT_REC_SEPERATOR)									
					.append("DEL_FLG=").append(getDelFlag()).append(AuditConstant.AUDIT_REC_SEPERATOR)
					.append("R_MOD_ID=").append(getrModId()).append(AuditConstant.AUDIT_REC_SEPERATOR)
					.append("R_MOD_TIME=").append(getrModTime()).append(AuditConstant.AUDIT_REC_SEPERATOR)
					.append("R_CRE_ID=").append(getrCreId()).append(AuditConstant.AUDIT_REC_SEPERATOR)
					.append("R_CRE_TIME=").append(getrCreTime()).append(AuditConstant.AUDIT_REC_SEPERATOR);

					logger.debug("String formed for audit is :" + stringBuffer.toString());

					return stringBuffer.toString();
				}
}
