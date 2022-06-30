package com.jaw.communication.dao;

import java.io.Serializable;

import org.apache.log4j.Logger;

public class SMSAlert implements Serializable{

	// Logging
	 Logger logger = Logger.getLogger(SMSAlert.class);
	 
	// Properties
	private Integer dbTs;
	private String instId;
	private String branchId;
	private String acTerm ;
	private String alertType;
	private String alertDate ;
	private String attendanceDate="2001-01-01" ;
	private String smsMessage ;
	private String linkId;
	private String mobileNum;
	private String deliveryStatus= "";
	private String msgGrpId= "";
	private String delFlag = "";
	private String rModId = "";
	private String rModTime = "";
	private String rCreId = "";
	private String rCreTime = "";
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
	public String getAcTerm() {
		return acTerm;
	}
	public void setAcTerm(String acTerm) {
		this.acTerm = acTerm;
	}
	public String getAlertType() {
		return alertType;
	}
	public void setAlertType(String alertType) {
		this.alertType = alertType;
	}
	public String getAlertDate() {
		return alertDate;
	}
	public void setAlertDate(String alertDate) {
		this.alertDate = alertDate;
	}
	public String getSmsMessage() {
		return smsMessage;
	}
	public void setSmsMessage(String smsMessage) {
		this.smsMessage = smsMessage;
	}
	public String getLinkId() {
		return linkId;
	}
	public void setLinkId(String linkId) {
		this.linkId = linkId;
	}
	public String getMobileNum() {
		return mobileNum;
	}
	public void setMobileNum(String mobileNum) {
		this.mobileNum = mobileNum;
	}
	public String getDeliveryStatus() {
		return deliveryStatus;
	}
	public void setDeliveryStatus(String deliveryStatus) {
		this.deliveryStatus = deliveryStatus;
	}
	public String getMsgGrpId() {
		return msgGrpId;
	}
	public void setMsgGrpId(String msgGrpId) {
		this.msgGrpId = msgGrpId;
	}
	public String getDelFlag() {
		return delFlag;
	}
	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
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
	public String getAttendanceDate() {
		return attendanceDate;
	}
	public void setAttendanceDate(String attendanceDate) {
		this.attendanceDate = attendanceDate;
	}
	@Override
	public String toString() {
		return "SMSAlert [dbTs=" + dbTs + ", instId=" + instId + ", branchId="
				+ branchId + ", acTerm=" + acTerm + ", alertType=" + alertType
				+ ", alertDate=" + alertDate + ", attendanceDate="
				+ attendanceDate + ", smsMessage=" + smsMessage + ", linkId="
				+ linkId + ", mobileNum=" + mobileNum + ", deliveryStatus="
				+ deliveryStatus + ", msgGrpId=" + msgGrpId + ", delFlag="
				+ delFlag + ", rModId=" + rModId + ", rModTime=" + rModTime
				+ ", rCreId=" + rCreId + ", rCreTime=" + rCreTime + "]";
	}
	

}
