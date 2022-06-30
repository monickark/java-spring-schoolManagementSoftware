package com.jaw.staff.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.jaw.common.constants.MenuOptionConstant;

public class FileTypeVO implements Serializable {

	private List<String> certicates = new ArrayList<String>();
	private List<String> biodata = new ArrayList<String>();
	private List<MultipartFile> certificateFiles;
	private List<MultipartFile> bioDataFiles;
	private String menuOption ="";
	private String staffId = "";

	public String getMenuOption() {
		return menuOption;
	}

	public void setMenuOption(String menuOption) {
		this.menuOption = menuOption;
	}

	public List<MultipartFile> getCertificateFiles() {
		return certificateFiles;
	}

	public void setCertificateFiles(List<MultipartFile> certificateFiles) {
		this.certificateFiles = certificateFiles;
	}

	public List<MultipartFile> getBioDataFiles() {
		return bioDataFiles;
	}

	public void setBioDataFiles(List<MultipartFile> bioDataFiles) {
		this.bioDataFiles = bioDataFiles;
	}

	public List<String> getCerticates() {
		return certicates;
	}

	public void setCerticates(List<String> certicates) {
		this.certicates = certicates;
	}

	public List<String> getBiodata() {
		return biodata;
	}

	public void setBiodata(List<String> biodata) {
		this.biodata = biodata;
	}

	public String getStaffId() {
		return staffId;
	}

	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}

	@Override
	public String toString() {
		return "FileTypeVO [certicates=" + certicates + ", biodata=" + biodata
				+ ", staffId=" + staffId + "]";
	}

}
