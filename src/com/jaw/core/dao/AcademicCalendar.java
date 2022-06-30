package com.jaw.core.dao;
import java.io.Serializable;

import org.apache.log4j.Logger;

import com.jaw.common.constants.AuditConstant;
//AcademicCalendar Pojo class
public class AcademicCalendar implements Serializable{

	// Logging
	 Logger logger = Logger.getLogger(AcademicCalendar.class);

	// Properties
	private Integer dbTs;
	private String instId;
	private String branchId;
	private String acItemId;
	private String acTerm ;
	private String itemStartDate;
	private String itemEndDate;
	private String itemType ;
	private String itemDesc ;
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
	public String getAcItemId() {
		return acItemId;
	}
	public void setAcItemId(String acItemId) {
		this.acItemId = acItemId;
	}
	public String getAcTerm() {
		return acTerm;
	}
	public void setAcTerm(String acTerm) {
		this.acTerm = acTerm;
	}
	public String getItemStartDate() {
		return itemStartDate;
	}
	public void setItemStartDate(String itemStartDate) {
		this.itemStartDate = itemStartDate;
	}
	public String getItemEndDate() {
		return itemEndDate;
	}
	public void setItemEndDate(String itemEndDate) {
		this.itemEndDate = itemEndDate;
	}
	public String getItemType() {
		return itemType;
	}
	public void setItemType(String itemType) {
		this.itemType = itemType;
	}
	public String getItemDesc() {
		return itemDesc;
	}
	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
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
		return "AcademicCalendar [dbTs=" + dbTs + ", instId=" + instId
				+ ", branchId=" + branchId + ", acItemId=" + acItemId
				+ ", acTerm=" + acTerm + ", itemStartDate=" + itemStartDate
				+ ", itemEndDate=" + itemEndDate + ", itemType=" + itemType
				+ ", itemDesc=" + itemDesc + ", delFlag=" + delFlag
				+ ", rModId=" + rModId + ", rModTime=" + rModTime + ", rCreId="
				+ rCreId + ", rCreTime=" + rCreTime + "]";
	}
	// method for create Academic Calendar Record for Audit
	public String toStringForAuditAcademicCalendarRecord() {
		StringBuffer stringBuffer = new StringBuffer()
		
		.append("DB_TS=").append(getDbTs()).append(AuditConstant.AUDIT_REC_SEPERATOR)
		.append("INST_ID=").append(getInstId()).append(AuditConstant.AUDIT_REC_SEPERATOR)
		.append("BRANCH_ID=").append(getBranchId()).append(AuditConstant.AUDIT_REC_SEPERATOR)
		.append("AC_ITEM_ID=").append(getAcItemId()).append(AuditConstant.AUDIT_REC_SEPERATOR)
		.append("AC_TERM=").append(getAcTerm()).append(AuditConstant.AUDIT_REC_SEPERATOR)
		.append("ITEM_START_DATE=").append(getItemStartDate()).append(AuditConstant.AUDIT_REC_SEPERATOR)
		.append("ITEM_END_DATE=").append(getItemEndDate()).append(AuditConstant.AUDIT_REC_SEPERATOR)
		.append("ITEM_TYPE=").append(getItemType()).append(AuditConstant.AUDIT_REC_SEPERATOR)
		.append("ITEM_DESC=").append(getItemDesc()).append(AuditConstant.AUDIT_REC_SEPERATOR)							
		.append("DEL_FLG=").append(getDelFlag()).append(AuditConstant.AUDIT_REC_SEPERATOR)
		.append("R_MOD_ID=").append(getrModId()).append(AuditConstant.AUDIT_REC_SEPERATOR)
		.append("R_MOD_TIME=").append(getrModTime()).append(AuditConstant.AUDIT_REC_SEPERATOR)
		.append("R_CRE_ID=").append(getrCreId()).append(AuditConstant.AUDIT_REC_SEPERATOR)
		.append("R_CRE_TIME=").append(getrCreTime()).append(AuditConstant.AUDIT_REC_SEPERATOR);

		logger.debug("String formed for audit is :" + stringBuffer.toString());

		return stringBuffer.toString();
	}
	
}
