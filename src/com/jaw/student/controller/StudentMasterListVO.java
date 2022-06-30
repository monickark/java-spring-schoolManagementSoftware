package com.jaw.student.controller;

import java.io.Serializable;

import org.apache.log4j.Logger;


public class StudentMasterListVO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	Logger logger = Logger.getLogger(StudentMasterListVO.class);

	private int rowid;	
	private String studentType = "";	
	private String secoundLang = "";
	public String secLangDesc = "";
	private String regNo = "";
	
	private String delFlg = "";
	private String rModId = "";
	private String rModTime = "";
	private String rCreId = "";
	private String rCreTime = "";
	private Integer rollno;
	
	private String courseVariant = "";
	private String courseVariantCat = "";
	private Integer dbTs;
	private String instId ;
	private String branchId;
	private String userId = "";
	private String studentId = "";
	private String academicYear;
	private String studentAdmisNo;
	private String course = "";
	private String standard = "";
	private String sec = "";
	private String combination = "";
	private String studentName = "";
	private String gender = "";
	private String houseName = "";
	private String thirdLang = "";
	private String elective1 = "";
	private String elective2 = "";
	private String religiousStudies = "";
	private String transfered = "";
	private String tcRefNo = "";
	private String accountNo = "";
	public Logger getLogger() {
		return logger;
	}
	public void setLogger(Logger logger) {
		this.logger = logger;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getStudentId() {
		return studentId;
	}
	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}
	public String getStandard() {
		return standard;
	}
	public void setStandard(String standard) {
		this.standard = standard;
	}
	public String getSec() {
		return sec;
	}
	public void setSec(String sec) {
		this.sec = sec;
	}
	public String getCombination() {
		return combination;
	}
	public void setCombination(String combination) {
		this.combination = combination;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getHouseName() {
		return houseName;
	}
	public void setHouseName(String houseName) {
		this.houseName = houseName;
	}
	public String getThirdLang() {
		return thirdLang;
	}
	public void setThirdLang(String thirdLang) {
		this.thirdLang = thirdLang;
	}
	public String getElective1() {
		return elective1;
	}
	public void setElective1(String elective1) {
		this.elective1 = elective1;
	}
	public String getElective2() {
		return elective2;
	}
	public void setElective2(String elective2) {
		this.elective2 = elective2;
	}
	public String getReligiousStudies() {
		return religiousStudies;
	}
	public void setReligiousStudies(String religiousStudies) {
		this.religiousStudies = religiousStudies;
	}
	public String getTransfered() {
		return transfered;
	}
	public void setTransfered(String transfered) {
		this.transfered = transfered;
	}
	public String getTcRefNo() {
		return tcRefNo;
	}
	public void setTcRefNo(String tcRefNo) {
		this.tcRefNo = tcRefNo;
	}
	public String getAccountNo() {
		return accountNo;
	}
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
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
	public Integer getRollno() {
		return rollno;
	}
	public void setRollno(Integer rollno) {
		this.rollno = rollno;
	}
	
	
	public String getSecLangDesc() {
		return secLangDesc;
	}
	public void setSecLangDesc(String secLangDesc) {
		this.secLangDesc = secLangDesc;
	}
	
	public String getRegNo() {
		return regNo;
	}
	public void setRegNo(String regNo) {
		this.regNo = regNo;
	}
	
	public String getCourseVariant() {
		return courseVariant;
	}
	public void setCourseVariant(String courseVariant) {
		this.courseVariant = courseVariant;
	}
	
	public int getRowid() {
		return rowid;
	}
	public void setRowid(int rowid) {
		this.rowid = rowid;
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
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public String getCourseVariantCat() {
		return courseVariantCat;
	}
	public void setCourseVariantCat(String courseVariantCat) {
		this.courseVariantCat = courseVariantCat;
	}
	public String getStudentType() {
		return studentType;
	}
	public void setStudentType(String studentType) {
		this.studentType = studentType;
	}
	public String getSecoundLang() {
		return secoundLang;
	}
	public void setSecoundLang(String secoundLang) {
		this.secoundLang = secoundLang;
	}
	public Integer getDbTs() {
		return dbTs;
	}
	public void setDbTs(Integer dbTs) {
		this.dbTs = dbTs;
	}
	public String getCourse() {
		return course;
	}
	public void setCourse(String course) {
		this.course = course;
	}
	@Override
	public String toString() {
		return "StudentMasterListVO [logger=" + logger + ", rowid=" + rowid
				+ ", studentType=" + studentType + ", secoundLang="
				+ secoundLang + ", secLangDesc=" + secLangDesc + ", regNo="
				+ regNo + ", delFlg=" + delFlg + ", rModId=" + rModId
				+ ", rModTime=" + rModTime + ", rCreId=" + rCreId
				+ ", rCreTime=" + rCreTime + ", rollno=" + rollno
				+ ", courseVariant=" + courseVariant + ", courseVariantCat="
				+ courseVariantCat + ", dbTs=" + dbTs + ", instId=" + instId
				+ ", branchId=" + branchId + ", userId=" + userId
				+ ", studentId=" + studentId + ", academicYear=" + academicYear
				+ ", studentAdmisNo=" + studentAdmisNo + ", course=" + course
				+ ", standard=" + standard + ", sec=" + sec + ", combination="
				+ combination + ", studentName=" + studentName + ", gender="
				+ gender + ", houseName=" + houseName + ", thirdLang="
				+ thirdLang + ", elective1=" + elective1 + ", elective2="
				+ elective2 + ", religiousStudies=" + religiousStudies
				+ ", transfered=" + transfered + ", tcRefNo=" + tcRefNo
				+ ", accountNo=" + accountNo + "]";
	}
	
	
}
