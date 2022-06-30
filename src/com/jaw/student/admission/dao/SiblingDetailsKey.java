package com.jaw.student.admission.dao;
import java.io.Serializable;

import com.jaw.common.constants.AuditConstant;
public class SiblingDetailsKey implements Serializable{
	private String instId;
	private String branchId;
	private String studentAdmisNo;
	private Integer siblingNo;

	public String getInstId() {
		return instId;
	}

	@Override
	public String toString() {
		return "SiblingDetailsKey [instId=" + instId + ", branchId=" + branchId
				+ ", studentAdmisNo=" + studentAdmisNo + ", siblingNo="
				+ siblingNo + "]";
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

	public Integer getSiblingNo() {
		return siblingNo;
	}

	public void setSiblingNo(Integer siblingNo) {
		this.siblingNo = siblingNo;
	}
	
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
		.append("SIBLING_NO=")
		.append(getSiblingNo())
		.append(AuditConstant.AUDIT_REC_SEPERATOR);
		
		return stringBuffer.toString();
	}

}
