package com.jaw.communication.controller;

import java.io.Serializable;

import org.apache.log4j.Logger;

import com.jaw.communication.dao.NoticeBoard;

public class NoticeBoardVO implements Serializable{
	
	// Logging
	 Logger logger = Logger.getLogger(NoticeBoardVO.class);
	
	// Properties	
	private String acTerm ;
	private String noticeSerialNo;
	private String noticeType ;
	private String noticeName ;
	private String noticeDesc ;
	private String fromDate;
	private String toDate;
	private String informParent;
	private String isImportant;
	private int rowId;
	public String getAcTerm() {
		return acTerm;
	}
	public void setAcTerm(String acTerm) {
		this.acTerm = acTerm;
	}
	public String getNoticeSerialNo() {
		return noticeSerialNo;
	}
	public void setNoticeSerialNo(String noticeSerialNo) {
		this.noticeSerialNo = noticeSerialNo;
	}
	public String getNoticeType() {
		return noticeType;
	}
	public void setNoticeType(String noticeType) {
		this.noticeType = noticeType;
	}
	public String getNoticeName() {
		return noticeName;
	}
	public void setNoticeName(String noticeName) {
		this.noticeName = noticeName;
	}
	public String getNoticeDesc() {
		return noticeDesc;
	}
	public void setNoticeDesc(String noticeDesc) {
		this.noticeDesc = noticeDesc;
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
	public String getInformParent() {
		return informParent;
	}
	public void setInformParent(String informParent) {
		this.informParent = informParent;
	}
	public String getIsImportant() {
		return isImportant;
	}
	public void setIsImportant(String isImportant) {
		this.isImportant = isImportant;
	}
	public int getRowId() {
		return rowId;
	}
	public void setRowId(int rowId) {
		this.rowId = rowId;
	}
	@Override
	public String toString() {
		return "NoticeBoardVO [acTerm=" + acTerm + ", noticeSerialNo="
				+ noticeSerialNo + ", noticeType=" + noticeType
				+ ", noticeName=" + noticeName + ", noticeDesc=" + noticeDesc
				+ ", fromDate=" + fromDate + ", toDate=" + toDate
				+ ", informParent=" + informParent + ", isImportant="
				+ isImportant + ", rowId=" + rowId + "]";
	}
	
	
}
