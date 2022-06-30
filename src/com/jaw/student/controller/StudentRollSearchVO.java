package com.jaw.student.controller;
import java.io.Serializable;
public class StudentRollSearchVO implements Serializable{
	private String studentAdmisNo= "";
	private String academicYear = "";
	private String standard= "";
	private String section= "";
	private String combination= "";
	private String orderone= "";
	private String ordertwo= "";
	private String userName= "";
	

	private String instId= "";
	@Override
	public String toString() {
		return "StudentRollSearchVO [studentAdmisNo=" + studentAdmisNo
				+ ", academicYear=" + academicYear + ", standard=" + standard
				+ ", section=" + section + ", combination=" + combination
				+ ", orderone=" + orderone + ", ordertwo=" + ordertwo
				+ ", userName=" + userName + ", instId=" + instId
				+ ", branchId=" + branchId + ", pageSize=" + pageSize
				+ ", studentGrpId=" + studentGrpId + "]";
	}

	private String branchId= "";
	private String pageSize= "10";
	private String studentGrpId= "";
		
	public String getStudentGrpId() {
		return studentGrpId;
	}

	

	public void setStudentGrpId(String studentGrpId) {
		this.studentGrpId = studentGrpId;
	}
	
	public String getInstId() {
		return instId;
	}

	public String getPageSize() {
		return pageSize;
	}



	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getOrderone() {
		return orderone;
	}

	public void setOrderone(String orderone) {
		this.orderone = orderone;
	}

	public String getOrdertwo() {
		return ordertwo;
	}

	public void setOrdertwo(String ordertwo) {
		this.ordertwo = ordertwo;
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

	public String getStandard() {
		return standard;
	}

	public void setStandard(String standard) {
		this.standard = standard;
	}

	public String getSection() {
		return section;
	}

	public void setSection(String section) {
		this.section = section;
	}

	public String getCombination() {
		return combination;
	}

	public void setCombination(String combination) {
		this.combination = combination;
	}

}
