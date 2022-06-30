package com.jaw.student.admission.controller;

import org.springframework.web.multipart.MultipartFile;
import java.io.Serializable;
public class TransportDetailsVO implements Serializable{
	
	private Integer dbTs;
	private String instId = "";
	private String branchId = "";
	private String studentAdmisNo = "";
	private String academicYear = "";
	private String serialNoForExcelUpdate = "";		
	private MultipartFile pickupAssPhoto = null;			
	private String modeOfTransport = "";
	private String vehicleNo = "";
	private String pickupPoint = "";
	private String droppingPoint = "";
	private String pickupAssSalut = "";
	private String pickupAssName = "";
	private String pickupAssPhotoRefno = "";	
	
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



	public String getStudentAdmisNo() {
		return studentAdmisNo;
	}



	public void setStudentAdmisNo(String studentAdmisNo) {
		this.studentAdmisNo = studentAdmisNo;
	}



	public String getAcademicYear() {
		return academicYear;
	}



	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
	}


	

	

	public Integer getDbTs() {
		return dbTs;
	}

	

	public String getSerialNoForExcelUpdate() {
		return serialNoForExcelUpdate;
	}



	public void setSerialNoForExcelUpdate(String serialNoForExcelUpdate) {
		this.serialNoForExcelUpdate = serialNoForExcelUpdate;
	}



	public void setDbTs(Integer dbTs) {
		this.dbTs = dbTs;
	}

	public MultipartFile getPickupAssPhoto() {
		return pickupAssPhoto;
	}

	public void setPickupAssPhoto(MultipartFile pickupAssPhoto) {
		this.pickupAssPhoto = pickupAssPhoto;
	}

	public String getModeOfTransport() {
		return modeOfTransport;
	}

	public void setModeOfTransport(String modeOfTransport) {
		this.modeOfTransport = modeOfTransport;
	}

	public String getVehicleNo() {
		return vehicleNo;
	}

	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}

	public String getPickupPoint() {
		return pickupPoint;
	}

	public void setPickupPoint(String pickupPoint) {
		this.pickupPoint = pickupPoint;
	}

	public String getDroppingPoint() {
		return droppingPoint;
	}

	public void setDroppingPoint(String droppingPoint) {
		this.droppingPoint = droppingPoint;
	}

	public String getPickupAssSalut() {
		return pickupAssSalut;
	}

	public void setPickupAssSalut(String pickupAssSalut) {
		this.pickupAssSalut = pickupAssSalut;
	}

	public String getPickupAssName() {
		return pickupAssName;
	}

	public void setPickupAssName(String pickupAssName) {
		this.pickupAssName = pickupAssName;
	}

	public String getPickupAssPhotoRefno() {
		return pickupAssPhotoRefno;
	}

	public void setPickupAssPhotoRefno(String pickupAssPhotoRefno) {
		this.pickupAssPhotoRefno = pickupAssPhotoRefno;
	}
}
