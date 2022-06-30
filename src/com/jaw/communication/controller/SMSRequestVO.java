package com.jaw.communication.controller;

import java.io.Serializable;

import org.apache.log4j.Logger;

public class SMSRequestVO implements Serializable {
	// Logging
	Logger logger = Logger.getLogger(SMSRequestVO.class);

	// Properties
	private String smsMessage ;
	private String reqstCategory ;
	private String generalGrpList= "";
	private String specificGrpList= "";
	private String specificMembrList= "";
	private int rowId;
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
	public int getRowId() {
		return rowId;
	}
	public void setRowId(int rowId) {
		this.rowId = rowId;
	}
	@Override
	public String toString() {
		return "SmsRequestVO [smsMessage=" + smsMessage + ", reqstCategory="
				+ reqstCategory + ", generalGrpList=" + generalGrpList
				+ ", specificGrpList=" + specificGrpList
				+ ", specificMembrList=" + specificMembrList + ", rowId="
				+ rowId + "]";
	}
}
