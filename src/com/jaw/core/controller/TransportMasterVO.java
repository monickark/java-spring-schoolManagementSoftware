package com.jaw.core.controller;

import java.io.Serializable;

import org.apache.log4j.Logger;

public class TransportMasterVO implements Serializable{
	// Logging
	 Logger logger = Logger.getLogger(TransportMasterVO.class);
	  
	
	// Properties

	private String pickupPointId = "";
	private String pickupPointName = "";
	private String Amount = "";
	private int rowId;
	
	public String getPickupPointId() {
		return pickupPointId;
	}
	public void setPickupPointId(String pickupPointId) {
		this.pickupPointId = pickupPointId;
	}
	public String getPickupPointName() {
		return pickupPointName;
	}
	public void setPickupPointName(String pickupPointName) {
		this.pickupPointName = pickupPointName;
	}
	public String getAmount() {
		return Amount;
	}
	public void setAmount(String amount) {
		Amount = amount;
	}
	public int getRowId() {
		return rowId;
	}
	public void setRowId(int rowId) {
		this.rowId = rowId;
	}
	
	
}
