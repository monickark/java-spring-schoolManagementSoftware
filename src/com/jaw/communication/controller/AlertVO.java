package com.jaw.communication.controller;

import java.io.Serializable;
import java.util.Arrays;

import org.apache.log4j.Logger;

import com.jaw.communication.dao.Alert;

public class AlertVO implements Serializable{

	// Logging
	 Logger logger = Logger.getLogger(AlertVO.class);
	
	// Properties	
	private String acTerm ;
	private String alertSerialNo;
	private String reqstCategory ;
	private String generalGrpList= "";
	private String specificGrpList= "";
	private String alertMessage ;
	private String fromDate;
	private String toDate;
	private String alertStop;
	private String important;
	private int rowId;
	private String[] generalGrpListArray;
	private String[] specificGrpListArray;
	public String getAcTerm() {
		return acTerm;
	}
	public void setAcTerm(String acTerm) {
		this.acTerm = acTerm;
	}
	public String getAlertSerialNo() {
		return alertSerialNo;
	}
	public void setAlertSerialNo(String alertSerialNo) {
		this.alertSerialNo = alertSerialNo;
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
	public String getAlertMessage() {
		return alertMessage;
	}
	public void setAlertMessage(String alertMessage) {
		this.alertMessage = alertMessage;
	}
	public String getFromDate() {
		return fromDate;
	}
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	public String getToDate() {
		return toDate;
	}
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	public String getAlertStop() {
		return alertStop;
	}
	public void setAlertStop(String alertStop) {
		this.alertStop = alertStop;
	}
	public String getImportant() {
		return important;
	}
	public void setImportant(String important) {
		this.important = important;
	}
	public int getRowId() {
		return rowId;
	}
	public void setRowId(int rowId) {
		this.rowId = rowId;
	}
	public String[] getGeneralGrpListArray() {
		return generalGrpListArray;
	}
	public void setGeneralGrpListArray(String[] generalGrpListArray) {
		this.generalGrpListArray = generalGrpListArray;
	}
	public String[] getSpecificGrpListArray() {
		return specificGrpListArray;
	}
	public void setSpecificGrpListArray(String[] specificGrpListArray) {
		this.specificGrpListArray = specificGrpListArray;
	}
	@Override
	public String toString() {
		return "AlertVO [acTerm=" + acTerm + ", alertSerialNo=" + alertSerialNo
				+ ", reqstCategory=" + reqstCategory + ", generalGrpList="
				+ generalGrpList + ", specificGrpList=" + specificGrpList
				+ ", alertMessage=" + alertMessage + ", fromDate=" + fromDate
				+ ", toDate=" + toDate + ", alertStop=" + alertStop
				+ ", important=" + important + ", rowId=" + rowId
				+ ", generalGrpListArray="
				+ Arrays.toString(generalGrpListArray)
				+ ", specificGrpListArray="
				+ Arrays.toString(specificGrpListArray) + "]";
	}
	
}