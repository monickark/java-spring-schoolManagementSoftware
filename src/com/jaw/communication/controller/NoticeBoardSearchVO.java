package com.jaw.communication.controller;

import java.io.Serializable;

import org.apache.log4j.Logger;

public class NoticeBoardSearchVO implements Serializable{
	
	// Logging
	 Logger logger = Logger.getLogger(NoticeBoardVO.class);
	
	// Properties	
	private String acTerm ;	
	private String noticeType ;
	private String fromDate;
	private String toDate;
	public String getAcTerm() {
		return acTerm;
	}
	public void setAcTerm(String acTerm) {
		this.acTerm = acTerm;
	}
	public String getNoticeType() {
		return noticeType;
	}
	public void setNoticeType(String noticeType) {
		this.noticeType = noticeType;
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
	@Override
	public String toString() {
		return "NoticeBoardSearchVO [acTerm=" + acTerm + ", noticeType="
				+ noticeType + ", fromDate=" + fromDate + ", toDate=" + toDate
				+ "]";
	}

}
