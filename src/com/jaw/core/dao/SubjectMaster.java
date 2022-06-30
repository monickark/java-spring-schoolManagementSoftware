package com.jaw.core.dao;

import java.io.Serializable;

import org.apache.log4j.Logger;

import com.jaw.common.constants.AuditConstant;

public class SubjectMaster implements Serializable {
	// Logging
	Logger logger = Logger.getLogger(SubjectMaster.class);	
	// Properties
	private Integer dbTs;
	private String instId;
	private String branchId;
	private String sub_Id;
	private String sub_Name;
	private String cust_Code= "";
	private String short_Code= "";
	private String is_Com = "";
	private String is_Elective = "";
	private String is_Lang = "";	
	private String is_rel = "";	
	private String isPreAcaSubject = "";	
	private String department = "";	
	private String courseName = "";	
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
	public String getSub_Id() {
		return sub_Id;
	}
	public void setSub_Id(String sub_Id) {
		this.sub_Id = sub_Id;
	}
	public String getSub_Name() {
		return sub_Name;
	}
	public void setSub_Name(String sub_Name) {
		this.sub_Name = sub_Name;
	}
	public String getCust_Code() {
		return cust_Code;
	}
	public void setCust_Code(String cust_Code) {
		this.cust_Code = cust_Code;
	}
	public String getShort_Code() {
		return short_Code;
	}
	public void setShort_Code(String short_Code) {
		this.short_Code = short_Code;
	}
	public String getIs_Com() {
		return is_Com;
	}
	public void setIs_Com(String is_Com) {
		this.is_Com = is_Com;
	}
	public String getIs_Elective() {
		return is_Elective;
	}
	public void setIs_Elective(String is_Elective) {
		this.is_Elective = is_Elective;
	}
	public String getIs_Lang() {
		return is_Lang;
	}
	public void setIs_Lang(String is_Lang) {
		this.is_Lang = is_Lang;
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
	public String getIs_rel() {
		return is_rel;
	}
	public void setIs_rel(String is_rel) {
		this.is_rel = is_rel;
	}
	
	public String getIsPreAcaSubject() {
		return isPreAcaSubject;
	}
	public void setIsPreAcaSubject(String isPreAcaSubject) {
		this.isPreAcaSubject = isPreAcaSubject;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	
		@Override
	public String toString() {
		return "SubjectMaster [dbTs=" + dbTs + ", instId=" + instId
				+ ", branchId=" + branchId + ", sub_Id=" + sub_Id
				+ ", sub_Name=" + sub_Name + ", cust_Code=" + cust_Code
				+ ", short_Code=" + short_Code + ", is_Com=" + is_Com
				+ ", is_Elective=" + is_Elective + ", is_Lang=" + is_Lang
				+ ", is_rel=" + is_rel + ", isPreAcaSubject=" + isPreAcaSubject
				+ ", department=" + department + ", courseName=" + courseName
				+ ", delFlag=" + delFlag + ", rModId=" + rModId + ", rModTime="
				+ rModTime + ", rCreId=" + rCreId + ", rCreTime=" + rCreTime
				+ "]";
	}
		
		// method for create Course Master Record for Audit
		public String toStringForAuditSubjectMasterRecord() {
			StringBuffer stringBuffer = new StringBuffer()		
			.append("DB_TS=").append(getDbTs()).append(AuditConstant.AUDIT_REC_SEPERATOR)
			.append("INST_ID=").append(getInstId()).append(AuditConstant.AUDIT_REC_SEPERATOR)
			.append("BRANCH_ID=").append(getBranchId()).append(AuditConstant.AUDIT_REC_SEPERATOR)
			.append("SUB_ID=").append(getSub_Id()).append(AuditConstant.AUDIT_REC_SEPERATOR)
			.append("SUB_NAME=").append(getSub_Name()).append(AuditConstant.AUDIT_REC_SEPERATOR)
			.append("CUST_CODE=").append(getCust_Code()).append(AuditConstant.AUDIT_REC_SEPERATOR)
			.append("SHORT_CODE=").append(getShort_Code()).append(AuditConstant.AUDIT_REC_SEPERATOR)
			.append("DEPT_ID=").append(getDepartment()).append(AuditConstant.AUDIT_REC_SEPERATOR)
			.append("COURSEMASTER_ID=").append(getCourseName()).append(AuditConstant.AUDIT_REC_SEPERATOR)
			.append("IS_COMP=").append(getIs_Com()).append(AuditConstant.AUDIT_REC_SEPERATOR)
			.append("IS_ELECTIVE=").append(getIs_Elective()).append(AuditConstant.AUDIT_REC_SEPERATOR)
			.append("IS_LANG=").append(getIs_Lang()).append(AuditConstant.AUDIT_REC_SEPERATOR)		
			.append("IS_REL=").append(getIs_rel()).append(AuditConstant.AUDIT_REC_SEPERATOR)
			.append("IS_PRE_ACA_SUB=").append(getIsPreAcaSubject()).append(AuditConstant.AUDIT_REC_SEPERATOR)	
			.append("DEL_FLG=").append(getDelFlag()).append(AuditConstant.AUDIT_REC_SEPERATOR)
			.append("R_MOD_ID=").append(getrModId()).append(AuditConstant.AUDIT_REC_SEPERATOR)
			.append("R_MOD_TIME=").append(getrModTime()).append(AuditConstant.AUDIT_REC_SEPERATOR)
			.append("R_CRE_ID=").append(getrCreId()).append(AuditConstant.AUDIT_REC_SEPERATOR)
			.append("R_CRE_TIME=").append(getrCreTime()).append(AuditConstant.AUDIT_REC_SEPERATOR);

			logger.debug("String formed for audit is :" + stringBuffer.toString());

			return stringBuffer.toString();
		}
}