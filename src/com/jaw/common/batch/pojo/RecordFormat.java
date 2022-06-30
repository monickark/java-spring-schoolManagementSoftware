package com.jaw.common.batch.pojo;

import java.util.List;
import java.io.Serializable;

//Pojo class to read the XML for Excel Operations
public class RecordFormat implements Serializable{		
	
	private String titleInExcel;
	public String getTitleInExcel() {
		return titleInExcel;
	}

	public void setTitleInExcel(String titleInExcel) {
		this.titleInExcel = titleInExcel;
	}

	private String rectype;		
	private String recordProductOrCustom;	
	List<Field> fieldList ;	

	public String getRecordProductOrCustom() {
		return recordProductOrCustom;
	}

	@Override
	public String toString() {
		return "RecordFormat [titleInExcel=" + titleInExcel + ", rectype="
				+ rectype + ", recordProductOrCustom=" + recordProductOrCustom
				+ ", fieldList=" + fieldList + "]";
	}

	public void setRecordProductOrCustom(String recordProductOrCustom) {
		this.recordProductOrCustom = recordProductOrCustom;
	}

	public String getRectype() {
		return rectype;
	}

	public void setRectype(String rectype) {
		this.rectype = rectype;
	}

	public List<Field> getFieldList() {
		return fieldList;
	}

	public void setFieldList(List<Field> fieldList) {
		this.fieldList = fieldList;
	}
	

}
