package com.jaw.student.admission.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

public class NewAdmissionDetailsVO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Logger logger = Logger.getLogger(NewAdmissionDetailsVO.class);
	
	private AdmissionSearchVO admissionSearchVO;
	private AdmissionListVO admissionListVO;
	private AdmissionCountListVO admissionCountListVO;
	private List<AdmissionListVO> admissionList=new ArrayList<AdmissionListVO>();
	private List<AdmissionCountListVO> admissionCountList=new ArrayList<AdmissionCountListVO>();
	private String pageNo = "";	
	
	public AdmissionSearchVO getAdmissionSearchVO() {
		return admissionSearchVO;
	}
	public void setAdmissionSearchVO(AdmissionSearchVO admissionSearchVO) {
		this.admissionSearchVO = admissionSearchVO;
	}
	public AdmissionListVO getAdmissionListVO() {
		return admissionListVO;
	}
	public void setAdmissionListVO(AdmissionListVO admissionListVO) {
		this.admissionListVO = admissionListVO;
	}
	public AdmissionCountListVO getAdmissionCountListVO() {
		return admissionCountListVO;
	}
	public void setAdmissionCountListVO(AdmissionCountListVO admissionCountListVO) {
		this.admissionCountListVO = admissionCountListVO;
	}
	
	public List<AdmissionListVO> getAdmissionList() {
		return admissionList;
	}
	public void setAdmissionList(List<AdmissionListVO> admissionList) {
		this.admissionList = admissionList;
	}
	public List<AdmissionCountListVO> getAdmissionCountList() {
		return admissionCountList;
	}
	public void setAdmissionCountList(List<AdmissionCountListVO> admissionCountList) {
		this.admissionCountList = admissionCountList;
	}
	public String getPageNo() {
		return pageNo;
	}
	public void setPageNo(String pageNo) {
		this.pageNo = pageNo;
	}
	@Override
	public String toString() {
		return "NewAdmissionDetailsVO [admissionSearchVO=" + admissionSearchVO
				+ ", admissionListVO=" + admissionListVO
				+ ", admissionCountListVO=" + admissionCountListVO
				+ ", admissionList=" + admissionList + ", admissionCountList="
				+ admissionCountList + ", pageNo=" + pageNo
				+ ", getAdmissionSearchVO()=" + getAdmissionSearchVO()
				+ ", getAdmissionListVO()=" + getAdmissionListVO()
				+ ", getAdmissionCountListVO()=" + getAdmissionCountListVO()
				+ ", getAdmissionList()=" + getAdmissionList()
				+ ", getAdmissionCountList()=" + getAdmissionCountList()
				+ ", getPageNo()=" + getPageNo() + ", getClass()=" + getClass()
				+ ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}
	
	
}
