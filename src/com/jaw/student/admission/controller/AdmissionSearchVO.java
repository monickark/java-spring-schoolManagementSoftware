package com.jaw.student.admission.controller;

import java.io.Serializable;

public class AdmissionSearchVO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String academicStatus = "";
	private String from_date;
	private String to_date;
		
	public String getAcademicStatus() {
		return academicStatus;
	}
	public void setAcademicStatus(String academicStatus) {
		this.academicStatus = academicStatus;
	}
	
	public String getFrom_date() {
		return from_date;
	}
	public void setFrom_date(String from_date) {
		this.from_date = from_date;
	}
	public String getTo_date() {
		return to_date;
	}
	public void setTo_date(String to_date) {
		this.to_date = to_date;
	}
	@Override
	public String toString() {
		return "AdmissionSearchVO [academicStatus=" + academicStatus
				+ ", from_date=" + from_date + ", to_date=" + to_date
				+ ", getAcademicStatus()=" + getAcademicStatus()
				+ ", getFrom_date()=" + getFrom_date() + ", getTo_date()="
				+ getTo_date() + ", getClass()=" + getClass() + ", hashCode()="
				+ hashCode() + ", toString()=" + super.toString() + "]";
	}
	
}
