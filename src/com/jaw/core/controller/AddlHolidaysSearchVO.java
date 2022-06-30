package com.jaw.core.controller;

import org.apache.log4j.Logger;

public class AddlHolidaysSearchVO {
	// Logging
	 Logger logger = Logger.getLogger(AddlHolidaysSearchVO.class);

	// Properties
	private String acTerm;
	private String ahId;
	private String studentGrpId ;
	private String holFromDate ;
	private String holToDate ;
	public String getAcTerm() {
		return acTerm;
	}
	public void setAcTerm(String acTerm) {
		this.acTerm = acTerm;
	}
	public String getAhId() {
		return ahId;
	}
	public void setAhId(String ahId) {
		this.ahId = ahId;
	}
	public String getStudentGrpId() {
		return studentGrpId;
	}
	public void setStudentGrpId(String studentGrpId) {
		this.studentGrpId = studentGrpId;
	}
	public String getHolFromDate() {
		return holFromDate;
	}
	public void setHolFromDate(String holFromDate) {
		this.holFromDate = holFromDate;
	}
	public String getHolToDate() {
		return holToDate;
	}
	public void setHolToDate(String holToDate) {
		this.holToDate = holToDate;
	}
	@Override
	public String toString() {
		return "AddlHolidaysSearchVO [acTerm=" + acTerm + ", ahId=" + ahId
				+ ", studentGrpId=" + studentGrpId + ", holFromDate="
				+ holFromDate + ", holToDate=" + holToDate + "]";
	}
}
