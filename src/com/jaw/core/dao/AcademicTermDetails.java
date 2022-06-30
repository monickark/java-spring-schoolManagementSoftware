package com.jaw.core.dao;

import java.io.Serializable;

import org.apache.log4j.Logger;

import com.jaw.common.constants.AuditConstant;

//AcademicTermDetails Pojo class
public class AcademicTermDetails implements Serializable{	 
	// Logging
	 Logger logger = Logger.getLogger(AcademicTermDetails.class);

	// Properties
	private Integer dbTs;
	private String instId;
	private String branchId;
	private String acYear;
	private String acTerm;
	private String termStartDate = "";
	private String termEndDate = "";
	private String promotionStatus ;
	private String promotionId = "";
	private String weeklyHoliday ;
	private String acTermSts;
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
	public String getAcYear() {
		return acYear;
	}
	public void setAcYear(String acYear) {
		this.acYear = acYear;
	}
	public String getAcTerm() {
		return acTerm;
	}
	public void setAcTerm(String acTerm) {
		this.acTerm = acTerm;
	}
	public String getTermStartDate() {
		return termStartDate;
	}
	public void setTermStartDate(String termStartDate) {
		this.termStartDate = termStartDate;
	}
	public String getTermEndDate() {
		return termEndDate;
	}
	public void setTermEndDate(String termEndDate) {
		this.termEndDate = termEndDate;
	}
	public String getWeeklyHoliday() {
		return weeklyHoliday;
	}
	public void setWeeklyHoliday(String weeklyHoliday) {
		this.weeklyHoliday = weeklyHoliday;
	}
	public String getAcTermSts() {
		return acTermSts;
	}
	public void setAcTermSts(String acTermSts) {
		this.acTermSts = acTermSts;
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
	public String getPromotionStatus() {
		return promotionStatus;
	}
	public void setPromotionStatus(String promotionStatus) {
		this.promotionStatus = promotionStatus;
	}
	public String getPromotionId() {
		return promotionId;
	}
	public void setPromotionId(String promotionId) {
		this.promotionId = promotionId;
	}
	
	
	@Override
	public String toString() {
		return "AcademicTermDetails [dbTs=" + dbTs + ", instId=" + instId
				+ ", branchId=" + branchId + ", acYear=" + acYear + ", acTerm="
				+ acTerm + ", termStartDate=" + termStartDate
				+ ", termEndDate=" + termEndDate + ", promotionStatus="
				+ promotionStatus + ", promotionId=" + promotionId
				+ ", weeklyHoliday=" + weeklyHoliday + ", acTermSts="
				+ acTermSts + ", delFlag=" + delFlag + ", rModId=" + rModId
				+ ", rModTime=" + rModTime + ", rCreId=" + rCreId
				+ ", rCreTime=" + rCreTime + "]";
	}
		// method for create Academic term Record for Audit  
		public String toStringForAuditAcademicTermRecord() {
			StringBuffer stringBuffer = new StringBuffer()
			.append("DB_TS=").append(getDbTs()).append(AuditConstant.AUDIT_REC_SEPERATOR)
			.append("INST_ID=").append(getInstId()).append(AuditConstant.AUDIT_REC_SEPERATOR)
			.append("BRANCH_ID=").append(getBranchId()).append(AuditConstant.AUDIT_REC_SEPERATOR)
			.append("AC_YEAR=").append(getAcYear()).append(AuditConstant.AUDIT_REC_SEPERATOR)
			.append("AC_TERM=").append(getAcTerm()).append(AuditConstant.AUDIT_REC_SEPERATOR)
			.append("TERM_START_DATE=").append(getTermStartDate()).append(AuditConstant.AUDIT_REC_SEPERATOR)
			.append("TERM_END_DATE=").append(getTermEndDate()).append(AuditConstant.AUDIT_REC_SEPERATOR)
			.append("WEEKLY_HOLIDAY=").append(getWeeklyHoliday()).append(AuditConstant.AUDIT_REC_SEPERATOR)
			.append("AC_TERM_STS=").append(getAcTermSts()).append(AuditConstant.AUDIT_REC_SEPERATOR)			
			.append("PROMO_PROC_STS=").append(getPromotionStatus()).append(AuditConstant.AUDIT_REC_SEPERATOR)
			.append("PROMO_PROC_STS_ID=").append(getPromotionId()).append(AuditConstant.AUDIT_REC_SEPERATOR)			
			.append("DEL_FLG=").append(getDelFlag()).append(AuditConstant.AUDIT_REC_SEPERATOR)
			.append("R_MOD_ID=").append(getrModId()).append(AuditConstant.AUDIT_REC_SEPERATOR)
			.append("R_MOD_TIME=").append(getrModTime()).append(AuditConstant.AUDIT_REC_SEPERATOR)
			.append("R_CRE_ID=").append(getrCreId()).append(AuditConstant.AUDIT_REC_SEPERATOR)
			.append("R_CRE_TIME=").append(getrCreTime()).append(AuditConstant.AUDIT_REC_SEPERATOR);

			logger.debug("String formed for audit is :" + stringBuffer.toString());

			return stringBuffer.toString();
		}
		
}
