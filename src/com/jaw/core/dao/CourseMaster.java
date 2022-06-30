package com.jaw.core.dao;

import java.io.Serializable;

import org.apache.log4j.Logger;

import com.jaw.common.constants.AuditConstant;
//CourseMaster Pojo class
public class CourseMaster implements Serializable{
	// Logging
			 Logger logger = Logger.getLogger(CourseMaster.class);
			  
			// Properties
			private Integer dbTs;
			private String instId;
			private String branchId;
			private String courseMasterId ;
			private String courseName = "";
			private String courseId = "";
			private String combBranchId = "";		
			private String courseGrp = "";
			private String cvAppcble = "";
			private String cvCatType = "";
			private String cvListType= "";
			private String affId = "";
			private String affDetails = "";		
			private String termApplcble = "";
			private String medium = "";		
			private String department = "";
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
			public String getCourseName() {
				return courseName;
			}
			public void setCourseName(String courseName) {
				this.courseName = courseName;
			}
			public String getCourseId() {
				return courseId;
			}
			public void setCourseId(String courseId) {
				this.courseId = courseId;
			}
			public String getCombBranchId() {
				return combBranchId;
			}
			public void setCombBranchId(String combBranchId) {
				this.combBranchId = combBranchId;
			}
			public String getCourseGrp() {
				return courseGrp;
			}
			public void setCourseGrp(String courseGrp) {
				this.courseGrp = courseGrp;
			}
			public String getCvAppcble() {
				return cvAppcble;
			}
			public void setCvAppcble(String cvAppcble) {
				this.cvAppcble = cvAppcble;
			}
			public String getCvCatType() {
				return cvCatType;
			}
			public void setCvCatType(String cvCatType) {
				this.cvCatType = cvCatType;
			}
			public String getCvListType() {
				return cvListType;
			}
			public void setCvListType(String cvListType) {
				this.cvListType = cvListType;
			}
			public String getAffId() {
				return affId;
			}
			public void setAffId(String affId) {
				this.affId = affId;
			}
			public String getAffDetails() {
				return affDetails;
			}
			public void setAffDetails(String affDetails) {
				this.affDetails = affDetails;
			}
			public String getTermApplcble() {
				return termApplcble;
			}
			public void setTermApplcble(String termApplcble) {
				this.termApplcble = termApplcble;
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
			public String getDepartment() {
				return department;
			}
			public void setDepartment(String department) {
				this.department = department;
			}
			
			
			@Override
			public String toString() {
				return "CourseMaster [dbTs=" + dbTs + ", instId=" + instId
						+ ", branchId=" + branchId + ", courseMasterId="
						+ courseMasterId + ", courseName=" + courseName
						+ ", courseId=" + courseId + ", combBranchId="
						+ combBranchId + ", courseGrp=" + courseGrp
						+ ", cvAppcble=" + cvAppcble + ", cvCatType="
						+ cvCatType + ", cvListType=" + cvListType + ", affId="
						+ affId + ", affDetails=" + affDetails
						+ ", termApplcble=" + termApplcble + ", medium="
						+ medium + ", department=" + department + ", delFlag="
						+ delFlag + ", rModId=" + rModId + ", rModTime="
						+ rModTime + ", rCreId=" + rCreId + ", rCreTime="
						+ rCreTime + "]";
			}
			// method for create Course Master Record for Audit
			public String toStringForAuditCourseMasterRecord() {
				
				StringBuffer stringBuffer = new StringBuffer()
				.append("DB_TS=").append(getDbTs()).append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("INST_ID=").append(getInstId()).append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("BRANCH_ID=").append(getBranchId()).append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("COURSEMASTER_ID=").append(getCourseMasterId()).append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("COURSE_NAME=").append(getCourseName()).append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("COURSE_ID=").append(getCourseId()).append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("COMB_BRNCH_ID=").append(getCombBranchId()).append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("COURSE_GRP=").append(getCourseGrp()).append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("CV_APPCBLE=").append(getCvAppcble()).append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("CV_CAT_TYPE=").append(getCvCatType()).append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("CV_LIST_TYPE=").append(getCvListType()).append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("AFF_ID=").append(getAffId()).append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("AFF_DETAILS=").append(getAffDetails()).append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("TERM_APPLCBLE=").append(getTermApplcble()).append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("MEDIUM=").append(getMedium()).append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("DEPT_ID=").append(getDepartment()).append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("DEL_FLG=").append(getDelFlag()).append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("R_MOD_ID=").append(getrModId()).append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("R_MOD_TIME=").append(getrModTime()).append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("R_CRE_ID=").append(getrCreId()).append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("R_CRE_TIME=").append(getrCreTime()).append(AuditConstant.AUDIT_REC_SEPERATOR);

				logger.debug("String formed for audit is :" + stringBuffer.toString());

				return stringBuffer.toString();
			}

			
}
