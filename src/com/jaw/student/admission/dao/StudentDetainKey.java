package com.jaw.student.admission.dao;

import com.jaw.common.constants.AuditConstant;

public class StudentDetainKey {
	private String stuAdmisNo = "";
	public String getStuAdmisNo() {
		return stuAdmisNo;
	}
	public void setStuAdmisNo(String stuAdmisNo) {
		this.stuAdmisNo = stuAdmisNo;
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
	public String getDbTs() {
		return dbTs;
	}
	public void setDbTs(String dbTs) {
		this.dbTs = dbTs;
	}
	private String instId = "";
	private String branchId = "";
	private String academicYear = "";
	private String dbTs = "";

	// method for create Student Detain String for Audit
			public String toStringForStuDetainKey() {

				StringBuffer stringBuffer = new StringBuffer().append("INST_ID=")					
						.append(getInstId()).append(AuditConstant.AUDIT_REC_SEPERATOR)
						.append("BRANCH_ID=")
						.append(getBranchId())
						.append(AuditConstant.AUDIT_REC_SEPERATOR)
						.append("AC_TERM=")
						.append(getAcademicYear())
								.append(AuditConstant.AUDIT_REC_SEPERATOR)
								.append("STUDENT_ADMIS_NO=")
								.append(getStuAdmisNo());
				return stringBuffer.toString();
			}
			public String getAcademicYear() {
				return academicYear;
			}
			public void setAcademicYear(String academicYear) {
				this.academicYear = academicYear;
			}
	
}
