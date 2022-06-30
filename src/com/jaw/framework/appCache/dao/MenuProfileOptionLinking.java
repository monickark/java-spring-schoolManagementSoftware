package com.jaw.framework.appCache.dao;

import java.io.Serializable;

public class MenuProfileOptionLinking implements Serializable {

	private String instId;
	private String branchId;
	private String menuId;
	private String menuProfile;
	private String menuOption;
	private String menuDescription;
	private String slmFlg;
	private String menuUrl;
	private int menuNode;
	private int menuLevel;
	private String menuIcon;
	private int menuOrder;
	private int levelOrder;
	
	
	public int getLevelOrder() {
		return levelOrder;
	}

	public void setLevelOrder(int i) {
		this.levelOrder = i;
	}

	public int getMenuOrder() {
		return menuOrder;
	}

	public void setMenuOrder(int menuOrder) {
		this.menuOrder = menuOrder;
	}

	public String getMenuIcon() {
		return menuIcon;
	}

	public void setMenuIcon(String string) {
		menuIcon = string;
	}

	public String getSlmFlg() {
		return slmFlg;
	}

	public void setSlmFlg(String slmFlg) {
		this.slmFlg = slmFlg;
	}

	/**
	 * @return the branchId
	 */
	public String getBranchId() {
		return branchId;
	}

	/**
	 * @param branchId
	 *            the branchId to set
	 */
	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

	/**
	 * @return the instId
	 */
	public String getInstId() {
		return instId;
	}

	/**
	 * @param instId
	 *            the instId to set
	 */
	public void setInstId(String instId) {
		this.instId = instId;
	}

	public String getMenuProfile() {
		return menuProfile;
	}

	public void setMenuProfile(String menuProfile) {
		this.menuProfile = menuProfile;
	}

	public String getMenuOption() {
		return menuOption;
	}

	public void setMenuOption(String menuOption) {
		this.menuOption = menuOption;
	}

	/**
	 * @return the menuId
	 */
	public String getMenuId() {
		return menuId;
	}

	/**
	 * @param menuId
	 *            the menuId to set
	 */
	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	/**
	 * @return the menuDescription
	 */
	public String getMenuDescription() {
		return menuDescription;
	}

	/**
	 * @param menuDescription
	 *            the menuDescription to set
	 */
	public void setMenuDescription(String menuDescription) {
		this.menuDescription = menuDescription;
	}

	/**
	 * @return the menuUrl
	 */
	public String getMenuUrl() {
		return menuUrl;
	}

	/**
	 * @param menuUrl
	 *            the menuUrl to set
	 */
	public void setMenuUrl(String menuUrl) {
		this.menuUrl = menuUrl;
	}

	/**
	 * @return the menuNode
	 */
	public int getMenuNode() {
		return menuNode;
	}

	/**
	 * @param menuNode
	 *            the menuNode to set
	 */
	public void setMenuNode(int menuNode) {
		this.menuNode = menuNode;
	}

	/**
	 * @return the menuLevel
	 */
	public int getMenuLevel() {
		return menuLevel;
	}

	/**
	 * @param menuLevel
	 *            the menuLevel to set
	 */
	public void setMenuLevel(int menuLevel) {
		this.menuLevel = menuLevel;
	}

	@Override
	public String toString() {
		return "MenuProfileOptionLinking [instId=" + instId + ", branchId="
				+ branchId + ", menuId=" + menuId + ", menuProfile="
				+ menuProfile + ", menuOption=" + menuOption
				+ ", menuDescription=" + menuDescription + ", slmFlg=" + slmFlg
				+ ", menuUrl=" + menuUrl + ", menuNode=" + menuNode
				+ ", menuLevel=" + menuLevel + ", menuIcon=" + menuIcon
				+ ", menuOrder=" + menuOrder + "]";
	}

}
