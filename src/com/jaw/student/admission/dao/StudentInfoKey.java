package com.jaw.student.admission.dao;
import java.io.Serializable;

import com.jaw.common.constants.AuditConstant;
public class StudentInfoKey implements Serializable{
	private String instId;
	private String branchId;
	private String studentAdmisNo;

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

	@Override
	public String toString() {
		return "StudentInfoKey [instId=" + instId + ", branchId=" + branchId
				+ ", studentAdmisNo=" + studentAdmisNo + "]";
	}
}
