package com.jaw.communication.controller;

import java.io.Serializable;
import java.util.Arrays;

import org.apache.log4j.Logger;

public class AlertSearchVo implements Serializable{
	
	// Logging
	 Logger logger = Logger.getLogger(AlertSearchVo.class);
	
	// Properties	
	    private String acTerm ;		
	    private String reqstCategory ;
		private String[] generalGrpList=null;
		private String generalGrpListRadio;
		private String fromDate;
		private String toDate;
		private String alertStop;
		private String important;
		public String getAcTerm() {
			return acTerm;
		}
		public void setAcTerm(String acTerm) {
			this.acTerm = acTerm;
		}
		public String getReqstCategory() {
			return reqstCategory;
		}
		public void setReqstCategory(String reqstCategory) {
			this.reqstCategory = reqstCategory;
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
		public String getAlertStop() {
			return alertStop;
		}
		public void setAlertStop(String alertStop) {
			this.alertStop = alertStop;
		}
		public String getImportant() {
			return important;
		}
		public void setImportant(String important) {
			this.important = important;
		}
		public String[] getGeneralGrpList() {
			return generalGrpList;
		}
		public void setGeneralGrpList(String[] generalGrpList) {
			this.generalGrpList = generalGrpList;
		}
		public String getGeneralGrpListRadio() {
			return generalGrpListRadio;
		}
		public void setGeneralGrpListRadio(String generalGrpListRadio) {
			this.generalGrpListRadio = generalGrpListRadio;
		}
		@Override
		public String toString() {
			return "AlertSearchVo [acTerm=" + acTerm + ", reqstCategory="
					+ reqstCategory + ", generalGrpList="
					+ Arrays.toString(generalGrpList)
					+ ", generalGrpListRadio=" + generalGrpListRadio
					+ ", fromDate=" + fromDate + ", toDate=" + toDate
					+ ", alertStop=" + alertStop + ", important=" + important
					+ "]";
		}
		
		
		

}
