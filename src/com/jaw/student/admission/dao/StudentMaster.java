package com.jaw.student.admission.dao;

import org.apache.log4j.Logger;
import java.io.Serializable;
import com.jaw.common.constants.AuditConstant;

public class StudentMaster implements Serializable{

	Logger logger = Logger.getLogger(StudentMaster.class);

	private Integer dbTs;
	private String instId ;
	private String branchId;
	private String userId = "";
	private String studentId = "";
	private String academicYear;
	private String studentAdmisNo;
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
	private String delFlg = "";
	private String rModId = "";
	private String rModTime = "";
	private String rCreId = "";
	private String rCreTime = "";
	private Integer rollno;
	private String regNo = "";
	@Override
	public String toString() {
		return "StudentMaster [dbTs=" + dbTs + ", instId=" + instId
				+ ", branchId=" + branchId + ", userId=" + userId
				+ ", studentId=" + studentId + ", academicYear=" + academicYear
				+ ", studentAdmisNo=" + studentAdmisNo + ", standard="
				+ standard + ", sec=" + sec + ", combination=" + combination
				+ ", studentName=" + studentName + ", gender=" + gender
				+ ", houseName=" + houseName + ", secoundLang=" + secoundLang
				+ ", thirdLang=" + thirdLang + ", elective1=" + elective1
				+ ", elective2=" + elective2 + ", religiousStudies="
				+ religiousStudies + ", transfered=" + transfered
				+ ", tcRefNo=" + tcRefNo + ", accountNo=" + accountNo
				+ ", delFlg=" + delFlg + ", rModId=" + rModId + ", rModTime="
				+ rModTime + ", rCreId=" + rCreId + ", rCreTime=" + rCreTime
				+ ", rollno=" + rollno + ", regNo=" + regNo + ", medium="
				+ medium + ", transferDate=" + transferDate + ", rowid="
				+ rowid + ", course=" + course + ", courseVariant="
				+ courseVariant + ", courseVariantCat=" + courseVariantCat
				+ ", studentType=" + studentType + ", studentBatch="
				+ studentBatch + ", labBatch=" + labBatch
				+ ", reasonForLeaving=" + reasonForLeaving + ", stuGrpId="
				+ stuGrpId + ", secLangDesc=" + secLangDesc + "]";
	}


	private String medium = "";
	private String transferDate = "";
	private int rowid;
	private String course = "";
	private String courseVariant = "";
	private String courseVariantCat = "";
	private String studentType = "";
	private String studentBatch = "";
	private String labBatch = "";
	private String reasonForLeaving = "";
	public String stuGrpId = "";
	public String secLangDesc = "";

	public String getStuGrpId() {
		return stuGrpId;
	}

	public String getSecLangDesc() {
		return secLangDesc;
	}

	public void setSecLangDesc(String secLangDesc) {
		this.secLangDesc = secLangDesc;
	}

	public void setStuGrpId(String stuGrpId) {
		this.stuGrpId = stuGrpId;
	}

	public int getRowid() {
		return rowid;
	}

	public void setRowid(int rowid) {
		this.rowid = rowid;
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

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
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
		if (transfered == null)
			transfered = "N";
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

	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public String getAccountNo() {
		return accountNo;
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

	public String getReasonForLeaving() {
		return reasonForLeaving;
	}

	public void setReasonForLeaving(String reasonForLeaving) {
		this.reasonForLeaving = reasonForLeaving;
	}

	// method for create InstituteMaster Record for Audit
	public String toStringForAuditInstMasterRecord() {
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
		.append("ACADEMIC_YEAR=")
		.append(getAcademicYear())
		.append(AuditConstant.AUDIT_REC_SEPERATOR)
		.append("STUDENT_ADMIS_NO=")
		.append(getStudentAdmisNo())
		.append(AuditConstant.AUDIT_REC_SEPERATOR)
		.append("REG_NO=")
		.append(getRegNo())
		.append(AuditConstant.AUDIT_REC_SEPERATOR)	
		.append("ROLL_NUMBER=")
		.append(getRollno())
		.append(AuditConstant.AUDIT_REC_SEPERATOR)		
		.append("COURSE=")
		.append(getCourse())
		.append(AuditConstant.AUDIT_REC_SEPERATOR)
		.append("STANDARD=")
		.append(getStandard())
		.append(AuditConstant.AUDIT_REC_SEPERATOR)		
		.append("COMBINATION=")
		.append(getCombination())
		.append(AuditConstant.AUDIT_REC_SEPERATOR)
		.append("SEC=")
		.append(getSec())
		.append(AuditConstant.AUDIT_REC_SEPERATOR)
		.append("COURSE_VARIANT_CAT=")
		.append(getCourseVariantCat())
		.append(AuditConstant.AUDIT_REC_SEPERATOR)
		.append("COURSE_VARIANT=")
		.append(getCourseVariant())
		.append(AuditConstant.AUDIT_REC_SEPERATOR)
		.append("STUDENT_TYPE=")
		.append(getStudentType())
		.append(AuditConstant.AUDIT_REC_SEPERATOR)
		.append("MEDIUM=")
		.append(getMedium())
		.append(AuditConstant.AUDIT_REC_SEPERATOR)
		.append("STUDENT_NAME=")
		.append(getStudentName())
		.append(AuditConstant.AUDIT_REC_SEPERATOR)
		.append("GENDER=")
		.append(getGender())
		.append(AuditConstant.AUDIT_REC_SEPERATOR)
		.append("HOUSE_NAME=")
		.append(getHouseName())
		.append(AuditConstant.AUDIT_REC_SEPERATOR)
		.append("SECOND_LANG=")
		.append(getSecoundLang())
		.append(AuditConstant.AUDIT_REC_SEPERATOR)
		.append("THIRD_LANG=")
		.append(getThirdLang())
		.append(AuditConstant.AUDIT_REC_SEPERATOR)
		.append("ELECTIVE_1=")
		.append(getElective1())
		.append(AuditConstant.AUDIT_REC_SEPERATOR)
		.append("ELECTIVE_2=")
		.append(getElective2())
		.append(AuditConstant.AUDIT_REC_SEPERATOR)
		.append("STUDENT_BATCH=")
		.append(getStudentBatch())
		.append(AuditConstant.AUDIT_REC_SEPERATOR)
		.append("LAB_BATCH=")
		.append(getLabBatch())
		.append(AuditConstant.AUDIT_REC_SEPERATOR)
		.append("RELIGIOUS_SUB=")
		.append(getReligiousStudies())
		.append(AuditConstant.AUDIT_REC_SEPERATOR)
		.append("TRANSFERRED=")
		.append(getTransfered())
		.append(AuditConstant.AUDIT_REC_SEPERATOR)
		.append("TRANSFER_DATE=")
		.append(getTransferDate())
		.append(AuditConstant.AUDIT_REC_SEPERATOR)
		.append("REASON_FOR_LEAVING=")
		.append(getReasonForLeaving())
		.append(AuditConstant.AUDIT_REC_SEPERATOR)	
		.append("ACCOUNT_NO=")
		.append(getAccountNo())
		.append(AuditConstant.AUDIT_REC_SEPERATOR)
		.append("STUDENTGRP_ID=")
		.append(getStuGrpId())
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
		return stringBuffer.toString();}

	public Integer getRollno() {
		return rollno;
	}

	public void setRollno(Integer rollno) {
		this.rollno = rollno;
	}

	public String toStringForFunAudit() {

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
		.append(AuditConstant.AUDIT_REC_SEPERATOR)
		.append("STANDARD=")
		.append(getStandard())
		.append(AuditConstant.AUDIT_REC_SEPERATOR)
		.append("COMBINATION=")
		.append(getCombination())
		.append(AuditConstant.AUDIT_REC_SEPERATOR);
		
		return stringBuffer.toString();
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

	
	@Override
	public boolean equals(Object other) {
	    if (!(other instanceof StudentMaster)) {
	        return false;
	    }

	    StudentMaster that = (StudentMaster) other;

	    // Custom equality check here.
	    return this.studentAdmisNo.equals(that.studentAdmisNo)
	      /*  && this.field2.equals(that.field2)*/;
	}
	

	@Override
	public int hashCode() {
	    int hashCode = 0;

	    hashCode = hashCode * 37 + this.studentAdmisNo.hashCode();
	  //  hashCode = hashCode * 37 + this.field2.hashCode();
		System.out.println("Hash code :"+hashCode);
	    return hashCode;
	}
	
	
}
