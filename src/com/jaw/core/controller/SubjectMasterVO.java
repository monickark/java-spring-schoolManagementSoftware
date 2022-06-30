package com.jaw.core.controller;

import java.io.Serializable;

import org.apache.log4j.Logger;

public class SubjectMasterVO implements Serializable {
	// Logging
	Logger logger = Logger.getLogger(SubjectMasterVO.class);

	// Properties
	private String sub_Id;
	private String sub_Name;
	private String cust_Code= "";
	private String short_Code= "";
	private String is_Com = "";
	private String is_Elective = "";
	private String is_Lang = "";
	private String is_rel = "";	
	private String isPreAcaSubject = "";	
	private String department = "";	
	private String courseName = "";	
	private int rowId;
	public String getSub_Id() {
		return sub_Id;
	}
	public void setSub_Id(String sub_Id) {
		this.sub_Id = sub_Id;
	}
	public String getSub_Name() {
		return sub_Name;
	}
	public void setSub_Name(String sub_Name) {
		this.sub_Name = sub_Name;
	}
	public String getCust_Code() {
		return cust_Code;
	}
	public void setCust_Code(String cust_Code) {
		this.cust_Code = cust_Code;
	}
	public String getShort_Code() {
		return short_Code;
	}
	public void setShort_Code(String short_Code) {
		this.short_Code = short_Code;
	}
	public String getIs_Com() {
		return is_Com;
	}
	public void setIs_Com(String is_Com) {
		this.is_Com = is_Com;
	}
	public String getIs_Elective() {
		return is_Elective;
	}
	public void setIs_Elective(String is_Elective) {
		this.is_Elective = is_Elective;
	}
	public String getIs_Lang() {
		return is_Lang;
	}
	public void setIs_Lang(String is_Lang) {
		this.is_Lang = is_Lang;
	}
	public int getRowId() {
		return rowId;
	}
	public void setRowId(int rowId) {
		this.rowId = rowId;
	}
	public String getIs_rel() {
		return is_rel;
	}
	public void setIs_rel(String is_rel) {
		this.is_rel = is_rel;
	}
	public String getIsPreAcaSubject() {
		return isPreAcaSubject;
	}
	public void setIsPreAcaSubject(String isPreAcaSubject) {
		this.isPreAcaSubject = isPreAcaSubject;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	@Override
	public String toString() {
		return "SubjectMasterVO [sub_Id=" + sub_Id + ", sub_Name=" + sub_Name
				+ ", cust_Code=" + cust_Code + ", short_Code=" + short_Code
				+ ", is_Com=" + is_Com + ", is_Elective=" + is_Elective
				+ ", is_Lang=" + is_Lang + ", is_rel=" + is_rel
				+ ", isPreAcaSubject=" + isPreAcaSubject + ", department="
				+ department + ", courseName=" + courseName + ", rowId="
				+ rowId + "]";
	}
	
	
		
}
