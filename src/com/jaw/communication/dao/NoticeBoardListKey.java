package com.jaw.communication.dao;

import java.io.Serializable;

import org.apache.log4j.Logger;

public class NoticeBoardListKey implements Serializable{
	// Logging
	 Logger logger = Logger.getLogger(NoticeBoardListKey.class);

	// Properties
	private Integer dbTs;
	private String instId;
	private String branchId;
	private String noticeType;
	private String fromDate;
	private String toDate;
	private String acTerm ;
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
	public String getAcTerm() {
		return acTerm;
	}
	public void setAcTerm(String acTerm) {
		this.acTerm = acTerm;
	}
	@Override
	public String toString() {
		return "NoticeBoardListKey [dbTs=" + dbTs + ", instId=" + instId
				+ ", branchId=" + branchId + ", noticeType=" + noticeType
				+ ", fromDate=" + fromDate + ", toDate=" + toDate + ", acTerm="
				+ acTerm + "]";
	}
	

}
