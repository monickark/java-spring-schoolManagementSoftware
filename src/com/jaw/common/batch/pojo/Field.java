package com.jaw.common.batch.pojo;
import java.io.Serializable;
//Pojo class to read the XML for Excel Operations
public class Field implements Serializable{
	
	String field_Name_Product = "";
	String filed_Name_Custom = "";
	String field_Type = "";
	String field_Size = "";
	String field_Mandatory_Product = "";
	String field_Mandatory_Custom = "";
	String field_Mandatory_Custom_Criteria = "";	
	String exclude_From_Dwnld = "NO";
	String cocd_Type = "";
	String default_Value = "";	
	String field_Property_Name = "";
	String field_Class_Name = "";	
	String field_Unique_key = "N";
	@Override
	public String toString() {
		return "Field [field_Name_Product=" + field_Name_Product
				+ ", filed_Name_Custom=" + filed_Name_Custom + ", field_Type="
				+ field_Type + ", field_Size=" + field_Size
				+ ", field_Mandatory_Product=" + field_Mandatory_Product
				+ ", field_Mandatory_Custom=" + field_Mandatory_Custom
				+ ", field_Mandatory_Custom_Criteria="
				+ field_Mandatory_Custom_Criteria + ", exclude_From_Dwnld="
				+ exclude_From_Dwnld + ", cocd_Type=" + cocd_Type
				+ ", default_Value=" + default_Value + ", field_Property_Name="
				+ field_Property_Name + ", field_Class_Name="
				+ field_Class_Name + ", field_Unique_key=" + field_Unique_key
				+ ", field_DB_ColumnName=" + field_DB_ColumnName
				+ ", field_Order=" + field_Order + "]";
	}
	String field_DB_ColumnName = "";	
	
	
	
	public String getField_DB_ColumnName() {
		return field_DB_ColumnName;
	}
	public void setField_DB_ColumnName(String field_DB_ColumnName) {
		this.field_DB_ColumnName = field_DB_ColumnName;
	}
	public String getField_Unique_key() {
		return field_Unique_key;
	}
	public void setField_Unique_key(String field_Unique_key) {
		this.field_Unique_key = field_Unique_key;
	}
	public String getField_Property_Name() {
		return field_Property_Name;
	}
	public void setField_Property_Name(String field_Property_Name) {
		this.field_Property_Name = field_Property_Name;
	}
	public String getField_Class_Name() {
		return field_Class_Name;
	}
	public void setField_Class_Name(String field_Class_Name) {
		this.field_Class_Name = field_Class_Name;
	}
	public String getExclude_From_Dwnld() {
		return exclude_From_Dwnld;
	}
	public void setExclude_From_Dwnld(String exclude_From_Dwnld) {
		this.exclude_From_Dwnld = exclude_From_Dwnld;
	}
	public String getDefault_Value() {
		return default_Value;
	}
	public void setDefault_Value(String default_Value) {
		this.default_Value = default_Value;
	}
	String field_Order;
	public String getField_Name_Product() {
		return field_Name_Product;
	}
	public void setField_Name_Product(String field_Name_Product) {
		this.field_Name_Product = field_Name_Product;
	}
	public String getField_Order() {
		return field_Order;
	}
	public void setField_Order(String field_Order) {
		this.field_Order = field_Order;
	}
	public String getFiled_Name_Custom() {
		return filed_Name_Custom;
	}
	public void setFiled_Name_Custom(String filed_Name_Custom) {
		this.filed_Name_Custom = filed_Name_Custom;
	}
	public String getField_Type() {
		return field_Type;
	}
	public void setField_Type(String field_Type) {
		this.field_Type = field_Type;
	}
	public String getField_Size() {
		return field_Size;
	}
	public void setField_Size(String field_Size) {
		this.field_Size = field_Size;
	}
	public String getField_Mandatory_Product() {
		return field_Mandatory_Product;
	}
	public void setField_Mandatory_Product(String field_Mandatory_Product) {
		this.field_Mandatory_Product = field_Mandatory_Product;
	}
	public String getField_Mandatory_Custom() {
		return field_Mandatory_Custom;
	}
	public void setField_Mandatory_Custom(String field_Mandatory_Custom) {
		this.field_Mandatory_Custom = field_Mandatory_Custom;
	}
	public String getField_Mandatory_Custom_Criteria() {
		return field_Mandatory_Custom_Criteria;
	}
	public void setField_Mandatory_Custom_Criteria(
			String field_Mandatory_Custom_Criteria) {
		this.field_Mandatory_Custom_Criteria = field_Mandatory_Custom_Criteria;
	}
	public String getCocd_Type() {
		return cocd_Type;
	}
	public void setCocd_Type(String cocd_Type) {
		this.cocd_Type = cocd_Type;
	}
	
	

}
