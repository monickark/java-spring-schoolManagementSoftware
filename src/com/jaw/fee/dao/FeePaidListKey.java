package com.jaw.fee.dao;

import java.io.Serializable;

import org.apache.log4j.Logger;

public class FeePaidListKey implements Serializable{

	// Logging
	 Logger logger = Logger.getLogger(FeeDueListKey.class);
	// Properties	  
		private String instId;		
		private String admissionNumber="";
		private String academicTerm = "";
		private String branchId;
		public String getInstId() {
			return instId;
		}
		public void setInstId(String instId) {
			this.instId = instId;
		}
		public String getAdmissionNumber() {
			return admissionNumber;
		}
		public void setAdmissionNumber(String admissionNumber) {
			this.admissionNumber = admissionNumber;
		}
		public String getAcademicTerm() {
			return academicTerm;
		}
		public void setAcademicTerm(String academicTerm) {
			this.academicTerm = academicTerm;
		}
		public String getBranchId() {
			return branchId;
		}
		public void setBranchId(String branchId) {
			this.branchId = branchId;
		}
		@Override
		public String toString() {
			return "FeePaidListKey [instId=" + instId + ", admissionNumber="
					+ admissionNumber + ", academicTerm=" + academicTerm
					+ ", branchId=" + branchId + "]";
		}
		
}
