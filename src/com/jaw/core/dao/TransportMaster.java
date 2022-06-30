package com.jaw.core.dao;

import java.io.Serializable;

import org.apache.log4j.Logger;

import com.jaw.common.constants.AuditConstant;
//CourseMaster Pojo class
public class TransportMaster implements Serializable{
	// Logging
			 Logger logger = Logger.getLogger(TransportMaster.class);
			  
			// Properties
			private Integer dbTs;
			private String instId;
			private String branchId;
			private String academicYear ;
			private String pickupPointId = "";
			private String pickupPointName = "";
			private double amount ;		
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
			public String getAcademicYear() {
				return academicYear;
			}
			public void setAcademicYear(String academicYear) {
				this.academicYear = academicYear;
			}
			public String getPickupPointId() {
				return pickupPointId;
			}
			public void setPickupPointId(String pickupPointId) {
				this.pickupPointId = pickupPointId;
			}
			public String getPickupPointName() {
				return pickupPointName;
			}
			public void setPickupPointName(String pickupPointName) {
				this.pickupPointName = pickupPointName;
			}
			public double getAmount() {
				return amount;
			}
			public void setAmount(double amount) {
				this.amount = amount;
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
				return "TransportMaster [logger=" + logger + ", dbTs=" + dbTs
						+ ", instId=" + instId + ", branchId=" + branchId
						+ ", academicYear=" + academicYear + ", pickupPointId="
						+ pickupPointId + ", pickupPointName="
						+ pickupPointName + ", amount=" + amount + ", delFlag="
						+ delFlag + ", rModId=" + rModId + ", rModTime="
						+ rModTime + ", rCreId=" + rCreId + ", rCreTime="
						+ rCreTime + "]";
			}
			// method for create Course Master Record for Audit
			public String toStringForAuditTransportMasterRecord() {
				
				StringBuffer stringBuffer = new StringBuffer()
				.append("DB_TS=").append(getDbTs()).append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("INST_ID=").append(getInstId()).append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("BRANCH_ID=").append(getBranchId()).append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("ACADEMIC_YEAR=").append(getAcademicYear()).append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("PICKUP_POINT_ID=").append(getPickupPointId()).append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("PICKUP_POINT=").append(getPickupPointName()).append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("AMOUNT=").append(getAmount()).append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("DEL_FLG=").append(getDelFlag()).append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("R_MOD_ID=").append(getrModId()).append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("R_MOD_TIME=").append(getrModTime()).append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("R_CRE_ID=").append(getrCreId()).append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("R_CRE_TIME=").append(getrCreTime()).append(AuditConstant.AUDIT_REC_SEPERATOR);

				logger.debug("String formed for audit is :" + stringBuffer.toString());

				return stringBuffer.toString();
			}

			
}
