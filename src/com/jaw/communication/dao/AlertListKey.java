package com.jaw.communication.dao;

import java.io.Serializable;
import java.util.Arrays;

import org.apache.log4j.Logger;

public class AlertListKey implements Serializable{
	// Logging
	 Logger logger = Logger.getLogger(AlertListKey.class);

	// Properties
	private Integer dbTs;
	private String instId;
	private String branchId;
	private String reqstCategory ;
	private String[] generalGrpList;
	private String fromDate;
	private String toDate;
	private String important ;
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
	public String getReqstCategory() {
		return reqstCategory;
	}
	public void setReqstCategory(String reqstCategory) {
		this.reqstCategory = reqstCategory;
	}
	public String[] getGeneralGrpList() {
		return generalGrpList;
	}
	public void setGeneralGrpList(String[] generalGrpList) {
		this.generalGrpList = generalGrpList;
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
	public String getImportant() {
		return important;
	}
	public void setImportant(String important) {
		this.important = important;
	}
	public String getAcTerm() {
		return acTerm;
	}
	public void setAcTerm(String acTerm) {
		this.acTerm = acTerm;
	}
	@Override
	public String toString() {
		return "AlertListKey [dbTs=" + dbTs + ", instId=" + instId
				+ ", branchId=" + branchId + ", reqstCategory=" + reqstCategory
				+ ", generalGrpList=" + Arrays.toString(generalGrpList)
				+ ", fromDate=" + fromDate + ", toDate=" + toDate
				+ ", important=" + important + ", acTerm=" + acTerm + "]";
	}
	
	
}
