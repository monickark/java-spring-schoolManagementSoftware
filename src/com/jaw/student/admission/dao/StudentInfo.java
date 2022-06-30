	package com.jaw.student.admission.dao;

import org.apache.log4j.Logger;
import java.io.Serializable;
import com.jaw.common.constants.AuditConstant;

public class StudentInfo implements Serializable{
	
	Logger logger=Logger.getLogger(StudentInfo.class);
	private Integer dbTs;
	private String instId;
	private String branchId;
	private String academicYear = "";
	private String studentAdmisNo;
	private String admisDate = "";
	private String admittedToClass = "";	
	private String firstName = "";
	private String middleName = "";
	private String lastName = "";
	private String placeOfBirth = "";
	private String dob = "";
	private String birthCertiVer = "";
	private String bloodGroup = "";
	private String idMark1 = "";
	private String idMark2 = "";
	private String mobileNo = "";
	private String email = "";
	private String casteCategory = "";
	private String caste = "";
	private String religion = "";
	private String nationality = "";
	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}


	private String feePayType = "";
	private String delFlg = "";
	private String rModId = "";
	@Override
	public String toString() {
		return "StudentInfo [logger=" + logger + ", dbTs=" + dbTs + ", instId="
				+ instId + ", branchId=" + branchId + ", academicYear="
				+ academicYear + ", studentAdmisNo=" + studentAdmisNo
				+ ", admisDate=" + admisDate + ", admittedToClass="
				+ admittedToClass + ", firstName=" + firstName
				+ ", middleName=" + middleName + ", lastName=" + lastName
				+ ", placeOfBirth=" + placeOfBirth + ", dob=" + dob
				+ ", birthCertiVer=" + birthCertiVer + ", bloodGroup="
				+ bloodGroup + ", idMark1=" + idMark1 + ", idMark2=" + idMark2
				+ ", mobileNo=" + mobileNo + ", email=" + email
				+ ", casteCategory=" + casteCategory + ", caste=" + caste
				+ ", religion=" + religion + ", nationality=" + nationality
				+ ", feePayType=" + feePayType + ", delFlg=" + delFlg
				+ ", rModId=" + rModId + ", rModTime=" + rModTime + ", gender="
				+ gender + ", rCreId=" + rCreId + ", rCreTime=" + rCreTime
				+ ", extraField1=" + extraField1 + ", extraField2="
				+ extraField2 + ", extraField3=" + extraField3
				+ ", extraField4=" + extraField4 + ", extraField5="
				+ extraField5 + ", subCaste=" + subCaste + ", admisCategory="
				+ admisCategory + ", motherTongue=" + motherTongue
				+ ", refPersonName=" + refPersonName + ", foreignPassFlg="
				+ foreignPassFlg + ", passportNo=" + passportNo
				+ ", passportValidity=" + passportValidity
				+ ", pass_placeOfIssue=" + pass_placeOfIssue
				+ ", tutOrColStudying=" + tutOrColStudying + "]";
	}


	private String rModTime = "";
	private String gender = "";	
	private String rCreId = "";
	private String rCreTime = "";
	private String extraField1 = "";
	private String extraField2 = "";
	private String extraField3 = "";
	private String extraField4 = "";
	private String extraField5 = "";
	private String subCaste = "";
	private String admisCategory = "";
	private String motherTongue = "";
	private String refPersonName = "";
	private String foreignPassFlg = "N";
	private String passportNo = "";
	private String passportValidity = "";
	private String pass_placeOfIssue = "";
	private String tutOrColStudying = "";
	
	
	
	public Logger getLogger() {
		return logger;
	}

	public void setLogger(Logger logger) {
		this.logger = logger;
	}

	public String getMotherTongue() {
		return motherTongue;
	}

	public void setMotherTongue(String motherTongue) {
		this.motherTongue = motherTongue;
	}

	public String getRefPersonName() {
		return refPersonName;
	}

	public void setRefPersonName(String refPersonName) {
		this.refPersonName = refPersonName;
	}

	public String getForeignPassFlg() {
		return foreignPassFlg;
	}

	public void setForeignPassFlg(String foreignPassFlg) {
		this.foreignPassFlg = foreignPassFlg;
	}

	public String getPassportNo() {
		return passportNo;
	}

	public void setPassportNo(String passportNo) {
		this.passportNo = passportNo;
	}

	public String getPassportValidity() {
		return passportValidity;
	}

	public void setPassportValidity(String passportValidity) {
		this.passportValidity = passportValidity;
	}

	public String getPass_placeOfIssue() {
		return pass_placeOfIssue;
	}

	public void setPass_placeOfIssue(String pass_placeOfIssue) {
		this.pass_placeOfIssue = pass_placeOfIssue;
	}

	public String getTutOrColStudying() {
		return tutOrColStudying;
	}

	public void setTutOrColStudying(String tutOrColStudying) {
		this.tutOrColStudying = tutOrColStudying;
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

	public String getAdmisDate() {
		return admisDate;
	}

	public void setAdmisDate(String admisDate) {
		this.admisDate = admisDate;
	}

	public String getAdmittedToClass() {
		return admittedToClass;
	}

	public void setAdmittedToClass(String admittedToClass) {
		this.admittedToClass = admittedToClass;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPlaceOfBirth() {
		return placeOfBirth;
	}

	public void setPlaceOfBirth(String placeOfBirth) {
		this.placeOfBirth = placeOfBirth;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getBirthCertiVer() {
		return birthCertiVer;
	}

	public void setBirthCertiVer(String birthCertiVer) {
		this.birthCertiVer = birthCertiVer;
	}

	public String getBloodGroup() {
		return bloodGroup;
	}

	public void setBloodGroup(String bloodGroup) {
		this.bloodGroup = bloodGroup;
	}

	public String getIdMark1() {
		return idMark1;
	}

	public void setIdMark1(String idMark1) {
		this.idMark1 = idMark1;
	}

	public String getIdMark2() {
		return idMark2;
	}

	public void setIdMark2(String idMark2) {
		this.idMark2 = idMark2;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCasteCategory() {
		return casteCategory;
	}

	public void setCasteCategory(String casteCategory) {
		this.casteCategory = casteCategory;
	}

	public String getCaste() {
		return caste;
	}

	public void setCaste(String caste) {
		this.caste = caste;
	}

	public String getReligion() {
		return religion;
	}

	public void setReligion(String religion) {
		this.religion = religion;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public String getFeePayType() {
		return feePayType;
	}

	public void setFeePayType(String feePayType) {
		this.feePayType = feePayType;
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

	public String getExtraField1() {
		return extraField1;
	}

	public void setExtraField1(String extraField1) {
		this.extraField1 = extraField1;
	}

	public String getExtraField2() {
		return extraField2;
	}

	public void setExtraField2(String extraField2) {
		this.extraField2 = extraField2;
	}

	public String getExtraField3() {
		return extraField3;
	}

	public void setExtraField3(String extraField3) {
		this.extraField3 = extraField3;
	}

	public String getExtraField4() {
		return extraField4;
	}

	public void setExtraField4(String extraField4) {
		this.extraField4 = extraField4;
	}

	public String getExtraField5() {
		return extraField5;
	}

	public void setExtraField5(String extraField5) {
		this.extraField5 = extraField5;
	}

	public String getSubCaste() {
		return subCaste;
	}

	public void setSubCaste(String subCaste) {
		this.subCaste = subCaste;
	}

	public String getAdmisCategory() {
		return admisCategory;
	}

	public void setAdmisCategory(String admisCategory) {
		this.admisCategory = admisCategory;
	}
	
	// method for create InstituteMaster Record for Audit
			public  String toStringForAuditInstMasterRecord()
			{
				
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
				.append("ADMIS_DATE=")
				.append(getAdmisDate())
				.append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("ADMITTED_TO_CLASS=")
				.append(getAdmittedToClass())
				.append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("FIRST_NAME=")
				.append(getFirstName())
				.append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("MIDDLE_NAME=")
				.append(getMiddleName())
				.append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("LAST_NAME=")
				.append(getLastName())
				.append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("DOB=")
				.append(getDob())
				.append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("BIRTH_CERTI_VER=")
				.append(getBirthCertiVer())
				.append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("BLOOD_GROUP=")
				.append(getBloodGroup())
				.append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("ID_MARK1=")
				.append(getIdMark1())
				.append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("ID_MARK2=")
				.append(getIdMark2())
				.append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("MOBILE_NO=")
				.append(getMobileNo())
				.append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("EMAIL=")
				.append(getEmail())
				.append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("CASTE_CATEGORY=")
				.append(getCasteCategory())
				.append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("CASTE=")
				.append(getCaste())
				.append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("RELIGION=")
				.append(getReligion())
				.append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("FEE_PAY_TYPE=")
				.append(getFeePayType())
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
				.append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("NATIONALITY=")
				.append(getNationality())
				.append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("PLACE_OF_BIRTH=")
				.append(getPlaceOfBirth())
				.append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("SUB_CASTE=")
				.append(getSubCaste())
				.append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("ADMIS_CATEGORY=")
				.append(getAdmisCategory())
				.append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("MOTHER_TONGUE=")
				.append(getMotherTongue())
				.append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("REF_PERSON_NAME=")
				.append(getRefPersonName())
				.append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("FOREIGN_PASS_FLAG=")
				.append(getForeignPassFlg())
				.append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("PASS_NO=")
				.append(getPassportNo())
				.append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("PASS_VALIDITY=")
				.append(getPassportNo())
				.append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("PASS_PLACE_OF_ISSUE=")
				.append(getPass_placeOfIssue())
				.append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("TUT_COL_OR_SCH_STUDYING=")
				.append(getTutOrColStudying())
				.append(AuditConstant.AUDIT_REC_SEPERATOR);
				
				
				logger.debug("String formed for audit is :"+stringBuffer.toString());
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
				.append("STUDENT_ADMIS_NO=")
				.append(getStudentAdmisNo())
				.append(AuditConstant.AUDIT_REC_SEPERATOR);
				
				return stringBuffer.toString();
			}

}
