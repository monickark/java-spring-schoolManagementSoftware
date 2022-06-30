package com.jaw.student.admission.dao;

import org.apache.log4j.Logger;
import java.io.Serializable;
import com.jaw.common.constants.AuditConstant;

public class SiblingDetails implements Serializable{
	
	Logger logger=Logger.getLogger(SiblingDetails.class);
	private Integer dbTs;
	private String instId;
	private String branchId;
	private String studentAdmisNo;
	private String siblingNo;
	private String siblingName = "";
	private String siblingClass = "";
	private String siblingYear = "";
	private String siblingAdmisNo = "";
	private String delFlg = "";
	private String rModId = "";
	private String rModTime;
	private String rCreId = "";
	private String rCreTime;
	
	@Override
	public String toString() {
		return "SiblingDetails [logger=" + logger + ", dbTs=" + dbTs
				+ ", instId=" + instId + ", branchId=" + branchId
				+ ", studentAdmisNo=" + studentAdmisNo + ", siblingNo="
				+ siblingNo + ", siblingName=" + siblingName
				+ ", siblingClass=" + siblingClass + ", siblingYear="
				+ siblingYear + ", siblingAdmisNo=" + siblingAdmisNo
				+ ", delFlg=" + delFlg + ", rModId=" + rModId + ", rModTime="
				+ rModTime + ", rCreId=" + rCreId + ", rCreTime=" + rCreTime
				+ "]";
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
	public String getStudentAdmisNo() {
		return studentAdmisNo;
	}
	public void setStudentAdmisNo(String studentAdmisNo) {
		this.studentAdmisNo = studentAdmisNo;
	}
	
	public String getSiblingNo() {
		return siblingNo;
	}
	public void setSiblingNo(String siblingNo) {
		this.siblingNo = siblingNo;
	}
	public String getSiblingName() {
		return siblingName;
	}
	public void setSiblingName(String siblingName) {
		this.siblingName = siblingName;
	}
	public String getSiblingClass() {
		return siblingClass;
	}
	public void setSiblingClass(String siblingClass) {
		this.siblingClass = siblingClass;
	}
	public String getSiblingYear() {
		return siblingYear;
	}
	public void setSiblingYear(String siblingYear) {
		this.siblingYear = siblingYear;
	}
	public String getSiblingAdmisNo() {
		return siblingAdmisNo;
	}
	public void setSiblingAdmisNo(String siblingAdmisNo) {
		this.siblingAdmisNo = siblingAdmisNo;
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
			public  String toStringForAuditSiblingRecord()
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
				.append("SIBLING_NO=")
				.append(getSiblingNo())
				.append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("SIBLING_NAME=")
				.append(getSiblingName())
				.append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("SIBLING_CLASS=")
				.append(getSiblingClass())
				.append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("SIBLING_YEAR=")
				.append(getSiblingYear())
				.append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("SIBLING_ADMIS_NO=")
				.append(getSiblingAdmisNo())
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
			public String toStringForAuditSibKey() {
				
				
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
				.append("SIBLING_NO=")
				.append(getSiblingNo())
				.append(AuditConstant.AUDIT_REC_SEPERATOR);
				
				return stringBuffer.toString();
			}
	
}
