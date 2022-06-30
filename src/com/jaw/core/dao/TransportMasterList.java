package com.jaw.core.dao;

import java.io.Serializable;

import org.apache.log4j.Logger;

import com.jaw.common.constants.AuditConstant;
//CourseMaster Pojo class
public class TransportMasterList implements Serializable{
	// Logging
			 Logger logger = Logger.getLogger(TransportMasterList.class);
		
			private String pickupPointName = "";
			private double amount ;
			
			public String getPickupPointName() {
				return pickupPointName;
			}
			public void setPickupPointName(String pickupPointName) {
				this.pickupPointName = pickupPointName;
			}
			public double getAmount() {
				return amount;
			}
			public void setAmount(double amount) {
				this.amount = amount;
			}	
			
			
			
}
