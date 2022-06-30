package com.jaw.core.controller;

import java.util.ArrayList;

import org.apache.log4j.Logger;

public class AddlHolidaysMasterVO {
	// Logging
			 Logger logger = Logger.getLogger(AddlHolidaysMasterVO.class);

			// Properties
	private String pageSize="10";	
	private ArrayList<AddlHolidaysVO> addlHolidaysVOList = new ArrayList<AddlHolidaysVO>();	
	private AddlHolidaysVO addlHolidaysVO = new AddlHolidaysVO();
	private AddlHolidaysSearchVO addlHolidaysSearchVo=new AddlHolidaysSearchVO();
	public String getPageSize() {
		return pageSize;
	}
	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}
	public ArrayList<AddlHolidaysVO> getAddlHolidaysVOList() {
		return addlHolidaysVOList;
	}
	public void setAddlHolidaysVOList(ArrayList<AddlHolidaysVO> addlHolidaysVOList) {
		this.addlHolidaysVOList = addlHolidaysVOList;
	}
	public AddlHolidaysVO getAddlHolidaysVO() {
		return addlHolidaysVO;
	}
	public void setAddlHolidaysVO(AddlHolidaysVO addlHolidaysVO) {
		this.addlHolidaysVO = addlHolidaysVO;
	}
	public AddlHolidaysSearchVO getAddlHolidaysSearchVo() {
		return addlHolidaysSearchVo;
	}
	public void setAddlHolidaysSearchVo(AddlHolidaysSearchVO addlHolidaysSearchVo) {
		this.addlHolidaysSearchVo = addlHolidaysSearchVo;
	}

	
	

}
