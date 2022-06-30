package com.jaw.core.dao;

import java.io.Serializable;

import org.apache.log4j.Logger;

import com.jaw.common.constants.AuditConstant;
//TeacherSubjectLink Pojo class
public class TeacherSubjectLink implements Serializable{
	// Logging
		 Logger logger = Logger.getLogger(TeacherSubjectLink.class);
		 
		// Properties
		private Integer dbTs;
		private String instId;
		private String branchId;
		private String trslId ;
		private String staffId ;
		private String subId= "" ;	
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
		public String getTrslId() {
			return trslId;
		}
		public void setTrslId(String trslId) {
			this.trslId = trslId;
		}
		public String getStaffId() {
			return staffId;
		}
		public void setStaffId(String staffId) {
			this.staffId = staffId;
		}
		public String getSubId() {
			return subId;
		}
		public void setSubId(String subId) {
			this.subId = subId;
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
			return "TeacherSubjectLink [dbTs=" + dbTs + ", instId=" + instId
					+ ", branchId=" + branchId + ", trslId=" + trslId
					+ ", staffId=" + staffId + ", subId=" + subId
					 + ", delFlag=" + delFlag
					+ ", rModId=" + rModId + ", rModTime=" + rModTime
					+ ", rCreId=" + rCreId + ", rCreTime=" + rCreTime + "]";
		}
		
		// method for create Course Master Record for Audit
		public String toStringForAuditTeacherSubLinkRecord() {
			StringBuffer stringBuffer = new StringBuffer()
			.append("DB_TS=").append(getDbTs()).append(AuditConstant.AUDIT_REC_SEPERATOR)
			.append("INST_ID=").append(getInstId()).append(AuditConstant.AUDIT_REC_SEPERATOR)
			.append("BRANCH_ID=").append(getBranchId()).append(AuditConstant.AUDIT_REC_SEPERATOR)
			.append("TRSL_ID=").append(getTrslId()).append(AuditConstant.AUDIT_REC_SEPERATOR)
			.append("STAFF_ID=").append(getStaffId()).append(AuditConstant.AUDIT_REC_SEPERATOR)
			.append("SUB_ID=").append(getSubId()).append(AuditConstant.AUDIT_REC_SEPERATOR)					
			.append("DEL_FLG=").append(getDelFlag()).append(AuditConstant.AUDIT_REC_SEPERATOR)
			.append("R_MOD_ID=").append(getrModId()).append(AuditConstant.AUDIT_REC_SEPERATOR)
			.append("R_MOD_TIME=").append(getrModTime()).append(AuditConstant.AUDIT_REC_SEPERATOR)
			.append("R_CRE_ID=").append(getrCreId()).append(AuditConstant.AUDIT_REC_SEPERATOR)
			.append("R_CRE_TIME=").append(getrCreTime()).append(AuditConstant.AUDIT_REC_SEPERATOR);

			logger.debug("String formed for audit is :" + stringBuffer.toString());

			return stringBuffer.toString();
		}
}
