package com.jaw.student.admission.controller;

import java.io.Serializable;

public class CommunicationDetailsVO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String serialNoForExcelUpdate = "";
	private Integer dbTs;
	private String instId = "";
	private String branchId = "";
	private String studentAdmisNo = "";
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

	public String getRcity() {
		return rcity;
	}

	public void setRcity(String rcity) {
		this.rcity = rcity;
	}

	@Override
	public String toString() {
		return "CommunicationDetailsVO [serialNoForExcelUpdate="
				+ serialNoForExcelUpdate + ", dbTs=" + dbTs + ", instId="
				+ instId + ", branchId=" + branchId + ", studentAdmisNo="
				+ studentAdmisNo + ", emergContactNo=" + emergContactNo
				+ ", mobileNoSms=" + mobileNoSms + ", residenceAdd1="
				+ residenceAdd1 + ", residenceAdd2=" + residenceAdd2
				+ ", residenceAdd3=" + residenceAdd3 + ", rcity=" + rcity
				+ ", rstate=" + rstate + ", rpincode=" + rpincode
				+ ", rresidenceTele=" + rresidenceTele + ", communicationAdd1="
				+ communicationAdd1 + ", communicationAdd2="
				+ communicationAdd2 + ", communicationAdd3="
				+ communicationAdd3 + ", city=" + city + ", state=" + state
				+ ", pincode=" + pincode + ", residenceTele=" + residenceTele
				+ "]";
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

}
