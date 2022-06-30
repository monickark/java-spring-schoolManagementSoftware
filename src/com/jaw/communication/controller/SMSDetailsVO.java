package com.jaw.communication.controller;

import java.io.Serializable;

import org.apache.log4j.Logger;

import com.jaw.communication.dao.SMSDetails;

public class SMSDetailsVO implements Serializable{

	// Logging
	 Logger logger = Logger.getLogger(SMSDetailsVO.class);
	
	// Properties
	private String branchId;
	private String acTerm ;
	private String smsReqstId;
	private int smsSrlNo ;
	private String mobileNumList= "" ;
	private int mobileNumCnt=0;
	private int deliveredNumCnt=0;
	private int unDeliveredNumCnt=0;
	private String unDeliveredNumList= "";
	private String detailsStatus= "";
	private String msgGrpId= "";
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
	public int getSmsSrlNo() {
		return smsSrlNo;
	}
	public void setSmsSrlNo(int smsSrlNo) {
		this.smsSrlNo = smsSrlNo;
	}
	public String getMobileNumList() {
		return mobileNumList;
	}
	public void setMobileNumList(String mobileNumList) {
		this.mobileNumList = mobileNumList;
	}
	public int getMobileNumCnt() {
		return mobileNumCnt;
	}
	public void setMobileNumCnt(int mobileNumCnt) {
		this.mobileNumCnt = mobileNumCnt;
	}
	public int getDeliveredNumCnt() {
		return deliveredNumCnt;
	}
	public void setDeliveredNumCnt(int deliveredNumCnt) {
		this.deliveredNumCnt = deliveredNumCnt;
	}
	public int getUnDeliveredNumCnt() {
		return unDeliveredNumCnt;
	}
	public void setUnDeliveredNumCnt(int unDeliveredNumCnt) {
		this.unDeliveredNumCnt = unDeliveredNumCnt;
	}
	public String getUnDeliveredNumList() {
		return unDeliveredNumList;
	}
	public void setUnDeliveredNumList(String unDeliveredNumList) {
		this.unDeliveredNumList = unDeliveredNumList;
	}
	public String getDetailsStatus() {
		return detailsStatus;
	}
	public void setDetailsStatus(String detailsStatus) {
		this.detailsStatus = detailsStatus;
	}
	public String getMsgGrpId() {
		return msgGrpId;
	}
	public void setMsgGrpId(String msgGrpId) {
		this.msgGrpId = msgGrpId;
	}
	public int getRowId() {
		return rowId;
	}
	public void setRowId(int rowId) {
		this.rowId = rowId;
	}
	@Override
	public String toString() {
		return "SMSDetailsVO [branchId=" + branchId + ", acTerm=" + acTerm
				+ ", smsReqstId=" + smsReqstId + ", smsSrlNo=" + smsSrlNo
				+ ", mobileNumList=" + mobileNumList + ", mobileNumCnt="
				+ mobileNumCnt + ", deliveredNumCnt=" + deliveredNumCnt
				+ ", unDeliveredNumCnt=" + unDeliveredNumCnt
				+ ", unDeliveredNumList=" + unDeliveredNumList
				+ ", detailsStatus=" + detailsStatus + ", msgGrpId=" + msgGrpId
				+ ", rowId=" + rowId + "]";
	}
	

}
