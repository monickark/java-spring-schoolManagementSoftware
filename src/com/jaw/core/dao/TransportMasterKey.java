package com.jaw.core.dao;

import java.io.Serializable;

import org.apache.log4j.Logger;

import com.jaw.common.constants.AuditConstant;

public class TransportMasterKey implements Serializable{
	// Logging
	 Logger logger = Logger.getLogger(TransportMasterKey.class);
	  
	// Properties
	private Integer dbTs;
	private String instId;
	private String branchId;
	private String academicYear ;
	private String pickupPointId = "";
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
		// method for create TransportMasterKey String for Audit
		public String toStringForAuditTransportMasterKey() {

			StringBuffer stringBuffer = new StringBuffer()
			.append("DB_TS=").append(getDbTs()).append(AuditConstant.AUDIT_REC_SEPERATOR)
			.append("INST_ID=").append(getInstId()).append(AuditConstant.AUDIT_REC_SEPERATOR)
			.append("BRANCH_ID=").append(getBranchId()).append(AuditConstant.AUDIT_REC_SEPERATOR)
			.append("ACADEMIC_YEAR=").append(getAcademicYear()).append(AuditConstant.AUDIT_REC_SEPERATOR)
			.append("PICKUP_POINT_ID").append(getPickupPointId()).append(AuditConstant.AUDIT_REC_SEPERATOR);
			return stringBuffer.toString();
		}
}
