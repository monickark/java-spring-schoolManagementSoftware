package com.jaw.student.admission.controller;

import java.sql.Blob;
import java.io.Serializable;
import org.springframework.web.multipart.MultipartFile;

public class StudentInfoVO implements Serializable{
	private Integer dbTs;
	private String instId = "";
	private String branchId = "";
	private String academicYear = "";
	private String studentAdmisNo = "";
	private String serialNoForExcelUpdate = "";		
	private MultipartFile studentPhoto;	
	private MultipartFile birthCertificate;	
	private MultipartFile casteCertificate;	
	private MultipartFile medicalCertificate;	
	private Blob img;		
	private String admisDate ;
	private String admittedToClass = "";
	private String firstName = "";
	private String middleName = "";
	private String lastName = "";
	private String placeOfBirth = "";
	private String studentPhotoRefNo = "";
	private String dob ;
	private String birthCertiVer = "";
	private String birthCertiRefNo = "";
	private String bloodGroup = "";
	private String idMark1 = "";
	private String idMark2 = "";
	private String mobileNo = "";
	private String email = "";
	private String casteCategory = "";
	private String caste = "";
	private String casteCertiRefNo = "";
	private String religion = "";
	private String nationality = "";
	private String feePayType = "";	
	private String subCaste = "";
	private String admisCategory = "";
	private String motherTongue = "";
	private String refPersonName = "";
	private String foreignPassFlg = "N";
	private String passportNo = "";
	private String passportValidity = "";
	private String pass_placeOfIssue = "";
	private String tutOrColStudying = "";
	private String gender = "";
	
	public String getMotherTongue() {
		return motherTongue;
	}


	public String getGender() {
		return gender;
	}


	public void setGender(String gender) {
		this.gender = gender;
	}


	public MultipartFile getMedicalCertificate() {
		return medicalCertificate;
	}


	public void setMedicalCertificate(MultipartFile medicalCertificate) {
		this.medicalCertificate = medicalCertificate;
	}

	@Override
	public String toString() {
		return "StudentInfoVO [dbTs=" + dbTs + ", instId=" + instId
				+ ", branchId=" + branchId + ", academicYear=" + academicYear
				+ ", studentAdmisNo=" + studentAdmisNo
				+ ", serialNoForExcelUpdate=" + serialNoForExcelUpdate
				+ ", studentPhoto=" + studentPhoto + ", birthCertificate="
				+ birthCertificate + ", casteCertificate=" + casteCertificate
				+ ", medicalCertificate=" + medicalCertificate + ", img=" + img
				+ ", admisDate=" + admisDate + ", admittedToClass="
				+ admittedToClass + ", firstName=" + firstName
				+ ", middleName=" + middleName + ", lastName=" + lastName
				+ ", placeOfBirth=" + placeOfBirth + ", studentPhotoRefNo="
				+ studentPhotoRefNo + ", dob=" + dob + ", birthCertiVer="
				+ birthCertiVer + ", birthCertiRefNo=" + birthCertiRefNo
				+ ", bloodGroup=" + bloodGroup + ", idMark1=" + idMark1
				+ ", idMark2=" + idMark2 + ", mobileNo=" + mobileNo
				+ ", email=" + email + ", casteCategory=" + casteCategory
				+ ", caste=" + caste + ", casteCertiRefNo=" + casteCertiRefNo
				+ ", religion=" + religion + ", nationality=" + nationality
				+ ", feePayType=" + feePayType + ", subCaste=" + subCaste
				+ ", admisCategory=" + admisCategory + ", motherTongue="
				+ motherTongue + ", refPersonName=" + refPersonName
				+ ", foreignPassFlg=" + foreignPassFlg + ", passportNo="
				+ passportNo + ", passportValidity=" + passportValidity
				+ ", pass_placeOfIssue=" + pass_placeOfIssue
				+ ", tutOrColStudying=" + tutOrColStudying + ", gender="
				+ gender + "]";
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


	
	public String getSerialNoForExcelUpdate() {
		return serialNoForExcelUpdate;
	}


	public void setSerialNoForExcelUpdate(String serialNoForExcelUpdate) {
		this.serialNoForExcelUpdate = serialNoForExcelUpdate;
	}


	public Integer getDbTs() {
		return dbTs;
	}

	public void setDbTs(Integer dbTs) {
		this.dbTs = dbTs;
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

	public Blob getImg() {
		return img;
	}

	public void setImg(Blob img) {
		this.img = img;
	}

	public MultipartFile getCasteCertificate() {
		return casteCertificate;
	}

	public void setCasteCertificate(MultipartFile casteCertificate) {
		this.casteCertificate = casteCertificate;
	}

	public MultipartFile getBirthCertificate() {
		return birthCertificate;
	}

	public void setBirthCertificate(MultipartFile birthCertificate) {
		this.birthCertificate = birthCertificate;
	}

	public MultipartFile getStudentPhoto() {
		return studentPhoto;
	}

	public void setStudentPhoto(MultipartFile studentPhoto) {
		this.studentPhoto = studentPhoto;
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
	

	public String getStudentPhotoRefNo() {
		return studentPhotoRefNo;
	}


	public void setStudentPhotoRefNo(String studentPhotoRefNo) {
		this.studentPhotoRefNo = studentPhotoRefNo;
	}


	public String getBirthCertiRefNo() {
		return birthCertiRefNo;
	}


	public void setBirthCertiRefNo(String birthCertiRefNo) {
		this.birthCertiRefNo = birthCertiRefNo;
	}


	public String getMobileNo() {
		return mobileNo;
	}


	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}


	public String getCasteCertiRefNo() {
		return casteCertiRefNo;
	}


	public void setCasteCertiRefNo(String casteCertiRefNo) {
		this.casteCertiRefNo = casteCertiRefNo;
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

}
