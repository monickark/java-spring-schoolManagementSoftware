package com.jaw.student.admission.dao;

import org.apache.log4j.Logger;
import java.io.Serializable;
import com.jaw.common.constants.AuditConstant;

public class CommunicationDetails implements Serializable {

	Logger logger = Logger.getLogger(CommunicationDetails.class);

	private Integer dbTs;
	private String instId ;
	private String branchId ;
	private String studentAdmisNo;
	private String emergContactNo = "";
	private String mobileNoSms = "";
	private String residenceAdd1 = "";
	private String residenceAdd2 = "";
	private String residenceAdd3 = "";
	private String rcity = "";
	private String rstate = "";
	private String rpincode = "";
	private String rresidenceTele = "";
	private String communicationAdd1 = "";
	private String communicationAdd2 = "";
	private String communicationAdd3 = "";
	private String city = "";
	private String state = "";
	private String pincode = "";
	private String residenceTele = "";
	private String delFlg = "";
	private String rModId = "";
	private String rModTime;
	private String rCreId = "";
	private String rCreTime;

	@Override
	public String toString() {
		return "CommunicationDetails [logger=" + logger + ", dbTs=" + dbTs
				+ ", instId=" + instId + ", branchId=" + branchId
				+ ", studentAdmisNo=" + studentAdmisNo + ", emergContactNo="
				+ emergContactNo + ", mobileNoSms=" + mobileNoSms
				+ ", residenceAdd1=" + residenceAdd1 + ", residenceAdd2="
				+ residenceAdd2 + ", residenceAdd3=" + residenceAdd3
				+ ", rcity=" + rcity + ", rstate=" + rstate + ", rpincode="
				+ rpincode + ", rresidenceTele=" + rresidenceTele
				+ ", communicationAdd1=" + communicationAdd1
				+ ", communicationAdd2=" + communicationAdd2
				+ ", communicationAdd3=" + communicationAdd3 + ", city=" + city
				+ ", state=" + state + ", pincode=" + pincode
				+ ", residenceTele=" + residenceTele + ", delFlg=" + delFlg
				+ ", rModId=" + rModId + ", rModTime=" + rModTime + ", rCreId="
				+ rCreId + ", rCreTime=" + rCreTime + "]";
	}

	public String getRcity() {
		return rcity;
	}

	public void setRcity(String rcity) {
		this.rcity = rcity;
	}

	public String getRstate() {
		return rstate;
	}

	public void setRstate(String rstate) {
		this.rstate = rstate;
	}

	public String getRpincode() {
		return rpincode;
	}

	public void setRpincode(String rpincode) {
		this.rpincode = rpincode;
	}

	public String getRresidenceTele() {
		return rresidenceTele;
	}

	public void setRresidenceTele(String rresidenceTele) {
		this.rresidenceTele = rresidenceTele;
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

	public String getEmergContactNo() {
		return emergContactNo;
	}

	public void setEmergContactNo(String emergContactNo) {
		this.emergContactNo = emergContactNo;
	}

	public String getMobileNoSms() {
		return mobileNoSms;
	}

	public void setMobileNoSms(String mobileNoSms) {
		this.mobileNoSms = mobileNoSms;
	}

	public String getResidenceAdd1() {
		return residenceAdd1;
	}

	public void setResidenceAdd1(String residenceAdd1) {
		this.residenceAdd1 = residenceAdd1;
	}

	public String getResidenceAdd2() {
		return residenceAdd2;
	}

	public void setResidenceAdd2(String residenceAdd2) {
		this.residenceAdd2 = residenceAdd2;
	}

	public String getResidenceAdd3() {
		return residenceAdd3;
	}

	public void setResidenceAdd3(String residenceAdd3) {
		this.residenceAdd3 = residenceAdd3;
	}

	public String getCommunicationAdd1() {
		return communicationAdd1;
	}

	public void setCommunicationAdd1(String communicationAdd1) {
		this.communicationAdd1 = communicationAdd1;
	}

	public String getCommunicationAdd2() {
		return communicationAdd2;
	}

	public void setCommunicationAdd2(String communicationAdd2) {
		this.communicationAdd2 = communicationAdd2;
	}

	public String getCommunicationAdd3() {
		return communicationAdd3;
	}

	public void setCommunicationAdd3(String communicationAdd3) {
		this.communicationAdd3 = communicationAdd3;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getPincode() {
		return pincode;
	}

	public void setPincode(String pincode) {
		this.pincode = pincode;
	}

	public String getResidenceTele() {
		return residenceTele;
	}

	public void setResidenceTele(String residenceTele) {
		this.residenceTele = residenceTele;
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
	public String toStringForAuditInstMasterRecord() {

		StringBuffer stringBuffer = new StringBuffer().append("DB_TS=")
				.append(getDbTs()).append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("INST_ID=").append(getInstId())
				.append(AuditConstant.AUDIT_REC_SEPERATOR).append("BRANCH_ID=")
				.append(getBranchId())
				.append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("STUDENT_ADMIS_NO=").append(getStudentAdmisNo())
				.append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("EMERG_CONTACT_NO=").append(getEmergContactNo())
				.append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("MOBILE_NO_SMS=").append(getMobileNoSms())
				.append(AuditConstant.AUDIT_REC_SEPERATOR).append("RES_ADD1=")
				.append(getResidenceAdd1())
				.append(AuditConstant.AUDIT_REC_SEPERATOR).append("RES_ADD2=")
				.append(getResidenceAdd2())
				.append(AuditConstant.AUDIT_REC_SEPERATOR).append("RES_ADD3=")
				.append(getResidenceAdd3())
				.append(AuditConstant.AUDIT_REC_SEPERATOR).append("RES_CITY=")
				.append(getRcity())
				.append(AuditConstant.AUDIT_REC_SEPERATOR).append("RES_STATE=")
				.append(getRstate())
				.append(AuditConstant.AUDIT_REC_SEPERATOR).append("PINCODE=")
				.append(getRpincode())
				.append(AuditConstant.AUDIT_REC_SEPERATOR).append("RES_TELE=")
				.append(getRresidenceTele())
				.append(AuditConstant.AUDIT_REC_SEPERATOR).append("COMM_ADD1=")
				.append(getCommunicationAdd1())
				.append(AuditConstant.AUDIT_REC_SEPERATOR).append("COMM_ADD2=")
				.append(getCommunicationAdd2())
				.append(AuditConstant.AUDIT_REC_SEPERATOR).append("COMM_ADD3=")
				.append(getCommunicationAdd3())
				.append(AuditConstant.AUDIT_REC_SEPERATOR).append("COMM_CITY=")
				.append(getCity())
				.append(AuditConstant.AUDIT_REC_SEPERATOR).append("COMM_STATE=")
				.append(getState())
				.append(AuditConstant.AUDIT_REC_SEPERATOR).append("COMM_PINCODE=")
				.append(getPincode())
				.append(AuditConstant.AUDIT_REC_SEPERATOR).append("COMM_TELE=")
				.append(getResidenceTele())
				.append(AuditConstant.AUDIT_REC_SEPERATOR).append("DEL_FLG=")
				.append(getDelFlg())
				.append(AuditConstant.AUDIT_REC_SEPERATOR).append("R_MOD_ID=")
				.append(getrModId())
				.append(AuditConstant.AUDIT_REC_SEPERATOR).append("R_MOD_TIME=")
				.append(getrModTime())
				.append(AuditConstant.AUDIT_REC_SEPERATOR).append("R_CRE_ID=")
				.append(getrCreId())
				.append(AuditConstant.AUDIT_REC_SEPERATOR).append("R_CRE_TIME=")
				.append(getrCreTime())
				.append(AuditConstant.AUDIT_REC_SEPERATOR);

		logger.debug("String formed for audit is :" + stringBuffer.toString());
		return stringBuffer.toString();
	}

	// method for create InstituteMasterKey String for Audit
	public String toStringForAuditInstMasterKey() {

		StringBuffer stringBuffer = new StringBuffer().append("INST_ID=")
				.append(getInstId()).append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("BRANCH_ID=").append(getBranchId())
				.append(AuditConstant.AUDIT_REC_SEPERATOR)
				.append("STUDENT_ADMIS_NO=").append(getStudentAdmisNo())
				.append(AuditConstant.AUDIT_REC_SEPERATOR);

		return stringBuffer.toString();
	}
	
	@Override
	public boolean equals(Object other) {
	    if (!(other instanceof CommunicationDetails)) {
	        return false;
	    }

	    CommunicationDetails that = (CommunicationDetails) other;

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
