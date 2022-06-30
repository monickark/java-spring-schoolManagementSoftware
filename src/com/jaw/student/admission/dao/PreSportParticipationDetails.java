package com.jaw.student.admission.dao;

import org.apache.log4j.Logger;

import com.jaw.common.constants.AuditConstant;
import com.jaw.student.service.ViewAdmisHelper;

public class PreSportParticipationDetails {
	Logger logger = Logger.getLogger(PreSportParticipationDetails.class);
	private Integer dbTs;
	private String instId ;
	private String branchId ;
	private String studentAdmisNo ;
	private String sportsEntrySerialNo;
	private String sportsLevel = "";
	private String partDetails = "";
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
	public String getSportsEntrySerialNo() {
		return sportsEntrySerialNo;
	}
	public void setSportsEntrySerialNo(String sportsEntrySerialNo) {
		this.sportsEntrySerialNo = sportsEntrySerialNo;
	}
	public String getSportsLevel() {
		return sportsLevel;
	}
	public void setSportsLevel(String sportsLevel) {
		this.sportsLevel = sportsLevel;
	}
	public String getPartDetails() {
		return partDetails;
	}
	public void setPartDetails(String partDetails) {
		this.partDetails = partDetails;
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
	
	
	// method for create InstituteMaster Record for Audit
				public  String toStringForAudit()
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
					.append("SE_SRL_NO=")
					.append(getSportsEntrySerialNo())
					.append(AuditConstant.AUDIT_REC_SEPERATOR)
					.append("SPORTS_LEVEL=")
					.append(getSportsLevel())
					.append(AuditConstant.AUDIT_REC_SEPERATOR)
					.append("PART_DETAILS=")
					.append(getPartDetails())
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
					.append(AuditConstant.AUDIT_REC_SEPERATOR)				
					.append("SE_SRL_NO=")
					.append(getSportsEntrySerialNo())
					.append(AuditConstant.AUDIT_REC_SEPERATOR);
					
					return stringBuffer.toString();
				}
				@Override
				public String toString() {
					return "PreSportParticipationDetails [logger=" + logger
							+ ", dbTs=" + dbTs + ", instId=" + instId
							+ ", branchId=" + branchId + ", studentAdmisNo="
							+ studentAdmisNo + ", sportsEntrySerialNo="
							+ sportsEntrySerialNo + ", sportsLevel="
							+ sportsLevel + ", partDetails=" + partDetails
							+ ", delFlg=" + delFlg + ", rModId=" + rModId
							+ ", rModTime=" + rModTime + ", rCreId=" + rCreId
							+ ", rCreTime=" + rCreTime + "]";
				}
}
