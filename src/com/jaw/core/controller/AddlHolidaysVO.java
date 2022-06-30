package com.jaw.core.controller;

import java.io.Serializable;

import org.apache.log4j.Logger;

public class AddlHolidaysVO implements Serializable{
	// Logging
		 Logger logger = Logger.getLogger(AddlHolidaysVO.class);

		// Properties
		private String acTerm ;
		private String ahId;
		private String studentGrpId ;
		private String holFromDate ;		
		private String holToDate ;
		private String ahRemarks;
		private int rowId;
		private String studentGrpName;
							
		public String getAhId() {
			return ahId;
		}
		public void setAhId(String ahId) {
			this.ahId = ahId;
		}
		
		public String getAcTerm() {
			return acTerm;
		}
		public void setAcTerm(String acTerm) {
			this.acTerm = acTerm;
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
		public String getAhRemarks() {
			return ahRemarks;
		}
		public void setAhRemarks(String ahRemarks) {
			this.ahRemarks = ahRemarks;
		}
		public int getRowId() {
			return rowId;
		}
		public void setRowId(int rowId) {
			this.rowId = rowId;
		}
		public String getStudentGrpName() {
			return studentGrpName;
		}
		public void setStudentGrpName(String studentGrpName) {
			this.studentGrpName = studentGrpName;
		}
		@Override
		public String toString() {
			return "AddlHolidaysVO [acTerm=" + acTerm + ", ahId=" + ahId
					+ ", studentGrpId=" + studentGrpId + ", holFromDate="
					+ holFromDate + ", holToDate=" + holToDate + ", ahRemarks="
					+ ahRemarks + ", rowId=" + rowId + ", studentGrpName="
					+ studentGrpName + "]";
		}
		
		
}
