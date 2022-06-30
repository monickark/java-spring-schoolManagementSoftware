package com.jaw.communication.controller;

import java.io.Serializable;

import org.apache.log4j.Logger;



public class SMSRequestListVO implements Serializable{

	// Logging
	 Logger logger = Logger.getLogger(SMSRequestListVO.class);
	
	// Properties	
	private String branchId;
	private String acTerm ;
	private String smsReqstId;
	private String smsMessage ;
	private String reqstCategory ;
	private String generalGrpList= "";
	private String specificGrpList= "";
	private String specificMembrList= "";
	private String reqstStatus= "";
	private String reqstType ;
	private int rowId;
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
	public String getSmsReqstId() {
		return smsReqstId;
	}
	public void setSmsReqstId(String smsReqstId) {
		this.smsReqstId = smsReqstId;
	}
	public String getSmsMessage() {
		return smsMessage;
	}
	public void setSmsMessage(String smsMessage) {
		this.smsMessage = smsMessage;
	}
	public String getReqstCategory() {
		return reqstCategory;
	}
	public void setReqstCategory(String reqstCategory) {
		this.reqstCategory = reqstCategory;
	}
	public String getGeneralGrpList() {
		return generalGrpList;
	}
	public void setGeneralGrpList(String generalGrpList) {
		this.generalGrpList = generalGrpList;
	}
	public String getSpecificGrpList() {
		return specificGrpList;
	}
	public void setSpecificGrpList(String specificGrpList) {
		this.specificGrpList = specificGrpList;
	}
	public String getSpecificMembrList() {
		return specificMembrList;
	}
	public void setSpecificMembrList(String specificMembrList) {
		this.specificMembrList = specificMembrList;
	}
	public String getReqstStatus() {
		return reqstStatus;
	}
	public void setReqstStatus(String reqstStatus) {
		this.reqstStatus = reqstStatus;
	}
	public String getReqstType() {
		return reqstType;
	}
	public void setReqstType(String reqstType) {
		this.reqstType = reqstType;
	}
	public int getRowId() {
		return rowId;
	}
	public void setRowId(int rowId) {
		this.rowId = rowId;
	}
	@Override
	public String toString() {
		return "SMSRequestListVO [branchId=" + branchId + ", acTerm=" + acTerm
				+ ", smsReqstId=" + smsReqstId + ", smsMessage=" + smsMessage
				+ ", reqstCategory=" + reqstCategory + ", generalGrpList="
				+ generalGrpList + ", specificGrpList=" + specificGrpList
				+ ", specificMembrList=" + specificMembrList + ", reqstStatus="
				+ reqstStatus + ", reqstType=" + reqstType + ", rowId=" + rowId
				+ "]";
	}

}
