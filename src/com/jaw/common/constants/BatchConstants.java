package com.jaw.common.constants;

public class BatchConstants {
	//For batch status
	public static final String BATCH_INITIALIZED="INIT";
	public static final String BATCH_FAILED="SUSPEND";
	public static final String BATCH_SUCCESS="SUCCESS";
	//Objects involved in batch pgm
	public static final String BATCH_STUDENTMASTER_OBJ="StudentMasterVO";
	public static final String BATCH_STUDENTINFO_OBJ="StudentInfoVO";
	public static final String BATCH_PREVACADEMICDETAILS_OBJ="PrevAcademicDetailsVO";
	public static final String BATCH_PARENTDETAILS_OBJ="ParentDetailsVO";
	public static final String BATCH_SIBLINGDETAILS_OBJ="SiblingDetailsVO";
	public static final String BATCH_COMMDETAILS_OBJ="CommunicationDetailsVO";
	public static final String BATCH_TRANSDETAILS_OBJ="TransportDetailsVO";
	public static final String BATCH_USERLINKDETAILS_OBJ="UserLink";
	public static final String BATCH_USER_OBJ="UserVO";
	//Tbl Name involved in Batch
	public static final String BATCH_STUDENTMASTER_TBL_NAME="stum";
	public static final String BATCH_STUDENTINFO_TBL_NAME="stin";
	public static final String BATCH_PREVACADEMICDETAILS_TBL_NAME="psde";
	public static final String BATCH_PARENTDETAILS_TBL_NAME="pard";
	public static final String BATCH_SIBLINGDETAILS_TBL_NAME="sibd";
	public static final String BATCH_COMMDETAILS_TBL_NAME="comd";
	public static final String BATCH_TRANSDETAILS_TBL_NAME="trsd";
	public static final String BATCH_USERLINKDETAILS_TBL_NAME="usrl";
	public static final String BATCH_USER_TBL_NAME="user";
	//validations for duplicate entries
	public static final String BATCH_DUPLICATE_WITHINEXCEL="WithinExcelDuplication";
	public static final String BATCH_DUPLICATE_WITHDB="WithDBDupilcation";
	
	//Font for Excel 
	public static final String BATCH_EXCEL_DEFAULT_FONT_FACE = "VERDANA";
	
	//For rCreId and rModId
	public static final String BATCH_UPLD_USER_ID="UPLD";
	
	//For Excel format
	public static final String BATCH_EXCEL_FORMATS_1="XLS";
	public static final String BATCH_EXCEL_FORMATS_2="XLSX";
	
	public static final String Class_Name_Seperator_XML = ":";
	public static final String SERIAL_NO_PROP_NAME = "SerialNoForExcelUpdate";
	
	public static final String BATCH_SIZE_FOR_DB_OPERATION="10";
	public static final String BATCH_SIZE_FOR_SEND_SMS="70";
	public static final String BATCH_SIZE_FOR_STUDENT_FEE_INSERT="300";
	
}
