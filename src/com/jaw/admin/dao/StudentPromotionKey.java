package com.jaw.admin.dao;

public class StudentPromotionKey {
private String instId;
private String branchId;
private String acTerm;
private String studentGroupId;
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
public String getAcTerm() {
	return acTerm;
}
public void setAcTerm(String acTerm) {
	this.acTerm = acTerm;
}
public String getStudentGroupId() {
	return studentGroupId;
}
public void setStudentGroupId(String studentGroupId) {
	this.studentGroupId = studentGroupId;
}
@Override
public String toString() {
	return "StudentPromotionKey [instId=" + instId + ", branchId=" + branchId
			+ ", acTerm=" + acTerm + ", studentGroupId=" + studentGroupId + "]";
}
}
