package com.jaw.student.admission.controller;

import org.springframework.web.multipart.MultipartFile;

import com.jaw.common.constants.ApplicationConstant;

import java.io.Serializable;
public class ParentDetailsVO implements Serializable{
	private String serialNoForExcelUpdate = "";
	private Integer dbTs;
	private String instId = "";
	private String branchId = "";
	private String parentId = "";
	private String studentAdmisNo = "";		
	private String parentType;
	private String fatherSalut = "";
	private String fatherName = "";
	private String fatherReligion = "";	
	private String fatherMothertongue = "";
	private MultipartFile fatherPhoto = null;
	private String fatherQual = "";
	private String fatherOccuDes = "";
	private String fatherAnnualIncome;
	private String fatherMobileNo = "";
	private String fatherEmail = "";
	private String fatherCompany = "";
	private String fatherOffAdd1 = "";
	private String fatherOffAdd2 = "";
	private String fatherOffAdd3 = "";
	private String fatherCity = "";
	private String fatherState = "";
	private String fatherPincode = "";
	private String fatherOffTele = "";
	private String fatherOldStudent = "";
	private String fatherPassedOut = "";
	private String motherSalut = "";
	private String motherName = "";
	private String motherReligion = "";
	private String motherMothertongue = "";
	private MultipartFile motherPhoto = null;
	private String motherQual = "";
	private String motherOccDes = "";
	private String motherAnnualIncome = "";
	private String motherMobileNo = "";
	private String motherEmail = "";
	private String motherCompany = "";	
	private String motherOffAdd1 = "";
	private String motherOffAdd2 = "";
	private String motherOffAdd3 = "";
	private String motherCity = "";
	private String motherPincode = "";
	private String motherState = "";
	private String motherOffTele = "";
	private String motherOldStudent = "";
	private String motherPassedOut = "";
	private String localGuardian = "";
	private String guardianSalut = "";
	private String guardianName = "";
	private String relationShip="";
	private String guardOccDes="";
	private String guardAnnualIncome="";
	private String guardEmail="";
	
	
	private MultipartFile guardianPhoto = null;
	private String guardianAdd1 = "";
	private String guardianAdd2 = "";
	private String guardianAdd3 = "";
	private String guardianCity = "";
	private String guardianState = "";
	private String guardianPincode = "";
	private String guardianLandlineNo = "";
	private String guardianMobileNo = "";
	private String noOfChildBoys = "";
	private String noOfChildGirls = "";
	
	public String getRelationShip() {
		return relationShip;
	}

	@Override
	public String toString() {
		return "ParentDetailsVO [serialNoForExcelUpdate="
				+ serialNoForExcelUpdate + ", dbTs=" + dbTs + ", instId="
				+ instId + ", branchId=" + branchId + ", parentId=" + parentId
				+ ", studentAdmisNo=" + studentAdmisNo + ", parentType="
				+ parentType + ", fatherSalut=" + fatherSalut + ", fatherName="
				+ fatherName + ", fatherReligion=" + fatherReligion
				+ ", fatherMothertongue=" + fatherMothertongue
				+ ", fatherPhoto=" + fatherPhoto + ", fatherQual=" + fatherQual
				+ ", fatherOccuDes=" + fatherOccuDes + ", fatherAnnualIncome="
				+ fatherAnnualIncome + ", fatherMobileNo=" + fatherMobileNo
				+ ", fatherEmail=" + fatherEmail + ", fatherCompany="
				+ fatherCompany + ", fatherOffAdd1=" + fatherOffAdd1
				+ ", fatherOffAdd2=" + fatherOffAdd2 + ", fatherOffAdd3="
				+ fatherOffAdd3 + ", fatherCity=" + fatherCity
				+ ", fatherState=" + fatherState + ", fatherPincode="
				+ fatherPincode + ", fatherOffTele=" + fatherOffTele
				+ ", fatherOldStudent=" + fatherOldStudent
				+ ", fatherPassedOut=" + fatherPassedOut + ", motherSalut="
				+ motherSalut + ", motherName=" + motherName
				+ ", motherReligion=" + motherReligion
				+ ", motherMothertongue=" + motherMothertongue
				+ ", motherPhoto=" + motherPhoto + ", motherQual=" + motherQual
				+ ", motherOccDes=" + motherOccDes + ", motherAnnualIncome="
				+ motherAnnualIncome + ", motherMobileNo=" + motherMobileNo
				+ ", motherEmail=" + motherEmail + ", motherCompany="
				+ motherCompany + ", motherOffAdd1=" + motherOffAdd1
				+ ", motherOffAdd2=" + motherOffAdd2 + ", motherOffAdd3="
				+ motherOffAdd3 + ", motherCity=" + motherCity
				+ ", motherPincode=" + motherPincode + ", motherState="
				+ motherState + ", motherOffTele=" + motherOffTele
				+ ", motherOldStudent=" + motherOldStudent
				+ ", motherPassedOut=" + motherPassedOut + ", localGuardian="
				+ localGuardian + ", guardianSalut=" + guardianSalut
				+ ", guardianName=" + guardianName + ", relationShip="
				+ relationShip + ", guardOccDes=" + guardOccDes
				+ ", guardAnnualIncome=" + guardAnnualIncome + ", guardEmail="
				+ guardEmail + ", guardianPhoto=" + guardianPhoto
				+ ", guardianAdd1=" + guardianAdd1 + ", guardianAdd2="
				+ guardianAdd2 + ", guardianAdd3=" + guardianAdd3
				+ ", guardianCity=" + guardianCity + ", guardianState="
				+ guardianState + ", guardianPincode=" + guardianPincode
				+ ", guardianLandlineNo=" + guardianLandlineNo
				+ ", guardianMobileNo=" + guardianMobileNo + ", noOfChildBoys="
				+ noOfChildBoys + ", noOfChildGirls=" + noOfChildGirls + "]";
	}

	public void setRelationShip(String relationShip) {
		this.relationShip = relationShip;
	}

	public String getGuardOccDes() {
		return guardOccDes;
	}

	public void setGuardOccDes(String guardOccDes) {
		this.guardOccDes = guardOccDes;
	}

	public String getGuardAnnualIncome() {
		return guardAnnualIncome;
	}

	public void setGuardAnnualIncome(String guardAnnualIncome) {
		this.guardAnnualIncome = guardAnnualIncome;
	}

	public String getGuardEmail() {
		return guardEmail;
	}

	public void setGuardEmail(String guardEmail) {
		this.guardEmail = guardEmail;
	}

	
	
	

	public String getInstId() {
		return instId;
	}

	public String getParentType() {
		return parentType;
	}

	public void setParentType(String parentType) {
		this.parentType = parentType;
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

	public MultipartFile getMotherPhoto() {
		return motherPhoto;
	}

	public void setMotherPhoto(MultipartFile motherPhoto) {
		this.motherPhoto = motherPhoto;
	}

	public MultipartFile getGuardianPhoto() {
		return guardianPhoto;
	}

	public void setGuardianPhoto(MultipartFile guardianPhoto) {
		this.guardianPhoto = guardianPhoto;
	}

	public MultipartFile getFatherPhoto() {
		return fatherPhoto;
	}

	public void setFatherPhoto(MultipartFile fatherPhoto) {
		this.fatherPhoto = fatherPhoto;
	}

	public String getNoOfChildBoys() {
		return noOfChildBoys;
	}

	public void setNoOfChildBoys(String noOfChildBoys) {
		this.noOfChildBoys = noOfChildBoys;
	}

	public String getNoOfChildGirls() {
		return noOfChildGirls;
	}

	public void setNoOfChildGirls(String noOfChildGirls) {
		this.noOfChildGirls = noOfChildGirls;
	}

	public String getFatherSalut() {
		return fatherSalut;
	}

	public void setFatherSalut(String fatherSalut) {
		this.fatherSalut = fatherSalut;
	}

	public String getFatherName() {
		return fatherName;
	}

	public void setFatherName(String fatherName) {
		this.fatherName = fatherName;
	}

	public String getFatherReligion() {
		return fatherReligion;
	}

	public void setFatherReligion(String fatherReligion) {
		this.fatherReligion = fatherReligion;
	}

	public String getFatherMothertongue() {
		return fatherMothertongue;
	}

	public void setFatherMothertongue(String fatherMothertongue) {
		this.fatherMothertongue = fatherMothertongue;
	}

	public String getFatherQual() {
		return fatherQual;
	}

	public void setFatherQual(String fatherQual) {
		this.fatherQual = fatherQual;
	}

	public String getFatherOccuDes() {
		return fatherOccuDes;
	}

	public void setFatherOccuDes(String fatherOccuDes) {
		this.fatherOccuDes = fatherOccuDes;
	}

	public String getFatherMobileNo() {
		return fatherMobileNo;
	}

	public void setFatherMobileNo(String fatherMobileNo) {
		this.fatherMobileNo = fatherMobileNo;
	}

	public String getFatherEmail() {
		return fatherEmail;
	}

	public void setFatherEmail(String fatherEmail) {
		this.fatherEmail = fatherEmail;
	}

	public String getFatherCompany() {
		return fatherCompany;
	}

	public void setFatherCompany(String fatherCompany) {
		this.fatherCompany = fatherCompany;
	}

	public String getFatherOffAdd1() {
		return fatherOffAdd1;
	}

	public void setFatherOffAdd1(String fatherOffAdd1) {
		this.fatherOffAdd1 = fatherOffAdd1;
	}

	public String getFatherOffAdd2() {
		return fatherOffAdd2;
	}

	public void setFatherOffAdd2(String fatherOffAdd2) {
		this.fatherOffAdd2 = fatherOffAdd2;
	}

	public String getFatherOffAdd3() {
		return fatherOffAdd3;
	}

	public void setFatherOffAdd3(String fatherOffAdd3) {
		this.fatherOffAdd3 = fatherOffAdd3;
	}

	public String getFatherCity() {
		return fatherCity;
	}

	public void setFatherCity(String fatherCity) {
		this.fatherCity = fatherCity;
	}

	public String getFatherState() {
		return fatherState;
	}

	public void setFatherState(String fatherState) {
		this.fatherState = fatherState;
	}

	public String getFatherOldStudent() {
		return fatherOldStudent;
	}

	public void setFatherOldStudent(String fatherOldStudent) {
		this.fatherOldStudent = fatherOldStudent;
	}

	public String getFatherPassedOut() {
		return fatherPassedOut;
	}

	public void setFatherPassedOut(String fatherPassedOut) {
		this.fatherPassedOut = fatherPassedOut;
	}

	public String getMotherSalut() {
		return motherSalut;
	}

	public void setMotherSalut(String motherSalut) {
		this.motherSalut = motherSalut;
	}

	public String getMotherName() {
		return motherName;
	}

	public void setMotherName(String motherName) {
		this.motherName = motherName;
	}

	public String getMotherReligion() {
		return motherReligion;
	}

	public void setMotherReligion(String motherReligion) {
		this.motherReligion = motherReligion;
	}

	public String getMotherMothertongue() {
		return motherMothertongue;
	}

	public void setMotherMothertongue(String motherMothertongue) {
		this.motherMothertongue = motherMothertongue;
	}

	public String getMotherQual() {
		return motherQual;
	}

	public void setMotherQual(String motherQual) {
		this.motherQual = motherQual;
	}

	public String getMotherOccDes() {
		return motherOccDes;
	}

	public void setMotherOccDes(String motherOccDes) {
		this.motherOccDes = motherOccDes;
	}

	public String getMotherEmail() {
		return motherEmail;
	}

	public void setMotherEmail(String motherEmail) {
		this.motherEmail = motherEmail;
	}

	public String getMotherCompany() {
		return motherCompany;
	}

	public void setMotherCompany(String motherCompany) {
		this.motherCompany = motherCompany;
	}

	public String getMotherOffAdd1() {
		return motherOffAdd1;
	}

	public void setMotherOffAdd1(String motherOffAdd1) {
		this.motherOffAdd1 = motherOffAdd1;
	}

	public String getMotherOffAdd2() {
		return motherOffAdd2;
	}

	public void setMotherOffAdd2(String motherOffAdd2) {
		this.motherOffAdd2 = motherOffAdd2;
	}

	public String getMotherOffAdd3() {
		return motherOffAdd3;
	}

	public void setMotherOffAdd3(String motherOffAdd3) {
		this.motherOffAdd3 = motherOffAdd3;
	}

	public String getMotherCity() {
		return motherCity;
	}

	public void setMotherCity(String motherCity) {
		this.motherCity = motherCity;
	}

	public String getMotherState() {
		return motherState;
	}

	public void setMotherState(String motherState) {
		this.motherState = motherState;
	}

	public String getMotherOldStudent() {
		return motherOldStudent;
	}

	public void setMotherOldStudent(String motherOldStudent) {
		this.motherOldStudent = motherOldStudent;
	}

	public String getMotherPassedOut() {
		return motherPassedOut;
	}

	public void setMotherPassedOut(String motherPassedOut) {
		this.motherPassedOut = motherPassedOut;
	}

	public String getLocalGuardian() {
		return localGuardian;
	}

	public void setLocalGuardian(String localGuardian) {
		this.localGuardian = localGuardian;
	}

	public String getGuardianSalut() {
		return guardianSalut;
	}

	public void setGuardianSalut(String guardianSalut) {
		this.guardianSalut = guardianSalut;
	}

	public String getGuardianName() {
		return guardianName;
	}

	public void setGuardianName(String guardianName) {
		this.guardianName = guardianName;
	}

	public String getGuardianAdd1() {
		return guardianAdd1;
	}

	public void setGuardianAdd1(String guardianAdd1) {
		this.guardianAdd1 = guardianAdd1;
	}

	public String getGuardianAdd2() {
		return guardianAdd2;
	}

	public void setGuardianAdd2(String guardianAdd2) {
		this.guardianAdd2 = guardianAdd2;
	}

	public String getGuardianAdd3() {
		return guardianAdd3;
	}

	public void setGuardianAdd3(String guardianAdd3) {
		this.guardianAdd3 = guardianAdd3;
	}

	public String getGuardianCity() {
		return guardianCity;
	}

	public void setGuardianCity(String guardianCity) {
		this.guardianCity = guardianCity;
	}

	public String getGuardianState() {
		return guardianState;
	}

	public void setGuardianState(String guardianState) {
		this.guardianState = guardianState;
	}

	public String getFatherAnnualIncome() {
		return fatherAnnualIncome;
	}

	public void setFatherAnnualIncome(String fatherAnnualIncome) {
		this.fatherAnnualIncome = fatherAnnualIncome;
	}

	public String getFatherPincode() {
		return fatherPincode;
	}

	public void setFatherPincode(String fatherPincode) {
		this.fatherPincode = fatherPincode;
	}

	public String getFatherOffTele() {
		return fatherOffTele;
	}

	public void setFatherOffTele(String fatherOffTele) {
		this.fatherOffTele = fatherOffTele;
	}

	public String getMotherAnnualIncome() {
		return motherAnnualIncome;
	}

	public void setMotherAnnualIncome(String motherAnnualIncome) {
		this.motherAnnualIncome = motherAnnualIncome;
	}

	public String getMotherMobileNo() {
		return motherMobileNo;
	}

	public void setMotherMobileNo(String motherMobileNo) {
		this.motherMobileNo = motherMobileNo;
	}

	public String getMotherPincode() {
		return motherPincode;
	}

	public void setMotherPincode(String motherPincode) {
		this.motherPincode = motherPincode;
	}

	public String getMotherOffTele() {
		return motherOffTele;
	}

	public void setMotherOffTele(String motherOffTele) {
		this.motherOffTele = motherOffTele;
	}

	public String getGuardianPincode() {
		return guardianPincode;
	}

	public void setGuardianPincode(String guardianPincode) {
		this.guardianPincode = guardianPincode;
	}

	public String getGuardianLandlineNo() {
		return guardianLandlineNo;
	}

	public void setGuardianLandlineNo(String guardianLandlineNo) {
		this.guardianLandlineNo = guardianLandlineNo;
	}

	public String getGuardianMobileNo() {
		return guardianMobileNo;
	}

	public void setGuardianMobileNo(String guardianMobileNo) {
		this.guardianMobileNo = guardianMobileNo;
	}
	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}


}
