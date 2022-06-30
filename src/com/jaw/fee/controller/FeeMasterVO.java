package com.jaw.fee.controller;

import java.io.Serializable;


/**
 * @author Gritz Horizons Ltd 1
 *
 */
public class FeeMasterVO implements Serializable {
	
	private String acTerm="";
	private String feeCategory="";
	private String feeTerm="";
	private String feeType="";
	private String course="";
	private String term="";
	private String courseVariant = "";
	private int feeAmt;
	private int rowid;
	
	
	public String getCourseVariant() {
		return courseVariant;
	}
	public void setCourseVariant(String courseVariant) {
		this.courseVariant = courseVariant;
	}
	public int getRowid() {
		return rowid;
	}
	public void setRowid(int rowid) {
		this.rowid = rowid;
	}
	public String getFeeCategory() {
		return feeCategory;
	}
	public void setFeeCategory(String feeCategory) {
		this.feeCategory = feeCategory;
	}
	public String getFeeType() {
		return feeType;
	}
	public void setFeeType(String feeType) {
		this.feeType = feeType;
	}

	public String getAcTerm() {
		return acTerm;
	}
	public void setAcTerm(String acTerm) {
		this.acTerm = acTerm;
	}
	public String getFeeTerm() {
		return feeTerm;
	}
	public void setFeeTerm(String feeTerm) {
		this.feeTerm = feeTerm;
	}
	public String getCourse() {
		return course;
	}
	public void setCourse(String course) {
		this.course = course;
	}
	public String getTerm() {
		return term;
	}
	public void setTerm(String term) {
		this.term = term;
	}
	public int getFeeAmt() {
		return feeAmt;
	}
	public void setFeeAmt(int feeAmt) {
		this.feeAmt = feeAmt;
	}
	@Override
	public String toString() {
		return "FeeMasterVO [acTerm=" + acTerm + ", feeCategory=" + feeCategory
				+ ", feeTerm=" + feeTerm + ", feeType=" + feeType + ", course="
				+ course + ", term=" + term + ", courseVariant="
				+ courseVariant + ", feeAmt=" + feeAmt + ", rowid=" + rowid
				+ "]";
	}

	
	
}
