package com.jaw.student.admission.dao;

import org.apache.log4j.Logger;

import com.jaw.common.constants.AuditConstant;
import java.io.Serializable;
public class ParentDetails implements Serializable{
	
	Logger logger=Logger.getLogger(ParentDetails.class);
	private Integer dbTs;
	private String instId;
	private String branchId;
	private String parentId;
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	@Override
	public String toString() {
		return "ParentDetails [logger=" + logger + ", dbTs=" + dbTs
				+ ", instId=" + instId + ", branchId=" + branchId
				+ ", parentId=" + parentId + ", studentAdmisNo="
				+ studentAdmisNo + ", fatherSalut=" + fatherSalut
				+ ", fatherName=" + fatherName + ", fatherReligion="
				+ fatherReligion + ", fatherMothertongue=" + fatherMothertongue
				+ ", fatherQual=" + fatherQual + ", fatherOccuDes="
				+ fatherOccuDes + ", fatherAnnualIncome=" + fatherAnnualIncome
				+ ", fatherMobileNo=" + fatherMobileNo + ", fatherEmail="
				+ fatherEmail + ", fatherCompany=" + fatherCompany
				+ ", fatherOffAdd1=" + fatherOffAdd1 + ", fatherOffAdd2="
				+ fatherOffAdd2 + ", fatherOffAdd3=" + fatherOffAdd3
				+ ", fatherCity=" + fatherCity + ", fatherState=" + fatherState
				+ ", fatherPincode=" + fatherPincode + ", fatherOffTele="
				+ fatherOffTele + ", fatherOldStudent=" + fatherOldStudent
				+ ", fatherPassedOut=" + fatherPassedOut + ", motherSalut="
				+ motherSalut + ", motherName=" + motherName
				+ ", motherReligion=" + motherReligion
				+ ", motherMothertongue=" + motherMothertongue
				+ ", motherQual=" + motherQual + ", motherOccDes="
				+ motherOccDes + ", motherAnnualIncome=" + motherAnnualIncome
				+ ", motherMobileNo=" + motherMobileNo + ", motherEmail="
				+ motherEmail + ", motherCompany=" + motherCompany
				+ ", motherOffAdd1=" + motherOffAdd1 + ", motherOffAdd2="
				+ motherOffAdd2 + ", motherOffAdd3=" + motherOffAdd3
				+ ", motherCity=" + motherCity + ", motherPincode="
				+ motherPincode + ", motherState=" + motherState
				+ ", motherOffTele=" + motherOffTele + ", motherOldStudent="
				+ motherOldStudent + ", motherPassedOut=" + motherPassedOut
				+ ", localGuardian=" + localGuardian + ", guardianSalut="
				+ guardianSalut + ", guardianName=" + guardianName
				+ ", guardianAdd1=" + guardianAdd1 + ", guardianAdd2="
				+ guardianAdd2 + ", guardianAdd3=" + guardianAdd3
				+ ", guardianCity=" + guardianCity + ", guardianState="
				+ guardianState + ", guardianPincode=" + guardianPincode
				+ ", guardianLandlineNo=" + guardianLandlineNo
				+ ", guardianMobileNo=" + guardianMobileNo + ", relationShip="
				+ relationShip + ", guardOccDes=" + guardOccDes
				+ ", guardAnnualIncome=" + guardAnnualIncome + ", guardEmail="
				+ guardEmail + ", noOfChildBoys=" + noOfChildBoys
				+ ", noOfChildGirls=" + noOfChildGirls + ", delFlg=" + delFlg
				+ ", rModId=" + rModId + ", rModTime=" + rModTime + ", rCreId="
				+ rCreId + ", rCreTime=" + rCreTime + "]";
	}

	private String studentAdmisNo;
	private String fatherSalut = "";
	private String fatherName = "";
	private String fatherReligion = "";
	private String fatherMothertongue = "";
	
	private String fatherQual = "";
	private String fatherOccuDes = "";
	private String fatherAnnualIncome = "";
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
	private String guardianAdd1 = "";
	private String guardianAdd2 = "";
	private String guardianAdd3 = "";
	private String guardianCity = "";
	private String guardianState = "";
	private String guardianPincode = "";
	private String guardianLandlineNo = "";
	private String guardianMobileNo = "";
	private String relationShip="";
	private String guardOccDes="";
	private String guardAnnualIncome="";
	private String guardEmail="";
	
	public Logger getLogger() {
		return logger;
	}
	public void setLogger(Logger logger) {
		this.logger = logger;
	}
	public String getRelationShip() {
		return relationShip;
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

	private String noOfChildBoys = "";
	private String noOfChildGirls = "";
	private String delFlg = "";
	private String rModId = "";
	private String rModTime ;
	private String rCreId = "";
	private String rCreTime;
	
	
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
	public String getMotherPincode() {
		return motherPincode;
	}
	public void setMotherPincode(String motherPincode) {
		this.motherPincode = motherPincode;
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
	public String getFatherPincode() {
		return fatherPincode;
	}
	public void setFatherPincode(String fatherPincode) {
		this.fatherPincode = fatherPincode;
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
	
	public String getGuardianState() {
		return guardianState;
	}
	public void setGuardianState(String guardianState) {
		this.guardianState = guardianState;
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
	public String getStudentAdmisNo() {
		return studentAdmisNo;
	}
	public void setStudentAdmisNo(String studentAdmisNo) {
		this.studentAdmisNo = studentAdmisNo;
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
	public String getFatherAnnualIncome() {
		return fatherAnnualIncome;
	}
	public void setFatherAnnualIncome(String fatherAnnualIncome) {
		this.fatherAnnualIncome = fatherAnnualIncome;
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
	public String getFatherOffTele() {
		return fatherOffTele;
	}
	public void setFatherOffTele(String fatherOffTele) {
		this.fatherOffTele = fatherOffTele;
	}
	public String getFatherOldStudent() {
		if(fatherOldStudent==null) fatherOldStudent="N";
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
	public String getMotherOffTele() {
		return motherOffTele;
	}
	public void setMotherOffTele(String motherOffTele) {
		this.motherOffTele = motherOffTele;
	}
	public String getMotherOldStudent() {
		if(motherOldStudent==null) motherOldStudent="N";
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
		if(localGuardian==null)localGuardian="N";
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
				.append("PARENT_ID=")
				.append(getParentId())
				.append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("STUDENT_ADMIS_NO=")
				.append(getStudentAdmisNo())
				.append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("FATHER_SALUT=")
				.append(getFatherSalut())
				.append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("FATHER_NAME=")
				.append(getFatherName())
				.append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("FATHER_RELIGION=")
				.append(getFatherReligion())
				.append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("FATHER_MOTHERTONGUE=")
				.append(getFatherMothertongue())
				.append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("FATHER_QUAL=")
				.append(getFatherQual())
				.append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("FATHER_OCCU_DES=")
				.append(getFatherOccuDes())
				.append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("FATHER_ANNUAL_INCOME=")
				.append(getFatherAnnualIncome())
				.append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("FATHER_MOBILE_N0=")
				.append(getFatherMobileNo())
				.append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("FATHER_EMAIL=")
				.append(getFatherEmail())
				.append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("FATHER_COMPANY=")
				.append(getFatherCompany())
				.append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("FATHER_OFF_ADD1=")
				.append(getFatherOffAdd1())
				.append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("FATHER_OFF_ADD2=")
				.append(getFatherOffAdd2())
				.append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("FATHER_OFF_ADD3=")
				.append(getFatherOffAdd3())
				.append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("FATHER_CITY=")
				.append(getFatherCity())
				.append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("FATHER_STATE=")
				.append(getFatherState())
				.append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("FATHER_PINCODE=")
				.append(getFatherPincode())
				.append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("FATHER_OFF_TELE=")
				.append(getFatherOffTele())
				.append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("FATHER_OLD_STUDENT=")
				.append(getFatherOldStudent())
				.append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("FATHER_PASSED_OUT=")
				.append(getFatherPassedOut())
				.append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("MOTHER_SALUT=")
				.append(getMotherSalut())
				.append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("MOTHER_NAME=")
				.append(getMotherName())
				.append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("MOTHER_RELIGION=")
				.append(getMotherReligion())
				.append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("MOTHER_MOTHERTONGUE=")
				.append(getMotherMothertongue())
				.append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("MOTHER_QUAL=")
				.append(getMotherQual())
				.append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("MOTHER_OCC_DES=")
				.append(getMotherOccDes())
				.append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("MOTHER_ANNUAL_INCOME=")
				.append(getMotherAnnualIncome())
				.append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("MOTHER_MOBILE_NO=")
				.append(getMotherMobileNo())
				.append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("MOTHER_EMAIL=")
				.append(getMotherEmail())
				.append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("MOTHER_COMPANY=")
				.append(getMotherCompany())
				.append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("MOTHER_OFF_ADD1=")
				.append(getMotherOffAdd1())
				.append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("MOTHER_OFF_ADD2=")
				.append(getMotherOffAdd2())
				.append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("MOTHER_OFF_ADD3=")
				.append(getMotherOffAdd3())
				.append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("MOTHER_CITY=")
				.append(getMotherCity())
				.append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("MOTHER_STATE=")
				.append(getMotherState())
				.append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("MOTHER_PINCODE=")
				.append(getMotherPincode())
				.append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("MOTHER_OFF_TEL=")
				.append(getMotherOffTele())
				.append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("MOTHER_OLD_STUDENT=")
				.append(getMotherOldStudent())
				.append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("MOTHER_PASSED_OUT=")
				.append(getMotherPassedOut())
				.append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("LOCAL_GUARDIAN=")
				.append(getLocalGuardian())
				.append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("GUARDIAN_SALUT=")
				.append(getGuardianSalut())
				.append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("GUARDIAN_NAME=")
				.append(getGuardianName())
				.append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("RELATIONSHIP=")
				.append(getRelationShip())
				.append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("GUARDIAN_OCC_DES=")
				.append(getGuardOccDes())
				.append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("GUARDIAN_ANNUAL_INCOME=")
				.append(getGuardAnnualIncome())
				.append(AuditConstant.AUDIT_REC_SEPERATOR)				
				.append("GUARDIAN_ADD1=")
				.append(getGuardianAdd1())
				.append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("GUARDIAN_ADD2=")
				.append(getGuardianAdd2())
				.append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("GUARDIAN_ADD3=")
				.append(getGuardianAdd3())
				.append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("GUARDIAN_CITY=")
				.append(getGuardianCity())
				.append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("GUARDIAN_STATE=")
				.append(getGuardianState())
				.append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("GUARDIAN_PINCODE=")
				.append(getGuardianPincode())
				.append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("GUARDIAN_LANDLINE_NO=")
				.append(getGuardianLandlineNo())
				.append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("GUARDIAN_MOBILE_NO=")
				.append(getGuardianMobileNo())
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
				.append("NO_OF_CHILD_BOYS=")
				.append(getNoOfChildBoys())
				.append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("NO_OF_CHILD_GIRLS=")
				.append(getNoOfChildGirls())
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
				.append("PARENT_ID=")
				.append(getParentId())
				.append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("STUDENT_ADMIS_NO=")
				.append(getStudentAdmisNo())
				.append(AuditConstant.AUDIT_REC_SEPERATOR);
				
				return stringBuffer.toString();
			}
			
			@Override
			public boolean equals(Object other) {
			    if (!(other instanceof ParentDetails)) {
			        return false;
			    }

			    ParentDetails that = (ParentDetails) other;

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
