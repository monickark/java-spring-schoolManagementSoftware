package com.jaw.student.admission.dao;
import java.io.Serializable;

import com.jaw.common.constants.AuditConstant;
public class ParentDetailsKey implements Serializable{
	private String instId;
	private String branchId;
	private String parentId;
	private String studentAdmisNo;

	public String getInstId() {
		return instId;
	}

	public void setInstId(String instId) {
		this.instId = instId;
	}

	@Override
	public String toString() {
		return "ParentDetailsKey [instId=" + instId + ", branchId=" + branchId
				+ ", parentId=" + parentId + ", studentAdmisNo="
				+ studentAdmisNo + "]";
	}

	public String getBranchId() {
		return branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}
	

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
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
		.append("PARENT_ID=")
		.append(getParentId())
		.append(AuditConstant.AUDIT_REC_SEPERATOR)
		.append("STUDENT_ADMIS_NO=")
		.append(getStudentAdmisNo())
		.append(AuditConstant.AUDIT_REC_SEPERATOR);
		
		return stringBuffer.toString();
	}
}
