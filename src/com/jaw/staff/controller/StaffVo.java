
package com.jaw.staff.controller;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;



public class StaffVo {
	private StaffMasterVo staffMasterVo=new StaffMasterVo();
	private StaffDetailsVo staffDetailsVo=new StaffDetailsVo();
	private FileTypeVO fileTypeVO=new FileTypeVO();
	private String isUser="N";
	private String isSame;
	private String userid;
	private String password; 
	private MultipartFile staffPhoto;
	private String branchId;
	private String isStaff;	
	private int dbTs;
	private String menuProfile;
	private List<MultipartFile> files;
	
	
	public FileTypeVO getFileTypeVO() {
		return fileTypeVO;
	}
	public void setFileTypeVO(FileTypeVO fileTypeVO) {
		this.fileTypeVO = fileTypeVO;
	}
	public List<MultipartFile> getFiles() {
		return files;
	}
	public void setFiles(List<MultipartFile> files) {
		this.files = files;
	}
	public String getMenuProfile() {
		return menuProfile;
	}
	public void setMenuProfile(String menuProfile) {
		this.menuProfile = menuProfile;
	}
	public int getDbTs() {
		return dbTs;
	}
	public void setDbTs(int dbTs) {
		this.dbTs = dbTs;
	}
	public String getIsStaff() {
		return isStaff;
	}
	public void setIsStaff(String isStaff) {
		this.isStaff = isStaff;
	}
	public String getIsSame() {
		return isSame;
	}
	public void setIsSame(String isSame) {
		this.isSame = isSame;
	}
	public String getBranchId() {
		return branchId;
	}
	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}
	public MultipartFile getStaffPhoto() {
		return staffPhoto;
	}
	public void setStaffPhoto(MultipartFile staffPhoto) {
		this.staffPhoto = staffPhoto;
	}
	public String getIsUser() {
		return isUser;
	}
	public void setIsUser(String isUser) {
		this.isUser = isUser;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public StaffMasterVo getStaffMasterVo() {
		return staffMasterVo;
	}
	public void setStaffMasterVo(StaffMasterVo staffMasterVo) {
		this.staffMasterVo = staffMasterVo;
	}
	public StaffDetailsVo getStaffDetailsVo() {
		return staffDetailsVo;
	}
	public void setStaffDetailsVo(StaffDetailsVo staffDetailsVo) {
		this.staffDetailsVo = staffDetailsVo;
	}


}
