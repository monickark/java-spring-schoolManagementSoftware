package com.jaw.core.dao;

import org.apache.log4j.Logger;
import java.io.Serializable;
import com.jaw.common.constants.AuditConstant;

public class TransportDetails implements Serializable{
	
	Logger logger=Logger.getLogger(TransportDetails.class);
	private Integer dbTs;
	private String instId;
	private String branchId;
	private String studentAdmisNo;
	private String academicYear;
	private String modeOfTransport = "";
	private String vehicleNo = "";
	private String pickupPoint = "";
	private String droppingPoint = "";
	private String pickupAssSalut = "";
	private String pickupAssName = "";
	private String delFlg = "";
	private String rModId = "";
	private String rModTime;
	private String rCreId = "";
	private String rCreTime;
	
	
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
	public String getStudentAdmisNo() {
		return studentAdmisNo;
	}
	public void setStudentAdmisNo(String studentAdmisNo) {
		this.studentAdmisNo = studentAdmisNo;
	}
	public String getAcademicYear() {
		return academicYear;
	}
	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
	}
	public String getDelFlg() {
		return delFlg;
	}
	public void setDelFlg(String delFlg) {
		this.delFlg = delFlg;
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
	public String getModeOfTransport() {
		return modeOfTransport;
	}
	public void setModeOfTransport(String modeOfTransport) {
		this.modeOfTransport = modeOfTransport;
	}
	public String getVehicleNo() {
		return vehicleNo;
	}
	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}
	public String getPickupPoint() {
		return pickupPoint;
	}
	public void setPickupPoint(String pickupPoint) {
		this.pickupPoint = pickupPoint;
	}
	public String getDroppingPoint() {
		return droppingPoint;
	}
	public void setDroppingPoint(String droppingPoint) {
		this.droppingPoint = droppingPoint;
	}
	public String getPickupAssSalut() {
		return pickupAssSalut;
	}
	public void setPickupAssSalut(String pickupAssSalut) {
		this.pickupAssSalut = pickupAssSalut;
	}
	public String getPickupAssName() {
		return pickupAssName;
	}
	public void setPickupAssName(String pickupAssName) {
		this.pickupAssName = pickupAssName;
	}
	
	// method for create InstituteMaster Record for Audit
			public  String toStringForAuditInstMasterRecord()
			{
				
				StringBuffer  stringBuffer=new StringBuffer()		
				.append("DB_TS=")
				.append(getDbTs())
				.append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("INST_ID=")
				.append(getInstId())
				.append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("BRANCH_ID=")
				.append(getBranchId())
				.append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("STUDENT_ADMIS_NO=")
				.append(getStudentAdmisNo())
				.append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("ACADEMIC_YEAR=")
				.append(getAcademicYear())
				.append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("MODE_OF_TRANS=")
				.append(getModeOfTransport())
				.append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("VEHICLE_NO=")
				.append(getVehicleNo())
				.append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("PICKUP_POINT=")
				.append(getPickupPoint())
				.append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("DROP_POINT=")
				.append(getDroppingPoint())
				.append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("PICKUP_ASST_SALUT=")
				.append(getPickupAssSalut())
				.append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("PICKUP_ASST_NAME=")
				.append(getPickupAssName())
				.append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("DEL_FLG=")
				.append(getDelFlg())
				.append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("R_MOD_ID=")
				.append(getrModId())
				.append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("R_MOD_TIME=")
				.append(getrModTime())
				.append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("R_CRE_ID=")
				.append(getrCreId())
				.append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("R_CRE_TIME=")
				.append(getrCreTime())
				.append(AuditConstant.AUDIT_REC_SEPERATOR);
				
				
				logger.debug("String formed for audit is :"+stringBuffer.toString());
				return stringBuffer.toString();
			}
			
			
			// method for create InstituteMasterKey String for Audit
			public String toStringForAuditInstMasterKey() {
				
				
				StringBuffer  stringBuffer=new StringBuffer()
				.append("INST_ID=")
				.append(getInstId())
				.append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("BRANCH_ID=")
				.append(getBranchId())
				.append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("STUDENT_ADMIS_NO=")
				.append(getStudentAdmisNo())
				.append(AuditConstant.AUDIT_REC_SEPERATOR);
				
				return stringBuffer.toString();
			}
}
