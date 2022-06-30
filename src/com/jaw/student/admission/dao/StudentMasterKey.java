package com.jaw.student.admission.dao;
import java.io.Serializable;

import com.jaw.common.constants.AuditConstant;
public class StudentMasterKey implements Serializable{
	private String instId;
	private String branchId;
	private String academicYear;
	private String studentAdmisNo;

	public String getInstId() {
		return instId;
	}

	@Override
	public String toString() {
		return "StudentMasterKey [instId=" + instId + ", branchId=" + branchId
				+ ", academicYear=" + academicYear + ", studentAdmisNo="
				+ studentAdmisNo + "]";
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

	public String getStudentAdmisNo() {
		return studentAdmisNo;
	}

	public void setStudentAdmisNo(String studentAdmisNo) {
		this.studentAdmisNo = studentAdmisNo;
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
			.append("ACADEMIC_YEAR=")
			.append(getAcademicYear())
			.append(AuditConstant.AUDIT_REC_SEPERATOR)
			.append("STUDENT_ADMIS_NO=")
			.append(getStudentAdmisNo())
			.append(AuditConstant.AUDIT_REC_SEPERATOR);
			
			return stringBuffer.toString();
		}
}
