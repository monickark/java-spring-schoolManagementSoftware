package com.jaw.student.admission.controller;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class FileTypeVO implements Serializable{
	private String studentPhoto = "";
	private String studentBirth = "";
	private String studentCaste = "";
	private String fatherPhoto = "";
	private String motherPhoto = "";
	private String guardianPhoto = "";
	private List<String> sportsCert = new ArrayList<String>();


	public String getStuMedicalCert() {
		return stuMedicalCert;
	}
	

	public void setStuMedicalCert(String stuMedicalCert) {
		this.stuMedicalCert = stuMedicalCert;
	}
	
	private String transCert = "";
	@Override
	public String toString() {
		return "FileTypeVO [studentPhoto=" + studentPhoto + ", studentBirth="
				+ studentBirth + ", studentCaste=" + studentCaste
				+ ", fatherPhoto=" + fatherPhoto + ", motherPhoto="
				+ motherPhoto + ", guardianPhoto=" + guardianPhoto
				+ ", sportsCert=" + sportsCert + ", transCert=" + transCert
				+ ", studentMark=" + studentMark + ", transAssitPhoto="
				+ transAssitPhoto + ", stuMedicalCert=" + stuMedicalCert + "]";
	}


	public List<String> getSportsCert() {
		return sportsCert;
	}


	public void setSportsCert(List<String> sportsCert) {
		this.sportsCert = sportsCert;
	}

	private String studentMark = "";
	private String transAssitPhoto = "";
	private String stuMedicalCert = "";

	public String getStudentPhoto() {
		return studentPhoto;
	}

	public void setStudentPhoto(String studentPhoto) {
		this.studentPhoto = studentPhoto;
	}

	public String getStudentBirth() {
		return studentBirth;
	}

	public void setStudentBirth(String studentBirth) {
		this.studentBirth = studentBirth;
	}

	public String getStudentCaste() {
		return studentCaste;
	}

	public void setStudentCaste(String studentCaste) {
		this.studentCaste = studentCaste;
	}

	public String getFatherPhoto() {
		return fatherPhoto;
	}

	public void setFatherPhoto(String fatherPhoto) {
		this.fatherPhoto = fatherPhoto;
	}

	public String getMotherPhoto() {
		return motherPhoto;
	}

	public void setMotherPhoto(String motherPhoto) {
		this.motherPhoto = motherPhoto;
	}

	public String getGuardianPhoto() {
		return guardianPhoto;
	}

	public void setGuardianPhoto(String guardianPhoto) {
		this.guardianPhoto = guardianPhoto;
	}

	public String getTransCert() {
		return transCert;
	}

	public void setTransCert(String transCert) {
		this.transCert = transCert;
	}

	public String getStudentMark() {
		return studentMark;
	}

	public void setStudentMark(String studentMark) {
		this.studentMark = studentMark;
	}

	public String getTransAssitPhoto() {
		return transAssitPhoto;
	}

	public void setTransAssitPhoto(String transAssitPhoto) {
		this.transAssitPhoto = transAssitPhoto;
	}

}
