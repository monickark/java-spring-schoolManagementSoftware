package com.jaw.fee.dao;

import java.io.Serializable;

import org.apache.log4j.Logger;

public class StudentFeeDiscountKey implements Serializable{

	// Logging
	 Logger logger = Logger.getLogger(StudentFeeDiscountKey.class);
	// Properties
	
		private String acTerm;
		private String stGroup;
		private String instId;
		private String branchId;

		public String getAcTerm() {
			return acTerm;
		}
		public void setAcTerm(String acTerm) {
			this.acTerm = acTerm;
		}
		public String getStGroup() {
			return stGroup;
		}
		public void setStGroup(String stGroup) {
			this.stGroup = stGroup;
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
		
		
}
