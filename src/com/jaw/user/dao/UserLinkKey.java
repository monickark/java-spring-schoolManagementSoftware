package com.jaw.user.dao;
import java.io.Serializable;
public class UserLinkKey implements Serializable{
	
	private String instId;
	private String userId;
	private String linkId;
	public String getLinkId() {
		return linkId;
	}
	public void setLinkId(String linkId) {
		this.linkId = linkId;
	}
	public String getInstId() {
		return instId;
	}
	public void setInstId(String instId) {
		this.instId = instId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}

}
