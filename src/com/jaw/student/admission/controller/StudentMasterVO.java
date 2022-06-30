package com.jaw.student.admission.controller;
import java.io.Serializable;

public class StudentMasterVO implements Serializable{
	private String serialNoForExcelUpdate = "";
	private Integer dbTs;
	private String instId = "";
	private String branchId = "";
	private String userId = "";
	private String studentId = "";
	private String academicYear = "";
	private String studentAdmisNo = "";
	private String standard = "";
	private String sec = "";
	private String combination = "";
	private String studentName = "";
	private String gender = "";
	private String houseName = "";
	private String secoundLang = "";
	private String thirdLang = "";
	private String elective1 = "";
	private String elective2 = "";
	private String religiousStudies = "";
	private String transfered = "";
	private String tcRefNo = "";
	private String accountNo = "";
	private Integer rollno;
	private String regNo = "";
	private String medium = "";
	private String transferDate;
	private int rowid;
	private String course = "";
	private String courseVariant = "";
	private String courseVariantCat = "";
	private String studentType = "";
	private String studentBatch = "";
	private String labBatch = "";
	private String reasonForLeaving = "";
	public String stuGrpId = "";

	public String getStuGrpId() {
		return stuGrpId;
	}

	public void setStuGrpId(String stuGrpId) {
		this.stuGrpId = stuGrpId;
	}

	public String getCourse() {
		return course;
	}

	public void setCourse(String course) {
		this.course = course;
	}

	public String getCourseVariant() {
		return courseVariant;
	}

	public void setCourseVariant(String courseVariant) {
		this.courseVariant = courseVariant;
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

	public String getStudentBatch() {
		return studentBatch;
	}

	public void setStudentBatch(String studentBatch) {
		this.studentBatch = studentBatch;
	}

	public String getLabBatch() {
		return labBatch;
	}

	public void setLabBatch(String labBatch) {
		this.labBatch = labBatch;
	}

	@Override
	public String toString() {
		return "StudentMasterVO [serialNoForExcelUpdate="
				+ serialNoForExcelUpdate + ", dbTs=" + dbTs + ", instId="
				+ instId + ", branchId=" + branchId + ", userId=" + userId
				+ ", studentId=" + studentId + ", academicYear=" + academicYear
				+ ", studentAdmisNo=" + studentAdmisNo + ", standard="
				+ standard + ", sec=" + sec + ", combination=" + combination
				+ ", studentName=" + studentName + ", gender=" + gender
				+ ", houseName=" + houseName + ", secoundLang=" + secoundLang
				+ ", thirdLang=" + thirdLang + ", elective1=" + elective1
				+ ", elective2=" + elective2 + ", religiousStudies="
				+ religiousStudies + ", transfered=" + transfered
				+ ", tcRefNo=" + tcRefNo + ", accountNo=" + accountNo
				+ ", rollno=" + rollno + ", regNo=" + regNo + ", medium="
				+ medium + ", transferDate=" + transferDate + ", rowid="
				+ rowid + ", course=" + course + ", courseVariant="
				+ courseVariant + ", courseVariantCat=" + courseVariantCat
				+ ", studentType=" + studentType + ", studentBatch="
				+ studentBatch + ", labBatch=" + labBatch
				+ ", reasonForLeaving=" + reasonForLeaving + ", stuGrpId="
				+ stuGrpId + "]";
	}

	public String getReasonForLeaving() {
		return reasonForLeaving;
	}

	public void setReasonForLeaving(String reasonForLeaving) {
		this.reasonForLeaving = reasonForLeaving;
	}

	public String getSerialNoForExcelUpdate() {
		return serialNoForExcelUpdate;
	}

	public Integer getDbTs() {
		return dbTs;
	}

	public void setDbTs(Integer dbTs) {
		this.dbTs = dbTs;
	}

	public void setSerialNoForExcelUpdate(String serialNoForExcelUpdate) {
		this.serialNoForExcelUpdate = serialNoForExcelUpdate;
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

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
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

	public String getSecoundLang() {
		return secoundLang;
	}

	public void setSecoundLang(String secoundLang) {
		this.secoundLang = secoundLang;
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

	public Integer getRollno() {
		return rollno;
	}

	public void setRollno(Integer rollno) {
		this.rollno = rollno;
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
	

	public String getRegNo() {
		return regNo;
	}

	public void setRegNo(String regNo) {
		this.regNo = regNo;
	}

	public String getMedium() {
		return medium;
	}

	public void setMedium(String medium) {
		this.medium = medium;
	}

	public String getTransferDate() {
		return transferDate;
	}

	public void setTransferDate(String transferDate) {
		this.transferDate = transferDate;
	}

	public int getRowid() {
		return rowid;
	}

	public void setRowid(int rowid) {
		this.rowid = rowid;
	}

}
