package com.jaw.core.dao;

import java.io.Serializable;

import org.apache.log4j.Logger;

import com.jaw.common.constants.AuditConstant;

//CourseSubLink Pojo class
public class CourseSubLink implements Serializable {
	// Logging
	Logger logger = Logger.getLogger(CourseSubLink.class);
	
	// Properties
	private Integer dbTs;
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
	private String clsDuration = "1";
	private String requiresLab = "";
	private String requiresTeacher = "";
	private Integer reportCardOrder = 0;
	private Integer noOfConseClasses = 0;
	private Integer noOfClsesPerWeek = 0;
	private Integer noOfLabClassesPerWeek=0;
	private Integer ttAssignmentWithinSg = 0;
	private String delFlag = "";
	private String rModId = "";
	private String rModTime = "";
	private String rCreId = "";
	private String rCreTime = "";
	private String subName = "";

	
	
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

	public String getSubName() {
		return subName;
	}

	public void setSubName(String subName) {
		this.subName = subName;
	}

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
	
	public String getCrslId() {
		return crslId;
	}
	
	public void setCrslId(String crslId) {
		this.crslId = crslId;
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
	
	public String getClsDuration() {
		return clsDuration;
	}
	
	public void setClsDuration(String clsDuration) {
		this.clsDuration = clsDuration;
	}
	
	public String getRequiresLab() {
		return requiresLab;
	}
	
	public void setRequiresLab(String requiresLab) {
		this.requiresLab = requiresLab;
	}
	
	public Integer getNoOfConseClasses() {
		return noOfConseClasses;
	}
	
	public void setNoOfConseClasses(Integer noOfConseClasses) {
		this.noOfConseClasses = noOfConseClasses;
	}
	
	public Integer getNoOfClsesPerWeek() {
		return noOfClsesPerWeek;
	}
	
	public void setNoOfClsesPerWeek(Integer noOfClsesPerWeek) {
		this.noOfClsesPerWeek = noOfClsesPerWeek;
	}
	
	public Integer getTtAssignmentWithinSg() {
		return ttAssignmentWithinSg;
	}
	
	public void setTtAssignmentWithinSg(Integer ttAssignmentWithinSg) {
		this.ttAssignmentWithinSg = ttAssignmentWithinSg;
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
	
	public Integer getNoOfLabClassesPerWeek() {
		return noOfLabClassesPerWeek;
	}

	public void setNoOfLabClassesPerWeek(Integer noOfLabClassesPerWeek) {
		this.noOfLabClassesPerWeek = noOfLabClassesPerWeek;
	}

	
	
	@Override
	public String toString() {
		return "CourseSubLink [logger=" + logger + ", dbTs=" + dbTs
				+ ", instId=" + instId + ", branchId=" + branchId + ", crslId="
				+ crslId + ", courseMasterId=" + courseMasterId + ", termId="
				+ termId + ", subId=" + subId + ", subType=" + subType
				+ ", optSubOrder=" + optSubOrder + ", optSubId=" + optSubId
				+ ", usedOnlyForTT=" + usedOnlyForTT + ", markGrade="
				+ markGrade + ", incForMarkTotal=" + incForMarkTotal
				+ ", incForAttCal=" + incForAttCal + ", clsDuration="
				+ clsDuration + ", requiresLab=" + requiresLab
				+ ", requiresTeacher=" + requiresTeacher + ", reportCardOrder="
				+ reportCardOrder + ", noOfConseClasses=" + noOfConseClasses
				+ ", noOfClsesPerWeek=" + noOfClsesPerWeek
				+ ", noOfLabClassesPerWeek=" + noOfLabClassesPerWeek
				+ ", ttAssignmentWithinSg=" + ttAssignmentWithinSg
				+ ", delFlag=" + delFlag + ", rModId=" + rModId + ", rModTime="
				+ rModTime + ", rCreId=" + rCreId + ", rCreTime=" + rCreTime
				+ ", subName=" + subName + "]";
	}

	public String stringForDbAudit() {
		StringBuffer result = new StringBuffer();
		result.append("DB_TS=").append(getDbTs())
				.append(AuditConstant.AUDIT_REC_SEPERATOR);
		result.append("INST_ID=").append(getInstId())
				.append(AuditConstant.AUDIT_REC_SEPERATOR);
		result.append("BRANCH_ID=").append(getBranchId())
				.append(AuditConstant.AUDIT_REC_SEPERATOR);
		result.append("CRSL_ID=").append(getCrslId())
				.append(AuditConstant.AUDIT_REC_SEPERATOR);
		result.append("COURSEMASTER_ID=").append(getCourseMasterId())
				.append(AuditConstant.AUDIT_REC_SEPERATOR);
		result.append("TERM_ID=").append(getTermId())
				.append(AuditConstant.AUDIT_REC_SEPERATOR);
		result.append("SUB_ID=").append(getSubId())
				.append(AuditConstant.AUDIT_REC_SEPERATOR);
		result.append("SUB_TYPE=").append(getSubType())
				.append(AuditConstant.AUDIT_REC_SEPERATOR);
		result.append("OPT_SUB_ORDER=").append(getOptSubOrder())
				.append(AuditConstant.AUDIT_REC_SEPERATOR);
		result.append("OPT_SUB_ID=").append(getOptSubOrder())
				.append(AuditConstant.AUDIT_REC_SEPERATOR);
		result.append("USED_ONLY_FOR_TT=").append(getUsedOnlyForTT())
				.append(AuditConstant.AUDIT_REC_SEPERATOR);
		result.append("MARK_GRADE=").append(getMarkGrade())
				.append(AuditConstant.AUDIT_REC_SEPERATOR);
		
		result.append("INC_FOR_MARK_TOTAL=").append(getIncForMarkTotal())
				.append(AuditConstant.AUDIT_REC_SEPERATOR);
		result.append("INC_FOR_ATT_CAL=").append(getIncForAttCal())
				.append(AuditConstant.AUDIT_REC_SEPERATOR);
		result.append("CLS_DURATION=").append(getClsDuration())
				.append(AuditConstant.AUDIT_REC_SEPERATOR);
		result.append("REQUIRES_LAB=").append(getRequiresLab())
				.append(AuditConstant.AUDIT_REC_SEPERATOR);
		result.append("NO_OF_CONSE_CLASSES=").append(getNoOfConseClasses())
				.append(AuditConstant.AUDIT_REC_SEPERATOR);
		result.append("NO_OF_LAB_CLSES_PER_WEEK=").append(getNoOfLabClassesPerWeek())
		.append(AuditConstant.AUDIT_REC_SEPERATOR);
		result.append("NO_OF_CLSES_PER_WEEK=").append(getNoOfClsesPerWeek())
				.append(AuditConstant.AUDIT_REC_SEPERATOR);
		result.append("TT_ASSIGNMENT_WITHIN_SG=").append(getTtAssignmentWithinSg())
				.append(AuditConstant.AUDIT_REC_SEPERATOR);
		result.append("delFlg=").append(getDelFlag())
				.append(AuditConstant.AUDIT_REC_SEPERATOR);
		result.append("R_CRE_ID=").append(getrCreId())
				.append(AuditConstant.AUDIT_REC_SEPERATOR);
		result.append("R_CRE_TIME=").append(getrCreTime())
				.append(AuditConstant.AUDIT_REC_SEPERATOR);
		result.append("R_MOD_ID=").append(getrModId())
				.append(AuditConstant.AUDIT_REC_SEPERATOR);
		result.append("R_MOD_TIME=").append(getrModTime())
				.append(AuditConstant.AUDIT_REC_SEPERATOR);
		return result.toString();
	}
	
}
